package com.iptv.browser;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import java.lang.annotation.Annotation;

import org.chromium.base.ApplicationStatus;
import org.chromium.base.CommandLine;
import org.chromium.base.ContextUtils;
import org.chromium.base.library_loader.LibraryLoader;
import org.chromium.base.library_loader.LibraryProcessType;
import org.chromium.base.PathUtils;
import org.chromium.base.ThreadUtils;
import org.chromium.content.browser.JavascriptInterface;
import org.chromium.content_public.browser.BrowserStartupController;
import org.chromium.content_public.browser.JavascriptInjector;
import org.chromium.content_public.browser.WebContents;
import org.chromium.content_shell.Shell;
import org.chromium.content_shell.ShellManager;
import org.chromium.ui.base.EventForwarder;

public class WebView extends ShellManager {
  private static final String COMMAND_LINE_FILE = "/data/local/tmp/shell-command-line";

  public static void attachBaseContext(Application app) {
    ContextUtils.initApplicationContext(app);
    PathUtils.setPrivateDataDirectorySuffix("data");
    ApplicationStatus.initialize(app);
  }

  public static void initWebView() {
    if (!CommandLine.isInitialized())
      CommandLine.initFromFile(COMMAND_LINE_FILE);
    LibraryLoader.getInstance().ensureInitialized(LibraryProcessType.PROCESS_BROWSER);
  }

  public static void Config(final String key, final String value) {
    if (TextUtils.isEmpty(value))
      CommandLine.getInstance().appendSwitch(key);
    else
      CommandLine.getInstance().appendSwitchWithValue(key, value);
  }

  public interface StartupCallback {
    void onSuccess();
    void onFailure();
    void onCreateTab(int id);
    void onDestroyTab(int id);
  }

  private StartupCallback mStartupCallback = null;
  public WebView(final Activity activity, final Bundle savedInstanceState, StartupCallback callback) {
    super(activity);

    getWindow().restoreInstanceState(savedInstanceState);
    getWindow().setAnimationPlaceholderView(getContentViewRenderView().getSurfaceView());
    mStartupCallback = callback;

    BrowserStartupController.get(LibraryProcessType.PROCESS_BROWSER).startBrowserProcessesAsync(
        true, false, new BrowserStartupController.StartupCallback() {
          @Override
          public void onSuccess() {
            if (mStartupCallback != null)
              mStartupCallback.onSuccess();
          }

          @Override
          public void onFailure() {
            if (mStartupCallback != null)
              mStartupCallback.onFailure();
          }
        });
  }

  @Override
  protected void onCreateTab(int id) {
    if (mStartupCallback != null)
      mStartupCallback.onCreateTab(id);
  }

  @Override
  protected void onDestroyTab(int id) {
    if (mStartupCallback != null)
      mStartupCallback.onDestroyTab(id);
  }

  public void loadUrl(final String url) {
    Shell activeView = getActiveShell();
    if (activeView != null)
      activeView.loadUrl(url);
    else
      launchUrl(url);
  }

  public void onSaveInstanceState(Bundle outState) {
    getWindow().saveInstanceState(outState);
  }

  public void onStart() {
    WebContents webContents = getActiveWebContents();
    if (webContents != null) webContents.onShow();
  }

  public void onDestroy() {
    destroy();
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    getWindow().onActivityResult(requestCode, resultCode, data);
  }

  public WebContents getActiveWebContents() {
    Shell shell = getActiveShell();
    return shell != null ? shell.getWebContents() : null;
  }

  public void dispatchCustomKeyEvent(KeyEvent event) {
    ThreadUtils.runOnUiThread(() -> {
      if (getActiveWebContents() != null)
        getActiveWebContents().getEventForwarder().dispatchKeyEvent(event);
    });
  }

  private JavascriptInjector getJavascriptInjector() {
    if (getActiveWebContents() != null)
      return JavascriptInjector.fromWebContents(getActiveWebContents());
    return null;
  }

  public void addJavascriptInterface(Object object, String name) {
    Class<? extends Annotation> requiredAnnotation = JavascriptInterface.class;
    if (getJavascriptInjector() != null)
      getJavascriptInjector().addPossiblyUnsafeInterface(object, name, requiredAnnotation);
  }

  public void removeJavascriptInterface(String interfaceName) {
    if (getJavascriptInjector() != null)
      getJavascriptInjector().removeInterface(interfaceName);
  }

  public void addInitJavascriptString(final String script) {
    if (getActiveWebContents() != null)
      getActiveWebContents().addInitJavascriptString(script);
  }
}


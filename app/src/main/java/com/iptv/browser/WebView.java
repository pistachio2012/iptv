package com.iptv.browser;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import java.lang.annotation.Annotation;

import org.chromium.base.ApplicationStatus;
import org.chromium.base.CommandLine;
import org.chromium.base.ContextUtils;
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
  public static void Init(Activity a) {
    ContextUtils.initApplicationContext(a.getApplication());
    PathUtils.setPrivateDataDirectorySuffix("data");
    ApplicationStatus.initialize(a.getApplication());

    if (!CommandLine.isInitialized())
      CommandLine.initFromFile("/data/local/tmp/command-line");
    CommandLine.getInstance().appendSwitch("disable-es3-gl-context");
  }

  public interface StartupCallback {
    void onSuccess();
    void onFailure();
    void onCreateTab(int id);
    void onDestroyTab(int id);
  }

  private StartupCallback mStartupCallback = null;
  public WebView(final Activity activity, StartupCallback callback) {
    super(activity);
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

  public void onStart() {
    WebContents webContents = getActiveWebContents();
    if (webContents != null) webContents.onShow();
  }

  public void onDestroy() {
    destroy();
  }

  public WebContents getActiveWebContents() {
    Shell shell = getActiveShell();
    return shell != null ? shell.getWebContents() : null;
  }

  @Override
  public boolean dispatchKeyEvent(KeyEvent event) {
    return super.dispatchKeyEvent(event);
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


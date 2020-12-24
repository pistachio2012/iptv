package com.iptv.browser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.content.res.AssetManager;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import android.widget.FrameLayout;
import android.view.Gravity;

import org.chromium.content.browser.JavascriptInterface;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import com.iptv.ijkplayer.IjkVideoView;
import com.iptv.ijkplayer.AndroidMediaController;

public class MainActivity extends Activity {
  private final String TAG = "MainActivity";
  private String mStartupUrl;

  private FrameLayout mRoot = null;
  private IjkVideoView mVideoView = null;
  private WebView web = null;
  private WebContentsObserverAndroid mWebContentsObserver = null;
  private AndroidMediaController mMediaController;
  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER);

    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.MATCH_PARENT,
        Gravity.CENTER);
    mRoot = new FrameLayout(this);
    setContentView(mRoot, layoutParams);

    IjkMediaPlayer.loadLibrariesOnce(null);
    mVideoView = new IjkVideoView(this);
    mRoot.addView(mVideoView, layoutParams);

    WebView.initWebView();
    web = new WebView(this, savedInstanceState, new WebView.StartupCallback() {
      @Override
      public void onSuccess() {
        Log.i(TAG, "WebView onSuccess.");
        finishInitialization(savedInstanceState);
      }

      @Override
      public void onFailure() {
        Log.i(TAG, "WebView onFailure!!!");
        initializationFailed();
      }

      @Override
      public void onCreateTab(int id) {
        Log.i(TAG, "WebView onCreateTab:" + id);
        initTab(id);
      }

      @Override
      public void onDestroyTab(int id) {
        Log.i(TAG, "WebView onDestroyTab:" + id);
      }
    });
    mRoot.addView(web, layoutParams);
    mStartupUrl = getUrlFromIntent(getIntent());
  }

  private void initTab(int id) {
    mWebContentsObserver = new WebContentsObserverAndroid(web);
    web.addJavascriptInterface(new MediaPlayerImpl(this, mVideoView), "MediaPlayerImpl");
    web.addInitJavascriptString(getInitJSString());
  }

  private void finishInitialization(Bundle savedInstanceState) {
    String shellUrl;
    if (!TextUtils.isEmpty(mStartupUrl)) {
      shellUrl = mStartupUrl;
    } else {
      shellUrl = "file:///android_asset/home.html";
    }
    web.loadUrl(shellUrl);
  }

  private String getInitJSString() {
    String script = "";
    try {
      InputStream is = getAssets().open("init.js", AssetManager.ACCESS_STREAMING);
      StringBuffer sb = new StringBuffer();
      byte[] str = new byte[256];
      int ret = is.read(str, 0, 256);
      while(ret > 0) {
        sb.append(new String(str, 0, ret, "UTF-8"));
        ret = is.read(str, 0, 256);
      }
      script = sb.toString();
    } catch (Exception e) {
      Log.e(TAG, "open helper error:" + e);
      script = "";
    }
    return script;
  }

  private void initializationFailed() {
    Log.e(TAG, "ContentView initialization failed.");
    Toast.makeText(MainActivity.this, "ContentView initialization failed.", Toast.LENGTH_SHORT).show();
    finish();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (web != null)
      web.onSaveInstanceState(outState);
  }

  @Override
  public boolean onKeyUp(int keyCode, KeyEvent event) {
    Log.e(TAG, "onKeyUp:" + keyCode);
    if (keyCode == KeyEvent.KEYCODE_BACK && web != null && web.goBack())
      return true;
    return super.onKeyUp(keyCode, event);
  }

  @Override
  protected void onNewIntent(Intent intent) {
    String url = getUrlFromIntent(intent);
    if (!TextUtils.isEmpty(url) && web != null)
      web.loadUrl(url);
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (web != null)
      web.onStart();
  }

  @Override
  protected void onStop() {
    super.onStop();

    mVideoView.stopPlayback();
    mVideoView.release(true);
  }

  @Override
  protected void onDestroy() {
    if (web != null)
      web.onDestroy();
    super.onDestroy();
  }

  private static String getUrlFromIntent(Intent intent) {
    return intent != null ? intent.getDataString() : null;
  }
}

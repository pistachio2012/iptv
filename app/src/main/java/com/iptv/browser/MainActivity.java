
package com.iptv.browser;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;
import java.nio.charset.StandardCharsets;
import java.io.InputStream;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import com.iptv.ijkplayer.IjkVideoView;
import com.iptv.plugin.Authentication;
import com.iptv.plugin.MediaPlayerImpl;
import com.iptv.plugin.Utility;

public class MainActivity extends Activity implements WebView.StartupCallback {
  private final String TAG = "MainActivity";
  private String mStartupUrl;

  private FrameLayout mRoot = null;

  private IjkVideoView mVideoView = null;
  private Authentication mAuthentication = null;
  private MediaPlayerImpl mMediaPlayerImpl = null;
  private Utility mUtility = null;

  private WebView web = null;
  private WebViewObserver mWebViewObserver = null;
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

    WebView.Init(this);
    web = new WebView(this, this);
    mRoot.addView(web, layoutParams);
    mStartupUrl = getUrlFromIntent(getIntent());

    mAuthentication = new Authentication(this);
    mUtility = new Utility(this);
    mMediaPlayerImpl = new MediaPlayerImpl(this, mVideoView, mUtility);
  }

  @Override
  public void onSuccess() {
    Log.i(TAG, "WebView onSuccess.");
    String shellUrl;
    if (!TextUtils.isEmpty(mStartupUrl)) {
      shellUrl = mStartupUrl;
    } else {
      shellUrl = "https://www.baidu.com";
    }
    web.loadUrl(shellUrl);
  }

  @Override
  public void onFailure() {
    Log.i(TAG, "WebView onFailure!!!");
    Toast.makeText(MainActivity.this, "ContentView initialization failed.", Toast.LENGTH_SHORT).show();
    finish();
  }

  @Override
  public void onCreateTab(int id) {
    Log.i(TAG, "WebView onCreateTab:" + id);
    if (mWebViewObserver == null)
      mWebViewObserver = new WebViewObserver(web);

    web.addJavascriptInterface(mAuthentication, "Authentication");
    web.addJavascriptInterface(mMediaPlayerImpl, "MediaPlayerImpl");
    web.addJavascriptInterface(mUtility, "Utility");
    web.addInitJavascriptString(getInitJSString());
  }

  @Override
  public void onDestroyTab(int id) {
    Log.i(TAG, "WebView onDestroyTab:" + id);
  }

  @Override
  public boolean onKeyUp(int keyCode, KeyEvent event) {
    Log.e(TAG, "onKeyUp:" + keyCode);
    return super.onKeyUp(keyCode, event);
  }

  public void sendKeyEvent(final int keyCode) {
    runOnUiThread(() -> {
      KeyEvent e = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
      web.dispatchKeyEvent(e);
    });
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
}

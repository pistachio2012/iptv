package com.iptv.mywebview;

import android.util.Log;
import org.chromium.content_public.browser.WebContents;
import org.chromium.content_public.browser.WebContentsObserver;
import android.view.KeyEvent;
import java.lang.ref.WeakReference;

public class WebContentsObserverAndroid extends WebContentsObserver {
  private static final String TAG = "chromium";
  private WeakReference<WebView> webview = null;
  public WebContentsObserverAndroid(WebView web) {
    super(web.getActiveWebContents());
    webview = new WeakReference<WebView>(web);
  }
}


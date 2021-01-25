package com.iptv.browser;

import android.util.Log;
import org.chromium.content_public.browser.WebContents;
import org.chromium.content_public.browser.WebContentsObserver;
import java.lang.ref.WeakReference;

public class WebViewObserver extends WebContentsObserver {
  private static final String TAG = "observer";
  private WeakReference<WebView> webview = null;
  public WebViewObserver(WebView web) {
    super(web.getActiveWebContents());
    webview = new WeakReference<WebView>(web);
  }
}


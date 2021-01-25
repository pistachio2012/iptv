package com.iptv.browser;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
      super.attachBaseContext(base);
      WebView.attachBaseContext(this);
    }
}

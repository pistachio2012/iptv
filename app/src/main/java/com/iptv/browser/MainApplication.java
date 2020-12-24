package com.iptv.browser;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class MainApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        WebView.attachBaseContext(this);
    }
}

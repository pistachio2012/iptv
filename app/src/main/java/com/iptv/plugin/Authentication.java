package com.iptv.plugin;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

import org.chromium.content.browser.JavascriptInterface;

import com.iptv.browser.MainActivity;

public class Authentication {
  private static final String TAG = "Authentication";
  private MainActivity mActivity = null;

  public Authentication(MainActivity activity) {
    mActivity = activity;
  }

  private HashMap<String, String> mConfigMap = new HashMap<String, String>();
  @JavascriptInterface
  public void CTCSetConfig(String name, String value) {
    mConfigMap.put(name, value);
  }

  @JavascriptInterface
	public String CTCGetConfig(String name) {
    if (mConfigMap.containsKey(name))
      return mConfigMap.get(name);
    return "";
  }
}

package com.iptv.plugin;

import android.util.Log;

import com.iptv.browser.MainActivity;

import org.chromium.content.browser.JavascriptInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class Utility {
  private static final String TAG = "Utility";
  private MainActivity mActivity = null;
  private HashMap<String, String> mNameValueMap = new HashMap<String, String>();

  private ArrayList<String> mEventList = new ArrayList<String>();
  private final int MAXEVENT_SIZE = 50;

  public Utility(MainActivity activity) {
    mActivity = activity;
  }

  public void setEvent(String evt) {
    if (mEventList.size() > MAXEVENT_SIZE) {
      mEventList.remove(0);
    }
    mEventList.add(evt);
    Log.i(TAG, "Size:" + mEventList.size() + " Add Event:" + evt);
  }

  @JavascriptInterface
  public String getEvent() {
    String evt = "{}";
    try {
      if (mEventList.size() > 0) {
        evt = mEventList.remove(0);
      }
      Log.i(TAG, "Size:" + mEventList.size() + " getEvent:" + evt);
    } catch (Exception ex) {
      Log.e(TAG, ex.getMessage());
    }
    return evt;
  }

  public void clearEvent() {
    try {
      mEventList.clear();
    } catch (Exception ex) {
    }
  }

  @JavascriptInterface
  public String getValueByName(String name) {
    if (this.mNameValueMap.containsKey(name)) return this.mNameValueMap.get(name);
    return "";
  }

  @JavascriptInterface
  public void setValueByName(String name, String value) {
    this.mNameValueMap.put(name, value);
  }
}

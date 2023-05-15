package com.iptv.plugin;

import android.content.Context;
import android.util.Log;
import java.lang.Runnable;
import android.os.Handler;

import org.chromium.content.browser.JavascriptInterface;

import com.iptv.browser.MainActivity;

public class MediaPlayerImpl {
  private final String TAG = "MediaPlayerImpl";
  private MainActivity mActivity = null;

  public MediaPlayerImpl(MainActivity activity) {
    mActivity = activity;
  }

  @JavascriptInterface
  public String createPlayer(int index) {
    Log.i(TAG, "createPlayer:" + index);
    return "MediaPlayerImpl_" + index;
  }

  @JavascriptInterface
  public int getNativePlayerInstanceID(String id) {
    Log.i(TAG, "getNativePlayerInstanceID:" + id);
    return 0;
  }

  @JavascriptInterface
  public void initMediaPlayer(String id,
      int nativePlayerinstanceID, int playlistFlag, int videoDisplayMode,
      int height, int width, int left, int top, int muteFlag,
      int useNativeUIFlag, int subtitleFlag, int videoAlpha,
      int cycleFlag, int randomFlag, int autoDelFlag) {
    Log.i(TAG, "initMediaPlayer:" + id);
  }

  @JavascriptInterface
  public int getVolume(String id) {
    Log.i(TAG, "getVolume:" + id);
    return 0;
  }

  @JavascriptInterface
  public int getMuteFlag(String id) {
    Log.i(TAG, "getMuteFlag:" + id);
    return 0;
  }

  @JavascriptInterface
  public int getChannelNum(String id) {
    Log.i(TAG, "getChannelNum:" + id);
    return 0;
  }

  @JavascriptInterface
  public void setVideoDisplayArea(String id, int left, int top, int width, int height) {
    Log.i(TAG, "setVideoDisplayArea:"+left+","+top+","+width+","+height);
  }

  @JavascriptInterface
	public void setVideoDisplayMode(String id, int mode) {
    Log.i(TAG, "setVideoDisplayMode:"+id);
  }

  @JavascriptInterface
	public void refreshVideoDisplay(String id) {
    Log.i(TAG, "refreshVideoDisplay:"+id);
  }

  @JavascriptInterface
  public void setSingleMedia(String id, String mediaStr) {
    Log.i(TAG, "setSingleMedia:" + id);
    Log.i(TAG, "setSingleMedia:" + mediaStr);
  }

  @JavascriptInterface
  public int releaseMediaPlayer(String id, int nativeInstanceID) {
    Log.i(TAG, "releaseMediaPlayer:" + id);
    return 0;
  }

  @JavascriptInterface
  public void playFromStart(String id) {
    Log.i(TAG, "playFromStart:" + id);
  }

  @JavascriptInterface
  public void stop(String id) {
    Log.i(TAG, "stop:" + id);
  }

  @JavascriptInterface
  public void playByTime(String id, String type, int timestamp, int speed) {
    Log.i(TAG, "playByTime:" + id);
  }

  @JavascriptInterface
  public int getMediaDuration(String id) {
    Log.i(TAG, "getMediaDuration:" + id);
    return 100;
  }

  @JavascriptInterface
  public int getCurrentPlayTime(String id) {
    //Log.i(TAG, "getCurrentPlayTime:" + id);
    return 10;
  }

  @JavascriptInterface
	public void setAllowTrickmodeFlag(String id, int bAllowTrickmodeFlag) {
    Log.i(TAG, "setAllowTrickmodeFlag:" + id);
  }

  @JavascriptInterface
	public void setNativeUIFlag(String id, int flag) {
    Log.i(TAG, "setNativeUIFlag:" + id);
  }

  @JavascriptInterface
	public void setAudioTrackUIFlag(String id, int flag) {
    Log.i(TAG, "setAudioTrackUIFlag:" + id);
  }

  @JavascriptInterface
	public void setMuteUIFlag(String id, int flag) {
    Log.i(TAG, "setMuteUIFlag:" + id);
  }

  @JavascriptInterface
	public void setAudioVolumeUIFlag(String id, int flag) {
    Log.i(TAG, "setAudioVolumeUIFlag:" + id);
  }

  public String getMediaCode() {
    String ret = "";
    return ret;
  }

  public String getEntryID() {
    String ret = "";
    return ret;
  }

  public void notifyMediaEvent(String type, int instance_id, 
      String mediaCode, String entryID) {
  }

  public void notifyMediaError(String type, int instance_id, int errorcode, 
      String errordesc, String mediaCode) {
  }

  public void notifyMediaPlayModeChange(String type, int instance_id,
      int servicetype, int new_play_mode, int new_play_rate,
      int old_play_mode, int old_play_rate) {
  }

  public void notifyMediaLiveModeChange(String type, int instance_id,
      int servicetype) {
  }
}

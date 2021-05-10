package com.iptv.plugin;

import android.content.Context;
import android.util.Log;
import java.lang.Runnable;
import android.os.Handler;

import org.chromium.content.browser.JavascriptInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import com.iptv.ijkplayer.IjkVideoView;
import com.iptv.browser.MainActivity;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class MediaPlayerImpl {
  private final String TAG = "MediaPlayerImpl";
  private MainActivity mActivity = null;
  private Handler mHandler = null;
  private IjkVideoView mVideoView = null;
  private Utility mUtility = null;

  public MediaPlayerImpl(MainActivity activity, IjkVideoView player, Utility utility) {
    mActivity = activity;
    mHandler = new Handler(mActivity.getMainLooper());
    mVideoView = player;
    mUtility = utility;

    mVideoView.setOnPreparedListener(mOnPreparedListener);
    mVideoView.setOnCompletionListener(mOnCompletionListener);
    mVideoView.setOnErrorListener(mOnErrorListener);
    mVideoView.setOnInfoListener(mOnInfoListener);
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

  private JSONObject singleMediaJson = null;
  @JavascriptInterface
  public void setSingleMedia(String id, String mediaStr) {
    Log.i(TAG, "setSingleMedia:" + id);
    Log.i(TAG, "setSingleMedia:" + mediaStr);
    singleMediaJson = null;
    String jsonString = mediaStr;
    if ((jsonString.startsWith("[")) && (jsonString.endsWith("]"))) {
      try {
        JSONArray tmpJSONArray = new JSONArray(jsonString);
        if (tmpJSONArray.length() > 0)
          this.singleMediaJson = (JSONObject) tmpJSONArray.get(0);
      } catch (Exception ex) {
        Log.e(TAG, "JSONArray:"+ex.toString());
        jsonString = mediaStr.substring(1, mediaStr.length() - 1); 
      }
    }
    if (singleMediaJson == null) {
      try {
        this.singleMediaJson = new JSONObject(jsonString);
      } catch (Exception ex) {
        Log.e(TAG, "JSONObject:"+ex.toString());
      }
    }
  }

  @JavascriptInterface
  public int releaseMediaPlayer(String id, int nativeInstanceID) {
    Log.i(TAG, "releaseMediaPlayer:" + id);
    return 0;
  }

  @JavascriptInterface
  public void playFromStart(String id) {
    Log.i(TAG, "playFromStart:" + id);
    String path = "http://cctvalih5ca.v.myalicdn.com/live/cctv1_2/index.m3u8";
    try {
      if (this.singleMediaJson.has("mediaUrl"))
        path = this.singleMediaJson.getString("mediaUrl");
      else if (this.singleMediaJson.has("mediaURL"))
        path = this.singleMediaJson.getString("mediaURL");
    } catch (Exception ex) {
      Log.e(TAG, "playFromStart get media url:"+ex.toString());
    }

    final String url = path;
    mHandler.post(() -> {
      mVideoView.setVideoPath(url);
      mVideoView.start();
    });
  }

  @JavascriptInterface
  public void stop(String id) {
    Log.i(TAG, "stop:" + id);
    //    mHandler.post(() -> {
    //      mVideoView.pause();
    //    });
  }

  @JavascriptInterface
  public void playByTime(String id, String type, int timestamp, int speed) {
    Log.i(TAG, "playByTime:" + id);
  }

  @JavascriptInterface
  public int getMediaDuration(String id) {
    //Log.i(TAG, "getMediaDuration:" + id);
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

  private IMediaPlayer.OnPreparedListener mOnPreparedListener = new IMediaPlayer.OnPreparedListener() {
    public void onPrepared(IMediaPlayer mp) {
        notifyMediaEvent("EVENT_MEDIA_BEGINING", 1,
                getMediaCode(), getEntryID());
    }
  };
  private IMediaPlayer.OnCompletionListener mOnCompletionListener = new IMediaPlayer.OnCompletionListener() {
    public void onCompletion(IMediaPlayer mp) {
    }
  };
  private IMediaPlayer.OnErrorListener mOnErrorListener = new IMediaPlayer.OnErrorListener() {
    public boolean onError(IMediaPlayer mp, int framework_err, int impl_err) {
      return false;
    }
  };
  private IMediaPlayer.OnInfoListener mOnInfoListener = new IMediaPlayer.OnInfoListener() {
    public boolean onInfo(IMediaPlayer mp, int arg1, int arg2) {
      return false;
    }
  };

  public String getMediaCode() {
    String ret = "";
    try {
      if (this.singleMediaJson.has("mediaCode"))
        ret = this.singleMediaJson.getString("mediaCode");
    } catch (Exception ex) {
      Log.e(TAG, "getMediaCode get media code:"+ex.toString());
    }
    return ret;
  }

  public String getEntryID() {
    String ret = "";
    try {
      if (this.singleMediaJson.has("entryID"))
        ret = this.singleMediaJson.getString("entryID");
    } catch (Exception ex) {
      Log.e(TAG, "getEntryID get media EntryID:"+ex.toString());
    }
    return ret;
  }

  public void notifyMediaEvent(String type, int instance_id, 
      String mediaCode, String entryID) {
    StringBuilder tmpsb = new StringBuilder();
    tmpsb.append("{\"type\":\"").append(type).append("\",\"instance_id\":")
      .append(instance_id).append(",\"media_code\":\"")
      .append(mediaCode).append("\",\"entry_id\":\"").append(entryID)
      .append("\"}");
    mUtility.setEvent(tmpsb.toString());
    mActivity.sendKeyEvent(0x0300);
  }

  public void notifyMediaError(String type, int instance_id, int errorcode, 
      String errordesc, String mediaCode) {
    StringBuilder tmpsb = new StringBuilder();
    tmpsb.append("{\"type\":\"").append(type).append("\",\"instance_id\":")
      .append(instance_id).append(",\"error_code\":")
      .append(errorcode).append(",\"error_message\":\"")
      .append(errordesc).append("\",\"media_code\":\"")
      .append(mediaCode).append("\"}");
    mUtility.setEvent(tmpsb.toString());
    mActivity.sendKeyEvent(0x0300);
  }

  public void notifyMediaPlayModeChange(String type, int instance_id,
      int servicetype, int new_play_mode, int new_play_rate,
      int old_play_mode, int old_play_rate) {
    StringBuilder tmpsb = new StringBuilder();
    tmpsb.append("{\"type\":\"").append(type).append("\",\"instance_id\":")
      .append(instance_id).append(",\"service_type\":")
      .append(servicetype).append(",\"new_play_mode\":")
      .append(new_play_mode).append(",\"new_play_rate\":")
      .append(new_play_rate).append(",\"old_play_mode\":")
      .append(old_play_mode).append(",\"old_play_rate\":")
      .append(old_play_rate).append("}");
    mUtility.setEvent(tmpsb.toString());
    mActivity.sendKeyEvent(0x0300);
  }

  public void notifyMediaLiveModeChange(String type, int instance_id,
      int servicetype) {
    StringBuilder tmpsb = new StringBuilder();

    type = "EVENT_PLTVMODE_CHANGE";
    tmpsb.append("{\"type\":\"").append(type).append("\",\"instance_id\":")
      .append(instance_id).append(",\"service_type\":")
      .append(servicetype).append("}");
    mUtility.setEvent(tmpsb.toString());
    mActivity.sendKeyEvent(0x0300);
  }
}

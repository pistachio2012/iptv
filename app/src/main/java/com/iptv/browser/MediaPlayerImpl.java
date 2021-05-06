package com.iptv.browser;

import android.content.Context;
import android.util.Log;
import android.os.Handler;
import java.lang.Runnable;

import com.iptv.ijkplayer.IjkVideoView;
import org.chromium.content.browser.JavascriptInterface;

public class MediaPlayerImpl {
  private final String TAG = "MediaPlayerImpl";
  private Handler mHandler = null;
  private IjkVideoView mVideoView = null;
  public MediaPlayerImpl(Context context, IjkVideoView player) {
    mHandler = new Handler(context.getMainLooper());
    mVideoView = player;
  }

  @JavascriptInterface
  public void setVideoDisplayArea(int left, int top, int width, int height) {
    Log.i(TAG, "setVideoDisplayArea:"+left+","+top+","+width+","+height);
  }

  @JavascriptInterface
  public void setSingleMedia(String mediaStr) {
    Log.i(TAG, "setSingleMedia:"+mediaStr);
  }

  @JavascriptInterface
  public void playFromStart() {
    Log.i(TAG, "playFromStart:");
    String path = "http://cctvalih5ca.v.myalicdn.com/live/cctv1_2/index.m3u8";
    mHandler.post(() -> {
      mVideoView.setVideoPath(path);
      mVideoView.start();
    });
  }

  @JavascriptInterface
  public void playByTime(String type, int timestamp, int speed) {
    Log.i(TAG, "playByTime:");
  }

  @JavascriptInterface
  public int getMediaDuration() {
    Log.i(TAG, "getMediaDuration:");
    return 100;
  }

  @JavascriptInterface
  public int getCurrentPlayTime() {
    Log.i(TAG, "getCurrentPlayTime:");
    return 10;
  }
}

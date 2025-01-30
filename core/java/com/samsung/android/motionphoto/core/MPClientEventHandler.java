package com.samsung.android.motionphoto.core;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/* loaded from: classes5.dex */
public class MPClientEventHandler extends Handler {
  private static final String TAG = MPClientEventHandler.class.getSimpleName();
  private OnErrorListener mOnErrorListener;
  private OnInfoListener mOnInfoListener;
  private int mToken;

  public interface OnErrorListener {
    void onError(int i, int i2, int i3, Object obj);
  }

  public interface OnInfoListener {
    void onInfo(int i, int i2, int i3, Object obj);
  }

  public MPClientEventHandler(Looper looper) {
    super(looper);
  }

  public void setOnInfoListener(OnInfoListener onInfoListener) {
    this.mOnInfoListener = onInfoListener;
  }

  public void setOnErrorListener(OnErrorListener mOnErrorListener) {
    this.mOnErrorListener = mOnErrorListener;
  }

  @Override // android.os.Handler
  public void handleMessage(Message msg) {
    String str = TAG;
    Log.m94d(
        str,
        String.format(
            "handleMessage: what=%d, arg1=%d, arg2=%d",
            Integer.valueOf(msg.what), Integer.valueOf(msg.arg1), Integer.valueOf(msg.arg2)));
    Log.m94d(str, "infolistener: " + this.mOnInfoListener);
    Log.m94d(str, "errorlistener: " + this.mOnErrorListener);
    switch (msg.what) {
      case 3001:
        OnInfoListener onInfoListener = this.mOnInfoListener;
        if (onInfoListener != null) {
          onInfoListener.onInfo(msg.arg1, msg.arg2, this.mToken, msg.obj);
          break;
        }
        break;
      case 3002:
        OnErrorListener onErrorListener = this.mOnErrorListener;
        if (onErrorListener != null) {
          onErrorListener.onError(msg.arg1, msg.arg2, this.mToken, msg.obj);
          break;
        }
        break;
      default:
        Log.m96e(str, "Unknown message type" + msg.what);
        break;
    }
  }

  public void setToken(int token) {
    this.mToken = token;
  }
}

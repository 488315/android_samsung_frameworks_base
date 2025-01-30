package com.samsung.android.motionphoto.core;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.lang.ref.WeakReference;

/* loaded from: classes5.dex */
public class MPRecorderListener {
  private static final String TAG;
  private MPClientEventHandler mEventHandler;
  private long mNativeContext;

  private native void native_finalize();

  private static native void native_init();

  private native void native_setup(Object obj);

  static {
    System.loadLibrary(Def.MP_LEGACY_NATIVE_LIB);
    native_init();
    TAG = MPRecorderListener.class.getSimpleName();
  }

  public MPRecorderListener() {
    Log.m94d(TAG, "MPRecorderListener");
    Looper looper = Looper.myLooper();
    if (looper != null) {
      this.mEventHandler = new MPClientEventHandler(looper);
    } else {
      Looper looper2 = Looper.getMainLooper();
      if (looper2 != null) {
        this.mEventHandler = new MPClientEventHandler(looper2);
      } else {
        this.mEventHandler = null;
      }
    }
    native_setup(new WeakReference(this));
  }

  public void release() {
    Log.m94d(TAG, "release");
    MPClientEventHandler mPClientEventHandler = this.mEventHandler;
    if (mPClientEventHandler != null) {
      mPClientEventHandler.removeCallbacksAndMessages(null);
      this.mEventHandler = null;
    }
    setOnInfoListener(null);
    setOnErrorListener(null);
    native_finalize();
  }

  protected void finalize() throws Throwable {
    try {
      release();
    } finally {
      super.finalize();
    }
  }

  public void setToken(int token) {
    Log.m94d(TAG, "setToken");
    MPClientEventHandler mPClientEventHandler = this.mEventHandler;
    if (mPClientEventHandler != null) {
      mPClientEventHandler.setToken(token);
    }
  }

  public void setOnErrorListener(MPClientEventHandler.OnErrorListener onErrorListener) {
    Log.m94d(TAG, "setOnErrorListener");
    MPClientEventHandler mPClientEventHandler = this.mEventHandler;
    if (mPClientEventHandler != null) {
      mPClientEventHandler.setOnErrorListener(onErrorListener);
    }
  }

  public void setOnInfoListener(MPClientEventHandler.OnInfoListener onInfoListener) {
    Log.m94d(TAG, "setOnErrorListener");
    MPClientEventHandler mPClientEventHandler = this.mEventHandler;
    if (mPClientEventHandler != null) {
      mPClientEventHandler.setOnInfoListener(onInfoListener);
    }
  }

  private static void postEventFromNative(
      Object listener_ref, int what, int arg1, int arg2, Object obj) {
    MPClientEventHandler mPClientEventHandler;
    Log.m94d(
        TAG,
        String.format(
                "postEventFromNative: %d, %d, %d",
                Integer.valueOf(what), Integer.valueOf(arg1), Integer.valueOf(arg2))
            + ", obj="
            + obj);
    MPRecorderListener l = (MPRecorderListener) ((WeakReference) listener_ref).get();
    if (l != null && (mPClientEventHandler = l.mEventHandler) != null) {
      Message m = mPClientEventHandler.obtainMessage(what, arg1, arg2, obj);
      l.mEventHandler.sendMessage(m);
    }
  }
}

package com.samsung.android.motionphoto.core;

import android.util.Log;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes5.dex */
public class MPRecorder {
  private static final String TAG;
  private MPRecorderListener mListener;
  private final ReentrantLock mLock = new ReentrantLock();
  private long mNativeContext;
  private final int mToken;

  private native void native_finalize();

  private static native void native_init();

  private native int native_setup(Object obj, String str);

  private native void native_start(String str);

  private native void native_stop();

  private native int native_store(long j);

  private native int native_store(String str);

  static {
    System.loadLibrary(Def.MP_LEGACY_NATIVE_LIB);
    native_init();
    TAG = MPRecorder.class.getSimpleName();
  }

  public MPRecorder(MPRecorderListener listener) {
    this.mListener = listener;
    String packageName = getClass().getName();
    int native_setup = native_setup(listener, packageName);
    this.mToken = native_setup;
    listener.setToken(native_setup);
  }

  public void release() {
    Log.m94d(TAG, "release");
    this.mLock.lock();
    try {
      this.mListener = null;
      if (this.mNativeContext != 0) {
        native_finalize();
      }
    } finally {
      this.mLock.unlock();
    }
  }

  public int getToken() {
    return this.mToken;
  }

  public void start(String param) {
    Log.m94d(TAG, "start: " + param);
    this.mLock.lock();
    try {
      if (this.mNativeContext != 0) {
        native_start(param);
      }
    } finally {
      this.mLock.unlock();
    }
  }

  public void stop() {
    Log.m94d(TAG, "stop");
    this.mLock.lock();
    try {
      if (this.mNativeContext != 0) {
        native_stop();
      }
    } finally {
      this.mLock.unlock();
    }
  }

  public int store(Flattenable data) {
    this.mLock.lock();
    try {
      if (this.mNativeContext != 0) {
        return native_store(data.flatten());
      }
      this.mLock.unlock();
      return -1;
    } finally {
      this.mLock.unlock();
    }
  }

  public void store(long id) {
    this.mLock.lock();
    try {
      if (this.mNativeContext != 0) {
        native_store(id);
      }
    } finally {
      this.mLock.unlock();
    }
  }

  public void store(String path) {
    this.mLock.lock();
    try {
      if (this.mNativeContext != 0) {
        native_store(path);
      }
    } finally {
      this.mLock.unlock();
    }
  }

  public void setErrorListener(MPClientEventHandler.OnErrorListener errorListener) {
    MPRecorderListener mPRecorderListener = this.mListener;
    if (mPRecorderListener != null) {
      mPRecorderListener.setOnErrorListener(errorListener);
    }
  }

  public void setInfoListener(MPClientEventHandler.OnInfoListener infoListener) {
    MPRecorderListener mPRecorderListener = this.mListener;
    if (mPRecorderListener != null) {
      mPRecorderListener.setOnInfoListener(infoListener);
    }
  }

  protected void finalize() throws Throwable {
    try {
      release();
    } finally {
      super.finalize();
    }
  }
}

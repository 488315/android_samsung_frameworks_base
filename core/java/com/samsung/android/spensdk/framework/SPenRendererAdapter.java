package com.samsung.android.spensdk.framework;

import android.graphics.Canvas;
import android.graphics.RecordingCanvas;
import android.util.Log;

/* loaded from: classes5.dex */
public class SPenRendererAdapter implements SPenRendererAdapterInterface {
  private static final String TAG = "Framework SPenRendererAdapter";
  private static final String VERSION = "1.0.2";
  private SpenDrawCallback mCallback;
  private long mNativeHwuiFunctor;
  private int mWebViewFunctor;

  private static native long Native_CreateNativeFunctor(SPenRendererAdapter sPenRendererAdapter);

  private static native int Native_CreateWebViewFunctor(SPenRendererAdapter sPenRendererAdapter);

  private static native void Native_DestroyNativeFunctor(long j);

  private static native void Native_DestroyWebViewFunctor(int i);

  private static native void Native_invokeFunctor(long j, boolean z);

  static {
    loadLibrary("HWRenderer.penlibrary.samsung");
  }

  public SPenRendererAdapter(SpenDrawCallback callback) {
    this.mCallback = null;
    this.mNativeHwuiFunctor = 0L;
    this.mWebViewFunctor = 0;
    this.mCallback = callback;
    this.mNativeHwuiFunctor = Native_CreateNativeFunctor(this);
    this.mWebViewFunctor = Native_CreateWebViewFunctor(this);
  }

  @Override // com.samsung.android.spensdk.framework.SPenRendererAdapterInterface
  public void close() {
    long j = this.mNativeHwuiFunctor;
    if (j != 0) {
      Native_DestroyNativeFunctor(j);
    }
    Native_DestroyWebViewFunctor(this.mWebViewFunctor);
  }

  private static boolean loadLibrary(String libraryName) {
    try {
      System.loadLibrary(libraryName);
      return true;
    } catch (SecurityException ex) {
      ex.printStackTrace();
      return false;
    } catch (UnsatisfiedLinkError ex2) {
      ex2.printStackTrace();
      return false;
    }
  }

  public static boolean isSupported() {
    return true;
  }

  public static String getVersion() {
    return VERSION;
  }

  @Override // com.samsung.android.spensdk.framework.SPenRendererAdapterInterface
  public boolean callOnDraw(Canvas canvas) {
    if (canvas instanceof RecordingCanvas) {
      try {
        ((RecordingCanvas) canvas).drawWebViewFunctor(this.mWebViewFunctor);
        return true;
      } catch (Exception e) {
        Log.m96e(TAG, e.toString() + "on drawWebViewFunctor");
        e.printStackTrace();
        return true;
      }
    }
    Log.m96e(TAG, "Canvas can not be cast to RecordingCanvas");
    return true;
  }

  @Override // com.samsung.android.spensdk.framework.SPenRendererAdapterInterface
  public boolean callOnProcess(boolean wait) {
    Native_invokeFunctor(this.mNativeHwuiFunctor, wait);
    return true;
  }

  private void onDraw(SpenDrawGlInfo info) {
    SpenDrawCallback spenDrawCallback = this.mCallback;
    if (spenDrawCallback != null) {
      spenDrawCallback.onDraw(info);
    }
  }

  private void onProcessWithoutScreenUpdate() {
    SpenDrawCallback spenDrawCallback = this.mCallback;
    if (spenDrawCallback != null) {
      spenDrawCallback.onProcessWithoutScreenUpdate();
    }
  }

  private void onProcessWithNoContext() {
    SpenDrawCallback spenDrawCallback = this.mCallback;
    if (spenDrawCallback != null) {
      spenDrawCallback.onProcessWithoutScreenUpdate();
    }
  }

  private void onSync() {
    SpenDrawCallback spenDrawCallback = this.mCallback;
    if (spenDrawCallback != null) {
      spenDrawCallback.onSync();
    }
  }
}

package com.android.server.knox.dar.ddar.ta;

import com.android.server.knox.dar.ddar.DDLog;

/* loaded from: classes2.dex */
class TZNative {
  public long mDDARTZNativePtr_;
  public boolean mIsLoaded;
  public String mProcessName;
  public int mRecvBufSize;
  public String mRootName;
  public int mSendBufSize;
  public int mTAId;
  public String mTATechnology;

  private native boolean nativeDDARProcessTACommand(
      TACommandRequest tACommandRequest, TACommandResponse tACommandResponse);

  public native long nativeDDARCreateTLCommunicationContext(
      int i, int i2, int i3, int i4, int i5, int i6, String str, String str2, String str3);

  public native void nativeDDARDestroyTLCommunicationContext();

  public TZNative(int i, String str, String str2, String str3, int i2, int i3) {
    DDLog.m33d("DualDAR:TZNative", "TZNative constructor: taId = " + i, new Object[0]);
    this.mTAId = i;
    this.mDDARTZNativePtr_ = 0L;
    this.mSendBufSize = i2;
    this.mRecvBufSize = i3;
    this.mTATechnology = str;
    this.mRootName = str2;
    this.mProcessName = str3;
    this.mIsLoaded = false;
  }

  public boolean loadTA(int i, long j, long j2) {
    if (this.mDDARTZNativePtr_ != 0) {
      DDLog.m34e(
          "DualDAR:TZNative",
          "TZNative::loadTA called for TA that is already loaded. Call Ignored",
          new Object[0]);
      return true;
    }
    if (j > 2147483647L || j2 > 2147483647L) {
      DDLog.m34e("DualDAR:TZNative", "loadTA: cannot get ta offset or size", new Object[0]);
      return false;
    }
    long nativeDDARCreateTLCommunicationContext =
        nativeDDARCreateTLCommunicationContext(
            i,
            (int) j,
            (int) j2,
            this.mTAId,
            this.mSendBufSize,
            this.mRecvBufSize,
            this.mTATechnology,
            this.mRootName,
            this.mProcessName);
    this.mDDARTZNativePtr_ = nativeDDARCreateTLCommunicationContext;
    if (nativeDDARCreateTLCommunicationContext == 0) {
      DDLog.m34e(
          "DualDAR:TZNative",
          "Error: nativeDDARCreateTLCommunicationContext failed",
          new Object[0]);
      return false;
    }
    synchronized (TZNative.class) {
      this.mIsLoaded = true;
    }
    DDLog.m33d(
        "DualDAR:TZNative",
        "TZNative::loadTA: mDDARTZNativePtr_ = " + this.mDDARTZNativePtr_,
        new Object[0]);
    return true;
  }

  public void unloadTA() {
    synchronized (TZNative.class) {
      if (this.mDDARTZNativePtr_ != 0 && this.mIsLoaded) {
        this.mIsLoaded = false;
        nativeDDARDestroyTLCommunicationContext();
        this.mDDARTZNativePtr_ = 0L;
        DDLog.m33d("DualDAR:TZNative", "TZNative::unloadTA called", new Object[0]);
        return;
      }
      DDLog.m34e(
          "DualDAR:TZNative",
          "TZNative::unloadTA called for TA that is not loaded. Call Ignored: ta loaded: "
              + this.mIsLoaded,
          new Object[0]);
    }
  }

  public TACommandResponse processTACommand(TACommandRequest tACommandRequest) {
    DDLog.m33d(
        "DualDAR:TZNative",
        "TZNative::processTACommand: request = "
            + tACommandRequest
            + "; mDDARTZNativePtr_ = "
            + this.mDDARTZNativePtr_,
        new Object[0]);
    TACommandResponse tACommandResponse = new TACommandResponse();
    if (nativeDDARProcessTACommand(tACommandRequest, tACommandResponse)) {
      return tACommandResponse;
    }
    DDLog.m34e(
        "DualDAR:TZNative",
        "TZNative::processTACommand: Error: nativeDDARProcessTACommand returned failure",
        new Object[0]);
    return null;
  }
}

package com.samsung.android.knox;

import java.util.List;

/* loaded from: classes5.dex */
public abstract class SemRcpCallback {

  /* renamed from: s */
  private SemIRCPCallback f3014s = new SubSemRcpCallback(this);

  public abstract void onComplete(List<String> list, int i, int i2);

  public abstract void onDone(String str, int i);

  public abstract void onFail(String str, int i, int i2);

  public abstract void onProgress(String str, int i, int i2);

  public class SubSemRcpCallback extends SemIRCPCallback.Stub {
    SemRcpCallback parent;

    public SubSemRcpCallback(SemRcpCallback SemRcpCallback) {
      this.parent = null;
      this.parent = SemRcpCallback;
    }

    @Override // com.samsung.android.knox.SemIRCPCallback
    public void onComplete(List<String> srcPathsOrig, int destinationUserId, int successCnt) {
      SemRcpCallback semRcpCallback = this.parent;
      if (semRcpCallback != null) {
        semRcpCallback.onComplete(srcPathsOrig, destinationUserId, successCnt);
      }
    }

    @Override // com.samsung.android.knox.SemIRCPCallback
    public void onDone(String srcPathsOrig, int destinationUserId) {
      SemRcpCallback semRcpCallback = this.parent;
      if (semRcpCallback != null) {
        semRcpCallback.onDone(srcPathsOrig, destinationUserId);
      }
    }

    @Override // com.samsung.android.knox.SemIRCPCallback
    public void onFail(String srcPathsOrig, int destinationUserId, int errorCode) {
      SemRcpCallback semRcpCallback = this.parent;
      if (semRcpCallback != null) {
        semRcpCallback.onFail(srcPathsOrig, destinationUserId, errorCode);
      }
    }

    @Override // com.samsung.android.knox.SemIRCPCallback
    public void onProgress(String srcPathsOrig, int destinationUserId, int progress) {
      SemRcpCallback semRcpCallback = this.parent;
      if (semRcpCallback != null) {
        semRcpCallback.onProgress(srcPathsOrig, destinationUserId, progress);
      }
    }
  }

  public SemIRCPCallback getChild() {
    return this.f3014s;
  }
}

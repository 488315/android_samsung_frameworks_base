package com.samsung.android.service.RemoteLockControl.KnoxGuard;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

/* loaded from: classes5.dex */
public final class KnoxGuardVaultException extends Exception {
  private int mKgvErrorCode;

  public KnoxGuardVaultException(int kgvErrorCode, String message) {
    super(
        NavigationBarInflaterView.SIZE_MOD_START
            + kgvErrorCode
            + NavigationBarInflaterView.SIZE_MOD_END
            + message);
    this.mKgvErrorCode = kgvErrorCode;
  }

  public int getKgvErrorCode() {
    return this.mKgvErrorCode;
  }
}

package com.android.server.biometrics.log;

import android.hardware.biometrics.common.AuthenticateReason;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.fingerprint.FingerprintAuthenticateOptions;

/* loaded from: classes.dex */
public class OperationContextExt {
  public final OperationContext mAidlContext;
  public int mDockState;
  public int mFoldState;
  public final boolean mIsBP;
  public boolean mIsDisplayOn;
  public int mOrientation;
  public BiometricContextSessionInfo mSessionInfo;

  public static int toAidlDisplayState(int i) {
    int i2 = 1;
    if (i != 1) {
      i2 = 2;
      if (i != 2) {
        i2 = 3;
        if (i != 3) {
          i2 = 4;
          if (i != 4) {
            return 0;
          }
        }
      }
    }
    return i2;
  }

  public final int getAuthReason(FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
    return 0;
  }

  public final int getWakeReason(FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
    return 0;
  }

  public OperationContextExt(boolean z) {
    this(new OperationContext(), z);
  }

  public OperationContextExt(OperationContext operationContext, boolean z) {
    this.mIsDisplayOn = false;
    this.mDockState = 0;
    this.mOrientation = 0;
    this.mFoldState = 0;
    this.mAidlContext = operationContext;
    this.mIsBP = z;
  }

  public OperationContext toAidlContext() {
    return this.mAidlContext;
  }

  public OperationContext toAidlContext(FaceAuthenticateOptions faceAuthenticateOptions) {
    this.mAidlContext.authenticateReason =
        AuthenticateReason.faceAuthenticateReason(getAuthReason(faceAuthenticateOptions));
    this.mAidlContext.wakeReason = getWakeReason(faceAuthenticateOptions);
    return this.mAidlContext;
  }

  public OperationContext toAidlContext(
      FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
    this.mAidlContext.authenticateReason =
        AuthenticateReason.fingerprintAuthenticateReason(
            getAuthReason(fingerprintAuthenticateOptions));
    this.mAidlContext.wakeReason = getWakeReason(fingerprintAuthenticateOptions);
    return this.mAidlContext;
  }

  public final int getAuthReason(FaceAuthenticateOptions faceAuthenticateOptions) {
    switch (faceAuthenticateOptions.getAuthenticateReason()) {
      case 1:
        return 1;
      case 2:
        return 2;
      case 3:
        return 3;
      case 4:
        return 4;
      case 5:
        return 5;
      case 6:
        return 6;
      case 7:
        return 7;
      case 8:
        return 8;
      case 9:
        return 9;
      case 10:
        return 10;
      default:
        return 0;
    }
  }

  public final int getWakeReason(FaceAuthenticateOptions faceAuthenticateOptions) {
    int wakeReason = faceAuthenticateOptions.getWakeReason();
    if (wakeReason == 1) {
      return 1;
    }
    if (wakeReason == 4) {
      return 2;
    }
    if (wakeReason == 10) {
      return 6;
    }
    if (wakeReason == 6) {
      return 3;
    }
    if (wakeReason == 7) {
      return 4;
    }
    switch (wakeReason) {
      case 15:
        return 7;
      case 16:
        return 8;
      case 17:
        return 9;
      default:
        return 0;
    }
  }

  public int getId() {
    return this.mAidlContext.id;
  }

  public int getOrderAndIncrement() {
    BiometricContextSessionInfo biometricContextSessionInfo = this.mSessionInfo;
    if (biometricContextSessionInfo != null) {
      return biometricContextSessionInfo.getOrderAndIncrement();
    }
    return -1;
  }

  public byte getReason() {
    return this.mAidlContext.reason;
  }

  public int getWakeReason() {
    return this.mAidlContext.wakeReason;
  }

  public boolean isDisplayOn() {
    return this.mIsDisplayOn;
  }

  public boolean isAod() {
    return this.mAidlContext.isAod;
  }

  public int getDisplayState() {
    return this.mAidlContext.displayState;
  }

  public boolean isCrypto() {
    return this.mAidlContext.isCrypto;
  }

  public int getDockState() {
    return this.mDockState;
  }

  public int getFoldState() {
    return this.mFoldState;
  }

  public int getOrientation() {
    return this.mOrientation;
  }

  public OperationContextExt update(BiometricContext biometricContext, boolean z) {
    this.mAidlContext.isAod = biometricContext.isAod();
    this.mAidlContext.displayState = toAidlDisplayState(biometricContext.getDisplayState());
    this.mAidlContext.isCrypto = z;
    setFirstSessionId(biometricContext);
    this.mIsDisplayOn = biometricContext.isDisplayOn();
    this.mDockState = biometricContext.getDockedState();
    this.mFoldState = biometricContext.getFoldState();
    this.mOrientation = biometricContext.getCurrentRotation();
    return this;
  }

  public final void setFirstSessionId(BiometricContext biometricContext) {
    if (this.mIsBP) {
      BiometricContextSessionInfo biometricPromptSessionInfo =
          biometricContext.getBiometricPromptSessionInfo();
      this.mSessionInfo = biometricPromptSessionInfo;
      if (biometricPromptSessionInfo != null) {
        this.mAidlContext.id = biometricPromptSessionInfo.getId();
        this.mAidlContext.reason = (byte) 1;
        return;
      }
    } else {
      BiometricContextSessionInfo keyguardEntrySessionInfo =
          biometricContext.getKeyguardEntrySessionInfo();
      this.mSessionInfo = keyguardEntrySessionInfo;
      if (keyguardEntrySessionInfo != null) {
        this.mAidlContext.id = keyguardEntrySessionInfo.getId();
        this.mAidlContext.reason = (byte) 2;
        return;
      }
    }
    OperationContext operationContext = this.mAidlContext;
    operationContext.id = 0;
    operationContext.reason = (byte) 0;
  }
}

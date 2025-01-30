package com.android.server.biometrics;

import android.hardware.biometrics.IBiometricSysuiReceiver;
import android.os.IBinder;
import android.util.Slog;

/* loaded from: classes.dex */
public class SemBiometricSysUiReceiver implements IBiometricSysuiReceiver {
  public final SemBiometricSysUiManager.SysUiListener mSysUiListener =
      new SemBiometricSysUiManager
          .SysUiListener() { // from class:
                             // com.android.server.biometrics.SemBiometricSysUiReceiver.1
        @Override // com.android.server.biometrics.SemBiometricSysUiManager.SysUiListener
        public void onError(int i, int i2) {
          SemBiometricSysUiReceiver.this.onSysUiError(i, i2);
        }

        @Override // com.android.server.biometrics.SemBiometricSysUiManager.SysUiListener
        public void onTryAgainPressed(int i) {
          if (i == 0) {
            SemBiometricSysUiReceiver.this.onTryAgainPressed();
          } else {
            SemBiometricSysUiReceiver.this.onModalitySwitched(i);
          }
        }

        @Override // com.android.server.biometrics.SemBiometricSysUiManager.SysUiListener
        public void onDismissed(int i, byte[] bArr) {
          SemBiometricSysUiReceiver.this.onDialogDismissed(i, bArr);
        }

        @Override // com.android.server.biometrics.SemBiometricSysUiManager.SysUiListener
        public void onEvent(int i, int i2) {
          switch (i) {
            case 1002:
              SemBiometricSysUiReceiver.this.onDeviceCredentialPressed();
              break;
            case 1003:
              SemBiometricSysUiReceiver.this.onSystemEvent(i2);
              break;
            case 1004:
              SemBiometricSysUiReceiver.this.onDialogAnimatedIn(true);
              break;
          }
        }
      };

  public IBinder asBinder() {
    return null;
  }

  public SemBiometricSysUiManager.SysUiListener getSysUiListener() {
    return this.mSysUiListener;
  }

  public void onDialogDismissed(int i, byte[] bArr) {
    Slog.d("BiometricService", "onTryAgainPressed: No implementation");
  }

  public void onTryAgainPressed() {
    Slog.d("BiometricService", "onTryAgainPressed: No implementation");
  }

  public void onDeviceCredentialPressed() {
    Slog.d("BiometricService", "onDeviceCredentialPressed: No implementation");
  }

  public void onSystemEvent(int i) {
    Slog.d("BiometricService", "onSystemEvent: No implementation");
  }

  public void onDialogAnimatedIn(boolean z) {
    Slog.d("BiometricService", "onDialogAnimatedIn: No implementation");
  }

  public void onStartFingerprintNow() {
    Slog.d("BiometricService", "onStartFingerprintNow: No implementation");
  }

  public void onModalitySwitched(int i) {
    Slog.d("BiometricService", "onModalitySwitched: No implementation");
  }

  public void onSysUiError(int i, int i2) {
    Slog.d("BiometricService", "onSysUiError: No implementation");
  }
}

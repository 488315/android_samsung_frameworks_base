package com.android.server.biometrics.sensors;

import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.face.Face;
import android.hardware.face.FaceAuthenticationFrame;
import android.hardware.face.FaceEnrollFrame;
import android.hardware.face.IFaceServiceReceiver;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.Bundle;
import android.text.TextUtils;
import com.android.server.biometrics.sensors.face.SemFaceUtils;
import com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback;

/* loaded from: classes.dex */
public class ClientMonitorCallbackConverter {
  public IFaceServiceReceiver mFaceServiceReceiver;
  public ISemFingerprintRequestCallback mFingerprintRequestReceiver;
  public IFingerprintServiceReceiver mFingerprintServiceReceiver;
  public IBiometricSensorReceiver mSensorReceiver;

  public ClientMonitorCallbackConverter(IBiometricSensorReceiver iBiometricSensorReceiver) {
    this.mSensorReceiver = iBiometricSensorReceiver;
  }

  public ClientMonitorCallbackConverter(IFaceServiceReceiver iFaceServiceReceiver) {
    this.mFaceServiceReceiver = iFaceServiceReceiver;
  }

  public ClientMonitorCallbackConverter(IFingerprintServiceReceiver iFingerprintServiceReceiver) {
    this.mFingerprintServiceReceiver = iFingerprintServiceReceiver;
  }

  public ClientMonitorCallbackConverter(
      ISemFingerprintRequestCallback iSemFingerprintRequestCallback) {
    this.mFingerprintRequestReceiver = iSemFingerprintRequestCallback;
  }

  public void onAcquired(int i, int i2, int i3) {
    IBiometricSensorReceiver iBiometricSensorReceiver = this.mSensorReceiver;
    if (iBiometricSensorReceiver != null) {
      iBiometricSensorReceiver.onAcquired(i, i2, i3);
      return;
    }
    IFaceServiceReceiver iFaceServiceReceiver = this.mFaceServiceReceiver;
    if (iFaceServiceReceiver != null) {
      iFaceServiceReceiver.onAcquired(i2, i3);
      return;
    }
    IFingerprintServiceReceiver iFingerprintServiceReceiver = this.mFingerprintServiceReceiver;
    if (iFingerprintServiceReceiver != null) {
      iFingerprintServiceReceiver.onAcquired(i2, i3);
    }
  }

  public void onAuthenticationSucceeded(
      int i, BiometricAuthenticator.Identifier identifier, byte[] bArr, int i2, boolean z) {
    if (this.mSensorReceiver != null) {
      onSemAuthenticationSucceeded(i, identifier, bArr, i2, z, null);
      return;
    }
    if (this.mFaceServiceReceiver != null) {
      byte[] fidoResultData = SemFaceUtils.getFidoResultData();
      if (fidoResultData == null) {
        this.mFaceServiceReceiver.onAuthenticationSucceeded((Face) identifier, i2, z);
        return;
      } else {
        this.mFaceServiceReceiver.onSemAuthenticationSucceeded(
            (Face) identifier, i2, z, fidoResultData);
        return;
      }
    }
    IFingerprintServiceReceiver iFingerprintServiceReceiver = this.mFingerprintServiceReceiver;
    if (iFingerprintServiceReceiver != null) {
      iFingerprintServiceReceiver.onAuthenticationSucceeded((Fingerprint) identifier, i2, z);
    }
  }

  public void onAuthenticationFailed(int i) {
    IBiometricSensorReceiver iBiometricSensorReceiver = this.mSensorReceiver;
    if (iBiometricSensorReceiver != null) {
      iBiometricSensorReceiver.onAuthenticationFailed(i);
      return;
    }
    IFaceServiceReceiver iFaceServiceReceiver = this.mFaceServiceReceiver;
    if (iFaceServiceReceiver != null) {
      iFaceServiceReceiver.onAuthenticationFailed();
      return;
    }
    IFingerprintServiceReceiver iFingerprintServiceReceiver = this.mFingerprintServiceReceiver;
    if (iFingerprintServiceReceiver != null) {
      iFingerprintServiceReceiver.onAuthenticationFailed();
    }
  }

  public void onError(int i, int i2, int i3, int i4) {
    IBiometricSensorReceiver iBiometricSensorReceiver = this.mSensorReceiver;
    if (iBiometricSensorReceiver != null) {
      iBiometricSensorReceiver.onError(i, i2, i3, i4);
      return;
    }
    IFaceServiceReceiver iFaceServiceReceiver = this.mFaceServiceReceiver;
    if (iFaceServiceReceiver != null) {
      iFaceServiceReceiver.onError(i3, i4);
      return;
    }
    IFingerprintServiceReceiver iFingerprintServiceReceiver = this.mFingerprintServiceReceiver;
    if (iFingerprintServiceReceiver != null) {
      iFingerprintServiceReceiver.onError(i3, i4);
    }
  }

  public void onDetected(int i, int i2, boolean z) {
    IFaceServiceReceiver iFaceServiceReceiver = this.mFaceServiceReceiver;
    if (iFaceServiceReceiver != null) {
      iFaceServiceReceiver.onFaceDetected(i, i2, z);
      return;
    }
    IFingerprintServiceReceiver iFingerprintServiceReceiver = this.mFingerprintServiceReceiver;
    if (iFingerprintServiceReceiver != null) {
      iFingerprintServiceReceiver.onFingerprintDetected(i, i2, z);
    }
  }

  public void onEnrollResult(BiometricAuthenticator.Identifier identifier, int i) {
    IFaceServiceReceiver iFaceServiceReceiver = this.mFaceServiceReceiver;
    if (iFaceServiceReceiver != null) {
      iFaceServiceReceiver.onEnrollResult((Face) identifier, i);
      return;
    }
    IFingerprintServiceReceiver iFingerprintServiceReceiver = this.mFingerprintServiceReceiver;
    if (iFingerprintServiceReceiver != null) {
      iFingerprintServiceReceiver.onEnrollResult((Fingerprint) identifier, i);
    }
  }

  public void onRemoved(BiometricAuthenticator.Identifier identifier, int i) {
    IFaceServiceReceiver iFaceServiceReceiver = this.mFaceServiceReceiver;
    if (iFaceServiceReceiver != null) {
      iFaceServiceReceiver.onRemoved((Face) identifier, i);
      return;
    }
    IFingerprintServiceReceiver iFingerprintServiceReceiver = this.mFingerprintServiceReceiver;
    if (iFingerprintServiceReceiver != null) {
      iFingerprintServiceReceiver.onRemoved((Fingerprint) identifier, i);
    }
  }

  public void onChallengeGenerated(int i, int i2, long j) {
    IFaceServiceReceiver iFaceServiceReceiver = this.mFaceServiceReceiver;
    if (iFaceServiceReceiver != null) {
      iFaceServiceReceiver.onChallengeGenerated(i, i2, j);
      return;
    }
    IFingerprintServiceReceiver iFingerprintServiceReceiver = this.mFingerprintServiceReceiver;
    if (iFingerprintServiceReceiver != null) {
      iFingerprintServiceReceiver.onChallengeGenerated(i, i2, j);
    }
  }

  public void onFeatureSet(boolean z, int i) {
    IFaceServiceReceiver iFaceServiceReceiver = this.mFaceServiceReceiver;
    if (iFaceServiceReceiver != null) {
      iFaceServiceReceiver.onFeatureSet(z, i);
    }
  }

  public void onFeatureGet(boolean z, int[] iArr, boolean[] zArr) {
    IFaceServiceReceiver iFaceServiceReceiver = this.mFaceServiceReceiver;
    if (iFaceServiceReceiver != null) {
      iFaceServiceReceiver.onFeatureGet(z, iArr, zArr);
    }
  }

  public void onUdfpsPointerDown(int i) {
    IFingerprintServiceReceiver iFingerprintServiceReceiver = this.mFingerprintServiceReceiver;
    if (iFingerprintServiceReceiver != null) {
      iFingerprintServiceReceiver.onUdfpsPointerDown(i);
    }
  }

  public void onUdfpsPointerUp(int i) {
    IFingerprintServiceReceiver iFingerprintServiceReceiver = this.mFingerprintServiceReceiver;
    if (iFingerprintServiceReceiver != null) {
      iFingerprintServiceReceiver.onUdfpsPointerUp(i);
    }
  }

  public void onAuthenticationFrame(FaceAuthenticationFrame faceAuthenticationFrame) {
    IFaceServiceReceiver iFaceServiceReceiver = this.mFaceServiceReceiver;
    if (iFaceServiceReceiver != null) {
      iFaceServiceReceiver.onAuthenticationFrame(faceAuthenticationFrame);
    }
  }

  public void onEnrollmentFrame(FaceEnrollFrame faceEnrollFrame) {
    IFaceServiceReceiver iFaceServiceReceiver = this.mFaceServiceReceiver;
    if (iFaceServiceReceiver != null) {
      iFaceServiceReceiver.onEnrollmentFrame(faceEnrollFrame);
    }
  }

  public void onSemTrustAppUpdateEvent(int i) {
    ISemFingerprintRequestCallback iSemFingerprintRequestCallback =
        this.mFingerprintRequestReceiver;
    if (iSemFingerprintRequestCallback != null) {
      iSemFingerprintRequestCallback.onResult(i);
      return;
    }
    IFaceServiceReceiver iFaceServiceReceiver = this.mFaceServiceReceiver;
    if (iFaceServiceReceiver != null) {
      iFaceServiceReceiver.onSemStatusUpdate(i, "");
    }
  }

  public void onImageProcessed(byte[] bArr, int i, int i2, int i3, int i4, Bundle bundle) {
    IFaceServiceReceiver iFaceServiceReceiver = this.mFaceServiceReceiver;
    if (iFaceServiceReceiver != null) {
      iFaceServiceReceiver.onSemImageProcessed(bArr, i, i2, i3, i4, bundle);
    }
  }

  public void onSemAuthenticationSucceededWithBundle(int i, Bundle bundle) {
    IFaceServiceReceiver iFaceServiceReceiver = this.mFaceServiceReceiver;
    if (iFaceServiceReceiver != null) {
      iFaceServiceReceiver.onSemAuthenticationSucceededWithBundle(
          new Face("", 1, 0L), i, false, bundle);
    }
  }

  public void onSemAuthenticationFailed() {
    IFaceServiceReceiver iFaceServiceReceiver = this.mFaceServiceReceiver;
    if (iFaceServiceReceiver != null) {
      iFaceServiceReceiver.onAuthenticationFailed();
    }
  }

  public void onSemAuthenticationSucceeded(
      int i,
      BiometricAuthenticator.Identifier identifier,
      byte[] bArr,
      int i2,
      boolean z,
      byte[] bArr2) {
    if (this.mSensorReceiver != null) {
      Bundle bundle = new Bundle();
      if (identifier != null) {
        if (!TextUtils.isEmpty(identifier.getName())) {
          bundle.putString("KEY_IDENTIFIER_NAME", identifier.getName().toString());
        }
        bundle.putInt("KEY_BIOMETRICS_ID", identifier.getBiometricId());
      }
      if (bArr2 != null) {
        bundle.putByteArray("KEY_CHALLENGE_TOKEN", bArr2);
      }
      this.mSensorReceiver.onSemAuthenticationSucceeded(i, bArr, bundle);
    }
  }

  public void onRequestResult(int i) {
    ISemFingerprintRequestCallback iSemFingerprintRequestCallback =
        this.mFingerprintRequestReceiver;
    if (iSemFingerprintRequestCallback != null) {
      iSemFingerprintRequestCallback.onResult(i);
    }
  }
}

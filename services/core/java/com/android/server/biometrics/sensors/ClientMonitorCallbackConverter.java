package com.android.server.biometrics.sensors;

import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.face.Face;
import android.hardware.face.IFaceServiceReceiver;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.server.biometrics.sensors.face.SemFaceUtils;

import com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ClientMonitorCallbackConverter {
    public final IFaceServiceReceiver mFaceServiceReceiver;
    public final ISemFingerprintRequestCallback mFingerprintRequestReceiver;
    public final IFingerprintServiceReceiver mFingerprintServiceReceiver;
    public final IBiometricSensorReceiver mSensorReceiver;

    public ClientMonitorCallbackConverter(IBiometricSensorReceiver iBiometricSensorReceiver) {
        this.mSensorReceiver = iBiometricSensorReceiver;
        this.mFaceServiceReceiver = null;
        this.mFingerprintServiceReceiver = null;
    }

    public ClientMonitorCallbackConverter(IFaceServiceReceiver iFaceServiceReceiver) {
        this.mSensorReceiver = null;
        this.mFaceServiceReceiver = iFaceServiceReceiver;
        this.mFingerprintServiceReceiver = null;
    }

    public ClientMonitorCallbackConverter(IFingerprintServiceReceiver iFingerprintServiceReceiver) {
        this.mSensorReceiver = null;
        this.mFaceServiceReceiver = null;
        this.mFingerprintServiceReceiver = iFingerprintServiceReceiver;
    }

    public ClientMonitorCallbackConverter(
            ISemFingerprintRequestCallback iSemFingerprintRequestCallback) {
        this.mFingerprintRequestReceiver = iSemFingerprintRequestCallback;
        this.mSensorReceiver = null;
        this.mFaceServiceReceiver = null;
        this.mFingerprintServiceReceiver = null;
    }

    public final void onAuthenticationSucceeded(
            int i, BiometricAuthenticator.Identifier identifier, byte[] bArr, int i2, boolean z) {
        if (this.mSensorReceiver != null) {
            Bundle bundle = new Bundle();
            if (identifier != null) {
                if (!TextUtils.isEmpty(identifier.getName())) {
                    bundle.putString("KEY_IDENTIFIER_NAME", identifier.getName().toString());
                }
                bundle.putInt("KEY_BIOMETRICS_ID", identifier.getBiometricId());
            }
            this.mSensorReceiver.onSemAuthenticationSucceeded(i, bArr, bundle);
            return;
        }
        if (this.mFaceServiceReceiver == null) {
            IFingerprintServiceReceiver iFingerprintServiceReceiver =
                    this.mFingerprintServiceReceiver;
            if (iFingerprintServiceReceiver != null) {
                iFingerprintServiceReceiver.onAuthenticationSucceeded(
                        (Fingerprint) identifier, i2, z);
                return;
            }
            return;
        }
        byte[] bArr2 = SemFaceUtils.mFidoResultData;
        byte[] bArr3 = bArr2 != null ? (byte[]) bArr2.clone() : null;
        SemFaceUtils.mFidoResultData = null;
        if (bArr3 == null) {
            this.mFaceServiceReceiver.onAuthenticationSucceeded((Face) identifier, i2, z);
        } else {
            this.mFaceServiceReceiver.onSemAuthenticationSucceeded((Face) identifier, i2, z, bArr3);
        }
    }

    public final void onError(int i, int i2, int i3, int i4) {
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
}

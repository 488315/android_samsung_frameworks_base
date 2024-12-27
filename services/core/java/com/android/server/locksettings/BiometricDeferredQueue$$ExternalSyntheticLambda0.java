package com.android.server.locksettings;

import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BiometricDeferredQueue$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ BiometricDeferredQueue f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ byte[] f$2;

    public /* synthetic */ BiometricDeferredQueue$$ExternalSyntheticLambda0(
            BiometricDeferredQueue biometricDeferredQueue, int i, byte[] bArr) {
        this.f$0 = biometricDeferredQueue;
        this.f$1 = i;
        this.f$2 = bArr;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BiometricDeferredQueue biometricDeferredQueue = this.f$0;
        int i = this.f$1;
        byte[] bArr = this.f$2;
        FaceManager faceManager = biometricDeferredQueue.mFaceManager;
        if (faceManager != null && faceManager.hasEnrolledTemplates(i)) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "Face addPendingLockoutResetForUser: ", "BiometricDeferredQueue");
            biometricDeferredQueue.mPendingResetLockoutsForFace.add(
                    new BiometricDeferredQueue.UserAuthInfo(i, bArr));
        }
        FingerprintManager fingerprintManager = biometricDeferredQueue.mFingerprintManager;
        if (fingerprintManager != null && fingerprintManager.hasEnrolledFingerprints(i)) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "Fingerprint addPendingLockoutResetForUser: ", "BiometricDeferredQueue");
            biometricDeferredQueue.mPendingResetLockoutsForFingerprint.add(
                    new BiometricDeferredQueue.UserAuthInfo(i, bArr));
        }
        if (biometricDeferredQueue.mBiometricManager != null) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "Fingerprint addPendingLockoutResetForUser: ", "BiometricDeferredQueue");
            biometricDeferredQueue.mPendingResetLockouts.add(
                    new BiometricDeferredQueue.UserAuthInfo(i, bArr));
        }
    }
}

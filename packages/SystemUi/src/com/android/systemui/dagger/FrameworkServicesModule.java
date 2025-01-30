package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.biometrics.BiometricManager;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FrameworkServicesModule {
    public static FaceManager provideFaceManager(Context context) {
        if (context.getPackageManager().hasSystemFeature("android.hardware.biometrics.face")) {
            return (FaceManager) context.getSystemService(FaceManager.class);
        }
        return null;
    }

    public static BiometricManager providesBiometricManager(Context context, FaceManager faceManager, FingerprintManager fingerprintManager) {
        if (faceManager == null && fingerprintManager == null) {
            return null;
        }
        return (BiometricManager) context.getSystemService(BiometricManager.class);
    }

    public static FingerprintManager providesFingerprintManager(Context context) {
        if (context.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
            return (FingerprintManager) context.getSystemService(FingerprintManager.class);
        }
        return null;
    }
}

package com.android.server.biometrics;

import android.hardware.SensorPrivacyManager;
import android.hardware.camera2.CameraManager;
import android.os.Handler;

import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BiometricCameraManagerImpl {
    public final AnonymousClass1 mCameraAvailabilityCallback;
    public final ConcurrentHashMap mIsCameraAvailable = new ConcurrentHashMap();
    public final SensorPrivacyManager mSensorPrivacyManager;

    public BiometricCameraManagerImpl(
            CameraManager cameraManager, SensorPrivacyManager sensorPrivacyManager) {
        CameraManager.AvailabilityCallback availabilityCallback =
                new CameraManager
                        .AvailabilityCallback() { // from class:
                                                  // com.android.server.biometrics.BiometricCameraManagerImpl.1
                    @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
                    public final void onCameraAvailable(String str) {
                        BiometricCameraManagerImpl.this.mIsCameraAvailable.put(str, Boolean.TRUE);
                    }

                    @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
                    public final void onCameraUnavailable(String str) {
                        BiometricCameraManagerImpl.this.mIsCameraAvailable.put(str, Boolean.FALSE);
                    }
                };
        this.mSensorPrivacyManager = sensorPrivacyManager;
        cameraManager.registerAvailabilityCallback(availabilityCallback, (Handler) null);
    }
}

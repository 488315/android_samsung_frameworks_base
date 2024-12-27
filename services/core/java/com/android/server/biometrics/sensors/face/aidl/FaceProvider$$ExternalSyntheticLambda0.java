package com.android.server.biometrics.sensors.face.aidl;

import android.app.ActivityManager;
import android.os.Binder;
import android.util.Slog;

import com.android.server.biometrics.SemBioAnalyticsManager;

public final /* synthetic */ class FaceProvider$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FaceProvider f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ FaceProvider$$ExternalSyntheticLambda0(
            FaceProvider faceProvider, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = faceProvider;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FaceProvider faceProvider = this.f$0;
                int i = this.f$1;
                SemFaceServiceExImpl serviceExtImpl = faceProvider.getServiceExtImpl();
                serviceExtImpl.getClass();
                if (i == 600) {
                    serviceExtImpl.mSemAnalyticsManager =
                            (SemBioAnalyticsManager) SemBioAnalyticsManager.sInstance.get();
                    break;
                }
                break;
            default:
                FaceProvider faceProvider2 = this.f$0;
                int i2 = this.f$1;
                Slog.d(faceProvider2.getTag(), "internalCleanupAndGetFeatureRunnable: ");
                if (faceProvider2.mFaceSensors.mSensors.get(i2) != null) {
                    faceProvider2.scheduleInternalCleanup(
                            i2, ActivityManager.getCurrentUser(), null);
                    Binder binder = new Binder();
                    int currentUser = ActivityManager.getCurrentUser();
                    faceProvider2.mContext.getOpPackageName();
                    faceProvider2.mHandler.post(
                            new FaceProvider$$ExternalSyntheticLambda9(
                                    faceProvider2, i2, currentUser, binder, null, 1));
                    break;
                } else {
                    Slog.d(faceProvider2.getTag(), "sensor is not added yet");
                    break;
                }
        }
    }
}

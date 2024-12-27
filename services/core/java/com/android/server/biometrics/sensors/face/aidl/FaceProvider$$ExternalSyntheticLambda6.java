package com.android.server.biometrics.sensors.face.aidl;

import android.hardware.biometrics.IInvalidationCallback;

import com.android.server.biometrics.Utils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class FaceProvider$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FaceProvider f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ FaceProvider$$ExternalSyntheticLambda6(
            FaceProvider faceProvider, int i, int i2, Object obj, int i3) {
        this.$r8$classId = i3;
        this.f$0 = faceProvider;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FaceProvider faceProvider = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                IInvalidationCallback iInvalidationCallback = (IInvalidationCallback) this.f$3;
                faceProvider.getClass();
                faceProvider.scheduleForSensor(
                        i,
                        new FaceInvalidationClient(
                                faceProvider.mContext,
                                ((Sensor) faceProvider.mFaceSensors.mSensors.get(i)).mLazySession,
                                i2,
                                i,
                                faceProvider.createLogger(
                                        0, 0, faceProvider.mAuthenticationStatsCollector),
                                faceProvider.mBiometricContext,
                                ((Sensor) faceProvider.mFaceSensors.mSensors.get(i))
                                        .mAuthenticatorIds,
                                iInvalidationCallback));
                break;
            default:
                FaceProvider faceProvider2 = this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                byte[] bArr = (byte[]) this.f$3;
                ((Sensor) faceProvider2.mFaceSensors.mSensors.get(i3))
                        .scheduleFaceUpdateActiveUserClient(i4);
                faceProvider2.scheduleForSensor(
                        i3,
                        new FaceResetLockoutClient(
                                faceProvider2.mContext,
                                ((Sensor) faceProvider2.mFaceSensors.mSensors.get(i3)).mLazySession,
                                i4,
                                faceProvider2.mContext.getOpPackageName(),
                                i3,
                                faceProvider2.createLogger(
                                        0, 0, faceProvider2.mAuthenticationStatsCollector),
                                faceProvider2.mBiometricContext,
                                bArr,
                                ((Sensor) faceProvider2.mFaceSensors.mSensors.get(i3))
                                        .getLockoutTracker(false),
                                faceProvider2.mLockoutResetDispatcher,
                                Utils.getCurrentStrength(i3)));
                break;
        }
    }
}

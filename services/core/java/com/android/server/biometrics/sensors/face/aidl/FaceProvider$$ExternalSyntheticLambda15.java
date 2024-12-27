package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;

import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.InvalidationRequesterClient;
import com.android.server.biometrics.sensors.face.FaceUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class FaceProvider$$ExternalSyntheticLambda15 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FaceProvider f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ FaceProvider$$ExternalSyntheticLambda15(
            FaceProvider faceProvider, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = faceProvider;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FaceProvider faceProvider = this.f$0;
                int i = this.f$1;
                ((Sensor) faceProvider.mFaceSensors.mSensors.get(i))
                        .mScheduler.startPreparedClient(this.f$2);
                break;
            case 1:
                FaceProvider faceProvider2 = this.f$0;
                int i2 = this.f$1;
                int i3 = this.f$2;
                faceProvider2.getClass();
                faceProvider2.scheduleForSensor(
                        i2,
                        new FaceGetAuthenticatorIdClient(
                                faceProvider2.mContext,
                                ((Sensor) faceProvider2.mFaceSensors.mSensors.get(i2)).mLazySession,
                                i3,
                                faceProvider2.mContext.getOpPackageName(),
                                i2,
                                faceProvider2.createLogger(
                                        0, 0, faceProvider2.mAuthenticationStatsCollector),
                                faceProvider2.mBiometricContext,
                                ((Sensor) faceProvider2.mFaceSensors.mSensors.get(i2))
                                        .mAuthenticatorIds));
                break;
            default:
                FaceProvider faceProvider3 = this.f$0;
                int i4 = this.f$1;
                int i5 = this.f$2;
                faceProvider3.getClass();
                Context context = faceProvider3.mContext;
                faceProvider3.scheduleForSensor(
                        i5,
                        new InvalidationRequesterClient(
                                context,
                                i4,
                                i5,
                                BiometricLogger.ofUnknown(context),
                                faceProvider3.mBiometricContext,
                                FaceUtils.getInstance(i5, null)));
                break;
        }
    }
}

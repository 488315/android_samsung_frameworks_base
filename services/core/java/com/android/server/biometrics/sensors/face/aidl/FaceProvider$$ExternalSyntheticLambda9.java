package com.android.server.biometrics.sensors.face.aidl;

import android.os.IBinder;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.face.FaceUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class FaceProvider$$ExternalSyntheticLambda9 implements Runnable {
    public final /* synthetic */ FaceProvider f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ IBinder f$3;
    public final /* synthetic */ ClientMonitorCallbackConverter f$4;
    public final /* synthetic */ int f$5;

    public /* synthetic */ FaceProvider$$ExternalSyntheticLambda9(
            FaceProvider faceProvider,
            int i,
            int i2,
            IBinder iBinder,
            ClientMonitorCallbackConverter clientMonitorCallbackConverter,
            int i3) {
        this.f$0 = faceProvider;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = iBinder;
        this.f$4 = clientMonitorCallbackConverter;
        this.f$5 = i3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FaceProvider faceProvider = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        IBinder iBinder = this.f$3;
        ClientMonitorCallbackConverter clientMonitorCallbackConverter = this.f$4;
        int i3 = this.f$5;
        ((Sensor) faceProvider.mFaceSensors.mSensors.get(i)).scheduleFaceUpdateActiveUserClient(i2);
        if (FaceUtils.getInstance(i, null)
                .getBiometricsForUser(faceProvider.mContext, i2)
                .isEmpty()) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i2,
                    "Ignoring getFeature, no templates enrolled for user: ",
                    faceProvider.getTag());
        } else {
            faceProvider.scheduleForSensor(
                    i,
                    new FaceGetFeatureClient(
                            faceProvider.mContext,
                            ((Sensor) faceProvider.mFaceSensors.mSensors.get(i)).mLazySession,
                            iBinder,
                            clientMonitorCallbackConverter,
                            i2,
                            faceProvider.mContext.getOpPackageName(),
                            i,
                            BiometricLogger.ofUnknown(faceProvider.mContext),
                            faceProvider.mBiometricContext,
                            i3));
        }
    }
}

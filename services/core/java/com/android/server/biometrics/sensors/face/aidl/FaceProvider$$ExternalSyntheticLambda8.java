package com.android.server.biometrics.sensors.face.aidl;

import android.hardware.face.IFaceServiceReceiver;
import android.os.IBinder;

import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.face.FaceUtils;

public final /* synthetic */ class FaceProvider$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ FaceProvider f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ IBinder f$3;
    public final /* synthetic */ IFaceServiceReceiver f$4;
    public final /* synthetic */ int[] f$5;
    public final /* synthetic */ String f$6;

    public /* synthetic */ FaceProvider$$ExternalSyntheticLambda8(
            FaceProvider faceProvider,
            int i,
            int i2,
            IBinder iBinder,
            IFaceServiceReceiver iFaceServiceReceiver,
            int[] iArr,
            String str) {
        this.f$0 = faceProvider;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = iBinder;
        this.f$4 = iFaceServiceReceiver;
        this.f$5 = iArr;
        this.f$6 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FaceProvider faceProvider = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        IBinder iBinder = this.f$3;
        IFaceServiceReceiver iFaceServiceReceiver = this.f$4;
        int[] iArr = this.f$5;
        String str = this.f$6;
        ((Sensor) faceProvider.mFaceSensors.mSensors.get(i)).scheduleFaceUpdateActiveUserClient(i2);
        faceProvider.scheduleForSensor(
                i,
                new FaceRemovalClient(
                        faceProvider.mContext,
                        ((Sensor) faceProvider.mFaceSensors.mSensors.get(i)).mLazySession,
                        iBinder,
                        new ClientMonitorCallbackConverter(iFaceServiceReceiver),
                        iArr,
                        i2,
                        str,
                        FaceUtils.getInstance(i, null),
                        i,
                        faceProvider.createLogger(4, 0, faceProvider.mAuthenticationStatsCollector),
                        faceProvider.mBiometricContext,
                        ((Sensor) faceProvider.mFaceSensors.mSensors.get(i)).mAuthenticatorIds),
                faceProvider.mBiometricStateCallback);
    }
}

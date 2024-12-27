package com.android.server.biometrics.sensors.face.aidl;

import android.hardware.face.FaceEnrollOptions;
import android.hardware.face.IFaceServiceReceiver;
import android.os.IBinder;
import android.view.Surface;

import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.face.FaceUtils;

public final /* synthetic */ class FaceProvider$$ExternalSyntheticLambda12 implements Runnable {
    public final /* synthetic */ FaceProvider f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ boolean f$10;
    public final /* synthetic */ FaceEnrollOptions f$11;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ IBinder f$3;
    public final /* synthetic */ IFaceServiceReceiver f$4;
    public final /* synthetic */ byte[] f$5;
    public final /* synthetic */ String f$6;
    public final /* synthetic */ long f$7;
    public final /* synthetic */ int[] f$8;
    public final /* synthetic */ Surface f$9;

    public /* synthetic */ FaceProvider$$ExternalSyntheticLambda12(
            FaceProvider faceProvider,
            int i,
            int i2,
            IBinder iBinder,
            IFaceServiceReceiver iFaceServiceReceiver,
            byte[] bArr,
            String str,
            long j,
            int[] iArr,
            Surface surface,
            boolean z,
            FaceEnrollOptions faceEnrollOptions) {
        this.f$0 = faceProvider;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = iBinder;
        this.f$4 = iFaceServiceReceiver;
        this.f$5 = bArr;
        this.f$6 = str;
        this.f$7 = j;
        this.f$8 = iArr;
        this.f$9 = surface;
        this.f$10 = z;
        this.f$11 = faceEnrollOptions;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FaceProvider faceProvider = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        IBinder iBinder = this.f$3;
        IFaceServiceReceiver iFaceServiceReceiver = this.f$4;
        byte[] bArr = this.f$5;
        String str = this.f$6;
        long j = this.f$7;
        int[] iArr = this.f$8;
        Surface surface = this.f$9;
        boolean z = this.f$10;
        FaceEnrollOptions faceEnrollOptions = this.f$11;
        ((Sensor) faceProvider.mFaceSensors.mSensors.get(i)).scheduleFaceUpdateActiveUserClient(i2);
        faceProvider.scheduleForSensor(
                i,
                new FaceEnrollClient(
                        faceProvider.mContext,
                        ((Sensor) faceProvider.mFaceSensors.mSensors.get(i)).mLazySession,
                        iBinder,
                        new ClientMonitorCallbackConverter(iFaceServiceReceiver),
                        i2,
                        bArr,
                        str,
                        j,
                        FaceUtils.getInstance(i, null),
                        iArr,
                        surface,
                        i,
                        faceProvider.createLogger(1, 0, faceProvider.mAuthenticationStatsCollector),
                        faceProvider.mBiometricContext,
                        ((Sensor) faceProvider.mFaceSensors.mSensors.get(i))
                                .mSensorProperties
                                .maxEnrollmentsPerUser,
                        z,
                        faceEnrollOptions,
                        faceProvider.mAuthenticationStateListeners),
                faceProvider.mBiometricStateCallback);
    }
}

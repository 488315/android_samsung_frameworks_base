package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.hardware.SensorPrivacyManager;
import android.hardware.face.FaceAuthenticateOptions;
import android.os.Bundle;
import android.os.IBinder;

import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.LockoutTracker;
import com.android.server.biometrics.sensors.face.SemFaceUtils;
import com.android.server.biometrics.sensors.face.aidl.FaceProvider.AnonymousClass3;

public final /* synthetic */ class FaceProvider$$ExternalSyntheticLambda10 implements Runnable {
    public final /* synthetic */ FaceProvider f$0;
    public final /* synthetic */ FaceAuthenticateOptions f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ IBinder f$3;
    public final /* synthetic */ long f$4;
    public final /* synthetic */ ClientMonitorCallbackConverter f$5;
    public final /* synthetic */ long f$6;
    public final /* synthetic */ boolean f$7;
    public final /* synthetic */ int f$8;
    public final /* synthetic */ int f$9;

    public /* synthetic */ FaceProvider$$ExternalSyntheticLambda10(
            FaceProvider faceProvider,
            FaceAuthenticateOptions faceAuthenticateOptions,
            boolean z,
            IBinder iBinder,
            long j,
            ClientMonitorCallbackConverter clientMonitorCallbackConverter,
            long j2,
            boolean z2,
            int i,
            int i2) {
        this.f$0 = faceProvider;
        this.f$1 = faceAuthenticateOptions;
        this.f$2 = z;
        this.f$3 = iBinder;
        this.f$4 = j;
        this.f$5 = clientMonitorCallbackConverter;
        this.f$6 = j2;
        this.f$7 = z2;
        this.f$8 = i;
        this.f$9 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        long j;
        int i;
        FaceProvider faceProvider = this.f$0;
        FaceAuthenticateOptions faceAuthenticateOptions = this.f$1;
        boolean z = this.f$2;
        IBinder iBinder = this.f$3;
        long j2 = this.f$4;
        ClientMonitorCallbackConverter clientMonitorCallbackConverter = this.f$5;
        long j3 = this.f$6;
        boolean z2 = this.f$7;
        int i2 = this.f$8;
        int i3 = this.f$9;
        faceProvider.getClass();
        int userId = faceAuthenticateOptions.getUserId();
        int sensorId = faceAuthenticateOptions.getSensorId();
        Bundle bundle = SemFaceUtils.mBundle;
        if (bundle == null) {
            i = 0;
            j = j3;
        } else {
            j = j3;
            i = bundle.getInt("sem_privileged_attr", 0);
        }
        boolean z3 = z | ((i & 4) != 0);
        boolean isStrongBiometric = Utils.isStrongBiometric(sensorId);
        ((Sensor) faceProvider.mFaceSensors.mSensors.get(sensorId))
                .scheduleFaceUpdateActiveUserClient(userId);
        LockoutTracker lockoutTracker =
                ((Sensor) faceProvider.mFaceSensors.mSensors.get(sensorId)).getLockoutTracker(true);
        Context context = faceProvider.mContext;
        FaceAuthenticationClient faceAuthenticationClient =
                new FaceAuthenticationClient(
                        context,
                        ((Sensor) faceProvider.mFaceSensors.mSensors.get(sensorId)).mLazySession,
                        iBinder,
                        j2,
                        clientMonitorCallbackConverter,
                        j,
                        z2,
                        faceAuthenticateOptions,
                        i2,
                        false,
                        faceProvider.createLogger(
                                2, i3, faceProvider.mAuthenticationStatsCollector),
                        faceProvider.mBiometricContext,
                        isStrongBiometric,
                        faceProvider.mUsageStats,
                        lockoutTracker,
                        z3,
                        (SensorPrivacyManager) context.getSystemService(SensorPrivacyManager.class),
                        Utils.getCurrentStrength(sensorId),
                        faceProvider.mAuthenticationStateListeners);
        faceProvider.scheduleForSensor(
                sensorId,
                faceAuthenticationClient,
                faceProvider.new AnonymousClass3(userId, sensorId, j2, faceAuthenticationClient));
    }
}

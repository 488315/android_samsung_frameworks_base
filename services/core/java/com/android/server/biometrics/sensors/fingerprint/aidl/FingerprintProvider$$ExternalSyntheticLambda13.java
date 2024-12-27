package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.IBinder;

import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.fingerprint.FingerprintUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FingerprintProvider$$ExternalSyntheticLambda13
        implements Runnable {
    public final /* synthetic */ FingerprintProvider f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ IBinder f$2;
    public final /* synthetic */ IFingerprintServiceReceiver f$3;
    public final /* synthetic */ int[] f$4;
    public final /* synthetic */ int f$5;
    public final /* synthetic */ String f$6;

    public /* synthetic */ FingerprintProvider$$ExternalSyntheticLambda13(
            FingerprintProvider fingerprintProvider,
            int i,
            IBinder iBinder,
            IFingerprintServiceReceiver iFingerprintServiceReceiver,
            int[] iArr,
            int i2,
            String str) {
        this.f$0 = fingerprintProvider;
        this.f$1 = i;
        this.f$2 = iBinder;
        this.f$3 = iFingerprintServiceReceiver;
        this.f$4 = iArr;
        this.f$5 = i2;
        this.f$6 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FingerprintProvider fingerprintProvider = this.f$0;
        int i = this.f$1;
        IBinder iBinder = this.f$2;
        IFingerprintServiceReceiver iFingerprintServiceReceiver = this.f$3;
        int[] iArr = this.f$4;
        int i2 = this.f$5;
        String str = this.f$6;
        fingerprintProvider.getClass();
        fingerprintProvider.scheduleForSensor$1(
                i,
                new FingerprintRemovalClient(
                        fingerprintProvider.mContext,
                        ((Sensor) fingerprintProvider.mFingerprintSensors.mSensors.get(i))
                                .mLazySession,
                        iBinder,
                        new ClientMonitorCallbackConverter(iFingerprintServiceReceiver),
                        iArr,
                        i2,
                        str,
                        FingerprintUtils.getInstance(i),
                        i,
                        fingerprintProvider.createLogger$1(
                                4, 0, fingerprintProvider.mAuthenticationStatsCollector),
                        fingerprintProvider.mBiometricContext,
                        ((Sensor) fingerprintProvider.mFingerprintSensors.mSensors.get(i))
                                .mAuthenticatorIds),
                fingerprintProvider.mBiometricStateCallback);
    }
}

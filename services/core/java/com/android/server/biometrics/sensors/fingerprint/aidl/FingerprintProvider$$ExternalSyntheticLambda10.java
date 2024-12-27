package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.os.IBinder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FingerprintProvider$$ExternalSyntheticLambda10 implements Runnable {
    public final /* synthetic */ FingerprintProvider f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ IBinder f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ String f$4;
    public final /* synthetic */ long f$5;

    public /* synthetic */ FingerprintProvider$$ExternalSyntheticLambda10(FingerprintProvider fingerprintProvider, int i, IBinder iBinder, int i2, String str, long j) {
        this.f$0 = fingerprintProvider;
        this.f$1 = i;
        this.f$2 = iBinder;
        this.f$3 = i2;
        this.f$4 = str;
        this.f$5 = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FingerprintProvider fingerprintProvider = this.f$0;
        int i = this.f$1;
        IBinder iBinder = this.f$2;
        int i2 = this.f$3;
        String str = this.f$4;
        long j = this.f$5;
        fingerprintProvider.getClass();
        fingerprintProvider.scheduleForSensor$1(i, new FingerprintRevokeChallengeClient(fingerprintProvider.mContext, ((Sensor) fingerprintProvider.mFingerprintSensors.mSensors.get(i)).mLazySession, iBinder, i2, str, i, fingerprintProvider.createLogger$1(0, 0, fingerprintProvider.mAuthenticationStatsCollector), fingerprintProvider.mBiometricContext, j));
    }
}

package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.content.Context;

import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.InvalidationRequesterClient;
import com.android.server.biometrics.sensors.fingerprint.FingerprintUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class FingerprintProvider$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FingerprintProvider f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ FingerprintProvider$$ExternalSyntheticLambda0(
            FingerprintProvider fingerprintProvider, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = fingerprintProvider;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FingerprintProvider fingerprintProvider = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                fingerprintProvider.getClass();
                Context context = fingerprintProvider.mContext;
                fingerprintProvider.scheduleForSensor$1(
                        i2,
                        new InvalidationRequesterClient(
                                context,
                                i,
                                i2,
                                BiometricLogger.ofUnknown(context),
                                fingerprintProvider.mBiometricContext,
                                FingerprintUtils.getInstance(i2)));
                break;
            case 1:
                FingerprintProvider fingerprintProvider2 = this.f$0;
                int i3 = this.f$1;
                ((Sensor) fingerprintProvider2.mFingerprintSensors.mSensors.get(i3))
                        .mScheduler.startPreparedClient(this.f$2);
                break;
            default:
                FingerprintProvider fingerprintProvider3 = this.f$0;
                int i4 = this.f$1;
                int i5 = this.f$2;
                fingerprintProvider3.getClass();
                fingerprintProvider3.scheduleForSensor$1(
                        i4,
                        new FingerprintGetAuthenticatorIdClient(
                                fingerprintProvider3.mContext,
                                ((Sensor) fingerprintProvider3.mFingerprintSensors.mSensors.get(i4))
                                        .mLazySession,
                                i5,
                                fingerprintProvider3.mContext.getOpPackageName(),
                                i4,
                                fingerprintProvider3.createLogger$1(
                                        0, 0, fingerprintProvider3.mAuthenticationStatsCollector),
                                fingerprintProvider3.mBiometricContext,
                                ((Sensor) fingerprintProvider3.mFingerprintSensors.mSensors.get(i4))
                                        .mAuthenticatorIds));
                break;
        }
    }
}

package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.app.ActivityManager;
import android.os.IBinder;

import com.android.server.biometrics.sensors.fingerprint.SemFpPauseResumeHandler;
import com.android.server.biometrics.sensors.fingerprint.SemUdfpsSysUiImpl;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FingerprintProvider$$ExternalSyntheticLambda9
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FingerprintProvider f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ FingerprintProvider$$ExternalSyntheticLambda9(
            FingerprintProvider fingerprintProvider, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = fingerprintProvider;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FingerprintProvider fingerprintProvider = this.f$0;
                int i = this.f$1;
                if (fingerprintProvider.mFingerprintSensors.mSensors.contains(i)) {
                    IBinder.DeathRecipient currentClient =
                            ((Sensor) fingerprintProvider.mFingerprintSensors.mSensors.get(i))
                                    .mScheduler.getCurrentClient();
                    if (currentClient instanceof SemFpPauseResumeHandler) {
                        SemFingerprintEnrollClient semFingerprintEnrollClient =
                                (SemFingerprintEnrollClient)
                                        ((SemFpPauseResumeHandler) currentClient);
                        SemUdfpsSysUiImpl semUdfpsSysUiImpl = semFingerprintEnrollClient.mUdfpsImpl;
                        if (semUdfpsSysUiImpl != null) {
                            semUdfpsSysUiImpl.handleOnPause();
                        }
                        semFingerprintEnrollClient.request(0);
                        break;
                    }
                }
                break;
            case 1:
                FingerprintProvider fingerprintProvider2 = this.f$0;
                int i2 = this.f$1;
                if (fingerprintProvider2.mFingerprintSensors.mSensors.contains(i2)) {
                    IBinder.DeathRecipient currentClient2 =
                            ((Sensor) fingerprintProvider2.mFingerprintSensors.mSensors.get(i2))
                                    .mScheduler.getCurrentClient();
                    if (currentClient2 instanceof SemFpPauseResumeHandler) {
                        SemFingerprintEnrollClient semFingerprintEnrollClient2 =
                                (SemFingerprintEnrollClient)
                                        ((SemFpPauseResumeHandler) currentClient2);
                        SemUdfpsSysUiImpl semUdfpsSysUiImpl2 =
                                semFingerprintEnrollClient2.mUdfpsImpl;
                        if (semUdfpsSysUiImpl2 != null) {
                            semUdfpsSysUiImpl2.handleOnResume();
                        }
                        semFingerprintEnrollClient2.request(1);
                        break;
                    }
                }
                break;
            default:
                FingerprintProvider fingerprintProvider3 = this.f$0;
                int i3 = this.f$1;
                fingerprintProvider3.getClass();
                fingerprintProvider3.scheduleInternalCleanup(
                        i3,
                        ActivityManager.getCurrentUser(),
                        fingerprintProvider3.mInternalCleanupClientCallback);
                break;
        }
    }
}

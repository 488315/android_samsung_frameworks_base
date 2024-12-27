package com.android.server.biometrics.sensors.fingerprint;

import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemFingerprintServiceExtImpl$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemFingerprintServiceExtImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ SemFingerprintServiceExtImpl$$ExternalSyntheticLambda1(
            SemFingerprintServiceExtImpl semFingerprintServiceExtImpl, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = semFingerprintServiceExtImpl;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SemFingerprintServiceExtImpl semFingerprintServiceExtImpl = this.f$0;
                boolean z = this.f$1;
                if (semFingerprintServiceExtImpl.mHasUltrasonicUdfps
                        && !semFingerprintServiceExtImpl.mIsScreenOn
                        && z) {
                    semFingerprintServiceExtImpl.handleQcomForceQDB();
                }
                semFingerprintServiceExtImpl.mIsScreenOn = z;
                if (semFingerprintServiceExtImpl.mHasOpticalUdfps) {
                    SemFingerprintServiceExtImpl.Injector injector =
                            semFingerprintServiceExtImpl.mInjector;
                    if (!z) {
                        injector.getClass();
                        SemUdfpsTspManager.get().screenOff();
                        return;
                    }
                    injector.getClass();
                    SemUdfpsTspManager semUdfpsTspManager = SemUdfpsTspManager.get();
                    synchronized (semUdfpsTspManager) {
                        semUdfpsTspManager.setHalfMode(false);
                        if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_ULTRASONIC) {
                            semUdfpsTspManager.enable(0);
                            semUdfpsTspManager.mIsHalfModeBlocked = false;
                        }
                    }
                    return;
                }
                return;
            default:
                SemFingerprintServiceExtImpl semFingerprintServiceExtImpl2 = this.f$0;
                boolean z2 = this.f$1;
                ServiceProvider serviceProvider =
                        semFingerprintServiceExtImpl2.getServiceProvider();
                if (serviceProvider != null) {
                    ((FingerprintProvider) serviceProvider).semSetTpaHalEnabled(z2);
                    return;
                }
                return;
        }
    }
}

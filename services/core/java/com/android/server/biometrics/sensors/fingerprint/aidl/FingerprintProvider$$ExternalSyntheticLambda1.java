package com.android.server.biometrics.sensors.fingerprint.aidl;

import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.fingerprint.SemFpHalLifecycleListener;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FingerprintProvider$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ FingerprintProvider$$ExternalSyntheticLambda1(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FingerprintProvider fingerprintProvider = (FingerprintProvider) this.f$0;
                SemFpHalLifecycleListener semFpHalLifecycleListener = (SemFpHalLifecycleListener) this.f$1;
                if (semFpHalLifecycleListener == null) {
                    fingerprintProvider.getClass();
                    break;
                } else if (!fingerprintProvider.mLifecycleListeners.contains(semFpHalLifecycleListener)) {
                    fingerprintProvider.mLifecycleListeners.add(semFpHalLifecycleListener);
                    if (fingerprintProvider.getHalInstance() != null && fingerprintProvider.mIsHalStarted) {
                        fingerprintProvider.mHandler.post(new FingerprintProvider$$ExternalSyntheticLambda1(1, fingerprintProvider, semFpHalLifecycleListener));
                        break;
                    }
                }
                break;
            case 1:
                FingerprintProvider fingerprintProvider2 = (FingerprintProvider) this.f$0;
                SemFpHalLifecycleListener semFpHalLifecycleListener2 = (SemFpHalLifecycleListener) this.f$1;
                fingerprintProvider2.getClass();
                semFpHalLifecycleListener2.onHalStarted(fingerprintProvider2);
                break;
            default:
                ((FingerprintProvider.AnonymousClass1) this.f$0).this$0.mProviderEx.updateCacheDataOfHAL(((BaseClientMonitor) this.f$1).mSensorId);
                break;
        }
    }
}

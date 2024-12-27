package com.android.server.biometrics.sensors.fingerprint;

import com.android.server.biometrics.SemBiometricSysUiManager;
import com.android.server.biometrics.SemBiometricSysUiManager$$ExternalSyntheticLambda6;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SemFingerprintServiceExtImpl$$ExternalSyntheticLambda6
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SemFingerprintServiceExtImpl$$ExternalSyntheticLambda6(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((SemFingerprintServiceExtImpl) obj).mSysUiDisplayStateCallback = null;
                break;
            case 1:
                ServiceProvider serviceProvider =
                        ((SemFingerprintServiceExtImpl) obj).getServiceProvider();
                if (serviceProvider != null) {
                    ((FingerprintProvider) serviceProvider).semUpdateTpaAction();
                    break;
                }
                break;
            case 2:
                ((SemFingerprintServiceExtImpl) obj).mInjector.getClass();
                SemBiometricSysUiManager semBiometricSysUiManager =
                        SemBiometricSysUiManager.sInstance;
                semBiometricSysUiManager.bind();
                semBiometricSysUiManager.handleRequest(
                        new SemBiometricSysUiManager$$ExternalSyntheticLambda6(
                                semBiometricSysUiManager, 118, 0));
                break;
            case 3:
                ((SemFingerprintServiceExtImpl) obj).mSysUiDisplayBrightnessCallback = null;
                break;
            default:
                SemFingerprintServiceExtImpl.this.unregisterAodController();
                break;
        }
    }
}

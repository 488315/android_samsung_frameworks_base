package com.samsung.android.biometrics.app.setting;

public final /* synthetic */ class SysUiUdfpsManager$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ SysUiUdfpsManager f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ SysUiUdfpsManager$$ExternalSyntheticLambda8(
            SysUiUdfpsManager sysUiUdfpsManager, boolean z) {
        this.f$0 = sysUiUdfpsManager;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SysUiUdfpsManager sysUiUdfpsManager = this.f$0;
        boolean z = this.f$1;
        if (z == sysUiUdfpsManager.mHasPrepareRequest) {
            return;
        }
        if (z) {
            ((FpServiceProviderImpl) sysUiUdfpsManager.mFpSvcProvider)
                    .requestToFpSvc(12, 1, 0L, null);
            sysUiUdfpsManager.mHasPrepareRequest = true;
        } else {
            ((FpServiceProviderImpl) sysUiUdfpsManager.mFpSvcProvider)
                    .requestToFpSvc(12, 0, 0L, null);
            sysUiUdfpsManager.mHasPrepareRequest = false;
        }
    }
}

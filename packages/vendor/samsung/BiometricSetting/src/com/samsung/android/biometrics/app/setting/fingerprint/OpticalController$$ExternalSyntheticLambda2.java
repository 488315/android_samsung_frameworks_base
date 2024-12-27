package com.samsung.android.biometrics.app.setting.fingerprint;

import android.os.Handler;

import com.samsung.android.biometrics.app.setting.FpServiceProviderImpl;
import com.samsung.android.biometrics.app.setting.Utils;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final /* synthetic */ class OpticalController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ OpticalController f$0;

    @Override // java.lang.Runnable
    public final void run() {
        long j;
        OpticalController opticalController = this.f$0;
        if (opticalController.mIsTouchDown) {
            opticalController.mSensorInfo.getClass();
            boolean z = Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE;
            if (z) {
                ((FpServiceProviderImpl) opticalController.mFpProvider)
                        .requestToFpSvc(5, 1, 0L, null);
            } else {
                opticalController.mHbmProvider.turnOnLightSource();
            }
            Handler handler = opticalController.mH;
            if (opticalController.isTablet()) {
                opticalController.mSensorInfo.getClass();
                if (z) {
                    j = 66;
                    handler.sendEmptyMessageDelayed(2, j);
                }
            }
            j = 34;
            handler.sendEmptyMessageDelayed(2, j);
        }
    }
}

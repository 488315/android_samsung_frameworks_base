package com.samsung.android.biometrics.app.setting;

import android.util.Log;

import com.samsung.android.biometrics.app.setting.fingerprint.OpticalController;

import java.util.ArrayList;

public final /* synthetic */ class SysUiUdfpsOpticalManager$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                OpticalController opticalController = (OpticalController) obj;
                if (!opticalController.mDisplayStateManager.isEnabledHbm()) {
                    if (!((ArrayList) opticalController.mPendingActionsWhenTurnedOnHbm)
                            .contains(opticalController.mActionTurnOnCalibrationLs)) {
                        ((ArrayList) opticalController.mPendingActionsWhenTurnedOnHbm)
                                .add(opticalController.mActionTurnOnCalibrationLs);
                        break;
                    }
                } else {
                    opticalController.mSensorInfo.getClass();
                    if (!Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE) {
                        opticalController.mHbmProvider.turnOnCalibrationLightSource();
                        break;
                    } else {
                        ((FpServiceProviderImpl) opticalController.mFpProvider)
                                .requestToFpSvc(5, 1, 0L, null);
                        break;
                    }
                }
                break;
            default:
                DisplayStateManager displayStateManager = (DisplayStateManager) obj;
                displayStateManager.getClass();
                if (Utils.DEBUG) {
                    Log.d("BSS_DisplayStateManager", "updateDisplayStateInAuthenticationSucceeded");
                }
                if (!displayStateManager.isOnState()) {
                    displayStateManager.handleDisplayStateChanged(
                            displayStateManager.mCurrentStateLogical);
                    break;
                }
                break;
        }
    }
}

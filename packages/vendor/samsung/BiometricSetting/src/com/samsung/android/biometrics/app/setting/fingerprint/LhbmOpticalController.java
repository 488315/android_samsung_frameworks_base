package com.samsung.android.biometrics.app.setting.fingerprint;

import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.DisplayStateManager$$ExternalSyntheticLambda0;
import com.samsung.android.biometrics.app.setting.DisplayStateManager$$ExternalSyntheticLambda1;
import com.samsung.android.biometrics.app.setting.FpServiceProviderImpl;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final class LhbmOpticalController extends OpticalController
        implements DisplayStateManager.LimitDisplayStateCallback {
    @Override // com.samsung.android.biometrics.app.setting.fingerprint.OpticalController
    public HbmLockStateMonitor createHbmLockStateMonitor() {
        return null;
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.OpticalController
    public final HbmProvider createHbmProvider(DisplayBrightnessMonitor displayBrightnessMonitor) {
        return new HbmProvider() { // from class:
                                   // com.samsung.android.biometrics.app.setting.fingerprint.LhbmOpticalController.1
            @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
            public final void turnOffCalibrationLightSource() {
                turnOffLightSource();
            }

            @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
            public final void turnOffHBM() {
                LhbmOpticalController lhbmOpticalController = LhbmOpticalController.this;
                DisplayStateManager displayStateManager =
                        lhbmOpticalController.mDisplayStateManager;
                displayStateManager.mVirtualHbmNode.set(false);
                displayStateManager.mHandler.post(
                        new DisplayStateManager$$ExternalSyntheticLambda0(displayStateManager, 0));
                ((FpServiceProviderImpl) lhbmOpticalController.mFpProvider)
                        .requestToFpSvc(13, 0, 0L, null);
            }

            @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
            public final void turnOffLightSource() {
                LhbmOpticalController.this.localHbmControlRequest(1);
            }

            @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
            public final void turnOnCalibrationLightSource() {
                turnOnLightSource();
            }

            @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
            public final void turnOnHBM() {
                LhbmOpticalController lhbmOpticalController = LhbmOpticalController.this;
                DisplayStateManager displayStateManager =
                        lhbmOpticalController.mDisplayStateManager;
                displayStateManager.mVirtualHbmNode.set(true);
                displayStateManager.mHandler.post(
                        new DisplayStateManager$$ExternalSyntheticLambda0(displayStateManager, 0));
                ((FpServiceProviderImpl) lhbmOpticalController.mFpProvider)
                        .requestToFpSvc(13, 1, 0L, null);
            }

            @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
            public final void turnOnLightSource() {
                LhbmOpticalController.this.localHbmControlRequest(2);
            }
        };
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.OpticalController
    public final void handleDisplayStateChanged(int i) {
        super.handleDisplayStateChanged(i);
        if (i == 2) {
            localHbmControlRequest(1);
        }
    }

    public final void localHbmControlRequest(int i) {
        if (hasMaskClient()) {
            ((FpServiceProviderImpl) this.mFpProvider).requestToFpSvc(13, i, 0L, null);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.DisplayStateManager.LimitDisplayStateCallback
    public final void onLimitDisplayStateChanged(boolean z) {
        if (z) {
            localHbmControlRequest(1);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.OpticalController
    public final void start() {
        super.start();
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        displayStateManager.mBgHandler.post(
                new DisplayStateManager$$ExternalSyntheticLambda1(displayStateManager, this, 0));
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.OpticalController
    public final void stop() {
        super.stop();
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        displayStateManager.mBgHandler.post(
                new DisplayStateManager$$ExternalSyntheticLambda1(displayStateManager, this, 1));
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.OpticalController
    public final void handleOnTaskStackChanged() {}
}

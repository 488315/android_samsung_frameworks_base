package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.SysUiManager;

/* loaded from: classes.dex */
public final class LhbmOpticalController extends OpticalController implements DisplayStateManager.LimitDisplayStateCallback {
    public LhbmOpticalController(Context context, UdfpsInfo udfpsInfo, FpServiceProvider fpServiceProvider, DisplayStateManager displayStateManager, UdfpsIconOptionMonitor udfpsIconOptionMonitor, AodStatusMonitor aodStatusMonitor) {
        super(context, udfpsInfo, fpServiceProvider, DisplayBrightnessMonitor.getInstance(), displayStateManager, udfpsIconOptionMonitor, aodStatusMonitor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void localHbmControlRequest(int i) {
        if (hasMaskClient()) {
            ((SysUiManager) this.mFpProvider).request(13, i, 0L);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.OpticalController
    @VisibleForTesting
    protected HbmLockStateMonitor createHbmLockStateMonitor() {
        return null;
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.OpticalController
    protected final HbmProvider createHbmProvider(DisplayBrightnessMonitor displayBrightnessMonitor) {
        return new HbmProvider() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.LhbmOpticalController.1
            @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
            public final void turnOffCalibrationLightSource() {
                turnOffLightSource();
            }

            @Override // com.samsung.android.biometrics.app.setting.fingerprint.HbmProvider
            public final void turnOffHBM() {
                LhbmOpticalController lhbmOpticalController = LhbmOpticalController.this;
                lhbmOpticalController.mDisplayStateManager.setVirtualHbmNode(false);
                ((SysUiManager) lhbmOpticalController.mFpProvider).request(13, 0, 0L);
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
                lhbmOpticalController.mDisplayStateManager.setVirtualHbmNode(true);
                ((SysUiManager) lhbmOpticalController.mFpProvider).request(13, 1, 0L);
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

    @Override // com.samsung.android.biometrics.app.setting.DisplayStateManager.LimitDisplayStateCallback
    public final void onLimitDisplayStateChanged(boolean z) {
        if (z) {
            localHbmControlRequest(1);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.OpticalController
    public final void start() {
        super.start();
        this.mDisplayStateManager.registerLimitDisplayStateCallback(this);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.OpticalController
    public final void stop() {
        super.stop();
        this.mDisplayStateManager.unregisterLimitDisplayStateCallback(this);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.OpticalController
    public final void handleOnTaskStackChanged() {
    }
}

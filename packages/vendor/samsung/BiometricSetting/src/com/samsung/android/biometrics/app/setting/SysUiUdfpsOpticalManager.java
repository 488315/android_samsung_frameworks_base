package com.samsung.android.biometrics.app.setting;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;

import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor;
import com.samsung.android.biometrics.app.setting.fingerprint.DisplayBrightnessMonitor;
import com.samsung.android.biometrics.app.setting.fingerprint.FingerprintSensorInfo;
import com.samsung.android.biometrics.app.setting.fingerprint.LhbmOpticalController;
import com.samsung.android.biometrics.app.setting.fingerprint.OpticalController;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsClient;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsIconOptionMonitor;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsIconVisibilityNotifier;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsKeyguardClient;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.IntConsumer;

public final class SysUiUdfpsOpticalManager extends SysUiUdfpsManager {
    public final OpticalController mOpticalController;

    public SysUiUdfpsOpticalManager(Context context) {
        super(
                context,
                new DisplayStateManager(context),
                new PowerServiceProviderImpl(context),
                new TaskStackObserver(),
                new FpServiceProviderImpl(),
                new AodStatusMonitor(context),
                new UdfpsIconOptionMonitor(context),
                new UdfpsIconVisibilityNotifier(context));
        this.mOpticalController =
                Utils.Config.FP_FEATURE_LOCAL_HBM
                        ? new LhbmOpticalController(
                                context,
                                this.mFingerprintSensorInfo,
                                this.mFpSvcProvider,
                                DisplayBrightnessMonitor.sInstanceHolder.sInstance,
                                this.mDisplayStateManager,
                                this.mIconOptionMonitor,
                                this.mAodStatusMonitor)
                        : new OpticalController(
                                context,
                                this.mFingerprintSensorInfo,
                                this.mFpSvcProvider,
                                DisplayBrightnessMonitor.sInstanceHolder.sInstance,
                                this.mDisplayStateManager,
                                this.mIconOptionMonitor,
                                this.mAodStatusMonitor);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiUdfpsManager,
              // com.samsung.android.biometrics.app.setting.SysUiManager
    public final SysUiClient createClient(
            SysUiClientOptions sysUiClientOptions,
            ISemBiometricSysUiCallback iSemBiometricSysUiCallback) {
        SysUiClient createClient =
                super.createClient(sysUiClientOptions, iSemBiometricSysUiCallback);
        if (createClient instanceof UdfpsClient) {
            OpticalController opticalController = this.mOpticalController;
            OpticalController.MaskClient maskClient = new OpticalController.MaskClient();
            maskClient.mSessionId = createClient.mSessionId;
            opticalController.addMaskClient(maskClient);
        }
        return createClient;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiUdfpsManager,
              // com.samsung.android.biometrics.app.setting.SysUiManager
    public final void destroy() {
        this.mOpticalController.stop();
        super.destroy();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public final void handleAuthenticationSucceeded(String str) {
        if (this.mCurrentClient instanceof UdfpsKeyguardClient) {
            if (Utils.Config.FEATURE_SUPPORT_AOD_TRANSITION_ANIMATION) {
                Handler handler = this.mH;
                DisplayStateManager displayStateManager = this.mDisplayStateManager;
                Objects.requireNonNull(displayStateManager);
                handler.postAtFrontOfQueue(
                        new SysUiUdfpsOpticalManager$$ExternalSyntheticLambda0(
                                displayStateManager));
            }
            OpticalController opticalController = this.mOpticalController;
            int i = this.mCurrentClient.mSessionId;
            opticalController.mIsDispatchedTouchDownEvent = false;
            if (i != 0) {
                opticalController.removeMaskClient(i);
                if (opticalController.mDisplayStateManager.isOnState()) {
                    opticalController.removeKeyguardMaskClientIfExist();
                } else {
                    opticalController.removeKeyguardMaskClientIfExist();
                }
            }
        } else {
            this.mOpticalController.mIsDispatchedTouchDownEvent = false;
        }
        super.handleAuthenticationSucceeded(str);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public final void handleCalibrationMode(int i, int i2, Bundle bundle) {
        SysUiClient sysUiClient = this.mCurrentClient;
        if (sysUiClient != null) {
            sysUiClient.stop();
        }
        if (i2 != 1) {
            OpticalController opticalController = this.mOpticalController;
            opticalController.mSensorInfo.getClass();
            if (Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE) {
                ((FpServiceProviderImpl) opticalController.mFpProvider)
                        .requestToFpSvc(5, 0, 0L, null);
            } else {
                opticalController.mHbmProvider.turnOffCalibrationLightSource();
            }
            ((ArrayList) opticalController.mPendingActionsWhenTurnedOnHbm)
                    .remove(opticalController.mActionTurnOnCalibrationLs);
            this.mOpticalController.removeMaskClient(i);
            return;
        }
        FingerprintSensorInfo fingerprintSensorInfo = this.mFingerprintSensorInfo;
        String string = bundle.getString("nits", "");
        fingerprintSensorInfo.getClass();
        if (!TextUtils.isEmpty(string)) {
            fingerprintSensorInfo.mCalibrationLightColor = string.toLowerCase();
        }
        bundle.getString("KEY_PACKAGE_NAME", "UnknownCalibrationClient");
        this.mOpticalController.addMaskClient(new OpticalController.MaskClient(i, false));
        Handler handler = this.mH;
        OpticalController opticalController2 = this.mOpticalController;
        Objects.requireNonNull(opticalController2);
        handler.post(new SysUiUdfpsOpticalManager$$ExternalSyntheticLambda0(opticalController2));
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiUdfpsManager,
              // com.samsung.android.biometrics.app.setting.SysUiManager
    public final void handleCaptureCompleted(int i) {
        this.mOpticalController.mHbmProvider.turnOffLightSource();
        super.handleCaptureCompleted(i);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public final void handleCommand(int i, int i2, int i3, Bundle bundle) {
        super.handleCommand(i, i2, i3, bundle);
        if (i2 == 500) {
            boolean z = bundle.getBoolean("KEY_KEYGUARD", false);
            bundle.getString("KEY_PACKAGE_NAME", "Unknown");
            OpticalController.MaskClient maskClient = new OpticalController.MaskClient(i, z);
            if (i3 != 1) {
                this.mOpticalController.removeMaskClient(i);
                return;
            }
            if (z) {
                this.mDisplayStateManager.updateDisplayState();
            }
            this.mOpticalController.addMaskClient(maskClient);
            BackgroundThread backgroundThread = BackgroundThread.get();
            SysUiUdfpsManager$$ExternalSyntheticLambda8
                    sysUiUdfpsManager$$ExternalSyntheticLambda8 =
                            new SysUiUdfpsManager$$ExternalSyntheticLambda8(this, false);
            backgroundThread.getClass();
            BackgroundThread.sHandler.post(sysUiUdfpsManager$$ExternalSyntheticLambda8);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiUdfpsManager,
              // com.samsung.android.biometrics.app.setting.SysUiManager
    public final void handleOnClientFinished(SysUiClient sysUiClient) {
        super.handleOnClientFinished(sysUiClient);
        this.mOpticalController.removeMaskClient(sysUiClient.mSessionId);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiUdfpsManager
    public final void handleOnFodSingleTap() {
        this.mOpticalController.handleSingleTapEvent();
        super.handleOnFodSingleTap();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiUdfpsManager
    public final void handleOnFodTouchDown() {
        int i;
        OpticalController opticalController = this.mOpticalController;
        if (opticalController.hasAuthMaskClient() && !opticalController.mIsTouchDown) {
            DisplayStateManager displayStateManager = opticalController.mDisplayStateManager;
            if (displayStateManager.mCurrentDisplayState != 1001) {
                if (displayStateManager.isOnState()
                        || !opticalController.mDisplayStateManager.mIsLimitedDisplayInProgress
                                .get()) {
                    opticalController.mIsTouchDown = true;
                    opticalController.turnOnHbm$1();
                    if (opticalController.mAodStatusMonitor.isShowing()
                            || (i = opticalController.mDisplayStateManager.mCurrentDisplayState)
                                    == 3
                            || i == 4) {
                        ((FpServiceProviderImpl) opticalController.mFpProvider)
                                .requestToFpSvc(11, 0, SystemClock.elapsedRealtime(), null);
                    }
                    if (opticalController.mDisplayStateManager.isEnabledHbm()) {
                        opticalController.mActionHandleTouchDown.run();
                    } else {
                        ((ArrayList) opticalController.mPendingActionsWhenTurnedOnHbm)
                                .add(opticalController.mActionHandleTouchDown);
                    }
                    ((FpServiceProviderImpl) opticalController.mFpProvider)
                            .requestToFpSvc(8, 1, 0L, null);
                }
            }
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiUdfpsManager
    public final void handleOnFodTouchUp() {
        this.mOpticalController.onTouchUp();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public final void handleOnTaskStackListener(int i, int i2) {
        super.handleOnTaskStackListener(i, i2);
        this.mOpticalController.handleOnTaskStackChanged();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public final void handlePrepareSession() {
        if (this.mOpticalController.hasMaskClient()) {
            return;
        }
        BackgroundThread backgroundThread = BackgroundThread.get();
        SysUiUdfpsManager$$ExternalSyntheticLambda8 sysUiUdfpsManager$$ExternalSyntheticLambda8 =
                new SysUiUdfpsManager$$ExternalSyntheticLambda8(this, true);
        backgroundThread.getClass();
        BackgroundThread.sHandler.post(sysUiUdfpsManager$$ExternalSyntheticLambda8);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiUdfpsManager,
              // com.samsung.android.biometrics.app.setting.SysUiManager
    public final void init() {
        super.init();
        this.mOpticalController.start();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public final void onConfigurationChanged(Configuration configuration) {
        this.mOpticalController.mHbmProvider.onConfigurationInfoChanged();
        super.onConfigurationChanged(configuration);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public final void onDisplayStateChanged(int i) {
        this.mOpticalController.handleDisplayStateChanged(i);
        super.onDisplayStateChanged(i);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiManager
    public final void onRotationStateChanged(int i) {
        this.mOpticalController.mHbmProvider.onRotationChanged();
        super.onRotationStateChanged(i);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiUdfpsManager
    public final void setUdfpsForBpClient(BiometricPromptClient biometricPromptClient) {
        super.setUdfpsForBpClient(biometricPromptClient);
        final OpticalController opticalController = this.mOpticalController;
        Objects.requireNonNull(opticalController);
        biometricPromptClient.mRemoveUdfpsClient =
                new IntConsumer() { // from class:
                                    // com.samsung.android.biometrics.app.setting.SysUiUdfpsOpticalManager$$ExternalSyntheticLambda1
                    @Override // java.util.function.IntConsumer
                    public final void accept(int i) {
                        OpticalController.this.removeMaskClient(i);
                    }
                };
        OpticalController opticalController2 = this.mOpticalController;
        OpticalController.MaskClient maskClient = new OpticalController.MaskClient();
        maskClient.mSessionId = biometricPromptClient.mSessionId;
        opticalController2.addMaskClient(maskClient);
    }
}

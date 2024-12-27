package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.os.Bundle;

import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.SysUiClient;

import java.util.function.Consumer;

public abstract class UdfpsClient extends SysUiClient implements UdfpsWindowCallback {
    public final UdfpsSensorWindow mBaseSensorWindow;
    public int mDismissedReason;
    public final DisplayStateManager mDisplayStateManager;
    public boolean mIsKeyguard;
    public final Consumer mSensorIconVisibilityStateHandler;
    public final FingerprintSensorInfo mSensorInfo;

    public UdfpsClient(
            Context context,
            int i,
            int i2,
            ISemBiometricSysUiCallback iSemBiometricSysUiCallback,
            Bundle bundle,
            String str,
            DisplayStateManager displayStateManager,
            UdfpsSensorWindow udfpsSensorWindow,
            FingerprintSensorInfo fingerprintSensorInfo,
            Consumer consumer) {
        super(context, i2, iSemBiometricSysUiCallback, context.getMainLooper(), bundle, str);
        this.mDisplayStateManager = displayStateManager;
        this.mBaseSensorWindow = udfpsSensorWindow;
        this.mSensorInfo = fingerprintSensorInfo;
        this.mSensorIconVisibilityStateHandler = consumer;
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsWindowCallback
    public final void onSensorIconVisibilityChanged(int i) {
        this.mSensorIconVisibilityStateHandler.accept(Boolean.valueOf(i == 0));
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsWindowCallback
    public void onUserCancel(int i) {
        this.mDismissedReason = i;
        stop();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public void stop() {
        if (this.mDismissedReason != 0) {
            sendDismissedEvent(10, null);
        }
        super.stop();
    }
}

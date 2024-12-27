package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;

import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.SysUiClient;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final class FpGestureCalibrationClient extends SysUiClient {
    public GestureCalibrationWindow mCalWindow;
    public final int swipeDirection;

    public FpGestureCalibrationClient(
            Context context,
            int i,
            int i2,
            ISemBiometricSysUiCallback iSemBiometricSysUiCallback,
            Looper looper,
            Bundle bundle,
            String str) {
        super(context, i2, iSemBiometricSysUiCallback, looper, bundle, str);
        this.swipeDirection = this.mRawExtraInfo.getInt("KEY_FP_GESTURE_DIRECTION");
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void prepareWindows() {
        GestureCalibrationWindow gestureCalibrationWindow =
                new GestureCalibrationWindow(this.mContext, this, this.swipeDirection);
        this.mCalWindow = gestureCalibrationWindow;
        this.mWindows.add(gestureCalibrationWindow);
    }
}

package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.SysUiClient;

/* loaded from: classes.dex */
public final class FpGestureCalibrationClient extends SysUiClient implements GestureCalibrationCallback, FpGestureConsumer {
    private GestureCalibrationWindow mCalWindow;
    private final int swipeDirection;

    public static void $r8$lambda$fngob_YN54UvN1L8YaUPsgSs0Jg(FpGestureCalibrationClient fpGestureCalibrationClient) {
        fpGestureCalibrationClient.sendDismissedEvent(1);
        fpGestureCalibrationClient.stop();
    }

    public FpGestureCalibrationClient(Context context, int i, int i2, ISemBiometricSysUiCallback iSemBiometricSysUiCallback, Looper looper, Bundle bundle, String str) {
        super(context, i2, iSemBiometricSysUiCallback, looper, bundle, str);
        this.swipeDirection = getRawExtraInfo().getInt("KEY_FP_GESTURE_DIRECTION");
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public final int getUiType() {
        return 65536;
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.FpGestureConsumer
    public final void onGestureEvent() {
        this.mCalWindow.onCalibrationSucceeded();
        sendEvent(8, 0);
        this.mHandler.postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.FpGestureCalibrationClient$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FpGestureCalibrationClient.$r8$lambda$fngob_YN54UvN1L8YaUPsgSs0Jg(FpGestureCalibrationClient.this);
            }
        }, 1500L);
    }

    public final void onUiReady() {
        sendEvent(1, 0);
    }

    public final void onUserCancel(int i) {
        sendDismissedEvent(2);
        stop();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void prepareWindows() {
        GestureCalibrationWindow gestureCalibrationWindow = new GestureCalibrationWindow(this.mContext, this, this.swipeDirection);
        this.mCalWindow = gestureCalibrationWindow;
        this.mWindows.add(gestureCalibrationWindow);
    }
}

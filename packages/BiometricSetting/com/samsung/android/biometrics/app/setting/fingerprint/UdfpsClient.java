package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.os.Bundle;
import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.SysUiClient;
import com.samsung.android.biometrics.app.setting.SysUiManager$Injector$$ExternalSyntheticLambda0;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public abstract class UdfpsClient extends SysUiClient implements UdfpsWindowCallback {
    protected UdfpsSensorWindow mBaseSensorWindow;
    private int mDismissedReason;
    protected DisplayStateManager mDisplayStateManager;
    protected boolean mIsKeyguard;
    protected Consumer<Boolean> mSensorIconVisibilityStateHandler;
    protected UdfpsInfo mSensorInfo;

    public UdfpsClient(Context context, int i, int i2, ISemBiometricSysUiCallback iSemBiometricSysUiCallback, Bundle bundle, String str, DisplayStateManager displayStateManager, UdfpsSensorWindow udfpsSensorWindow, UdfpsInfo udfpsInfo, SysUiManager$Injector$$ExternalSyntheticLambda0 sysUiManager$Injector$$ExternalSyntheticLambda0) {
        super(context, i2, iSemBiometricSysUiCallback, context.getMainLooper(), bundle, str);
        this.mDisplayStateManager = displayStateManager;
        this.mBaseSensorWindow = udfpsSensorWindow;
        this.mSensorInfo = udfpsInfo;
        this.mSensorIconVisibilityStateHandler = sysUiManager$Injector$$ExternalSyntheticLambda0;
    }

    public void onSensorIconVisibilityChanged(int i) {
        this.mSensorIconVisibilityStateHandler.accept(Boolean.valueOf(i == 0));
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsWindowCallback
    public void onUserCancel(int i) {
        this.mDismissedReason = i;
        stop();
    }

    public final void pause() {
        UdfpsSensorWindow udfpsSensorWindow = this.mBaseSensorWindow;
        if (udfpsSensorWindow != null) {
            udfpsSensorWindow.showSensorIcon();
        }
    }

    public final void resume() {
        UdfpsSensorWindow udfpsSensorWindow = this.mBaseSensorWindow;
        if (udfpsSensorWindow != null) {
            udfpsSensorWindow.hideSensorIcon();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public void stop() {
        if (this.mDismissedReason != 0) {
            sendDismissedEvent(10);
        }
        super.stop();
    }
}

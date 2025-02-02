package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.os.Bundle;
import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.SysUiManager$Injector$$ExternalSyntheticLambda0;

/* loaded from: classes.dex */
public final class UdfpsEnrollClient extends UdfpsClient {
    UdfpsEnrollSensorWindow mEnrollWindow;

    public UdfpsEnrollClient(Context context, int i, int i2, ISemBiometricSysUiCallback iSemBiometricSysUiCallback, Bundle bundle, String str, DisplayStateManager displayStateManager, UdfpsSensorWindow udfpsSensorWindow, UdfpsInfo udfpsInfo, SysUiManager$Injector$$ExternalSyntheticLambda0 sysUiManager$Injector$$ExternalSyntheticLambda0) {
        super(context, i, i2, iSemBiometricSysUiCallback, bundle, str, displayStateManager, udfpsSensorWindow, udfpsInfo, sysUiManager$Injector$$ExternalSyntheticLambda0);
        this.mEnrollWindow = new UdfpsEnrollSensorWindow(context, this, udfpsInfo, displayStateManager);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public final int getUiType() {
        return 64;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void prepareWindows() {
        this.mEnrollWindow.initFromBaseWindow(this.mBaseSensorWindow);
        this.mEnrollWindow.invokeIconVisibilityCallback(0);
        this.mWindows.add(this.mEnrollWindow);
    }
}

package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.SysUiManager$Injector$$ExternalSyntheticLambda0;

/* loaded from: classes.dex */
public class UdfpsPrivilegedAuthClient extends UdfpsAuthClient {
    private final String mIconColor;
    private final String mIconContainerColor;
    private final boolean mIsRequireTouchBlock;
    private final boolean mUseKeyguardIcon;

    public UdfpsPrivilegedAuthClient(Context context, int i, int i2, ISemBiometricSysUiCallback iSemBiometricSysUiCallback, Bundle bundle, String str, DisplayStateManager displayStateManager, UdfpsSensorWindow udfpsSensorWindow, UdfpsInfo udfpsInfo, SysUiManager$Injector$$ExternalSyntheticLambda0 sysUiManager$Injector$$ExternalSyntheticLambda0, boolean z) {
        super(context, i, i2, iSemBiometricSysUiCallback, bundle, str, displayStateManager, udfpsSensorWindow, udfpsInfo, sysUiManager$Injector$$ExternalSyntheticLambda0);
        this.mIsRequireTouchBlock = z;
        boolean z2 = (bundle.getInt("sem_privileged_attr") & 32) != 0;
        this.mUseKeyguardIcon = z2;
        String string = bundle.getString("EXTRA_KEY_ICON_COLOR");
        this.mIconColor = string;
        String string2 = bundle.getString("EXTRA_KEY_ICON_CONTAINER_COLOR");
        this.mIconContainerColor = string2;
        Log.d("BSS_UdfpsPrivilegedAuthClient", "custom: " + z2 + ", " + string + ", " + string2);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient, com.samsung.android.biometrics.app.setting.SysUiClient
    public final int getUiType() {
        return 2;
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient, com.samsung.android.biometrics.app.setting.SysUiClient
    public void prepareWindows() {
        UdfpsPrivilegedAuthSensorWindow udfpsPrivilegedAuthSensorWindow = new UdfpsPrivilegedAuthSensorWindow(this.mContext, this, this.mSensorInfo, this.mDisplayStateManager, this.mIconColor, this.mIconContainerColor, this.mUseKeyguardIcon);
        udfpsPrivilegedAuthSensorWindow.initFromBaseWindow(this.mBaseSensorWindow);
        udfpsPrivilegedAuthSensorWindow.showSensorIcon();
        this.mWindows.add(udfpsPrivilegedAuthSensorWindow);
        if (this.mIsRequireTouchBlock) {
            Point point = new Point(udfpsPrivilegedAuthSensorWindow.mOriginPosX, udfpsPrivilegedAuthSensorWindow.mOriginPosY);
            this.mWindows.add(new UdfpsTouchBlockWidow(getContext(), this.mSensorInfo, point.x, point.y));
        }
    }
}

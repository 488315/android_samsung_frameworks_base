package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class UdfpsPrivilegedAuthClient extends UdfpsAuthClient {
    public final String mIconColor;
    public final String mIconContainerColor;
    public final boolean mIsRequireTouchBlock;
    public UdfpsPrivilegedAuthSensorWindow mUdfpsSensorWindow;
    public final boolean mUseKeyguardIcon;

    public UdfpsPrivilegedAuthClient(
            Context context,
            int i,
            int i2,
            ISemBiometricSysUiCallback iSemBiometricSysUiCallback,
            Bundle bundle,
            String str,
            DisplayStateManager displayStateManager,
            UdfpsSensorWindow udfpsSensorWindow,
            FingerprintSensorInfo fingerprintSensorInfo,
            Consumer consumer,
            boolean z) {
        super(
                context,
                i,
                i2,
                iSemBiometricSysUiCallback,
                bundle,
                str,
                displayStateManager,
                udfpsSensorWindow,
                fingerprintSensorInfo,
                consumer);
        this.mIsRequireTouchBlock = z;
        boolean z2 = (bundle.getInt("sem_privileged_attr") & 32) != 0;
        this.mUseKeyguardIcon = z2;
        String string = bundle.getString("EXTRA_KEY_ICON_COLOR");
        this.mIconColor = string;
        String string2 = bundle.getString("EXTRA_KEY_ICON_CONTAINER_COLOR");
        this.mIconContainerColor = string2;
        Log.d("BSS_UdfpsPrivilegedAuthClient", "custom: " + z2 + ", " + string + ", " + string2);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public void handleTspBlock(boolean z) {
        if (z) {
            this.mUdfpsSensorWindow.hideSensorIcon(0);
        } else if (this.mUdfpsSensorWindow.isVisible()) {
            this.mUdfpsSensorWindow.showSensorIcon();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public void prepareWindows() {
        UdfpsPrivilegedAuthSensorWindow udfpsPrivilegedAuthSensorWindow =
                new UdfpsPrivilegedAuthSensorWindow(
                        this.mContext,
                        this,
                        this.mSensorInfo,
                        this.mDisplayStateManager,
                        this.mIconColor,
                        this.mIconContainerColor,
                        this.mUseKeyguardIcon,
                        new Supplier() { // from class:
                                         // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsPrivilegedAuthClient$$ExternalSyntheticLambda0
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                UdfpsPrivilegedAuthClient.this.getClass();
                                return "ic_fingerprint_dark_theme.json";
                            }
                        });
        this.mUdfpsSensorWindow = udfpsPrivilegedAuthSensorWindow;
        udfpsPrivilegedAuthSensorWindow.initFromBaseWindow(this.mBaseSensorWindow);
        this.mUdfpsSensorWindow.showSensorIcon();
        this.mWindows.add(this.mUdfpsSensorWindow);
        if (this.mIsRequireTouchBlock) {
            UdfpsPrivilegedAuthSensorWindow udfpsPrivilegedAuthSensorWindow2 =
                    this.mUdfpsSensorWindow;
            udfpsPrivilegedAuthSensorWindow2.getClass();
            Point point =
                    new Point(
                            udfpsPrivilegedAuthSensorWindow2.mOriginPosX,
                            udfpsPrivilegedAuthSensorWindow2.mOriginPosY);
            this.mWindows.add(
                    new UdfpsTouchBlockWidow(this.mContext, this.mSensorInfo, point.x, point.y));
        }
    }
}

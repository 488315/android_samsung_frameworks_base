package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.InsetDrawable;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.R;

import java.util.function.Supplier;

public class UdfpsPrivilegedAuthSensorWindow extends UdfpsAuthSensorWindow {
    public final String mIconColor;
    public final String mIconContainerColor;
    public final Supplier mLockFingerIcon;
    public final boolean mUseKeyguardIcon;

    public UdfpsPrivilegedAuthSensorWindow(
            Context context,
            UdfpsWindowCallback udfpsWindowCallback,
            FingerprintSensorInfo fingerprintSensorInfo,
            DisplayStateManager displayStateManager,
            String str,
            String str2,
            boolean z,
            UdfpsPrivilegedAuthClient$$ExternalSyntheticLambda0
                    udfpsPrivilegedAuthClient$$ExternalSyntheticLambda0) {
        super(context, udfpsWindowCallback, fingerprintSensorInfo, displayStateManager);
        this.mIconColor = str;
        this.mIconContainerColor = str2;
        this.mUseKeyguardIcon = z;
        this.mLockFingerIcon = udfpsPrivilegedAuthClient$$ExternalSyntheticLambda0;
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow
    public final int getContainerColor() {
        String str = this.mIconContainerColor;
        if (TextUtils.isEmpty(str)) {
            return this.mContext
                    .getResources()
                    .getColor(R.color.biometric_prompt_dialog_color, null);
        }
        try {
            return Color.parseColor(str);
        } catch (IllegalArgumentException e) {
            Log.w("BSS_UdfpsPrivilegedAuthSensorWindow", "getContainerColor: " + e.getMessage());
            return 0;
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow
    public final int getIconColor() {
        String str = this.mIconColor;
        if (!TextUtils.isEmpty(str)) {
            try {
                return Color.parseColor(str);
            } catch (IllegalArgumentException e) {
                Log.w("BSS_UdfpsPrivilegedAuthSensorWindow", "getIconColor: " + e.getMessage());
            }
        }
        return this.mContext.getColor(R.color.biometric_prompt_title_text_color);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow
    public void initSensorLayout() {
        super.initSensorLayout();
        if (!this.mDisplayStateManager.isOnState() || UdfpsAuthSensorWindow.mIsKeyguard) {
            return;
        }
        showSensorIcon();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow
    public void setSensorIcon() {
        if (!this.mUseKeyguardIcon) {
            super.setSensorIcon();
            return;
        }
        int iconColor = getIconColor();
        if (Color.alpha(iconColor) == 0) {
            Log.i("BSS_UdfpsPrivilegedAuthSensorWindow", "SensorIcon: transparent color");
            return;
        }
        Supplier supplier = this.mLockFingerIcon;
        if (supplier == null) {
            return;
        }
        this.mAnimationView.setAnimation((String) supplier.get());
        this.mAnimationView.setProgress(1.0f);
        setLottieViewColorFilter(iconColor);
        InsetDrawable containerDrawable = getContainerDrawable();
        if (containerDrawable != null) {
            this.mFpIconContainer.setBackground(containerDrawable);
        }
    }
}

package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.R;

/* loaded from: classes.dex */
public class UdfpsPrivilegedAuthSensorWindow extends UdfpsAuthSensorWindow {
    private final String mIconColor;
    private final String mIconContainerColor;
    private final boolean mUseKeyguardIcon;

    public UdfpsPrivilegedAuthSensorWindow(Context context, UdfpsWindowCallback udfpsWindowCallback, UdfpsInfo udfpsInfo, DisplayStateManager displayStateManager, String str, String str2, boolean z) {
        super(context, udfpsWindowCallback, udfpsInfo, displayStateManager);
        this.mIconColor = str;
        this.mIconContainerColor = str2;
        this.mUseKeyguardIcon = z;
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow
    protected final int getContainerColor() {
        String str = this.mIconContainerColor;
        if (TextUtils.isEmpty(str)) {
            return getContext().getResources().getColor(R.color.biometric_prompt_dialog_color, null);
        }
        try {
            return Color.parseColor(str);
        } catch (IllegalArgumentException e) {
            Log.w("BSS_UdfpsPrivilegedAuthSensorWindow", "getContainerColor: " + e.getMessage());
            return 0;
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow
    protected final int getIconColor() {
        String str = this.mIconColor;
        if (!TextUtils.isEmpty(str)) {
            try {
                return Color.parseColor(str);
            } catch (IllegalArgumentException e) {
                Log.w("BSS_UdfpsPrivilegedAuthSensorWindow", "getIconColor: " + e.getMessage());
            }
        }
        return this.getContext().getColor(R.color.biometric_prompt_title_text_color);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow
    protected void initSensorLayout() {
        super.initSensorLayout();
        if (this.mDisplayStateManager.isOnState()) {
            showSensorIcon();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow
    public void setSensorIcon() {
        LottieOnCompositionLoadedListener lottieOnCompositionLoadedListener;
        if (!this.mUseKeyguardIcon) {
            super.setSensorIcon();
            return;
        }
        int iconColor = getIconColor();
        if (Color.alpha(iconColor) == 0) {
            Log.i("BSS_UdfpsPrivilegedAuthSensorWindow", "SensorIcon: transparent color");
            return;
        }
        this.mAnimationView.setAnimation("ic_fingerprint_dark_theme.json");
        this.mAnimationView.setProgress(1.0f);
        this.mLottieViewFilterColor = iconColor;
        LottieAnimationView lottieAnimationView = this.mAnimationView;
        if (lottieAnimationView != null && (lottieOnCompositionLoadedListener = this.mLottieCompositionLoadedListener) != null) {
            lottieAnimationView.addLottieOnCompositionLoadedListener(lottieOnCompositionLoadedListener);
        }
        setIconContainerColor();
    }
}

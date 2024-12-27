package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.vi.VisualEffectContainer;

import java.util.function.Function;
import java.util.function.Supplier;

public final class UdfpsKeyguardSensorWindow extends UdfpsPrivilegedAuthSensorWindow
        implements VisualEffectContainer.Callback {
    static final String FB_LAYER_BACKGROUND_COLOR = "#CC1e1e1e";
    String mCurrentAnimationType;
    public final Function mGetIconColorFilter;
    public TextView mHelpMessageOnAodView;
    public final Supplier mKeyguardFingerIcon;
    VisualEffectContainer mVisualEffectView;

    public UdfpsKeyguardSensorWindow(
            Context context,
            UdfpsWindowCallback udfpsWindowCallback,
            FingerprintSensorInfo fingerprintSensorInfo,
            DisplayStateManager displayStateManager,
            UdfpsKeyguardClient$$ExternalSyntheticLambda1
                    udfpsKeyguardClient$$ExternalSyntheticLambda1,
            UdfpsKeyguardClient$$ExternalSyntheticLambda2
                    udfpsKeyguardClient$$ExternalSyntheticLambda2) {
        super(
                context,
                udfpsWindowCallback,
                fingerprintSensorInfo,
                displayStateManager,
                null,
                null,
                false,
                null);
        UdfpsAuthSensorWindow.mIsKeyguard = true;
        this.mKeyguardFingerIcon = udfpsKeyguardClient$$ExternalSyntheticLambda1;
        this.mGetIconColorFilter = udfpsKeyguardClient$$ExternalSyntheticLambda2;
    }

    public final void calculateLocationOfHelpMessageView() {
        int i = this.mDisplayStateManager.mCurrentRotation;
        FingerprintSensorInfo fingerprintSensorInfo = this.mSensorInfo;
        int i2 = fingerprintSensorInfo.mSensorImageSize;
        int i3 = fingerprintSensorInfo.mSensorMarginBottom;
        int i4 = fingerprintSensorInfo.mSensorAreaHeight;
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) this.mHelpMessageOnAodView.getLayoutParams();
        DisplayMetrics displayMetrics = Utils.getDisplayMetrics(this.mContext);
        layoutParams.width = -2;
        layoutParams.height = -2;
        int i5 = (i4 / 2) + i3;
        int dipToPixel = i5 - (Utils.dipToPixel(this.mContext, 40.0d) / 2);
        int i6 = i2 / 2;
        int round = Math.round((float) (26.0d * displayMetrics.density)) + i5 + i6;
        layoutParams.gravity = 81;
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setCornerRadius(Utils.dipToPixel(this.mContext, 20.0d));
        gradientDrawable.setColor(Color.parseColor(FB_LAYER_BACKGROUND_COLOR));
        this.mHelpMessageOnAodView.setPadding(
                Utils.dipToPixel(this.mContext, 16.0d),
                Utils.dipToPixel(this.mContext, 10.0d),
                Utils.dipToPixel(this.mContext, 16.0d),
                Utils.dipToPixel(this.mContext, 10.0d));
        this.mHelpMessageOnAodView.setBackground(gradientDrawable);
        float f = i * 90;
        layoutParams.bottomMargin = round;
        layoutParams.setMarginStart(0);
        layoutParams.setMarginEnd(0);
        if (i == 0) {
            f = RecyclerView.DECELERATION_RATE;
        } else if (i == 1 || i == 3) {
            int round2 = Math.round((float) (24.0d * displayMetrics.density));
            if ((this.mHelpMessageOnAodView.getMeasuredWidth() / 2) + round2 > dipToPixel) {
                dipToPixel = (this.mHelpMessageOnAodView.getMeasuredWidth() / 2) + round2;
            }
            layoutParams.bottomMargin = dipToPixel;
            layoutParams.setMarginStart(i == 1 ? i6 + round2 : -(i6 + round2));
        }
        this.mHelpMessageOnAodView.setRotation(f);
        this.mHelpMessageOnAodView.setLayoutParams(layoutParams);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsPrivilegedAuthSensorWindow, com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow
    public final void initSensorLayout() {
        super.initSensorLayout();
        VisualEffectContainer visualEffectContainer =
                (VisualEffectContainer)
                        this.mFingerprintLayout.findViewById(R.id.sem_fingerprint_vi_effect);
        this.mVisualEffectView = visualEffectContainer;
        visualEffectContainer.init(this.mSensorInfo, this);
        this.mHelpMessageOnAodView =
                (TextView) this.mFingerprintLayout.findViewById(R.id.sem_fingerprint_fail_textview);
        calculateLocationOfHelpMessageView();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow,
              // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        calculateLocationOfHelpMessageView();
        this.mVisualEffectView.stop();
        this.mVisualEffectView.init(this.mSensorInfo, this);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow,
              // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void onRotationInfoChanged(int i) {
        super.onRotationInfoChanged(i);
        calculateLocationOfHelpMessageView();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthSensorWindow,
              // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow,
              // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void removeView() {
        this.mHelpMessageOnAodView.setVisibility(8);
        this.mVisualEffectView.stop();
        super.removeView();
    }

    public final void setAnimationTypeIfNeeded() {
        String str = (String) this.mKeyguardFingerIcon.get();
        String str2 = this.mCurrentAnimationType;
        if (str2 == null || !str.contentEquals(str2)) {
            this.mCurrentAnimationType = str;
            this.mAnimationView.setAnimation(str);
            this.mAnimationView.setProgress(1.0f);
            Log.i(
                    "BSS_UdfpsKeyguardSensorWindow",
                    "animationType : "
                            .concat(
                                    this.mCurrentAnimationType.contains("dark_theme")
                                            ? "0"
                                            : this.mCurrentAnimationType.contains("light_theme")
                                                    ? "1"
                                                    : "2"));
        }
        if (this.mCurrentAnimationType.contains("aod")) {
            setLottieViewColorFilter(0);
            return;
        }
        int intValue =
                ((Integer) this.mGetIconColorFilter.apply(this.mCurrentAnimationType)).intValue();
        if (Color.alpha(intValue) == 0) {
            return;
        }
        setLottieViewColorFilter(intValue);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsPrivilegedAuthSensorWindow, com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow
    public final void setSensorIcon() {
        this.mFpIconContainer.setBackground(null);
        this.mAnimationView.setImageDrawable(null);
        setAnimationTypeIfNeeded();
    }

    public final void showHelpMessageOnAod(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mHelpMessageOnAodView.setTextSize(1, 14.0f);
        this.mHelpMessageOnAodView.setText(str);
        this.mHelpMessageOnAodView.measure(0, 0);
        calculateLocationOfHelpMessageView();
        this.mHelpMessageOnAodView.setVisibility(0);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow
    public final void showSensorIcon() {
        setAnimationTypeIfNeeded();
        super.showSensorIcon();
    }

    public final void showSensorIconWithAnimation() {
        showSensorIcon();
        this.mAnimationView.pauseAnimation();
        this.mAnimationView.playAnimation();
    }
}

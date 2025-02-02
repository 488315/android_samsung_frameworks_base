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
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.p000vi.VisualEffectContainer;
import java.util.function.IntSupplier;

/* loaded from: classes.dex */
public final class UdfpsKeyguardSensorWindow extends UdfpsPrivilegedAuthSensorWindow implements VisualEffectContainer.Callback {

    @VisibleForTesting
    static final String FB_LAYER_BACKGROUND_COLOR = "#CC1e1e1e";

    @VisibleForTesting
    String mCurrentAnimationType;
    private final IntSupplier mGetWallpaperFontColor;
    private TextView mHelpMessageOnAodView;

    @VisibleForTesting
    VisualEffectContainer mVisualEffectView;

    public UdfpsKeyguardSensorWindow(Context context, UdfpsWindowCallback udfpsWindowCallback, UdfpsInfo udfpsInfo, DisplayStateManager displayStateManager, UdfpsKeyguardClient$$ExternalSyntheticLambda2 udfpsKeyguardClient$$ExternalSyntheticLambda2) {
        super(context, udfpsWindowCallback, udfpsInfo, displayStateManager, null, null, false);
        this.mGetWallpaperFontColor = udfpsKeyguardClient$$ExternalSyntheticLambda2;
    }

    private void calculateLocationOfHelpMessageView() {
        int currentRotation = this.mDisplayStateManager.getCurrentRotation();
        UdfpsInfo udfpsInfo = this.mSensorInfo;
        int imageSize = udfpsInfo.getImageSize();
        int marginBottom = udfpsInfo.getMarginBottom();
        int areaHeight = udfpsInfo.getAreaHeight();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mHelpMessageOnAodView.getLayoutParams();
        DisplayMetrics displayMetrics = Utils.getDisplayMetrics(getContext());
        layoutParams.width = -2;
        layoutParams.height = -2;
        int i = (areaHeight / 2) + marginBottom;
        int dipToPixel = i - (Utils.dipToPixel(getContext(), 40.0d) / 2);
        int i2 = imageSize / 2;
        int round = Math.round((float) (26.0d * displayMetrics.density)) + i + i2;
        layoutParams.gravity = 81;
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setCornerRadius(Utils.dipToPixel(getContext(), 20.0d));
        gradientDrawable.setColor(Color.parseColor(FB_LAYER_BACKGROUND_COLOR));
        this.mHelpMessageOnAodView.setPadding(Utils.dipToPixel(getContext(), 16.0d), 0, Utils.dipToPixel(getContext(), 16.0d), 0);
        this.mHelpMessageOnAodView.setBackground(gradientDrawable);
        float f = currentRotation * 90;
        layoutParams.bottomMargin = round;
        layoutParams.setMarginStart(0);
        layoutParams.setMarginEnd(0);
        if (currentRotation == 0) {
            f = 0.0f;
        } else if (currentRotation == 1 || currentRotation == 3) {
            int round2 = Math.round((float) (24.0d * displayMetrics.density));
            if ((this.mHelpMessageOnAodView.getMeasuredWidth() / 2) + round2 > dipToPixel) {
                dipToPixel = (this.mHelpMessageOnAodView.getMeasuredWidth() / 2) + round2;
            }
            layoutParams.bottomMargin = dipToPixel;
            layoutParams.setMarginStart(currentRotation == 1 ? i2 + round2 : -(i2 + round2));
        }
        this.mHelpMessageOnAodView.setRotation(f);
        this.mHelpMessageOnAodView.setLayoutParams(layoutParams);
    }

    private void setAnimationTypeIfNeeded() {
        LottieOnCompositionLoadedListener lottieOnCompositionLoadedListener;
        LottieOnCompositionLoadedListener lottieOnCompositionLoadedListener2;
        String str = this.mDisplayStateManager.isOnState() ? this.mGetWallpaperFontColor.getAsInt() == 0 ? "ic_fingerprint_dark_theme.json" : "ic_fingerprint_light_theme.json" : "ic_fingerprint_aod.json";
        String str2 = this.mCurrentAnimationType;
        if (str2 == null || !str.contentEquals(str2)) {
            this.mCurrentAnimationType = str;
            this.mAnimationView.setAnimation(str);
            this.mAnimationView.setProgress(1.0f);
            Log.i("BSS_UdfpsKeyguardSensorWindow", "animationType : ".concat(this.mCurrentAnimationType.contentEquals("ic_fingerprint_dark_theme.json") ? "0" : this.mCurrentAnimationType.contentEquals("ic_fingerprint_light_theme.json") ? "1" : "2"));
        }
        if (this.mCurrentAnimationType.equals("ic_fingerprint_aod.json")) {
            this.mLottieViewFilterColor = 0;
            LottieAnimationView lottieAnimationView = this.mAnimationView;
            if (lottieAnimationView == null || (lottieOnCompositionLoadedListener2 = this.mLottieCompositionLoadedListener) == null) {
                return;
            }
            lottieAnimationView.addLottieOnCompositionLoadedListener(lottieOnCompositionLoadedListener2);
            return;
        }
        int color = this.mCurrentAnimationType.equals("ic_fingerprint_dark_theme.json") ? getContext().getColor(R.color.fingerprint_icon_lock_tint) : getContext().getColor(R.color.fingerprint_icon_lock_tint_dark);
        if (Color.alpha(color) == 0) {
            return;
        }
        this.mLottieViewFilterColor = color;
        LottieAnimationView lottieAnimationView2 = this.mAnimationView;
        if (lottieAnimationView2 == null || (lottieOnCompositionLoadedListener = this.mLottieCompositionLoadedListener) == null) {
            return;
        }
        lottieAnimationView2.addLottieOnCompositionLoadedListener(lottieOnCompositionLoadedListener);
    }

    public final void hideHelpMessageOnAod() {
        this.mHelpMessageOnAodView.setVisibility(4);
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsPrivilegedAuthSensorWindow, com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow
    protected final void initSensorLayout() {
        super.initSensorLayout();
        VisualEffectContainer visualEffectContainer = (VisualEffectContainer) this.mFingerprintLayout.findViewById(R.id.sem_fingerprint_vi_effect);
        this.mVisualEffectView = visualEffectContainer;
        visualEffectContainer.init(this.mSensorInfo, this);
        this.mHelpMessageOnAodView = (TextView) this.mFingerprintLayout.findViewById(R.id.sem_fingerprint_fail_textview);
        calculateLocationOfHelpMessageView();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow, com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        calculateLocationOfHelpMessageView();
        this.mVisualEffectView.updateDisplayChanged();
    }

    public final void onEffectFinished() {
        UdfpsWindowCallback udfpsWindowCallback = this.mCallback;
        if (udfpsWindowCallback != null) {
            udfpsWindowCallback.onVisualEffectFinished();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow, com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void onRotationInfoChanged(int i) {
        super.onRotationInfoChanged(i);
        calculateLocationOfHelpMessageView();
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthSensorWindow, com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow, com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void removeView() {
        this.mHelpMessageOnAodView.setVisibility(8);
        this.mVisualEffectView.stop();
        super.removeView();
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

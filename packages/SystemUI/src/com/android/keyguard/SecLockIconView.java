package com.android.keyguard;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.SimpleColorFilter;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIImageView;

public class SecLockIconView extends LockIconView {
    public ObjectAnimator mAnimTranslationX;
    public final SparseArray mDrawableCache;
    public boolean mIsLockStarEnabled;
    public boolean mIsOneHandModeEnabled;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public LottieAnimationView mLottieIcon;
    public SpringAnimation mScaleXAnim;
    public SpringAnimation mScaleYAnim;
    public SystemUIImageView mSecLockIcon;
    public View mlockIcon;

    public SecLockIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDrawableCache = new SparseArray();
        this.mKeyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
    }

    public final Drawable getIcon(int i) {
        if (!this.mDrawableCache.contains(i)) {
            this.mDrawableCache.put(i, getResources().getDrawable(i, getContext().getTheme()));
        }
        return (Drawable) this.mDrawableCache.get(i);
    }

    public final void initBiometricErrorIndicationAnimationValue(Object obj, boolean z) {
        if (obj == null) {
            return;
        }
        if (obj instanceof LottieAnimationView) {
            LottieAnimationView lottieAnimationView = (LottieAnimationView) obj;
            this.mlockIcon = lottieAnimationView;
            this.mLottieIcon = lottieAnimationView;
        } else {
            this.mlockIcon = (SystemUIImageView) obj;
        }
        if ((!LsRune.SECURITY_PUNCH_HOLE_FACE_VI || this.mIsOneHandModeEnabled) && !this.mIsLockStarEnabled) {
            this.mlockIcon.setAlpha(1.0f);
        }
        SpringAnimation springAnimation = this.mScaleXAnim;
        if (springAnimation != null && springAnimation.mRunning) {
            springAnimation.cancel();
        }
        SpringAnimation springAnimation2 = this.mScaleYAnim;
        if (springAnimation2 != null && springAnimation2.mRunning) {
            springAnimation2.cancel();
        }
        ObjectAnimator objectAnimator = this.mAnimTranslationX;
        if (objectAnimator != null && objectAnimator.isRunning()) {
            this.mAnimTranslationX.cancel();
        }
        if (!z) {
            this.mlockIcon.setScaleX(1.0f);
            this.mlockIcon.setScaleY(1.0f);
            this.mlockIcon.setTranslationX(0.0f);
            return;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mlockIcon, (Property<View, Float>) View.TRANSLATION_X, 0.0f);
        this.mAnimTranslationX = ofFloat;
        KeyguardSecPatternViewController$$ExternalSyntheticOutline0.m(0.4f, 0.5f, 0.0f, 1.0f, ofFloat);
        this.mAnimTranslationX.setDuration(400L);
        this.mAnimTranslationX.start();
        SpringForce springForce = new SpringForce(1.0f);
        springForce.setStiffness(150.0f);
        springForce.setDampingRatio(0.48f);
        SpringAnimation springAnimation3 = new SpringAnimation(this.mlockIcon, DynamicAnimation.SCALE_X);
        springAnimation3.mSpring = springForce;
        this.mScaleXAnim = springAnimation3;
        SpringAnimation springAnimation4 = new SpringAnimation(this.mlockIcon, DynamicAnimation.SCALE_Y);
        springAnimation4.mSpring = springForce;
        this.mScaleYAnim = springAnimation4;
        this.mScaleXAnim.start();
        this.mScaleYAnim.start();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mSecLockIcon = (SystemUIImageView) findViewById(R.id.sec_lock_icon);
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
        SystemUIImageView systemUIImageView = this.mSecLockIcon;
        if (systemUIImageView != null) {
            systemUIImageView.setVisibility(i);
        }
    }

    public final void showBiometricErrorAnimation(Object obj, float f, VibrationUtil vibrationUtil) {
        if (obj == null) {
            return;
        }
        initBiometricErrorIndicationAnimationValue(obj, false);
        int x = (int) ((this.mlockIcon.getX() + getResources().getDimensionPixelSize(R.dimen.kg_biometric_view_text_margin)) - f);
        int displayWidth = (DeviceState.getDisplayWidth(getContext()) - getResources().getDimensionPixelSize(R.dimen.kg_biometric_view_min_height)) / 2;
        if (x > displayWidth) {
            x = displayWidth;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mlockIcon, (Property<View, Float>) View.TRANSLATION_X, x * (-1));
        this.mAnimTranslationX = ofFloat;
        KeyguardSecPatternViewController$$ExternalSyntheticOutline0.m(0.4f, 0.5f, 0.0f, 1.0f, ofFloat);
        this.mAnimTranslationX.setDuration(400L);
        this.mAnimTranslationX.start();
        SpringForce springForce = new SpringForce(0.72f);
        springForce.setStiffness(150.0f);
        springForce.setDampingRatio(0.48f);
        SpringAnimation springAnimation = new SpringAnimation(this.mlockIcon, DynamicAnimation.SCALE_X);
        springAnimation.mSpring = springForce;
        this.mScaleXAnim = springAnimation;
        SpringAnimation springAnimation2 = new SpringAnimation(this.mlockIcon, DynamicAnimation.SCALE_Y);
        springAnimation2.mSpring = springForce;
        this.mScaleYAnim = springAnimation2;
        this.mScaleXAnim.start();
        this.mScaleYAnim.start();
        if (this.mLottieIcon == null) {
            if (vibrationUtil != null) {
                vibrationUtil.playVibration(114);
                return;
            }
            return;
        }
        boolean isWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("background");
        this.mLottieIcon.setAnimation("unlock_fail_icon.json");
        this.mLottieIcon.addValueCallback(new KeyPath("**"), (KeyPath) LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(getResources().getColor(isWhiteKeyguardWallpaper ? R.color.biometric_toast_text_color : R.color.origin_keyguard_message_area_text_color, null))));
        this.mLottieIcon.playAnimation();
        if (vibrationUtil != null) {
            vibrationUtil.playVibration(114);
        }
        this.mLottieIcon = null;
    }

    public final void updateLockIconViewLayoutParams(int i) {
        FrameLayout.LayoutParams layoutParams;
        int dimensionPixelSize;
        int i2;
        SystemUIImageView systemUIImageView = this.mSecLockIcon;
        if (systemUIImageView == null || (layoutParams = (FrameLayout.LayoutParams) systemUIImageView.getLayoutParams()) == null) {
            return;
        }
        if (!this.mKeyguardUpdateMonitor.getUserHasTrust(i) || this.mKeyguardUpdateMonitor.isForcedLock()) {
            dimensionPixelSize = getResources().getDimensionPixelSize((DeviceType.isTablet() || this.mKeyguardUpdateMonitor.isDualDisplayPolicyAllowed()) ? R.dimen.kg_biometric_view_height : R.dimen.kg_biometric_view_min_height);
            i2 = 0;
        } else {
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_extend_lock_view_height);
            i2 = getResources().getDimensionPixelSize(R.dimen.kg_extend_lock_view_padding);
        }
        layoutParams.width = dimensionPixelSize;
        layoutParams.height = dimensionPixelSize;
        this.mSecLockIcon.setPadding(i2, i2, i2, i2);
        this.mSecLockIcon.setLayoutParams(layoutParams);
    }

    public final void updateScanningFaceAnimation(SystemUIImageView systemUIImageView) {
        if (systemUIImageView == null) {
            return;
        }
        if ((!LsRune.SECURITY_PUNCH_HOLE_FACE_VI || this.mIsOneHandModeEnabled) && this.mKeyguardUpdateMonitor.isFaceDetectionRunning()) {
            systemUIImageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lock_icon_fade_in_out));
        } else {
            systemUIImageView.clearAnimation();
        }
    }
}

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
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.util.DeviceType;
import com.android.systemui.widget.SystemUIImageView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SecLockIconView extends LockIconView {
    public ObjectAnimator mAnimTranslationX;
    public final SparseArray mDrawableCache;
    public boolean mIsLockStarEnabled;
    public boolean mIsOneHandModeEnabled;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public SpringAnimation mScaleXAnim;
    public SpringAnimation mScaleYAnim;
    public SystemUIImageView mSecLockIcon;

    public SecLockIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDrawableCache = new SparseArray();
        this.mKeyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
    }

    public final Drawable getIcon(int i) {
        if (!this.mDrawableCache.contains(i)) {
            this.mDrawableCache.put(i, getResources().getDrawable(i, getContext().getTheme()));
        }
        return (Drawable) this.mDrawableCache.get(i);
    }

    public final void initBiometricErrorIndicationAnimationValue(SystemUIImageView systemUIImageView, boolean z) {
        if (systemUIImageView == null) {
            return;
        }
        if ((!LsRune.SECURITY_PUNCH_HOLE_FACE_VI || this.mIsOneHandModeEnabled) && !this.mIsLockStarEnabled) {
            systemUIImageView.setAlpha(1.0f);
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
            systemUIImageView.setScaleX(1.0f);
            systemUIImageView.setScaleY(1.0f);
            systemUIImageView.setTranslationX(0.0f);
            return;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(systemUIImageView, (Property<SystemUIImageView, Float>) View.TRANSLATION_X, 0.0f);
        this.mAnimTranslationX = ofFloat;
        SecLockIconView$$ExternalSyntheticOutline0.m82m(0.4f, 0.5f, 0.0f, 1.0f, ofFloat);
        this.mAnimTranslationX.setDuration(400L);
        this.mAnimTranslationX.start();
        SpringForce springForce = new SpringForce(1.0f);
        springForce.setStiffness(150.0f);
        springForce.setDampingRatio(0.48f);
        SpringAnimation springAnimation3 = new SpringAnimation(systemUIImageView, DynamicAnimation.SCALE_X);
        springAnimation3.mSpring = springForce;
        this.mScaleXAnim = springAnimation3;
        SpringAnimation springAnimation4 = new SpringAnimation(systemUIImageView, DynamicAnimation.SCALE_Y);
        springAnimation4.mSpring = springForce;
        this.mScaleYAnim = springAnimation4;
        this.mScaleXAnim.start();
        this.mScaleYAnim.start();
    }

    @Override // com.android.keyguard.LockIconView, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mSecLockIcon = (SystemUIImageView) findViewById(R.id.sec_lock_icon);
    }

    @Override // com.android.keyguard.LockIconView
    public final void setImageDrawable(Drawable drawable) {
        SystemUIImageView systemUIImageView = this.mSecLockIcon;
        if (systemUIImageView != null) {
            systemUIImageView.setImageDrawable(drawable);
        }
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
        SystemUIImageView systemUIImageView = this.mSecLockIcon;
        if (systemUIImageView != null) {
            systemUIImageView.setVisibility(i);
        }
    }

    public final void updateLockIconViewLayoutParams() {
        FrameLayout.LayoutParams layoutParams;
        int dimensionPixelSize;
        int i;
        SystemUIImageView systemUIImageView = this.mSecLockIcon;
        if (systemUIImageView == null || (layoutParams = (FrameLayout.LayoutParams) systemUIImageView.getLayoutParams()) == null) {
            return;
        }
        if (!this.mKeyguardUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser()) || this.mKeyguardUpdateMonitor.isForcedLock()) {
            dimensionPixelSize = getResources().getDimensionPixelSize((DeviceType.isTablet() || this.mKeyguardUpdateMonitor.isDualDisplayPolicyAllowed()) ? R.dimen.kg_biometric_view_height : R.dimen.kg_biometric_view_min_height);
            i = 0;
        } else {
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_extend_lock_view_height);
            i = getResources().getDimensionPixelSize(R.dimen.kg_extend_lock_view_padding);
        }
        layoutParams.width = dimensionPixelSize;
        layoutParams.height = dimensionPixelSize;
        this.mSecLockIcon.setPadding(i, i, i, i);
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

package com.android.systemui.biometrics;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.systemui.R;
import com.android.systemui.biometrics.UdfpsKeyguardViewLegacy;
import com.android.systemui.doze.util.BurnInHelperKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class UdfpsKeyguardViewLegacy extends UdfpsAnimationView {
    public int mAlpha;
    public int mAnimationType;
    public LottieAnimationView mAodFp;
    public AnimatorSet mBackgroundInAnimator;
    public ImageView mBgProtection;
    public final UdfpsFpDrawable mFingerprintDrawable;
    public boolean mFullyInflated;
    public float mInterpolatedDarkAmount;
    public final AnonymousClass2 mLayoutInflaterFinishListener;
    public LottieAnimationView mLockScreenFp;
    public final int mMaxBurnInOffsetX;
    public final int mMaxBurnInOffsetY;
    public float mScaleFactor;
    public final Rect mSensorBounds;
    public int mTextColorPrimary;
    public boolean mUdfpsRequested;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.biometrics.UdfpsKeyguardViewLegacy$2, reason: invalid class name */
    public final class AnonymousClass2 implements AsyncLayoutInflater.OnInflateFinishedListener {
        public AnonymousClass2() {
        }

        @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
        public final void onInflateFinished(View view, ViewGroup viewGroup) {
            UdfpsKeyguardViewLegacy udfpsKeyguardViewLegacy = UdfpsKeyguardViewLegacy.this;
            udfpsKeyguardViewLegacy.mFullyInflated = true;
            udfpsKeyguardViewLegacy.mAodFp = (LottieAnimationView) view.findViewById(R.id.udfps_aod_fp);
            udfpsKeyguardViewLegacy.mLockScreenFp = (LottieAnimationView) view.findViewById(R.id.udfps_lockscreen_fp);
            udfpsKeyguardViewLegacy.mBgProtection = (ImageView) view.findViewById(R.id.udfps_keyguard_fp_bg);
            udfpsKeyguardViewLegacy.updatePadding();
            udfpsKeyguardViewLegacy.updateColor();
            udfpsKeyguardViewLegacy.updateAlpha();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            layoutParams.width = udfpsKeyguardViewLegacy.mSensorBounds.width();
            layoutParams.height = udfpsKeyguardViewLegacy.mSensorBounds.height();
            RectF rectF = new RectF(udfpsKeyguardViewLegacy.mSensorBounds);
            int[] locationOnScreen = udfpsKeyguardViewLegacy.getLocationOnScreen();
            float f = rectF.left;
            float f2 = locationOnScreen[0];
            float f3 = rectF.top;
            float f4 = locationOnScreen[1];
            RectF rectF2 = new RectF(f - f2, f3 - f4, rectF.right - f2, rectF.bottom - f4);
            layoutParams.setMarginsRelative((int) rectF2.left, (int) rectF2.top, (int) rectF2.right, (int) rectF2.bottom);
            viewGroup.addView(view, layoutParams);
            udfpsKeyguardViewLegacy.mLockScreenFp.addValueCallback(new KeyPath("**"), (KeyPath) LottieProperty.COLOR_FILTER, new SimpleLottieValueCallback() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewLegacy$2$$ExternalSyntheticLambda0
                @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
                public final Object getValue() {
                    UdfpsKeyguardViewLegacy.AnonymousClass2 anonymousClass2 = UdfpsKeyguardViewLegacy.AnonymousClass2.this;
                    anonymousClass2.getClass();
                    return new PorterDuffColorFilter(UdfpsKeyguardViewLegacy.this.mTextColorPrimary, PorterDuff.Mode.SRC_ATOP);
                }
            });
            throw null;
        }
    }

    public UdfpsKeyguardViewLegacy(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBackgroundInAnimator = new AnimatorSet();
        this.mScaleFactor = 1.0f;
        this.mSensorBounds = new Rect();
        this.mAnimationType = 0;
        new AnonymousClass2();
        this.mFingerprintDrawable = new UdfpsFpDrawable(context);
        this.mMaxBurnInOffsetX = context.getResources().getDimensionPixelSize(R.dimen.udfps_burn_in_offset_x);
        this.mMaxBurnInOffsetY = context.getResources().getDimensionPixelSize(R.dimen.udfps_burn_in_offset_y);
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public final int calculateAlpha() {
        if (this.mPauseAuth) {
            return 0;
        }
        return this.mAlpha;
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public final UdfpsFpDrawable getDrawable() {
        return this.mFingerprintDrawable;
    }

    public final void onDozeAmountChanged(float f, int i) {
        this.mAnimationType = i;
        this.mInterpolatedDarkAmount = f;
        updateAlpha();
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public final int updateAlpha() {
        boolean z;
        int updateAlpha = super.updateAlpha();
        if (this.mFullyInflated) {
            float f = this.mAnimationType == 2 ? 1.0f : this.mInterpolatedDarkAmount;
            float lerp = MathUtils.lerp(0.0f, BurnInHelperKt.getBurnInOffset(this.mMaxBurnInOffsetX * 2, true) - this.mMaxBurnInOffsetX, f);
            float lerp2 = MathUtils.lerp(0.0f, BurnInHelperKt.getBurnInOffset(this.mMaxBurnInOffsetY * 2, false) - this.mMaxBurnInOffsetY, f);
            float lerp3 = MathUtils.lerp(0.0f, BurnInHelperKt.getBurnInProgressOffset(), f);
            if (this.mAnimationType == 1 && !this.mPauseAuth) {
                this.mLockScreenFp.setTranslationX(lerp);
                this.mLockScreenFp.setTranslationY(lerp2);
                this.mBgProtection.setAlpha(1.0f - this.mInterpolatedDarkAmount);
                this.mLockScreenFp.setAlpha(1.0f - this.mInterpolatedDarkAmount);
            } else if (f == 0.0f) {
                this.mLockScreenFp.setTranslationX(0.0f);
                this.mLockScreenFp.setTranslationY(0.0f);
                this.mBgProtection.setAlpha(this.mAlpha / 255.0f);
                this.mLockScreenFp.setAlpha(this.mAlpha / 255.0f);
            } else {
                this.mBgProtection.setAlpha(0.0f);
                this.mLockScreenFp.setAlpha(0.0f);
            }
            this.mLockScreenFp.setProgress(1.0f - this.mInterpolatedDarkAmount);
            this.mAodFp.setTranslationX(lerp);
            this.mAodFp.setTranslationY(lerp2);
            this.mAodFp.setProgress(lerp3);
            this.mAodFp.setAlpha(this.mInterpolatedDarkAmount);
            int i = this.mAnimationType;
            if (i == 1) {
                float f2 = this.mInterpolatedDarkAmount;
                if (f2 == 0.0f || f2 == 1.0f) {
                    z = true;
                    boolean z2 = i != 2 && this.mInterpolatedDarkAmount == 1.0f;
                    if (!z || z2) {
                        this.mAnimationType = 0;
                    }
                }
            }
            z = false;
            if (i != 2) {
            }
            if (!z) {
            }
            this.mAnimationType = 0;
        }
        return updateAlpha;
    }

    public final void updateColor() {
        if (this.mFullyInflated) {
            this.mTextColorPrimary = com.android.settingslib.Utils.getColorAttrDefaultColor(((FrameLayout) this).mContext, android.R.^attr-private.materialColorOnTertiary, 0);
            this.mBgProtection.setImageTintList(ColorStateList.valueOf(com.android.settingslib.Utils.getColorAttrDefaultColor(getContext(), android.R.^attr-private.materialColorSurfaceContainerLowest, 0)));
            this.mLockScreenFp.invalidate();
        }
    }

    public final void updatePadding() {
        if (this.mLockScreenFp == null || this.mAodFp == null) {
            return;
        }
        int dimensionPixelSize = (int) (getResources().getDimensionPixelSize(R.dimen.lock_icon_padding) * this.mScaleFactor);
        this.mLockScreenFp.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        this.mAodFp.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
    }
}

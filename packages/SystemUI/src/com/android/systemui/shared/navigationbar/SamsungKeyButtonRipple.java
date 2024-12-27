package com.android.systemui.shared.navigationbar;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CanvasProperty;
import android.graphics.Paint;
import android.util.Log;
import android.util.Property;
import android.view.RenderNodeAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.systemui.navigationbar.BasicRuneWrapper;
import com.android.systemui.shared.navigationbar.KeyButtonRipple;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SamsungKeyButtonRipple extends KeyButtonRipple {
    public static final Interpolator FADE_IN_INTERPOLATOR;
    public static final Interpolator FADE_OUT_INTERPOLATOR;
    public static final Interpolator SCALE_OUT_INTERPOLATOR;
    public boolean isHover;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        FADE_IN_INTERPOLATOR = new PathInterpolator(0.0f, 0.0f, 1.0f, 1.0f);
        FADE_OUT_INTERPOLATOR = new PathInterpolator(0.17f, 0.17f, 0.67f, 1.0f);
        SCALE_OUT_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
    }

    public /* synthetic */ SamsungKeyButtonRipple(Context context, View view, int i, float f, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, view, i, (i2 & 8) != 0 ? 1.35f : f);
    }

    @Override // com.android.systemui.shared.navigationbar.KeyButtonRipple
    public final void drawSoftware(Canvas canvas) {
        if (this.mGlowAlpha > 0.0f) {
            Paint ripplePaint = getRipplePaint();
            if (this.isHover) {
                ripplePaint.setAlpha((int) (this.mGlowAlpha * 255.0f * 0.6f));
            } else {
                ripplePaint.setAlpha((int) (this.mGlowAlpha * 255.0f));
            }
            float width = getBounds().width();
            float height = getBounds().height();
            boolean z = width > height;
            float rippleSize = getRippleSize() * this.mGlowScale * 0.5f;
            float f = width * 0.5f;
            float f2 = height * 0.5f;
            float f3 = z ? rippleSize : f;
            if (z) {
                rippleSize = f2;
            }
            float f4 = z ? f2 : f;
            if (this.mType == KeyButtonRipple.Type.ROUNDED_RECT) {
                canvas.drawRoundRect(f - f3, f2 - rippleSize, f3 + f, f2 + rippleSize, f4, f4, ripplePaint);
                return;
            }
            canvas.save();
            canvas.translate(f, f2);
            float min = (float) Math.min(f3, rippleSize);
            float f5 = -min;
            canvas.drawOval(f5, f5, min, min, ripplePaint);
            canvas.restore();
        }
    }

    @Override // com.android.systemui.shared.navigationbar.KeyButtonRipple
    public final void enterHardware() {
        enterHardwareAnim();
    }

    public final void enterHardwareAnim() {
        endAnimations("enterHardware", true);
        this.mVisible = true;
        this.mDrawingHardwareGlow = true;
        float f = 2;
        float extendSize = (getExtendSize() / 2) - ((this.GLOW_MAX_SCALE_FACTOR * getRippleSize()) / f);
        float rippleSize = ((this.GLOW_MAX_SCALE_FACTOR * getRippleSize()) / f) + (getExtendSize() / 2);
        float f2 = this.isHover ? 153.0f : 255.0f;
        CanvasProperty createFloat = CanvasProperty.createFloat(extendSize);
        if (isHorizontal()) {
            this.mLeftProp = createFloat;
        } else {
            this.mTopProp = createFloat;
        }
        RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(isHorizontal() ? this.mLeftProp : this.mTopProp, extendSize);
        renderNodeAnimator.setDuration(100L);
        Interpolator interpolator = FADE_IN_INTERPOLATOR;
        renderNodeAnimator.setInterpolator(interpolator);
        if (!this.isHover) {
            renderNodeAnimator.addListener(this.mAnimatorListener);
        }
        renderNodeAnimator.setTarget(this.mTargetView);
        CanvasProperty createFloat2 = CanvasProperty.createFloat(rippleSize);
        if (isHorizontal()) {
            this.mRightProp = createFloat2;
        } else {
            this.mBottomProp = createFloat2;
        }
        RenderNodeAnimator renderNodeAnimator2 = new RenderNodeAnimator(isHorizontal() ? this.mRightProp : this.mBottomProp, rippleSize);
        renderNodeAnimator2.setDuration(100L);
        renderNodeAnimator2.setInterpolator(interpolator);
        if (!this.isHover) {
            renderNodeAnimator2.addListener(this.mAnimatorListener);
        }
        renderNodeAnimator2.addListener(this.mEnterHwTraceAnimator);
        renderNodeAnimator2.setTarget(this.mTargetView);
        if (isHorizontal()) {
            this.mTopProp = CanvasProperty.createFloat(0.0f);
            this.mBottomProp = CanvasProperty.createFloat(getBounds().height());
            this.mRxProp = CanvasProperty.createFloat(getBounds().height() / 2);
            this.mRyProp = CanvasProperty.createFloat(getBounds().height() / 2);
        } else {
            this.mLeftProp = CanvasProperty.createFloat(0.0f);
            this.mRightProp = CanvasProperty.createFloat(getBounds().width());
            this.mRxProp = CanvasProperty.createFloat(getBounds().width() / 2);
            this.mRyProp = CanvasProperty.createFloat(getBounds().width() / 2);
        }
        this.mGlowScale = this.GLOW_MAX_SCALE_FACTOR;
        this.mGlowAlpha = getMaxGlowAlpha();
        Paint ripplePaint = getRipplePaint();
        this.mRipplePaint = ripplePaint;
        ripplePaint.setAlpha((int) (this.mGlowAlpha * f2));
        this.mPaintProp = CanvasProperty.createPaint(this.mRipplePaint);
        renderNodeAnimator.start();
        renderNodeAnimator2.start();
        this.mRunningAnimations.add(renderNodeAnimator);
        this.mRunningAnimations.add(renderNodeAnimator2);
        if (!this.isHover) {
            enterScaleAnimation();
        }
        invalidateSelf();
    }

    public final void enterScaleAnimation() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mTargetView, (Property<View, Float>) View.SCALE_X, 1.0f, 0.96f);
        Interpolator interpolator = FADE_IN_INTERPOLATOR;
        ofFloat.setInterpolator(interpolator);
        ofFloat.setDuration(100L);
        ofFloat.addListener(this.mAnimatorListener);
        ofFloat.start();
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mTargetView, (Property<View, Float>) View.SCALE_Y, 1.0f, 0.96f);
        ofFloat2.setInterpolator(interpolator);
        ofFloat2.setDuration(100L);
        ofFloat2.addListener(this.mAnimatorListener);
        ofFloat2.start();
        this.mRunningAnimations.add(ofFloat);
        this.mRunningAnimations.add(ofFloat2);
    }

    @Override // com.android.systemui.shared.navigationbar.KeyButtonRipple
    public final void enterSoftware() {
        enterSoftwareAnim();
    }

    public final void enterSoftwareAnim() {
        endAnimations("enterSoftware", true);
        this.mVisible = true;
        this.mGlowAlpha = getMaxGlowAlpha();
        float f = this.GLOW_MAX_SCALE_FACTOR;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "glowScale", f, f);
        Interpolator interpolator = FADE_IN_INTERPOLATOR;
        ofFloat.setInterpolator(interpolator);
        ofFloat.setDuration(100L);
        if (!this.isHover) {
            ofFloat.addListener(this.mAnimatorListener);
        }
        ofFloat.start();
        this.mRunningAnimations.add(ofFloat);
        if (BasicRuneWrapper.NAVBAR_ENABLED) {
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, "glowAlpha", 0.0f, getMaxGlowAlpha());
            ofFloat2.setInterpolator(interpolator);
            ofFloat2.setDuration(100L);
            if (!this.isHover) {
                ofFloat2.addListener(this.mAnimatorListener);
            }
            ofFloat2.start();
            this.mRunningAnimations.add(ofFloat2);
        }
        if (this.isHover) {
            return;
        }
        enterScaleAnimation();
    }

    @Override // com.android.systemui.shared.navigationbar.KeyButtonRipple
    public final void exitHardware() {
        this.isHover = false;
        this.mPaintProp = CanvasProperty.createPaint(getRipplePaint());
        RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(this.mPaintProp, 1, 0.0f);
        renderNodeAnimator.setDuration(350L);
        renderNodeAnimator.setInterpolator(FADE_OUT_INTERPOLATOR);
        renderNodeAnimator.addListener(this.mAnimatorListener);
        renderNodeAnimator.addListener(this.mExitHwTraceAnimator);
        renderNodeAnimator.setTarget(this.mTargetView);
        renderNodeAnimator.start();
        this.mRunningAnimations.add(renderNodeAnimator);
        exitScaleAnimation();
        invalidateSelf();
    }

    @Override // com.android.systemui.shared.navigationbar.KeyButtonRipple
    public final void exitHoverAnim() {
        Log.d("SamsungKeyButtonRipple", "exitHoverAnim " + this.mTargetView);
        if (this.mSupportHardware) {
            exitHardware();
        } else {
            exitSoftware();
        }
    }

    public final void exitScaleAnimation() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mTargetView, (Property<View, Float>) View.SCALE_X, 0.96f, 1.0f);
        Interpolator interpolator = SCALE_OUT_INTERPOLATOR;
        ofFloat.setInterpolator(interpolator);
        ofFloat.setDuration(100L);
        ofFloat.addListener(this.mAnimatorListener);
        ofFloat.start();
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mTargetView, (Property<View, Float>) View.SCALE_Y, 0.96f, 1.0f);
        ofFloat2.setInterpolator(interpolator);
        ofFloat2.setDuration(100L);
        ofFloat2.addListener(this.mAnimatorListener);
        ofFloat2.start();
        this.mRunningAnimations.add(ofFloat);
        this.mRunningAnimations.add(ofFloat2);
    }

    @Override // com.android.systemui.shared.navigationbar.KeyButtonRipple
    public final void exitSoftware() {
        if (!this.mRunningAnimations.isEmpty()) {
            endAnimations("exitSoftware", true);
        }
        this.isHover = false;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "glowAlpha", this.mGlowAlpha, 0.0f);
        ofFloat.setInterpolator(FADE_OUT_INTERPOLATOR);
        ofFloat.setDuration(350L);
        ofFloat.addListener(this.mAnimatorListener);
        ofFloat.start();
        this.mRunningAnimations.add(ofFloat);
        exitScaleAnimation();
    }

    @Override // com.android.systemui.shared.navigationbar.KeyButtonRipple
    public final void setDarkIntensity(float f) {
        boolean z;
        super.setDarkIntensity(f);
        if (!this.isHover || (z = this.mDark) == this.mLastDark) {
            return;
        }
        this.mRipplePaint = null;
        this.mLastDark = z;
        startHoverAnim();
    }

    @Override // com.android.systemui.shared.navigationbar.KeyButtonRipple
    public final void startHoverAnim() {
        boolean z = this.mDark;
        if (z != this.mLastDark) {
            this.mRipplePaint = null;
            this.mLastDark = z;
        }
        Log.d("SamsungKeyButtonRipple", "startHoverAnim " + this.mTargetView);
        this.isHover = true;
        if (this.mSupportHardware) {
            enterHardwareAnim();
        } else {
            enterSoftwareAnim();
        }
    }

    public SamsungKeyButtonRipple(Context context, View view, int i, float f) {
        super(context, view, i, f);
    }
}

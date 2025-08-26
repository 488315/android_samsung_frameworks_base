package com.android.systemui.shared.navigationbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CanvasProperty;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.view.RenderNodeAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.systemui.globalactions.presentation.features.FakeFeatures$$ExternalSyntheticOutline0;
import com.android.systemui.navigationbar.BasicRuneWrapper;
import com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda4;
import java.util.ArrayList;
import java.util.HashSet;

/* loaded from: classes3.dex */
public class KeyButtonRipple extends Drawable {
    public static final Interpolator ALPHA_OUT_INTERPOLATOR = new PathInterpolator(0.0f, 0.0f, 0.8f, 1.0f);
    public final float GLOW_MAX_SCALE_FACTOR;
    public final AnonymousClass1 mAnimatorListener;
    public CanvasProperty mBottomProp;
    public boolean mDark;
    public boolean mDrawingHardwareGlow;
    public final TraceAnimatorListener mEnterHwTraceAnimator;
    public final TraceAnimatorListener mExitHwTraceAnimator;
    public float mGlowAlpha;
    public float mGlowScale;
    public final Handler mHandler;
    public final LogInterpolator mInterpolator;
    public boolean mLastDark;
    public CanvasProperty mLeftProp;
    public int mMaxWidth;
    public final int mMaxWidthResource;
    public NavigationBar$$ExternalSyntheticLambda4 mOnInvisibleRunnable;
    public CanvasProperty mPaintProp;
    public boolean mPressed;
    public CanvasProperty mRightProp;
    public Paint mRipplePaint;
    public final HashSet mRunningAnimations;
    public CanvasProperty mRxProp;
    public CanvasProperty mRyProp;
    public boolean mSpeedUpNextFade;
    public boolean mSupportHardware;
    public final View mTargetView;
    public final ArrayList mTmpArray;
    public CanvasProperty mTopProp;
    public Type mType;
    public boolean mVisible;

    public final class LogInterpolator implements Interpolator {
        public /* synthetic */ LogInterpolator(int i) {
            this();
        }

        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            return 1.0f - ((float) Math.pow(400.0d, (-f) * 1.4d));
        }

        private LogInterpolator() {
        }
    }

    public final class TraceAnimatorListener extends AnimatorListenerAdapter {
        public final String mName;

        public TraceAnimatorListener(String str) {
            this.mName = str;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            if (Trace.isEnabled()) {
                Trace.instant(4096L, "KeyButtonRipple.cancel." + this.mName);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            if (Trace.isEnabled()) {
                Trace.instant(4096L, "KeyButtonRipple.end." + this.mName);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            if (Trace.isEnabled()) {
                Trace.instant(4096L, "KeyButtonRipple.start." + this.mName);
            }
        }
    }

    public enum Type {
        OVAL,
        ROUNDED_RECT
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.systemui.shared.navigationbar.KeyButtonRipple$1] */
    public KeyButtonRipple(Context context, View view, int i) {
        this.GLOW_MAX_SCALE_FACTOR = 1.35f;
        this.mGlowAlpha = 0.0f;
        this.mGlowScale = 1.0f;
        this.mInterpolator = new LogInterpolator(0);
        this.mHandler = new Handler();
        this.mTmpArray = new ArrayList();
        this.mRunningAnimations = new HashSet();
        this.mExitHwTraceAnimator = new TraceAnimatorListener("exitHardware");
        this.mEnterHwTraceAnimator = new TraceAnimatorListener("enterHardware");
        this.mType = Type.ROUNDED_RECT;
        this.mAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.shared.navigationbar.KeyButtonRipple.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyButtonRipple.this.mRunningAnimations.remove(animator);
                if (KeyButtonRipple.this.mRunningAnimations.isEmpty()) {
                    KeyButtonRipple keyButtonRipple = KeyButtonRipple.this;
                    if (keyButtonRipple.mPressed) {
                        return;
                    }
                    keyButtonRipple.mVisible = false;
                    keyButtonRipple.mDrawingHardwareGlow = false;
                    keyButtonRipple.invalidateSelf();
                }
            }
        };
        this.mMaxWidthResource = i;
        this.mMaxWidth = context.getResources().getDimensionPixelSize(i);
        this.mTargetView = view;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        boolean zIsHardwareAccelerated = canvas.isHardwareAccelerated();
        this.mSupportHardware = zIsHardwareAccelerated;
        if (zIsHardwareAccelerated) {
            RecordingCanvas recordingCanvas = (RecordingCanvas) canvas;
            if (this.mDrawingHardwareGlow) {
                if (this.mType == Type.ROUNDED_RECT) {
                    recordingCanvas.drawRoundRect(this.mLeftProp, this.mTopProp, this.mRightProp, this.mBottomProp, this.mRxProp, this.mRyProp, this.mPaintProp);
                } else {
                    recordingCanvas.drawCircle(CanvasProperty.createFloat(getBounds().width() / 2), CanvasProperty.createFloat(getBounds().height() / 2), CanvasProperty.createFloat((Math.min(getBounds().width(), getBounds().height()) * 1.0f) / 2.0f), this.mPaintProp);
                }
            }
        } else {
            drawSoftware(canvas);
        }
        if (this.mPressed || this.mVisible || this.mOnInvisibleRunnable == null) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(this.mOnInvisibleRunnable);
        this.mOnInvisibleRunnable = null;
    }

    public void drawSoftware(Canvas canvas) {
        if (this.mGlowAlpha > 0.0f) {
            Paint ripplePaint = getRipplePaint();
            ripplePaint.setAlpha((int) (this.mGlowAlpha * 255.0f));
            float fWidth = getBounds().width();
            float fHeight = getBounds().height();
            boolean z = fWidth > fHeight;
            float rippleSize = getRippleSize() * this.mGlowScale * 0.5f;
            float f = fWidth * 0.5f;
            float f2 = fHeight * 0.5f;
            float f3 = z ? rippleSize : f;
            if (z) {
                rippleSize = f2;
            }
            float f4 = z ? f2 : f;
            if (this.mType == Type.ROUNDED_RECT) {
                float f5 = rippleSize;
                canvas.drawRoundRect(f - f3, f2 - f5, f3 + f, f2 + f5, f4, f4, ripplePaint);
                return;
            }
            canvas.save();
            canvas.translate(f, f2);
            float fMin = Math.min(f3, rippleSize);
            float f6 = -fMin;
            canvas.drawOval(f6, f6, fMin, fMin, ripplePaint);
            canvas.restore();
        }
    }

    public final void endAnimations(String str, boolean z) {
        if (Trace.isEnabled()) {
            Trace.instant(4096L, FakeFeatures$$ExternalSyntheticOutline0.m("KeyButtonRipple.endAnim: reason=", str, " cancel=", z));
        }
        this.mVisible = false;
        this.mTmpArray.addAll(this.mRunningAnimations);
        int size = this.mTmpArray.size();
        for (int i = 0; i < size; i++) {
            Animator animator = (Animator) this.mTmpArray.get(i);
            if (z) {
                animator.cancel();
            } else {
                animator.end();
            }
        }
        this.mTmpArray.clear();
        this.mRunningAnimations.clear();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public void enterHardware() {
        endAnimations("enterHardware", true);
        this.mVisible = true;
        this.mDrawingHardwareGlow = true;
        CanvasProperty canvasPropertyCreateFloat = CanvasProperty.createFloat(getExtendSize() / 2);
        if (isHorizontal()) {
            this.mLeftProp = canvasPropertyCreateFloat;
        } else {
            this.mTopProp = canvasPropertyCreateFloat;
        }
        RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(isHorizontal() ? this.mLeftProp : this.mTopProp, (getExtendSize() / 2) - ((this.GLOW_MAX_SCALE_FACTOR * getRippleSize()) / 2.0f));
        renderNodeAnimator.setDuration(350L);
        renderNodeAnimator.setInterpolator(this.mInterpolator);
        renderNodeAnimator.addListener(this.mAnimatorListener);
        renderNodeAnimator.setTarget(this.mTargetView);
        CanvasProperty canvasPropertyCreateFloat2 = CanvasProperty.createFloat(getExtendSize() / 2);
        if (isHorizontal()) {
            this.mRightProp = canvasPropertyCreateFloat2;
        } else {
            this.mBottomProp = canvasPropertyCreateFloat2;
        }
        RenderNodeAnimator renderNodeAnimator2 = new RenderNodeAnimator(isHorizontal() ? this.mRightProp : this.mBottomProp, ((this.GLOW_MAX_SCALE_FACTOR * getRippleSize()) / 2.0f) + (getExtendSize() / 2));
        renderNodeAnimator2.setDuration(350L);
        renderNodeAnimator2.setInterpolator(this.mInterpolator);
        renderNodeAnimator2.addListener(this.mAnimatorListener);
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
        this.mGlowAlpha = this.mLastDark ? 0.1f : 0.2f;
        Paint ripplePaint = getRipplePaint();
        this.mRipplePaint = ripplePaint;
        ripplePaint.setAlpha((int) (this.mGlowAlpha * 255.0f));
        this.mPaintProp = CanvasProperty.createPaint(this.mRipplePaint);
        renderNodeAnimator.start();
        renderNodeAnimator2.start();
        this.mRunningAnimations.add(renderNodeAnimator);
        this.mRunningAnimations.add(renderNodeAnimator2);
        invalidateSelf();
    }

    public void enterSoftware() {
        endAnimations("enterSoftware", true);
        this.mVisible = true;
        this.mGlowAlpha = this.mLastDark ? 0.1f : 0.2f;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "glowScale", 0.0f, this.GLOW_MAX_SCALE_FACTOR);
        objectAnimatorOfFloat.setInterpolator(this.mInterpolator);
        objectAnimatorOfFloat.setDuration(350L);
        objectAnimatorOfFloat.addListener(this.mAnimatorListener);
        objectAnimatorOfFloat.start();
        this.mRunningAnimations.add(objectAnimatorOfFloat);
    }

    public void exitHardware() {
        this.mPaintProp = CanvasProperty.createPaint(getRipplePaint());
        RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(this.mPaintProp, 1, 0.0f);
        int i = this.mSpeedUpNextFade ? 80 : 450;
        this.mSpeedUpNextFade = false;
        renderNodeAnimator.setDuration(i);
        renderNodeAnimator.setInterpolator(ALPHA_OUT_INTERPOLATOR);
        renderNodeAnimator.addListener(this.mAnimatorListener);
        renderNodeAnimator.addListener(this.mExitHwTraceAnimator);
        renderNodeAnimator.setTarget(this.mTargetView);
        renderNodeAnimator.start();
        this.mRunningAnimations.add(renderNodeAnimator);
        invalidateSelf();
    }

    public void exitSoftware() {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "glowAlpha", this.mGlowAlpha, 0.0f);
        objectAnimatorOfFloat.setInterpolator(ALPHA_OUT_INTERPOLATOR);
        int i = this.mSpeedUpNextFade ? 80 : 450;
        this.mSpeedUpNextFade = false;
        objectAnimatorOfFloat.setDuration(i);
        objectAnimatorOfFloat.addListener(this.mAnimatorListener);
        objectAnimatorOfFloat.start();
        this.mRunningAnimations.add(objectAnimatorOfFloat);
    }

    public final int getExtendSize() {
        boolean zIsHorizontal = isHorizontal();
        Rect bounds = getBounds();
        return zIsHorizontal ? bounds.width() : bounds.height();
    }

    public float getGlowAlpha() {
        return this.mGlowAlpha;
    }

    public float getGlowScale() {
        return this.mGlowScale;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    public final Paint getRipplePaint() {
        if (this.mRipplePaint == null) {
            Paint paint = new Paint();
            this.mRipplePaint = paint;
            paint.setAntiAlias(true);
            this.mRipplePaint.setColor(this.mLastDark ? -16777216 : -1);
        }
        return this.mRipplePaint;
    }

    public final int getRippleSize() {
        int iWidth = isHorizontal() ? getBounds().width() : getBounds().height();
        return BasicRuneWrapper.NAVBAR_ENABLED ? iWidth : Math.min(iWidth, this.mMaxWidth);
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean hasFocusStateSpecified() {
        return true;
    }

    public final boolean isHorizontal() {
        return getBounds().width() > getBounds().height();
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final void jumpToCurrentState() {
        endAnimations("jumpToCurrentState", false);
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
        boolean z;
        int i = 0;
        while (true) {
            if (i >= iArr.length) {
                z = false;
                break;
            }
            if (iArr[i] == 16842919) {
                z = true;
                break;
            }
            i++;
        }
        if (z == this.mPressed) {
            return false;
        }
        boolean z2 = this.mDark;
        if (z2 != this.mLastDark && z) {
            this.mRipplePaint = null;
            this.mLastDark = z2;
        }
        if (this.mSupportHardware) {
            if (z) {
                enterHardware();
            } else {
                exitHardware();
            }
        } else if (z) {
            enterSoftware();
        } else {
            exitSoftware();
        }
        this.mPressed = z;
        return true;
    }

    public void setDarkIntensity(float f) {
        this.mDark = f >= 0.5f;
    }

    public void setGlowAlpha(float f) {
        this.mGlowAlpha = f;
        invalidateSelf();
    }

    public void setGlowScale(float f) {
        this.mGlowScale = f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (visible) {
            jumpToCurrentState();
        }
        return visible;
    }

    public KeyButtonRipple(Context context, View view, int i, float f) {
        this(context, view, i);
        this.GLOW_MAX_SCALE_FACTOR = f;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
    }

    public void exitHoverAnim() {
    }

    public void startHoverAnim() {
    }
}

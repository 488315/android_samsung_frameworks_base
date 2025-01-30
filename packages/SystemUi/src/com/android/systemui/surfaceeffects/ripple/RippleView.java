package com.android.systemui.surfaceeffects.ripple;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import com.android.systemui.surfaceeffects.ripple.RippleShader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class RippleView extends View {
    public final ValueAnimator animator;
    public float centerX;
    public float centerY;
    public long duration;
    public final Paint ripplePaint;
    public RippleShader rippleShader;
    public RippleShader.RippleShape rippleShape;

    public RippleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.ripplePaint = new Paint();
        this.animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.duration = 1750L;
    }

    public final RippleShader getRippleShader() {
        RippleShader rippleShader = this.rippleShader;
        if (rippleShader != null) {
            return rippleShader;
        }
        return null;
    }

    @Override // android.view.View
    public final void onAttachedToWindow() {
        getRippleShader().setPixelDensity(getResources().getDisplayMetrics().density);
        super.onAttachedToWindow();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        getRippleShader().setPixelDensity(getResources().getDisplayMetrics().density);
        super.onConfigurationChanged(configuration);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (canvas.isHardwareAccelerated()) {
            RippleShader.RippleShape rippleShape = this.rippleShape;
            if ((rippleShape != null ? rippleShape : null) == RippleShader.RippleShape.CIRCLE) {
                canvas.drawCircle(this.centerX, this.centerY, getRippleShader().rippleSize.currentWidth, this.ripplePaint);
                return;
            }
            if (rippleShape == null) {
                rippleShape = null;
            }
            if (rippleShape != RippleShader.RippleShape.ELLIPSE) {
                canvas.drawPaint(this.ripplePaint);
                return;
            }
            float f = 2;
            float f2 = getRippleShader().rippleSize.currentWidth * f;
            float f3 = getRippleShader().rippleSize.currentHeight * f;
            float f4 = this.centerX;
            float f5 = this.centerY;
            canvas.drawRect(f4 - f2, f5 - f3, f4 + f2, f5 + f3, this.ripplePaint);
        }
    }

    public final void setCenter(float f, float f2) {
        this.centerX = f;
        this.centerY = f2;
        getRippleShader().setFloatUniform("in_center", f, f2);
    }

    public final void setColor(int i, int i2) {
        getRippleShader().setColorUniform("in_color", ColorUtils.setAlphaComponent(i, i2));
    }

    public final void setupShader(RippleShader.RippleShape rippleShape) {
        this.rippleShape = rippleShape;
        this.rippleShader = new RippleShader(rippleShape);
        getRippleShader().setColorUniform("in_color", -1);
        getRippleShader().setRawProgress(0.0f);
        getRippleShader().setFloatUniform("in_sparkle_strength", 0.3f);
        getRippleShader().setPixelDensity(getResources().getDisplayMetrics().density);
        this.ripplePaint.setShader(getRippleShader());
    }

    public final void startRipple(final Runnable runnable) {
        if (this.animator.isRunning()) {
            return;
        }
        this.animator.setDuration(this.duration);
        this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.ripple.RippleView$startRipple$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                long currentPlayTime = valueAnimator.getCurrentPlayTime();
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                RippleView.this.getRippleShader().setRawProgress(floatValue);
                RippleShader rippleShader = RippleView.this.getRippleShader();
                float f = 1 - floatValue;
                float f2 = 75;
                rippleShader.setFloatUniform("in_distort_radial", rippleShader.rawProgress * f2 * f);
                rippleShader.setFloatUniform("in_distort_xy", f2 * f);
                RippleView.this.getRippleShader().setFloatUniform("in_time", currentPlayTime);
                RippleView.this.invalidate();
            }
        });
        this.animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.ripple.RippleView$startRipple$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        this.animator.start();
    }
}

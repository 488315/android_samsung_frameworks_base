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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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

    @Override // android.view.View
    public final void onAttachedToWindow() {
        RippleShader rippleShader = this.rippleShader;
        if (rippleShader == null) {
            rippleShader = null;
        }
        rippleShader.setPixelDensity(getResources().getDisplayMetrics().density);
        super.onAttachedToWindow();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        RippleShader rippleShader = this.rippleShader;
        if (rippleShader == null) {
            rippleShader = null;
        }
        rippleShader.setPixelDensity(getResources().getDisplayMetrics().density);
        super.onConfigurationChanged(configuration);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (canvas.isHardwareAccelerated()) {
            RippleShader.RippleShape rippleShape = this.rippleShape;
            if ((rippleShape != null ? rippleShape : null) == RippleShader.RippleShape.CIRCLE) {
                RippleShader rippleShader = this.rippleShader;
                canvas.drawCircle(this.centerX, this.centerY, (rippleShader != null ? rippleShader : null).rippleSize.currentWidth, this.ripplePaint);
                return;
            }
            if (rippleShape == null) {
                rippleShape = null;
            }
            if (rippleShape != RippleShader.RippleShape.ELLIPSE) {
                canvas.drawPaint(this.ripplePaint);
                return;
            }
            RippleShader rippleShader2 = this.rippleShader;
            float f = 2;
            float f2 = (rippleShader2 != null ? rippleShader2 : null).rippleSize.currentWidth * f;
            float f3 = (rippleShader2 != null ? rippleShader2 : null).rippleSize.currentHeight * f;
            float f4 = this.centerX;
            float f5 = this.centerY;
            canvas.drawRect(f4 - f2, f5 - f3, f4 + f2, f5 + f3, this.ripplePaint);
        }
    }

    public final void setCenter(float f, float f2) {
        this.centerX = f;
        this.centerY = f2;
        RippleShader rippleShader = this.rippleShader;
        if (rippleShader == null) {
            rippleShader = null;
        }
        rippleShader.setFloatUniform("in_center", f, f2);
    }

    public final void setColor(int i, int i2) {
        RippleShader rippleShader = this.rippleShader;
        if (rippleShader == null) {
            rippleShader = null;
        }
        rippleShader.setColorUniform("in_color", ColorUtils.setAlphaComponent(i, i2));
    }

    public final void setupShader(RippleShader.RippleShape rippleShape) {
        this.rippleShape = rippleShape;
        RippleShader rippleShader = new RippleShader(rippleShape);
        this.rippleShader = rippleShader;
        rippleShader.setColorUniform("in_color", -1);
        RippleShader rippleShader2 = this.rippleShader;
        if (rippleShader2 == null) {
            rippleShader2 = null;
        }
        rippleShader2.setRawProgress(0.0f);
        RippleShader rippleShader3 = this.rippleShader;
        if (rippleShader3 == null) {
            rippleShader3 = null;
        }
        rippleShader3.setFloatUniform("in_sparkle_strength", 0.3f);
        RippleShader rippleShader4 = this.rippleShader;
        if (rippleShader4 == null) {
            rippleShader4 = null;
        }
        rippleShader4.setPixelDensity(getResources().getDisplayMetrics().density);
        Paint paint = this.ripplePaint;
        RippleShader rippleShader5 = this.rippleShader;
        paint.setShader(rippleShader5 != null ? rippleShader5 : null);
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
                RippleShader rippleShader = RippleView.this.rippleShader;
                if (rippleShader == null) {
                    rippleShader = null;
                }
                rippleShader.setRawProgress(floatValue);
                RippleShader rippleShader2 = RippleView.this.rippleShader;
                if (rippleShader2 == null) {
                    rippleShader2 = null;
                }
                rippleShader2.setDistortionStrength(1 - floatValue);
                RippleShader rippleShader3 = RippleView.this.rippleShader;
                (rippleShader3 != null ? rippleShader3 : null).setFloatUniform("in_time", currentPlayTime);
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

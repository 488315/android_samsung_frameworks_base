package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.PathInterpolator;
import com.android.app.animation.Interpolators;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import kotlin.comparisons.ComparisonsKt___ComparisonsJvmKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AuthRippleView extends View {
    public boolean drawDwell;
    public boolean drawRipple;
    public final long dwellExpandDuration;
    public Point dwellOrigin;
    public final Paint dwellPaint;
    public final long dwellPulseDuration;
    public Animator dwellPulseOutAnimator;
    public float dwellRadius;
    public final DwellRippleShader dwellShader;
    public final long fadeDuration;
    public Animator fadeDwellAnimator;
    public int lockScreenColorVal;
    public Point origin;
    public final long retractDuration;
    public Animator retractDwellAnimator;
    public final PathInterpolator retractInterpolator;
    public final Paint ripplePaint;
    public final RippleShader rippleShader;
    public Animator unlockedRippleAnimator;

    public AuthRippleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.retractInterpolator = new PathInterpolator(0.05f, 0.93f, 0.1f, 1.0f);
        this.dwellPulseDuration = 100L;
        this.dwellExpandDuration = 1900L;
        this.lockScreenColorVal = -1;
        this.fadeDuration = 83L;
        this.retractDuration = 400L;
        DwellRippleShader dwellRippleShader = new DwellRippleShader();
        this.dwellShader = dwellRippleShader;
        Paint paint = new Paint();
        this.dwellPaint = paint;
        RippleShader rippleShader = new RippleShader(null, 1, null);
        this.rippleShader = rippleShader;
        Paint paint2 = new Paint();
        this.ripplePaint = paint2;
        this.dwellOrigin = new Point();
        this.origin = new Point();
        rippleShader.setRawProgress(0.0f);
        rippleShader.setPixelDensity(getResources().getDisplayMetrics().density);
        rippleShader.setFloatUniform("in_sparkle_strength", 0.3f);
        RippleShader.FadeParams fadeParams = rippleShader.baseRingFadeParams;
        fadeParams.fadeInStart = 0.0f;
        fadeParams.fadeInEnd = 0.2f;
        fadeParams.fadeOutStart = 0.2f;
        fadeParams.fadeOutEnd = 1.0f;
        RippleShader.FadeParams fadeParams2 = rippleShader.centerFillFadeParams;
        fadeParams2.fadeInStart = 0.0f;
        fadeParams2.fadeInEnd = 0.15f;
        fadeParams2.fadeOutStart = 0.15f;
        fadeParams2.fadeOutEnd = 0.56f;
        paint2.setShader(rippleShader);
        this.lockScreenColorVal = -1;
        rippleShader.setColorUniform("in_color", ColorUtils.setAlphaComponent(-1, 62));
        dwellRippleShader.setColor(-1);
        dwellRippleShader.setProgress(0.0f);
        dwellRippleShader.setFloatUniform("in_distortion_strength", 0.4f);
        paint.setShader(dwellRippleShader);
        setVisibility(8);
    }

    public final void fadeDwellRipple() {
        Animator animator;
        Animator animator2 = this.fadeDwellAnimator;
        if (animator2 == null || !animator2.isRunning()) {
            Animator animator3 = this.dwellPulseOutAnimator;
            if ((animator3 == null || !animator3.isRunning()) && ((animator = this.retractDwellAnimator) == null || !animator.isRunning())) {
                return;
            }
            ValueAnimator ofInt = ValueAnimator.ofInt(Color.alpha(this.dwellShader.color), 0);
            ofInt.setInterpolator(Interpolators.LINEAR);
            ofInt.setDuration(this.fadeDuration);
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleView$fadeDwellRipple$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    DwellRippleShader dwellRippleShader = AuthRippleView.this.dwellShader;
                    dwellRippleShader.setColor(ColorUtils.setAlphaComponent(dwellRippleShader.color, ((Integer) valueAnimator.getAnimatedValue()).intValue()));
                    AuthRippleView.this.invalidate();
                }
            });
            ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.AuthRippleView$fadeDwellRipple$1$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator4) {
                    AuthRippleView authRippleView = AuthRippleView.this;
                    authRippleView.drawDwell = false;
                    DwellRippleShader dwellRippleShader = authRippleView.dwellShader;
                    dwellRippleShader.setColor(ColorUtils.setAlphaComponent(dwellRippleShader.color, 255));
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator4) {
                    Animator animator5 = AuthRippleView.this.retractDwellAnimator;
                    if (animator5 != null) {
                        animator5.cancel();
                    }
                    Animator animator6 = AuthRippleView.this.dwellPulseOutAnimator;
                    if (animator6 != null) {
                        animator6.cancel();
                    }
                    AuthRippleView.this.drawDwell = true;
                }
            });
            ofInt.start();
            this.fadeDwellAnimator = ofInt;
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (this.drawDwell) {
            float f = 1;
            float f2 = this.dwellShader.progress;
            float f3 = (f - ((f - f2) * ((f - f2) * (f - f2)))) * this.dwellRadius * 2.0f;
            Point point = this.dwellOrigin;
            canvas.drawCircle(point.x, point.y, f3, this.dwellPaint);
        }
        if (this.drawRipple) {
            Point point2 = this.origin;
            canvas.drawCircle(point2.x, point2.y, this.rippleShader.rippleSize.currentWidth, this.ripplePaint);
        }
    }

    public final void retractDwellRipple() {
        Animator animator;
        Animator animator2 = this.retractDwellAnimator;
        if (animator2 == null || !animator2.isRunning()) {
            Animator animator3 = this.fadeDwellAnimator;
            if ((animator3 == null || !animator3.isRunning()) && (animator = this.dwellPulseOutAnimator) != null && animator.isRunning()) {
                ValueAnimator ofFloat = ValueAnimator.ofFloat(this.dwellShader.progress, 0.0f);
                ofFloat.setInterpolator(this.retractInterpolator);
                ofFloat.setDuration(this.retractDuration);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleView$retractDwellRipple$retractDwellRippleAnimator$1$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        long currentPlayTime = valueAnimator.getCurrentPlayTime();
                        AuthRippleView.this.dwellShader.setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        AuthRippleView.this.dwellShader.setTime(currentPlayTime);
                        AuthRippleView.this.invalidate();
                    }
                });
                ValueAnimator ofInt = ValueAnimator.ofInt(255, 0);
                ofInt.setInterpolator(Interpolators.LINEAR);
                ofInt.setDuration(this.retractDuration);
                ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleView$retractDwellRipple$retractAlphaAnimator$1$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        DwellRippleShader dwellRippleShader = AuthRippleView.this.dwellShader;
                        dwellRippleShader.setColor(ColorUtils.setAlphaComponent(dwellRippleShader.color, ((Integer) valueAnimator.getAnimatedValue()).intValue()));
                        AuthRippleView.this.invalidate();
                    }
                });
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(ofFloat, ofInt);
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.AuthRippleView$retractDwellRipple$1$1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator4) {
                        AuthRippleView authRippleView = AuthRippleView.this;
                        authRippleView.drawDwell = false;
                        DwellRippleShader dwellRippleShader = authRippleView.dwellShader;
                        dwellRippleShader.setColor(ColorUtils.setAlphaComponent(dwellRippleShader.color, 255));
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator4) {
                        Animator animator5 = AuthRippleView.this.dwellPulseOutAnimator;
                        if (animator5 != null) {
                            animator5.cancel();
                        }
                        AuthRippleView.this.drawDwell = true;
                    }
                });
                animatorSet.start();
                this.retractDwellAnimator = animatorSet;
            }
        }
    }

    public final void setFingerprintSensorLocation(Point point, float f) {
        this.rippleShader.setFloatUniform("in_center", point.x, point.y);
        this.origin = point;
        RippleShader.RippleSize rippleSize = this.rippleShader.rippleSize;
        float maxOf = ComparisonsKt___ComparisonsJvmKt.maxOf(point.x, point.y, getWidth() - point.x, getHeight() - point.y) * 0.9f * 2.0f;
        rippleSize.getClass();
        rippleSize.setSizeAtProgresses(rippleSize.initialSize, new RippleShader.SizeAtProgress(1.0f, maxOf, maxOf));
        DwellRippleShader dwellRippleShader = this.dwellShader;
        dwellRippleShader.getClass();
        dwellRippleShader.setFloatUniform("in_origin", point.x, point.y);
        this.dwellOrigin = point;
        float f2 = f * 1.5f;
        this.dwellShader.maxRadius = f2;
        this.dwellRadius = f2;
    }

    public final void setSensorLocation(Point point) {
        this.rippleShader.setFloatUniform("in_center", point.x, point.y);
        this.origin = point;
        float maxOf = ComparisonsKt___ComparisonsJvmKt.maxOf(point.x, point.y, getWidth() - point.x, getHeight() - point.y) * 0.9f;
        RippleShader.RippleSize rippleSize = this.rippleShader.rippleSize;
        float f = maxOf * 2.0f;
        rippleSize.getClass();
        rippleSize.setSizeAtProgresses(rippleSize.initialSize, new RippleShader.SizeAtProgress(1.0f, f, f));
    }
}

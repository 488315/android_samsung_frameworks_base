package com.android.systemui.media.controls.ui.drawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.os.Trace;
import android.util.MathUtils;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.graphics.ColorUtils;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public final class SquigglyProgress extends Drawable {
    public boolean animate;
    public ValueAnimator heightAnimator;
    public float heightFraction;
    public long lastFrameTime;
    public float lineAmplitude;
    public final Paint linePaint;
    public final float matchedWaveEndpoint;
    public final float minWaveEndpoint;
    public final Path path;
    public float phaseOffset;
    public float phaseSpeed;
    public float strokeWidth;
    public boolean transitionEnabled;
    public final float transitionPeriods;
    public float waveLength;
    public final Paint wavePaint;

    public SquigglyProgress() {
        Paint paint = new Paint();
        this.wavePaint = paint;
        Paint paint2 = new Paint();
        this.linePaint = paint2;
        this.path = new Path();
        this.lastFrameTime = -1L;
        this.transitionPeriods = 1.5f;
        this.minWaveEndpoint = 0.2f;
        this.matchedWaveEndpoint = 0.6f;
        this.transitionEnabled = true;
        Paint.Cap cap = Paint.Cap.ROUND;
        paint.setStrokeCap(cap);
        paint2.setStrokeCap(cap);
        Paint.Style style = Paint.Style.STROKE;
        paint2.setStyle(style);
        paint.setStyle(style);
        paint2.setAlpha(77);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("SquigglyProgress#draw");
        }
        try {
            drawTraced(canvas);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    public final void drawTraced(Canvas canvas) {
        if (this.animate) {
            invalidateSelf();
            long uptimeMillis = SystemClock.uptimeMillis();
            this.phaseOffset = ((((uptimeMillis - this.lastFrameTime) / 1000.0f) * this.phaseSpeed) + this.phaseOffset) % this.waveLength;
            this.lastFrameTime = uptimeMillis;
        }
        float level = getLevel() / 10000.0f;
        float width = getBounds().width();
        float f = width * level;
        if (this.transitionEnabled) {
            float f2 = this.matchedWaveEndpoint;
            if (level <= f2) {
                level = MathUtils.lerp(this.minWaveEndpoint, f2, MathUtils.lerpInv(0.0f, f2, level));
            }
        }
        final float f3 = level * width;
        float f4 = (-this.phaseOffset) - (this.waveLength / 2.0f);
        float f5 = this.transitionEnabled ? width : f3;
        Function2 function2 = new Function2() { // from class: com.android.systemui.media.controls.ui.drawable.SquigglyProgress$drawTraced$computeAmplitude$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                float f6;
                float floatValue = ((Number) obj).floatValue();
                float floatValue2 = ((Number) obj2).floatValue();
                SquigglyProgress squigglyProgress = SquigglyProgress.this;
                if (squigglyProgress.transitionEnabled) {
                    float f7 = squigglyProgress.transitionPeriods * squigglyProgress.waveLength;
                    float f8 = f3;
                    float f9 = f7 / 2.0f;
                    float lerpInvSat = MathUtils.lerpInvSat(f8 + f9, f8 - f9, floatValue);
                    SquigglyProgress squigglyProgress2 = SquigglyProgress.this;
                    f6 = floatValue2 * squigglyProgress2.heightFraction * squigglyProgress2.lineAmplitude * lerpInvSat;
                } else {
                    f6 = floatValue2 * squigglyProgress.heightFraction * squigglyProgress.lineAmplitude;
                }
                return Float.valueOf(f6);
            }
        };
        this.path.rewind();
        this.path.moveTo(f4, 0.0f);
        float f6 = 1.0f;
        float floatValue = ((Number) function2.invoke(Float.valueOf(f4), Float.valueOf(1.0f))).floatValue();
        float f7 = this.waveLength / 2.0f;
        float f8 = floatValue;
        float f9 = f4;
        while (f9 < f5) {
            f6 = -f6;
            float f10 = f9 + f7;
            float f11 = (f7 / 2) + f9;
            float floatValue2 = ((Number) function2.invoke(Float.valueOf(f10), Float.valueOf(f6))).floatValue();
            this.path.cubicTo(f11, f8, f11, floatValue2, f10, floatValue2);
            f8 = floatValue2;
            f9 = f10;
        }
        float f12 = this.lineAmplitude + this.strokeWidth;
        canvas.save();
        canvas.translate(getBounds().left, getBounds().centerY());
        canvas.save();
        float f13 = (-1.0f) * f12;
        canvas.clipRect(0.0f, f13, f, f12);
        canvas.drawPath(this.path, this.wavePaint);
        canvas.restore();
        if (this.transitionEnabled) {
            canvas.save();
            canvas.clipRect(f, f13, width, f12);
            canvas.drawPath(this.path, this.linePaint);
            canvas.restore();
        } else {
            canvas.drawLine(f, 0.0f, width, 0.0f, this.linePaint);
        }
        canvas.drawPoint(0.0f, ((float) Math.cos((Math.abs(f4) / this.waveLength) * 6.2831855f)) * this.lineAmplitude * this.heightFraction, this.wavePaint);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.wavePaint.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onLevelChange(int i) {
        return this.animate;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        updateColors(this.wavePaint.getColor(), i);
    }

    public final void setAnimate(boolean z) {
        if (this.animate == z) {
            return;
        }
        this.animate = z;
        if (z) {
            this.lastFrameTime = SystemClock.uptimeMillis();
        }
        ValueAnimator valueAnimator = this.heightAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.heightFraction, this.animate ? 1.0f : 0.0f);
        if (this.animate) {
            ofFloat.setStartDelay(60L);
            ofFloat.setDuration(800L);
            ofFloat.setInterpolator(Interpolators.EMPHASIZED_DECELERATE);
        } else {
            ofFloat.setDuration(550L);
            ofFloat.setInterpolator(Interpolators.STANDARD_DECELERATE);
        }
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.controls.ui.drawable.SquigglyProgress$animate$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                SquigglyProgress.this.heightFraction = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                SquigglyProgress.this.invalidateSelf();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.controls.ui.drawable.SquigglyProgress$animate$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SquigglyProgress.this.heightAnimator = null;
            }
        });
        ofFloat.start();
        this.heightAnimator = ofFloat;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.wavePaint.setColorFilter(colorFilter);
        this.linePaint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTint(int i) {
        updateColors(i, this.wavePaint.getAlpha());
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        if (colorStateList == null) {
            return;
        }
        updateColors(colorStateList.getDefaultColor(), this.wavePaint.getAlpha());
    }

    public final void updateColors(int i, int i2) {
        this.wavePaint.setColor(ColorUtils.setAlphaComponent(i, i2));
        this.linePaint.setColor(ColorUtils.setAlphaComponent(i, (int) ((i2 / 255.0f) * 77)));
    }
}

package com.android.systemui.media.controls.p010ui;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.MathUtils;
import com.android.internal.graphics.ColorUtils;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SquigglyProgress extends Drawable {
    public final Paint linePaint;
    public final float matchedWaveEndpoint;
    public final float minWaveEndpoint;
    public final Path path;
    public final boolean transitionEnabled;
    public final float transitionPeriods;
    public final Paint wavePaint;

    public SquigglyProgress() {
        Paint paint = new Paint();
        this.wavePaint = paint;
        Paint paint2 = new Paint();
        this.linePaint = paint2;
        this.path = new Path();
        this.transitionPeriods = 1.5f;
        this.minWaveEndpoint = 0.2f;
        this.matchedWaveEndpoint = 0.6f;
        this.transitionEnabled = true;
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.STROKE);
        paint2.setAlpha(77);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
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
        float f4 = this.transitionEnabled ? width : f3;
        Function2 function2 = new Function2() { // from class: com.android.systemui.media.controls.ui.SquigglyProgress$draw$computeAmplitude$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                float f5;
                float floatValue = ((Number) obj).floatValue();
                float floatValue2 = ((Number) obj2).floatValue();
                SquigglyProgress squigglyProgress = SquigglyProgress.this;
                if (squigglyProgress.transitionEnabled) {
                    float f6 = squigglyProgress.transitionPeriods * 0.0f;
                    float f7 = f3;
                    float f8 = f6 / 2.0f;
                    float lerpInvSat = MathUtils.lerpInvSat(f7 + f8, f7 - f8, floatValue);
                    SquigglyProgress.this.getClass();
                    SquigglyProgress.this.getClass();
                    f5 = floatValue2 * 0.0f * 0.0f * lerpInvSat;
                } else {
                    squigglyProgress.getClass();
                    f5 = floatValue2 * 0.0f * 0.0f;
                }
                return Float.valueOf(f5);
            }
        };
        this.path.rewind();
        this.path.moveTo(-0.0f, 0.0f);
        float f5 = 1.0f;
        float floatValue = ((Number) function2.invoke(Float.valueOf(-0.0f), Float.valueOf(1.0f))).floatValue();
        float f6 = -0.0f;
        while (f6 < f4) {
            f5 = -f5;
            float f7 = f6 + 0.0f;
            float f8 = (0.0f / 2) + f6;
            float floatValue2 = ((Number) function2.invoke(Float.valueOf(f7), Float.valueOf(f5))).floatValue();
            this.path.cubicTo(f8, floatValue, f8, floatValue2, f7, floatValue2);
            floatValue = floatValue2;
            f6 = f7;
        }
        canvas.save();
        canvas.translate(getBounds().left, getBounds().centerY());
        canvas.save();
        canvas.clipRect(0.0f, -0.0f, f, 0.0f);
        canvas.drawPath(this.path, this.wavePaint);
        canvas.restore();
        if (this.transitionEnabled) {
            canvas.save();
            canvas.clipRect(f, -0.0f, width, 0.0f);
            canvas.drawPath(this.path, this.linePaint);
            canvas.restore();
        } else {
            canvas.drawLine(f, 0.0f, width, 0.0f, this.linePaint);
        }
        canvas.drawPoint(0.0f, ((float) Math.cos((Math.abs(-0.0f) / 0.0f) * 6.2831855f)) * 0.0f * 0.0f, this.wavePaint);
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
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        updateColors(this.wavePaint.getColor(), i);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.wavePaint.setColorFilter(colorFilter);
        this.linePaint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTint(int i) {
        updateColors(i, getAlpha());
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        if (colorStateList == null) {
            return;
        }
        updateColors(colorStateList.getDefaultColor(), getAlpha());
    }

    public final void updateColors(int i, int i2) {
        this.wavePaint.setColor(ColorUtils.setAlphaComponent(i, i2));
        this.linePaint.setColor(ColorUtils.setAlphaComponent(i, (int) ((i2 / 255.0f) * 77)));
    }
}

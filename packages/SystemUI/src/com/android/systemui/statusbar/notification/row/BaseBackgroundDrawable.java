package com.android.systemui.statusbar.notification.row;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import com.android.systemui.R;
import com.android.wm.shell.shared.animation.Interpolators;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BaseBackgroundDrawable extends Drawable {
    public final Paint bgPaint;
    public final Path buttonShape;
    public final float cornerRadius;
    public int gradientAlpha;
    public final float insetVertical;
    public final int outlineEndColor;
    public final Paint outlineGradientPaint;
    public final int outlineMiddleColor;
    public final Paint outlineSolidPaint;
    public final int outlineStartColor;
    public float rotationAngle;
    public int solidAlpha;

    public BaseBackgroundDrawable(Context context) {
        this.cornerRadius = context.getResources().getDimension(R.dimen.animated_action_button_corner_radius);
        float dimension = context.getResources().getDimension(R.dimen.animated_action_button_outline_stroke_width);
        this.insetVertical = 8 * context.getResources().getDisplayMetrics().density;
        this.buttonShape = new Path();
        int color = context.getColor(R.color.animated_action_button_stroke_color);
        Paint paint = new Paint(1);
        paint.setColor(context.getColor(android.R.color.sliding_tab_text_color_shadow));
        paint.setStyle(Paint.Style.FILL);
        this.bgPaint = paint;
        Paint paint2 = new Paint(1);
        paint2.setColor(color);
        Paint.Style style = Paint.Style.STROKE;
        paint2.setStyle(style);
        paint2.setStrokeWidth(dimension);
        this.outlineGradientPaint = paint2;
        Paint paint3 = new Paint(1);
        paint3.setColor(color);
        paint3.setStyle(style);
        paint3.setStrokeWidth(dimension);
        this.outlineSolidPaint = paint3;
        this.outlineStartColor = context.getColor(android.R.color.surface_variant_dark);
        this.outlineMiddleColor = context.getColor(android.R.color.secondary_text_material_light);
        this.outlineEndColor = context.getColor(android.R.color.secondary_text_inverse_when_activated_material);
        this.rotationAngle = 20.0f;
        this.gradientAlpha = 255;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(1500L);
        ofFloat.setInterpolator(Interpolators.LINEAR);
        ofFloat.setRepeatCount(0);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.row.BaseBackgroundDrawable$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                BaseBackgroundDrawable baseBackgroundDrawable = BaseBackgroundDrawable.this;
                baseBackgroundDrawable.rotationAngle = (floatValue * 360.0f) + 20.0f;
                baseBackgroundDrawable.invalidateSelf();
            }
        });
        ofFloat.start();
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat2.setDuration(500L);
        ofFloat2.setStartDelay(1000L);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.row.BaseBackgroundDrawable$2$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                BaseBackgroundDrawable baseBackgroundDrawable = BaseBackgroundDrawable.this;
                float f = 255;
                baseBackgroundDrawable.gradientAlpha = (int) ((1 - floatValue) * f);
                baseBackgroundDrawable.solidAlpha = (int) (floatValue * f);
                baseBackgroundDrawable.invalidateSelf();
            }
        });
        ofFloat2.start();
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        RectF rectF = new RectF(getBounds());
        rectF.inset(0.0f, this.insetVertical);
        this.buttonShape.reset();
        Path path = this.buttonShape;
        float f = this.cornerRadius;
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
        canvas.save();
        canvas.clipPath(this.buttonShape);
        canvas.drawPath(this.buttonShape, this.bgPaint);
        LinearGradient linearGradient = new LinearGradient(rectF.left, rectF.top, rectF.right, rectF.bottom, new int[]{this.outlineStartColor, this.outlineMiddleColor, this.outlineEndColor}, (float[]) null, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.setRotate(this.rotationAngle, rectF.centerX(), rectF.centerY());
        linearGradient.setLocalMatrix(matrix);
        this.outlineGradientPaint.setShader(linearGradient);
        this.outlineGradientPaint.setAlpha(this.gradientAlpha);
        canvas.drawPath(this.buttonShape, this.outlineGradientPaint);
        this.outlineSolidPaint.setAlpha(this.solidAlpha);
        canvas.drawPath(this.buttonShape, this.outlineSolidPaint);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        this.bgPaint.setAlpha(i);
        this.outlineGradientPaint.setAlpha(i);
        this.outlineSolidPaint.setAlpha(i);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.bgPaint.setColorFilter(colorFilter);
        this.outlineGradientPaint.setColorFilter(colorFilter);
        this.outlineSolidPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }
}

package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.math.MathUtils;
import com.google.android.material.progressindicator.DrawingDelegate;

/* loaded from: classes4.dex */
public final class CircularDrawingDelegate extends DrawingDelegate {
    public float adjustedRadius;
    public float displayedCornerRadius;
    public float displayedTrackThickness;
    public float totalTrackLengthFraction;
    public boolean useStrokeCap;

    public CircularDrawingDelegate(CircularProgressIndicatorSpec circularProgressIndicatorSpec) {
        super(circularProgressIndicatorSpec);
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final void adjustCanvas(Canvas canvas, Rect rect, float f, boolean z, boolean z2) {
        float fWidth = rect.width() / getSize();
        float fHeight = rect.height() / getSize();
        CircularProgressIndicatorSpec circularProgressIndicatorSpec = (CircularProgressIndicatorSpec) this.spec;
        float f2 = (circularProgressIndicatorSpec.indicatorSize / 2.0f) + circularProgressIndicatorSpec.indicatorInset;
        canvas.translate((f2 * fWidth) + rect.left, (f2 * fHeight) + rect.top);
        canvas.rotate(-90.0f);
        canvas.scale(fWidth, fHeight);
        if (circularProgressIndicatorSpec.indicatorDirection != 0) {
            canvas.scale(1.0f, -1.0f);
        }
        float f3 = -f2;
        canvas.clipRect(f3, f3, f2, f2);
        int i = circularProgressIndicatorSpec.trackThickness;
        this.useStrokeCap = i / 2 <= circularProgressIndicatorSpec.trackCornerRadius;
        this.displayedTrackThickness = i * f;
        this.displayedCornerRadius = Math.min(i / 2, r1) * f;
        int i2 = circularProgressIndicatorSpec.indicatorSize;
        int i3 = circularProgressIndicatorSpec.trackThickness;
        float f4 = (i2 - i3) / 2.0f;
        this.adjustedRadius = f4;
        if (z || z2) {
            if ((z && circularProgressIndicatorSpec.showAnimationBehavior == 2) || (z2 && circularProgressIndicatorSpec.hideAnimationBehavior == 1)) {
                this.adjustedRadius = (((1.0f - f) * i3) / 2.0f) + f4;
            } else if ((z && circularProgressIndicatorSpec.showAnimationBehavior == 1) || (z2 && circularProgressIndicatorSpec.hideAnimationBehavior == 2)) {
                this.adjustedRadius = f4 - (((1.0f - f) * i3) / 2.0f);
            }
        }
        if (z2 && circularProgressIndicatorSpec.hideAnimationBehavior == 3) {
            this.totalTrackLengthFraction = f;
        } else {
            this.totalTrackLengthFraction = 1.0f;
        }
    }

    public final void drawArc(Canvas canvas, Paint paint, float f, float f2, int i, int i2, int i3) {
        float f3 = f2 >= f ? f2 - f : (f2 + 1.0f) - f;
        float f4 = f % 1.0f;
        if (this.totalTrackLengthFraction < 1.0f) {
            float f5 = f4 + f3;
            if (f5 > 1.0f) {
                drawArc(canvas, paint, f4, 1.0f, i, i2, 0);
                drawArc(canvas, paint, 1.0f, f5, i, 0, i3);
                return;
            }
        }
        float degrees = (float) Math.toDegrees(this.displayedCornerRadius / this.adjustedRadius);
        if (f4 == 0.0f && f3 >= 0.99f) {
            f3 += (((degrees * 2.0f) / 360.0f) * (f3 - 0.99f)) / 0.01f;
        }
        float fLerp = MathUtils.lerp(1.0f - this.totalTrackLengthFraction, 1.0f, f4);
        float fLerp2 = MathUtils.lerp(0.0f, this.totalTrackLengthFraction, f3);
        float degrees2 = (float) Math.toDegrees(i2 / this.adjustedRadius);
        float degrees3 = ((fLerp2 * 360.0f) - degrees2) - ((float) Math.toDegrees(i3 / this.adjustedRadius));
        float f6 = (fLerp * 360.0f) + degrees2;
        if (degrees3 <= 0.0f) {
            return;
        }
        paint.setAntiAlias(true);
        paint.setColor(i);
        paint.setStrokeWidth(this.displayedTrackThickness);
        float f7 = degrees * 2.0f;
        if (degrees3 < f7) {
            float f8 = degrees3 / f7;
            paint.setStyle(Paint.Style.FILL);
            drawRoundedBlock(canvas, paint, (degrees * f8) + f6, this.displayedCornerRadius * 2.0f, this.displayedTrackThickness, f8);
            return;
        }
        float f9 = this.adjustedRadius;
        float f10 = -f9;
        RectF rectF = new RectF(f10, f10, f9, f9);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(this.useStrokeCap ? Paint.Cap.ROUND : Paint.Cap.BUTT);
        float f11 = f6 + degrees;
        canvas.drawArc(rectF, f11, degrees3 - f7, false, paint);
        if (this.useStrokeCap || this.displayedCornerRadius <= 0.0f) {
            return;
        }
        paint.setStyle(Paint.Style.FILL);
        drawRoundedBlock(canvas, paint, f11, this.displayedCornerRadius * 2.0f, this.displayedTrackThickness, 1.0f);
        drawRoundedBlock(canvas, paint, (f6 + degrees3) - degrees, this.displayedCornerRadius * 2.0f, this.displayedTrackThickness, 1.0f);
    }

    public final void drawRoundedBlock(Canvas canvas, Paint paint, float f, float f2, float f3, float f4) {
        float fMin = (int) Math.min(f3, this.displayedTrackThickness);
        float f5 = f2 / 2.0f;
        float fMin2 = Math.min(f5, (this.displayedCornerRadius * fMin) / this.displayedTrackThickness);
        RectF rectF = new RectF((-fMin) / 2.0f, (-f2) / 2.0f, fMin / 2.0f, f5);
        canvas.save();
        double d = f;
        canvas.translate((float) (Math.cos(Math.toRadians(d)) * this.adjustedRadius), (float) (Math.sin(Math.toRadians(d)) * this.adjustedRadius));
        canvas.rotate(f);
        canvas.scale(f4, f4);
        canvas.drawRoundRect(rectF, fMin2, fMin2, paint);
        canvas.restore();
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final void fillIndicator(Canvas canvas, Paint paint, DrawingDelegate.ActiveIndicator activeIndicator, int i) {
        int iCompositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(activeIndicator.color, i);
        float f = activeIndicator.startFraction;
        float f2 = activeIndicator.endFraction;
        int i2 = activeIndicator.gapSize;
        drawArc(canvas, paint, f, f2, iCompositeARGBWithAlpha, i2, i2);
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final void fillTrack(Canvas canvas, Paint paint, float f, float f2, int i, int i2, int i3) {
        drawArc(canvas, paint, f, f2, MaterialColors.compositeARGBWithAlpha(i, i2), i3, i3);
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final int getPreferredHeight() {
        return getSize();
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final int getPreferredWidth() {
        return getSize();
    }

    public final int getSize() {
        BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.spec;
        return (((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).indicatorInset * 2) + ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).indicatorSize;
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final void drawStopIndicator(Canvas canvas, Paint paint, int i, int i2) {
    }
}

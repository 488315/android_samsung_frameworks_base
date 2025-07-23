package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.core.math.MathUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.progressindicator.DrawingDelegate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class LinearDrawingDelegate extends DrawingDelegate {
    public float displayedCornerRadius;
    public float displayedTrackThickness;
    public float totalTrackLengthFraction;
    public float trackLength;
    public boolean useStrokeCap;

    public LinearDrawingDelegate(LinearProgressIndicatorSpec linearProgressIndicatorSpec) {
        super(linearProgressIndicatorSpec);
        this.trackLength = 300.0f;
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final void adjustCanvas(Canvas canvas, Rect rect, float f, boolean z, boolean z2) {
        this.trackLength = rect.width();
        LinearProgressIndicatorSpec linearProgressIndicatorSpec = (LinearProgressIndicatorSpec) this.spec;
        float f2 = linearProgressIndicatorSpec.trackThickness;
        canvas.translate((rect.width() / 2.0f) + rect.left, Math.max(0.0f, (rect.height() - f2) / 2.0f) + (rect.height() / 2.0f) + rect.top);
        if (linearProgressIndicatorSpec.drawHorizontallyInverse) {
            canvas.scale(-1.0f, 1.0f);
        }
        float f3 = this.trackLength / 2.0f;
        float f4 = f2 / 2.0f;
        canvas.clipRect(-f3, -f4, f3, f4);
        int i = linearProgressIndicatorSpec.trackThickness;
        this.useStrokeCap = i / 2 == linearProgressIndicatorSpec.trackCornerRadius;
        this.displayedTrackThickness = i * f;
        this.displayedCornerRadius = Math.min(i / 2, r6) * f;
        if (z || z2) {
            if ((z && linearProgressIndicatorSpec.showAnimationBehavior == 2) || (z2 && linearProgressIndicatorSpec.hideAnimationBehavior == 1)) {
                canvas.scale(1.0f, -1.0f);
            }
            if (z || (z2 && linearProgressIndicatorSpec.hideAnimationBehavior != 3)) {
                canvas.translate(0.0f, ((1.0f - f) * linearProgressIndicatorSpec.trackThickness) / 2.0f);
            }
        }
        if (z2 && linearProgressIndicatorSpec.hideAnimationBehavior == 3) {
            this.totalTrackLengthFraction = f;
        } else {
            this.totalTrackLengthFraction = 1.0f;
        }
    }

    public final void drawLine(Canvas canvas, Paint paint, float f, float f2, int i, int i2, int i3) {
        float f3;
        float clamp = MathUtils.clamp(f, 0.0f, 1.0f);
        float clamp2 = MathUtils.clamp(f2, 0.0f, 1.0f);
        float lerp = com.google.android.material.math.MathUtils.lerp(1.0f - this.totalTrackLengthFraction, 1.0f, clamp);
        float lerp2 = com.google.android.material.math.MathUtils.lerp(1.0f - this.totalTrackLengthFraction, 1.0f, clamp2);
        int clamp3 = (int) ((MathUtils.clamp(lerp, 0.0f, 0.01f) * i2) / 0.01f);
        float clamp4 = 1.0f - MathUtils.clamp(lerp2, 0.99f, 1.0f);
        float f4 = this.trackLength;
        int i4 = (int) ((lerp * f4) + clamp3);
        int i5 = (int) ((lerp2 * f4) - ((int) ((clamp4 * i3) / 0.01f)));
        float f5 = (-f4) / 2.0f;
        if (i4 <= i5) {
            float f6 = this.displayedCornerRadius;
            float f7 = i4 + f6;
            float f8 = i5 - f6;
            float f9 = f6 * 2.0f;
            paint.setColor(i);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(this.displayedTrackThickness);
            if (f7 >= f8) {
                drawRoundedBlock(canvas, paint, new PointF(f7 + f5, 0.0f), new PointF(f8 + f5, 0.0f), f9, this.displayedTrackThickness);
                return;
            }
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(this.useStrokeCap ? Paint.Cap.ROUND : Paint.Cap.BUTT);
            float f10 = f7 + f5;
            float f11 = f8 + f5;
            canvas.drawLine(f10, 0.0f, f11, 0.0f, paint);
            if (this.useStrokeCap || this.displayedCornerRadius <= 0.0f) {
                return;
            }
            paint.setStyle(Paint.Style.FILL);
            if (f7 > 0.0f) {
                f3 = f9;
                drawRoundedBlock(canvas, paint, new PointF(f10, 0.0f), null, f3, this.displayedTrackThickness);
            } else {
                f3 = f9;
            }
            if (f8 < this.trackLength) {
                drawRoundedBlock(canvas, paint, new PointF(f11, 0.0f), null, f3, this.displayedTrackThickness);
            }
        }
    }

    public final void drawRoundedBlock(Canvas canvas, Paint paint, PointF pointF, PointF pointF2, float f, float f2) {
        float min = Math.min(f2, this.displayedTrackThickness);
        float f3 = f / 2.0f;
        float min2 = Math.min(f3, (this.displayedCornerRadius * min) / this.displayedTrackThickness);
        RectF rectF = new RectF((-f) / 2.0f, (-min) / 2.0f, f3, min / 2.0f);
        paint.setStyle(Paint.Style.FILL);
        canvas.save();
        if (pointF2 != null) {
            canvas.translate(pointF2.x, pointF2.y);
            Path path = new Path();
            path.addRoundRect(rectF, min2, min2, Path.Direction.CCW);
            canvas.clipPath(path);
            canvas.translate(-pointF2.x, -pointF2.y);
        }
        canvas.translate(pointF.x, pointF.y);
        canvas.drawRoundRect(rectF, min2, min2, paint);
        canvas.restore();
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final void drawStopIndicator(Canvas canvas, Paint paint, int i, int i2) {
        int compositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(i, i2);
        LinearProgressIndicatorSpec linearProgressIndicatorSpec = (LinearProgressIndicatorSpec) this.spec;
        if (linearProgressIndicatorSpec.trackStopIndicatorSize <= 0 || compositeARGBWithAlpha == 0) {
            return;
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(compositeARGBWithAlpha);
        PointF pointF = new PointF((this.trackLength / 2.0f) - (this.displayedTrackThickness / 2.0f), 0.0f);
        int i3 = linearProgressIndicatorSpec.trackStopIndicatorSize;
        drawRoundedBlock(canvas, paint, pointF, null, i3, i3);
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final void fillIndicator(Canvas canvas, Paint paint, DrawingDelegate.ActiveIndicator activeIndicator, int i) {
        int compositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(activeIndicator.color, i);
        float f = activeIndicator.startFraction;
        float f2 = activeIndicator.endFraction;
        int i2 = activeIndicator.gapSize;
        drawLine(canvas, paint, f, f2, compositeARGBWithAlpha, i2, i2);
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final void fillTrack(Canvas canvas, Paint paint, float f, float f2, int i, int i2, int i3) {
        drawLine(canvas, paint, f, f2, MaterialColors.compositeARGBWithAlpha(i, i2), i3, i3);
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final int getPreferredHeight() {
        return ((LinearProgressIndicatorSpec) this.spec).trackThickness;
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final int getPreferredWidth() {
        return -1;
    }
}

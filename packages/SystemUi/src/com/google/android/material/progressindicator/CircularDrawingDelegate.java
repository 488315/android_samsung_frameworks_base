package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.google.android.material.color.MaterialColors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CircularDrawingDelegate extends DrawingDelegate {
    public float adjustedRadius;
    public int arcDirectionFactor;
    public float displayedCornerRadius;
    public float displayedTrackThickness;

    public CircularDrawingDelegate(CircularProgressIndicatorSpec circularProgressIndicatorSpec) {
        super(circularProgressIndicatorSpec);
        this.arcDirectionFactor = 1;
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final void adjustCanvas(Canvas canvas, Rect rect, float f) {
        float width = rect.width() / getSize();
        float height = rect.height() / getSize();
        BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.spec;
        float f2 = (((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).indicatorSize / 2.0f) + ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).indicatorInset;
        canvas.translate((f2 * width) + rect.left, (f2 * height) + rect.top);
        canvas.scale(width, height);
        canvas.rotate(-90.0f);
        float f3 = -f2;
        canvas.clipRect(f3, f3, f2, f2);
        this.arcDirectionFactor = ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).indicatorDirection == 0 ? 1 : -1;
        this.displayedTrackThickness = ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).trackThickness * f;
        this.displayedCornerRadius = ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).trackCornerRadius * f;
        this.adjustedRadius = (((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).indicatorSize - ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).trackThickness) / 2.0f;
        if ((this.drawable.isShowing() && ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).showAnimationBehavior == 2) || (this.drawable.isHiding() && ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).hideAnimationBehavior == 1)) {
            this.adjustedRadius = (((1.0f - f) * ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).trackThickness) / 2.0f) + this.adjustedRadius;
        } else if ((this.drawable.isShowing() && ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).showAnimationBehavior == 1) || (this.drawable.isHiding() && ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).hideAnimationBehavior == 2)) {
            this.adjustedRadius -= ((1.0f - f) * ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).trackThickness) / 2.0f;
        }
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final void fillIndicator(Canvas canvas, Paint paint, float f, float f2, int i) {
        if (f == f2) {
            return;
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setAntiAlias(true);
        paint.setColor(i);
        paint.setStrokeWidth(this.displayedTrackThickness);
        float f3 = this.arcDirectionFactor;
        float f4 = f * 360.0f * f3;
        if (f2 < f) {
            f2 += 1.0f;
        }
        float f5 = (f2 - f) * 360.0f * f3;
        float f6 = this.adjustedRadius;
        float f7 = -f6;
        canvas.drawArc(new RectF(f7, f7, f6, f6), f4, f5, false, paint);
        if (this.displayedCornerRadius <= 0.0f || Math.abs(f5) >= 360.0f) {
            return;
        }
        paint.setStyle(Paint.Style.FILL);
        float f8 = this.displayedTrackThickness;
        float f9 = this.displayedCornerRadius;
        canvas.save();
        canvas.rotate(f4);
        float f10 = this.adjustedRadius;
        float f11 = f8 / 2.0f;
        canvas.drawRoundRect(new RectF(f10 - f11, f9, f10 + f11, -f9), f9, f9, paint);
        canvas.restore();
        float f12 = this.displayedTrackThickness;
        float f13 = this.displayedCornerRadius;
        canvas.save();
        canvas.rotate(f4 + f5);
        float f14 = this.adjustedRadius;
        float f15 = f12 / 2.0f;
        canvas.drawRoundRect(new RectF(f14 - f15, f13, f14 + f15, -f13), f13, f13, paint);
        canvas.restore();
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final void fillTrack(Canvas canvas, Paint paint) {
        int compositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(((CircularProgressIndicatorSpec) this.spec).trackColor, this.drawable.totalAlpha);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setAntiAlias(true);
        paint.setColor(compositeARGBWithAlpha);
        paint.setStrokeWidth(this.displayedTrackThickness);
        float f = this.adjustedRadius;
        canvas.drawArc(new RectF(-f, -f, f, f), 0.0f, 360.0f, false, paint);
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
}

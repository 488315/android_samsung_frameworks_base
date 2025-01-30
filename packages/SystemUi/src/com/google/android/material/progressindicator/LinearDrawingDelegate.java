package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.constraintlayout.motion.widget.MotionPaths$$ExternalSyntheticOutline0;
import com.google.android.material.color.MaterialColors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LinearDrawingDelegate extends DrawingDelegate {
    public float displayedCornerRadius;
    public float displayedTrackThickness;
    public float trackLength;

    public LinearDrawingDelegate(LinearProgressIndicatorSpec linearProgressIndicatorSpec) {
        super(linearProgressIndicatorSpec);
        this.trackLength = 300.0f;
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final void adjustCanvas(Canvas canvas, Rect rect, float f) {
        this.trackLength = rect.width();
        BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.spec;
        float f2 = ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackThickness;
        canvas.translate((rect.width() / 2.0f) + rect.left, Math.max(0.0f, (rect.height() - ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackThickness) / 2.0f) + (rect.height() / 2.0f) + rect.top);
        if (((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).drawHorizontallyInverse) {
            canvas.scale(-1.0f, 1.0f);
        }
        if ((this.drawable.isShowing() && ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).showAnimationBehavior == 1) || (this.drawable.isHiding() && ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).hideAnimationBehavior == 2)) {
            canvas.scale(1.0f, -1.0f);
        }
        if (this.drawable.isShowing() || this.drawable.isHiding()) {
            canvas.translate(0.0f, ((f - 1.0f) * ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackThickness) / 2.0f);
        }
        float f3 = this.trackLength;
        canvas.clipRect((-f3) / 2.0f, (-f2) / 2.0f, f3 / 2.0f, f2 / 2.0f);
        this.displayedTrackThickness = ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackThickness * f;
        this.displayedCornerRadius = ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackCornerRadius * f;
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final void fillIndicator(Canvas canvas, Paint paint, float f, float f2, int i) {
        if (f == f2) {
            return;
        }
        float f3 = this.trackLength;
        float f4 = (-f3) / 2.0f;
        float f5 = this.displayedCornerRadius * 2.0f;
        float f6 = f3 - f5;
        float f7 = (f * f6) + f4;
        float m24m = MotionPaths$$ExternalSyntheticOutline0.m24m(f6, f2, f4, f5);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(i);
        float f8 = this.displayedTrackThickness;
        RectF rectF = new RectF(f7, (-f8) / 2.0f, m24m, f8 / 2.0f);
        float f9 = this.displayedCornerRadius;
        canvas.drawRoundRect(rectF, f9, f9, paint);
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public final void fillTrack(Canvas canvas, Paint paint) {
        int compositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(((LinearProgressIndicatorSpec) this.spec).trackColor, this.drawable.totalAlpha);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(compositeARGBWithAlpha);
        float f = this.trackLength;
        float f2 = this.displayedTrackThickness;
        RectF rectF = new RectF((-f) / 2.0f, (-f2) / 2.0f, f / 2.0f, f2 / 2.0f);
        float f3 = this.displayedCornerRadius;
        canvas.drawRoundRect(rectF, f3, f3, paint);
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

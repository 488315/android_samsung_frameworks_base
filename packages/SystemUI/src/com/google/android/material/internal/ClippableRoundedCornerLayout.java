package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ClippableRoundedCornerLayout extends FrameLayout {
    public float cornerRadius;
    public Path path;

    public ClippableRoundedCornerLayout(Context context) {
        super(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        if (this.path == null) {
            super.dispatchDraw(canvas);
            return;
        }
        int save = canvas.save();
        canvas.clipPath(this.path);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(save);
    }

    public final void updateClipBoundsAndCornerRadius(float f, float f2, float f3, float f4, float f5) {
        RectF rectF = new RectF(f, f2, f3, f4);
        if (this.path == null) {
            this.path = new Path();
        }
        this.cornerRadius = f5;
        this.path.reset();
        this.path.addRoundRect(rectF, f5, f5, Path.Direction.CW);
        this.path.close();
        invalidate();
    }

    public ClippableRoundedCornerLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ClippableRoundedCornerLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}

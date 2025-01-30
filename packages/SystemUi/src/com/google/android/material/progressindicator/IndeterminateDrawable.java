package com.google.android.material.progressindicator;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.Settings;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class IndeterminateDrawable extends DrawableWithAnimatedVisibilityChange {
    public IndeterminateAnimatorDelegate animatorDelegate;
    public DrawingDelegate drawingDelegate;

    public IndeterminateDrawable(Context context, BaseProgressIndicatorSpec baseProgressIndicatorSpec, DrawingDelegate drawingDelegate, IndeterminateAnimatorDelegate indeterminateAnimatorDelegate) {
        super(context, baseProgressIndicatorSpec);
        this.drawingDelegate = drawingDelegate;
        drawingDelegate.drawable = this;
        this.animatorDelegate = indeterminateAnimatorDelegate;
        indeterminateAnimatorDelegate.drawable = this;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Rect rect = new Rect();
        if (getBounds().isEmpty() || !isVisible() || !canvas.getClipBounds(rect)) {
            return;
        }
        canvas.save();
        DrawingDelegate drawingDelegate = this.drawingDelegate;
        Rect bounds = getBounds();
        float growFraction = getGrowFraction();
        drawingDelegate.spec.validateSpec();
        drawingDelegate.adjustCanvas(canvas, bounds, growFraction);
        this.drawingDelegate.fillTrack(canvas, this.paint);
        int i = 0;
        while (true) {
            IndeterminateAnimatorDelegate indeterminateAnimatorDelegate = this.animatorDelegate;
            int[] iArr = indeterminateAnimatorDelegate.segmentColors;
            if (i >= iArr.length) {
                canvas.restore();
                return;
            }
            DrawingDelegate drawingDelegate2 = this.drawingDelegate;
            Paint paint = this.paint;
            int i2 = i * 2;
            float[] fArr = indeterminateAnimatorDelegate.segmentPositions;
            drawingDelegate2.fillIndicator(canvas, paint, fArr[i2], fArr[i2 + 1], iArr[i]);
            i++;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.drawingDelegate.getPreferredHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.drawingDelegate.getPreferredWidth();
    }

    @Override // com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange
    public final boolean setVisibleInternal(boolean z, boolean z2, boolean z3) {
        boolean visibleInternal = super.setVisibleInternal(z, z2, z3);
        if (!isRunning()) {
            this.animatorDelegate.cancelAnimatorImmediately();
        }
        AnimatorDurationScaleProvider animatorDurationScaleProvider = this.animatorDurationScaleProvider;
        ContentResolver contentResolver = this.context.getContentResolver();
        animatorDurationScaleProvider.getClass();
        Settings.Global.getFloat(contentResolver, "animator_duration_scale", 1.0f);
        if (z && z3) {
            this.animatorDelegate.startAnimator();
        }
        return visibleInternal;
    }
}

package com.google.android.material.progressindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import com.android.systemui.util.SettingsHelper;
import com.google.android.material.progressindicator.DrawingDelegate;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class IndeterminateDrawable extends DrawableWithAnimatedVisibilityChange {
    public final IndeterminateAnimatorDelegate animatorDelegate;
    public final DrawingDelegate drawingDelegate;
    public Drawable staticDummyDrawable;

    public IndeterminateDrawable(Context context, BaseProgressIndicatorSpec baseProgressIndicatorSpec, DrawingDelegate drawingDelegate, IndeterminateAnimatorDelegate indeterminateAnimatorDelegate) {
        super(context, baseProgressIndicatorSpec);
        this.drawingDelegate = drawingDelegate;
        this.animatorDelegate = indeterminateAnimatorDelegate;
        indeterminateAnimatorDelegate.drawable = this;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        int i;
        Drawable drawable;
        Rect rect = new Rect();
        if (!getBounds().isEmpty() && isVisible() && canvas.getClipBounds(rect)) {
            if ((this.animatorDurationScaleProvider != null && Settings.Global.getFloat(this.context.getContentResolver(), SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1.0f) == 0.0f) && (drawable = this.staticDummyDrawable) != null) {
                drawable.setBounds(getBounds());
                this.staticDummyDrawable.setTint(this.baseSpec.indicatorColors[0]);
                this.staticDummyDrawable.draw(canvas);
                return;
            }
            canvas.save();
            DrawingDelegate drawingDelegate = this.drawingDelegate;
            Rect bounds = getBounds();
            float growFraction = getGrowFraction();
            boolean isShowing = isShowing();
            boolean isHiding = isHiding();
            drawingDelegate.spec.validateSpec();
            drawingDelegate.adjustCanvas(canvas, bounds, growFraction, isShowing, isHiding);
            BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.baseSpec;
            int i2 = baseProgressIndicatorSpec.indicatorTrackGapSize;
            int i3 = this.totalAlpha;
            if (i2 == 0) {
                this.drawingDelegate.fillTrack(canvas, this.paint, 0.0f, 1.0f, baseProgressIndicatorSpec.trackColor, i3, 0);
                i = i2;
            } else {
                DrawingDelegate.ActiveIndicator activeIndicator = (DrawingDelegate.ActiveIndicator) ((ArrayList) this.animatorDelegate.activeIndicators).get(0);
                DrawingDelegate.ActiveIndicator activeIndicator2 = (DrawingDelegate.ActiveIndicator) AlertController$$ExternalSyntheticOutline0.m((ArrayList) this.animatorDelegate.activeIndicators, 1);
                DrawingDelegate drawingDelegate2 = this.drawingDelegate;
                if (drawingDelegate2 instanceof LinearDrawingDelegate) {
                    i = i2;
                    drawingDelegate2.fillTrack(canvas, this.paint, 0.0f, activeIndicator.startFraction, this.baseSpec.trackColor, i3, i);
                    this.drawingDelegate.fillTrack(canvas, this.paint, activeIndicator2.endFraction, 1.0f, this.baseSpec.trackColor, i3, i);
                } else {
                    i = i2;
                    i3 = 0;
                    drawingDelegate2.fillTrack(canvas, this.paint, activeIndicator2.endFraction, activeIndicator.startFraction + 1.0f, this.baseSpec.trackColor, 0, i);
                }
            }
            for (int i4 = 0; i4 < ((ArrayList) this.animatorDelegate.activeIndicators).size(); i4++) {
                DrawingDelegate.ActiveIndicator activeIndicator3 = (DrawingDelegate.ActiveIndicator) ((ArrayList) this.animatorDelegate.activeIndicators).get(i4);
                this.drawingDelegate.fillIndicator(canvas, this.paint, activeIndicator3, this.totalAlpha);
                if (i4 > 0 && i > 0) {
                    this.drawingDelegate.fillTrack(canvas, this.paint, ((DrawingDelegate.ActiveIndicator) ((ArrayList) this.animatorDelegate.activeIndicators).get(i4 - 1)).endFraction, activeIndicator3.startFraction, this.baseSpec.trackColor, i3, i);
                }
            }
            canvas.restore();
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

    public void setStaticDummyDrawable(Drawable drawable) {
        this.staticDummyDrawable = drawable;
    }

    @Override // com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange
    public final boolean setVisibleInternal(boolean z, boolean z2, boolean z3) {
        Drawable drawable;
        boolean visibleInternal = super.setVisibleInternal(z, z2, z3);
        if (this.animatorDurationScaleProvider != null && Settings.Global.getFloat(this.context.getContentResolver(), SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1.0f) == 0.0f && (drawable = this.staticDummyDrawable) != null) {
            return drawable.setVisible(z, z2);
        }
        if (!isRunning()) {
            this.animatorDelegate.cancelAnimatorImmediately();
        }
        if (z && z3) {
            this.animatorDelegate.startAnimator();
        }
        return visibleInternal;
    }
}

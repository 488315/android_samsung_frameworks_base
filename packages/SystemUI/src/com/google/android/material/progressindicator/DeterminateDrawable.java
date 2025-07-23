package com.google.android.material.progressindicator;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.Settings;
import androidx.core.math.MathUtils;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.systemui.util.SettingsHelper;
import com.google.android.material.progressindicator.DrawingDelegate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class DeterminateDrawable extends DrawableWithAnimatedVisibilityChange {
    public static final AnonymousClass1 INDICATOR_LENGTH_IN_LEVEL = new FloatPropertyCompat("indicatorLevel") { // from class: com.google.android.material.progressindicator.DeterminateDrawable.1
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final float getValue(Object obj) {
            return ((DeterminateDrawable) obj).activeIndicator.endFraction * 10000.0f;
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final void setValue(Object obj, float f) {
            DeterminateDrawable determinateDrawable = (DeterminateDrawable) obj;
            AnonymousClass1 anonymousClass1 = DeterminateDrawable.INDICATOR_LENGTH_IN_LEVEL;
            determinateDrawable.activeIndicator.endFraction = f / 10000.0f;
            determinateDrawable.invalidateSelf();
        }
    };
    public final DrawingDelegate.ActiveIndicator activeIndicator;
    public final DrawingDelegate drawingDelegate;
    public boolean skipAnimationOnLevelChange;
    public final SpringAnimation springAnimation;
    public final SpringForce springForce;

    public DeterminateDrawable(Context context, BaseProgressIndicatorSpec baseProgressIndicatorSpec, DrawingDelegate drawingDelegate) {
        super(context, baseProgressIndicatorSpec);
        this.skipAnimationOnLevelChange = false;
        this.drawingDelegate = drawingDelegate;
        this.activeIndicator = new DrawingDelegate.ActiveIndicator();
        SpringForce springForce = new SpringForce();
        this.springForce = springForce;
        springForce.setDampingRatio(1.0f);
        springForce.setStiffness(50.0f);
        SpringAnimation springAnimation = new SpringAnimation(this, INDICATOR_LENGTH_IN_LEVEL);
        this.springAnimation = springAnimation;
        springAnimation.mSpring = springForce;
        if (this.growFraction != 1.0f) {
            this.growFraction = 1.0f;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Rect rect = new Rect();
        if (!getBounds().isEmpty() && isVisible() && canvas.getClipBounds(rect)) {
            canvas.save();
            DrawingDelegate drawingDelegate = this.drawingDelegate;
            Rect bounds = getBounds();
            float growFraction = getGrowFraction();
            boolean isShowing = isShowing();
            boolean isHiding = isHiding();
            drawingDelegate.spec.validateSpec();
            drawingDelegate.adjustCanvas(canvas, bounds, growFraction, isShowing, isHiding);
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setAntiAlias(true);
            DrawingDelegate.ActiveIndicator activeIndicator = this.activeIndicator;
            BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.baseSpec;
            activeIndicator.color = baseProgressIndicatorSpec.indicatorColors[0];
            int i = baseProgressIndicatorSpec.indicatorTrackGapSize;
            if (i > 0) {
                if (!(this.drawingDelegate instanceof LinearDrawingDelegate)) {
                    i = (int) ((MathUtils.clamp(activeIndicator.endFraction, 0.0f, 0.01f) * i) / 0.01f);
                }
                this.drawingDelegate.fillTrack(canvas, this.paint, this.activeIndicator.endFraction, 1.0f, this.baseSpec.trackColor, this.totalAlpha, i);
            } else {
                this.drawingDelegate.fillTrack(canvas, this.paint, 0.0f, 1.0f, baseProgressIndicatorSpec.trackColor, this.totalAlpha, 0);
            }
            this.drawingDelegate.fillIndicator(canvas, this.paint, this.activeIndicator, this.totalAlpha);
            this.drawingDelegate.drawStopIndicator(canvas, this.paint, this.baseSpec.indicatorColors[0], this.totalAlpha);
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

    @Override // android.graphics.drawable.Drawable
    public final void jumpToCurrentState() {
        this.springAnimation.skipToEnd();
        this.activeIndicator.endFraction = getLevel() / 10000.0f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onLevelChange(int i) {
        if (!this.skipAnimationOnLevelChange) {
            this.springAnimation.setStartValue(this.activeIndicator.endFraction * 10000.0f);
            this.springAnimation.animateToFinalPosition(i);
            return true;
        }
        this.springAnimation.skipToEnd();
        this.activeIndicator.endFraction = i / 10000.0f;
        invalidateSelf();
        return true;
    }

    @Override // com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange
    public final boolean setVisibleInternal(boolean z, boolean z2, boolean z3) {
        boolean visibleInternal = super.setVisibleInternal(z, z2, z3);
        AnimatorDurationScaleProvider animatorDurationScaleProvider = this.animatorDurationScaleProvider;
        ContentResolver contentResolver = this.context.getContentResolver();
        animatorDurationScaleProvider.getClass();
        float f = Settings.Global.getFloat(contentResolver, SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1.0f);
        if (f == 0.0f) {
            this.skipAnimationOnLevelChange = true;
            return visibleInternal;
        }
        this.skipAnimationOnLevelChange = false;
        this.springForce.setStiffness(50.0f / f);
        return visibleInternal;
    }
}

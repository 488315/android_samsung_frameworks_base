package com.google.android.material.progressindicator;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.provider.Settings;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.google.android.material.color.MaterialColors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DeterminateDrawable extends DrawableWithAnimatedVisibilityChange {
    public static final C43351 INDICATOR_LENGTH_IN_LEVEL = new FloatPropertyCompat("indicatorLevel") { // from class: com.google.android.material.progressindicator.DeterminateDrawable.1
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final float getValue(Object obj) {
            return ((DeterminateDrawable) obj).indicatorFraction * 10000.0f;
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final void setValue(Object obj, float f) {
            DeterminateDrawable determinateDrawable = (DeterminateDrawable) obj;
            C43351 c43351 = DeterminateDrawable.INDICATOR_LENGTH_IN_LEVEL;
            determinateDrawable.indicatorFraction = f / 10000.0f;
            determinateDrawable.invalidateSelf();
        }
    };
    public DrawingDelegate drawingDelegate;
    public float indicatorFraction;
    public boolean skipAnimationOnLevelChange;
    public final SpringAnimation springAnimation;
    public final SpringForce springForce;

    public DeterminateDrawable(Context context, BaseProgressIndicatorSpec baseProgressIndicatorSpec, DrawingDelegate drawingDelegate) {
        super(context, baseProgressIndicatorSpec);
        this.skipAnimationOnLevelChange = false;
        this.drawingDelegate = drawingDelegate;
        drawingDelegate.drawable = this;
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
            drawingDelegate.spec.validateSpec();
            drawingDelegate.adjustCanvas(canvas, bounds, growFraction);
            this.drawingDelegate.fillTrack(canvas, this.paint);
            this.drawingDelegate.fillIndicator(canvas, this.paint, 0.0f, this.indicatorFraction, MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[0], this.totalAlpha));
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
        this.indicatorFraction = getLevel() / 10000.0f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onLevelChange(int i) {
        if (!this.skipAnimationOnLevelChange) {
            this.springAnimation.setStartValue(this.indicatorFraction * 10000.0f);
            this.springAnimation.animateToFinalPosition(i);
            return true;
        }
        this.springAnimation.skipToEnd();
        this.indicatorFraction = i / 10000.0f;
        invalidateSelf();
        return true;
    }

    @Override // com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange
    public final boolean setVisibleInternal(boolean z, boolean z2, boolean z3) {
        boolean visibleInternal = super.setVisibleInternal(z, z2, z3);
        AnimatorDurationScaleProvider animatorDurationScaleProvider = this.animatorDurationScaleProvider;
        ContentResolver contentResolver = this.context.getContentResolver();
        animatorDurationScaleProvider.getClass();
        float f = Settings.Global.getFloat(contentResolver, "animator_duration_scale", 1.0f);
        if (f == 0.0f) {
            this.skipAnimationOnLevelChange = true;
        } else {
            this.skipAnimationOnLevelChange = false;
            this.springForce.setStiffness(50.0f / f);
        }
        return visibleInternal;
    }
}

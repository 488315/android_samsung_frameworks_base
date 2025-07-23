package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.graphics.Rect;
import android.util.Property;
import android.view.View;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class FloatingActionButtonImplLollipop extends FloatingActionButtonImpl {
    public StateListAnimator stateListAnimator;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AlwaysStatefulMaterialShapeDrawable extends MaterialShapeDrawable {
        public AlwaysStatefulMaterialShapeDrawable(ShapeAppearanceModel shapeAppearanceModel) {
            super(shapeAppearanceModel);
        }

        @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
        public final boolean isStateful() {
            return true;
        }
    }

    public FloatingActionButtonImplLollipop(FloatingActionButton floatingActionButton, ShadowViewDelegate shadowViewDelegate) {
        super(floatingActionButton, shadowViewDelegate);
    }

    public final Animator createElevationAnimator(float f, float f2) {
        AnimatorSet animatorSet = new AnimatorSet();
        FloatingActionButton floatingActionButton = this.view;
        animatorSet.play(ObjectAnimator.ofFloat(floatingActionButton, "elevation", f).setDuration(0L)).with(ObjectAnimator.ofFloat(floatingActionButton, (Property<FloatingActionButton, Float>) View.TRANSLATION_Z, f2).setDuration(100L));
        animatorSet.setInterpolator(FloatingActionButtonImpl.ELEVATION_ANIM_INTERPOLATOR);
        return animatorSet;
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    public final float getElevation() {
        return this.view.getElevation();
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    public final void getPadding(Rect rect) {
        if (FloatingActionButton.this.compatPadding) {
            super.getPadding(rect);
            return;
        }
        if (this.ensureMinTouchTargetSize) {
            FloatingActionButton floatingActionButton = this.view;
            int sizeDimension = floatingActionButton.getSizeDimension(floatingActionButton.size);
            int i = this.minTouchTargetSize;
            if (sizeDimension < i) {
                int sizeDimension2 = (i - floatingActionButton.getSizeDimension(floatingActionButton.size)) / 2;
                rect.set(sizeDimension2, sizeDimension2, sizeDimension2, sizeDimension2);
                return;
            }
        }
        rect.set(0, 0, 0, 0);
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    public final void onElevationsChanged(float f, float f2, float f3) {
        FloatingActionButton floatingActionButton = this.view;
        if (floatingActionButton.getStateListAnimator() == this.stateListAnimator) {
            StateListAnimator stateListAnimator = new StateListAnimator();
            stateListAnimator.addState(FloatingActionButtonImpl.PRESSED_ENABLED_STATE_SET, createElevationAnimator(f, f3));
            stateListAnimator.addState(FloatingActionButtonImpl.HOVERED_FOCUSED_ENABLED_STATE_SET, createElevationAnimator(f, f2));
            stateListAnimator.addState(FloatingActionButtonImpl.FOCUSED_ENABLED_STATE_SET, createElevationAnimator(f, f2));
            stateListAnimator.addState(FloatingActionButtonImpl.HOVERED_ENABLED_STATE_SET, createElevationAnimator(f, f2));
            AnimatorSet animatorSet = new AnimatorSet();
            ArrayList arrayList = new ArrayList();
            arrayList.add(ObjectAnimator.ofFloat(floatingActionButton, "elevation", f).setDuration(0L));
            arrayList.add(ObjectAnimator.ofFloat(floatingActionButton, (Property<FloatingActionButton, Float>) View.TRANSLATION_Z, 0.0f).setDuration(100L));
            animatorSet.playSequentially((Animator[]) arrayList.toArray(new Animator[0]));
            animatorSet.setInterpolator(FloatingActionButtonImpl.ELEVATION_ANIM_INTERPOLATOR);
            stateListAnimator.addState(FloatingActionButtonImpl.ENABLED_STATE_SET, animatorSet);
            stateListAnimator.addState(FloatingActionButtonImpl.EMPTY_STATE_SET, createElevationAnimator(0.0f, 0.0f));
            this.stateListAnimator = stateListAnimator;
            floatingActionButton.setStateListAnimator(stateListAnimator);
        }
        if (shouldAddPadding()) {
            updatePadding();
        }
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    public final boolean shouldAddPadding() {
        if (FloatingActionButton.this.compatPadding) {
            return true;
        }
        if (!this.ensureMinTouchTargetSize) {
            return false;
        }
        FloatingActionButton floatingActionButton = this.view;
        return floatingActionButton.getSizeDimension(floatingActionButton.size) < this.minTouchTargetSize;
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    public final void jumpDrawableToCurrentState() {
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    public final void updateFromViewRotation() {
    }
}

package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.Property;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.android.systemui.R;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FloatingActionButtonImplLollipop extends FloatingActionButtonImpl {
    public StateListAnimator stateListAnimator;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AlwaysStatefulMaterialShapeDrawable extends MaterialShapeDrawable {
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
    public final MaterialShapeDrawable createShapeDrawable() {
        ShapeAppearanceModel shapeAppearanceModel = this.shapeAppearance;
        shapeAppearanceModel.getClass();
        return new AlwaysStatefulMaterialShapeDrawable(shapeAppearanceModel);
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
        boolean z = this.ensureMinTouchTargetSize;
        FloatingActionButton floatingActionButton = this.view;
        if (!z || floatingActionButton.getSizeDimension(floatingActionButton.size) >= this.minTouchTargetSize) {
            rect.set(0, 0, 0, 0);
        } else {
            int sizeDimension = (this.minTouchTargetSize - floatingActionButton.getSizeDimension(floatingActionButton.size)) / 2;
            rect.set(sizeDimension, sizeDimension, sizeDimension, sizeDimension);
        }
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    public final void initializeBackgroundDrawable(ColorStateList colorStateList, PorterDuff.Mode mode, ColorStateList colorStateList2, int i) {
        Drawable drawable;
        MaterialShapeDrawable createShapeDrawable = createShapeDrawable();
        this.shapeDrawable = createShapeDrawable;
        createShapeDrawable.setTintList(colorStateList);
        if (mode != null) {
            this.shapeDrawable.setTintMode(mode);
        }
        MaterialShapeDrawable materialShapeDrawable = this.shapeDrawable;
        FloatingActionButton floatingActionButton = this.view;
        materialShapeDrawable.initializeElevationOverlay(floatingActionButton.getContext());
        if (i > 0) {
            Context context = floatingActionButton.getContext();
            ShapeAppearanceModel shapeAppearanceModel = this.shapeAppearance;
            shapeAppearanceModel.getClass();
            BorderDrawable borderDrawable = new BorderDrawable(shapeAppearanceModel);
            Object obj = ContextCompat.sLock;
            int color = context.getColor(R.color.design_fab_stroke_top_outer_color);
            int color2 = context.getColor(R.color.design_fab_stroke_top_inner_color);
            int color3 = context.getColor(R.color.design_fab_stroke_end_inner_color);
            int color4 = context.getColor(R.color.design_fab_stroke_end_outer_color);
            borderDrawable.topOuterStrokeColor = color;
            borderDrawable.topInnerStrokeColor = color2;
            borderDrawable.bottomOuterStrokeColor = color3;
            borderDrawable.bottomInnerStrokeColor = color4;
            float f = i;
            if (borderDrawable.borderWidth != f) {
                borderDrawable.borderWidth = f;
                borderDrawable.paint.setStrokeWidth(f * 1.3333f);
                borderDrawable.invalidateShader = true;
                borderDrawable.invalidateSelf();
            }
            if (colorStateList != null) {
                borderDrawable.currentBorderTintColor = colorStateList.getColorForState(borderDrawable.getState(), borderDrawable.currentBorderTintColor);
            }
            borderDrawable.borderTint = colorStateList;
            borderDrawable.invalidateShader = true;
            borderDrawable.invalidateSelf();
            this.borderDrawable = borderDrawable;
            BorderDrawable borderDrawable2 = this.borderDrawable;
            borderDrawable2.getClass();
            MaterialShapeDrawable materialShapeDrawable2 = this.shapeDrawable;
            materialShapeDrawable2.getClass();
            drawable = new LayerDrawable(new Drawable[]{borderDrawable2, materialShapeDrawable2});
        } else {
            this.borderDrawable = null;
            drawable = this.shapeDrawable;
        }
        RippleDrawable rippleDrawable = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(colorStateList2), drawable, null);
        this.rippleDrawable = rippleDrawable;
        this.contentBackground = rippleDrawable;
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

    /* JADX WARN: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldAddPadding() {
        boolean z;
        if (FloatingActionButton.this.compatPadding) {
            return true;
        }
        if (this.ensureMinTouchTargetSize) {
            FloatingActionButton floatingActionButton = this.view;
            if (floatingActionButton.getSizeDimension(floatingActionButton.size) < this.minTouchTargetSize) {
                z = false;
                return z;
            }
        }
        z = true;
        if (z) {
        }
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    public final void onDrawableStateChanged(int[] iArr) {
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    public final void jumpDrawableToCurrentState() {
    }

    @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl
    public final void updateFromViewRotation() {
    }
}

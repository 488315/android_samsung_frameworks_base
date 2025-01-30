package com.google.android.material.ripple;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RippleDrawableCompat extends Drawable implements Shapeable {
    public RippleDrawableCompatState drawableState;

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        RippleDrawableCompatState rippleDrawableCompatState = this.drawableState;
        if (rippleDrawableCompatState.shouldDrawDelegate) {
            rippleDrawableCompatState.delegate.draw(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        return this.drawableState;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return this.drawableState.delegate.getOpacity();
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable mutate() {
        this.drawableState = new RippleDrawableCompatState(this.drawableState);
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.drawableState.delegate.setBounds(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
        boolean onStateChange = super.onStateChange(iArr);
        if (this.drawableState.delegate.setState(iArr)) {
            onStateChange = true;
        }
        boolean shouldDrawRippleCompat = RippleUtils.shouldDrawRippleCompat(iArr);
        RippleDrawableCompatState rippleDrawableCompatState = this.drawableState;
        if (rippleDrawableCompatState.shouldDrawDelegate == shouldDrawRippleCompat) {
            return onStateChange;
        }
        rippleDrawableCompatState.shouldDrawDelegate = shouldDrawRippleCompat;
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        this.drawableState.delegate.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.drawableState.delegate.setColorFilter(colorFilter);
    }

    @Override // com.google.android.material.shape.Shapeable
    public final void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.drawableState.delegate.setShapeAppearanceModel(shapeAppearanceModel);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTint(int i) {
        this.drawableState.delegate.setTint(i);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        this.drawableState.delegate.setTintList(colorStateList);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintMode(PorterDuff.Mode mode) {
        this.drawableState.delegate.setTintMode(mode);
    }

    public RippleDrawableCompat(ShapeAppearanceModel shapeAppearanceModel) {
        this(new RippleDrawableCompatState(new MaterialShapeDrawable(shapeAppearanceModel)));
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RippleDrawableCompatState extends Drawable.ConstantState {
        public final MaterialShapeDrawable delegate;
        public boolean shouldDrawDelegate;

        public RippleDrawableCompatState(MaterialShapeDrawable materialShapeDrawable) {
            this.delegate = materialShapeDrawable;
            this.shouldDrawDelegate = false;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final int getChangingConfigurations() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable() {
            return new RippleDrawableCompat(new RippleDrawableCompatState(this));
        }

        public RippleDrawableCompatState(RippleDrawableCompatState rippleDrawableCompatState) {
            this.delegate = (MaterialShapeDrawable) rippleDrawableCompatState.delegate.drawableState.newDrawable();
            this.shouldDrawDelegate = rippleDrawableCompatState.shouldDrawDelegate;
        }
    }

    private RippleDrawableCompat(RippleDrawableCompatState rippleDrawableCompatState) {
        this.drawableState = rippleDrawableCompatState;
    }
}

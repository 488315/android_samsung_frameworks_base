package com.android.systemui.util;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.InsetDrawable;
import android.util.AttributeSet;
import com.android.systemui.R$styleable;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class AlphaTintDrawableWrapper extends InsetDrawable {
    public int[] mThemeAttrs;
    public ColorStateList mTint;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AlphaTintState extends Drawable.ConstantState {
        public final int mAlpha;
        public final ColorStateList mColorStateList;
        public final int[] mThemeAttrs;
        public final Drawable.ConstantState mWrappedState;

        public AlphaTintState(Drawable.ConstantState constantState, int[] iArr, int i, ColorStateList colorStateList) {
            this.mWrappedState = constantState;
            this.mThemeAttrs = iArr;
            this.mAlpha = i;
            this.mColorStateList = colorStateList;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final boolean canApplyTheme() {
            return true;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final int getChangingConfigurations() {
            return this.mWrappedState.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable() {
            return newDrawable(null, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable(Resources resources, Resources.Theme theme) {
            AlphaTintDrawableWrapper alphaTintDrawableWrapper = new AlphaTintDrawableWrapper(((DrawableWrapper) this.mWrappedState.newDrawable(resources, theme)).getDrawable(), this.mThemeAttrs);
            alphaTintDrawableWrapper.setTintList(this.mColorStateList);
            alphaTintDrawableWrapper.setAlpha(this.mAlpha);
            return alphaTintDrawableWrapper;
        }
    }

    public AlphaTintDrawableWrapper() {
        super((Drawable) null, 0);
    }

    @Override // android.graphics.drawable.InsetDrawable, android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        int[] iArr = this.mThemeAttrs;
        if (iArr != null && theme != null) {
            TypedArray resolveAttributes = theme.resolveAttributes(iArr, R$styleable.AlphaTintDrawableWrapper);
            updateStateFromTypedArray(resolveAttributes);
            resolveAttributes.recycle();
        }
        if (getDrawable() == null || this.mTint == null) {
            return;
        }
        getDrawable().mutate().setTintList(this.mTint);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final boolean canApplyTheme() {
        int[] iArr = this.mThemeAttrs;
        return (iArr != null && iArr.length > 0) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        return new AlphaTintState(super.getConstantState(), this.mThemeAttrs, getAlpha(), this.mTint);
    }

    @Override // android.graphics.drawable.InsetDrawable, android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        TypedArray obtainAttributes = InsetDrawable.obtainAttributes(resources, theme, attributeSet, R$styleable.AlphaTintDrawableWrapper);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        this.mThemeAttrs = obtainAttributes.extractThemeAttrs();
        updateStateFromTypedArray(obtainAttributes);
        obtainAttributes.recycle();
        if (getDrawable() == null || this.mTint == null) {
            return;
        }
        getDrawable().mutate().setTintList(this.mTint);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        super.setTintList(colorStateList);
        this.mTint = colorStateList;
    }

    public final void updateStateFromTypedArray(TypedArray typedArray) {
        if (typedArray.hasValue(0)) {
            this.mTint = typedArray.getColorStateList(0);
        }
        if (typedArray.hasValue(1)) {
            setAlpha(Math.round(typedArray.getFloat(1, 1.0f) * 255.0f));
        }
    }

    public AlphaTintDrawableWrapper(Drawable drawable, int[] iArr) {
        super(drawable, 0);
        this.mThemeAttrs = iArr;
    }
}

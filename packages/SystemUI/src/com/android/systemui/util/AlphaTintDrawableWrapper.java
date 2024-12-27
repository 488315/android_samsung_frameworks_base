package com.android.systemui.util;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.InsetDrawable;
import android.util.AttributeSet;
import com.android.systemui.res.R$styleable;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class AlphaTintDrawableWrapper extends InsetDrawable {
    private int[] mThemeAttrs;
    private ColorStateList mTint;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    class AlphaTintState extends Drawable.ConstantState {
        private int mAlpha;
        private ColorStateList mColorStateList;
        private int[] mThemeAttrs;
        private Drawable.ConstantState mWrappedState;

        public AlphaTintState(Drawable.ConstantState constantState, int[] iArr, int i, ColorStateList colorStateList) {
            this.mWrappedState = constantState;
            this.mThemeAttrs = iArr;
            this.mAlpha = i;
            this.mColorStateList = colorStateList;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return true;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mWrappedState.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return newDrawable(null, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            AlphaTintDrawableWrapper alphaTintDrawableWrapper = new AlphaTintDrawableWrapper(((DrawableWrapper) this.mWrappedState.newDrawable(resources, theme)).getDrawable(), this.mThemeAttrs);
            alphaTintDrawableWrapper.setTintList(this.mColorStateList);
            alphaTintDrawableWrapper.setAlpha(this.mAlpha);
            return alphaTintDrawableWrapper;
        }
    }

    public AlphaTintDrawableWrapper() {
        super((Drawable) null, 0);
    }

    private void applyTint() {
        if (getDrawable() == null || this.mTint == null) {
            return;
        }
        getDrawable().mutate().setTintList(this.mTint);
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        if (typedArray.hasValue(0)) {
            this.mTint = typedArray.getColorStateList(0);
        }
        if (typedArray.hasValue(1)) {
            setAlpha(Math.round(typedArray.getFloat(1, 1.0f) * 255.0f));
        }
    }

    @Override // android.graphics.drawable.InsetDrawable, android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        int[] iArr = this.mThemeAttrs;
        if (iArr != null && theme != null) {
            TypedArray resolveAttributes = theme.resolveAttributes(iArr, R$styleable.AlphaTintDrawableWrapper);
            updateStateFromTypedArray(resolveAttributes);
            resolveAttributes.recycle();
        }
        applyTint();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        int[] iArr = this.mThemeAttrs;
        return (iArr != null && iArr.length > 0) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return new AlphaTintState(super.getConstantState(), this.mThemeAttrs, getAlpha(), this.mTint);
    }

    @Override // android.graphics.drawable.InsetDrawable, android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray obtainAttributes = InsetDrawable.obtainAttributes(resources, theme, attributeSet, R$styleable.AlphaTintDrawableWrapper);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        this.mThemeAttrs = obtainAttributes.extractThemeAttrs();
        updateStateFromTypedArray(obtainAttributes);
        obtainAttributes.recycle();
        applyTint();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        super.setTintList(colorStateList);
        this.mTint = colorStateList;
    }

    public AlphaTintDrawableWrapper(Drawable drawable, int[] iArr) {
        super(drawable, 0);
        this.mThemeAttrs = iArr;
    }
}

package com.google.android.setupdesign.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.Button;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NavigationBarButton extends Button {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TintedDrawable extends LayerDrawable {
        public ColorStateList tintList;

        public TintedDrawable(Drawable drawable) {
            super(new Drawable[]{drawable});
            this.tintList = null;
        }

        public static TintedDrawable wrap(Drawable drawable) {
            return drawable instanceof TintedDrawable ? (TintedDrawable) drawable : new TintedDrawable(drawable.mutate());
        }

        @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
        public final boolean isStateful() {
            return true;
        }

        @Override // android.graphics.drawable.Drawable
        public final boolean setState(int[] iArr) {
            boolean z;
            boolean state = super.setState(iArr);
            ColorStateList colorStateList = this.tintList;
            if (colorStateList != null) {
                setColorFilter(colorStateList.getColorForState(getState(), 0), PorterDuff.Mode.SRC_IN);
                z = true;
            } else {
                z = false;
            }
            return state || z;
        }
    }

    public NavigationBarButton(Context context) {
        super(context);
        init();
    }

    public final void init() {
        if (isInEditMode()) {
            return;
        }
        Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
        for (int i = 0; i < compoundDrawablesRelative.length; i++) {
            Drawable drawable = compoundDrawablesRelative[i];
            if (drawable != null) {
                compoundDrawablesRelative[i] = TintedDrawable.wrap(drawable);
            }
        }
        setCompoundDrawablesRelativeWithIntrinsicBounds(compoundDrawablesRelative[0], compoundDrawablesRelative[1], compoundDrawablesRelative[2], compoundDrawablesRelative[3]);
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            drawable = TintedDrawable.wrap(drawable);
        }
        if (drawable2 != null) {
            drawable2 = TintedDrawable.wrap(drawable2);
        }
        if (drawable3 != null) {
            drawable3 = TintedDrawable.wrap(drawable3);
        }
        if (drawable4 != null) {
            drawable4 = TintedDrawable.wrap(drawable4);
        }
        super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        tintDrawables();
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            drawable = TintedDrawable.wrap(drawable);
        }
        if (drawable2 != null) {
            drawable2 = TintedDrawable.wrap(drawable2);
        }
        if (drawable3 != null) {
            drawable3 = TintedDrawable.wrap(drawable3);
        }
        if (drawable4 != null) {
            drawable4 = TintedDrawable.wrap(drawable4);
        }
        super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        tintDrawables();
    }

    @Override // android.widget.TextView
    public final void setTextColor(ColorStateList colorStateList) {
        super.setTextColor(colorStateList);
        tintDrawables();
    }

    public final void tintDrawables() {
        ColorStateList textColors = getTextColors();
        if (textColors != null) {
            Drawable[] compoundDrawables = getCompoundDrawables();
            Drawable drawable = compoundDrawables[0];
            Drawable drawable2 = compoundDrawables[1];
            Drawable drawable3 = compoundDrawables[2];
            Drawable drawable4 = compoundDrawables[3];
            Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
            Drawable[] drawableArr = {drawable, drawable2, drawable3, drawable4, compoundDrawablesRelative[0], compoundDrawablesRelative[2]};
            for (int i = 0; i < 6; i++) {
                Drawable drawable5 = drawableArr[i];
                if (drawable5 instanceof TintedDrawable) {
                    TintedDrawable tintedDrawable = (TintedDrawable) drawable5;
                    tintedDrawable.tintList = textColors;
                    tintedDrawable.setColorFilter(textColors.getColorForState(tintedDrawable.getState(), 0), PorterDuff.Mode.SRC_IN);
                    tintedDrawable.invalidateSelf();
                }
            }
            invalidate();
        }
    }

    public NavigationBarButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }
}

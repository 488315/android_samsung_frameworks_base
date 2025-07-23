package com.android.internal.accessibility.dialog;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

/* loaded from: classes5.dex */
public class WrappedDrawable extends Drawable {
    private final Drawable mDrawable;

    protected Drawable getDrawable() {
        return this.mDrawable;
    }

    public WrappedDrawable(Drawable drawable) {
        this.mDrawable = drawable;
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        Drawable drawable = getDrawable();
        if (drawable != null) {
            drawable.setBounds(i, i2, i3, i4);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            drawable.setAlpha(i);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            return drawable.getOpacity();
        }
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            drawable.draw(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            return drawable.getBounds().width();
        }
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            return drawable.getBounds().height();
        }
        return 0;
    }
}

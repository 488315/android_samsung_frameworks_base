package com.android.systemui.accessibility.floatingmenu;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

public final class InstantInsetLayerDrawable extends LayerDrawable {
    public InstantInsetLayerDrawable(Drawable[] drawableArr) {
        super(drawableArr);
    }

    @Override // android.graphics.drawable.LayerDrawable
    public final void setLayerInset(int i, int i2, int i3, int i4, int i5) {
        super.setLayerInset(i, i2, i3, i4, i5);
        onBoundsChange(getBounds());
    }
}

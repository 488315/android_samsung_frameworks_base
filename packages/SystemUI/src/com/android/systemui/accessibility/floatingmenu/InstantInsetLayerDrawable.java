package com.android.systemui.accessibility.floatingmenu;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class InstantInsetLayerDrawable extends LayerDrawable {
    public InstantInsetLayerDrawable(Drawable[] drawableArr) {
        super(drawableArr);
    }

    @Override // android.graphics.drawable.LayerDrawable
    public final void setLayerInset(int i, int i2, int i3, int i4, int i5) {
        super.setLayerInset(i, i2, i3, i4, i5);
        onBoundsChange(getBounds());
    }
}

package com.android.systemui.statusbar.model;

import android.graphics.drawable.Drawable;

public final class StringDrawableContainer {
    public final Drawable mDrawable;
    public final String mDrawableDescription;
    public final String mString;

    public StringDrawableContainer(String str, Drawable drawable, String str2) {
        this.mString = str;
        this.mDrawable = drawable;
        this.mDrawableDescription = str2;
    }
}

package com.android.systemui.statusbar.model;

import android.graphics.drawable.Drawable;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

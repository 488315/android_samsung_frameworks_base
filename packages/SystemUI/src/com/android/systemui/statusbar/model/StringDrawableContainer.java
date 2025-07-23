package com.android.systemui.statusbar.model;

import android.graphics.drawable.Drawable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class StringDrawableContainer {
    public final Drawable mDrawable;
    public final String mDrawableDescription;
    public final String mString;

    public StringDrawableContainer(String str, Drawable drawable, String str2) {
        this.mString = str;
        this.mDrawable = drawable;
        this.mDrawableDescription = str2;
    }
}

package com.android.systemui.statusbar.model;

import android.graphics.drawable.Drawable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

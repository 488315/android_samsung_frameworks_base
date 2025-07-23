package com.samsung.systemui.splugins.navigationbar;

import android.graphics.drawable.Drawable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class IconResource {
    public Drawable mDarkDrawable;
    public IconType mIconType;
    public Drawable mLightDrawable;
    public boolean mNeedRtlCheck;

    public IconResource(IconType iconType, Drawable drawable, Drawable drawable2, boolean z) {
        this.mIconType = iconType;
        this.mLightDrawable = drawable;
        this.mDarkDrawable = drawable2;
        this.mNeedRtlCheck = z;
    }
}

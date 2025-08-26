package com.samsung.systemui.splugins.navigationbar;

import android.graphics.drawable.Drawable;

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

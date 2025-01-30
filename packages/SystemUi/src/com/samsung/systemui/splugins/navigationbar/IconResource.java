package com.samsung.systemui.splugins.navigationbar;

import android.graphics.drawable.Drawable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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

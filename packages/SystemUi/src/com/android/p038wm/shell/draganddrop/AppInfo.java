package com.android.p038wm.shell.draganddrop;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AppInfo {
    public final Drawable mIcon;
    public final Intent mIntent;
    public final boolean mIsDropResolver;

    public AppInfo(Intent intent, Drawable drawable, boolean z) {
        this.mIntent = intent;
        this.mIcon = drawable;
        this.mIsDropResolver = z;
    }
}

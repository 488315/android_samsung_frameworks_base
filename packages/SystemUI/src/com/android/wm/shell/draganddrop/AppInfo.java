package com.android.wm.shell.draganddrop;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/* loaded from: classes3.dex */
public class AppInfo {
    public final Drawable mIcon;
    public final Intent mIntent;
    public final boolean mIsDropResolver;

    public AppInfo(Intent intent, Drawable drawable, boolean z) {
        this.mIntent = intent;
        this.mIcon = drawable;
        this.mIsDropResolver = z;
    }
}

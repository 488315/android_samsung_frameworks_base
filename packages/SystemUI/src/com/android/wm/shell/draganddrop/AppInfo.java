package com.android.wm.shell.draganddrop;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

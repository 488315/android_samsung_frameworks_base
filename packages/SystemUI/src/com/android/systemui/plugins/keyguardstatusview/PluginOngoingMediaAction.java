package com.android.systemui.plugins.keyguardstatusview;

import android.app.Notification;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class PluginOngoingMediaAction extends Notification.Action {
    private int mColor;
    private String mContentDescription;
    private Drawable mDrawable;
    private Icon mIcon;
    private Runnable mRunnable;

    public PluginOngoingMediaAction(Icon icon, Runnable runnable, Drawable drawable, int i, String str) {
        super(0, "", null);
        this.mIcon = icon;
        this.mRunnable = runnable;
        this.mDrawable = drawable;
        this.mColor = i;
        this.mContentDescription = str;
    }

    public int getColor() {
        return this.mColor;
    }

    public String getContentDescription() {
        return this.mContentDescription;
    }

    public Drawable getDrawable() {
        return this.mDrawable;
    }

    @Override // android.app.Notification.Action
    public Icon getIcon() {
        return this.mIcon;
    }

    public Runnable getRunnable() {
        return this.mRunnable;
    }

    public void run() {
        Runnable runnable = this.mRunnable;
        if (runnable != null) {
            runnable.run();
        }
    }
}

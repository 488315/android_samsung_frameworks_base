package com.android.systemui.plugins.keyguardstatusview;

import android.app.Notification;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

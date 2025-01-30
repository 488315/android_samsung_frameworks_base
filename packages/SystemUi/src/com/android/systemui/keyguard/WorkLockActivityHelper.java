package com.android.systemui.keyguard;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WorkLockActivityHelper {
    public final Activity mActivity;
    public final Context mContext;
    public final int mUserId;
    public View blankView = null;
    public RelativeLayout mwLockScreen = null;
    public boolean isblankView = false;

    public WorkLockActivityHelper(Context context, Activity activity, int i) {
        this.mContext = null;
        this.mContext = context;
        this.mActivity = activity;
        this.mUserId = i;
    }

    public final void setContentblank(boolean z) {
        Activity activity = this.mActivity;
        if (!z) {
            RelativeLayout relativeLayout = this.mwLockScreen;
            if (relativeLayout != null) {
                activity.setContentView(relativeLayout);
                this.isblankView = false;
                return;
            }
            return;
        }
        View view = this.blankView;
        if (view != null) {
            activity.setContentView(view);
            this.isblankView = true;
            try {
                Window window = activity.getWindow();
                window.setNavigationBarColor(0);
                window.addFlags(134217728);
                window.getDecorView().setSystemUiVisibility(1792);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

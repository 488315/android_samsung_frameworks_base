package com.android.wm.shell.windowdecor.viewholder;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class DesktopModeWindowDecorationViewHolder {
    public final Context context;

    public DesktopModeWindowDecorationViewHolder(View view) {
        this.context = view.getContext();
    }

    public static boolean shouldUseLightCaptionColors(ActivityManager.RunningTaskInfo runningTaskInfo) {
        return ((double) Color.valueOf(runningTaskInfo.taskDescription.getStatusBarColor()).luminance()) < 0.5d;
    }

    public abstract void bindData(ActivityManager.RunningTaskInfo runningTaskInfo);
}

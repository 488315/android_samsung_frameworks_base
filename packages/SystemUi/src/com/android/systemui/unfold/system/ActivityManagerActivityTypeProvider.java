package com.android.systemui.unfold.system;

import android.app.ActivityManager;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.unfold.util.CurrentActivityTypeProvider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ActivityManagerActivityTypeProvider implements CurrentActivityTypeProvider {
    public Boolean _isHomeActivity;
    public final ActivityManager activityManager;
    public final ActivityManagerActivityTypeProvider$taskStackChangeListener$1 taskStackChangeListener = new TaskStackChangeListener() { // from class: com.android.systemui.unfold.system.ActivityManagerActivityTypeProvider$taskStackChangeListener$1
        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public final void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
            ActivityManagerActivityTypeProvider activityManagerActivityTypeProvider = ActivityManagerActivityTypeProvider.this;
            activityManagerActivityTypeProvider.getClass();
            activityManagerActivityTypeProvider._isHomeActivity = Boolean.valueOf(runningTaskInfo.topActivityType == 2);
        }
    };

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.unfold.system.ActivityManagerActivityTypeProvider$taskStackChangeListener$1] */
    public ActivityManagerActivityTypeProvider(ActivityManager activityManager) {
        this.activityManager = activityManager;
    }
}

package com.android.p038wm.shell.recents;

import android.app.ActivityManager;
import com.android.p038wm.shell.common.SingleInstanceRemoteListener;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.recents.RecentTasksController$IRecentTasksImpl$1$$ExternalSyntheticLambda1 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C4082x6ba2c7fc implements SingleInstanceRemoteListener.RemoteCall {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityManager.RunningTaskInfo f$0;

    public /* synthetic */ C4082x6ba2c7fc(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        this.$r8$classId = i;
        this.f$0 = runningTaskInfo;
    }

    @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.f$0;
        switch (i) {
            case 0:
                ((IRecentTasksListener) obj).onRunningTaskAppeared(runningTaskInfo);
                break;
            default:
                ((IRecentTasksListener) obj).onRunningTaskVanished(runningTaskInfo);
                break;
        }
    }
}

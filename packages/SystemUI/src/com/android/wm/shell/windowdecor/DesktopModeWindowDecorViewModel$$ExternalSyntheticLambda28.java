package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.IActivityManager;
import android.app.IActivityTaskManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.RemoteException;
import android.os.UserHandle;
import android.window.TaskSnapshot;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda28 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda28(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ComponentName componentName;
        switch (this.$r8$classId) {
            case 0:
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = (DesktopModeWindowDecorViewModel) this.f$0;
                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.f$1;
                ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration.mTaskInfo;
                ArrayList arrayList = new ArrayList();
                IActivityManager service = ActivityManager.getService();
                IActivityTaskManager service2 = ActivityTaskManager.getService();
                try {
                    List<ActivityManager.RecentTaskInfo> recentTasks = desktopModeWindowDecorViewModel.mActivityTaskManager.getRecentTasks(Integer.MAX_VALUE, 1, service.getCurrentUser().id);
                    String packageName = runningTaskInfo.baseActivity.getPackageName();
                    for (ActivityManager.RecentTaskInfo recentTaskInfo : recentTasks) {
                        if (recentTaskInfo.taskId != runningTaskInfo.taskId && (componentName = recentTaskInfo.baseActivity) != null) {
                            String packageName2 = componentName.getPackageName();
                            if (packageName2.equals(packageName) && recentTaskInfo.baseActivity != null && packageName.equals(packageName2)) {
                                try {
                                    TaskSnapshot taskSnapshot = service2.getTaskSnapshot(recentTaskInfo.taskId, false);
                                    if (taskSnapshot == null) {
                                        taskSnapshot = service2.takeTaskSnapshot(recentTaskInfo.taskId, false);
                                    }
                                    arrayList.add(new Pair(Integer.valueOf(recentTaskInfo.taskId), taskSnapshot));
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                    desktopModeWindowDecorViewModel.mMainExecutor.execute(new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda31(desktopModeWindowDecorViewModel, desktopModeWindowDecoration, arrayList, 0));
                    return;
                } catch (RemoteException e2) {
                    throw new RuntimeException(e2);
                }
            case 1:
                DesktopModeWindowDecorViewModel.this.mContext.startServiceAsUser((Intent) this.f$1, UserHandle.CURRENT);
                return;
            default:
                DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener = (DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener) this.f$0;
                DesktopModeWindowDecoration desktopModeWindowDecoration2 = (DesktopModeWindowDecoration) this.f$1;
                desktopModeTouchEventListener.mDragInterrupted = true;
                desktopModeTouchEventListener.mIsDragging = false;
                desktopModeWindowDecoration2.mIsDragging = false;
                return;
        }
    }
}

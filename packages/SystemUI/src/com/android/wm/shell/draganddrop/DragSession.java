package com.android.wm.shell.draganddrop;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.app.WindowConfiguration;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class DragSession {
    public ActivityInfo activityInfo;
    public Intent appData;
    public DisplayLayout displayLayout;
    public boolean dragItemSupportsSplitscreen;
    public final int hideDragSourceTaskId;
    public boolean isDragDataDropResolver;
    public boolean isDragFromRecent;
    public PendingIntent launchableIntent;
    public final ActivityTaskManager mActivityTaskManager;
    public final ExecutableAppHolder mExecutableAppHolder;
    public final ClipData mInitialDragData;
    public final int mInitialDragFlags;
    public final VisibleTasks mVisibleTasks;
    public int runningTaskActType;
    public ActivityManager.RunningTaskInfo runningTaskInfo;
    public boolean runningTaskSupportsSplitScreen;

    public DragSession(ActivityTaskManager activityTaskManager, DisplayLayout displayLayout, ClipData clipData, int i) {
        this(activityTaskManager, displayLayout, clipData, i, null, null);
    }

    public final List getNonFloatingTopTask() {
        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.mActivityTaskManager.getTasks(10, false, false, 0).stream().filter(new Predicate() { // from class: com.android.wm.shell.draganddrop.DragSession$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                DragSession dragSession = this.f$0;
                ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) obj;
                dragSession.getClass();
                return (WindowConfiguration.isFloating(runningTaskInfo2.getWindowingMode()) || runningTaskInfo2.taskId == dragSession.hideDragSourceTaskId) ? false : true;
            }
        }).findFirst().orElse(null);
        return runningTaskInfo == null ? Collections.EMPTY_LIST : List.of(runningTaskInfo);
    }

    public final void initialize() {
        ActivityInfo activityInfo = this.mInitialDragData.getItemAt(0).getActivityInfo();
        this.activityInfo = activityInfo;
        if (MultiWindowCoreState.MW_ENABLED) {
            ExecutableAppHolder executableAppHolder = this.mExecutableAppHolder;
            if (executableAppHolder != null) {
                AppResult appResult = executableAppHolder.mResult;
                this.dragItemSupportsSplitscreen = appResult != null && appResult.hasResizableResolveInfo();
            } else {
                if (activityInfo != null && !ActivityInfo.isResizeableMode(activityInfo.resizeMode)) {
                    z = false;
                }
                this.dragItemSupportsSplitscreen = z;
            }
        } else {
            this.dragItemSupportsSplitscreen = false;
        }
        this.appData = this.mInitialDragData.getItemAt(0).getIntent();
        this.launchableIntent = DragUtils.getLaunchIntent(this.mInitialDragData, this.mInitialDragFlags);
    }

    public final void updateRunningTask() {
        List nonFloatingTopTask;
        int i;
        Intent intent;
        int i2 = this.hideDragSourceTaskId;
        boolean z = i2 != -1;
        boolean zIsDragFromRecent = this.mInitialDragData.getDescription().isDragFromRecent();
        this.isDragFromRecent = zIsDragFromRecent;
        int intExtra = (!zIsDragFromRecent || (intent = this.mInitialDragData.getItemAt(0).getIntent()) == null) ? -1 : intent.getIntExtra("android.intent.extra.DND_RECENT_TOP_TASK_ID", -1);
        if (this.isDragFromRecent && intExtra != -1) {
            Iterator it = this.mActivityTaskManager.getTasks(Integer.MAX_VALUE, false).iterator();
            while (true) {
                if (!it.hasNext()) {
                    nonFloatingTopTask = Collections.EMPTY_LIST;
                    break;
                }
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) it.next();
                if (runningTaskInfo.taskId == intExtra) {
                    nonFloatingTopTask = List.of(runningTaskInfo);
                    break;
                }
            }
        } else {
            nonFloatingTopTask = getNonFloatingTopTask();
        }
        if (nonFloatingTopTask.isEmpty()) {
            return;
        }
        for (int i3 = 0; i3 < nonFloatingTopTask.size(); i3++) {
            ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) nonFloatingTopTask.get(i3);
            if (!z || i2 != (i = runningTaskInfo2.taskId)) {
                if ((this.isDragFromRecent || runningTaskInfo2.isVisible) && !runningTaskInfo2.configuration.windowConfiguration.isAlwaysOnTop()) {
                    this.runningTaskInfo = runningTaskInfo2;
                    runningTaskInfo2.getWindowingMode();
                    runningTaskInfo2.getActivityType();
                    this.runningTaskActType = runningTaskInfo2.topActivityType;
                    this.runningTaskSupportsSplitScreen = runningTaskInfo2.supportsMultiWindow;
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
                        long j = runningTaskInfo2.taskId;
                        Intent intent2 = runningTaskInfo2.baseIntent;
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -2392824589187272349L, 1, Long.valueOf(j), String.valueOf(intent2 != null ? intent2.getComponent() : "null"));
                        return;
                    }
                    return;
                }
            } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
                long j2 = i;
                Intent intent3 = runningTaskInfo2.baseIntent;
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -8010626571342564658L, 1, Long.valueOf(j2), String.valueOf(intent3 != null ? intent3.getComponent() : "null"));
            }
        }
    }

    public DragSession(ActivityTaskManager activityTaskManager, DisplayLayout displayLayout, ClipData clipData, int i, ExecutableAppHolder executableAppHolder, VisibleTasks visibleTasks) {
        this.runningTaskActType = 1;
        this.mActivityTaskManager = activityTaskManager;
        this.mInitialDragData = clipData;
        this.mInitialDragFlags = i;
        this.displayLayout = displayLayout;
        int i2 = -1;
        if (clipData != null && clipData.getDescription().getExtras() != null) {
            i2 = clipData.getDescription().getExtras().getInt("android.intent.extra.HIDE_DRAG_SOURCE_TASK_ID", -1);
        }
        this.hideDragSourceTaskId = i2;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -7476564039895466497L, 1, Long.valueOf(i2));
        }
        this.mExecutableAppHolder = executableAppHolder;
        this.mVisibleTasks = visibleTasks;
    }
}

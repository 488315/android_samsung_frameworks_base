package com.android.wm.shell;

import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.TaskInfo;
import android.content.Context;
import android.content.LocusId;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.SurfaceControl;
import android.window.ITaskOrganizerController;
import android.window.StartingWindowInfo;
import android.window.StartingWindowRemovalInfo;
import android.window.TaskAppearedInfo;
import android.window.TaskOrganizer;
import com.android.internal.R;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.internal.util.FrameworkStatsLog;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TaskStackListenerCallback;
import com.android.wm.shell.common.TaskStackListenerImpl;
import com.android.wm.shell.compatui.api.CompatUIHandler;
import com.android.wm.shell.compatui.api.CompatUIInfo;
import com.android.wm.shell.compatui.impl.CompatUIEvents;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.freeform.FreeformTaskListener;
import com.android.wm.shell.fullscreen.FullscreenTaskListener;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.splitscreen.ForcedResizableInfoActivityController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.splitscreen.StageTaskListener;
import com.android.wm.shell.startingsurface.SplashscreenContentDrawer;
import com.android.wm.shell.startingsurface.SplashscreenContentDrawer.SplashViewBuilder;
import com.android.wm.shell.startingsurface.StartingWindowController;
import com.android.wm.shell.startingsurface.StartingWindowController$$ExternalSyntheticLambda0;
import com.android.wm.shell.startingsurface.StartingWindowController$$ExternalSyntheticLambda4;
import com.android.wm.shell.startingsurface.StartingWindowController$$ExternalSyntheticLambda5;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.FocusTransitionObserver;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.unfold.UnfoldAnimationController;
import com.android.wm.shell.unfold.animation.UnfoldTaskAnimator;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.TaskOrganizerInfo;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ShellTaskOrganizer extends TaskOrganizer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CompatUIHandler mCompatUI;
    public final ArraySet mFocusListeners;
    public final FocusTransitionObserver mFocusTransitionObserver;
    public final ForcedResizableInfoActivityController mForcedResizableController;
    public final SurfaceControl mHomeTaskOverlayContainer;
    public ActivityManager.RunningTaskInfo mLastFocusedTaskInfo;
    public final ArrayMap mLaunchCookieToListener;
    public final Lazy mLazyDesktopTasksController;
    public Iterator mListenerIterator;
    public final Object mLock;
    public final ArraySet mLocusIdListeners;
    public final ArraySet mMultiWindowCoreStateChangeListeners;
    public final Optional mRecentTasks;
    public final ShellCommandHandler mShellCommandHandler;
    public StartingWindowController mStartingWindow;
    public final SparseArray mTaskListeners;
    public final ArraySet mTaskVanishedListeners;
    public final SparseArray mTasks;
    public final UnfoldAnimationController mUnfoldAnimationController;
    public final SparseArray mVisibleTasksWithLocusId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.ShellTaskOrganizer$1, reason: invalid class name */
    class AnonymousClass1 implements TaskStackListenerCallback {
        public AnonymousClass1() {
        }

        @Override // com.android.wm.shell.common.TaskStackListenerCallback
        public final void onActivityDismissingSplitTask(String str) {
            ShellTaskOrganizer.this.getExecutor().execute(new ShellTaskOrganizer$1$$ExternalSyntheticLambda0(this, str));
        }

        @Override // com.android.wm.shell.common.TaskStackListenerCallback
        public final void onActivityForcedResizable(String str, int i, int i2) {
            ShellTaskOrganizer.this.getExecutor().execute(new ShellTaskOrganizer$1$$ExternalSyntheticLambda0(this, str, i, i2));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface FocusListener {
        void onFocusTaskChanged(ActivityManager.RunningTaskInfo runningTaskInfo);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface LocusIdListener {
        void onVisibilityChanged(int i, LocusId locusId, boolean z);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface MultiWindowCoreStateChangeListener {
        boolean onMultiWindowCoreStateChanged(int i);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public @interface TaskListenerType {
    }

    public ShellTaskOrganizer(ShellExecutor shellExecutor, Context context, TaskStackListenerImpl taskStackListenerImpl) {
        this(null, null, null, null, Optional.empty(), Optional.empty(), shellExecutor, null, taskStackListenerImpl, context, null);
    }

    public static int taskInfoToTaskListenerType(ActivityManager.RunningTaskInfo runningTaskInfo) {
        int windowingMode = runningTaskInfo.getWindowingMode();
        if (windowingMode == 1) {
            return -2;
        }
        if (windowingMode == 2) {
            return -4;
        }
        if (windowingMode != 5) {
            return windowingMode != 6 ? -1 : -3;
        }
        return -5;
    }

    public static String taskListenerTypeToString(int i) {
        return i != -5 ? i != -4 ? i != -3 ? i != -2 ? i != -1 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "taskId#") : "TASK_LISTENER_TYPE_UNDEFINED" : "TASK_LISTENER_TYPE_FULLSCREEN" : "TASK_LISTENER_TYPE_MULTI_WINDOW" : "TASK_LISTENER_TYPE_PIP" : "TASK_LISTENER_TYPE_FREEFORM";
    }

    public final void addListenerForType(TaskListener taskListener, int... iArr) {
        synchronized (this.mLock) {
            try {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TASK_ORG_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, -3661524279245587623L, 0, String.valueOf(Arrays.toString(iArr)), String.valueOf(taskListener));
                }
                for (int i : iArr) {
                    if (this.mTaskListeners.get(i) != null) {
                        throw new IllegalArgumentException("Listener for listenerType=" + i + " already exists");
                    }
                    this.mTaskListeners.put(i, taskListener);
                }
                for (int size = this.mTasks.size() - 1; size >= 0; size--) {
                    TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) this.mTasks.valueAt(size);
                    if (getTaskListener(taskAppearedInfo.getTaskInfo(), false) == taskListener) {
                        taskListener.onTaskAppeared(taskAppearedInfo.getTaskInfo(), taskAppearedInfo.getLeash());
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addStartingWindow(StartingWindowInfo startingWindowInfo) {
        StartingWindowController startingWindowController = this.mStartingWindow;
        if (startingWindowController != null) {
            startingWindowController.mSplashScreenExecutor.execute(new StartingWindowController$$ExternalSyntheticLambda4(0, startingWindowController, startingWindowInfo));
        }
    }

    public final void addTaskVanishedListener(TaskVanishedListener taskVanishedListener) {
        synchronized (this.mLock) {
            this.mTaskVanishedListeners.add(taskVanishedListener);
        }
    }

    public final void clearForcedResizablePackagesIfNeeded() {
        for (int size = this.mTaskListeners.size() - 1; size >= 0; size--) {
            TaskListener taskListener = (TaskListener) this.mTaskListeners.valueAt(size);
            if (taskListener.isMultiWindow() && taskListener.hasChild()) {
                return;
            }
        }
        Log.w("ShellTaskOrganizer", "clearForcedResizablePackagesIfNeeded");
        this.mForcedResizableController.mPackagesShownInSession.clear();
    }

    public final void copySplashScreenView(int i) {
        StartingWindowController startingWindowController = this.mStartingWindow;
        if (startingWindowController != null) {
            startingWindowController.mSplashScreenExecutor.execute(new StartingWindowController$$ExternalSyntheticLambda0(startingWindowController, i, 0));
        }
    }

    public final void createDeskRootTask(int i, int i2, TaskListener taskListener) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TASK_ORG_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, 5460813693373922362L, 21, Long.valueOf(i), Long.valueOf(5), Long.valueOf(i2), String.valueOf(taskListener.toString()));
        }
        Binder binder = new Binder();
        setPendingLaunchCookieListener(binder, taskListener);
        super.createDeskRootTask(i, 5, i2, binder, true, true);
    }

    public final void createRootTask(int i, StageCoordinator stageCoordinator) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TASK_ORG_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, -8442159261225566629L, 5, Long.valueOf(i), Long.valueOf(1), String.valueOf(stageCoordinator.toString()));
        }
        Binder binder = new Binder();
        setPendingLaunchCookieListener(binder, stageCoordinator);
        super.createRootTask(i, 1, binder, false, false);
    }

    public final void createStageRootTask(int i, int i2, StageTaskListener stageTaskListener) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TASK_ORG_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, -6122139013585619599L, 21, Long.valueOf(i), Long.valueOf(6), Long.valueOf(i2), String.valueOf(stageTaskListener.toString()));
        }
        Binder binder = new Binder();
        setPendingLaunchCookieListener(binder, stageTaskListener);
        super.createStageRootTask(i, 6, i2, binder);
    }

    public final ActivityManager.RunningTaskInfo getFocusedActivityTaskWithWinMode(int i, int i2) {
        if (i == -1) {
            Slog.w("ShellTaskOrganizer", "getFocusedActivityTask() : focusDisplayId is invalid.");
            return null;
        }
        ArrayList runningTasks = getRunningTasks(i);
        int size = runningTasks.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = runningTasks.get(i3);
            i3++;
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj;
            if (runningTaskInfo.isFocused && !runningTaskInfo.activatableDeskRoot && runningTaskInfo.realActivity != null && runningTaskInfo.getActivityType() != 2 && runningTaskInfo.getActivityType() != 3 && (i2 == 0 || i2 == runningTaskInfo.getWindowingMode())) {
                return runningTaskInfo;
            }
        }
        return null;
    }

    public final int getFreeformCaptionType(ActivityManager.RunningTaskInfo runningTaskInfo) {
        synchronized (this.mLock) {
            try {
                if (!this.mTaskListeners.contains(-5) || runningTaskInfo == null || !runningTaskInfo.isFreeform()) {
                    return 0;
                }
                return ((FreeformTaskListener) this.mTaskListeners.get(-5)).mCaptionType;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final SurfaceControl getHomeTaskSurface() {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mTasks.size(); i++) {
                try {
                    TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) this.mTasks.valueAt(i);
                    if (taskAppearedInfo.getTaskInfo().getActivityType() == 2) {
                        return taskAppearedInfo.getLeash();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return null;
        }
    }

    public final ActivityManager.RunningTaskInfo getRunningTaskInfo(int i) {
        ActivityManager.RunningTaskInfo taskInfo;
        synchronized (this.mLock) {
            try {
                TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) this.mTasks.get(i);
                taskInfo = taskAppearedInfo != null ? taskAppearedInfo.getTaskInfo() : null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return taskInfo;
    }

    public final ArrayList getRunningTasks(int i) {
        ArrayList arrayList;
        synchronized (this.mLock) {
            try {
                arrayList = new ArrayList();
                for (int i2 = 0; i2 < this.mTasks.size(); i2++) {
                    ActivityManager.RunningTaskInfo taskInfo = ((TaskAppearedInfo) this.mTasks.valueAt(i2)).getTaskInfo();
                    if (taskInfo.displayId == i) {
                        arrayList.add(taskInfo);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final TaskListener getTaskListener(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
        TaskListener taskListener;
        int i = runningTaskInfo.taskId;
        ArrayList arrayList = runningTaskInfo.launchCookies;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            IBinder iBinder = (IBinder) arrayList.get(size);
            TaskListener taskListener2 = (TaskListener) this.mLaunchCookieToListener.get(iBinder);
            if (taskListener2 != null) {
                if (z) {
                    this.mLaunchCookieToListener.remove(iBinder);
                    this.mTaskListeners.put(i, taskListener2);
                }
                return taskListener2;
            }
        }
        TaskListener taskListener3 = (TaskListener) this.mTaskListeners.get(i);
        return taskListener3 != null ? taskListener3 : (!runningTaskInfo.hasParentTask() || (taskListener = (TaskListener) this.mTaskListeners.get(runningTaskInfo.parentTaskId)) == null) ? (TaskListener) this.mTaskListeners.get(taskInfoToTaskListenerType(runningTaskInfo)) : taskListener;
    }

    public final List getVisibleTaskAppearedInfos() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            try {
                arrayList = new ArrayList();
                for (int size = this.mTasks.size() - 1; size >= 0; size--) {
                    TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) this.mTasks.valueAt(size);
                    if (taskAppearedInfo.getTaskInfo().isVisible) {
                        arrayList.add(taskAppearedInfo);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final boolean isTargetTaskImeShowing(int i) {
        FreeformTaskListener freeformTaskListener = (FreeformTaskListener) this.mTaskListeners.get(-5);
        return freeformTaskListener != null && freeformTaskListener.mDisplayImeController.isImeShowing(i) && freeformTaskListener.mImePositionProcessor.mImeShown;
    }

    public final void minimizeAllDesktopTasks(final int i) {
        ((Optional) this.mLazyDesktopTasksController.get()).ifPresent(new Consumer() { // from class: com.android.wm.shell.ShellTaskOrganizer$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                int i3 = ShellTaskOrganizer.$r8$clinit;
                ((DesktopTasksController) obj).minimizeAllTasks(i2);
            }
        });
    }

    public final void notifyCompatUI(ActivityManager.RunningTaskInfo runningTaskInfo, TaskListener taskListener) {
        if (this.mCompatUI == null) {
            return;
        }
        if (taskListener != null && taskListener.supportCompatUI() && taskListener.supportCompatUI$1() && runningTaskInfo.appCompatTaskInfo.hasCompatUI() && runningTaskInfo.isVisible) {
            this.mCompatUI.onCompatInfoChanged(new CompatUIInfo(runningTaskInfo, taskListener));
        } else {
            this.mCompatUI.onCompatInfoChanged(new CompatUIInfo(runningTaskInfo, null));
        }
    }

    public final void notifyLocusIdChange(int i, LocusId locusId, boolean z) {
        for (int i2 = 0; i2 < this.mLocusIdListeners.size(); i2++) {
            ((LocusIdListener) this.mLocusIdListeners.valueAt(i2)).onVisibilityChanged(i, locusId, z);
        }
    }

    public final void notifyLocusVisibilityIfNeeded(TaskInfo taskInfo) {
        int i = taskInfo.taskId;
        LocusId locusId = (LocusId) this.mVisibleTasksWithLocusId.get(i);
        boolean equals = Objects.equals(locusId, taskInfo.mTopActivityLocusId);
        if (locusId == null) {
            LocusId locusId2 = taskInfo.mTopActivityLocusId;
            if (locusId2 == null || !taskInfo.isVisible) {
                return;
            }
            this.mVisibleTasksWithLocusId.put(i, locusId2);
            notifyLocusIdChange(i, taskInfo.mTopActivityLocusId, true);
            return;
        }
        if (equals && !taskInfo.isVisible) {
            this.mVisibleTasksWithLocusId.remove(i);
            notifyLocusIdChange(i, taskInfo.mTopActivityLocusId, false);
        } else {
            if (equals) {
                return;
            }
            if (!taskInfo.isVisible) {
                this.mVisibleTasksWithLocusId.remove(taskInfo.taskId);
                notifyLocusIdChange(i, locusId, false);
            } else {
                this.mVisibleTasksWithLocusId.put(i, taskInfo.mTopActivityLocusId);
                notifyLocusIdChange(i, locusId, false);
                notifyLocusIdChange(i, taskInfo.mTopActivityLocusId, true);
            }
        }
    }

    public final void onAppSplashScreenViewRemoved(int i) {
        StartingWindowController startingWindowController = this.mStartingWindow;
        if (startingWindowController != null) {
            startingWindowController.mSplashScreenExecutor.execute(new StartingWindowController$$ExternalSyntheticLambda0(startingWindowController, i, 2));
        }
    }

    public final void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) {
        synchronized (this.mLock) {
            try {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TASK_ORG_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, 6500052048103815784L, 1, Long.valueOf(runningTaskInfo.taskId));
                }
                TaskListener taskListener = getTaskListener(runningTaskInfo, false);
                if (taskListener != null) {
                    taskListener.onBackPressedOnTaskRoot(runningTaskInfo);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onImeDrawnOnTask(int i) {
        StartingWindowController startingWindowController = this.mStartingWindow;
        if (startingWindowController != null) {
            startingWindowController.mSplashScreenExecutor.execute(new StartingWindowController$$ExternalSyntheticLambda0(startingWindowController, i, 1));
        }
    }

    public void onSizeCompatRestartButtonAppeared(CompatUIEvents.SizeCompatRestartButtonAppeared sizeCompatRestartButtonAppeared) {
        TaskAppearedInfo taskAppearedInfo;
        ActivityInfo activityInfo;
        int i = sizeCompatRestartButtonAppeared.taskId;
        synchronized (this.mLock) {
            taskAppearedInfo = (TaskAppearedInfo) this.mTasks.get(i);
        }
        if (taskAppearedInfo == null || (activityInfo = taskAppearedInfo.getTaskInfo().topActivityInfo) == null) {
            return;
        }
        FrameworkStatsLog.write(387, activityInfo.applicationInfo.uid, 1);
    }

    public void onSizeCompatRestartButtonClicked(CompatUIEvents.SizeCompatRestartButtonClicked sizeCompatRestartButtonClicked) {
        TaskAppearedInfo taskAppearedInfo;
        int i = sizeCompatRestartButtonClicked.taskId;
        synchronized (this.mLock) {
            taskAppearedInfo = (TaskAppearedInfo) this.mTasks.get(i);
        }
        if (taskAppearedInfo == null) {
            return;
        }
        ActivityInfo activityInfo = taskAppearedInfo.getTaskInfo().topActivityInfo;
        if (activityInfo != null) {
            FrameworkStatsLog.write(387, activityInfo.applicationInfo.uid, 2);
        }
        restartTaskTopActivityProcessIfVisible(taskAppearedInfo.getTaskInfo().token);
    }

    public final void onSplitLayoutChangeRequested(ActivityManager.RunningTaskInfo runningTaskInfo, Bundle bundle) {
        synchronized (this.mLock) {
            try {
                TaskListener taskListener = getTaskListener(((TaskAppearedInfo) this.mTasks.get(runningTaskInfo.taskId)).getTaskInfo(), false);
                if (taskListener != null) {
                    taskListener.onSplitLayoutChangeRequested(TaskOrganizerInfo.fromBundle(bundle));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) {
        if (surfaceControl != null) {
            surfaceControl.setUnreleasedWarningCallSite("ShellTaskOrganizer.onTaskAppeared");
        }
        synchronized (this.mLock) {
            onTaskAppeared(new TaskAppearedInfo(runningTaskInfo, surfaceControl));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:87:0x011b, code lost:
    
        if (java.util.Objects.equals(r0.configuration.windowConfiguration.getAppBounds(), r11.configuration.windowConfiguration.getAppBounds()) != false) goto L71;
     */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0173 A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:4:0x0005, B:6:0x000c, B:7:0x0025, B:9:0x0029, B:10:0x002c, B:12:0x0038, B:13:0x0049, B:16:0x004b, B:21:0x008a, B:22:0x008d, B:24:0x0093, B:26:0x009d, B:30:0x00a4, B:32:0x00a8, B:34:0x00b5, B:36:0x00b9, B:38:0x00c3, B:40:0x00c7, B:42:0x00cb, B:44:0x00d1, B:45:0x00dc, B:48:0x011d, B:49:0x0128, B:51:0x012c, B:53:0x0130, B:57:0x0136, B:59:0x013a, B:61:0x0140, B:63:0x0165, B:65:0x0169, B:67:0x0175, B:69:0x016d, B:71:0x0173, B:74:0x014d, B:76:0x0155, B:78:0x0163, B:79:0x00eb, B:82:0x00f7, B:84:0x00fd, B:86:0x0107, B:88:0x00ac, B:90:0x00b2, B:92:0x0072, B:94:0x0077, B:96:0x007c, B:99:0x0082), top: B:3:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0155 A[Catch: all -> 0x0022, LOOP:0: B:74:0x014d->B:76:0x0155, LOOP_END, TryCatch #0 {all -> 0x0022, blocks: (B:4:0x0005, B:6:0x000c, B:7:0x0025, B:9:0x0029, B:10:0x002c, B:12:0x0038, B:13:0x0049, B:16:0x004b, B:21:0x008a, B:22:0x008d, B:24:0x0093, B:26:0x009d, B:30:0x00a4, B:32:0x00a8, B:34:0x00b5, B:36:0x00b9, B:38:0x00c3, B:40:0x00c7, B:42:0x00cb, B:44:0x00d1, B:45:0x00dc, B:48:0x011d, B:49:0x0128, B:51:0x012c, B:53:0x0130, B:57:0x0136, B:59:0x013a, B:61:0x0140, B:63:0x0165, B:65:0x0169, B:67:0x0175, B:69:0x016d, B:71:0x0173, B:74:0x014d, B:76:0x0155, B:78:0x0163, B:79:0x00eb, B:82:0x00f7, B:84:0x00fd, B:86:0x0107, B:88:0x00ac, B:90:0x00b2, B:92:0x0072, B:94:0x0077, B:96:0x007c, B:99:0x0082), top: B:3:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onTaskInfoChanged(android.app.ActivityManager.RunningTaskInfo r11) {
        /*
            Method dump skipped, instructions count: 377
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.ShellTaskOrganizer.onTaskInfoChanged(android.app.ActivityManager$RunningTaskInfo):void");
    }

    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        synchronized (this.mLock) {
            try {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TASK_ORG_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, 8756077958776566595L, 1, Long.valueOf(runningTaskInfo.taskId));
                }
                UnfoldAnimationController unfoldAnimationController = this.mUnfoldAnimationController;
                if (unfoldAnimationController != null) {
                    unfoldAnimationController.mTaskSurfaces.remove(runningTaskInfo.taskId);
                    UnfoldTaskAnimator unfoldTaskAnimator = (UnfoldTaskAnimator) unfoldAnimationController.mAnimatorsByTaskId.get(runningTaskInfo.taskId);
                    if (unfoldTaskAnimator != null) {
                        if (unfoldAnimationController.mIsInStageChange) {
                            TransactionPool transactionPool = unfoldAnimationController.mTransactionPool;
                            SurfaceControl.Transaction acquire = transactionPool.acquire();
                            unfoldTaskAnimator.resetSurface(runningTaskInfo, acquire);
                            acquire.apply();
                            transactionPool.release(acquire);
                        }
                        unfoldTaskAnimator.onTaskVanished(runningTaskInfo);
                        unfoldAnimationController.mAnimatorsByTaskId.remove(runningTaskInfo.taskId);
                    }
                }
                int i = runningTaskInfo.taskId;
                TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) this.mTasks.get(i);
                if (taskAppearedInfo == null) {
                    Log.w("ShellTaskOrganizer", "onTaskVanished: cannot find TaskAppearedInfo, " + runningTaskInfo);
                    return;
                }
                TaskListener taskListener = getTaskListener(taskAppearedInfo.getTaskInfo(), false);
                this.mTasks.remove(i);
                if (taskListener != null) {
                    taskListener.onTaskVanished(runningTaskInfo);
                }
                notifyLocusVisibilityIfNeeded(runningTaskInfo);
                notifyCompatUI(runningTaskInfo, null);
                this.mRecentTasks.ifPresent(new ShellTaskOrganizer$$ExternalSyntheticLambda1(runningTaskInfo, 2));
                if (runningTaskInfo.getActivityType() == 2) {
                    SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                    transaction.reparent(this.mHomeTaskOverlayContainer, null);
                    transaction.apply();
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TASK_ORG_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, 2257855729973411439L, 0, null);
                    }
                }
                this.mListenerIterator = this.mTaskVanishedListeners.iterator();
                while (this.mListenerIterator.hasNext()) {
                    ((TaskVanishedListener) this.mListenerIterator.next()).onTaskVanished(runningTaskInfo);
                }
                this.mListenerIterator = null;
                if (taskListener != null && taskListener.isMultiWindow()) {
                    clearForcedResizablePackagesIfNeeded();
                }
                if (taskListener != null && runningTaskInfo.getWindowingMode() == 6) {
                    taskListener.onSplitPairUpdateRequested();
                }
                if (!Transitions.ENABLE_SHELL_TRANSITIONS && taskAppearedInfo.getLeash() != null) {
                    taskAppearedInfo.getLeash().release();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void preloadSplashScreenAppIcon(final ActivityInfo activityInfo, final int i, final Configuration configuration) {
        final StartingWindowController startingWindowController = this.mStartingWindow;
        if (startingWindowController != null) {
            startingWindowController.mSplashScreenExecutor.execute(new Runnable() { // from class: com.android.wm.shell.startingsurface.StartingWindowController$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    StartingWindowController startingWindowController2 = StartingWindowController.this;
                    final ActivityInfo activityInfo2 = activityInfo;
                    int i2 = i;
                    Configuration configuration2 = configuration;
                    SplashscreenWindowCreator splashscreenWindowCreator = startingWindowController2.mStartingSurfaceDrawer.mSplashscreenWindowCreator;
                    splashscreenWindowCreator.getClass();
                    int splashScreenTheme = AbsSplashWindowCreator.getSplashScreenTheme(0, activityInfo2);
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 4492556486619968381L, 0, String.valueOf(activityInfo2.packageName), String.valueOf(Integer.toHexString(splashScreenTheme)));
                    }
                    splashscreenWindowCreator.mDisplayManager.getDisplay(0);
                    final Context context = splashscreenWindowCreator.mContext;
                    if (context == null) {
                        return;
                    }
                    if (splashScreenTheme != context.getThemeResId()) {
                        try {
                            context = context.createPackageContextAsUser(activityInfo2.packageName, 4, UserHandle.of(i2));
                            context.setTheme(splashScreenTheme);
                        } catch (PackageManager.NameNotFoundException e) {
                            Slog.w("ShellStartingWindow", "Failed creating package context with package name " + activityInfo2.packageName + " for user " + i2 + " while preloading icon", e);
                            return;
                        }
                    }
                    if (configuration2.diffPublicOnly(context.getResources().getConfiguration()) != 0) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 791307452130606631L, 0, String.valueOf(configuration2));
                        }
                        Context createConfigurationContext = context.createConfigurationContext(configuration2);
                        createConfigurationContext.setTheme(splashScreenTheme);
                        TypedArray obtainStyledAttributes = createConfigurationContext.obtainStyledAttributes(R.styleable.Window);
                        int resourceId = obtainStyledAttributes.getResourceId(1, 0);
                        if (resourceId != 0) {
                            try {
                                if (createConfigurationContext.getDrawable(resourceId) != null) {
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -7977290308847521382L, 0, String.valueOf(configuration2));
                                    }
                                    context = createConfigurationContext;
                                }
                            } catch (Resources.NotFoundException e2) {
                                Slog.w("ShellStartingWindow", "failed creating starting window for globalConfig at activityInfo: " + activityInfo2, e2);
                                return;
                            }
                        }
                        obtainStyledAttributes.recycle();
                    }
                    final SplashscreenContentDrawer splashscreenContentDrawer = splashscreenWindowCreator.mSplashscreenContentDrawer;
                    splashscreenContentDrawer.getClass();
                    splashscreenContentDrawer.mSplashscreenWorkerHandler.post(new Runnable() { // from class: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            SplashscreenContentDrawer splashscreenContentDrawer2 = SplashscreenContentDrawer.this;
                            Context context2 = context;
                            ActivityInfo activityInfo3 = activityInfo2;
                            int i3 = SplashscreenContentDrawer.mThemeBackgroundColor;
                            splashscreenContentDrawer2.updateDensity();
                            SplashscreenContentDrawer.getWindowAttrs(context2, splashscreenContentDrawer2.mTmpAttrs);
                            splashscreenContentDrawer2.mLastPackageContextConfigHash = context2.getResources().getConfiguration().hashCode();
                            try {
                                int bGColorFromCache = splashscreenContentDrawer2.getBGColorFromCache(activityInfo3, new SplashscreenContentDrawer$$ExternalSyntheticLambda6(splashscreenContentDrawer2, context2, 0));
                                SplashscreenContentDrawer.SplashViewBuilder splashViewBuilder = splashscreenContentDrawer2.new SplashViewBuilder(context2, activityInfo3);
                                splashViewBuilder.mThemeColor = bGColorFromCache;
                                splashViewBuilder.mOverlayDrawable = null;
                                splashViewBuilder.mSuggestType = 0;
                                splashViewBuilder.build(true);
                            } catch (RuntimeException e3) {
                                Slog.w("ShellStartingWindow", "failed to preload starting window app icon. ", e3);
                            }
                        }
                    });
                }
            });
        }
    }

    public final List registerOrganizer() {
        List registerOrganizer;
        synchronized (this.mLock) {
            try {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TASK_ORG_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, 471160942078541602L, 0, null);
                }
                registerOrganizer = super.registerOrganizer();
                for (int i = 0; i < registerOrganizer.size(); i++) {
                    TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) registerOrganizer.get(i);
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TASK_ORG_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, -3643791260082359448L, 1, Long.valueOf(taskAppearedInfo.getTaskInfo().taskId), String.valueOf(taskAppearedInfo.getTaskInfo().baseIntent));
                    }
                    onTaskAppeared(taskAppearedInfo);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return registerOrganizer;
    }

    public final void removeStartingWindow(final StartingWindowRemovalInfo startingWindowRemovalInfo) {
        if (this.mStartingWindow != null) {
            Log.d("ShellTaskOrganizer", "removeStartingWindow, removalInfo=" + startingWindowRemovalInfo);
            final StartingWindowController startingWindowController = this.mStartingWindow;
            startingWindowController.getClass();
            final int i = 0;
            Runnable runnable = new Runnable() { // from class: com.android.wm.shell.startingsurface.StartingWindowController$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i) {
                        case 0:
                            StartingWindowController startingWindowController2 = startingWindowController;
                            StartingWindowRemovalInfo startingWindowRemovalInfo2 = startingWindowRemovalInfo;
                            StartingSurfaceDrawer startingSurfaceDrawer = startingWindowController2.mStartingSurfaceDrawer;
                            startingSurfaceDrawer.getClass();
                            if (startingWindowRemovalInfo2.windowlessSurface) {
                                startingSurfaceDrawer.mWindowlessRecords.removeWindow(startingWindowRemovalInfo2, startingWindowRemovalInfo2.removeImmediately);
                                return;
                            }
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 4806354277159419601L, 1, Long.valueOf(startingWindowRemovalInfo2.taskId));
                            }
                            startingSurfaceDrawer.mWindowRecords.removeWindow(startingWindowRemovalInfo2, startingWindowRemovalInfo2.removeImmediately);
                            return;
                        default:
                            StartingWindowController startingWindowController3 = startingWindowController;
                            StartingWindowRemovalInfo startingWindowRemovalInfo3 = startingWindowRemovalInfo;
                            synchronized (startingWindowController3.mTaskBackgroundColors) {
                                startingWindowController3.mTaskBackgroundColors.delete(startingWindowRemovalInfo3.taskId);
                            }
                            return;
                    }
                }
            };
            ShellExecutor shellExecutor = startingWindowController.mSplashScreenExecutor;
            shellExecutor.execute(runnable);
            if (startingWindowRemovalInfo.windowlessSurface) {
                return;
            }
            final int i2 = 1;
            ((HandlerExecutor) shellExecutor).executeDelayed(new Runnable() { // from class: com.android.wm.shell.startingsurface.StartingWindowController$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            StartingWindowController startingWindowController2 = startingWindowController;
                            StartingWindowRemovalInfo startingWindowRemovalInfo2 = startingWindowRemovalInfo;
                            StartingSurfaceDrawer startingSurfaceDrawer = startingWindowController2.mStartingSurfaceDrawer;
                            startingSurfaceDrawer.getClass();
                            if (startingWindowRemovalInfo2.windowlessSurface) {
                                startingSurfaceDrawer.mWindowlessRecords.removeWindow(startingWindowRemovalInfo2, startingWindowRemovalInfo2.removeImmediately);
                                return;
                            }
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 4806354277159419601L, 1, Long.valueOf(startingWindowRemovalInfo2.taskId));
                            }
                            startingSurfaceDrawer.mWindowRecords.removeWindow(startingWindowRemovalInfo2, startingWindowRemovalInfo2.removeImmediately);
                            return;
                        default:
                            StartingWindowController startingWindowController3 = startingWindowController;
                            StartingWindowRemovalInfo startingWindowRemovalInfo3 = startingWindowRemovalInfo;
                            synchronized (startingWindowController3.mTaskBackgroundColors) {
                                startingWindowController3.mTaskBackgroundColors.delete(startingWindowRemovalInfo3.taskId);
                            }
                            return;
                    }
                }
            }, 5000L);
        }
    }

    public final void reparentChildSurfaceToTask(int i, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        TaskListener taskListener;
        synchronized (this.mLock) {
            try {
                taskListener = this.mTasks.contains(i) ? getTaskListener(((TaskAppearedInfo) this.mTasks.get(i)).getTaskInfo(), false) : null;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (taskListener != null) {
            taskListener.reparentChildSurfaceToTask(i, transaction, surfaceControl);
        } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TASK_ORG_enabled[3]) {
            ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_TASK_ORG, -2252708287258544819L, 1, Long.valueOf(i));
        }
    }

    public final void requestAffordanceAnim(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        FullscreenTaskListener fullscreenTaskListener = (FullscreenTaskListener) this.mTaskListeners.get(-2);
        if (fullscreenTaskListener != null) {
            fullscreenTaskListener.animForAffordance(runningTaskInfo.taskId, i);
        }
    }

    public final void resetStashedFreeform(int i, boolean z) {
        TaskAppearedInfo taskAppearedInfo;
        synchronized (this.mLock) {
            taskAppearedInfo = (TaskAppearedInfo) this.mTasks.get(i);
        }
        if (taskAppearedInfo == null) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "addListenerForTaskId unknown taskId="));
        }
        TaskListener taskListener = getTaskListener(taskAppearedInfo.getTaskInfo(), false);
        if (taskListener != null) {
            taskListener.resetStashedFreeform(i, z);
        }
    }

    public final void setPendingLaunchCookieListener(IBinder iBinder, TaskListener taskListener) {
        synchronized (this.mLock) {
            this.mLaunchCookieToListener.put(iBinder, taskListener);
        }
    }

    public final void setTaskSurfaceVisibility(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) this.mTasks.get(i);
                if (taskAppearedInfo != null) {
                    SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                    transaction.setVisibility(taskAppearedInfo.getLeash(), z);
                    transaction.apply();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterOrganizer() {
        super.unregisterOrganizer();
        StartingWindowController startingWindowController = this.mStartingWindow;
        if (startingWindowController != null) {
            startingWindowController.mSplashScreenExecutor.execute(new StartingWindowController$$ExternalSyntheticLambda5(startingWindowController, 0));
        }
    }

    public ShellTaskOrganizer(ShellInit shellInit, ShellCommandHandler shellCommandHandler, CompatUIHandler compatUIHandler, Optional<UnfoldAnimationController> optional, Optional<RecentTasksController> optional2, ShellExecutor shellExecutor, Lazy lazy, TaskStackListenerImpl taskStackListenerImpl, Context context, FocusTransitionObserver focusTransitionObserver) {
        this(shellInit, shellCommandHandler, null, compatUIHandler, optional, optional2, shellExecutor, lazy, taskStackListenerImpl, context, focusTransitionObserver);
    }

    public ShellTaskOrganizer(ShellInit shellInit, ShellCommandHandler shellCommandHandler, ITaskOrganizerController iTaskOrganizerController, CompatUIHandler compatUIHandler, Optional<UnfoldAnimationController> optional, Optional<RecentTasksController> optional2, ShellExecutor shellExecutor, Lazy lazy, TaskStackListenerImpl taskStackListenerImpl, Context context, FocusTransitionObserver focusTransitionObserver) {
        super(iTaskOrganizerController, shellExecutor);
        this.mTaskListeners = new SparseArray();
        this.mTasks = new SparseArray();
        this.mLaunchCookieToListener = new ArrayMap();
        this.mVisibleTasksWithLocusId = new SparseArray();
        this.mLocusIdListeners = new ArraySet();
        this.mFocusListeners = new ArraySet();
        this.mTaskVanishedListeners = new ArraySet();
        this.mListenerIterator = null;
        this.mLock = new Object();
        this.mHomeTaskOverlayContainer = new SurfaceControl.Builder().setName("home_task_overlay_container").setContainerLayer().setHidden(false).setCallsite("ShellTaskOrganizer.mHomeTaskOverlayContainer").build();
        this.mMultiWindowCoreStateChangeListeners = new ArraySet();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mShellCommandHandler = shellCommandHandler;
        this.mCompatUI = compatUIHandler;
        this.mRecentTasks = optional2;
        this.mUnfoldAnimationController = optional.orElse(null);
        if (shellInit != null) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.ShellTaskOrganizer$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    final ShellTaskOrganizer shellTaskOrganizer = ShellTaskOrganizer.this;
                    shellTaskOrganizer.mShellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.ShellTaskOrganizer$$ExternalSyntheticLambda6
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            ShellTaskOrganizer shellTaskOrganizer2 = ShellTaskOrganizer.this;
                            PrintWriter printWriter = (PrintWriter) obj;
                            String str = (String) obj2;
                            synchronized (shellTaskOrganizer2.mLock) {
                                try {
                                    String str2 = str + "  ";
                                    String str3 = str2 + "  ";
                                    printWriter.println(str + "ShellTaskOrganizer");
                                    printWriter.println(str2 + shellTaskOrganizer2.mTaskListeners.size() + " Listeners");
                                    for (int size = shellTaskOrganizer2.mTaskListeners.size() + (-1); size >= 0; size += -1) {
                                        int keyAt = shellTaskOrganizer2.mTaskListeners.keyAt(size);
                                        ShellTaskOrganizer.TaskListener taskListener = (ShellTaskOrganizer.TaskListener) shellTaskOrganizer2.mTaskListeners.valueAt(size);
                                        printWriter.println(str2 + "#" + size + " " + ShellTaskOrganizer.taskListenerTypeToString(keyAt));
                                        taskListener.dump$2(printWriter, str3);
                                    }
                                    printWriter.println();
                                    printWriter.println(str2 + shellTaskOrganizer2.mTasks.size() + " Tasks");
                                    for (int size2 = shellTaskOrganizer2.mTasks.size() + (-1); size2 >= 0; size2 += -1) {
                                        int keyAt2 = shellTaskOrganizer2.mTasks.keyAt(size2);
                                        TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) shellTaskOrganizer2.mTasks.valueAt(size2);
                                        ShellTaskOrganizer.TaskListener taskListener2 = shellTaskOrganizer2.getTaskListener(taskAppearedInfo.getTaskInfo(), false);
                                        int windowingMode = taskAppearedInfo.getTaskInfo().getWindowingMode();
                                        String str4 = "";
                                        if (taskAppearedInfo.getTaskInfo().baseActivity != null) {
                                            str4 = taskAppearedInfo.getTaskInfo().baseActivity.getPackageName();
                                        }
                                        printWriter.println(str2 + "#" + size2 + " task=" + keyAt2 + " listener=" + taskListener2 + " wmMode=" + windowingMode + " pkg=" + str4 + " bounds=" + taskAppearedInfo.getTaskInfo().getConfiguration().windowConfiguration.getBounds() + " running=" + taskAppearedInfo.getTaskInfo().isRunning + " visible=" + taskAppearedInfo.getTaskInfo().isVisible + " focused=" + taskAppearedInfo.getTaskInfo().isFocused);
                                    }
                                    printWriter.println();
                                    printWriter.println(str2 + shellTaskOrganizer2.mLaunchCookieToListener.size() + " Launch Cookies");
                                    for (int size3 = shellTaskOrganizer2.mLaunchCookieToListener.size() + (-1); size3 >= 0; size3 += -1) {
                                        printWriter.println(str2 + "#" + size3 + " cookie=" + ((IBinder) shellTaskOrganizer2.mLaunchCookieToListener.keyAt(size3)) + " listener=" + ((ShellTaskOrganizer.TaskListener) shellTaskOrganizer2.mLaunchCookieToListener.valueAt(size3)));
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }, shellTaskOrganizer);
                    CompatUIHandler compatUIHandler2 = shellTaskOrganizer.mCompatUI;
                    if (compatUIHandler2 != null) {
                        compatUIHandler2.setCallback(new ShellTaskOrganizer$$ExternalSyntheticLambda0(shellTaskOrganizer, 1));
                    }
                    shellTaskOrganizer.registerOrganizer();
                }
            }, this);
        }
        this.mForcedResizableController = new ForcedResizableInfoActivityController(context, shellExecutor);
        if (taskStackListenerImpl != null) {
            taskStackListenerImpl.addListener(anonymousClass1);
        }
        ActivityThread currentActivityThread = ActivityThread.currentActivityThread();
        if (currentActivityThread != null) {
            currentActivityThread.registerMultiWindowCoreStateListener(new MultiWindowCoreState.MultiWindowCoreStateListener() { // from class: com.android.wm.shell.ShellTaskOrganizer$$ExternalSyntheticLambda8
                public final void onMultiWindowCoreStateChanged(final int i) {
                    final ShellTaskOrganizer shellTaskOrganizer = ShellTaskOrganizer.this;
                    int i2 = ShellTaskOrganizer.$r8$clinit;
                    shellTaskOrganizer.getExecutor().execute(new Runnable() { // from class: com.android.wm.shell.ShellTaskOrganizer$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            ShellTaskOrganizer shellTaskOrganizer2 = ShellTaskOrganizer.this;
                            int i3 = i;
                            for (int i4 = 0; i4 < shellTaskOrganizer2.mMultiWindowCoreStateChangeListeners.size(); i4++) {
                                ((ShellTaskOrganizer.MultiWindowCoreStateChangeListener) shellTaskOrganizer2.mMultiWindowCoreStateChangeListeners.valueAt(i4)).onMultiWindowCoreStateChanged(i3);
                            }
                        }
                    });
                }
            });
        }
        this.mLazyDesktopTasksController = lazy;
        this.mFocusTransitionObserver = focusTransitionObserver;
    }

    public final void onTaskAppeared(TaskAppearedInfo taskAppearedInfo) {
        int i = taskAppearedInfo.getTaskInfo().taskId;
        this.mTasks.put(i, taskAppearedInfo);
        TaskListener taskListener = getTaskListener(taskAppearedInfo.getTaskInfo(), true);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TASK_ORG_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, -3692726879013162739L, 1, Long.valueOf(i), String.valueOf(taskListener));
        }
        if (taskListener != null) {
            taskListener.onTaskAppeared(taskAppearedInfo.getTaskInfo(), taskAppearedInfo.getLeash());
        }
        UnfoldAnimationController unfoldAnimationController = this.mUnfoldAnimationController;
        if (unfoldAnimationController != null) {
            ActivityManager.RunningTaskInfo taskInfo = taskAppearedInfo.getTaskInfo();
            SurfaceControl leash = taskAppearedInfo.getLeash();
            unfoldAnimationController.mTaskSurfaces.put(taskInfo.taskId, leash);
            int i2 = 0;
            while (true) {
                if (i2 >= unfoldAnimationController.mAnimators.size()) {
                    break;
                }
                UnfoldTaskAnimator unfoldTaskAnimator = (UnfoldTaskAnimator) unfoldAnimationController.mAnimators.get(i2);
                if (unfoldTaskAnimator.isApplicableTask(taskInfo)) {
                    unfoldAnimationController.mAnimatorsByTaskId.put(taskInfo.taskId, unfoldTaskAnimator);
                    unfoldTaskAnimator.onTaskAppeared(taskInfo, leash);
                    break;
                }
                i2++;
            }
        }
        if (taskAppearedInfo.getTaskInfo().getActivityType() == 2) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TASK_ORG_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, 4851942932365456466L, 0, null);
            }
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            transaction.setLayer(this.mHomeTaskOverlayContainer, Integer.MAX_VALUE);
            transaction.reparent(this.mHomeTaskOverlayContainer, taskAppearedInfo.getLeash());
            transaction.apply();
        }
        notifyLocusVisibilityIfNeeded(taskAppearedInfo.getTaskInfo());
        notifyCompatUI(taskAppearedInfo.getTaskInfo(), taskListener);
        this.mRecentTasks.ifPresent(new ShellTaskOrganizer$$ExternalSyntheticLambda0(taskAppearedInfo, 0));
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface TaskListener {
        default void attachChildSurfaceToTask(int i, SurfaceControl.Builder builder) {
            throw new IllegalStateException("This task listener doesn't support child surface attachment.");
        }

        default boolean hasChild() {
            return false;
        }

        default boolean isMultiWindow() {
            return false;
        }

        default void reparentChildSurfaceToTask(int i, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
            throw new IllegalStateException("This task listener doesn't support child surface reparent.");
        }

        default boolean supportCompatUI() {
            return true;
        }

        default boolean supportCompatUI$1() {
            return false;
        }

        default void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) {
        }

        default void onSplitLayoutChangeRequested(TaskOrganizerInfo taskOrganizerInfo) {
        }

        default void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        }

        default void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        }

        default void onSplitPairUpdateRequested() {
        }

        default void dump$2(PrintWriter printWriter, String str) {
        }

        default void onTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) {
        }

        default void resetStashedFreeform(int i, boolean z) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface TaskVanishedListener {
        default void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        }
    }
}

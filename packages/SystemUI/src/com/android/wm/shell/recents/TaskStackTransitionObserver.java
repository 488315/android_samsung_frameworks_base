package com.android.wm.shell.recents;

import android.app.ActivityManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.DesktopModeFlags;
import android.window.TransitionInfo;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$$ExternalSyntheticOutline0;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.recents.TaskStackTransitionObserver;
import com.android.wm.shell.shared.GroupedTaskInfo;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class TaskStackTransitionObserver implements Transitions.TransitionObserver, ShellTaskOrganizer.TaskVanishedListener {
    public final ShellCommandHandler shellCommandHandler;
    public final Lazy shellTaskOrganizer;
    public final ArrayMap taskStackTransitionObserverListeners;
    public final Lazy transitions;
    public final List visibleTasks = new ArrayList();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface TaskStackTransitionObserverListener {
    }

    static {
        new Companion(null);
    }

    public TaskStackTransitionObserver(ShellInit shellInit, Lazy lazy, ShellCommandHandler shellCommandHandler, Lazy lazy2) {
        this.shellTaskOrganizer = lazy;
        this.shellCommandHandler = shellCommandHandler;
        this.transitions = lazy2;
        new ArrayList();
        this.taskStackTransitionObserverListeners = new ArrayMap();
        new TransitionUtil.LeafTaskFilter();
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.recents.TaskStackTransitionObserver.1
            @Override // java.lang.Runnable
            public final void run() {
                final TaskStackTransitionObserver taskStackTransitionObserver = TaskStackTransitionObserver.this;
                ((ShellTaskOrganizer) taskStackTransitionObserver.shellTaskOrganizer.get()).addTaskVanishedListener(taskStackTransitionObserver);
                taskStackTransitionObserver.shellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.recents.TaskStackTransitionObserver$onInit$1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        PrintWriter printWriter = (PrintWriter) obj;
                        String str = (String) obj2;
                        TaskStackTransitionObserver taskStackTransitionObserver2 = taskStackTransitionObserver;
                        taskStackTransitionObserver2.getClass();
                        printWriter.println(str + "TaskStackTransitionObserver:");
                        if (taskStackTransitionObserver2.visibleTasks.isEmpty()) {
                            QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str, "  visibleTasks=[]");
                            return;
                        }
                        StringJoiner stringJoiner = new StringJoiner(",\n\t", "[\n\t", "\n]");
                        for (ActivityManager.RunningTaskInfo runningTaskInfo : taskStackTransitionObserver2.visibleTasks) {
                            stringJoiner.add("id=" + runningTaskInfo.taskId + " cmp=" + runningTaskInfo.baseIntent.getComponent());
                        }
                        printWriter.println(str + "  visibleTasks=" + stringJoiner);
                    }
                }, taskStackTransitionObserver);
                ((Transitions) taskStackTransitionObserver.transitions.get()).registerObserver(taskStackTransitionObserver);
            }
        }, this);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        final ActivityManager.RunningTaskInfo taskInfo;
        if (DesktopModeFlags.ENABLE_TASK_STACK_OBSERVER_IN_SHELL.isTrue()) {
            for (TransitionInfo.Change change : transitionInfo.getChanges()) {
                if ((change.getFlags() & 2) == 0 && (taskInfo = change.getTaskInfo()) != null && taskInfo.taskId != -1) {
                    if (TransitionUtil.isOpeningType(change.getMode())) {
                        for (Map.Entry entry : this.taskStackTransitionObserverListeners.entrySet()) {
                            final TaskStackTransitionObserverListener taskStackTransitionObserverListener = (TaskStackTransitionObserverListener) entry.getKey();
                            ((Executor) entry.getValue()).execute(new Runnable() { // from class: com.android.wm.shell.recents.TaskStackTransitionObserver$notifyOnTaskMovedToFront$1$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    TaskStackTransitionObserver.TaskStackTransitionObserverListener taskStackTransitionObserverListener2 = taskStackTransitionObserverListener;
                                    ActivityManager.RunningTaskInfo runningTaskInfo = taskInfo;
                                    RecentTasksController recentTasksController = (RecentTasksController) taskStackTransitionObserverListener2;
                                    if (recentTasksController.mListener == null || !DesktopModeFlags.ENABLE_TASK_STACK_OBSERVER_IN_SHELL.isTrue() || runningTaskInfo.realActivity == null || RecentTasksController.excludeTaskFromGeneratedList(runningTaskInfo)) {
                                        return;
                                    }
                                    try {
                                        recentTasksController.mListener.onTaskMovedToFront(GroupedTaskInfo.forFullscreenTasks(runningTaskInfo));
                                    } catch (RemoteException e) {
                                        Slog.w("RecentTasksController", "Failed call onTaskMovedToFront", e);
                                    }
                                }
                            });
                        }
                        return;
                    }
                    if (change.getMode() == 6) {
                        for (Map.Entry entry2 : this.taskStackTransitionObserverListeners.entrySet()) {
                            final TaskStackTransitionObserverListener taskStackTransitionObserverListener2 = (TaskStackTransitionObserverListener) entry2.getKey();
                            ((Executor) entry2.getValue()).execute(new Runnable() { // from class: com.android.wm.shell.recents.TaskStackTransitionObserver$notifyOnTaskChanged$1$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    TaskStackTransitionObserver.TaskStackTransitionObserverListener taskStackTransitionObserverListener3 = taskStackTransitionObserverListener2;
                                    ActivityManager.RunningTaskInfo runningTaskInfo = taskInfo;
                                    RecentTasksController recentTasksController = (RecentTasksController) taskStackTransitionObserverListener3;
                                    if (recentTasksController.mListener == null || !DesktopModeFlags.ENABLE_TASK_STACK_OBSERVER_IN_SHELL.isTrue() || runningTaskInfo.realActivity == null || RecentTasksController.excludeTaskFromGeneratedList(runningTaskInfo)) {
                                        return;
                                    }
                                    try {
                                        recentTasksController.mListener.onTaskInfoChanged(runningTaskInfo);
                                    } catch (RemoteException e) {
                                        Slog.w("RecentTasksController", "Failed call onTaskInfoChanged", e);
                                    }
                                }
                            });
                        }
                    }
                }
            }
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskVanishedListener
    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionStarting(IBinder iBinder) {
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionFinished(IBinder iBinder, boolean z) {
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
    }
}

package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.app.TaskInfo;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.SystemProperties;
import android.util.EventLog;
import android.util.SparseArray;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import androidx.core.util.SparseArrayKt;
import com.android.internal.protolog.ProtoLog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopModeLoggerTransitionObserver implements Transitions.TransitionObserver {
    public final DesktopModeEventLogger desktopModeEventLogger;
    public final Optional desktopTasksLimiter;
    public TaskInfo focusedFreeformTask;
    public boolean isSessionActive;
    public final SparseArray tasksSavedForRecents;
    public final Transitions transitions;
    public final SparseArray visibleFreeformTaskInfos;
    public boolean wasPreviousTransitionExitByScreenOff;
    public boolean wasPreviousTransitionExitToOverview;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getVISIBLE_TASKS_COUNTER_NAME$annotations() {
        }

        public static /* synthetic */ void getVISIBLE_TASKS_COUNTER_SYSTEM_PROPERTY$annotations() {
        }
    }

    static {
        new Companion(null);
    }

    public DesktopModeLoggerTransitionObserver(ShellInit shellInit, Transitions transitions, DesktopModeEventLogger desktopModeEventLogger, Optional<DesktopTasksLimiter> optional, DesktopState desktopState) {
        this.transitions = transitions;
        this.desktopModeEventLogger = desktopModeEventLogger;
        this.desktopTasksLimiter = optional;
        if (((DesktopStateImpl) desktopState).canEnterDesktopMode) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeLoggerTransitionObserver.1
                @Override // java.lang.Runnable
                public final void run() {
                    DesktopModeLoggerTransitionObserver desktopModeLoggerTransitionObserver = DesktopModeLoggerTransitionObserver.this;
                    desktopModeLoggerTransitionObserver.transitions.registerObserver(desktopModeLoggerTransitionObserver);
                    SystemProperties.set("debug.tracing.desktop_mode_visible_tasks", "0");
                    desktopModeLoggerTransitionObserver.desktopModeEventLogger.getClass();
                    DesktopModeEventLogger.logTaskUpdate(4, 0, new DesktopModeEventLogger.Companion.TaskUpdate(0, 0, 0, 0, 0, 0, null, null, 0, null, KnoxEnterpriseLicenseManager.ERROR_LICENSE_QUANTITY_EXHAUSTED_ON_AUTO_RELEASE, null));
                }
            }, this);
        }
        this.visibleFreeformTaskInfos = new SparseArray();
        this.tasksSavedForRecents = new SparseArray();
    }

    public static DesktopModeEventLogger.Companion.TaskUpdate buildTaskUpdateForTask$default(DesktopModeLoggerTransitionObserver desktopModeLoggerTransitionObserver, TaskInfo taskInfo, int i, DesktopModeEventLogger.Companion.MinimizeReason minimizeReason, DesktopModeEventLogger.Companion.FocusReason focusReason, int i2) {
        DesktopModeEventLogger.Companion.MinimizeReason minimizeReason2 = (i2 & 4) != 0 ? null : minimizeReason;
        DesktopModeEventLogger.Companion.FocusReason focusReason2 = (i2 & 16) != 0 ? null : focusReason;
        desktopModeLoggerTransitionObserver.getClass();
        Rect bounds = taskInfo.configuration.windowConfiguration.getBounds();
        Point point = taskInfo.positionInParent;
        return new DesktopModeEventLogger.Companion.TaskUpdate(taskInfo.taskId, taskInfo.effectiveUid, bounds.height(), bounds.width(), point.x, point.y, minimizeReason2, null, i, focusReason2);
    }

    public static boolean isExitToRecentsTransition(TransitionInfo transitionInfo) {
        return transitionInfo.getType() == 3 && transitionInfo.getFlags() == 128;
    }

    public static ActivityManager.RunningTaskInfo requireTaskInfo(TransitionInfo.Change change) {
        ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
        if (taskInfo != null) {
            return taskInfo;
        }
        throw new IllegalStateException("Expected TaskInfo in the Change");
    }

    public final void addTaskInfosToCachedMap(TaskInfo taskInfo) {
        this.visibleFreeformTaskInfos.set(taskInfo.taskId, taskInfo);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0182  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void identifyAndLogTaskUpdates(android.os.IBinder r32, android.window.TransitionInfo r33, android.util.SparseArray r34, android.util.SparseArray r35, android.app.TaskInfo r36) {
        /*
            Method dump skipped, instructions count: 488
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.DesktopModeLoggerTransitionObserver.identifyAndLogTaskUpdates(android.os.IBinder, android.window.TransitionInfo, android.util.SparseArray, android.util.SparseArray, android.app.TaskInfo):void");
    }

    public final void identifyLogEventAndUpdateState(IBinder iBinder, TransitionInfo transitionInfo, SparseArray sparseArray, SparseArray sparseArray2, TaskInfo taskInfo) {
        DesktopModeEventLogger.Companion.EnterReason enterReason;
        DesktopModeEventLogger.Companion.ExitReason exitReason;
        int size = sparseArray2.size();
        DesktopModeEventLogger desktopModeEventLogger = this.desktopModeEventLogger;
        if (size == 0 && sparseArray.size() != 0 && this.isSessionActive) {
            identifyAndLogTaskUpdates(iBinder, transitionInfo, sparseArray, sparseArray2, taskInfo);
            if (transitionInfo != null && transitionInfo.getType() == 12) {
                this.wasPreviousTransitionExitByScreenOff = true;
                exitReason = DesktopModeEventLogger.Companion.ExitReason.SCREEN_OFF;
            } else if (transitionInfo != null && transitionInfo.getType() == 4) {
                exitReason = DesktopModeEventLogger.Companion.ExitReason.TASK_MOVED_TO_BACK;
            } else if (transitionInfo != null && transitionInfo.getType() == 2) {
                exitReason = DesktopModeEventLogger.Companion.ExitReason.TASK_FINISHED;
            } else if (transitionInfo != null && transitionInfo.getType() == 1105) {
                exitReason = DesktopModeEventLogger.Companion.ExitReason.DRAG_TO_EXIT;
            } else if (transitionInfo != null && transitionInfo.getType() == 1106) {
                exitReason = DesktopModeEventLogger.Companion.ExitReason.APP_HANDLE_MENU_BUTTON_EXIT;
            } else if (transitionInfo != null && transitionInfo.getType() == 1107) {
                exitReason = DesktopModeEventLogger.Companion.ExitReason.KEYBOARD_SHORTCUT_EXIT;
            } else if (transitionInfo != null && isExitToRecentsTransition(transitionInfo)) {
                exitReason = DesktopModeEventLogger.Companion.ExitReason.RETURN_HOME_OR_OVERVIEW;
            } else if (transitionInfo == null || transitionInfo.getType() != 1020) {
                ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "Unknown exit reason for transition type: %s", new Object[]{transitionInfo != null ? Integer.valueOf(transitionInfo.getType()) : null});
                exitReason = DesktopModeEventLogger.Companion.ExitReason.UNKNOWN_EXIT;
            } else {
                exitReason = DesktopModeEventLogger.Companion.ExitReason.TASK_MINIMIZED;
            }
            int andSet = desktopModeEventLogger.currentSessionId.getAndSet(0);
            if (andSet == 0) {
                ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: No session id found for logging exit from desktop mode", new Object[0]);
            } else {
                ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: Logging session exit, session: %s reason: %s", new Object[]{Integer.valueOf(andSet), exitReason.name()});
                FrameworkStatsLog.write(818, 2, 0, exitReason.getReason(), andSet);
                EventLog.writeEvent(38501, Integer.valueOf(exitReason.getReason()), Integer.valueOf(andSet));
            }
            this.isSessionActive = false;
        } else if (sparseArray2.size() != 0 && sparseArray.size() == 0 && !this.isSessionActive) {
            this.isSessionActive = true;
            if ((transitionInfo != null && transitionInfo.getType() == 11) || (transitionInfo != null && transitionInfo.getType() == 4 && this.wasPreviousTransitionExitByScreenOff)) {
                enterReason = DesktopModeEventLogger.Companion.EnterReason.SCREEN_ON;
            } else if (transitionInfo != null && transitionInfo.getType() == 1110) {
                enterReason = DesktopModeEventLogger.Companion.EnterReason.APP_HANDLE_DRAG;
            } else if (transitionInfo != null && transitionInfo.getType() == 1101) {
                enterReason = DesktopModeEventLogger.Companion.EnterReason.APP_HANDLE_MENU_BUTTON;
            } else if (transitionInfo != null && transitionInfo.getType() == 1102) {
                enterReason = DesktopModeEventLogger.Companion.EnterReason.APP_FROM_OVERVIEW;
            } else if (transitionInfo != null && transitionInfo.getType() == 1103) {
                enterReason = DesktopModeEventLogger.Companion.EnterReason.KEYBOARD_SHORTCUT_ENTER;
            } else if (transitionInfo != null && transitionInfo.getType() == 3) {
                enterReason = DesktopModeEventLogger.Companion.EnterReason.OVERVIEW;
            } else if (this.wasPreviousTransitionExitToOverview) {
                enterReason = DesktopModeEventLogger.Companion.EnterReason.OVERVIEW;
            } else if (transitionInfo == null || transitionInfo.getType() != 1) {
                ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "Unknown enter reason for transition type: %s", new Object[]{transitionInfo != null ? Integer.valueOf(transitionInfo.getType()) : null});
                enterReason = DesktopModeEventLogger.Companion.EnterReason.UNKNOWN_ENTER;
            } else {
                enterReason = DesktopModeEventLogger.Companion.EnterReason.APP_FREEFORM_INTENT;
            }
            this.wasPreviousTransitionExitByScreenOff = false;
            int nextInt = desktopModeEventLogger.random.nextInt(1048576) + 1;
            int andSet2 = desktopModeEventLogger.currentSessionId.getAndSet(nextInt);
            if (andSet2 != 0) {
                ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: Existing desktop mode session id: %s found on desktop mode enter", new Object[]{Integer.valueOf(andSet2)});
            }
            ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: Logging session enter, session: %s reason: %s", new Object[]{Integer.valueOf(nextInt), enterReason.name()});
            FrameworkStatsLog.write(818, 1, enterReason.getReason(), 0, nextInt);
            EventLog.writeEvent(38500, Integer.valueOf(enterReason.getReason()), Integer.valueOf(nextInt));
            identifyAndLogTaskUpdates(iBinder, transitionInfo, sparseArray, sparseArray2, taskInfo);
        } else if (this.isSessionActive) {
            identifyAndLogTaskUpdates(iBinder, transitionInfo, sparseArray, sparseArray2, taskInfo);
        }
        this.visibleFreeformTaskInfos.clear();
        SparseArrayKt.putAll(this.visibleFreeformTaskInfos, sparseArray2);
        this.focusedFreeformTask = taskInfo;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        SparseArray sparseArray;
        SparseArray sparseArray2;
        Object obj;
        int i = 0;
        if (isExitToRecentsTransition(transitionInfo) && this.tasksSavedForRecents.size() == 0) {
            ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: Recents animation running, saving tasks for later", new Object[0]);
            SparseArrayKt.putAll(this.tasksSavedForRecents, this.visibleFreeformTaskInfos);
        }
        if (transitionInfo.getType() == 12) {
            sparseArray = new SparseArray();
        } else {
            List changes = transitionInfo.getChanges();
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : changes) {
                TransitionInfo.Change change = (TransitionInfo.Change) obj2;
                if (change.getTaskInfo() != null && requireTaskInfo(change).taskId != -1) {
                    arrayList.add(obj2);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj3 = arrayList.get(i2);
                i2++;
                TransitionInfo.Change change2 = (TransitionInfo.Change) obj3;
                change2.getClass();
                if (requireTaskInfo(change2).getWindowingMode() == 5 || this.visibleFreeformTaskInfos.indexOfKey(requireTaskInfo(change2).taskId) >= 0) {
                    arrayList2.add(obj3);
                }
            }
            SparseArray sparseArray3 = new SparseArray();
            SparseArrayKt.putAll(sparseArray3, this.visibleFreeformTaskInfos);
            int size2 = arrayList2.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj4 = arrayList2.get(i3);
                i3++;
                TransitionInfo.Change change3 = (TransitionInfo.Change) obj4;
                change3.getClass();
                ActivityManager.RunningTaskInfo requireTaskInfo = requireTaskInfo(change3);
                if (this.visibleFreeformTaskInfos.indexOfKey(requireTaskInfo.taskId) >= 0 && ((TaskInfo) this.visibleFreeformTaskInfos.get(requireTaskInfo.taskId)).getWindowingMode() == 5 && requireTaskInfo.getWindowingMode() != 5) {
                    sparseArray3.remove(requireTaskInfo.taskId);
                } else if (!TransitionUtil.isOpeningType(change3.getMode()) && (TransitionUtil.isClosingType(change3.getMode()) || change3.getMode() != 6)) {
                    sparseArray3.remove(requireTaskInfo.taskId);
                } else {
                    sparseArray3.put(requireTaskInfo.taskId, requireTaskInfo);
                }
            }
            ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: taskInfo map after processing changes %s", new Object[]{Integer.valueOf(sparseArray3.size())});
            sparseArray = sparseArray3;
        }
        if (transitionInfo.getType() == 0 && transitionInfo.getFlags() == 0 && this.tasksSavedForRecents.size() != 0) {
            ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: Canceled recents animation, restoring tasks", new Object[0]);
            SparseArray sparseArray4 = this.tasksSavedForRecents;
            SparseArray sparseArray5 = new SparseArray(sparseArray4.size() + sparseArray.size());
            SparseArrayKt.putAll(sparseArray5, sparseArray);
            SparseArrayKt.putAll(sparseArray5, sparseArray4);
            this.tasksSavedForRecents.clear();
            sparseArray2 = sparseArray5;
        } else {
            sparseArray2 = sparseArray;
        }
        SparseArray sparseArray6 = this.visibleFreeformTaskInfos;
        List changes2 = transitionInfo.getChanges();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj5 : changes2) {
            TransitionInfo.Change change4 = (TransitionInfo.Change) obj5;
            if (change4.getTaskInfo() != null && requireTaskInfo(change4).taskId != -1) {
                arrayList3.add(obj5);
            }
        }
        ArrayList arrayList4 = new ArrayList();
        int size3 = arrayList3.size();
        while (i < size3) {
            Object obj6 = arrayList3.get(i);
            i++;
            TransitionInfo.Change change5 = (TransitionInfo.Change) obj6;
            change5.getClass();
            if (requireTaskInfo(change5).getWindowingMode() == 5) {
                arrayList4.add(obj6);
            }
        }
        ListIterator listIterator = arrayList4.listIterator(arrayList4.size());
        while (true) {
            if (!listIterator.hasPrevious()) {
                obj = null;
                break;
            }
            obj = listIterator.previous();
            TransitionInfo.Change change6 = (TransitionInfo.Change) obj;
            if (change6.hasFlags(1048576) || change6.getMode() == 1) {
                break;
            }
        }
        TransitionInfo.Change change7 = (TransitionInfo.Change) obj;
        identifyLogEventAndUpdateState(iBinder, transitionInfo, sparseArray6, sparseArray2, change7 != null ? change7.getTaskInfo() : null);
        this.wasPreviousTransitionExitToOverview = isExitToRecentsTransition(transitionInfo);
    }

    public final void setFocusedTaskForTesting(TaskInfo taskInfo) {
        this.focusedFreeformTask = taskInfo;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionStarting(IBinder iBinder) {
    }

    public static /* synthetic */ void isSessionActive$annotations() {
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionFinished(IBinder iBinder, boolean z) {
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
    }
}

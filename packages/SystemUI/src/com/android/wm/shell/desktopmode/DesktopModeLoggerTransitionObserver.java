package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.app.TaskInfo;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.EventLog;
import android.util.SparseArray;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import androidx.core.util.SparseArrayKt;
import com.android.internal.protolog.ProtoLog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopTasksLimiter;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
    /* JADX WARN: Removed duplicated region for block: B:27:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void identifyAndLogTaskUpdates(IBinder iBinder, TransitionInfo transitionInfo, SparseArray sparseArray, SparseArray sparseArray2, TaskInfo taskInfo) {
        String str;
        DesktopModeEventLogger desktopModeEventLogger;
        DesktopTasksLimiter.TaskDetails taskDetails;
        DesktopTasksLimiter.TaskDetails taskDetails2;
        long j;
        DesktopModeEventLogger.Companion.MinimizeReason minimizeReason;
        DesktopModeEventLogger.Companion.MinimizeReason minimizeReason2;
        String str2;
        long j2;
        int i;
        DesktopTasksLimiter.TaskDetails taskDetails3;
        DesktopTasksLimiter desktopTasksLimiter;
        DesktopModeLoggerTransitionObserver desktopModeLoggerTransitionObserver = this;
        int size = sparseArray2.size();
        int i2 = 0;
        while (true) {
            str = "debug.tracing.desktop_mode_visible_tasks";
            desktopModeEventLogger = desktopModeLoggerTransitionObserver.desktopModeEventLogger;
            if (i2 >= size) {
                break;
            }
            int iKeyAt = sparseArray2.keyAt(i2);
            TaskInfo taskInfo2 = (TaskInfo) sparseArray2.valueAt(i2);
            DesktopModeEventLogger.Companion.FocusReason focusReason = (taskInfo == null || iKeyAt != taskInfo.taskId || taskInfo.equals(desktopModeLoggerTransitionObserver.focusedFreeformTask)) ? null : DesktopModeEventLogger.Companion.FocusReason.UNKNOWN;
            DesktopModeEventLogger.Companion.TaskUpdate taskUpdateBuildTaskUpdateForTask$default = buildTaskUpdateForTask$default(desktopModeLoggerTransitionObserver, taskInfo2, sparseArray2.size(), null, focusReason, 12);
            TaskInfo taskInfo3 = (TaskInfo) sparseArray.get(iKeyAt);
            if (taskInfo3 == null) {
                if (iBinder == null || (desktopTasksLimiter = (DesktopTasksLimiter) desktopModeLoggerTransitionObserver.desktopTasksLimiter.orElse(null)) == null) {
                    taskDetails3 = null;
                } else {
                    DesktopTasksLimiter.MinimizeTransitionObserver minimizeTransitionObserver = desktopTasksLimiter.minimizeTransitionObserver;
                    taskDetails3 = (DesktopTasksLimiter.TaskDetails) ((LinkedHashMap) minimizeTransitionObserver.pendingUnminimizeTransitionTokensAndTasks).get(iBinder);
                    if (taskDetails3 == null) {
                        taskDetails3 = (DesktopTasksLimiter.TaskDetails) ((LinkedHashMap) minimizeTransitionObserver.activeUnminimizeTransitionTokensAndTasks).get(iBinder);
                    }
                }
                if (taskDetails3 != null) {
                    DesktopModeEventLogger.Companion.UnminimizeReason unminimizeReason = taskDetails3.taskId == taskInfo2.taskId ? taskDetails3.unminimizeReason : null;
                    DesktopModeEventLogger.Companion.TaskUpdate taskUpdate = new DesktopModeEventLogger.Companion.TaskUpdate(taskUpdateBuildTaskUpdateForTask$default.instanceId, taskUpdateBuildTaskUpdateForTask$default.uid, taskUpdateBuildTaskUpdateForTask$default.taskHeight, taskUpdateBuildTaskUpdateForTask$default.taskWidth, taskUpdateBuildTaskUpdateForTask$default.taskX, taskUpdateBuildTaskUpdateForTask$default.taskY, taskUpdateBuildTaskUpdateForTask$default.minimizeReason, unminimizeReason, taskUpdateBuildTaskUpdateForTask$default.visibleTaskCount, taskUpdateBuildTaskUpdateForTask$default.focusReason);
                    int i3 = desktopModeEventLogger.currentSessionId.get();
                    if (i3 == 0) {
                        ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: No session id found for logging task added", new Object[0]);
                    } else {
                        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: Logging task added, session: %s taskId: %s", new Object[]{Integer.valueOf(i3), Integer.valueOf(taskUpdate.instanceId)});
                        DesktopModeEventLogger.logTaskUpdate(1, i3, taskUpdate);
                    }
                    Trace.setCounter(32L, "desktop_mode_visible_tasks", sparseArray2.size());
                    SystemProperties.set("debug.tracing.desktop_mode_visible_tasks", String.valueOf(sparseArray2.size()));
                }
                i2++;
            } else if (focusReason != null) {
                desktopModeEventLogger.logTaskInfoChanged(taskUpdateBuildTaskUpdateForTask$default);
            } else {
                desktopModeLoggerTransitionObserver = this;
                if (!buildTaskUpdateForTask$default(desktopModeLoggerTransitionObserver, taskInfo3, sparseArray2.size(), null, focusReason, 12).equals(taskUpdateBuildTaskUpdateForTask$default)) {
                    desktopModeEventLogger.logTaskInfoChanged(taskUpdateBuildTaskUpdateForTask$default);
                }
                i2++;
            }
            desktopModeLoggerTransitionObserver = this;
            i2++;
        }
        long j3 = 32;
        int size2 = sparseArray.size();
        int i4 = 0;
        while (i4 < size2) {
            int iKeyAt2 = sparseArray.keyAt(i4);
            TaskInfo taskInfo4 = (TaskInfo) sparseArray.valueAt(i4);
            if (sparseArray2.indexOfKey(iKeyAt2) >= 0) {
                str2 = str;
                j2 = j3;
            } else {
                if (transitionInfo == null || transitionInfo.getType() != 1020) {
                    if (iBinder != null) {
                        taskDetails = null;
                        DesktopTasksLimiter desktopTasksLimiter2 = (DesktopTasksLimiter) desktopModeLoggerTransitionObserver.desktopTasksLimiter.orElse(null);
                        if (desktopTasksLimiter2 != null) {
                            DesktopTasksLimiter.MinimizeTransitionObserver minimizeTransitionObserver2 = desktopTasksLimiter2.minimizeTransitionObserver;
                            taskDetails2 = (DesktopTasksLimiter.TaskDetails) ((LinkedHashMap) minimizeTransitionObserver2.pendingUnminimizeTransitionTokensAndTasks).get(iBinder);
                            if (taskDetails2 == null && (taskDetails2 = (DesktopTasksLimiter.TaskDetails) ((LinkedHashMap) minimizeTransitionObserver2.pendingTransitionTokensAndTasks).get(iBinder)) == null) {
                                taskDetails2 = (DesktopTasksLimiter.TaskDetails) ((LinkedHashMap) minimizeTransitionObserver2.activeTransitionTokensAndTasks).get(iBinder);
                            }
                        }
                        if (taskDetails2 != null) {
                            if (taskDetails2.taskId == taskInfo4.taskId) {
                                minimizeReason2 = taskDetails2.minimizeReason;
                                j = j3;
                            }
                        }
                        j = j3;
                        minimizeReason = taskDetails;
                        str2 = str;
                        j2 = j;
                        DesktopModeEventLogger.Companion.TaskUpdate taskUpdateBuildTaskUpdateForTask$default2 = buildTaskUpdateForTask$default(desktopModeLoggerTransitionObserver, taskInfo4, sparseArray2.size(), minimizeReason, null, 24);
                        i = desktopModeEventLogger.currentSessionId.get();
                        if (i == 0) {
                            ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: No session id found for logging task removed", new Object[0]);
                        } else {
                            ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: Logging task remove, session: %s taskId: %s", new Object[]{Integer.valueOf(i), Integer.valueOf(taskUpdateBuildTaskUpdateForTask$default2.instanceId)});
                            DesktopModeEventLogger.logTaskUpdate(2, i, taskUpdateBuildTaskUpdateForTask$default2);
                        }
                        Trace.setCounter(j2, "desktop_mode_visible_tasks", sparseArray2.size());
                        SystemProperties.set(str2, String.valueOf(sparseArray2.size()));
                    } else {
                        taskDetails = null;
                    }
                    taskDetails2 = taskDetails;
                    if (taskDetails2 != null) {
                    }
                    j = j3;
                    minimizeReason = taskDetails;
                    str2 = str;
                    j2 = j;
                    DesktopModeEventLogger.Companion.TaskUpdate taskUpdateBuildTaskUpdateForTask$default22 = buildTaskUpdateForTask$default(desktopModeLoggerTransitionObserver, taskInfo4, sparseArray2.size(), minimizeReason, null, 24);
                    i = desktopModeEventLogger.currentSessionId.get();
                    if (i == 0) {
                    }
                    Trace.setCounter(j2, "desktop_mode_visible_tasks", sparseArray2.size());
                    SystemProperties.set(str2, String.valueOf(sparseArray2.size()));
                } else {
                    minimizeReason2 = DesktopModeEventLogger.Companion.MinimizeReason.MINIMIZE_BUTTON;
                    j = j3;
                }
                minimizeReason = minimizeReason2;
                str2 = str;
                j2 = j;
                DesktopModeEventLogger.Companion.TaskUpdate taskUpdateBuildTaskUpdateForTask$default222 = buildTaskUpdateForTask$default(desktopModeLoggerTransitionObserver, taskInfo4, sparseArray2.size(), minimizeReason, null, 24);
                i = desktopModeEventLogger.currentSessionId.get();
                if (i == 0) {
                }
                Trace.setCounter(j2, "desktop_mode_visible_tasks", sparseArray2.size());
                SystemProperties.set(str2, String.valueOf(sparseArray2.size()));
            }
            i4++;
            desktopModeLoggerTransitionObserver = this;
            j3 = j2;
            str = str2;
        }
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
            } else if ((transitionInfo != null && transitionInfo.getType() == 3) || this.wasPreviousTransitionExitToOverview) {
                enterReason = DesktopModeEventLogger.Companion.EnterReason.OVERVIEW;
            } else if (transitionInfo == null || transitionInfo.getType() != 1) {
                ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "Unknown enter reason for transition type: %s", new Object[]{transitionInfo != null ? Integer.valueOf(transitionInfo.getType()) : null});
                enterReason = DesktopModeEventLogger.Companion.EnterReason.UNKNOWN_ENTER;
            } else {
                enterReason = DesktopModeEventLogger.Companion.EnterReason.APP_FREEFORM_INTENT;
            }
            this.wasPreviousTransitionExitByScreenOff = false;
            int iNextInt = desktopModeEventLogger.random.nextInt(1048576) + 1;
            int andSet2 = desktopModeEventLogger.currentSessionId.getAndSet(iNextInt);
            if (andSet2 != 0) {
                ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: Existing desktop mode session id: %s found on desktop mode enter", new Object[]{Integer.valueOf(andSet2)});
            }
            ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: Logging session enter, session: %s reason: %s", new Object[]{Integer.valueOf(iNextInt), enterReason.name()});
            FrameworkStatsLog.write(818, 1, enterReason.getReason(), 0, iNextInt);
            EventLog.writeEvent(38500, Integer.valueOf(enterReason.getReason()), Integer.valueOf(iNextInt));
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
        Object objPrevious;
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
            for (Object obj : changes) {
                TransitionInfo.Change change = (TransitionInfo.Change) obj;
                if (change.getTaskInfo() != null && requireTaskInfo(change).taskId != -1) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj2 = arrayList.get(i2);
                i2++;
                TransitionInfo.Change change2 = (TransitionInfo.Change) obj2;
                change2.getClass();
                if (requireTaskInfo(change2).getWindowingMode() == 5 || this.visibleFreeformTaskInfos.indexOfKey(requireTaskInfo(change2).taskId) >= 0) {
                    arrayList2.add(obj2);
                }
            }
            SparseArray sparseArray3 = new SparseArray();
            SparseArrayKt.putAll(sparseArray3, this.visibleFreeformTaskInfos);
            int size2 = arrayList2.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj3 = arrayList2.get(i3);
                i3++;
                TransitionInfo.Change change3 = (TransitionInfo.Change) obj3;
                change3.getClass();
                ActivityManager.RunningTaskInfo runningTaskInfoRequireTaskInfo = requireTaskInfo(change3);
                if (this.visibleFreeformTaskInfos.indexOfKey(runningTaskInfoRequireTaskInfo.taskId) >= 0 && ((TaskInfo) this.visibleFreeformTaskInfos.get(runningTaskInfoRequireTaskInfo.taskId)).getWindowingMode() == 5 && runningTaskInfoRequireTaskInfo.getWindowingMode() != 5) {
                    sparseArray3.remove(runningTaskInfoRequireTaskInfo.taskId);
                } else if (!TransitionUtil.isOpeningType(change3.getMode()) && (TransitionUtil.isClosingType(change3.getMode()) || change3.getMode() != 6)) {
                    sparseArray3.remove(runningTaskInfoRequireTaskInfo.taskId);
                } else {
                    sparseArray3.put(runningTaskInfoRequireTaskInfo.taskId, runningTaskInfoRequireTaskInfo);
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
        for (Object obj4 : changes2) {
            TransitionInfo.Change change4 = (TransitionInfo.Change) obj4;
            if (change4.getTaskInfo() != null && requireTaskInfo(change4).taskId != -1) {
                arrayList3.add(obj4);
            }
        }
        ArrayList arrayList4 = new ArrayList();
        int size3 = arrayList3.size();
        while (i < size3) {
            Object obj5 = arrayList3.get(i);
            i++;
            TransitionInfo.Change change5 = (TransitionInfo.Change) obj5;
            change5.getClass();
            if (requireTaskInfo(change5).getWindowingMode() == 5) {
                arrayList4.add(obj5);
            }
        }
        ListIterator listIterator = arrayList4.listIterator(arrayList4.size());
        while (true) {
            if (!listIterator.hasPrevious()) {
                objPrevious = null;
                break;
            }
            objPrevious = listIterator.previous();
            TransitionInfo.Change change6 = (TransitionInfo.Change) objPrevious;
            if (change6.hasFlags(1048576) || change6.getMode() == 1) {
                break;
            }
        }
        TransitionInfo.Change change7 = (TransitionInfo.Change) objPrevious;
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

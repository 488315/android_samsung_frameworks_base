package com.android.server.wm;

import android.app.ITaskStackListener;
import android.app.TaskInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Slog;
import android.window.TaskSnapshot;

import com.android.internal.os.SomeArgs;
import com.android.internal.util.function.pooled.PooledLambda;

import com.samsung.android.rune.CoreRune;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TaskChangeNotificationController {
    public final MainHandler mHandler;
    public final ActivityTaskSupervisor mTaskSupervisor;
    public final RemoteCallbackList mRemoteTaskStackListeners = new RemoteCallbackList();
    public final ArrayList mLocalTaskStackListeners = new ArrayList();
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyTaskStackChanged =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(0);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0 mNotifyTaskCreated =
            new TaskChangeNotificationController$$ExternalSyntheticLambda0(2);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0 mNotifyTaskRemoved =
            new TaskChangeNotificationController$$ExternalSyntheticLambda0(12);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyTaskMovedToFront =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(13);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyTaskDescriptionChanged =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(14);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyBackPressedOnTaskRoot =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(15);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyActivityRequestedOrientationChanged =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(16);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyTaskRemovalStarted =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(17);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0 mNotifyActivityPinned =
            new TaskChangeNotificationController$$ExternalSyntheticLambda0(18);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyActivityUnpinned =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(19);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyActivityRestartAttempt =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(11);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyActivityForcedResizable =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(20);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyActivityDismissingDockedTask =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(21);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyActivityLaunchOnSecondaryDisplayFailed =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(22);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyActivityLaunchOnSecondaryDisplayRerouted =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(23);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyTaskProfileLocked =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(24);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyTaskSnapshotChanged =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(25);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyTaskSnapshotInvalidated =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(26);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyTaskDisplayChanged =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(27);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0 mNotifyTaskListUpdated =
            new TaskChangeNotificationController$$ExternalSyntheticLambda0(1);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0 mNotifyTaskListFrozen =
            new TaskChangeNotificationController$$ExternalSyntheticLambda0(3);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyTaskFocusChanged =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(4);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyTaskRequestedOrientationChanged =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(5);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyOnActivityRotation =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(6);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0 mNotifyTaskMovedToBack =
            new TaskChangeNotificationController$$ExternalSyntheticLambda0(7);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyLockTaskModeChanged =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(8);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyTaskWindowingModeChanged =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(9);
    public final TaskChangeNotificationController$$ExternalSyntheticLambda0
            mNotifyTaskbarVisibleChanged =
                    new TaskChangeNotificationController$$ExternalSyntheticLambda0(10);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            TaskChangeNotificationController taskChangeNotificationController =
                    TaskChangeNotificationController.this;
            switch (i) {
                case 2:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyTaskStackChanged,
                            message);
                    break;
                case 3:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyActivityPinned,
                            message);
                    break;
                case 4:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyActivityRestartAttempt,
                            message);
                    break;
                case 6:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyActivityForcedResizable,
                            message);
                    break;
                case 7:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyActivityDismissingDockedTask,
                            message);
                    break;
                case 8:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyTaskCreated,
                            message);
                    break;
                case 9:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyTaskRemoved,
                            message);
                    break;
                case 10:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyTaskMovedToFront,
                            message);
                    break;
                case 11:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyTaskDescriptionChanged,
                            message);
                    break;
                case 12:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController
                                    .mNotifyActivityRequestedOrientationChanged,
                            message);
                    break;
                case 13:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyTaskRemovalStarted,
                            message);
                    break;
                case 14:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyTaskProfileLocked,
                            message);
                    break;
                case 15:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyTaskSnapshotChanged,
                            message);
                    ((TaskSnapshot) message.obj).removeReference(1);
                    break;
                case 17:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyActivityUnpinned,
                            message);
                    break;
                case 18:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController
                                    .mNotifyActivityLaunchOnSecondaryDisplayFailed,
                            message);
                    break;
                case 19:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController
                                    .mNotifyActivityLaunchOnSecondaryDisplayRerouted,
                            message);
                    break;
                case 20:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyBackPressedOnTaskRoot,
                            message);
                    break;
                case 21:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyTaskDisplayChanged,
                            message);
                    break;
                case 22:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyTaskListUpdated,
                            message);
                    break;
                case 23:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyTaskListFrozen,
                            message);
                    break;
                case 24:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyTaskFocusChanged,
                            message);
                    break;
                case 25:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyTaskRequestedOrientationChanged,
                            message);
                    break;
                case 26:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyOnActivityRotation,
                            message);
                    break;
                case 27:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyTaskMovedToBack,
                            message);
                    break;
                case 28:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyLockTaskModeChanged,
                            message);
                    break;
                case 29:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyTaskSnapshotInvalidated,
                            message);
                    break;
                case 30:
                    TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                            taskChangeNotificationController,
                            taskChangeNotificationController.mNotifyTaskWindowingModeChanged,
                            message);
                    break;
                case 31:
                    if (CoreRune.FW_NOTIFY_TASKBAR_VISIBLE) {
                        TaskChangeNotificationController.m1069$$Nest$mforAllRemoteListeners(
                                taskChangeNotificationController,
                                taskChangeNotificationController.mNotifyTaskbarVisibleChanged,
                                message);
                        break;
                    }
                    break;
            }
            Object obj = message.obj;
            if (obj instanceof SomeArgs) {
                ((SomeArgs) obj).recycle();
            }
        }
    }

    /* renamed from: -$$Nest$mforAllRemoteListeners, reason: not valid java name */
    public static void m1069$$Nest$mforAllRemoteListeners(
            TaskChangeNotificationController taskChangeNotificationController,
            TaskChangeNotificationController$$ExternalSyntheticLambda0
                    taskChangeNotificationController$$ExternalSyntheticLambda0,
            Message message) {
        synchronized (taskChangeNotificationController.mRemoteTaskStackListeners) {
            for (int beginBroadcast =
                            taskChangeNotificationController.mRemoteTaskStackListeners
                                            .beginBroadcast()
                                    - 1;
                    beginBroadcast >= 0;
                    beginBroadcast--) {
                try {
                    taskChangeNotificationController$$ExternalSyntheticLambda0.accept(
                            (ITaskStackListener)
                                    taskChangeNotificationController.mRemoteTaskStackListeners
                                            .getBroadcastItem(beginBroadcast),
                            message);
                } catch (RemoteException unused) {
                }
            }
            taskChangeNotificationController.mRemoteTaskStackListeners.finishBroadcast();
        }
    }

    public TaskChangeNotificationController(
            ActivityTaskSupervisor activityTaskSupervisor, Handler handler) {
        this.mTaskSupervisor = activityTaskSupervisor;
        this.mHandler = new MainHandler(handler.getLooper());
    }

    public final void forAllLocalListeners(
            TaskChangeNotificationController$$ExternalSyntheticLambda0
                    taskChangeNotificationController$$ExternalSyntheticLambda0,
            Message message) {
        synchronized (this.mLocalTaskStackListeners) {
            for (int size = this.mLocalTaskStackListeners.size() - 1; size >= 0; size--) {
                try {
                    taskChangeNotificationController$$ExternalSyntheticLambda0.accept(
                            (ITaskStackListener) this.mLocalTaskStackListeners.get(size), message);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public final void notifyActivityForcedResizable(int i, int i2, String str) {
        MainHandler mainHandler = this.mHandler;
        mainHandler.removeMessages(6);
        Message obtainMessage = mainHandler.obtainMessage(6, i, i2, str);
        forAllLocalListeners(this.mNotifyActivityForcedResizable, obtainMessage);
        obtainMessage.sendToTarget();
    }

    public final void notifyTaskMovedToFront(TaskInfo taskInfo) {
        Message obtainMessage = this.mHandler.obtainMessage(10, taskInfo);
        forAllLocalListeners(this.mNotifyTaskMovedToFront, obtainMessage);
        obtainMessage.sendToTarget();
    }

    public final void notifyTaskStackChanged() {
        ActivityMetricsLogger activityMetricsLogger = this.mTaskSupervisor.mActivityMetricsLogger;
        activityMetricsLogger.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime() / 1000;
        int i = activityMetricsLogger.mWindowState;
        if (i != -1) {
            activityMetricsLogger.mLoggerHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new ActivityMetricsLogger$$ExternalSyntheticLambda4(0),
                            activityMetricsLogger,
                            ActivityMetricsLogger.TRON_WINDOW_STATE_VARZ_STRINGS[i],
                            Integer.valueOf(
                                    (int)
                                            (elapsedRealtime
                                                    - activityMetricsLogger.mLastLogTimeSecs))));
        }
        activityMetricsLogger.mLastLogTimeSecs = elapsedRealtime;
        activityMetricsLogger.mWindowState = -1;
        Task topDisplayFocusedRootTask =
                activityMetricsLogger.mSupervisor.mRootWindowContainer
                        .getTopDisplayFocusedRootTask();
        if (topDisplayFocusedRootTask != null) {
            if (topDisplayFocusedRootTask.isActivityTypeAssistant()) {
                activityMetricsLogger.mWindowState = 3;
            } else {
                int windowingMode = topDisplayFocusedRootTask.getWindowingMode();
                if (windowingMode == 1) {
                    activityMetricsLogger.mWindowState = 0;
                } else if (windowingMode == 5) {
                    activityMetricsLogger.mWindowState = 2;
                } else if (windowingMode == 6) {
                    activityMetricsLogger.mWindowState = 4;
                } else if (windowingMode != 0) {
                    Slog.wtf(
                            "ActivityTaskManager",
                            "Unknown windowing mode for task="
                                    + topDisplayFocusedRootTask
                                    + " windowingMode="
                                    + windowingMode);
                }
            }
        }
        MainHandler mainHandler = this.mHandler;
        mainHandler.removeMessages(2);
        Message obtainMessage = mainHandler.obtainMessage(2);
        forAllLocalListeners(this.mNotifyTaskStackChanged, obtainMessage);
        mainHandler.sendMessageDelayed(obtainMessage, 100L);
    }
}

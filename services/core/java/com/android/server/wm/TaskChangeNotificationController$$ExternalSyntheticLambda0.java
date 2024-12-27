package com.android.server.wm;

import android.app.ActivityManager;
import android.app.ITaskStackListener;
import android.content.ComponentName;
import android.os.Message;
import android.window.TaskSnapshot;

import com.android.internal.os.SomeArgs;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class TaskChangeNotificationController$$ExternalSyntheticLambda0 {
    public final /* synthetic */ int $r8$classId;

    public final void accept(ITaskStackListener iTaskStackListener, Message message) {
        switch (this.$r8$classId) {
            case 0:
                iTaskStackListener.onTaskStackChanged();
                break;
            case 1:
                iTaskStackListener.onRecentTaskListUpdated();
                break;
            case 2:
                iTaskStackListener.onTaskCreated(message.arg1, (ComponentName) message.obj);
                break;
            case 3:
                iTaskStackListener.onRecentTaskListFrozenChanged(message.arg1 != 0);
                break;
            case 4:
                iTaskStackListener.onTaskFocusChanged(message.arg1, message.arg2 != 0);
                break;
            case 5:
                iTaskStackListener.onTaskRequestedOrientationChanged(message.arg1, message.arg2);
                break;
            case 6:
                iTaskStackListener.onActivityRotation(message.arg1);
                break;
            case 7:
                iTaskStackListener.onTaskMovedToBack((ActivityManager.RunningTaskInfo) message.obj);
                break;
            case 8:
                iTaskStackListener.onLockTaskModeChanged(message.arg1);
                break;
            case 9:
                iTaskStackListener.onTaskWindowingModeChanged(message.arg1);
                break;
            case 10:
                Object obj = message.obj;
                iTaskStackListener.onTaskbarIconVisibleChangeRequest(
                        obj != null ? (ComponentName) obj : null, message.arg1 != 0);
                break;
            case 11:
                SomeArgs someArgs = (SomeArgs) message.obj;
                iTaskStackListener.onActivityRestartAttempt(
                        (ActivityManager.RunningTaskInfo) someArgs.arg1,
                        someArgs.argi1 != 0,
                        someArgs.argi2 != 0,
                        someArgs.argi3 != 0);
                break;
            case 12:
                iTaskStackListener.onTaskRemoved(message.arg1);
                break;
            case 13:
                iTaskStackListener.onTaskMovedToFront(
                        (ActivityManager.RunningTaskInfo) message.obj);
                break;
            case 14:
                iTaskStackListener.onTaskDescriptionChanged(
                        (ActivityManager.RunningTaskInfo) message.obj);
                break;
            case 15:
                iTaskStackListener.onBackPressedOnTaskRoot(
                        (ActivityManager.RunningTaskInfo) message.obj);
                break;
            case 16:
                iTaskStackListener.onActivityRequestedOrientationChanged(
                        message.arg1, message.arg2);
                break;
            case 17:
                iTaskStackListener.onTaskRemovalStarted(
                        (ActivityManager.RunningTaskInfo) message.obj);
                break;
            case 18:
                iTaskStackListener.onActivityPinned(
                        (String) message.obj, message.sendingUid, message.arg1, message.arg2);
                break;
            case 19:
                iTaskStackListener.onActivityUnpinned();
                break;
            case 20:
                iTaskStackListener.onActivityForcedResizable(
                        (String) message.obj, message.arg1, message.arg2);
                break;
            case 21:
                Object obj2 = message.obj;
                if (!(obj2 instanceof String)) {
                    iTaskStackListener.onActivityDismissingDockedTask();
                    break;
                } else {
                    iTaskStackListener.onActivityDismissingSplitTask((String) obj2);
                    break;
                }
            case 22:
                iTaskStackListener.onActivityLaunchOnSecondaryDisplayFailed(
                        (ActivityManager.RunningTaskInfo) message.obj, message.arg1);
                break;
            case 23:
                iTaskStackListener.onActivityLaunchOnSecondaryDisplayRerouted(
                        (ActivityManager.RunningTaskInfo) message.obj, message.arg1);
                break;
            case 24:
                iTaskStackListener.onTaskProfileLocked(
                        (ActivityManager.RunningTaskInfo) message.obj, message.arg1);
                break;
            case 25:
                iTaskStackListener.onTaskSnapshotChanged(message.arg1, (TaskSnapshot) message.obj);
                break;
            case 26:
                iTaskStackListener.onTaskSnapshotInvalidated(message.arg1);
                break;
            default:
                iTaskStackListener.onTaskDisplayChanged(message.arg1, message.arg2);
                break;
        }
    }
}

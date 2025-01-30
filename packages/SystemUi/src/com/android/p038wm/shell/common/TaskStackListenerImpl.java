package com.android.p038wm.shell.common;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.TaskStackListener;
import android.content.ComponentName;
import android.os.Handler;
import android.os.Message;
import android.os.Trace;
import android.util.Log;
import android.window.TaskSnapshot;
import com.android.internal.os.SomeArgs;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TaskStackListenerImpl extends TaskStackListener implements Handler.Callback {
    public final IActivityTaskManager mActivityTaskManager;
    public Handler mMainHandler;
    public final List mTaskStackListeners;
    public final List mTmpListeners;

    public TaskStackListenerImpl(Handler handler) {
        this.mTaskStackListeners = new ArrayList();
        this.mTmpListeners = new ArrayList();
        this.mActivityTaskManager = ActivityTaskManager.getService();
        this.mMainHandler = new Handler(handler.getLooper(), this);
    }

    public final void addListener(TaskStackListenerCallback taskStackListenerCallback) {
        boolean isEmpty;
        synchronized (this.mTaskStackListeners) {
            isEmpty = ((ArrayList) this.mTaskStackListeners).isEmpty();
            ((ArrayList) this.mTaskStackListeners).add(taskStackListenerCallback);
        }
        if (isEmpty) {
            try {
                this.mActivityTaskManager.registerTaskStackListener(this);
            } catch (Exception e) {
                Log.w("TaskStackListenerImpl", "Failed to call registerTaskStackListener", e);
            }
        }
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        synchronized (this.mTaskStackListeners) {
            int i = message.what;
            if (i != 100) {
                switch (i) {
                    case 1:
                        Trace.beginSection("onTaskStackChanged");
                        for (int size = ((ArrayList) this.mTaskStackListeners).size() - 1; size >= 0; size--) {
                            ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size)).onTaskStackChanged();
                        }
                        Trace.endSection();
                        break;
                    case 2:
                        Trace.beginSection("onTaskSnapshotChanged");
                        TaskSnapshot taskSnapshot = (TaskSnapshot) message.obj;
                        for (int size2 = ((ArrayList) this.mTaskStackListeners).size() - 1; size2 >= 0; size2--) {
                            ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size2)).getClass();
                        }
                        if (taskSnapshot.getHardwareBuffer() != null) {
                            taskSnapshot.getHardwareBuffer().close();
                        }
                        Trace.endSection();
                        break;
                    case 3:
                        SomeArgs someArgs = (SomeArgs) message.obj;
                        for (int size3 = ((ArrayList) this.mTaskStackListeners).size() - 1; size3 >= 0; size3--) {
                            ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size3)).onActivityPinned((String) someArgs.arg1, someArgs.argi1);
                        }
                        break;
                    case 4:
                        SomeArgs someArgs2 = (SomeArgs) message.obj;
                        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) someArgs2.arg1;
                        int i2 = someArgs2.argi1;
                        boolean z = someArgs2.argi2 != 0;
                        for (int size4 = ((ArrayList) this.mTaskStackListeners).size() - 1; size4 >= 0; size4--) {
                            ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size4)).onActivityRestartAttempt(runningTaskInfo, z);
                        }
                        break;
                    case 5:
                        for (int size5 = ((ArrayList) this.mTaskStackListeners).size() - 1; size5 >= 0; size5--) {
                            ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size5)).onActivityForcedResizable((String) message.obj, message.arg1, message.arg2);
                        }
                        break;
                    case 6:
                        for (int size6 = ((ArrayList) this.mTaskStackListeners).size() - 1; size6 >= 0; size6--) {
                            ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size6)).getClass();
                        }
                        break;
                    case 7:
                        for (int size7 = ((ArrayList) this.mTaskStackListeners).size() - 1; size7 >= 0; size7--) {
                            ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size7)).getClass();
                        }
                        break;
                    case 8:
                        for (int size8 = ((ArrayList) this.mTaskStackListeners).size() - 1; size8 >= 0; size8--) {
                            ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size8)).onActivityUnpinned();
                        }
                        break;
                    case 9:
                        for (int size9 = ((ArrayList) this.mTaskStackListeners).size() - 1; size9 >= 0; size9--) {
                            ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size9)).getClass();
                        }
                        break;
                    case 10:
                        for (int size10 = ((ArrayList) this.mTaskStackListeners).size() - 1; size10 >= 0; size10--) {
                            TaskStackListenerCallback taskStackListenerCallback = (TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size10);
                            taskStackListenerCallback.onTaskCreated();
                        }
                        break;
                    case 11:
                        for (int size11 = ((ArrayList) this.mTaskStackListeners).size() - 1; size11 >= 0; size11--) {
                            ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size11)).getClass();
                        }
                        break;
                    case 12:
                        ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) message.obj;
                        for (int size12 = ((ArrayList) this.mTaskStackListeners).size() - 1; size12 >= 0; size12--) {
                            TaskStackListenerCallback taskStackListenerCallback2 = (TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size12);
                            taskStackListenerCallback2.getClass();
                            taskStackListenerCallback2.onTaskMovedToFront(runningTaskInfo2.taskId);
                        }
                        break;
                    case 13:
                        for (int size13 = ((ArrayList) this.mTaskStackListeners).size() - 1; size13 >= 0; size13--) {
                            ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size13)).getClass();
                        }
                        break;
                    case 14:
                        for (int size14 = ((ArrayList) this.mTaskStackListeners).size() - 1; size14 >= 0; size14--) {
                            ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size14)).getClass();
                        }
                        break;
                    case 15:
                        for (int size15 = ((ArrayList) this.mTaskStackListeners).size() - 1; size15 >= 0; size15--) {
                            TaskStackListenerCallback taskStackListenerCallback3 = (TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size15);
                            taskStackListenerCallback3.getClass();
                        }
                        break;
                    case 16:
                        for (int size16 = ((ArrayList) this.mTaskStackListeners).size() - 1; size16 >= 0; size16--) {
                            ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size16)).getClass();
                        }
                        break;
                    case 17:
                        for (int size17 = ((ArrayList) this.mTaskStackListeners).size() - 1; size17 >= 0; size17--) {
                            ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size17)).onRecentTaskListUpdated();
                        }
                        break;
                    case 18:
                        for (int size18 = ((ArrayList) this.mTaskStackListeners).size() - 1; size18 >= 0; size18--) {
                            ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size18)).getClass();
                        }
                        break;
                    case 19:
                        for (int size19 = ((ArrayList) this.mTaskStackListeners).size() - 1; size19 >= 0; size19--) {
                            ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size19)).getClass();
                        }
                        break;
                    case 20:
                        for (int size20 = ((ArrayList) this.mTaskStackListeners).size() - 1; size20 >= 0; size20--) {
                            ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size20)).getClass();
                        }
                        break;
                }
            } else {
                for (int size21 = ((ArrayList) this.mTaskStackListeners).size() - 1; size21 >= 0; size21--) {
                    ((TaskStackListenerCallback) ((ArrayList) this.mTaskStackListeners).get(size21)).onActivityDismissingSplitTask((String) message.obj);
                }
            }
        }
        Object obj = message.obj;
        if (obj instanceof SomeArgs) {
            ((SomeArgs) obj).recycle();
        }
        return true;
    }

    public final void onActivityDismissingDockedTask() {
        this.mMainHandler.sendEmptyMessage(6);
    }

    public final void onActivityDismissingSplitTask(String str) {
        this.mMainHandler.obtainMessage(100, str).sendToTarget();
    }

    public final void onActivityForcedResizable(String str, int i, int i2) {
        this.mMainHandler.obtainMessage(5, i, i2, str).sendToTarget();
    }

    public final void onActivityLaunchOnSecondaryDisplayFailed(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        this.mMainHandler.obtainMessage(9, i, 0, runningTaskInfo).sendToTarget();
    }

    public final void onActivityLaunchOnSecondaryDisplayRerouted(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        this.mMainHandler.obtainMessage(14, i, 0, runningTaskInfo).sendToTarget();
    }

    public final void onActivityPinned(String str, int i, int i2, int i3) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = str;
        obtain.argi1 = i;
        obtain.argi2 = i2;
        obtain.argi3 = i3;
        this.mMainHandler.removeMessages(3);
        this.mMainHandler.obtainMessage(3, obtain).sendToTarget();
    }

    public final void onActivityRequestedOrientationChanged(int i, int i2) {
        this.mMainHandler.obtainMessage(13, i, i2).sendToTarget();
    }

    public final void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = runningTaskInfo;
        obtain.argi1 = z ? 1 : 0;
        obtain.argi2 = z2 ? 1 : 0;
        obtain.argi3 = z3 ? 1 : 0;
        this.mMainHandler.obtainMessage(4, obtain).sendToTarget();
    }

    public final void onActivityRotation(int i) {
        this.mMainHandler.obtainMessage(20, i, 0).sendToTarget();
    }

    public final void onActivityUnpinned() {
        this.mMainHandler.removeMessages(8);
        this.mMainHandler.sendEmptyMessage(8);
    }

    public final void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.mMainHandler.obtainMessage(15, runningTaskInfo).sendToTarget();
    }

    public final void onRecentTaskListFrozenChanged(boolean z) {
        this.mMainHandler.obtainMessage(18, z ? 1 : 0, 0).sendToTarget();
    }

    public final void onRecentTaskListUpdated() {
        this.mMainHandler.obtainMessage(17).sendToTarget();
    }

    public final void onTaskCreated(int i, ComponentName componentName) {
        this.mMainHandler.obtainMessage(10, i, 0, componentName).sendToTarget();
    }

    public final void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.mMainHandler.obtainMessage(19, runningTaskInfo).sendToTarget();
    }

    public final void onTaskDisplayChanged(int i, int i2) {
        this.mMainHandler.obtainMessage(16, i, i2).sendToTarget();
    }

    public final void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.mMainHandler.obtainMessage(12, runningTaskInfo).sendToTarget();
    }

    public final void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        this.mMainHandler.obtainMessage(7, i, 0, runningTaskInfo).sendToTarget();
    }

    public final void onTaskRemoved(int i) {
        this.mMainHandler.obtainMessage(11, i, 0).sendToTarget();
    }

    public final void onTaskSnapshotChanged(int i, TaskSnapshot taskSnapshot) {
        this.mMainHandler.obtainMessage(2, i, 0, taskSnapshot).sendToTarget();
    }

    public final void onTaskStackChanged() {
        synchronized (this.mTaskStackListeners) {
            ((ArrayList) this.mTmpListeners).addAll(this.mTaskStackListeners);
        }
        for (int size = ((ArrayList) this.mTmpListeners).size() - 1; size >= 0; size--) {
            ((TaskStackListenerCallback) ((ArrayList) this.mTmpListeners).get(size)).getClass();
        }
        ((ArrayList) this.mTmpListeners).clear();
        this.mMainHandler.removeMessages(1);
        this.mMainHandler.sendEmptyMessage(1);
    }

    public void setHandler(Handler handler) {
        this.mMainHandler = handler;
    }

    public TaskStackListenerImpl(IActivityTaskManager iActivityTaskManager) {
        this.mTaskStackListeners = new ArrayList();
        this.mTmpListeners = new ArrayList();
        this.mActivityTaskManager = iActivityTaskManager;
    }
}

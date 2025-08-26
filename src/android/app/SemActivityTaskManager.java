package android.app;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.os.Debug;
import android.os.RemoteException;
import android.util.Log;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public class SemActivityTaskManager {
    public static final int CAMERA_CUTOUT_SETTING_APP_DEFAULT = 0;
    public static final int CAMERA_CUTOUT_SETTING_HIDE = 2;
    public static final int CAMERA_CUTOUT_SETTING_SHOW = 1;
    private static final String TAG = "SemActivityTaskManager";
    private static SemActivityTaskManager sInstance;
    private TaskChangeController mTaskChangeController = new TaskChangeController();
    private final CopyOnWriteArrayList<SemTaskChangeCallback> mTaskChangeCallbacks = new CopyOnWriteArrayList<>();

    private SemActivityTaskManager() {
    }

    public static synchronized SemActivityTaskManager getInstance() {
        if (sInstance == null) {
            sInstance = new SemActivityTaskManager();
        }
        return sInstance;
    }

    private static IActivityTaskManager getTaskService() {
        return ActivityTaskManager.getService();
    }

    public boolean registerTaskChangeCallback(SemTaskChangeCallback semTaskChangeCallback) {
        synchronized (this.mTaskChangeCallbacks) {
            if (this.mTaskChangeCallbacks.contains(semTaskChangeCallback)) {
                Log.w(TAG, "TaskChangeCallback already registered");
                return false;
            }
            try {
                this.mTaskChangeCallbacks.add(semTaskChangeCallback);
                getTaskService().registerTaskStackListener(this.mTaskChangeController);
                return true;
            } catch (RemoteException e) {
                warningException(e);
                return false;
            }
        }
    }

    public boolean unregisterTaskChangeCallback(SemTaskChangeCallback semTaskChangeCallback) {
        synchronized (this.mTaskChangeCallbacks) {
            if (!this.mTaskChangeCallbacks.contains(semTaskChangeCallback)) {
                Log.w(TAG, "TaskChangeCallback no registered");
                return false;
            }
            try {
                this.mTaskChangeCallbacks.remove(semTaskChangeCallback);
                if (this.mTaskChangeCallbacks.isEmpty()) {
                    getTaskService().unregisterTaskStackListener(this.mTaskChangeController);
                    return true;
                }
            } catch (RemoteException e) {
                warningException(e);
            }
            return false;
        }
    }

    public int getCameraCutoutSetting(int i, String str) {
        try {
            return getTaskService().getCutoutPolicy(i, str);
        } catch (RemoteException e) {
            warningException(e);
            return 0;
        }
    }

    private static void warningException(Exception exc) {
        Log.w(TAG, "warningException() : caller=" + Debug.getCaller() + exc.getMessage());
    }

    private class TaskChangeController extends TaskStackListener {
        private TaskChangeController() {
        }

        @Override // android.app.TaskStackListener, android.app.ITaskStackListener
        public void onTaskCreated(int i, ComponentName componentName) {
            if (SemActivityTaskManager.this.mTaskChangeCallbacks.isEmpty()) {
                return;
            }
            Iterator it = SemActivityTaskManager.this.mTaskChangeCallbacks.iterator();
            while (it.hasNext()) {
                ((SemTaskChangeCallback) it.next()).onTaskCreated(i, componentName);
            }
        }

        @Override // android.app.TaskStackListener, android.app.ITaskStackListener
        public void onTaskRemoved(int i) {
            if (SemActivityTaskManager.this.mTaskChangeCallbacks.isEmpty()) {
                return;
            }
            Iterator it = SemActivityTaskManager.this.mTaskChangeCallbacks.iterator();
            while (it.hasNext()) {
                ((SemTaskChangeCallback) it.next()).onTaskRemoved(i);
            }
        }

        @Override // android.app.TaskStackListener, android.app.ITaskStackListener
        public void onTaskDisplayChanged(int i, int i2) {
            if (SemActivityTaskManager.this.mTaskChangeCallbacks.isEmpty()) {
                return;
            }
            Iterator it = SemActivityTaskManager.this.mTaskChangeCallbacks.iterator();
            while (it.hasNext()) {
                ((SemTaskChangeCallback) it.next()).onTaskDisplayChanged(i, i2);
            }
        }

        @Override // android.app.TaskStackListener, android.app.ITaskStackListener
        public void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
            if (SemActivityTaskManager.this.mTaskChangeCallbacks.isEmpty()) {
                return;
            }
            Iterator it = SemActivityTaskManager.this.mTaskChangeCallbacks.iterator();
            while (it.hasNext()) {
                ((SemTaskChangeCallback) it.next()).onTaskMovedToFront(runningTaskInfo);
            }
        }

        @Override // android.app.TaskStackListener, android.app.ITaskStackListener
        public void onTaskMovedToBack(ActivityManager.RunningTaskInfo runningTaskInfo) {
            if (SemActivityTaskManager.this.mTaskChangeCallbacks.isEmpty()) {
                return;
            }
            Iterator it = SemActivityTaskManager.this.mTaskChangeCallbacks.iterator();
            while (it.hasNext()) {
                ((SemTaskChangeCallback) it.next()).onTaskMovedToBack(runningTaskInfo);
            }
        }

        @Override // android.app.TaskStackListener, android.app.ITaskStackListener
        public void onActivityRequestedOrientationChanged(int i, int i2) {
            if (SemActivityTaskManager.this.mTaskChangeCallbacks.isEmpty()) {
                return;
            }
            Iterator it = SemActivityTaskManager.this.mTaskChangeCallbacks.iterator();
            while (it.hasNext()) {
                ((SemTaskChangeCallback) it.next()).onActivityRequestedOrientationChanged(i, i2);
            }
        }

        @Override // android.app.TaskStackListener, android.app.ITaskStackListener
        public void onTaskRequestedOrientationChanged(int i, int i2) {
            if (SemActivityTaskManager.this.mTaskChangeCallbacks.isEmpty()) {
                return;
            }
            Iterator it = SemActivityTaskManager.this.mTaskChangeCallbacks.iterator();
            while (it.hasNext()) {
                ((SemTaskChangeCallback) it.next()).onTaskRequestedOrientationChanged(i, i2);
            }
        }
    }
}

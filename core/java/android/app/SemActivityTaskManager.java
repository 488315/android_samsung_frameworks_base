package android.app;

import android.content.ComponentName;
import android.os.Debug;
import android.os.RemoteException;
import android.util.Log;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class SemActivityTaskManager {
    public static final int CAMERA_CUTOUT_SETTING_APP_DEFAULT = 0;
    public static final int CAMERA_CUTOUT_SETTING_HIDE = 2;
    public static final int CAMERA_CUTOUT_SETTING_SHOW = 1;
    private static final String TAG = "SemActivityTaskManager";
    private static SemActivityTaskManager sInstance;
    private TaskChangeController mTaskChangeController = new TaskChangeController();
    private final CopyOnWriteArrayList<SemTaskChangeCallback> mTaskChangeCallbacks =
            new CopyOnWriteArrayList<>();

    private SemActivityTaskManager() {}

    public static synchronized SemActivityTaskManager getInstance() {
        SemActivityTaskManager semActivityTaskManager;
        synchronized (SemActivityTaskManager.class) {
            if (sInstance == null) {
                sInstance = new SemActivityTaskManager();
            }
            semActivityTaskManager = sInstance;
        }
        return semActivityTaskManager;
    }

    private static IActivityTaskManager getTaskService() {
        return ActivityTaskManager.getService();
    }

    public boolean registerTaskChangeCallback(SemTaskChangeCallback taskChangeCallback) {
        synchronized (this.mTaskChangeCallbacks) {
            if (this.mTaskChangeCallbacks.contains(taskChangeCallback)) {
                Log.w(TAG, "TaskChangeCallback already registered");
                return false;
            }
            try {
                this.mTaskChangeCallbacks.add(taskChangeCallback);
                getTaskService().registerTaskStackListener(this.mTaskChangeController);
                return true;
            } catch (RemoteException e) {
                warningException(e);
                return false;
            }
        }
    }

    public boolean unregisterTaskChangeCallback(SemTaskChangeCallback taskChangeCallback) {
        synchronized (this.mTaskChangeCallbacks) {
            if (!this.mTaskChangeCallbacks.contains(taskChangeCallback)) {
                Log.w(TAG, "TaskChangeCallback no registered");
                return false;
            }
            try {
                this.mTaskChangeCallbacks.remove(taskChangeCallback);
            } catch (RemoteException e) {
                warningException(e);
            }
            if (!this.mTaskChangeCallbacks.isEmpty()) {
                return false;
            }
            getTaskService().unregisterTaskStackListener(this.mTaskChangeController);
            return true;
        }
    }

    public int getCameraCutoutSetting(int userId, String packageName) {
        try {
            return getTaskService().getCutoutPolicy(userId, packageName);
        } catch (RemoteException e) {
            warningException(e);
            return 0;
        }
    }

    private static void warningException(Exception e) {
        Log.w(TAG, "warningException() : caller=" + Debug.getCaller() + e.getMessage());
    }

    private class TaskChangeController extends TaskStackListener {
        private TaskChangeController() {}

        @Override // android.app.TaskStackListener, android.app.ITaskStackListener
        public void onTaskCreated(int taskId, ComponentName componentName) {
            if (!SemActivityTaskManager.this.mTaskChangeCallbacks.isEmpty()) {
                Iterator it = SemActivityTaskManager.this.mTaskChangeCallbacks.iterator();
                while (it.hasNext()) {
                    SemTaskChangeCallback i = (SemTaskChangeCallback) it.next();
                    i.onTaskCreated(taskId, componentName);
                }
            }
        }

        @Override // android.app.TaskStackListener, android.app.ITaskStackListener
        public void onTaskRemoved(int taskId) {
            if (!SemActivityTaskManager.this.mTaskChangeCallbacks.isEmpty()) {
                Iterator it = SemActivityTaskManager.this.mTaskChangeCallbacks.iterator();
                while (it.hasNext()) {
                    SemTaskChangeCallback i = (SemTaskChangeCallback) it.next();
                    i.onTaskRemoved(taskId);
                }
            }
        }

        @Override // android.app.TaskStackListener, android.app.ITaskStackListener
        public void onTaskDisplayChanged(int taskId, int newDisplayId) {
            if (!SemActivityTaskManager.this.mTaskChangeCallbacks.isEmpty()) {
                Iterator it = SemActivityTaskManager.this.mTaskChangeCallbacks.iterator();
                while (it.hasNext()) {
                    SemTaskChangeCallback i = (SemTaskChangeCallback) it.next();
                    i.onTaskDisplayChanged(taskId, newDisplayId);
                }
            }
        }

        @Override // android.app.TaskStackListener, android.app.ITaskStackListener
        public void onTaskMovedToFront(ActivityManager.RunningTaskInfo taskInfo) {
            if (!SemActivityTaskManager.this.mTaskChangeCallbacks.isEmpty()) {
                Iterator it = SemActivityTaskManager.this.mTaskChangeCallbacks.iterator();
                while (it.hasNext()) {
                    SemTaskChangeCallback i = (SemTaskChangeCallback) it.next();
                    i.onTaskMovedToFront(taskInfo);
                }
            }
        }

        @Override // android.app.TaskStackListener, android.app.ITaskStackListener
        public void onTaskMovedToBack(ActivityManager.RunningTaskInfo taskInfo) {
            if (!SemActivityTaskManager.this.mTaskChangeCallbacks.isEmpty()) {
                Iterator it = SemActivityTaskManager.this.mTaskChangeCallbacks.iterator();
                while (it.hasNext()) {
                    SemTaskChangeCallback i = (SemTaskChangeCallback) it.next();
                    i.onTaskMovedToBack(taskInfo);
                }
            }
        }

        @Override // android.app.TaskStackListener, android.app.ITaskStackListener
        public void onActivityRequestedOrientationChanged(int taskId, int requestedOrientation) {
            if (!SemActivityTaskManager.this.mTaskChangeCallbacks.isEmpty()) {
                Iterator it = SemActivityTaskManager.this.mTaskChangeCallbacks.iterator();
                while (it.hasNext()) {
                    SemTaskChangeCallback i = (SemTaskChangeCallback) it.next();
                    i.onActivityRequestedOrientationChanged(taskId, requestedOrientation);
                }
            }
        }

        @Override // android.app.TaskStackListener, android.app.ITaskStackListener
        public void onTaskRequestedOrientationChanged(int taskId, int requestedOrientation) {
            if (!SemActivityTaskManager.this.mTaskChangeCallbacks.isEmpty()) {
                Iterator it = SemActivityTaskManager.this.mTaskChangeCallbacks.iterator();
                while (it.hasNext()) {
                    SemTaskChangeCallback i = (SemTaskChangeCallback) it.next();
                    i.onTaskRequestedOrientationChanged(taskId, requestedOrientation);
                }
            }
        }
    }
}

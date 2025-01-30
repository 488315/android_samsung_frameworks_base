package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.Activity;
import android.app.ActivityClient;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.view.IRecentsAnimationController;
import android.view.IRecentsAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.window.TaskSnapshot;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import com.android.internal.app.IVoiceInteractionManagerService;
import com.samsung.android.desktopsystemui.sharedlib.recents.model.Task;
import com.samsung.android.desktopsystemui.sharedlib.recents.model.ThumbnailData;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ActivityManagerWrapper {
    public static final String CLOSE_SYSTEM_WINDOWS_REASON_HOME_KEY = "homekey";
    public static final String CLOSE_SYSTEM_WINDOWS_REASON_RECENTS = "recentapps";
    private static final String INVOCATION_TIME_MS_KEY = "invocation_time_ms";
    private static final int NUM_RECENT_ACTIVITIES_REQUEST = 3;
    private static final String TAG = "[DS]ActivityManagerWrapper";
    private static final ActivityManagerWrapper sInstance = new ActivityManagerWrapper();

    private ActivityManagerWrapper() {
    }

    public static ActivityManagerWrapper getInstance() {
        return sInstance;
    }

    public static boolean isHomeTask(ActivityManager.RunningTaskInfo runningTaskInfo) {
        return runningTaskInfo.configuration.windowConfiguration.getActivityType() == 2;
    }

    public void cancelRecentsAnimation(boolean z) {
        try {
            ActivityTaskManager.getService().cancelRecentsAnimation(z);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to cancel recents animation", e);
        }
    }

    public boolean clearLongLiveTask(int i) {
        try {
            return ActivityManager.getService().clearLongLiveTask(i);
        } catch (RemoteException e) {
            Log.w("ActivityManagerWrapper", e.toString());
            return false;
        }
    }

    public void closeSystemWindows(String str) {
        try {
            ActivityManager.getService().closeSystemDialogs(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to close system windows", e);
        }
    }

    public int getCurrentUserId() {
        try {
            UserInfo currentUser = ActivityManager.getService().getCurrentUser();
            if (currentUser != null) {
                return currentUser.id;
            }
            return 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getLongLiveProcesses() {
        try {
            return ActivityManager.getService().getLongLiveProcesses();
        } catch (RemoteException e) {
            Log.w("ActivityManagerWrapper", e.toString());
            return null;
        }
    }

    public List getLongLiveTaskIdsForUser(int i) {
        try {
            return ActivityManager.getService().getLongLiveTaskIdsForUser(i);
        } catch (RemoteException e) {
            Log.w("ActivityManagerWrapper", e.toString());
            return null;
        }
    }

    public int getMaxLongLiveApps() {
        try {
            return ActivityManager.getService().getMaxLongLiveApps();
        } catch (RemoteException e) {
            Log.w("ActivityManagerWrapper", e.toString());
            return 0;
        }
    }

    public List<ActivityManager.RecentTaskInfo> getRecentPairTasks(int i, int i2) {
        return new ArrayList();
    }

    public List<ActivityManager.RecentTaskInfo> getRecentTasks(int i, int i2) {
        return ActivityTaskManager.getInstance().getRecentTasks(i, 2, i2);
    }

    public List<Bundle> getRecentTasksforTaskbar(int i) {
        try {
            List<ActivityManager.RunningTaskInfo> tasks = ActivityTaskManager.getService().getTasks(Integer.MAX_VALUE, false, false, i);
            ArrayList arrayList = new ArrayList();
            for (ActivityManager.RunningTaskInfo runningTaskInfo : tasks) {
                if (runningTaskInfo.configuration.windowConfiguration.getActivityType() == 1 && runningTaskInfo.displayId == i && runningTaskInfo.numActivities > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("taskId", runningTaskInfo.id);
                    bundle.putInt("userId", runningTaskInfo.userId);
                    bundle.putInt("taskType", runningTaskInfo.topActivityType);
                    bundle.putParcelable("lastTaskDescription", runningTaskInfo.taskDescription);
                    bundle.putBoolean("resizeable", runningTaskInfo.resizeMode != 0);
                    bundle.putInt("displayId", runningTaskInfo.displayId);
                    bundle.putString("componentName", runningTaskInfo.baseActivity.flattenToShortString());
                    bundle.putLong("lastGainFocusTime", runningTaskInfo.lastGainFocusTime);
                    if (!arrayList.contains(bundle)) {
                        arrayList.add(bundle);
                    }
                }
            }
            return arrayList;
        } catch (RemoteException e) {
            Log.e(TAG, "Failed getRecentTasksforTaskbar()", e);
            return new ArrayList();
        }
    }

    public ActivityManager.RunningTaskInfo getRunningTask() {
        return getRunningTask(false);
    }

    public ActivityManager.RunningTaskInfo getRunningTaskIncludingPairTask(boolean z) {
        return null;
    }

    public ActivityManager.RunningTaskInfo[] getRunningTasks(boolean z) {
        List tasks = ActivityTaskManager.getInstance().getTasks(3, z);
        return (ActivityManager.RunningTaskInfo[]) tasks.toArray(new ActivityManager.RunningTaskInfo[tasks.size()]);
    }

    public Bundle getTaskInfoFromTaskIdforTaskbar(int i, int i2) {
        Bundle bundle;
        RemoteException e;
        try {
            boolean z = false;
            List tasks = ActivityTaskManager.getService().getTasks(Integer.MAX_VALUE, false, false, i2);
            if (tasks == null || tasks.isEmpty()) {
                return null;
            }
            for (int i3 = 0; i3 < tasks.size(); i3++) {
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) tasks.get(i3);
                if (i == runningTaskInfo.taskId) {
                    bundle = new Bundle();
                    try {
                        bundle.putInt("taskId", runningTaskInfo.id);
                        bundle.putInt("userId", runningTaskInfo.userId);
                        bundle.putInt("taskType", runningTaskInfo.topActivityType);
                        bundle.putParcelable("lastTaskDescription", runningTaskInfo.taskDescription);
                        if (runningTaskInfo.resizeMode != 0) {
                            z = true;
                        }
                        bundle.putBoolean("resizeable", z);
                        bundle.putInt("displayId", runningTaskInfo.displayId);
                        bundle.putString("componentName", runningTaskInfo.baseIntent.getComponent().flattenToShortString());
                        bundle.putLong("lastGainFocusTime", runningTaskInfo.lastGainFocusTime);
                        return bundle;
                    } catch (RemoteException e2) {
                        e = e2;
                        Log.e(TAG, "Failed getTaskInfoFromTaskIdforTaskbar()" + e.toString());
                        return bundle;
                    }
                }
            }
            return null;
        } catch (RemoteException e3) {
            bundle = null;
            e = e3;
        }
    }

    public ThumbnailData getTaskThumbnail(int i, boolean z, boolean z2) {
        TaskSnapshot taskSnapshot;
        try {
            taskSnapshot = ActivityTaskManager.getService().getTaskSnapshot(i, z, z2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to retrieve task snapshot", e);
            taskSnapshot = null;
        }
        return taskSnapshot != null ? new ThumbnailData(taskSnapshot) : new ThumbnailData();
    }

    public String getTopTaskClassName() {
        String className;
        ActivityManager.RunningTaskInfo runningTask = getRunningTask();
        String str = null;
        if (runningTask != null) {
            ComponentName componentName = runningTask.origActivity;
            if (componentName != null) {
                className = componentName.getClassName();
            } else {
                ComponentName componentName2 = runningTask.topActivity;
                if (componentName2 != null) {
                    className = componentName2.getClassName();
                }
                AbstractC0000x2c234b15.m3m("getTopTaskClassNameAsTaskId, className=", str, TAG);
            }
            str = className;
            AbstractC0000x2c234b15.m3m("getTopTaskClassNameAsTaskId, className=", str, TAG);
        }
        return str;
    }

    public void invalidateHomeTaskSnapshot(Activity activity) {
        try {
            ActivityClient.getInstance().invalidateHomeTaskSnapshot(activity.getActivityToken());
        } catch (Throwable th) {
            Log.w(TAG, "Failed to invalidate home snapshot", th);
        }
    }

    public boolean isLockTaskKioskModeActive() {
        try {
            return ActivityTaskManager.getService().getLockTaskModeState() == 1;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean isLockToAppActive() {
        try {
            return ActivityTaskManager.getService().getLockTaskModeState() != 0;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean isScreenPinningActive() {
        try {
            return ActivityTaskManager.getService().getLockTaskModeState() == 2;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean isScreenPinningEnabled() {
        return Settings.System.getInt(AppGlobals.getInitialApplication().getContentResolver(), "lock_to_app_enabled", 0) != 0;
    }

    public boolean isTopTaskAsTaskId(int i) {
        if (i <= 0) {
            IconCompat$$ExternalSyntheticOutline0.m30m("isTopTaskAsTaskId, error. taskId=", i, TAG);
            return false;
        }
        ActivityManager.RunningTaskInfo runningTask = getRunningTask();
        if (runningTask == null) {
            return false;
        }
        AbstractC0000x2c234b15.m3m("isTopTaskAsTaskId, className=", runningTask.topActivity.getClassName(), TAG);
        return runningTask.id == i;
    }

    public void registerTaskStackListener(TaskStackChangeListener taskStackChangeListener) {
        TaskStackChangeListeners.getInstance().registerTaskStackListener(taskStackChangeListener);
    }

    public void removeAllRecentTasks() {
        try {
            ActivityTaskManager.getService().removeAllVisibleRecentTasks();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to remove all tasks", e);
        }
    }

    public void removeTask(int i) {
        try {
            ActivityTaskManager.getService().removeTask(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to remove task=" + i, e);
        }
    }

    public boolean setLongLiveTask(int i) {
        try {
            return ActivityManager.getService().setLongLiveTask(i);
        } catch (RemoteException e) {
            Log.w("ActivityManagerWrapper", e.toString());
            return false;
        }
    }

    public boolean showVoiceSession(IBinder iBinder, Bundle bundle, int i) {
        if (IVoiceInteractionManagerService.Stub.asInterface(ServiceManager.getService("voiceinteraction")) == null) {
            return false;
        }
        bundle.putLong(INVOCATION_TIME_MS_KEY, SystemClock.elapsedRealtime());
        return false;
    }

    public boolean startActivityFromRecents(Task.TaskKey taskKey, ActivityOptions activityOptions) {
        ActivityOptionsCompat.addTaskInfo(activityOptions, taskKey);
        return startActivityFromRecents(taskKey.f469id, activityOptions);
    }

    public void startActivityFromRecentsAsync(Task.TaskKey taskKey, ActivityOptions activityOptions, Consumer<Boolean> consumer, Handler handler) {
        startActivityFromRecentsAsync(taskKey, activityOptions, 0, 0, consumer, handler);
    }

    public void startRecentsActivity(Intent intent, long j, RecentsAnimationListener recentsAnimationListener, final Consumer<Boolean> consumer, Handler handler) {
        final boolean startRecentsActivity = startRecentsActivity(intent, j, recentsAnimationListener);
        if (consumer != null) {
            handler.post(new Runnable() { // from class: com.samsung.android.desktopsystemui.sharedlib.system.ActivityManagerWrapper.1
                @Override // java.lang.Runnable
                public void run() {
                    consumer.accept(Boolean.valueOf(startRecentsActivity));
                }
            });
        }
    }

    public boolean supportsFreeformMultiWindow(Context context) {
        boolean z = Settings.Global.getInt(context.getContentResolver(), "enable_freeform_support", 0) != 0;
        if (ActivityTaskManager.supportsMultiWindow(context)) {
            return context.getPackageManager().hasSystemFeature("android.software.freeform_window_management") || z;
        }
        return false;
    }

    public void unregisterTaskStackListener(TaskStackChangeListener taskStackChangeListener) {
        TaskStackChangeListeners.getInstance().unregisterTaskStackListener(taskStackChangeListener);
    }

    public ActivityManager.RunningTaskInfo getRunningTask(boolean z) {
        List tasks = ActivityTaskManager.getInstance().getTasks(1, z);
        if (tasks.isEmpty()) {
            return null;
        }
        return (ActivityManager.RunningTaskInfo) tasks.get(0);
    }

    public void startActivityFromRecentsAsync(Task.TaskKey taskKey, ActivityOptions activityOptions, int i, int i2, final Consumer<Boolean> consumer, Handler handler) {
        final boolean z;
        if (i != 0 || i2 != 0) {
            if (activityOptions == null) {
                activityOptions = ActivityOptions.makeBasic();
            }
            activityOptions.setLaunchWindowingMode(i);
            activityOptions.setLaunchActivityType(i2);
        }
        try {
            z = startActivityFromRecents(taskKey.f469id, activityOptions);
        } catch (Exception unused) {
            z = false;
        }
        if (consumer != null) {
            handler.post(new Runnable() { // from class: com.samsung.android.desktopsystemui.sharedlib.system.ActivityManagerWrapper.3
                @Override // java.lang.Runnable
                public void run() {
                    consumer.accept(Boolean.valueOf(z));
                }
            });
        }
    }

    public boolean startActivityFromRecents(int i, ActivityOptions activityOptions) {
        Bundle bundle;
        if (activityOptions == null) {
            bundle = null;
        } else {
            try {
                bundle = activityOptions.toBundle();
            } catch (Exception unused) {
                return false;
            }
        }
        ActivityTaskManager.getService().startActivityFromRecents(i, bundle);
        return true;
    }

    public boolean startRecentsActivity(Intent intent, long j, final RecentsAnimationListener recentsAnimationListener) {
        IRecentsAnimationRunner iRecentsAnimationRunner;
        if (recentsAnimationListener != null) {
            try {
                iRecentsAnimationRunner = new IRecentsAnimationRunner.Stub() { // from class: com.samsung.android.desktopsystemui.sharedlib.system.ActivityManagerWrapper.2
                    public void onAnimationCanceled(int[] iArr, TaskSnapshot[] taskSnapshotArr) {
                        recentsAnimationListener.onAnimationCanceled(ThumbnailData.wrap(iArr, taskSnapshotArr));
                    }

                    public void onAnimationStart(IRecentsAnimationController iRecentsAnimationController, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, Rect rect, Rect rect2) {
                        recentsAnimationListener.onAnimationStart(new RecentsAnimationControllerCompat(iRecentsAnimationController), RemoteAnimationTargetCompat.wrap(remoteAnimationTargetArr), RemoteAnimationTargetCompat.wrap(remoteAnimationTargetArr2), rect, rect2);
                    }

                    public void onTasksAppeared(RemoteAnimationTarget[] remoteAnimationTargetArr) {
                        RemoteAnimationTargetCompat[] remoteAnimationTargetCompatArr = new RemoteAnimationTargetCompat[remoteAnimationTargetArr.length];
                        for (int i = 0; i < remoteAnimationTargetArr.length; i++) {
                            remoteAnimationTargetCompatArr[i] = new RemoteAnimationTargetCompat(remoteAnimationTargetArr[i]);
                        }
                        recentsAnimationListener.onTasksAppeared(remoteAnimationTargetCompatArr);
                    }
                };
            } catch (Exception unused) {
                return false;
            }
        } else {
            iRecentsAnimationRunner = null;
        }
        ActivityTaskManager.getService().startRecentsActivity(intent, j, iRecentsAnimationRunner);
        return true;
    }
}

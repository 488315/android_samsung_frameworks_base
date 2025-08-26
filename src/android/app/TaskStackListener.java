package android.app;

import android.app.ActivityManager;
import android.app.ITaskStackListener;
import android.content.ComponentName;
import android.os.RemoteException;
import android.window.TaskSnapshot;

/* loaded from: classes.dex */
public abstract class TaskStackListener extends ITaskStackListener.Stub {
    private boolean mIsRemote = true;

    @Override // android.app.ITaskStackListener
    public void onActivityDismissingDockedTask() throws RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onActivityDismissingSplitTask(String str) {
    }

    @Override // android.app.ITaskStackListener
    public void onActivityForcedResizable(String str, int i, int i2) throws RemoteException {
    }

    @Deprecated
    public void onActivityLaunchOnSecondaryDisplayFailed() throws RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onActivityLaunchOnSecondaryDisplayRerouted(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onActivityPinned(String str, int i, int i2, int i3) throws RemoteException {
    }

    public void onActivityRequestedOrientationChanged(int i, int i2) throws RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) throws RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onActivityRotation(int i) {
    }

    @Override // android.app.ITaskStackListener
    public void onActivityUnpinned() throws RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onLockTaskModeChanged(int i) {
    }

    @Override // android.app.ITaskStackListener
    public void onRecentTaskListFrozenChanged(boolean z) {
    }

    @Override // android.app.ITaskStackListener
    public void onRecentTaskListUpdated() throws RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onRecentTaskRemoved(int i) {
    }

    @Override // android.app.ITaskStackListener
    public void onRecentTaskRemovedForAddTask(int i) {
    }

    public void onTaskCreated(int i, ComponentName componentName) throws RemoteException {
    }

    @Deprecated
    public void onTaskDescriptionChanged(int i, ActivityManager.TaskDescription taskDescription) throws RemoteException {
    }

    public void onTaskDisplayChanged(int i, int i2) throws RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onTaskFocusChanged(int i, boolean z) {
    }

    public void onTaskMovedToBack(ActivityManager.RunningTaskInfo runningTaskInfo) {
    }

    @Deprecated
    public void onTaskMovedToFront(int i) throws RemoteException {
    }

    @Deprecated
    public void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
    }

    @Deprecated
    public void onTaskRemovalStarted(int i) throws RemoteException {
    }

    public void onTaskRemoved(int i) throws RemoteException {
    }

    public void onTaskRequestedOrientationChanged(int i, int i2) {
    }

    @Override // android.app.ITaskStackListener
    public void onTaskSnapshotInvalidated(int i) {
    }

    @Override // android.app.ITaskStackListener
    public void onTaskStackChanged() throws RemoteException {
    }

    @Override // android.app.ITaskStackListener
    public void onTaskWindowingModeChanged(int i) {
    }

    @Override // android.app.ITaskStackListener
    public void onTaskbarIconVisibleChangeRequest(ComponentName componentName, boolean z) {
    }

    public void setIsLocal() {
        this.mIsRemote = false;
    }

    @Override // android.app.ITaskStackListener
    public void onActivityLaunchOnSecondaryDisplayFailed(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException {
        onActivityLaunchOnSecondaryDisplayFailed();
    }

    public void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        onTaskMovedToFront(runningTaskInfo.taskId);
    }

    @Override // android.app.ITaskStackListener
    public void onTaskRemovalStarted(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        onTaskRemovalStarted(runningTaskInfo.taskId);
    }

    @Override // android.app.ITaskStackListener
    public void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        onTaskDescriptionChanged(runningTaskInfo.taskId, runningTaskInfo.taskDescription);
    }

    @Override // android.app.ITaskStackListener
    public void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException {
        onTaskProfileLocked(runningTaskInfo);
    }

    @Override // android.app.ITaskStackListener
    public void onTaskSnapshotChanged(int i, TaskSnapshot taskSnapshot) throws RemoteException {
        if (!this.mIsRemote || taskSnapshot == null || taskSnapshot.getHardwareBuffer() == null) {
            return;
        }
        taskSnapshot.getHardwareBuffer().close();
    }
}

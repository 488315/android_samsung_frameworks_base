package com.android.systemui.mediaprojection.taskswitcher.domain.model;

import android.app.ActivityManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface TaskSwitchState {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NotProjectingTask implements TaskSwitchState {
        public static final NotProjectingTask INSTANCE = new NotProjectingTask();

        private NotProjectingTask() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TaskSwitched implements TaskSwitchState {
        public final ActivityManager.RunningTaskInfo foregroundTask;
        public final ActivityManager.RunningTaskInfo projectedTask;

        public TaskSwitched(ActivityManager.RunningTaskInfo runningTaskInfo, ActivityManager.RunningTaskInfo runningTaskInfo2) {
            this.projectedTask = runningTaskInfo;
            this.foregroundTask = runningTaskInfo2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TaskSwitched)) {
                return false;
            }
            TaskSwitched taskSwitched = (TaskSwitched) obj;
            return Intrinsics.areEqual(this.projectedTask, taskSwitched.projectedTask) && Intrinsics.areEqual(this.foregroundTask, taskSwitched.foregroundTask);
        }

        public final int hashCode() {
            return this.foregroundTask.hashCode() + (this.projectedTask.hashCode() * 31);
        }

        public final String toString() {
            return "TaskSwitched(projectedTask=" + this.projectedTask + ", foregroundTask=" + this.foregroundTask + ")";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TaskUnchanged implements TaskSwitchState {
        public static final TaskUnchanged INSTANCE = new TaskUnchanged();

        private TaskUnchanged() {
        }
    }
}

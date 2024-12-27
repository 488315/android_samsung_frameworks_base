package com.android.systemui.mediaprojection.taskswitcher.ui.model;

import android.app.ActivityManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface TaskSwitcherNotificationUiState {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NotShowing implements TaskSwitcherNotificationUiState {
        public static final NotShowing INSTANCE = new NotShowing();

        private NotShowing() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Showing implements TaskSwitcherNotificationUiState {
        public final ActivityManager.RunningTaskInfo foregroundTask;
        public final ActivityManager.RunningTaskInfo projectedTask;

        public Showing(ActivityManager.RunningTaskInfo runningTaskInfo, ActivityManager.RunningTaskInfo runningTaskInfo2) {
            this.projectedTask = runningTaskInfo;
            this.foregroundTask = runningTaskInfo2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Showing)) {
                return false;
            }
            Showing showing = (Showing) obj;
            return Intrinsics.areEqual(this.projectedTask, showing.projectedTask) && Intrinsics.areEqual(this.foregroundTask, showing.foregroundTask);
        }

        public final int hashCode() {
            return this.foregroundTask.hashCode() + (this.projectedTask.hashCode() * 31);
        }

        public final String toString() {
            return "Showing(projectedTask=" + this.projectedTask + ", foregroundTask=" + this.foregroundTask + ")";
        }
    }
}

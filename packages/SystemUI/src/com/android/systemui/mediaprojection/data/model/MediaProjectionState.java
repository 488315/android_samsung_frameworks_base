package com.android.systemui.mediaprojection.data.model;

import android.app.ActivityManager;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface MediaProjectionState {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NotProjecting implements MediaProjectionState {
        public static final NotProjecting INSTANCE = new NotProjecting();

        private NotProjecting() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NotProjecting);
        }

        public final int hashCode() {
            return -570342309;
        }

        public final String toString() {
            return "NotProjecting";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class Projecting implements MediaProjectionState {
        public final String hostPackage;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class EntireScreen extends Projecting {
            public final String hostPackage;

            public EntireScreen(String str) {
                super(str, null);
                this.hostPackage = str;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof EntireScreen) && Intrinsics.areEqual(this.hostPackage, ((EntireScreen) obj).hostPackage);
            }

            @Override // com.android.systemui.mediaprojection.data.model.MediaProjectionState.Projecting
            public final String getHostPackage() {
                return this.hostPackage;
            }

            public final int hashCode() {
                return this.hostPackage.hashCode();
            }

            public final String toString() {
                return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("EntireScreen(hostPackage="), this.hostPackage, ")");
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class SingleTask extends Projecting {
            public final String hostPackage;
            public final ActivityManager.RunningTaskInfo task;

            public SingleTask(String str, ActivityManager.RunningTaskInfo runningTaskInfo) {
                super(str, null);
                this.hostPackage = str;
                this.task = runningTaskInfo;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof SingleTask)) {
                    return false;
                }
                SingleTask singleTask = (SingleTask) obj;
                return Intrinsics.areEqual(this.hostPackage, singleTask.hostPackage) && Intrinsics.areEqual(this.task, singleTask.task);
            }

            @Override // com.android.systemui.mediaprojection.data.model.MediaProjectionState.Projecting
            public final String getHostPackage() {
                return this.hostPackage;
            }

            public final int hashCode() {
                return this.task.hashCode() + (this.hostPackage.hashCode() * 31);
            }

            public final String toString() {
                return "SingleTask(hostPackage=" + this.hostPackage + ", task=" + this.task + ")";
            }
        }

        public /* synthetic */ Projecting(String str, DefaultConstructorMarker defaultConstructorMarker) {
            this(str);
        }

        public String getHostPackage() {
            return this.hostPackage;
        }

        private Projecting(String str) {
            this.hostPackage = str;
        }
    }
}

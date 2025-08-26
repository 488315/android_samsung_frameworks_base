package com.android.systemui.mediaprojection.data.model;

import android.app.ActivityManager;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public interface MediaProjectionState {

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

    public abstract class Projecting implements MediaProjectionState {
        public final String hostDeviceName;
        public final String hostPackage;

        public final class EntireScreen extends Projecting {
            public final String hostDeviceName;
            public final String hostPackage;

            public /* synthetic */ EntireScreen(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(str, (i & 2) != 0 ? null : str2);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof EntireScreen)) {
                    return false;
                }
                EntireScreen entireScreen = (EntireScreen) obj;
                return Intrinsics.areEqual(this.hostPackage, entireScreen.hostPackage) && Intrinsics.areEqual(this.hostDeviceName, entireScreen.hostDeviceName);
            }

            @Override // com.android.systemui.mediaprojection.data.model.MediaProjectionState.Projecting
            public final String getHostDeviceName() {
                return this.hostDeviceName;
            }

            @Override // com.android.systemui.mediaprojection.data.model.MediaProjectionState.Projecting
            public final String getHostPackage() {
                return this.hostPackage;
            }

            public final int hashCode() {
                int iHashCode = this.hostPackage.hashCode() * 31;
                String str = this.hostDeviceName;
                return iHashCode + (str == null ? 0 : str.hashCode());
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("EntireScreen(hostPackage=");
                sb.append(this.hostPackage);
                sb.append(", hostDeviceName=");
                return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.hostDeviceName, ")");
            }

            public EntireScreen(String str, String str2) {
                super(str, str2, null);
                this.hostPackage = str;
                this.hostDeviceName = str2;
            }
        }

        public final class NoScreen extends Projecting {
            public final String hostDeviceName;
            public final String hostPackage;

            public /* synthetic */ NoScreen(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(str, (i & 2) != 0 ? null : str2);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof NoScreen)) {
                    return false;
                }
                NoScreen noScreen = (NoScreen) obj;
                return Intrinsics.areEqual(this.hostPackage, noScreen.hostPackage) && Intrinsics.areEqual(this.hostDeviceName, noScreen.hostDeviceName);
            }

            @Override // com.android.systemui.mediaprojection.data.model.MediaProjectionState.Projecting
            public final String getHostDeviceName() {
                return this.hostDeviceName;
            }

            @Override // com.android.systemui.mediaprojection.data.model.MediaProjectionState.Projecting
            public final String getHostPackage() {
                return this.hostPackage;
            }

            public final int hashCode() {
                int iHashCode = this.hostPackage.hashCode() * 31;
                String str = this.hostDeviceName;
                return iHashCode + (str == null ? 0 : str.hashCode());
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("NoScreen(hostPackage=");
                sb.append(this.hostPackage);
                sb.append(", hostDeviceName=");
                return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.hostDeviceName, ")");
            }

            public NoScreen(String str, String str2) {
                super(str, str2, null);
                this.hostPackage = str;
                this.hostDeviceName = str2;
            }
        }

        public final class SingleTask extends Projecting {
            public final String hostDeviceName;
            public final String hostPackage;
            public final ActivityManager.RunningTaskInfo task;

            public SingleTask(String str, String str2, ActivityManager.RunningTaskInfo runningTaskInfo) {
                super(str, str2, null);
                this.hostPackage = str;
                this.hostDeviceName = str2;
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
                return Intrinsics.areEqual(this.hostPackage, singleTask.hostPackage) && Intrinsics.areEqual(this.hostDeviceName, singleTask.hostDeviceName) && Intrinsics.areEqual(this.task, singleTask.task);
            }

            @Override // com.android.systemui.mediaprojection.data.model.MediaProjectionState.Projecting
            public final String getHostDeviceName() {
                return this.hostDeviceName;
            }

            @Override // com.android.systemui.mediaprojection.data.model.MediaProjectionState.Projecting
            public final String getHostPackage() {
                return this.hostPackage;
            }

            public final int hashCode() {
                int iHashCode = this.hostPackage.hashCode() * 31;
                String str = this.hostDeviceName;
                return this.task.hashCode() + ((iHashCode + (str == null ? 0 : str.hashCode())) * 31);
            }

            public final String toString() {
                return "SingleTask(hostPackage=" + this.hostPackage + ", hostDeviceName=" + this.hostDeviceName + ", task=" + this.task + ")";
            }
        }

        public /* synthetic */ Projecting(String str, String str2, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2);
        }

        public String getHostDeviceName() {
            return this.hostDeviceName;
        }

        public String getHostPackage() {
            return this.hostPackage;
        }

        private Projecting(String str, String str2) {
            this.hostPackage = str;
            this.hostDeviceName = str2;
        }
    }
}

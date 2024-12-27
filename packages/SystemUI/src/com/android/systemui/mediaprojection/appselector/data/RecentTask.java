package com.android.systemui.mediaprojection.appselector.data;

import android.app.ActivityManager;
import android.content.ComponentName;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.wm.shell.util.SplitBounds;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class RecentTask {
    public final ComponentName baseIntentComponent;
    public final Integer colorBackground;
    public final int displayId;
    public final boolean isForegroundTask;
    public final SplitBounds splitBounds;
    public final int taskId;
    public final ComponentName topActivityComponent;
    public final int userId;
    public final UserType userType;

    public final class UserType {
        public static final /* synthetic */ UserType[] $VALUES;
        public static final UserType CLONED;
        public static final UserType PRIVATE;
        public static final UserType STANDARD;
        public static final UserType WORK;

        static {
            UserType userType = new UserType(SystemUIAnalytics.ST_VALUE_MIC_MODE_STANDARD, 0);
            STANDARD = userType;
            UserType userType2 = new UserType("WORK", 1);
            WORK = userType2;
            UserType userType3 = new UserType("PRIVATE", 2);
            PRIVATE = userType3;
            UserType userType4 = new UserType("CLONED", 3);
            CLONED = userType4;
            UserType[] userTypeArr = {userType, userType2, userType3, userType4};
            $VALUES = userTypeArr;
            EnumEntriesKt.enumEntries(userTypeArr);
        }

        private UserType(String str, int i) {
        }

        public static UserType valueOf(String str) {
            return (UserType) Enum.valueOf(UserType.class, str);
        }

        public static UserType[] values() {
            return (UserType[]) $VALUES.clone();
        }
    }

    public RecentTask(int i, int i2, int i3, ComponentName componentName, ComponentName componentName2, Integer num, boolean z, UserType userType, SplitBounds splitBounds) {
        this.taskId = i;
        this.displayId = i2;
        this.userId = i3;
        this.topActivityComponent = componentName;
        this.baseIntentComponent = componentName2;
        this.colorBackground = num;
        this.isForegroundTask = z;
        this.userType = userType;
        this.splitBounds = splitBounds;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RecentTask)) {
            return false;
        }
        RecentTask recentTask = (RecentTask) obj;
        return this.taskId == recentTask.taskId && this.displayId == recentTask.displayId && this.userId == recentTask.userId && Intrinsics.areEqual(this.topActivityComponent, recentTask.topActivityComponent) && Intrinsics.areEqual(this.baseIntentComponent, recentTask.baseIntentComponent) && Intrinsics.areEqual(this.colorBackground, recentTask.colorBackground) && this.isForegroundTask == recentTask.isForegroundTask && this.userType == recentTask.userType && Intrinsics.areEqual(this.splitBounds, recentTask.splitBounds);
    }

    public final int hashCode() {
        int m = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.userId, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.displayId, Integer.hashCode(this.taskId) * 31, 31), 31);
        ComponentName componentName = this.topActivityComponent;
        int hashCode = (m + (componentName == null ? 0 : componentName.hashCode())) * 31;
        ComponentName componentName2 = this.baseIntentComponent;
        int hashCode2 = (hashCode + (componentName2 == null ? 0 : componentName2.hashCode())) * 31;
        Integer num = this.colorBackground;
        int hashCode3 = (this.userType.hashCode() + TransitionData$$ExternalSyntheticOutline0.m((hashCode2 + (num == null ? 0 : num.hashCode())) * 31, 31, this.isForegroundTask)) * 31;
        SplitBounds splitBounds = this.splitBounds;
        return hashCode3 + (splitBounds != null ? splitBounds.hashCode() : 0);
    }

    public final String toString() {
        return "RecentTask(taskId=" + this.taskId + ", displayId=" + this.displayId + ", userId=" + this.userId + ", topActivityComponent=" + this.topActivityComponent + ", baseIntentComponent=" + this.baseIntentComponent + ", colorBackground=" + this.colorBackground + ", isForegroundTask=" + this.isForegroundTask + ", userType=" + this.userType + ", splitBounds=" + this.splitBounds + ")";
    }

    public /* synthetic */ RecentTask(ActivityManager.RecentTaskInfo recentTaskInfo, boolean z, UserType userType, SplitBounds splitBounds, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(recentTaskInfo, z, userType, (i & 8) != 0 ? null : splitBounds);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public RecentTask(android.app.ActivityManager.RecentTaskInfo r11, boolean r12, com.android.systemui.mediaprojection.appselector.data.RecentTask.UserType r13, com.android.wm.shell.util.SplitBounds r14) {
        /*
            r10 = this;
            int r1 = r11.taskId
            int r2 = r11.displayId
            int r3 = r11.userId
            android.content.ComponentName r4 = r11.topActivity
            android.content.Intent r0 = r11.baseIntent
            r5 = 0
            if (r0 == 0) goto L13
            android.content.ComponentName r0 = r0.getComponent()
            r6 = r0
            goto L14
        L13:
            r6 = r5
        L14:
            android.app.ActivityManager$TaskDescription r11 = r11.taskDescription
            if (r11 == 0) goto L21
            int r11 = r11.getBackgroundColor()
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
            goto L22
        L21:
            r11 = r5
        L22:
            r0 = r10
            r5 = r6
            r6 = r11
            r7 = r12
            r8 = r13
            r9 = r14
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.appselector.data.RecentTask.<init>(android.app.ActivityManager$RecentTaskInfo, boolean, com.android.systemui.mediaprojection.appselector.data.RecentTask$UserType, com.android.wm.shell.util.SplitBounds):void");
    }
}

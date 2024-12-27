package com.android.systemui.statusbar.chips.screenrecord.domain.model;

import android.app.ActivityManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface ScreenRecordChipModel {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DoingNothing implements ScreenRecordChipModel {
        public static final DoingNothing INSTANCE = new DoingNothing();

        private DoingNothing() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof DoingNothing);
        }

        public final int hashCode() {
            return 1662958005;
        }

        public final String toString() {
            return "DoingNothing";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Recording implements ScreenRecordChipModel {
        public final ActivityManager.RunningTaskInfo recordedTask;

        public Recording(ActivityManager.RunningTaskInfo runningTaskInfo) {
            this.recordedTask = runningTaskInfo;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Recording) && Intrinsics.areEqual(this.recordedTask, ((Recording) obj).recordedTask);
        }

        public final int hashCode() {
            ActivityManager.RunningTaskInfo runningTaskInfo = this.recordedTask;
            if (runningTaskInfo == null) {
                return 0;
            }
            return runningTaskInfo.hashCode();
        }

        public final String toString() {
            return "Recording(recordedTask=" + this.recordedTask + ")";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Starting implements ScreenRecordChipModel {
        public final long millisUntilStarted;

        public Starting(long j) {
            this.millisUntilStarted = j;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Starting) && this.millisUntilStarted == ((Starting) obj).millisUntilStarted;
        }

        public final int hashCode() {
            return Long.hashCode(this.millisUntilStarted);
        }

        public final String toString() {
            return "Starting(millisUntilStarted=" + this.millisUntilStarted + ")";
        }
    }
}

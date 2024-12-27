package com.android.systemui.statusbar.chips.screenrecord.domain.model;

import android.app.ActivityManager;
import kotlin.jvm.internal.Intrinsics;

public interface ScreenRecordChipModel {

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

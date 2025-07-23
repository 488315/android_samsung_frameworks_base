package com.android.systemui.statusbar.chips.screenrecord.domain.model;

import android.app.ActivityManager;
import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface ScreenRecordChipModel {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Recording implements ScreenRecordChipModel {
        public final String hostPackage;
        public final ActivityManager.RunningTaskInfo recordedTask;

        public Recording(String str, ActivityManager.RunningTaskInfo runningTaskInfo) {
            this.hostPackage = str;
            this.recordedTask = runningTaskInfo;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Recording)) {
                return false;
            }
            Recording recording = (Recording) obj;
            return Intrinsics.areEqual(this.hostPackage, recording.hostPackage) && Intrinsics.areEqual(this.recordedTask, recording.recordedTask);
        }

        public final int hashCode() {
            String str = this.hostPackage;
            int hashCode = (str == null ? 0 : str.hashCode()) * 31;
            ActivityManager.RunningTaskInfo runningTaskInfo = this.recordedTask;
            return hashCode + (runningTaskInfo != null ? runningTaskInfo.hashCode() : 0);
        }

        public final String toString() {
            return "Recording(hostPackage=" + this.hostPackage + ", recordedTask=" + this.recordedTask + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.millisUntilStarted, ")", new StringBuilder("Starting(millisUntilStarted="));
        }
    }
}

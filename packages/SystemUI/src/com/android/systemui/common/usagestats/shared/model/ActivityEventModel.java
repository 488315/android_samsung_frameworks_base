package com.android.systemui.common.usagestats.shared.model;

import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class ActivityEventModel {
    public final int instanceId;
    public final Lifecycle lifecycle;
    public final String packageName;
    public final long timestamp;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class Lifecycle {
        public static final /* synthetic */ Lifecycle[] $VALUES;
        public static final Lifecycle DESTROYED;
        public static final Lifecycle PAUSED;
        public static final Lifecycle RESUMED;
        public static final Lifecycle STOPPED;
        public static final Lifecycle UNKNOWN;

        static {
            Lifecycle lifecycle = new Lifecycle("UNKNOWN", 0);
            UNKNOWN = lifecycle;
            Lifecycle lifecycle2 = new Lifecycle("RESUMED", 1);
            RESUMED = lifecycle2;
            Lifecycle lifecycle3 = new Lifecycle("PAUSED", 2);
            PAUSED = lifecycle3;
            Lifecycle lifecycle4 = new Lifecycle("STOPPED", 3);
            STOPPED = lifecycle4;
            Lifecycle lifecycle5 = new Lifecycle("DESTROYED", 4);
            DESTROYED = lifecycle5;
            Lifecycle[] lifecycleArr = {lifecycle, lifecycle2, lifecycle3, lifecycle4, lifecycle5};
            $VALUES = lifecycleArr;
            EnumEntriesKt.enumEntries(lifecycleArr);
        }

        private Lifecycle(String str, int i) {
        }

        public static Lifecycle valueOf(String str) {
            return (Lifecycle) Enum.valueOf(Lifecycle.class, str);
        }

        public static Lifecycle[] values() {
            return (Lifecycle[]) $VALUES.clone();
        }
    }

    public ActivityEventModel(int i, String str, Lifecycle lifecycle, long j) {
        this.instanceId = i;
        this.packageName = str;
        this.lifecycle = lifecycle;
        this.timestamp = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActivityEventModel)) {
            return false;
        }
        ActivityEventModel activityEventModel = (ActivityEventModel) obj;
        return this.instanceId == activityEventModel.instanceId && Intrinsics.areEqual(this.packageName, activityEventModel.packageName) && this.lifecycle == activityEventModel.lifecycle && this.timestamp == activityEventModel.timestamp;
    }

    public final int hashCode() {
        return Long.hashCode(this.timestamp) + ((this.lifecycle.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(Integer.hashCode(this.instanceId) * 31, 31, this.packageName)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ActivityEventModel(instanceId=");
        sb.append(this.instanceId);
        sb.append(", packageName=");
        sb.append(this.packageName);
        sb.append(", lifecycle=");
        sb.append(this.lifecycle);
        sb.append(", timestamp=");
        return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.timestamp, ")", sb);
    }
}

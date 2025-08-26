package com.android.systemui.inputdevice.tutorial.data.model;

import java.time.Instant;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class DeviceSchedulerInfo {
    public final Instant firstConnectionTime;
    public final Instant launchedTime;
    public final Instant notifiedTime;

    public DeviceSchedulerInfo() {
        this(null, null, null, 7, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeviceSchedulerInfo)) {
            return false;
        }
        DeviceSchedulerInfo deviceSchedulerInfo = (DeviceSchedulerInfo) obj;
        return Intrinsics.areEqual(this.launchedTime, deviceSchedulerInfo.launchedTime) && Intrinsics.areEqual(this.firstConnectionTime, deviceSchedulerInfo.firstConnectionTime) && Intrinsics.areEqual(this.notifiedTime, deviceSchedulerInfo.notifiedTime);
    }

    public final int hashCode() {
        Instant instant = this.launchedTime;
        int iHashCode = (instant == null ? 0 : instant.hashCode()) * 31;
        Instant instant2 = this.firstConnectionTime;
        int iHashCode2 = (iHashCode + (instant2 == null ? 0 : instant2.hashCode())) * 31;
        Instant instant3 = this.notifiedTime;
        return iHashCode2 + (instant3 != null ? instant3.hashCode() : 0);
    }

    public final String toString() {
        return "DeviceSchedulerInfo(launchedTime=" + this.launchedTime + ", firstConnectionTime=" + this.firstConnectionTime + ", notifiedTime=" + this.notifiedTime + ")";
    }

    public DeviceSchedulerInfo(Instant instant, Instant instant2, Instant instant3) {
        this.launchedTime = instant;
        this.firstConnectionTime = instant2;
        this.notifiedTime = instant3;
    }

    public /* synthetic */ DeviceSchedulerInfo(Instant instant, Instant instant2, Instant instant3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : instant, (i & 2) != 0 ? null : instant2, (i & 4) != 0 ? null : instant3);
    }

    public DeviceSchedulerInfo(Long l, Long l2, Long l3) {
        this(l != null ? Instant.ofEpochSecond(l.longValue()) : null, l2 != null ? Instant.ofEpochSecond(l2.longValue()) : null, l3 != null ? Instant.ofEpochSecond(l3.longValue()) : null);
    }
}

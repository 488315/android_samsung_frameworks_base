package com.android.systemui.education.data.model;

import java.time.Instant;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class EduDeviceConnectionTime {
    public final Instant keyboardFirstConnectionTime;
    public final Instant touchpadFirstConnectionTime;

    public EduDeviceConnectionTime() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EduDeviceConnectionTime)) {
            return false;
        }
        EduDeviceConnectionTime eduDeviceConnectionTime = (EduDeviceConnectionTime) obj;
        return Intrinsics.areEqual(this.keyboardFirstConnectionTime, eduDeviceConnectionTime.keyboardFirstConnectionTime) && Intrinsics.areEqual(this.touchpadFirstConnectionTime, eduDeviceConnectionTime.touchpadFirstConnectionTime);
    }

    public final int hashCode() {
        Instant instant = this.keyboardFirstConnectionTime;
        int hashCode = (instant == null ? 0 : instant.hashCode()) * 31;
        Instant instant2 = this.touchpadFirstConnectionTime;
        return hashCode + (instant2 != null ? instant2.hashCode() : 0);
    }

    public final String toString() {
        return "EduDeviceConnectionTime(keyboardFirstConnectionTime=" + this.keyboardFirstConnectionTime + ", touchpadFirstConnectionTime=" + this.touchpadFirstConnectionTime + ")";
    }

    public EduDeviceConnectionTime(Instant instant, Instant instant2) {
        this.keyboardFirstConnectionTime = instant;
        this.touchpadFirstConnectionTime = instant2;
    }

    public /* synthetic */ EduDeviceConnectionTime(Instant instant, Instant instant2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : instant, (i & 2) != 0 ? null : instant2);
    }
}

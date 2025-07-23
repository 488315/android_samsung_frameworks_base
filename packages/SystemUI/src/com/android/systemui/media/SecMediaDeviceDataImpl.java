package com.android.systemui.media;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecMediaDeviceDataImpl {
    public Integer deviceType;

    public SecMediaDeviceDataImpl(Integer num) {
        this.deviceType = num;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SecMediaDeviceDataImpl) && Intrinsics.areEqual(this.deviceType, ((SecMediaDeviceDataImpl) obj).deviceType);
    }

    public final int hashCode() {
        Integer num = this.deviceType;
        if (num == null) {
            return 0;
        }
        return num.hashCode();
    }

    public final String toString() {
        return "SecMediaDeviceDataImpl(deviceType=" + this.deviceType + ")";
    }
}

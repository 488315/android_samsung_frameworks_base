package com.android.systemui.media;

import kotlin.jvm.internal.Intrinsics;

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

package com.android.systemui.media;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

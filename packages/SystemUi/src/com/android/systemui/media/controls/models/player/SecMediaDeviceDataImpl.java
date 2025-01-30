package com.android.systemui.media.controls.models.player;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

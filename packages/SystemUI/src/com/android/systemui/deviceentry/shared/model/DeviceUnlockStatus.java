package com.android.systemui.deviceentry.shared.model;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DeviceUnlockStatus {
    public final DeviceUnlockSource deviceUnlockSource;
    public final boolean isUnlocked;

    public DeviceUnlockStatus(boolean z, DeviceUnlockSource deviceUnlockSource) {
        this.isUnlocked = z;
        this.deviceUnlockSource = deviceUnlockSource;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeviceUnlockStatus)) {
            return false;
        }
        DeviceUnlockStatus deviceUnlockStatus = (DeviceUnlockStatus) obj;
        return this.isUnlocked == deviceUnlockStatus.isUnlocked && Intrinsics.areEqual(this.deviceUnlockSource, deviceUnlockStatus.deviceUnlockSource);
    }

    public final int hashCode() {
        int hashCode = Boolean.hashCode(this.isUnlocked) * 31;
        DeviceUnlockSource deviceUnlockSource = this.deviceUnlockSource;
        return hashCode + (deviceUnlockSource == null ? 0 : deviceUnlockSource.hashCode());
    }

    public final String toString() {
        return "DeviceUnlockStatus(isUnlocked=" + this.isUnlocked + ", deviceUnlockSource=" + this.deviceUnlockSource + ")";
    }
}

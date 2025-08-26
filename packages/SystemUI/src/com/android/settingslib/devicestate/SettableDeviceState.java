package com.android.settingslib.devicestate;

/* loaded from: classes.dex */
public final class SettableDeviceState {
    public final int deviceState;
    public final boolean isSettable;

    public SettableDeviceState(int i, boolean z) {
        this.deviceState = i;
        this.isSettable = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SettableDeviceState)) {
            return false;
        }
        SettableDeviceState settableDeviceState = (SettableDeviceState) obj;
        return this.deviceState == settableDeviceState.deviceState && this.isSettable == settableDeviceState.isSettable;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isSettable) + (Integer.hashCode(this.deviceState) * 31);
    }

    public final String toString() {
        return "SettableDeviceState(deviceState=" + this.deviceState + ", isSettable=" + this.isSettable + ")";
    }
}

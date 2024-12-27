package com.samsung.android.hardware.secinputdev.device;

/* loaded from: classes.dex */
public class NotDefined extends SemInputDevice {
    public NotDefined() {
        super("NotDefined", 0, 0, "NG");
    }

    @Override // com.samsung.android.hardware.secinputdev.device.SemInputDevice
    public String toString() {
        return this.name + "(" + this.devid + ")";
    }
}

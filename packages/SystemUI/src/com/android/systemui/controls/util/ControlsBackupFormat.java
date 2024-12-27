package com.android.systemui.controls.util;

import kotlin.jvm.internal.Intrinsics;

public final class ControlsBackupFormat {
    public final ControlsBackupControl controls;
    public final ControlsBackupSetting setting;

    public ControlsBackupFormat(ControlsBackupSetting controlsBackupSetting, ControlsBackupControl controlsBackupControl) {
        this.setting = controlsBackupSetting;
        this.controls = controlsBackupControl;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ControlsBackupFormat)) {
            return false;
        }
        ControlsBackupFormat controlsBackupFormat = (ControlsBackupFormat) obj;
        return Intrinsics.areEqual(this.setting, controlsBackupFormat.setting) && Intrinsics.areEqual(this.controls, controlsBackupFormat.controls);
    }

    public final int hashCode() {
        return this.controls.structures.hashCode() + (this.setting.hashCode() * 31);
    }

    public final String toString() {
        return "ControlsBackupFormat(setting=" + this.setting + ", controls=" + this.controls + ")";
    }
}

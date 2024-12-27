package com.android.systemui.controls.util;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

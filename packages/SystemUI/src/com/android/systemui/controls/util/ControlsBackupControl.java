package com.android.systemui.controls.util;

import com.android.systemui.controls.controller.StructureInfo;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

public final class ControlsBackupControl {
    public final List structures;

    public ControlsBackupControl(List<StructureInfo> list) {
        this.structures = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ControlsBackupControl) && Intrinsics.areEqual(this.structures, ((ControlsBackupControl) obj).structures);
    }

    public final int hashCode() {
        return this.structures.hashCode();
    }

    public final String toString() {
        return "ControlsBackupControl(structures=" + this.structures + ")";
    }
}

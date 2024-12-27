package com.android.systemui.controls.util;

import com.android.systemui.controls.controller.StructureInfo;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

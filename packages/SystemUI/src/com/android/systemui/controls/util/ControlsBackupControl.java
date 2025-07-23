package com.android.systemui.controls.util;

import com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0;
import com.android.systemui.controls.controller.StructureInfo;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
        return LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0.m("ControlsBackupControl(structures=", this.structures, ")");
    }
}

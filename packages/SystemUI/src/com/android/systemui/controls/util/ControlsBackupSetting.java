package com.android.systemui.controls.util;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ControlsBackupSetting {
    public boolean controlDevice;
    public boolean isOOBECompleted;
    public String selectedComponent;
    public boolean showDevice;

    public ControlsBackupSetting(boolean z, boolean z2, boolean z3, String str) {
        this.showDevice = z;
        this.controlDevice = z2;
        this.isOOBECompleted = z3;
        this.selectedComponent = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ControlsBackupSetting)) {
            return false;
        }
        ControlsBackupSetting controlsBackupSetting = (ControlsBackupSetting) obj;
        return this.showDevice == controlsBackupSetting.showDevice && this.controlDevice == controlsBackupSetting.controlDevice && this.isOOBECompleted == controlsBackupSetting.isOOBECompleted && Intrinsics.areEqual(this.selectedComponent, controlsBackupSetting.selectedComponent);
    }

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.showDevice) * 31, 31, this.controlDevice), 31, this.isOOBECompleted);
        String str = this.selectedComponent;
        return m + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        boolean z = this.showDevice;
        boolean z2 = this.controlDevice;
        boolean z3 = this.isOOBECompleted;
        String str = this.selectedComponent;
        StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("ControlsBackupSetting(showDevice=", ", controlDevice=", ", isOOBECompleted=", z, z2);
        m.append(z3);
        m.append(", selectedComponent=");
        m.append(str);
        m.append(")");
        return m.toString();
    }
}

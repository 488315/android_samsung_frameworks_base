package com.android.systemui.controls.util;

import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r2v0, types: [boolean] */
    public final int hashCode() {
        boolean z = this.showDevice;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int i = r0 * 31;
        ?? r2 = this.controlDevice;
        int i2 = r2;
        if (r2 != 0) {
            i2 = 1;
        }
        int i3 = (i + i2) * 31;
        boolean z2 = this.isOOBECompleted;
        int i4 = (i3 + (z2 ? 1 : z2 ? 1 : 0)) * 31;
        String str = this.selectedComponent;
        return i4 + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        boolean z = this.showDevice;
        boolean z2 = this.controlDevice;
        boolean z3 = this.isOOBECompleted;
        String str = this.selectedComponent;
        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("ControlsBackupSetting(showDevice=", z, ", controlDevice=", z2, ", isOOBECompleted=");
        m69m.append(z3);
        m69m.append(", selectedComponent=");
        m69m.append(str);
        m69m.append(")");
        return m69m.toString();
    }
}

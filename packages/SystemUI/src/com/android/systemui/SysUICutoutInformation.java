package com.android.systemui;

import android.view.DisplayCutout;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SysUICutoutInformation {
    public final CameraProtectionInfo cameraProtection;
    public final DisplayCutout cutout;

    public SysUICutoutInformation(DisplayCutout displayCutout, CameraProtectionInfo cameraProtectionInfo) {
        this.cutout = displayCutout;
        this.cameraProtection = cameraProtectionInfo;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SysUICutoutInformation)) {
            return false;
        }
        SysUICutoutInformation sysUICutoutInformation = (SysUICutoutInformation) obj;
        return Intrinsics.areEqual(this.cutout, sysUICutoutInformation.cutout) && Intrinsics.areEqual(this.cameraProtection, sysUICutoutInformation.cameraProtection);
    }

    public final int hashCode() {
        int hashCode = this.cutout.hashCode() * 31;
        CameraProtectionInfo cameraProtectionInfo = this.cameraProtection;
        return hashCode + (cameraProtectionInfo == null ? 0 : cameraProtectionInfo.hashCode());
    }

    public final String toString() {
        return "SysUICutoutInformation(cutout=" + this.cutout + ", cameraProtection=" + this.cameraProtection + ")";
    }
}

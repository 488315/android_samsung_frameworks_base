package com.android.systemui;

import android.view.DisplayCutout;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

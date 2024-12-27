package com.android.systemui;

import android.graphics.Path;
import android.graphics.Rect;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CameraProtectionInfo {
    public final Rect bounds;
    public final Path cutoutProtectionPath;
    public final String displayUniqueId;
    public final String logicalCameraId;
    public final String physicalCameraId;

    public CameraProtectionInfo(String str, String str2, Path path, Rect rect, String str3) {
        this.logicalCameraId = str;
        this.physicalCameraId = str2;
        this.cutoutProtectionPath = path;
        this.bounds = rect;
        this.displayUniqueId = str3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CameraProtectionInfo)) {
            return false;
        }
        CameraProtectionInfo cameraProtectionInfo = (CameraProtectionInfo) obj;
        return Intrinsics.areEqual(this.logicalCameraId, cameraProtectionInfo.logicalCameraId) && Intrinsics.areEqual(this.physicalCameraId, cameraProtectionInfo.physicalCameraId) && Intrinsics.areEqual(this.cutoutProtectionPath, cameraProtectionInfo.cutoutProtectionPath) && Intrinsics.areEqual(this.bounds, cameraProtectionInfo.bounds) && Intrinsics.areEqual(this.displayUniqueId, cameraProtectionInfo.displayUniqueId);
    }

    public final int hashCode() {
        int hashCode = this.logicalCameraId.hashCode() * 31;
        String str = this.physicalCameraId;
        int hashCode2 = (this.bounds.hashCode() + ((this.cutoutProtectionPath.hashCode() + ((hashCode + (str == null ? 0 : str.hashCode())) * 31)) * 31)) * 31;
        String str2 = this.displayUniqueId;
        return hashCode2 + (str2 != null ? str2.hashCode() : 0);
    }

    public final String toString() {
        Path path = this.cutoutProtectionPath;
        Rect rect = this.bounds;
        StringBuilder sb = new StringBuilder("CameraProtectionInfo(logicalCameraId=");
        sb.append(this.logicalCameraId);
        sb.append(", physicalCameraId=");
        sb.append(this.physicalCameraId);
        sb.append(", cutoutProtectionPath=");
        sb.append(path);
        sb.append(", bounds=");
        sb.append(rect);
        sb.append(", displayUniqueId=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.displayUniqueId, ")");
    }
}

package com.android.systemui.biometrics.data.repository;

import android.graphics.Point;
import kotlin.jvm.internal.Intrinsics;

public final class CameraInfo {
    public final String cameraId;
    public final Point cameraLocation;
    public final String cameraPhysicalId;

    public CameraInfo(String str, String str2, Point point) {
        this.cameraId = str;
        this.cameraPhysicalId = str2;
        this.cameraLocation = point;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CameraInfo)) {
            return false;
        }
        CameraInfo cameraInfo = (CameraInfo) obj;
        return Intrinsics.areEqual(this.cameraId, cameraInfo.cameraId) && Intrinsics.areEqual(this.cameraPhysicalId, cameraInfo.cameraPhysicalId) && Intrinsics.areEqual(this.cameraLocation, cameraInfo.cameraLocation);
    }

    public final int hashCode() {
        int hashCode = this.cameraId.hashCode() * 31;
        String str = this.cameraPhysicalId;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        Point point = this.cameraLocation;
        return hashCode2 + (point != null ? point.hashCode() : 0);
    }

    public final String toString() {
        return "CameraInfo(cameraId=" + this.cameraId + ", cameraPhysicalId=" + this.cameraPhysicalId + ", cameraLocation=" + this.cameraLocation + ")";
    }
}

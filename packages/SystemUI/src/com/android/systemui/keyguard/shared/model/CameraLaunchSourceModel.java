package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class CameraLaunchSourceModel {
    public final long detectedTime;
    public final CameraLaunchType type;

    public CameraLaunchSourceModel() {
        this(null, 0L, 3, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CameraLaunchSourceModel)) {
            return false;
        }
        CameraLaunchSourceModel cameraLaunchSourceModel = (CameraLaunchSourceModel) obj;
        return this.type == cameraLaunchSourceModel.type && this.detectedTime == cameraLaunchSourceModel.detectedTime;
    }

    public final int hashCode() {
        return Long.hashCode(this.detectedTime) + (this.type.hashCode() * 31);
    }

    public final String toString() {
        return "CameraLaunchSourceModel(type=" + this.type + ", detectedTime=" + this.detectedTime + ")";
    }

    public CameraLaunchSourceModel(CameraLaunchType cameraLaunchType, long j) {
        this.type = cameraLaunchType;
        this.detectedTime = j;
    }

    public /* synthetic */ CameraLaunchSourceModel(CameraLaunchType cameraLaunchType, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CameraLaunchType.IGNORE : cameraLaunchType, (i & 2) != 0 ? System.currentTimeMillis() : j);
    }
}

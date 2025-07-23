package com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MediaOutputDeviceDomain {
    public final String description;
    public final String deviceId;
    public final String deviceName;
    public final String deviceType;

    public MediaOutputDeviceDomain(String str, String str2, String str3, String str4) {
        this.deviceId = str;
        this.deviceName = str2;
        this.deviceType = str3;
        this.description = str4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaOutputDeviceDomain)) {
            return false;
        }
        MediaOutputDeviceDomain mediaOutputDeviceDomain = (MediaOutputDeviceDomain) obj;
        return Intrinsics.areEqual(this.deviceId, mediaOutputDeviceDomain.deviceId) && Intrinsics.areEqual(this.deviceName, mediaOutputDeviceDomain.deviceName) && Intrinsics.areEqual(this.deviceType, mediaOutputDeviceDomain.deviceType) && Intrinsics.areEqual(this.description, mediaOutputDeviceDomain.description);
    }

    public final int hashCode() {
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.deviceId.hashCode() * 31, 31, this.deviceName);
        String str = this.deviceType;
        int hashCode = (m + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.description;
        return hashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MediaOutputDeviceDomain(deviceId=");
        sb.append(this.deviceId);
        sb.append(", deviceName=");
        sb.append(this.deviceName);
        sb.append(", deviceType=");
        sb.append(this.deviceType);
        sb.append(", description=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.description, ")");
    }
}

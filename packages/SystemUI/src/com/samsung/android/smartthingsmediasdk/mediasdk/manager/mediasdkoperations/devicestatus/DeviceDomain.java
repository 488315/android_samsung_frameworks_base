package com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class DeviceDomain {
    public final String deviceId;
    public final String deviceName;
    public final String iconUrl;
    public final String locationId;
    public final String locationName;
    public final String roomId;
    public final String roomName;

    public DeviceDomain(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.deviceId = str;
        this.deviceName = str2;
        this.iconUrl = str3;
        this.locationId = str4;
        this.locationName = str5;
        this.roomId = str6;
        this.roomName = str7;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeviceDomain)) {
            return false;
        }
        DeviceDomain deviceDomain = (DeviceDomain) obj;
        return Intrinsics.areEqual(this.deviceId, deviceDomain.deviceId) && Intrinsics.areEqual(this.deviceName, deviceDomain.deviceName) && Intrinsics.areEqual(this.iconUrl, deviceDomain.iconUrl) && Intrinsics.areEqual(this.locationId, deviceDomain.locationId) && Intrinsics.areEqual(this.locationName, deviceDomain.locationName) && Intrinsics.areEqual(this.roomId, deviceDomain.roomId) && Intrinsics.areEqual(this.roomName, deviceDomain.roomName);
    }

    public final int hashCode() {
        return this.roomName.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.deviceId.hashCode() * 31, 31, this.deviceName), 31, this.iconUrl), 31, this.locationId), 31, this.locationName), 31, this.roomId);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DeviceDomain(deviceId=");
        sb.append(this.deviceId);
        sb.append(", deviceName=");
        sb.append(this.deviceName);
        sb.append(", iconUrl=");
        sb.append(this.iconUrl);
        sb.append(", locationId=");
        sb.append(this.locationId);
        sb.append(", locationName=");
        sb.append(this.locationName);
        sb.append(", roomId=");
        sb.append(this.roomId);
        sb.append(", roomName=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.roomName, ")");
    }
}

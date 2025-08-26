package com.samsung.android.oneconnect.mediaoutput.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class Device implements Parcelable {
    public static final Parcelable.Creator<Device> CREATOR = new Creator();
    public final String deviceId;
    public final String deviceName;
    public final String iconUrl;
    public final String locationId;
    public final String locationName;
    public final String roomId;
    public final String roomName;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new Device(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Device[i];
        }
    }

    public Device(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.deviceId = str;
        this.deviceName = str2;
        this.iconUrl = str3;
        this.locationId = str4;
        this.locationName = str5;
        this.roomId = str6;
        this.roomName = str7;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Device)) {
            return false;
        }
        Device device = (Device) obj;
        return Intrinsics.areEqual(this.deviceId, device.deviceId) && Intrinsics.areEqual(this.deviceName, device.deviceName) && Intrinsics.areEqual(this.iconUrl, device.iconUrl) && Intrinsics.areEqual(this.locationId, device.locationId) && Intrinsics.areEqual(this.locationName, device.locationName) && Intrinsics.areEqual(this.roomId, device.roomId) && Intrinsics.areEqual(this.roomName, device.roomName);
    }

    public final int hashCode() {
        return this.roomName.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.deviceId.hashCode() * 31, 31, this.deviceName), 31, this.iconUrl), 31, this.locationId), 31, this.locationName), 31, this.roomId);
    }

    public final String toString() {
        String str = this.deviceId;
        String str2 = this.deviceName;
        String str3 = this.iconUrl;
        String str4 = this.locationId;
        String str5 = this.locationName;
        String str6 = this.roomId;
        String str7 = this.roomName;
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Device(deviceId=", str, ", deviceName=", str2, ", iconUrl=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, str3, ", locationId=", str4, ", locationName=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, str5, ", roomId=", str6, ", roomName=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sbM, str7, ")");
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.deviceId);
        parcel.writeString(this.deviceName);
        parcel.writeString(this.iconUrl);
        parcel.writeString(this.locationId);
        parcel.writeString(this.locationName);
        parcel.writeString(this.roomId);
        parcel.writeString(this.roomName);
    }
}

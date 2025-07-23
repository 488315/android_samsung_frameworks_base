package com.samsung.android.oneconnect.mediaoutput.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MediaOutputDeviceV2 implements Parcelable {
    public static final Parcelable.Creator<MediaOutputDeviceV2> CREATOR = new Creator();
    public final String description;
    public final String deviceId;
    public final String deviceName;
    public final String deviceType;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new MediaOutputDeviceV2(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new MediaOutputDeviceV2[i];
        }
    }

    public MediaOutputDeviceV2(String str, String str2, String str3, String str4) {
        this.deviceId = str;
        this.deviceName = str2;
        this.deviceType = str3;
        this.description = str4;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaOutputDeviceV2)) {
            return false;
        }
        MediaOutputDeviceV2 mediaOutputDeviceV2 = (MediaOutputDeviceV2) obj;
        return Intrinsics.areEqual(this.deviceId, mediaOutputDeviceV2.deviceId) && Intrinsics.areEqual(this.deviceName, mediaOutputDeviceV2.deviceName) && Intrinsics.areEqual(this.deviceType, mediaOutputDeviceV2.deviceType) && Intrinsics.areEqual(this.description, mediaOutputDeviceV2.description);
    }

    public final int hashCode() {
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.deviceId.hashCode() * 31, 31, this.deviceName);
        String str = this.deviceType;
        int hashCode = (m + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.description;
        return hashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public final String toString() {
        String str = this.deviceId;
        String str2 = this.deviceName;
        return NotificationController$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("MediaOutputDeviceV2(deviceId=", str, ", deviceName=", str2, ", deviceType="), this.deviceType, ", description=", this.description, ")");
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.deviceId);
        parcel.writeString(this.deviceName);
        parcel.writeString(this.deviceType);
        parcel.writeString(this.description);
    }
}

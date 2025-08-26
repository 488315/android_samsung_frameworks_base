package com.samsung.android.oneconnect.mediaoutput.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class MediaOutputDevice implements Parcelable {
    public static final Parcelable.Creator<MediaOutputDevice> CREATOR = new Creator();
    public final String deviceId;
    public final String deviceName;
    public final String iconUrl;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new MediaOutputDevice(parcel.readString(), parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new MediaOutputDevice[i];
        }
    }

    public MediaOutputDevice(String str, String str2, String str3) {
        this.deviceId = str;
        this.deviceName = str2;
        this.iconUrl = str3;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaOutputDevice)) {
            return false;
        }
        MediaOutputDevice mediaOutputDevice = (MediaOutputDevice) obj;
        return Intrinsics.areEqual(this.deviceId, mediaOutputDevice.deviceId) && Intrinsics.areEqual(this.deviceName, mediaOutputDevice.deviceName) && Intrinsics.areEqual(this.iconUrl, mediaOutputDevice.iconUrl);
    }

    public final int hashCode() {
        int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.deviceId.hashCode() * 31, 31, this.deviceName);
        String str = this.iconUrl;
        return iM + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        String str = this.deviceId;
        String str2 = this.deviceName;
        return TransitionKt$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("MediaOutputDevice(deviceId=", str, ", deviceName=", str2, ", iconUrl="), this.iconUrl, ")");
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.deviceId);
        parcel.writeString(this.deviceName);
        parcel.writeString(this.iconUrl);
    }
}

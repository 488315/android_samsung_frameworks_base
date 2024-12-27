package com.android.server.location.nsflp;

import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.os.Parcel;
import android.os.Parcelable;

public final class LocationRequestInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public long backgroundDuration;
    public long backgroundTime;
    public long interval;
    public boolean isAllowed;
    public boolean isForeground;
    public boolean isHighPowerRequest;
    public boolean isListenerType;
    public boolean isPassive;
    public boolean isSystemApp;
    public long lastUpdateTime;
    public String listenerId;
    public long maxWaitTime;
    public long minUpdateInterval;
    public int numUpdates;
    public String packageName;
    public int pid;
    public String provider;
    public int quality;
    public long removedTime;
    public long requestTime;
    public int requester;
    public int uid;
    public String versionName;

    /* renamed from: com.android.server.location.nsflp.LocationRequestInfo$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            LocationRequestInfo locationRequestInfo = new LocationRequestInfo();
            locationRequestInfo.isPassive = false;
            locationRequestInfo.isHighPowerRequest = false;
            locationRequestInfo.lastUpdateTime = 0L;
            locationRequestInfo.isAllowed = true;
            locationRequestInfo.packageName = parcel.readString();
            locationRequestInfo.versionName = parcel.readString();
            locationRequestInfo.listenerId = parcel.readString();
            locationRequestInfo.provider = parcel.readString();
            locationRequestInfo.quality = parcel.readInt();
            locationRequestInfo.requestTime = parcel.readLong();
            locationRequestInfo.removedTime = parcel.readLong();
            locationRequestInfo.backgroundTime = parcel.readLong();
            locationRequestInfo.backgroundDuration = parcel.readLong();
            locationRequestInfo.interval = parcel.readLong();
            locationRequestInfo.minUpdateInterval = parcel.readLong();
            locationRequestInfo.maxWaitTime = parcel.readLong();
            locationRequestInfo.requester = parcel.readInt();
            locationRequestInfo.isForeground = parcel.readInt() != 0;
            locationRequestInfo.numUpdates = parcel.readInt();
            locationRequestInfo.uid = parcel.readInt();
            locationRequestInfo.pid = parcel.readInt();
            locationRequestInfo.isListenerType = parcel.readByte() != 0;
            locationRequestInfo.isSystemApp = parcel.readByte() != 0;
            locationRequestInfo.isPassive = parcel.readByte() != 0;
            locationRequestInfo.isHighPowerRequest = parcel.readByte() != 0;
            locationRequestInfo.lastUpdateTime = parcel.readLong();
            locationRequestInfo.isAllowed = parcel.readByte() != 0;
            return locationRequestInfo;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new LocationRequestInfo[i];
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.packageName);
        sb.append("[");
        sb.append(this.provider);
        sb.append(",");
        sb.append(this.interval);
        sb.append(",");
        return OptionalBool$$ExternalSyntheticOutline0.m("]", sb, this.isForeground);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeString(this.versionName);
        parcel.writeString(this.listenerId);
        parcel.writeString(this.provider);
        parcel.writeInt(this.quality);
        parcel.writeLong(this.requestTime);
        parcel.writeLong(this.removedTime);
        parcel.writeLong(this.backgroundTime);
        parcel.writeLong(this.backgroundDuration);
        parcel.writeLong(this.interval);
        parcel.writeLong(this.minUpdateInterval);
        parcel.writeLong(this.maxWaitTime);
        parcel.writeInt(this.requester);
        parcel.writeInt(this.isForeground ? 1 : 0);
        parcel.writeInt(this.numUpdates);
        parcel.writeInt(this.uid);
        parcel.writeInt(this.pid);
        parcel.writeByte(this.isListenerType ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isSystemApp ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isPassive ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isHighPowerRequest ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.lastUpdateTime);
        parcel.writeByte(this.isAllowed ? (byte) 1 : (byte) 0);
    }
}

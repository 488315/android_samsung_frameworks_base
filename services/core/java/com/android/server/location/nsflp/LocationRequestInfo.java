package com.android.server.location.nsflp;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class LocationRequestInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.android.server.location.nsflp.LocationRequestInfo.1
        @Override // android.os.Parcelable.Creator
        public LocationRequestInfo createFromParcel(Parcel parcel) {
            return new LocationRequestInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LocationRequestInfo[] newArray(int i) {
            return new LocationRequestInfo[i];
        }
    };
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LocationRequestInfo(Builder builder) {
        this.isPassive = false;
        this.isHighPowerRequest = false;
        this.lastUpdateTime = 0L;
        this.isAllowed = true;
        this.packageName = builder.packageName;
        this.versionName = builder.versionName;
        this.listenerId = builder.listenerId;
        this.provider = builder.provider;
        this.quality = builder.quality;
        this.requestTime = builder.requestTime;
        this.backgroundTime = builder.backgroundTime;
        this.interval = builder.interval;
        this.minUpdateInterval = builder.minUpdateInterval;
        this.maxWaitTime = builder.maxWaitTime;
        this.requester = builder.requester;
        this.isForeground = builder.isForeground;
        this.numUpdates = builder.numUpdates;
        this.uid = builder.uid;
        this.pid = builder.pid;
        this.isListenerType = builder.isListenerType;
        this.isAllowed = builder.isAllowed;
        this.isHighPowerRequest = builder.isHighPowerRequest;
    }

    public String toString() {
        return this.packageName + "[" + this.provider + "," + this.interval + "," + this.isForeground + "]";
    }

    public final class Builder {
        public long backgroundTime;
        public long interval;
        public boolean isAllowed;
        public boolean isForeground;
        public boolean isHighPowerRequest;
        public boolean isListenerType;
        public String listenerId;
        public long maxWaitTime;
        public long minUpdateInterval;
        public int numUpdates = Integer.MAX_VALUE;
        public String packageName;
        public int pid;
        public String provider;
        public int quality;
        public long requestTime;
        public int requester;
        public int uid;
        public String versionName;

        public Builder setPackageName(String str) {
            this.packageName = str;
            return this;
        }

        public Builder setListenerId(String str) {
            this.listenerId = str;
            return this;
        }

        public Builder setProvider(String str) {
            this.provider = str;
            return this;
        }

        public Builder setQuality(int i) {
            this.quality = i;
            return this;
        }

        public Builder setRequestTime(long j) {
            this.requestTime = j;
            return this;
        }

        public Builder setInterval(long j) {
            this.interval = j;
            return this;
        }

        public Builder setMinUpdateInterval(long j) {
            this.minUpdateInterval = j;
            return this;
        }

        public Builder setMaxWaitTime(long j) {
            this.maxWaitTime = j;
            return this;
        }

        public Builder setForeground(boolean z) {
            this.isForeground = z;
            return this;
        }

        public Builder setListenerType(boolean z) {
            this.isListenerType = z;
            return this;
        }

        public Builder setAllowed(boolean z) {
            this.isAllowed = z;
            return this;
        }

        public Builder setHighPowerRequest(boolean z) {
            this.isHighPowerRequest = z;
            return this;
        }

        public LocationRequestInfo build() {
            if (this.provider == null || this.listenerId == null || this.packageName == null) {
                throw new NullPointerException("New requestInfo mandatory fields are null,PackageName=" + this.packageName + "/ListenerId=" + this.listenerId + "/Provider=" + this.provider + "/isAllowed=" + this.isAllowed);
            }
            return new LocationRequestInfo(this);
        }

        public Builder setUid(int i) {
            this.uid = i;
            return this;
        }

        public Builder setPid(int i) {
            this.pid = i;
            return this;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
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

    public LocationRequestInfo(Parcel parcel) {
        this.isPassive = false;
        this.isHighPowerRequest = false;
        this.lastUpdateTime = 0L;
        this.isAllowed = true;
        this.packageName = parcel.readString();
        this.versionName = parcel.readString();
        this.listenerId = parcel.readString();
        this.provider = parcel.readString();
        this.quality = parcel.readInt();
        this.requestTime = parcel.readLong();
        this.removedTime = parcel.readLong();
        this.backgroundTime = parcel.readLong();
        this.backgroundDuration = parcel.readLong();
        this.interval = parcel.readLong();
        this.minUpdateInterval = parcel.readLong();
        this.maxWaitTime = parcel.readLong();
        this.requester = parcel.readInt();
        this.isForeground = parcel.readInt() != 0;
        this.numUpdates = parcel.readInt();
        this.uid = parcel.readInt();
        this.pid = parcel.readInt();
        this.isListenerType = parcel.readByte() != 0;
        this.isSystemApp = parcel.readByte() != 0;
        this.isPassive = parcel.readByte() != 0;
        this.isHighPowerRequest = parcel.readByte() != 0;
        this.lastUpdateTime = parcel.readLong();
        this.isAllowed = parcel.readByte() != 0;
    }
}

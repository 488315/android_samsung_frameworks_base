package com.samsung.android.mocca;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.Arrays;

/* loaded from: classes6.dex */
public final class ContextEvent implements Serializable, Parcelable {
    public static final Parcelable.Creator<ContextEvent> CREATOR = new Parcelable.Creator<ContextEvent>() { // from class: com.samsung.android.mocca.ContextEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextEvent createFromParcel(Parcel parcel) {
            return new ContextEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextEvent[] newArray(int i) {
            return new ContextEvent[i];
        }
    };
    private static final long serialVersionUID = 4417824416088197504L;
    public final byte[] data;
    public final String deviceId;
    public final long timestamp;
    public final String type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ContextEvent(long j, String str, String str2, byte[] bArr) {
        this.timestamp = j;
        this.deviceId = str;
        this.type = str2;
        this.data = Arrays.copyOf(bArr, bArr.length);
    }

    protected ContextEvent(Parcel parcel) {
        this.timestamp = parcel.readLong();
        this.deviceId = parcel.readString();
        this.type = parcel.readString();
        this.data = parcel.createByteArray();
    }

    public static ContextEventBuilder builder() {
        return new ContextEventBuilder();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.timestamp);
        parcel.writeString(this.deviceId);
        parcel.writeString(this.type);
        parcel.writeByteArray(this.data);
    }

    public static class ContextEventBuilder {
        private long mTimestamp = -1;
        private String mDeviceId = null;
        private String mType = null;
        private byte[] mData = null;

        protected ContextEventBuilder() {
        }

        public ContextEventBuilder setTimestamp(long j) {
            this.mTimestamp = j;
            return this;
        }

        public ContextEventBuilder setDeviceId(String str) {
            this.mDeviceId = str;
            return this;
        }

        public ContextEventBuilder setType(String str) {
            this.mType = str;
            return this;
        }

        public ContextEventBuilder setData(byte[] bArr) {
            this.mData = bArr;
            return this;
        }

        public ContextEvent build() {
            if (this.mTimestamp < 0 || this.mDeviceId == null || this.mType == null || this.mData == null) {
                return null;
            }
            return new ContextEvent(this.mTimestamp, this.mDeviceId, this.mType, this.mData);
        }
    }
}

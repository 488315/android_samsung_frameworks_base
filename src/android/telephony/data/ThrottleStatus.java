package android.telephony.data;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class ThrottleStatus implements Parcelable {
    public static final Parcelable.Creator<ThrottleStatus> CREATOR = new Parcelable.Creator<ThrottleStatus>() { // from class: android.telephony.data.ThrottleStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ThrottleStatus createFromParcel(Parcel parcel) {
            return new ThrottleStatus(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ThrottleStatus[] newArray(int i) {
            return new ThrottleStatus[i];
        }
    };
    public static final int RETRY_TYPE_HANDOVER = 3;
    public static final int RETRY_TYPE_NEW_CONNECTION = 2;
    public static final int RETRY_TYPE_NONE = 1;
    public static final int THROTTLE_TYPE_ELAPSED_TIME = 2;
    public static final int THROTTLE_TYPE_NONE = 1;
    private final int mApnType;
    private final int mRetryType;
    private final int mSlotIndex;
    private final long mThrottleExpiryTimeMillis;
    private final int mThrottleType;
    private final int mTransportType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RetryType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ThrottleType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getSlotIndex() {
        return this.mSlotIndex;
    }

    public int getTransportType() {
        return this.mTransportType;
    }

    public int getApnType() {
        return this.mApnType;
    }

    public int getThrottleType() {
        return this.mThrottleType;
    }

    public int getRetryType() {
        return this.mRetryType;
    }

    public long getThrottleExpiryTimeMillis() {
        return this.mThrottleExpiryTimeMillis;
    }

    private ThrottleStatus(int i, int i2, int i3, int i4, long j, int i5) {
        this.mSlotIndex = i;
        this.mTransportType = i2;
        this.mApnType = i3;
        this.mThrottleType = i4;
        this.mThrottleExpiryTimeMillis = j;
        this.mRetryType = i5;
    }

    private ThrottleStatus(Parcel parcel) {
        this.mSlotIndex = parcel.readInt();
        this.mTransportType = parcel.readInt();
        this.mApnType = parcel.readInt();
        this.mThrottleExpiryTimeMillis = parcel.readLong();
        this.mRetryType = parcel.readInt();
        this.mThrottleType = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSlotIndex);
        parcel.writeInt(this.mTransportType);
        parcel.writeInt(this.mApnType);
        parcel.writeLong(this.mThrottleExpiryTimeMillis);
        parcel.writeInt(this.mRetryType);
        parcel.writeInt(this.mThrottleType);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mSlotIndex), Integer.valueOf(this.mApnType), Integer.valueOf(this.mRetryType), Integer.valueOf(this.mThrottleType), Long.valueOf(this.mThrottleExpiryTimeMillis), Integer.valueOf(this.mTransportType));
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ThrottleStatus)) {
            ThrottleStatus throttleStatus = (ThrottleStatus) obj;
            if (this.mSlotIndex == throttleStatus.mSlotIndex && this.mApnType == throttleStatus.mApnType && this.mRetryType == throttleStatus.mRetryType && this.mThrottleType == throttleStatus.mThrottleType && this.mThrottleExpiryTimeMillis == throttleStatus.mThrottleExpiryTimeMillis && this.mTransportType == throttleStatus.mTransportType) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "ThrottleStatus{mSlotIndex=" + this.mSlotIndex + ", mTransportType=" + this.mTransportType + ", mApnType=" + ApnSetting.getApnTypeString(this.mApnType) + ", mThrottleExpiryTimeMillis=" + this.mThrottleExpiryTimeMillis + ", mRetryType=" + this.mRetryType + ", mThrottleType=" + this.mThrottleType + '}';
    }

    public static final class Builder {
        public static final long NO_THROTTLE_EXPIRY_TIME = -1;
        private int mApnType;
        private int mRetryType;
        private int mSlotIndex;
        private long mThrottleExpiryTimeMillis;
        private int mThrottleType;
        private int mTransportType;

        public Builder setSlotIndex(int i) {
            this.mSlotIndex = i;
            return this;
        }

        public Builder setTransportType(int i) {
            this.mTransportType = i;
            return this;
        }

        public Builder setApnType(int i) {
            this.mApnType = i;
            return this;
        }

        public Builder setThrottleExpiryTimeMillis(long j) {
            if (j >= 0) {
                this.mThrottleExpiryTimeMillis = j;
                this.mThrottleType = 2;
                return this;
            }
            throw new IllegalArgumentException("throttleExpiryTimeMillis must be greater than or equal to 0");
        }

        public Builder setNoThrottle() {
            this.mThrottleType = 1;
            this.mThrottleExpiryTimeMillis = -1L;
            return this;
        }

        public Builder setRetryType(int i) {
            this.mRetryType = i;
            return this;
        }

        public ThrottleStatus build() {
            return new ThrottleStatus(this.mSlotIndex, this.mTransportType, this.mApnType, this.mThrottleType, this.mThrottleExpiryTimeMillis, this.mRetryType);
        }
    }
}

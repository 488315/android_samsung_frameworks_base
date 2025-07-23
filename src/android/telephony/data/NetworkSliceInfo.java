package android.telephony.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class NetworkSliceInfo implements Parcelable {
    public static final Parcelable.Creator<NetworkSliceInfo> CREATOR = new Parcelable.Creator<NetworkSliceInfo>() { // from class: android.telephony.data.NetworkSliceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkSliceInfo createFromParcel(Parcel parcel) {
            return new NetworkSliceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkSliceInfo[] newArray(int i) {
            return new NetworkSliceInfo[i];
        }
    };
    public static final int MAX_SLICE_DIFFERENTIATOR = 16777214;
    public static final int MAX_SLICE_STATUS = 5;
    public static final int MIN_SLICE_DIFFERENTIATOR = -1;
    public static final int MIN_SLICE_STATUS = 0;
    public static final int SLICE_DIFFERENTIATOR_NO_SLICE = -1;
    public static final int SLICE_SERVICE_TYPE_EMBB = 1;
    public static final int SLICE_SERVICE_TYPE_MIOT = 3;
    public static final int SLICE_SERVICE_TYPE_NONE = 0;
    public static final int SLICE_SERVICE_TYPE_URLLC = 2;
    public static final int SLICE_STATUS_ALLOWED = 2;
    public static final int SLICE_STATUS_CONFIGURED = 1;
    public static final int SLICE_STATUS_DEFAULT_CONFIGURED = 5;
    public static final int SLICE_STATUS_REJECTED_NOT_AVAILABLE_IN_PLMN = 3;
    public static final int SLICE_STATUS_REJECTED_NOT_AVAILABLE_IN_REGISTERED_AREA = 4;
    public static final int SLICE_STATUS_UNKNOWN = 0;
    private final int mMappedHplmnSliceDifferentiator;
    private final int mMappedHplmnSliceServiceType;
    private final int mSliceDifferentiator;
    private final int mSliceServiceType;
    private final int mStatus;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SliceServiceType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SliceStatus {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private NetworkSliceInfo(int i, int i2, int i3, int i4, int i5) {
        this.mSliceServiceType = i;
        this.mSliceDifferentiator = i2;
        this.mMappedHplmnSliceDifferentiator = i4;
        this.mMappedHplmnSliceServiceType = i3;
        this.mStatus = i5;
    }

    public int getSliceServiceType() {
        return this.mSliceServiceType;
    }

    public int getSliceDifferentiator() {
        return this.mSliceDifferentiator;
    }

    public int getMappedHplmnSliceServiceType() {
        return this.mMappedHplmnSliceServiceType;
    }

    public int getMappedHplmnSliceDifferentiator() {
        return this.mMappedHplmnSliceDifferentiator;
    }

    public int getStatus() {
        return this.mStatus;
    }

    private NetworkSliceInfo(Parcel parcel) {
        this.mSliceServiceType = parcel.readInt();
        this.mSliceDifferentiator = parcel.readInt();
        this.mMappedHplmnSliceServiceType = parcel.readInt();
        this.mMappedHplmnSliceDifferentiator = parcel.readInt();
        this.mStatus = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSliceServiceType);
        parcel.writeInt(this.mSliceDifferentiator);
        parcel.writeInt(this.mMappedHplmnSliceServiceType);
        parcel.writeInt(this.mMappedHplmnSliceDifferentiator);
        parcel.writeInt(this.mStatus);
    }

    public String toString() {
        return "SliceInfo{mSliceServiceType=" + sliceServiceTypeToString(this.mSliceServiceType) + ", mSliceDifferentiator=" + this.mSliceDifferentiator + ", mMappedHplmnSliceServiceType=" + sliceServiceTypeToString(this.mMappedHplmnSliceServiceType) + ", mMappedHplmnSliceDifferentiator=" + this.mMappedHplmnSliceDifferentiator + ", mStatus=" + sliceStatusToString(this.mStatus) + '}';
    }

    private static String sliceServiceTypeToString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "EMBB";
        }
        if (i == 2) {
            return "URLLC";
        }
        if (i == 3) {
            return "MIOT";
        }
        return Integer.toString(i);
    }

    private static String sliceStatusToString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "CONFIGURED";
        }
        if (i == 2) {
            return "ALLOWED";
        }
        if (i == 3) {
            return "REJECTED_NOT_AVAILABLE_IN_PLMN";
        }
        if (i == 4) {
            return "REJECTED_NOT_AVAILABLE_IN_REGISTERED_AREA";
        }
        if (i == 5) {
            return "DEFAULT_CONFIGURED";
        }
        return Integer.toString(i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            NetworkSliceInfo networkSliceInfo = (NetworkSliceInfo) obj;
            if (this.mSliceServiceType == networkSliceInfo.mSliceServiceType && this.mSliceDifferentiator == networkSliceInfo.mSliceDifferentiator && this.mMappedHplmnSliceServiceType == networkSliceInfo.mMappedHplmnSliceServiceType && this.mMappedHplmnSliceDifferentiator == networkSliceInfo.mMappedHplmnSliceDifferentiator && this.mStatus == networkSliceInfo.mStatus) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mSliceServiceType), Integer.valueOf(this.mSliceDifferentiator), Integer.valueOf(this.mMappedHplmnSliceServiceType), Integer.valueOf(this.mMappedHplmnSliceDifferentiator), Integer.valueOf(this.mStatus));
    }

    public static final class Builder {
        private int mSliceServiceType = 0;
        private int mSliceDifferentiator = -1;
        private int mMappedHplmnSliceServiceType = 0;
        private int mMappedHplmnSliceDifferentiator = -1;
        private int mStatus = 0;

        public Builder setSliceServiceType(int i) {
            this.mSliceServiceType = i;
            return this;
        }

        public Builder setSliceDifferentiator(int i) {
            if (i < -1 || i > 16777214) {
                throw new IllegalArgumentException("The slice diffentiator value is out of range");
            }
            this.mSliceDifferentiator = i;
            return this;
        }

        public Builder setMappedHplmnSliceServiceType(int i) {
            this.mMappedHplmnSliceServiceType = i;
            return this;
        }

        public Builder setMappedHplmnSliceDifferentiator(int i) {
            if (i < -1 || i > 16777214) {
                throw new IllegalArgumentException("The slice diffentiator value is out of range");
            }
            this.mMappedHplmnSliceDifferentiator = i;
            return this;
        }

        public Builder setStatus(int i) {
            if (i < 0 || i > 5) {
                throw new IllegalArgumentException("The slice status is not valid");
            }
            this.mStatus = i;
            return this;
        }

        public NetworkSliceInfo build() {
            return new NetworkSliceInfo(this.mSliceServiceType, this.mSliceDifferentiator, this.mMappedHplmnSliceServiceType, this.mMappedHplmnSliceDifferentiator, this.mStatus);
        }
    }
}

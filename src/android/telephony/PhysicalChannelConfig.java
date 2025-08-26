package android.telephony;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.TelephonyFeatures;
import com.samsung.android.lock.LsConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class PhysicalChannelConfig implements Parcelable {
    public static final int BAND_UNKNOWN = 0;
    public static final int CELL_BANDWIDTH_UNKNOWN = 0;
    public static final int CHANNEL_NUMBER_UNKNOWN = Integer.MAX_VALUE;

    @Deprecated
    public static final int CONNECTION_PRIMARY_SERVING = 1;

    @Deprecated
    public static final int CONNECTION_SECONDARY_SERVING = 2;

    @Deprecated
    public static final int CONNECTION_UNKNOWN = -1;
    public static final Parcelable.Creator<PhysicalChannelConfig> CREATOR = new Parcelable.Creator<PhysicalChannelConfig>() { // from class: android.telephony.PhysicalChannelConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhysicalChannelConfig createFromParcel(Parcel parcel) {
            return new PhysicalChannelConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhysicalChannelConfig[] newArray(int i) {
            return new PhysicalChannelConfig[i];
        }
    };
    public static final int FREQUENCY_UNKNOWN = -1;
    public static final int PHYSICAL_CELL_ID_MAXIMUM_VALUE = 1007;
    public static final int PHYSICAL_CELL_ID_UNKNOWN = -1;
    private int mBand;
    private int mCellBandwidthDownlinkKhz;
    private int mCellBandwidthUplinkKhz;
    private int mCellConnectionStatus;
    private int[] mContextIds;
    private int mDownlinkChannelNumber;
    private int mDownlinkFrequency;
    private int mFrequencyRange;
    private int mNetworkType;
    private int mPhysicalCellId;
    private int mUplinkChannelNumber;
    private int mUplinkFrequency;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConnectionStatus {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCellConnectionStatus);
        parcel.writeInt(this.mCellBandwidthDownlinkKhz);
        parcel.writeInt(this.mCellBandwidthUplinkKhz);
        parcel.writeInt(this.mNetworkType);
        parcel.writeInt(this.mDownlinkChannelNumber);
        parcel.writeInt(this.mUplinkChannelNumber);
        parcel.writeInt(this.mFrequencyRange);
        parcel.writeIntArray(this.mContextIds);
        parcel.writeInt(this.mPhysicalCellId);
        parcel.writeInt(this.mBand);
    }

    public int getCellBandwidthDownlinkKhz() {
        return this.mCellBandwidthDownlinkKhz;
    }

    public int getCellBandwidthUplinkKhz() {
        return this.mCellBandwidthUplinkKhz;
    }

    public int[] getContextIds() {
        return this.mContextIds;
    }

    public int getFrequencyRange() {
        return this.mFrequencyRange;
    }

    public int getDownlinkChannelNumber() {
        return this.mDownlinkChannelNumber;
    }

    public int getUplinkChannelNumber() {
        return this.mUplinkChannelNumber;
    }

    public int getBand() {
        return this.mBand;
    }

    public int getDownlinkFrequencyKhz() {
        return this.mDownlinkFrequency;
    }

    public int getUplinkFrequencyKhz() {
        return this.mUplinkFrequency;
    }

    public int getPhysicalCellId() {
        return this.mPhysicalCellId;
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    public int getConnectionStatus() {
        return this.mCellConnectionStatus;
    }

    public PhysicalChannelConfig createLocationInfoSanitizedCopy() {
        return new Builder(this).setPhysicalCellId(-1).build();
    }

    private String getConnectionStatusString() {
        int i = this.mCellConnectionStatus;
        if (i == -1) {
            return LsConstants.TAG_UNKNOWN;
        }
        if (i == 1) {
            return "PrimaryServing";
        }
        if (i == 2) {
            return "SecondaryServing";
        }
        return "Invalid(" + this.mCellConnectionStatus + NavigationBarInflaterView.KEY_CODE_END;
    }

    private void setDownlinkFrequency() {
        int i = this.mNetworkType;
        if (i != 1 && i != 2) {
            if (i != 3) {
                if (i == 13) {
                    this.mDownlinkFrequency = AccessNetworkUtils.getFrequencyFromEarfcn(this.mBand, this.mDownlinkChannelNumber, false);
                    return;
                }
                if (i == 20) {
                    this.mDownlinkFrequency = AccessNetworkUtils.getFrequencyFromNrArfcn(this.mDownlinkChannelNumber);
                    return;
                }
                switch (i) {
                    default:
                        switch (i) {
                        }
                    case 8:
                    case 9:
                    case 10:
                        this.mDownlinkFrequency = AccessNetworkUtils.getFrequencyFromUarfcn(this.mBand, this.mDownlinkChannelNumber, false);
                        break;
                }
                return;
            }
            this.mDownlinkFrequency = AccessNetworkUtils.getFrequencyFromUarfcn(this.mBand, this.mDownlinkChannelNumber, false);
            return;
        }
        this.mDownlinkFrequency = AccessNetworkUtils.getFrequencyFromArfcn(this.mBand, this.mDownlinkChannelNumber, false);
    }

    private void setUplinkFrequency() {
        int i = this.mNetworkType;
        if (i != 1 && i != 2) {
            if (i != 3) {
                if (i == 13) {
                    this.mUplinkFrequency = AccessNetworkUtils.getFrequencyFromEarfcn(this.mBand, this.mUplinkChannelNumber, true);
                    return;
                }
                if (i == 20) {
                    this.mUplinkFrequency = AccessNetworkUtils.getFrequencyFromNrArfcn(this.mUplinkChannelNumber);
                    return;
                }
                switch (i) {
                    default:
                        switch (i) {
                        }
                    case 8:
                    case 9:
                    case 10:
                        this.mUplinkFrequency = AccessNetworkUtils.getFrequencyFromUarfcn(this.mBand, this.mUplinkChannelNumber, true);
                        break;
                }
                return;
            }
            this.mUplinkFrequency = AccessNetworkUtils.getFrequencyFromUarfcn(this.mBand, this.mUplinkChannelNumber, true);
            return;
        }
        this.mUplinkFrequency = AccessNetworkUtils.getFrequencyFromArfcn(this.mBand, this.mUplinkChannelNumber, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0034 A[FALL_THROUGH] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setFrequencyRange() {
        if (this.mFrequencyRange != 0) {
            return;
        }
        int i = this.mNetworkType;
        if (i == 1 || i == 2) {
            this.mFrequencyRange = AccessNetworkUtils.getFrequencyRangeGroupFromGeranBand(this.mBand);
        } else if (i == 3) {
            this.mFrequencyRange = AccessNetworkUtils.getFrequencyRangeGroupFromUtranBand(this.mBand);
        } else if (i == 13) {
            this.mFrequencyRange = AccessNetworkUtils.getFrequencyRangeGroupFromEutranBand(this.mBand);
        } else if (i == 20) {
            this.mFrequencyRange = AccessNetworkUtils.getFrequencyRangeGroupFromNrBand(this.mBand);
        } else {
            switch (i) {
                default:
                    switch (i) {
                        case 15:
                        case 17:
                            break;
                        case 16:
                            break;
                        default:
                            this.mFrequencyRange = 0;
                            break;
                    }
                case 8:
                case 9:
                case 10:
                    break;
            }
        }
        if (this.mFrequencyRange == 0) {
            this.mFrequencyRange = AccessNetworkUtils.getFrequencyRangeFromArfcn(this.mDownlinkFrequency);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PhysicalChannelConfig)) {
            return false;
        }
        PhysicalChannelConfig physicalChannelConfig = (PhysicalChannelConfig) obj;
        return this.mCellConnectionStatus == physicalChannelConfig.mCellConnectionStatus && this.mCellBandwidthDownlinkKhz == physicalChannelConfig.mCellBandwidthDownlinkKhz && this.mCellBandwidthUplinkKhz == physicalChannelConfig.mCellBandwidthUplinkKhz && this.mNetworkType == physicalChannelConfig.mNetworkType && this.mFrequencyRange == physicalChannelConfig.mFrequencyRange && this.mDownlinkChannelNumber == physicalChannelConfig.mDownlinkChannelNumber && this.mUplinkChannelNumber == physicalChannelConfig.mUplinkChannelNumber && this.mPhysicalCellId == physicalChannelConfig.mPhysicalCellId && Arrays.equals(this.mContextIds, physicalChannelConfig.mContextIds) && this.mBand == physicalChannelConfig.mBand && this.mDownlinkFrequency == physicalChannelConfig.mDownlinkFrequency && this.mUplinkFrequency == physicalChannelConfig.mUplinkFrequency;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mCellConnectionStatus), Integer.valueOf(this.mCellBandwidthDownlinkKhz), Integer.valueOf(this.mCellBandwidthUplinkKhz), Integer.valueOf(this.mNetworkType), Integer.valueOf(this.mFrequencyRange), Integer.valueOf(this.mDownlinkChannelNumber), Integer.valueOf(this.mUplinkChannelNumber), Integer.valueOf(Arrays.hashCode(this.mContextIds)), Integer.valueOf(this.mPhysicalCellId), Integer.valueOf(this.mBand), Integer.valueOf(this.mDownlinkFrequency), Integer.valueOf(this.mUplinkFrequency));
    }

    public String toString() {
        return "{mConnectionStatus=" + getConnectionStatusString() + ",mCellBandwidthDownlinkKhz=" + this.mCellBandwidthDownlinkKhz + ",mCellBandwidthUplinkKhz=" + this.mCellBandwidthUplinkKhz + ",mNetworkType=" + TelephonyManager.getNetworkTypeName(this.mNetworkType) + ",mFrequencyRange=" + ServiceState.frequencyRangeToString(this.mFrequencyRange) + ",mDownlinkChannelNumber=" + this.mDownlinkChannelNumber + ",mUplinkChannelNumber=" + this.mUplinkChannelNumber + ",mContextIds=" + Arrays.toString(this.mContextIds) + ",mPhysicalCellId=" + this.mPhysicalCellId + ",mBand=" + this.mBand + ",mDownlinkFrequency=" + this.mDownlinkFrequency + ",mUplinkFrequency=" + this.mUplinkFrequency + "}";
    }

    private PhysicalChannelConfig(Parcel parcel) {
        this.mCellConnectionStatus = parcel.readInt();
        this.mCellBandwidthDownlinkKhz = parcel.readInt();
        this.mCellBandwidthUplinkKhz = parcel.readInt();
        this.mNetworkType = parcel.readInt();
        this.mDownlinkChannelNumber = parcel.readInt();
        this.mUplinkChannelNumber = parcel.readInt();
        this.mFrequencyRange = parcel.readInt();
        this.mContextIds = parcel.createIntArray();
        this.mPhysicalCellId = parcel.readInt();
        int i = parcel.readInt();
        this.mBand = i;
        if (i > 0) {
            setDownlinkFrequency();
            setUplinkFrequency();
            setFrequencyRange();
        }
    }

    private PhysicalChannelConfig(Builder builder) {
        this.mCellConnectionStatus = builder.mCellConnectionStatus;
        this.mCellBandwidthDownlinkKhz = builder.mCellBandwidthDownlinkKhz;
        this.mCellBandwidthUplinkKhz = builder.mCellBandwidthUplinkKhz;
        this.mNetworkType = builder.mNetworkType;
        this.mDownlinkChannelNumber = builder.mDownlinkChannelNumber;
        this.mUplinkChannelNumber = builder.mUplinkChannelNumber;
        this.mFrequencyRange = builder.mFrequencyRange;
        this.mContextIds = builder.mContextIds;
        this.mPhysicalCellId = builder.mPhysicalCellId;
        this.mBand = builder.mBand;
        if (this.mCellConnectionStatus == 2 && TelephonyFeatures.IS_QCOM && this.mNetworkType == 20 && this.mFrequencyRange == 0) {
            this.mFrequencyRange = this.mBand;
            this.mBand = 0;
        }
        if (this.mBand > 0) {
            setDownlinkFrequency();
            setUplinkFrequency();
            setFrequencyRange();
        }
    }

    public static final class Builder {
        private int mBand;
        private int mCellBandwidthDownlinkKhz;
        private int mCellBandwidthUplinkKhz;
        private int mCellConnectionStatus;
        private int[] mContextIds;
        private int mDownlinkChannelNumber;
        private int mFrequencyRange;
        private int mNetworkType;
        private int mPhysicalCellId;
        private int mUplinkChannelNumber;

        public Builder() {
            this.mNetworkType = 0;
            this.mFrequencyRange = 0;
            this.mDownlinkChannelNumber = Integer.MAX_VALUE;
            this.mUplinkChannelNumber = Integer.MAX_VALUE;
            this.mCellBandwidthDownlinkKhz = 0;
            this.mCellBandwidthUplinkKhz = 0;
            this.mCellConnectionStatus = -1;
            this.mContextIds = new int[0];
            this.mPhysicalCellId = -1;
            this.mBand = 0;
        }

        public Builder(PhysicalChannelConfig physicalChannelConfig) {
            this.mNetworkType = physicalChannelConfig.getNetworkType();
            this.mFrequencyRange = physicalChannelConfig.getFrequencyRange();
            this.mDownlinkChannelNumber = physicalChannelConfig.getDownlinkChannelNumber();
            this.mUplinkChannelNumber = physicalChannelConfig.getUplinkChannelNumber();
            this.mCellBandwidthDownlinkKhz = physicalChannelConfig.getCellBandwidthDownlinkKhz();
            this.mCellBandwidthUplinkKhz = physicalChannelConfig.getCellBandwidthUplinkKhz();
            this.mCellConnectionStatus = physicalChannelConfig.getConnectionStatus();
            this.mContextIds = Arrays.copyOf(physicalChannelConfig.getContextIds(), physicalChannelConfig.getContextIds().length);
            this.mPhysicalCellId = physicalChannelConfig.getPhysicalCellId();
            this.mBand = physicalChannelConfig.getBand();
        }

        public PhysicalChannelConfig build() {
            return new PhysicalChannelConfig(this);
        }

        public Builder setNetworkType(int i) {
            if (!TelephonyManager.isNetworkTypeValid(i)) {
                throw new IllegalArgumentException("Network type " + i + " is invalid.");
            }
            this.mNetworkType = i;
            return this;
        }

        public Builder setFrequencyRange(int i) {
            if (!ServiceState.isFrequencyRangeValid(i) && i != 0) {
                throw new IllegalArgumentException("Frequency range " + i + " is invalid.");
            }
            this.mFrequencyRange = i;
            return this;
        }

        public Builder setDownlinkChannelNumber(int i) {
            this.mDownlinkChannelNumber = i;
            return this;
        }

        public Builder setUplinkChannelNumber(int i) {
            this.mUplinkChannelNumber = i;
            return this;
        }

        public Builder setCellBandwidthDownlinkKhz(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Cell downlink bandwidth(kHz) " + i + " is invalid.");
            }
            this.mCellBandwidthDownlinkKhz = i;
            return this;
        }

        public Builder setCellBandwidthUplinkKhz(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Cell uplink bandwidth(kHz) " + i + " is invalid.");
            }
            this.mCellBandwidthUplinkKhz = i;
            return this;
        }

        public Builder setCellConnectionStatus(int i) {
            this.mCellConnectionStatus = i;
            return this;
        }

        public Builder setContextIds(int[] iArr) {
            if (iArr != null) {
                Arrays.sort(iArr);
            }
            this.mContextIds = iArr;
            return this;
        }

        public Builder setPhysicalCellId(int i) {
            if (i > 1007) {
                throw new IllegalArgumentException("Physical cell ID " + i + " is over limit.");
            }
            this.mPhysicalCellId = i;
            return this;
        }

        public Builder setBand(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("Band " + i + " is invalid.");
            }
            this.mBand = i;
            return this;
        }
    }
}

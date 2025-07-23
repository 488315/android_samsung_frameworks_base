package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public class HdmiDeviceInfo implements Parcelable {
    public static final int ADDR_INTERNAL = 0;
    public static final int ADDR_INVALID = -1;
    public static final int DEVICE_AUDIO_SYSTEM = 5;
    public static final int DEVICE_INACTIVE = -1;
    public static final int DEVICE_PLAYBACK = 4;
    public static final int DEVICE_PURE_CEC_SWITCH = 6;
    public static final int DEVICE_RECORDER = 1;
    public static final int DEVICE_RESERVED = 2;
    public static final int DEVICE_TUNER = 3;
    public static final int DEVICE_TV = 0;
    public static final int DEVICE_VIDEO_PROCESSOR = 7;
    private static final int HDMI_DEVICE_TYPE_CEC = 0;
    private static final int HDMI_DEVICE_TYPE_HARDWARE = 2;
    private static final int HDMI_DEVICE_TYPE_INACTIVE = 100;
    private static final int HDMI_DEVICE_TYPE_MHL = 1;
    public static final int ID_INVALID = 65535;
    private static final int ID_OFFSET_CEC = 0;
    private static final int ID_OFFSET_HARDWARE = 192;
    private static final int ID_OFFSET_MHL = 128;
    public static final int PATH_INTERNAL = 0;
    public static final int PATH_INVALID = 65535;
    public static final int PORT_INVALID = -1;
    public static final int VENDOR_ID_UNKNOWN = 16777215;
    private final int mAdopterId;
    private final int mCecVersion;
    private final DeviceFeatures mDeviceFeatures;
    private final int mDeviceId;
    private final int mDevicePowerStatus;
    private final int mDeviceType;
    private final String mDisplayName;
    private final int mHdmiDeviceType;
    private final int mId;
    private final int mLogicalAddress;
    private final int mPhysicalAddress;
    private final int mPortId;
    private final int mVendorId;
    public static final HdmiDeviceInfo INACTIVE_DEVICE = new HdmiDeviceInfo();
    public static final Parcelable.Creator<HdmiDeviceInfo> CREATOR = new Parcelable.Creator<HdmiDeviceInfo>() { // from class: android.hardware.hdmi.HdmiDeviceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HdmiDeviceInfo createFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            if (readInt == 0) {
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                int readInt6 = parcel.readInt();
                int readInt7 = parcel.readInt();
                String readString = parcel.readString();
                return HdmiDeviceInfo.cecDeviceBuilder().setLogicalAddress(readInt4).setPhysicalAddress(readInt2).setPortId(readInt3).setDeviceType(readInt5).setVendorId(readInt6).setDisplayName(readString).setDevicePowerStatus(readInt7).setCecVersion(parcel.readInt()).build();
            }
            if (readInt == 1) {
                return HdmiDeviceInfo.mhlDevice(readInt2, readInt3, parcel.readInt(), parcel.readInt());
            }
            if (readInt == 2) {
                return HdmiDeviceInfo.hardwarePort(readInt2, readInt3);
            }
            if (readInt != 100) {
                return null;
            }
            return HdmiDeviceInfo.INACTIVE_DEVICE;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HdmiDeviceInfo[] newArray(int i) {
            return new HdmiDeviceInfo[i];
        }
    };

    public static int idForCecDevice(int i) {
        return i;
    }

    public static int idForHardware(int i) {
        return i + 192;
    }

    public static int idForMhlDevice(int i) {
        return i + 128;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public HdmiDeviceInfo() {
        this.mHdmiDeviceType = 100;
        this.mPhysicalAddress = 65535;
        this.mId = 65535;
        this.mLogicalAddress = -1;
        this.mDeviceType = -1;
        this.mCecVersion = 5;
        this.mPortId = -1;
        this.mDevicePowerStatus = -1;
        this.mDisplayName = "Inactive";
        this.mVendorId = 0;
        this.mDeviceFeatures = DeviceFeatures.ALL_FEATURES_SUPPORT_UNKNOWN;
        this.mDeviceId = -1;
        this.mAdopterId = -1;
    }

    public Builder toBuilder() {
        return new Builder();
    }

    private HdmiDeviceInfo(Builder builder) {
        int i = builder.mHdmiDeviceType;
        this.mHdmiDeviceType = i;
        this.mPhysicalAddress = builder.mPhysicalAddress;
        int i2 = builder.mPortId;
        this.mPortId = i2;
        int i3 = builder.mLogicalAddress;
        this.mLogicalAddress = i3;
        this.mDeviceType = builder.mDeviceType;
        this.mCecVersion = builder.mCecVersion;
        this.mVendorId = builder.mVendorId;
        this.mDisplayName = builder.mDisplayName;
        this.mDevicePowerStatus = builder.mDevicePowerStatus;
        this.mDeviceFeatures = builder.mDeviceFeatures;
        this.mDeviceId = builder.mDeviceId;
        this.mAdopterId = builder.mAdopterId;
        if (i == 0) {
            this.mId = idForCecDevice(i3);
            return;
        }
        if (i == 1) {
            this.mId = idForMhlDevice(i2);
        } else if (i == 2) {
            this.mId = idForHardware(i2);
        } else {
            this.mId = 65535;
        }
    }

    public static Builder cecDeviceBuilder() {
        return new Builder(0);
    }

    public static HdmiDeviceInfo mhlDevice(int i, int i2, int i3, int i4) {
        return new Builder(1).setPhysicalAddress(i).setPortId(i2).setVendorId(0).setDisplayName("Mobile").setDeviceId(i3).setAdopterId(i4).build();
    }

    public static HdmiDeviceInfo hardwarePort(int i, int i2) {
        return new Builder(2).setPhysicalAddress(i).setPortId(i2).setVendorId(0).setDisplayName("HDMI" + i2).build();
    }

    public int getId() {
        return this.mId;
    }

    public DeviceFeatures getDeviceFeatures() {
        return this.mDeviceFeatures;
    }

    public int getLogicalAddress() {
        return this.mLogicalAddress;
    }

    public int getPhysicalAddress() {
        return this.mPhysicalAddress;
    }

    public int getPortId() {
        return this.mPortId;
    }

    public int getDeviceType() {
        return this.mDeviceType;
    }

    public int getCecVersion() {
        return this.mCecVersion;
    }

    public int getDevicePowerStatus() {
        return this.mDevicePowerStatus;
    }

    public int getDeviceId() {
        return this.mDeviceId;
    }

    public int getAdopterId() {
        return this.mAdopterId;
    }

    public boolean isSourceType() {
        if (!isCecDevice()) {
            return isMhlDevice();
        }
        int i = this.mDeviceType;
        return i == 4 || i == 1 || i == 3;
    }

    public boolean isCecDevice() {
        return this.mHdmiDeviceType == 0;
    }

    public boolean isMhlDevice() {
        return this.mHdmiDeviceType == 1;
    }

    public boolean isInactivated() {
        return this.mHdmiDeviceType == 100;
    }

    public String getDisplayName() {
        return this.mDisplayName;
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mHdmiDeviceType);
        parcel.writeInt(this.mPhysicalAddress);
        parcel.writeInt(this.mPortId);
        int i2 = this.mHdmiDeviceType;
        if (i2 != 0) {
            if (i2 != 1) {
                return;
            }
            parcel.writeInt(this.mDeviceId);
            parcel.writeInt(this.mAdopterId);
            return;
        }
        parcel.writeInt(this.mLogicalAddress);
        parcel.writeInt(this.mDeviceType);
        parcel.writeInt(this.mVendorId);
        parcel.writeInt(this.mDevicePowerStatus);
        parcel.writeString(this.mDisplayName);
        parcel.writeInt(this.mCecVersion);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = this.mHdmiDeviceType;
        if (i == 0) {
            sb.append("CEC: logical_address: ");
            sb.append(String.format("0x%02X", Integer.valueOf(this.mLogicalAddress)));
            sb.append(" device_type: ");
            sb.append(this.mDeviceType);
            sb.append(" cec_version: ");
            sb.append(this.mCecVersion);
            sb.append(" vendor_id: ");
            sb.append(this.mVendorId);
            sb.append(" display_name: ");
            sb.append(this.mDisplayName);
            sb.append(" power_status: ");
            sb.append(this.mDevicePowerStatus);
            sb.append(" ");
        } else if (i == 1) {
            sb.append("MHL: device_id: ");
            sb.append(String.format("0x%04X", Integer.valueOf(this.mDeviceId)));
            sb.append(" adopter_id: ");
            sb.append(String.format("0x%04X", Integer.valueOf(this.mAdopterId)));
            sb.append(" ");
        } else if (i == 2) {
            sb.append("Hardware: ");
        } else if (i == 100) {
            sb.append("Inactivated: ");
        } else {
            return "";
        }
        sb.append("physical_address: ");
        sb.append(String.format("0x%04X", Integer.valueOf(this.mPhysicalAddress)));
        sb.append(" port_id: ");
        sb.append(this.mPortId);
        if (this.mHdmiDeviceType == 0) {
            sb.append("\n  ");
            sb.append(this.mDeviceFeatures.toString());
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof HdmiDeviceInfo)) {
            return false;
        }
        HdmiDeviceInfo hdmiDeviceInfo = (HdmiDeviceInfo) obj;
        return this.mHdmiDeviceType == hdmiDeviceInfo.mHdmiDeviceType && this.mPhysicalAddress == hdmiDeviceInfo.mPhysicalAddress && this.mPortId == hdmiDeviceInfo.mPortId && this.mLogicalAddress == hdmiDeviceInfo.mLogicalAddress && this.mDeviceType == hdmiDeviceInfo.mDeviceType && this.mCecVersion == hdmiDeviceInfo.mCecVersion && this.mVendorId == hdmiDeviceInfo.mVendorId && this.mDevicePowerStatus == hdmiDeviceInfo.mDevicePowerStatus && this.mDisplayName.equals(hdmiDeviceInfo.mDisplayName) && this.mDeviceId == hdmiDeviceInfo.mDeviceId && this.mAdopterId == hdmiDeviceInfo.mAdopterId;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mHdmiDeviceType), Integer.valueOf(this.mPhysicalAddress), Integer.valueOf(this.mPortId), Integer.valueOf(this.mLogicalAddress), Integer.valueOf(this.mDeviceType), Integer.valueOf(this.mCecVersion), Integer.valueOf(this.mVendorId), Integer.valueOf(this.mDevicePowerStatus), this.mDisplayName, Integer.valueOf(this.mDeviceId), Integer.valueOf(this.mAdopterId));
    }

    public static final class Builder {
        private int mAdopterId;
        private int mCecVersion;
        private DeviceFeatures mDeviceFeatures;
        private int mDeviceId;
        private int mDevicePowerStatus;
        private int mDeviceType;
        private String mDisplayName;
        private final int mHdmiDeviceType;
        private int mLogicalAddress;
        private int mPhysicalAddress;
        private int mPortId;
        private int mVendorId;

        private Builder(int i) {
            this.mPhysicalAddress = 65535;
            this.mPortId = -1;
            this.mLogicalAddress = -1;
            this.mDeviceType = 2;
            this.mCecVersion = 5;
            this.mVendorId = 16777215;
            this.mDisplayName = "";
            this.mDevicePowerStatus = -1;
            this.mDeviceId = -1;
            this.mAdopterId = -1;
            this.mHdmiDeviceType = i;
            if (i == 0) {
                this.mDeviceFeatures = DeviceFeatures.ALL_FEATURES_SUPPORT_UNKNOWN;
            } else {
                this.mDeviceFeatures = DeviceFeatures.NO_FEATURES_SUPPORTED;
            }
        }

        private Builder(HdmiDeviceInfo hdmiDeviceInfo) {
            this.mPhysicalAddress = 65535;
            this.mPortId = -1;
            this.mLogicalAddress = -1;
            this.mDeviceType = 2;
            this.mCecVersion = 5;
            this.mVendorId = 16777215;
            this.mDisplayName = "";
            this.mDevicePowerStatus = -1;
            this.mDeviceId = -1;
            this.mAdopterId = -1;
            this.mHdmiDeviceType = hdmiDeviceInfo.mHdmiDeviceType;
            this.mPhysicalAddress = hdmiDeviceInfo.mPhysicalAddress;
            this.mPortId = hdmiDeviceInfo.mPortId;
            this.mLogicalAddress = hdmiDeviceInfo.mLogicalAddress;
            this.mDeviceType = hdmiDeviceInfo.mDeviceType;
            this.mCecVersion = hdmiDeviceInfo.mCecVersion;
            this.mVendorId = hdmiDeviceInfo.mVendorId;
            this.mDisplayName = hdmiDeviceInfo.mDisplayName;
            this.mDevicePowerStatus = hdmiDeviceInfo.mDevicePowerStatus;
            this.mDeviceId = hdmiDeviceInfo.mDeviceId;
            this.mAdopterId = hdmiDeviceInfo.mAdopterId;
            this.mDeviceFeatures = hdmiDeviceInfo.mDeviceFeatures;
        }

        public HdmiDeviceInfo build() {
            return new HdmiDeviceInfo(this);
        }

        public Builder setPhysicalAddress(int i) {
            this.mPhysicalAddress = i;
            return this;
        }

        public Builder setPortId(int i) {
            this.mPortId = i;
            return this;
        }

        public Builder setLogicalAddress(int i) {
            this.mLogicalAddress = i;
            return this;
        }

        public Builder setDeviceType(int i) {
            this.mDeviceType = i;
            return this;
        }

        public Builder setCecVersion(int i) {
            this.mCecVersion = i;
            return this;
        }

        public Builder setVendorId(int i) {
            this.mVendorId = i;
            return this;
        }

        public Builder setDisplayName(String str) {
            this.mDisplayName = str;
            return this;
        }

        public Builder setDevicePowerStatus(int i) {
            this.mDevicePowerStatus = i;
            return this;
        }

        public Builder setDeviceFeatures(DeviceFeatures deviceFeatures) {
            this.mDeviceFeatures = deviceFeatures;
            return this;
        }

        public Builder setDeviceId(int i) {
            this.mDeviceId = i;
            return this;
        }

        public Builder setAdopterId(int i) {
            this.mAdopterId = i;
            return this;
        }

        public Builder updateDeviceFeatures(DeviceFeatures deviceFeatures) {
            this.mDeviceFeatures = this.mDeviceFeatures.toBuilder().update(deviceFeatures).build();
            return this;
        }
    }
}

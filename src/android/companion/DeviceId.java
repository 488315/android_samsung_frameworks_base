package android.companion;

import android.net.MacAddress;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes.dex */
public final class DeviceId implements Parcelable {
    public static final Parcelable.Creator<DeviceId> CREATOR = new Parcelable.Creator<DeviceId>() { // from class: android.companion.DeviceId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceId[] newArray(int i) {
            return new DeviceId[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceId createFromParcel(Parcel parcel) {
            return new DeviceId(parcel);
        }
    };
    private static final int CUSTOM_ID_LENGTH_LIMIT = 1024;
    private final String mCustomId;
    private final MacAddress mMacAddress;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DeviceId(String str, MacAddress macAddress) {
        this.mCustomId = str;
        this.mMacAddress = macAddress;
    }

    public boolean isSameDevice(DeviceId deviceId) {
        MacAddress macAddress;
        String str;
        if (deviceId == null) {
            return false;
        }
        String str2 = this.mCustomId;
        if (str2 != null && (str = deviceId.mCustomId) != null) {
            return str2.equals(str);
        }
        MacAddress macAddress2 = this.mMacAddress;
        if (macAddress2 == null || (macAddress = deviceId.mMacAddress) == null) {
            return false;
        }
        return macAddress2.equals(macAddress);
    }

    public String getMacAddressAsString() {
        MacAddress macAddress = this.mMacAddress;
        if (macAddress != null) {
            return macAddress.toString().toUpperCase(Locale.US);
        }
        return null;
    }

    public String getCustomId() {
        return this.mCustomId;
    }

    public MacAddress getMacAddress() {
        return this.mMacAddress;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mCustomId != null) {
            parcel.writeInt(1);
            parcel.writeString8(this.mCustomId);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeTypedObject(this.mMacAddress, 0);
    }

    private DeviceId(Parcel parcel) {
        if (parcel.readInt() == 1) {
            this.mCustomId = parcel.readString8();
        } else {
            this.mCustomId = null;
        }
        this.mMacAddress = (MacAddress) parcel.readTypedObject(MacAddress.CREATOR);
    }

    public int hashCode() {
        return Objects.hash(this.mCustomId, this.mMacAddress);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DeviceId) {
            DeviceId deviceId = (DeviceId) obj;
            if (Objects.equals(this.mCustomId, deviceId.mCustomId) && Objects.equals(this.mMacAddress, deviceId.mMacAddress)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "DeviceId{,mCustomId= " + this.mCustomId + ",mMacAddress= " + this.mMacAddress + "}";
    }

    public static final class Builder {
        private String mCustomId;
        private MacAddress mMacAddress;

        public Builder setCustomId(String str) {
            if (str != null && str.length() > 1024) {
                throw new IllegalArgumentException("Length of the custom id must be at most 1024 characters");
            }
            this.mCustomId = str;
            return this;
        }

        public Builder setMacAddress(MacAddress macAddress) {
            this.mMacAddress = macAddress;
            return this;
        }

        public DeviceId build() {
            if (this.mCustomId == null && this.mMacAddress == null) {
                throw new IllegalArgumentException("At least one device id property must benon-null to build a DeviceId.");
            }
            return new DeviceId(this.mCustomId, this.mMacAddress);
        }
    }
}

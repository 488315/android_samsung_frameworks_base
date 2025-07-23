package android.hardware.display;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class DeviceProductInfo implements Parcelable {
    public static final int CONNECTION_TO_SINK_BUILT_IN = 1;
    public static final int CONNECTION_TO_SINK_DIRECT = 2;
    public static final int CONNECTION_TO_SINK_TRANSITIVE = 3;
    public static final int CONNECTION_TO_SINK_UNKNOWN = 0;
    public static final Parcelable.Creator<DeviceProductInfo> CREATOR = new Parcelable.Creator<DeviceProductInfo>() { // from class: android.hardware.display.DeviceProductInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceProductInfo createFromParcel(Parcel parcel) {
            return new DeviceProductInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceProductInfo[] newArray(int i) {
            return new DeviceProductInfo[i];
        }
    };
    private final int mConnectionToSinkType;
    private final ManufactureDate mManufactureDate;
    private final String mManufacturerPnpId;
    private final Integer mModelYear;
    private final String mName;
    private final String mProductId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConnectionToSinkType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DeviceProductInfo(String str, String str2, String str3, Integer num, ManufactureDate manufactureDate, int i) {
        this.mName = str;
        this.mManufacturerPnpId = str2;
        this.mProductId = str3;
        this.mModelYear = num;
        this.mManufactureDate = manufactureDate;
        this.mConnectionToSinkType = i;
    }

    public DeviceProductInfo(String str, String str2, String str3, int i, int i2) {
        this.mName = str;
        this.mManufacturerPnpId = (String) Objects.requireNonNull(str2);
        this.mProductId = (String) Objects.requireNonNull(str3);
        this.mModelYear = Integer.valueOf(i);
        this.mManufactureDate = null;
        this.mConnectionToSinkType = i2;
    }

    private DeviceProductInfo(Parcel parcel) {
        this.mName = parcel.readString();
        this.mManufacturerPnpId = parcel.readString();
        this.mProductId = (String) parcel.readValue(null);
        this.mModelYear = (Integer) parcel.readValue(null);
        this.mManufactureDate = (ManufactureDate) parcel.readValue(null);
        this.mConnectionToSinkType = parcel.readInt();
    }

    public String getName() {
        return this.mName;
    }

    public String getManufacturerPnpId() {
        return this.mManufacturerPnpId;
    }

    public String getProductId() {
        return this.mProductId;
    }

    public int getModelYear() {
        Integer num = this.mModelYear;
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    public int getManufactureYear() {
        ManufactureDate manufactureDate = this.mManufactureDate;
        if (manufactureDate == null || manufactureDate.mYear == null) {
            return -1;
        }
        return this.mManufactureDate.mYear.intValue();
    }

    public int getManufactureWeek() {
        ManufactureDate manufactureDate = this.mManufactureDate;
        if (manufactureDate == null || manufactureDate.mWeek == null) {
            return -1;
        }
        return this.mManufactureDate.mWeek.intValue();
    }

    public ManufactureDate getManufactureDate() {
        return this.mManufactureDate;
    }

    public int getConnectionToSinkType() {
        return this.mConnectionToSinkType;
    }

    public String toString() {
        return "DeviceProductInfo{name=" + this.mName + ", manufacturerPnpId=" + this.mManufacturerPnpId + ", productId=" + this.mProductId + ", modelYear=" + this.mModelYear + ", manufactureDate=" + this.mManufactureDate + ", connectionToSinkType=" + this.mConnectionToSinkType + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            DeviceProductInfo deviceProductInfo = (DeviceProductInfo) obj;
            if (Objects.equals(this.mName, deviceProductInfo.mName) && Objects.equals(this.mManufacturerPnpId, deviceProductInfo.mManufacturerPnpId) && Objects.equals(this.mProductId, deviceProductInfo.mProductId) && Objects.equals(this.mModelYear, deviceProductInfo.mModelYear) && Objects.equals(this.mManufactureDate, deviceProductInfo.mManufactureDate) && this.mConnectionToSinkType == deviceProductInfo.mConnectionToSinkType) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mName, this.mManufacturerPnpId, this.mProductId, this.mModelYear, this.mManufactureDate, Integer.valueOf(this.mConnectionToSinkType));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeString(this.mManufacturerPnpId);
        parcel.writeValue(this.mProductId);
        parcel.writeValue(this.mModelYear);
        parcel.writeValue(this.mManufactureDate);
        parcel.writeInt(this.mConnectionToSinkType);
    }

    public static class ManufactureDate implements Parcelable {
        public static final Parcelable.Creator<ManufactureDate> CREATOR = new Parcelable.Creator<ManufactureDate>() { // from class: android.hardware.display.DeviceProductInfo.ManufactureDate.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ManufactureDate createFromParcel(Parcel parcel) {
                return new ManufactureDate(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ManufactureDate[] newArray(int i) {
                return new ManufactureDate[i];
            }
        };
        private final Integer mWeek;
        private final Integer mYear;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public ManufactureDate(Integer num, Integer num2) {
            this.mWeek = num;
            this.mYear = num2;
        }

        protected ManufactureDate(Parcel parcel) {
            this.mWeek = (Integer) parcel.readValue(null);
            this.mYear = (Integer) parcel.readValue(null);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeValue(this.mWeek);
            parcel.writeValue(this.mYear);
        }

        public Integer getYear() {
            return this.mYear;
        }

        public Integer getWeek() {
            return this.mWeek;
        }

        public String toString() {
            return "ManufactureDate{week=" + this.mWeek + ", year=" + this.mYear + '}';
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                ManufactureDate manufactureDate = (ManufactureDate) obj;
                if (Objects.equals(this.mWeek, manufactureDate.mWeek) && Objects.equals(this.mYear, manufactureDate.mYear)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mWeek, this.mYear);
        }
    }
}

package android.net.wifi.sharedconnectivity.app;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class NetworkProviderInfo implements Parcelable {
    public static final Parcelable.Creator<NetworkProviderInfo> CREATOR = new Parcelable.Creator<NetworkProviderInfo>() { // from class: android.net.wifi.sharedconnectivity.app.NetworkProviderInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkProviderInfo createFromParcel(Parcel parcel) {
            return NetworkProviderInfo.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkProviderInfo[] newArray(int i) {
            return new NetworkProviderInfo[i];
        }
    };
    public static final int DEVICE_TYPE_AUTO = 5;
    public static final int DEVICE_TYPE_LAPTOP = 3;
    public static final int DEVICE_TYPE_PHONE = 1;
    public static final int DEVICE_TYPE_TABLET = 2;
    public static final int DEVICE_TYPE_UNKNOWN = 0;
    public static final int DEVICE_TYPE_WATCH = 4;
    private final int mBatteryPercentage;
    private final int mConnectionStrength;
    private final String mDeviceName;
    private final int mDeviceType;
    private final Bundle mExtras;
    private final boolean mIsBatteryCharging;
    private final String mModelName;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeviceType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private int mBatteryPercentage;
        private int mConnectionStrength;
        private String mDeviceName;
        private int mDeviceType;
        private Bundle mExtras = Bundle.EMPTY;
        private boolean mIsBatteryCharging;
        private String mModelName;

        public Builder(String str, String str2) {
            Objects.requireNonNull(str);
            Objects.requireNonNull(str2);
            this.mDeviceName = str;
            this.mModelName = str2;
        }

        public Builder setDeviceType(int i) {
            this.mDeviceType = i;
            return this;
        }

        public Builder setDeviceName(String str) {
            Objects.requireNonNull(str);
            this.mDeviceName = str;
            return this;
        }

        public Builder setModelName(String str) {
            Objects.requireNonNull(str);
            this.mModelName = str;
            return this;
        }

        public Builder setBatteryPercentage(int i) {
            this.mBatteryPercentage = i;
            return this;
        }

        public Builder setBatteryCharging(boolean z) {
            this.mIsBatteryCharging = z;
            return this;
        }

        public Builder setConnectionStrength(int i) {
            this.mConnectionStrength = i;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            Objects.requireNonNull(bundle);
            this.mExtras = bundle;
            return this;
        }

        public NetworkProviderInfo build() {
            return new NetworkProviderInfo(this.mDeviceType, this.mDeviceName, this.mModelName, this.mBatteryPercentage, this.mIsBatteryCharging, this.mConnectionStrength, this.mExtras);
        }
    }

    private static void validate(int i, String str, String str2, int i2, int i3) {
        if (i != 0 && i != 1 && i != 2 && i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException("Illegal device type");
        }
        if (i2 < 0 || i2 > 100) {
            throw new IllegalArgumentException("BatteryPercentage must be in range 0-100");
        }
        if (i3 < 0 || i3 > 4) {
            throw new IllegalArgumentException("ConnectionStrength must be in range 0-4");
        }
    }

    private NetworkProviderInfo(int i, String str, String str2, int i2, boolean z, int i3, Bundle bundle) {
        validate(i, str, str2, i2, i3);
        this.mDeviceType = i;
        this.mDeviceName = str;
        this.mModelName = str2;
        this.mBatteryPercentage = i2;
        this.mIsBatteryCharging = z;
        this.mConnectionStrength = i3;
        this.mExtras = bundle;
    }

    public int getDeviceType() {
        return this.mDeviceType;
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public String getModelName() {
        return this.mModelName;
    }

    public int getBatteryPercentage() {
        return this.mBatteryPercentage;
    }

    public boolean isBatteryCharging() {
        return this.mIsBatteryCharging;
    }

    public int getConnectionStrength() {
        return this.mConnectionStrength;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof NetworkProviderInfo)) {
            return false;
        }
        NetworkProviderInfo networkProviderInfo = (NetworkProviderInfo) obj;
        return this.mDeviceType == networkProviderInfo.getDeviceType() && Objects.equals(this.mDeviceName, networkProviderInfo.mDeviceName) && Objects.equals(this.mModelName, networkProviderInfo.mModelName) && this.mBatteryPercentage == networkProviderInfo.mBatteryPercentage && this.mIsBatteryCharging == networkProviderInfo.mIsBatteryCharging && this.mConnectionStrength == networkProviderInfo.mConnectionStrength;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mDeviceType), this.mDeviceName, this.mModelName, Integer.valueOf(this.mBatteryPercentage), Boolean.valueOf(this.mIsBatteryCharging), Integer.valueOf(this.mConnectionStrength));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mDeviceType);
        parcel.writeString(this.mDeviceName);
        parcel.writeString(this.mModelName);
        parcel.writeInt(this.mBatteryPercentage);
        parcel.writeBoolean(this.mIsBatteryCharging);
        parcel.writeInt(this.mConnectionStrength);
        parcel.writeBundle(this.mExtras);
    }

    public static NetworkProviderInfo readFromParcel(Parcel parcel) {
        return new NetworkProviderInfo(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readBoolean(), parcel.readInt(), parcel.readBundle());
    }

    public String toString() {
        return "NetworkProviderInfo[deviceType=" + this.mDeviceType + ", deviceName=" + this.mDeviceName + ", modelName=" + this.mModelName + ", batteryPercentage=" + this.mBatteryPercentage + ", isBatteryCharging=" + this.mIsBatteryCharging + ", connectionStrength=" + this.mConnectionStrength + ", extras=" + this.mExtras.toString() + NavigationBarInflaterView.SIZE_MOD_END;
    }
}

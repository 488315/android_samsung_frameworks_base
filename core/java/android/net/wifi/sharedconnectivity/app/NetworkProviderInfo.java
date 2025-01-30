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
  public static final Parcelable.Creator<NetworkProviderInfo> CREATOR =
      new Parcelable.Creator<
          NetworkProviderInfo>() { // from class:
                                   // android.net.wifi.sharedconnectivity.app.NetworkProviderInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkProviderInfo createFromParcel(Parcel in) {
          return NetworkProviderInfo.readFromParcel(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkProviderInfo[] newArray(int size) {
          return new NetworkProviderInfo[size];
        }
      };
  public static final int DEVICE_TYPE_AUTO = 5;
  public static final int DEVICE_TYPE_LAPTOP = 3;
  public static final int DEVICE_TYPE_PHONE = 1;
  public static final int DEVICE_TYPE_TABLET = 2;
  public static final int DEVICE_TYPE_UNKNOWN = 0;
  public static final int DEVICE_TYPE_WATCH = 4;
  public static final String EXTRA_KEY_IS_BATTERY_CHARGING = "is_battery_charging";
  private final int mBatteryPercentage;
  private final int mConnectionStrength;
  private final String mDeviceName;
  private final int mDeviceType;
  private final Bundle mExtras;
  private final String mModelName;

  @Retention(RetentionPolicy.SOURCE)
  public @interface DeviceType {}

  public static final class Builder {
    private int mBatteryPercentage;
    private int mConnectionStrength;
    private String mDeviceName;
    private int mDeviceType;
    private Bundle mExtras = Bundle.EMPTY;
    private String mModelName;

    public Builder(String deviceName, String modelName) {
      Objects.requireNonNull(deviceName);
      Objects.requireNonNull(modelName);
      this.mDeviceName = deviceName;
      this.mModelName = modelName;
    }

    public Builder setDeviceType(int deviceType) {
      this.mDeviceType = deviceType;
      return this;
    }

    public Builder setDeviceName(String deviceName) {
      Objects.requireNonNull(deviceName);
      this.mDeviceName = deviceName;
      return this;
    }

    public Builder setModelName(String modelName) {
      Objects.requireNonNull(modelName);
      this.mModelName = modelName;
      return this;
    }

    public Builder setBatteryPercentage(int batteryPercentage) {
      this.mBatteryPercentage = batteryPercentage;
      return this;
    }

    public Builder setConnectionStrength(int connectionStrength) {
      this.mConnectionStrength = connectionStrength;
      return this;
    }

    public Builder setExtras(Bundle extras) {
      Objects.requireNonNull(extras);
      this.mExtras = extras;
      return this;
    }

    public NetworkProviderInfo build() {
      return new NetworkProviderInfo(
          this.mDeviceType,
          this.mDeviceName,
          this.mModelName,
          this.mBatteryPercentage,
          this.mConnectionStrength,
          this.mExtras);
    }
  }

  private static void validate(
      int deviceType,
      String deviceName,
      String modelName,
      int batteryPercentage,
      int connectionStrength) {
    if (deviceType != 0
        && deviceType != 1
        && deviceType != 2
        && deviceType != 3
        && deviceType != 4
        && deviceType != 5) {
      throw new IllegalArgumentException("Illegal device type");
    }
    if (batteryPercentage < 0 || batteryPercentage > 100) {
      throw new IllegalArgumentException("BatteryPercentage must be in range 0-100");
    }
    if (connectionStrength < 0 || connectionStrength > 4) {
      throw new IllegalArgumentException("ConnectionStrength must be in range 0-4");
    }
  }

  private NetworkProviderInfo(
      int deviceType,
      String deviceName,
      String modelName,
      int batteryPercentage,
      int connectionStrength,
      Bundle extras) {
    validate(deviceType, deviceName, modelName, batteryPercentage, connectionStrength);
    this.mDeviceType = deviceType;
    this.mDeviceName = deviceName;
    this.mModelName = modelName;
    this.mBatteryPercentage = batteryPercentage;
    this.mConnectionStrength = connectionStrength;
    this.mExtras = extras;
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
    NetworkProviderInfo other = (NetworkProviderInfo) obj;
    return this.mDeviceType == other.getDeviceType()
        && Objects.equals(this.mDeviceName, other.mDeviceName)
        && Objects.equals(this.mModelName, other.mModelName)
        && this.mBatteryPercentage == other.mBatteryPercentage
        && this.mConnectionStrength == other.mConnectionStrength;
  }

  public int hashCode() {
    return Objects.hash(
        Integer.valueOf(this.mDeviceType),
        this.mDeviceName,
        this.mModelName,
        Integer.valueOf(this.mBatteryPercentage),
        Integer.valueOf(this.mConnectionStrength));
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mDeviceType);
    dest.writeString(this.mDeviceName);
    dest.writeString(this.mModelName);
    dest.writeInt(this.mBatteryPercentage);
    dest.writeInt(this.mConnectionStrength);
    dest.writeBundle(this.mExtras);
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  public static NetworkProviderInfo readFromParcel(Parcel in) {
    return new NetworkProviderInfo(
        in.readInt(),
        in.readString(),
        in.readString(),
        in.readInt(),
        in.readInt(),
        in.readBundle());
  }

  public String toString() {
    return "NetworkProviderInfo[deviceType="
        + this.mDeviceType
        + ", deviceName="
        + this.mDeviceName
        + ", modelName="
        + this.mModelName
        + ", batteryPercentage="
        + this.mBatteryPercentage
        + ", connectionStrength="
        + this.mConnectionStrength
        + ", extras="
        + this.mExtras.toString()
        + NavigationBarInflaterView.SIZE_MOD_END;
  }
}

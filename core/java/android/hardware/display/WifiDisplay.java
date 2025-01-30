package android.hardware.display;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Build;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.text.TextUtils;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class WifiDisplay implements Parcelable {
  public static final int REMOTE_DISPLAY_PAUSED = 7;
  public static final int REMOTE_DISPLAY_RESUMED = 6;
  private String mBluetoothMacAddress;
  private final boolean mCanConnect;
  private final String mDeviceAddress;
  private final String mDeviceAlias;
  private int mDeviceInfo;
  private final String mDeviceName;
  private String mDeviceType;
  private int mFlags;
  private final boolean mIsAvailable;
  private boolean mIsEmptySurface;
  private final boolean mIsRemembered;
  private int mMode;
  private ConcurrentHashMap<String, String> mParameters;
  private int mSamsungDeviceIcon;
  private int mSamsungDeviceType;
  private String mScreenSharingHashedDi;
  private int mState = 6;
  public static final WifiDisplay[] EMPTY_ARRAY = new WifiDisplay[0];
  public static final Parcelable.Creator<WifiDisplay> CREATOR =
      new Parcelable.Creator<WifiDisplay>() { // from class: android.hardware.display.WifiDisplay.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WifiDisplay createFromParcel(Parcel in) {
          String deviceAddress = in.readString();
          String deviceName = in.readString();
          String deviceAlias = in.readString();
          boolean isAvailable = in.readInt() != 0;
          boolean canConnect = in.readInt() != 0;
          boolean isRemembered = in.readInt() != 0;
          String deviceType = in.readString();
          WifiDisplay wifiDisplay =
              new WifiDisplay(
                  deviceAddress,
                  deviceName,
                  deviceAlias,
                  isAvailable,
                  canConnect,
                  isRemembered,
                  deviceType);
          String btMac = in.readString();
          wifiDisplay.setBluetoothMacAddress(btMac);
          wifiDisplay.setScreenSharingHashedDi(in.readString());
          wifiDisplay.setSamsungDeviceType(in.readInt());
          wifiDisplay.setSamsungDeviceIcon(in.readInt());
          boolean isEmptySurface = in.readInt() != 0;
          wifiDisplay.setEmptySurface(isEmptySurface);
          wifiDisplay.setFlags(in.readInt());
          wifiDisplay.setMode(in.readInt());
          wifiDisplay.setDeviceInfo(in.readInt());
          int parameterMapSize = in.readInt();
          for (int i = 0; i < parameterMapSize; i++) {
            String key = in.readString();
            String value = (String) in.readValue(String.class.getClassLoader());
            wifiDisplay.addParameter(key, value);
          }
          return wifiDisplay;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WifiDisplay[] newArray(int size) {
          return size == 0 ? WifiDisplay.EMPTY_ARRAY : new WifiDisplay[size];
        }
      };

  public WifiDisplay(
      String deviceAddress,
      String deviceName,
      String deviceAlias,
      boolean available,
      boolean canConnect,
      boolean remembered,
      String deviceType) {
    if (deviceAddress == null) {
      throw new IllegalArgumentException("deviceAddress must not be null");
    }
    if (deviceName == null) {
      throw new IllegalArgumentException("deviceName must not be null");
    }
    this.mDeviceAddress = deviceAddress;
    this.mDeviceName = deviceName;
    this.mDeviceAlias = deviceAlias;
    this.mIsAvailable = available;
    this.mCanConnect = canConnect;
    this.mIsRemembered = remembered;
    this.mDeviceType = deviceType;
    this.mMode = 0;
    this.mDeviceInfo = 0;
    this.mParameters = new ConcurrentHashMap<>();
  }

  public String getDeviceAddress() {
    return this.mDeviceAddress;
  }

  public String getDeviceName() {
    return this.mDeviceName;
  }

  public String getDeviceAlias() {
    return this.mDeviceAlias;
  }

  public boolean isAvailable() {
    return this.mIsAvailable;
  }

  public boolean canConnect() {
    return this.mCanConnect;
  }

  public boolean isRemembered() {
    return this.mIsRemembered;
  }

  public String getFriendlyDisplayName() {
    String str = this.mDeviceAlias;
    return str != null ? str : this.mDeviceName;
  }

  public String getPrimaryDeviceType() {
    return this.mDeviceType;
  }

  public int getSamsungDeviceType() {
    return this.mSamsungDeviceType;
  }

  public int getSamsungDeviceIcon() {
    return this.mSamsungDeviceIcon;
  }

  public String getBluetoothMacAddress() {
    return this.mBluetoothMacAddress;
  }

  public String getScreenSharingHashedDi() {
    return this.mScreenSharingHashedDi;
  }

  public int getFlags() {
    return this.mFlags;
  }

  public int getMode() {
    return this.mMode;
  }

  public int getState() {
    return this.mState;
  }

  public String getViewMode() {
    String value = this.mParameters.get(SemWifiDisplayParameter.KEY_VIEW_MODE);
    if (value == null || value.isEmpty()) {
      return "none";
    }
    return value;
  }

  public boolean isSupport(String feature) {
    String value = this.mParameters.get(feature);
    if (value == null || value.isEmpty()) {
      return false;
    }
    return SemWifiDisplayParameter.VALUE_SUPPORT.equals(value);
  }

  public boolean isVoipModeSupported() {
    String value = this.mParameters.get(SemWifiDisplayParameter.KEY_VOIP_MODE);
    if (value == null || value.isEmpty()) {
      return false;
    }
    return SemWifiDisplayParameter.VALUE_SUPPORT.equals(value);
  }

  public boolean isHighResolutionModeSupported() {
    String value = this.mParameters.get("high_resolution_mode");
    if (value == null || value.isEmpty()) {
      return false;
    }
    return SemWifiDisplayParameter.VALUE_SUPPORT.equals(value);
  }

  public boolean isDmrSupported() {
    String dmrSupport = this.mParameters.get(SemWifiDisplayParameter.KEY_DMR_SUPPORT);
    if (TextUtils.isEmpty(dmrSupport)) {
      return false;
    }
    return "enable".equals(dmrSupport);
  }

  public boolean isDmrSupportedType(int type) {
    String dmrMetaCheck;
    if (!isDmrSupported()) {
      return false;
    }
    if (type == 0) {
      return Build.VERSION.SEM_PLATFORM_INT < 150100
          || !((dmrMetaCheck = this.mParameters.get(SemWifiDisplayParameter.KEY_DMR_META_CHECK))
                  == null
              || dmrMetaCheck.equals("none"));
    }
    String value = this.mParameters.get(SemWifiDisplayParameter.KEY_DMR_SUPPORT_TYPE);
    if (TextUtils.isEmpty(value)) {
      return false;
    }
    if (type == 1) {
      return value.contains("image");
    }
    if (type == 4) {
      return value.contains(
          SemWifiDisplayParameter.VALUE_DMR_SUPPORT_TYPE_VIDEO_HEVC_SUPER_SLOW_MOTION);
    }
    return false;
  }

  public boolean isEmptySurface() {
    return this.mIsEmptySurface;
  }

  public void setSamsungDeviceType(int samsungDeviceType) {
    this.mSamsungDeviceType = samsungDeviceType;
  }

  public void setSamsungDeviceIcon(int samsungDeviceIcon) {
    this.mSamsungDeviceIcon = samsungDeviceIcon;
  }

  public void setBluetoothMacAddress(String bluetoothMacAddress) {
    this.mBluetoothMacAddress = bluetoothMacAddress;
  }

  public void setScreenSharingHashedDi(String screenSharingHashedDi) {
    this.mScreenSharingHashedDi = screenSharingHashedDi;
  }

  public void setEmptySurface(boolean isEmptySurface) {
    this.mIsEmptySurface = isEmptySurface;
  }

  public void setFlags(int flags) {
    this.mFlags = flags;
  }

  public void setMode(int mode) {
    this.mMode = mode;
  }

  public void setState(int state) {
    this.mState = state;
  }

  public void addParameter(String key, String value) {
    this.mParameters.put(key, value);
  }

  public ConcurrentHashMap<String, String> getParameters() {
    return this.mParameters;
  }

  public int getDeviceInfo() {
    return this.mDeviceInfo;
  }

  public void setDeviceInfo(int devInfo) {
    this.mDeviceInfo = devInfo;
  }

  public boolean equals(Object o) {
    return (o instanceof WifiDisplay) && equals((WifiDisplay) o);
  }

  public boolean equals(WifiDisplay other) {
    return other != null
        && this.mDeviceAddress.equals(other.mDeviceAddress)
        && this.mDeviceName.equals(other.mDeviceName)
        && Objects.equals(this.mDeviceAlias, other.mDeviceAlias);
  }

  public boolean hasSameAddress(WifiDisplay other) {
    return other != null && this.mDeviceAddress.equals(other.mDeviceAddress);
  }

  public int hashCode() {
    return this.mDeviceAddress.hashCode();
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(this.mDeviceAddress);
    parcel.writeString(this.mDeviceName);
    parcel.writeString(this.mDeviceAlias);
    parcel.writeInt(this.mIsAvailable ? 1 : 0);
    parcel.writeInt(this.mCanConnect ? 1 : 0);
    parcel.writeInt(this.mIsRemembered ? 1 : 0);
    parcel.writeString(this.mDeviceType);
    parcel.writeString(this.mBluetoothMacAddress);
    parcel.writeString(this.mScreenSharingHashedDi);
    parcel.writeInt(this.mSamsungDeviceType);
    parcel.writeInt(this.mSamsungDeviceIcon);
    parcel.writeInt(this.mIsEmptySurface ? 1 : 0);
    parcel.writeInt(this.mFlags);
    parcel.writeInt(this.mMode);
    parcel.writeInt(this.mDeviceInfo);
    ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(this.mParameters);
    parcel.writeInt(concurrentHashMap.size());
    for (Map.Entry entry : concurrentHashMap.entrySet()) {
      parcel.writeString((String) entry.getKey());
      parcel.writeValue(entry.getValue());
    }
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  public String toString() {
    String result =
        this.mDeviceName + " (" + this.mDeviceAddress + NavigationBarInflaterView.KEY_CODE_END;
    if (this.mDeviceAlias != null) {
      result = result + ", alias " + this.mDeviceAlias;
    }
    return result
        + ", isAvailable "
        + this.mIsAvailable
        + ", canConnect "
        + this.mCanConnect
        + ", isRemembered "
        + this.mIsRemembered
        + ", deviceType "
        + this.mDeviceType
        + ", samsungDeviceType "
        + this.mSamsungDeviceType
        + ", samsungDeviceIcon "
        + this.mSamsungDeviceIcon
        + ", isEmptySurface "
        + this.mIsEmptySurface
        + ", flags "
        + this.mFlags
        + ", mode "
        + this.mMode
        + ", DeviceInfo "
        + this.mDeviceInfo
        + ", paramters "
        + this.mParameters.toString();
  }
}

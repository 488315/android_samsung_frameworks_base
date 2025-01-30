package com.samsung.android.wifi;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.text.format.DateFormat;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/* loaded from: classes6.dex */
public class SemWifiApBleScanResult implements Parcelable {
  public boolean isDataSaverEnabled;
  public boolean isMobileDataLimitReached;
  public boolean isNotValidNetwork;
  public boolean isWifiProfileShareEnabled;
  public int mBLERssi;
  public int mBattery;
  public String mDevice;
  public int mMHSdeviceType;
  public int mNetworkSignalStrength;
  public int mNetworkType;
  public String mSSID;
  public int mSecurity;
  public long mTimeStamp;
  public String mUserName;
  public String mWifiMac;
  public int mhidden;
  public int version;
  public static int MHS_WIFI_6_NETWORK = 16;
  public static int MHS_WIFI_6E_NETWORK = 32;
  public static final Parcelable.Creator<SemWifiApBleScanResult> CREATOR =
      new Parcelable.Creator<
          SemWifiApBleScanResult>() { // from class:
                                      // com.samsung.android.wifi.SemWifiApBleScanResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiApBleScanResult createFromParcel(Parcel source) {
          return new SemWifiApBleScanResult(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiApBleScanResult[] newArray(int size) {
          return new SemWifiApBleScanResult[size];
        }
      };

  public SemWifiApBleScanResult(
      String device,
      int tMHSdeviceType,
      int tBattery,
      int tNetworkType,
      int tNetworkSignalStrength,
      String tWifiMAC,
      String tUserName,
      String tSSID,
      int thidden,
      int tSecurity,
      long tTimeStamp,
      int tBLERssi,
      int ver,
      boolean dataSaverEnabled,
      boolean isWifiProfileShareEnabled,
      boolean isMobileDataLimitReached) {
    this.mDevice = device;
    this.mBattery = tBattery;
    this.mMHSdeviceType = tMHSdeviceType;
    this.mNetworkType = tNetworkType;
    this.mNetworkSignalStrength = tNetworkSignalStrength;
    this.mWifiMac = tWifiMAC;
    this.mUserName = tUserName;
    this.mSSID = tSSID;
    this.mhidden = thidden;
    this.mSecurity = tSecurity;
    this.mTimeStamp = tTimeStamp;
    this.mBLERssi = tBLERssi;
    this.version = ver;
    this.isDataSaverEnabled = dataSaverEnabled;
    this.isWifiProfileShareEnabled = isWifiProfileShareEnabled;
    this.isMobileDataLimitReached = isMobileDataLimitReached;
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    if (this.mDevice != null) {
      dest.writeInt(1);
      dest.writeByteArray(this.mDevice.getBytes());
    } else {
      dest.writeInt(0);
    }
    dest.writeInt(this.mBattery);
    dest.writeInt(this.mMHSdeviceType);
    dest.writeInt(this.mNetworkType);
    dest.writeInt(this.mNetworkSignalStrength);
    if (this.mWifiMac != null) {
      dest.writeInt(1);
      dest.writeByteArray(this.mWifiMac.getBytes());
    } else {
      dest.writeInt(0);
    }
    if (this.mUserName != null) {
      dest.writeInt(1);
      dest.writeByteArray(this.mUserName.getBytes());
    } else {
      dest.writeInt(0);
    }
    if (this.mSSID != null) {
      dest.writeInt(1);
      dest.writeByteArray(this.mSSID.getBytes());
    } else {
      dest.writeInt(0);
    }
    dest.writeInt(this.mhidden);
    dest.writeInt(this.mSecurity);
    dest.writeLong(this.mTimeStamp);
    dest.writeInt(this.mBLERssi);
    dest.writeInt(this.version);
    dest.writeBoolean(this.isDataSaverEnabled);
    dest.writeBoolean(this.isWifiProfileShareEnabled);
    dest.writeBoolean(this.isMobileDataLimitReached);
    dest.writeBoolean(this.isNotValidNetwork);
  }

  private SemWifiApBleScanResult(Parcel in) {
    readFromParcel(in);
  }

  private void readFromParcel(Parcel in) {
    if (in.readInt() == 1) {
      this.mDevice = new String(in.createByteArray(), StandardCharsets.UTF_8);
    }
    this.mBattery = in.readInt();
    this.mMHSdeviceType = in.readInt();
    this.mNetworkType = in.readInt();
    this.mNetworkSignalStrength = in.readInt();
    if (in.readInt() == 1) {
      this.mWifiMac = new String(in.createByteArray(), StandardCharsets.UTF_8);
    }
    if (in.readInt() == 1) {
      this.mUserName = new String(in.createByteArray(), StandardCharsets.UTF_8);
    }
    if (in.readInt() == 1) {
      this.mSSID = new String(in.createByteArray(), StandardCharsets.UTF_8);
    }
    this.mhidden = in.readInt();
    this.mSecurity = in.readInt();
    this.mTimeStamp = in.readLong();
    this.mBLERssi = in.readInt();
    this.version = in.readInt();
    this.isDataSaverEnabled = in.readBoolean();
    this.isWifiProfileShareEnabled = in.readBoolean();
    this.isMobileDataLimitReached = in.readBoolean();
    this.isNotValidNetwork = in.readBoolean();
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    SemWifiApBleScanResult other = (SemWifiApBleScanResult) obj;
    return Objects.equals(this.mWifiMac, other.mWifiMac);
  }

  public String toString() {
    return "SemWifiApBleScanResult{mMHSdeviceType="
        + this.mMHSdeviceType
        + ", mDevice='"
        + this.mDevice
        + DateFormat.QUOTE
        + ", mBattery="
        + this.mBattery
        + ", mNetworkType="
        + this.mNetworkType
        + ", mNetworkSignalStrength="
        + this.mNetworkSignalStrength
        + ", mWifiMac='"
        + this.mWifiMac
        + DateFormat.QUOTE
        + ", mUserName='"
        + this.mUserName
        + DateFormat.QUOTE
        + ", mSSID='"
        + this.mSSID
        + DateFormat.QUOTE
        + ", mhidden="
        + this.mhidden
        + ", mSecurity="
        + this.mSecurity
        + ", mTimeStamp="
        + this.mTimeStamp
        + ", mBLERssi="
        + this.mBLERssi
        + ", version="
        + this.version
        + ", isDataSaverEnabled="
        + this.isDataSaverEnabled
        + ", isWifiProfileShareEnabled="
        + this.isWifiProfileShareEnabled
        + ", isMobileDataLimitReached="
        + this.isMobileDataLimitReached
        + ", isNotValidNetwork="
        + this.isNotValidNetwork
        + '}';
  }
}

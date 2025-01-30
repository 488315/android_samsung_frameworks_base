package android.net.wifi.nl80211;

import android.annotation.SystemApi;
import android.net.MacAddress;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public final class NativeScanResult implements Parcelable {
  public static final int BSS_CAPABILITY_APSD = 2048;
  public static final int BSS_CAPABILITY_CF_POLLABLE = 4;
  public static final int BSS_CAPABILITY_CF_POLL_REQUEST = 8;
  public static final int BSS_CAPABILITY_CHANNEL_AGILITY = 128;
  public static final int BSS_CAPABILITY_DELAYED_BLOCK_ACK = 16384;
  public static final int BSS_CAPABILITY_DMG_ESS = 3;
  public static final int BSS_CAPABILITY_DMG_IBSS = 1;
  public static final int BSS_CAPABILITY_DSSS_OFDM = 8192;
  public static final int BSS_CAPABILITY_ESS = 1;
  public static final int BSS_CAPABILITY_IBSS = 2;
  public static final int BSS_CAPABILITY_IMMEDIATE_BLOCK_ACK = 32768;
  public static final int BSS_CAPABILITY_PBCC = 64;
  public static final int BSS_CAPABILITY_PRIVACY = 16;
  public static final int BSS_CAPABILITY_QOS = 512;
  public static final int BSS_CAPABILITY_RADIO_MANAGEMENT = 4096;
  public static final int BSS_CAPABILITY_SHORT_PREAMBLE = 32;
  public static final int BSS_CAPABILITY_SHORT_SLOT_TIME = 1024;
  public static final int BSS_CAPABILITY_SPECTRUM_MANAGEMENT = 256;
  public static final Parcelable.Creator<NativeScanResult> CREATOR =
      new Parcelable.Creator<
          NativeScanResult>() { // from class: android.net.wifi.nl80211.NativeScanResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NativeScanResult createFromParcel(Parcel in) {
          NativeScanResult result = new NativeScanResult();
          result.ssid = in.createByteArray();
          if (result.ssid == null) {
            result.ssid = new byte[0];
          }
          result.bssid = in.createByteArray();
          if (result.bssid == null) {
            result.bssid = new byte[0];
          }
          result.infoElement = in.createByteArray();
          if (result.infoElement == null) {
            result.infoElement = new byte[0];
          }
          result.frequency = in.readInt();
          result.signalMbm = in.readInt();
          result.tsf = in.readLong();
          result.capability = in.readInt();
          result.associated = in.readInt() != 0;
          result.radioChainInfos = new ArrayList();
          in.readTypedList(result.radioChainInfos, RadioChainInfo.CREATOR);
          return result;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NativeScanResult[] newArray(int size) {
          return new NativeScanResult[size];
        }
      };
  private static final String TAG = "NativeScanResult";
  public boolean associated;
  public byte[] bssid;
  public int capability;
  public int frequency;
  public byte[] infoElement;
  public List<RadioChainInfo> radioChainInfos;
  public int signalMbm;
  public byte[] ssid;
  public long tsf;

  @Retention(RetentionPolicy.SOURCE)
  public @interface BssCapabilityBits {}

  public byte[] getSsid() {
    return this.ssid;
  }

  public MacAddress getBssid() {
    try {
      return MacAddress.fromBytes(this.bssid);
    } catch (IllegalArgumentException e) {
      Log.m97e(TAG, "Illegal argument " + Arrays.toString(this.bssid), e);
      return null;
    }
  }

  public byte[] getInformationElements() {
    return this.infoElement;
  }

  public int getFrequencyMhz() {
    return this.frequency;
  }

  public int getSignalMbm() {
    return this.signalMbm;
  }

  public long getTsf() {
    return this.tsf;
  }

  public boolean isAssociated() {
    return this.associated;
  }

  public int getCapabilities() {
    return this.capability;
  }

  public List<RadioChainInfo> getRadioChainInfos() {
    return this.radioChainInfos;
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeByteArray(this.ssid);
    parcel.writeByteArray(this.bssid);
    parcel.writeByteArray(this.infoElement);
    parcel.writeInt(this.frequency);
    parcel.writeInt(this.signalMbm);
    parcel.writeLong(this.tsf);
    parcel.writeInt(this.capability);
    parcel.writeInt(this.associated ? 1 : 0);
    parcel.writeTypedList(this.radioChainInfos);
  }
}

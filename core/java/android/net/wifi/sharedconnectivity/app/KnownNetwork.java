package android.net.wifi.sharedconnectivity.app;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArraySet;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.Set;

@SystemApi
/* loaded from: classes3.dex */
public final class KnownNetwork implements Parcelable {
  public static final Parcelable.Creator<KnownNetwork> CREATOR =
      new Parcelable.Creator<
          KnownNetwork>() { // from class: android.net.wifi.sharedconnectivity.app.KnownNetwork.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KnownNetwork createFromParcel(Parcel in) {
          return KnownNetwork.readFromParcel(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KnownNetwork[] newArray(int size) {
          return new KnownNetwork[size];
        }
      };
  public static final int NETWORK_SOURCE_CLOUD_SELF = 2;
  public static final int NETWORK_SOURCE_NEARBY_SELF = 1;
  public static final int NETWORK_SOURCE_UNKNOWN = 0;
  private final Bundle mExtras;
  private final NetworkProviderInfo mNetworkProviderInfo;
  private final int mNetworkSource;
  private final ArraySet<Integer> mSecurityTypes;
  private final String mSsid;

  @Retention(RetentionPolicy.SOURCE)
  public @interface NetworkSource {}

  public static final class Builder {
    private NetworkProviderInfo mNetworkProviderInfo;
    private String mSsid;
    private int mNetworkSource = -1;
    private final ArraySet<Integer> mSecurityTypes = new ArraySet<>();
    private Bundle mExtras = Bundle.EMPTY;

    public Builder setNetworkSource(int networkSource) {
      this.mNetworkSource = networkSource;
      return this;
    }

    public Builder setSsid(String ssid) {
      this.mSsid = ssid;
      return this;
    }

    public Builder addSecurityType(int securityType) {
      this.mSecurityTypes.add(Integer.valueOf(securityType));
      return this;
    }

    public Builder setNetworkProviderInfo(NetworkProviderInfo networkProviderInfo) {
      this.mNetworkProviderInfo = networkProviderInfo;
      return this;
    }

    public Builder setExtras(Bundle extras) {
      this.mExtras = extras;
      return this;
    }

    public KnownNetwork build() {
      return new KnownNetwork(
          this.mNetworkSource,
          this.mSsid,
          this.mSecurityTypes,
          this.mNetworkProviderInfo,
          this.mExtras);
    }
  }

  private static void validate(
      int networkSource,
      String ssid,
      Set<Integer> securityTypes,
      NetworkProviderInfo networkProviderInfo) {
    if (networkSource != 0 && networkSource != 2 && networkSource != 1) {
      throw new IllegalArgumentException("Illegal network source");
    }
    if (TextUtils.isEmpty(ssid)) {
      throw new IllegalArgumentException("SSID must be set");
    }
    if (securityTypes.isEmpty()) {
      throw new IllegalArgumentException("SecurityTypes must be set");
    }
    if (networkSource == 1 && networkProviderInfo == null) {
      throw new IllegalArgumentException(
          "Device info must be provided when network source is NETWORK_SOURCE_NEARBY_SELF");
    }
  }

  private KnownNetwork(
      int networkSource,
      String ssid,
      ArraySet<Integer> securityTypes,
      NetworkProviderInfo networkProviderInfo,
      Bundle extras) {
    validate(networkSource, ssid, securityTypes, networkProviderInfo);
    this.mNetworkSource = networkSource;
    this.mSsid = ssid;
    this.mSecurityTypes = new ArraySet<>((ArraySet) securityTypes);
    this.mNetworkProviderInfo = networkProviderInfo;
    this.mExtras = extras;
  }

  public int getNetworkSource() {
    return this.mNetworkSource;
  }

  public String getSsid() {
    return this.mSsid;
  }

  public Set<Integer> getSecurityTypes() {
    return this.mSecurityTypes;
  }

  public NetworkProviderInfo getNetworkProviderInfo() {
    return this.mNetworkProviderInfo;
  }

  public Bundle getExtras() {
    return this.mExtras;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof KnownNetwork)) {
      return false;
    }
    KnownNetwork other = (KnownNetwork) obj;
    return this.mNetworkSource == other.getNetworkSource()
        && Objects.equals(this.mSsid, other.getSsid())
        && Objects.equals(this.mSecurityTypes, other.getSecurityTypes())
        && Objects.equals(this.mNetworkProviderInfo, other.getNetworkProviderInfo());
  }

  public int hashCode() {
    return Objects.hash(
        Integer.valueOf(this.mNetworkSource),
        this.mSsid,
        this.mSecurityTypes,
        this.mNetworkProviderInfo);
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mNetworkSource);
    dest.writeString(this.mSsid);
    dest.writeArraySet(this.mSecurityTypes);
    if (this.mNetworkProviderInfo != null) {
      dest.writeBoolean(true);
      this.mNetworkProviderInfo.writeToParcel(dest, flags);
    } else {
      dest.writeBoolean(false);
    }
    dest.writeBundle(this.mExtras);
  }

  public static KnownNetwork readFromParcel(Parcel in) {
    int networkSource = in.readInt();
    String mSsid = in.readString();
    ArraySet<? extends Object> readArraySet = in.readArraySet(null);
    if (in.readBoolean()) {
      return new KnownNetwork(
          networkSource,
          mSsid,
          readArraySet,
          NetworkProviderInfo.readFromParcel(in),
          in.readBundle());
    }
    return new KnownNetwork(networkSource, mSsid, readArraySet, null, in.readBundle());
  }

  public String toString() {
    return "KnownNetwork[NetworkSource="
        + this.mNetworkSource
        + ", ssid="
        + this.mSsid
        + ", securityTypes="
        + this.mSecurityTypes.toString()
        + ", networkProviderInfo="
        + this.mNetworkProviderInfo.toString()
        + ", extras="
        + this.mExtras.toString()
        + NavigationBarInflaterView.SIZE_MOD_END;
  }
}

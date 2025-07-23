package android.net.wifi.sharedconnectivity.app;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.Set;

@SystemApi
/* loaded from: classes3.dex */
public final class HotspotNetwork implements Parcelable {
    public static final Parcelable.Creator<HotspotNetwork> CREATOR = new Parcelable.Creator<HotspotNetwork>() { // from class: android.net.wifi.sharedconnectivity.app.HotspotNetwork.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HotspotNetwork createFromParcel(Parcel parcel) {
            return HotspotNetwork.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HotspotNetwork[] newArray(int i) {
            return new HotspotNetwork[i];
        }
    };
    public static final int NETWORK_TYPE_CELLULAR = 1;
    public static final int NETWORK_TYPE_ETHERNET = 3;
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    public static final int NETWORK_TYPE_WIFI = 2;
    private final long mDeviceId;
    private final Bundle mExtras;
    private final String mHotspotBssid;
    private final ArraySet<Integer> mHotspotSecurityTypes;
    private final String mHotspotSsid;
    private final String mNetworkName;
    private final NetworkProviderInfo mNetworkProviderInfo;
    private final int mNetworkType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface NetworkType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private String mHotspotBssid;
        private String mHotspotSsid;
        private String mNetworkName;
        private NetworkProviderInfo mNetworkProviderInfo;
        private int mNetworkType;
        private long mDeviceId = -1;
        private final ArraySet<Integer> mHotspotSecurityTypes = new ArraySet<>();
        private Bundle mExtras = Bundle.EMPTY;

        public Builder setDeviceId(long j) {
            this.mDeviceId = j;
            return this;
        }

        public Builder setNetworkProviderInfo(NetworkProviderInfo networkProviderInfo) {
            this.mNetworkProviderInfo = networkProviderInfo;
            return this;
        }

        public Builder setHostNetworkType(int i) {
            this.mNetworkType = i;
            return this;
        }

        public Builder setNetworkName(String str) {
            this.mNetworkName = str;
            return this;
        }

        public Builder setHotspotSsid(String str) {
            this.mHotspotSsid = str;
            return this;
        }

        public Builder setHotspotBssid(String str) {
            this.mHotspotBssid = str;
            return this;
        }

        public Builder addHotspotSecurityType(int i) {
            this.mHotspotSecurityTypes.add(Integer.valueOf(i));
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public HotspotNetwork build() {
            return new HotspotNetwork(this.mDeviceId, this.mNetworkProviderInfo, this.mNetworkType, this.mNetworkName, this.mHotspotSsid, this.mHotspotBssid, this.mHotspotSecurityTypes, this.mExtras);
        }
    }

    private static void validate(long j, int i, String str, NetworkProviderInfo networkProviderInfo) {
        if (j < 0) {
            throw new IllegalArgumentException("DeviceId must be set");
        }
        Objects.isNull(networkProviderInfo);
        if (networkProviderInfo == null) {
            throw new IllegalArgumentException("NetworkProviderInfo must be set");
        }
        if (i != 1 && i != 2 && i != 3 && i != 0) {
            throw new IllegalArgumentException("Illegal network type");
        }
        Objects.isNull(str);
        if (str == null) {
            throw new IllegalArgumentException("NetworkName must be set");
        }
    }

    private HotspotNetwork(long j, NetworkProviderInfo networkProviderInfo, int i, String str, String str2, String str3, ArraySet<Integer> arraySet, Bundle bundle) {
        validate(j, i, str, networkProviderInfo);
        this.mDeviceId = j;
        this.mNetworkProviderInfo = networkProviderInfo;
        this.mNetworkType = i;
        this.mNetworkName = str;
        this.mHotspotSsid = str2;
        this.mHotspotBssid = str3;
        this.mHotspotSecurityTypes = new ArraySet<>((ArraySet) arraySet);
        this.mExtras = bundle;
    }

    public long getDeviceId() {
        return this.mDeviceId;
    }

    public NetworkProviderInfo getNetworkProviderInfo() {
        return this.mNetworkProviderInfo;
    }

    public int getHostNetworkType() {
        return this.mNetworkType;
    }

    public String getNetworkName() {
        return this.mNetworkName;
    }

    public String getHotspotSsid() {
        return this.mHotspotSsid;
    }

    public String getHotspotBssid() {
        return this.mHotspotBssid;
    }

    public Set<Integer> getHotspotSecurityTypes() {
        return this.mHotspotSecurityTypes;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof HotspotNetwork)) {
            return false;
        }
        HotspotNetwork hotspotNetwork = (HotspotNetwork) obj;
        return this.mDeviceId == hotspotNetwork.getDeviceId() && Objects.equals(this.mNetworkProviderInfo, hotspotNetwork.getNetworkProviderInfo()) && this.mNetworkType == hotspotNetwork.getHostNetworkType() && Objects.equals(this.mNetworkName, hotspotNetwork.getNetworkName()) && Objects.equals(this.mHotspotSsid, hotspotNetwork.getHotspotSsid()) && Objects.equals(this.mHotspotBssid, hotspotNetwork.getHotspotBssid()) && Objects.equals(this.mHotspotSecurityTypes, hotspotNetwork.getHotspotSecurityTypes());
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.mDeviceId), this.mNetworkProviderInfo, this.mNetworkName, this.mHotspotSsid, this.mHotspotBssid, this.mHotspotSecurityTypes);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mDeviceId);
        this.mNetworkProviderInfo.writeToParcel(parcel, i);
        parcel.writeInt(this.mNetworkType);
        parcel.writeString(this.mNetworkName);
        parcel.writeString(this.mHotspotSsid);
        parcel.writeString(this.mHotspotBssid);
        parcel.writeArraySet(this.mHotspotSecurityTypes);
        parcel.writeBundle(this.mExtras);
    }

    public static HotspotNetwork readFromParcel(Parcel parcel) {
        return new HotspotNetwork(parcel.readLong(), NetworkProviderInfo.readFromParcel(parcel), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readArraySet(null), parcel.readBundle());
    }

    public String toString() {
        return "HotspotNetwork[deviceId=" + this.mDeviceId + ", networkType=" + this.mNetworkType + ", networkProviderInfo=" + this.mNetworkProviderInfo.toString() + ", networkName=" + this.mNetworkName + ", hotspotSsid=" + this.mHotspotSsid + ", hotspotBssid=" + this.mHotspotBssid + ", hotspotSecurityTypes=" + this.mHotspotSecurityTypes.toString() + ", extras=" + this.mExtras.toString() + NavigationBarInflaterView.SIZE_MOD_END;
    }
}

package android.net.wifi.nl80211;

import android.annotation.SystemApi;
import android.net.MacAddress;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class NativeWifiClient implements Parcelable {
    public static final Parcelable.Creator<NativeWifiClient> CREATOR = new Parcelable.Creator<NativeWifiClient>() { // from class: android.net.wifi.nl80211.NativeWifiClient.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NativeWifiClient createFromParcel(Parcel parcel) {
            MacAddress macAddressFromBytes;
            try {
                macAddressFromBytes = MacAddress.fromBytes(parcel.createByteArray());
            } catch (IllegalArgumentException unused) {
                macAddressFromBytes = null;
            }
            return new NativeWifiClient(macAddressFromBytes);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NativeWifiClient[] newArray(int i) {
            return new NativeWifiClient[i];
        }
    };
    private final MacAddress mMacAddress;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MacAddress getMacAddress() {
        return this.mMacAddress;
    }

    public NativeWifiClient(MacAddress macAddress) {
        this.mMacAddress = macAddress;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof NativeWifiClient) {
            return Objects.equals(this.mMacAddress, ((NativeWifiClient) obj).mMacAddress);
        }
        return false;
    }

    public int hashCode() {
        return this.mMacAddress.hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByteArray(this.mMacAddress.toByteArray());
    }
}

package android.net.wifi.nl80211;

import android.annotation.SystemApi;
import android.net.MacAddress;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class NativeWifiClient implements Parcelable {
    public static final Parcelable.Creator<NativeWifiClient> CREATOR =
            new Parcelable.Creator<
                    NativeWifiClient>() { // from class: android.net.wifi.nl80211.NativeWifiClient.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public NativeWifiClient createFromParcel(Parcel in) {
                    MacAddress macAddress;
                    try {
                        macAddress = MacAddress.fromBytes(in.createByteArray());
                    } catch (IllegalArgumentException e) {
                        macAddress = null;
                    }
                    return new NativeWifiClient(macAddress);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public NativeWifiClient[] newArray(int size) {
                    return new NativeWifiClient[size];
                }
            };
    private final MacAddress mMacAddress;

    public MacAddress getMacAddress() {
        return this.mMacAddress;
    }

    public NativeWifiClient(MacAddress macAddress) {
        this.mMacAddress = macAddress;
    }

    public boolean equals(Object rhs) {
        if (this == rhs) {
            return true;
        }
        if (!(rhs instanceof NativeWifiClient)) {
            return false;
        }
        NativeWifiClient other = (NativeWifiClient) rhs;
        return Objects.equals(this.mMacAddress, other.mMacAddress);
    }

    public int hashCode() {
        return this.mMacAddress.hashCode();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeByteArray(this.mMacAddress.toByteArray());
    }
}

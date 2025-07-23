package android.net;

import android.annotation.SystemApi;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
@Deprecated
/* loaded from: classes3.dex */
public class NetworkKey implements Parcelable {
    public static final Parcelable.Creator<NetworkKey> CREATOR = new Parcelable.Creator<NetworkKey>() { // from class: android.net.NetworkKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkKey createFromParcel(Parcel parcel) {
            return new NetworkKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkKey[] newArray(int i) {
            return new NetworkKey[i];
        }
    };
    private static final String TAG = "NetworkKey";
    public static final int TYPE_WIFI = 1;
    public final int type;
    public final WifiKey wifiKey;

    @Retention(RetentionPolicy.SOURCE)
    public @interface NetworkType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static NetworkKey createFromScanResult(ScanResult scanResult) {
        Objects.requireNonNull(scanResult);
        String str = scanResult.SSID;
        if (!TextUtils.isEmpty(str) && !str.equals("<unknown ssid>")) {
            String str2 = scanResult.BSSID;
            if (TextUtils.isEmpty(str2)) {
                return null;
            }
            try {
                return new NetworkKey(new WifiKey(String.format("\"%s\"", str), str2));
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "Unable to create WifiKey.", e);
            }
        }
        return null;
    }

    public static NetworkKey createFromWifiInfo(WifiInfo wifiInfo) {
        if (wifiInfo != null) {
            String ssid = wifiInfo.getSSID();
            String bssid = wifiInfo.getBSSID();
            if (!TextUtils.isEmpty(ssid) && !ssid.equals("<unknown ssid>") && !TextUtils.isEmpty(bssid)) {
                try {
                    return new NetworkKey(new WifiKey(ssid, bssid));
                } catch (IllegalArgumentException e) {
                    Log.e(TAG, "Unable to create WifiKey.", e);
                }
            }
        }
        return null;
    }

    public NetworkKey(WifiKey wifiKey) {
        this.type = 1;
        this.wifiKey = wifiKey;
    }

    private NetworkKey(Parcel parcel) {
        int readInt = parcel.readInt();
        this.type = readInt;
        if (readInt == 1) {
            this.wifiKey = WifiKey.CREATOR.createFromParcel(parcel);
        } else {
            throw new IllegalArgumentException("Parcel has unknown type: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.type);
        if (this.type == 1) {
            this.wifiKey.writeToParcel(parcel, i);
        } else {
            throw new IllegalStateException("NetworkKey has unknown type " + this.type);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            NetworkKey networkKey = (NetworkKey) obj;
            if (this.type == networkKey.type && Objects.equals(this.wifiKey, networkKey.wifiKey)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.type), this.wifiKey);
    }

    public String toString() {
        if (this.type == 1) {
            return this.wifiKey.toString();
        }
        return "InvalidKey";
    }
}

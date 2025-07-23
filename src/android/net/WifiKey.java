package android.net;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.regex.Pattern;

@SystemApi
@Deprecated
/* loaded from: classes3.dex */
public class WifiKey implements Parcelable {
    public final String bssid;
    public final String ssid;
    private static final Pattern SSID_PATTERN = Pattern.compile("(\".*\")|(0x[\\p{XDigit}]+)", 32);
    private static final Pattern BSSID_PATTERN = Pattern.compile("([\\p{XDigit}]{2}:){5}[\\p{XDigit}]{2}");
    public static final Parcelable.Creator<WifiKey> CREATOR = new Parcelable.Creator<WifiKey>() { // from class: android.net.WifiKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WifiKey createFromParcel(Parcel parcel) {
            return new WifiKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WifiKey[] newArray(int i) {
            return new WifiKey[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WifiKey(String str, String str2) {
        if (str == null || !SSID_PATTERN.matcher(str).matches()) {
            throw new IllegalArgumentException("Invalid ssid: " + str);
        }
        if (str2 == null || !BSSID_PATTERN.matcher(str2).matches()) {
            throw new IllegalArgumentException("Invalid bssid: " + str2);
        }
        this.ssid = str;
        this.bssid = str2;
    }

    private WifiKey(Parcel parcel) {
        this.ssid = parcel.readString();
        this.bssid = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.ssid);
        parcel.writeString(this.bssid);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            WifiKey wifiKey = (WifiKey) obj;
            if (Objects.equals(this.ssid, wifiKey.ssid) && Objects.equals(this.bssid, wifiKey.bssid)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.ssid, this.bssid);
    }

    public String toString() {
        return "WifiKey[SSID=" + this.ssid + ",BSSID=" + this.bssid + NavigationBarInflaterView.SIZE_MOD_END;
    }
}

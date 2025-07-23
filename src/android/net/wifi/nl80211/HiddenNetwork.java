package android.net.wifi.nl80211;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class HiddenNetwork implements Parcelable {
    public static final Parcelable.Creator<HiddenNetwork> CREATOR = new Parcelable.Creator<HiddenNetwork>() { // from class: android.net.wifi.nl80211.HiddenNetwork.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HiddenNetwork createFromParcel(Parcel parcel) {
            HiddenNetwork hiddenNetwork = new HiddenNetwork();
            hiddenNetwork.ssid = parcel.createByteArray();
            return hiddenNetwork;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HiddenNetwork[] newArray(int i) {
            return new HiddenNetwork[i];
        }
    };
    private static final String TAG = "HiddenNetwork";
    public byte[] ssid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof HiddenNetwork) {
            return Arrays.equals(this.ssid, ((HiddenNetwork) obj).ssid);
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(this.ssid);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByteArray(this.ssid);
    }
}

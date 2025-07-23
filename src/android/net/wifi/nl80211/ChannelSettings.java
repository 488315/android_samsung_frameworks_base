package android.net.wifi.nl80211;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.Objects;

/* loaded from: classes3.dex */
public class ChannelSettings implements Parcelable {
    public static final Parcelable.Creator<ChannelSettings> CREATOR = new Parcelable.Creator<ChannelSettings>() { // from class: android.net.wifi.nl80211.ChannelSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ChannelSettings createFromParcel(Parcel parcel) {
            ChannelSettings channelSettings = new ChannelSettings();
            channelSettings.frequency = parcel.readInt();
            if (parcel.dataAvail() != 0) {
                Log.e(ChannelSettings.TAG, "Found trailing data after parcel parsing.");
            }
            return channelSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ChannelSettings[] newArray(int i) {
            return new ChannelSettings[i];
        }
    };
    private static final String TAG = "ChannelSettings";
    public int frequency;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        ChannelSettings channelSettings;
        if (this == obj) {
            return true;
        }
        return (obj instanceof ChannelSettings) && (channelSettings = (ChannelSettings) obj) != null && this.frequency == channelSettings.frequency;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.frequency));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.frequency);
    }
}

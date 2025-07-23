package android.net.wifi.nl80211;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes3.dex */
public class SingleScanSettings implements Parcelable {
    public static final Parcelable.Creator<SingleScanSettings> CREATOR = new Parcelable.Creator<SingleScanSettings>() { // from class: android.net.wifi.nl80211.SingleScanSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SingleScanSettings createFromParcel(Parcel parcel) {
            SingleScanSettings singleScanSettings = new SingleScanSettings();
            singleScanSettings.scanType = parcel.readInt();
            if (!SingleScanSettings.isValidScanType(singleScanSettings.scanType)) {
                Log.wtf(SingleScanSettings.TAG, "Invalid scan type " + singleScanSettings.scanType);
            }
            singleScanSettings.enable6GhzRnr = parcel.readBoolean();
            singleScanSettings.channelSettings = new ArrayList<>();
            parcel.readTypedList(singleScanSettings.channelSettings, ChannelSettings.CREATOR);
            singleScanSettings.hiddenNetworks = new ArrayList<>();
            parcel.readTypedList(singleScanSettings.hiddenNetworks, HiddenNetwork.CREATOR);
            singleScanSettings.vendorIes = parcel.createByteArray();
            if (singleScanSettings.vendorIes == null) {
                singleScanSettings.vendorIes = new byte[0];
            }
            if (parcel.dataAvail() != 0) {
                Log.e(SingleScanSettings.TAG, "Found trailing data after parcel parsing.");
            }
            return singleScanSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SingleScanSettings[] newArray(int i) {
            return new SingleScanSettings[i];
        }
    };
    private static final String TAG = "SingleScanSettings";
    public ArrayList<ChannelSettings> channelSettings;
    public boolean enable6GhzRnr;
    public ArrayList<HiddenNetwork> hiddenNetworks;
    public int scanType;
    public byte[] vendorIes;

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidScanType(int i) {
        return i == 0 || i == 1 || i == 2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        SingleScanSettings singleScanSettings;
        if (this == obj) {
            return true;
        }
        return (obj instanceof SingleScanSettings) && (singleScanSettings = (SingleScanSettings) obj) != null && this.scanType == singleScanSettings.scanType && this.enable6GhzRnr == singleScanSettings.enable6GhzRnr && this.channelSettings.equals(singleScanSettings.channelSettings) && this.hiddenNetworks.equals(singleScanSettings.hiddenNetworks) && Arrays.equals(this.vendorIes, singleScanSettings.vendorIes);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.scanType), this.channelSettings, this.hiddenNetworks, Boolean.valueOf(this.enable6GhzRnr), Integer.valueOf(Arrays.hashCode(this.vendorIes)));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (!isValidScanType(this.scanType)) {
            Log.wtf(TAG, "Invalid scan type " + this.scanType);
        }
        parcel.writeInt(this.scanType);
        parcel.writeBoolean(this.enable6GhzRnr);
        parcel.writeTypedList(this.channelSettings);
        parcel.writeTypedList(this.hiddenNetworks);
        byte[] bArr = this.vendorIes;
        if (bArr == null) {
            parcel.writeByteArray(new byte[0]);
        } else {
            parcel.writeByteArray(bArr);
        }
    }
}

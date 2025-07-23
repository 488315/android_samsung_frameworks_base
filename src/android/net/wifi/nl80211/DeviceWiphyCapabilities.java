package android.net.wifi.nl80211;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class DeviceWiphyCapabilities implements Parcelable {
    public static final Parcelable.Creator<DeviceWiphyCapabilities> CREATOR = new Parcelable.Creator<DeviceWiphyCapabilities>() { // from class: android.net.wifi.nl80211.DeviceWiphyCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceWiphyCapabilities createFromParcel(Parcel parcel) {
            DeviceWiphyCapabilities deviceWiphyCapabilities = new DeviceWiphyCapabilities();
            deviceWiphyCapabilities.m80211nSupported = parcel.readBoolean();
            deviceWiphyCapabilities.m80211acSupported = parcel.readBoolean();
            deviceWiphyCapabilities.m80211axSupported = parcel.readBoolean();
            deviceWiphyCapabilities.m80211beSupported = parcel.readBoolean();
            deviceWiphyCapabilities.mChannelWidth160MhzSupported = parcel.readBoolean();
            deviceWiphyCapabilities.mChannelWidth80p80MhzSupported = parcel.readBoolean();
            deviceWiphyCapabilities.mChannelWidth320MhzSupported = parcel.readBoolean();
            deviceWiphyCapabilities.mMaxNumberTxSpatialStreams = parcel.readInt();
            deviceWiphyCapabilities.mMaxNumberRxSpatialStreams = parcel.readInt();
            deviceWiphyCapabilities.mMaxNumberAkms = parcel.readInt();
            return deviceWiphyCapabilities;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceWiphyCapabilities[] newArray(int i) {
            return new DeviceWiphyCapabilities[i];
        }
    };
    private static final String TAG = "DeviceWiphyCapabilities";
    private boolean m80211nSupported = false;
    private boolean m80211acSupported = false;
    private boolean m80211axSupported = false;
    private boolean m80211beSupported = false;
    private boolean mChannelWidth160MhzSupported = false;
    private boolean mChannelWidth80p80MhzSupported = false;
    private boolean mChannelWidth320MhzSupported = false;
    private int mMaxNumberTxSpatialStreams = 1;
    private int mMaxNumberRxSpatialStreams = 1;
    private int mMaxNumberAkms = 1;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isWifiStandardSupported(int i) {
        if (i == 1) {
            return true;
        }
        if (i == 8) {
            return this.m80211beSupported;
        }
        if (i == 4) {
            return this.m80211nSupported;
        }
        if (i == 5) {
            return this.m80211acSupported;
        }
        if (i == 6) {
            return this.m80211axSupported;
        }
        Log.e(TAG, "isWifiStandardSupported called with invalid standard: " + i);
        return false;
    }

    public void setWifiStandardSupport(int i, boolean z) {
        if (i == 4) {
            this.m80211nSupported = z;
            return;
        }
        if (i == 5) {
            this.m80211acSupported = z;
            return;
        }
        if (i == 6) {
            this.m80211axSupported = z;
        } else {
            if (i == 8) {
                this.m80211beSupported = z;
                return;
            }
            Log.e(TAG, "setWifiStandardSupport called with invalid standard: " + i);
        }
    }

    public boolean isChannelWidthSupported(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    return this.m80211acSupported || this.m80211axSupported || this.m80211beSupported;
                }
                if (i == 3) {
                    return this.mChannelWidth160MhzSupported;
                }
                if (i == 4) {
                    return this.mChannelWidth80p80MhzSupported;
                }
                if (i == 5) {
                    return this.mChannelWidth320MhzSupported;
                }
                Log.e(TAG, "isChannelWidthSupported called with invalid channel width: " + i);
                return false;
            }
            if (!this.m80211nSupported && !this.m80211acSupported && !this.m80211axSupported && !this.m80211beSupported) {
                return false;
            }
        }
        return true;
    }

    public void setChannelWidthSupported(int i, boolean z) {
        if (i == 3) {
            this.mChannelWidth160MhzSupported = z;
            return;
        }
        if (i == 4) {
            this.mChannelWidth80p80MhzSupported = z;
        } else {
            if (i == 5) {
                this.mChannelWidth320MhzSupported = z;
                return;
            }
            Log.e(TAG, "setChannelWidthSupported called with Invalid channel width: " + i);
        }
    }

    public int getMaxNumberTxSpatialStreams() {
        return this.mMaxNumberTxSpatialStreams;
    }

    public void setMaxNumberTxSpatialStreams(int i) {
        this.mMaxNumberTxSpatialStreams = i;
    }

    public int getMaxNumberRxSpatialStreams() {
        return this.mMaxNumberRxSpatialStreams;
    }

    public int getMaxNumberAkms() {
        return this.mMaxNumberAkms;
    }

    public void setMaxNumberAkms(int i) {
        this.mMaxNumberAkms = i;
    }

    public void setMaxNumberRxSpatialStreams(int i) {
        this.mMaxNumberRxSpatialStreams = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeviceWiphyCapabilities)) {
            return false;
        }
        DeviceWiphyCapabilities deviceWiphyCapabilities = (DeviceWiphyCapabilities) obj;
        return this.m80211nSupported == deviceWiphyCapabilities.m80211nSupported && this.m80211acSupported == deviceWiphyCapabilities.m80211acSupported && this.m80211axSupported == deviceWiphyCapabilities.m80211axSupported && this.m80211beSupported == deviceWiphyCapabilities.m80211beSupported && this.mChannelWidth160MhzSupported == deviceWiphyCapabilities.mChannelWidth160MhzSupported && this.mChannelWidth80p80MhzSupported == deviceWiphyCapabilities.mChannelWidth80p80MhzSupported && this.mChannelWidth320MhzSupported == deviceWiphyCapabilities.mChannelWidth320MhzSupported && this.mMaxNumberTxSpatialStreams == deviceWiphyCapabilities.mMaxNumberTxSpatialStreams && this.mMaxNumberRxSpatialStreams == deviceWiphyCapabilities.mMaxNumberRxSpatialStreams && this.mMaxNumberAkms == deviceWiphyCapabilities.mMaxNumberAkms;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.m80211nSupported), Boolean.valueOf(this.m80211acSupported), Boolean.valueOf(this.m80211axSupported), Boolean.valueOf(this.m80211beSupported), Boolean.valueOf(this.mChannelWidth160MhzSupported), Boolean.valueOf(this.mChannelWidth80p80MhzSupported), Boolean.valueOf(this.mChannelWidth320MhzSupported), Integer.valueOf(this.mMaxNumberTxSpatialStreams), Integer.valueOf(this.mMaxNumberRxSpatialStreams), Integer.valueOf(this.mMaxNumberAkms));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this.m80211nSupported);
        parcel.writeBoolean(this.m80211acSupported);
        parcel.writeBoolean(this.m80211axSupported);
        parcel.writeBoolean(this.m80211beSupported);
        parcel.writeBoolean(this.mChannelWidth160MhzSupported);
        parcel.writeBoolean(this.mChannelWidth80p80MhzSupported);
        parcel.writeBoolean(this.mChannelWidth320MhzSupported);
        parcel.writeInt(this.mMaxNumberTxSpatialStreams);
        parcel.writeInt(this.mMaxNumberRxSpatialStreams);
        parcel.writeInt(this.mMaxNumberAkms);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("m80211nSupported:");
        sb.append(this.m80211nSupported ? "Yes" : "No");
        sb.append("m80211acSupported:");
        sb.append(this.m80211acSupported ? "Yes" : "No");
        sb.append("m80211axSupported:");
        sb.append(this.m80211axSupported ? "Yes" : "No");
        sb.append("m80211beSupported:");
        sb.append(this.m80211beSupported ? "Yes" : "No");
        sb.append("mChannelWidth160MhzSupported: ");
        sb.append(this.mChannelWidth160MhzSupported ? "Yes" : "No");
        sb.append("mChannelWidth80p80MhzSupported: ");
        sb.append(this.mChannelWidth80p80MhzSupported ? "Yes" : "No");
        sb.append("mChannelWidth320MhzSupported: ");
        sb.append(this.mChannelWidth320MhzSupported ? "Yes" : "No");
        sb.append("mMaxNumberTxSpatialStreams: ");
        sb.append(this.mMaxNumberTxSpatialStreams);
        sb.append("mMaxNumberRxSpatialStreams: ");
        sb.append(this.mMaxNumberRxSpatialStreams);
        sb.append("mMaxNumberAkms: ");
        sb.append(this.mMaxNumberAkms);
        return sb.toString();
    }
}

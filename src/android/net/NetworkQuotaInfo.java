package android.net;

import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes3.dex */
public class NetworkQuotaInfo implements Parcelable {
    public static final Parcelable.Creator<NetworkQuotaInfo> CREATOR = new Parcelable.Creator<NetworkQuotaInfo>() { // from class: android.net.NetworkQuotaInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkQuotaInfo createFromParcel(Parcel parcel) {
            return new NetworkQuotaInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkQuotaInfo[] newArray(int i) {
            return new NetworkQuotaInfo[i];
        }
    };
    public static final long NO_LIMIT = -1;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public long getEstimatedBytes() {
        return 0L;
    }

    public long getHardLimitBytes() {
        return -1L;
    }

    public long getSoftLimitBytes() {
        return -1L;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
    }

    public NetworkQuotaInfo() {
    }

    public NetworkQuotaInfo(Parcel parcel) {
    }
}

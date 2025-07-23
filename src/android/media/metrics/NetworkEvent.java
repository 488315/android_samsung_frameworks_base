package android.media.metrics;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class NetworkEvent extends Event implements Parcelable {
    public static final Parcelable.Creator<NetworkEvent> CREATOR = new Parcelable.Creator<NetworkEvent>() { // from class: android.media.metrics.NetworkEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkEvent[] newArray(int i) {
            return new NetworkEvent[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkEvent createFromParcel(Parcel parcel) {
            return new NetworkEvent(parcel);
        }
    };
    public static final int NETWORK_TYPE_2G = 4;
    public static final int NETWORK_TYPE_3G = 5;
    public static final int NETWORK_TYPE_4G = 6;
    public static final int NETWORK_TYPE_5G_NSA = 7;
    public static final int NETWORK_TYPE_5G_SA = 8;
    public static final int NETWORK_TYPE_ETHERNET = 3;
    public static final int NETWORK_TYPE_OFFLINE = 9;
    public static final int NETWORK_TYPE_OTHER = 1;
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    public static final int NETWORK_TYPE_WIFI = 2;
    private final int mNetworkType;
    private final long mTimeSinceCreatedMillis;

    @Retention(RetentionPolicy.SOURCE)
    public @interface NetworkType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String networkTypeToString(int i) {
        switch (i) {
            case 0:
                return "NETWORK_TYPE_UNKNOWN";
            case 1:
                return "NETWORK_TYPE_OTHER";
            case 2:
                return "NETWORK_TYPE_WIFI";
            case 3:
                return "NETWORK_TYPE_ETHERNET";
            case 4:
                return "NETWORK_TYPE_2G";
            case 5:
                return "NETWORK_TYPE_3G";
            case 6:
                return "NETWORK_TYPE_4G";
            case 7:
                return "NETWORK_TYPE_5G_NSA";
            case 8:
                return "NETWORK_TYPE_5G_SA";
            case 9:
                return "NETWORK_TYPE_OFFLINE";
            default:
                return Integer.toHexString(i);
        }
    }

    private NetworkEvent(int i, long j, Bundle bundle) {
        this.mNetworkType = i;
        this.mTimeSinceCreatedMillis = j;
        this.mMetricsBundle = bundle == null ? null : bundle.deepCopy();
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    @Override // android.media.metrics.Event
    public long getTimeSinceCreatedMillis() {
        return this.mTimeSinceCreatedMillis;
    }

    @Override // android.media.metrics.Event
    public Bundle getMetricsBundle() {
        return this.mMetricsBundle;
    }

    public String toString() {
        return "NetworkEvent { networkType = " + this.mNetworkType + ", timeSinceCreatedMillis = " + this.mTimeSinceCreatedMillis + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            NetworkEvent networkEvent = (NetworkEvent) obj;
            if (this.mNetworkType == networkEvent.mNetworkType && this.mTimeSinceCreatedMillis == networkEvent.mTimeSinceCreatedMillis) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mNetworkType), Long.valueOf(this.mTimeSinceCreatedMillis));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mNetworkType);
        parcel.writeLong(this.mTimeSinceCreatedMillis);
        parcel.writeBundle(this.mMetricsBundle);
    }

    private NetworkEvent(Parcel parcel) {
        int readInt = parcel.readInt();
        long readLong = parcel.readLong();
        Bundle readBundle = parcel.readBundle();
        this.mNetworkType = readInt;
        this.mTimeSinceCreatedMillis = readLong;
        this.mMetricsBundle = readBundle;
    }

    public static final class Builder {
        private int mNetworkType = 0;
        private long mTimeSinceCreatedMillis = -1;
        private Bundle mMetricsBundle = new Bundle();

        public Builder setNetworkType(int i) {
            this.mNetworkType = i;
            return this;
        }

        public Builder setTimeSinceCreatedMillis(long j) {
            this.mTimeSinceCreatedMillis = j;
            return this;
        }

        public Builder setMetricsBundle(Bundle bundle) {
            this.mMetricsBundle = bundle;
            return this;
        }

        public NetworkEvent build() {
            return new NetworkEvent(this.mNetworkType, this.mTimeSinceCreatedMillis, this.mMetricsBundle);
        }
    }
}

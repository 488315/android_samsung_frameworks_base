package android.net.wifi.sharedconnectivity.app;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class HotspotNetworkConnectionStatus implements Parcelable {
    public static final int CONNECTION_STATUS_CONNECT_TO_HOTSPOT_FAILED = 9;
    public static final int CONNECTION_STATUS_ENABLING_HOTSPOT = 1;
    public static final int CONNECTION_STATUS_ENABLING_HOTSPOT_FAILED = 7;
    public static final int CONNECTION_STATUS_ENABLING_HOTSPOT_TIMEOUT = 8;
    public static final int CONNECTION_STATUS_NO_CELL_DATA = 6;
    public static final int CONNECTION_STATUS_PROVISIONING_FAILED = 3;
    public static final int CONNECTION_STATUS_TETHERING_TIMEOUT = 4;
    public static final int CONNECTION_STATUS_TETHERING_UNSUPPORTED = 5;
    public static final int CONNECTION_STATUS_UNKNOWN = 0;
    public static final int CONNECTION_STATUS_UNKNOWN_ERROR = 2;
    public static final Parcelable.Creator<HotspotNetworkConnectionStatus> CREATOR = new Parcelable.Creator<HotspotNetworkConnectionStatus>() { // from class: android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HotspotNetworkConnectionStatus createFromParcel(Parcel parcel) {
            return HotspotNetworkConnectionStatus.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HotspotNetworkConnectionStatus[] newArray(int i) {
            return new HotspotNetworkConnectionStatus[i];
        }
    };
    private final Bundle mExtras;
    private final HotspotNetwork mHotspotNetwork;
    private final int mStatus;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConnectionStatus {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private Bundle mExtras = Bundle.EMPTY;
        private HotspotNetwork mHotspotNetwork;
        private int mStatus;

        public Builder setStatus(int i) {
            this.mStatus = i;
            return this;
        }

        public Builder setHotspotNetwork(HotspotNetwork hotspotNetwork) {
            this.mHotspotNetwork = hotspotNetwork;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public HotspotNetworkConnectionStatus build() {
            return new HotspotNetworkConnectionStatus(this.mStatus, this.mHotspotNetwork, this.mExtras);
        }
    }

    private static void validate(int i) {
        if (i != 0 && i != 1 && i != 2 && i != 3 && i != 4 && i != 5 && i != 6 && i != 7 && i != 8 && i != 9) {
            throw new IllegalArgumentException("Illegal connection status");
        }
    }

    private HotspotNetworkConnectionStatus(int i, HotspotNetwork hotspotNetwork, Bundle bundle) {
        validate(i);
        this.mStatus = i;
        this.mHotspotNetwork = hotspotNetwork;
        this.mExtras = bundle;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public HotspotNetwork getHotspotNetwork() {
        return this.mHotspotNetwork;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof HotspotNetworkConnectionStatus)) {
            return false;
        }
        HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus = (HotspotNetworkConnectionStatus) obj;
        return this.mStatus == hotspotNetworkConnectionStatus.getStatus() && Objects.equals(this.mHotspotNetwork, hotspotNetworkConnectionStatus.getHotspotNetwork());
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mStatus), this.mHotspotNetwork);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mStatus);
        this.mHotspotNetwork.writeToParcel(parcel, i);
        parcel.writeBundle(this.mExtras);
    }

    public static HotspotNetworkConnectionStatus readFromParcel(Parcel parcel) {
        return new HotspotNetworkConnectionStatus(parcel.readInt(), HotspotNetwork.readFromParcel(parcel), parcel.readBundle());
    }

    public String toString() {
        return "HotspotNetworkConnectionStatus[status=" + this.mStatus + "hotspot network=" + this.mHotspotNetwork.toString() + "extras=" + this.mExtras.toString() + NavigationBarInflaterView.SIZE_MOD_END;
    }
}

package android.net.wifi.sharedconnectivity.app;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class KnownNetworkConnectionStatus implements Parcelable {
    public static final int CONNECTION_STATUS_SAVED = 1;
    public static final int CONNECTION_STATUS_SAVE_FAILED = 2;
    public static final int CONNECTION_STATUS_UNKNOWN = 0;
    public static final Parcelable.Creator<KnownNetworkConnectionStatus> CREATOR = new Parcelable.Creator<KnownNetworkConnectionStatus>() { // from class: android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KnownNetworkConnectionStatus createFromParcel(Parcel parcel) {
            return KnownNetworkConnectionStatus.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KnownNetworkConnectionStatus[] newArray(int i) {
            return new KnownNetworkConnectionStatus[i];
        }
    };
    private final Bundle mExtras;
    private final KnownNetwork mKnownNetwork;
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
        private KnownNetwork mKnownNetwork;
        private int mStatus;

        public Builder setStatus(int i) {
            this.mStatus = i;
            return this;
        }

        public Builder setKnownNetwork(KnownNetwork knownNetwork) {
            this.mKnownNetwork = knownNetwork;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public KnownNetworkConnectionStatus build() {
            return new KnownNetworkConnectionStatus(this.mStatus, this.mKnownNetwork, this.mExtras);
        }
    }

    private static void validate(int i) {
        if (i != 0 && i != 1 && i != 2) {
            throw new IllegalArgumentException("Illegal connection status");
        }
    }

    private KnownNetworkConnectionStatus(int i, KnownNetwork knownNetwork, Bundle bundle) {
        validate(i);
        this.mStatus = i;
        this.mKnownNetwork = knownNetwork;
        this.mExtras = bundle;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public KnownNetwork getKnownNetwork() {
        return this.mKnownNetwork;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof KnownNetworkConnectionStatus)) {
            return false;
        }
        KnownNetworkConnectionStatus knownNetworkConnectionStatus = (KnownNetworkConnectionStatus) obj;
        return this.mStatus == knownNetworkConnectionStatus.getStatus() && Objects.equals(this.mKnownNetwork, knownNetworkConnectionStatus.getKnownNetwork());
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mStatus), this.mKnownNetwork);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
        parcel.writeInt(this.mStatus);
        this.mKnownNetwork.writeToParcel(parcel, i);
        parcel.writeBundle(this.mExtras);
    }

    public static KnownNetworkConnectionStatus readFromParcel(Parcel parcel) {
        return new KnownNetworkConnectionStatus(parcel.readInt(), KnownNetwork.readFromParcel(parcel), parcel.readBundle());
    }

    public String toString() {
        return "KnownNetworkConnectionStatus[status=" + this.mStatus + "known network=" + this.mKnownNetwork.toString() + "extras=" + this.mExtras.toString() + NavigationBarInflaterView.SIZE_MOD_END;
    }
}

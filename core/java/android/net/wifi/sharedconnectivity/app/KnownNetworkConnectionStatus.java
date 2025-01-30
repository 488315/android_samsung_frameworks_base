package android.net.wifi.sharedconnectivity.app;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;
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
        public KnownNetworkConnectionStatus createFromParcel(Parcel in) {
            return KnownNetworkConnectionStatus.readFromParcel(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KnownNetworkConnectionStatus[] newArray(int size) {
            return new KnownNetworkConnectionStatus[size];
        }
    };
    private final Bundle mExtras;
    private final KnownNetwork mKnownNetwork;
    private final int mStatus;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConnectionStatus {
    }

    public static final class Builder {
        private Bundle mExtras = Bundle.EMPTY;
        private KnownNetwork mKnownNetwork;
        private int mStatus;

        public Builder setStatus(int status) {
            this.mStatus = status;
            return this;
        }

        public Builder setKnownNetwork(KnownNetwork knownNetwork) {
            this.mKnownNetwork = knownNetwork;
            return this;
        }

        public Builder setExtras(Bundle extras) {
            this.mExtras = extras;
            return this;
        }

        public KnownNetworkConnectionStatus build() {
            return new KnownNetworkConnectionStatus(this.mStatus, this.mKnownNetwork, this.mExtras);
        }
    }

    private static void validate(int status) {
        if (status != 0 && status != 1 && status != 2) {
            throw new IllegalArgumentException("Illegal connection status");
        }
    }

    private KnownNetworkConnectionStatus(int status, KnownNetwork knownNetwork, Bundle extras) {
        validate(status);
        this.mStatus = status;
        this.mKnownNetwork = knownNetwork;
        this.mExtras = extras;
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
        KnownNetworkConnectionStatus other = (KnownNetworkConnectionStatus) obj;
        return this.mStatus == other.getStatus() && Objects.equals(this.mKnownNetwork, other.getKnownNetwork());
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mStatus), this.mKnownNetwork);
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mStatus);
        this.mKnownNetwork.writeToParcel(dest, flags);
        dest.writeBundle(this.mExtras);
    }

    public static KnownNetworkConnectionStatus readFromParcel(Parcel in) {
        return new KnownNetworkConnectionStatus(in.readInt(), KnownNetwork.readFromParcel(in), in.readBundle());
    }

    public String toString() {
        return "KnownNetworkConnectionStatus[status=" + this.mStatus + "known network=" + this.mKnownNetwork.toString() + "extras=" + this.mExtras.toString() + NavigationBarInflaterView.SIZE_MOD_END;
    }
}

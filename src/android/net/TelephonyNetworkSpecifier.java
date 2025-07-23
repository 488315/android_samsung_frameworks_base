package android.net;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class TelephonyNetworkSpecifier extends NetworkSpecifier implements Parcelable {
    public static final Parcelable.Creator<TelephonyNetworkSpecifier> CREATOR = new Parcelable.Creator<TelephonyNetworkSpecifier>() { // from class: android.net.TelephonyNetworkSpecifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TelephonyNetworkSpecifier createFromParcel(Parcel parcel) {
            return new TelephonyNetworkSpecifier(parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TelephonyNetworkSpecifier[] newArray(int i) {
            return new TelephonyNetworkSpecifier[i];
        }
    };
    private final int mSubId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getSubscriptionId() {
        return this.mSubId;
    }

    public TelephonyNetworkSpecifier(int i) {
        this.mSubId = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSubId);
    }

    public int hashCode() {
        return this.mSubId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof TelephonyNetworkSpecifier) && this.mSubId == ((TelephonyNetworkSpecifier) obj).mSubId;
    }

    public String toString() {
        return "TelephonyNetworkSpecifier [mSubId = " + this.mSubId + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.net.NetworkSpecifier
    public boolean canBeSatisfiedBy(NetworkSpecifier networkSpecifier) {
        return equals(networkSpecifier) || (networkSpecifier instanceof MatchAllNetworkSpecifier);
    }

    public static final class Builder {
        private static final int SENTINEL_SUB_ID = Integer.MIN_VALUE;
        private int mSubId = Integer.MIN_VALUE;

        public Builder setSubscriptionId(int i) {
            this.mSubId = i;
            return this;
        }

        public TelephonyNetworkSpecifier build() {
            if (this.mSubId == Integer.MIN_VALUE) {
                throw new IllegalArgumentException("Subscription Id is not provided.");
            }
            return new TelephonyNetworkSpecifier(this.mSubId);
        }
    }
}

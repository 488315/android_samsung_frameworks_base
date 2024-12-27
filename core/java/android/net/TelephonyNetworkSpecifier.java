package android.net;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

public final class TelephonyNetworkSpecifier extends NetworkSpecifier implements Parcelable {
    public static final Parcelable.Creator<TelephonyNetworkSpecifier> CREATOR =
            new Parcelable.Creator<
                    TelephonyNetworkSpecifier>() { // from class:
                                                   // android.net.TelephonyNetworkSpecifier.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TelephonyNetworkSpecifier createFromParcel(Parcel in) {
                    int subId = in.readInt();
                    return new TelephonyNetworkSpecifier(subId);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TelephonyNetworkSpecifier[] newArray(int size) {
                    return new TelephonyNetworkSpecifier[size];
                }
            };
    private final int mSubId;

    public int getSubscriptionId() {
        return this.mSubId;
    }

    public TelephonyNetworkSpecifier(int subId) {
        this.mSubId = subId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mSubId);
    }

    public int hashCode() {
        return this.mSubId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TelephonyNetworkSpecifier)) {
            return false;
        }
        TelephonyNetworkSpecifier lhs = (TelephonyNetworkSpecifier) obj;
        return this.mSubId == lhs.mSubId;
    }

    public String toString() {
        return "TelephonyNetworkSpecifier [mSubId = "
                + this.mSubId
                + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.net.NetworkSpecifier
    public boolean canBeSatisfiedBy(NetworkSpecifier other) {
        return equals(other) || (other instanceof MatchAllNetworkSpecifier);
    }

    public static final class Builder {
        private static final int SENTINEL_SUB_ID = Integer.MIN_VALUE;
        private int mSubId = Integer.MIN_VALUE;

        public Builder setSubscriptionId(int subId) {
            this.mSubId = subId;
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

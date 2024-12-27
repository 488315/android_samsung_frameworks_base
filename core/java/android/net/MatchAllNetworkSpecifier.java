package android.net;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class MatchAllNetworkSpecifier extends NetworkSpecifier implements Parcelable {
    public static final Parcelable.Creator<MatchAllNetworkSpecifier> CREATOR =
            new Parcelable.Creator<
                    MatchAllNetworkSpecifier>() { // from class:
                                                  // android.net.MatchAllNetworkSpecifier.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public MatchAllNetworkSpecifier createFromParcel(Parcel in) {
                    return new MatchAllNetworkSpecifier();
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public MatchAllNetworkSpecifier[] newArray(int size) {
                    return new MatchAllNetworkSpecifier[size];
                }
            };

    @Override // android.net.NetworkSpecifier
    public boolean canBeSatisfiedBy(NetworkSpecifier other) {
        throw new IllegalStateException(
                "MatchAllNetworkSpecifier must not be used in NetworkRequests");
    }

    public boolean equals(Object o) {
        return o instanceof MatchAllNetworkSpecifier;
    }

    public int hashCode() {
        return 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {}
}

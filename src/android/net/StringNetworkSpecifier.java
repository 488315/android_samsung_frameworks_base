package android.net;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class StringNetworkSpecifier extends NetworkSpecifier implements Parcelable {
    public static final Parcelable.Creator<StringNetworkSpecifier> CREATOR = new Parcelable.Creator<StringNetworkSpecifier>() { // from class: android.net.StringNetworkSpecifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StringNetworkSpecifier createFromParcel(Parcel parcel) {
            return new StringNetworkSpecifier(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StringNetworkSpecifier[] newArray(int i) {
            return new StringNetworkSpecifier[i];
        }
    };
    public final String specifier;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public StringNetworkSpecifier(String str) {
        Preconditions.checkStringNotEmpty(str);
        this.specifier = str;
    }

    @Override // android.net.NetworkSpecifier
    public boolean canBeSatisfiedBy(NetworkSpecifier networkSpecifier) {
        return equals(networkSpecifier);
    }

    public boolean equals(Object obj) {
        if (obj instanceof StringNetworkSpecifier) {
            return TextUtils.equals(this.specifier, ((StringNetworkSpecifier) obj).specifier);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.specifier);
    }

    public String toString() {
        return this.specifier;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.specifier);
    }
}

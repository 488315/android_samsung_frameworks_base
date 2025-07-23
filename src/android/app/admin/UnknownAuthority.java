package android.app.admin;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class UnknownAuthority extends Authority {
    private final String mName;
    public static final UnknownAuthority UNKNOWN_AUTHORITY = new UnknownAuthority();
    public static final Parcelable.Creator<UnknownAuthority> CREATOR = new Parcelable.Creator<UnknownAuthority>() { // from class: android.app.admin.UnknownAuthority.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UnknownAuthority createFromParcel(Parcel parcel) {
            return new UnknownAuthority(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UnknownAuthority[] newArray(int i) {
            return new UnknownAuthority[i];
        }
    };

    @Override // android.app.admin.Authority, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UnknownAuthority() {
        this.mName = null;
    }

    public UnknownAuthority(String str) {
        this.mName = str;
    }

    private UnknownAuthority(Parcel parcel) {
        this(parcel.readString8());
    }

    public String getName() {
        return this.mName;
    }

    public String toString() {
        return "DefaultAuthority {" + this.mName + "}";
    }

    @Override // android.app.admin.Authority
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mName, ((UnknownAuthority) obj).mName);
    }

    @Override // android.app.admin.Authority
    public int hashCode() {
        return Objects.hashCode(this.mName);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mName);
    }
}

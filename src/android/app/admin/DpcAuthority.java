package android.app.admin;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public final class DpcAuthority extends Authority {
    public static final DpcAuthority DPC_AUTHORITY = new DpcAuthority();
    public static final Parcelable.Creator<DpcAuthority> CREATOR = new Parcelable.Creator<DpcAuthority>() { // from class: android.app.admin.DpcAuthority.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DpcAuthority createFromParcel(Parcel parcel) {
            return DpcAuthority.DPC_AUTHORITY;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DpcAuthority[] newArray(int i) {
            return new DpcAuthority[i];
        }
    };

    @Override // android.app.admin.Authority, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.app.admin.Authority
    public int hashCode() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
    }

    public String toString() {
        return "DpcAuthority {}";
    }

    @Override // android.app.admin.Authority
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass();
    }
}

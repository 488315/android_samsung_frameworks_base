package android.app.admin;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@SystemApi
/* loaded from: classes.dex */
public final class RoleAuthority extends Authority {
    public static final Parcelable.Creator<RoleAuthority> CREATOR = new Parcelable.Creator<RoleAuthority>() { // from class: android.app.admin.RoleAuthority.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RoleAuthority createFromParcel(Parcel parcel) {
            return new RoleAuthority(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RoleAuthority[] newArray(int i) {
            return new RoleAuthority[i];
        }
    };
    private final Set<String> mRoles;

    @Override // android.app.admin.Authority, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RoleAuthority(Set<String> set) {
        this.mRoles = new HashSet((Collection) Objects.requireNonNull(set));
    }

    private RoleAuthority(Parcel parcel) {
        this.mRoles = new HashSet();
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            this.mRoles.add(parcel.readString());
        }
    }

    public Set<String> getRoles() {
        return this.mRoles;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRoles.size());
        Iterator<String> it = this.mRoles.iterator();
        while (it.hasNext()) {
            parcel.writeString(it.next());
        }
    }

    @Override // android.app.admin.Authority
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mRoles, ((RoleAuthority) obj).mRoles);
    }

    @Override // android.app.admin.Authority
    public int hashCode() {
        return Objects.hash(this.mRoles);
    }

    public String toString() {
        return "RoleAuthority { mRoles= " + this.mRoles + " }";
    }
}

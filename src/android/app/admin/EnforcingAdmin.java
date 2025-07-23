package android.app.admin;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class EnforcingAdmin implements Parcelable {
    public static final Parcelable.Creator<EnforcingAdmin> CREATOR = new Parcelable.Creator<EnforcingAdmin>() { // from class: android.app.admin.EnforcingAdmin.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnforcingAdmin createFromParcel(Parcel parcel) {
            return new EnforcingAdmin(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnforcingAdmin[] newArray(int i) {
            return new EnforcingAdmin[i];
        }
    };
    private final Authority mAuthority;
    private final ComponentName mComponentName;
    private final String mPackageName;
    private final UserHandle mUserHandle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public EnforcingAdmin(String str, Authority authority, UserHandle userHandle) {
        this.mPackageName = (String) Objects.requireNonNull(str);
        this.mAuthority = (Authority) Objects.requireNonNull(authority);
        this.mUserHandle = (UserHandle) Objects.requireNonNull(userHandle);
        this.mComponentName = null;
    }

    public EnforcingAdmin(String str, Authority authority, UserHandle userHandle, ComponentName componentName) {
        this.mPackageName = (String) Objects.requireNonNull(str);
        this.mAuthority = (Authority) Objects.requireNonNull(authority);
        this.mUserHandle = (UserHandle) Objects.requireNonNull(userHandle);
        this.mComponentName = componentName;
    }

    private EnforcingAdmin(Parcel parcel) {
        this.mPackageName = (String) Objects.requireNonNull(parcel.readString());
        this.mUserHandle = new UserHandle(parcel.readInt());
        this.mAuthority = (Authority) Objects.requireNonNull((Authority) parcel.readParcelable(Authority.class.getClassLoader()));
        this.mComponentName = (ComponentName) parcel.readParcelable(ComponentName.class.getClassLoader());
    }

    public Authority getAuthority() {
        return this.mAuthority;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            EnforcingAdmin enforcingAdmin = (EnforcingAdmin) obj;
            if (Objects.equals(this.mPackageName, enforcingAdmin.mPackageName) && Objects.equals(this.mAuthority, enforcingAdmin.mAuthority) && Objects.equals(this.mUserHandle, enforcingAdmin.mUserHandle) && Objects.equals(this.mComponentName, enforcingAdmin.mComponentName)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mPackageName, this.mAuthority, this.mUserHandle);
    }

    public String toString() {
        return "EnforcingAdmin { mPackageName= " + this.mPackageName + ", mAuthority= " + this.mAuthority + ", mUserHandle= " + this.mUserHandle + ", mComponentName= " + this.mComponentName + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mUserHandle.getIdentifier());
        parcel.writeParcelable(this.mAuthority, i);
        parcel.writeParcelable(this.mComponentName, i);
    }
}

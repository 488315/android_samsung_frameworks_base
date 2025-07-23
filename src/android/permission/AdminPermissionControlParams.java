package android.permission;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

@SystemApi
/* loaded from: classes3.dex */
public final class AdminPermissionControlParams implements Parcelable {
    public static final Parcelable.Creator<AdminPermissionControlParams> CREATOR = new Parcelable.Creator<AdminPermissionControlParams>() { // from class: android.permission.AdminPermissionControlParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AdminPermissionControlParams createFromParcel(Parcel parcel) {
            return new AdminPermissionControlParams(parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readBoolean());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AdminPermissionControlParams[] newArray(int i) {
            return new AdminPermissionControlParams[i];
        }
    };
    private final boolean mCanAdminGrantSensorsPermissions;
    private final int mGrantState;
    private final String mGranteePackageName;
    private final String mPermission;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AdminPermissionControlParams(String str, String str2, int i, boolean z) {
        Preconditions.checkStringNotEmpty(str, "Package name must not be empty.");
        Preconditions.checkStringNotEmpty(str2, "Permission must not be empty.");
        boolean z2 = true;
        if (i != 1 && i != 2 && i != 0) {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
        this.mGranteePackageName = str;
        this.mPermission = str2;
        this.mGrantState = i;
        this.mCanAdminGrantSensorsPermissions = z;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mGranteePackageName);
        parcel.writeString(this.mPermission);
        parcel.writeInt(this.mGrantState);
        parcel.writeBoolean(this.mCanAdminGrantSensorsPermissions);
    }

    public String getGranteePackageName() {
        return this.mGranteePackageName;
    }

    public String getPermission() {
        return this.mPermission;
    }

    public int getGrantState() {
        return this.mGrantState;
    }

    public boolean canAdminGrantSensorsPermissions() {
        return this.mCanAdminGrantSensorsPermissions;
    }

    public String toString() {
        return String.format("Grantee %s Permission %s state: %d admin grant of sensors permissions: %b", this.mGranteePackageName, this.mPermission, Integer.valueOf(this.mGrantState), Boolean.valueOf(this.mCanAdminGrantSensorsPermissions));
    }
}

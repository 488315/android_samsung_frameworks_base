package android.permission;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

@SystemApi
/* loaded from: classes3.dex */
public final class RuntimePermissionUsageInfo implements Parcelable {
    public static final Parcelable.Creator<RuntimePermissionUsageInfo> CREATOR = new Parcelable.Creator<RuntimePermissionUsageInfo>() { // from class: android.permission.RuntimePermissionUsageInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RuntimePermissionUsageInfo createFromParcel(Parcel parcel) {
            return new RuntimePermissionUsageInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RuntimePermissionUsageInfo[] newArray(int i) {
            return new RuntimePermissionUsageInfo[i];
        }
    };
    private final String mName;
    private final int mNumUsers;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RuntimePermissionUsageInfo(String str, int i) {
        Preconditions.checkNotNull(str);
        Preconditions.checkArgumentNonnegative(i);
        this.mName = str;
        this.mNumUsers = i;
    }

    private RuntimePermissionUsageInfo(Parcel parcel) {
        this(parcel.readString(), parcel.readInt());
    }

    public int getAppAccessCount() {
        return this.mNumUsers;
    }

    public String getName() {
        return this.mName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mNumUsers);
    }
}

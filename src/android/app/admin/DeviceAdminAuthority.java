package android.app.admin;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public final class DeviceAdminAuthority extends Authority {
    public static final DeviceAdminAuthority DEVICE_ADMIN_AUTHORITY = new DeviceAdminAuthority();
    public static final Parcelable.Creator<DeviceAdminAuthority> CREATOR = new Parcelable.Creator<DeviceAdminAuthority>() { // from class: android.app.admin.DeviceAdminAuthority.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceAdminAuthority createFromParcel(Parcel parcel) {
            return DeviceAdminAuthority.DEVICE_ADMIN_AUTHORITY;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceAdminAuthority[] newArray(int i) {
            return new DeviceAdminAuthority[i];
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
        return "DeviceAdminAuthority {}";
    }

    @Override // android.app.admin.Authority
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass();
    }
}

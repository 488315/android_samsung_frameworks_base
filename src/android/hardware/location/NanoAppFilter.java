package android.hardware.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
@Deprecated
/* loaded from: classes2.dex */
public class NanoAppFilter implements Parcelable {
    public static final int APP_ANY = -1;
    public static final Parcelable.Creator<NanoAppFilter> CREATOR = new Parcelable.Creator<NanoAppFilter>() { // from class: android.hardware.location.NanoAppFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NanoAppFilter createFromParcel(Parcel parcel) {
            return new NanoAppFilter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NanoAppFilter[] newArray(int i) {
            return new NanoAppFilter[i];
        }
    };
    public static final int FLAGS_VERSION_ANY = -1;
    public static final int FLAGS_VERSION_GREAT_THAN = 2;
    public static final int FLAGS_VERSION_LESS_THAN = 4;
    public static final int FLAGS_VERSION_STRICTLY_EQUAL = 8;
    public static final int HUB_ANY = -1;
    private static final String TAG = "NanoAppFilter";
    public static final int VENDOR_ANY = -1;
    private long mAppId;
    private long mAppIdVendorMask;
    private int mAppVersion;
    private int mContextHubId;
    private int mVersionRestrictionMask;

    private boolean versionsMatch(int i, int i2, int i3) {
        return true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private NanoAppFilter(Parcel parcel) {
        this.mContextHubId = -1;
        this.mAppId = parcel.readLong();
        this.mAppVersion = parcel.readInt();
        this.mVersionRestrictionMask = parcel.readInt();
        this.mAppIdVendorMask = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mAppId);
        parcel.writeInt(this.mAppVersion);
        parcel.writeInt(this.mVersionRestrictionMask);
        parcel.writeLong(this.mAppIdVendorMask);
    }

    public NanoAppFilter(long j, int i, int i2, long j2) {
        this.mContextHubId = -1;
        this.mAppId = j;
        this.mAppVersion = i;
        this.mVersionRestrictionMask = i2;
        this.mAppIdVendorMask = j2;
    }

    public boolean testMatch(NanoAppInstanceInfo nanoAppInstanceInfo) {
        if (this.mContextHubId == -1 || nanoAppInstanceInfo.getContexthubId() == this.mContextHubId) {
            return (this.mAppId == -1 || nanoAppInstanceInfo.getAppId() == this.mAppId) && versionsMatch(this.mVersionRestrictionMask, this.mAppVersion, nanoAppInstanceInfo.getAppVersion());
        }
        return false;
    }

    public String toString() {
        return "nanoAppId: 0x" + Long.toHexString(this.mAppId) + ", nanoAppVersion: 0x" + Integer.toHexString(this.mAppVersion) + ", versionMask: " + this.mVersionRestrictionMask + ", vendorMask: " + this.mAppIdVendorMask;
    }
}

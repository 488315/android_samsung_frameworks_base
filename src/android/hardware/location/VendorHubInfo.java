package android.hardware.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.ParcelableHolder;

/* loaded from: classes2.dex */
public final class VendorHubInfo implements Parcelable {
    public static final Parcelable.Creator<VendorHubInfo> CREATOR = new Parcelable.Creator<VendorHubInfo>() { // from class: android.hardware.location.VendorHubInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VendorHubInfo createFromParcel(Parcel parcel) {
            return new VendorHubInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VendorHubInfo[] newArray(int i) {
            return new VendorHubInfo[i];
        }
    };
    private final ParcelableHolder mExtendedInfo;
    private final String mName;
    private final int mVersion;

    public VendorHubInfo(android.hardware.contexthub.VendorHubInfo vendorHubInfo) {
        this.mName = vendorHubInfo.name;
        this.mVersion = vendorHubInfo.version;
        this.mExtendedInfo = vendorHubInfo.extendedInfo;
    }

    private VendorHubInfo(Parcel parcel) {
        this.mName = parcel.readString();
        this.mVersion = parcel.readInt();
        this.mExtendedInfo = ParcelableHolder.CREATOR.createFromParcel(parcel);
    }

    public String getName() {
        return this.mName;
    }

    public int getVersion() {
        return this.mVersion;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return this.mExtendedInfo.describeContents();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mVersion);
        this.mExtendedInfo.writeToParcel(parcel, i);
    }

    public String toString() {
        return "VendorHub Name : " + this.mName + ", Version : " + this.mVersion;
    }
}

package android.hardware.camera2.params;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class VendorTagDescriptorCache implements Parcelable {
    public static final Parcelable.Creator<VendorTagDescriptorCache> CREATOR = new Parcelable.Creator<VendorTagDescriptorCache>() { // from class: android.hardware.camera2.params.VendorTagDescriptorCache.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VendorTagDescriptorCache createFromParcel(Parcel parcel) {
            return new VendorTagDescriptorCache(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VendorTagDescriptorCache[] newArray(int i) {
            return new VendorTagDescriptorCache[i];
        }
    };
    private static final String TAG = "VendorTagDescriptorCache";

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VendorTagDescriptorCache(Parcel parcel) {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            throw new IllegalArgumentException("dest must not be null");
        }
    }
}

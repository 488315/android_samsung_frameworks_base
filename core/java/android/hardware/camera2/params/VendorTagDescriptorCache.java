package android.hardware.camera2.params;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class VendorTagDescriptorCache implements Parcelable {
    public static final Parcelable.Creator<VendorTagDescriptorCache> CREATOR =
            new Parcelable.Creator<VendorTagDescriptorCache>() { // from class:
                // android.hardware.camera2.params.VendorTagDescriptorCache.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VendorTagDescriptorCache createFromParcel(Parcel source) {
                    return new VendorTagDescriptorCache(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VendorTagDescriptorCache[] newArray(int size) {
                    return new VendorTagDescriptorCache[size];
                }
            };
    private static final String TAG = "VendorTagDescriptorCache";

    private VendorTagDescriptorCache(Parcel source) {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (dest == null) {
            throw new IllegalArgumentException("dest must not be null");
        }
    }
}

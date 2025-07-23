package android.hardware.camera2.params;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class VendorTagDescriptor implements Parcelable {
    public static final Parcelable.Creator<VendorTagDescriptor> CREATOR = new Parcelable.Creator<VendorTagDescriptor>() { // from class: android.hardware.camera2.params.VendorTagDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VendorTagDescriptor createFromParcel(Parcel parcel) {
            return new VendorTagDescriptor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VendorTagDescriptor[] newArray(int i) {
            return new VendorTagDescriptor[i];
        }
    };
    private static final String TAG = "VendorTagDescriptor";

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VendorTagDescriptor(Parcel parcel) {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            throw new IllegalArgumentException("dest must not be null");
        }
    }
}

package android.hardware;

import android.os.Parcel;
import android.os.Parcelable;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes2.dex */
public final class OverlayProperties implements Parcelable {
    private static OverlayProperties sDefaultOverlayProperties;
    private Runnable mCloser;
    private LutProperties[] mLutProperties;
    private long mNativeObject;
    private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(OverlayProperties.class.getClassLoader(), nGetDestructor());
    public static final Parcelable.Creator<OverlayProperties> CREATOR = new Parcelable.Creator<OverlayProperties>() { // from class: android.hardware.OverlayProperties.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverlayProperties createFromParcel(Parcel parcel) {
            if (parcel.readInt() != 0) {
                return new OverlayProperties(OverlayProperties.nReadOverlayPropertiesFromParcel(parcel));
            }
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverlayProperties[] newArray(int i) {
            return new OverlayProperties[i];
        }
    };

    private static native long nCreateDefault();

    private static native long nGetDestructor();

    private static native LutProperties[] nGetLutProperties(long j);

    private static native boolean nIsCombinationSupported(long j, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nReadOverlayPropertiesFromParcel(Parcel parcel);

    private static native boolean nSupportMixedColorSpaces(long j);

    private static native void nWriteOverlayPropertiesToParcel(long j, Parcel parcel);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private OverlayProperties(long j) {
        if (j != 0) {
            this.mCloser = sRegistry.registerNativeAllocation(this, j);
        }
        this.mNativeObject = j;
    }

    public static OverlayProperties getDefault() {
        if (sDefaultOverlayProperties == null) {
            sDefaultOverlayProperties = new OverlayProperties(nCreateDefault());
        }
        return sDefaultOverlayProperties;
    }

    public LutProperties[] getLutProperties() {
        long j = this.mNativeObject;
        if (j == 0) {
            return null;
        }
        if (this.mLutProperties == null) {
            this.mLutProperties = nGetLutProperties(j);
        }
        return this.mLutProperties;
    }

    public boolean isCombinationSupported(int i, int i2) {
        long j = this.mNativeObject;
        if (j == 0) {
            return false;
        }
        return nIsCombinationSupported(j, i, i2);
    }

    public boolean isMixedColorSpacesSupported() {
        long j = this.mNativeObject;
        if (j == 0) {
            return false;
        }
        return nSupportMixedColorSpaces(j);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mNativeObject == 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            nWriteOverlayPropertiesToParcel(this.mNativeObject, parcel);
        }
    }
}

package android.hardware;

import android.os.Parcel;
import android.os.Parcelable;

import libcore.util.NativeAllocationRegistry;

public final class OverlayProperties implements Parcelable {
    private static OverlayProperties sDefaultOverlayProperties;
    private Runnable mCloser;
    private long mNativeObject;
    private static final NativeAllocationRegistry sRegistry =
            NativeAllocationRegistry.createMalloced(
                    OverlayProperties.class.getClassLoader(), nGetDestructor());
    public static final Parcelable.Creator<OverlayProperties> CREATOR =
            new Parcelable.Creator<
                    OverlayProperties>() { // from class: android.hardware.OverlayProperties.1
                @Override // android.os.Parcelable.Creator
                public OverlayProperties createFromParcel(Parcel in) {
                    if (in.readInt() != 0) {
                        return new OverlayProperties(
                                OverlayProperties.nReadOverlayPropertiesFromParcel(in));
                    }
                    return null;
                }

                @Override // android.os.Parcelable.Creator
                public OverlayProperties[] newArray(int size) {
                    return new OverlayProperties[size];
                }
            };

    private static native long nCreateDefault();

    private static native long nGetDestructor();

    private static native boolean nIsCombinationSupported(long j, int i, int i2);

    public static native long nReadOverlayPropertiesFromParcel(Parcel parcel);

    private static native boolean nSupportMixedColorSpaces(long j);

    private static native void nWriteOverlayPropertiesToParcel(long j, Parcel parcel);

    private OverlayProperties(long nativeObject) {
        if (nativeObject != 0) {
            this.mCloser = sRegistry.registerNativeAllocation(this, nativeObject);
        }
        this.mNativeObject = nativeObject;
    }

    public static OverlayProperties getDefault() {
        if (sDefaultOverlayProperties == null) {
            sDefaultOverlayProperties = new OverlayProperties(nCreateDefault());
        }
        return sDefaultOverlayProperties;
    }

    public boolean isCombinationSupported(int dataspace, int format) {
        if (this.mNativeObject == 0) {
            return false;
        }
        return nIsCombinationSupported(this.mNativeObject, dataspace, format);
    }

    public boolean isMixedColorSpacesSupported() {
        if (this.mNativeObject == 0) {
            return false;
        }
        return nSupportMixedColorSpaces(this.mNativeObject);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (this.mNativeObject == 0) {
            dest.writeInt(0);
        } else {
            dest.writeInt(1);
            nWriteOverlayPropertiesToParcel(this.mNativeObject, dest);
        }
    }
}

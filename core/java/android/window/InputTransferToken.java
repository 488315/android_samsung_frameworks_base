package android.window;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

import libcore.util.NativeAllocationRegistry;

import java.util.Objects;

public final class InputTransferToken implements Parcelable {
    public final long mNativeObject;
    private static final NativeAllocationRegistry sRegistry =
            NativeAllocationRegistry.createMalloced(
                    InputTransferToken.class.getClassLoader(),
                    nativeGetNativeInputTransferTokenFinalizer());
    public static final Parcelable.Creator<InputTransferToken> CREATOR =
            new Parcelable.Creator<
                    InputTransferToken>() { // from class: android.window.InputTransferToken.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public InputTransferToken createFromParcel(Parcel in) {
                    return new InputTransferToken(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public InputTransferToken[] newArray(int size) {
                    return new InputTransferToken[size];
                }
            };

    private static native long nativeCreate();

    private static native long nativeCreate(IBinder iBinder);

    private static native boolean nativeEquals(long j, long j2);

    private static native IBinder nativeGetBinderToken(long j);

    private static native long nativeGetBinderTokenRef(long j);

    private static native long nativeGetNativeInputTransferTokenFinalizer();

    private static native long nativeReadFromParcel(Parcel parcel);

    private static native void nativeWriteToParcel(long j, Parcel parcel);

    private InputTransferToken(long nativeObject) {
        this.mNativeObject = nativeObject;
        sRegistry.registerNativeAllocation(this, nativeObject);
    }

    public InputTransferToken(IBinder token) {
        this(nativeCreate(token));
    }

    public InputTransferToken() {
        this(nativeCreate());
    }

    public IBinder getToken() {
        return nativeGetBinderToken(this.mNativeObject);
    }

    private InputTransferToken(Parcel in) {
        this(nativeReadFromParcel(in));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        nativeWriteToParcel(this.mNativeObject, dest);
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(nativeGetBinderTokenRef(this.mNativeObject)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        InputTransferToken other = (InputTransferToken) obj;
        if (other.mNativeObject == this.mNativeObject) {
            return true;
        }
        return nativeEquals(this.mNativeObject, other.mNativeObject);
    }
}

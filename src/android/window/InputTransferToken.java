package android.window;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes5.dex */
public final class InputTransferToken implements Parcelable {
    public final long mNativeObject;
    private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(InputTransferToken.class.getClassLoader(), nativeGetNativeInputTransferTokenFinalizer());
    public static final Parcelable.Creator<InputTransferToken> CREATOR = new Parcelable.Creator<InputTransferToken>() { // from class: android.window.InputTransferToken.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputTransferToken createFromParcel(Parcel parcel) {
            return new InputTransferToken(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputTransferToken[] newArray(int i) {
            return new InputTransferToken[i];
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private InputTransferToken(long j) {
        this.mNativeObject = j;
        sRegistry.registerNativeAllocation(this, j);
    }

    public InputTransferToken(IBinder iBinder) {
        this(nativeCreate(iBinder));
    }

    public InputTransferToken() {
        this(nativeCreate());
    }

    public IBinder getToken() {
        return nativeGetBinderToken(this.mNativeObject);
    }

    private InputTransferToken(Parcel parcel) {
        this(nativeReadFromParcel(parcel));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        nativeWriteToParcel(this.mNativeObject, parcel);
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
        long j = ((InputTransferToken) obj).mNativeObject;
        long j2 = this.mNativeObject;
        if (j == j2) {
            return true;
        }
        return nativeEquals(j2, j);
    }
}

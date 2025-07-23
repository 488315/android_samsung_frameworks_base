package android.view;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes4.dex */
public final class InputChannel implements Parcelable {
    private static final boolean DEBUG = false;
    private static final String TAG = "InputChannel";
    private long mPtr;
    private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(InputChannel.class.getClassLoader(), nativeGetFinalizer());
    public static final Parcelable.Creator<InputChannel> CREATOR = new Parcelable.Creator<InputChannel>() { // from class: android.view.InputChannel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputChannel createFromParcel(Parcel parcel) {
            InputChannel inputChannel = new InputChannel();
            inputChannel.readFromParcel(parcel);
            return inputChannel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputChannel[] newArray(int i) {
            return new InputChannel[i];
        }
    };

    private native void nativeDispose(long j);

    private native long nativeDup(long j);

    private static native long nativeGetFinalizer();

    private native String nativeGetName(long j);

    private native IBinder nativeGetToken(long j);

    private static native long[] nativeOpenInputChannelPair(String str);

    private native long nativeReadFromParcel(Parcel parcel);

    private native void nativeWriteToParcel(Parcel parcel, long j);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 1;
    }

    public void release() {
    }

    private void setNativeInputChannel(long j) {
        if (j == 0) {
            throw new IllegalArgumentException("Attempting to set native input channel to null.");
        }
        if (this.mPtr != 0) {
            throw new IllegalArgumentException("Already has native input channel.");
        }
        sRegistry.registerNativeAllocation(this, j);
        this.mPtr = j;
    }

    public static InputChannel[] openInputChannelPair(String str) {
        if (str == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        InputChannel[] inputChannelArr = new InputChannel[2];
        long[] nativeOpenInputChannelPair = nativeOpenInputChannelPair(str);
        for (int i = 0; i < 2; i++) {
            InputChannel inputChannel = new InputChannel();
            inputChannelArr[i] = inputChannel;
            inputChannel.setNativeInputChannel(nativeOpenInputChannelPair[i]);
        }
        return inputChannelArr;
    }

    public String getName() {
        String nativeGetName = nativeGetName(this.mPtr);
        return nativeGetName != null ? nativeGetName : "uninitialized";
    }

    public void dispose() {
        nativeDispose(this.mPtr);
    }

    public void copyTo(InputChannel inputChannel) {
        if (inputChannel == null) {
            throw new IllegalArgumentException("outParameter must not be null");
        }
        if (inputChannel.mPtr != 0) {
            throw new IllegalArgumentException("Other object already has a native input channel.");
        }
        inputChannel.setNativeInputChannel(nativeDup(this.mPtr));
    }

    public InputChannel dup() {
        InputChannel inputChannel = new InputChannel();
        inputChannel.setNativeInputChannel(nativeDup(this.mPtr));
        return inputChannel;
    }

    public void readFromParcel(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("in must not be null");
        }
        long nativeReadFromParcel = nativeReadFromParcel(parcel);
        if (nativeReadFromParcel != 0) {
            setNativeInputChannel(nativeReadFromParcel);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            throw new IllegalArgumentException("out must not be null");
        }
        nativeWriteToParcel(parcel, this.mPtr);
        if ((i & 1) != 0) {
            dispose();
        }
    }

    public String toString() {
        return getName();
    }

    public IBinder getToken() {
        return nativeGetToken(this.mPtr);
    }
}

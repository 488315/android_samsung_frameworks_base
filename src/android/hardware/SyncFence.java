package android.hardware;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.time.Duration;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes2.dex */
public final class SyncFence implements AutoCloseable, Parcelable {
    public static final long SIGNAL_TIME_INVALID = -1;
    public static final long SIGNAL_TIME_PENDING = Long.MAX_VALUE;
    private final Runnable mCloser;
    private long mNativePtr;
    private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createNonmalloced(SyncFence.class.getClassLoader(), nGetDestructor(), 4);
    public static final Parcelable.Creator<SyncFence> CREATOR = new Parcelable.Creator<SyncFence>() { // from class: android.hardware.SyncFence.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SyncFence createFromParcel(Parcel parcel) {
            return new SyncFence(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SyncFence[] newArray(int i) {
            return new SyncFence[i];
        }
    };

    static /* synthetic */ void lambda$new$0() {
    }

    static /* synthetic */ void lambda$new$1() {
    }

    static /* synthetic */ void lambda$new$2() {
    }

    private static native long nCreate(int i);

    private static native long nGetDestructor();

    private static native int nGetFd(long j);

    private static native long nGetSignalTime(long j);

    private static native void nIncRef(long j);

    private static native boolean nIsValid(long j);

    private static native boolean nWait(long j, long j2);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 1;
    }

    private SyncFence(int i) {
        long nCreate = nCreate(i);
        this.mNativePtr = nCreate;
        this.mCloser = sRegistry.registerNativeAllocation(this, nCreate);
    }

    private SyncFence(Parcel parcel) {
        FileDescriptor readRawFileDescriptor = parcel.readBoolean() ? parcel.readRawFileDescriptor() : null;
        if (readRawFileDescriptor != null) {
            long nCreate = nCreate(readRawFileDescriptor.getInt$());
            this.mNativePtr = nCreate;
            this.mCloser = sRegistry.registerNativeAllocation(this, nCreate);
            return;
        }
        this.mCloser = new Runnable() { // from class: android.hardware.SyncFence$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SyncFence.lambda$new$0();
            }
        };
    }

    public SyncFence(long j) {
        this.mNativePtr = j;
        if (j != 0) {
            this.mCloser = sRegistry.registerNativeAllocation(this, j);
        } else {
            this.mCloser = new Runnable() { // from class: android.hardware.SyncFence$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SyncFence.lambda$new$1();
                }
            };
        }
    }

    public SyncFence(SyncFence syncFence) {
        this(syncFence.mNativePtr);
        long j = this.mNativePtr;
        if (j != 0) {
            nIncRef(j);
        }
    }

    private SyncFence() {
        this.mCloser = new Runnable() { // from class: android.hardware.SyncFence$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SyncFence.lambda$new$2();
            }
        };
    }

    public static SyncFence createEmpty() {
        return new SyncFence();
    }

    public static SyncFence create(ParcelFileDescriptor parcelFileDescriptor) {
        return new SyncFence(parcelFileDescriptor.detachFd());
    }

    public static SyncFence adopt(int i) {
        return new SyncFence(i);
    }

    public ParcelFileDescriptor getFdDup() throws IOException {
        ParcelFileDescriptor fromFd;
        synchronized (this.mCloser) {
            long j = this.mNativePtr;
            int nGetFd = j != 0 ? nGetFd(j) : -1;
            if (nGetFd == -1) {
                throw new IllegalStateException("Cannot dup the FD of an invalid SyncFence");
            }
            fromFd = ParcelFileDescriptor.fromFd(nGetFd);
        }
        return fromFd;
    }

    public boolean isValid() {
        boolean z;
        synchronized (this.mCloser) {
            long j = this.mNativePtr;
            z = j != 0 && nIsValid(j);
        }
        return z;
    }

    public boolean await(Duration duration) {
        return await(duration.isNegative() ? -1L : duration.toNanos());
    }

    public boolean awaitForever() {
        return await(-1L);
    }

    private boolean await(long j) {
        boolean z;
        synchronized (this.mCloser) {
            long j2 = this.mNativePtr;
            z = j2 != 0 && nWait(j2, j);
        }
        return z;
    }

    public long getSignalTime() {
        long nGetSignalTime;
        synchronized (this.mCloser) {
            long j = this.mNativePtr;
            nGetSignalTime = j != 0 ? nGetSignalTime(j) : -1L;
        }
        return nGetSignalTime;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mCloser) {
            if (this.mNativePtr == 0) {
                return;
            }
            this.mNativePtr = 0L;
            this.mCloser.run();
        }
    }

    public Object getLock() {
        return this.mCloser;
    }

    public long getNativeFence() {
        return this.mNativePtr;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        synchronized (this.mCloser) {
            long j = this.mNativePtr;
            int nGetFd = j != 0 ? nGetFd(j) : -1;
            if (nGetFd == -1) {
                parcel.writeBoolean(false);
            } else {
                parcel.writeBoolean(true);
                FileDescriptor fileDescriptor = new FileDescriptor();
                fileDescriptor.setInt$(nGetFd);
                parcel.writeFileDescriptor(fileDescriptor);
            }
        }
    }
}

package android.util;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import java.io.Closeable;
import java.io.IOException;
import java.util.UUID;
import libcore.io.IoUtils;

/* loaded from: classes4.dex */
public final class MemoryIntArray implements Parcelable, Closeable {
    public static final Parcelable.Creator<MemoryIntArray> CREATOR = new Parcelable.Creator<MemoryIntArray>() { // from class: android.util.MemoryIntArray.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MemoryIntArray createFromParcel(Parcel parcel) {
            try {
                return new MemoryIntArray(parcel);
            } catch (IOException unused) {
                throw new IllegalArgumentException("Error unparceling MemoryIntArray");
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MemoryIntArray[] newArray(int i) {
            return new MemoryIntArray[i];
        }
    };
    private static final int MAX_SIZE = 1024;
    private static final String TAG = "MemoryIntArray";
    private final dalvik.system.CloseGuard mCloseGuard;
    private int mFd;
    private final boolean mIsOwner;
    private final long mMemoryAddr;
    private final int mSize;

    public static int getMaxSize() {
        return 1024;
    }

    private native void nativeClose(int i, long j, boolean z);

    private native int nativeCreate(String str, int i);

    private native int nativeGet(int i, long j, int i2);

    private native long nativeOpen(int i, boolean z);

    private native void nativeSet(int i, long j, int i2, int i3);

    private native int nativeSize(int i);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 1;
    }

    public MemoryIntArray(int i) throws IOException {
        dalvik.system.CloseGuard closeGuard = dalvik.system.CloseGuard.get();
        this.mCloseGuard = closeGuard;
        this.mFd = -1;
        if (i > 1024) {
            throw new IllegalArgumentException("Max size is 1024");
        }
        this.mIsOwner = true;
        int nativeCreate = nativeCreate(UUID.randomUUID().toString(), i);
        this.mFd = nativeCreate;
        this.mMemoryAddr = nativeOpen(nativeCreate, true);
        this.mSize = nativeSize(this.mFd);
        closeGuard.open("MemoryIntArray.close");
    }

    private MemoryIntArray(Parcel parcel) throws IOException {
        dalvik.system.CloseGuard closeGuard = dalvik.system.CloseGuard.get();
        this.mCloseGuard = closeGuard;
        this.mFd = -1;
        this.mIsOwner = false;
        ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readParcelable(null, ParcelFileDescriptor.class);
        if (parcelFileDescriptor == null) {
            throw new IOException("No backing file descriptor");
        }
        int detachFd = parcelFileDescriptor.detachFd();
        this.mFd = detachFd;
        this.mMemoryAddr = nativeOpen(detachFd, false);
        this.mSize = nativeSize(this.mFd);
        closeGuard.open("MemoryIntArray.close");
    }

    public boolean isWritable() {
        enforceNotClosed();
        return this.mIsOwner;
    }

    public int get(int i) throws IOException {
        enforceNotClosed();
        enforceValidIndex(i);
        return nativeGet(this.mFd, this.mMemoryAddr, i);
    }

    public void set(int i, int i2) throws IOException {
        enforceNotClosed();
        enforceWritable();
        enforceValidIndex(i);
        nativeSet(this.mFd, this.mMemoryAddr, i, i2);
    }

    public int size() {
        enforceNotClosed();
        return this.mSize;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (isClosed()) {
            return;
        }
        nativeClose(this.mFd, this.mMemoryAddr, this.mIsOwner);
        this.mFd = -1;
        this.mCloseGuard.close();
    }

    public boolean isClosed() {
        return this.mFd == -1;
    }

    protected void finalize() throws Throwable {
        try {
            dalvik.system.CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            IoUtils.closeQuietly(this);
        } finally {
            super.finalize();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        try {
            ParcelFileDescriptor fromFd = ParcelFileDescriptor.fromFd(this.mFd);
            try {
                parcel.writeParcelable(fromFd, i);
                if (fromFd != null) {
                    fromFd.close();
                }
            } finally {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return getClass() == obj.getClass() && this.mFd == ((MemoryIntArray) obj).mFd;
    }

    public int hashCode() {
        return this.mFd;
    }

    private void enforceNotClosed() {
        if (isClosed()) {
            throw new IllegalStateException("cannot interact with a closed instance");
        }
    }

    private void enforceValidIndex(int i) {
        if (i < 0 || i > this.mSize - 1) {
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append(" not between 0 and ");
            sb.append(this.mSize - 1);
            throw new IndexOutOfBoundsException(sb.toString());
        }
    }

    private void enforceWritable() {
        if (!isWritable()) {
            throw new UnsupportedOperationException("array is not writable");
        }
    }
}

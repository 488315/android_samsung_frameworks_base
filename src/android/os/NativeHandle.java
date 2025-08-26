package android.os;

import android.annotation.SystemApi;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;

@SystemApi
/* loaded from: classes3.dex */
public final class NativeHandle implements Closeable {
    private FileDescriptor[] mFds;
    private int[] mInts;
    private boolean mOwn;

    public NativeHandle() {
        this(new FileDescriptor[0], new int[0], false);
    }

    public NativeHandle(FileDescriptor fileDescriptor, boolean z) {
        this(new FileDescriptor[]{fileDescriptor}, new int[0], z);
    }

    private static FileDescriptor[] createFileDescriptorArray(int[] iArr) {
        FileDescriptor[] fileDescriptorArr = new FileDescriptor[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            FileDescriptor fileDescriptor = new FileDescriptor();
            fileDescriptor.setInt$(iArr[i]);
            fileDescriptorArr[i] = fileDescriptor;
        }
        return fileDescriptorArr;
    }

    private NativeHandle(int[] iArr, int[] iArr2, boolean z) {
        this(createFileDescriptorArray(iArr), iArr2, z);
    }

    public NativeHandle(FileDescriptor[] fileDescriptorArr, int[] iArr, boolean z) {
        this.mOwn = false;
        this.mFds = (FileDescriptor[]) fileDescriptorArr.clone();
        this.mInts = (int[]) iArr.clone();
        this.mOwn = z;
    }

    public boolean hasSingleFileDescriptor() {
        checkOpen();
        return this.mFds.length == 1 && this.mInts.length == 0;
    }

    public NativeHandle dup() throws IOException {
        FileDescriptor[] fileDescriptorArr = new FileDescriptor[this.mFds.length];
        for (int i = 0; i < this.mFds.length; i++) {
            try {
                FileDescriptor fileDescriptor = new FileDescriptor();
                fileDescriptor.setInt$(Os.fcntlInt(this.mFds[i], OsConstants.F_DUPFD_CLOEXEC, 0));
                fileDescriptorArr[i] = fileDescriptor;
            } catch (ErrnoException e) {
                e.rethrowAsIOException();
            }
        }
        return new NativeHandle(fileDescriptorArr, this.mInts, true);
    }

    private void checkOpen() {
        if (this.mFds == null) {
            throw new IllegalStateException("NativeHandle is invalidated after close.");
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException, ErrnoException {
        checkOpen();
        if (this.mOwn) {
            try {
                for (FileDescriptor fileDescriptor : this.mFds) {
                    Os.close(fileDescriptor);
                }
            } catch (ErrnoException e) {
                e.rethrowAsIOException();
            }
            this.mOwn = false;
        }
        this.mFds = null;
        this.mInts = null;
    }

    public FileDescriptor getFileDescriptor() {
        checkOpen();
        if (!hasSingleFileDescriptor()) {
            throw new IllegalStateException("NativeHandle is not single file descriptor. Contents must be retreived through getFileDescriptors and getInts.");
        }
        return this.mFds[0];
    }

    private int[] getFdsAsIntArray() {
        checkOpen();
        int length = this.mFds.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = this.mFds[i].getInt$();
        }
        return iArr;
    }

    public FileDescriptor[] getFileDescriptors() {
        checkOpen();
        return this.mFds;
    }

    public int[] getInts() {
        checkOpen();
        return this.mInts;
    }
}

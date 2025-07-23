package android.os;

import android.system.ErrnoException;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class MemoryFile {
    private static String TAG = "MemoryFile";
    private boolean mAllowPurging = false;
    private ByteBuffer mMapping;
    private SharedMemory mSharedMemory;

    private static native int native_get_size(FileDescriptor fileDescriptor) throws IOException;

    private static native boolean native_pin(FileDescriptor fileDescriptor, boolean z) throws IOException;

    public MemoryFile(String str, int i) throws IOException {
        try {
            SharedMemory create = SharedMemory.create(str, i);
            this.mSharedMemory = create;
            this.mMapping = create.mapReadWrite();
        } catch (ErrnoException e) {
            e.rethrowAsIOException();
        }
    }

    public void close() {
        deactivate();
        this.mSharedMemory.close();
    }

    void deactivate() {
        ByteBuffer byteBuffer = this.mMapping;
        if (byteBuffer != null) {
            SharedMemory.unmap(byteBuffer);
            this.mMapping = null;
        }
    }

    private void checkActive() throws IOException {
        if (this.mMapping == null) {
            throw new IOException("MemoryFile has been deactivated");
        }
    }

    private void beginAccess() throws IOException {
        checkActive();
        if (this.mAllowPurging && native_pin(this.mSharedMemory.getFileDescriptor(), true)) {
            throw new IOException("MemoryFile has been purged");
        }
    }

    private void endAccess() throws IOException {
        if (this.mAllowPurging) {
            native_pin(this.mSharedMemory.getFileDescriptor(), false);
        }
    }

    public int length() {
        return this.mSharedMemory.getSize();
    }

    @Deprecated
    public boolean isPurgingAllowed() {
        return this.mAllowPurging;
    }

    @Deprecated
    public synchronized boolean allowPurging(boolean z) throws IOException {
        boolean z2;
        z2 = this.mAllowPurging;
        if (z2 != z) {
            native_pin(this.mSharedMemory.getFileDescriptor(), !z);
            this.mAllowPurging = z;
        }
        return z2;
    }

    public InputStream getInputStream() {
        return new MemoryInputStream();
    }

    public OutputStream getOutputStream() {
        return new MemoryOutputStream();
    }

    public int readBytes(byte[] bArr, int i, int i2, int i3) throws IOException {
        beginAccess();
        try {
            this.mMapping.position(i);
            this.mMapping.get(bArr, i2, i3);
            return i3;
        } finally {
            endAccess();
        }
    }

    public void writeBytes(byte[] bArr, int i, int i2, int i3) throws IOException {
        beginAccess();
        try {
            this.mMapping.position(i2);
            this.mMapping.put(bArr, i, i3);
        } finally {
            endAccess();
        }
    }

    public FileDescriptor getFileDescriptor() throws IOException {
        return this.mSharedMemory.getFileDescriptor();
    }

    public static int getSize(FileDescriptor fileDescriptor) throws IOException {
        return native_get_size(fileDescriptor);
    }

    private class MemoryInputStream extends InputStream {
        private int mMark;
        private int mOffset;
        private byte[] mSingleByte;

        @Override // java.io.InputStream
        public boolean markSupported() {
            return true;
        }

        private MemoryInputStream() {
            this.mMark = 0;
            this.mOffset = 0;
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            if (this.mOffset >= MemoryFile.this.mSharedMemory.getSize()) {
                return 0;
            }
            return MemoryFile.this.mSharedMemory.getSize() - this.mOffset;
        }

        @Override // java.io.InputStream
        public void mark(int i) {
            this.mMark = this.mOffset;
        }

        @Override // java.io.InputStream
        public void reset() throws IOException {
            this.mOffset = this.mMark;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            if (this.mSingleByte == null) {
                this.mSingleByte = new byte[1];
            }
            if (read(this.mSingleByte, 0, 1) != 1) {
                return -1;
            }
            return this.mSingleByte[0];
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            if (i < 0 || i2 < 0 || i + i2 > bArr.length) {
                throw new IndexOutOfBoundsException();
            }
            int min = Math.min(i2, available());
            if (min < 1) {
                return -1;
            }
            int readBytes = MemoryFile.this.readBytes(bArr, this.mOffset, i, min);
            if (readBytes > 0) {
                this.mOffset += readBytes;
            }
            return readBytes;
        }

        @Override // java.io.InputStream
        public long skip(long j) throws IOException {
            if (this.mOffset + j > MemoryFile.this.mSharedMemory.getSize()) {
                j = MemoryFile.this.mSharedMemory.getSize() - this.mOffset;
            }
            this.mOffset = (int) (this.mOffset + j);
            return j;
        }
    }

    private class MemoryOutputStream extends OutputStream {
        private int mOffset;
        private byte[] mSingleByte;

        private MemoryOutputStream() {
            this.mOffset = 0;
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            MemoryFile.this.writeBytes(bArr, i, this.mOffset, i2);
            this.mOffset += i2;
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            if (this.mSingleByte == null) {
                this.mSingleByte = new byte[1];
            }
            byte[] bArr = this.mSingleByte;
            bArr[0] = (byte) i;
            write(bArr, 0, 1);
        }
    }
}

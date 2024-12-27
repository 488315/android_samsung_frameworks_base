package android.content.res;

import android.os.Bundle;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/* loaded from: classes.dex */
public class AssetFileDescriptor implements Parcelable, Closeable {
    public static final Parcelable.Creator<AssetFileDescriptor> CREATOR = new Parcelable.Creator<AssetFileDescriptor>() { // from class: android.content.res.AssetFileDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssetFileDescriptor createFromParcel(Parcel in) {
            return new AssetFileDescriptor(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssetFileDescriptor[] newArray(int size) {
            return new AssetFileDescriptor[size];
        }
    };
    public static final long UNKNOWN_LENGTH = -1;
    private final Bundle mExtras;
    private final ParcelFileDescriptor mFd;
    private final long mLength;
    private final long mStartOffset;

    public AssetFileDescriptor(ParcelFileDescriptor fd, long startOffset, long length) {
        this(fd, startOffset, length, null);
    }

    public AssetFileDescriptor(ParcelFileDescriptor fd, long startOffset, long length, Bundle extras) {
        if (fd == null) {
            throw new IllegalArgumentException("fd must not be null");
        }
        if (length < 0 && startOffset != 0) {
            throw new IllegalArgumentException("startOffset must be 0 when using UNKNOWN_LENGTH");
        }
        this.mFd = fd;
        this.mStartOffset = startOffset;
        this.mLength = length;
        this.mExtras = extras;
    }

    public ParcelFileDescriptor getParcelFileDescriptor() {
        return this.mFd;
    }

    public FileDescriptor getFileDescriptor() {
        return this.mFd.getFileDescriptor();
    }

    public long getStartOffset() {
        return this.mStartOffset;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public long getLength() {
        if (this.mLength >= 0) {
            return this.mLength;
        }
        long len = this.mFd.getStatSize();
        if (len >= 0) {
            return len;
        }
        return -1L;
    }

    public long getDeclaredLength() {
        return this.mLength;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.mFd.close();
    }

    public FileInputStream createInputStream() throws IOException {
        if (this.mLength < 0) {
            return new ParcelFileDescriptor.AutoCloseInputStream(this.mFd);
        }
        return new AutoCloseInputStream(this);
    }

    public FileInputStream createKumihoInputStream() throws IOException {
        if (this.mLength < 0) {
            return new ParcelFileDescriptor.KumihoInputStream(this.mFd);
        }
        return new KumihoInputStream(this);
    }

    public FileOutputStream createOutputStream() throws IOException {
        if (this.mLength < 0) {
            return new ParcelFileDescriptor.AutoCloseOutputStream(this.mFd);
        }
        return new AutoCloseOutputStream(this);
    }

    public String toString() {
        return "{AssetFileDescriptor: " + this.mFd + " start=" + this.mStartOffset + " len=" + this.mLength + "}";
    }

    public static class AutoCloseInputStream extends ParcelFileDescriptor.AutoCloseInputStream {
        private ParcelFileDescriptor.AutoCloseInputStream mDelegateInputStream;

        public AutoCloseInputStream(AssetFileDescriptor fd) throws IOException {
            super(fd.getParcelFileDescriptor());
            try {
                StructStat ss = Os.fstat(fd.getParcelFileDescriptor().getFileDescriptor());
                if (OsConstants.S_ISSOCK(ss.st_mode) || OsConstants.S_ISFIFO(ss.st_mode)) {
                    this.mDelegateInputStream = new NonSeekableAutoCloseInputStream(fd);
                } else {
                    this.mDelegateInputStream = new SeekableAutoCloseInputStream(fd);
                }
            } catch (ErrnoException e) {
                throw new IOException(e);
            }
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public int available() throws IOException {
            return this.mDelegateInputStream.available();
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read() throws IOException {
            return this.mDelegateInputStream.read();
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read(byte[] buffer, int offset, int count) throws IOException {
            return this.mDelegateInputStream.read(buffer, offset, count);
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read(byte[] buffer) throws IOException {
            return this.mDelegateInputStream.read(buffer);
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public long skip(long count) throws IOException {
            return this.mDelegateInputStream.skip(count);
        }

        @Override // java.io.InputStream
        public void mark(int readlimit) {
            this.mDelegateInputStream.mark(readlimit);
        }

        @Override // java.io.InputStream
        public boolean markSupported() {
            return this.mDelegateInputStream.markSupported();
        }

        @Override // java.io.InputStream
        public synchronized void reset() throws IOException {
            this.mDelegateInputStream.reset();
        }

        @Override // java.io.FileInputStream
        public FileChannel getChannel() {
            return this.mDelegateInputStream.getChannel();
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.mDelegateInputStream.close();
        }
    }

    private static class NonSeekableAutoCloseInputStream extends ParcelFileDescriptor.AutoCloseInputStream {
        private long mRemaining;

        NonSeekableAutoCloseInputStream(AssetFileDescriptor fd) throws IOException {
            super(fd.getParcelFileDescriptor());
            super.skip(fd.getStartOffset());
            this.mRemaining = (int) fd.getLength();
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public int available() throws IOException {
            if (this.mRemaining < 0) {
                return super.available();
            }
            if (this.mRemaining < 2147483647L) {
                return (int) this.mRemaining;
            }
            return Integer.MAX_VALUE;
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read() throws IOException {
            byte[] buffer = new byte[1];
            int result = read(buffer, 0, 1);
            if (result != -1) {
                return buffer[0] & 255;
            }
            return -1;
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read(byte[] buffer, int offset, int count) throws IOException {
            if (this.mRemaining < 0) {
                return super.read(buffer, offset, count);
            }
            if (this.mRemaining == 0) {
                return -1;
            }
            if (count > this.mRemaining) {
                count = (int) this.mRemaining;
            }
            int res = super.read(buffer, offset, count);
            if (res >= 0) {
                this.mRemaining -= res;
            }
            return res;
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read(byte[] buffer) throws IOException {
            return read(buffer, 0, buffer.length);
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public long skip(long count) throws IOException {
            if (this.mRemaining < 0) {
                return super.skip(count);
            }
            if (this.mRemaining == 0) {
                return -1L;
            }
            if (count > this.mRemaining) {
                count = this.mRemaining;
            }
            long res = super.skip(count);
            if (res >= 0) {
                this.mRemaining -= res;
            }
            return res;
        }

        @Override // java.io.InputStream
        public void mark(int readlimit) {
            if (this.mRemaining >= 0) {
                return;
            }
            super.mark(readlimit);
        }

        @Override // java.io.InputStream
        public boolean markSupported() {
            if (this.mRemaining >= 0) {
                return false;
            }
            return super.markSupported();
        }

        @Override // java.io.InputStream
        public synchronized void reset() throws IOException {
            if (this.mRemaining >= 0) {
                return;
            }
            super.reset();
        }
    }

    public static class KumihoInputStream extends AutoCloseInputStream {
        public KumihoInputStream(AssetFileDescriptor fd) throws IOException {
            super(fd);
        }
    }

    private static class SeekableAutoCloseInputStream extends ParcelFileDescriptor.AutoCloseInputStream {
        private final long mFileOffset;
        private long mOffset;
        private OffsetCorrectFileChannel mOffsetCorrectFileChannel;
        private long mTotalSize;

        SeekableAutoCloseInputStream(AssetFileDescriptor fd) throws IOException {
            super(fd.getParcelFileDescriptor());
            this.mTotalSize = fd.getLength();
            this.mFileOffset = fd.getStartOffset();
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public int available() throws IOException {
            long available = this.mTotalSize - this.mOffset;
            if (available < 0) {
                return 0;
            }
            if (available < 2147483647L) {
                return (int) available;
            }
            return Integer.MAX_VALUE;
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read() throws IOException {
            byte[] buffer = new byte[1];
            int result = read(buffer, 0, 1);
            if (result != -1) {
                return buffer[0] & 255;
            }
            return -1;
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read(byte[] buffer, int offset, int count) throws IOException {
            int available = available();
            if (available <= 0) {
                return -1;
            }
            if (count == 0) {
                return 0;
            }
            if (count > available) {
                count = available;
            }
            try {
                int res = Os.pread(getFD(), buffer, offset, count, this.mFileOffset + this.mOffset);
                if (res == 0) {
                    res = -1;
                }
                if (res > 0) {
                    this.mOffset += res;
                    updateChannelPosition(this.mOffset + this.mFileOffset);
                }
                return res;
            } catch (ErrnoException e) {
                throw new IOException(e);
            }
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read(byte[] buffer) throws IOException {
            return read(buffer, 0, buffer.length);
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public long skip(long count) throws IOException {
            int available = available();
            if (available <= 0) {
                return -1L;
            }
            if (count > available) {
                count = available;
            }
            this.mOffset += count;
            updateChannelPosition(this.mOffset + this.mFileOffset);
            return count;
        }

        @Override // java.io.InputStream
        public void mark(int readlimit) {
        }

        @Override // java.io.InputStream
        public boolean markSupported() {
            return false;
        }

        @Override // java.io.InputStream
        public synchronized void reset() throws IOException {
        }

        @Override // java.io.FileInputStream
        public FileChannel getChannel() {
            if (this.mOffsetCorrectFileChannel == null) {
                this.mOffsetCorrectFileChannel = new OffsetCorrectFileChannel(super.getChannel());
            }
            try {
                updateChannelPosition(this.mOffset + this.mFileOffset);
                return this.mOffsetCorrectFileChannel;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void updateChannelPosition(long newPosition) throws IOException {
            if (this.mOffsetCorrectFileChannel != null) {
                this.mOffsetCorrectFileChannel.position(newPosition);
            }
        }

        private class OffsetCorrectFileChannel extends FileChannel {
            private static final String METHOD_NOT_SUPPORTED_MESSAGE = "This Method is not supported in AutoCloseInputStream FileChannel.";
            private final FileChannel mDelegate;

            OffsetCorrectFileChannel(FileChannel fc) {
                this.mDelegate = fc;
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel, java.nio.channels.ReadableByteChannel
            public int read(ByteBuffer dst) throws IOException {
                if (SeekableAutoCloseInputStream.this.available() <= 0) {
                    return -1;
                }
                int bytesRead = this.mDelegate.read(dst);
                if (bytesRead != -1) {
                    SeekableAutoCloseInputStream.this.mOffset += bytesRead;
                }
                return bytesRead;
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.ScatteringByteChannel
            public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
                if (SeekableAutoCloseInputStream.this.available() <= 0) {
                    return -1L;
                }
                if (SeekableAutoCloseInputStream.this.mOffset + length > SeekableAutoCloseInputStream.this.mTotalSize) {
                    length = (int) (SeekableAutoCloseInputStream.this.mTotalSize - SeekableAutoCloseInputStream.this.mOffset);
                }
                long bytesRead = this.mDelegate.read(dsts, offset, length);
                if (bytesRead != -1) {
                    SeekableAutoCloseInputStream.this.mOffset += bytesRead;
                }
                return bytesRead;
            }

            @Override // java.nio.channels.FileChannel
            public int read(ByteBuffer dst, long position) throws IOException {
                if (position - SeekableAutoCloseInputStream.this.mFileOffset > SeekableAutoCloseInputStream.this.mTotalSize) {
                    return -1;
                }
                return this.mDelegate.read(dst, position);
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel
            public long position() throws IOException {
                return this.mDelegate.position();
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel
            public FileChannel position(long newPosition) throws IOException {
                SeekableAutoCloseInputStream.this.mOffset = newPosition - SeekableAutoCloseInputStream.this.mFileOffset;
                return this.mDelegate.position(newPosition);
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel
            public long size() throws IOException {
                return SeekableAutoCloseInputStream.this.mTotalSize;
            }

            @Override // java.nio.channels.FileChannel
            public long transferTo(long position, long count, WritableByteChannel target) throws IOException {
                if (position - SeekableAutoCloseInputStream.this.mFileOffset > SeekableAutoCloseInputStream.this.mTotalSize) {
                    return 0L;
                }
                if ((position - SeekableAutoCloseInputStream.this.mFileOffset) + count > SeekableAutoCloseInputStream.this.mTotalSize) {
                    count = SeekableAutoCloseInputStream.this.mTotalSize - (position - SeekableAutoCloseInputStream.this.mFileOffset);
                }
                return this.mDelegate.transferTo(position, count, target);
            }

            @Override // java.nio.channels.FileChannel
            public MappedByteBuffer map(FileChannel.MapMode mode, long position, long size) throws IOException {
                if (position - SeekableAutoCloseInputStream.this.mFileOffset > SeekableAutoCloseInputStream.this.mTotalSize) {
                    throw new IOException("Cannot map to buffer because position exceed current file size.");
                }
                if ((position - SeekableAutoCloseInputStream.this.mFileOffset) + size > SeekableAutoCloseInputStream.this.mTotalSize) {
                    size = SeekableAutoCloseInputStream.this.mTotalSize - (position - SeekableAutoCloseInputStream.this.mFileOffset);
                }
                return this.mDelegate.map(mode, position, size);
            }

            @Override // java.nio.channels.spi.AbstractInterruptibleChannel
            protected void implCloseChannel() throws IOException {
                this.mDelegate.close();
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel, java.nio.channels.WritableByteChannel
            public int write(ByteBuffer src) throws IOException {
                throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.GatheringByteChannel
            public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
                throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel
            public int write(ByteBuffer src, long position) throws IOException {
                throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel
            public long transferFrom(ReadableByteChannel src, long position, long count) throws IOException {
                throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel
            public FileChannel truncate(long size) throws IOException {
                throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel
            public void force(boolean metaData) throws IOException {
                throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel
            public FileLock lock(long position, long size, boolean shared) throws IOException {
                throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel
            public FileLock tryLock(long position, long size, boolean shared) throws IOException {
                throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }
        }
    }

    public static class AutoCloseOutputStream extends ParcelFileDescriptor.AutoCloseOutputStream {
        private long mRemaining;

        public AutoCloseOutputStream(AssetFileDescriptor fd) throws IOException {
            super(fd.getParcelFileDescriptor());
            if (fd.getParcelFileDescriptor().seekTo(fd.getStartOffset()) < 0) {
                throw new IOException("Unable to seek");
            }
            this.mRemaining = (int) fd.getLength();
        }

        @Override // java.io.FileOutputStream, java.io.OutputStream
        public void write(byte[] buffer, int offset, int count) throws IOException {
            if (this.mRemaining < 0) {
                super.write(buffer, offset, count);
            } else {
                if (this.mRemaining == 0) {
                    return;
                }
                if (count > this.mRemaining) {
                    count = (int) this.mRemaining;
                }
                super.write(buffer, offset, count);
                this.mRemaining -= count;
            }
        }

        @Override // java.io.FileOutputStream, java.io.OutputStream
        public void write(byte[] buffer) throws IOException {
            if (this.mRemaining < 0) {
                super.write(buffer);
                return;
            }
            if (this.mRemaining == 0) {
                return;
            }
            int count = buffer.length;
            if (count > this.mRemaining) {
                count = (int) this.mRemaining;
            }
            super.write(buffer);
            this.mRemaining -= count;
        }

        @Override // java.io.FileOutputStream, java.io.OutputStream
        public void write(int oneByte) throws IOException {
            if (this.mRemaining < 0) {
                super.write(oneByte);
            } else {
                if (this.mRemaining == 0) {
                    return;
                }
                super.write(oneByte);
                this.mRemaining--;
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return this.mFd.describeContents();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        this.mFd.writeToParcel(out, flags);
        out.writeLong(this.mStartOffset);
        out.writeLong(this.mLength);
        if (this.mExtras != null) {
            out.writeInt(1);
            out.writeBundle(this.mExtras);
        } else {
            out.writeInt(0);
        }
    }

    AssetFileDescriptor(Parcel src) {
        this.mFd = ParcelFileDescriptor.CREATOR.createFromParcel(src);
        this.mStartOffset = src.readLong();
        this.mLength = src.readLong();
        if (src.readInt() != 0) {
            this.mExtras = src.readBundle();
        } else {
            this.mExtras = null;
        }
    }
}

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
        public AssetFileDescriptor createFromParcel(Parcel parcel) {
            return new AssetFileDescriptor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssetFileDescriptor[] newArray(int i) {
            return new AssetFileDescriptor[i];
        }
    };
    public static final long UNKNOWN_LENGTH = -1;
    private final Bundle mExtras;
    private final ParcelFileDescriptor mFd;
    private final long mLength;
    private final long mStartOffset;

    public AssetFileDescriptor(ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
        this(parcelFileDescriptor, j, j2, null);
    }

    public AssetFileDescriptor(ParcelFileDescriptor parcelFileDescriptor, long j, long j2, Bundle bundle) {
        if (parcelFileDescriptor == null) {
            throw new IllegalArgumentException("fd must not be null");
        }
        if (j2 < 0 && j != 0) {
            throw new IllegalArgumentException("startOffset must be 0 when using UNKNOWN_LENGTH");
        }
        this.mFd = parcelFileDescriptor;
        this.mStartOffset = j;
        this.mLength = j2;
        this.mExtras = bundle;
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

    public long getLength() throws ErrnoException {
        long j = this.mLength;
        if (j >= 0) {
            return j;
        }
        long statSize = this.mFd.getStatSize();
        if (statSize >= 0) {
            return statSize;
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

        public AutoCloseInputStream(AssetFileDescriptor assetFileDescriptor) throws IOException, ErrnoException {
            super(assetFileDescriptor.getParcelFileDescriptor());
            try {
                StructStat structStatFstat = Os.fstat(assetFileDescriptor.getParcelFileDescriptor().getFileDescriptor());
                if (OsConstants.S_ISSOCK(structStatFstat.st_mode) || OsConstants.S_ISFIFO(structStatFstat.st_mode)) {
                    this.mDelegateInputStream = new NonSeekableAutoCloseInputStream(assetFileDescriptor);
                } else {
                    this.mDelegateInputStream = new SeekableAutoCloseInputStream(assetFileDescriptor);
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
        public int read(byte[] bArr, int i, int i2) throws IOException {
            return this.mDelegateInputStream.read(bArr, i, i2);
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read(byte[] bArr) throws IOException {
            return this.mDelegateInputStream.read(bArr);
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public long skip(long j) throws IOException {
            return this.mDelegateInputStream.skip(j);
        }

        @Override // java.io.InputStream
        public void mark(int i) {
            this.mDelegateInputStream.mark(i);
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

        NonSeekableAutoCloseInputStream(AssetFileDescriptor assetFileDescriptor) throws IOException {
            super(assetFileDescriptor.getParcelFileDescriptor());
            skipRaw(assetFileDescriptor.getStartOffset());
            this.mRemaining = (int) assetFileDescriptor.getLength();
        }

        private long skipRaw(long j) throws IOException {
            return super.skip(j);
        }

        private long skipRaw$ravenwood(long j) throws IOException {
            int i;
            int iMin = (int) Math.min(1024L, j);
            byte[] bArr = new byte[iMin];
            long j2 = 0;
            while (j2 < j && (i = super.read(bArr, 0, (int) Math.min(j - j2, iMin))) != -1) {
                j2 += i;
            }
            return j2;
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public int available() throws IOException {
            long j = this.mRemaining;
            if (j < 0) {
                return super.available();
            }
            if (j < 2147483647L) {
                return (int) j;
            }
            return Integer.MAX_VALUE;
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read() throws IOException {
            byte[] bArr = new byte[1];
            if (read(bArr, 0, 1) == -1) {
                return -1;
            }
            return bArr[0] & 255;
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            long j = this.mRemaining;
            if (j < 0) {
                return super.read(bArr, i, i2);
            }
            if (j == 0) {
                return -1;
            }
            if (i2 > j) {
                i2 = (int) j;
            }
            int i3 = super.read(bArr, i, i2);
            if (i3 >= 0) {
                this.mRemaining -= i3;
            }
            return i3;
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read(byte[] bArr) throws IOException {
            return read(bArr, 0, bArr.length);
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public long skip(long j) throws IOException {
            long j2 = this.mRemaining;
            if (j2 < 0) {
                return skipRaw(j);
            }
            if (j2 == 0) {
                return -1L;
            }
            if (j > j2) {
                j = j2;
            }
            long jSkipRaw = skipRaw(j);
            if (jSkipRaw >= 0) {
                this.mRemaining -= jSkipRaw;
            }
            return jSkipRaw;
        }

        @Override // java.io.InputStream
        public void mark(int i) {
            if (this.mRemaining >= 0) {
                return;
            }
            super.mark(i);
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
        public KumihoInputStream(AssetFileDescriptor assetFileDescriptor) throws IOException {
            super(assetFileDescriptor);
        }
    }

    private static class SeekableAutoCloseInputStream extends ParcelFileDescriptor.AutoCloseInputStream {
        private final long mFileOffset;
        private long mOffset;
        private OffsetCorrectFileChannel mOffsetCorrectFileChannel;
        private long mTotalSize;

        @Override // java.io.InputStream
        public void mark(int i) {
        }

        @Override // java.io.InputStream
        public boolean markSupported() {
            return false;
        }

        SeekableAutoCloseInputStream(AssetFileDescriptor assetFileDescriptor) throws IOException {
            super(assetFileDescriptor.getParcelFileDescriptor());
            this.mTotalSize = assetFileDescriptor.getLength();
            this.mFileOffset = assetFileDescriptor.getStartOffset();
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public int available() throws IOException {
            long j = this.mTotalSize - this.mOffset;
            if (j < 0) {
                return 0;
            }
            if (j < 2147483647L) {
                return (int) j;
            }
            return Integer.MAX_VALUE;
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read() throws IOException {
            byte[] bArr = new byte[1];
            if (read(bArr, 0, 1) == -1) {
                return -1;
            }
            return bArr[0] & 255;
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException, ErrnoException {
            int iAvailable = available();
            int i3 = -1;
            if (iAvailable <= 0) {
                return -1;
            }
            if (i2 == 0) {
                return 0;
            }
            try {
                int iPread = Os.pread(getFD(), bArr, i, i2 > iAvailable ? iAvailable : i2, this.mOffset + this.mFileOffset);
                if (iPread != 0) {
                    i3 = iPread;
                }
                if (i3 > 0) {
                    long j = this.mOffset + i3;
                    this.mOffset = j;
                    updateChannelPosition(j + this.mFileOffset);
                }
                return i3;
            } catch (ErrnoException e) {
                throw new IOException(e);
            }
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read(byte[] bArr) throws IOException {
            return read(bArr, 0, bArr.length);
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public long skip(long j) throws IOException {
            int iAvailable = available();
            if (iAvailable <= 0) {
                return -1L;
            }
            long j2 = iAvailable;
            if (j > j2) {
                j = j2;
            }
            long j3 = this.mOffset + j;
            this.mOffset = j3;
            updateChannelPosition(j3 + this.mFileOffset);
            return j;
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

        private void updateChannelPosition(long j) throws IOException {
            OffsetCorrectFileChannel offsetCorrectFileChannel = this.mOffsetCorrectFileChannel;
            if (offsetCorrectFileChannel != null) {
                offsetCorrectFileChannel.position(j);
            }
        }

        private class OffsetCorrectFileChannel extends FileChannel {
            private static final String METHOD_NOT_SUPPORTED_MESSAGE = "This Method is not supported in AutoCloseInputStream FileChannel.";
            private final FileChannel mDelegate;

            OffsetCorrectFileChannel(FileChannel fileChannel) {
                this.mDelegate = fileChannel;
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel, java.nio.channels.ReadableByteChannel
            public int read(ByteBuffer byteBuffer) throws IOException {
                if (SeekableAutoCloseInputStream.this.available() <= 0) {
                    return -1;
                }
                int i = this.mDelegate.read(byteBuffer);
                if (i != -1) {
                    SeekableAutoCloseInputStream.this.mOffset += i;
                }
                return i;
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.ScatteringByteChannel
            public long read(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException {
                if (SeekableAutoCloseInputStream.this.available() <= 0) {
                    return -1L;
                }
                if (SeekableAutoCloseInputStream.this.mOffset + i2 > SeekableAutoCloseInputStream.this.mTotalSize) {
                    i2 = (int) (SeekableAutoCloseInputStream.this.mTotalSize - SeekableAutoCloseInputStream.this.mOffset);
                }
                long j = this.mDelegate.read(byteBufferArr, i, i2);
                if (j != -1) {
                    SeekableAutoCloseInputStream.this.mOffset += j;
                }
                return j;
            }

            @Override // java.nio.channels.FileChannel
            public int read(ByteBuffer byteBuffer, long j) throws IOException {
                if (j - SeekableAutoCloseInputStream.this.mFileOffset > SeekableAutoCloseInputStream.this.mTotalSize) {
                    return -1;
                }
                return this.mDelegate.read(byteBuffer, j);
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel
            public long position() throws IOException {
                return this.mDelegate.position();
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel
            public FileChannel position(long j) throws IOException {
                SeekableAutoCloseInputStream seekableAutoCloseInputStream = SeekableAutoCloseInputStream.this;
                seekableAutoCloseInputStream.mOffset = j - seekableAutoCloseInputStream.mFileOffset;
                return this.mDelegate.position(j);
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel
            public long size() throws IOException {
                return SeekableAutoCloseInputStream.this.mTotalSize;
            }

            @Override // java.nio.channels.FileChannel
            public long transferTo(long j, long j2, WritableByteChannel writableByteChannel) throws IOException {
                if (j - SeekableAutoCloseInputStream.this.mFileOffset > SeekableAutoCloseInputStream.this.mTotalSize) {
                    return 0L;
                }
                if ((j - SeekableAutoCloseInputStream.this.mFileOffset) + j2 > SeekableAutoCloseInputStream.this.mTotalSize) {
                    j2 = SeekableAutoCloseInputStream.this.mTotalSize - (j - SeekableAutoCloseInputStream.this.mFileOffset);
                }
                return this.mDelegate.transferTo(j, j2, writableByteChannel);
            }

            @Override // java.nio.channels.FileChannel
            public MappedByteBuffer map(FileChannel.MapMode mapMode, long j, long j2) throws IOException {
                if (j - SeekableAutoCloseInputStream.this.mFileOffset > SeekableAutoCloseInputStream.this.mTotalSize) {
                    throw new IOException("Cannot map to buffer because position exceed current file size.");
                }
                if ((j - SeekableAutoCloseInputStream.this.mFileOffset) + j2 > SeekableAutoCloseInputStream.this.mTotalSize) {
                    j2 = SeekableAutoCloseInputStream.this.mTotalSize - (j - SeekableAutoCloseInputStream.this.mFileOffset);
                }
                return this.mDelegate.map(mapMode, j, j2);
            }

            @Override // java.nio.channels.spi.AbstractInterruptibleChannel
            protected void implCloseChannel() throws IOException {
                this.mDelegate.close();
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel, java.nio.channels.WritableByteChannel
            public int write(ByteBuffer byteBuffer) throws IOException {
                throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.GatheringByteChannel
            public long write(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException {
                throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel
            public int write(ByteBuffer byteBuffer, long j) throws IOException {
                throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel
            public long transferFrom(ReadableByteChannel readableByteChannel, long j, long j2) throws IOException {
                throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel
            public FileChannel truncate(long j) throws IOException {
                throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel
            public void force(boolean z) throws IOException {
                throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel
            public FileLock lock(long j, long j2, boolean z) throws IOException {
                throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel
            public FileLock tryLock(long j, long j2, boolean z) throws IOException {
                throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }
        }
    }

    public static class AutoCloseOutputStream extends ParcelFileDescriptor.AutoCloseOutputStream {
        private long mRemaining;

        public AutoCloseOutputStream(AssetFileDescriptor assetFileDescriptor) throws IOException {
            super(assetFileDescriptor.getParcelFileDescriptor());
            if (assetFileDescriptor.getParcelFileDescriptor().seekTo(assetFileDescriptor.getStartOffset()) < 0) {
                throw new IOException("Unable to seek");
            }
            this.mRemaining = (int) assetFileDescriptor.getLength();
        }

        @Override // java.io.FileOutputStream, java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            long j = this.mRemaining;
            if (j < 0) {
                super.write(bArr, i, i2);
            } else {
                if (j == 0) {
                    return;
                }
                if (i2 > j) {
                    i2 = (int) j;
                }
                super.write(bArr, i, i2);
                this.mRemaining -= i2;
            }
        }

        @Override // java.io.FileOutputStream, java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            long j = this.mRemaining;
            if (j < 0) {
                super.write(bArr);
                return;
            }
            if (j == 0) {
                return;
            }
            int length = bArr.length;
            if (length > j) {
                length = (int) j;
            }
            super.write(bArr);
            this.mRemaining -= length;
        }

        @Override // java.io.FileOutputStream, java.io.OutputStream
        public void write(int i) throws IOException {
            long j = this.mRemaining;
            if (j < 0) {
                super.write(i);
            } else {
                if (j == 0) {
                    return;
                }
                super.write(i);
                this.mRemaining--;
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return this.mFd.describeContents();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mFd.writeToParcel(parcel, i);
        parcel.writeLong(this.mStartOffset);
        parcel.writeLong(this.mLength);
        if (this.mExtras != null) {
            parcel.writeInt(1);
            parcel.writeBundle(this.mExtras);
        } else {
            parcel.writeInt(0);
        }
    }

    AssetFileDescriptor(Parcel parcel) {
        this.mFd = ParcelFileDescriptor.CREATOR.createFromParcel(parcel);
        this.mStartOffset = parcel.readLong();
        this.mLength = parcel.readLong();
        if (parcel.readInt() != 0) {
            this.mExtras = parcel.readBundle();
        } else {
            this.mExtras = null;
        }
    }
}

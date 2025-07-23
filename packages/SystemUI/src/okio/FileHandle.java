package okio;

import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import com.sec.ims.presence.ServiceTuple;
import java.io.Closeable;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class FileHandle implements Closeable {
    public boolean closed;
    public final ReentrantLock lock = new ReentrantLock();
    public int openStreamCount;
    public final boolean readWrite;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FileHandleSink implements Sink {
        public boolean closed;
        public final FileHandle fileHandle;
        public long position;

        public FileHandleSink(FileHandle fileHandle, long j) {
            this.fileHandle = fileHandle;
            this.position = j;
        }

        @Override // okio.Sink, java.lang.AutoCloseable, java.nio.channels.Channel
        public final void close() {
            if (this.closed) {
                return;
            }
            this.closed = true;
            ReentrantLock reentrantLock = this.fileHandle.lock;
            reentrantLock.lock();
            try {
                FileHandle fileHandle = this.fileHandle;
                int i = fileHandle.openStreamCount - 1;
                fileHandle.openStreamCount = i;
                if (i == 0 && fileHandle.closed) {
                    Unit unit = Unit.INSTANCE;
                    reentrantLock.unlock();
                    this.fileHandle.protectedClose();
                }
            } finally {
                reentrantLock.unlock();
            }
        }

        @Override // okio.Sink, java.io.Flushable
        public final void flush() {
            if (this.closed) {
                throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
            }
            this.fileHandle.protectedFlush();
        }

        @Override // okio.Sink
        public final void write(Buffer buffer, long j) {
            if (this.closed) {
                throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
            }
            FileHandle fileHandle = this.fileHandle;
            long j2 = this.position;
            fileHandle.getClass();
            SegmentedByteString.checkOffsetAndCount(buffer.size, 0L, j);
            long j3 = j2 + j;
            while (j2 < j3) {
                Segment segment = buffer.head;
                segment.getClass();
                int min = (int) Math.min(j3 - j2, segment.limit - segment.pos);
                fileHandle.protectedWrite(j2, segment.data, segment.pos, min);
                int i = segment.pos + min;
                segment.pos = i;
                long j4 = min;
                j2 += j4;
                buffer.size -= j4;
                if (i == segment.limit) {
                    buffer.head = segment.pop();
                    SegmentPool.recycle(segment);
                }
            }
            this.position += j;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FileHandleSource implements Source {
        public boolean closed;
        public final FileHandle fileHandle;
        public long position;

        public FileHandleSource(FileHandle fileHandle, long j) {
            this.fileHandle = fileHandle;
            this.position = j;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public final void close() {
            if (this.closed) {
                return;
            }
            this.closed = true;
            ReentrantLock reentrantLock = this.fileHandle.lock;
            reentrantLock.lock();
            try {
                FileHandle fileHandle = this.fileHandle;
                int i = fileHandle.openStreamCount - 1;
                fileHandle.openStreamCount = i;
                if (i == 0 && fileHandle.closed) {
                    Unit unit = Unit.INSTANCE;
                    reentrantLock.unlock();
                    this.fileHandle.protectedClose();
                }
            } finally {
                reentrantLock.unlock();
            }
        }

        @Override // okio.Source
        public final long read(Buffer buffer, long j) {
            long j2;
            long j3;
            if (this.closed) {
                throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
            }
            FileHandle fileHandle = this.fileHandle;
            long j4 = this.position;
            fileHandle.getClass();
            if (j < 0) {
                throw new IllegalArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("byteCount < 0: ", j).toString());
            }
            long j5 = j + j4;
            long j6 = j4;
            while (true) {
                if (j6 >= j5) {
                    j2 = -1;
                    break;
                }
                Segment writableSegment$external__okio__android_common__okio_lib = buffer.writableSegment$external__okio__android_common__okio_lib(1);
                j2 = -1;
                long j7 = j5;
                int protectedRead = fileHandle.protectedRead(j6, writableSegment$external__okio__android_common__okio_lib.data, writableSegment$external__okio__android_common__okio_lib.limit, (int) Math.min(j5 - j6, 8192 - r9));
                if (protectedRead == -1) {
                    if (writableSegment$external__okio__android_common__okio_lib.pos == writableSegment$external__okio__android_common__okio_lib.limit) {
                        buffer.head = writableSegment$external__okio__android_common__okio_lib.pop();
                        SegmentPool.recycle(writableSegment$external__okio__android_common__okio_lib);
                    }
                    if (j4 == j6) {
                        j3 = -1;
                    }
                } else {
                    writableSegment$external__okio__android_common__okio_lib.limit += protectedRead;
                    long j8 = protectedRead;
                    j6 += j8;
                    buffer.size += j8;
                    j5 = j7;
                }
            }
            j3 = j6 - j4;
            if (j3 != j2) {
                this.position += j3;
            }
            return j3;
        }
    }

    public FileHandle(boolean z) {
        this.readWrite = z;
    }

    public static FileHandleSink sink$default(FileHandle fileHandle) {
        if (!fileHandle.readWrite) {
            throw new IllegalStateException("file handle is read-only");
        }
        ReentrantLock reentrantLock = fileHandle.lock;
        reentrantLock.lock();
        try {
            if (fileHandle.closed) {
                throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
            }
            fileHandle.openStreamCount++;
            reentrantLock.unlock();
            return new FileHandleSink(fileHandle, 0L);
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            if (this.closed) {
                return;
            }
            this.closed = true;
            if (this.openStreamCount != 0) {
                return;
            }
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            protectedClose();
        } finally {
            reentrantLock.unlock();
        }
    }

    public final void flush() {
        if (!this.readWrite) {
            throw new IllegalStateException("file handle is read-only");
        }
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            if (this.closed) {
                throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
            }
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            protectedFlush();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    public abstract void protectedClose();

    public abstract void protectedFlush();

    public abstract int protectedRead(long j, byte[] bArr, int i, int i2);

    public abstract long protectedSize();

    public abstract void protectedWrite(long j, byte[] bArr, int i, int i2);

    public final long size() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            if (this.closed) {
                throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
            }
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            return protectedSize();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    public final FileHandleSource source(long j) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            if (this.closed) {
                throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
            }
            this.openStreamCount++;
            reentrantLock.unlock();
            return new FileHandleSource(this, j);
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }
}

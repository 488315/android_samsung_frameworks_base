package okio;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.io.Closeable;
import java.io.EOFException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import kotlin.collections.ArraysKt___ArraysJvmKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Buffer implements BufferedSource, Sink, WritableByteChannel, Cloneable, ByteChannel {
    public Segment head;
    public long size;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class UnsafeCursor implements Closeable {
        @Override // java.io.Closeable, java.lang.AutoCloseable
        public final void close() {
            throw new IllegalStateException("not attached to a buffer");
        }
    }

    public final Object clone() {
        Buffer buffer = new Buffer();
        if (this.size == 0) {
            return buffer;
        }
        Segment segment = this.head;
        segment.getClass();
        Segment sharedCopy = segment.sharedCopy();
        buffer.head = sharedCopy;
        sharedCopy.prev = sharedCopy;
        sharedCopy.next = sharedCopy;
        for (Segment segment2 = segment.next; segment2 != segment; segment2 = segment2.next) {
            Segment segment3 = sharedCopy.prev;
            segment3.getClass();
            segment2.getClass();
            segment3.push(segment2.sharedCopy());
        }
        buffer.size = this.size;
        return buffer;
    }

    public final boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        boolean z2 = false;
        if (!(obj instanceof Buffer)) {
            return false;
        }
        long j = this.size;
        Buffer buffer = (Buffer) obj;
        if (j != buffer.size) {
            return false;
        }
        if (j == 0) {
            return true;
        }
        Segment segment = this.head;
        segment.getClass();
        Segment segment2 = buffer.head;
        segment2.getClass();
        int i = segment.pos;
        int i2 = segment2.pos;
        long j2 = 0;
        while (j2 < this.size) {
            long min = Math.min(segment.limit - i, segment2.limit - i2);
            long j3 = 0;
            while (j3 < min) {
                int i3 = i + 1;
                boolean z3 = z;
                byte b = segment.data[i];
                int i4 = i2 + 1;
                boolean z4 = z2;
                if (b != segment2.data[i2]) {
                    return z4;
                }
                j3++;
                i2 = i4;
                i = i3;
                z = z3;
                z2 = z4;
            }
            boolean z5 = z;
            boolean z6 = z2;
            if (i == segment.limit) {
                Segment segment3 = segment.next;
                segment3.getClass();
                i = segment3.pos;
                segment = segment3;
            }
            if (i2 == segment2.limit) {
                segment2 = segment2.next;
                segment2.getClass();
                i2 = segment2.pos;
            }
            j2 += min;
            z = z5;
            z2 = z6;
        }
        return z;
    }

    @Override // okio.BufferedSource
    public final boolean exhausted() {
        return this.size == 0;
    }

    public final byte getByte(long j) {
        SegmentedByteString.checkOffsetAndCount(this.size, j, 1L);
        Segment segment = this.head;
        segment.getClass();
        long j2 = this.size;
        if (j2 - j < j) {
            while (j2 > j) {
                segment = segment.prev;
                segment.getClass();
                j2 -= segment.limit - segment.pos;
            }
            return segment.data[(int) ((segment.pos + j) - j2)];
        }
        long j3 = 0;
        while (true) {
            int i = segment.limit;
            int i2 = segment.pos;
            long j4 = (i - i2) + j3;
            if (j4 > j) {
                return segment.data[(int) ((i2 + j) - j3)];
            }
            segment = segment.next;
            segment.getClass();
            j3 = j4;
        }
    }

    public final int hashCode() {
        Segment segment = this.head;
        if (segment == null) {
            return 0;
        }
        int i = 1;
        do {
            int i2 = segment.limit;
            for (int i3 = segment.pos; i3 < i2; i3++) {
                i = (i * 31) + segment.data[i3];
            }
            segment = segment.next;
            segment.getClass();
        } while (segment != this.head);
        return i;
    }

    @Override // okio.BufferedSource
    public final long indexOfElement(ByteString byteString) {
        return indexOfElement(byteString, 0L);
    }

    @Override // java.nio.channels.Channel
    public final boolean isOpen() {
        return true;
    }

    public final boolean rangeEquals(ByteString byteString) {
        int size$external__okio__android_common__okio_lib = byteString.getSize$external__okio__android_common__okio_lib();
        if (size$external__okio__android_common__okio_lib >= 0 && this.size >= size$external__okio__android_common__okio_lib && byteString.getSize$external__okio__android_common__okio_lib() >= size$external__okio__android_common__okio_lib) {
            for (int i = 0; i < size$external__okio__android_common__okio_lib; i++) {
                if (getByte(i) == byteString.internalGet$external__okio__android_common__okio_lib(i)) {
                }
            }
            return true;
        }
        return false;
    }

    @Override // okio.Source
    public final long read(Buffer buffer, long j) {
        if (j < 0) {
            throw new IllegalArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("byteCount < 0: ", j).toString());
        }
        long j2 = this.size;
        if (j2 == 0) {
            return -1L;
        }
        if (j > j2) {
            j = j2;
        }
        buffer.write(this, j);
        return j;
    }

    public final byte readByte() {
        if (this.size == 0) {
            throw new EOFException();
        }
        Segment segment = this.head;
        segment.getClass();
        int i = segment.pos;
        int i2 = segment.limit;
        int i3 = i + 1;
        byte b = segment.data[i];
        this.size--;
        if (i3 != i2) {
            segment.pos = i3;
            return b;
        }
        this.head = segment.pop();
        SegmentPool.recycle(segment);
        return b;
    }

    public final byte[] readByteArray(long j) {
        if (j < 0 || j > 2147483647L) {
            throw new IllegalArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("byteCount: ", j).toString());
        }
        if (this.size < j) {
            throw new EOFException();
        }
        int i = (int) j;
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int read = read(bArr, i2, i - i2);
            if (read == -1) {
                throw new EOFException();
            }
            i2 += read;
        }
        return bArr;
    }

    public final ByteString readByteString(long j) {
        if (j < 0 || j > 2147483647L) {
            throw new IllegalArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("byteCount: ", j).toString());
        }
        if (this.size < j) {
            throw new EOFException();
        }
        if (j < 4096) {
            return new ByteString(readByteArray(j));
        }
        ByteString snapshot = snapshot((int) j);
        skip(j);
        return snapshot;
    }

    public final int readInt() {
        if (this.size < 4) {
            throw new EOFException();
        }
        Segment segment = this.head;
        segment.getClass();
        int i = segment.pos;
        int i2 = segment.limit;
        if (i2 - i < 4) {
            return (readByte() & 255) | ((readByte() & 255) << 24) | ((readByte() & 255) << 16) | ((readByte() & 255) << 8);
        }
        byte[] bArr = segment.data;
        int i3 = i + 3;
        int i4 = ((bArr[i + 1] & 255) << 16) | ((bArr[i] & 255) << 24) | ((bArr[i + 2] & 255) << 8);
        int i5 = i + 4;
        int i6 = i4 | (bArr[i3] & 255);
        this.size -= 4;
        if (i5 != i2) {
            segment.pos = i5;
            return i6;
        }
        this.head = segment.pop();
        SegmentPool.recycle(segment);
        return i6;
    }

    public final String readString(long j, Charset charset) {
        if (j < 0 || j > 2147483647L) {
            throw new IllegalArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("byteCount: ", j).toString());
        }
        if (this.size < j) {
            throw new EOFException();
        }
        if (j == 0) {
            return "";
        }
        Segment segment = this.head;
        segment.getClass();
        int i = segment.pos;
        if (i + j > segment.limit) {
            return new String(readByteArray(j), charset);
        }
        int i2 = (int) j;
        String str = new String(segment.data, i, i2, charset);
        int i3 = segment.pos + i2;
        segment.pos = i3;
        this.size -= j;
        if (i3 == segment.limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return str;
    }

    @Override // okio.BufferedSource
    public final boolean request(long j) {
        return this.size >= j;
    }

    @Override // okio.BufferedSource
    public final int select(Options options) {
        int selectPrefix = okio.internal.Buffer.selectPrefix(this, options, false);
        if (selectPrefix == -1) {
            return -1;
        }
        skip(options.byteStrings[selectPrefix].getSize$external__okio__android_common__okio_lib());
        return selectPrefix;
    }

    @Override // okio.BufferedSource
    public final void skip(long j) {
        while (j > 0) {
            Segment segment = this.head;
            if (segment == null) {
                throw new EOFException();
            }
            int min = (int) Math.min(j, segment.limit - segment.pos);
            long j2 = min;
            this.size -= j2;
            j -= j2;
            int i = segment.pos + min;
            segment.pos = i;
            if (i == segment.limit) {
                this.head = segment.pop();
                SegmentPool.recycle(segment);
            }
        }
    }

    public final ByteString snapshot(int i) {
        if (i == 0) {
            return ByteString.EMPTY;
        }
        SegmentedByteString.checkOffsetAndCount(this.size, 0L, i);
        Segment segment = this.head;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            segment.getClass();
            int i5 = segment.limit;
            int i6 = segment.pos;
            if (i5 == i6) {
                throw new AssertionError("s.limit == s.pos");
            }
            i3 += i5 - i6;
            i4++;
            segment = segment.next;
        }
        byte[][] bArr = new byte[i4][];
        int[] iArr = new int[i4 * 2];
        Segment segment2 = this.head;
        int i7 = 0;
        while (i2 < i) {
            segment2.getClass();
            bArr[i7] = segment2.data;
            i2 += segment2.limit - segment2.pos;
            iArr[i7] = Math.min(i2, i);
            iArr[i7 + i4] = segment2.pos;
            segment2.shared = true;
            i7++;
            segment2 = segment2.next;
        }
        return new C0436SegmentedByteString(bArr, iArr);
    }

    public final String toString() {
        long j = this.size;
        if (j <= 2147483647L) {
            return snapshot((int) j).toString();
        }
        throw new IllegalStateException(ValueAnimator$$ExternalSyntheticOutline0.m("size > Int.MAX_VALUE: ", j).toString());
    }

    public final Segment writableSegment$external__okio__android_common__okio_lib(int i) {
        if (i < 1 || i > 8192) {
            throw new IllegalArgumentException("unexpected capacity");
        }
        Segment segment = this.head;
        if (segment == null) {
            Segment take = SegmentPool.take();
            this.head = take;
            take.prev = take;
            take.next = take;
            return take;
        }
        Segment segment2 = segment.prev;
        segment2.getClass();
        if (segment2.limit + i <= 8192 && segment2.owner) {
            return segment2;
        }
        Segment take2 = SegmentPool.take();
        segment2.push(take2);
        return take2;
    }

    @Override // okio.Sink
    public final void write(Buffer buffer, long j) {
        Segment take;
        if (buffer == this) {
            throw new IllegalArgumentException("source == this");
        }
        SegmentedByteString.checkOffsetAndCount(buffer.size, 0L, j);
        while (j > 0) {
            Segment segment = buffer.head;
            segment.getClass();
            int i = segment.limit;
            Segment segment2 = buffer.head;
            segment2.getClass();
            long j2 = i - segment2.pos;
            int i2 = 0;
            if (j < j2) {
                Segment segment3 = this.head;
                Segment segment4 = segment3 != null ? segment3.prev : null;
                if (segment4 != null && segment4.owner) {
                    if ((segment4.limit + j) - (segment4.shared ? 0 : segment4.pos) <= 8192) {
                        Segment segment5 = buffer.head;
                        segment5.getClass();
                        segment5.writeTo(segment4, (int) j);
                        buffer.size -= j;
                        this.size += j;
                        return;
                    }
                }
                Segment segment6 = buffer.head;
                segment6.getClass();
                int i3 = (int) j;
                if (i3 <= 0 || i3 > segment6.limit - segment6.pos) {
                    throw new IllegalArgumentException("byteCount out of range");
                }
                if (i3 >= 1024) {
                    take = segment6.sharedCopy();
                } else {
                    take = SegmentPool.take();
                    int i4 = segment6.pos;
                    ArraysKt___ArraysJvmKt.copyInto$default(segment6.data, i4, i4 + i3, take.data, 2);
                }
                take.limit = take.pos + i3;
                segment6.pos += i3;
                Segment segment7 = segment6.prev;
                segment7.getClass();
                segment7.push(take);
                buffer.head = take;
            }
            Segment segment8 = buffer.head;
            segment8.getClass();
            long j3 = segment8.limit - segment8.pos;
            buffer.head = segment8.pop();
            Segment segment9 = this.head;
            if (segment9 == null) {
                this.head = segment8;
                segment8.prev = segment8;
                segment8.next = segment8;
            } else {
                Segment segment10 = segment9.prev;
                segment10.getClass();
                segment10.push(segment8);
                Segment segment11 = segment8.prev;
                if (segment11 == segment8) {
                    throw new IllegalStateException("cannot compact");
                }
                segment11.getClass();
                if (segment11.owner) {
                    int i5 = segment8.limit - segment8.pos;
                    Segment segment12 = segment8.prev;
                    segment12.getClass();
                    int i6 = 8192 - segment12.limit;
                    Segment segment13 = segment8.prev;
                    segment13.getClass();
                    if (!segment13.shared) {
                        Segment segment14 = segment8.prev;
                        segment14.getClass();
                        i2 = segment14.pos;
                    }
                    if (i5 <= i6 + i2) {
                        Segment segment15 = segment8.prev;
                        segment15.getClass();
                        segment8.writeTo(segment15, i5);
                        segment8.pop();
                        SegmentPool.recycle(segment8);
                    }
                }
            }
            buffer.size -= j3;
            this.size += j3;
            j -= j3;
        }
    }

    public final void writeAll(Buffer buffer) {
        while (buffer.read(this, 8192L) != -1) {
        }
    }

    public final void writeByte(int i) {
        Segment writableSegment$external__okio__android_common__okio_lib = writableSegment$external__okio__android_common__okio_lib(1);
        int i2 = writableSegment$external__okio__android_common__okio_lib.limit;
        writableSegment$external__okio__android_common__okio_lib.limit = i2 + 1;
        writableSegment$external__okio__android_common__okio_lib.data[i2] = (byte) i;
        this.size++;
    }

    public final void writeInt(int i) {
        Segment writableSegment$external__okio__android_common__okio_lib = writableSegment$external__okio__android_common__okio_lib(4);
        int i2 = writableSegment$external__okio__android_common__okio_lib.limit;
        byte[] bArr = writableSegment$external__okio__android_common__okio_lib.data;
        bArr[i2] = (byte) ((i >>> 24) & 255);
        bArr[i2 + 1] = (byte) ((i >>> 16) & 255);
        bArr[i2 + 2] = (byte) ((i >>> 8) & 255);
        bArr[i2 + 3] = (byte) (i & 255);
        writableSegment$external__okio__android_common__okio_lib.limit = i2 + 4;
        this.size += 4;
    }

    public final void writeUtf8(int i, int i2, String str) {
        char charAt;
        if (i < 0) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "beginIndex < 0: ").toString());
        }
        if (i2 < i) {
            throw new IllegalArgumentException(ListImplementation$$ExternalSyntheticOutline0.m(i2, i, "endIndex < beginIndex: ", " < ").toString());
        }
        if (i2 > str.length()) {
            throw new IllegalArgumentException(ListImplementation$$ExternalSyntheticOutline0.m(i2, str.length(), "endIndex > string.length: ", " > ").toString());
        }
        while (i < i2) {
            char charAt2 = str.charAt(i);
            if (charAt2 < 128) {
                Segment writableSegment$external__okio__android_common__okio_lib = writableSegment$external__okio__android_common__okio_lib(1);
                int i3 = writableSegment$external__okio__android_common__okio_lib.limit - i;
                int min = Math.min(i2, 8192 - i3);
                int i4 = i + 1;
                byte[] bArr = writableSegment$external__okio__android_common__okio_lib.data;
                bArr[i + i3] = (byte) charAt2;
                while (true) {
                    i = i4;
                    if (i >= min || (charAt = str.charAt(i)) >= 128) {
                        break;
                    }
                    i4 = i + 1;
                    bArr[i + i3] = (byte) charAt;
                }
                int i5 = writableSegment$external__okio__android_common__okio_lib.limit;
                int i6 = (i3 + i) - i5;
                writableSegment$external__okio__android_common__okio_lib.limit = i5 + i6;
                this.size += i6;
            } else {
                if (charAt2 < 2048) {
                    Segment writableSegment$external__okio__android_common__okio_lib2 = writableSegment$external__okio__android_common__okio_lib(2);
                    int i7 = writableSegment$external__okio__android_common__okio_lib2.limit;
                    byte[] bArr2 = writableSegment$external__okio__android_common__okio_lib2.data;
                    bArr2[i7] = (byte) ((charAt2 >> 6) | 192);
                    bArr2[i7 + 1] = (byte) ((charAt2 & '?') | 128);
                    writableSegment$external__okio__android_common__okio_lib2.limit = i7 + 2;
                    this.size += 2;
                } else if (charAt2 < 55296 || charAt2 > 57343) {
                    Segment writableSegment$external__okio__android_common__okio_lib3 = writableSegment$external__okio__android_common__okio_lib(3);
                    int i8 = writableSegment$external__okio__android_common__okio_lib3.limit;
                    byte b = (byte) ((charAt2 >> '\f') | IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType);
                    byte[] bArr3 = writableSegment$external__okio__android_common__okio_lib3.data;
                    bArr3[i8] = b;
                    bArr3[i8 + 1] = (byte) ((63 & (charAt2 >> 6)) | 128);
                    bArr3[i8 + 2] = (byte) ((charAt2 & '?') | 128);
                    writableSegment$external__okio__android_common__okio_lib3.limit = i8 + 3;
                    this.size += 3;
                } else {
                    int i9 = i + 1;
                    char charAt3 = i9 < i2 ? str.charAt(i9) : (char) 0;
                    if (charAt2 > 56319 || 56320 > charAt3 || charAt3 >= 57344) {
                        writeByte(63);
                        i = i9;
                    } else {
                        int i10 = (((charAt2 & 1023) << 10) | (charAt3 & 1023)) + 65536;
                        Segment writableSegment$external__okio__android_common__okio_lib4 = writableSegment$external__okio__android_common__okio_lib(4);
                        int i11 = writableSegment$external__okio__android_common__okio_lib4.limit;
                        byte b2 = (byte) ((i10 >> 18) | IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp);
                        byte[] bArr4 = writableSegment$external__okio__android_common__okio_lib4.data;
                        bArr4[i11] = b2;
                        bArr4[i11 + 1] = (byte) (((i10 >> 12) & 63) | 128);
                        bArr4[i11 + 2] = (byte) (((i10 >> 6) & 63) | 128);
                        bArr4[i11 + 3] = (byte) ((i10 & 63) | 128);
                        writableSegment$external__okio__android_common__okio_lib4.limit = i11 + 4;
                        this.size += 4;
                        i += 2;
                    }
                }
                i++;
            }
        }
    }

    public final long indexOfElement(ByteString byteString, long j) {
        long j2 = 0;
        if (j < 0) {
            throw new IllegalArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("fromIndex < 0: ", j).toString());
        }
        Segment segment = this.head;
        if (segment == null) {
            return -1L;
        }
        long j3 = this.size;
        if (j3 - j < j) {
            while (j3 > j) {
                segment = segment.prev;
                segment.getClass();
                j3 -= segment.limit - segment.pos;
            }
            if (byteString.getSize$external__okio__android_common__okio_lib() == 2) {
                byte internalGet$external__okio__android_common__okio_lib = byteString.internalGet$external__okio__android_common__okio_lib(0);
                byte internalGet$external__okio__android_common__okio_lib2 = byteString.internalGet$external__okio__android_common__okio_lib(1);
                while (j3 < this.size) {
                    int i = segment.limit;
                    for (int i2 = (int) ((segment.pos + j) - j3); i2 < i; i2++) {
                        byte b = segment.data[i2];
                        if (b == internalGet$external__okio__android_common__okio_lib || b == internalGet$external__okio__android_common__okio_lib2) {
                            return (i2 - segment.pos) + j3;
                        }
                    }
                    j3 += segment.limit - segment.pos;
                    segment = segment.next;
                    segment.getClass();
                    j = j3;
                }
                return -1L;
            }
            byte[] internalArray$external__okio__android_common__okio_lib = byteString.internalArray$external__okio__android_common__okio_lib();
            while (j3 < this.size) {
                int i3 = segment.limit;
                for (int i4 = (int) ((segment.pos + j) - j3); i4 < i3; i4++) {
                    byte b2 = segment.data[i4];
                    for (byte b3 : internalArray$external__okio__android_common__okio_lib) {
                        if (b2 == b3) {
                            return (i4 - segment.pos) + j3;
                        }
                    }
                }
                j3 += segment.limit - segment.pos;
                segment = segment.next;
                segment.getClass();
                j = j3;
            }
            return -1L;
        }
        while (true) {
            long j4 = (segment.limit - segment.pos) + j2;
            if (j4 > j) {
                break;
            }
            segment = segment.next;
            segment.getClass();
            j2 = j4;
        }
        if (byteString.getSize$external__okio__android_common__okio_lib() == 2) {
            byte internalGet$external__okio__android_common__okio_lib3 = byteString.internalGet$external__okio__android_common__okio_lib(0);
            byte internalGet$external__okio__android_common__okio_lib4 = byteString.internalGet$external__okio__android_common__okio_lib(1);
            while (j2 < this.size) {
                int i5 = segment.limit;
                for (int i6 = (int) ((segment.pos + j) - j2); i6 < i5; i6++) {
                    byte b4 = segment.data[i6];
                    if (b4 == internalGet$external__okio__android_common__okio_lib3 || b4 == internalGet$external__okio__android_common__okio_lib4) {
                        return (i6 - segment.pos) + j2;
                    }
                }
                j2 += segment.limit - segment.pos;
                segment = segment.next;
                segment.getClass();
                j = j2;
            }
            return -1L;
        }
        byte[] internalArray$external__okio__android_common__okio_lib2 = byteString.internalArray$external__okio__android_common__okio_lib();
        while (j2 < this.size) {
            int i7 = segment.limit;
            for (int i8 = (int) ((segment.pos + j) - j2); i8 < i7; i8++) {
                byte b5 = segment.data[i8];
                for (byte b6 : internalArray$external__okio__android_common__okio_lib2) {
                    if (b5 == b6) {
                        return (i8 - segment.pos) + j2;
                    }
                }
            }
            j2 += segment.limit - segment.pos;
            segment = segment.next;
            segment.getClass();
            j = j2;
        }
        return -1L;
    }

    @Override // java.nio.channels.ReadableByteChannel
    public final int read(ByteBuffer byteBuffer) {
        Segment segment = this.head;
        if (segment == null) {
            return -1;
        }
        int min = Math.min(byteBuffer.remaining(), segment.limit - segment.pos);
        byteBuffer.put(segment.data, segment.pos, min);
        int i = segment.pos + min;
        segment.pos = i;
        this.size -= min;
        if (i == segment.limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return min;
    }

    public final int read(byte[] bArr, int i, int i2) {
        SegmentedByteString.checkOffsetAndCount(bArr.length, i, i2);
        Segment segment = this.head;
        if (segment == null) {
            return -1;
        }
        int min = Math.min(i2, segment.limit - segment.pos);
        int i3 = segment.pos;
        System.arraycopy(segment.data, i3, bArr, i, (i3 + min) - i3);
        int i4 = segment.pos + min;
        segment.pos = i4;
        this.size -= min;
        if (i4 == segment.limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return min;
    }

    @Override // okio.BufferedSource
    public final Buffer buffer() {
        return this;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel, okio.Sink
    public final void close() {
    }

    @Override // okio.Sink, java.io.Flushable
    public final void flush() {
    }

    @Override // okio.BufferedSource
    public final Buffer getBuffer() {
        return this;
    }

    public final void write(ByteString byteString) {
        byteString.write$external__okio__android_common__okio_lib(this, byteString.getSize$external__okio__android_common__okio_lib());
    }

    @Override // java.nio.channels.WritableByteChannel
    public final int write(ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        int i = remaining;
        while (i > 0) {
            Segment writableSegment$external__okio__android_common__okio_lib = writableSegment$external__okio__android_common__okio_lib(1);
            int min = Math.min(i, 8192 - writableSegment$external__okio__android_common__okio_lib.limit);
            byteBuffer.get(writableSegment$external__okio__android_common__okio_lib.data, writableSegment$external__okio__android_common__okio_lib.limit, min);
            i -= min;
            writableSegment$external__okio__android_common__okio_lib.limit += min;
        }
        this.size += remaining;
        return remaining;
    }

    public final void write(byte[] bArr, int i, int i2) {
        long j = i2;
        SegmentedByteString.checkOffsetAndCount(bArr.length, i, j);
        int i3 = i2 + i;
        while (i < i3) {
            Segment writableSegment$external__okio__android_common__okio_lib = writableSegment$external__okio__android_common__okio_lib(1);
            int min = Math.min(i3 - i, 8192 - writableSegment$external__okio__android_common__okio_lib.limit);
            int i4 = i + min;
            System.arraycopy(bArr, i, writableSegment$external__okio__android_common__okio_lib.data, writableSegment$external__okio__android_common__okio_lib.limit, i4 - i);
            writableSegment$external__okio__android_common__okio_lib.limit += min;
            i = i4;
        }
        this.size += j;
    }
}

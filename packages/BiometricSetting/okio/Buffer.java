package okio;

import androidx.appcompat.view.menu.SubMenuBuilder$$ExternalSyntheticOutline0;
import java.io.Closeable;
import java.io.EOFException;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public final class Buffer implements BufferedSource, Closeable, Flushable, WritableByteChannel, Cloneable, ByteChannel {

    @Nullable
    Segment head;
    long size;

    public final void clear() {
        try {
            skip(this.size);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public final Object clone() throws CloneNotSupportedException {
        Buffer buffer = new Buffer();
        if (this.size != 0) {
            Segment sharedCopy = this.head.sharedCopy();
            buffer.head = sharedCopy;
            sharedCopy.prev = sharedCopy;
            sharedCopy.next = sharedCopy;
            Segment segment = this.head;
            while (true) {
                segment = segment.next;
                if (segment == this.head) {
                    break;
                }
                buffer.head.prev.push(segment.sharedCopy());
            }
            buffer.size = this.size;
        }
        return buffer;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Buffer)) {
            return false;
        }
        Buffer buffer = (Buffer) obj;
        long j = this.size;
        if (j != buffer.size) {
            return false;
        }
        long j2 = 0;
        if (j == 0) {
            return true;
        }
        Segment segment = this.head;
        Segment segment2 = buffer.head;
        int i = segment.pos;
        int i2 = segment2.pos;
        while (j2 < this.size) {
            long min = Math.min(segment.limit - i, segment2.limit - i2);
            int i3 = 0;
            while (i3 < min) {
                int i4 = i + 1;
                int i5 = i2 + 1;
                if (segment.data[i] != segment2.data[i2]) {
                    return false;
                }
                i3++;
                i = i4;
                i2 = i5;
            }
            if (i == segment.limit) {
                segment = segment.next;
                i = segment.pos;
            }
            if (i2 == segment2.limit) {
                segment2 = segment2.next;
                i2 = segment2.pos;
            }
            j2 += min;
        }
        return true;
    }

    public final byte getByte(long j) {
        int i;
        Util.checkOffsetAndCount(this.size, j, 1L);
        long j2 = this.size;
        if (j2 - j <= j) {
            long j3 = j - j2;
            Segment segment = this.head;
            do {
                segment = segment.prev;
                int i2 = segment.limit;
                i = segment.pos;
                j3 += i2 - i;
            } while (j3 < 0);
            return segment.data[i + ((int) j3)];
        }
        Segment segment2 = this.head;
        while (true) {
            int i3 = segment2.limit;
            int i4 = segment2.pos;
            long j4 = i3 - i4;
            if (j < j4) {
                return segment2.data[i4 + ((int) j)];
            }
            j -= j4;
            segment2 = segment2.next;
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
        } while (segment != this.head);
        return i;
    }

    @Override // okio.BufferedSource
    public final long indexOfElement(ByteString byteString) {
        return indexOfElement(byteString, 0L);
    }

    @Override // okio.BufferedSource
    public final InputStream inputStream() {
        throw null;
    }

    @Override // java.nio.channels.Channel
    public final boolean isOpen() {
        return true;
    }

    @Override // okio.BufferedSource
    public final BufferedSource peek() {
        PeekSource peekSource = new PeekSource(this);
        int i = Okio.$r8$clinit;
        return new RealBufferedSource(peekSource);
    }

    public final int read(byte[] bArr, int i, int i2) {
        Util.checkOffsetAndCount(bArr.length, i, i2);
        Segment segment = this.head;
        if (segment == null) {
            return -1;
        }
        int min = Math.min(i2, segment.limit - segment.pos);
        System.arraycopy(segment.data, segment.pos, bArr, i, min);
        int i3 = segment.pos + min;
        segment.pos = i3;
        this.size -= min;
        if (i3 == segment.limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return min;
    }

    @Override // okio.BufferedSource
    public final byte readByte() {
        long j = this.size;
        if (j == 0) {
            throw new IllegalStateException("size == 0");
        }
        Segment segment = this.head;
        int i = segment.pos;
        int i2 = segment.limit;
        int i3 = i + 1;
        byte b = segment.data[i];
        this.size = j - 1;
        if (i3 == i2) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = i3;
        }
        return b;
    }

    public final byte[] readByteArray(long j) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0L, j);
        if (j > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j);
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

    public final ByteString readByteString() {
        try {
            return new ByteString(readByteArray(this.size));
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public final String readString(long j, Charset charset) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0L, j);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        if (j > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j);
        }
        if (j == 0) {
            return "";
        }
        Segment segment = this.head;
        int i = segment.pos;
        if (i + j > segment.limit) {
            return new String(readByteArray(j), charset);
        }
        String str = new String(segment.data, i, (int) j, charset);
        int i2 = (int) (segment.pos + j);
        segment.pos = i2;
        this.size -= j;
        if (i2 == segment.limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return str;
    }

    public final String readUtf8() {
        try {
            return readString(this.size, Util.UTF_8);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    @Override // okio.BufferedSource
    public final boolean request(long j) {
        return this.size >= j;
    }

    @Override // okio.BufferedSource
    public final int select(Options options) {
        int selectPrefix = selectPrefix(options, false);
        if (selectPrefix == -1) {
            return -1;
        }
        try {
            skip(options.byteStrings[selectPrefix].size());
            return selectPrefix;
        } catch (EOFException unused) {
            throw new AssertionError();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0055, code lost:
    
        if (r20 == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0057, code lost:
    
        return -2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0058, code lost:
    
        return r10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final int selectPrefix(Options options, boolean z) {
        int i;
        byte[] bArr;
        int i2;
        int i3;
        byte[] bArr2;
        int i4;
        Segment segment;
        Segment segment2 = this.head;
        if (segment2 != null) {
            int i5 = segment2.pos;
            int i6 = segment2.limit;
            int[] iArr = options.trie;
            byte[] bArr3 = segment2.data;
            Segment segment3 = segment2;
            int i7 = 0;
            int i8 = -1;
            loop0: while (true) {
                int i9 = i7 + 1;
                int i10 = iArr[i7];
                int i11 = i9 + 1;
                int i12 = iArr[i9];
                if (i12 != -1) {
                    i8 = i12;
                }
                if (segment3 == null) {
                    break;
                }
                if (i10 >= 0) {
                    int i13 = i5 + 1;
                    int i14 = bArr3[i5] & 255;
                    int i15 = i11 + i10;
                    while (i11 != i15) {
                        if (i14 == iArr[i11]) {
                            i = iArr[i11 + i10];
                            if (i13 == i6) {
                                segment3 = segment3.next;
                                i3 = segment3.pos;
                                i2 = segment3.limit;
                                bArr = segment3.data;
                                if (segment3 == segment2) {
                                    segment3 = null;
                                }
                            } else {
                                bArr = bArr3;
                                i2 = i6;
                                i3 = i13;
                            }
                        } else {
                            i11++;
                        }
                    }
                    return i8;
                }
                int i16 = (i10 * (-1)) + i11;
                while (true) {
                    int i17 = i5 + 1;
                    int i18 = i11 + 1;
                    if ((bArr3[i5] & 255) != iArr[i11]) {
                        return i8;
                    }
                    boolean z2 = i18 == i16;
                    if (i17 == i6) {
                        Segment segment4 = segment3.next;
                        i4 = segment4.pos;
                        int i19 = segment4.limit;
                        bArr2 = segment4.data;
                        if (segment4 != segment2) {
                            segment = segment4;
                            i6 = i19;
                        } else {
                            if (!z2) {
                                break loop0;
                            }
                            i6 = i19;
                            segment = null;
                        }
                    } else {
                        Segment segment5 = segment3;
                        bArr2 = bArr3;
                        i4 = i17;
                        segment = segment5;
                    }
                    if (z2) {
                        i = iArr[i18];
                        int i20 = i4;
                        i2 = i6;
                        i3 = i20;
                        byte[] bArr4 = bArr2;
                        segment3 = segment;
                        bArr = bArr4;
                        break;
                    }
                    i5 = i4;
                    bArr3 = bArr2;
                    i11 = i18;
                    segment3 = segment;
                }
                if (i >= 0) {
                    return i;
                }
                byte[] bArr5 = bArr;
                i7 = -i;
                i5 = i3;
                i6 = i2;
                bArr3 = bArr5;
            }
        } else {
            if (z) {
                return -2;
            }
            return options.indexOf(ByteString.EMPTY);
        }
    }

    public final long size() {
        return this.size;
    }

    public final void skip(long j) throws EOFException {
        while (j > 0) {
            if (this.head == null) {
                throw new EOFException();
            }
            int min = (int) Math.min(j, r0.limit - r0.pos);
            long j2 = min;
            this.size -= j2;
            j -= j2;
            Segment segment = this.head;
            int i = segment.pos + min;
            segment.pos = i;
            if (i == segment.limit) {
                this.head = segment.pop();
                SegmentPool.recycle(segment);
            }
        }
    }

    public final String toString() {
        long j = this.size;
        if (j <= 2147483647L) {
            int i = (int) j;
            return (i == 0 ? ByteString.EMPTY : new SegmentedByteString(this, i)).toString();
        }
        throw new IllegalArgumentException("size > Integer.MAX_VALUE: " + this.size);
    }

    final Segment writableSegment(int i) {
        if (i < 1 || i > 8192) {
            throw new IllegalArgumentException();
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
        if (segment2.limit + i <= 8192 && segment2.owner) {
            return segment2;
        }
        Segment take2 = SegmentPool.take();
        segment2.push(take2);
        return take2;
    }

    @Override // java.nio.channels.WritableByteChannel
    public final int write(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer == null) {
            throw new IllegalArgumentException("source == null");
        }
        int remaining = byteBuffer.remaining();
        int i = remaining;
        while (i > 0) {
            Segment writableSegment = writableSegment(1);
            int min = Math.min(i, 8192 - writableSegment.limit);
            byteBuffer.get(writableSegment.data, writableSegment.limit, min);
            i -= min;
            writableSegment.limit += min;
        }
        this.size += remaining;
        return remaining;
    }

    public final void writeByte(int i) {
        Segment writableSegment = writableSegment(1);
        int i2 = writableSegment.limit;
        writableSegment.limit = i2 + 1;
        writableSegment.data[i2] = (byte) i;
        this.size++;
    }

    public final void writeInt(int i) {
        Segment writableSegment = writableSegment(4);
        int i2 = writableSegment.limit;
        int i3 = i2 + 1;
        byte[] bArr = writableSegment.data;
        bArr[i2] = (byte) ((i >>> 24) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((i >>> 16) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((i >>> 8) & 255);
        bArr[i5] = (byte) (i & 255);
        writableSegment.limit = i5 + 1;
        this.size += 4;
    }

    public final void writeUtf8(int i, int i2, String str) {
        char charAt;
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (i < 0) {
            throw new IllegalArgumentException(SubMenuBuilder$$ExternalSyntheticOutline0.m0m("beginIndex < 0: ", i));
        }
        if (i2 < i) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + i2 + " < " + i);
        }
        if (i2 > str.length()) {
            throw new IllegalArgumentException("endIndex > string.length: " + i2 + " > " + str.length());
        }
        while (i < i2) {
            char charAt2 = str.charAt(i);
            if (charAt2 < 128) {
                Segment writableSegment = writableSegment(1);
                int i3 = writableSegment.limit - i;
                int min = Math.min(i2, 8192 - i3);
                int i4 = i + 1;
                byte[] bArr = writableSegment.data;
                bArr[i + i3] = (byte) charAt2;
                while (true) {
                    i = i4;
                    if (i >= min || (charAt = str.charAt(i)) >= 128) {
                        break;
                    }
                    i4 = i + 1;
                    bArr[i + i3] = (byte) charAt;
                }
                int i5 = writableSegment.limit;
                int i6 = (i3 + i) - i5;
                writableSegment.limit = i5 + i6;
                this.size += i6;
            } else {
                if (charAt2 < 2048) {
                    writeByte((charAt2 >> 6) | 192);
                    writeByte((charAt2 & '?') | 128);
                } else if (charAt2 < 55296 || charAt2 > 57343) {
                    writeByte((charAt2 >> '\f') | 224);
                    writeByte(((charAt2 >> 6) & 63) | 128);
                    writeByte((charAt2 & '?') | 128);
                } else {
                    int i7 = i + 1;
                    char charAt3 = i7 < i2 ? str.charAt(i7) : (char) 0;
                    if (charAt2 > 56319 || charAt3 < 56320 || charAt3 > 57343) {
                        writeByte(63);
                        i = i7;
                    } else {
                        int i8 = (((charAt2 & 10239) << 10) | (9215 & charAt3)) + 65536;
                        writeByte((i8 >> 18) | 240);
                        writeByte(((i8 >> 12) & 63) | 128);
                        writeByte(((i8 >> 6) & 63) | 128);
                        writeByte((i8 & 63) | 128);
                        i += 2;
                    }
                }
                i++;
            }
        }
    }

    public final long indexOfElement(ByteString byteString, long j) {
        int i;
        int i2;
        long j2 = 0;
        if (j < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        Segment segment = this.head;
        if (segment == null) {
            return -1L;
        }
        long j3 = this.size;
        if (j3 - j < j) {
            while (j3 > j) {
                segment = segment.prev;
                j3 -= segment.limit - segment.pos;
            }
        } else {
            while (true) {
                long j4 = (segment.limit - segment.pos) + j2;
                if (j4 >= j) {
                    break;
                }
                segment = segment.next;
                j2 = j4;
            }
            j3 = j2;
        }
        byte[] bArr = byteString.data;
        if (bArr.length == 2) {
            byte b = bArr[0];
            byte b2 = bArr[1];
            while (j3 < this.size) {
                byte[] bArr2 = segment.data;
                i = (int) ((segment.pos + j) - j3);
                int i3 = segment.limit;
                while (i < i3) {
                    byte b3 = bArr2[i];
                    if (b3 == b || b3 == b2) {
                        i2 = segment.pos;
                        return (i - i2) + j3;
                    }
                    i++;
                }
                j3 += segment.limit - segment.pos;
                segment = segment.next;
                j = j3;
            }
            return -1L;
        }
        while (j3 < this.size) {
            byte[] bArr3 = segment.data;
            i = (int) ((segment.pos + j) - j3);
            int i4 = segment.limit;
            while (i < i4) {
                byte b4 = bArr3[i];
                for (byte b5 : bArr) {
                    if (b4 == b5) {
                        i2 = segment.pos;
                        return (i - i2) + j3;
                    }
                }
                i++;
            }
            j3 += segment.limit - segment.pos;
            segment = segment.next;
            j = j3;
        }
        return -1L;
    }

    public final String readUtf8(long j) throws EOFException {
        return readString(j, Util.UTF_8);
    }

    public final void write(Buffer buffer, long j) {
        Segment take;
        if (buffer == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (buffer != this) {
            Util.checkOffsetAndCount(buffer.size, 0L, j);
            while (j > 0) {
                Segment segment = buffer.head;
                int i = segment.limit - segment.pos;
                if (j < i) {
                    Segment segment2 = this.head;
                    Segment segment3 = segment2 != null ? segment2.prev : null;
                    if (segment3 != null && segment3.owner) {
                        if ((segment3.limit + j) - (segment3.shared ? 0 : segment3.pos) <= 8192) {
                            segment.writeTo(segment3, (int) j);
                            buffer.size -= j;
                            this.size += j;
                            return;
                        }
                    }
                    int i2 = (int) j;
                    if (i2 > 0 && i2 <= i) {
                        if (i2 >= 1024) {
                            take = segment.sharedCopy();
                        } else {
                            take = SegmentPool.take();
                            System.arraycopy(segment.data, segment.pos, take.data, 0, i2);
                        }
                        take.limit = take.pos + i2;
                        segment.pos += i2;
                        segment.prev.push(take);
                        buffer.head = take;
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
                Segment segment4 = buffer.head;
                long j2 = segment4.limit - segment4.pos;
                buffer.head = segment4.pop();
                Segment segment5 = this.head;
                if (segment5 == null) {
                    this.head = segment4;
                    segment4.prev = segment4;
                    segment4.next = segment4;
                } else {
                    segment5.prev.push(segment4);
                    Segment segment6 = segment4.prev;
                    if (segment6 != segment4) {
                        if (segment6.owner) {
                            int i3 = segment4.limit - segment4.pos;
                            if (i3 <= (8192 - segment6.limit) + (segment6.shared ? 0 : segment6.pos)) {
                                segment4.writeTo(segment6, i3);
                                segment4.pop();
                                SegmentPool.recycle(segment4);
                            }
                        }
                    } else {
                        throw new IllegalStateException();
                    }
                }
                buffer.size -= j2;
                this.size += j2;
                j -= j2;
            }
            return;
        }
        throw new IllegalArgumentException("source == this");
    }

    @Override // java.nio.channels.ReadableByteChannel
    public final int read(ByteBuffer byteBuffer) throws IOException {
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

    @Override // okio.Source
    public final long read(Buffer buffer, long j) {
        if (buffer == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (j >= 0) {
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
        throw new IllegalArgumentException("byteCount < 0: " + j);
    }

    @Override // okio.BufferedSource
    public final Buffer buffer() {
        return this;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
    public final void close() {
    }

    @Override // java.io.Flushable
    public final void flush() {
    }
}

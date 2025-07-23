package android.util.proto;

import android.os.BatteryStats;
import android.util.Log;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public final class EncodedBuffer {
    private static final String TAG = "EncodedBuffer";
    private int mBufferCount;
    private final ArrayList<byte[]> mBuffers;
    private final int mChunkSize;
    private int mReadBufIndex;
    private byte[] mReadBuffer;
    private int mReadIndex;
    private int mReadLimit;
    private int mReadableSize;
    private int mWriteBufIndex;
    private byte[] mWriteBuffer;
    private int mWriteIndex;

    public static int getRawVarint32Size(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return (i & (-268435456)) == 0 ? 4 : 5;
    }

    public static int getRawVarint64Size(long j) {
        if (((-128) & j) == 0) {
            return 1;
        }
        if (((-16384) & j) == 0) {
            return 2;
        }
        if (((-2097152) & j) == 0) {
            return 3;
        }
        if (((-268435456) & j) == 0) {
            return 4;
        }
        if (((-34359738368L) & j) == 0) {
            return 5;
        }
        if (((-4398046511104L) & j) == 0) {
            return 6;
        }
        if (((-562949953421312L) & j) == 0) {
            return 7;
        }
        if ((BatteryStats.STEP_LEVEL_MODIFIED_MODE_MASK & j) == 0) {
            return 8;
        }
        return (j & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    private static int zigZag32(int i) {
        return (i >> 31) ^ (i << 1);
    }

    private static long zigZag64(long j) {
        return (j >> 63) ^ (j << 1);
    }

    public EncodedBuffer() {
        this(0);
    }

    public EncodedBuffer(int i) {
        ArrayList<byte[]> arrayList = new ArrayList<>();
        this.mBuffers = arrayList;
        this.mReadLimit = -1;
        this.mReadableSize = -1;
        i = i <= 0 ? 8192 : i;
        this.mChunkSize = i;
        byte[] bArr = new byte[i];
        this.mWriteBuffer = bArr;
        arrayList.add(bArr);
        this.mBufferCount = 1;
    }

    public void startEditing() {
        int i = this.mWriteBufIndex * this.mChunkSize;
        int i2 = this.mWriteIndex;
        this.mReadableSize = i + i2;
        this.mReadLimit = i2;
        byte[] bArr = this.mBuffers.get(0);
        this.mWriteBuffer = bArr;
        this.mWriteIndex = 0;
        this.mWriteBufIndex = 0;
        this.mReadBuffer = bArr;
        this.mReadBufIndex = 0;
        this.mReadIndex = 0;
    }

    public void rewindRead() {
        this.mReadBuffer = this.mBuffers.get(0);
        this.mReadBufIndex = 0;
        this.mReadIndex = 0;
    }

    public int getReadableSize() {
        return this.mReadableSize;
    }

    public int getSize() {
        return ((this.mBufferCount - 1) * this.mChunkSize) + this.mWriteIndex;
    }

    public int getReadPos() {
        return (this.mReadBufIndex * this.mChunkSize) + this.mReadIndex;
    }

    public void skipRead(int i) {
        if (i < 0) {
            throw new RuntimeException("skipRead with negative amount=" + i);
        }
        if (i == 0) {
            return;
        }
        int i2 = this.mChunkSize;
        int i3 = this.mReadIndex;
        if (i <= i2 - i3) {
            this.mReadIndex = i3 + i;
            return;
        }
        int i4 = i - (i2 - i3);
        int i5 = i4 % i2;
        this.mReadIndex = i5;
        if (i5 == 0) {
            this.mReadIndex = i2;
            this.mReadBufIndex += i4 / i2;
        } else {
            this.mReadBufIndex += (i4 / i2) + 1;
        }
        this.mReadBuffer = this.mBuffers.get(this.mReadBufIndex);
    }

    public byte readRawByte() {
        int i = this.mReadBufIndex;
        int i2 = this.mBufferCount;
        if (i > i2 || (i == i2 - 1 && this.mReadIndex >= this.mReadLimit)) {
            throw new IndexOutOfBoundsException("Trying to read too much data mReadBufIndex=" + this.mReadBufIndex + " mBufferCount=" + this.mBufferCount + " mReadIndex=" + this.mReadIndex + " mReadLimit=" + this.mReadLimit);
        }
        if (this.mReadIndex >= this.mChunkSize) {
            int i3 = i + 1;
            this.mReadBufIndex = i3;
            this.mReadBuffer = this.mBuffers.get(i3);
            this.mReadIndex = 0;
        }
        byte[] bArr = this.mReadBuffer;
        int i4 = this.mReadIndex;
        this.mReadIndex = i4 + 1;
        return bArr[i4];
    }

    public long readRawUnsigned() {
        int i = 0;
        long j = 0;
        do {
            j |= (r3 & Byte.MAX_VALUE) << i;
            if ((readRawByte() & 128) == 0) {
                return j;
            }
            i += 7;
        } while (i <= 64);
        throw new ProtoParseException("Varint too long -- " + getDebugString());
    }

    public int readRawFixed32() {
        return ((readRawByte() & 255) << 24) | (readRawByte() & 255) | ((readRawByte() & 255) << 8) | ((readRawByte() & 255) << 16);
    }

    private void nextWriteBuffer() {
        int i = this.mWriteBufIndex + 1;
        this.mWriteBufIndex = i;
        if (i >= this.mBufferCount) {
            byte[] bArr = new byte[this.mChunkSize];
            this.mWriteBuffer = bArr;
            this.mBuffers.add(bArr);
            this.mBufferCount++;
        } else {
            this.mWriteBuffer = this.mBuffers.get(i);
        }
        this.mWriteIndex = 0;
    }

    public void writeRawByte(byte b) {
        if (this.mWriteIndex >= this.mChunkSize) {
            nextWriteBuffer();
        }
        byte[] bArr = this.mWriteBuffer;
        int i = this.mWriteIndex;
        this.mWriteIndex = i + 1;
        bArr[i] = b;
    }

    public void writeRawVarint32(int i) {
        while ((i & (-128)) != 0) {
            writeRawByte((byte) ((i & 127) | 128));
            i >>>= 7;
        }
        writeRawByte((byte) i);
    }

    public static int getRawZigZag32Size(int i) {
        return getRawVarint32Size(zigZag32(i));
    }

    public void writeRawZigZag32(int i) {
        writeRawVarint32(zigZag32(i));
    }

    public void writeRawVarint64(long j) {
        while (((-128) & j) != 0) {
            writeRawByte((byte) ((127 & j) | 128));
            j >>>= 7;
        }
        writeRawByte((byte) j);
    }

    public static int getRawZigZag64Size(long j) {
        return getRawVarint64Size(zigZag64(j));
    }

    public void writeRawZigZag64(long j) {
        writeRawVarint64(zigZag64(j));
    }

    public void writeRawFixed32(int i) {
        writeRawByte((byte) i);
        writeRawByte((byte) (i >> 8));
        writeRawByte((byte) (i >> 16));
        writeRawByte((byte) (i >> 24));
    }

    public void writeRawFixed64(long j) {
        writeRawByte((byte) j);
        writeRawByte((byte) (j >> 8));
        writeRawByte((byte) (j >> 16));
        writeRawByte((byte) (j >> 24));
        writeRawByte((byte) (j >> 32));
        writeRawByte((byte) (j >> 40));
        writeRawByte((byte) (j >> 48));
        writeRawByte((byte) (j >> 56));
    }

    public void writeRawBuffer(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        writeRawBuffer(bArr, 0, bArr.length);
    }

    public void writeRawBuffer(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            return;
        }
        int i3 = this.mChunkSize;
        int i4 = this.mWriteIndex;
        int i5 = i2 < i3 - i4 ? i2 : i3 - i4;
        if (i5 > 0) {
            System.arraycopy(bArr, i, this.mWriteBuffer, i4, i5);
            this.mWriteIndex += i5;
            i2 -= i5;
            i += i5;
        }
        while (i2 > 0) {
            nextWriteBuffer();
            int i6 = this.mChunkSize;
            if (i2 < i6) {
                i6 = i2;
            }
            System.arraycopy(bArr, i, this.mWriteBuffer, this.mWriteIndex, i6);
            this.mWriteIndex += i6;
            i2 -= i6;
            i += i6;
        }
    }

    public void writeFromThisBuffer(int i, int i2) {
        if (this.mReadLimit < 0) {
            throw new IllegalStateException("writeFromThisBuffer before startEditing");
        }
        if (i < getWritePos()) {
            throw new IllegalArgumentException("Can only move forward in the buffer -- srcOffset=" + i + " size=" + i2 + " " + getDebugString());
        }
        if (i + i2 > this.mReadableSize) {
            throw new IllegalArgumentException("Trying to move more data than there is -- srcOffset=" + i + " size=" + i2 + " " + getDebugString());
        }
        if (i2 == 0) {
            return;
        }
        int i3 = this.mWriteBufIndex;
        int i4 = this.mChunkSize;
        int i5 = this.mWriteIndex;
        if (i == (i3 * i4) + i5) {
            if (i2 <= i4 - i5) {
                this.mWriteIndex = i5 + i2;
                return;
            }
            int i6 = i2 - (i4 - i5);
            int i7 = i6 % i4;
            this.mWriteIndex = i7;
            if (i7 == 0) {
                this.mWriteIndex = i4;
                this.mWriteBufIndex = i3 + (i6 / i4);
            } else {
                this.mWriteBufIndex = i3 + (i6 / i4) + 1;
            }
            this.mWriteBuffer = this.mBuffers.get(this.mWriteBufIndex);
            return;
        }
        int i8 = i / i4;
        byte[] bArr = this.mBuffers.get(i8);
        int i9 = i % this.mChunkSize;
        while (i2 > 0) {
            if (this.mWriteIndex >= this.mChunkSize) {
                nextWriteBuffer();
            }
            if (i9 >= this.mChunkSize) {
                i8++;
                bArr = this.mBuffers.get(i8);
                i9 = 0;
            }
            int i10 = this.mChunkSize;
            int min = Math.min(i2, Math.min(i10 - this.mWriteIndex, i10 - i9));
            System.arraycopy(bArr, i9, this.mWriteBuffer, this.mWriteIndex, min);
            this.mWriteIndex += min;
            i9 += min;
            i2 -= min;
        }
    }

    public int getWritePos() {
        return (this.mWriteBufIndex * this.mChunkSize) + this.mWriteIndex;
    }

    public void rewindWriteTo(int i) {
        if (i > getWritePos()) {
            throw new RuntimeException("rewindWriteTo only can go backwards" + i);
        }
        int i2 = this.mChunkSize;
        int i3 = i / i2;
        this.mWriteBufIndex = i3;
        int i4 = i % i2;
        this.mWriteIndex = i4;
        if (i4 == 0 && i3 != 0) {
            this.mWriteIndex = i2;
            this.mWriteBufIndex = i3 - 1;
        }
        this.mWriteBuffer = this.mBuffers.get(this.mWriteBufIndex);
    }

    public int getRawFixed32At(int i) {
        byte[] bArr = this.mBuffers.get(i / this.mChunkSize);
        int i2 = this.mChunkSize;
        int i3 = bArr[i % i2] & 255;
        int i4 = i + 1;
        byte[] bArr2 = this.mBuffers.get(i4 / i2);
        int i5 = this.mChunkSize;
        int i6 = i3 | ((bArr2[i4 % i5] & 255) << 8);
        int i7 = i + 2;
        byte[] bArr3 = this.mBuffers.get(i7 / i5);
        int i8 = this.mChunkSize;
        int i9 = i + 3;
        return ((this.mBuffers.get(i9 / i8)[i9 % this.mChunkSize] & 255) << 24) | i6 | ((bArr3[i7 % i8] & 255) << 16);
    }

    public void editRawFixed32(int i, int i2) {
        byte[] bArr = this.mBuffers.get(i / this.mChunkSize);
        int i3 = this.mChunkSize;
        bArr[i % i3] = (byte) i2;
        int i4 = i + 1;
        byte[] bArr2 = this.mBuffers.get(i4 / i3);
        int i5 = this.mChunkSize;
        bArr2[i4 % i5] = (byte) (i2 >> 8);
        int i6 = i + 2;
        byte[] bArr3 = this.mBuffers.get(i6 / i5);
        int i7 = this.mChunkSize;
        bArr3[i6 % i7] = (byte) (i2 >> 16);
        int i8 = i + 3;
        this.mBuffers.get(i8 / i7)[i8 % this.mChunkSize] = (byte) (i2 >> 24);
    }

    public byte[] getBytes(int i) {
        byte[] bArr = new byte[i];
        int i2 = i / this.mChunkSize;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            System.arraycopy(this.mBuffers.get(i3), 0, bArr, i4, this.mChunkSize);
            i4 += this.mChunkSize;
            i3++;
        }
        int i5 = i - (i2 * this.mChunkSize);
        if (i5 > 0) {
            System.arraycopy(this.mBuffers.get(i3), 0, bArr, i4, i5);
        }
        return bArr;
    }

    public int getChunkCount() {
        return this.mBuffers.size();
    }

    public int getWriteIndex() {
        return this.mWriteIndex;
    }

    public int getWriteBufIndex() {
        return this.mWriteBufIndex;
    }

    public String getDebugString() {
        return "EncodedBuffer( mChunkSize=" + this.mChunkSize + " mBuffers.size=" + this.mBuffers.size() + " mBufferCount=" + this.mBufferCount + " mWriteIndex=" + this.mWriteIndex + " mWriteBufIndex=" + this.mWriteBufIndex + " mReadBufIndex=" + this.mReadBufIndex + " mReadIndex=" + this.mReadIndex + " mReadableSize=" + this.mReadableSize + " mReadLimit=" + this.mReadLimit + " )";
    }

    public void dumpBuffers(String str) {
        int size = this.mBuffers.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i += dumpByteString(str, "{" + i2 + "} ", i, this.mBuffers.get(i2));
        }
    }

    public static void dumpByteString(String str, String str2, byte[] bArr) {
        dumpByteString(str, str2, 0, bArr);
    }

    private static int dumpByteString(String str, String str2, int i, byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        int length = bArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (i2 % 16 == 0) {
                if (i2 != 0) {
                    Log.d(str, sb.toString());
                    sb = new StringBuilder();
                }
                sb.append(str2);
                sb.append('[');
                sb.append(i + i2);
                sb.append(']');
                sb.append(' ');
            } else {
                sb.append(' ');
            }
            byte b = bArr[i2];
            byte b2 = (byte) ((b >> 4) & 15);
            if (b2 < 10) {
                sb.append((char) (b2 + SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90));
            } else {
                sb.append((char) (b2 + 87));
            }
            byte b3 = (byte) (b & 15);
            if (b3 < 10) {
                sb.append((char) (b3 + SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90));
            } else {
                sb.append((char) (b3 + 87));
            }
        }
        Log.d(str, sb.toString());
        return length;
    }
}

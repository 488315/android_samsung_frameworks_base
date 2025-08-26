package android.util.proto;

import android.util.LongArray;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class ProtoInputStream extends ProtoStream {
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int NO_MORE_FIELDS = -1;
    private static final byte STATE_FIELD_MISS = 4;
    private static final byte STATE_READING_PACKED = 2;
    private static final byte STATE_STARTED_FIELD_READ = 1;
    private byte[] mBuffer;
    private final int mBufferSize;
    private int mDepth;
    private int mDiscardedBytes;
    private int mEnd;
    private LongArray mExpectedObjectTokenStack;
    private int mFieldNumber;
    private int mOffset;
    private int mPackedEnd;
    private byte mState;
    private InputStream mStream;
    private int mWireType;

    public int decodeZigZag32(int i) {
        return (i >>> 1) ^ (-(i & 1));
    }

    public long decodeZigZag64(long j) {
        return (-(j & 1)) ^ (j >>> 1);
    }

    public ProtoInputStream(InputStream inputStream, int i) {
        this.mState = (byte) 0;
        this.mExpectedObjectTokenStack = null;
        this.mDepth = -1;
        this.mDiscardedBytes = 0;
        this.mOffset = 0;
        this.mEnd = 0;
        this.mPackedEnd = 0;
        this.mStream = inputStream;
        if (i > 0) {
            this.mBufferSize = i;
        } else {
            this.mBufferSize = 8192;
        }
        this.mBuffer = new byte[this.mBufferSize];
    }

    public ProtoInputStream(InputStream inputStream) {
        this(inputStream, 8192);
    }

    public ProtoInputStream(byte[] bArr) {
        this.mState = (byte) 0;
        this.mExpectedObjectTokenStack = null;
        this.mDepth = -1;
        this.mDiscardedBytes = 0;
        this.mOffset = 0;
        this.mEnd = 0;
        this.mPackedEnd = 0;
        this.mBufferSize = bArr.length;
        this.mEnd = bArr.length;
        this.mBuffer = bArr;
        this.mStream = null;
    }

    public int getFieldNumber() {
        return this.mFieldNumber;
    }

    public int getWireType() {
        if ((this.mState & 2) == 2) {
            return 2;
        }
        return this.mWireType;
    }

    public int getOffset() {
        return this.mOffset + this.mDiscardedBytes;
    }

    public int nextField() throws IOException {
        byte b = this.mState;
        if ((b & 4) == 4) {
            this.mState = (byte) (b & (-5));
            return this.mFieldNumber;
        }
        if ((b & 1) == 1) {
            skip();
            this.mState = (byte) (this.mState & (-2));
        }
        if ((this.mState & 2) == 2) {
            if (getOffset() < this.mPackedEnd) {
                this.mState = (byte) (this.mState | 1);
                return this.mFieldNumber;
            }
            if (getOffset() == this.mPackedEnd) {
                this.mState = (byte) (this.mState & (-3));
            } else {
                throw new ProtoParseException("Unexpectedly reached end of packed field at offset 0x" + Integer.toHexString(this.mPackedEnd) + dumpDebugData());
            }
        }
        if (this.mDepth >= 0 && getOffset() == getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth))) {
            this.mFieldNumber = -1;
        } else {
            readTag();
        }
        return this.mFieldNumber;
    }

    public boolean nextField(long j) throws IOException {
        if (nextField() == ((int) j)) {
            return true;
        }
        this.mState = (byte) (this.mState | 4);
        return false;
    }

    public double readDouble(long j) throws IOException {
        assertFreshData();
        assertFieldNumber(j);
        checkPacked(j);
        if (((int) ((ProtoStream.FIELD_TYPE_MASK & j) >>> 32)) == 1) {
            assertWireType(1);
            double dLongBitsToDouble = Double.longBitsToDouble(readFixed64());
            this.mState = (byte) (this.mState & (-2));
            return dLongBitsToDouble;
        }
        throw new IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") cannot be read as a double" + dumpDebugData());
    }

    public float readFloat(long j) throws IOException {
        assertFreshData();
        assertFieldNumber(j);
        checkPacked(j);
        if (((int) ((ProtoStream.FIELD_TYPE_MASK & j) >>> 32)) == 2) {
            assertWireType(5);
            float fIntBitsToFloat = Float.intBitsToFloat(readFixed32());
            this.mState = (byte) (this.mState & (-2));
            return fIntBitsToFloat;
        }
        throw new IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") is not a float" + dumpDebugData());
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x005a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int readInt(long j) throws IOException {
        int varint;
        assertFreshData();
        assertFieldNumber(j);
        checkPacked(j);
        int i = (int) ((ProtoStream.FIELD_TYPE_MASK & j) >>> 32);
        if (i == 5) {
            assertWireType(0);
            varint = (int) readVarint();
        } else if (i == 7) {
            assertWireType(5);
            varint = readFixed32();
        } else if (i == 17) {
            assertWireType(0);
            varint = decodeZigZag32((int) readVarint());
        } else {
            switch (i) {
                case 13:
                case 14:
                    break;
                case 15:
                    break;
                default:
                    throw new IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") is not an int" + dumpDebugData());
            }
        }
        this.mState = (byte) (this.mState & (-2));
        return varint;
    }

    public long readLong(long j) throws IOException {
        long varint;
        assertFreshData();
        assertFieldNumber(j);
        checkPacked(j);
        int i = (int) ((ProtoStream.FIELD_TYPE_MASK & j) >>> 32);
        if (i == 3 || i == 4) {
            assertWireType(0);
            varint = readVarint();
        } else if (i == 6 || i == 16) {
            assertWireType(1);
            varint = readFixed64();
        } else if (i == 18) {
            assertWireType(0);
            varint = decodeZigZag64(readVarint());
        } else {
            throw new IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") is not an long" + dumpDebugData());
        }
        this.mState = (byte) (this.mState & (-2));
        return varint;
    }

    public boolean readBoolean(long j) throws IOException {
        assertFreshData();
        assertFieldNumber(j);
        checkPacked(j);
        if (((int) ((ProtoStream.FIELD_TYPE_MASK & j) >>> 32)) == 8) {
            assertWireType(0);
            boolean z = readVarint() != 0;
            this.mState = (byte) (this.mState & (-2));
            return z;
        }
        throw new IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") is not an boolean" + dumpDebugData());
    }

    public String readString(long j) throws IOException {
        assertFreshData();
        assertFieldNumber(j);
        if (((int) ((ProtoStream.FIELD_TYPE_MASK & j) >>> 32)) == 9) {
            assertWireType(2);
            String rawString = readRawString((int) readVarint());
            this.mState = (byte) (this.mState & (-2));
            return rawString;
        }
        throw new IllegalArgumentException("Requested field id(" + getFieldIdString(j) + ") is not an string" + dumpDebugData());
    }

    public byte[] readBytes(long j) throws IOException {
        assertFreshData();
        assertFieldNumber(j);
        int i = (int) ((ProtoStream.FIELD_TYPE_MASK & j) >>> 32);
        if (i == 11 || i == 12) {
            assertWireType(2);
            byte[] rawBytes = readRawBytes((int) readVarint());
            this.mState = (byte) (this.mState & (-2));
            return rawBytes;
        }
        throw new IllegalArgumentException("Requested field type (" + getFieldIdString(j) + ") cannot be read as raw bytes" + dumpDebugData());
    }

    public long start(long j) throws IOException {
        assertFreshData();
        assertFieldNumber(j);
        assertWireType(2);
        int varint = (int) readVarint();
        if (this.mExpectedObjectTokenStack == null) {
            this.mExpectedObjectTokenStack = new LongArray();
        }
        int i = this.mDepth + 1;
        this.mDepth = i;
        if (i == this.mExpectedObjectTokenStack.size()) {
            this.mExpectedObjectTokenStack.add(makeToken(0, (j & 2199023255552L) == 2199023255552L, this.mDepth, (int) j, getOffset() + varint));
        } else {
            LongArray longArray = this.mExpectedObjectTokenStack;
            int i2 = this.mDepth;
            longArray.set(i2, makeToken(0, (j & 2199023255552L) == 2199023255552L, i2, (int) j, getOffset() + varint));
        }
        int i3 = this.mDepth;
        if (i3 > 0 && getOffsetFromToken(this.mExpectedObjectTokenStack.get(i3)) > getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth - 1))) {
            throw new ProtoParseException("Embedded Object (" + token2String(this.mExpectedObjectTokenStack.get(this.mDepth)) + ") ends after of parent Objects's (" + token2String(this.mExpectedObjectTokenStack.get(this.mDepth - 1)) + ") end" + dumpDebugData());
        }
        this.mState = (byte) (this.mState & (-2));
        return this.mExpectedObjectTokenStack.get(this.mDepth);
    }

    public void end(long j) {
        if (this.mExpectedObjectTokenStack.get(this.mDepth) != j) {
            throw new ProtoParseException("end token " + j + " does not match current message token " + this.mExpectedObjectTokenStack.get(this.mDepth) + dumpDebugData());
        }
        if (getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth)) > getOffset()) {
            incOffset(getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth)) - getOffset());
        }
        this.mDepth--;
        this.mState = (byte) (this.mState & (-2));
    }

    private void readTag() throws IOException {
        fillBuffer();
        if (this.mOffset >= this.mEnd) {
            this.mFieldNumber = -1;
            return;
        }
        int varint = (int) readVarint();
        this.mFieldNumber = varint >>> 3;
        this.mWireType = varint & 7;
        this.mState = (byte) (this.mState | 1);
    }

    private long readVarint() throws IOException {
        long j = 0;
        int i = 0;
        while (true) {
            fillBuffer();
            int i2 = this.mEnd - this.mOffset;
            if (i2 < 0) {
                throw new ProtoParseException("Incomplete varint at offset 0x" + Integer.toHexString(getOffset()) + dumpDebugData());
            }
            for (int i3 = 0; i3 < i2; i3++) {
                byte b = this.mBuffer[this.mOffset + i3];
                j |= (b & 127) << i;
                if ((b & 128) == 0) {
                    incOffset(i3 + 1);
                    return j;
                }
                i += 7;
                if (i > 63) {
                    throw new ProtoParseException("Varint is too large at offset 0x" + Integer.toHexString(getOffset() + i3) + dumpDebugData());
                }
            }
            incOffset(i2);
        }
    }

    private int readFixed32() throws IOException {
        if (this.mOffset + 4 <= this.mEnd) {
            incOffset(4);
            byte[] bArr = this.mBuffer;
            int i = this.mOffset;
            return ((bArr[i - 1] & 255) << 24) | (bArr[i - 4] & 255) | ((bArr[i - 3] & 255) << 8) | ((bArr[i - 2] & 255) << 16);
        }
        int i2 = 0;
        int i3 = 4;
        int i4 = 0;
        while (i3 > 0) {
            fillBuffer();
            int i5 = this.mEnd;
            int i6 = this.mOffset;
            int i7 = i5 - i6 < i3 ? i5 - i6 : i3;
            if (i7 < 0) {
                throw new ProtoParseException("Incomplete fixed32 at offset 0x" + Integer.toHexString(getOffset()) + dumpDebugData());
            }
            incOffset(i7);
            i3 -= i7;
            while (i7 > 0) {
                i2 |= (this.mBuffer[this.mOffset - i7] & 255) << i4;
                i7--;
                i4 += 8;
            }
        }
        return i2;
    }

    private long readFixed64() throws IOException {
        int i = 8;
        if (this.mOffset + 8 <= this.mEnd) {
            incOffset(8);
            byte[] bArr = this.mBuffer;
            int i2 = this.mOffset;
            return ((bArr[i2 - 7] & 255) << 8) | (bArr[i2 - 8] & 255) | ((bArr[i2 - 6] & 255) << 16) | ((bArr[i2 - 5] & 255) << 24) | ((bArr[i2 - 4] & 255) << 32) | ((bArr[i2 - 3] & 255) << 40) | ((bArr[i2 - 2] & 255) << 48) | ((255 & bArr[i2 - 1]) << 56);
        }
        long j = 0;
        int i3 = 0;
        while (i > 0) {
            fillBuffer();
            int i4 = this.mEnd;
            int i5 = this.mOffset;
            int i6 = i4 - i5 < i ? i4 - i5 : i;
            if (i6 < 0) {
                throw new ProtoParseException("Incomplete fixed64 at offset 0x" + Integer.toHexString(getOffset()) + dumpDebugData());
            }
            incOffset(i6);
            i -= i6;
            while (i6 > 0) {
                j |= (this.mBuffer[this.mOffset - i6] & 255) << i3;
                i6--;
                i3 += 8;
            }
        }
        return j;
    }

    private byte[] readRawBytes(int i) throws IOException {
        byte[] bArr = new byte[i];
        int i2 = 0;
        do {
            int i3 = this.mOffset;
            int i4 = (i3 + i) - i2;
            int i5 = this.mEnd;
            if (i4 > i5) {
                int i6 = i5 - i3;
                if (i6 > 0) {
                    System.arraycopy(this.mBuffer, i3, bArr, i2, i6);
                    incOffset(i6);
                    i2 += i6;
                }
                fillBuffer();
            } else {
                int i7 = i - i2;
                System.arraycopy(this.mBuffer, i3, bArr, i2, i7);
                incOffset(i7);
                return bArr;
            }
        } while (this.mOffset < this.mEnd);
        throw new ProtoParseException("Unexpectedly reached end of the InputStream at offset 0x" + Integer.toHexString(this.mEnd) + dumpDebugData());
    }

    private String readRawString(int i) throws IOException {
        fillBuffer();
        int i2 = this.mOffset;
        int i3 = i2 + i;
        int i4 = this.mEnd;
        if (i3 <= i4) {
            String str = new String(this.mBuffer, this.mOffset, i, StandardCharsets.UTF_8);
            incOffset(i);
            return str;
        }
        if (i <= this.mBufferSize) {
            int i5 = i4 - i2;
            byte[] bArr = this.mBuffer;
            System.arraycopy(bArr, i2, bArr, 0, i5);
            this.mEnd = i5 + this.mStream.read(this.mBuffer, i5, i - i5);
            this.mDiscardedBytes += this.mOffset;
            this.mOffset = 0;
            String str2 = new String(this.mBuffer, this.mOffset, i, StandardCharsets.UTF_8);
            incOffset(i);
            return str2;
        }
        return new String(readRawBytes(i), 0, i, StandardCharsets.UTF_8);
    }

    private void fillBuffer() throws IOException {
        InputStream inputStream;
        int i = this.mOffset;
        int i2 = this.mEnd;
        if (i < i2 || (inputStream = this.mStream) == null) {
            return;
        }
        int i3 = i - i2;
        this.mOffset = i3;
        this.mDiscardedBytes += i2;
        if (i3 >= this.mBufferSize) {
            int iSkip = (int) inputStream.skip((i3 / r1) * r1);
            this.mDiscardedBytes += iSkip;
            this.mOffset -= iSkip;
        }
        this.mEnd = this.mStream.read(this.mBuffer);
    }

    public void skip() throws IOException {
        byte b;
        if ((this.mState & 2) == 2) {
            incOffset(this.mPackedEnd - getOffset());
        } else {
            int i = this.mWireType;
            if (i == 0) {
                do {
                    fillBuffer();
                    b = this.mBuffer[this.mOffset];
                    incOffset(1);
                } while ((b & 128) != 0);
            } else if (i == 1) {
                incOffset(8);
            } else if (i == 2) {
                fillBuffer();
                incOffset((int) readVarint());
            } else if (i == 5) {
                incOffset(4);
            } else {
                throw new ProtoParseException("Unexpected wire type: " + this.mWireType + " at offset 0x" + Integer.toHexString(this.mOffset) + dumpDebugData());
            }
        }
        this.mState = (byte) (this.mState & (-2));
    }

    private void incOffset(int i) {
        this.mOffset += i;
        if (this.mDepth < 0 || getOffset() <= getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth))) {
            return;
        }
        throw new ProtoParseException("Unexpectedly reached end of embedded object.  " + token2String(this.mExpectedObjectTokenStack.get(this.mDepth)) + dumpDebugData());
    }

    private void checkPacked(long j) throws IOException {
        if (this.mWireType == 2) {
            int varint = (int) readVarint();
            this.mPackedEnd = getOffset() + varint;
            this.mState = (byte) (2 | this.mState);
            switch ((int) ((ProtoStream.FIELD_TYPE_MASK & j) >>> 32)) {
                case 1:
                case 6:
                case 16:
                    if (varint % 8 != 0) {
                        throw new IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") packed length " + varint + " is not aligned for fixed64" + dumpDebugData());
                    }
                    this.mWireType = 1;
                    return;
                case 2:
                case 7:
                case 15:
                    if (varint % 4 != 0) {
                        throw new IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") packed length " + varint + " is not aligned for fixed32" + dumpDebugData());
                    }
                    this.mWireType = 5;
                    return;
                case 3:
                case 4:
                case 5:
                case 8:
                case 13:
                case 14:
                case 17:
                case 18:
                    this.mWireType = 0;
                    return;
                case 9:
                case 10:
                case 11:
                case 12:
                default:
                    throw new IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") is not a packable field" + dumpDebugData());
            }
        }
    }

    private void assertFieldNumber(long j) {
        if (((int) j) == this.mFieldNumber) {
            return;
        }
        throw new IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") does not match current field number (0x" + Integer.toHexString(this.mFieldNumber) + ") at offset 0x" + Integer.toHexString(getOffset()) + dumpDebugData());
    }

    private void assertWireType(int i) {
        if (i == this.mWireType) {
            return;
        }
        throw new WireTypeMismatchException("Current wire type " + getWireTypeString(this.mWireType) + " does not match expected wire type " + getWireTypeString(i) + " at offset 0x" + Integer.toHexString(getOffset()) + dumpDebugData());
    }

    private void assertFreshData() {
        if ((this.mState & 1) == 1) {
            return;
        }
        throw new ProtoParseException("Attempting to read already read field at offset 0x" + Integer.toHexString(getOffset()) + dumpDebugData());
    }

    public String dumpDebugData() {
        return "\nmFieldNumber : 0x" + Integer.toHexString(this.mFieldNumber) + "\nmWireType : 0x" + Integer.toHexString(this.mWireType) + "\nmState : 0x" + Integer.toHexString(this.mState) + "\nmDiscardedBytes : 0x" + Integer.toHexString(this.mDiscardedBytes) + "\nmOffset : 0x" + Integer.toHexString(this.mOffset) + "\nmExpectedObjectTokenStack : " + Objects.toString(this.mExpectedObjectTokenStack) + "\nmDepth : 0x" + Integer.toHexString(this.mDepth) + "\nmBuffer : " + Arrays.toString(this.mBuffer) + "\nmBufferSize : 0x" + Integer.toHexString(this.mBufferSize) + "\nmEnd : 0x" + Integer.toHexString(this.mEnd);
    }
}

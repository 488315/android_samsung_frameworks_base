package com.google.protobuf.nano;

/* loaded from: classes4.dex */
public final class CodedInputByteBufferNano {
    public final byte[] buffer;
    public int bufferPos;
    public int bufferSize;
    public int bufferSizeAfterLimit;
    public final int bufferStart;
    public int currentLimit = Integer.MAX_VALUE;
    public int lastTag;
    public int recursionDepth;

    private CodedInputByteBufferNano(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.bufferStart = i;
        this.bufferSize = i2 + i;
        this.bufferPos = i;
    }

    public static CodedInputByteBufferNano newInstance(int i, int i2, byte[] bArr) {
        return new CodedInputByteBufferNano(bArr, i, i2);
    }

    public final void readMessage(MessageNano messageNano) throws InvalidProtocolBufferNanoException {
        int rawVarint32 = readRawVarint32();
        int i = this.recursionDepth;
        if (i >= 64) {
            throw new InvalidProtocolBufferNanoException("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
        }
        if (rawVarint32 < 0) {
            throw new InvalidProtocolBufferNanoException("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        int i2 = rawVarint32 + this.bufferPos;
        int i3 = this.currentLimit;
        if (i2 > i3) {
            throw InvalidProtocolBufferNanoException.truncatedMessage();
        }
        this.currentLimit = i2;
        int i4 = this.bufferSize + this.bufferSizeAfterLimit;
        this.bufferSize = i4;
        if (i4 > i2) {
            int i5 = i4 - i2;
            this.bufferSizeAfterLimit = i5;
            this.bufferSize = i4 - i5;
        } else {
            this.bufferSizeAfterLimit = 0;
        }
        this.recursionDepth = i + 1;
        messageNano.mergeFrom(this);
        if (this.lastTag != 0) {
            throw new InvalidProtocolBufferNanoException("Protocol message end-group tag did not match expected tag.");
        }
        this.recursionDepth--;
        this.currentLimit = i3;
        int i6 = this.bufferSize + this.bufferSizeAfterLimit;
        this.bufferSize = i6;
        if (i6 <= i3) {
            this.bufferSizeAfterLimit = 0;
            return;
        }
        int i7 = i6 - i3;
        this.bufferSizeAfterLimit = i7;
        this.bufferSize = i6 - i7;
    }

    public final byte readRawByte() throws InvalidProtocolBufferNanoException {
        int i = this.bufferPos;
        if (i == this.bufferSize) {
            throw InvalidProtocolBufferNanoException.truncatedMessage();
        }
        this.bufferPos = i + 1;
        return this.buffer[i];
    }

    public final int readRawVarint32() throws InvalidProtocolBufferNanoException {
        int i;
        byte rawByte = readRawByte();
        if (rawByte >= 0) {
            return rawByte;
        }
        int i2 = rawByte & Byte.MAX_VALUE;
        byte rawByte2 = readRawByte();
        if (rawByte2 >= 0) {
            i = rawByte2 << 7;
        } else {
            i2 |= (rawByte2 & Byte.MAX_VALUE) << 7;
            byte rawByte3 = readRawByte();
            if (rawByte3 >= 0) {
                i = rawByte3 << 14;
            } else {
                i2 |= (rawByte3 & Byte.MAX_VALUE) << 14;
                byte rawByte4 = readRawByte();
                if (rawByte4 < 0) {
                    int i3 = i2 | ((rawByte4 & Byte.MAX_VALUE) << 21);
                    byte rawByte5 = readRawByte();
                    int i4 = i3 | (rawByte5 << 28);
                    if (rawByte5 < 0) {
                        for (int i5 = 0; i5 < 5; i5++) {
                            if (readRawByte() < 0) {
                            }
                        }
                        throw new InvalidProtocolBufferNanoException("CodedInputStream encountered a malformed varint.");
                    }
                    return i4;
                }
                i = rawByte4 << 21;
            }
        }
        return i | i2;
    }

    public final String readString() throws InvalidProtocolBufferNanoException {
        int rawVarint32 = readRawVarint32();
        int i = this.bufferSize;
        int i2 = this.bufferPos;
        int i3 = i - i2;
        byte[] bArr = this.buffer;
        if (rawVarint32 <= i3 && rawVarint32 > 0) {
            String str = new String(bArr, i2, rawVarint32, InternalNano.UTF_8);
            this.bufferPos += rawVarint32;
            return str;
        }
        if (rawVarint32 < 0) {
            throw new InvalidProtocolBufferNanoException("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        int i4 = i2 + rawVarint32;
        int i5 = this.currentLimit;
        if (i4 > i5) {
            skipRawBytes(i5 - i2);
            throw InvalidProtocolBufferNanoException.truncatedMessage();
        }
        if (rawVarint32 > i3) {
            throw InvalidProtocolBufferNanoException.truncatedMessage();
        }
        byte[] bArr2 = new byte[rawVarint32];
        System.arraycopy(bArr, i2, bArr2, 0, rawVarint32);
        this.bufferPos += rawVarint32;
        return new String(bArr2, InternalNano.UTF_8);
    }

    public final int readTag() throws InvalidProtocolBufferNanoException {
        if (this.bufferPos == this.bufferSize) {
            this.lastTag = 0;
            return 0;
        }
        int rawVarint32 = readRawVarint32();
        this.lastTag = rawVarint32;
        if (rawVarint32 != 0) {
            return rawVarint32;
        }
        throw new InvalidProtocolBufferNanoException("Protocol message contained an invalid tag (zero).");
    }

    public final boolean skipField(int i) throws InvalidProtocolBufferNanoException {
        int tag;
        int i2 = i & 7;
        if (i2 == 0) {
            readRawVarint32();
            return true;
        }
        if (i2 == 1) {
            readRawByte();
            readRawByte();
            readRawByte();
            readRawByte();
            readRawByte();
            readRawByte();
            readRawByte();
            readRawByte();
            return true;
        }
        if (i2 == 2) {
            skipRawBytes(readRawVarint32());
            return true;
        }
        if (i2 == 3) {
            do {
                tag = readTag();
                if (tag == 0) {
                    break;
                }
            } while (skipField(tag));
            if (this.lastTag == (((i >>> 3) << 3) | 4)) {
                return true;
            }
            throw new InvalidProtocolBufferNanoException("Protocol message end-group tag did not match expected tag.");
        }
        if (i2 == 4) {
            return false;
        }
        if (i2 != 5) {
            throw new InvalidProtocolBufferNanoException("Protocol message tag had invalid wire type.");
        }
        readRawByte();
        readRawByte();
        readRawByte();
        readRawByte();
        return true;
    }

    public final void skipRawBytes(int i) throws InvalidProtocolBufferNanoException {
        if (i < 0) {
            throw new InvalidProtocolBufferNanoException("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        int i2 = this.bufferPos;
        int i3 = i2 + i;
        int i4 = this.currentLimit;
        if (i3 > i4) {
            skipRawBytes(i4 - i2);
            throw InvalidProtocolBufferNanoException.truncatedMessage();
        }
        if (i > this.bufferSize - i2) {
            throw InvalidProtocolBufferNanoException.truncatedMessage();
        }
        this.bufferPos = i3;
    }
}

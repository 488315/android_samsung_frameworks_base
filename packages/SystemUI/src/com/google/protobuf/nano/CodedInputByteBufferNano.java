package com.google.protobuf.nano;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public final void readMessage(MessageNano messageNano) {
        int readRawVarint32 = readRawVarint32();
        int i = this.recursionDepth;
        if (i >= 64) {
            throw new InvalidProtocolBufferNanoException("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
        }
        if (readRawVarint32 < 0) {
            throw new InvalidProtocolBufferNanoException("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        int i2 = readRawVarint32 + this.bufferPos;
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

    public final byte readRawByte() {
        int i = this.bufferPos;
        if (i == this.bufferSize) {
            throw InvalidProtocolBufferNanoException.truncatedMessage();
        }
        this.bufferPos = i + 1;
        return this.buffer[i];
    }

    public final int readRawVarint32() {
        int i;
        byte readRawByte = readRawByte();
        if (readRawByte >= 0) {
            return readRawByte;
        }
        int i2 = readRawByte & Byte.MAX_VALUE;
        byte readRawByte2 = readRawByte();
        if (readRawByte2 >= 0) {
            i = readRawByte2 << 7;
        } else {
            i2 |= (readRawByte2 & Byte.MAX_VALUE) << 7;
            byte readRawByte3 = readRawByte();
            if (readRawByte3 >= 0) {
                i = readRawByte3 << 14;
            } else {
                i2 |= (readRawByte3 & Byte.MAX_VALUE) << 14;
                byte readRawByte4 = readRawByte();
                if (readRawByte4 < 0) {
                    int i3 = i2 | ((readRawByte4 & Byte.MAX_VALUE) << 21);
                    byte readRawByte5 = readRawByte();
                    int i4 = i3 | (readRawByte5 << 28);
                    if (readRawByte5 < 0) {
                        for (int i5 = 0; i5 < 5; i5++) {
                            if (readRawByte() < 0) {
                            }
                        }
                        throw new InvalidProtocolBufferNanoException("CodedInputStream encountered a malformed varint.");
                    }
                    return i4;
                }
                i = readRawByte4 << 21;
            }
        }
        return i | i2;
    }

    public final String readString() {
        int readRawVarint32 = readRawVarint32();
        int i = this.bufferSize;
        int i2 = this.bufferPos;
        int i3 = i - i2;
        byte[] bArr = this.buffer;
        if (readRawVarint32 <= i3 && readRawVarint32 > 0) {
            String str = new String(bArr, i2, readRawVarint32, InternalNano.UTF_8);
            this.bufferPos += readRawVarint32;
            return str;
        }
        if (readRawVarint32 < 0) {
            throw new InvalidProtocolBufferNanoException("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        int i4 = i2 + readRawVarint32;
        int i5 = this.currentLimit;
        if (i4 > i5) {
            skipRawBytes(i5 - i2);
            throw InvalidProtocolBufferNanoException.truncatedMessage();
        }
        if (readRawVarint32 > i3) {
            throw InvalidProtocolBufferNanoException.truncatedMessage();
        }
        byte[] bArr2 = new byte[readRawVarint32];
        System.arraycopy(bArr, i2, bArr2, 0, readRawVarint32);
        this.bufferPos += readRawVarint32;
        return new String(bArr2, InternalNano.UTF_8);
    }

    public final int readTag() {
        if (this.bufferPos == this.bufferSize) {
            this.lastTag = 0;
            return 0;
        }
        int readRawVarint32 = readRawVarint32();
        this.lastTag = readRawVarint32;
        if (readRawVarint32 != 0) {
            return readRawVarint32;
        }
        throw new InvalidProtocolBufferNanoException("Protocol message contained an invalid tag (zero).");
    }

    public final boolean skipField(int i) {
        int readTag;
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
                readTag = readTag();
                if (readTag == 0) {
                    break;
                }
            } while (skipField(readTag));
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

    public final void skipRawBytes(int i) {
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

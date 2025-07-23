package com.google.protobuf.nano;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class CodedOutputByteBufferNano {
    public final ByteBuffer buffer;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class OutOfSpaceException extends IOException {
        private static final long serialVersionUID = -6947486886997889499L;

        public OutOfSpaceException(int i, int i2) {
            super(MutableVectorKt$$ExternalSyntheticOutline0.m(i, i2, "CodedOutputStream was writing to a flat byte array and ran out of space (pos ", " limit ", ")."));
        }
    }

    private CodedOutputByteBufferNano(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    public static int computeInt32Size(int i, int i2) {
        return computeTagSize(i) + (i2 >= 0 ? computeRawVarint32Size(i2) : 10);
    }

    public static int computeMessageSize(int i, MessageNano messageNano) {
        int computeTagSize = computeTagSize(i);
        int serializedSize = messageNano.getSerializedSize();
        return computeRawVarint32Size(serializedSize) + serializedSize + computeTagSize;
    }

    public static int computeRawVarint32Size(int i) {
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

    public static int computeStringSize(int i, String str) {
        int computeTagSize = computeTagSize(i);
        int encodedLength = encodedLength(str);
        return computeRawVarint32Size(encodedLength) + encodedLength + computeTagSize;
    }

    public static int computeTagSize(int i) {
        return computeRawVarint32Size(i << 3);
    }

    public static void encode(CharSequence charSequence, ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        if (byteBuffer.hasArray()) {
            try {
                byteBuffer.position(encode(charSequence, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining()) - byteBuffer.arrayOffset());
                return;
            } catch (ArrayIndexOutOfBoundsException e) {
                BufferOverflowException bufferOverflowException = new BufferOverflowException();
                bufferOverflowException.initCause(e);
                throw bufferOverflowException;
            }
        }
        String str = (String) charSequence;
        int length = str.length();
        int i = 0;
        while (i < length) {
            char charAt = str.charAt(i);
            if (charAt < 128) {
                byteBuffer.put((byte) charAt);
            } else if (charAt < 2048) {
                byteBuffer.put((byte) ((charAt >>> 6) | 960));
                byteBuffer.put((byte) ((charAt & '?') | 128));
            } else {
                if (charAt >= 55296 && 57343 >= charAt) {
                    int i2 = i + 1;
                    if (i2 != str.length()) {
                        char charAt2 = str.charAt(i2);
                        if (Character.isSurrogatePair(charAt, charAt2)) {
                            int codePoint = Character.toCodePoint(charAt, charAt2);
                            byteBuffer.put((byte) ((codePoint >>> 18) | IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp));
                            byteBuffer.put((byte) (((codePoint >>> 12) & 63) | 128));
                            byteBuffer.put((byte) (((codePoint >>> 6) & 63) | 128));
                            byteBuffer.put((byte) ((codePoint & 63) | 128));
                            i = i2;
                        } else {
                            i = i2;
                        }
                    }
                    StringBuilder sb = new StringBuilder("Unpaired surrogate at index ");
                    sb.append(i - 1);
                    throw new IllegalArgumentException(sb.toString());
                }
                byteBuffer.put((byte) ((charAt >>> '\f') | VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE));
                byteBuffer.put((byte) (((charAt >>> 6) & 63) | 128));
                byteBuffer.put((byte) ((charAt & '?') | 128));
            }
            i++;
        }
    }

    public static int encodedLength(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && charSequence.charAt(i2) < 128) {
            i2++;
        }
        int i3 = length;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char charAt = charSequence.charAt(i2);
            if (charAt < 2048) {
                i3 += (127 - charAt) >>> 31;
                i2++;
            } else {
                int length2 = charSequence.length();
                while (i2 < length2) {
                    char charAt2 = charSequence.charAt(i2);
                    if (charAt2 < 2048) {
                        i += (127 - charAt2) >>> 31;
                    } else {
                        i += 2;
                        if (55296 <= charAt2 && charAt2 <= 57343) {
                            if (Character.codePointAt(charSequence, i2) < 65536) {
                                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "Unpaired surrogate at index "));
                            }
                            i2++;
                        }
                    }
                    i2++;
                }
                i3 += i;
            }
        }
        if (i3 >= length) {
            return i3;
        }
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (i3 + 4294967296L));
    }

    public static CodedOutputByteBufferNano newInstance(int i, int i2, byte[] bArr) {
        return new CodedOutputByteBufferNano(bArr, i, i2);
    }

    public final void writeInt32(int i, int i2) {
        writeTag(i, 0);
        if (i2 >= 0) {
            writeRawVarint32(i2);
            return;
        }
        long j = i2;
        while (((-128) & j) != 0) {
            writeRawByte((((int) j) & 127) | 128);
            j >>>= 7;
        }
        writeRawByte((int) j);
    }

    public final void writeMessage(int i, MessageNano messageNano) {
        writeTag(i, 2);
        writeRawVarint32(messageNano.getCachedSize());
        messageNano.writeTo(this);
    }

    public final void writeRawByte(int i) {
        byte b = (byte) i;
        if (!this.buffer.hasRemaining()) {
            throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
        }
        this.buffer.put(b);
    }

    public final void writeRawVarint32(int i) {
        while ((i & (-128)) != 0) {
            writeRawByte((i & 127) | 128);
            i >>>= 7;
        }
        writeRawByte(i);
    }

    public final void writeString(int i, String str) {
        writeTag(i, 2);
        try {
            int computeRawVarint32Size = computeRawVarint32Size(str.length());
            if (computeRawVarint32Size != computeRawVarint32Size(str.length() * 3)) {
                writeRawVarint32(encodedLength(str));
                encode(str, this.buffer);
                return;
            }
            int position = this.buffer.position();
            if (this.buffer.remaining() < computeRawVarint32Size) {
                throw new OutOfSpaceException(position + computeRawVarint32Size, this.buffer.limit());
            }
            this.buffer.position(position + computeRawVarint32Size);
            encode(str, this.buffer);
            int position2 = this.buffer.position();
            this.buffer.position(position);
            writeRawVarint32((position2 - position) - computeRawVarint32Size);
            this.buffer.position(position2);
        } catch (BufferOverflowException e) {
            OutOfSpaceException outOfSpaceException = new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
            outOfSpaceException.initCause(e);
            throw outOfSpaceException;
        }
    }

    public final void writeTag(int i, int i2) {
        writeRawVarint32((i << 3) | i2);
    }

    private CodedOutputByteBufferNano(ByteBuffer byteBuffer) {
        this.buffer = byteBuffer;
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x001f, code lost:
    
        return r9 + r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int encode(java.lang.CharSequence r7, byte[] r8, int r9, int r10) {
        /*
            Method dump skipped, instructions count: 239
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.nano.CodedOutputByteBufferNano.encode(java.lang.CharSequence, byte[], int, int):int");
    }
}

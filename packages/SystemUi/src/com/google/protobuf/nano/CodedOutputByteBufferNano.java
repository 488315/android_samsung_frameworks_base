package com.google.protobuf.nano;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CodedOutputByteBufferNano {
    public final ByteBuffer buffer;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class OutOfSpaceException extends IOException {
        private static final long serialVersionUID = -6947486886997889499L;

        public OutOfSpaceException(int i, int i2) {
            super(SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("CodedOutputStream was writing to a flat byte array and ran out of space (pos ", i, " limit ", i2, ")."));
        }
    }

    private CodedOutputByteBufferNano(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    public static int computeInt32Size(int i, int i2) {
        return computeTagSize(i) + (i2 >= 0 ? computeRawVarint32Size(i2) : 10);
    }

    public static int computeInt64Size(int i, long j) {
        return computeTagSize(i) + (((-128) & j) == 0 ? 1 : ((-16384) & j) == 0 ? 2 : ((-2097152) & j) == 0 ? 3 : ((-268435456) & j) == 0 ? 4 : ((-34359738368L) & j) == 0 ? 5 : ((-4398046511104L) & j) == 0 ? 6 : ((-562949953421312L) & j) == 0 ? 7 : ((-72057594037927936L) & j) == 0 ? 8 : (j & Long.MIN_VALUE) == 0 ? 9 : 10);
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
        return computeRawVarint32Size((i << 3) | 0);
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
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt < 128) {
                byteBuffer.put((byte) charAt);
            } else if (charAt < 2048) {
                byteBuffer.put((byte) ((charAt >>> 6) | 960));
                byteBuffer.put((byte) ((charAt & '?') | 128));
            } else {
                if (charAt >= 55296 && 57343 >= charAt) {
                    int i2 = i + 1;
                    if (i2 != charSequence.length()) {
                        char charAt2 = charSequence.charAt(i2);
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
                                throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Unpaired surrogate at index ", i2));
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
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (i3 + SemWallpaperColorsWrapper.LOCKSCREEN_STATUS_BAR));
    }

    public static CodedOutputByteBufferNano newInstance(byte[] bArr, int i, int i2) {
        return new CodedOutputByteBufferNano(bArr, i, i2);
    }

    public final void writeFixed64(int i, long j) {
        writeTag(i, 1);
        ByteBuffer byteBuffer = this.buffer;
        if (byteBuffer.remaining() < 8) {
            throw new OutOfSpaceException(byteBuffer.position(), byteBuffer.limit());
        }
        byteBuffer.putLong(j);
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
        ByteBuffer byteBuffer = this.buffer;
        if (!byteBuffer.hasRemaining()) {
            throw new OutOfSpaceException(byteBuffer.position(), byteBuffer.limit());
        }
        byteBuffer.put(b);
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
        ByteBuffer byteBuffer = this.buffer;
        try {
            int computeRawVarint32Size = computeRawVarint32Size(str.length());
            if (computeRawVarint32Size != computeRawVarint32Size(str.length() * 3)) {
                writeRawVarint32(encodedLength(str));
                encode(str, byteBuffer);
                return;
            }
            int position = byteBuffer.position();
            if (byteBuffer.remaining() < computeRawVarint32Size) {
                throw new OutOfSpaceException(position + computeRawVarint32Size, byteBuffer.limit());
            }
            byteBuffer.position(position + computeRawVarint32Size);
            encode(str, byteBuffer);
            int position2 = byteBuffer.position();
            byteBuffer.position(position);
            writeRawVarint32((position2 - position) - computeRawVarint32Size);
            byteBuffer.position(position2);
        } catch (BufferOverflowException e) {
            OutOfSpaceException outOfSpaceException = new OutOfSpaceException(byteBuffer.position(), byteBuffer.limit());
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

    /* JADX WARN: Code restructure failed: missing block: B:12:0x001d, code lost:
    
        return r8 + r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int encode(CharSequence charSequence, byte[] bArr, int i, int i2) {
        int i3;
        int i4;
        char charAt;
        int length = charSequence.length();
        int i5 = i2 + i;
        int i6 = 0;
        while (i6 < length && (i4 = i6 + i) < i5 && (charAt = charSequence.charAt(i6)) < 128) {
            bArr[i4] = (byte) charAt;
            i6++;
        }
        int i7 = i + i6;
        while (i6 < length) {
            char charAt2 = charSequence.charAt(i6);
            if (charAt2 >= 128 || i7 >= i5) {
                if (charAt2 < 2048 && i7 <= i5 - 2) {
                    int i8 = i7 + 1;
                    bArr[i7] = (byte) ((charAt2 >>> 6) | 960);
                    i7 = i8 + 1;
                    bArr[i8] = (byte) ((charAt2 & '?') | 128);
                } else {
                    if ((charAt2 >= 55296 && 57343 >= charAt2) || i7 > i5 - 3) {
                        if (i7 <= i5 - 4) {
                            int i9 = i6 + 1;
                            if (i9 != charSequence.length()) {
                                char charAt3 = charSequence.charAt(i9);
                                if (Character.isSurrogatePair(charAt2, charAt3)) {
                                    int codePoint = Character.toCodePoint(charAt2, charAt3);
                                    int i10 = i7 + 1;
                                    bArr[i7] = (byte) ((codePoint >>> 18) | IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp);
                                    int i11 = i10 + 1;
                                    bArr[i10] = (byte) (((codePoint >>> 12) & 63) | 128);
                                    int i12 = i11 + 1;
                                    bArr[i11] = (byte) (((codePoint >>> 6) & 63) | 128);
                                    i7 = i12 + 1;
                                    bArr[i12] = (byte) ((codePoint & 63) | 128);
                                    i6 = i9;
                                } else {
                                    i6 = i9;
                                }
                            }
                            StringBuilder sb = new StringBuilder("Unpaired surrogate at index ");
                            sb.append(i6 - 1);
                            throw new IllegalArgumentException(sb.toString());
                        }
                        throw new ArrayIndexOutOfBoundsException("Failed writing " + charAt2 + " at index " + i7);
                    }
                    int i13 = i7 + 1;
                    bArr[i7] = (byte) ((charAt2 >>> '\f') | VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE);
                    int i14 = i13 + 1;
                    bArr[i13] = (byte) (((charAt2 >>> 6) & 63) | 128);
                    i3 = i14 + 1;
                    bArr[i14] = (byte) ((charAt2 & '?') | 128);
                }
                i6++;
            } else {
                i3 = i7 + 1;
                bArr[i7] = (byte) charAt2;
            }
            i7 = i3;
            i6++;
        }
        return i7;
    }
}

package com.google.protobuf;

import com.android.systemui.AbstractC0970x34f4116a;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.volte2.data.VolteConstants;
import java.nio.charset.Charset;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Utf8 {
    public static final Processor processor;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DecodeUtil {
        private DecodeUtil() {
        }

        public static boolean isNotTrailingByte(byte b) {
            return b > -65;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Processor {
        public abstract String decodeUtf8(byte[] bArr, int i, int i2);

        public abstract int encodeUtf8(CharSequence charSequence, byte[] bArr, int i, int i2);

        public abstract int partialIsValidUtf8(int i, int i2, byte[] bArr);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SafeProcessor extends Processor {
        @Override // com.google.protobuf.Utf8.Processor
        public final String decodeUtf8(byte[] bArr, int i, int i2) {
            int i3;
            int i4;
            if ((i | i2 | ((bArr.length - i) - i2)) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)));
            }
            int i5 = i + i2;
            char[] cArr = new char[i2];
            int i6 = 0;
            while (i < i5) {
                byte b = bArr[i];
                if (!(b >= 0)) {
                    break;
                }
                i++;
                cArr[i6] = (char) b;
                i6++;
            }
            while (i < i5) {
                int i7 = i + 1;
                byte b2 = bArr[i];
                if (b2 >= 0) {
                    int i8 = i6 + 1;
                    cArr[i6] = (char) b2;
                    i = i7;
                    while (true) {
                        i6 = i8;
                        if (i >= i5) {
                            break;
                        }
                        byte b3 = bArr[i];
                        if (!(b3 >= 0)) {
                            break;
                        }
                        i++;
                        i8 = i6 + 1;
                        cArr[i6] = (char) b3;
                    }
                } else {
                    if (!(b2 < -32)) {
                        if (!(b2 < -16)) {
                            if (i7 >= i5 - 2) {
                                throw InvalidProtocolBufferException.invalidUtf8();
                            }
                            int i9 = i7 + 1;
                            byte b4 = bArr[i7];
                            int i10 = i9 + 1;
                            byte b5 = bArr[i9];
                            i3 = i10 + 1;
                            byte b6 = bArr[i10];
                            int i11 = i6 + 1;
                            if (!DecodeUtil.isNotTrailingByte(b4)) {
                                if ((((b4 + 112) + (b2 << 28)) >> 30) == 0 && !DecodeUtil.isNotTrailingByte(b5) && !DecodeUtil.isNotTrailingByte(b6)) {
                                    int i12 = ((b2 & 7) << 18) | ((b4 & 63) << 12) | ((b5 & 63) << 6) | (b6 & 63);
                                    cArr[i6] = (char) ((i12 >>> 10) + 55232);
                                    cArr[i11] = (char) ((i12 & 1023) + 56320);
                                    i4 = i11 + 1;
                                }
                            }
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                        if (i7 >= i5 - 1) {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                        int i13 = i7 + 1;
                        byte b7 = bArr[i7];
                        i3 = i13 + 1;
                        byte b8 = bArr[i13];
                        i4 = i6 + 1;
                        if (DecodeUtil.isNotTrailingByte(b7) || ((b2 == -32 && b7 < -96) || ((b2 == -19 && b7 >= -96) || DecodeUtil.isNotTrailingByte(b8)))) {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                        cArr[i6] = (char) (((b2 & 15) << 12) | ((b7 & 63) << 6) | (b8 & 63));
                        i = i3;
                        i6 = i4;
                    } else {
                        if (i7 >= i5) {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                        int i14 = i7 + 1;
                        byte b9 = bArr[i7];
                        int i15 = i6 + 1;
                        if (b2 < -62 || DecodeUtil.isNotTrailingByte(b9)) {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                        cArr[i6] = (char) (((b2 & 31) << 6) | (b9 & 63));
                        i = i14;
                        i6 = i15;
                    }
                }
            }
            return new String(cArr, 0, i6);
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x001d, code lost:
        
            return r9 + r6;
         */
        @Override // com.google.protobuf.Utf8.Processor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final int encodeUtf8(CharSequence charSequence, byte[] bArr, int i, int i2) {
            int i3;
            int i4;
            int i5;
            char charAt;
            int length = charSequence.length();
            int i6 = i2 + i;
            int i7 = 0;
            while (i7 < length && (i5 = i7 + i) < i6 && (charAt = charSequence.charAt(i7)) < 128) {
                bArr[i5] = (byte) charAt;
                i7++;
            }
            int i8 = i + i7;
            while (i7 < length) {
                char charAt2 = charSequence.charAt(i7);
                if (charAt2 >= 128 || i8 >= i6) {
                    if (charAt2 < 2048 && i8 <= i6 - 2) {
                        int i9 = i8 + 1;
                        bArr[i8] = (byte) ((charAt2 >>> 6) | 960);
                        i8 = i9 + 1;
                        bArr[i9] = (byte) ((charAt2 & '?') | 128);
                    } else {
                        if ((charAt2 >= 55296 && 57343 >= charAt2) || i8 > i6 - 3) {
                            if (i8 > i6 - 4) {
                                if (55296 <= charAt2 && charAt2 <= 57343 && ((i4 = i7 + 1) == charSequence.length() || !Character.isSurrogatePair(charAt2, charSequence.charAt(i4)))) {
                                    throw new UnpairedSurrogateException(i7, length);
                                }
                                throw new ArrayIndexOutOfBoundsException("Failed writing " + charAt2 + " at index " + i8);
                            }
                            int i10 = i7 + 1;
                            if (i10 != charSequence.length()) {
                                char charAt3 = charSequence.charAt(i10);
                                if (Character.isSurrogatePair(charAt2, charAt3)) {
                                    int codePoint = Character.toCodePoint(charAt2, charAt3);
                                    int i11 = i8 + 1;
                                    bArr[i8] = (byte) ((codePoint >>> 18) | IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp);
                                    int i12 = i11 + 1;
                                    bArr[i11] = (byte) (((codePoint >>> 12) & 63) | 128);
                                    int i13 = i12 + 1;
                                    bArr[i12] = (byte) (((codePoint >>> 6) & 63) | 128);
                                    i8 = i13 + 1;
                                    bArr[i13] = (byte) ((codePoint & 63) | 128);
                                    i7 = i10;
                                } else {
                                    i7 = i10;
                                }
                            }
                            throw new UnpairedSurrogateException(i7 - 1, length);
                        }
                        int i14 = i8 + 1;
                        bArr[i8] = (byte) ((charAt2 >>> '\f') | VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE);
                        int i15 = i14 + 1;
                        bArr[i14] = (byte) (((charAt2 >>> 6) & 63) | 128);
                        i3 = i15 + 1;
                        bArr[i15] = (byte) ((charAt2 & '?') | 128);
                    }
                    i7++;
                } else {
                    i3 = i8 + 1;
                    bArr[i8] = (byte) charAt2;
                }
                i8 = i3;
                i7++;
            }
            return i8;
        }

        @Override // com.google.protobuf.Utf8.Processor
        public final int partialIsValidUtf8(int i, int i2, byte[] bArr) {
            while (i < i2 && bArr[i] >= 0) {
                i++;
            }
            if (i < i2) {
                while (i < i2) {
                    int i3 = i + 1;
                    byte b = bArr[i];
                    if (b < 0) {
                        if (b < -32) {
                            if (i3 >= i2) {
                                return b;
                            }
                            if (b >= -62) {
                                i = i3 + 1;
                                if (bArr[i3] > -65) {
                                }
                            }
                            return -1;
                        }
                        if (b < -16) {
                            if (i3 >= i2 - 1) {
                                return Utf8.access$1100(bArr, i3, i2);
                            }
                            int i4 = i3 + 1;
                            byte b2 = bArr[i3];
                            if (b2 <= -65 && ((b != -32 || b2 >= -96) && (b != -19 || b2 < -96))) {
                                i = i4 + 1;
                                if (bArr[i4] > -65) {
                                }
                            }
                            return -1;
                        }
                        if (i3 >= i2 - 2) {
                            return Utf8.access$1100(bArr, i3, i2);
                        }
                        int i5 = i3 + 1;
                        byte b3 = bArr[i3];
                        if (b3 <= -65) {
                            if ((((b3 + 112) + (b << 28)) >> 30) == 0) {
                                int i6 = i5 + 1;
                                if (bArr[i5] <= -65) {
                                    i = i6 + 1;
                                    if (bArr[i6] > -65) {
                                    }
                                }
                            }
                        }
                        return -1;
                    }
                    i = i3;
                }
            }
            return 0;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    class UnpairedSurrogateException extends IllegalArgumentException {
        public UnpairedSurrogateException(int i, int i2) {
            super(AbstractC0970x34f4116a.m94m("Unpaired surrogate at index ", i, " of ", i2));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UnsafeProcessor extends Processor {
        public static int unsafeIncompleteStateFor(int i, int i2, long j, byte[] bArr) {
            if (i2 == 0) {
                Processor processor = Utf8.processor;
                if (i > -12) {
                    return -1;
                }
                return i;
            }
            if (i2 == 1) {
                return Utf8.incompleteStateFor(i, UnsafeUtil.getByte(bArr, j));
            }
            if (i2 == 2) {
                return Utf8.incompleteStateFor(i, UnsafeUtil.getByte(bArr, j), UnsafeUtil.getByte(bArr, j + 1));
            }
            throw new AssertionError();
        }

        @Override // com.google.protobuf.Utf8.Processor
        public final String decodeUtf8(byte[] bArr, int i, int i2) {
            Charset charset = Internal.UTF_8;
            String str = new String(bArr, i, i2, charset);
            if (!str.contains("�")) {
                return str;
            }
            if (Arrays.equals(str.getBytes(charset), Arrays.copyOfRange(bArr, i, i2 + i))) {
                return str;
            }
            throw InvalidProtocolBufferException.invalidUtf8();
        }

        @Override // com.google.protobuf.Utf8.Processor
        public final int encodeUtf8(CharSequence charSequence, byte[] bArr, int i, int i2) {
            long j;
            char c;
            long j2;
            long j3;
            char c2;
            int i3;
            char charAt;
            long j4 = i;
            long j5 = i2 + j4;
            int length = charSequence.length();
            if (length > i2 || bArr.length - i2 < i) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(length - 1) + " at index " + (i + i2));
            }
            int i4 = 0;
            while (true) {
                j = 1;
                c = 128;
                if (i4 >= length || (charAt = charSequence.charAt(i4)) >= 128) {
                    break;
                }
                UnsafeUtil.putByte(bArr, j4, (byte) charAt);
                i4++;
                j4 = 1 + j4;
            }
            if (i4 == length) {
                return (int) j4;
            }
            while (i4 < length) {
                char charAt2 = charSequence.charAt(i4);
                if (charAt2 < c && j4 < j5) {
                    long j6 = j4 + j;
                    UnsafeUtil.putByte(bArr, j4, (byte) charAt2);
                    j3 = j;
                    j2 = j6;
                    c2 = c;
                } else if (charAt2 < 2048 && j4 <= j5 - 2) {
                    long j7 = j4 + j;
                    UnsafeUtil.putByte(bArr, j4, (byte) ((charAt2 >>> 6) | 960));
                    long j8 = j7 + j;
                    UnsafeUtil.putByte(bArr, j7, (byte) ((charAt2 & '?') | 128));
                    long j9 = j;
                    c2 = 128;
                    j2 = j8;
                    j3 = j9;
                } else {
                    if ((charAt2 >= 55296 && 57343 >= charAt2) || j4 > j5 - 3) {
                        if (j4 > j5 - 4) {
                            if (55296 <= charAt2 && charAt2 <= 57343 && ((i3 = i4 + 1) == length || !Character.isSurrogatePair(charAt2, charSequence.charAt(i3)))) {
                                throw new UnpairedSurrogateException(i4, length);
                            }
                            throw new ArrayIndexOutOfBoundsException("Failed writing " + charAt2 + " at index " + j4);
                        }
                        int i5 = i4 + 1;
                        if (i5 != length) {
                            char charAt3 = charSequence.charAt(i5);
                            if (Character.isSurrogatePair(charAt2, charAt3)) {
                                int codePoint = Character.toCodePoint(charAt2, charAt3);
                                long j10 = j4 + 1;
                                UnsafeUtil.putByte(bArr, j4, (byte) ((codePoint >>> 18) | IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp));
                                long j11 = j10 + 1;
                                c2 = 128;
                                UnsafeUtil.putByte(bArr, j10, (byte) (((codePoint >>> 12) & 63) | 128));
                                long j12 = j11 + 1;
                                UnsafeUtil.putByte(bArr, j11, (byte) (((codePoint >>> 6) & 63) | 128));
                                j3 = 1;
                                j2 = j12 + 1;
                                UnsafeUtil.putByte(bArr, j12, (byte) ((codePoint & 63) | 128));
                                i4 = i5;
                            } else {
                                i4 = i5;
                            }
                        }
                        throw new UnpairedSurrogateException(i4 - 1, length);
                    }
                    long j13 = j4 + j;
                    UnsafeUtil.putByte(bArr, j4, (byte) ((charAt2 >>> '\f') | VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE));
                    long j14 = j13 + j;
                    UnsafeUtil.putByte(bArr, j13, (byte) (((charAt2 >>> 6) & 63) | 128));
                    UnsafeUtil.putByte(bArr, j14, (byte) ((charAt2 & '?') | 128));
                    j2 = j14 + 1;
                    j3 = 1;
                    c2 = 128;
                }
                i4++;
                c = c2;
                long j15 = j3;
                j4 = j2;
                j = j15;
            }
            return (int) j4;
        }

        @Override // com.google.protobuf.Utf8.Processor
        public final int partialIsValidUtf8(int i, int i2, byte[] bArr) {
            int i3;
            long j;
            if ((i | i2 | (bArr.length - i2)) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("Array length=%d, index=%d, limit=%d", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)));
            }
            long j2 = i;
            int i4 = (int) (i2 - j2);
            if (i4 >= 16) {
                int i5 = 8 - (((int) j2) & 7);
                long j3 = j2;
                i3 = 0;
                while (true) {
                    if (i3 >= i5) {
                        while (true) {
                            int i6 = i3 + 8;
                            if (i6 > i4 || (UnsafeUtil.getLong(UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + j3, bArr) & (-9187201950435737472L)) != 0) {
                                break;
                            }
                            j3 += 8;
                            i3 = i6;
                        }
                        while (true) {
                            if (i3 >= i4) {
                                i3 = i4;
                                break;
                            }
                            long j4 = j3 + 1;
                            if (UnsafeUtil.getByte(bArr, j3) < 0) {
                                break;
                            }
                            i3++;
                            j3 = j4;
                        }
                    } else {
                        long j5 = j3 + 1;
                        if (UnsafeUtil.getByte(bArr, j3) < 0) {
                            break;
                        }
                        i3++;
                        j3 = j5;
                    }
                }
            } else {
                i3 = 0;
            }
            int i7 = i4 - i3;
            long j6 = j2 + i3;
            while (true) {
                byte b = 0;
                while (true) {
                    if (i7 <= 0) {
                        break;
                    }
                    long j7 = j6 + 1;
                    b = UnsafeUtil.getByte(bArr, j6);
                    if (b < 0) {
                        j6 = j7;
                        break;
                    }
                    i7--;
                    j6 = j7;
                }
                if (i7 != 0) {
                    int i8 = i7 - 1;
                    if (b >= -32) {
                        if (b >= -16) {
                            if (i8 >= 3) {
                                i7 = i8 - 3;
                                long j8 = j6 + 1;
                                byte b2 = UnsafeUtil.getByte(bArr, j6);
                                if (b2 > -65) {
                                    break;
                                }
                                if ((((b2 + 112) + (b << 28)) >> 30) != 0) {
                                    break;
                                }
                                long j9 = j8 + 1;
                                if (UnsafeUtil.getByte(bArr, j8) > -65) {
                                    break;
                                }
                                j = j9 + 1;
                                if (UnsafeUtil.getByte(bArr, j9) > -65) {
                                    break;
                                }
                                j6 = j;
                            } else {
                                return unsafeIncompleteStateFor(b, i8, j6, bArr);
                            }
                        } else if (i8 >= 2) {
                            i7 = i8 - 2;
                            long j10 = j6 + 1;
                            byte b3 = UnsafeUtil.getByte(bArr, j6);
                            if (b3 > -65 || ((b == -32 && b3 < -96) || (b == -19 && b3 >= -96))) {
                                break;
                            }
                            j6 = j10 + 1;
                            if (UnsafeUtil.getByte(bArr, j10) > -65) {
                                break;
                            }
                        } else {
                            return unsafeIncompleteStateFor(b, i8, j6, bArr);
                        }
                    } else if (i8 != 0) {
                        i7 = i8 - 1;
                        if (b < -62) {
                            break;
                        }
                        j = j6 + 1;
                        if (UnsafeUtil.getByte(bArr, j6) > -65) {
                            break;
                        }
                        j6 = j;
                    } else {
                        return b;
                    }
                } else {
                    return 0;
                }
            }
            return -1;
        }
    }

    static {
        processor = (!(UnsafeUtil.HAS_UNSAFE_ARRAY_OPERATIONS && UnsafeUtil.HAS_UNSAFE_BYTEBUFFER_OPERATIONS) || Android.isOnAndroidDevice()) ? new SafeProcessor() : new UnsafeProcessor();
    }

    private Utf8() {
    }

    public static int access$1100(byte[] bArr, int i, int i2) {
        byte b = bArr[i - 1];
        int i3 = i2 - i;
        if (i3 == 0) {
            if (b > -12) {
                b = -1;
            }
            return b;
        }
        if (i3 == 1) {
            return incompleteStateFor(b, bArr[i]);
        }
        if (i3 == 2) {
            return incompleteStateFor(b, bArr[i], bArr[i + 1]);
        }
        throw new AssertionError();
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
                                throw new UnpairedSurrogateException(i2, length2);
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

    public static int incompleteStateFor(int i, int i2) {
        if (i > -12 || i2 > -65) {
            return -1;
        }
        return i ^ (i2 << 8);
    }

    public static int incompleteStateFor(int i, int i2, int i3) {
        if (i > -12 || i2 > -65 || i3 > -65) {
            return -1;
        }
        return (i ^ (i2 << 8)) ^ (i3 << 16);
    }
}

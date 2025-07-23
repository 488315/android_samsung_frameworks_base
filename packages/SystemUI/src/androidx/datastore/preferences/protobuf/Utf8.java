package androidx.datastore.preferences.protobuf;

import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.volte2.data.VolteConstants;
import java.nio.charset.Charset;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Utf8 {
    public static final Processor processor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DecodeUtil {
        private DecodeUtil() {
        }

        public static boolean isNotTrailingByte(byte b) {
            return b > -65;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Processor {
        public abstract String decodeUtf8(int i, int i2, byte[] bArr);

        public abstract int encodeUtf8(String str, byte[] bArr, int i, int i2);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SafeProcessor extends Processor {
        @Override // androidx.datastore.preferences.protobuf.Utf8.Processor
        public final String decodeUtf8(int i, int i2, byte[] bArr) {
            if ((i | i2 | ((bArr.length - i) - i2)) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)));
            }
            int i3 = i + i2;
            char[] cArr = new char[i2];
            int i4 = 0;
            while (i < i3) {
                byte b = bArr[i];
                if (b < 0) {
                    break;
                }
                i++;
                cArr[i4] = (char) b;
                i4++;
            }
            while (i < i3) {
                int i5 = i + 1;
                byte b2 = bArr[i];
                if (b2 >= 0) {
                    int i6 = i4 + 1;
                    cArr[i4] = (char) b2;
                    while (i5 < i3) {
                        byte b3 = bArr[i5];
                        if (b3 < 0) {
                            break;
                        }
                        i5++;
                        cArr[i6] = (char) b3;
                        i6++;
                    }
                    i4 = i6;
                    i = i5;
                } else if (b2 < -32) {
                    if (i5 >= i3) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    i += 2;
                    byte b4 = bArr[i5];
                    int i7 = i4 + 1;
                    if (b2 < -62 || DecodeUtil.isNotTrailingByte(b4)) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    cArr[i4] = (char) ((b4 & 63) | ((b2 & 31) << 6));
                    i4 = i7;
                } else {
                    if (b2 >= -16) {
                        if (i5 >= i3 - 2) {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                        byte b5 = bArr[i5];
                        int i8 = i + 3;
                        byte b6 = bArr[i + 2];
                        i += 4;
                        byte b7 = bArr[i8];
                        int i9 = i4 + 1;
                        if (!DecodeUtil.isNotTrailingByte(b5)) {
                            if ((((b5 + 112) + (b2 << 28)) >> 30) == 0 && !DecodeUtil.isNotTrailingByte(b6) && !DecodeUtil.isNotTrailingByte(b7)) {
                                int i10 = ((b5 & 63) << 12) | ((b2 & 7) << 18) | ((b6 & 63) << 6) | (b7 & 63);
                                cArr[i4] = (char) ((i10 >>> 10) + 55232);
                                cArr[i9] = (char) ((i10 & 1023) + 56320);
                                i4 += 2;
                            }
                        }
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    if (i5 >= i3 - 1) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    int i11 = i + 2;
                    byte b8 = bArr[i5];
                    i += 3;
                    byte b9 = bArr[i11];
                    int i12 = i4 + 1;
                    if (DecodeUtil.isNotTrailingByte(b8) || ((b2 == -32 && b8 < -96) || ((b2 == -19 && b8 >= -96) || DecodeUtil.isNotTrailingByte(b9)))) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    cArr[i4] = (char) (((b8 & 63) << 6) | ((b2 & 15) << 12) | (b9 & 63));
                    i4 = i12;
                }
            }
            return new String(cArr, 0, i4);
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x001d, code lost:
        
            return r9 + r6;
         */
        @Override // androidx.datastore.preferences.protobuf.Utf8.Processor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int encodeUtf8(java.lang.String r7, byte[] r8, int r9, int r10) {
            /*
                Method dump skipped, instructions count: 251
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.Utf8.SafeProcessor.encodeUtf8(java.lang.String, byte[], int, int):int");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class UnpairedSurrogateException extends IllegalArgumentException {
        public UnpairedSurrogateException(int i, int i2) {
            super(ListImplementation$$ExternalSyntheticOutline0.m(i, i2, "Unpaired surrogate at index ", " of "));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class UnsafeProcessor extends Processor {
        @Override // androidx.datastore.preferences.protobuf.Utf8.Processor
        public final String decodeUtf8(int i, int i2, byte[] bArr) {
            Charset charset = Internal.UTF_8;
            String str = new String(bArr, i, i2, charset);
            if (str.indexOf(65533) >= 0 && !Arrays.equals(str.getBytes(charset), Arrays.copyOfRange(bArr, i, i2 + i))) {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            return str;
        }

        @Override // androidx.datastore.preferences.protobuf.Utf8.Processor
        public final int encodeUtf8(String str, byte[] bArr, int i, int i2) {
            long j;
            long j2;
            long j3;
            int i3;
            char charAt;
            long j4 = i;
            long j5 = i2 + j4;
            int length = str.length();
            if (length > i2 || bArr.length - i2 < i) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + str.charAt(length - 1) + " at index " + (i + i2));
            }
            int i4 = 0;
            while (true) {
                j = 1;
                if (i4 >= length || (charAt = str.charAt(i4)) >= 128) {
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
                char charAt2 = str.charAt(i4);
                if (charAt2 < 128 && j4 < j5) {
                    UnsafeUtil.putByte(bArr, j4, (byte) charAt2);
                    j3 = j5;
                    j2 = j;
                    j4 += j;
                } else if (charAt2 >= 2048 || j4 > j5 - 2) {
                    j2 = j;
                    if ((charAt2 >= 55296 && 57343 >= charAt2) || j4 > j5 - 3) {
                        j3 = j5;
                        if (j4 > j3 - 4) {
                            if (55296 <= charAt2 && charAt2 <= 57343 && ((i3 = i4 + 1) == length || !Character.isSurrogatePair(charAt2, str.charAt(i3)))) {
                                throw new UnpairedSurrogateException(i4, length);
                            }
                            throw new ArrayIndexOutOfBoundsException("Failed writing " + charAt2 + " at index " + j4);
                        }
                        int i5 = i4 + 1;
                        if (i5 != length) {
                            char charAt3 = str.charAt(i5);
                            if (Character.isSurrogatePair(charAt2, charAt3)) {
                                int codePoint = Character.toCodePoint(charAt2, charAt3);
                                UnsafeUtil.putByte(bArr, j4, (byte) ((codePoint >>> 18) | IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp));
                                UnsafeUtil.putByte(bArr, j4 + j2, (byte) (((codePoint >>> 12) & 63) | 128));
                                long j6 = j4 + 3;
                                UnsafeUtil.putByte(bArr, j4 + 2, (byte) (((codePoint >>> 6) & 63) | 128));
                                j4 += 4;
                                UnsafeUtil.putByte(bArr, j6, (byte) ((codePoint & 63) | 128));
                                i4 = i5;
                            } else {
                                i4 = i5;
                            }
                        }
                        throw new UnpairedSurrogateException(i4 - 1, length);
                    }
                    UnsafeUtil.putByte(bArr, j4, (byte) ((charAt2 >>> '\f') | VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE));
                    long j7 = j4 + 2;
                    j3 = j5;
                    UnsafeUtil.putByte(bArr, j4 + j2, (byte) (((charAt2 >>> 6) & 63) | 128));
                    j4 += 3;
                    UnsafeUtil.putByte(bArr, j7, (byte) ((charAt2 & '?') | 128));
                } else {
                    j2 = j;
                    long j8 = j4 + j2;
                    UnsafeUtil.putByte(bArr, j4, (byte) ((charAt2 >>> 6) | 960));
                    j4 += 2;
                    UnsafeUtil.putByte(bArr, j8, (byte) ((charAt2 & '?') | 128));
                    j3 = j5;
                }
                i4++;
                j = j2;
                j5 = j3;
            }
            return (int) j4;
        }
    }

    static {
        processor = (UnsafeUtil.HAS_UNSAFE_ARRAY_OPERATIONS && UnsafeUtil.HAS_UNSAFE_BYTEBUFFER_OPERATIONS && !Android.isOnAndroidDevice()) ? new UnsafeProcessor() : new SafeProcessor();
    }

    private Utf8() {
    }

    public static int encodedLength(String str) {
        int length = str.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && str.charAt(i2) < 128) {
            i2++;
        }
        int i3 = length;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char charAt = str.charAt(i2);
            if (charAt < 2048) {
                i3 += (127 - charAt) >>> 31;
                i2++;
            } else {
                int length2 = str.length();
                while (i2 < length2) {
                    char charAt2 = str.charAt(i2);
                    if (charAt2 < 2048) {
                        i += (127 - charAt2) >>> 31;
                    } else {
                        i += 2;
                        if (55296 <= charAt2 && charAt2 <= 57343) {
                            if (Character.codePointAt(str, i2) < 65536) {
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
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (i3 + 4294967296L));
    }
}

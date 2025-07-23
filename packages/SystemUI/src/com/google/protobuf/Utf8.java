package com.google.protobuf;

import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Utf8 {
    public static final SafeProcessor processor;

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
        public final boolean isValidUtf8(int i, int i2, byte[] bArr) {
            return partialIsValidUtf8(i, i2, bArr) == 0;
        }

        public abstract int partialIsValidUtf8(int i, int i2, byte[] bArr);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SafeProcessor extends Processor {
        @Override // com.google.protobuf.Utf8.Processor
        public final int partialIsValidUtf8(int i, int i2, byte[] bArr) {
            while (i < i2 && bArr[i] >= 0) {
                i++;
            }
            if (i >= i2) {
                return 0;
            }
            while (i < i2) {
                int i3 = i + 1;
                byte b = bArr[i];
                if (b >= 0) {
                    i = i3;
                } else if (b < -32) {
                    if (i3 >= i2) {
                        return b;
                    }
                    if (b < -62) {
                        return -1;
                    }
                    i += 2;
                    if (bArr[i3] > -65) {
                        return -1;
                    }
                } else if (b < -16) {
                    if (i3 >= i2 - 1) {
                        return Utf8.access$1100(i3, i2, bArr);
                    }
                    int i4 = i + 2;
                    byte b2 = bArr[i3];
                    if (b2 > -65) {
                        return -1;
                    }
                    if (b == -32 && b2 < -96) {
                        return -1;
                    }
                    if (b == -19 && b2 >= -96) {
                        return -1;
                    }
                    i += 3;
                    if (bArr[i4] > -65) {
                        return -1;
                    }
                } else {
                    if (i3 >= i2 - 2) {
                        return Utf8.access$1100(i3, i2, bArr);
                    }
                    int i5 = i + 2;
                    byte b3 = bArr[i3];
                    if (b3 > -65) {
                        return -1;
                    }
                    if ((((b3 + 112) + (b << 28)) >> 30) != 0) {
                        return -1;
                    }
                    int i6 = i + 3;
                    if (bArr[i5] > -65) {
                        return -1;
                    }
                    i += 4;
                    if (bArr[i6] > -65) {
                        return -1;
                    }
                }
            }
            return 0;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class UnpairedSurrogateException extends IllegalArgumentException {
        public UnpairedSurrogateException(int i, int i2) {
            super(ListImplementation$$ExternalSyntheticOutline0.m(i, i2, "Unpaired surrogate at index ", " of "));
        }
    }

    static {
        if (UnsafeUtil.HAS_UNSAFE_ARRAY_OPERATIONS && UnsafeUtil.HAS_UNSAFE_BYTEBUFFER_OPERATIONS) {
            Class cls = Android.MEMORY_CLASS;
        }
        processor = new SafeProcessor();
    }

    private Utf8() {
    }

    public static int access$1100(int i, int i2, byte[] bArr) {
        byte b = bArr[i - 1];
        int i3 = i2 - i;
        if (i3 == 0) {
            if (b > -12) {
                return -1;
            }
            return b;
        }
        if (i3 == 1) {
            byte b2 = bArr[i];
            if (b > -12 || b2 > -65) {
                return -1;
            }
            return (b2 << 8) ^ b;
        }
        if (i3 != 2) {
            throw new AssertionError();
        }
        byte b3 = bArr[i];
        byte b4 = bArr[i + 1];
        if (b > -12 || b3 > -65 || b4 > -65) {
            return -1;
        }
        return (b4 << 16) ^ ((b3 << 8) ^ b);
    }

    public static String decodeUtf8(int i, int i2, byte[] bArr) {
        processor.getClass();
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

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0024, code lost:
    
        return r9 + r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int encode(java.lang.CharSequence r7, byte[] r8, int r9, int r10) {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.Utf8.encode(java.lang.CharSequence, byte[], int, int):int");
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
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (i3 + 4294967296L));
    }
}

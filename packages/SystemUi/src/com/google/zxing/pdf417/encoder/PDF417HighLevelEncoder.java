package com.google.zxing.pdf417.encoder;

import java.math.BigInteger;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PDF417HighLevelEncoder {
    public static final byte[] MIXED;
    public static final byte[] TEXT_MIXED_RAW = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 38, 13, 9, 44, 58, 35, 45, 46, 36, 47, 43, 37, 42, 61, 94, 0, 32, 0, 0, 0};
    public static final byte[] TEXT_PUNCTUATION_RAW = {59, 60, 62, 64, 91, 92, 93, 95, 96, 126, 33, 13, 9, 44, 58, 10, 45, 46, 36, 47, 34, 124, 42, 40, 41, 63, 123, 125, 39, 0};
    public static final byte[] PUNCTUATION = new byte[128];

    static {
        byte[] bArr = new byte[128];
        MIXED = bArr;
        Arrays.fill(bArr, (byte) -1);
        byte b = 0;
        byte b2 = 0;
        while (true) {
            byte[] bArr2 = TEXT_MIXED_RAW;
            if (b2 >= bArr2.length) {
                break;
            }
            byte b3 = bArr2[b2];
            if (b3 > 0) {
                MIXED[b3] = b2;
            }
            b2 = (byte) (b2 + 1);
        }
        Arrays.fill(PUNCTUATION, (byte) -1);
        while (true) {
            byte[] bArr3 = TEXT_PUNCTUATION_RAW;
            if (b >= bArr3.length) {
                return;
            }
            byte b4 = bArr3[b];
            if (b4 > 0) {
                PUNCTUATION[b4] = b;
            }
            b = (byte) (b + 1);
        }
    }

    private PDF417HighLevelEncoder() {
    }

    public static void encodeBinary(byte[] bArr, int i, int i2, int i3, StringBuilder sb) {
        int i4;
        if (i2 == 1 && i3 == 0) {
            sb.append((char) 913);
        }
        if (i2 >= 6) {
            sb.append((char) 924);
            char[] cArr = new char[5];
            i4 = i;
            while ((i + i2) - i4 >= 6) {
                long j = 0;
                for (int i5 = 0; i5 < 6; i5++) {
                    j = (j << 8) + (bArr[i4 + i5] & 255);
                }
                for (int i6 = 0; i6 < 5; i6++) {
                    cArr[i6] = (char) (j % 900);
                    j /= 900;
                }
                for (int i7 = 4; i7 >= 0; i7--) {
                    sb.append(cArr[i7]);
                }
                i4 += 6;
            }
        } else {
            i4 = i;
        }
        int i8 = i + i2;
        if (i4 < i8) {
            sb.append((char) 901);
        }
        while (i4 < i8) {
            sb.append((char) (bArr[i4] & 255));
            i4++;
        }
    }

    public static void encodeNumeric(int i, int i2, String str, StringBuilder sb) {
        StringBuilder sb2 = new StringBuilder((i2 / 3) + 1);
        BigInteger valueOf = BigInteger.valueOf(900L);
        BigInteger valueOf2 = BigInteger.valueOf(0L);
        int i3 = 0;
        while (i3 < i2 - 1) {
            sb2.setLength(0);
            int min = Math.min(44, i2 - i3);
            StringBuilder sb3 = new StringBuilder("1");
            int i4 = i + i3;
            sb3.append(str.substring(i4, i4 + min));
            BigInteger bigInteger = new BigInteger(sb3.toString());
            do {
                sb2.append((char) bigInteger.mod(valueOf).intValue());
                bigInteger = bigInteger.divide(valueOf);
            } while (!bigInteger.equals(valueOf2));
            int length = sb2.length();
            while (true) {
                length--;
                if (length >= 0) {
                    sb.append(sb2.charAt(length));
                }
            }
            i3 += min;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0103 A[EDGE_INSN: B:23:0x0103->B:24:0x0103 BREAK  A[LOOP:0: B:2:0x000e->B:18:0x000e], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x000e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int encodeText(CharSequence charSequence, int i, int i2, StringBuilder sb, int i3) {
        StringBuilder sb2 = new StringBuilder(i2);
        int i4 = i3;
        int i5 = 0;
        while (true) {
            int i6 = i + i5;
            char charAt = charSequence.charAt(i6);
            byte[] bArr = PUNCTUATION;
            byte[] bArr2 = MIXED;
            if (i4 != 0) {
                if (i4 != 1) {
                    if (i4 != 2) {
                        byte b = bArr[charAt];
                        if (b != -1) {
                            sb2.append((char) b);
                        } else {
                            sb2.append((char) 29);
                            i4 = 0;
                        }
                    } else {
                        byte b2 = bArr2[charAt];
                        if (b2 != -1) {
                            sb2.append((char) b2);
                        } else if (isAlphaUpper(charAt)) {
                            sb2.append((char) 28);
                            i4 = 0;
                        } else if (isAlphaLower(charAt)) {
                            sb2.append((char) 27);
                            i4 = 1;
                        } else {
                            int i7 = i6 + 1;
                            if (i7 < i2) {
                                if (bArr[charSequence.charAt(i7)] != -1) {
                                    sb2.append((char) 25);
                                    i4 = 3;
                                }
                            }
                            sb2.append((char) 29);
                            sb2.append((char) bArr[charAt]);
                        }
                    }
                } else if (isAlphaLower(charAt)) {
                    if (charAt == ' ') {
                        sb2.append((char) 26);
                    } else {
                        sb2.append((char) (charAt - 'a'));
                    }
                } else if (isAlphaUpper(charAt)) {
                    sb2.append((char) 27);
                    sb2.append((char) (charAt - 'A'));
                } else if (bArr2[charAt] != -1) {
                    sb2.append((char) 28);
                    i4 = 2;
                } else {
                    sb2.append((char) 29);
                    sb2.append((char) bArr[charAt]);
                }
                i5++;
                if (i5 < i2) {
                    break;
                }
            } else {
                if (isAlphaUpper(charAt)) {
                    if (charAt == ' ') {
                        sb2.append((char) 26);
                    } else {
                        sb2.append((char) (charAt - 'A'));
                    }
                } else if (isAlphaLower(charAt)) {
                    sb2.append((char) 27);
                    i4 = 1;
                } else if (bArr2[charAt] != -1) {
                    sb2.append((char) 28);
                    i4 = 2;
                } else {
                    sb2.append((char) 29);
                    sb2.append((char) bArr[charAt]);
                }
                i5++;
                if (i5 < i2) {
                }
            }
        }
        int length = sb2.length();
        char c = 0;
        for (int i8 = 0; i8 < length; i8++) {
            if (i8 % 2 != 0) {
                c = (char) (sb2.charAt(i8) + (c * 30));
                sb.append(c);
            } else {
                c = sb2.charAt(i8);
            }
        }
        if (length % 2 != 0) {
            sb.append((char) ((c * 30) + 29));
        }
        return i4;
    }

    public static boolean isAlphaLower(char c) {
        return c == ' ' || (c >= 'a' && c <= 'z');
    }

    public static boolean isAlphaUpper(char c) {
        return c == ' ' || (c >= 'A' && c <= 'Z');
    }
}

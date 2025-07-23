package com.google.zxing.pdf417.encoder;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.google.zxing.WriterException;
import com.google.zxing.common.ECIInput;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class PDF417HighLevelEncoder {
    public static final byte[] MIXED;
    public static final byte[] TEXT_MIXED_RAW = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 38, 13, 9, 44, 58, 35, 45, 46, 36, 47, 43, 37, 42, 61, 94, 0, 32, 0, 0, 0};
    public static final byte[] TEXT_PUNCTUATION_RAW = {59, 60, 62, 64, 91, 92, 93, 95, 96, 126, 33, 13, 9, 44, 58, 10, 45, 46, 36, 47, 34, 124, 42, 40, 41, 63, 123, 125, 39, 0};
    public static final byte[] PUNCTUATION = new byte[128];
    public static final Charset DEFAULT_ENCODING = StandardCharsets.ISO_8859_1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$zxing$pdf417$encoder$Compaction;

        static {
            int[] iArr = new int[Compaction.values().length];
            $SwitchMap$com$google$zxing$pdf417$encoder$Compaction = iArr;
            try {
                iArr[Compaction.TEXT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$zxing$pdf417$encoder$Compaction[Compaction.BYTE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$zxing$pdf417$encoder$Compaction[Compaction.NUMERIC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NoECIInput implements ECIInput {
        public final String input;

        public /* synthetic */ NoECIInput(String str, int i) {
            this(str);
        }

        @Override // com.google.zxing.common.ECIInput
        public final char charAt(int i) {
            return this.input.charAt(i);
        }

        @Override // com.google.zxing.common.ECIInput
        public final int getECIValue(int i) {
            return -1;
        }

        @Override // com.google.zxing.common.ECIInput
        public final boolean isECI(int i) {
            return false;
        }

        @Override // com.google.zxing.common.ECIInput
        public final int length() {
            return this.input.length();
        }

        @Override // com.google.zxing.common.ECIInput
        public final CharSequence subSequence(int i, int i2) {
            return this.input.subSequence(i, i2);
        }

        public final String toString() {
            return this.input;
        }

        private NoECIInput(String str) {
            this.input = str;
        }
    }

    static {
        int i = 0;
        byte[] bArr = new byte[128];
        MIXED = bArr;
        Arrays.fill(bArr, (byte) -1);
        int i2 = 0;
        while (true) {
            byte[] bArr2 = TEXT_MIXED_RAW;
            if (i2 >= bArr2.length) {
                break;
            }
            byte b = bArr2[i2];
            if (b > 0) {
                MIXED[b] = (byte) i2;
            }
            i2++;
        }
        Arrays.fill(PUNCTUATION, (byte) -1);
        while (true) {
            byte[] bArr3 = TEXT_PUNCTUATION_RAW;
            if (i >= bArr3.length) {
                return;
            }
            byte b2 = bArr3[i];
            if (b2 > 0) {
                PUNCTUATION[b2] = (byte) i;
            }
            i++;
        }
    }

    private PDF417HighLevelEncoder() {
    }

    public static void encodeBinary(byte[] bArr, int i, int i2, StringBuilder sb) {
        if (i == 1 && i2 == 0) {
            sb.append((char) 913);
        } else if (i % 6 == 0) {
            sb.append((char) 924);
        } else {
            sb.append((char) 901);
        }
        int i3 = 0;
        if (i >= 6) {
            char[] cArr = new char[5];
            int i4 = 0;
            while (i - i4 >= 6) {
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
            i3 = i4;
        }
        while (i3 < i) {
            sb.append((char) (bArr[i3] & 255));
            i3++;
        }
    }

    public static void encodeMultiECIBinary(int i, int i2, int i3, ECIInput eCIInput, StringBuilder sb) {
        int min = Math.min(i2 + i, eCIInput.length());
        int i4 = i;
        while (true) {
            if (i4 >= min || !eCIInput.isECI(i4)) {
                int i5 = i4;
                while (i5 < min && !eCIInput.isECI(i5)) {
                    i5++;
                }
                int i6 = i5 - i4;
                if (i6 <= 0) {
                    return;
                }
                byte[] bArr = new byte[i6];
                for (int i7 = i4; i7 < i5; i7++) {
                    bArr[i7 - i4] = (byte) (eCIInput.charAt(i7) & 255);
                }
                encodeBinary(bArr, i6, i4 == i ? i3 : 1, sb);
                i4 = i5;
            } else {
                encodingECI(sb, eCIInput.getECIValue(i4));
                i4++;
            }
        }
    }

    public static void encodeNumeric(ECIInput eCIInput, int i, int i2, StringBuilder sb) {
        StringBuilder sb2 = new StringBuilder((i2 / 3) + 1);
        BigInteger valueOf = BigInteger.valueOf(900L);
        BigInteger valueOf2 = BigInteger.valueOf(0L);
        int i3 = 0;
        while (i3 < i2) {
            sb2.setLength(0);
            int min = Math.min(44, i2 - i3);
            StringBuilder sb3 = new StringBuilder("1");
            int i4 = i + i3;
            sb3.append((Object) eCIInput.subSequence(i4, i4 + min));
            BigInteger bigInteger = new BigInteger(sb3.toString());
            do {
                sb2.append((char) bigInteger.mod(valueOf).intValue());
                bigInteger = bigInteger.divide(valueOf);
            } while (!bigInteger.equals(valueOf2));
            for (int length = sb2.length() - 1; length >= 0; length--) {
                sb.append(sb2.charAt(length));
            }
            i3 += min;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00fb A[EDGE_INSN: B:24:0x00fb->B:25:0x00fb BREAK  A[LOOP:0: B:2:0x000e->B:19:0x000e], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x000e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int encodeText(int r17, int r18, int r19, com.google.zxing.common.ECIInput r20, java.lang.StringBuilder r21) {
        /*
            Method dump skipped, instructions count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder.encodeText(int, int, int, com.google.zxing.common.ECIInput, java.lang.StringBuilder):int");
    }

    public static void encodingECI(StringBuilder sb, int i) {
        if (i >= 0 && i < 900) {
            sb.append((char) 927);
            sb.append((char) i);
        } else if (i < 810900) {
            sb.append((char) 926);
            sb.append((char) ((i / 900) - 1));
            sb.append((char) (i % 900));
        } else {
            if (i >= 811800) {
                throw new WriterException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "ECI number not in valid range from 0..811799, but was "));
            }
            sb.append((char) 925);
            sb.append((char) (810900 - i));
        }
    }

    public static boolean isAlphaLower(char c) {
        if (c != ' ') {
            return c >= 'a' && c <= 'z';
        }
        return true;
    }

    public static boolean isAlphaUpper(char c) {
        if (c != ' ') {
            return c >= 'A' && c <= 'Z';
        }
        return true;
    }
}

package com.google.zxing.qrcode.encoder;

import com.google.zxing.common.StringUtils;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Encoder {
    public static final int[] ALPHANUMERIC_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1};
    public static final Charset DEFAULT_BYTE_MODE_ENCODING = StandardCharsets.ISO_8859_1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.google.zxing.qrcode.encoder.Encoder$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$zxing$qrcode$decoder$Mode;

        static {
            int[] iArr = new int[Mode.values().length];
            $SwitchMap$com$google$zxing$qrcode$decoder$Mode = iArr;
            try {
                iArr[Mode.NUMERIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.ALPHANUMERIC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.BYTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.KANJI.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private Encoder() {
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x004f A[LOOP:0: B:11:0x0024->B:18:0x004f, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x005e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void appendBytes(java.lang.String r8, com.google.zxing.qrcode.decoder.Mode r9, com.google.zxing.common.BitArray r10, java.nio.charset.Charset r11) {
        /*
            Method dump skipped, instructions count: 279
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.encoder.Encoder.appendBytes(java.lang.String, com.google.zxing.qrcode.decoder.Mode, com.google.zxing.common.BitArray, java.nio.charset.Charset):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:172:0x0478, code lost:
    
        if ((r3 >= 0 && r3 < 8) != false) goto L203;
     */
    /* JADX WARN: Code restructure failed: missing block: B:243:0x0549, code lost:
    
        if (r7 == false) goto L273;
     */
    /* JADX WARN: Code restructure failed: missing block: B:284:0x05c7, code lost:
    
        if (r6 == false) goto L319;
     */
    /* JADX WARN: Removed duplicated region for block: B:231:0x0527  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x0556  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x05a6  */
    /* JADX WARN: Removed duplicated region for block: B:293:0x05ca  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x05d2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.zxing.qrcode.encoder.QRCode encode(java.lang.String r26, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel r27, java.util.Map r28) {
        /*
            Method dump skipped, instructions count: 1730
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.encoder.Encoder.encode(java.lang.String, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel, java.util.Map):com.google.zxing.qrcode.encoder.QRCode");
    }

    public static boolean isOnlyDoubleByteKanji(String str) {
        byte[] bytes = str.getBytes(StringUtils.SHIFT_JIS_CHARSET);
        int length = bytes.length;
        if (length % 2 != 0) {
            return false;
        }
        for (int i = 0; i < length; i += 2) {
            int i2 = bytes[i] & 255;
            if ((i2 < 129 || i2 > 159) && (i2 < 224 || i2 > 235)) {
                return false;
            }
        }
        return true;
    }

    public static boolean willFit(int i, Version version, ErrorCorrectionLevel errorCorrectionLevel) {
        int i2 = version.totalCodewords;
        Version.ECBlocks eCBlocks = version.ecBlocks[errorCorrectionLevel.ordinal()];
        int i3 = 0;
        for (Version.ECB ecb : eCBlocks.ecBlocks) {
            i3 += ecb.count;
        }
        return i2 - (i3 * eCBlocks.ecCodewordsPerBlock) >= (i + 7) / 8;
    }
}

package com.android.settingslib.qrcode;

import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class QrCodeGenerator {
    public static final QrCodeGenerator INSTANCE = new QrCodeGenerator();

    private QrCodeGenerator() {
    }

    public static final Bitmap encodeQrCode(int i, String str) {
        EnumMap enumMap = new EnumMap(EncodeHintType.class);
        INSTANCE.getClass();
        if (!StandardCharsets.ISO_8859_1.newEncoder().canEncode(str)) {
            enumMap.put((EnumMap) EncodeHintType.CHARACTER_SET, (EncodeHintType) StandardCharsets.UTF_8.name());
        }
        BitMatrix encode = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, i, i, enumMap);
        int[] iArr = new int[i * i];
        for (int i2 = 0; i2 < i; i2++) {
            for (int i3 = 0; i3 < i; i3++) {
                iArr[(i2 * i) + i3] = encode.get(i2, i3) ? -16777216 : -1;
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.RGB_565);
        createBitmap.setPixels(iArr, 0, i, 0, 0, i, i);
        return createBitmap;
    }
}

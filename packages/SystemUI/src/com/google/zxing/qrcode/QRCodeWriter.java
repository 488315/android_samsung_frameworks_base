package com.google.zxing.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import java.util.EnumMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class QRCodeWriter implements Writer {
    @Override // com.google.zxing.Writer
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (barcodeFormat != BarcodeFormat.QR_CODE) {
            throw new IllegalArgumentException("Can only encode QR_CODE, but got " + barcodeFormat);
        }
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("Requested dimensions are too small: " + i + 'x' + i2);
        }
        ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
        EncodeHintType encodeHintType = EncodeHintType.ERROR_CORRECTION;
        EnumMap enumMap = (EnumMap) map;
        if (enumMap.containsKey(encodeHintType)) {
            errorCorrectionLevel = ErrorCorrectionLevel.valueOf(enumMap.get(encodeHintType).toString());
        }
        EncodeHintType encodeHintType2 = EncodeHintType.MARGIN;
        int parseInt = enumMap.containsKey(encodeHintType2) ? Integer.parseInt(enumMap.get(encodeHintType2).toString()) : 4;
        ByteMatrix byteMatrix = Encoder.encode(str, errorCorrectionLevel, map).matrix;
        if (byteMatrix == null) {
            throw new IllegalStateException();
        }
        int i3 = parseInt * 2;
        int i4 = byteMatrix.width;
        int i5 = i4 + i3;
        int i6 = byteMatrix.height;
        int i7 = i3 + i6;
        int max = Math.max(i, i5);
        int max2 = Math.max(i2, i7);
        int min = Math.min(max / i5, max2 / i7);
        int i8 = (max - (i4 * min)) / 2;
        int i9 = (max2 - (i6 * min)) / 2;
        BitMatrix bitMatrix = new BitMatrix(max, max2);
        int i10 = 0;
        while (i10 < i6) {
            int i11 = 0;
            int i12 = i8;
            while (i11 < i4) {
                if (byteMatrix.get(i11, i10) == 1) {
                    bitMatrix.setRegion(i12, i9, min, min);
                }
                i11++;
                i12 += min;
            }
            i10++;
            i9 += min;
        }
        return bitMatrix;
    }
}

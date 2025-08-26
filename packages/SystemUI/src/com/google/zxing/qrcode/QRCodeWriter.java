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
        ErrorCorrectionLevel errorCorrectionLevelValueOf = ErrorCorrectionLevel.L;
        EncodeHintType encodeHintType = EncodeHintType.ERROR_CORRECTION;
        EnumMap enumMap = (EnumMap) map;
        if (enumMap.containsKey(encodeHintType)) {
            errorCorrectionLevelValueOf = ErrorCorrectionLevel.valueOf(enumMap.get(encodeHintType).toString());
        }
        EncodeHintType encodeHintType2 = EncodeHintType.MARGIN;
        int i3 = enumMap.containsKey(encodeHintType2) ? Integer.parseInt(enumMap.get(encodeHintType2).toString()) : 4;
        ByteMatrix byteMatrix = Encoder.encode(str, errorCorrectionLevelValueOf, map).matrix;
        if (byteMatrix == null) {
            throw new IllegalStateException();
        }
        int i4 = i3 * 2;
        int i5 = byteMatrix.width;
        int i6 = i5 + i4;
        int i7 = byteMatrix.height;
        int i8 = i4 + i7;
        int iMax = Math.max(i, i6);
        int iMax2 = Math.max(i2, i8);
        int iMin = Math.min(iMax / i6, iMax2 / i8);
        int i9 = (iMax - (i5 * iMin)) / 2;
        int i10 = (iMax2 - (i7 * iMin)) / 2;
        BitMatrix bitMatrix = new BitMatrix(iMax, iMax2);
        int i11 = 0;
        while (i11 < i7) {
            int i12 = 0;
            int i13 = i9;
            while (i12 < i5) {
                if (byteMatrix.get(i12, i11) == 1) {
                    bitMatrix.setRegion(i13, i10, iMin, iMin);
                }
                i12++;
                i13 += iMin;
            }
            i11++;
            i10 += iMin;
        }
        return bitMatrix;
    }
}

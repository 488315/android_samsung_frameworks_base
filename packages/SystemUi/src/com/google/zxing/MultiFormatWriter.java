package com.google.zxing;

import com.google.zxing.aztec.AztecWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.EAN8Writer;
import com.google.zxing.oned.ITFWriter;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MultiFormatWriter implements Writer {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.zxing.MultiFormatWriter$1 */
    public abstract /* synthetic */ class AbstractC45471 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$zxing$BarcodeFormat;

        static {
            int[] iArr = new int[BarcodeFormat.values().length];
            $SwitchMap$com$google$zxing$BarcodeFormat = iArr;
            try {
                iArr[BarcodeFormat.EAN_8.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$zxing$BarcodeFormat[BarcodeFormat.EAN_13.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$zxing$BarcodeFormat[BarcodeFormat.UPC_A.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$zxing$BarcodeFormat[BarcodeFormat.QR_CODE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$zxing$BarcodeFormat[BarcodeFormat.CODE_39.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$zxing$BarcodeFormat[BarcodeFormat.CODE_128.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$zxing$BarcodeFormat[BarcodeFormat.ITF.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$google$zxing$BarcodeFormat[BarcodeFormat.PDF_417.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$google$zxing$BarcodeFormat[BarcodeFormat.CODABAR.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$google$zxing$BarcodeFormat[BarcodeFormat.DATA_MATRIX.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$google$zxing$BarcodeFormat[BarcodeFormat.AZTEC.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }

    @Override // com.google.zxing.Writer
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) {
        Writer eAN8Writer;
        switch (AbstractC45471.$SwitchMap$com$google$zxing$BarcodeFormat[barcodeFormat.ordinal()]) {
            case 1:
                eAN8Writer = new EAN8Writer();
                break;
            case 2:
                eAN8Writer = new EAN13Writer();
                break;
            case 3:
                eAN8Writer = new UPCAWriter();
                break;
            case 4:
                eAN8Writer = new QRCodeWriter();
                break;
            case 5:
                eAN8Writer = new Code39Writer();
                break;
            case 6:
                eAN8Writer = new Code128Writer();
                break;
            case 7:
                eAN8Writer = new ITFWriter();
                break;
            case 8:
                eAN8Writer = new PDF417Writer();
                break;
            case 9:
                eAN8Writer = new CodaBarWriter();
                break;
            case 10:
                eAN8Writer = new DataMatrixWriter();
                break;
            case 11:
                eAN8Writer = new AztecWriter();
                break;
            default:
                throw new IllegalArgumentException("No encoder available for format " + barcodeFormat);
        }
        return eAN8Writer.encode(str, barcodeFormat, i, i2, map);
    }
}

package com.google.zxing.oned;

import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.sec.ims.configuration.DATA;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UPCAWriter implements Writer {
    public final EAN13Writer subWriter = new EAN13Writer();

    @Override // com.google.zxing.Writer
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) {
        if (barcodeFormat != BarcodeFormat.UPC_A) {
            throw new IllegalArgumentException("Can only encode UPC-A, but got " + barcodeFormat);
        }
        EAN13Writer eAN13Writer = this.subWriter;
        int length = str.length();
        if (length == 11) {
            int i3 = 0;
            for (int i4 = 0; i4 < 11; i4++) {
                i3 += (str.charAt(i4) - '0') * (i4 % 2 == 0 ? 3 : 1);
            }
            StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str);
            m18m.append((1000 - i3) % 10);
            str = m18m.toString();
        } else if (length != 12) {
            throw new IllegalArgumentException("Requested contents should be 11 or 12 digits long, but got " + str.length());
        }
        return eAN13Writer.encode(KeyAttributes$$ExternalSyntheticOutline0.m21m(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, str), BarcodeFormat.EAN_13, i, i2, map);
    }
}

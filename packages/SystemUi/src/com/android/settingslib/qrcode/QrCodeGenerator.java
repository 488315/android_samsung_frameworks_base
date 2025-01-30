package com.android.settingslib.qrcode;

import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class QrCodeGenerator {
    public static Bitmap encodeQrCode(int i, String str) {
        HashMap hashMap = new HashMap();
        if (!StandardCharsets.ISO_8859_1.newEncoder().canEncode(str)) {
            hashMap.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
        }
        BitMatrix encode = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, i, i, hashMap);
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.RGB_565);
        for (int i2 = 0; i2 < i; i2++) {
            for (int i3 = 0; i3 < i; i3++) {
                createBitmap.setPixel(i2, i3, encode.get(i2, i3) ? EmergencyPhoneWidget.BG_COLOR : -1);
            }
        }
        return createBitmap;
    }
}

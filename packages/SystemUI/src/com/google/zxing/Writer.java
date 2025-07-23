package com.google.zxing;

import com.google.zxing.common.BitMatrix;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface Writer {
    BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map);
}

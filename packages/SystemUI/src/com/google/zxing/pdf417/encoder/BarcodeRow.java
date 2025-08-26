package com.google.zxing.pdf417.encoder;

/* loaded from: classes4.dex */
public final class BarcodeRow {
    public int currentLocation = 0;
    public final byte[] row;

    public BarcodeRow(int i) {
        this.row = new byte[i];
    }
}

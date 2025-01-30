package com.google.zxing.qrcode.decoder;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public enum ErrorCorrectionLevel {
    L(1),
    /* JADX INFO: Fake field, exist only in values array */
    M(0),
    /* JADX INFO: Fake field, exist only in values array */
    Q(3),
    /* JADX INFO: Fake field, exist only in values array */
    H(2);

    private final int bits;

    ErrorCorrectionLevel(int i) {
        this.bits = i;
    }

    public final int getBits() {
        return this.bits;
    }
}

package com.google.zxing.qrcode.decoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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

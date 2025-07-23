package com.samsung.android.knox.analytics.util;

/* loaded from: classes6.dex */
public class ZipResult {
    private byte[] content;
    private int length;
    private int originalLength;

    public ZipResult(byte[] bArr, int i, int i2) {
        this.content = bArr;
        this.length = i;
        this.originalLength = i2;
    }

    public byte[] getContent() {
        return this.content;
    }

    public int getLength() {
        return this.length;
    }

    public int getOriginalLength() {
        return this.originalLength;
    }
}

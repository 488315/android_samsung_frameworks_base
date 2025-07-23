package com.samsung.android.media;

/* loaded from: classes6.dex */
public class SemQuramDngFingerPrint {
    static final int kDNGFingerprintSize = 16;
    byte[] data;

    public SemQuramDngFingerPrint() {
        this.data = new byte[16];
    }

    public SemQuramDngFingerPrint(byte[] bArr) {
        this.data = bArr;
    }

    public boolean isNull() {
        for (int i = 0; i < 16; i++) {
            if (this.data[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isValid() {
        return !isNull();
    }

    public void clear() {
        for (int i = 0; i < 16; i++) {
            this.data[i] = 0;
        }
    }

    public byte[] getData() {
        return this.data;
    }
}

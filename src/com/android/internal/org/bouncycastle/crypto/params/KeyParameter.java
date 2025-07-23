package com.android.internal.org.bouncycastle.crypto.params;

import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.util.Arrays;

/* loaded from: classes5.dex */
public class KeyParameter implements CipherParameters {
    private byte[] key;

    public KeyParameter(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public KeyParameter(byte[] bArr, int i, int i2) {
        this(i2);
        System.arraycopy(bArr, i, this.key, 0, i2);
    }

    private KeyParameter(int i) {
        this.key = new byte[i];
    }

    public void copyTo(byte[] bArr, int i, int i2) {
        byte[] bArr2 = this.key;
        if (bArr2.length != i2) {
            throw new IllegalArgumentException("len");
        }
        System.arraycopy(bArr2, 0, bArr, i, i2);
    }

    public byte[] getKey() {
        return this.key;
    }

    public int getKeyLength() {
        return this.key.length;
    }

    public KeyParameter reverse() {
        KeyParameter keyParameter = new KeyParameter(this.key.length);
        Arrays.reverse(this.key, keyParameter.key);
        return keyParameter;
    }
}

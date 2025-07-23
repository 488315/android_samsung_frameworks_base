package com.android.internal.org.bouncycastle.crypto.params;

import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.util.Arrays;

/* loaded from: classes5.dex */
public class AEADParameters implements CipherParameters {
    private byte[] associatedText;
    private KeyParameter key;
    private int macSize;
    private byte[] nonce;

    public AEADParameters(KeyParameter keyParameter, int i, byte[] bArr) {
        this(keyParameter, i, bArr, null);
    }

    public AEADParameters(KeyParameter keyParameter, int i, byte[] bArr, byte[] bArr2) {
        this.key = keyParameter;
        this.nonce = Arrays.clone(bArr);
        this.macSize = i;
        this.associatedText = Arrays.clone(bArr2);
    }

    public KeyParameter getKey() {
        return this.key;
    }

    public int getMacSize() {
        return this.macSize;
    }

    public byte[] getAssociatedText() {
        return Arrays.clone(this.associatedText);
    }

    public byte[] getNonce() {
        return Arrays.clone(this.nonce);
    }
}

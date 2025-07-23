package com.android.internal.org.bouncycastle.jcajce;

import com.android.internal.org.bouncycastle.util.Arrays;
import javax.crypto.SecretKey;

/* loaded from: classes5.dex */
public final class SecretKeyWithEncapsulation implements SecretKey {
    private final byte[] encapsulation;
    private final SecretKey secretKey;

    public SecretKeyWithEncapsulation(SecretKey secretKey, byte[] bArr) {
        this.secretKey = secretKey;
        this.encapsulation = Arrays.clone(bArr);
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return this.secretKey.getAlgorithm();
    }

    @Override // java.security.Key
    public String getFormat() {
        return this.secretKey.getFormat();
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        return this.secretKey.getEncoded();
    }

    public byte[] getEncapsulation() {
        return Arrays.clone(this.encapsulation);
    }

    public boolean equals(Object obj) {
        return this.secretKey.equals(obj);
    }

    public int hashCode() {
        return this.secretKey.hashCode();
    }
}

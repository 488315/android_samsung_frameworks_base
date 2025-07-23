package com.samsung.android.knox.dar.ddar.securesession;

import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.SecretKey;
import javax.security.auth.DestroyFailedException;

/* loaded from: classes6.dex */
class SessionSecretKeySpec implements KeySpec, SecretKey {
    private static final long serialVersionUID = 6560385466025255248L;
    private String algorithm;
    private boolean isDestroyed;
    private byte[] key;

    SessionSecretKeySpec(byte[] bArr, String str) {
        if (bArr == null || str == null) {
            throw new IllegalArgumentException("No key/algorithm specified");
        }
        if (bArr.length == 0) {
            throw new IllegalArgumentException("Key length is zero");
        }
        this.key = (byte[]) bArr.clone();
        this.algorithm = str;
        this.isDestroyed = false;
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override // java.security.Key
    public String getFormat() {
        return "RAW";
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        return this.key;
    }

    public int hashCode() {
        int i = 0;
        if (this.isDestroyed) {
            return 0;
        }
        int i2 = 1;
        while (true) {
            byte[] bArr = this.key;
            if (i2 < bArr.length) {
                i += bArr[i2] * i2;
                i2++;
            } else {
                return this.algorithm.hashCode() ^ i;
            }
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SecretKey)) {
            return false;
        }
        SecretKey secretKey = (SecretKey) obj;
        if (!secretKey.getAlgorithm().equalsIgnoreCase(this.algorithm) || this.isDestroyed != secretKey.isDestroyed()) {
            return false;
        }
        return Arrays.equals(this.key, secretKey.getEncoded());
    }

    @Override // javax.security.auth.Destroyable
    public void destroy() throws DestroyFailedException {
        byte[] bArr = this.key;
        if (bArr != null) {
            Arrays.fill(bArr, (byte) 0);
            this.key = null;
        }
        this.isDestroyed = true;
    }

    @Override // javax.security.auth.Destroyable
    public boolean isDestroyed() {
        return this.isDestroyed;
    }
}

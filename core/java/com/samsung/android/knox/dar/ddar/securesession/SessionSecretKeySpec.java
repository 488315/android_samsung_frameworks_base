package com.samsung.android.knox.dar.ddar.securesession;

import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.SecretKey;
import javax.security.auth.DestroyFailedException;

/* loaded from: classes5.dex */
class SessionSecretKeySpec implements KeySpec, SecretKey {
    private static final long serialVersionUID = 6560385466025255248L;
    private String algorithm;
    private boolean isDestroyed;
    private byte[] key;

    SessionSecretKeySpec(byte[] key, String algorithm) {
        if (key == null || algorithm == null) {
            throw new IllegalArgumentException("No key/algorithm specified");
        }
        if (key.length == 0) {
            throw new IllegalArgumentException("Key length is zero");
        }
        this.key = (byte[]) key.clone();
        this.algorithm = algorithm;
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
        int retval = 0;
        if (this.isDestroyed) {
            return 0;
        }
        int i = 1;
        while (true) {
            byte[] bArr = this.key;
            if (i < bArr.length) {
                retval += bArr[i] * i;
                i++;
            } else {
                return retval ^ this.algorithm.hashCode();
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
        String thatAlg = ((SecretKey) obj).getAlgorithm();
        if (!thatAlg.equalsIgnoreCase(this.algorithm) || this.isDestroyed != ((SecretKey) obj).isDestroyed()) {
            return false;
        }
        byte[] thatKey = ((SecretKey) obj).getEncoded();
        return Arrays.equals(this.key, thatKey);
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

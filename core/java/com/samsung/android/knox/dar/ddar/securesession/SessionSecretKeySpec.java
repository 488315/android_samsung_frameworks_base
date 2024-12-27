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
        for (int i = 1; i < this.key.length; i++) {
            retval += this.key[i] * i;
        }
        return retval ^ this.algorithm.hashCode();
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
        if (this.key != null) {
            Arrays.fill(this.key, (byte) 0);
            this.key = null;
        }
        this.isDestroyed = true;
    }

    @Override // javax.security.auth.Destroyable
    public boolean isDestroyed() {
        return this.isDestroyed;
    }
}

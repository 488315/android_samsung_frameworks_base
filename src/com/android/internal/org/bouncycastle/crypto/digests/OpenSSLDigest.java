package com.android.internal.org.bouncycastle.crypto.digests;

import android.security.keystore.KeyProperties;
import com.android.internal.org.bouncycastle.crypto.ExtendedDigest;
import java.security.DigestException;
import java.security.MessageDigest;

/* loaded from: classes5.dex */
public class OpenSSLDigest implements ExtendedDigest {
    private final int byteSize;
    private final MessageDigest delegate;

    public OpenSSLDigest(String str, int i) {
        try {
            this.delegate = MessageDigest.getInstance(str, "AndroidOpenSSL");
            this.byteSize = i;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return this.delegate.getAlgorithm();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return this.delegate.getDigestLength();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return this.byteSize;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void reset() {
        this.delegate.reset();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void update(byte b) {
        this.delegate.update(b);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i, int i2) {
        this.delegate.update(bArr, i, i2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i) {
        try {
            return this.delegate.digest(bArr, i, bArr.length - i);
        } catch (DigestException e) {
            throw new RuntimeException(e);
        }
    }

    public static class MD5 extends OpenSSLDigest {
        public MD5() {
            super(KeyProperties.DIGEST_MD5, 64);
        }
    }

    public static class SHA1 extends OpenSSLDigest {
        public SHA1() {
            super("SHA-1", 64);
        }
    }

    public static class SHA224 extends OpenSSLDigest {
        public SHA224() {
            super(KeyProperties.DIGEST_SHA224, 64);
        }
    }

    public static class SHA256 extends OpenSSLDigest {
        public SHA256() {
            super("SHA-256", 64);
        }
    }

    public static class SHA384 extends OpenSSLDigest {
        public SHA384() {
            super(KeyProperties.DIGEST_SHA384, 128);
        }
    }

    public static class SHA512 extends OpenSSLDigest {
        public SHA512() {
            super(KeyProperties.DIGEST_SHA512, 128);
        }
    }
}

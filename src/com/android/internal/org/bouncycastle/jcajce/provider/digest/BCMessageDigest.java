package com.android.internal.org.bouncycastle.jcajce.provider.digest;

import com.android.internal.org.bouncycastle.crypto.Digest;
import java.security.DigestException;
import java.security.MessageDigest;

/* loaded from: classes5.dex */
public class BCMessageDigest extends MessageDigest {
    protected Digest digest;
    protected int digestSize;

    protected BCMessageDigest(Digest digest) {
        super(digest.getAlgorithmName());
        this.digest = digest;
        this.digestSize = digest.getDigestSize();
    }

    @Override // java.security.MessageDigestSpi
    public void engineReset() {
        this.digest.reset();
    }

    @Override // java.security.MessageDigestSpi
    public void engineUpdate(byte b) {
        this.digest.update(b);
    }

    @Override // java.security.MessageDigestSpi
    public void engineUpdate(byte[] bArr, int i, int i2) {
        this.digest.update(bArr, i, i2);
    }

    @Override // java.security.MessageDigestSpi
    public int engineGetDigestLength() {
        return this.digestSize;
    }

    @Override // java.security.MessageDigestSpi
    public byte[] engineDigest() {
        byte[] bArr = new byte[this.digestSize];
        this.digest.doFinal(bArr, 0);
        return bArr;
    }

    @Override // java.security.MessageDigestSpi
    public int engineDigest(byte[] bArr, int i, int i2) throws DigestException {
        int i3 = this.digestSize;
        if (i2 < i3) {
            throw new DigestException("partial digests not returned");
        }
        if (bArr.length - i < i3) {
            throw new DigestException("insufficient space in the output buffer to store the digest");
        }
        this.digest.doFinal(bArr, i);
        return this.digestSize;
    }
}

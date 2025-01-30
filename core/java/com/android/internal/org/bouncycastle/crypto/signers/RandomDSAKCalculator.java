package com.android.internal.org.bouncycastle.crypto.signers;

import com.android.internal.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes5.dex */
public class RandomDSAKCalculator implements DSAKCalculator {
    private static final BigInteger ZERO = BigInteger.valueOf(0);

    /* renamed from: q */
    private BigInteger f891q;
    private SecureRandom random;

    @Override // com.android.internal.org.bouncycastle.crypto.signers.DSAKCalculator
    public boolean isDeterministic() {
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.signers.DSAKCalculator
    public void init(BigInteger n, SecureRandom random) {
        this.f891q = n;
        this.random = random;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.signers.DSAKCalculator
    public void init(BigInteger n, BigInteger d, byte[] message) {
        throw new IllegalStateException("Operation not supported");
    }

    @Override // com.android.internal.org.bouncycastle.crypto.signers.DSAKCalculator
    public BigInteger nextK() {
        int qBitLength = this.f891q.bitLength();
        while (true) {
            BigInteger k = BigIntegers.createRandomBigInteger(qBitLength, this.random);
            if (!k.equals(ZERO) && k.compareTo(this.f891q) < 0) {
                return k;
            }
        }
    }
}

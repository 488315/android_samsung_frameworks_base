package com.android.internal.org.bouncycastle.crypto.params;

import com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters;
import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes5.dex */
public class RSAKeyGenerationParameters extends KeyGenerationParameters {
    private int certainty;
    private BigInteger publicExponent;

    public RSAKeyGenerationParameters(BigInteger bigInteger, SecureRandom secureRandom, int i, int i2) {
        super(secureRandom, i);
        if (i < 12) {
            throw new IllegalArgumentException("key strength too small");
        }
        if (!bigInteger.testBit(0)) {
            throw new IllegalArgumentException("public exponent cannot be even");
        }
        this.publicExponent = bigInteger;
        this.certainty = i2;
    }

    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }

    public int getCertainty() {
        return this.certainty;
    }
}

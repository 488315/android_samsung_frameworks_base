package com.android.internal.org.bouncycastle.crypto;

import com.android.internal.org.bouncycastle.crypto.constraints.DefaultServiceProperties;
import java.security.SecureRandom;

/* loaded from: classes5.dex */
public class CipherKeyGenerator {
    protected SecureRandom random;
    protected int strength;

    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.random = keyGenerationParameters.getRandom();
        this.strength = (keyGenerationParameters.getStrength() + 7) / 8;
        CryptoServicesRegistrar.checkConstraints(new DefaultServiceProperties("SymKeyGen", keyGenerationParameters.getStrength()));
    }

    public byte[] generateKey() {
        byte[] bArr = new byte[this.strength];
        this.random.nextBytes(bArr);
        return bArr;
    }
}

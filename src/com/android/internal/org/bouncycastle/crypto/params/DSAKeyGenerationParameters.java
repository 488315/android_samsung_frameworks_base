package com.android.internal.org.bouncycastle.crypto.params;

import com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters;
import java.security.SecureRandom;

/* loaded from: classes5.dex */
public class DSAKeyGenerationParameters extends KeyGenerationParameters {
    private DSAParameters params;

    public DSAKeyGenerationParameters(SecureRandom secureRandom, DSAParameters dSAParameters) {
        super(secureRandom, dSAParameters.getP().bitLength() - 1);
        this.params = dSAParameters;
    }

    public DSAParameters getParameters() {
        return this.params;
    }
}

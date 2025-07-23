package com.android.internal.org.bouncycastle.crypto.params;

import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import java.security.SecureRandom;

/* loaded from: classes5.dex */
public class ParametersWithRandom implements CipherParameters {
    private CipherParameters parameters;
    private SecureRandom random;

    public ParametersWithRandom(CipherParameters cipherParameters, SecureRandom secureRandom) {
        this.random = CryptoServicesRegistrar.getSecureRandom(secureRandom);
        this.parameters = cipherParameters;
    }

    public ParametersWithRandom(CipherParameters cipherParameters) {
        this(cipherParameters, null);
    }

    public SecureRandom getRandom() {
        return this.random;
    }

    public CipherParameters getParameters() {
        return this.parameters;
    }
}

package com.android.internal.org.bouncycastle.jcajce.provider.symmetric;

import com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import com.android.internal.org.bouncycastle.crypto.CipherKeyGenerator;
import com.android.internal.org.bouncycastle.crypto.engines.BlowfishEngine;
import com.android.internal.org.bouncycastle.crypto.modes.CBCBlockCipher;
import com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class Blowfish {
    private Blowfish() {}

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super(new BlowfishEngine());
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super(new CBCBlockCipher(new BlowfishEngine()), 64);
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("Blowfish", 128, new CipherKeyGenerator());
        }
    }

    public static class AlgParams extends IvAlgorithmParameters {
        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters, java.security.AlgorithmParametersSpi
        protected String engineToString() {
            return "Blowfish IV";
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String PREFIX = Blowfish.class.getName();

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider provider) {
            provider.addAlgorithm("Cipher.BLOWFISH", PREFIX + "$ECB");
            provider.addAlgorithm("KeyGenerator.BLOWFISH", PREFIX + "$KeyGen");
            provider.addAlgorithm(
                    "Alg.Alias.KeyGenerator",
                    MiscObjectIdentifiers.cryptlib_algorithm_blowfish_CBC,
                    "BLOWFISH");
            provider.addAlgorithm("AlgorithmParameters.BLOWFISH", PREFIX + "$AlgParams");
            provider.addAlgorithm(
                    "Alg.Alias.AlgorithmParameters",
                    MiscObjectIdentifiers.cryptlib_algorithm_blowfish_CBC,
                    "BLOWFISH");
        }
    }
}

package com.android.internal.org.bouncycastle.jcajce.provider.keystore;

import com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

/* loaded from: classes5.dex */
public class BC {
    private static final String PREFIX = "com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.";

    public static class Mappings extends AsymmetricAlgorithmProvider {
        @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("KeyStore.BKS", "com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi$Std");
            configurableProvider.addAlgorithm("KeyStore.BouncyCastle", "com.android.internal.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi$BouncyCastleStore");
            configurableProvider.addAlgorithm("Alg.Alias.KeyStore.UBER", "BouncyCastle");
            configurableProvider.addAlgorithm("Alg.Alias.KeyStore.BOUNCYCASTLE", "BouncyCastle");
            configurableProvider.addAlgorithm("Alg.Alias.KeyStore.bouncycastle", "BouncyCastle");
        }
    }
}

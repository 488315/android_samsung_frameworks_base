package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric;

import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

/* loaded from: classes5.dex */
public class LMS {
    private static final String PREFIX = "com.android.internal.org.bouncycastle.pqc.jcajce.provider.lms.";

    public static class Mappings extends AsymmetricAlgorithmProvider {
        @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("KeyFactory.LMS", "com.android.internal.org.bouncycastle.pqc.jcajce.provider.lms.LMSKeyFactorySpi");
            configurableProvider.addAlgorithm("Alg.Alias.KeyFactory." + PKCSObjectIdentifiers.id_alg_hss_lms_hashsig, "LMS");
            configurableProvider.addAlgorithm("KeyPairGenerator.LMS", "com.android.internal.org.bouncycastle.pqc.jcajce.provider.lms.LMSKeyPairGeneratorSpi");
            configurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator." + PKCSObjectIdentifiers.id_alg_hss_lms_hashsig, "LMS");
            configurableProvider.addAlgorithm("Signature.LMS", "com.android.internal.org.bouncycastle.pqc.jcajce.provider.lms.LMSSignatureSpi$generic");
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + PKCSObjectIdentifiers.id_alg_hss_lms_hashsig, "LMS");
        }
    }
}

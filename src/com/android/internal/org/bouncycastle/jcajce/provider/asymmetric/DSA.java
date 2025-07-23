package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric;

import com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil;
import com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.KeyFactorySpi;
import com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

/* loaded from: classes5.dex */
public class DSA {
    private static final String PREFIX = "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.";

    public static class Mappings extends AsymmetricAlgorithmProvider {
        @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("AlgorithmParameters.DSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.AlgorithmParametersSpi");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.DSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.AlgorithmParameterGeneratorSpi");
            configurableProvider.addAlgorithm("KeyPairGenerator.DSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.KeyPairGeneratorSpi");
            configurableProvider.addAlgorithm("KeyFactory.DSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.KeyFactorySpi");
            configurableProvider.addAlgorithm("Signature.SHA1withDSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner$stdDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.DSA", "SHA1withDSA");
            configurableProvider.addAlgorithm("Signature.NONEWITHDSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner$noneDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.RAWDSA", "NONEWITHDSA");
            addSignatureAlgorithm(configurableProvider, "SHA224", "DSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner$dsa224", NISTObjectIdentifiers.dsa_with_sha224);
            addSignatureAlgorithm(configurableProvider, "SHA256", "DSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner$dsa256", NISTObjectIdentifiers.dsa_with_sha256);
            configurableProvider.addAlgorithm("Alg.Alias.Signature.SHA/DSA", "SHA1withDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.SHA1withDSA", "SHA1withDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.SHA1WITHDSA", "SHA1withDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.10040.4.1", "SHA1withDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.10040.4.3", "SHA1withDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.DSAwithSHA1", "SHA1withDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.DSAWITHSHA1", "SHA1withDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.SHA1WithDSA", "SHA1withDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.DSAWithSHA1", "SHA1withDSA");
            KeyFactorySpi keyFactorySpi = new KeyFactorySpi();
            for (int i = 0; i != DSAUtil.dsaOids.length; i++) {
                configurableProvider.addAlgorithm("Alg.Alias.Signature." + DSAUtil.dsaOids[i], "SHA1withDSA");
                registerOid(configurableProvider, DSAUtil.dsaOids[i], "DSA", keyFactorySpi);
                registerOidAlgorithmParameterGenerator(configurableProvider, DSAUtil.dsaOids[i], "DSA");
            }
        }
    }
}

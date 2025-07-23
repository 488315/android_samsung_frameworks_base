package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyFactorySpi;
import com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class RSA {
    private static final String PREFIX = "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.";
    private static final Map<String, String> generalRsaAttributes;

    static {
        HashMap hashMap = new HashMap();
        generalRsaAttributes = hashMap;
        hashMap.put("SupportedKeyClasses", "java.security.interfaces.RSAPublicKey|java.security.interfaces.RSAPrivateKey");
        hashMap.put("SupportedKeyFormats", "PKCS#8|X.509");
    }

    public static class Mappings extends AsymmetricAlgorithmProvider {
        @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("AlgorithmParameters.PSS", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi$PSS");
            configurableProvider.addAttributes("Cipher.RSA", RSA.generalRsaAttributes);
            configurableProvider.addAlgorithm("Cipher.RSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.CipherSpi$NoPadding");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA/RAW", "RSA");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA//RAW", "RSA");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA//NOPADDING", "RSA");
            configurableProvider.addAlgorithm("KeyFactory.RSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyFactorySpi");
            configurableProvider.addAlgorithm("KeyPairGenerator.RSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyPairGeneratorSpi");
            KeyFactorySpi keyFactorySpi = new KeyFactorySpi();
            registerOid(configurableProvider, PKCSObjectIdentifiers.rsaEncryption, "RSA", keyFactorySpi);
            registerOid(configurableProvider, X509ObjectIdentifiers.id_ea_rsa, "RSA", keyFactorySpi);
            registerOid(configurableProvider, PKCSObjectIdentifiers.id_RSAES_OAEP, "RSA", keyFactorySpi);
        }

        private void addDigestSignature(ConfigurableProvider configurableProvider, String str, String str2, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
            String str3 = str + "WITHRSA";
            String str4 = str + "withRSA";
            String str5 = str + "WithRSA";
            String str6 = str + "/RSA";
            String str7 = str + "WITHRSAENCRYPTION";
            String str8 = str + "withRSAEncryption";
            configurableProvider.addAlgorithm("Signature." + str3, str2);
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str4, str3);
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str5, str3);
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str7, str3);
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str8, str3);
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + (str + "WithRSAEncryption"), str3);
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str6, str3);
            if (aSN1ObjectIdentifier != null) {
                configurableProvider.addAlgorithm("Alg.Alias.Signature." + aSN1ObjectIdentifier, str3);
                configurableProvider.addAlgorithm("Alg.Alias.Signature.OID." + aSN1ObjectIdentifier, str3);
            }
            configurableProvider.addAttributes("Signature." + str3, RSA.generalRsaAttributes);
        }

        private void addISO9796Signature(ConfigurableProvider configurableProvider, String str, String str2) {
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "withRSA/ISO9796-2", str + "WITHRSA/ISO9796-2");
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "WithRSA/ISO9796-2", str + "WITHRSA/ISO9796-2");
            configurableProvider.addAlgorithm("Signature." + str + "WITHRSA/ISO9796-2", str2);
            configurableProvider.addAttributes("Signature." + str + "WITHRSA/ISO9796-2", RSA.generalRsaAttributes);
        }

        private void addPSSSignature(ConfigurableProvider configurableProvider, String str, String str2, String str3) {
            String str4 = "WITHRSAAND" + str2;
            if (str2.equals("MGF1")) {
                configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "withRSA/PSS", str + str4);
                configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "WithRSA/PSS", str + str4);
                configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "WITHRSA/PSS", str + str4);
                configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "withRSASSA-PSS", str + str4);
                configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "WithRSASSA-PSS", str + str4);
                configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "WITHRSASSA-PSS", str + str4);
            }
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "withRSAand" + str2, str + str4);
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "WithRSAAnd" + str2, str + str4);
            configurableProvider.addAlgorithm("Signature." + str + "WITHRSAAND" + str2, str3);
            configurableProvider.addAttributes("Signature." + str + "WITHRSAAND" + str2, RSA.generalRsaAttributes);
        }

        private void addPSSSignature(ConfigurableProvider configurableProvider, String str, String str2, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "withRSA/PSS", str + "WITHRSAANDMGF1");
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "WithRSA/PSS", str + "WITHRSAANDMGF1");
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "withRSAandMGF1", str + "WITHRSAANDMGF1");
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "WithRSAAndMGF1", str + "WITHRSAANDMGF1");
            configurableProvider.addAlgorithm("Signature." + str + "WITHRSAANDMGF1", str2);
        }

        private void addX931Signature(ConfigurableProvider configurableProvider, String str, String str2) {
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "withRSA/X9.31", str + "WITHRSA/X9.31");
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "WithRSA/X9.31", str + "WITHRSA/X9.31");
            configurableProvider.addAlgorithm("Signature." + str + "WITHRSA/X9.31", str2);
            configurableProvider.addAttributes("Signature." + str + "WITHRSA/X9.31", RSA.generalRsaAttributes);
        }
    }
}

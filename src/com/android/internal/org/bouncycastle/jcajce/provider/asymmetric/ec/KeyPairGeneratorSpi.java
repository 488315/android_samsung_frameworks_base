package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec;

import android.security.keystore.KeyProperties;
import com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.internal.org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.internal.org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters;
import com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;
import com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveSpec;
import com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec;
import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.util.Integers;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.util.Hashtable;

/* loaded from: classes5.dex */
public abstract class KeyPairGeneratorSpi extends KeyPairGenerator {
    public KeyPairGeneratorSpi(String str) {
        super(str);
    }

    public static class EC extends KeyPairGeneratorSpi {
        private static Hashtable ecParameters;
        String algorithm;
        ProviderConfiguration configuration;
        Object ecParams;
        ECKeyPairGenerator engine;
        boolean initialised;
        ECKeyGenerationParameters param;
        SecureRandom random;
        int strength;

        static {
            Hashtable hashtable = new Hashtable();
            ecParameters = hashtable;
            hashtable.put(Integers.valueOf(192), new ECGenParameterSpec("prime192v1"));
            ecParameters.put(Integers.valueOf(239), new ECGenParameterSpec("prime239v1"));
            ecParameters.put(Integers.valueOf(256), new ECGenParameterSpec("prime256v1"));
            ecParameters.put(Integers.valueOf(224), new ECGenParameterSpec("P-224"));
            ecParameters.put(Integers.valueOf(384), new ECGenParameterSpec("P-384"));
            ecParameters.put(Integers.valueOf(521), new ECGenParameterSpec("P-521"));
        }

        public EC() {
            super(KeyProperties.KEY_ALGORITHM_EC);
            this.engine = new ECKeyPairGenerator();
            this.ecParams = null;
            this.strength = 256;
            this.random = CryptoServicesRegistrar.getSecureRandom();
            this.initialised = false;
            this.algorithm = KeyProperties.KEY_ALGORITHM_EC;
            this.configuration = BouncyCastleProvider.CONFIGURATION;
        }

        public EC(String str, ProviderConfiguration providerConfiguration) {
            super(str);
            this.engine = new ECKeyPairGenerator();
            this.ecParams = null;
            this.strength = 256;
            this.random = CryptoServicesRegistrar.getSecureRandom();
            this.initialised = false;
            this.algorithm = str;
            this.configuration = providerConfiguration;
        }

        @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
        public void initialize(int i, SecureRandom secureRandom) {
            this.strength = i;
            if (secureRandom != null) {
                this.random = secureRandom;
            }
            ECGenParameterSpec eCGenParameterSpec = (ECGenParameterSpec) ecParameters.get(Integers.valueOf(i));
            if (eCGenParameterSpec == null) {
                throw new InvalidParameterException("unknown key size.");
            }
            try {
                initialize(eCGenParameterSpec, secureRandom);
            } catch (InvalidAlgorithmParameterException unused) {
                throw new InvalidParameterException("key size not configurable.");
            }
        }

        @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
        public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            if (secureRandom == null) {
                secureRandom = this.random;
            }
            if (algorithmParameterSpec == null) {
                ECParameterSpec ecImplicitlyCa = this.configuration.getEcImplicitlyCa();
                if (ecImplicitlyCa == null) {
                    throw new InvalidAlgorithmParameterException("null parameter passed but no implicitCA set");
                }
                this.ecParams = null;
                this.param = createKeyGenParamsBC(ecImplicitlyCa, secureRandom);
            } else if (algorithmParameterSpec instanceof ECParameterSpec) {
                this.ecParams = algorithmParameterSpec;
                this.param = createKeyGenParamsBC((ECParameterSpec) algorithmParameterSpec, secureRandom);
            } else if (algorithmParameterSpec instanceof java.security.spec.ECParameterSpec) {
                this.ecParams = algorithmParameterSpec;
                this.param = createKeyGenParamsJCE((java.security.spec.ECParameterSpec) algorithmParameterSpec, secureRandom);
            } else if (algorithmParameterSpec instanceof ECGenParameterSpec) {
                initializeNamedCurve(((ECGenParameterSpec) algorithmParameterSpec).getName(), secureRandom);
            } else if (algorithmParameterSpec instanceof ECNamedCurveGenParameterSpec) {
                initializeNamedCurve(((ECNamedCurveGenParameterSpec) algorithmParameterSpec).getName(), secureRandom);
            } else {
                String nameFrom = ECUtil.getNameFrom(algorithmParameterSpec);
                if (nameFrom != null) {
                    initializeNamedCurve(nameFrom, secureRandom);
                } else {
                    throw new InvalidAlgorithmParameterException("invalid parameterSpec: " + algorithmParameterSpec);
                }
            }
            this.engine.init(this.param);
            this.initialised = true;
        }

        @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
        public KeyPair generateKeyPair() {
            if (!this.initialised) {
                initialize(this.strength, new SecureRandom());
            }
            AsymmetricCipherKeyPair generateKeyPair = this.engine.generateKeyPair();
            ECPublicKeyParameters eCPublicKeyParameters = (ECPublicKeyParameters) generateKeyPair.getPublic();
            ECPrivateKeyParameters eCPrivateKeyParameters = (ECPrivateKeyParameters) generateKeyPair.getPrivate();
            Object obj = this.ecParams;
            if (obj instanceof ECParameterSpec) {
                ECParameterSpec eCParameterSpec = (ECParameterSpec) obj;
                BCECPublicKey bCECPublicKey = new BCECPublicKey(this.algorithm, eCPublicKeyParameters, eCParameterSpec, this.configuration);
                return new KeyPair(bCECPublicKey, new BCECPrivateKey(this.algorithm, eCPrivateKeyParameters, bCECPublicKey, eCParameterSpec, this.configuration));
            }
            if (obj == null) {
                return new KeyPair(new BCECPublicKey(this.algorithm, eCPublicKeyParameters, this.configuration), new BCECPrivateKey(this.algorithm, eCPrivateKeyParameters, this.configuration));
            }
            java.security.spec.ECParameterSpec eCParameterSpec2 = (java.security.spec.ECParameterSpec) obj;
            BCECPublicKey bCECPublicKey2 = new BCECPublicKey(this.algorithm, eCPublicKeyParameters, eCParameterSpec2, this.configuration);
            return new KeyPair(bCECPublicKey2, new BCECPrivateKey(this.algorithm, eCPrivateKeyParameters, bCECPublicKey2, eCParameterSpec2, this.configuration));
        }

        protected ECKeyGenerationParameters createKeyGenParamsBC(ECParameterSpec eCParameterSpec, SecureRandom secureRandom) {
            return new ECKeyGenerationParameters(new ECDomainParameters(eCParameterSpec.getCurve(), eCParameterSpec.getG(), eCParameterSpec.getN(), eCParameterSpec.getH()), secureRandom);
        }

        protected ECKeyGenerationParameters createKeyGenParamsJCE(java.security.spec.ECParameterSpec eCParameterSpec, SecureRandom secureRandom) {
            X9ECParameters domainParametersFromName;
            if ((eCParameterSpec instanceof ECNamedCurveSpec) && (domainParametersFromName = ECUtils.getDomainParametersFromName(((ECNamedCurveSpec) eCParameterSpec).getName(), this.configuration)) != null) {
                return createKeyGenParamsJCE(domainParametersFromName, secureRandom);
            }
            ECCurve convertCurve = EC5Util.convertCurve(eCParameterSpec.getCurve());
            return new ECKeyGenerationParameters(new ECDomainParameters(convertCurve, EC5Util.convertPoint(convertCurve, eCParameterSpec.getGenerator()), eCParameterSpec.getOrder(), BigInteger.valueOf(eCParameterSpec.getCofactor())), secureRandom);
        }

        protected ECKeyGenerationParameters createKeyGenParamsJCE(X9ECParameters x9ECParameters, SecureRandom secureRandom) {
            return new ECKeyGenerationParameters(new ECDomainParameters(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN(), x9ECParameters.getH()), secureRandom);
        }

        protected void initializeNamedCurve(String str, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            X9ECParameters domainParametersFromName = ECUtils.getDomainParametersFromName(str, this.configuration);
            if (domainParametersFromName == null) {
                throw new InvalidAlgorithmParameterException("unknown curve name: " + str);
            }
            this.ecParams = new ECNamedCurveSpec(str, domainParametersFromName.getCurve(), domainParametersFromName.getG(), domainParametersFromName.getN(), domainParametersFromName.getH(), null);
            this.param = createKeyGenParamsJCE(domainParametersFromName, secureRandom);
        }
    }

    public static class ECDSA extends EC {
        public ECDSA() {
            super("ECDSA", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECDH extends EC {
        public ECDH() {
            super("ECDH", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECDHC extends EC {
        public ECDHC() {
            super("ECDHC", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECMQV extends EC {
        public ECMQV() {
            super("ECMQV", BouncyCastleProvider.CONFIGURATION);
        }
    }
}

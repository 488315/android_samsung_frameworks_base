package com.android.internal.org.bouncycastle.jcajce.provider.symmetric;

import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.pkcs.PBKDF2Params;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey;
import com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE;
import com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import com.android.internal.org.bouncycastle.jcajce.spec.PBKDF2KeySpec;
import com.android.internal.org.bouncycastle.util.Integers;
import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/* loaded from: classes5.dex */
public class PBEPBKDF2 {
    private static final Map prfCodes;

    static {
        HashMap map = new HashMap();
        prfCodes = map;
        map.put(PKCSObjectIdentifiers.id_hmacWithSHA1, Integers.valueOf(1));
        map.put(PKCSObjectIdentifiers.id_hmacWithSHA256, Integers.valueOf(4));
        map.put(PKCSObjectIdentifiers.id_hmacWithSHA224, Integers.valueOf(7));
        map.put(PKCSObjectIdentifiers.id_hmacWithSHA384, Integers.valueOf(8));
        map.put(PKCSObjectIdentifiers.id_hmacWithSHA512, Integers.valueOf(9));
    }

    private PBEPBKDF2() {
    }

    public static class AlgParams extends BaseAlgorithmParameters {
        PBKDF2Params params;

        @Override // java.security.AlgorithmParametersSpi
        protected byte[] engineGetEncoded() {
            try {
                return this.params.getEncoded(ASN1Encoding.DER);
            } catch (IOException e) {
                throw new RuntimeException("Oooops! " + e.toString());
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        protected byte[] engineGetEncoded(String str) {
            if (isASN1FormatString(str)) {
                return engineGetEncoded();
            }
            return null;
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters
        protected AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) throws InvalidParameterSpecException {
            if (cls == PBEParameterSpec.class || cls == AlgorithmParameterSpec.class) {
                return new PBEParameterSpec(this.params.getSalt(), this.params.getIterationCount().intValue());
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to PBKDF2 PBE parameters object.");
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
            if (!(algorithmParameterSpec instanceof PBEParameterSpec)) {
                throw new InvalidParameterSpecException("PBEParameterSpec required to initialise a PBKDF2 PBE parameters algorithm parameters object");
            }
            PBEParameterSpec pBEParameterSpec = (PBEParameterSpec) algorithmParameterSpec;
            this.params = new PBKDF2Params(pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(byte[] bArr) throws IOException {
            this.params = PBKDF2Params.getInstance(ASN1Primitive.fromByteArray(bArr));
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(byte[] bArr, String str) throws IOException {
            if (isASN1FormatString(str)) {
                engineInit(bArr);
                return;
            }
            throw new IOException("Unknown parameters format in PBKDF2 parameters object");
        }

        @Override // java.security.AlgorithmParametersSpi
        protected String engineToString() {
            return "PBKDF2 Parameters";
        }
    }

    public static class BasePBKDF2 extends BaseSecretKeyFactory {
        private int defaultDigest;
        private int ivSizeInBits;
        private int keySizeInBits;
        private int scheme;

        public BasePBKDF2(String str, int i) {
            this(str, i, 1);
        }

        private BasePBKDF2(String str, int i, int i2, int i3, int i4) {
            super(str, PKCSObjectIdentifiers.id_PBKDF2);
            this.scheme = i;
            this.keySizeInBits = i3;
            this.ivSizeInBits = i4;
            this.defaultDigest = i2;
        }

        private BasePBKDF2(String str, int i, int i2) {
            this(str, i, i2, 0, 0);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory, javax.crypto.SecretKeyFactorySpi
        protected SecretKey engineGenerateSecret(KeySpec keySpec) throws InvalidKeySpecException {
            if (keySpec instanceof PBEKeySpec) {
                PBEKeySpec pBEKeySpec = (PBEKeySpec) keySpec;
                if (pBEKeySpec.getSalt() == null && pBEKeySpec.getIterationCount() == 0 && pBEKeySpec.getKeyLength() == 0 && pBEKeySpec.getPassword().length > 0 && this.keySizeInBits != 0) {
                    return new BCPBEKey(this.algName, this.algOid, this.scheme, this.defaultDigest, this.keySizeInBits, this.ivSizeInBits, pBEKeySpec, null);
                }
                if (pBEKeySpec.getSalt() == null) {
                    throw new InvalidKeySpecException("missing required salt");
                }
                if (pBEKeySpec.getIterationCount() <= 0) {
                    throw new InvalidKeySpecException("positive iteration count required: " + pBEKeySpec.getIterationCount());
                }
                if (pBEKeySpec.getKeyLength() <= 0) {
                    throw new InvalidKeySpecException("positive key length required: " + pBEKeySpec.getKeyLength());
                }
                if (pBEKeySpec.getPassword().length == 0) {
                    throw new IllegalArgumentException("password empty");
                }
                if (pBEKeySpec instanceof PBKDF2KeySpec) {
                    int digestCode = getDigestCode(((PBKDF2KeySpec) pBEKeySpec).getPrf().getAlgorithm());
                    int keyLength = pBEKeySpec.getKeyLength();
                    return new BCPBEKey(this.algName, this.algOid, this.scheme, digestCode, keyLength, -1, pBEKeySpec, PBE.Util.makePBEMacParameters(pBEKeySpec, this.scheme, digestCode, keyLength));
                }
                int i = this.defaultDigest;
                int keyLength2 = pBEKeySpec.getKeyLength();
                return new BCPBEKey(this.algName, this.algOid, this.scheme, i, keyLength2, -1, pBEKeySpec, PBE.Util.makePBEMacParameters(pBEKeySpec, this.scheme, i, keyLength2));
            }
            throw new InvalidKeySpecException("Invalid KeySpec");
        }

        private int getDigestCode(ASN1ObjectIdentifier aSN1ObjectIdentifier) throws InvalidKeySpecException {
            Integer num = (Integer) PBEPBKDF2.prfCodes.get(aSN1ObjectIdentifier);
            if (num != null) {
                return num.intValue();
            }
            throw new InvalidKeySpecException("Invalid KeySpec: unknown PRF algorithm " + aSN1ObjectIdentifier);
        }
    }

    public static class PBKDF2withUTF8 extends BasePBKDF2 {
        public PBKDF2withUTF8() {
            super("PBKDF2", 5);
        }
    }

    public static class BasePBKDF2WithHmacSHA1 extends BasePBKDF2 {
        public BasePBKDF2WithHmacSHA1(String str, int i) {
            super(str, i, 1);
        }
    }

    public static class PBKDF2WithHmacSHA1UTF8 extends BasePBKDF2WithHmacSHA1 {
        public PBKDF2WithHmacSHA1UTF8() {
            super("PBKDF2WithHmacSHA1", 5);
        }
    }

    public static class PBKDF2WithHmacSHA18BIT extends BasePBKDF2WithHmacSHA1 {
        public PBKDF2WithHmacSHA18BIT() {
            super("PBKDF2WithHmacSHA1And8bit", 1);
        }
    }

    public static class BasePBKDF2WithHmacSHA224 extends BasePBKDF2 {
        public BasePBKDF2WithHmacSHA224(String str, int i) {
            super(str, i, 7);
        }
    }

    public static class PBKDF2WithHmacSHA224UTF8 extends BasePBKDF2WithHmacSHA224 {
        public PBKDF2WithHmacSHA224UTF8() {
            super("PBKDF2WithHmacSHA224", 5);
        }
    }

    public static class BasePBKDF2WithHmacSHA256 extends BasePBKDF2 {
        public BasePBKDF2WithHmacSHA256(String str, int i) {
            super(str, i, 4);
        }
    }

    public static class PBKDF2WithHmacSHA256UTF8 extends BasePBKDF2WithHmacSHA256 {
        public PBKDF2WithHmacSHA256UTF8() {
            super("PBKDF2WithHmacSHA256", 5);
        }
    }

    public static class BasePBKDF2WithHmacSHA384 extends BasePBKDF2 {
        public BasePBKDF2WithHmacSHA384(String str, int i) {
            super(str, i, 8);
        }
    }

    public static class PBKDF2WithHmacSHA384UTF8 extends BasePBKDF2WithHmacSHA384 {
        public PBKDF2WithHmacSHA384UTF8() {
            super("PBKDF2WithHmacSHA384", 5);
        }
    }

    public static class BasePBKDF2WithHmacSHA512 extends BasePBKDF2 {
        public BasePBKDF2WithHmacSHA512(String str, int i) {
            super(str, i, 9);
        }
    }

    public static class PBKDF2WithHmacSHA512UTF8 extends BasePBKDF2WithHmacSHA512 {
        public PBKDF2WithHmacSHA512UTF8() {
            super("PBKDF2WithHmacSHA512", 5);
        }
    }

    public static class PBEWithHmacSHA1AndAES_128 extends BasePBKDF2 {
        public PBEWithHmacSHA1AndAES_128() {
            super("PBEWithHmacSHA1AndAES_128", 5, 1, 128, 128);
        }
    }

    public static class PBEWithHmacSHA224AndAES_128 extends BasePBKDF2 {
        public PBEWithHmacSHA224AndAES_128() {
            super("PBEWithHmacSHA224AndAES_128", 5, 7, 128, 128);
        }
    }

    public static class PBEWithHmacSHA256AndAES_128 extends BasePBKDF2 {
        public PBEWithHmacSHA256AndAES_128() {
            super("PBEWithHmacSHA256AndAES_128", 5, 4, 128, 128);
        }
    }

    public static class PBEWithHmacSHA384AndAES_128 extends BasePBKDF2 {
        public PBEWithHmacSHA384AndAES_128() {
            super("PBEWithHmacSHA384AndAES_128", 5, 8, 128, 128);
        }
    }

    public static class PBEWithHmacSHA512AndAES_128 extends BasePBKDF2 {
        public PBEWithHmacSHA512AndAES_128() {
            super("PBEWithHmacSHA512AndAES_128", 5, 9, 128, 128);
        }
    }

    public static class PBEWithHmacSHA1AndAES_256 extends BasePBKDF2 {
        public PBEWithHmacSHA1AndAES_256() {
            super("PBEWithHmacSHA1AndAES_256", 5, 1, 256, 128);
        }
    }

    public static class PBEWithHmacSHA224AndAES_256 extends BasePBKDF2 {
        public PBEWithHmacSHA224AndAES_256() {
            super("PBEWithHmacSHA224AndAES_256", 5, 7, 256, 128);
        }
    }

    public static class PBEWithHmacSHA256AndAES_256 extends BasePBKDF2 {
        public PBEWithHmacSHA256AndAES_256() {
            super("PBEWithHmacSHA256AndAES_256", 5, 4, 256, 128);
        }
    }

    public static class PBEWithHmacSHA384AndAES_256 extends BasePBKDF2 {
        public PBEWithHmacSHA384AndAES_256() {
            super("PBEWithHmacSHA384AndAES_256", 5, 8, 256, 128);
        }
    }

    public static class PBEWithHmacSHA512AndAES_256 extends BasePBKDF2 {
        public PBEWithHmacSHA512AndAES_256() {
            super("PBEWithHmacSHA512AndAES_256", 5, 9, 256, 128);
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String PREFIX = PBEPBKDF2.class.getName();

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2WithHmacSHA1AndUTF8", "PBKDF2WithHmacSHA1");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2with8BIT", "PBKDF2WithHmacSHA1And8BIT");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2withASCII", "PBKDF2WithHmacSHA1And8BIT");
            StringBuilder sb = new StringBuilder();
            String str = PREFIX;
            sb.append(str);
            sb.append("$PBKDF2WithHmacSHA1UTF8");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA1", sb.toString());
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA224", str + "$PBKDF2WithHmacSHA224UTF8");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA256", str + "$PBKDF2WithHmacSHA256UTF8");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA384", str + "$PBKDF2WithHmacSHA384UTF8");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA512", str + "$PBKDF2WithHmacSHA512UTF8");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA1AndAES_128", str + "$PBEWithHmacSHA1AndAES_128");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA224AndAES_128", str + "$PBEWithHmacSHA224AndAES_128");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA256AndAES_128", str + "$PBEWithHmacSHA256AndAES_128");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA384AndAES_128", str + "$PBEWithHmacSHA384AndAES_128");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA512AndAES_128", str + "$PBEWithHmacSHA512AndAES_128");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA1AndAES_256", str + "$PBEWithHmacSHA1AndAES_256");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA224AndAES_256", str + "$PBEWithHmacSHA224AndAES_256");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA256AndAES_256", str + "$PBEWithHmacSHA256AndAES_256");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA384AndAES_256", str + "$PBEWithHmacSHA384AndAES_256");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA512AndAES_256", str + "$PBEWithHmacSHA512AndAES_256");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA1And8BIT", str + "$PBKDF2WithHmacSHA18BIT");
            configurableProvider.addPrivateAlgorithm("SecretKeyFactory.PBKDF2", str + "$PBKDF2withUTF8");
            configurableProvider.addPrivateAlgorithm("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.5.12", "PBKDF2");
        }
    }
}

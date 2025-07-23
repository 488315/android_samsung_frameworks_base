package android.security.keystore;

import android.annotation.SystemApi;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
public abstract class KeyProperties {
    public static final int AUTH_BIOMETRIC_STRONG = 2;
    public static final int AUTH_DEVICE_CREDENTIAL = 1;
    public static final String BLOCK_MODE_CBC = "CBC";
    public static final String BLOCK_MODE_CTR = "CTR";
    public static final String BLOCK_MODE_ECB = "ECB";
    public static final String BLOCK_MODE_GCM = "GCM";
    public static final String DIGEST_MD5 = "MD5";
    public static final String DIGEST_NONE = "NONE";
    public static final String DIGEST_SHA1 = "SHA-1";
    public static final String DIGEST_SHA224 = "SHA-224";
    public static final String DIGEST_SHA256 = "SHA-256";
    public static final String DIGEST_SHA384 = "SHA-384";
    public static final String DIGEST_SHA512 = "SHA-512";
    public static final String ENCRYPTION_PADDING_NONE = "NoPadding";
    public static final String ENCRYPTION_PADDING_PKCS7 = "PKCS7Padding";
    public static final String ENCRYPTION_PADDING_RSA_OAEP = "OAEPPadding";
    public static final String ENCRYPTION_PADDING_RSA_PKCS1 = "PKCS1Padding";

    @Deprecated
    public static final String KEY_ALGORITHM_3DES = "DESede";
    public static final String KEY_ALGORITHM_AES = "AES";
    public static final String KEY_ALGORITHM_EC = "EC";
    public static final String KEY_ALGORITHM_HMAC_SHA1 = "HmacSHA1";
    public static final String KEY_ALGORITHM_HMAC_SHA224 = "HmacSHA224";
    public static final String KEY_ALGORITHM_HMAC_SHA256 = "HmacSHA256";
    public static final String KEY_ALGORITHM_HMAC_SHA384 = "HmacSHA384";
    public static final String KEY_ALGORITHM_HMAC_SHA512 = "HmacSHA512";
    public static final String KEY_ALGORITHM_RSA = "RSA";
    public static final String KEY_ALGORITHM_XDH = "XDH";

    @SystemApi
    public static final int NAMESPACE_APPLICATION = -1;
    public static final int NAMESPACE_LOCKSETTINGS = 103;

    @SystemApi
    public static final int NAMESPACE_WIFI = 102;
    public static final int ORIGIN_GENERATED = 1;
    public static final int ORIGIN_IMPORTED = 2;
    public static final int ORIGIN_SECURELY_IMPORTED = 8;
    public static final int ORIGIN_UNKNOWN = 4;
    public static final int PURPOSE_AGREE_KEY = 64;
    public static final int PURPOSE_ATTEST_KEY = 128;
    public static final int PURPOSE_DECRYPT = 2;
    public static final int PURPOSE_ENCRYPT = 1;
    public static final int PURPOSE_SIGN = 4;
    public static final int PURPOSE_VERIFY = 8;
    public static final int PURPOSE_WRAP_KEY = 32;
    public static final int SECURITY_LEVEL_SOFTWARE = 0;
    public static final int SECURITY_LEVEL_STRONGBOX = 2;
    public static final int SECURITY_LEVEL_TRUSTED_ENVIRONMENT = 1;
    public static final int SECURITY_LEVEL_UNKNOWN = -2;
    public static final int SECURITY_LEVEL_UNKNOWN_SECURE = -1;
    public static final String SIGNATURE_PADDING_RSA_PKCS1 = "PKCS1";
    public static final String SIGNATURE_PADDING_RSA_PSS = "PSS";
    public static final int UID_SELF = -1;
    public static final int UNRESTRICTED_USAGE_COUNT = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthEnum {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BlockModeEnum {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DigestEnum {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EncryptionPaddingEnum {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyAlgorithmEnum {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Namespace {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OriginEnum {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PurposeEnum {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SecurityLevelEnum {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SignaturePaddingEnum {
    }

    private static int getSetBitCount(int i) {
        int i2 = 0;
        if (i == 0) {
            return 0;
        }
        while (i != 0) {
            if ((i & 1) != 0) {
                i2++;
            }
            i >>>= 1;
        }
        return i2;
    }

    private KeyProperties() {
    }

    public static abstract class Purpose {
        private Purpose() {
        }

        public static int toKeymaster(int i) {
            if (i == 1) {
                return 0;
            }
            if (i == 2) {
                return 1;
            }
            if (i == 4) {
                return 2;
            }
            if (i == 8) {
                return 3;
            }
            if (i == 32) {
                return 5;
            }
            if (i == 64) {
                return 6;
            }
            if (i == 128) {
                return 7;
            }
            throw new IllegalArgumentException("Unknown purpose: " + i);
        }

        public static int fromKeymaster(int i) {
            if (i == 0) {
                return 1;
            }
            if (i == 1) {
                return 2;
            }
            if (i == 2) {
                return 4;
            }
            if (i == 3) {
                return 8;
            }
            if (i == 5) {
                return 32;
            }
            if (i == 6) {
                return 64;
            }
            if (i == 7) {
                return 128;
            }
            throw new IllegalArgumentException("Unknown purpose: " + i);
        }

        public static int[] allToKeymaster(int i) {
            int[] setFlags = KeyProperties.getSetFlags(i);
            for (int i2 = 0; i2 < setFlags.length; i2++) {
                setFlags[i2] = toKeymaster(setFlags[i2]);
            }
            return setFlags;
        }

        public static int allFromKeymaster(Collection<Integer> collection) {
            Iterator<Integer> it = collection.iterator();
            int i = 0;
            while (it.hasNext()) {
                i |= fromKeymaster(it.next().intValue());
            }
            return i;
        }
    }

    public static abstract class KeyAlgorithm {
        private KeyAlgorithm() {
        }

        public static int toKeymasterAsymmetricKeyAlgorithm(String str) {
            if (KeyProperties.KEY_ALGORITHM_EC.equalsIgnoreCase(str) || KeyProperties.KEY_ALGORITHM_XDH.equalsIgnoreCase(str)) {
                return 3;
            }
            if ("RSA".equalsIgnoreCase(str)) {
                return 1;
            }
            throw new IllegalArgumentException("Unsupported key algorithm: " + str);
        }

        public static String fromKeymasterAsymmetricKeyAlgorithm(int i) {
            if (i == 1) {
                return "RSA";
            }
            if (i == 3) {
                return KeyProperties.KEY_ALGORITHM_EC;
            }
            throw new IllegalArgumentException("Unsupported key algorithm: " + i);
        }

        public static int toKeymasterSecretKeyAlgorithm(String str) {
            if ("AES".equalsIgnoreCase(str)) {
                return 32;
            }
            if (KeyProperties.KEY_ALGORITHM_3DES.equalsIgnoreCase(str)) {
                return 33;
            }
            if (str.toUpperCase(Locale.US).startsWith("HMAC")) {
                return 128;
            }
            throw new IllegalArgumentException("Unsupported secret key algorithm: " + str);
        }

        public static String fromKeymasterSecretKeyAlgorithm(int i, int i2) {
            if (i == 32) {
                return "AES";
            }
            if (i == 33) {
                return KeyProperties.KEY_ALGORITHM_3DES;
            }
            if (i != 128) {
                throw new IllegalArgumentException("Unsupported key algorithm: " + i);
            }
            if (i2 == 2) {
                return KeyProperties.KEY_ALGORITHM_HMAC_SHA1;
            }
            if (i2 == 3) {
                return KeyProperties.KEY_ALGORITHM_HMAC_SHA224;
            }
            if (i2 == 4) {
                return KeyProperties.KEY_ALGORITHM_HMAC_SHA256;
            }
            if (i2 == 5) {
                return KeyProperties.KEY_ALGORITHM_HMAC_SHA384;
            }
            if (i2 == 6) {
                return KeyProperties.KEY_ALGORITHM_HMAC_SHA512;
            }
            throw new IllegalArgumentException("Unsupported HMAC digest: " + Digest.fromKeymaster(i2));
        }

        public static int toKeymasterDigest(String str) {
            String substring;
            String upperCase = str.toUpperCase(Locale.US);
            if (!upperCase.startsWith("HMAC")) {
                return -1;
            }
            substring = upperCase.substring(4);
            substring.hashCode();
            switch (substring) {
                case "SHA224":
                    return 3;
                case "SHA256":
                    return 4;
                case "SHA384":
                    return 5;
                case "SHA512":
                    return 6;
                case "SHA1":
                    return 2;
                default:
                    throw new IllegalArgumentException("Unsupported HMAC digest: " + substring);
            }
        }
    }

    public static abstract class BlockMode {
        private BlockMode() {
        }

        public static int toKeymaster(String str) {
            if (KeyProperties.BLOCK_MODE_ECB.equalsIgnoreCase(str)) {
                return 1;
            }
            if (KeyProperties.BLOCK_MODE_CBC.equalsIgnoreCase(str)) {
                return 2;
            }
            if (KeyProperties.BLOCK_MODE_CTR.equalsIgnoreCase(str)) {
                return 3;
            }
            if ("GCM".equalsIgnoreCase(str)) {
                return 32;
            }
            throw new IllegalArgumentException("Unsupported block mode: " + str);
        }

        public static String fromKeymaster(int i) {
            if (i == 1) {
                return KeyProperties.BLOCK_MODE_ECB;
            }
            if (i == 2) {
                return KeyProperties.BLOCK_MODE_CBC;
            }
            if (i == 3) {
                return KeyProperties.BLOCK_MODE_CTR;
            }
            if (i == 32) {
                return "GCM";
            }
            throw new IllegalArgumentException("Unsupported block mode: " + i);
        }

        public static String[] allFromKeymaster(Collection<Integer> collection) {
            if (collection == null || collection.isEmpty()) {
                return EmptyArray.STRING;
            }
            String[] strArr = new String[collection.size()];
            Iterator<Integer> it = collection.iterator();
            int i = 0;
            while (it.hasNext()) {
                strArr[i] = fromKeymaster(it.next().intValue());
                i++;
            }
            return strArr;
        }

        public static int[] allToKeymaster(String[] strArr) {
            if (strArr == null || strArr.length == 0) {
                return EmptyArray.INT;
            }
            int[] iArr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                iArr[i] = toKeymaster(strArr[i]);
            }
            return iArr;
        }
    }

    public static abstract class EncryptionPadding {
        private EncryptionPadding() {
        }

        public static int toKeymaster(String str) {
            if ("NoPadding".equalsIgnoreCase(str)) {
                return 1;
            }
            if (KeyProperties.ENCRYPTION_PADDING_PKCS7.equalsIgnoreCase(str)) {
                return 64;
            }
            if (KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1.equalsIgnoreCase(str)) {
                return 4;
            }
            if (KeyProperties.ENCRYPTION_PADDING_RSA_OAEP.equalsIgnoreCase(str)) {
                return 2;
            }
            throw new IllegalArgumentException("Unsupported encryption padding scheme: " + str);
        }

        public static String fromKeymaster(int i) {
            if (i == 1) {
                return "NoPadding";
            }
            if (i == 2) {
                return KeyProperties.ENCRYPTION_PADDING_RSA_OAEP;
            }
            if (i == 4) {
                return KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1;
            }
            if (i == 64) {
                return KeyProperties.ENCRYPTION_PADDING_PKCS7;
            }
            throw new IllegalArgumentException("Unsupported encryption padding: " + i);
        }

        public static int[] allToKeymaster(String[] strArr) {
            if (strArr == null || strArr.length == 0) {
                return EmptyArray.INT;
            }
            int[] iArr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                iArr[i] = toKeymaster(strArr[i]);
            }
            return iArr;
        }
    }

    public static abstract class SignaturePadding {
        private SignaturePadding() {
        }

        public static int toKeymaster(String str) {
            String upperCase = str.toUpperCase(Locale.US);
            upperCase.hashCode();
            if (upperCase.equals(KeyProperties.SIGNATURE_PADDING_RSA_PSS)) {
                return 3;
            }
            if (upperCase.equals(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)) {
                return 5;
            }
            throw new IllegalArgumentException("Unsupported signature padding scheme: " + str);
        }

        public static String fromKeymaster(int i) {
            if (i == 3) {
                return KeyProperties.SIGNATURE_PADDING_RSA_PSS;
            }
            if (i == 5) {
                return KeyProperties.SIGNATURE_PADDING_RSA_PKCS1;
            }
            throw new IllegalArgumentException("Unsupported signature padding: " + i);
        }

        public static int[] allToKeymaster(String[] strArr) {
            if (strArr == null || strArr.length == 0) {
                return EmptyArray.INT;
            }
            int[] iArr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                iArr[i] = toKeymaster(strArr[i]);
            }
            return iArr;
        }
    }

    public static abstract class Digest {
        private Digest() {
        }

        public static int toKeymaster(String str) {
            String upperCase = str.toUpperCase(Locale.US);
            upperCase.hashCode();
            switch (upperCase) {
                case "SHA-224":
                    return 3;
                case "SHA-256":
                    return 4;
                case "SHA-384":
                    return 5;
                case "SHA-512":
                    return 6;
                case "MD5":
                    return 1;
                case "NONE":
                    return 0;
                case "SHA-1":
                    return 2;
                default:
                    throw new IllegalArgumentException("Unsupported digest algorithm: " + str);
            }
        }

        public static String fromKeymaster(int i) {
            switch (i) {
                case 0:
                    return KeyProperties.DIGEST_NONE;
                case 1:
                    return KeyProperties.DIGEST_MD5;
                case 2:
                    return "SHA-1";
                case 3:
                    return KeyProperties.DIGEST_SHA224;
                case 4:
                    return "SHA-256";
                case 5:
                    return KeyProperties.DIGEST_SHA384;
                case 6:
                    return KeyProperties.DIGEST_SHA512;
                default:
                    throw new IllegalArgumentException("Unsupported digest algorithm: " + i);
            }
        }

        public static AlgorithmParameterSpec fromKeymasterToMGF1ParameterSpec(int i) {
            if (i == 3) {
                return MGF1ParameterSpec.SHA224;
            }
            if (i == 4) {
                return MGF1ParameterSpec.SHA256;
            }
            if (i == 5) {
                return MGF1ParameterSpec.SHA384;
            }
            if (i != 6) {
                return MGF1ParameterSpec.SHA1;
            }
            return MGF1ParameterSpec.SHA512;
        }

        public static String fromKeymasterToSignatureAlgorithmDigest(int i) {
            switch (i) {
                case 0:
                    return KeyProperties.DIGEST_NONE;
                case 1:
                    return KeyProperties.DIGEST_MD5;
                case 2:
                    return "SHA1";
                case 3:
                    return "SHA224";
                case 4:
                    return "SHA256";
                case 5:
                    return "SHA384";
                case 6:
                    return "SHA512";
                default:
                    throw new IllegalArgumentException("Unsupported digest algorithm: " + i);
            }
        }

        public static String[] allFromKeymaster(Collection<Integer> collection) {
            if (collection.isEmpty()) {
                return EmptyArray.STRING;
            }
            String[] strArr = new String[collection.size()];
            Iterator<Integer> it = collection.iterator();
            int i = 0;
            while (it.hasNext()) {
                strArr[i] = fromKeymaster(it.next().intValue());
                i++;
            }
            return strArr;
        }

        public static int[] allToKeymaster(String[] strArr) {
            if (strArr == null || strArr.length == 0) {
                return EmptyArray.INT;
            }
            int[] iArr = new int[strArr.length];
            int i = 0;
            for (String str : strArr) {
                iArr[i] = toKeymaster(str);
                i++;
            }
            return iArr;
        }
    }

    public static abstract class Origin {
        private Origin() {
        }

        public static int fromKeymaster(int i) {
            if (i == 0) {
                return 1;
            }
            if (i == 2) {
                return 2;
            }
            if (i == 3) {
                return 4;
            }
            if (i == 4) {
                return 8;
            }
            throw new IllegalArgumentException("Unknown origin: " + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int[] getSetFlags(int i) {
        if (i == 0) {
            return EmptyArray.INT;
        }
        int[] iArr = new int[getSetBitCount(i)];
        int i2 = 1;
        int i3 = 0;
        while (i != 0) {
            if ((i & 1) != 0) {
                iArr[i3] = i2;
                i3++;
            }
            i >>>= 1;
            i2 <<= 1;
        }
        return iArr;
    }

    public static abstract class SecurityLevel {
        private SecurityLevel() {
        }

        public static int toKeymaster(int i) {
            if (i == 0) {
                return 0;
            }
            if (i == 1) {
                return 1;
            }
            if (i == 2) {
                return 2;
            }
            throw new IllegalArgumentException("Unsupported security level: " + i);
        }

        public static int fromKeymaster(int i) {
            if (i == 0) {
                return 0;
            }
            if (i == 1) {
                return 1;
            }
            if (i == 2) {
                return 2;
            }
            throw new IllegalArgumentException("Unsupported security level: " + i);
        }
    }

    public static abstract class EcCurve {
        public static int fromKeymasterCurve(int i) {
            if (i == 0) {
                return 224;
            }
            if (i == 1) {
                return 256;
            }
            if (i == 2) {
                return 384;
            }
            if (i != 3) {
                return i != 4 ? -1 : 256;
            }
            return 521;
        }

        private EcCurve() {
        }

        public static int toKeymasterCurve(ECParameterSpec eCParameterSpec) {
            int fieldSize = eCParameterSpec.getCurve().getField().getFieldSize();
            if (fieldSize == 224) {
                return 0;
            }
            if (fieldSize == 256) {
                return 1;
            }
            if (fieldSize != 384) {
                return fieldSize != 521 ? -1 : 3;
            }
            return 2;
        }
    }

    public static int namespaceToLegacyUid(int i) {
        if (i == -1) {
            return -1;
        }
        if (i == 102) {
            return 1010;
        }
        throw new IllegalArgumentException("No UID corresponding to namespace " + i);
    }

    public static int legacyUidToNamespace(int i) {
        if (i == -1) {
            return -1;
        }
        if (i == 1010) {
            return 102;
        }
        throw new IllegalArgumentException("No namespace corresponding to uid " + i);
    }
}

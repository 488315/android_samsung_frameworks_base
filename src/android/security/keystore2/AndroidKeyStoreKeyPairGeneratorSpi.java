package android.security.keystore2;

import android.app.AppGlobals;
import android.hardware.security.keymint.KeyParameter;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.KeyStore2;
import android.security.KeyStoreException;
import android.security.keymaster.KeymasterArguments;
import android.security.keystore.ArrayUtils;
import android.security.keystore.DeviceIdAttestationException;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.system.keystore2.Authorization;
import android.system.keystore2.KeyDescriptor;
import android.system.keystore2.KeyEntryResponse;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGeneratorSpi;
import java.security.ProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.NamedParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
public abstract class AndroidKeyStoreKeyPairGeneratorSpi extends KeyPairGeneratorSpi {
    private static final int ALGORITHM_ED25519 = 1204;
    private static final int ALGORITHM_XDH = 1203;
    private static final String CURVE_ED_25519;
    private static final String CURVE_X_25519;
    private static final int EC_DEFAULT_KEY_SIZE = 256;
    private static final int RSA_DEFAULT_KEY_SIZE = 2048;
    private static final int RSA_MAX_KEY_SIZE = 8192;
    private static final int RSA_MIN_KEY_SIZE = 512;
    private static final List<String> SUPPORTED_EC_CURVE_NAMES;
    private static final Map<String, Integer> SUPPORTED_EC_CURVE_NAME_TO_SIZE;
    private static final List<Integer> SUPPORTED_EC_CURVE_SIZES;
    private static final String TAG = "AndroidKeyStoreKeyPairGeneratorSpi";
    private KeyDescriptor mAttestKeyDescriptor;
    private String mEcCurveName;
    private String mEntryAlias;
    private int mEntryNamespace;
    private String mJcaKeyAlgorithm;
    private int mKeySizeBits;
    private KeyStore2 mKeyStore;
    private int mKeymasterAlgorithm = -1;
    private int[] mKeymasterBlockModes;
    private int[] mKeymasterDigests;
    private int[] mKeymasterEncryptionPaddings;
    private int[] mKeymasterMgf1Digests;
    private int[] mKeymasterPurposes;
    private int[] mKeymasterSignaturePaddings;
    private final int mOriginalKeymasterAlgorithm;
    private Long mRSAPublicExponent;
    private SecureRandom mRng;
    private KeyGenParameterSpec mSpec;

    private static boolean hasOnlyAllowedPurposeForEd25519(int i) {
        return ((i & 140) != 0) && !((i & (-141)) != 0);
    }

    public static class RSA extends AndroidKeyStoreKeyPairGeneratorSpi {
        public RSA() {
            super(1);
        }
    }

    public static class EC extends AndroidKeyStoreKeyPairGeneratorSpi {
        public EC() {
            super(3);
        }
    }

    public static class XDH extends AndroidKeyStoreKeyPairGeneratorSpi {
        public XDH() {
            super(1203);
        }
    }

    public static class ED25519 extends AndroidKeyStoreKeyPairGeneratorSpi {
        public ED25519() {
            super(1204);
        }
    }

    static {
        HashMap hashMap = new HashMap();
        SUPPORTED_EC_CURVE_NAME_TO_SIZE = hashMap;
        ArrayList arrayList = new ArrayList();
        SUPPORTED_EC_CURVE_NAMES = arrayList;
        ArrayList arrayList2 = new ArrayList();
        SUPPORTED_EC_CURVE_SIZES = arrayList2;
        String name = NamedParameterSpec.X25519.getName();
        CURVE_X_25519 = name;
        String name2 = NamedParameterSpec.ED25519.getName();
        CURVE_ED_25519 = name2;
        hashMap.put("p-224", 224);
        hashMap.put("secp224r1", 224);
        hashMap.put("p-256", 256);
        hashMap.put("secp256r1", 256);
        hashMap.put("prime256v1", 256);
        hashMap.put(name.toLowerCase(Locale.US), 256);
        hashMap.put(name2.toLowerCase(Locale.US), 256);
        hashMap.put("p-384", 384);
        hashMap.put("secp384r1", 384);
        hashMap.put("p-521", 521);
        hashMap.put("secp521r1", 521);
        arrayList.addAll(hashMap.keySet());
        Collections.sort(arrayList);
        arrayList2.addAll(new HashSet(hashMap.values()));
        Collections.sort(arrayList2);
    }

    protected AndroidKeyStoreKeyPairGeneratorSpi(int i) {
        this.mOriginalKeymasterAlgorithm = i;
    }

    private static int keySizeAndNameToEcCurve(int i, String str) throws InvalidAlgorithmParameterException {
        if (i == 224) {
            return 0;
        }
        if (i == 256) {
            return isCurve25519(str) ? 4 : 1;
        }
        if (i == 384) {
            return 2;
        }
        if (i == 521) {
            return 3;
        }
        throw new InvalidAlgorithmParameterException("Unsupported EC curve keysize: " + i);
    }

    @Override // java.security.KeyPairGeneratorSpi
    public void initialize(int i, SecureRandom secureRandom) {
        throw new IllegalArgumentException(KeyGenParameterSpec.class.getName() + " or " + KeyPairGeneratorSpec.class.getName() + " required to initialize this KeyPairGenerator");
    }

    @Override // java.security.KeyPairGeneratorSpi
    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        KeyGenParameterSpec buildKeyGenParameterSpecFromLegacy;
        resetAll();
        try {
            if (algorithmParameterSpec == null) {
                throw new InvalidAlgorithmParameterException("Must supply params of type " + KeyGenParameterSpec.class.getName() + " or " + KeyPairGeneratorSpec.class.getName());
            }
            int i = this.mOriginalKeymasterAlgorithm;
            if (i == 1203 || i == 1204) {
                i = 3;
            }
            if (algorithmParameterSpec instanceof KeyGenParameterSpec) {
                buildKeyGenParameterSpecFromLegacy = (KeyGenParameterSpec) algorithmParameterSpec;
            } else if (algorithmParameterSpec instanceof KeyPairGeneratorSpec) {
                KeyPairGeneratorSpec keyPairGeneratorSpec = (KeyPairGeneratorSpec) algorithmParameterSpec;
                try {
                    i = getKeymasterAlgorithmFromLegacy(i, keyPairGeneratorSpec);
                    buildKeyGenParameterSpecFromLegacy = buildKeyGenParameterSpecFromLegacy(keyPairGeneratorSpec, i);
                } catch (IllegalArgumentException | NullPointerException e) {
                    throw new InvalidAlgorithmParameterException(e);
                }
            } else {
                if (algorithmParameterSpec instanceof NamedParameterSpec) {
                    NamedParameterSpec namedParameterSpec = (NamedParameterSpec) algorithmParameterSpec;
                    if (!namedParameterSpec.getName().equalsIgnoreCase(NamedParameterSpec.X25519.getName()) && !namedParameterSpec.getName().equalsIgnoreCase(NamedParameterSpec.ED25519.getName())) {
                        throw new InvalidAlgorithmParameterException("Unsupported algorithm specified via NamedParameterSpec: " + namedParameterSpec.getName());
                    }
                    throw new IllegalArgumentException("This KeyPairGenerator cannot be initialized using NamedParameterSpec. use " + KeyGenParameterSpec.class.getName() + " or " + KeyPairGeneratorSpec.class.getName());
                }
                throw new InvalidAlgorithmParameterException("Unsupported params class: " + algorithmParameterSpec.getClass().getName() + ". Supported: " + KeyGenParameterSpec.class.getName() + ", " + KeyPairGeneratorSpec.class.getName());
            }
            this.mEntryAlias = buildKeyGenParameterSpecFromLegacy.getKeystoreAlias();
            this.mEntryNamespace = buildKeyGenParameterSpecFromLegacy.getNamespace();
            this.mSpec = buildKeyGenParameterSpecFromLegacy;
            this.mKeymasterAlgorithm = i;
            this.mKeySizeBits = buildKeyGenParameterSpecFromLegacy.getKeySize();
            initAlgorithmSpecificParameters();
            if (this.mKeySizeBits == -1) {
                this.mKeySizeBits = getDefaultKeySize(i);
            }
            checkValidKeySize(i, this.mKeySizeBits, this.mSpec.isStrongBoxBacked(), this.mEcCurveName);
            if (buildKeyGenParameterSpecFromLegacy.getKeystoreAlias() == null) {
                throw new InvalidAlgorithmParameterException("KeyStore entry alias not provided");
            }
            try {
                String fromKeymasterAsymmetricKeyAlgorithm = KeyProperties.KeyAlgorithm.fromKeymasterAsymmetricKeyAlgorithm(i);
                this.mKeymasterPurposes = KeyProperties.Purpose.allToKeymaster(buildKeyGenParameterSpecFromLegacy.getPurposes());
                this.mKeymasterBlockModes = KeyProperties.BlockMode.allToKeymaster(buildKeyGenParameterSpecFromLegacy.getBlockModes());
                this.mKeymasterEncryptionPaddings = KeyProperties.EncryptionPadding.allToKeymaster(buildKeyGenParameterSpecFromLegacy.getEncryptionPaddings());
                int i2 = 0;
                if ((buildKeyGenParameterSpecFromLegacy.getPurposes() & 1) != 0 && buildKeyGenParameterSpecFromLegacy.isRandomizedEncryptionRequired()) {
                    for (int i3 : this.mKeymasterEncryptionPaddings) {
                        if (!KeymasterUtils.isKeymasterPaddingSchemeIndCpaCompatibleWithAsymmetricCrypto(i3)) {
                            throw new InvalidAlgorithmParameterException("Randomized encryption (IND-CPA) required but may be violated by padding scheme: " + KeyProperties.EncryptionPadding.fromKeymaster(i3) + ". See " + KeyGenParameterSpec.class.getName() + " documentation.");
                        }
                    }
                }
                this.mKeymasterSignaturePaddings = KeyProperties.SignaturePadding.allToKeymaster(buildKeyGenParameterSpecFromLegacy.getSignaturePaddings());
                if (buildKeyGenParameterSpecFromLegacy.isDigestsSpecified()) {
                    this.mKeymasterDigests = KeyProperties.Digest.allToKeymaster(buildKeyGenParameterSpecFromLegacy.getDigests());
                } else {
                    this.mKeymasterDigests = EmptyArray.INT;
                }
                if (buildKeyGenParameterSpecFromLegacy.isMgf1DigestsSpecified()) {
                    Set<String> mgf1Digests = buildKeyGenParameterSpecFromLegacy.getMgf1Digests();
                    this.mKeymasterMgf1Digests = new int[mgf1Digests.size()];
                    Iterator<String> it = mgf1Digests.iterator();
                    while (it.hasNext()) {
                        this.mKeymasterMgf1Digests[i2] = KeyProperties.Digest.toKeymaster(it.next());
                        i2++;
                    }
                } else {
                    this.mKeymasterMgf1Digests = new int[]{KeyProperties.Digest.toKeymaster("SHA-1")};
                }
                KeyStore2ParameterUtils.addUserAuthArgs(new ArrayList(), this.mSpec);
                this.mJcaKeyAlgorithm = fromKeymasterAsymmetricKeyAlgorithm;
                this.mRng = secureRandom;
                this.mKeyStore = KeyStore2.getInstance();
                this.mAttestKeyDescriptor = buildAndCheckAttestKeyDescriptor(buildKeyGenParameterSpecFromLegacy);
                checkAttestKeyPurpose(buildKeyGenParameterSpecFromLegacy);
                checkCorrectKeyPurposeForCurve(buildKeyGenParameterSpecFromLegacy);
            } catch (IllegalArgumentException | IllegalStateException e2) {
                throw new InvalidAlgorithmParameterException(e2);
            }
        } catch (Throwable th) {
            resetAll();
            throw th;
        }
    }

    private void checkAttestKeyPurpose(KeyGenParameterSpec keyGenParameterSpec) throws InvalidAlgorithmParameterException {
        if ((keyGenParameterSpec.getPurposes() & 128) != 0 && keyGenParameterSpec.getPurposes() != 128) {
            throw new InvalidAlgorithmParameterException("PURPOSE_ATTEST_KEY may not be specified with any other purposes");
        }
    }

    private void checkCorrectKeyPurposeForCurve(KeyGenParameterSpec keyGenParameterSpec) throws InvalidAlgorithmParameterException {
        if (isCurve25519(this.mEcCurveName)) {
            if (this.mEcCurveName.equalsIgnoreCase(CURVE_X_25519) && keyGenParameterSpec.getPurposes() != 64) {
                throw new InvalidAlgorithmParameterException("x25519 may only be used for key agreement.");
            }
            if (this.mEcCurveName.equalsIgnoreCase(CURVE_ED_25519) && !hasOnlyAllowedPurposeForEd25519(keyGenParameterSpec.getPurposes())) {
                throw new InvalidAlgorithmParameterException("ed25519 may not be used for key agreement.");
            }
        }
    }

    private static boolean isCurve25519(String str) {
        if (str == null) {
            return false;
        }
        return str.equalsIgnoreCase(CURVE_X_25519) || str.equalsIgnoreCase(CURVE_ED_25519);
    }

    private KeyDescriptor buildAndCheckAttestKeyDescriptor(KeyGenParameterSpec keyGenParameterSpec) throws InvalidAlgorithmParameterException {
        if (keyGenParameterSpec.getAttestKeyAlias() == null) {
            return null;
        }
        KeyDescriptor keyDescriptor = new KeyDescriptor();
        keyDescriptor.domain = 0;
        keyDescriptor.alias = keyGenParameterSpec.getAttestKeyAlias();
        try {
            KeyEntryResponse keyEntry = this.mKeyStore.getKeyEntry(keyDescriptor);
            checkAttestKeyChallenge(keyGenParameterSpec);
            checkAttestKeyPurpose(keyEntry.metadata.authorizations);
            checkAttestKeySecurityLevel(keyGenParameterSpec, keyEntry);
            return keyDescriptor;
        } catch (KeyStoreException e) {
            throw new InvalidAlgorithmParameterException("Invalid attestKeyAlias", e);
        }
    }

    private void checkAttestKeyChallenge(KeyGenParameterSpec keyGenParameterSpec) throws InvalidAlgorithmParameterException {
        if (keyGenParameterSpec.getAttestationChallenge() == null) {
            throw new InvalidAlgorithmParameterException("AttestKey specified but no attestation challenge provided");
        }
    }

    private void checkAttestKeyPurpose(Authorization[] authorizationArr) throws InvalidAlgorithmParameterException {
        if (Arrays.stream(authorizationArr).noneMatch(new Predicate() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AndroidKeyStoreKeyPairGeneratorSpi.lambda$checkAttestKeyPurpose$0((Authorization) obj);
            }
        })) {
            throw new InvalidAlgorithmParameterException("Invalid attestKey, does not have PURPOSE_ATTEST_KEY");
        }
    }

    static /* synthetic */ boolean lambda$checkAttestKeyPurpose$0(Authorization authorization) {
        return authorization.keyParameter.tag == 536870913 && authorization.keyParameter.value.getKeyPurpose() == 7;
    }

    private void checkAttestKeySecurityLevel(KeyGenParameterSpec keyGenParameterSpec, KeyEntryResponse keyEntryResponse) throws InvalidAlgorithmParameterException {
        boolean z = keyEntryResponse.metadata.keySecurityLevel == 2;
        if (keyGenParameterSpec.isStrongBoxBacked() != z) {
            if (z) {
                throw new InvalidAlgorithmParameterException("Invalid security level: Cannot sign non-StrongBox key with StrongBox attestKey");
            }
            throw new InvalidAlgorithmParameterException("Invalid security level: Cannot sign StrongBox key with non-StrongBox attestKey");
        }
    }

    private int getKeymasterAlgorithmFromLegacy(int i, KeyPairGeneratorSpec keyPairGeneratorSpec) throws InvalidAlgorithmParameterException {
        String keyType = keyPairGeneratorSpec.getKeyType();
        if (keyType == null) {
            return i;
        }
        try {
            return KeyProperties.KeyAlgorithm.toKeymasterAsymmetricKeyAlgorithm(keyType);
        } catch (IllegalArgumentException e) {
            throw new InvalidAlgorithmParameterException("Invalid key type in parameters", e);
        }
    }

    private KeyGenParameterSpec buildKeyGenParameterSpecFromLegacy(KeyPairGeneratorSpec keyPairGeneratorSpec, int i) {
        KeyGenParameterSpec.Builder builder;
        if (i == 1) {
            builder = new KeyGenParameterSpec.Builder(keyPairGeneratorSpec.getKeystoreAlias(), 15);
            builder.setDigests(KeyProperties.DIGEST_NONE, KeyProperties.DIGEST_MD5, "SHA-1", KeyProperties.DIGEST_SHA224, "SHA-256", KeyProperties.DIGEST_SHA384, KeyProperties.DIGEST_SHA512);
            builder.setEncryptionPaddings("NoPadding", KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1, KeyProperties.ENCRYPTION_PADDING_RSA_OAEP);
            builder.setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1, KeyProperties.SIGNATURE_PADDING_RSA_PSS);
            builder.setRandomizedEncryptionRequired(false);
        } else if (i == 3) {
            builder = new KeyGenParameterSpec.Builder(keyPairGeneratorSpec.getKeystoreAlias(), 12);
            builder.setDigests(KeyProperties.DIGEST_NONE, "SHA-1", KeyProperties.DIGEST_SHA224, "SHA-256", KeyProperties.DIGEST_SHA384, KeyProperties.DIGEST_SHA512);
        } else {
            throw new ProviderException("Unsupported algorithm: " + this.mKeymasterAlgorithm);
        }
        if (keyPairGeneratorSpec.getKeySize() != -1) {
            builder.setKeySize(keyPairGeneratorSpec.getKeySize());
        }
        if (keyPairGeneratorSpec.getAlgorithmParameterSpec() != null) {
            builder.setAlgorithmParameterSpec(keyPairGeneratorSpec.getAlgorithmParameterSpec());
        }
        builder.setCertificateSubject(keyPairGeneratorSpec.getSubjectDN());
        builder.setCertificateSerialNumber(keyPairGeneratorSpec.getSerialNumber());
        builder.setCertificateNotBefore(keyPairGeneratorSpec.getStartDate());
        builder.setCertificateNotAfter(keyPairGeneratorSpec.getEndDate());
        builder.setUserAuthenticationRequired(false);
        return builder.build();
    }

    private void resetAll() {
        this.mEntryAlias = null;
        this.mEntryNamespace = -1;
        this.mJcaKeyAlgorithm = null;
        this.mKeymasterAlgorithm = -1;
        this.mKeymasterPurposes = null;
        this.mKeymasterBlockModes = null;
        this.mKeymasterEncryptionPaddings = null;
        this.mKeymasterSignaturePaddings = null;
        this.mKeymasterDigests = null;
        this.mKeymasterMgf1Digests = null;
        this.mKeySizeBits = 0;
        this.mSpec = null;
        this.mRSAPublicExponent = null;
        this.mRng = null;
        this.mKeyStore = null;
        this.mEcCurveName = null;
    }

    private void initAlgorithmSpecificParameters() throws InvalidAlgorithmParameterException {
        BigInteger bigInteger;
        AlgorithmParameterSpec algorithmParameterSpec = this.mSpec.getAlgorithmParameterSpec();
        int i = this.mKeymasterAlgorithm;
        if (i == 1) {
            if (algorithmParameterSpec instanceof RSAKeyGenParameterSpec) {
                RSAKeyGenParameterSpec rSAKeyGenParameterSpec = (RSAKeyGenParameterSpec) algorithmParameterSpec;
                int i2 = this.mKeySizeBits;
                if (i2 == -1) {
                    this.mKeySizeBits = rSAKeyGenParameterSpec.getKeysize();
                } else if (i2 != rSAKeyGenParameterSpec.getKeysize()) {
                    throw new InvalidAlgorithmParameterException("RSA key size must match  between " + this.mSpec + " and " + algorithmParameterSpec + ": " + this.mKeySizeBits + " vs " + rSAKeyGenParameterSpec.getKeysize());
                }
                bigInteger = rSAKeyGenParameterSpec.getPublicExponent();
            } else {
                if (algorithmParameterSpec != null) {
                    throw new InvalidAlgorithmParameterException("RSA may only use RSAKeyGenParameterSpec");
                }
                bigInteger = null;
            }
            if (bigInteger == null) {
                bigInteger = RSAKeyGenParameterSpec.F4;
            }
            if (bigInteger.compareTo(BigInteger.ZERO) < 1) {
                throw new InvalidAlgorithmParameterException("RSA public exponent must be positive: " + bigInteger);
            }
            if (bigInteger.signum() == -1 || bigInteger.compareTo(KeymasterArguments.UINT64_MAX_VALUE) > 0) {
                throw new InvalidAlgorithmParameterException("Unsupported RSA public exponent: " + bigInteger + ". Maximum supported value: " + KeymasterArguments.UINT64_MAX_VALUE);
            }
            this.mRSAPublicExponent = Long.valueOf(bigInteger.longValue());
            return;
        }
        if (i == 3) {
            if (!(algorithmParameterSpec instanceof ECGenParameterSpec)) {
                if (algorithmParameterSpec != null) {
                    throw new InvalidAlgorithmParameterException("EC may only use ECGenParameterSpec");
                }
                return;
            }
            String name = ((ECGenParameterSpec) algorithmParameterSpec).getName();
            this.mEcCurveName = name;
            if (this.mOriginalKeymasterAlgorithm == 1203 && !name.equalsIgnoreCase("x25519")) {
                throw new InvalidAlgorithmParameterException("XDH algorithm only supports x25519 curve.");
            }
            if (this.mOriginalKeymasterAlgorithm == 1204 && !this.mEcCurveName.equalsIgnoreCase("ed25519")) {
                throw new InvalidAlgorithmParameterException("Ed25519 algorithm only supports ed25519 curve.");
            }
            Integer num = SUPPORTED_EC_CURVE_NAME_TO_SIZE.get(this.mEcCurveName.toLowerCase(Locale.US));
            if (num == null) {
                throw new InvalidAlgorithmParameterException("Unsupported EC curve name: " + this.mEcCurveName + ". Supported: " + SUPPORTED_EC_CURVE_NAMES);
            }
            int i3 = this.mKeySizeBits;
            if (i3 == -1) {
                this.mKeySizeBits = num.intValue();
                return;
            }
            if (i3 == num.intValue()) {
                return;
            }
            throw new InvalidAlgorithmParameterException("EC key size must match  between " + this.mSpec + " and " + algorithmParameterSpec + ": " + this.mKeySizeBits + " vs " + num);
        }
        throw new ProviderException("Unsupported algorithm: " + this.mKeymasterAlgorithm);
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00a4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:? A[SYNTHETIC] */
    @Override // java.security.KeyPairGeneratorSpi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.security.KeyPair generateKeyPair() {
        /*
            r12 = this;
            java.lang.String r0 = "generateKeyPair"
            android.os.StrictMode.noteSlowCall(r0)
            android.security.KeyStore2 r0 = r12.mKeyStore
            if (r0 == 0) goto Lba
            android.security.keystore.KeyGenParameterSpec r0 = r12.mSpec
            if (r0 == 0) goto Lba
            boolean r0 = r0.isStrongBoxBacked()
            r1 = 2
            r2 = 1
            if (r0 == 0) goto L17
            r0 = r1
            goto L18
        L17:
            r0 = r2
        L18:
            android.security.keystore.KeyGenParameterSpec r3 = r12.mSpec
            boolean r8 = r3.isCriticalToDeviceEncryption()
            java.security.SecureRandom r3 = r12.mRng
            int r4 = r12.mKeySizeBits
            r10 = 7
            int r4 = r4 + r10
            int r4 = r4 / 8
            byte[] r9 = android.security.keystore2.KeyStoreCryptoOperationUtils.getRandomBytesToMixIntoKeystoreRng(r3, r4)
            android.system.keystore2.KeyDescriptor r5 = new android.system.keystore2.KeyDescriptor
            r5.<init>()
            java.lang.String r3 = r12.mEntryAlias
            r5.alias = r3
            int r3 = r12.mEntryNamespace
            r4 = -1
            r11 = 0
            if (r3 != r4) goto L3a
            r1 = r11
        L3a:
            r5.domain = r1
            int r1 = r12.mEntryNamespace
            long r3 = (long) r1
            r5.nspace = r3
            r1 = 0
            r5.blob = r1
            android.security.KeyStore2 r1 = r12.mKeyStore     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6c android.security.KeyStoreException -> L76
            android.security.KeyStoreSecurityLevel r4 = r1.getSecurityLevel(r0)     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6c android.security.KeyStoreException -> L76
            android.system.keystore2.KeyDescriptor r6 = r12.mAttestKeyDescriptor     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6c android.security.KeyStoreException -> L76
            java.util.Collection r7 = r12.constructKeyGenerationArguments()     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6c android.security.KeyStoreException -> L76
            android.system.keystore2.KeyMetadata r0 = r4.generateKey(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6c android.security.KeyStoreException -> L76
            int r1 = r12.mKeymasterAlgorithm     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6c android.security.KeyStoreException -> L76
            android.security.keystore2.AndroidKeyStorePublicKey r0 = android.security.keystore2.AndroidKeyStoreProvider.makeAndroidKeyStorePublicKeyFromKeyEntryResponse(r5, r0, r4, r1)     // Catch: java.lang.Throwable -> L68 java.lang.Throwable -> L6c android.security.KeyStoreException -> L76
            java.security.KeyPair r1 = new java.security.KeyPair     // Catch: java.lang.Throwable -> L64 android.security.KeyStoreException -> L66 java.lang.Throwable -> La0
            android.security.keystore2.AndroidKeyStorePrivateKey r3 = r0.getPrivateKey()     // Catch: java.lang.Throwable -> L64 android.security.KeyStoreException -> L66 java.lang.Throwable -> La0
            r1.<init>(r0, r3)     // Catch: java.lang.Throwable -> L64 android.security.KeyStoreException -> L66 java.lang.Throwable -> La0
            return r1
        L64:
            r0 = move-exception
            goto L6e
        L66:
            r0 = move-exception
            goto L78
        L68:
            r0 = move-exception
            r1 = r0
            r2 = r11
            goto La2
        L6c:
            r0 = move-exception
            r2 = r11
        L6e:
            java.security.ProviderException r1 = new java.security.ProviderException     // Catch: java.lang.Throwable -> La0
            java.lang.String r3 = "Failed to construct key object from newly generated key pair."
            r1.<init>(r3, r0)     // Catch: java.lang.Throwable -> La0
            throw r1     // Catch: java.lang.Throwable -> La0
        L76:
            r0 = move-exception
            r2 = r11
        L78:
            int r1 = r0.getErrorCode()     // Catch: java.lang.Throwable -> La0
            r3 = -68
            if (r1 == r3) goto L98
            java.security.ProviderException r1 = new java.security.ProviderException     // Catch: java.lang.Throwable -> La0
            java.lang.String r3 = "Failed to generate key pair."
            r1.<init>(r3, r0)     // Catch: java.lang.Throwable -> La0
            android.security.keystore.KeyGenParameterSpec r0 = r12.mSpec     // Catch: java.lang.Throwable -> La0
            int r0 = r0.getPurposes()     // Catch: java.lang.Throwable -> La0
            r0 = r0 & 32
            if (r0 == 0) goto L97
            android.security.keystore.SecureKeyImportUnavailableException r0 = new android.security.keystore.SecureKeyImportUnavailableException     // Catch: java.lang.Throwable -> La0
            r0.<init>(r1)     // Catch: java.lang.Throwable -> La0
            throw r0     // Catch: java.lang.Throwable -> La0
        L97:
            throw r1     // Catch: java.lang.Throwable -> La0
        L98:
            android.security.keystore.StrongBoxUnavailableException r1 = new android.security.keystore.StrongBoxUnavailableException     // Catch: java.lang.Throwable -> La0
            java.lang.String r3 = "Failed to generated key pair."
            r1.<init>(r3, r0)     // Catch: java.lang.Throwable -> La0
            throw r1     // Catch: java.lang.Throwable -> La0
        La0:
            r0 = move-exception
            r1 = r0
        La2:
            if (r2 != 0) goto Lb9
            android.security.KeyStore2 r12 = r12.mKeyStore     // Catch: android.security.KeyStoreException -> Laa
            r12.deleteKey(r5)     // Catch: android.security.KeyStoreException -> Laa
            goto Lb9
        Laa:
            r0 = move-exception
            r12 = r0
            int r0 = r12.getErrorCode()
            if (r0 == r10) goto Lb9
            java.lang.String r0 = "AndroidKeyStoreKeyPairGeneratorSpi"
            java.lang.String r2 = "Failed to delete newly generated key after generation failed unexpectedly."
            android.util.Log.e(r0, r2, r12)
        Lb9:
            throw r1
        Lba:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "Not initialized"
            r12.<init>(r0)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi.generateKeyPair():java.security.KeyPair");
    }

    private void addAttestationParameters(List<KeyParameter> list) throws ProviderException, IllegalArgumentException, DeviceIdAttestationException {
        TelephonyManager telephonyManager;
        String str;
        byte[] attestationChallenge = this.mSpec.getAttestationChallenge();
        if (attestationChallenge != null) {
            list.add(KeyStore2ParameterUtils.makeBytes(-1879047484, attestationChallenge));
            if (this.mSpec.isDevicePropertiesAttestationIncluded()) {
                list.add(KeyStore2ParameterUtils.makeBytes(-1879047482, (isPropertyEmptyOrUnknown(Build.BRAND_FOR_ATTESTATION) ? Build.BRAND : Build.BRAND_FOR_ATTESTATION).getBytes(StandardCharsets.UTF_8)));
                list.add(KeyStore2ParameterUtils.makeBytes(-1879047481, (isPropertyEmptyOrUnknown(Build.DEVICE_FOR_ATTESTATION) ? Build.DEVICE : Build.DEVICE_FOR_ATTESTATION).getBytes(StandardCharsets.UTF_8)));
                list.add(KeyStore2ParameterUtils.makeBytes(-1879047480, (isPropertyEmptyOrUnknown(Build.PRODUCT_FOR_ATTESTATION) ? Build.PRODUCT : Build.PRODUCT_FOR_ATTESTATION).getBytes(StandardCharsets.UTF_8)));
                list.add(KeyStore2ParameterUtils.makeBytes(-1879047476, (isPropertyEmptyOrUnknown(Build.MANUFACTURER_FOR_ATTESTATION) ? Build.MANUFACTURER : Build.MANUFACTURER_FOR_ATTESTATION).getBytes(StandardCharsets.UTF_8)));
                list.add(KeyStore2ParameterUtils.makeBytes(-1879047475, (isPropertyEmptyOrUnknown(Build.MODEL_FOR_ATTESTATION) ? Build.MODEL : Build.MODEL_FOR_ATTESTATION).getBytes(StandardCharsets.UTF_8)));
            }
            int[] attestationIds = this.mSpec.getAttestationIds();
            if (attestationIds.length == 0) {
                return;
            }
            ArraySet<Integer> arraySet = new ArraySet(attestationIds.length);
            for (int i : attestationIds) {
                arraySet.add(Integer.valueOf(i));
            }
            if (arraySet.contains(2) || arraySet.contains(3)) {
                telephonyManager = (TelephonyManager) AppGlobals.getInitialApplication().getSystemService("phone");
                if (telephonyManager == null) {
                    throw new DeviceIdAttestationException("Unable to access telephony service");
                }
            } else {
                telephonyManager = null;
            }
            for (Integer num : arraySet) {
                int intValue = num.intValue();
                if (intValue == 1) {
                    list.add(KeyStore2ParameterUtils.makeBytes(-1879047479, Build.getSerial().getBytes(StandardCharsets.UTF_8)));
                } else if (intValue == 2) {
                    String imei = telephonyManager.getImei(0);
                    if (imei == null) {
                        throw new DeviceIdAttestationException("Unable to retrieve IMEI");
                    }
                    list.add(KeyStore2ParameterUtils.makeBytes(-1879047478, imei.getBytes(StandardCharsets.UTF_8)));
                    String imei2 = telephonyManager.getImei(1);
                    if (!TextUtils.isEmpty(imei2)) {
                        list.add(KeyStore2ParameterUtils.makeBytes(-1879047469, imei2.getBytes(StandardCharsets.UTF_8)));
                    }
                } else if (intValue == 3) {
                    try {
                        str = telephonyManager.getMeid(0);
                    } catch (UnsupportedOperationException e) {
                        Log.e(TAG, "Unable to retrieve MEID", e);
                        str = null;
                    }
                    if (str == null) {
                        throw new DeviceIdAttestationException("Unable to retrieve MEID");
                    }
                    list.add(KeyStore2ParameterUtils.makeBytes(-1879047477, str.getBytes(StandardCharsets.UTF_8)));
                } else if (intValue == 4) {
                    list.add(KeyStore2ParameterUtils.makeBool(1879048912));
                } else {
                    throw new IllegalArgumentException("Unknown device ID type " + num);
                }
            }
        }
    }

    private Collection<KeyParameter> constructKeyGenerationArguments() throws DeviceIdAttestationException, IllegalArgumentException, InvalidAlgorithmParameterException {
        final ArrayList arrayList = new ArrayList();
        arrayList.add(KeyStore2ParameterUtils.makeInt(805306371, this.mKeySizeBits));
        arrayList.add(KeyStore2ParameterUtils.makeEnum(268435458, this.mKeymasterAlgorithm));
        if (this.mKeymasterAlgorithm == 3) {
            arrayList.add(KeyStore2ParameterUtils.makeEnum(268435466, keySizeAndNameToEcCurve(this.mKeySizeBits, this.mEcCurveName)));
        }
        ArrayUtils.forEach(this.mKeymasterPurposes, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add(KeyStore2ParameterUtils.makeEnum(536870913, ((Integer) obj).intValue()));
            }
        });
        ArrayUtils.forEach(this.mKeymasterBlockModes, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add(KeyStore2ParameterUtils.makeEnum(536870916, ((Integer) obj).intValue()));
            }
        });
        ArrayUtils.forEach(this.mKeymasterEncryptionPaddings, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AndroidKeyStoreKeyPairGeneratorSpi.this.lambda$constructKeyGenerationArguments$5(arrayList, (Integer) obj);
            }
        });
        ArrayUtils.forEach(this.mKeymasterSignaturePaddings, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add(KeyStore2ParameterUtils.makeEnum(536870918, ((Integer) obj).intValue()));
            }
        });
        ArrayUtils.forEach(this.mKeymasterDigests, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add(KeyStore2ParameterUtils.makeEnum(536870917, ((Integer) obj).intValue()));
            }
        });
        KeyStore2ParameterUtils.addUserAuthArgs(arrayList, this.mSpec);
        if (this.mSpec.getKeyValidityStart() != null) {
            arrayList.add(KeyStore2ParameterUtils.makeDate(1610613136, this.mSpec.getKeyValidityStart()));
        }
        if (this.mSpec.getKeyValidityForOriginationEnd() != null) {
            arrayList.add(KeyStore2ParameterUtils.makeDate(1610613137, this.mSpec.getKeyValidityForOriginationEnd()));
        }
        if (this.mSpec.getKeyValidityForConsumptionEnd() != null) {
            arrayList.add(KeyStore2ParameterUtils.makeDate(1610613138, this.mSpec.getKeyValidityForConsumptionEnd()));
        }
        if (this.mSpec.getCertificateNotAfter() != null) {
            arrayList.add(KeyStore2ParameterUtils.makeDate(1610613745, this.mSpec.getCertificateNotAfter()));
        }
        if (this.mSpec.getCertificateNotBefore() != null) {
            arrayList.add(KeyStore2ParameterUtils.makeDate(1610613744, this.mSpec.getCertificateNotBefore()));
        }
        if (this.mSpec.getCertificateSerialNumber() != null) {
            arrayList.add(KeyStore2ParameterUtils.makeBignum(-2147482642, this.mSpec.getCertificateSerialNumber()));
        }
        if (this.mSpec.getCertificateSubject() != null) {
            arrayList.add(KeyStore2ParameterUtils.makeBytes(-1879047185, this.mSpec.getCertificateSubject().getEncoded()));
        }
        if (this.mSpec.getMaxUsageCount() != -1) {
            arrayList.add(KeyStore2ParameterUtils.makeInt(805306773, this.mSpec.getMaxUsageCount()));
        }
        addAlgorithmSpecificParameters(arrayList);
        if (this.mSpec.isUniqueIdIncluded()) {
            arrayList.add(KeyStore2ParameterUtils.makeBool(1879048394));
        }
        addAttestationParameters(arrayList);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$constructKeyGenerationArguments$5(final List list, Integer num) {
        list.add(KeyStore2ParameterUtils.makeEnum(536870918, num.intValue()));
        int i = this.mSpec.isStrongBoxBacked() ? 2 : 1;
        if (num.intValue() == 2 && KeymasterUtils.isKeyMintDevice(i)) {
            ArrayUtils.forEach(this.mKeymasterMgf1Digests, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    list.add(KeyStore2ParameterUtils.makeEnum(536871115, ((Integer) obj).intValue()));
                }
            });
            if (getMgf1DigestSetterFlag()) {
                return;
            }
            final int keymaster = KeyProperties.Digest.toKeymaster("SHA-1");
            ArrayUtils.forEach(this.mKeymasterDigests, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AndroidKeyStoreKeyPairGeneratorSpi.lambda$constructKeyGenerationArguments$4(keymaster, list, (Integer) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$constructKeyGenerationArguments$4(int i, List list, Integer num) {
        if (num.intValue() != i) {
            list.add(KeyStore2ParameterUtils.makeEnum(536871115, num.intValue()));
        }
    }

    private static boolean getMgf1DigestSetterFlag() {
        try {
            return com.android.internal.hidden_from_bootclasspath.android.security.Flags.mgf1DigestSetterV2();
        } catch (SecurityException e) {
            Log.w(TAG, "Cannot read MGF1 Digest setter flag value", e);
            return false;
        }
    }

    private void addAlgorithmSpecificParameters(List<KeyParameter> list) {
        int i = this.mKeymasterAlgorithm;
        if (i == 1) {
            list.add(KeyStore2ParameterUtils.makeLong(1342177480, this.mRSAPublicExponent.longValue()));
        } else {
            if (i == 3) {
                return;
            }
            throw new ProviderException("Unsupported algorithm: " + this.mKeymasterAlgorithm);
        }
    }

    private static int getDefaultKeySize(int i) {
        if (i == 1) {
            return 2048;
        }
        if (i == 3) {
            return 256;
        }
        throw new ProviderException("Unsupported algorithm: " + i);
    }

    private static void checkValidKeySize(int i, int i2, boolean z, String str) throws InvalidAlgorithmParameterException {
        if (i == 1) {
            if (i2 < 512 || i2 > 8192) {
                throw new InvalidAlgorithmParameterException("RSA key size must be >= 512 and <= 8192");
            }
            return;
        }
        if (i != 3) {
            throw new ProviderException("Unsupported algorithm: " + i);
        }
        if (z && i2 != 256) {
            throw new InvalidAlgorithmParameterException("Unsupported StrongBox EC key size: " + i2 + " bits. Supported: 256");
        }
        if (z && isCurve25519(str)) {
            throw new InvalidAlgorithmParameterException("Unsupported StrongBox EC: " + str);
        }
        List<Integer> list = SUPPORTED_EC_CURVE_SIZES;
        if (list.contains(Integer.valueOf(i2))) {
            return;
        }
        throw new InvalidAlgorithmParameterException("Unsupported EC key size: " + i2 + " bits. Supported: " + list);
    }

    private static String getCertificateSignatureAlgorithm(int i, int i2, KeyGenParameterSpec keyGenParameterSpec) {
        if ((keyGenParameterSpec.getPurposes() & 4) == 0 || keyGenParameterSpec.isUserAuthenticationRequired() || !keyGenParameterSpec.isDigestsSpecified()) {
            return null;
        }
        if (i == 1) {
            if (!com.android.internal.util.ArrayUtils.contains(KeyProperties.SignaturePadding.allToKeymaster(keyGenParameterSpec.getSignaturePaddings()), 5)) {
                return null;
            }
            int i3 = i2 - 240;
            Iterator<Integer> it = getAvailableKeymasterSignatureDigests(keyGenParameterSpec.getDigests(), AndroidKeyStoreBCWorkaroundProvider.getSupportedEcdsaSignatureDigests()).iterator();
            int i4 = -1;
            int i5 = -1;
            while (it.hasNext()) {
                int intValue = it.next().intValue();
                int digestOutputSizeBits = KeymasterUtils.getDigestOutputSizeBits(intValue);
                if (digestOutputSizeBits <= i3 && (i4 == -1 || digestOutputSizeBits > i5)) {
                    i4 = intValue;
                    i5 = digestOutputSizeBits;
                }
            }
            if (i4 == -1) {
                return null;
            }
            return KeyProperties.Digest.fromKeymasterToSignatureAlgorithmDigest(i4) + "WithRSA";
        }
        if (i == 3) {
            Iterator<Integer> it2 = getAvailableKeymasterSignatureDigests(keyGenParameterSpec.getDigests(), AndroidKeyStoreBCWorkaroundProvider.getSupportedEcdsaSignatureDigests()).iterator();
            int i6 = -1;
            int i7 = -1;
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                int intValue2 = it2.next().intValue();
                int digestOutputSizeBits2 = KeymasterUtils.getDigestOutputSizeBits(intValue2);
                if (digestOutputSizeBits2 == i2) {
                    i6 = intValue2;
                    break;
                }
                if (i6 != -1) {
                    if (i7 < i2) {
                        if (digestOutputSizeBits2 > i7) {
                        }
                    } else if (digestOutputSizeBits2 < i7 && digestOutputSizeBits2 >= i2) {
                    }
                }
                i6 = intValue2;
                i7 = digestOutputSizeBits2;
            }
            if (i6 == -1) {
                return null;
            }
            return KeyProperties.Digest.fromKeymasterToSignatureAlgorithmDigest(i6) + "WithECDSA";
        }
        throw new ProviderException("Unsupported algorithm: " + i);
    }

    private static Set<Integer> getAvailableKeymasterSignatureDigests(String[] strArr, String[] strArr2) {
        HashSet hashSet = new HashSet();
        for (int i : KeyProperties.Digest.allToKeymaster(strArr)) {
            hashSet.add(Integer.valueOf(i));
        }
        HashSet hashSet2 = new HashSet();
        for (int i2 : KeyProperties.Digest.allToKeymaster(strArr2)) {
            hashSet2.add(Integer.valueOf(i2));
        }
        HashSet hashSet3 = new HashSet(hashSet2);
        hashSet3.retainAll(hashSet);
        return hashSet3;
    }

    private boolean isPropertyEmptyOrUnknown(String str) {
        return TextUtils.isEmpty(str) || str.equals("unknown");
    }
}

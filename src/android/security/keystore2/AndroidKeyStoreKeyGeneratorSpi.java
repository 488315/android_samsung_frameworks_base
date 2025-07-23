package android.security.keystore2;

import android.os.StrictMode;
import android.security.KeyStore2;
import android.security.KeyStoreException;
import android.security.KeyStoreSecurityLevel;
import android.security.keystore.ArrayUtils;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.security.keystore.StrongBoxUnavailableException;
import android.system.keystore2.KeyDescriptor;
import android.util.Log;
import java.security.InvalidAlgorithmParameterException;
import java.security.ProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.SecretKey;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
public abstract class AndroidKeyStoreKeyGeneratorSpi extends KeyGeneratorSpi {
    private static final String TAG = "AndroidKeyStoreKeyGeneratorSpi";
    private final int mDefaultKeySizeBits;
    protected int mKeySizeBits;
    private final KeyStore2 mKeyStore;
    private final int mKeymasterAlgorithm;
    private int[] mKeymasterBlockModes;
    private final int mKeymasterDigest;
    private int[] mKeymasterDigests;
    private int[] mKeymasterPaddings;
    private int[] mKeymasterPurposes;
    private SecureRandom mRng;
    private KeyGenParameterSpec mSpec;

    public static class AES extends AndroidKeyStoreKeyGeneratorSpi {
        public AES() {
            super(32, 128);
        }

        @Override // android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi, javax.crypto.KeyGeneratorSpi
        protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            super.engineInit(algorithmParameterSpec, secureRandom);
            if (this.mKeySizeBits == 128 || this.mKeySizeBits == 192 || this.mKeySizeBits == 256) {
                return;
            }
            throw new InvalidAlgorithmParameterException("Unsupported key size: " + this.mKeySizeBits + ". Supported: 128, 192, 256.");
        }
    }

    public static class DESede extends AndroidKeyStoreKeyGeneratorSpi {
        public DESede() {
            super(33, 168);
        }
    }

    protected static abstract class HmacBase extends AndroidKeyStoreKeyGeneratorSpi {
        protected HmacBase(int i) {
            super(128, i, KeymasterUtils.getDigestOutputSizeBits(i));
        }
    }

    public static class HmacSHA1 extends HmacBase {
        public HmacSHA1() {
            super(2);
        }
    }

    public static class HmacSHA224 extends HmacBase {
        public HmacSHA224() {
            super(3);
        }
    }

    public static class HmacSHA256 extends HmacBase {
        public HmacSHA256() {
            super(4);
        }
    }

    public static class HmacSHA384 extends HmacBase {
        public HmacSHA384() {
            super(5);
        }
    }

    public static class HmacSHA512 extends HmacBase {
        public HmacSHA512() {
            super(6);
        }
    }

    protected AndroidKeyStoreKeyGeneratorSpi(int i, int i2) {
        this(i, -1, i2);
    }

    protected AndroidKeyStoreKeyGeneratorSpi(int i, int i2, int i3) {
        this.mKeyStore = KeyStore2.getInstance();
        this.mKeymasterAlgorithm = i;
        this.mKeymasterDigest = i2;
        this.mDefaultKeySizeBits = i3;
        if (i3 <= 0) {
            throw new IllegalArgumentException("Default key size must be positive");
        }
        if (i == 128 && i2 == -1) {
            throw new IllegalArgumentException("Digest algorithm must be specified for HMAC key");
        }
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected void engineInit(SecureRandom secureRandom) {
        throw new UnsupportedOperationException("Cannot initialize without a " + KeyGenParameterSpec.class.getName() + " parameter");
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected void engineInit(int i, SecureRandom secureRandom) {
        throw new UnsupportedOperationException("Cannot initialize without a " + KeyGenParameterSpec.class.getName() + " parameter");
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        if (algorithmParameterSpec != null) {
            try {
                if (algorithmParameterSpec instanceof KeyGenParameterSpec) {
                    KeyGenParameterSpec keyGenParameterSpec = (KeyGenParameterSpec) algorithmParameterSpec;
                    if (keyGenParameterSpec.getKeystoreAlias() == null) {
                        throw new InvalidAlgorithmParameterException("KeyStore entry alias not provided");
                    }
                    this.mRng = secureRandom;
                    this.mSpec = keyGenParameterSpec;
                    int keySize = keyGenParameterSpec.getKeySize() != -1 ? keyGenParameterSpec.getKeySize() : this.mDefaultKeySizeBits;
                    this.mKeySizeBits = keySize;
                    if (keySize <= 0) {
                        throw new InvalidAlgorithmParameterException("Key size must be positive: " + this.mKeySizeBits);
                    }
                    if (keySize % 8 != 0) {
                        throw new InvalidAlgorithmParameterException("Key size must be a multiple of 8: " + this.mKeySizeBits);
                    }
                    try {
                        this.mKeymasterPurposes = KeyProperties.Purpose.allToKeymaster(keyGenParameterSpec.getPurposes());
                        this.mKeymasterPaddings = KeyProperties.EncryptionPadding.allToKeymaster(keyGenParameterSpec.getEncryptionPaddings());
                        if (keyGenParameterSpec.getSignaturePaddings().length > 0) {
                            throw new InvalidAlgorithmParameterException("Signature paddings not supported for symmetric key algorithms");
                        }
                        this.mKeymasterBlockModes = KeyProperties.BlockMode.allToKeymaster(keyGenParameterSpec.getBlockModes());
                        if ((keyGenParameterSpec.getPurposes() & 1) != 0 && keyGenParameterSpec.isRandomizedEncryptionRequired()) {
                            for (int i : this.mKeymasterBlockModes) {
                                if (!KeymasterUtils.isKeymasterBlockModeIndCpaCompatibleWithSymmetricCrypto(i)) {
                                    throw new InvalidAlgorithmParameterException("Randomized encryption (IND-CPA) required but may be violated by block mode: " + KeyProperties.BlockMode.fromKeymaster(i) + ". See " + KeyGenParameterSpec.class.getName() + " documentation.");
                                }
                            }
                        }
                        int i2 = this.mKeymasterAlgorithm;
                        if (i2 == 33 && this.mKeySizeBits != 168) {
                            throw new InvalidAlgorithmParameterException("3DES key size must be 168 bits.");
                        }
                        if (i2 == 128) {
                            int i3 = this.mKeySizeBits;
                            if (i3 < 64 || i3 > 512) {
                                throw new InvalidAlgorithmParameterException("HMAC key sizes must be within 64-512 bits, inclusive.");
                            }
                            this.mKeymasterDigests = new int[]{this.mKeymasterDigest};
                            if (keyGenParameterSpec.isDigestsSpecified()) {
                                int[] allToKeymaster = KeyProperties.Digest.allToKeymaster(keyGenParameterSpec.getDigests());
                                if (allToKeymaster.length != 1 || allToKeymaster[0] != this.mKeymasterDigest) {
                                    throw new InvalidAlgorithmParameterException("Unsupported digests specification: " + Arrays.asList(keyGenParameterSpec.getDigests()) + ". Only " + KeyProperties.Digest.fromKeymaster(this.mKeymasterDigest) + " supported for this HMAC key algorithm");
                                }
                            }
                        } else if (keyGenParameterSpec.isDigestsSpecified()) {
                            this.mKeymasterDigests = KeyProperties.Digest.allToKeymaster(keyGenParameterSpec.getDigests());
                        } else {
                            this.mKeymasterDigests = EmptyArray.INT;
                        }
                        KeyStore2ParameterUtils.addUserAuthArgs(new ArrayList(), keyGenParameterSpec);
                        return;
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        throw new InvalidAlgorithmParameterException(e);
                    }
                }
            } finally {
                resetAll();
            }
        }
        throw new InvalidAlgorithmParameterException("Cannot initialize without a " + KeyGenParameterSpec.class.getName() + " parameter");
    }

    private void resetAll() {
        this.mSpec = null;
        this.mRng = null;
        this.mKeySizeBits = -1;
        this.mKeymasterPurposes = null;
        this.mKeymasterPaddings = null;
        this.mKeymasterBlockModes = null;
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected SecretKey engineGenerateKey() {
        StrictMode.noteSlowCall("engineGenerateKey");
        KeyGenParameterSpec keyGenParameterSpec = this.mSpec;
        if (keyGenParameterSpec == null) {
            throw new IllegalStateException("Not initialized");
        }
        final ArrayList arrayList = new ArrayList();
        arrayList.add(KeyStore2ParameterUtils.makeInt(805306371, this.mKeySizeBits));
        arrayList.add(KeyStore2ParameterUtils.makeEnum(268435458, this.mKeymasterAlgorithm));
        ArrayUtils.forEach(this.mKeymasterPurposes, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add(KeyStore2ParameterUtils.makeEnum(536870913, ((Integer) obj).intValue()));
            }
        });
        ArrayUtils.forEach(this.mKeymasterBlockModes, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AndroidKeyStoreKeyGeneratorSpi.this.lambda$engineGenerateKey$1(arrayList, (Integer) obj);
            }
        });
        ArrayUtils.forEach(this.mKeymasterPaddings, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add(KeyStore2ParameterUtils.makeEnum(536870918, ((Integer) obj).intValue()));
            }
        });
        ArrayUtils.forEach(this.mKeymasterDigests, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add(KeyStore2ParameterUtils.makeEnum(536870917, ((Integer) obj).intValue()));
            }
        });
        if (this.mKeymasterAlgorithm == 128) {
            int[] iArr = this.mKeymasterDigests;
            if (iArr.length != 0) {
                int digestOutputSizeBits = KeymasterUtils.getDigestOutputSizeBits(iArr[0]);
                if (digestOutputSizeBits == -1) {
                    throw new ProviderException("HMAC key authorized for unsupported digest: " + KeyProperties.Digest.fromKeymaster(this.mKeymasterDigests[0]));
                }
                arrayList.add(KeyStore2ParameterUtils.makeInt(805306376, digestOutputSizeBits));
            }
        }
        KeyStore2ParameterUtils.addUserAuthArgs(arrayList, keyGenParameterSpec);
        if (keyGenParameterSpec.getKeyValidityStart() != null) {
            arrayList.add(KeyStore2ParameterUtils.makeDate(1610613136, keyGenParameterSpec.getKeyValidityStart()));
        }
        if (keyGenParameterSpec.getKeyValidityForOriginationEnd() != null) {
            arrayList.add(KeyStore2ParameterUtils.makeDate(1610613137, keyGenParameterSpec.getKeyValidityForOriginationEnd()));
        }
        if (keyGenParameterSpec.getKeyValidityForConsumptionEnd() != null) {
            arrayList.add(KeyStore2ParameterUtils.makeDate(1610613138, keyGenParameterSpec.getKeyValidityForConsumptionEnd()));
        }
        if ((keyGenParameterSpec.getPurposes() & 1) != 0 && !keyGenParameterSpec.isRandomizedEncryptionRequired()) {
            arrayList.add(KeyStore2ParameterUtils.makeBool(1879048199));
        }
        if (keyGenParameterSpec.getMaxUsageCount() != -1) {
            arrayList.add(KeyStore2ParameterUtils.makeInt(805306773, keyGenParameterSpec.getMaxUsageCount()));
        }
        byte[] randomBytesToMixIntoKeystoreRng = KeyStoreCryptoOperationUtils.getRandomBytesToMixIntoKeystoreRng(this.mRng, (this.mKeySizeBits + 7) / 8);
        int i = keyGenParameterSpec.isStrongBoxBacked() ? 2 : 1;
        int i2 = 0;
        boolean isCriticalToDeviceEncryption = keyGenParameterSpec.isCriticalToDeviceEncryption();
        int i3 = i;
        KeyDescriptor keyDescriptor = new KeyDescriptor();
        keyDescriptor.alias = keyGenParameterSpec.getKeystoreAlias();
        keyDescriptor.nspace = keyGenParameterSpec.getNamespace();
        if (keyDescriptor.nspace != -1) {
            i2 = 2;
        }
        keyDescriptor.domain = i2;
        keyDescriptor.blob = null;
        try {
            KeyStoreSecurityLevel securityLevel = this.mKeyStore.getSecurityLevel(i3);
            try {
                return new AndroidKeyStoreSecretKey(keyDescriptor, securityLevel.generateKey(keyDescriptor, null, arrayList, isCriticalToDeviceEncryption ? 1 : 0, randomBytesToMixIntoKeystoreRng), KeyProperties.KeyAlgorithm.fromKeymasterSecretKeyAlgorithm(this.mKeymasterAlgorithm, this.mKeymasterDigest), securityLevel);
            } catch (IllegalArgumentException e) {
                try {
                    this.mKeyStore.deleteKey(keyDescriptor);
                } catch (KeyStoreException e2) {
                    Log.e(TAG, "Failed to delete key after generating successfully but failed to get the algorithm string.", e2);
                }
                throw new ProviderException("Failed to obtain JCA secret key algorithm name", e);
            }
        } catch (KeyStoreException e3) {
            if (e3.getErrorCode() == -68) {
                throw new StrongBoxUnavailableException("Failed to generate key");
            }
            throw new ProviderException("Keystore key generation failed", e3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$engineGenerateKey$1(List list, Integer num) {
        if (num.intValue() == 32 && this.mKeymasterAlgorithm == 32) {
            list.add(KeyStore2ParameterUtils.makeInt(805306376, 96));
        }
        list.add(KeyStore2ParameterUtils.makeEnum(536870916, num.intValue()));
    }
}

package android.security.keystore2;

import android.hardware.security.keymint.KeyParameter;
import android.security.keystore.ArrayUtils;
import android.security.keystore.KeyProperties;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.ProviderException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.List;

import javax.crypto.spec.IvParameterSpec;

/* loaded from: classes3.dex */
public abstract class AndroidKeyStore3DESCipherSpi extends AndroidKeyStoreCipherSpiBase {
    private static final int BLOCK_SIZE_BYTES = 8;
    private byte[] mIv;
    private boolean mIvHasBeenUsed;
    private final boolean mIvRequired;
    private final int mKeymasterBlockMode;
    private final int mKeymasterPadding;

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    public /* bridge */ /* synthetic */ void finalize() throws Throwable {
        super.finalize();
    }

    AndroidKeyStore3DESCipherSpi(int keymasterBlockMode, int keymasterPadding, boolean ivRequired) {
        this.mKeymasterBlockMode = keymasterBlockMode;
        this.mKeymasterPadding = keymasterPadding;
        this.mIvRequired = ivRequired;
    }

    abstract static class ECB extends AndroidKeyStore3DESCipherSpi {
        protected ECB(int keymasterPadding) {
            super(1, keymasterPadding, false);
        }

        public static class NoPadding extends ECB {
            @Override // android.security.keystore2.AndroidKeyStore3DESCipherSpi,
                      // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            public /* bridge */ /* synthetic */ void finalize() throws Throwable {
                super.finalize();
            }

            public NoPadding() {
                super(1);
            }

            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            protected final String getTransform() {
                return "DESede/ECB/NoPadding";
            }
        }

        public static class PKCS7Padding extends ECB {
            @Override // android.security.keystore2.AndroidKeyStore3DESCipherSpi,
                      // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            public /* bridge */ /* synthetic */ void finalize() throws Throwable {
                super.finalize();
            }

            public PKCS7Padding() {
                super(64);
            }

            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            protected final String getTransform() {
                return "DESede/ECB/PKCS7Padding";
            }
        }
    }

    abstract static class CBC extends AndroidKeyStore3DESCipherSpi {
        protected CBC(int keymasterPadding) {
            super(2, keymasterPadding, true);
        }

        public static class NoPadding extends CBC {
            @Override // android.security.keystore2.AndroidKeyStore3DESCipherSpi,
                      // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            public /* bridge */ /* synthetic */ void finalize() throws Throwable {
                super.finalize();
            }

            public NoPadding() {
                super(1);
            }

            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            protected final String getTransform() {
                return "DESede/CBC/NoPadding";
            }
        }

        public static class PKCS7Padding extends CBC {
            @Override // android.security.keystore2.AndroidKeyStore3DESCipherSpi,
                      // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            public /* bridge */ /* synthetic */ void finalize() throws Throwable {
                super.finalize();
            }

            public PKCS7Padding() {
                super(64);
            }

            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            protected final String getTransform() {
                return "DESede/CBC/PKCS7Padding";
            }
        }
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected void initKey(int i, Key key) throws InvalidKeyException {
        if (!(key instanceof AndroidKeyStoreSecretKey)) {
            throw new InvalidKeyException(
                    "Unsupported key: " + (key != null ? key.getClass().getName() : "null"));
        }
        if (!KeyProperties.KEY_ALGORITHM_3DES.equalsIgnoreCase(key.getAlgorithm())) {
            throw new InvalidKeyException(
                    "Unsupported key algorithm: "
                            + key.getAlgorithm()
                            + ". Only "
                            + KeyProperties.KEY_ALGORITHM_3DES
                            + " supported");
        }
        setKey((AndroidKeyStoreSecretKey) key);
    }

    @Override // javax.crypto.CipherSpi
    protected int engineGetBlockSize() {
        return 8;
    }

    @Override // javax.crypto.CipherSpi
    protected int engineGetOutputSize(int inputLen) {
        return inputLen + 24;
    }

    @Override // javax.crypto.CipherSpi
    protected final byte[] engineGetIV() {
        return ArrayUtils.cloneIfNotEmpty(this.mIv);
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase, javax.crypto.CipherSpi
    protected AlgorithmParameters engineGetParameters() {
        if (!this.mIvRequired || this.mIv == null || this.mIv.length <= 0) {
            return null;
        }
        try {
            AlgorithmParameters params =
                    AlgorithmParameters.getInstance(KeyProperties.KEY_ALGORITHM_3DES);
            params.init(new IvParameterSpec(this.mIv));
            return params;
        } catch (NoSuchAlgorithmException e) {
            throw new ProviderException("Failed to obtain 3DES AlgorithmParameters", e);
        } catch (InvalidParameterSpecException e2) {
            throw new ProviderException(
                    "Failed to initialize 3DES AlgorithmParameters with an IV", e2);
        }
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected void initAlgorithmSpecificParameters() throws InvalidKeyException {
        if (this.mIvRequired && !isEncrypting()) {
            throw new InvalidKeyException(
                    "IV required when decrypting. Use IvParameterSpec or AlgorithmParameters to"
                        + " provide it.");
        }
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected void initAlgorithmSpecificParameters(AlgorithmParameterSpec params)
            throws InvalidAlgorithmParameterException {
        if (!this.mIvRequired) {
            if (params != null) {
                throw new InvalidAlgorithmParameterException("Unsupported parameters: " + params);
            }
        } else if (params == null) {
            if (!isEncrypting()) {
                throw new InvalidAlgorithmParameterException(
                        "IvParameterSpec must be provided when decrypting");
            }
        } else {
            if (!(params instanceof IvParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Only IvParameterSpec supported");
            }
            this.mIv = ((IvParameterSpec) params).getIV();
            if (this.mIv == null) {
                throw new InvalidAlgorithmParameterException("Null IV in IvParameterSpec");
            }
        }
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected void initAlgorithmSpecificParameters(AlgorithmParameters params)
            throws InvalidAlgorithmParameterException {
        if (!this.mIvRequired) {
            if (params != null) {
                throw new InvalidAlgorithmParameterException("Unsupported parameters: " + params);
            }
            return;
        }
        if (params == null) {
            if (!isEncrypting()) {
                throw new InvalidAlgorithmParameterException(
                        "IV required when decrypting. Use IvParameterSpec or AlgorithmParameters to"
                            + " provide it.");
            }
            return;
        }
        if (!KeyProperties.KEY_ALGORITHM_3DES.equalsIgnoreCase(params.getAlgorithm())) {
            throw new InvalidAlgorithmParameterException(
                    "Unsupported AlgorithmParameters algorithm: "
                            + params.getAlgorithm()
                            + ". Supported: DESede");
        }
        try {
            IvParameterSpec ivSpec =
                    (IvParameterSpec) params.getParameterSpec(IvParameterSpec.class);
            this.mIv = ivSpec.getIV();
            if (this.mIv == null) {
                throw new InvalidAlgorithmParameterException("Null IV in AlgorithmParameters");
            }
        } catch (InvalidParameterSpecException e) {
            if (!isEncrypting()) {
                throw new InvalidAlgorithmParameterException(
                        "IV required when decrypting, but not found in parameters: " + params, e);
            }
            this.mIv = null;
        }
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final int getAdditionalEntropyAmountForBegin() {
        if (this.mIvRequired && this.mIv == null && isEncrypting()) {
            return 8;
        }
        return 0;
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected int getAdditionalEntropyAmountForFinish() {
        return 0;
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected void addAlgorithmSpecificParametersToBegin(List<KeyParameter> parameters) {
        if (isEncrypting() && this.mIvRequired && this.mIvHasBeenUsed) {
            throw new IllegalStateException(
                    "IV has already been used. Reusing IV in encryption mode violates security best"
                        + " practices.");
        }
        parameters.add(KeyStore2ParameterUtils.makeEnum(268435458, 33));
        parameters.add(KeyStore2ParameterUtils.makeEnum(536870916, this.mKeymasterBlockMode));
        parameters.add(KeyStore2ParameterUtils.makeEnum(536870918, this.mKeymasterPadding));
        if (this.mIvRequired && this.mIv != null) {
            parameters.add(KeyStore2ParameterUtils.makeBytes(-1879047191, this.mIv));
        }
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected void loadAlgorithmSpecificParametersFromBeginResult(KeyParameter[] parameters) {
        this.mIvHasBeenUsed = true;
        byte[] returnedIv = null;
        if (parameters != null) {
            int length = parameters.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                KeyParameter p = parameters[i];
                if (p.tag != -1879047191) {
                    i++;
                } else {
                    returnedIv = p.value.getBlob();
                    break;
                }
            }
        }
        if (this.mIvRequired) {
            if (this.mIv == null) {
                this.mIv = returnedIv;
                return;
            } else {
                if (returnedIv != null && !Arrays.equals(returnedIv, this.mIv)) {
                    throw new ProviderException("IV in use differs from provided IV");
                }
                return;
            }
        }
        if (returnedIv != null) {
            throw new ProviderException(
                    "IV in use despite IV not being used by this transformation");
        }
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void resetAll() {
        this.mIv = null;
        this.mIvHasBeenUsed = false;
        super.resetAll();
    }
}

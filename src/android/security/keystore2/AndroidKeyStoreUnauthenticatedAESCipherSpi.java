package android.security.keystore2;

import android.hardware.security.keymint.KeyParameter;
import android.security.keystore.ArrayUtils;
import com.android.internal.protolog.PerfettoProtoLogImpl;
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
abstract class AndroidKeyStoreUnauthenticatedAESCipherSpi extends AndroidKeyStoreCipherSpiBase {
    private static final int BLOCK_SIZE_BYTES = 16;
    private byte[] mIv;
    private boolean mIvHasBeenUsed;
    private final boolean mIvRequired;
    private final int mKeymasterBlockMode;
    private final int mKeymasterPadding;

    @Override // javax.crypto.CipherSpi
    protected final int engineGetBlockSize() {
        return 16;
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineGetOutputSize(int i) {
        return i + 48;
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final int getAdditionalEntropyAmountForFinish() {
        return 0;
    }

    static abstract class ECB extends AndroidKeyStoreUnauthenticatedAESCipherSpi {
        protected ECB(int i) {
            super(1, i, false);
        }

        public static class NoPadding extends ECB {
            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            public /* bridge */ /* synthetic */ void finalize() throws Throwable {
                super.finalize();
            }

            public NoPadding() {
                super(1);
            }

            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            protected final String getTransform() {
                return "AES/ECB/NoPadding";
            }
        }

        public static class PKCS7Padding extends ECB {
            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            public /* bridge */ /* synthetic */ void finalize() throws Throwable {
                super.finalize();
            }

            public PKCS7Padding() {
                super(64);
            }

            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            protected final String getTransform() {
                return "AES/ECB/PKCS7Padding";
            }
        }
    }

    static abstract class CBC extends AndroidKeyStoreUnauthenticatedAESCipherSpi {
        protected CBC(int i) {
            super(2, i, true);
        }

        public static class NoPadding extends CBC {
            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            public /* bridge */ /* synthetic */ void finalize() throws Throwable {
                super.finalize();
            }

            public NoPadding() {
                super(1);
            }

            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            protected final String getTransform() {
                return "AES/CBC/NoPadding";
            }
        }

        public static class PKCS7Padding extends CBC {
            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            public /* bridge */ /* synthetic */ void finalize() throws Throwable {
                super.finalize();
            }

            public PKCS7Padding() {
                super(64);
            }

            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            protected final String getTransform() {
                return "AES/CBC/PKCS7Padding";
            }
        }
    }

    static abstract class CTR extends AndroidKeyStoreUnauthenticatedAESCipherSpi {
        protected CTR(int i) {
            super(3, i, true);
        }

        public static class NoPadding extends CTR {
            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            public /* bridge */ /* synthetic */ void finalize() throws Throwable {
                super.finalize();
            }

            public NoPadding() {
                super(1);
            }

            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            protected final String getTransform() {
                return "AES/CTR/NoPadding";
            }
        }
    }

    AndroidKeyStoreUnauthenticatedAESCipherSpi(int i, int i2, boolean z) {
        this.mKeymasterBlockMode = i;
        this.mKeymasterPadding = i2;
        this.mIvRequired = z;
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void resetAll() {
        this.mIv = null;
        this.mIvHasBeenUsed = false;
        super.resetAll();
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void resetWhilePreservingInitState() {
        super.resetWhilePreservingInitState();
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void initKey(int i, Key key) throws InvalidKeyException {
        if (!(key instanceof AndroidKeyStoreSecretKey)) {
            StringBuilder sb = new StringBuilder("Unsupported key: ");
            sb.append(key != null ? key.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING);
            throw new InvalidKeyException(sb.toString());
        }
        if (!"AES".equalsIgnoreCase(key.getAlgorithm())) {
            throw new InvalidKeyException("Unsupported key algorithm: " + key.getAlgorithm() + ". Only AES supported");
        }
        setKey((AndroidKeyStoreSecretKey) key);
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void initAlgorithmSpecificParameters() throws InvalidKeyException {
        if (this.mIvRequired && !isEncrypting()) {
            throw new InvalidKeyException("IV required when decrypting. Use IvParameterSpec or AlgorithmParameters to provide it.");
        }
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void initAlgorithmSpecificParameters(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        if (!this.mIvRequired) {
            if (algorithmParameterSpec == null) {
                return;
            }
            throw new InvalidAlgorithmParameterException("Unsupported parameters: " + algorithmParameterSpec);
        }
        if (algorithmParameterSpec == null) {
            if (!isEncrypting()) {
                throw new InvalidAlgorithmParameterException("IvParameterSpec must be provided when decrypting");
            }
        } else {
            if (!(algorithmParameterSpec instanceof IvParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Only IvParameterSpec supported");
            }
            byte[] iv = ((IvParameterSpec) algorithmParameterSpec).getIV();
            this.mIv = iv;
            if (iv == null) {
                throw new InvalidAlgorithmParameterException("Null IV in IvParameterSpec");
            }
        }
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void initAlgorithmSpecificParameters(AlgorithmParameters algorithmParameters) throws InvalidAlgorithmParameterException {
        if (!this.mIvRequired) {
            if (algorithmParameters == null) {
                return;
            }
            throw new InvalidAlgorithmParameterException("Unsupported parameters: " + algorithmParameters);
        }
        if (algorithmParameters == null) {
            if (!isEncrypting()) {
                throw new InvalidAlgorithmParameterException("IV required when decrypting. Use IvParameterSpec or AlgorithmParameters to provide it.");
            }
            return;
        }
        if (!"AES".equalsIgnoreCase(algorithmParameters.getAlgorithm())) {
            throw new InvalidAlgorithmParameterException("Unsupported AlgorithmParameters algorithm: " + algorithmParameters.getAlgorithm() + ". Supported: AES");
        }
        try {
            byte[] iv = ((IvParameterSpec) algorithmParameters.getParameterSpec(IvParameterSpec.class)).getIV();
            this.mIv = iv;
            if (iv == null) {
                throw new InvalidAlgorithmParameterException("Null IV in AlgorithmParameters");
            }
        } catch (InvalidParameterSpecException e) {
            if (!isEncrypting()) {
                throw new InvalidAlgorithmParameterException("IV required when decrypting, but not found in parameters: " + algorithmParameters, e);
            }
            this.mIv = null;
        }
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final int getAdditionalEntropyAmountForBegin() {
        return (this.mIvRequired && this.mIv == null && isEncrypting()) ? 16 : 0;
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void addAlgorithmSpecificParametersToBegin(List<KeyParameter> list) {
        byte[] bArr;
        if (isEncrypting() && this.mIvRequired && this.mIvHasBeenUsed) {
            throw new IllegalStateException("IV has already been used. Reusing IV in encryption mode violates security best practices.");
        }
        list.add(KeyStore2ParameterUtils.makeEnum(268435458, 32));
        list.add(KeyStore2ParameterUtils.makeEnum(536870916, this.mKeymasterBlockMode));
        list.add(KeyStore2ParameterUtils.makeEnum(536870918, this.mKeymasterPadding));
        if (!this.mIvRequired || (bArr = this.mIv) == null) {
            return;
        }
        list.add(KeyStore2ParameterUtils.makeBytes(-1879047191, bArr));
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void loadAlgorithmSpecificParametersFromBeginResult(KeyParameter[] keyParameterArr) {
        byte[] blob;
        this.mIvHasBeenUsed = true;
        if (keyParameterArr != null) {
            for (KeyParameter keyParameter : keyParameterArr) {
                if (keyParameter.tag == -1879047191) {
                    blob = keyParameter.value.getBlob();
                    break;
                }
            }
            blob = null;
        } else {
            blob = null;
        }
        if (!this.mIvRequired) {
            if (blob != null) {
                throw new ProviderException("IV in use despite IV not being used by this transformation");
            }
            return;
        }
        byte[] bArr = this.mIv;
        if (bArr == null) {
            this.mIv = blob;
        } else if (blob != null && !Arrays.equals(blob, bArr)) {
            throw new ProviderException("IV in use differs from provided IV");
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final byte[] engineGetIV() {
        return ArrayUtils.cloneIfNotEmpty(this.mIv);
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase, javax.crypto.CipherSpi
    protected final AlgorithmParameters engineGetParameters() throws NoSuchAlgorithmException, InvalidParameterSpecException {
        byte[] bArr;
        if (!this.mIvRequired || (bArr = this.mIv) == null || bArr.length <= 0) {
            return null;
        }
        try {
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("AES");
            algorithmParameters.init(new IvParameterSpec(this.mIv));
            return algorithmParameters;
        } catch (NoSuchAlgorithmException e) {
            throw new ProviderException("Failed to obtain AES AlgorithmParameters", e);
        } catch (InvalidParameterSpecException e2) {
            throw new ProviderException("Failed to initialize AES AlgorithmParameters with an IV", e2);
        }
    }
}

package android.security.keystore2;

import android.hardware.security.keymint.KeyParameter;
import android.security.KeyStoreException;
import android.security.KeyStoreOperation;
import android.security.keystore.ArrayUtils;
import android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.security.mdf.MdfUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import javax.crypto.spec.GCMParameterSpec;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
abstract class AndroidKeyStoreAuthenticatedAESCipherSpi extends AndroidKeyStoreCipherSpiBase {
    private static final int BLOCK_SIZE_BYTES = 16;
    private byte[] mIv;
    private boolean mIvHasBeenUsed;
    private final int mKeymasterBlockMode;
    private final int mKeymasterPadding;

    @Override // javax.crypto.CipherSpi
    protected final int engineGetBlockSize() {
        return 16;
    }

    static abstract class GCM extends AndroidKeyStoreAuthenticatedAESCipherSpi {
        private static final int DEFAULT_TAG_LENGTH_BITS = 128;
        private static final int IV_LENGTH_BYTES = 12;
        private static final int MAX_SUPPORTED_TAG_LENGTH_BITS = 128;
        static final int MIN_SUPPORTED_TAG_LENGTH_BITS = 96;
        private int mTagLengthBits;

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final int getAdditionalEntropyAmountForFinish() {
            return 0;
        }

        GCM(int i) {
            super(32, i);
            this.mTagLengthBits = 128;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final String getTransform() {
            return MdfUtils.MDF_CIPHER_MODE;
        }

        @Override // android.security.keystore2.AndroidKeyStoreAuthenticatedAESCipherSpi, android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void resetAll() {
            this.mTagLengthBits = 128;
            super.resetAll();
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void resetWhilePreservingInitState() {
            super.resetWhilePreservingInitState();
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void initAlgorithmSpecificParameters() throws InvalidKeyException {
            if (!isEncrypting()) {
                throw new InvalidKeyException("IV required when decrypting. Use IvParameterSpec or AlgorithmParameters to provide it.");
            }
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void initAlgorithmSpecificParameters(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
            if (algorithmParameterSpec == null) {
                if (!isEncrypting()) {
                    throw new InvalidAlgorithmParameterException("GCMParameterSpec must be provided when decrypting");
                }
                return;
            }
            if (!(algorithmParameterSpec instanceof GCMParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Only GCMParameterSpec supported");
            }
            GCMParameterSpec gCMParameterSpec = (GCMParameterSpec) algorithmParameterSpec;
            byte[] iv = gCMParameterSpec.getIV();
            if (iv == null) {
                throw new InvalidAlgorithmParameterException("Null IV in GCMParameterSpec");
            }
            if (iv.length != 12) {
                throw new InvalidAlgorithmParameterException("Unsupported IV length: " + iv.length + " bytes. Only 12 bytes long IV supported");
            }
            int tLen = gCMParameterSpec.getTLen();
            if (tLen < 96 || tLen > 128 || tLen % 8 != 0) {
                throw new InvalidAlgorithmParameterException("Unsupported tag length: " + tLen + " bits. Supported lengths: 96, 104, 112, 120, 128");
            }
            setIv(iv);
            this.mTagLengthBits = tLen;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void initAlgorithmSpecificParameters(AlgorithmParameters algorithmParameters) throws InvalidAlgorithmParameterException {
            if (algorithmParameters == null) {
                if (!isEncrypting()) {
                    throw new InvalidAlgorithmParameterException("IV required when decrypting. Use GCMParameterSpec or GCM AlgorithmParameters to provide it.");
                }
                return;
            }
            if (!"GCM".equalsIgnoreCase(algorithmParameters.getAlgorithm())) {
                throw new InvalidAlgorithmParameterException("Unsupported AlgorithmParameters algorithm: " + algorithmParameters.getAlgorithm() + ". Supported: GCM");
            }
            try {
                initAlgorithmSpecificParameters((GCMParameterSpec) algorithmParameters.getParameterSpec(GCMParameterSpec.class));
            } catch (InvalidParameterSpecException e) {
                if (!isEncrypting()) {
                    throw new InvalidAlgorithmParameterException("IV and tag length required when decrypting, but not found in parameters: " + algorithmParameters, e);
                }
                setIv(null);
            }
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase, javax.crypto.CipherSpi
        protected final AlgorithmParameters engineGetParameters() throws NoSuchAlgorithmException, InvalidParameterSpecException {
            byte[] iv = getIv();
            if (iv == null || iv.length <= 0) {
                return null;
            }
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("GCM");
                algorithmParameters.init(new GCMParameterSpec(this.mTagLengthBits, iv));
                return algorithmParameters;
            } catch (NoSuchAlgorithmException e) {
                throw new ProviderException("Failed to obtain GCM AlgorithmParameters", e);
            } catch (InvalidParameterSpecException e2) {
                throw new ProviderException("Failed to initialize GCM AlgorithmParameters", e2);
            }
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected KeyStoreCryptoOperationStreamer createMainDataStreamer(KeyStoreOperation keyStoreOperation) {
            KeyStoreCryptoOperationChunkedStreamer keyStoreCryptoOperationChunkedStreamer = new KeyStoreCryptoOperationChunkedStreamer(new KeyStoreCryptoOperationChunkedStreamer.MainDataStream(keyStoreOperation), 0);
            return isEncrypting() ? keyStoreCryptoOperationChunkedStreamer : new BufferAllOutputUntilDoFinalStreamer(keyStoreCryptoOperationChunkedStreamer);
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final KeyStoreCryptoOperationStreamer createAdditionalAuthenticationDataStreamer(KeyStoreOperation keyStoreOperation) {
            return new KeyStoreCryptoOperationChunkedStreamer(new AdditionalAuthenticationDataStream(keyStoreOperation), 0);
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final int getAdditionalEntropyAmountForBegin() {
            return (getIv() == null && isEncrypting()) ? 12 : 0;
        }

        @Override // android.security.keystore2.AndroidKeyStoreAuthenticatedAESCipherSpi, android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void addAlgorithmSpecificParametersToBegin(List<KeyParameter> list) {
            super.addAlgorithmSpecificParametersToBegin(list);
            list.add(KeyStore2ParameterUtils.makeInt(805307371, this.mTagLengthBits));
        }

        protected final int getTagLengthBits() {
            return this.mTagLengthBits;
        }

        public static final class NoPadding extends GCM {
            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            public /* bridge */ /* synthetic */ void finalize() throws Throwable {
                super.finalize();
            }

            public NoPadding() {
                super(1);
            }

            @Override // javax.crypto.CipherSpi
            protected final int engineGetOutputSize(int i) {
                long consumedInputSizeBytes;
                int tagLengthBits = (getTagLengthBits() + 7) / 8;
                if (isEncrypting()) {
                    consumedInputSizeBytes = (getConsumedInputSizeBytes() - getProducedOutputSizeBytes()) + i + tagLengthBits;
                } else {
                    consumedInputSizeBytes = ((getConsumedInputSizeBytes() - getProducedOutputSizeBytes()) + i) - tagLengthBits;
                }
                if (consumedInputSizeBytes < 0) {
                    return 0;
                }
                if (consumedInputSizeBytes > 2147483647L) {
                    return Integer.MAX_VALUE;
                }
                return (int) consumedInputSizeBytes;
            }
        }
    }

    AndroidKeyStoreAuthenticatedAESCipherSpi(int i, int i2) {
        this.mKeymasterBlockMode = i;
        this.mKeymasterPadding = i2;
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected void resetAll() {
        this.mIv = null;
        this.mIvHasBeenUsed = false;
        super.resetAll();
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
    protected void addAlgorithmSpecificParametersToBegin(List<KeyParameter> list) {
        if (isEncrypting() && this.mIvHasBeenUsed) {
            throw new IllegalStateException("IV has already been used. Reusing IV in encryption mode violates security best practices.");
        }
        list.add(KeyStore2ParameterUtils.makeEnum(268435458, 32));
        list.add(KeyStore2ParameterUtils.makeEnum(536870916, this.mKeymasterBlockMode));
        list.add(KeyStore2ParameterUtils.makeEnum(536870918, this.mKeymasterPadding));
        byte[] bArr = this.mIv;
        if (bArr != null) {
            list.add(KeyStore2ParameterUtils.makeBytes(-1879047191, bArr));
        }
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

    protected void setIv(byte[] bArr) {
        this.mIv = bArr;
    }

    protected byte[] getIv() {
        return this.mIv;
    }

    private static class BufferAllOutputUntilDoFinalStreamer implements KeyStoreCryptoOperationStreamer {
        private ByteArrayOutputStream mBufferedOutput;
        private final KeyStoreCryptoOperationStreamer mDelegate;
        private long mProducedOutputSizeBytes;

        private BufferAllOutputUntilDoFinalStreamer(KeyStoreCryptoOperationStreamer keyStoreCryptoOperationStreamer) {
            this.mBufferedOutput = new ByteArrayOutputStream();
            this.mDelegate = keyStoreCryptoOperationStreamer;
        }

        @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
        public byte[] update(byte[] bArr, int i, int i2) throws KeyStoreException {
            byte[] bArrUpdate = this.mDelegate.update(bArr, i, i2);
            if (bArrUpdate != null) {
                try {
                    this.mBufferedOutput.write(bArrUpdate);
                } catch (IOException e) {
                    throw new ProviderException("Failed to buffer output", e);
                }
            }
            return EmptyArray.BYTE;
        }

        @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
        public byte[] doFinal(byte[] bArr, int i, int i2, byte[] bArr2) throws KeyStoreException {
            byte[] bArrDoFinal = this.mDelegate.doFinal(bArr, i, i2, bArr2);
            if (bArrDoFinal != null) {
                try {
                    this.mBufferedOutput.write(bArrDoFinal);
                } catch (IOException e) {
                    throw new ProviderException("Failed to buffer output", e);
                }
            }
            byte[] byteArray = this.mBufferedOutput.toByteArray();
            this.mBufferedOutput.reset();
            this.mProducedOutputSizeBytes += byteArray.length;
            return byteArray;
        }

        @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
        public long getConsumedInputSizeBytes() {
            return this.mDelegate.getConsumedInputSizeBytes();
        }

        @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
        public long getProducedOutputSizeBytes() {
            return this.mProducedOutputSizeBytes;
        }
    }

    private static class AdditionalAuthenticationDataStream implements KeyStoreCryptoOperationChunkedStreamer.Stream {
        private final KeyStoreOperation mOperation;

        @Override // android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.Stream
        public byte[] finish(byte[] bArr, byte[] bArr2) {
            return null;
        }

        private AdditionalAuthenticationDataStream(KeyStoreOperation keyStoreOperation) {
            this.mOperation = keyStoreOperation;
        }

        @Override // android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.Stream
        public byte[] update(byte[] bArr) throws KeyStoreException {
            this.mOperation.updateAad(bArr);
            return null;
        }
    }
}

package android.security.keystore2;

import android.hardware.security.keymint.KeyParameter;
import android.security.KeyStoreException;
import android.security.KeyStoreOperation;
import android.security.keystore.ArrayUtils;
import android.security.keystore.KeyStoreCryptoOperation;
import android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.util.ArrayList;
import java.util.List;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
abstract class AndroidKeyStoreSignatureSpiBase extends SignatureSpi implements KeyStoreCryptoOperation {
    private static final String TAG = "AndroidKeyStoreSignatureSpiBase";
    private Exception mCachedException;
    private KeyStoreCryptoOperationStreamer mMessageStreamer;
    private Signature mSignature;
    private KeyStoreOperation mOperation = null;
    private long mOperationChallenge = 0;
    private boolean mSigning = false;
    private AndroidKeyStoreKey mKey = null;

    protected abstract void addAlgorithmSpecificParametersToBegin(List<KeyParameter> list);

    protected abstract int getAdditionalEntropyAmountForSign();

    protected abstract String getAlgorithm();

    AndroidKeyStoreSignatureSpiBase() {
        this.appRandom = null;
        this.mMessageStreamer = null;
        this.mCachedException = null;
        this.mSignature = null;
    }

    @Override // java.security.SignatureSpi
    protected final void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        engineInitSign(privateKey, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.security.SignatureSpi
    protected final void engineInitSign(PrivateKey privateKey, SecureRandom secureRandom) throws InvalidKeyException {
        resetAll();
        try {
            if (privateKey == 0) {
                throw new InvalidKeyException("Unsupported key: null");
            }
            if (!(privateKey instanceof AndroidKeyStorePrivateKey)) {
                throw new InvalidKeyException("Unsupported private key type: " + privateKey);
            }
            this.mSigning = true;
            initKey((AndroidKeyStoreKey) privateKey);
            this.appRandom = secureRandom;
            ensureKeystoreOperationInitialized();
        } catch (Throwable th) {
            resetAll();
            throw th;
        }
    }

    @Override // java.security.SignatureSpi
    protected final void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        resetAll();
        try {
            Signature signature = Signature.getInstance(getAlgorithm());
            this.mSignature = signature;
            signature.initVerify(publicKey);
        } catch (NoSuchAlgorithmException e) {
            throw new InvalidKeyException(e);
        }
    }

    protected void initKey(AndroidKeyStoreKey androidKeyStoreKey) throws InvalidKeyException {
        this.mKey = androidKeyStoreKey;
    }

    private void abortOperation() {
        KeyStoreCryptoOperationUtils.abortOperation(this.mOperation);
        this.mOperation = null;
    }

    protected void resetAll() {
        abortOperation();
        this.mOperationChallenge = 0L;
        this.mSigning = false;
        this.mKey = null;
        this.appRandom = null;
        this.mMessageStreamer = null;
        this.mCachedException = null;
    }

    protected void resetWhilePreservingInitState() {
        abortOperation();
        this.mOperationChallenge = 0L;
        this.mMessageStreamer = null;
        this.mCachedException = null;
    }

    private void ensureKeystoreOperationInitialized() throws InvalidKeyException {
        if (this.mMessageStreamer == null && this.mCachedException == null) {
            if (this.mKey == null) {
                throw new IllegalStateException("Not initialized");
            }
            ArrayList arrayList = new ArrayList();
            addAlgorithmSpecificParametersToBegin(arrayList);
            arrayList.add(KeyStore2ParameterUtils.makeEnum(536870913, this.mSigning ? 2 : 3));
            try {
                KeyStoreOperation createOperation = this.mKey.getSecurityLevel().createOperation(this.mKey.getKeyIdDescriptor(), arrayList);
                this.mOperation = createOperation;
                this.mOperationChallenge = KeyStoreCryptoOperationUtils.getOrMakeOperationChallenge(createOperation, this.mKey);
                this.mMessageStreamer = createMainDataStreamer(this.mOperation);
            } catch (KeyStoreException e) {
                throw KeyStoreCryptoOperationUtils.getInvalidKeyException(this.mKey, e);
            }
        }
    }

    protected KeyStoreCryptoOperationStreamer createMainDataStreamer(KeyStoreOperation keyStoreOperation) {
        return new KeyStoreCryptoOperationChunkedStreamer(new KeyStoreCryptoOperationChunkedStreamer.MainDataStream(keyStoreOperation));
    }

    @Override // android.security.keystore.KeyStoreCryptoOperation
    public final long getOperationHandle() {
        return this.mOperationChallenge;
    }

    @Override // java.security.SignatureSpi
    protected final void engineUpdate(byte[] bArr, int i, int i2) throws SignatureException {
        Signature signature = this.mSignature;
        if (signature != null) {
            signature.update(bArr, i, i2);
            return;
        }
        if (this.mCachedException != null) {
            throw new SignatureException(this.mCachedException);
        }
        try {
            ensureKeystoreOperationInitialized();
            if (i2 == 0) {
                return;
            }
            try {
                byte[] update = this.mMessageStreamer.update(bArr, i, i2);
                if (update.length == 0) {
                    return;
                }
                throw new ProviderException("Update operation unexpectedly produced output: " + update.length + " bytes");
            } catch (KeyStoreException e) {
                throw new SignatureException(e);
            }
        } catch (InvalidKeyException e2) {
            throw new SignatureException(e2);
        }
    }

    @Override // java.security.SignatureSpi
    protected final void engineUpdate(byte b) throws SignatureException {
        engineUpdate(new byte[]{b}, 0, 1);
    }

    @Override // java.security.SignatureSpi
    protected final void engineUpdate(ByteBuffer byteBuffer) {
        byte[] bArr;
        int i;
        int remaining = byteBuffer.remaining();
        if (byteBuffer.hasArray()) {
            bArr = byteBuffer.array();
            i = byteBuffer.arrayOffset() + byteBuffer.position();
            byteBuffer.position(byteBuffer.limit());
        } else {
            bArr = new byte[remaining];
            byteBuffer.get(bArr);
            i = 0;
        }
        try {
            engineUpdate(bArr, i, remaining);
        } catch (SignatureException e) {
            this.mCachedException = e;
        }
    }

    @Override // java.security.SignatureSpi
    protected final int engineSign(byte[] bArr, int i, int i2) throws SignatureException {
        return super.engineSign(bArr, i, i2);
    }

    @Override // java.security.SignatureSpi
    protected final byte[] engineSign() throws SignatureException {
        if (this.mCachedException != null) {
            throw new SignatureException(this.mCachedException);
        }
        try {
            ensureKeystoreOperationInitialized();
            KeyStoreCryptoOperationUtils.getRandomBytesToMixIntoKeystoreRng(this.appRandom, getAdditionalEntropyAmountForSign());
            byte[] doFinal = this.mMessageStreamer.doFinal(EmptyArray.BYTE, 0, 0, null);
            resetWhilePreservingInitState();
            return doFinal;
        } catch (KeyStoreException | InvalidKeyException e) {
            throw new SignatureException(e);
        }
    }

    @Override // java.security.SignatureSpi
    protected final boolean engineVerify(byte[] bArr) throws SignatureException {
        Signature signature = this.mSignature;
        if (signature != null) {
            return signature.verify(bArr);
        }
        throw new IllegalStateException("Not initialised.");
    }

    @Override // java.security.SignatureSpi
    protected final boolean engineVerify(byte[] bArr, int i, int i2) throws SignatureException {
        return engineVerify(ArrayUtils.subarray(bArr, i, i2));
    }

    @Override // java.security.SignatureSpi
    @Deprecated
    protected final Object engineGetParameter(String str) throws InvalidParameterException {
        throw new InvalidParameterException();
    }

    @Override // java.security.SignatureSpi
    @Deprecated
    protected final void engineSetParameter(String str, Object obj) throws InvalidParameterException {
        throw new InvalidParameterException();
    }

    protected final boolean isSigning() {
        return this.mSigning;
    }
}

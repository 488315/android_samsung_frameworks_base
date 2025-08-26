package android.security.keystore2;

import android.os.StrictMode;
import android.security.KeyStoreException;
import android.security.KeyStoreOperation;
import android.security.keystore.KeyStoreCryptoOperation;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.ECKey;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import javax.crypto.KeyAgreementSpi;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes3.dex */
public class AndroidKeyStoreKeyAgreementSpi extends KeyAgreementSpi implements KeyStoreCryptoOperation {
    private static final String TAG = "AndroidKeyStoreKeyAgreementSpi";
    private AndroidKeyStorePrivateKey mKey;
    private final int mKeymintAlgorithm;
    private KeyStoreOperation mOperation;
    private long mOperationHandle;
    private PublicKey mOtherPartyKey;

    public static class ECDH extends AndroidKeyStoreKeyAgreementSpi {
        public ECDH() {
            super(3);
        }
    }

    public static class XDH extends AndroidKeyStoreKeyAgreementSpi {
        public XDH() {
            super(3);
        }
    }

    protected AndroidKeyStoreKeyAgreementSpi(int i) {
        resetAll();
        this.mKeymintAlgorithm = i;
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected void engineInit(Key key, SecureRandom secureRandom) throws InvalidKeyException {
        if (key == null) {
            throw new InvalidKeyException("key == null");
        }
        if (!(key instanceof AndroidKeyStorePrivateKey)) {
            throw new InvalidKeyException("Only Android KeyStore private keys supported. Key: " + key);
        }
        this.mKey = (AndroidKeyStorePrivateKey) key;
        try {
            ensureKeystoreOperationInitialized();
        } finally {
            resetAll();
        }
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameterSpec != null) {
            throw new InvalidAlgorithmParameterException("Unsupported algorithm parameters: " + algorithmParameterSpec);
        }
        engineInit(key, secureRandom);
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected Key engineDoPhase(Key key, boolean z) throws IllegalStateException, InvalidKeyException {
        ensureKeystoreOperationInitialized();
        if (key == null) {
            throw new InvalidKeyException("key == null");
        }
        if (!(key instanceof PublicKey)) {
            throw new InvalidKeyException("Only public keys supported. Key: " + key);
        }
        AndroidKeyStorePrivateKey androidKeyStorePrivateKey = this.mKey;
        if ((androidKeyStorePrivateKey instanceof ECKey) && !(key instanceof ECKey)) {
            throw new InvalidKeyException("Public and Private key should be of the same type.");
        }
        if ((androidKeyStorePrivateKey instanceof ECKey) && !((ECKey) key).getParams().getCurve().equals(((ECKey) this.mKey).getParams().getCurve())) {
            throw new InvalidKeyException("Public and Private key parameters should be same.");
        }
        if (!z) {
            throw new IllegalStateException("Only one other party supported. lastPhase must be set to true.");
        }
        if (this.mOtherPartyKey != null) {
            throw new IllegalStateException("Only one other party supported. doPhase() must only be called exactly once.");
        }
        this.mOtherPartyKey = (PublicKey) key;
        return null;
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected byte[] engineGenerateSecret() throws IllegalStateException {
        try {
            ensureKeystoreOperationInitialized();
            PublicKey publicKey = this.mOtherPartyKey;
            if (publicKey == null) {
                throw new IllegalStateException("Other party key not provided. Call doPhase() first.");
            }
            byte[] encoded = publicKey.getEncoded();
            StrictMode.noteSlowCall("engineGenerateSecret");
            try {
                try {
                    return this.mOperation.finish(encoded, null);
                } catch (KeyStoreException e) {
                    throw new ProviderException("Keystore operation failed", e);
                }
            } finally {
                resetWhilePreservingInitState();
            }
        } catch (InvalidKeyException e2) {
            throw new IllegalStateException("Not initialized", e2);
        }
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected SecretKey engineGenerateSecret(String str) throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException {
        return new SecretKeySpec(engineGenerateSecret(), str);
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected int engineGenerateSecret(byte[] bArr, int i) throws IllegalStateException, ShortBufferException {
        byte[] bArrEngineGenerateSecret = engineGenerateSecret();
        if (bArrEngineGenerateSecret.length > bArr.length - i) {
            throw new ShortBufferException("Needed: " + bArrEngineGenerateSecret.length);
        }
        System.arraycopy(bArrEngineGenerateSecret, 0, bArr, i, bArrEngineGenerateSecret.length);
        return bArrEngineGenerateSecret.length;
    }

    @Override // android.security.keystore.KeyStoreCryptoOperation
    public long getOperationHandle() {
        return this.mOperationHandle;
    }

    protected void finalize() throws Throwable {
        try {
            resetAll();
        } finally {
            super.finalize();
        }
    }

    private void resetWhilePreservingInitState() {
        KeyStoreCryptoOperationUtils.abortOperation(this.mOperation);
        this.mOperationHandle = 0L;
        this.mOperation = null;
        this.mOtherPartyKey = null;
    }

    private void resetAll() {
        resetWhilePreservingInitState();
        this.mKey = null;
    }

    private void ensureKeystoreOperationInitialized() throws IllegalStateException, InvalidKeyException {
        if (this.mKey == null) {
            throw new IllegalStateException("Not initialized");
        }
        if (this.mOperation != null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(KeyStore2ParameterUtils.makeEnum(536870913, 6));
        StrictMode.noteDiskWrite();
        try {
            this.mOperation = this.mKey.getSecurityLevel().createOperation(this.mKey.getKeyIdDescriptor(), arrayList);
        } catch (KeyStoreException e) {
            InvalidKeyException invalidKeyException = KeyStoreCryptoOperationUtils.getInvalidKeyException(this.mKey, e);
            if (invalidKeyException != null) {
                throw invalidKeyException;
            }
        }
        this.mOperationHandle = KeyStoreCryptoOperationUtils.getOrMakeOperationChallenge(this.mOperation, this.mKey);
    }
}

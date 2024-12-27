package android.hardware.biometrics;

import android.security.identity.IdentityCredential;
import android.security.identity.PresentationSession;
import android.security.keystore2.AndroidKeyStoreProvider;

import java.security.Signature;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.Mac;

/* loaded from: classes2.dex */
public class CryptoObject {
    private final Object mCrypto;

    public CryptoObject(Signature signature) {
        this.mCrypto = signature;
    }

    public CryptoObject(Cipher cipher) {
        this.mCrypto = cipher;
    }

    public CryptoObject(Mac mac) {
        this.mCrypto = mac;
    }

    @Deprecated
    public CryptoObject(IdentityCredential credential) {
        this.mCrypto = credential;
    }

    public CryptoObject(PresentationSession session) {
        this.mCrypto = session;
    }

    public CryptoObject(KeyAgreement keyAgreement) {
        this.mCrypto = keyAgreement;
    }

    public CryptoObject(long operationHandle) {
        this.mCrypto = Long.valueOf(operationHandle);
    }

    public Signature getSignature() {
        if (this.mCrypto instanceof Signature) {
            return (Signature) this.mCrypto;
        }
        return null;
    }

    public Cipher getCipher() {
        if (this.mCrypto instanceof Cipher) {
            return (Cipher) this.mCrypto;
        }
        return null;
    }

    public Mac getMac() {
        if (this.mCrypto instanceof Mac) {
            return (Mac) this.mCrypto;
        }
        return null;
    }

    @Deprecated
    public IdentityCredential getIdentityCredential() {
        if (this.mCrypto instanceof IdentityCredential) {
            return (IdentityCredential) this.mCrypto;
        }
        return null;
    }

    public PresentationSession getPresentationSession() {
        if (this.mCrypto instanceof PresentationSession) {
            return (PresentationSession) this.mCrypto;
        }
        return null;
    }

    public KeyAgreement getKeyAgreement() {
        if (this.mCrypto instanceof KeyAgreement) {
            return (KeyAgreement) this.mCrypto;
        }
        return null;
    }

    public long getOpId() {
        if (this.mCrypto == null) {
            return 0L;
        }
        if (this.mCrypto instanceof Long) {
            return ((Long) this.mCrypto).longValue();
        }
        if (this.mCrypto instanceof IdentityCredential) {
            return ((IdentityCredential) this.mCrypto).getCredstoreOperationHandle();
        }
        if (this.mCrypto instanceof PresentationSession) {
            return ((PresentationSession) this.mCrypto).getCredstoreOperationHandle();
        }
        return AndroidKeyStoreProvider.getKeyStoreOperationHandle(this.mCrypto);
    }
}

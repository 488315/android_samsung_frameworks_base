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
    public CryptoObject(IdentityCredential identityCredential) {
        this.mCrypto = identityCredential;
    }

    public CryptoObject(PresentationSession presentationSession) {
        this.mCrypto = presentationSession;
    }

    public CryptoObject(KeyAgreement keyAgreement) {
        this.mCrypto = keyAgreement;
    }

    public CryptoObject(long j) {
        this.mCrypto = Long.valueOf(j);
    }

    public Signature getSignature() {
        Object obj = this.mCrypto;
        if (obj instanceof Signature) {
            return (Signature) obj;
        }
        return null;
    }

    public Cipher getCipher() {
        Object obj = this.mCrypto;
        if (obj instanceof Cipher) {
            return (Cipher) obj;
        }
        return null;
    }

    public Mac getMac() {
        Object obj = this.mCrypto;
        if (obj instanceof Mac) {
            return (Mac) obj;
        }
        return null;
    }

    @Deprecated
    public IdentityCredential getIdentityCredential() {
        Object obj = this.mCrypto;
        if (obj instanceof IdentityCredential) {
            return (IdentityCredential) obj;
        }
        return null;
    }

    public PresentationSession getPresentationSession() {
        Object obj = this.mCrypto;
        if (obj instanceof PresentationSession) {
            return (PresentationSession) obj;
        }
        return null;
    }

    public KeyAgreement getKeyAgreement() {
        Object obj = this.mCrypto;
        if (obj instanceof KeyAgreement) {
            return (KeyAgreement) obj;
        }
        return null;
    }

    public long getOpId() {
        Object obj = this.mCrypto;
        if (obj == null) {
            return 0L;
        }
        if (obj instanceof Long) {
            return ((Long) obj).longValue();
        }
        if (obj instanceof IdentityCredential) {
            return ((IdentityCredential) obj).getCredstoreOperationHandle();
        }
        if (obj instanceof PresentationSession) {
            return ((PresentationSession) obj).getCredstoreOperationHandle();
        }
        return AndroidKeyStoreProvider.getKeyStoreOperationHandle(obj);
    }
}

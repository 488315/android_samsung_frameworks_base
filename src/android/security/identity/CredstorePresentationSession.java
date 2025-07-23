package android.security.identity;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.security.KeyChain;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes3.dex */
class CredstorePresentationSession extends PresentationSession {
    private static final String TAG = "CredstorePresentationSession";
    private ISession mBinder;
    private int mCipherSuite;
    private Context mContext;
    private int mFeatureVersion;
    private CredstoreIdentityCredentialStore mStore;
    private Map<String, CredstoreIdentityCredential> mCredentialCache = new LinkedHashMap();
    private KeyPair mEphemeralKeyPair = null;
    private byte[] mSessionTranscript = null;
    private boolean mOperationHandleSet = false;
    private long mOperationHandle = 0;

    CredstorePresentationSession(Context context, int i, CredstoreIdentityCredentialStore credstoreIdentityCredentialStore, ISession iSession, int i2) {
        this.mContext = context;
        this.mCipherSuite = i;
        this.mStore = credstoreIdentityCredentialStore;
        this.mBinder = iSession;
        this.mFeatureVersion = i2;
    }

    private void ensureEphemeralKeyPair() {
        if (this.mEphemeralKeyPair != null) {
            return;
        }
        try {
            byte[] ephemeralKeyPair = this.mBinder.getEphemeralKeyPair();
            char[] cArr = new char[0];
            KeyStore keyStore = KeyStore.getInstance(KeyChain.EXTRA_PKCS12);
            keyStore.load(new ByteArrayInputStream(ephemeralKeyPair), cArr);
            this.mEphemeralKeyPair = new KeyPair(keyStore.getCertificate("ephemeralKey").getPublicKey(), (PrivateKey) keyStore.getKey("ephemeralKey", cArr));
        } catch (RemoteException | IOException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException e) {
            throw new RuntimeException("Unexpected exception ", e);
        } catch (ServiceSpecificException e2) {
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.PresentationSession
    public KeyPair getEphemeralKeyPair() {
        ensureEphemeralKeyPair();
        return this.mEphemeralKeyPair;
    }

    @Override // android.security.identity.PresentationSession
    public void setReaderEphemeralPublicKey(PublicKey publicKey) throws InvalidKeyException {
        try {
            this.mBinder.setReaderEphemeralPublicKey(Util.publicKeyEncodeUncompressedForm(publicKey));
        } catch (RemoteException e) {
            throw new RuntimeException("Unexpected RemoteException ", e);
        } catch (ServiceSpecificException e2) {
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.PresentationSession
    public void setSessionTranscript(byte[] bArr) {
        try {
            this.mBinder.setSessionTranscript(bArr);
            this.mSessionTranscript = bArr;
        } catch (RemoteException e) {
            throw new RuntimeException("Unexpected RemoteException ", e);
        } catch (ServiceSpecificException e2) {
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.PresentationSession
    public CredentialDataResult getCredentialData(String str, CredentialDataRequest credentialDataRequest) throws NoAuthenticationKeyAvailableException, InvalidReaderSignatureException, InvalidRequestMessageException, EphemeralPublicKeyNotFoundException {
        CredstorePresentationSession credstorePresentationSession;
        try {
            CredstoreIdentityCredential credstoreIdentityCredential = this.mCredentialCache.get(str);
            if (credstoreIdentityCredential == null) {
                credstorePresentationSession = this;
                CredstoreIdentityCredential credstoreIdentityCredential2 = new CredstoreIdentityCredential(this.mContext, str, this.mCipherSuite, this.mBinder.getCredentialForPresentation(str), credstorePresentationSession, this.mFeatureVersion);
                credstorePresentationSession.mCredentialCache.put(str, credstoreIdentityCredential2);
                credstoreIdentityCredential2.setAllowUsingExhaustedKeys(credentialDataRequest.isAllowUsingExhaustedKeys());
                credstoreIdentityCredential2.setAllowUsingExpiredKeys(credentialDataRequest.isAllowUsingExpiredKeys());
                credstoreIdentityCredential2.setIncrementKeyUsageCount(credentialDataRequest.isIncrementUseCount());
                credstoreIdentityCredential = credstoreIdentityCredential2;
            } else {
                credstorePresentationSession = this;
            }
            return new CredstoreCredentialDataResult(credstoreIdentityCredential.getEntries(credentialDataRequest.getRequestMessage(), credentialDataRequest.getDeviceSignedEntriesToRequest(), credstorePresentationSession.mSessionTranscript, credentialDataRequest.getReaderSignature()), credstoreIdentityCredential.getEntries(credentialDataRequest.getRequestMessage(), credentialDataRequest.getIssuerSignedEntriesToRequest(), credstorePresentationSession.mSessionTranscript, credentialDataRequest.getReaderSignature()));
        } catch (RemoteException e) {
            throw new RuntimeException("Unexpected RemoteException ", e);
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                return null;
            }
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        } catch (SessionTranscriptMismatchException e3) {
            throw new RuntimeException("Unexpected ", e3);
        }
    }

    @Override // android.security.identity.PresentationSession
    public long getCredstoreOperationHandle() {
        if (!this.mOperationHandleSet) {
            try {
                this.mOperationHandle = this.mBinder.getAuthChallenge();
                this.mOperationHandleSet = true;
            } catch (RemoteException e) {
                throw new RuntimeException("Unexpected RemoteException ", e);
            } catch (ServiceSpecificException e2) {
                int i = e2.errorCode;
                throw new RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
            }
        }
        return this.mOperationHandle;
    }
}

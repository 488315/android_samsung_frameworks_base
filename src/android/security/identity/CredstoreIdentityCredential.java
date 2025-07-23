package android.security.identity;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.security.KeyChain;
import android.security.identity.CredstoreResultData;
import com.samsung.android.security.mdf.MdfUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes3.dex */
class CredstoreIdentityCredential extends IdentityCredential {
    private static final String TAG = "CredstoreIdentityCredential";
    private ICredential mBinder;
    private int mCipherSuite;
    private Context mContext;
    private String mCredentialName;
    private int mEphemeralCounter;
    private int mFeatureVersion;
    private int mReadersExpectedEphemeralCounter;
    private CredstorePresentationSession mSession;
    private KeyPair mEphemeralKeyPair = null;
    private SecretKey mSecretKey = null;
    private SecretKey mReaderSecretKey = null;
    private boolean mAllowUsingExhaustedKeys = true;
    private boolean mAllowUsingExpiredKeys = false;
    private boolean mIncrementKeyUsageCount = true;
    private boolean mOperationHandleSet = false;
    private long mOperationHandle = 0;

    CredstoreIdentityCredential(Context context, String str, int i, ICredential iCredential, CredstorePresentationSession credstorePresentationSession, int i2) {
        this.mContext = context;
        this.mCredentialName = str;
        this.mCipherSuite = i;
        this.mBinder = iCredential;
        this.mSession = credstorePresentationSession;
        this.mFeatureVersion = i2;
    }

    private void ensureEphemeralKeyPair() {
        if (this.mEphemeralKeyPair != null) {
            return;
        }
        try {
            byte[] createEphemeralKeyPair = this.mBinder.createEphemeralKeyPair();
            char[] cArr = new char[0];
            KeyStore keyStore = KeyStore.getInstance(KeyChain.EXTRA_PKCS12);
            keyStore.load(new ByteArrayInputStream(createEphemeralKeyPair), cArr);
            this.mEphemeralKeyPair = new KeyPair(keyStore.getCertificate("ephemeralKey").getPublicKey(), (PrivateKey) keyStore.getKey("ephemeralKey", cArr));
        } catch (RemoteException e) {
            throw new RuntimeException("Unexpected RemoteException ", e);
        } catch (ServiceSpecificException e2) {
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException e3) {
            throw new RuntimeException("Unexpected exception ", e3);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public KeyPair createEphemeralKeyPair() {
        ensureEphemeralKeyPair();
        return this.mEphemeralKeyPair;
    }

    @Override // android.security.identity.IdentityCredential
    public void setReaderEphemeralPublicKey(PublicKey publicKey) throws InvalidKeyException {
        try {
            this.mBinder.setReaderEphemeralPublicKey(Util.publicKeyEncodeUncompressedForm(publicKey));
            ensureEphemeralKeyPair();
            try {
                KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH");
                keyAgreement.init(this.mEphemeralKeyPair.getPrivate());
                keyAgreement.doPhase(publicKey, true);
                byte[] generateSecret = keyAgreement.generateSecret();
                byte[] bArr = new byte[0];
                byte[] bArr2 = {1};
                this.mSecretKey = new SecretKeySpec(Util.computeHkdf("HmacSha256", generateSecret, bArr2, bArr, 32), "AES");
                bArr2[0] = 0;
                this.mReaderSecretKey = new SecretKeySpec(Util.computeHkdf("HmacSha256", generateSecret, bArr2, bArr, 32), "AES");
                this.mEphemeralCounter = 1;
                this.mReadersExpectedEphemeralCounter = 1;
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error performing key agreement", e);
            }
        } catch (RemoteException e2) {
            throw new RuntimeException("Unexpected RemoteException ", e2);
        } catch (ServiceSpecificException e3) {
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e3.errorCode, e3);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public byte[] encryptMessageToReader(byte[] bArr) {
        try {
            ByteBuffer allocate = ByteBuffer.allocate(12);
            allocate.putInt(0, 0);
            allocate.putInt(4, 1);
            allocate.putInt(8, this.mEphemeralCounter);
            Cipher cipher = Cipher.getInstance(MdfUtils.MDF_CIPHER_MODE);
            cipher.init(1, this.mSecretKey, new GCMParameterSpec(128, allocate.array()));
            byte[] doFinal = cipher.doFinal(bArr);
            this.mEphemeralCounter++;
            return doFinal;
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new RuntimeException("Error encrypting message", e);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public byte[] decryptMessageFromReader(byte[] bArr) throws MessageDecryptionException {
        ByteBuffer allocate = ByteBuffer.allocate(12);
        allocate.putInt(0, 0);
        allocate.putInt(4, 0);
        allocate.putInt(8, this.mReadersExpectedEphemeralCounter);
        try {
            Cipher cipher = Cipher.getInstance(MdfUtils.MDF_CIPHER_MODE);
            cipher.init(2, this.mReaderSecretKey, new GCMParameterSpec(128, allocate.array()));
            byte[] doFinal = cipher.doFinal(bArr);
            this.mReadersExpectedEphemeralCounter++;
            return doFinal;
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new MessageDecryptionException("Error decrypting message", e);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public Collection<X509Certificate> getCredentialKeyCertificateChain() {
        try {
            try {
                Collection<? extends Certificate> generateCertificates = CertificateFactory.getInstance("X.509").generateCertificates(new ByteArrayInputStream(this.mBinder.getCredentialKeyCertificateChain()));
                ArrayList arrayList = new ArrayList();
                Iterator<? extends Certificate> it = generateCertificates.iterator();
                while (it.hasNext()) {
                    arrayList.add((X509Certificate) it.next());
                }
                return arrayList;
            } catch (CertificateException e) {
                throw new RuntimeException("Error decoding certificates", e);
            }
        } catch (RemoteException e2) {
            throw new RuntimeException("Unexpected RemoteException ", e2);
        } catch (ServiceSpecificException e3) {
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e3.errorCode, e3);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public void setAllowUsingExhaustedKeys(boolean z) {
        this.mAllowUsingExhaustedKeys = z;
    }

    @Override // android.security.identity.IdentityCredential
    public void setAllowUsingExpiredKeys(boolean z) {
        this.mAllowUsingExpiredKeys = z;
    }

    @Override // android.security.identity.IdentityCredential
    public void setIncrementKeyUsageCount(boolean z) {
        this.mIncrementKeyUsageCount = z;
    }

    @Override // android.security.identity.IdentityCredential
    public long getCredstoreOperationHandle() {
        if (!this.mOperationHandleSet) {
            try {
                this.mOperationHandle = this.mBinder.selectAuthKey(this.mAllowUsingExhaustedKeys, this.mAllowUsingExpiredKeys, this.mIncrementKeyUsageCount);
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

    @Override // android.security.identity.IdentityCredential
    public ResultData getEntries(byte[] bArr, Map<String, Collection<String>> map, byte[] bArr2, byte[] bArr3) throws SessionTranscriptMismatchException, NoAuthenticationKeyAvailableException, InvalidReaderSignatureException, EphemeralPublicKeyNotFoundException, InvalidRequestMessageException {
        RequestNamespaceParcel[] requestNamespaceParcelArr = new RequestNamespaceParcel[map.size()];
        int i = 0;
        for (String str : map.keySet()) {
            Collection<String> collection = map.get(str);
            RequestNamespaceParcel requestNamespaceParcel = new RequestNamespaceParcel();
            requestNamespaceParcelArr[i] = requestNamespaceParcel;
            requestNamespaceParcel.namespaceName = str;
            requestNamespaceParcelArr[i].entries = new RequestEntryParcel[collection.size()];
            int i2 = 0;
            for (String str2 : collection) {
                requestNamespaceParcelArr[i].entries[i2] = new RequestEntryParcel();
                requestNamespaceParcelArr[i].entries[i2].name = str2;
                i2++;
            }
            i++;
        }
        try {
            ICredential iCredential = this.mBinder;
            if (bArr == null) {
                bArr = new byte[0];
            }
            byte[] bArr4 = bArr;
            if (bArr2 == null) {
                bArr2 = new byte[0];
            }
            byte[] bArr5 = bArr2;
            if (bArr3 == null) {
                bArr3 = new byte[0];
            }
            GetEntriesResultParcel entries = iCredential.getEntries(bArr4, requestNamespaceParcelArr, bArr5, bArr3, this.mAllowUsingExhaustedKeys, this.mAllowUsingExpiredKeys, this.mIncrementKeyUsageCount);
            byte[] bArr6 = entries.signature;
            byte[] bArr7 = (bArr6 == null || bArr6.length != 0) ? bArr6 : null;
            byte[] bArr8 = entries.mac;
            CredstoreResultData.Builder builder = new CredstoreResultData.Builder(this.mFeatureVersion, entries.staticAuthenticationData, entries.deviceNameSpaces, (bArr8 == null || bArr8.length != 0) ? bArr8 : null, bArr7);
            for (ResultNamespaceParcel resultNamespaceParcel : entries.resultNamespaces) {
                for (ResultEntryParcel resultEntryParcel : resultNamespaceParcel.entries) {
                    if (resultEntryParcel.status == 0) {
                        builder.addEntry(resultNamespaceParcel.namespaceName, resultEntryParcel.name, resultEntryParcel.value);
                    } else {
                        builder.addErrorStatus(resultNamespaceParcel.namespaceName, resultEntryParcel.name, resultEntryParcel.status);
                    }
                }
            }
            return builder.build();
        } catch (RemoteException e) {
            throw new RuntimeException("Unexpected RemoteException ", e);
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 5) {
                throw new EphemeralPublicKeyNotFoundException(e2.getMessage(), e2);
            }
            if (e2.errorCode == 7) {
                throw new InvalidReaderSignatureException(e2.getMessage(), e2);
            }
            if (e2.errorCode == 6) {
                throw new NoAuthenticationKeyAvailableException(e2.getMessage(), e2);
            }
            if (e2.errorCode == 10) {
                throw new InvalidRequestMessageException(e2.getMessage(), e2);
            }
            if (e2.errorCode == 11) {
                throw new SessionTranscriptMismatchException(e2.getMessage(), e2);
            }
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public void setAvailableAuthenticationKeys(int i, int i2) {
        setAvailableAuthenticationKeys(i, i2, 0L);
    }

    @Override // android.security.identity.IdentityCredential
    public void setAvailableAuthenticationKeys(int i, int i2, long j) {
        try {
            this.mBinder.setAvailableAuthenticationKeys(i, i2, j);
        } catch (RemoteException e) {
            throw new RuntimeException("Unexpected RemoteException ", e);
        } catch (ServiceSpecificException e2) {
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public Collection<X509Certificate> getAuthKeysNeedingCertification() {
        try {
            AuthKeyParcel[] authKeysNeedingCertification = this.mBinder.getAuthKeysNeedingCertification();
            ArrayList arrayList = new ArrayList();
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            for (AuthKeyParcel authKeyParcel : authKeysNeedingCertification) {
                Collection<? extends Certificate> generateCertificates = certificateFactory.generateCertificates(new ByteArrayInputStream(authKeyParcel.x509cert));
                if (generateCertificates.size() != 1) {
                    throw new RuntimeException("Returned blob yields more than one X509 cert");
                }
                arrayList.add((X509Certificate) generateCertificates.iterator().next());
            }
            return arrayList;
        } catch (RemoteException e) {
            throw new RuntimeException("Unexpected RemoteException ", e);
        } catch (ServiceSpecificException e2) {
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        } catch (CertificateException e3) {
            throw new RuntimeException("Error decoding authenticationKey", e3);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public void storeStaticAuthenticationData(X509Certificate x509Certificate, byte[] bArr) throws UnknownAuthenticationKeyException {
        try {
            AuthKeyParcel authKeyParcel = new AuthKeyParcel();
            authKeyParcel.x509cert = x509Certificate.getEncoded();
            this.mBinder.storeStaticAuthenticationData(authKeyParcel, bArr);
        } catch (RemoteException e) {
            throw new RuntimeException("Unexpected RemoteException ", e);
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 9) {
                throw new UnknownAuthenticationKeyException(e2.getMessage(), e2);
            }
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        } catch (CertificateEncodingException e3) {
            throw new RuntimeException("Error encoding authenticationKey", e3);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public void storeStaticAuthenticationData(X509Certificate x509Certificate, Instant instant, byte[] bArr) throws UnknownAuthenticationKeyException {
        try {
            AuthKeyParcel authKeyParcel = new AuthKeyParcel();
            authKeyParcel.x509cert = x509Certificate.getEncoded();
            this.mBinder.storeStaticAuthenticationDataWithExpiration(authKeyParcel, (instant.getEpochSecond() * 1000) + (instant.getNano() / 1000000), bArr);
        } catch (RemoteException e) {
            throw new RuntimeException("Unexpected RemoteException ", e);
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 12) {
                throw new UnsupportedOperationException("Not supported", e2);
            }
            if (e2.errorCode == 9) {
                throw new UnknownAuthenticationKeyException(e2.getMessage(), e2);
            }
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        } catch (CertificateEncodingException e3) {
            throw new RuntimeException("Error encoding authenticationKey", e3);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public int[] getAuthenticationDataUsageCount() {
        try {
            return this.mBinder.getAuthenticationDataUsageCount();
        } catch (RemoteException e) {
            throw new RuntimeException("Unexpected RemoteException ", e);
        } catch (ServiceSpecificException e2) {
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public List<AuthenticationKeyMetadata> getAuthenticationKeyMetadata() {
        try {
            int[] authenticationDataUsageCount = this.mBinder.getAuthenticationDataUsageCount();
            long[] authenticationDataExpirations = this.mBinder.getAuthenticationDataExpirations();
            if (authenticationDataUsageCount.length != authenticationDataExpirations.length) {
                throw new IllegalStateException("Size og usageCount and expirationMillis differ");
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < authenticationDataExpirations.length; i++) {
                long j = authenticationDataExpirations[i];
                arrayList.add(j != Long.MAX_VALUE ? new AuthenticationKeyMetadata(authenticationDataUsageCount[i], Instant.ofEpochMilli(j)) : null);
            }
            return arrayList;
        } catch (RemoteException e) {
            throw new IllegalStateException("Unexpected RemoteException ", e);
        } catch (ServiceSpecificException e2) {
            throw new IllegalStateException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public byte[] proveOwnership(byte[] bArr) {
        try {
            return this.mBinder.proveOwnership(bArr);
        } catch (RemoteException e) {
            throw new RuntimeException("Unexpected RemoteException ", e);
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 12) {
                throw new UnsupportedOperationException("Not supported", e2);
            }
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public byte[] delete(byte[] bArr) {
        try {
            return this.mBinder.deleteWithChallenge(bArr);
        } catch (RemoteException e) {
            throw new RuntimeException("Unexpected RemoteException ", e);
        } catch (ServiceSpecificException e2) {
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public byte[] update(PersonalizationData personalizationData) {
        try {
            return CredstoreWritableIdentityCredential.personalize(this.mBinder.update(), personalizationData);
        } catch (RemoteException e) {
            throw new RuntimeException("Unexpected RemoteException ", e);
        } catch (ServiceSpecificException e2) {
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }
}

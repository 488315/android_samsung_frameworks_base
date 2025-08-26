package android.security.keystore;

import android.security.KeyStore2;
import android.security.KeyStoreException;
import android.security.keystore2.AndroidKeyStoreKey;
import android.security.keystore2.AndroidKeyStorePublicKey;
import android.system.keystore2.KeyDescriptor;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.Key;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class KeyStoreManager {
    public static final int MODULE_HASH = -1879047468;
    private static final String TAG = "KeyStoreManager";
    private static KeyStoreManager sInstance;
    private static final Object sInstanceLock = new Object();
    private final KeyStore2 mKeyStore2 = KeyStore2.getInstance();

    @Retention(RetentionPolicy.SOURCE)
    public @interface SupplementaryAttestationInfoTagEnum {
    }

    private KeyStoreManager() {
    }

    public static KeyStoreManager getInstance() {
        KeyStoreManager keyStoreManager;
        synchronized (sInstanceLock) {
            if (sInstance == null) {
                sInstance = new KeyStoreManager();
            }
            keyStoreManager = sInstance;
        }
        return keyStoreManager;
    }

    public long grantKeyAccess(String str, int i) throws UnrecoverableKeyException, KeyStoreException {
        try {
            KeyDescriptor keyDescriptorGrant = this.mKeyStore2.grant(createKeyDescriptorFromAlias(str), i, 260);
            if (keyDescriptorGrant == null) {
                Log.e(TAG, "Received a null KeyDescriptor from grant");
                throw new KeyStoreException(4, "No ID was returned for the grant request for alias " + str + " to uid " + i);
            }
            if (keyDescriptorGrant.domain != 1) {
                Log.e(TAG, "Received a result outside the grant domain: " + keyDescriptorGrant.domain);
                throw new KeyStoreException(4, "Unable to obtain a grant ID for alias " + str + " to uid " + i);
            }
            return keyDescriptorGrant.nspace;
        } catch (KeyStoreException e) {
            if (e.getNumericErrorCode() == 6) {
                throw new UnrecoverableKeyException("No key found by the given alias");
            }
            throw e;
        }
    }

    public void revokeKeyAccess(String str, int i) throws InterruptedException, UnrecoverableKeyException, KeyStoreException {
        try {
            this.mKeyStore2.ungrant(createKeyDescriptorFromAlias(str), i);
        } catch (KeyStoreException e) {
            if (e.getNumericErrorCode() == 6) {
                throw new UnrecoverableKeyException("No key found by the given alias");
            }
            throw e;
        }
    }

    public Key getGrantedKeyFromId(long j) throws KeyPermanentlyInvalidatedException, UnrecoverableKeyException {
        AndroidKeyStoreKey androidKeyStoreKeyLoadAndroidKeyStoreKeyFromKeystore = android.security.keystore2.AndroidKeyStoreProvider.loadAndroidKeyStoreKeyFromKeystore(this.mKeyStore2, null, j, 1);
        if (androidKeyStoreKeyLoadAndroidKeyStoreKeyFromKeystore != null) {
            return androidKeyStoreKeyLoadAndroidKeyStoreKeyFromKeystore;
        }
        throw new UnrecoverableKeyException("No key found by the given alias");
    }

    public KeyPair getGrantedKeyPairFromId(long j) throws KeyPermanentlyInvalidatedException, UnrecoverableKeyException {
        return android.security.keystore2.AndroidKeyStoreProvider.loadAndroidKeyStoreKeyPairFromKeystore(this.mKeyStore2, createKeyDescriptorFromId(j, 1));
    }

    public List<X509Certificate> getGrantedCertificateChainFromId(long j) throws KeyPermanentlyInvalidatedException, UnrecoverableKeyException {
        PublicKey publicKey = android.security.keystore2.AndroidKeyStoreProvider.loadAndroidKeyStoreKeyPairFromKeystore(this.mKeyStore2, createKeyDescriptorFromId(j, 1)).getPublic();
        if (publicKey instanceof AndroidKeyStorePublicKey) {
            AndroidKeyStorePublicKey androidKeyStorePublicKey = (AndroidKeyStorePublicKey) publicKey;
            X509Certificate certificate = getCertificate(androidKeyStorePublicKey.getCertificate());
            if (certificate == null) {
                return Collections.EMPTY_LIST;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(certificate);
            arrayList.addAll(getCertificates(androidKeyStorePublicKey.getCertificateChain()));
            return arrayList;
        }
        Log.e(TAG, "keyStoreKey is not of the expected type: " + publicKey);
        return Collections.EMPTY_LIST;
    }

    private static X509Certificate getCertificate(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr));
        } catch (Exception e) {
            Log.e(TAG, "Caught an exception parsing the certificate: ", e);
            return null;
        }
    }

    private static Collection<X509Certificate> getCertificates(byte[] bArr) throws CertificateException {
        if (bArr != null) {
            try {
                Collection collectionGenerateCertificates = CertificateFactory.getInstance("X.509").generateCertificates(new ByteArrayInputStream(bArr));
                if (collectionGenerateCertificates != null) {
                    return collectionGenerateCertificates;
                }
                Log.e(TAG, "Received null certificates from a non-null certificateChain");
                return Collections.EMPTY_LIST;
            } catch (Exception e) {
                Log.e(TAG, "Caught an exception parsing the certs: ", e);
            }
        }
        return Collections.EMPTY_LIST;
    }

    public byte[] getSupplementaryAttestationInfo(int i) throws KeyStoreException {
        return this.mKeyStore2.getSupplementaryAttestationInfo(i);
    }

    private static KeyDescriptor createKeyDescriptorFromAlias(String str) {
        KeyDescriptor keyDescriptor = new KeyDescriptor();
        keyDescriptor.domain = 0;
        keyDescriptor.nspace = -1L;
        keyDescriptor.alias = str;
        keyDescriptor.blob = null;
        return keyDescriptor;
    }

    private static KeyDescriptor createKeyDescriptorFromId(long j, int i) {
        KeyDescriptor keyDescriptor = new KeyDescriptor();
        keyDescriptor.domain = i;
        keyDescriptor.nspace = j;
        keyDescriptor.alias = null;
        keyDescriptor.blob = null;
        return keyDescriptor;
    }
}

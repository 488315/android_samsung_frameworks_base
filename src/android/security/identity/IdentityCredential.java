package android.security.identity;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public abstract class IdentityCredential {
    @Deprecated
    public abstract KeyPair createEphemeralKeyPair();

    @Deprecated
    public abstract byte[] decryptMessageFromReader(byte[] bArr) throws MessageDecryptionException;

    @Deprecated
    public abstract byte[] encryptMessageToReader(byte[] bArr);

    public abstract Collection<X509Certificate> getAuthKeysNeedingCertification();

    @Deprecated
    public abstract int[] getAuthenticationDataUsageCount();

    public abstract Collection<X509Certificate> getCredentialKeyCertificateChain();

    public abstract long getCredstoreOperationHandle();

    @Deprecated
    public abstract ResultData getEntries(byte[] bArr, Map<String, Collection<String>> map, byte[] bArr2, byte[] bArr3) throws InvalidReaderSignatureException, EphemeralPublicKeyNotFoundException, SessionTranscriptMismatchException, InvalidRequestMessageException, NoAuthenticationKeyAvailableException;

    @Deprecated
    public abstract void setAllowUsingExhaustedKeys(boolean z);

    @Deprecated
    public abstract void setAvailableAuthenticationKeys(int i, int i2);

    @Deprecated
    public abstract void setReaderEphemeralPublicKey(PublicKey publicKey) throws InvalidKeyException;

    @Deprecated
    public abstract void storeStaticAuthenticationData(X509Certificate x509Certificate, byte[] bArr) throws UnknownAuthenticationKeyException;

    protected IdentityCredential() {
    }

    @Deprecated
    public void setAllowUsingExpiredKeys(boolean z) {
        throw new UnsupportedOperationException();
    }

    public void setIncrementKeyUsageCount(boolean z) {
        throw new UnsupportedOperationException();
    }

    public void storeStaticAuthenticationData(X509Certificate x509Certificate, Instant instant, byte[] bArr) throws UnknownAuthenticationKeyException {
        throw new UnsupportedOperationException();
    }

    public byte[] proveOwnership(byte[] bArr) {
        throw new UnsupportedOperationException();
    }

    public byte[] delete(byte[] bArr) {
        throw new UnsupportedOperationException();
    }

    public byte[] update(PersonalizationData personalizationData) {
        throw new UnsupportedOperationException();
    }

    public void setAvailableAuthenticationKeys(int i, int i2, long j) {
        throw new UnsupportedOperationException();
    }

    public List<AuthenticationKeyMetadata> getAuthenticationKeyMetadata() {
        throw new UnsupportedOperationException();
    }
}

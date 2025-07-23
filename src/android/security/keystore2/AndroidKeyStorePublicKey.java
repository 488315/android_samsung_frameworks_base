package android.security.keystore2;

import android.security.KeyStoreSecurityLevel;
import android.security.keystore.ArrayUtils;
import android.system.keystore2.KeyDescriptor;
import android.system.keystore2.KeyMetadata;
import java.security.PublicKey;
import java.util.Arrays;

/* loaded from: classes3.dex */
public abstract class AndroidKeyStorePublicKey extends AndroidKeyStoreKey implements PublicKey {
    private final byte[] mCertificate;
    private final byte[] mCertificateChain;
    private final byte[] mEncoded;

    abstract AndroidKeyStorePrivateKey getPrivateKey();

    public AndroidKeyStorePublicKey(KeyDescriptor keyDescriptor, KeyMetadata keyMetadata, byte[] bArr, String str, KeyStoreSecurityLevel keyStoreSecurityLevel) {
        super(keyDescriptor, keyMetadata.key.nspace, keyMetadata.authorizations, str, keyStoreSecurityLevel);
        this.mCertificate = keyMetadata.certificate;
        this.mCertificateChain = keyMetadata.certificateChain;
        this.mEncoded = bArr;
    }

    public byte[] getCertificate() {
        return this.mCertificate;
    }

    public byte[] getCertificateChain() {
        return this.mCertificateChain;
    }

    @Override // android.security.keystore2.AndroidKeyStoreKey, java.security.Key
    public String getFormat() {
        return "X.509";
    }

    @Override // android.security.keystore2.AndroidKeyStoreKey, java.security.Key
    public byte[] getEncoded() {
        return ArrayUtils.cloneIfNotEmpty(this.mEncoded);
    }

    @Override // android.security.keystore2.AndroidKeyStoreKey
    public int hashCode() {
        return ((((super.hashCode() + 31) * 31) + Arrays.hashCode(this.mCertificate)) * 31) + Arrays.hashCode(this.mCertificateChain);
    }

    @Override // android.security.keystore2.AndroidKeyStoreKey
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        AndroidKeyStorePublicKey androidKeyStorePublicKey = (AndroidKeyStorePublicKey) obj;
        return Arrays.equals(this.mCertificate, androidKeyStorePublicKey.mCertificate) && Arrays.equals(this.mCertificateChain, androidKeyStorePublicKey.mCertificateChain);
    }
}

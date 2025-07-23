package android.security.keystore2;

import android.security.KeyStoreSecurityLevel;
import android.system.keystore2.KeyDescriptor;
import android.system.keystore2.KeyMetadata;
import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;

/* loaded from: classes3.dex */
public class AndroidKeyStoreRSAPublicKey extends AndroidKeyStorePublicKey implements RSAPublicKey {
    private final BigInteger mModulus;
    private final BigInteger mPublicExponent;

    public AndroidKeyStoreRSAPublicKey(KeyDescriptor keyDescriptor, KeyMetadata keyMetadata, byte[] bArr, KeyStoreSecurityLevel keyStoreSecurityLevel, BigInteger bigInteger, BigInteger bigInteger2) {
        super(keyDescriptor, keyMetadata, bArr, "RSA", keyStoreSecurityLevel);
        this.mModulus = bigInteger;
        this.mPublicExponent = bigInteger2;
    }

    public AndroidKeyStoreRSAPublicKey(KeyDescriptor keyDescriptor, KeyMetadata keyMetadata, KeyStoreSecurityLevel keyStoreSecurityLevel, RSAPublicKey rSAPublicKey) {
        this(keyDescriptor, keyMetadata, rSAPublicKey.getEncoded(), keyStoreSecurityLevel, rSAPublicKey.getModulus(), rSAPublicKey.getPublicExponent());
        if ("X.509".equalsIgnoreCase(rSAPublicKey.getFormat())) {
            return;
        }
        throw new IllegalArgumentException("Unsupported key export format: " + rSAPublicKey.getFormat());
    }

    @Override // android.security.keystore2.AndroidKeyStorePublicKey
    public AndroidKeyStorePrivateKey getPrivateKey() {
        return new AndroidKeyStoreRSAPrivateKey(getUserKeyDescriptor(), getKeyIdDescriptor().nspace, getAuthorizations(), getSecurityLevel(), this.mModulus);
    }

    @Override // java.security.interfaces.RSAKey
    public BigInteger getModulus() {
        return this.mModulus;
    }

    @Override // java.security.interfaces.RSAPublicKey
    public BigInteger getPublicExponent() {
        return this.mPublicExponent;
    }
}

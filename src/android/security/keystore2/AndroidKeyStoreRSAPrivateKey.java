package android.security.keystore2;

import android.security.KeyStoreSecurityLevel;
import android.system.keystore2.Authorization;
import android.system.keystore2.KeyDescriptor;
import java.math.BigInteger;
import java.security.interfaces.RSAKey;

/* loaded from: classes3.dex */
public class AndroidKeyStoreRSAPrivateKey extends AndroidKeyStorePrivateKey implements RSAKey {
    private final BigInteger mModulus;

    public AndroidKeyStoreRSAPrivateKey(KeyDescriptor keyDescriptor, long j, Authorization[] authorizationArr, KeyStoreSecurityLevel keyStoreSecurityLevel, BigInteger bigInteger) {
        super(keyDescriptor, j, authorizationArr, "RSA", keyStoreSecurityLevel);
        this.mModulus = bigInteger;
    }

    @Override // java.security.interfaces.RSAKey
    public BigInteger getModulus() {
        return this.mModulus;
    }
}

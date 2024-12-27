package android.security.keystore2;

import android.security.KeyStoreSecurityLevel;
import android.system.keystore2.Authorization;
import android.system.keystore2.KeyDescriptor;

import java.security.interfaces.EdECKey;
import java.security.spec.NamedParameterSpec;

public class AndroidKeyStoreEdECPrivateKey extends AndroidKeyStorePrivateKey implements EdECKey {
    public AndroidKeyStoreEdECPrivateKey(
            KeyDescriptor descriptor,
            long keyId,
            Authorization[] authorizations,
            String algorithm,
            KeyStoreSecurityLevel securityLevel) {
        super(descriptor, keyId, authorizations, algorithm, securityLevel);
    }

    @Override // java.security.interfaces.EdECKey
    public NamedParameterSpec getParams() {
        return NamedParameterSpec.ED25519;
    }
}

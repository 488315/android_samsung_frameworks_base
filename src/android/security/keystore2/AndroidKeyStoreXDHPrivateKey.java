package android.security.keystore2;

import android.security.KeyStoreSecurityLevel;
import android.system.keystore2.Authorization;
import android.system.keystore2.KeyDescriptor;
import java.security.interfaces.XECPrivateKey;
import java.security.spec.NamedParameterSpec;
import java.util.Optional;

/* loaded from: classes3.dex */
public class AndroidKeyStoreXDHPrivateKey extends AndroidKeyStorePrivateKey implements XECPrivateKey {
    public AndroidKeyStoreXDHPrivateKey(KeyDescriptor keyDescriptor, long j, Authorization[] authorizationArr, String str, KeyStoreSecurityLevel keyStoreSecurityLevel) {
        super(keyDescriptor, j, authorizationArr, str, keyStoreSecurityLevel);
    }

    @Override // java.security.interfaces.XECKey
    public NamedParameterSpec getParams() {
        return NamedParameterSpec.X25519;
    }

    @Override // java.security.interfaces.XECPrivateKey
    public Optional<byte[]> getScalar() {
        return Optional.empty();
    }
}

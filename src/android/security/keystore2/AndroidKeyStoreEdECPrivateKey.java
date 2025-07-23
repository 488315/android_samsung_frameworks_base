package android.security.keystore2;

import android.security.KeyStoreSecurityLevel;
import android.system.keystore2.Authorization;
import android.system.keystore2.KeyDescriptor;
import java.security.interfaces.EdECKey;
import java.security.spec.NamedParameterSpec;

/* loaded from: classes3.dex */
public class AndroidKeyStoreEdECPrivateKey extends AndroidKeyStorePrivateKey implements EdECKey {
    public AndroidKeyStoreEdECPrivateKey(KeyDescriptor keyDescriptor, long j, Authorization[] authorizationArr, String str, KeyStoreSecurityLevel keyStoreSecurityLevel) {
        super(keyDescriptor, j, authorizationArr, str, keyStoreSecurityLevel);
    }

    @Override // java.security.interfaces.EdECKey
    public NamedParameterSpec getParams() {
        return NamedParameterSpec.ED25519;
    }
}

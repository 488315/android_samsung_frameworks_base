package android.security.keystore2;

import android.security.KeyStoreSecurityLevel;
import android.security.keystore.KeyProperties;
import android.system.keystore2.Authorization;
import android.system.keystore2.KeyDescriptor;
import java.security.interfaces.ECKey;
import java.security.spec.ECParameterSpec;

/* loaded from: classes3.dex */
public class AndroidKeyStoreECPrivateKey extends AndroidKeyStorePrivateKey implements ECKey {
    private final ECParameterSpec mParams;

    public AndroidKeyStoreECPrivateKey(KeyDescriptor keyDescriptor, long j, Authorization[] authorizationArr, KeyStoreSecurityLevel keyStoreSecurityLevel, ECParameterSpec eCParameterSpec) {
        super(keyDescriptor, j, authorizationArr, KeyProperties.KEY_ALGORITHM_EC, keyStoreSecurityLevel);
        this.mParams = eCParameterSpec;
    }

    @Override // java.security.interfaces.ECKey
    public ECParameterSpec getParams() {
        return this.mParams;
    }
}

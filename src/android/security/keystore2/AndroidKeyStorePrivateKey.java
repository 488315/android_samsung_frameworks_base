package android.security.keystore2;

import android.security.KeyStoreSecurityLevel;
import android.system.keystore2.Authorization;
import android.system.keystore2.KeyDescriptor;
import java.security.PrivateKey;

/* loaded from: classes3.dex */
public class AndroidKeyStorePrivateKey extends AndroidKeyStoreKey implements PrivateKey {
    public AndroidKeyStorePrivateKey(KeyDescriptor keyDescriptor, long j, Authorization[] authorizationArr, String str, KeyStoreSecurityLevel keyStoreSecurityLevel) {
        super(keyDescriptor, j, authorizationArr, str, keyStoreSecurityLevel);
    }
}

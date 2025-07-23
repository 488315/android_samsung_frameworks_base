package android.security.keystore2;

import android.security.KeyStoreSecurityLevel;
import android.system.keystore2.KeyDescriptor;
import android.system.keystore2.KeyMetadata;
import javax.crypto.SecretKey;

/* loaded from: classes3.dex */
public class AndroidKeyStoreSecretKey extends AndroidKeyStoreKey implements SecretKey {
    public AndroidKeyStoreSecretKey(KeyDescriptor keyDescriptor, KeyMetadata keyMetadata, String str, KeyStoreSecurityLevel keyStoreSecurityLevel) {
        super(keyDescriptor, keyMetadata.key.nspace, keyMetadata.authorizations, str, keyStoreSecurityLevel);
    }
}

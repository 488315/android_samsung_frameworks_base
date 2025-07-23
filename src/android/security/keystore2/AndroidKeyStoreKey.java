package android.security.keystore2;

import android.security.KeyStoreSecurityLevel;
import android.system.keystore2.Authorization;
import android.system.keystore2.KeyDescriptor;
import java.security.Key;

/* loaded from: classes3.dex */
public class AndroidKeyStoreKey implements Key {
    private final String mAlgorithm;
    private final Authorization[] mAuthorizations;
    private final KeyDescriptor mDescriptor;
    private final long mKeyId;
    private final KeyStoreSecurityLevel mSecurityLevel;

    @Override // java.security.Key
    public byte[] getEncoded() {
        return null;
    }

    @Override // java.security.Key
    public String getFormat() {
        return null;
    }

    public AndroidKeyStoreKey(KeyDescriptor keyDescriptor, long j, Authorization[] authorizationArr, String str, KeyStoreSecurityLevel keyStoreSecurityLevel) {
        this.mDescriptor = keyDescriptor;
        this.mKeyId = j;
        this.mAuthorizations = authorizationArr;
        this.mAlgorithm = str;
        this.mSecurityLevel = keyStoreSecurityLevel;
    }

    KeyDescriptor getUserKeyDescriptor() {
        return this.mDescriptor;
    }

    KeyDescriptor getKeyIdDescriptor() {
        KeyDescriptor keyDescriptor = new KeyDescriptor();
        keyDescriptor.nspace = this.mKeyId;
        keyDescriptor.domain = 4;
        keyDescriptor.alias = null;
        keyDescriptor.blob = null;
        return keyDescriptor;
    }

    Authorization[] getAuthorizations() {
        return this.mAuthorizations;
    }

    KeyStoreSecurityLevel getSecurityLevel() {
        return this.mSecurityLevel;
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return this.mAlgorithm;
    }

    public int hashCode() {
        int hashCode = (getClass().hashCode() + 31) * 31;
        long j = this.mKeyId;
        return ((hashCode + ((int) (j >>> 32))) * 31) + ((int) j);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.mKeyId == ((AndroidKeyStoreKey) obj).mKeyId;
    }
}

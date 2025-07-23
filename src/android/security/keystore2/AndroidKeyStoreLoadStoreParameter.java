package android.security.keystore2;

import java.security.KeyStore;

/* loaded from: classes3.dex */
public class AndroidKeyStoreLoadStoreParameter implements KeyStore.LoadStoreParameter {
    private final int mNamespace;

    @Override // java.security.KeyStore.LoadStoreParameter
    public KeyStore.ProtectionParameter getProtectionParameter() {
        return null;
    }

    public AndroidKeyStoreLoadStoreParameter(int i) {
        this.mNamespace = i;
    }

    int getNamespace() {
        return this.mNamespace;
    }
}

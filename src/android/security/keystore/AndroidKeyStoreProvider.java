package android.security.keystore;

import android.annotation.SystemApi;
import android.security.keystore2.AndroidKeyStoreLoadStoreParameter;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.cert.CertificateException;

@SystemApi
/* loaded from: classes3.dex */
public class AndroidKeyStoreProvider extends Provider {
    private static final String PROVIDER_NAME = "AndroidKeyStore";

    public AndroidKeyStoreProvider(String str) {
        super(str, 1.0d, "Android KeyStore security provider");
        throw new IllegalStateException("Should not be instantiated.");
    }

    public static long getKeyStoreOperationHandle(Object obj) {
        return android.security.keystore2.AndroidKeyStoreProvider.getKeyStoreOperationHandle(obj);
    }

    @SystemApi
    public static KeyStore getKeyStoreForUid(int i) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, NoSuchProviderException {
        AndroidKeyStoreLoadStoreParameter androidKeyStoreLoadStoreParameter = new AndroidKeyStoreLoadStoreParameter(KeyProperties.legacyUidToNamespace(i));
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        try {
            keyStore.load(androidKeyStoreLoadStoreParameter);
            return keyStore;
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new KeyStoreException("Failed to load AndroidKeyStore KeyStore for UID " + i, e);
        }
    }
}

package android.security.keystore;

import android.security.KeyStoreException;
import java.security.ProviderException;

/* loaded from: classes3.dex */
public class SecureKeyImportUnavailableException extends ProviderException {
    public SecureKeyImportUnavailableException() {
    }

    public SecureKeyImportUnavailableException(String str) {
        super(str, new KeyStoreException(-68, "Secure Key Import not available"));
    }

    public SecureKeyImportUnavailableException(String str, Throwable th) {
        super(str, th);
    }

    public SecureKeyImportUnavailableException(Throwable th) {
        super(th);
    }
}

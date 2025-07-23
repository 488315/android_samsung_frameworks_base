package android.security.keystore;

import android.security.KeyStoreException;
import java.security.ProviderException;

/* loaded from: classes3.dex */
public class StrongBoxUnavailableException extends ProviderException {
    public StrongBoxUnavailableException() {
    }

    public StrongBoxUnavailableException(String str) {
        super(str, new KeyStoreException(-68, "No StrongBox available"));
    }

    public StrongBoxUnavailableException(String str, Throwable th) {
        super(str, th);
    }

    public StrongBoxUnavailableException(Throwable th) {
        super(th);
    }
}

package android.security.keystore;

import java.security.InvalidKeyException;

/* loaded from: classes3.dex */
public class KeyPermanentlyInvalidatedException extends InvalidKeyException {
    public KeyPermanentlyInvalidatedException() {
        super("Key permanently invalidated");
    }

    public KeyPermanentlyInvalidatedException(String str) {
        super(str);
    }

    public KeyPermanentlyInvalidatedException(String str, Throwable th) {
        super(str, th);
    }
}

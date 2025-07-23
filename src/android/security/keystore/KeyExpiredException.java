package android.security.keystore;

import java.security.InvalidKeyException;

/* loaded from: classes3.dex */
public class KeyExpiredException extends InvalidKeyException {
    public KeyExpiredException() {
        super("Key expired");
    }

    public KeyExpiredException(String str) {
        super(str);
    }

    public KeyExpiredException(String str, Throwable th) {
        super(str, th);
    }
}

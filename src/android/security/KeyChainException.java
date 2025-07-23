package android.security;

/* loaded from: classes3.dex */
public class KeyChainException extends Exception {
    public KeyChainException() {
    }

    public KeyChainException(String str) {
        super(str);
    }

    public KeyChainException(String str, Throwable th) {
        super(str, th);
    }

    public KeyChainException(Throwable th) {
        super(th == null ? null : th.toString(), th);
    }
}

package android.util;

/* loaded from: classes4.dex */
public class AndroidRuntimeException extends RuntimeException {
    public AndroidRuntimeException() {
    }

    public AndroidRuntimeException(String str) {
        super(str);
    }

    public AndroidRuntimeException(String str, Throwable th) {
        super(str, th);
    }

    public AndroidRuntimeException(Exception exc) {
        super(exc);
    }
}

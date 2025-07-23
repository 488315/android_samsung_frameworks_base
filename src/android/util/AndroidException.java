package android.util;

/* loaded from: classes4.dex */
public class AndroidException extends Exception {
    public AndroidException() {
    }

    public AndroidException(String str) {
        super(str);
    }

    public AndroidException(String str, Throwable th) {
        super(str, th);
    }

    public AndroidException(Exception exc) {
        super(exc);
    }

    protected AndroidException(String str, Throwable th, boolean z, boolean z2) {
        super(str, th, z, z2);
    }
}

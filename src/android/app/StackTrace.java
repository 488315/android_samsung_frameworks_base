package android.app;

/* loaded from: classes.dex */
public class StackTrace extends Exception {
    public StackTrace(String str) {
        super(str);
    }

    public StackTrace(String str, Throwable th) {
        super(str, th);
    }
}

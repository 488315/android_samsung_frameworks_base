package android.accounts;

/* loaded from: classes.dex */
public class OperationCanceledException extends AccountsException {
    public OperationCanceledException() {
    }

    public OperationCanceledException(String str) {
        super(str);
    }

    public OperationCanceledException(String str, Throwable th) {
        super(str, th);
    }

    public OperationCanceledException(Throwable th) {
        super(th);
    }
}

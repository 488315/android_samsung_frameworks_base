package android.os.strictmode;

/* loaded from: classes3.dex */
public final class ServiceConnectionLeakedViolation extends Violation {
    public ServiceConnectionLeakedViolation(Throwable th) {
        super(null);
        setStackTrace(th.getStackTrace());
    }
}

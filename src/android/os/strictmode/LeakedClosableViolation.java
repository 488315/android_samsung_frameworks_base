package android.os.strictmode;

/* loaded from: classes3.dex */
public final class LeakedClosableViolation extends Violation {
    public LeakedClosableViolation(String str, Throwable th) {
        super(str);
        initCause(th);
    }

    public LeakedClosableViolation(String str) {
        super(str);
    }
}

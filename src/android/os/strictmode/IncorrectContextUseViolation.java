package android.os.strictmode;

/* loaded from: classes3.dex */
public final class IncorrectContextUseViolation extends Violation {
    public IncorrectContextUseViolation(String str, Throwable th) {
        super(str);
        initCause(th);
    }
}

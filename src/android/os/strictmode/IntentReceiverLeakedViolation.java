package android.os.strictmode;

/* loaded from: classes3.dex */
public final class IntentReceiverLeakedViolation extends Violation {
    public IntentReceiverLeakedViolation(Throwable th) {
        super(null);
        setStackTrace(th.getStackTrace());
    }
}

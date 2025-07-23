package android.os.strictmode;

/* loaded from: classes3.dex */
public final class WebViewMethodCalledOnWrongThreadViolation extends Violation {
    public WebViewMethodCalledOnWrongThreadViolation(Throwable th) {
        super(null);
        setStackTrace(th.getStackTrace());
    }
}

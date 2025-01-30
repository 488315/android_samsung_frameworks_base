package android.os.strictmode;

/* loaded from: classes3.dex */
public final class WebViewMethodCalledOnWrongThreadViolation extends Violation {
  public WebViewMethodCalledOnWrongThreadViolation(Throwable originStack) {
    super(null);
    setStackTrace(originStack.getStackTrace());
  }
}

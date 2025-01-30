package android.p009os.strictmode;

/* loaded from: classes3.dex */
public final class IncorrectContextUseViolation extends Violation {
  public IncorrectContextUseViolation(String message, Throwable originStack) {
    super(message);
    initCause(originStack);
  }
}

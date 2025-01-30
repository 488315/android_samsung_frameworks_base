package android.hardware.fingerprint;

/* loaded from: classes2.dex */
public interface SemFingerprintViewListener {
  public static final int EVT_AUTHENTICATION_SUCCEEDED = 100;
  public static final int EVT_DISMISS = 3;
  public static final int EVT_SHOW = 2;
  public static final int EVT_STARTED = 0;
  public static final int EVT_STOPPED = 1;

  void onDismiss();

  void onShow();

  void onStarted();

  void onStopped();

  default void onAuthenticationSucceeded() {}

  default void onEvent(int event) {}
}

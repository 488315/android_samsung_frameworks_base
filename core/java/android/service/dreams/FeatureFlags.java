package android.service.dreams;

public interface FeatureFlags {
    boolean dismissDreamOnKeyguardDismiss();

    boolean dreamHandlesBeingObscured();

    boolean dreamHandlesConfirmKeys();

    boolean dreamOverlayHost();

    boolean dreamWakeRedirect();
}

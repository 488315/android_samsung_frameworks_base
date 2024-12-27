package android.service.dreams;

/* loaded from: classes3.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_DISMISS_DREAM_ON_KEYGUARD_DISMISS = "android.service.dreams.dismiss_dream_on_keyguard_dismiss";
    public static final String FLAG_DREAM_HANDLES_BEING_OBSCURED = "android.service.dreams.dream_handles_being_obscured";
    public static final String FLAG_DREAM_HANDLES_CONFIRM_KEYS = "android.service.dreams.dream_handles_confirm_keys";
    public static final String FLAG_DREAM_OVERLAY_HOST = "android.service.dreams.dream_overlay_host";
    public static final String FLAG_DREAM_WAKE_REDIRECT = "android.service.dreams.dream_wake_redirect";

    public static boolean dismissDreamOnKeyguardDismiss() {
        return FEATURE_FLAGS.dismissDreamOnKeyguardDismiss();
    }

    public static boolean dreamHandlesBeingObscured() {
        return FEATURE_FLAGS.dreamHandlesBeingObscured();
    }

    public static boolean dreamHandlesConfirmKeys() {
        return FEATURE_FLAGS.dreamHandlesConfirmKeys();
    }

    public static boolean dreamOverlayHost() {
        return FEATURE_FLAGS.dreamOverlayHost();
    }

    public static boolean dreamWakeRedirect() {
        return FEATURE_FLAGS.dreamWakeRedirect();
    }
}

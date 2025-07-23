package android.service.dreams;

/* loaded from: classes3.dex */
public interface FeatureFlags {
    boolean allowDreamWhenPostured();

    boolean cleanupDreamSettingsOnUninstall();

    boolean dismissDreamOnKeyguardDismiss();

    boolean dreamHandlesBeingObscured();

    boolean dreamHandlesConfirmKeys();

    boolean dreamOverlayHost();

    boolean dreamWakeRedirect();

    boolean dreamsV2();

    boolean publishPreviewStateToOverlay();

    boolean startAndStopDozingInBackground();
}

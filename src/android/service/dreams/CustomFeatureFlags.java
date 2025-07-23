package android.service.dreams;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ALLOW_DREAM_WHEN_POSTURED, Flags.FLAG_CLEANUP_DREAM_SETTINGS_ON_UNINSTALL, Flags.FLAG_DISMISS_DREAM_ON_KEYGUARD_DISMISS, Flags.FLAG_DREAM_HANDLES_BEING_OBSCURED, Flags.FLAG_DREAM_HANDLES_CONFIRM_KEYS, Flags.FLAG_DREAM_OVERLAY_HOST, Flags.FLAG_DREAM_WAKE_REDIRECT, Flags.FLAG_DREAMS_V2, Flags.FLAG_PUBLISH_PREVIEW_STATE_TO_OVERLAY, Flags.FLAG_START_AND_STOP_DOZING_IN_BACKGROUND, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean allowDreamWhenPostured() {
        return getValue(Flags.FLAG_ALLOW_DREAM_WHEN_POSTURED, new Predicate() { // from class: android.service.dreams.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowDreamWhenPostured();
            }
        });
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean cleanupDreamSettingsOnUninstall() {
        return getValue(Flags.FLAG_CLEANUP_DREAM_SETTINGS_ON_UNINSTALL, new Predicate() { // from class: android.service.dreams.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cleanupDreamSettingsOnUninstall();
            }
        });
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dismissDreamOnKeyguardDismiss() {
        return getValue(Flags.FLAG_DISMISS_DREAM_ON_KEYGUARD_DISMISS, new Predicate() { // from class: android.service.dreams.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dismissDreamOnKeyguardDismiss();
            }
        });
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamHandlesBeingObscured() {
        return getValue(Flags.FLAG_DREAM_HANDLES_BEING_OBSCURED, new Predicate() { // from class: android.service.dreams.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dreamHandlesBeingObscured();
            }
        });
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamHandlesConfirmKeys() {
        return getValue(Flags.FLAG_DREAM_HANDLES_CONFIRM_KEYS, new Predicate() { // from class: android.service.dreams.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dreamHandlesConfirmKeys();
            }
        });
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamOverlayHost() {
        return getValue(Flags.FLAG_DREAM_OVERLAY_HOST, new Predicate() { // from class: android.service.dreams.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dreamOverlayHost();
            }
        });
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamWakeRedirect() {
        return getValue(Flags.FLAG_DREAM_WAKE_REDIRECT, new Predicate() { // from class: android.service.dreams.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dreamWakeRedirect();
            }
        });
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamsV2() {
        return getValue(Flags.FLAG_DREAMS_V2, new Predicate() { // from class: android.service.dreams.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dreamsV2();
            }
        });
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean publishPreviewStateToOverlay() {
        return getValue(Flags.FLAG_PUBLISH_PREVIEW_STATE_TO_OVERLAY, new Predicate() { // from class: android.service.dreams.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).publishPreviewStateToOverlay();
            }
        });
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean startAndStopDozingInBackground() {
        return getValue(Flags.FLAG_START_AND_STOP_DOZING_IN_BACKGROUND, new Predicate() { // from class: android.service.dreams.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).startAndStopDozingInBackground();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ALLOW_DREAM_WHEN_POSTURED, Flags.FLAG_CLEANUP_DREAM_SETTINGS_ON_UNINSTALL, Flags.FLAG_DISMISS_DREAM_ON_KEYGUARD_DISMISS, Flags.FLAG_DREAM_HANDLES_BEING_OBSCURED, Flags.FLAG_DREAM_HANDLES_CONFIRM_KEYS, Flags.FLAG_DREAM_OVERLAY_HOST, Flags.FLAG_DREAM_WAKE_REDIRECT, Flags.FLAG_DREAMS_V2, Flags.FLAG_PUBLISH_PREVIEW_STATE_TO_OVERLAY, Flags.FLAG_START_AND_STOP_DOZING_IN_BACKGROUND);
    }
}

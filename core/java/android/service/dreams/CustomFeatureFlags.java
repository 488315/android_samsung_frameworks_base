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
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_DISMISS_DREAM_ON_KEYGUARD_DISMISS,
                            Flags.FLAG_DREAM_HANDLES_BEING_OBSCURED,
                            Flags.FLAG_DREAM_HANDLES_CONFIRM_KEYS,
                            Flags.FLAG_DREAM_OVERLAY_HOST,
                            Flags.FLAG_DREAM_WAKE_REDIRECT,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dismissDreamOnKeyguardDismiss() {
        return getValue(
                Flags.FLAG_DISMISS_DREAM_ON_KEYGUARD_DISMISS,
                new Predicate() { // from class:
                                  // android.service.dreams.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).dismissDreamOnKeyguardDismiss();
                    }
                });
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamHandlesBeingObscured() {
        return getValue(
                Flags.FLAG_DREAM_HANDLES_BEING_OBSCURED,
                new Predicate() { // from class:
                                  // android.service.dreams.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).dreamHandlesBeingObscured();
                    }
                });
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamHandlesConfirmKeys() {
        return getValue(
                Flags.FLAG_DREAM_HANDLES_CONFIRM_KEYS,
                new Predicate() { // from class:
                                  // android.service.dreams.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).dreamHandlesConfirmKeys();
                    }
                });
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamOverlayHost() {
        return getValue(
                Flags.FLAG_DREAM_OVERLAY_HOST,
                new Predicate() { // from class:
                                  // android.service.dreams.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).dreamOverlayHost();
                    }
                });
    }

    @Override // android.service.dreams.FeatureFlags
    public boolean dreamWakeRedirect() {
        return getValue(
                Flags.FLAG_DREAM_WAKE_REDIRECT,
                new Predicate() { // from class:
                                  // android.service.dreams.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).dreamWakeRedirect();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_DISMISS_DREAM_ON_KEYGUARD_DISMISS,
                Flags.FLAG_DREAM_HANDLES_BEING_OBSCURED,
                Flags.FLAG_DREAM_HANDLES_CONFIRM_KEYS,
                Flags.FLAG_DREAM_OVERLAY_HOST,
                Flags.FLAG_DREAM_WAKE_REDIRECT);
    }
}

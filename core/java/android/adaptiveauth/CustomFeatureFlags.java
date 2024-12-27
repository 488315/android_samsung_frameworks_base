package android.adaptiveauth;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_ENABLE_ADAPTIVE_AUTH,
                            Flags.FLAG_REPORT_BIOMETRIC_AUTH_ATTEMPTS,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.adaptiveauth.FeatureFlags
    public boolean enableAdaptiveAuth() {
        return getValue(
                Flags.FLAG_ENABLE_ADAPTIVE_AUTH,
                new Predicate() { // from class:
                    // android.adaptiveauth.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableAdaptiveAuth();
                    }
                });
    }

    @Override // android.adaptiveauth.FeatureFlags
    public boolean reportBiometricAuthAttempts() {
        return getValue(
                Flags.FLAG_REPORT_BIOMETRIC_AUTH_ATTEMPTS,
                new Predicate() { // from class:
                    // android.adaptiveauth.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).reportBiometricAuthAttempts();
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
                Flags.FLAG_ENABLE_ADAPTIVE_AUTH, Flags.FLAG_REPORT_BIOMETRIC_AUTH_ATTEMPTS);
    }
}

package android.webkit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_UPDATE_SERVICE_IPC_WRAPPER,
                            Flags.FLAG_UPDATE_SERVICE_V2,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.webkit.FeatureFlags
    public boolean updateServiceIpcWrapper() {
        return getValue(
                Flags.FLAG_UPDATE_SERVICE_IPC_WRAPPER,
                new Predicate() { // from class:
                                  // android.webkit.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).updateServiceIpcWrapper();
                    }
                });
    }

    @Override // android.webkit.FeatureFlags
    public boolean updateServiceV2() {
        return getValue(
                Flags.FLAG_UPDATE_SERVICE_V2,
                new Predicate() { // from class:
                                  // android.webkit.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).updateServiceV2();
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
        return Arrays.asList(Flags.FLAG_UPDATE_SERVICE_IPC_WRAPPER, Flags.FLAG_UPDATE_SERVICE_V2);
    }
}

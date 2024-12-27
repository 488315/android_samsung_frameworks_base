package android.app.contextualsearch.flags;

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
                    Arrays.asList(Flags.FLAG_ENABLE_SERVICE, Flags.FLAG_ENABLE_TOKEN_REFRESH, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean enableService() {
        return getValue(
                Flags.FLAG_ENABLE_SERVICE,
                new Predicate() { // from class:
                    // android.app.contextualsearch.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableService();
                    }
                });
    }

    @Override // android.app.contextualsearch.flags.FeatureFlags
    public boolean enableTokenRefresh() {
        return getValue(
                Flags.FLAG_ENABLE_TOKEN_REFRESH,
                new Predicate() { // from class:
                    // android.app.contextualsearch.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableTokenRefresh();
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
        return Arrays.asList(Flags.FLAG_ENABLE_SERVICE, Flags.FLAG_ENABLE_TOKEN_REFRESH);
    }
}

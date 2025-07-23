package android.app.jank;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_DETAILED_APP_JANK_METRICS_API, Flags.FLAG_DETAILED_APP_JANK_METRICS_LOGGING_ENABLED, Flags.FLAG_VIEWROOT_CHOREOGRAPHER, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.app.jank.FeatureFlags
    public boolean detailedAppJankMetricsApi() {
        return getValue(Flags.FLAG_DETAILED_APP_JANK_METRICS_API, new Predicate() { // from class: android.app.jank.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).detailedAppJankMetricsApi();
            }
        });
    }

    @Override // android.app.jank.FeatureFlags
    public boolean detailedAppJankMetricsLoggingEnabled() {
        return getValue(Flags.FLAG_DETAILED_APP_JANK_METRICS_LOGGING_ENABLED, new Predicate() { // from class: android.app.jank.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).detailedAppJankMetricsLoggingEnabled();
            }
        });
    }

    @Override // android.app.jank.FeatureFlags
    public boolean viewrootChoreographer() {
        return getValue(Flags.FLAG_VIEWROOT_CHOREOGRAPHER, new Predicate() { // from class: android.app.jank.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).viewrootChoreographer();
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
        return Arrays.asList(Flags.FLAG_DETAILED_APP_JANK_METRICS_API, Flags.FLAG_DETAILED_APP_JANK_METRICS_LOGGING_ENABLED, Flags.FLAG_VIEWROOT_CHOREOGRAPHER);
    }
}

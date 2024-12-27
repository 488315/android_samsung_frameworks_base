package android.app.usage;

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
                            Flags.FLAG_DISABLE_IDLE_CHECK,
                            Flags.FLAG_FILTER_BASED_EVENT_QUERY_API,
                            Flags.FLAG_GET_APP_BYTES_BY_DATA_TYPE_API,
                            Flags.FLAG_REPORT_USAGE_STATS_PERMISSION,
                            Flags.FLAG_USE_DEDICATED_HANDLER_THREAD,
                            Flags.FLAG_USE_PARCELED_LIST,
                            Flags.FLAG_USER_INTERACTION_TYPE_API,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.app.usage.FeatureFlags
    public boolean disableIdleCheck() {
        return getValue(
                Flags.FLAG_DISABLE_IDLE_CHECK,
                new Predicate() { // from class:
                    // android.app.usage.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).disableIdleCheck();
                    }
                });
    }

    @Override // android.app.usage.FeatureFlags
    public boolean filterBasedEventQueryApi() {
        return getValue(
                Flags.FLAG_FILTER_BASED_EVENT_QUERY_API,
                new Predicate() { // from class:
                    // android.app.usage.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).filterBasedEventQueryApi();
                    }
                });
    }

    @Override // android.app.usage.FeatureFlags
    public boolean getAppBytesByDataTypeApi() {
        return getValue(
                Flags.FLAG_GET_APP_BYTES_BY_DATA_TYPE_API,
                new Predicate() { // from class:
                    // android.app.usage.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).getAppBytesByDataTypeApi();
                    }
                });
    }

    @Override // android.app.usage.FeatureFlags
    public boolean reportUsageStatsPermission() {
        return getValue(
                Flags.FLAG_REPORT_USAGE_STATS_PERMISSION,
                new Predicate() { // from class:
                    // android.app.usage.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).reportUsageStatsPermission();
                    }
                });
    }

    @Override // android.app.usage.FeatureFlags
    public boolean useDedicatedHandlerThread() {
        return getValue(
                Flags.FLAG_USE_DEDICATED_HANDLER_THREAD,
                new Predicate() { // from class:
                    // android.app.usage.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).useDedicatedHandlerThread();
                    }
                });
    }

    @Override // android.app.usage.FeatureFlags
    public boolean useParceledList() {
        return getValue(
                Flags.FLAG_USE_PARCELED_LIST,
                new Predicate() { // from class:
                    // android.app.usage.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).useParceledList();
                    }
                });
    }

    @Override // android.app.usage.FeatureFlags
    public boolean userInteractionTypeApi() {
        return getValue(
                Flags.FLAG_USER_INTERACTION_TYPE_API,
                new Predicate() { // from class:
                    // android.app.usage.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).userInteractionTypeApi();
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
                Flags.FLAG_DISABLE_IDLE_CHECK,
                Flags.FLAG_FILTER_BASED_EVENT_QUERY_API,
                Flags.FLAG_GET_APP_BYTES_BY_DATA_TYPE_API,
                Flags.FLAG_REPORT_USAGE_STATS_PERMISSION,
                Flags.FLAG_USE_DEDICATED_HANDLER_THREAD,
                Flags.FLAG_USE_PARCELED_LIST,
                Flags.FLAG_USER_INTERACTION_TYPE_API);
    }
}

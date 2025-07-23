package android.database.sqlite;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_CONCURRENT_OPEN_HELPER, Flags.FLAG_NO_CHECKPOINT_ON_FINALIZE, Flags.FLAG_ONEWAY_FINALIZER_CLOSE_FIXED, Flags.FLAG_SQLITE_APIS_35, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.database.sqlite.FeatureFlags
    public boolean concurrentOpenHelper() {
        return getValue(Flags.FLAG_CONCURRENT_OPEN_HELPER, new Predicate() { // from class: android.database.sqlite.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).concurrentOpenHelper();
            }
        });
    }

    @Override // android.database.sqlite.FeatureFlags
    public boolean noCheckpointOnFinalize() {
        return getValue(Flags.FLAG_NO_CHECKPOINT_ON_FINALIZE, new Predicate() { // from class: android.database.sqlite.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).noCheckpointOnFinalize();
            }
        });
    }

    @Override // android.database.sqlite.FeatureFlags
    public boolean onewayFinalizerCloseFixed() {
        return getValue(Flags.FLAG_ONEWAY_FINALIZER_CLOSE_FIXED, new Predicate() { // from class: android.database.sqlite.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).onewayFinalizerCloseFixed();
            }
        });
    }

    @Override // android.database.sqlite.FeatureFlags
    public boolean sqliteApis35() {
        return getValue(Flags.FLAG_SQLITE_APIS_35, new Predicate() { // from class: android.database.sqlite.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sqliteApis35();
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
        return Arrays.asList(Flags.FLAG_CONCURRENT_OPEN_HELPER, Flags.FLAG_NO_CHECKPOINT_ON_FINALIZE, Flags.FLAG_ONEWAY_FINALIZER_CLOSE_FIXED, Flags.FLAG_SQLITE_APIS_35);
    }
}

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
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_SIMPLE_SQL_COMMENT_SCANNER, Flags.FLAG_SQLITE_ALLOW_TEMP_TABLES, Flags.FLAG_SQLITE_APIS_35, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.database.sqlite.FeatureFlags
    public boolean simpleSqlCommentScanner() {
        return getValue(Flags.FLAG_SIMPLE_SQL_COMMENT_SCANNER, new Predicate() { // from class: android.database.sqlite.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).simpleSqlCommentScanner();
            }
        });
    }

    @Override // android.database.sqlite.FeatureFlags
    public boolean sqliteAllowTempTables() {
        return getValue(Flags.FLAG_SQLITE_ALLOW_TEMP_TABLES, new Predicate() { // from class: android.database.sqlite.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sqliteAllowTempTables();
            }
        });
    }

    @Override // android.database.sqlite.FeatureFlags
    public boolean sqliteApis35() {
        return getValue(Flags.FLAG_SQLITE_APIS_35, new Predicate() { // from class: android.database.sqlite.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sqliteApis35();
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
        return Arrays.asList(Flags.FLAG_SIMPLE_SQL_COMMENT_SCANNER, Flags.FLAG_SQLITE_ALLOW_TEMP_TABLES, Flags.FLAG_SQLITE_APIS_35);
    }
}

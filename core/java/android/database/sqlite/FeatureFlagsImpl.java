package android.database.sqlite;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.database.sqlite.FeatureFlags
    public boolean simpleSqlCommentScanner() {
        return true;
    }

    @Override // android.database.sqlite.FeatureFlags
    public boolean sqliteAllowTempTables() {
        return true;
    }

    @Override // android.database.sqlite.FeatureFlags
    public boolean sqliteApis35() {
        return true;
    }
}

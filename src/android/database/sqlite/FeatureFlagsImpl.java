package android.database.sqlite;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.database.sqlite.FeatureFlags
    public boolean concurrentOpenHelper() {
        return true;
    }

    @Override // android.database.sqlite.FeatureFlags
    public boolean noCheckpointOnFinalize() {
        return false;
    }

    @Override // android.database.sqlite.FeatureFlags
    public boolean onewayFinalizerCloseFixed() {
        return true;
    }

    @Override // android.database.sqlite.FeatureFlags
    public boolean sqliteApis35() {
        return true;
    }
}

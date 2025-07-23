package android.database.sqlite;

/* loaded from: classes.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_CONCURRENT_OPEN_HELPER = "android.database.sqlite.concurrent_open_helper";
    public static final String FLAG_NO_CHECKPOINT_ON_FINALIZE = "android.database.sqlite.no_checkpoint_on_finalize";
    public static final String FLAG_ONEWAY_FINALIZER_CLOSE_FIXED = "android.database.sqlite.oneway_finalizer_close_fixed";
    public static final String FLAG_SQLITE_APIS_35 = "android.database.sqlite.sqlite_apis_35";

    public static boolean concurrentOpenHelper() {
        return FEATURE_FLAGS.concurrentOpenHelper();
    }

    public static boolean noCheckpointOnFinalize() {
        return FEATURE_FLAGS.noCheckpointOnFinalize();
    }

    public static boolean onewayFinalizerCloseFixed() {
        return FEATURE_FLAGS.onewayFinalizerCloseFixed();
    }

    public static boolean sqliteApis35() {
        return FEATURE_FLAGS.sqliteApis35();
    }
}

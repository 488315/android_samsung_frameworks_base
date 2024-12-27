package android.database.sqlite;

/* loaded from: classes.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_SIMPLE_SQL_COMMENT_SCANNER =
            "android.database.sqlite.simple_sql_comment_scanner";
    public static final String FLAG_SQLITE_ALLOW_TEMP_TABLES =
            "android.database.sqlite.sqlite_allow_temp_tables";
    public static final String FLAG_SQLITE_APIS_35 = "android.database.sqlite.sqlite_apis_35";

    public static boolean simpleSqlCommentScanner() {
        return FEATURE_FLAGS.simpleSqlCommentScanner();
    }

    public static boolean sqliteAllowTempTables() {
        return FEATURE_FLAGS.sqliteAllowTempTables();
    }

    public static boolean sqliteApis35() {
        return FEATURE_FLAGS.sqliteApis35();
    }
}

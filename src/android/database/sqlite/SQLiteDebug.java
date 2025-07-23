package android.database.sqlite;

import android.os.Build;
import android.os.Process;
import android.os.SystemProperties;
import android.util.Log;
import android.util.Printer;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class SQLiteDebug {

    public static class PagerStats {
        public ArrayList<DbStats> dbStats;
        public int largestMemAlloc;
        public int memoryUsed;
        public int pageCacheOverflow;
    }

    private static native void nativeGetPagerStats(PagerStats pagerStats);

    public static final class NoPreloadHolder {
        public static final boolean DEBUG_LOG_DETAILED;
        public static final boolean DEBUG_LOG_SLOW_QUERIES;
        public static final boolean NO_DOUBLE_QUOTED_STRS;
        private static final String SLOW_QUERY_THRESHOLD_PROP = "db.log.slow_query_threshold";
        private static final String SLOW_QUERY_THRESHOLD_UID_PROP;
        public static final boolean DEBUG_SQL_LOG = Log.isLoggable("SQLiteLog", 2);
        public static final boolean DEBUG_SQL_STATEMENTS = Log.isLoggable("SQLiteStatements", 2);
        public static final boolean DEBUG_SQL_TIME = Log.isLoggable("SQLiteTime", 2);
        public static final boolean DEBUG_ENABLE = Build.IS_DEBUGGABLE;

        static {
            DEBUG_LOG_SLOW_QUERIES = Build.IS_DEBUGGABLE || Log.isLoggable("SQLiteSlowQueries", 2);
            SLOW_QUERY_THRESHOLD_UID_PROP = "db.log.slow_query_threshold." + Process.myUid();
            DEBUG_LOG_DETAILED = Build.IS_DEBUGGABLE && SystemProperties.getBoolean("db.log.detailed", false);
            NO_DOUBLE_QUOTED_STRS = SystemProperties.getBoolean("debug.sqlite.no_double_quoted_strs", false);
        }
    }

    private SQLiteDebug() {
    }

    public static boolean shouldLogSlowQuery(long j) {
        return j >= ((long) Math.min(SystemProperties.getInt("db.log.slow_query_threshold", Integer.MAX_VALUE), SystemProperties.getInt(NoPreloadHolder.SLOW_QUERY_THRESHOLD_UID_PROP, Integer.MAX_VALUE)));
    }

    public static final boolean shouldLogQueryPlan() {
        return SystemProperties.getBoolean("db.log.explain_query_plan", false);
    }

    public static final boolean shouldLogIndexRecommendation() {
        return SystemProperties.getBoolean("db.log.index_recommendation", false);
    }

    public static class DbStats {
        public final boolean arePoolStats;
        public final int cacheHits;
        public final int cacheMisses;
        public final int cacheSize;
        public String dbName;
        public long dbSize;
        public int lookaside;
        public long pageSize;

        public DbStats(String str, long j, long j2, int i, int i2, int i3, int i4, boolean z) {
            this.dbName = str;
            this.pageSize = j2 / 1024;
            this.dbSize = (j * j2) / 1024;
            this.lookaside = i;
            this.cacheHits = i2;
            this.cacheMisses = i3;
            this.cacheSize = i4;
            this.arePoolStats = z;
        }
    }

    public static PagerStats getDatabaseInfo() {
        PagerStats pagerStats = new PagerStats();
        nativeGetPagerStats(pagerStats);
        pagerStats.dbStats = SQLiteDatabase.getDbStats();
        return pagerStats;
    }

    public static long getMemoryUsed() {
        nativeGetPagerStats(new PagerStats());
        return r0.memoryUsed;
    }

    public static void dump(Printer printer, String[] strArr) {
        dump(printer, strArr, false);
    }

    public static void dump(Printer printer, String[] strArr, boolean z) {
        boolean z2 = false;
        for (String str : strArr) {
            if (str.equals("-v")) {
                z2 = true;
            }
        }
        SQLiteDatabase.dumpAll(printer, z2, z);
    }
}

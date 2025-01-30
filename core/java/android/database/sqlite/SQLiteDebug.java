package android.database.sqlite;

import android.p009os.Build;
import android.p009os.Process;
import android.p009os.SystemProperties;
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
    private static final String SLOW_QUERY_THRESHOLD_PROP = "db.log.slow_query_threshold";
    private static final String SLOW_QUERY_THRESHOLD_UID_PROP;
    public static final boolean DEBUG_SQL_LOG = Log.isLoggable("SQLiteLog", 2);
    public static final boolean DEBUG_SQL_STATEMENTS = Log.isLoggable("SQLiteStatements", 2);
    public static final boolean DEBUG_SQL_TIME = Log.isLoggable("SQLiteTime", 2);
    public static final boolean DEBUG_ENABLE = Build.IS_DEBUGGABLE;

    static {
      DEBUG_LOG_SLOW_QUERIES = Build.IS_DEBUGGABLE || Log.isLoggable("SQLiteSlowQueries", 2);
      SLOW_QUERY_THRESHOLD_UID_PROP = "db.log.slow_query_threshold." + Process.myUid();
      DEBUG_LOG_DETAILED =
          Build.IS_DEBUGGABLE && SystemProperties.getBoolean("db.log.detailed", false);
    }
  }

  private SQLiteDebug() {}

  public static boolean shouldLogSlowQuery(long elapsedTimeMillis) {
    int slowQueryMillis =
        Math.min(
            SystemProperties.getInt("db.log.slow_query_threshold", Integer.MAX_VALUE),
            SystemProperties.getInt(
                NoPreloadHolder.SLOW_QUERY_THRESHOLD_UID_PROP, Integer.MAX_VALUE));
    return elapsedTimeMillis >= ((long) slowQueryMillis);
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

    public DbStats(
        String dbName,
        long pageCount,
        long pageSize,
        int lookaside,
        int hits,
        int misses,
        int cachesize,
        boolean arePoolStats) {
      this.dbName = dbName;
      this.pageSize = pageSize / 1024;
      this.dbSize = (pageCount * pageSize) / 1024;
      this.lookaside = lookaside;
      this.cacheHits = hits;
      this.cacheMisses = misses;
      this.cacheSize = cachesize;
      this.arePoolStats = arePoolStats;
    }
  }

  public static PagerStats getDatabaseInfo() {
    PagerStats stats = new PagerStats();
    nativeGetPagerStats(stats);
    stats.dbStats = SQLiteDatabase.getDbStats();
    return stats;
  }

  public static long getMemoryUsed() {
    PagerStats stats = new PagerStats();
    nativeGetPagerStats(stats);
    return stats.memoryUsed;
  }

  public static void dump(Printer printer, String[] args) {
    dump(printer, args, false);
  }

  public static void dump(Printer printer, String[] args, boolean isSystem) {
    boolean verbose = false;
    for (String arg : args) {
      if (arg.equals("-v")) {
        verbose = true;
      }
    }
    SQLiteDatabase.dumpAll(printer, verbose, isSystem);
  }
}

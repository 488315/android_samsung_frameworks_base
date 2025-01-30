package android.database.sqlite;

import android.app.ActivityManager;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDebug;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Process;
import android.util.Log;
import com.android.internal.content.NativeLibraryHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class SQLiteSlowQueryCollector extends Thread {
    private static final int COLLECT_SCHEMA_VERSION = 2;
    public static final int COLLECT_THRESHOLD = 3000;
    private static final String COMPONENT_NAME = "Database";
    private static final String FEATURE_NAME = "SQUE";
    private static final int MAX_MESSAGE_COUNT = 4;
    private static final int MAX_MESSAGE_SIZE = 1022;
    private static final int MAX_NAME_SIZE = 255;
    private static final int MAX_QUERY_COUNT = 10;
    private static final int SAMPLING_THRESHOLD = 10800000;
    private static final int SLEEP_TIME_ADJUST_VALUE = 500;
    private static final String TAG = "SQLiteSlowQueryCollector";
    private Context mContext;
    private long mNow;
    private SlowQueryParams mParams;
    private int mPid;
    private SQLiteConnectionPool mPool;
    private int mSqlHash;
    private static AtomicBoolean sIsSending = new AtomicBoolean(false);
    private static long sLastCollectTime = -1;
    private static int sStartPoint = new Random().nextInt(10) + 1;
    private static ArrayList<Integer> sSqlHashList = new ArrayList<>();

    private SQLiteSlowQueryCollector(Context context, SQLiteConnectionPool pool, long now, int pid, SlowQueryParams params) {
        this.mContext = context;
        this.mPool = pool;
        this.mNow = now;
        this.mPid = pid;
        this.mParams = params;
        this.mSqlHash = params.mSql.hashCode();
    }

    public static void sendSlowQueryLog(SQLiteConnectionPool pool, int pid, SlowQueryParams params) {
        boolean z;
        try {
            SQLiteDatabase db = pool.getDatabase();
            if (db == null) {
                return;
            }
            Context context = db.getContext();
            if (context == null) {
                if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
                    Log.m98i(TAG, "Could not send a sql without the context. " + params.mLat + "ms-" + params.mDbn + " (" + params.mSql + NavigationBarInflaterView.KEY_CODE_END);
                    return;
                }
                return;
            }
            long now = System.currentTimeMillis();
            if (!isNeedCollect(now, params) || !sIsSending.compareAndSet(false, true)) {
                return;
            }
            try {
                z = false;
            } catch (Exception e) {
                e = e;
                z = false;
            }
            try {
                SQLiteSlowQueryCollector collector = new SQLiteSlowQueryCollector(context, pool, now, pid, params);
                collector.start();
            } catch (Exception e2) {
                e = e2;
                sIsSending.set(z);
                if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
                    Log.m102w(TAG, "Failed to start a thread for a sql. " + params.mLat + "ms-" + params.mDbn + " (" + params.mSql + NavigationBarInflaterView.KEY_CODE_END);
                    e.printStackTrace();
                    throw e;
                }
            }
        } catch (Exception e3) {
        }
    }

    private static boolean isNeedCollect(long now, SlowQueryParams params) {
        int sqlLength = params.mSql.length();
        if (sqlLength > 4088) {
            if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
                Log.m102w(TAG, "Too long to send a sql, just leave a log. (" + sqlLength + " ), " + params.mLat + "ms-" + params.mDbn + " (" + params.mSql + NavigationBarInflaterView.KEY_CODE_END);
            }
            return false;
        }
        if (!checkSql(params.mSql)) {
            return false;
        }
        long j = sLastCollectTime;
        if (j > 0 && now - j < 10800000) {
            if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
                Log.m98i(TAG, "Could send a sql during 3 hours. " + params.mLat + "ms-" + params.mDbn + " (" + params.mSql + NavigationBarInflaterView.KEY_CODE_END);
            }
            return false;
        }
        int i = sStartPoint;
        if (i > 0) {
            int i2 = i - 1;
            sStartPoint = i2;
            if (i2 > 0) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.util.concurrent.atomic.AtomicBoolean] */
    /* JADX WARN: Type inference failed for: r1v7 */
    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        ?? r1 = 0;
        r1 = 0;
        try {
            try {
            } catch (Exception e) {
                if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
                    Log.m102w(TAG, "Failed to send a sql. " + this.mParams.mLat + "ms-" + this.mParams.mDbn + " (" + this.mParams.mSql + NavigationBarInflaterView.KEY_CODE_END);
                    e.printStackTrace();
                }
            }
            if (queryCollectorPhaseOne()) {
                if (queryCollectorPhaseTwo()) {
                    if (queryCollectorPhaseThree()) {
                        queryCollectorPostProcess();
                    }
                }
            }
        } finally {
            this.mContext = r1;
            sIsSending.set(false);
        }
    }

    private boolean queryCollectorPhaseOne() {
        SQLiteDatabase db = this.mPool.getDatabase();
        if (db == null || !db.isOpen()) {
            return false;
        }
        for (int i = 0; i < sSqlHashList.size(); i++) {
            if (this.mSqlHash == sSqlHashList.get(i).intValue()) {
                return false;
            }
        }
        if (!checkDeviceStatus()) {
            return false;
        }
        String databaseFilePath = db.getPath();
        this.mParams.mDbs = getFileSize(databaseFilePath);
        if (db.isWriteAheadLoggingEnabled()) {
            this.mParams.mWas = getFileSize(databaseFilePath + "-wal");
        }
        String processName = this.mContext.getPackageName();
        String clientName = processName;
        int pid = Process.myPid();
        if (pid != this.mPid) {
            ActivityManager am = (ActivityManager) this.mContext.getSystemService("activity");
            List<ActivityManager.RunningAppProcessInfo> processInfoList = am.getRunningAppProcesses();
            if (processInfoList != null) {
                Iterator<ActivityManager.RunningAppProcessInfo> it = processInfoList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ActivityManager.RunningAppProcessInfo processInfo = it.next();
                    if (processInfo.pid == this.mPid) {
                        clientName = processInfo.processName;
                        break;
                    }
                }
            }
        }
        this.mParams.mMpn = processName.substring(0, Math.min(processName.length(), 255));
        this.mParams.mCpn = clientName.substring(0, Math.min(clientName.length(), 255));
        SQLiteConnection connection = null;
        try {
            try {
                connection = this.mPool.acquireConnection("PRAGMA page_count;", 1, null);
                this.mParams.mPgc = connection.executeForLong("PRAGMA page_count;", null, null);
                this.mParams.mVer = connection.executeForLong("PRAGMA user_version;", null, null);
                SlowQueryParams slowQueryParams = this.mParams;
                slowQueryParams.mHiddenSql = connection.executeForString("SELECT sqlite3_hide_consts(?);", new String[]{slowQueryParams.mSql}, null);
                if (connection != null) {
                    this.mPool.releaseConnection(connection);
                }
                if (this.mParams.mHiddenSql == null) {
                    return false;
                }
                if (!this.mParams.mHiddenSql.endsWith(NavigationBarInflaterView.GRAVITY_SEPARATOR)) {
                    StringBuilder sb = new StringBuilder();
                    SlowQueryParams slowQueryParams2 = this.mParams;
                    slowQueryParams2.mHiddenSql = sb.append(slowQueryParams2.mHiddenSql).append(NavigationBarInflaterView.GRAVITY_SEPARATOR).toString();
                }
                String databaseName = this.mParams.mDbn;
                if (databaseName.contains("/")) {
                    databaseName = databaseName.substring(databaseName.lastIndexOf("/") + 1);
                }
                String hashedDBName = Integer.toHexString(databaseName.hashCode());
                this.mParams.mHashedDbn = hashedDBName.substring(0, Math.min(hashedDBName.length(), 255));
                return true;
            } catch (Exception e) {
                if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
                    Log.m102w(TAG, "Failed to execute PRAGMA statements. " + this.mParams.mLat + "ms-" + this.mParams.mDbn + " (" + this.mParams.mSql + NavigationBarInflaterView.KEY_CODE_END);
                    e.printStackTrace();
                }
                if (connection != null) {
                    this.mPool.releaseConnection(connection);
                }
                return false;
            }
        } catch (Throwable th) {
            if (connection != null) {
                this.mPool.releaseConnection(connection);
            }
            throw th;
        }
    }

    private boolean queryCollectorPhaseTwo() {
        String subSql;
        this.mParams.mPackets = new ArrayList<>(4);
        StringBuilder sb = new StringBuilder(1024);
        sb.append("\"QCV\":2,");
        sb.append("\"MPN\":\"" + this.mParams.mMpn + "\",");
        sb.append("\"CPN\":\"" + this.mParams.mCpn + "\",");
        sb.append("\"DBN\":\"" + this.mParams.mHashedDbn + "\",");
        sb.append("\"DBS\":" + this.mParams.mDbs + ",");
        sb.append("\"WAS\":" + this.mParams.mWas + ",");
        sb.append("\"PGC\":" + this.mParams.mPgc + ",");
        sb.append("\"VER\":" + this.mParams.mVer + ",");
        sb.append("\"LAT\":" + this.mParams.mLat + ",");
        sb.append("\"CRW\":" + this.mParams.mCurrentRows + ",");
        sb.append("\"TRW\":" + this.mParams.mTotalRows + ",");
        String sql = this.mParams.mHiddenSql;
        int sqlLength = sql.length();
        if ((1022 - sb.toString().length()) - "\"SQL\":\"\"".length() > sqlLength) {
            sb.append("\"SQL\":\"" + sql + "\"");
            this.mParams.mPackets.add(sb.toString());
            return true;
        }
        String magic = "\"PQM\":\"" + Integer.toHexString(this.mSqlHash) + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + Long.toHexString(this.mNow);
        int bufSize = ((1022 - sb.toString().length()) - magic.length()) - "-0\",\"SQL\":\"\"".length();
        if (bufSize * 4 < sqlLength) {
            if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
                Log.m102w(TAG, "Too long to send a sql, just leave a log. (" + sqlLength + " ), " + this.mParams.mLat + "ms-" + this.mParams.mDbn + " (" + this.mParams.mSql + NavigationBarInflaterView.KEY_CODE_END);
                return false;
            }
            return false;
        }
        int index = 0;
        for (int partial = 0; index < sqlLength && partial < 4; partial++) {
            if (index + bufSize < sqlLength) {
                subSql = sql.substring(index, index + bufSize);
            } else {
                subSql = sql.substring(index, sqlLength);
            }
            String msg = sb.toString() + magic + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + partial + "\",\"SQL\":\"" + subSql + "\"";
            this.mParams.mPackets.add(msg);
            index += bufSize;
        }
        return true;
    }

    private boolean queryCollectorPhaseThree() {
        return true;
    }

    private void queryCollectorPostProcess() {
        if (sSqlHashList.size() >= 10) {
            sSqlHashList.remove(0);
        }
        sSqlHashList.add(Integer.valueOf(this.mSqlHash));
        sLastCollectTime = this.mNow;
    }

    private long getFileSize(String path) {
        File f = new File(path);
        return f.length();
    }

    private static boolean checkSql(String sql) {
        int n = DatabaseUtils.getSqlStatementType(sql);
        if (n == 1 || n == 2 || n == 99) {
            return true;
        }
        return false;
    }

    private boolean checkDeviceStatus() {
        long[] lastWakeupAndSleepTime = {0, 0};
        long lastWakeupTime = lastWakeupAndSleepTime[0];
        long lastSleepTime = lastWakeupAndSleepTime[1];
        if (lastWakeupTime > lastSleepTime) {
            if (this.mParams.mStartTime <= lastWakeupTime) {
                return false;
            }
            return true;
        }
        long lastSleepTime2 = lastSleepTime + 500;
        if (this.mParams.mStartTime <= lastWakeupTime || this.mParams.mStartTime + this.mParams.mLat >= lastSleepTime2) {
            return false;
        }
        return true;
    }

    public static final class SlowQueryParams {
        public String mCpn;
        public long mCurrentRows;
        public String mDbn;
        public long mDbs;
        public String mHashedDbn;
        public String mHiddenSql;
        public long mLat;
        public String mMpn;
        public ArrayList<String> mPackets;
        public long mPgc;
        public String mSql;
        public long mStartTime;
        public long mTotalRows;
        public long mVer;
        public long mWas;

        public SlowQueryParams(String databaseName, long startTime, long executionTime, String sql, long currentRows, long totalRows) {
            this.mDbn = databaseName;
            this.mLat = executionTime;
            this.mSql = sql;
            this.mCurrentRows = currentRows;
            this.mTotalRows = totalRows;
            this.mStartTime = startTime;
        }
    }
}

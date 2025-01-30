package android.database.sqlite;

import android.content.Context;
import android.content.Intent;
import android.content.p002pm.PackageManager;
import android.util.Log;
import java.io.File;

/* loaded from: classes.dex */
public class SQLiteUtils {
    public static final long DB_SIZE_CHECK_PERIOD = 3600000;
    private static final String TAG = "SQLiteUtils";
    private static volatile int sIssueTrackerOn = -1;
    private static final Object mLock = new Object();
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static boolean isPackageInstalled(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                Log.m94d(TAG, "The PackageManager is null.");
                return false;
            }
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isIssueTrackerOn(SQLiteDatabase db) {
        if (sIssueTrackerOn < 0) {
            synchronized (mLock) {
                if (sIssueTrackerOn < 0) {
                    Context context = db.getContext();
                    if (context != null && isPackageInstalled(context, "com.salab.issuetracker")) {
                        sIssueTrackerOn = 1;
                    } else {
                        sIssueTrackerOn = 0;
                    }
                }
            }
        }
        return sIssueTrackerOn > 0;
    }

    public static long getFileSize(String path) {
        File f = new File(path);
        return f.length();
    }

    private static class ReportRunnable implements Runnable {
        Context context;
        long size;

        public ReportRunnable(Context context, long size) {
            this.context = context;
            this.size = size;
        }

        @Override // java.lang.Runnable
        public void run() {
            Intent issueTrackerIntent = new Intent("com.salab.issuetracker.intent.ACTION_STORAGE_ISSUE");
            issueTrackerIntent.putExtra("ERRCODE", Integer.valueOf(PackageManager.INSTALL_FAILED_PROCESS_NOT_DEFINED));
            issueTrackerIntent.putExtra("ERRNAME", "DBSIZE");
            String packageName = this.context.getPackageName();
            issueTrackerIntent.putExtra("ERRPKG", packageName);
            issueTrackerIntent.putExtra("ERRMSG", "Abnormal database file size has been detected from '" + packageName + "' size: " + ((this.size / 1024) / 1024) + "MB");
            this.context.sendBroadcast(issueTrackerIntent);
        }
    }

    private static void reportAbnormalDBSize(Context context, long dbSize) {
        Thread t = new Thread(new ReportRunnable(context, dbSize));
        t.start();
    }

    public static void checkAbnormalDBSize(SQLiteDatabase db, String dbPath) {
        Context context;
        if (db == null || (context = db.getContext()) == null) {
            return;
        }
        try {
            long dbSize = getFileSize(dbPath);
            if (dbSize > 1048576000) {
                File mark = new File(dbPath + "-1GB");
                if (!mark.exists()) {
                    Log.m94d(TAG, "Abnormal database file size has been detected from '" + dbPath + "' size: " + ((dbSize / 1024) / 1024) + "MB");
                    reportAbnormalDBSize(context, dbSize);
                    mark.createNewFile();
                }
            } else if (dbSize > 524288000) {
                File mark2 = new File(dbPath + "-500MB");
                if (!mark2.exists()) {
                    Log.m94d(TAG, "Abnormal database file size has been detected from '" + dbPath + "' size: " + ((dbSize / 1024) / 1024) + "MB");
                    reportAbnormalDBSize(context, dbSize);
                    mark2.createNewFile();
                }
            }
        } catch (Exception e) {
        }
    }

    public static String getHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 255;
            char[] cArr = hexArray;
            hexChars[j * 2] = cArr[v >>> 4];
            hexChars[(j * 2) + 1] = cArr[v & 15];
        }
        return new String(hexChars);
    }
}

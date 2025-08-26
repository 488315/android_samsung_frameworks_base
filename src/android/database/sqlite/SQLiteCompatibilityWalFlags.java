package android.database.sqlite;

import android.app.ActivityThread;
import android.app.Application;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.KeyValueListParser;
import android.util.Log;

/* loaded from: classes.dex */
public class SQLiteCompatibilityWalFlags {
    private static final String TAG = "SQLiteCompatibilityWalFlags";
    private static volatile boolean sCallingGlobalSettings = false;
    private static volatile boolean sInitialized = false;
    private static volatile boolean sLegacyCompatibilityWalEnabled = false;
    private static volatile long sTruncateSize = -1;
    private static volatile String sWALSyncMode;

    private SQLiteCompatibilityWalFlags() {
    }

    public static boolean isLegacyCompatibilityWalEnabled() {
        initIfNeeded();
        return sLegacyCompatibilityWalEnabled;
    }

    public static String getWALSyncMode() {
        initIfNeeded();
        if (!sLegacyCompatibilityWalEnabled) {
            throw new IllegalStateException("isLegacyCompatibilityWalEnabled() == false");
        }
        return sWALSyncMode;
    }

    public static long getTruncateSize() {
        initIfNeeded();
        return sTruncateSize;
    }

    private static void initIfNeeded() {
        if (sInitialized || sCallingGlobalSettings) {
            return;
        }
        ActivityThread activityThreadCurrentActivityThread = ActivityThread.currentActivityThread();
        String string = null;
        Application application = activityThreadCurrentActivityThread == null ? null : activityThreadCurrentActivityThread.getApplication();
        if (application == null) {
            Log.w(TAG, "Cannot read global setting sqlite_compatibility_wal_flags - Application state not available");
        } else {
            try {
                try {
                    sCallingGlobalSettings = true;
                    string = Settings.Global.getString(application.getContentResolver(), Settings.Global.SQLITE_COMPATIBILITY_WAL_FLAGS);
                } catch (Exception e) {
                    Log.w(TAG, "Cannot read global setting sqlite_compatibility_wal_flags - " + e.toString());
                }
            } finally {
                sCallingGlobalSettings = false;
            }
        }
        init(string);
    }

    public static void init(String str) {
        if (TextUtils.isEmpty(str)) {
            sInitialized = true;
            return;
        }
        KeyValueListParser keyValueListParser = new KeyValueListParser(',');
        try {
            keyValueListParser.setString(str);
            sLegacyCompatibilityWalEnabled = keyValueListParser.getBoolean("legacy_compatibility_wal_enabled", false);
            sWALSyncMode = keyValueListParser.getString("wal_syncmode", SQLiteGlobal.getWALSyncMode());
            sTruncateSize = keyValueListParser.getInt("truncate_size", -1);
            Log.i(TAG, "Read compatibility WAL flags: legacy_compatibility_wal_enabled=" + sLegacyCompatibilityWalEnabled + ", wal_syncmode=" + sWALSyncMode);
            sInitialized = true;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Setting has invalid format: " + str, e);
            sInitialized = true;
        }
    }

    public static void reset() {
        sInitialized = false;
        sLegacyCompatibilityWalEnabled = false;
        sWALSyncMode = null;
    }
}

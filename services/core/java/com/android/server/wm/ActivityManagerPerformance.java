package com.android.server.wm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SemHqmManager;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.am.DynamicHiddenApp;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.input.KeyboardMetricsCollector;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import com.samsung.android.os.SemDvfsManager;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ActivityManagerPerformance {
    public static final boolean AMP_ENABLE;
    public static final boolean AMP_PERF_ENABLE;
    public static final boolean AMP_RELAUNCH_RESUME_ON;
    public static final boolean DEBUG;
    public static final boolean DEBUG_TRACE;
    public static final int TIMEOUT_ACT_RESUME;
    public static final int TIMEOUT_ACT_START;
    public static final int TIMEOUT_APP_SWITCH;
    public static final int TIMEOUT_PREV_RESUME;
    public static final String[] WALLPAPER_PROFILE;
    public static ActivityManagerPerformance booster;
    public static ActivityRecord curTopAct;
    public static int curTopState;
    public static final String[] gBlockedPkgs;
    public static final String[] gSystemuiPkgs;
    public static final boolean isChinaModel;
    public static boolean isPerfReserveSupport;
    public static SQLiteDatabase mAppLaunchDB;
    public static AppLaunchTimeDBHelper mAppLaunchDBHelper;
    public static final String[] mAppLaunchPackagesTimeOutLM;
    public static final String[] mAppLaunchPackagesTimeOutM;
    public static Context mContext;
    public static DynamicHiddenApp mDynamicHiddenApp;
    public static long mFoldListenedTime;
    public static final Object mLockAppSwitch;
    public static final Object mLockinit;
    public static final Base64.Decoder pkgDecoder;
    public static ActivityRecord prevSwitchActivity;
    public static ActivityRecord rCurBoostAppSwitch;
    public static final String[] sLowPerformancePkgList;
    public static HashSet sLowPerformancePkgSet;
    public static long sNextTimeToSendSlugBigdata;
    public boolean isMultiWindowResume;
    public long lastHomeBoostedTime;
    public long lastHomePressedTime;
    public SemDvfsManager mBoosterActResume;
    public SemDvfsManager mBoosterActStart;
    public SemDvfsManager mBoosterAppLaunch;
    public SemDvfsManager mBoosterAppSwitch;
    public SemDvfsManager mBoosterHome;
    public SemDvfsManager mBoosterLazy;
    public SemDvfsManager mBoosterPrevResume;
    public SemDvfsManager mBoosterRelaunchResume;
    public SemDvfsManager mBoosterTail;
    public final DeviceStateManager mDeviceStateManager;
    public final MainHandler mHandler;
    public SemHqmManager mHqmManager;
    public final boolean mIsAppLaunchDBInit;
    public final boolean mIsTaskBoostExist;
    public SemDvfsManager mLuckyMoneyBooster;
    public final ActivityTaskManagerService mService;
    public final SemDvfsManager mTaskBoostManager;
    public boolean needSkipResume;
    public ActivityRecord rCurBoostActResume;
    public ActivityRecord rCurBoostActStart;
    public ActivityRecord rLastActHome;
    public ActivityRecord rLastActTail;
    public ActivityRecord rLastRelaunchResume;
    public final Set mBigdataSlugSkipPackage = new HashSet(Arrays.asList(x("YW5kcm9pZA=="), x("Y29tLmdvb2dsZS5hbmRyb2lkLnBhY2thZ2VpbnN0YWxsZXI="), x("Y29tLnRydWVjYWxsZXI="), x("Y29tLmdvb2dsZS5hbmRyb2lkLnBlcm1pc3Npb25jb250cm9sbGVy"), x("Y29tLnNlYy5hbmRyb2lkLmFwcC5sYXVuY2hlcg=="), x("c3lzdGVtdWk=")));
    public boolean mIsSdhmsInitCompleted = false;
    public boolean mIsMidGroundCpuSetEnable = false;
    public long mDramSize = 0;
    public final Map mAppLaunchTimeCountStats = new HashMap();
    public boolean mIsFolded = false;
    public final AnonymousClass1 mDeviceStateCallback = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.server.wm.ActivityManagerPerformance.1
        public final void onDeviceStateChanged(DeviceState deviceState) {
            ActivityManagerPerformance.this.mIsFolded = deviceState.hasProperties(new int[]{1});
            if (ActivityManagerPerformance.this.mIsFolded) {
                ActivityManagerPerformance.mFoldListenedTime = SystemClock.uptimeMillis();
            }
            if (ActivityManagerPerformance.DEBUG) {
                Slog.d("ActivityManagerPerformance", "onDisplayFoldChanged: state = " + ActivityManagerPerformance.this.mIsFolded + " " + deviceState);
            }
        }
    };
    public final Object mLockActResume = new Object();
    public final Object mLockActStart = new Object();
    public final Object mLockTail = new Object();
    public final Object mLockHome = new Object();
    public final Object mLockRelaunchResume = new Object();
    public final Object mLockAppLaunch = new Object();
    public boolean mIsScreenOn = true;
    public final AnonymousClass2 mIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.wm.ActivityManagerPerformance.2
        /* JADX WARN: Removed duplicated region for block: B:71:0x01e5 A[Catch: Exception -> 0x01af, TryCatch #1 {Exception -> 0x01af, blocks: (B:53:0x0164, B:55:0x017a, B:61:0x018e, B:64:0x0195, B:68:0x01dd, B:71:0x01e5, B:73:0x01eb, B:75:0x01f4, B:83:0x01b8, B:85:0x01bd), top: B:52:0x0164 }] */
        /* JADX WARN: Removed duplicated region for block: B:75:0x01f4 A[Catch: Exception -> 0x01af, TRY_LEAVE, TryCatch #1 {Exception -> 0x01af, blocks: (B:53:0x0164, B:55:0x017a, B:61:0x018e, B:64:0x0195, B:68:0x01dd, B:71:0x01e5, B:73:0x01eb, B:75:0x01f4, B:83:0x01b8, B:85:0x01bd), top: B:52:0x0164 }] */
        /* JADX WARN: Removed duplicated region for block: B:78:0x020f A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:80:0x020f A[ADDED_TO_REGION, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:94:0x02b0  */
        /* JADX WARN: Removed duplicated region for block: B:97:0x025f A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r31, android.content.Intent r32) {
            /*
                Method dump skipped, instructions count: 721
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityManagerPerformance.AnonymousClass2.onReceive(android.content.Context, android.content.Intent):void");
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppLaunchTimeDBHelper extends SQLiteOpenHelper {
        public static final Object dbLock = new Object();
        public String mBuildVersion;
        public long mDBDayOfWeek;
        public long mDevicePowerOnCount;
        public long mLastModifiedTime;
        public long mStartTimeGathered;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class AppLaunchTimeDBErrorHandler implements DatabaseErrorHandler {
            @Override // android.database.DatabaseErrorHandler
            public final void onCorruption(SQLiteDatabase sQLiteDatabase) {
                Slog.e("ActivityManagerPerformance", "app launch time db is deleted by AppLaunchTimeDBErrorHandler");
                SQLiteDatabase.deleteDatabase(new File(sQLiteDatabase.getPath()));
            }
        }

        /* renamed from: -$$Nest$mdeleteDBData, reason: not valid java name */
        public static void m1050$$Nest$mdeleteDBData(AppLaunchTimeDBHelper appLaunchTimeDBHelper, SQLiteDatabase sQLiteDatabase) {
            int i;
            appLaunchTimeDBHelper.getClass();
            String[] strArr = {"AppLaunchTimeCountStats"};
            synchronized (dbLock) {
                try {
                    i = sQLiteDatabase.delete("AppLaunchTime", "NAME=?", strArr);
                    if (i > 0) {
                        appLaunchTimeDBHelper.mLastModifiedTime = Calendar.getInstance().getTimeInMillis();
                    }
                } catch (SQLException e) {
                    Slog.e("ActivityManagerPerformance", "AppLaunchTime Failed to delete. " + e.getMessage());
                    i = -1;
                }
            }
            if (i <= 0) {
                Slog.e("ActivityManagerPerformance", "AppLaunchTime Failed to delete AppLaunchTimeCountStats");
            }
        }

        /* renamed from: -$$Nest$mfetch, reason: not valid java name */
        public static void m1051$$Nest$mfetch(AppLaunchTimeDBHelper appLaunchTimeDBHelper, SQLiteDatabase sQLiteDatabase) {
            Cursor cursor;
            appLaunchTimeDBHelper.getClass();
            try {
                cursor = sQLiteDatabase.query("AppLaunchTime", new String[]{"NAME", "VALUE"}, null, null, null, null, null);
            } catch (SQLiteException e) {
                Slog.e("ActivityManagerPerformance:AppLaunchTime", "Failed to query. " + e.getMessage());
                cursor = null;
            }
            if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                throw new SQLiteException("failed to fetch");
            }
            do {
                try {
                    String string = cursor.getString(0);
                    String string2 = cursor.getString(1);
                    if ("AppLaunchTimeCountStats".equals(string)) {
                        appLaunchTimeDBHelper.fetchAppLaunchTimeCountStatsFromDBValue(string2);
                    } else if ("DayOfWeek".equals(string)) {
                        appLaunchTimeDBHelper.mDBDayOfWeek = Long.parseLong(string2);
                    } else if ("lastModifiedTime".equals(string)) {
                        appLaunchTimeDBHelper.mLastModifiedTime = Long.parseLong(string2);
                    } else if ("startTimeOfGatheredData".equals(string)) {
                        appLaunchTimeDBHelper.mStartTimeGathered = Long.parseLong(string2);
                    } else if ("buildVersion".equals(string)) {
                        appLaunchTimeDBHelper.mBuildVersion = string2;
                    } else if ("devicePowerOnCount".equals(string)) {
                        appLaunchTimeDBHelper.mDevicePowerOnCount = Long.parseLong(string2);
                    }
                } catch (Throwable th) {
                    cursor.close();
                    throw th;
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        /* renamed from: -$$Nest$mupdateDataInTable, reason: not valid java name */
        public static void m1052$$Nest$mupdateDataInTable(AppLaunchTimeDBHelper appLaunchTimeDBHelper, SQLiteDatabase sQLiteDatabase, String str) {
            String valueOf;
            int i;
            long j;
            long insert;
            appLaunchTimeDBHelper.getClass();
            ContentValues contentValues = new ContentValues();
            if ("AppLaunchTimeCountStats".equals(str)) {
                JSONObject jSONObject = new JSONObject();
                String[] strArr = {"cold", "warm", "hot"};
                try {
                    Iterator it = ((HashMap) ActivityManagerPerformance.this.mAppLaunchTimeCountStats).entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = (Map.Entry) it.next();
                        String str2 = (String) entry.getKey();
                        Map map = (Map) entry.getValue();
                        JSONObject jSONObject2 = new JSONObject();
                        Iterator it2 = map.entrySet().iterator();
                        while (it2.hasNext()) {
                            Map.Entry entry2 = (Map.Entry) it2.next();
                            String str3 = (String) entry2.getKey();
                            Map map2 = (Map) entry2.getValue();
                            JSONObject jSONObject3 = new JSONObject();
                            Iterator it3 = map2.entrySet().iterator();
                            while (it3.hasNext()) {
                                Map.Entry entry3 = (Map.Entry) it3.next();
                                String str4 = (String) entry3.getKey();
                                Long[][] lArr = (Long[][]) entry3.getValue();
                                Iterator it4 = it;
                                JSONObject jSONObject4 = new JSONObject();
                                Iterator it5 = it2;
                                Iterator it6 = it3;
                                for (int i2 = 0; i2 < 3; i2++) {
                                    try {
                                        jSONObject4.put(strArr[i2], new JSONArray(lArr[i2]));
                                    } catch (JSONException unused) {
                                        if (ActivityManagerPerformance.DEBUG) {
                                            Slog.e("ActivityManagerPerformance", "the pec stats in '" + str2 + "' is not normal");
                                        }
                                    }
                                }
                                jSONObject3.put(str4, jSONObject4);
                                jSONObject2.put(str3, jSONObject3);
                                jSONObject.put(str2, jSONObject2);
                                it = it4;
                                it3 = it6;
                                it2 = it5;
                            }
                        }
                    }
                } catch (Exception unused2) {
                    Slog.e("ActivityManagerPerformance", "failed to getJsonFromAppLaunchTimeCountStats");
                }
                valueOf = jSONObject.length() > 0 ? jSONObject.toString() : "";
            } else {
                valueOf = "DayOfWeek".equals(str) ? String.valueOf(appLaunchTimeDBHelper.mDBDayOfWeek) : "lastModifiedTime".equals(str) ? String.valueOf(appLaunchTimeDBHelper.mLastModifiedTime) : "startTimeOfGatheredData".equals(str) ? String.valueOf(appLaunchTimeDBHelper.mStartTimeGathered) : "buildVersion".equals(str) ? appLaunchTimeDBHelper.mBuildVersion : "devicePowerOnCount".equals(str) ? String.valueOf(appLaunchTimeDBHelper.mDevicePowerOnCount) : null;
            }
            if (valueOf == null) {
                return;
            }
            contentValues.put("NAME", str);
            contentValues.put("VALUE", valueOf);
            String[] strArr2 = {str};
            synchronized (dbLock) {
                try {
                    i = sQLiteDatabase.update("AppLaunchTime", contentValues, "NAME=?", strArr2);
                    if (i > 0) {
                        appLaunchTimeDBHelper.mLastModifiedTime = Calendar.getInstance().getTimeInMillis();
                    }
                } catch (SQLiteException e) {
                    Slog.e("ActivityManagerPerformance:AppLaunchTime", "Failed to update. " + e.getMessage());
                    i = -1;
                }
            }
            if (i <= 0) {
                synchronized (dbLock) {
                    j = -1;
                    try {
                        insert = sQLiteDatabase.insert("AppLaunchTime", null, contentValues);
                        if (insert != -1) {
                            appLaunchTimeDBHelper.mLastModifiedTime = Calendar.getInstance().getTimeInMillis();
                        }
                    } catch (SQLiteException e2) {
                        Slog.e("ActivityManagerPerformance:AppLaunchTime", "Failed to insert. " + e2.getMessage());
                    }
                }
                j = insert;
                if (j < 0) {
                    Slog.e("ActivityManagerPerformance", "AppLaunchTime Failed to insert ".concat(str));
                }
            }
        }

        public AppLaunchTimeDBHelper(Context context) {
            super(context, "/data/misc/AppLaunchTime/app_launch_time.db", null, 1, new AppLaunchTimeDBErrorHandler());
            this.mDBDayOfWeek = -1L;
            this.mLastModifiedTime = -1L;
            this.mStartTimeGathered = -1L;
            this.mDevicePowerOnCount = -1L;
            this.mBuildVersion = null;
        }

        /* JADX WARN: Removed duplicated region for block: B:69:0x017b A[Catch: JSONException -> 0x019c, TRY_LEAVE, TryCatch #5 {JSONException -> 0x019c, blocks: (B:67:0x0177, B:69:0x017b), top: B:66:0x0177 }] */
        /* JADX WARN: Removed duplicated region for block: B:92:0x019f  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void fetchAppLaunchTimeCountStatsFromDBValue(java.lang.String r28) {
            /*
                Method dump skipped, instructions count: 506
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityManagerPerformance.AppLaunchTimeDBHelper.fetchAppLaunchTimeCountStatsFromDBValue(java.lang.String):void");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onCreate(SQLiteDatabase sQLiteDatabase) {
            try {
                sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS AppLaunchTime (NAME TEXT unique, VALUE TEXT);");
                Slog.i("ActivityManagerPerformance", "AppLaunchTime table creation done");
            } catch (Exception e) {
                Slog.e("ActivityManagerPerformance", "failed to create AppLaunchTime table");
                e.printStackTrace();
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum LaunchTimeRange {
        /* JADX INFO: Fake field, exist only in values array */
        EF7("UNDER_500"),
        /* JADX INFO: Fake field, exist only in values array */
        EF17("UNDER_1000"),
        /* JADX INFO: Fake field, exist only in values array */
        EF27("UNDER_1500"),
        /* JADX INFO: Fake field, exist only in values array */
        EF37("UNDER_2000"),
        /* JADX INFO: Fake field, exist only in values array */
        EF47("UNDER_2500"),
        /* JADX INFO: Fake field, exist only in values array */
        EF57("UNDER_3000"),
        /* JADX INFO: Fake field, exist only in values array */
        EF67("UNDER_4000"),
        /* JADX INFO: Fake field, exist only in values array */
        EF77("UNDER_5000"),
        /* JADX INFO: Fake field, exist only in values array */
        EF88("UNDER_10000"),
        /* JADX INFO: Fake field, exist only in values array */
        EF98("OVER_THAN_10000");

        private final int threshold;

        LaunchTimeRange(String str) {
            this.threshold = r2;
        }

        public final int getThreshold() {
            return this.threshold;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper, null, true);
        }

        /* JADX WARN: Can't wrap try/catch for region: R(11:211|(1:213)(1:246)|214|(8:241|242|217|(2:219|(2:221|222))|226|227|228|(1:237)(2:232|(2:234|235)(1:236)))|216|217|(0)|226|227|228|(2:230|237)(1:238)) */
        /* JADX WARN: Code restructure failed: missing block: B:240:0x046d, code lost:
        
            r4 = null;
         */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0039  */
        /* JADX WARN: Removed duplicated region for block: B:219:0x03ef  */
        /* JADX WARN: Removed duplicated region for block: B:269:0x04e0  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r24) {
            /*
                Method dump skipped, instructions count: 1298
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityManagerPerformance.MainHandler.handleMessage(android.os.Message):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SlugBigDataInfo {
        public final String activityName;
        public final long launchTime;
        public final int launchType;
        public final String packageName;

        public SlugBigDataInfo(int i, String str, long j, String str2) {
            this.packageName = str;
            this.activityName = str2;
            this.launchTime = j;
            this.launchType = i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum TransitionLaunchType {
        /* JADX INFO: Fake field, exist only in values array */
        EF0("COLD", "cold"),
        /* JADX INFO: Fake field, exist only in values array */
        EF1("WARM", "warm"),
        HOT("HOT", "hot"),
        UNKNOWN("UNKNOWN", "unknown");

        private String name;
        private int type;

        TransitionLaunchType(String str, String str2) {
            this.type = r2;
            this.name = str2;
        }

        public final String getName() {
            return this.name;
        }

        public final int getType() {
            return this.type;
        }
    }

    /* renamed from: -$$Nest$mwriteAppLaunchTimeCountStatsToDB, reason: not valid java name */
    public static void m1049$$Nest$mwriteAppLaunchTimeCountStatsToDB(ActivityManagerPerformance activityManagerPerformance) {
        activityManagerPerformance.getClass();
        boolean z = DEBUG;
        SQLiteDatabase writableDatabase = mAppLaunchDBHelper.getWritableDatabase();
        mAppLaunchDB = writableDatabase;
        try {
            if (writableDatabase == null) {
                Slog.e("ActivityManagerPerformance", "failed DB writable open to writeAppLaunchTimeCountStatsToDB.");
                return;
            }
            try {
                AppLaunchTimeDBHelper appLaunchTimeDBHelper = mAppLaunchDBHelper;
                Objects.requireNonNull(appLaunchTimeDBHelper);
                AppLaunchTimeDBHelper.m1052$$Nest$mupdateDataInTable(appLaunchTimeDBHelper, writableDatabase, "AppLaunchTimeCountStats");
                AppLaunchTimeDBHelper appLaunchTimeDBHelper2 = mAppLaunchDBHelper;
                SQLiteDatabase sQLiteDatabase = mAppLaunchDB;
                Objects.requireNonNull(appLaunchTimeDBHelper2);
                AppLaunchTimeDBHelper.m1052$$Nest$mupdateDataInTable(appLaunchTimeDBHelper2, sQLiteDatabase, "lastModifiedTime");
                if (z) {
                    Slog.d("ActivityManagerPerformance", "writeAppLaunchTimeCountStatsToDB");
                }
            } catch (Exception e) {
                if (z) {
                    Slog.e("ActivityManagerPerformance", "failed writeAppLaunchTimeCountStatsToDB, " + e.getMessage());
                }
            }
        } finally {
            mAppLaunchDB.close();
        }
    }

    static {
        boolean z = true;
        if (!(!"user".equals(Build.TYPE)) && !"true".equals(SystemProperties.get("sys.config.amp_debug", "false"))) {
            z = false;
        }
        DEBUG = z;
        DEBUG_TRACE = "true".equals(SystemProperties.get("sys.config.amp_debug_trace", "false"));
        AMP_ENABLE = "true".equals(SystemProperties.get("sys.config.amp_enable", "true"));
        AMP_PERF_ENABLE = "true".equals(SystemProperties.get("sys.config.amp_perf_enable", "true"));
        AMP_RELAUNCH_RESUME_ON = "true".equals(SystemProperties.get("sys.config.amp_relaunch_resume", "true"));
        TIMEOUT_ACT_RESUME = Integer.parseInt(SystemProperties.get("sys.config.amp_to_act_resume", "1000"));
        TIMEOUT_ACT_START = Integer.parseInt(SystemProperties.get("sys.config.amp_to_act_start", "2000"));
        TIMEOUT_APP_SWITCH = Integer.parseInt(SystemProperties.get("sys.config.amp_to_app_switch", "3000"));
        TIMEOUT_PREV_RESUME = Integer.parseInt(SystemProperties.get("sys.config.amp_to_prev_resume", "3000"));
        isChinaModel = ActivationMonitor.CHINA_COUNTRY_CODE.equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY));
        mDynamicHiddenApp = null;
        pkgDecoder = Base64.getDecoder();
        sLowPerformancePkgList = new String[]{x("Y29tLnNlYy5hbmRyb2lkLmFwcC5zYnJvd3Nlcg=="), x("Y29tLmFuZHJvaWQuY2hyb21l")};
        mAppLaunchPackagesTimeOutLM = new String[]{x("Y29tLnNlYy5hbmRyb2lkLmFwcC5zYnJvd3Nlcg=="), x("Y29tLnNhbXN1bmcuYW5kcm9pZC5ob21laHVi"), x("Y29tLm5obi5hbmRyb2lkLnNlYXJjaA=="), x("Y29tLmdvb2dsZS5hbmRyb2lkLmdt"), x("Y29tLnNhbXN1bmcuYW5kcm9pZC5lbWFpbC51aQ=="), x("Y29tLmFuZHJvaWQudmVuZGluZw=="), x("Y29tLnNhbXN1bmcuZXZlcmdsYWRlcy52aWRlbw=="), x("Y29tLnNhbXN1bmcuYW5kcm9pZC52aWRlbw=="), x("Y29tLnNlYy5hbmRyb2lkLmdhbGxlcnkzZA=="), x("Y29tLmdvb2dsZS5hbmRyb2lkLmFwcHMubWFwcw=="), x("Y29tLmJhaWR1LmFwcHNlYXJjaA=="), x("Y29tLnNpbmEud2VpYm8="), x("Y29tLmJhaWR1LkJhaWR1TWFw"), x("Y29tLnR3aXR0ZXIuYW5kcm9pZA==")};
        mAppLaunchPackagesTimeOutM = new String[]{x("Y29tLnNlYy5hbmRyb2lkLmFwcC5jYW1lcmE=")};
        x("Y29tLnNlYy5hbmRyb2lkLmFwcC5jYW1lcmE=");
        x("Y29tLnNlYy5hbmRyb2lkLmFwcC5zYnJvd3Nlcg==");
        gBlockedPkgs = new String[]{x("Y29tLnNlYy5hbmRyb2lkLmFwcC50aW55bQ==")};
        isPerfReserveSupport = false;
        mContext = null;
        mAppLaunchDBHelper = null;
        mAppLaunchDB = null;
        sNextTimeToSendSlugBigdata = Duration.ofMinutes(30L).toMillis() + System.currentTimeMillis();
        mFoldListenedTime = SystemClock.uptimeMillis();
        mLockinit = new Object();
        mLockAppSwitch = new Object();
        prevSwitchActivity = null;
        rCurBoostAppSwitch = null;
        WALLPAPER_PROFILE = new String[]{"MidgroundProcess"};
        gSystemuiPkgs = new String[]{x("Y29tLmFuZHJvaWQuc3lzdGVtdWk="), x("Y29tLnNlYy5hbmRyb2lkLmRleHN5c3RlbXVp")};
    }

    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.server.wm.ActivityManagerPerformance$1] */
    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.server.wm.ActivityManagerPerformance$2] */
    public ActivityManagerPerformance(Context context, ActivityTaskManagerService activityTaskManagerService) {
        long j;
        String str;
        long j2;
        this.mIsAppLaunchDBInit = false;
        StrictMode.ThreadPolicy threadPolicy = null;
        this.mDeviceStateManager = null;
        this.mIsTaskBoostExist = false;
        this.mTaskBoostManager = null;
        this.mService = activityTaskManagerService;
        mContext = context;
        sLowPerformancePkgSet = new HashSet();
        int i = 0;
        while (true) {
            String[] strArr = sLowPerformancePkgList;
            if (i >= strArr.length) {
                break;
            }
            sLowPerformancePkgSet.add(strArr[i]);
            i++;
        }
        this.mHandler = new MainHandler(Watchdog$$ExternalSyntheticOutline0.m(-2, "AmpHandlerThread", false).getLooper());
        isPerfReserveSupport = BatteryService$$ExternalSyntheticOutline0.m45m("/proc/perf_reserve");
        IntentFilter m = VcnManagementService$$ExternalSyntheticOutline0.m("android.intent.action.SCREEN_ON", "android.intent.action.SCREEN_OFF", "com.sec.android.sdhms.action.INIT_COMPLETED", "com.sec.android.intent.action.BIGDATA_EVERY_DAY_UPDATE", "com.sec.android.intent.action.BIGDATA_EVERY_WEEK_CLEANUP");
        m.addAction("android.intent.action.ACTION_SHUTDOWN");
        m.setPriority(999);
        mContext.registerReceiver(this.mIntentReceiver, m, 2);
        SemDvfsManager createInstance = SemDvfsManager.createInstance(mContext, "TASK_BOOST");
        this.mTaskBoostManager = createInstance;
        if (createInstance != null) {
            this.mIsTaskBoostExist = createInstance.checkSysfsIdExist(4204048);
        }
        boolean z = DEBUG;
        if (z) {
            threadPolicy = StrictMode.getThreadPolicy();
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(threadPolicy).permitDiskReads().permitDiskWrites().build());
        }
        StrictMode.ThreadPolicy threadPolicy2 = threadPolicy;
        AppLaunchTimeDBHelper appLaunchTimeDBHelper = new AppLaunchTimeDBHelper(mContext);
        mAppLaunchDBHelper = appLaunchTimeDBHelper;
        try {
            mAppLaunchDB = appLaunchTimeDBHelper.getWritableDatabase();
            Slog.d("ActivityManagerPerformance", "getWritableDatabase for appDB");
        } catch (SQLiteDatabaseCorruptException unused) {
            Slog.e("ActivityManagerPerformance", "SQLiteDatabaseCorruptException for appDB");
            mAppLaunchDB = mAppLaunchDBHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            Slog.e("ActivityManagerPerformance", "SQLiteException for appDB " + e.getMessage());
            if (e.getMessage().contains("malformed database")) {
                Slog.e("ActivityManagerPerformance", "delete app db and open");
                File databasePath = mContext.getDatabasePath("/data/misc/AppLaunchTime/app_launch_time.db");
                if (databasePath != null && databasePath.exists()) {
                    SQLiteDatabase.deleteDatabase(databasePath);
                }
                mAppLaunchDB = mAppLaunchDBHelper.getWritableDatabase();
            }
        } catch (Exception e2) {
            NandswapManager$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception for appDB "), "ActivityManagerPerformance");
        }
        SQLiteDatabase sQLiteDatabase = mAppLaunchDB;
        if (sQLiteDatabase != null) {
            try {
                try {
                    AppLaunchTimeDBHelper.m1051$$Nest$mfetch(mAppLaunchDBHelper, sQLiteDatabase);
                    if (z) {
                        Slog.i("ActivityManagerPerformance", "AppLaunchDB fetch");
                    }
                } finally {
                }
            } catch (Exception unused2) {
                AppLaunchTimeDBHelper appLaunchTimeDBHelper2 = mAppLaunchDBHelper;
                SQLiteDatabase sQLiteDatabase2 = mAppLaunchDB;
                appLaunchTimeDBHelper2.getClass();
                try {
                    sQLiteDatabase2.execSQL("DROP TABLE AppLaunchTime;");
                } catch (SQLiteException e3) {
                    Slog.e("ActivityManagerPerformance:AppLaunchTime", "Failed to drop table. " + e3.getMessage());
                }
                mAppLaunchDBHelper.onCreate(mAppLaunchDB);
                Slog.e("ActivityManagerPerformance", "failed fetch, drop & onCreate new");
            }
            mAppLaunchDB.close();
            AppLaunchTimeDBHelper appLaunchTimeDBHelper3 = mAppLaunchDBHelper;
            long j3 = appLaunchTimeDBHelper3.mStartTimeGathered;
            if (j3 <= 0) {
                SQLiteDatabase writableDatabase = appLaunchTimeDBHelper3.getWritableDatabase();
                mAppLaunchDB = writableDatabase;
                if (writableDatabase != null) {
                    try {
                        try {
                            mAppLaunchDBHelper.mStartTimeGathered = System.currentTimeMillis();
                            AppLaunchTimeDBHelper appLaunchTimeDBHelper4 = mAppLaunchDBHelper;
                            SQLiteDatabase sQLiteDatabase3 = mAppLaunchDB;
                            Objects.requireNonNull(appLaunchTimeDBHelper4);
                            AppLaunchTimeDBHelper.m1052$$Nest$mupdateDataInTable(appLaunchTimeDBHelper4, sQLiteDatabase3, "startTimeOfGatheredData");
                        } finally {
                        }
                    } catch (Exception e4) {
                        if (z) {
                            Slog.e("ActivityManagerPerformance", "failed checkStartTimeGathered, " + e4.getMessage());
                        }
                    }
                    mAppLaunchDB.close();
                }
                if (z) {
                    BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("setStartTimeGathered : "), mAppLaunchDBHelper.mStartTimeGathered, "ActivityManagerPerformance");
                }
            } else if (z) {
                Slog.d("ActivityManagerPerformance", "DB mStartTimeGathered : " + j3);
            }
            long j4 = mAppLaunchDBHelper.mDevicePowerOnCount;
            if (j4 > 0) {
                j = j4 + 1;
                if (z) {
                    Slog.d("ActivityManagerPerformance", "DB mDevicePowerOnCount : " + j);
                }
            } else {
                j = 1;
            }
            SQLiteDatabase writableDatabase2 = mAppLaunchDBHelper.getWritableDatabase();
            mAppLaunchDB = writableDatabase2;
            try {
                if (writableDatabase2 != null) {
                    try {
                        AppLaunchTimeDBHelper appLaunchTimeDBHelper5 = mAppLaunchDBHelper;
                        appLaunchTimeDBHelper5.mDevicePowerOnCount = j;
                        AppLaunchTimeDBHelper.m1052$$Nest$mupdateDataInTable(appLaunchTimeDBHelper5, writableDatabase2, "devicePowerOnCount");
                    } catch (Exception e5) {
                        if (z) {
                            Slog.e("ActivityManagerPerformance", "failed checkDevicePowerOnCount, " + e5.getMessage());
                        }
                    }
                    mAppLaunchDB.close();
                }
                if (z) {
                    BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("setDevicePowerOnCount : "), mAppLaunchDBHelper.mDevicePowerOnCount, "ActivityManagerPerformance");
                }
                String str2 = mAppLaunchDBHelper.mBuildVersion;
                String str3 = SystemProperties.get("ro.build.version.incremental", KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
                if (str2 == null || !str2.equals(str3)) {
                    deleteDBTableAndClear();
                    SQLiteDatabase writableDatabase3 = mAppLaunchDBHelper.getWritableDatabase();
                    mAppLaunchDB = writableDatabase3;
                    if (writableDatabase3 != null) {
                        try {
                            try {
                                AppLaunchTimeDBHelper appLaunchTimeDBHelper6 = mAppLaunchDBHelper;
                                appLaunchTimeDBHelper6.mBuildVersion = str3;
                                AppLaunchTimeDBHelper.m1052$$Nest$mupdateDataInTable(appLaunchTimeDBHelper6, writableDatabase3, "buildVersion");
                                mAppLaunchDBHelper.mStartTimeGathered = System.currentTimeMillis();
                                AppLaunchTimeDBHelper appLaunchTimeDBHelper7 = mAppLaunchDBHelper;
                                SQLiteDatabase sQLiteDatabase4 = mAppLaunchDB;
                                Objects.requireNonNull(appLaunchTimeDBHelper7);
                                AppLaunchTimeDBHelper.m1052$$Nest$mupdateDataInTable(appLaunchTimeDBHelper7, sQLiteDatabase4, "startTimeOfGatheredData");
                                AppLaunchTimeDBHelper appLaunchTimeDBHelper8 = mAppLaunchDBHelper;
                                appLaunchTimeDBHelper8.mDevicePowerOnCount = 1L;
                                AppLaunchTimeDBHelper.m1052$$Nest$mupdateDataInTable(appLaunchTimeDBHelper8, mAppLaunchDB, "devicePowerOnCount");
                            } finally {
                            }
                        } catch (Exception e6) {
                            if (z) {
                                Slog.e("ActivityManagerPerformance", "failed checkBuildVersionChanged, " + e6.getMessage());
                            }
                        }
                    }
                    if (z) {
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m("detect New build version : ", str3, "ActivityManagerPerformance");
                    }
                } else if (z) {
                    GmsAlarmManager$$ExternalSyntheticOutline0.m("build version check : ", str2, ",", str3, "ActivityManagerPerformance");
                }
                AlarmManager alarmManager = (AlarmManager) mContext.getSystemService("alarm");
                Calendar calendar = Calendar.getInstance();
                long timeInMillis = calendar.getTimeInMillis();
                PendingIntent broadcast = PendingIntent.getBroadcast(mContext, 0, new Intent("com.sec.android.intent.action.BIGDATA_EVERY_DAY_UPDATE"), 67108864);
                calendar.set(11, 3);
                calendar.set(12, 30);
                calendar.set(13, 0);
                calendar.set(14, 0);
                alarmManager.setRepeating(1, calendar.getTimeInMillis() + BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, broadcast);
                int i2 = (int) mAppLaunchDBHelper.mDBDayOfWeek;
                if (i2 < 0) {
                    Random random = new Random();
                    str = "com.sec.android.intent.action.BIGDATA_EVERY_WEEK_CLEANUP";
                    random.setSeed(System.currentTimeMillis());
                    int nextInt = random.nextInt(7) + 1;
                    SQLiteDatabase writableDatabase4 = mAppLaunchDBHelper.getWritableDatabase();
                    mAppLaunchDB = writableDatabase4;
                    if (writableDatabase4 != null) {
                        try {
                            try {
                                AppLaunchTimeDBHelper appLaunchTimeDBHelper9 = mAppLaunchDBHelper;
                                appLaunchTimeDBHelper9.mDBDayOfWeek = nextInt;
                                AppLaunchTimeDBHelper.m1052$$Nest$mupdateDataInTable(appLaunchTimeDBHelper9, writableDatabase4, "DayOfWeek");
                            } finally {
                            }
                        } catch (Exception e7) {
                            if (z) {
                                Slog.e("ActivityManagerPerformance", "failed registerAlarm - dayOfWeek, " + e7.getMessage());
                            }
                        }
                    }
                    i2 = nextInt;
                } else {
                    str = "com.sec.android.intent.action.BIGDATA_EVERY_WEEK_CLEANUP";
                }
                if (z) {
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(i2, "TTLS Day of Week : ", "ActivityManagerPerformance");
                }
                PendingIntent broadcast2 = PendingIntent.getBroadcast(mContext, 0, new Intent(str), 67108864);
                calendar.set(7, i2);
                calendar.set(11, 0);
                calendar.set(12, 0);
                long timeInMillis2 = calendar.getTimeInMillis();
                if (timeInMillis2 > timeInMillis) {
                    j2 = timeInMillis2 - Duration.ofDays(7L).toMillis();
                } else {
                    timeInMillis2 = calendar.getTimeInMillis() + 604800000;
                    j2 = timeInMillis2;
                }
                alarmManager.setRepeating(1, timeInMillis2, 604800000L, broadcast2);
                if (z) {
                    Slog.d("ActivityManagerPerformance", "Bigdata will send at : " + timeInMillis2);
                }
                long millis = (j2 - mAppLaunchDBHelper.mLastModifiedTime) / Duration.ofDays(1L).toMillis();
                if (millis > 7) {
                    deleteDBTableAndClear();
                    if (z) {
                        Slog.d("ActivityManagerPerformance", "deleted data older than last week.");
                    }
                } else if (millis > 0) {
                    onReceive(mContext, new Intent(str));
                    if (z) {
                        Slog.d("ActivityManagerPerformance", "did cleanup the data old the last week.");
                    }
                } else if (z) {
                    Slog.d("ActivityManagerPerformance", "all data was updated this week.");
                }
                this.mIsAppLaunchDBInit = true;
            } finally {
            }
        }
        if (z) {
            StrictMode.setThreadPolicy(threadPolicy2);
        }
        if (this.mDeviceStateManager == null) {
            this.mDeviceStateManager = (DeviceStateManager) mContext.getSystemService(DeviceStateManager.class);
        }
        DeviceStateManager deviceStateManager = this.mDeviceStateManager;
        if (deviceStateManager != null) {
            deviceStateManager.registerCallback(new SystemServerInitThreadPool$$ExternalSyntheticLambda0(), this.mDeviceStateCallback);
        }
    }

    public static ActivityManagerPerformance getBooster(Context context, ActivityTaskManagerService activityTaskManagerService) {
        ActivityManagerPerformance activityManagerPerformance;
        if (activityTaskManagerService == null || context == null) {
            return null;
        }
        synchronized (mLockinit) {
            try {
                if (AMP_ENABLE && booster == null) {
                    booster = new ActivityManagerPerformance(context, activityTaskManagerService);
                }
                activityManagerPerformance = AMP_ENABLE ? booster : null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return activityManagerPerformance;
    }

    public static final boolean isBlockedApp(String str) {
        if (str == null) {
            return false;
        }
        for (String str2 : gBlockedPkgs) {
            if (str.contains(str2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSpeg(String str, String str2) {
        if (!CoreRune.SYSFW_APP_SPEG || !"com.samsung.speg".equals(str2)) {
            return false;
        }
        BinaryTransparencyService$$ExternalSyntheticOutline0.m("Skipping boost of ", str, "SPEG");
        return true;
    }

    public static synchronized void notifyCurTopAct(ActivityRecord activityRecord) {
        ActivityRecord activityRecord2;
        HashSet hashSet;
        ActivityManagerPerformance activityManagerPerformance;
        ActivityRecord activityRecord3;
        synchronized (ActivityManagerPerformance.class) {
            try {
                if (AMP_ENABLE && curTopAct != activityRecord) {
                    int i = activityRecord.isActivityTypeHome() ? 2 : activityRecord.isActivityTypeRecents() ? 3 : 4;
                    boolean z = DEBUG;
                    if (z) {
                        String str = "notifyCurTopAct() activity changed\n[Activity] prev: " + curTopAct + ", cur: " + activityRecord;
                        boolean z2 = DEBUG_TRACE;
                        if (z2) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(str);
                            sb.append("\n[Process] prev: ");
                            ActivityRecord activityRecord4 = curTopAct;
                            sb.append(activityRecord4 != null ? activityRecord4.processName : null);
                            sb.append(", cur: ");
                            sb.append(activityRecord.processName);
                            String sb2 = sb.toString();
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(sb2);
                            sb3.append("\n[Package] prev: ");
                            ActivityRecord activityRecord5 = curTopAct;
                            sb3.append(activityRecord5 != null ? activityRecord5.packageName : null);
                            sb3.append(", cur: ");
                            sb3.append(activityRecord.packageName);
                            str = sb3.toString() + "\n[TOP_STATE] prev: " + topStateToString(curTopState) + ", cur: " + topStateToString(i);
                        }
                        Slog.d("ActivityManagerPerformance", str);
                        if (z2) {
                            new Exception().printStackTrace();
                        }
                    }
                    if (CoreRune.ALLIED_PROC_PROTECTION_LMKD && (activityRecord3 = curTopAct) != null) {
                        if (activityRecord.mLaunchSourceType != 4) {
                            ArrayList arrayList = DynamicHiddenApp.alliedProtectedProcList;
                            synchronized (arrayList) {
                                arrayList.clear();
                            }
                        } else if (!activityRecord.packageName.equals(activityRecord3.packageName)) {
                            ArrayList arrayList2 = DynamicHiddenApp.alliedProtectedProcList;
                            synchronized (arrayList2) {
                                try {
                                    ActivityRecord activityRecord6 = activityRecord.resultTo;
                                    if (activityRecord6 != null) {
                                        if (!arrayList2.contains(activityRecord6.packageName)) {
                                            arrayList2.add(activityRecord.resultTo.packageName);
                                        }
                                        if (z) {
                                            Slog.d("ActivityManagerPerformance", activityRecord.resultTo.packageName + " allied process eligble for LMKD kill protect");
                                        }
                                    }
                                    if (arrayList2.contains(activityRecord.packageName)) {
                                        arrayList2.remove(activityRecord.packageName);
                                    }
                                } finally {
                                }
                            }
                        }
                    }
                    curTopAct = activityRecord;
                    prevSwitchActivity = null;
                    int i2 = curTopState;
                    if (i2 != i) {
                        curTopState = i;
                        if (AMP_PERF_ENABLE && (activityManagerPerformance = booster) != null && activityManagerPerformance.mIsScreenOn) {
                            if (i == 2) {
                                if (z) {
                                    Slog.d("ActivityManagerPerformance", "notifyCurTopAct() call setBoosterHome()");
                                }
                                booster.setBoosterHome(activityRecord, true, false);
                                return;
                            }
                            if (i2 == 3 && (i == 4 || i == 1)) {
                                try {
                                    ActivityRecord activityRecord7 = rCurBoostAppSwitch;
                                    if (activityRecord7 != null) {
                                        if (activityRecord7 != activityRecord && !activityRecord.processName.equals(activityRecord7.processName)) {
                                        }
                                    }
                                    ActivityManagerPerformance activityManagerPerformance2 = booster;
                                    if (activityManagerPerformance2.isMultiWindowResume && curTopState == 4) {
                                        activityManagerPerformance2.isMultiWindowResume = false;
                                        if (z) {
                                            Slog.d("ActivityManagerPerformance", "notifyCurTopAct() skipped. isMultiWindowResume: true");
                                        }
                                        return;
                                    } else {
                                        if (z) {
                                            Slog.d("ActivityManagerPerformance", "notifyCurTopAct() call setBoosterAppSwitch()");
                                        }
                                        booster.setBoosterAppSwitch(true, activityRecord);
                                        return;
                                    }
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                    booster.isMultiWindowResume = false;
                                    return;
                                }
                            }
                        }
                    }
                    ActivityManagerPerformance activityManagerPerformance3 = booster;
                    if (activityManagerPerformance3 != null && (activityRecord2 = curTopAct) != null && (hashSet = sLowPerformancePkgSet) != null) {
                        activityManagerPerformance3.setLowPower(hashSet.contains(activityRecord2.packageName));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static String topStateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? VibrationParam$1$$ExternalSyntheticOutline0.m(i, "NO_STATE_NUM_OF_") : "TOP_STATE_APP" : "TOP_STATE_RECENTS_APP" : "TOP_STATE_HOME" : "TOP_STATE_IS_CREATING" : "TOP_STATE_INIT";
    }

    public static String x(String str) {
        Base64.Decoder decoder = pkgDecoder;
        return decoder == null ? "" : new String(decoder.decode(str));
    }

    public final void checkBoostDisabledByFold() {
        if (CoreRune.SYSPERF_BOOST_DISABLE_WHEN_FOLDED) {
            this.mService.mWindowManager.getClass();
        }
    }

    public final void deleteDBTableAndClear() {
        boolean z = DEBUG;
        SQLiteDatabase writableDatabase = mAppLaunchDBHelper.getWritableDatabase();
        mAppLaunchDB = writableDatabase;
        try {
            if (writableDatabase == null) {
                Slog.e("ActivityManagerPerformance", "failed DB writable open to deleteDBTableAndClear.");
                return;
            }
            try {
                AppLaunchTimeDBHelper appLaunchTimeDBHelper = mAppLaunchDBHelper;
                Objects.requireNonNull(appLaunchTimeDBHelper);
                AppLaunchTimeDBHelper.m1050$$Nest$mdeleteDBData(appLaunchTimeDBHelper, writableDatabase);
                AppLaunchTimeDBHelper appLaunchTimeDBHelper2 = mAppLaunchDBHelper;
                SQLiteDatabase sQLiteDatabase = mAppLaunchDB;
                Objects.requireNonNull(appLaunchTimeDBHelper2);
                AppLaunchTimeDBHelper.m1052$$Nest$mupdateDataInTable(appLaunchTimeDBHelper2, sQLiteDatabase, "lastModifiedTime");
                ((HashMap) this.mAppLaunchTimeCountStats).clear();
                if (z) {
                    Slog.d("ActivityManagerPerformance", "deleteDBTableAndClear");
                }
            } catch (Exception e) {
                if (z) {
                    Slog.e("ActivityManagerPerformance", "failed deleteDBTableAndClear, " + e.getMessage());
                }
            }
        } finally {
            mAppLaunchDB.close();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0089 A[Catch: Exception -> 0x00ad, TryCatch #1 {Exception -> 0x00ad, blocks: (B:23:0x007d, B:25:0x0089, B:27:0x0099, B:29:0x00a5, B:30:0x0303, B:32:0x030b, B:37:0x0319, B:39:0x031d, B:41:0x0329, B:45:0x0334, B:46:0x0340, B:48:0x037c, B:49:0x0399, B:51:0x03a7, B:52:0x03c4, B:55:0x03dc, B:57:0x03e2, B:59:0x03e6, B:64:0x03b0, B:66:0x03bc, B:67:0x0385, B:69:0x0391, B:43:0x0339, B:34:0x0314, B:74:0x0317, B:75:0x00b0, B:77:0x016d, B:78:0x0233), top: B:22:0x007d }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x030b A[Catch: Exception -> 0x00ad, TryCatch #1 {Exception -> 0x00ad, blocks: (B:23:0x007d, B:25:0x0089, B:27:0x0099, B:29:0x00a5, B:30:0x0303, B:32:0x030b, B:37:0x0319, B:39:0x031d, B:41:0x0329, B:45:0x0334, B:46:0x0340, B:48:0x037c, B:49:0x0399, B:51:0x03a7, B:52:0x03c4, B:55:0x03dc, B:57:0x03e2, B:59:0x03e6, B:64:0x03b0, B:66:0x03bc, B:67:0x0385, B:69:0x0391, B:43:0x0339, B:34:0x0314, B:74:0x0317, B:75:0x00b0, B:77:0x016d, B:78:0x0233), top: B:22:0x007d }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x031d A[Catch: Exception -> 0x00ad, TryCatch #1 {Exception -> 0x00ad, blocks: (B:23:0x007d, B:25:0x0089, B:27:0x0099, B:29:0x00a5, B:30:0x0303, B:32:0x030b, B:37:0x0319, B:39:0x031d, B:41:0x0329, B:45:0x0334, B:46:0x0340, B:48:0x037c, B:49:0x0399, B:51:0x03a7, B:52:0x03c4, B:55:0x03dc, B:57:0x03e2, B:59:0x03e6, B:64:0x03b0, B:66:0x03bc, B:67:0x0385, B:69:0x0391, B:43:0x0339, B:34:0x0314, B:74:0x0317, B:75:0x00b0, B:77:0x016d, B:78:0x0233), top: B:22:0x007d }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x03dc A[Catch: Exception -> 0x00ad, TryCatch #1 {Exception -> 0x00ad, blocks: (B:23:0x007d, B:25:0x0089, B:27:0x0099, B:29:0x00a5, B:30:0x0303, B:32:0x030b, B:37:0x0319, B:39:0x031d, B:41:0x0329, B:45:0x0334, B:46:0x0340, B:48:0x037c, B:49:0x0399, B:51:0x03a7, B:52:0x03c4, B:55:0x03dc, B:57:0x03e2, B:59:0x03e6, B:64:0x03b0, B:66:0x03bc, B:67:0x0385, B:69:0x0391, B:43:0x0339, B:34:0x0314, B:74:0x0317, B:75:0x00b0, B:77:0x016d, B:78:0x0233), top: B:22:0x007d }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x03e6 A[Catch: Exception -> 0x00ad, TRY_LEAVE, TryCatch #1 {Exception -> 0x00ad, blocks: (B:23:0x007d, B:25:0x0089, B:27:0x0099, B:29:0x00a5, B:30:0x0303, B:32:0x030b, B:37:0x0319, B:39:0x031d, B:41:0x0329, B:45:0x0334, B:46:0x0340, B:48:0x037c, B:49:0x0399, B:51:0x03a7, B:52:0x03c4, B:55:0x03dc, B:57:0x03e2, B:59:0x03e6, B:64:0x03b0, B:66:0x03bc, B:67:0x0385, B:69:0x0391, B:43:0x0339, B:34:0x0314, B:74:0x0317, B:75:0x00b0, B:77:0x016d, B:78:0x0233), top: B:22:0x007d }] */
    /* JADX WARN: Removed duplicated region for block: B:63:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x03d7  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0317 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0233 A[Catch: Exception -> 0x00ad, TryCatch #1 {Exception -> 0x00ad, blocks: (B:23:0x007d, B:25:0x0089, B:27:0x0099, B:29:0x00a5, B:30:0x0303, B:32:0x030b, B:37:0x0319, B:39:0x031d, B:41:0x0329, B:45:0x0334, B:46:0x0340, B:48:0x037c, B:49:0x0399, B:51:0x03a7, B:52:0x03c4, B:55:0x03dc, B:57:0x03e2, B:59:0x03e6, B:64:0x03b0, B:66:0x03bc, B:67:0x0385, B:69:0x0391, B:43:0x0339, B:34:0x0314, B:74:0x0317, B:75:0x00b0, B:77:0x016d, B:78:0x0233), top: B:22:0x007d }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void gatherAppLaunchTime(java.lang.String r32, java.lang.String r33, long r34, int r36, java.lang.String r37, long r38, java.lang.String r40) {
        /*
            Method dump skipped, instructions count: 1056
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityManagerPerformance.gatherAppLaunchTime(java.lang.String, java.lang.String, long, int, java.lang.String, long, java.lang.String):void");
    }

    public final String getCurBoostInfoStr() {
        String sb;
        StringBuilder sb2 = new StringBuilder("===== ActivityManagerPerformance, getCurBoostInfoStr info =====\nAMP_ENABLE: ");
        sb2.append(AMP_ENABLE);
        sb2.append(", AMP_PERF_ENABLE: ");
        boolean z = AMP_PERF_ENABLE;
        sb2.append(z);
        sb2.append(", mIsScreenOn: ");
        sb2.append(this.mIsScreenOn);
        String sb3 = sb2.toString();
        if (z) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(sb3, "\nTIMEOUT_ACT_RESUME: ");
            m.append(TIMEOUT_ACT_RESUME);
            m.append(", TIMEOUT_ACT_START: ");
            m.append(TIMEOUT_ACT_START);
            m.append(", TIMEOUT_APP_SWITCH: ");
            m.append(TIMEOUT_APP_SWITCH);
            sb = m.toString();
        } else {
            StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(sb3, "\nTIMEOUT_PREV_RESUME: ");
            m2.append(TIMEOUT_PREV_RESUME);
            sb = m2.toString();
        }
        StringBuilder m3 = Preconditions$$ExternalSyntheticOutline0.m(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(sb, "\n"), "\n[curTopState] ");
        m3.append(topStateToString(curTopState));
        StringBuilder m4 = Preconditions$$ExternalSyntheticOutline0.m(m3.toString(), "\n[rCurBoostAppSwitch] procName: ");
        ActivityRecord activityRecord = rCurBoostAppSwitch;
        m4.append(activityRecord != null ? activityRecord.processName : null);
        m4.append(" (");
        m4.append(rCurBoostAppSwitch);
        m4.append(")");
        StringBuilder m5 = Preconditions$$ExternalSyntheticOutline0.m(m4.toString(), "\n[rCurBoostActStart] procName: ");
        ActivityRecord activityRecord2 = this.rCurBoostActStart;
        m5.append(activityRecord2 != null ? activityRecord2.processName : null);
        m5.append(" (");
        m5.append(this.rCurBoostActStart);
        m5.append(")");
        StringBuilder m6 = Preconditions$$ExternalSyntheticOutline0.m(m5.toString(), "\n[rCurBoostActResume] procName: ");
        ActivityRecord activityRecord3 = this.rCurBoostActResume;
        m6.append(activityRecord3 != null ? activityRecord3.processName : null);
        m6.append(" (");
        m6.append(this.rCurBoostActResume);
        m6.append(")");
        StringBuilder m7 = Preconditions$$ExternalSyntheticOutline0.m(m6.toString(), "\n[rLastActTail] procName: ");
        ActivityRecord activityRecord4 = this.rLastActTail;
        m7.append(activityRecord4 != null ? activityRecord4.processName : null);
        m7.append(" (");
        m7.append(this.rLastActTail);
        m7.append(")");
        StringBuilder m8 = Preconditions$$ExternalSyntheticOutline0.m(m7.toString(), "\n[rLastActHome] procName: ");
        ActivityRecord activityRecord5 = this.rLastActHome;
        m8.append(activityRecord5 != null ? activityRecord5.processName : null);
        m8.append(" (");
        m8.append(this.rLastActHome);
        m8.append(")");
        StringBuilder m9 = Preconditions$$ExternalSyntheticOutline0.m(m8.toString(), "\n[rLastRelaunchResume] procName: ");
        ActivityRecord activityRecord6 = this.rLastRelaunchResume;
        m9.append(activityRecord6 != null ? activityRecord6.processName : null);
        m9.append(" (");
        m9.append(this.rLastRelaunchResume);
        m9.append(")");
        return ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m9.toString(), "\n===== ActivityManagerPerformance, getCurBoostInfoStr end  =====");
    }

    public final boolean isJustFoldedState() {
        if (!this.mIsFolded || SystemClock.uptimeMillis() - mFoldListenedTime >= 500) {
            return false;
        }
        if (!DEBUG) {
            return true;
        }
        Slog.d("ActivityManagerPerformance", "mIsFolded: true && Folded just now");
        return true;
    }

    public final void notifyTaskBoost(int i) {
        Integer valueOf = Integer.valueOf(i);
        MainHandler mainHandler = this.mHandler;
        mainHandler.sendMessage(mainHandler.obtainMessage(12, valueOf));
    }

    public final void onActivityRelaunchLocked(ActivityRecord activityRecord) {
        boolean z = DEBUG;
        if (z) {
            Slog.d("ActivityManagerPerformance", "onActivityRelaunchLocked() r: " + (activityRecord != null ? activityRecord.processName : null) + " (" + activityRecord + "), andResume: true");
            if (DEBUG_TRACE) {
                Slog.d("ActivityManagerPerformance", "onActivityRelaunchLocked() Trace\n" + getCurBoostInfoStr());
                new Exception().printStackTrace();
            }
        }
        if (AMP_RELAUNCH_RESUME_ON) {
            if (!this.mIsScreenOn) {
                if (z) {
                    Slog.d("ActivityManagerPerformance", "onActivityRelaunchLocked() skipped. mIsScreenOn: false");
                    return;
                }
                return;
            }
            if (isJustFoldedState()) {
                return;
            }
            if (z) {
                Slog.d("ActivityManagerPerformance", "setBoosterRelaunchResume() r: " + (activityRecord != null ? activityRecord.processName : null) + " (" + activityRecord + ")");
                if (DEBUG_TRACE) {
                    Slog.d("ActivityManagerPerformance", "setBoosterRelaunchResume() Trace\n" + getCurBoostInfoStr());
                    new Exception().printStackTrace();
                }
            }
            if (this.mBoosterRelaunchResume == null) {
                SemDvfsManager createInstance = SemDvfsManager.createInstance(mContext, "AMS_RELAUNCH_RESUME");
                this.mBoosterRelaunchResume = createInstance;
                if (createInstance != null) {
                    createInstance.setHint(2);
                }
            }
            SemDvfsManager semDvfsManager = this.mBoosterRelaunchResume;
            if (semDvfsManager == null) {
                Slog.e("ActivityManagerPerformance", "setBoosterRelaunchResume() skipped. SemDvfsManager.createInstance() failed");
                return;
            }
            try {
                synchronized (this.mLockRelaunchResume) {
                    semDvfsManager.acquire();
                    this.rLastRelaunchResume = activityRecord;
                }
                Slog.d("ActivityManagerPerformance", "AMP_acquire() RELAUNCH_RESUME");
            } catch (Exception e) {
                Slog.e("ActivityManagerPerformance", "AMP_acquire() RELAUNCH_RESUME failed");
                if (DEBUG) {
                    Slog.e("ActivityManagerPerformance", "AMP_acquire() RELAUNCH_RESUME failed. e: " + e + "\n" + getCurBoostInfoStr());
                }
                e.printStackTrace();
            }
        }
    }

    public final void onActivityStartLocked(ActivityRecord activityRecord) {
        boolean z = DEBUG;
        if (z) {
            Slog.d("ActivityManagerPerformance", "onActivityStartLocked() r: " + activityRecord.processName + " (" + activityRecord + ")");
            if (DEBUG_TRACE) {
                Slog.d("ActivityManagerPerformance", "onActivityStartLocked() Trace\n" + getCurBoostInfoStr());
                new Exception().printStackTrace();
            }
        }
        if (!AMP_ENABLE || isSpeg(activityRecord.packageName, activityRecord.launchedFromPackage) || isBlockedApp(activityRecord.packageName)) {
            return;
        }
        if (this.isMultiWindowResume) {
            this.isMultiWindowResume = false;
        }
        if (!this.mIsScreenOn) {
            if (z) {
                Slog.d("ActivityManagerPerformance", "onActivityStartLocked() skipped. mIsScreenOn: false");
                return;
            }
            return;
        }
        if (isJustFoldedState()) {
            return;
        }
        if (this.needSkipResume) {
            this.needSkipResume = false;
        }
        if (!AMP_PERF_ENABLE) {
            setBoosterActStart(true, activityRecord);
            return;
        }
        if (activityRecord.isActivityTypeHome()) {
            setBoosterHome(activityRecord, false, true);
            return;
        }
        if (activityRecord.isActivityTypeRecents()) {
            setBoosterActStart(true, activityRecord);
            return;
        }
        ActivityRecord activityRecord2 = curTopAct;
        if (activityRecord2 == null || !activityRecord2.processName.equals(activityRecord.processName)) {
            try {
                ActivityRecord activityRecord3 = rCurBoostAppSwitch;
                if (activityRecord3 != activityRecord && (activityRecord3 == null || !activityRecord.processName.equals(activityRecord3.processName))) {
                    MainHandler mainHandler = this.mHandler;
                    mainHandler.sendMessage(mainHandler.obtainMessage(8, activityRecord));
                    return;
                } else {
                    if (z) {
                        Slog.d("ActivityManagerPerformance", "onActivityStartLocked() skipped. already boosted " + activityRecord.processName);
                        return;
                    }
                    return;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            ActivityRecord activityRecord4 = rCurBoostAppSwitch;
            ActivityRecord activityRecord5 = this.rCurBoostActStart;
            if (activityRecord4 != activityRecord) {
                if (activityRecord5 != activityRecord) {
                    if (activityRecord4 != null) {
                        if (!activityRecord.processName.equals(activityRecord4.processName)) {
                        }
                    }
                    if (activityRecord5 == null || !activityRecord.processName.equals(activityRecord5.processName)) {
                        setBoosterActStart(true, activityRecord);
                        return;
                    }
                }
            }
            if (z) {
                Slog.d("ActivityManagerPerformance", "onActivityStartLocked() skipped. already boosted " + activityRecord.processName);
            }
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        }
    }

    public final void onActivityVisibleLocked(ActivityRecord activityRecord) {
        if (DEBUG) {
            if (activityRecord == null) {
                Slog.e("ActivityManagerPerformance", "onActivityVisibleLocked() ActivityRecord is Null");
                return;
            }
            if (activityRecord.processName == null) {
                Slog.e("ActivityManagerPerformance", "onActivityVisibleLocked() ActivityRecord's ProcessName is Null");
            } else {
                Slog.d("ActivityManagerPerformance", "onActivityVisibleLocked() r: " + activityRecord.processName + " (" + activityRecord + ")");
            }
            if (DEBUG_TRACE) {
                Slog.d("ActivityManagerPerformance", "onActivityVisibleLocked() Trace\n" + getCurBoostInfoStr());
                new Exception().printStackTrace();
            }
        }
        if (AMP_ENABLE && !isJustFoldedState()) {
            if (this.isMultiWindowResume) {
                this.isMultiWindowResume = false;
            }
            if (this.rCurBoostActResume != null) {
                setBoosterActResume(false, activityRecord);
            }
            if (this.rCurBoostActStart != null) {
                setBoosterActStart(false, activityRecord);
            }
            if (rCurBoostAppSwitch == null || activityRecord == null || activityRecord.isActivityTypeHome() || activityRecord.isActivityTypeRecents()) {
                return;
            }
            setBoosterAppSwitch(false, activityRecord);
        }
    }

    public final void onAppLaunch(boolean z, ActivityRecord activityRecord) {
        if (activityRecord == null || isSpeg(activityRecord.packageName, activityRecord.launchedFromPackage) || isBlockedApp(activityRecord.packageName) || isJustFoldedState()) {
            return;
        }
        synchronized (this.mLockAppLaunch) {
            try {
                if (this.mBoosterAppLaunch == null) {
                    this.mBoosterAppLaunch = SemDvfsManager.createInstance(mContext, "AMS_APP_LAUNCH");
                }
                if (this.mBoosterAppLaunch != null) {
                    String str = activityRecord.packageName;
                    int i = -999;
                    if (str != null) {
                        String[] strArr = mAppLaunchPackagesTimeOutM;
                        int length = strArr.length;
                        int i2 = 0;
                        int i3 = 0;
                        while (true) {
                            if (i3 >= length) {
                                String[] strArr2 = mAppLaunchPackagesTimeOutLM;
                                int length2 = strArr2.length;
                                while (true) {
                                    if (i2 < length2) {
                                        if (str.contains(strArr2[i2])) {
                                            i = z ? 35 : 28;
                                        } else {
                                            i2++;
                                        }
                                    } else if (!z) {
                                        i = 32;
                                    }
                                }
                            } else {
                                if (str.contains(strArr[i3])) {
                                    i = 27;
                                    break;
                                }
                                i3++;
                            }
                        }
                    }
                    this.mBoosterAppLaunch.setHint(i);
                    this.mBoosterAppLaunch.acquire();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final synchronized void sendHqmBigData(String str, String str2, String str3) {
        try {
            if (this.mHqmManager == null) {
                this.mHqmManager = (SemHqmManager) mContext.getSystemService("HqmManagerService");
            }
            SemHqmManager semHqmManager = this.mHqmManager;
            if (semHqmManager == null) {
                Slog.e("ActivityManagerPerformance", "HQM services is not working");
                return;
            }
            if (!semHqmManager.sendHWParamToHQM(0, "Sluggish", str, "ph", str2, "sec", "", str3, "")) {
                Slog.e("ActivityManagerPerformance", "failed to send to server");
            } else if (DEBUG) {
                Slog.d("ActivityManagerPerformance", "Success to report '" + str + "' : " + str3);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setBoosterActResume(boolean r8, com.android.server.wm.ActivityRecord r9) {
        /*
            Method dump skipped, instructions count: 374
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityManagerPerformance.setBoosterActResume(boolean, com.android.server.wm.ActivityRecord):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setBoosterActStart(boolean r7, com.android.server.wm.ActivityRecord r8) {
        /*
            Method dump skipped, instructions count: 518
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityManagerPerformance.setBoosterActStart(boolean, com.android.server.wm.ActivityRecord):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0064  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setBoosterAppSwitch(boolean r10, com.android.server.wm.ActivityRecord r11) {
        /*
            Method dump skipped, instructions count: 550
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityManagerPerformance.setBoosterAppSwitch(boolean, com.android.server.wm.ActivityRecord):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:113:0x0161, code lost:
    
        android.util.Slog.d("ActivityManagerPerformance", "setBoosterHome() skipped. already boosted " + r11.processName);
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0174, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0121, code lost:
    
        if (r11.processName.equals(r2.processName) != false) goto L66;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setBoosterHome(com.android.server.wm.ActivityRecord r11, boolean r12, boolean r13) {
        /*
            Method dump skipped, instructions count: 518
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityManagerPerformance.setBoosterHome(com.android.server.wm.ActivityRecord, boolean, boolean):void");
    }

    public final void setBoosterTail(boolean z, ActivityRecord activityRecord) {
        boolean z2 = DEBUG;
        if (z2) {
            String str = activityRecord != null ? activityRecord.processName : null;
            StringBuilder sb = new StringBuilder("setBoosterTail() from ");
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, z ? "AppSwitch" : "ActStart", ", r: ", str, " (");
            sb.append(activityRecord);
            sb.append(")");
            Slog.d("ActivityManagerPerformance", sb.toString());
            if (DEBUG_TRACE) {
                Slog.d("ActivityManagerPerformance", "setBoosterTail() Trace\n" + getCurBoostInfoStr());
                new Exception().printStackTrace();
            }
        }
        checkBoostDisabledByFold();
        if (!this.mIsScreenOn) {
            if (z2) {
                Slog.d("ActivityManagerPerformance", "setBoosterTail() skipped. mIsScreenOn: false");
                return;
            }
            return;
        }
        if ((z && this.rCurBoostActStart != null) || (!z && rCurBoostAppSwitch != null)) {
            if (z2) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("setBoosterTail() skipped. "), !z ? "AppSwitch" : "ActStart", " is not finished", "ActivityManagerPerformance");
                return;
            }
            return;
        }
        if (this.mBoosterTail == null) {
            SemDvfsManager createInstance = SemDvfsManager.createInstance(mContext, "AMS_RESUME_TAIL");
            this.mBoosterTail = createInstance;
            if (createInstance != null) {
                createInstance.setHint(5);
            }
        }
        if (this.mBoosterLazy == null) {
            SemDvfsManager createInstance2 = SemDvfsManager.createInstance(mContext, "AMS_ACT_LAZY");
            this.mBoosterLazy = createInstance2;
            if (createInstance2 != null) {
                createInstance2.setHint(6);
            }
        }
        SemDvfsManager semDvfsManager = this.mBoosterTail;
        if (semDvfsManager == null) {
            Slog.e("ActivityManagerPerformance", "setBoosterTail() skipped. SemDvfsManager.createInstance() failed");
            return;
        }
        SemDvfsManager semDvfsManager2 = this.mBoosterLazy;
        if (semDvfsManager2 == null) {
            Slog.e("ActivityManagerPerformance", "setBoosterTail() skipped. SemDvfsManager.createInstance() failed");
            return;
        }
        try {
            synchronized (this.mLockTail) {
                semDvfsManager.acquire();
                semDvfsManager2.acquire();
                this.rLastActTail = activityRecord;
            }
            Slog.d("ActivityManagerPerformance", "AMP_acquire() TAIL");
        } catch (Exception e) {
            Slog.e("ActivityManagerPerformance", "AMP_acquire() TAIL failed");
            if (DEBUG) {
                Slog.e("ActivityManagerPerformance", "AMP_acquire() TAIL failed. e: " + e + "\n" + getCurBoostInfoStr());
            }
            e.printStackTrace();
        }
    }

    public final void setLowPower(boolean z) {
        synchronized (this.mLockActStart) {
            try {
                SemDvfsManager semDvfsManager = this.mBoosterActStart;
                if (semDvfsManager != null) {
                    if (z) {
                        semDvfsManager.setHint(29);
                    } else {
                        semDvfsManager.setHint(4);
                    }
                }
            } finally {
            }
        }
        synchronized (this.mLockActResume) {
            try {
                SemDvfsManager semDvfsManager2 = this.mBoosterActResume;
                if (semDvfsManager2 != null) {
                    if (z) {
                        semDvfsManager2.setHint(30);
                    } else {
                        semDvfsManager2.setHint(3);
                    }
                }
            } finally {
            }
        }
        synchronized (this.mLockTail) {
            try {
                SemDvfsManager semDvfsManager3 = this.mBoosterTail;
                if (semDvfsManager3 != null) {
                    if (z) {
                        semDvfsManager3.setHint(31);
                    } else {
                        semDvfsManager3.setHint(5);
                    }
                }
            } finally {
            }
        }
    }
}

package com.android.server.net.watchlist;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;

import com.android.internal.util.HexDump;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public final class WatchlistLoggingHandler extends Handler {
    static final int FORCE_REPORT_RECORDS_NOW_FOR_TEST_MSG = 3;
    static final int LOG_WATCHLIST_EVENT_MSG = 1;
    public static final long ONE_DAY_MS = TimeUnit.DAYS.toMillis(1);
    static final int REPORT_RECORDS_IF_NECESSARY_MSG = 2;
    public final FileHashCache mApkHashCache;
    public final ConcurrentHashMap mCachedUidDigestMap;
    public final WatchlistConfig mConfig;
    public final Context mContext;
    public final WatchlistReportDbHelper mDbHelper;
    public final DropBoxManager mDropBoxManager;
    public final PackageManager mPm;
    public int mPrimaryUserId;
    public final ContentResolver mResolver;
    public final WatchlistSettings mSettings;

    public WatchlistLoggingHandler(Context context, Looper looper) {
        super(looper);
        WatchlistReportDbHelper watchlistReportDbHelper;
        this.mPrimaryUserId = -1;
        this.mCachedUidDigestMap = new ConcurrentHashMap();
        this.mContext = context;
        this.mPm = context.getPackageManager();
        this.mResolver = context.getContentResolver();
        String[] strArr = WatchlistReportDbHelper.DIGEST_DOMAIN_PROJECTION;
        synchronized (WatchlistReportDbHelper.class) {
            watchlistReportDbHelper = WatchlistReportDbHelper.sInstance;
            if (watchlistReportDbHelper == null) {
                watchlistReportDbHelper =
                        new WatchlistReportDbHelper(
                                context,
                                new File(
                                                Environment.getDataSystemDirectory(),
                                                "watchlist_report.db")
                                        .getAbsolutePath(),
                                null,
                                2);
                watchlistReportDbHelper.setIdleConnectionTimeout(30000L);
                WatchlistReportDbHelper.sInstance = watchlistReportDbHelper;
            }
        }
        this.mDbHelper = watchlistReportDbHelper;
        this.mConfig = WatchlistConfig.sInstance;
        this.mSettings = WatchlistSettings.sInstance;
        this.mDropBoxManager = (DropBoxManager) context.getSystemService(DropBoxManager.class);
        UserInfo primaryUser = ((UserManager) context.getSystemService("user")).getPrimaryUser();
        this.mPrimaryUserId = primaryUser != null ? primaryUser.id : -1;
        if (!context.getResources().getBoolean(R.bool.ignore_emergency_number_routing_from_db)) {
            this.mApkHashCache = null;
        } else {
            this.mApkHashCache = new FileHashCache(this);
            Slog.i("WatchlistLoggingHandler", "Using file hashes cache.");
        }
    }

    public static String[] getAllSubDomains(String str) {
        if (str == null) {
            return null;
        }
        ArrayList m = PortStatus_1_1$$ExternalSyntheticOutline0.m(str);
        int indexOf = str.indexOf(".");
        while (indexOf != -1) {
            str = str.substring(indexOf + 1);
            if (!TextUtils.isEmpty(str)) {
                m.add(str);
            }
            indexOf = str.indexOf(".");
        }
        return (String[]) m.toArray(new String[0]);
    }

    public final void asyncNetworkEvent(String str, int i, String[] strArr) {
        Message obtainMessage = obtainMessage(1);
        Bundle m142m = AccountManagerService$$ExternalSyntheticOutline0.m142m("host", str);
        m142m.putStringArray("ipAddresses", strArr);
        m142m.putInt("uid", i);
        m142m.putLong("timestamp", System.currentTimeMillis());
        obtainMessage.setData(m142m);
        sendMessage(obtainMessage);
    }

    public List getAllDigestsForReport(WatchlistReportDbHelper.AggregatedResult aggregatedResult) {
        List<ApplicationInfo> installedApplications =
                this.mContext.getPackageManager().getInstalledApplications(131072);
        HashSet hashSet =
                new HashSet(
                        aggregatedResult.appDigestCNCList.size() + installedApplications.size());
        int size = installedApplications.size();
        for (int i = 0; i < size; i++) {
            int i2 = installedApplications.get(i).uid;
            byte[] bArr =
                    (byte[])
                            this.mCachedUidDigestMap.computeIfAbsent(
                                    Integer.valueOf(i2),
                                    new WatchlistLoggingHandler$$ExternalSyntheticLambda0(
                                            this, i2));
            if (bArr != null) {
                hashSet.add(HexDump.toHexString(bArr));
            }
        }
        hashSet.addAll(aggregatedResult.appDigestCNCList.keySet());
        return new ArrayList(hashSet);
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x014c A[LOOP:0: B:33:0x00fc->B:38:0x014c, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x014a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00f1 A[LOOP:1: B:57:0x00a2->B:62:0x00f1, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00f4 A[EDGE_INSN: B:63:0x00f4->B:29:0x00f4 BREAK  A[LOOP:1: B:57:0x00a2->B:62:0x00f1], SYNTHETIC] */
    @Override // android.os.Handler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleMessage(android.os.Message r14) {
        /*
            Method dump skipped, instructions count: 341
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.watchlist.WatchlistLoggingHandler.handleMessage(android.os.Message):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0023, code lost:

       if ((r4.mPm.getApplicationInfo(r2[0], 0).flags & 256) != 0) goto L16;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void insertRecord(int r5, java.lang.String r6, long r7) {
        /*
            r4 = this;
            com.android.server.net.watchlist.WatchlistConfig r0 = r4.mConfig
            boolean r0 = r0.mIsSecureConfig
            java.lang.String r1 = "WatchlistLoggingHandler"
            if (r0 != 0) goto L3a
            java.lang.String r0 = "Couldn't find package: "
            android.content.pm.PackageManager r2 = r4.mPm     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            java.lang.String[] r2 = r2.getPackagesForUid(r5)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            if (r2 == 0) goto L26
            int r3 = r2.length     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            if (r3 != 0) goto L16
            goto L26
        L16:
            android.content.pm.PackageManager r0 = r4.mPm     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            r3 = 0
            r2 = r2[r3]     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            android.content.pm.ApplicationInfo r0 = r0.getApplicationInfo(r2, r3)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            int r0 = r0.flags
            r0 = r0 & 256(0x100, float:3.59E-43)
            if (r0 == 0) goto L39
            goto L3a
        L26:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            r4.<init>(r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            java.lang.String r5 = java.util.Arrays.toString(r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            r4.append(r5)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            java.lang.String r4 = r4.toString()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
            android.util.Slog.e(r1, r4)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L39
        L39:
            return
        L3a:
            java.util.concurrent.ConcurrentHashMap r0 = r4.mCachedUidDigestMap
            java.lang.Integer r2 = java.lang.Integer.valueOf(r5)
            com.android.server.net.watchlist.WatchlistLoggingHandler$$ExternalSyntheticLambda0 r3 = new com.android.server.net.watchlist.WatchlistLoggingHandler$$ExternalSyntheticLambda0
            r3.<init>(r4, r5)
            java.lang.Object r0 = r0.computeIfAbsent(r2, r3)
            byte[] r0 = (byte[]) r0
            if (r0 != 0) goto L4e
            return
        L4e:
            com.android.server.net.watchlist.WatchlistReportDbHelper r4 = r4.mDbHelper
            r4.getClass()
            android.database.sqlite.SQLiteDatabase r4 = r4.getWritableDatabase()     // Catch: android.database.sqlite.SQLiteException -> L84
            android.content.ContentValues r2 = new android.content.ContentValues
            r2.<init>()
            java.lang.String r3 = "app_digest"
            r2.put(r3, r0)
            java.lang.String r0 = "cnc_domain"
            r2.put(r0, r6)
            java.lang.Long r6 = java.lang.Long.valueOf(r7)
            java.lang.String r7 = "timestamp"
            r2.put(r7, r6)
            java.lang.String r6 = "records"
            r7 = 0
            long r6 = r4.insert(r6, r7, r2)
            r2 = -1
            int r4 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r4 == 0) goto L8c
            java.lang.String r4 = "Unable to insert record for uid: "
            com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m(r5, r4, r1)
            goto L8c
        L84:
            r4 = move-exception
            java.lang.String r5 = "WatchlistReportDbHelper"
            java.lang.String r6 = "Error opening the database to insert a new record"
            android.util.Slog.e(r5, r6, r4)
        L8c:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.watchlist.WatchlistLoggingHandler.insertRecord(int,"
                    + " java.lang.String, long):void");
    }

    public final void tryAggregateRecords(long j) {
        byte[] bArr;
        long currentTimeMillis;
        String str;
        StringBuilder sb;
        long currentTimeMillis2 = System.currentTimeMillis();
        try {
            long j2 =
                    Settings.Global.getLong(
                            this.mResolver, "network_watchlist_last_report_time", 0L);
            if (j < j2) {
                Slog.i(
                        "WatchlistLoggingHandler",
                        "Last report time is larger than current time, reset report");
                this.mDbHelper.cleanup(j2);
            } else if (j >= j2 + ONE_DAY_MS) {
                Slog.i("WatchlistLoggingHandler", "Start aggregating watchlist records.");
                DropBoxManager dropBoxManager = this.mDropBoxManager;
                if (dropBoxManager == null
                        || !dropBoxManager.isTagEnabled("network_watchlist_report")) {
                    Slog.w(
                            "WatchlistLoggingHandler",
                            "Network Watchlist dropbox tag is not enabled");
                } else {
                    Settings.Global.putLong(
                            this.mResolver, "network_watchlist_last_report_time", j);
                    WatchlistReportDbHelper.AggregatedResult aggregatedRecords =
                            this.mDbHelper.getAggregatedRecords(j);
                    if (aggregatedRecords == null) {
                        Slog.i("WatchlistLoggingHandler", "Cannot get result from database");
                        currentTimeMillis = System.currentTimeMillis();
                        str = "WatchlistLoggingHandler";
                        sb = new StringBuilder("Milliseconds spent on tryAggregateRecords(): ");
                        sb.append(currentTimeMillis - currentTimeMillis2);
                        Slog.i(str, sb.toString());
                    }
                    List allDigestsForReport = getAllDigestsForReport(aggregatedRecords);
                    WatchlistSettings watchlistSettings = this.mSettings;
                    synchronized (watchlistSettings) {
                        bArr = new byte[48];
                        System.arraycopy(watchlistSettings.mPrivacySecretKey, 0, bArr, 0, 48);
                    }
                    WatchlistConfig watchlistConfig = this.mConfig;
                    byte[] serializeReport =
                            ReportEncoder.serializeReport(
                                    watchlistConfig,
                                    PrivacyUtils.createDpEncodedReportMap(
                                            watchlistConfig.mIsSecureConfig,
                                            bArr,
                                            allDigestsForReport,
                                            aggregatedRecords));
                    if (serializeReport != null) {
                        this.mDropBoxManager.addData(
                                "network_watchlist_report", serializeReport, 0);
                    }
                }
                this.mDbHelper.cleanup(j);
                return;
            }
            Slog.i("WatchlistLoggingHandler", "No need to aggregate record yet.");
            currentTimeMillis = System.currentTimeMillis();
            str = "WatchlistLoggingHandler";
            sb = new StringBuilder("Milliseconds spent on tryAggregateRecords(): ");
            sb.append(currentTimeMillis - currentTimeMillis2);
            Slog.i(str, sb.toString());
        } finally {
            Slog.i(
                    "WatchlistLoggingHandler",
                    "Milliseconds spent on tryAggregateRecords(): "
                            + (System.currentTimeMillis() - currentTimeMillis2));
        }
    }
}

package com.android.server.am.mars.database;

import android.content.Context;
import android.database.Cursor;
import android.util.Slog;

import java.util.ArrayList;

public final class FASDataManager {
    public Context context;
    public Boolean mIsDisableReasonColumnExist;
    public Boolean mIsPreBatteryUsageColumnExist;

    public abstract class FASDataManagerHolder {
        public static final FASDataManager INSTANCE;

        static {
            FASDataManager fASDataManager = new FASDataManager();
            fASDataManager.context = null;
            fASDataManager.mIsPreBatteryUsageColumnExist = null;
            fASDataManager.mIsDisableReasonColumnExist = null;
            INSTANCE = fASDataManager;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x003c, code lost:

       if (r1 == null) goto L17;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Boolean checkDisableReasonColumnExist() {
        /*
            r9 = this;
            java.lang.String r0 = "FASDataManager"
            java.lang.Boolean r1 = r9.mIsDisableReasonColumnExist
            if (r1 == 0) goto L7
            return r1
        L7:
            java.lang.Boolean r1 = new java.lang.Boolean
            r2 = 0
            r1.<init>(r2)
            r9.mIsDisableReasonColumnExist = r1
            r1 = 0
            android.content.Context r2 = r9.context     // Catch: java.lang.Throwable -> L2b android.database.sqlite.SQLiteException -> L2d java.lang.IllegalArgumentException -> L36
            android.content.ContentResolver r3 = r2.getContentResolver()     // Catch: java.lang.Throwable -> L2b android.database.sqlite.SQLiteException -> L2d java.lang.IllegalArgumentException -> L36
            android.net.Uri r4 = com.android.server.am.mars.database.FASTableContract.SMART_MGR_FORCED_APP_STANDBY_URI     // Catch: java.lang.Throwable -> L2b android.database.sqlite.SQLiteException -> L2d java.lang.IllegalArgumentException -> L36
            java.lang.String[] r5 = com.android.server.am.mars.database.FASTableContract.disableReasonProjection     // Catch: java.lang.Throwable -> L2b android.database.sqlite.SQLiteException -> L2d java.lang.IllegalArgumentException -> L36
            r8 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L2b android.database.sqlite.SQLiteException -> L2d java.lang.IllegalArgumentException -> L36
            java.lang.Boolean r2 = java.lang.Boolean.TRUE     // Catch: java.lang.Throwable -> L2b android.database.sqlite.SQLiteException -> L2d java.lang.IllegalArgumentException -> L36
            r9.mIsDisableReasonColumnExist = r2     // Catch: java.lang.Throwable -> L2b android.database.sqlite.SQLiteException -> L2d java.lang.IllegalArgumentException -> L36
            if (r1 == 0) goto L3f
        L27:
            r1.close()
            goto L3f
        L2b:
            r9 = move-exception
            goto L42
        L2d:
            java.lang.String r2 = "checkDisableReasonColumnExist-sql, catch no column exception!"
            android.util.Slog.e(r0, r2)     // Catch: java.lang.Throwable -> L2b
            if (r1 == 0) goto L3f
            goto L27
        L36:
            java.lang.String r2 = "checkDisableReasonColumnExist, catch no column exception!"
            android.util.Slog.e(r0, r2)     // Catch: java.lang.Throwable -> L2b
            if (r1 == 0) goto L3f
            goto L27
        L3f:
            java.lang.Boolean r9 = r9.mIsDisableReasonColumnExist
            return r9
        L42:
            if (r1 == 0) goto L47
            r1.close()
        L47:
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.mars.database.FASDataManager.checkDisableReasonColumnExist():java.lang.Boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x003c, code lost:

       if (r1 == null) goto L17;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Boolean checkPreBatteryUsageColumnExist() {
        /*
            r9 = this;
            java.lang.String r0 = "FASDataManager"
            java.lang.Boolean r1 = r9.mIsPreBatteryUsageColumnExist
            if (r1 == 0) goto L7
            return r1
        L7:
            java.lang.Boolean r1 = new java.lang.Boolean
            r2 = 0
            r1.<init>(r2)
            r9.mIsPreBatteryUsageColumnExist = r1
            r1 = 0
            android.content.Context r2 = r9.context     // Catch: java.lang.Throwable -> L2b android.database.sqlite.SQLiteException -> L2d java.lang.IllegalArgumentException -> L36
            android.content.ContentResolver r3 = r2.getContentResolver()     // Catch: java.lang.Throwable -> L2b android.database.sqlite.SQLiteException -> L2d java.lang.IllegalArgumentException -> L36
            android.net.Uri r4 = com.android.server.am.mars.database.FASTableContract.SMART_MGR_FORCED_APP_STANDBY_URI     // Catch: java.lang.Throwable -> L2b android.database.sqlite.SQLiteException -> L2d java.lang.IllegalArgumentException -> L36
            java.lang.String[] r5 = com.android.server.am.mars.database.FASTableContract.preBattetyUsageProjection     // Catch: java.lang.Throwable -> L2b android.database.sqlite.SQLiteException -> L2d java.lang.IllegalArgumentException -> L36
            r8 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L2b android.database.sqlite.SQLiteException -> L2d java.lang.IllegalArgumentException -> L36
            java.lang.Boolean r2 = java.lang.Boolean.TRUE     // Catch: java.lang.Throwable -> L2b android.database.sqlite.SQLiteException -> L2d java.lang.IllegalArgumentException -> L36
            r9.mIsPreBatteryUsageColumnExist = r2     // Catch: java.lang.Throwable -> L2b android.database.sqlite.SQLiteException -> L2d java.lang.IllegalArgumentException -> L36
            if (r1 == 0) goto L3f
        L27:
            r1.close()
            goto L3f
        L2b:
            r9 = move-exception
            goto L42
        L2d:
            java.lang.String r2 = "checkPreBatteryUsageColumnExist-sql, catch no column exception!"
            android.util.Slog.e(r0, r2)     // Catch: java.lang.Throwable -> L2b
            if (r1 == 0) goto L3f
            goto L27
        L36:
            java.lang.String r2 = "checkPreBatteryUsageColumnExist, catch no column exception!"
            android.util.Slog.e(r0, r2)     // Catch: java.lang.Throwable -> L2b
            if (r1 == 0) goto L3f
            goto L27
        L3f:
            java.lang.Boolean r9 = r9.mIsPreBatteryUsageColumnExist
            return r9
        L42:
            if (r1 == 0) goto L47
            r1.close()
        L47:
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.mars.database.FASDataManager.checkPreBatteryUsageColumnExist():java.lang.Boolean");
    }

    public final ArrayList getFASDataFromDB() {
        Cursor cursor;
        FASEntity build;
        try {
            cursor =
                    this.context
                            .getContentResolver()
                            .query(
                                    FASTableContract.SMART_MGR_FORCED_APP_STANDBY_URI,
                                    (checkDisableReasonColumnExist().booleanValue()
                                                    && checkPreBatteryUsageColumnExist()
                                                            .booleanValue())
                                            ? FASTableContract.FASQueryProjectionV3
                                            : checkPreBatteryUsageColumnExist().booleanValue()
                                                    ? FASTableContract.FASQueryProjectionV2
                                                    : FASTableContract.FASQueryProjectionV1,
                                    null,
                                    null,
                                    null);
        } catch (Exception e) {
            Slog.e("FASDataManager", "Exception with contentResolver : " + e.getMessage());
            e.printStackTrace();
            cursor = null;
        }
        if (cursor == null) {
            Slog.e("FASDataManager", "getFASDataFromDB no database!");
            return null;
        }
        ArrayList arrayList = new ArrayList();
        while (cursor.moveToNext()) {
            if (cursor.getString(0) != null) {
                if (checkDisableReasonColumnExist().booleanValue()
                        && checkPreBatteryUsageColumnExist().booleanValue()) {
                    FASEntityBuilder fASEntityBuilder = new FASEntityBuilder();
                    fASEntityBuilder.strPkgName = cursor.getString(0);
                    fASEntityBuilder.strUid = cursor.getString(1);
                    fASEntityBuilder.strMode = cursor.getString(2);
                    fASEntityBuilder.strNew = cursor.getString(3);
                    fASEntityBuilder.strFasReason = cursor.getString(4);
                    fASEntityBuilder.strExtras = cursor.getString(5);
                    fASEntityBuilder.strResetTime = cursor.getString(6);
                    fASEntityBuilder.strPackageType = cursor.getString(7);
                    fASEntityBuilder.strLevel = cursor.getString(8);
                    fASEntityBuilder.strDisableType = cursor.getString(9);
                    fASEntityBuilder.strDisableResetTime = cursor.getString(10);
                    fASEntityBuilder.strPreBatteryUsage = cursor.getString(11);
                    fASEntityBuilder.strDisableReason = cursor.getString(12);
                    build = fASEntityBuilder.build();
                } else if (checkPreBatteryUsageColumnExist().booleanValue()) {
                    FASEntityBuilder fASEntityBuilder2 = new FASEntityBuilder();
                    fASEntityBuilder2.strPkgName = cursor.getString(0);
                    fASEntityBuilder2.strUid = cursor.getString(1);
                    fASEntityBuilder2.strMode = cursor.getString(2);
                    fASEntityBuilder2.strNew = cursor.getString(3);
                    fASEntityBuilder2.strFasReason = cursor.getString(4);
                    fASEntityBuilder2.strExtras = cursor.getString(5);
                    fASEntityBuilder2.strResetTime = cursor.getString(6);
                    fASEntityBuilder2.strPackageType = cursor.getString(7);
                    fASEntityBuilder2.strLevel = cursor.getString(8);
                    fASEntityBuilder2.strDisableType = cursor.getString(9);
                    fASEntityBuilder2.strDisableResetTime = cursor.getString(10);
                    fASEntityBuilder2.strPreBatteryUsage = cursor.getString(11);
                    build = fASEntityBuilder2.build();
                } else {
                    FASEntityBuilder fASEntityBuilder3 = new FASEntityBuilder();
                    fASEntityBuilder3.strPkgName = cursor.getString(0);
                    fASEntityBuilder3.strUid = cursor.getString(1);
                    fASEntityBuilder3.strMode = cursor.getString(2);
                    fASEntityBuilder3.strNew = cursor.getString(3);
                    fASEntityBuilder3.strFasReason = cursor.getString(4);
                    fASEntityBuilder3.strExtras = cursor.getString(5);
                    fASEntityBuilder3.strResetTime = cursor.getString(6);
                    fASEntityBuilder3.strPackageType = cursor.getString(7);
                    fASEntityBuilder3.strLevel = cursor.getString(8);
                    fASEntityBuilder3.strDisableType = cursor.getString(9);
                    fASEntityBuilder3.strDisableResetTime = cursor.getString(10);
                    build = fASEntityBuilder3.build();
                }
                if (build != null) {
                    arrayList.add(build);
                }
            }
        }
        Slog.d("FASDataManager", "getFASDataFromDB fasEntityList size : " + arrayList.size());
        cursor.close();
        return arrayList;
    }
}

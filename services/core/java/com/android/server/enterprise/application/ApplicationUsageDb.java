package com.android.server.enterprise.application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ApplicationUsageDb {
    public final Context mContext;

    public ApplicationUsageDb(Context context) {
        this.mContext = context;
    }

    public static SQLiteDatabase getAppControlDB(Context context) {
        SQLiteDatabase sQLiteDatabase = null;
        if (context != null) {
            try {
                sQLiteDatabase = context.openOrCreateDatabase("dmappmgr.db", 0, null);
            } catch (Exception e) {
                Log.i("ApplicationUsageDb", "::getAppControlDB: Exception to create DB");
                e.printStackTrace();
            }
            if (sQLiteDatabase != null) {
                if (sQLiteDatabase != null) {
                    try {
                        sQLiteDatabase.execSQL("SELECT 1 FROM ApplicationControl WHERE 1=0");
                    } catch (Exception unused) {
                        Log.i("ApplicationUsageDb", "::isTableExists:Table Does not exists ");
                    }
                }
                try {
                    sQLiteDatabase.execSQL(
                            "create table ApplicationControl (_id integer primary key"
                                + " autoincrement, pkgname text, lastpausetime long,"
                                + " applastservicestarttime long, applastservicestoptime long,"
                                + " totalusagetime long, launchcount integer, lastlaunchtime long"
                                + " );");
                    Log.i("ApplicationUsageDb", "::createDmAppMgrTable: Table is Created ");
                } catch (Exception e2) {
                    Log.i(
                            "ApplicationUsageDb",
                            "::createDmAppMgrTable: Exception while table is creating ");
                    e2.printStackTrace();
                }
            }
        }
        return sQLiteDatabase;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0095 A[Catch: all -> 0x00a5, Exception -> 0x00a8, TryCatch #2 {all -> 0x00a5, blocks: (B:19:0x0032, B:21:0x0038, B:23:0x003d, B:33:0x007b, B:37:0x00b0, B:42:0x0095, B:44:0x00a2, B:45:0x00ad, B:46:0x00ab, B:47:0x007d, B:50:0x0083, B:51:0x0086), top: B:18:0x0032 }] */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v10 */
    /* JADX WARN: Type inference failed for: r13v15 */
    /* JADX WARN: Type inference failed for: r13v2, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r13v5, types: [java.util.HashMap] */
    /* JADX WARN: Type inference failed for: r13v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.HashMap getAppUsageData() {
        /*
            Method dump skipped, instructions count: 233
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.application.ApplicationUsageDb.getAppUsageData():java.util.HashMap");
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x007f, code lost:

       return r2;
    */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x007c, code lost:

       if (r10 != null) goto L29;
    */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0064, code lost:

       if (r10 != null) goto L29;
    */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0066, code lost:

       r10.close();
    */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.HashMap getLaunchCountOfAllApplication() {
        /*
            r10 = this;
            r0 = 0
            android.content.Context r10 = r10.mContext     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6f
            android.database.sqlite.SQLiteDatabase r10 = getAppControlDB(r10)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6f
            if (r10 == 0) goto L5e
            java.lang.String r2 = "ApplicationControl"
            r7 = 0
            r8 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r1 = r10
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L59
            if (r1 == 0) goto L52
            java.util.HashMap r2 = new java.util.HashMap     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4d
            r2.<init>()     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4d
            boolean r0 = r1.moveToFirst()     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L49
            if (r0 == 0) goto L4b
        L23:
            java.lang.String r0 = "pkgname"
            int r0 = r1.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L49
            java.lang.String r0 = r1.getString(r0)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L49
            java.lang.String r3 = "launchcount"
            int r3 = r1.getColumnIndex(r3)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L49
            int r3 = r1.getInt(r3)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L49
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L49
            r2.put(r0, r3)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L49
            boolean r0 = r1.moveToNext()     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L49
            if (r0 != 0) goto L23
            goto L4b
        L47:
            r0 = move-exception
            goto L80
        L49:
            r0 = move-exception
            goto L74
        L4b:
            r0 = r1
            goto L5f
        L4d:
            r2 = move-exception
            r9 = r2
            r2 = r0
            r0 = r9
            goto L74
        L52:
            r2 = r0
            goto L4b
        L54:
            r1 = move-exception
            r9 = r1
            r1 = r0
            r0 = r9
            goto L80
        L59:
            r1 = move-exception
            r2 = r0
            r0 = r1
            r1 = r2
            goto L74
        L5e:
            r2 = r0
        L5f:
            if (r0 == 0) goto L64
            r0.close()
        L64:
            if (r10 == 0) goto L7f
        L66:
            r10.close()
            goto L7f
        L6a:
            r10 = move-exception
            r1 = r0
            r0 = r10
            r10 = r1
            goto L80
        L6f:
            r10 = move-exception
            r1 = r0
            r2 = r1
            r0 = r10
            r10 = r2
        L74:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L47
            if (r1 == 0) goto L7c
            r1.close()
        L7c:
            if (r10 == 0) goto L7f
            goto L66
        L7f:
            return r2
        L80:
            if (r1 == 0) goto L85
            r1.close()
        L85:
            if (r10 == 0) goto L8a
            r10.close()
        L8a:
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.application.ApplicationUsageDb.getLaunchCountOfAllApplication():java.util.HashMap");
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateBackGroundUsageDetails(long r9, long r11, java.lang.String r13) {
        /*
            r8 = this;
            java.lang.String r0 = "'"
            java.lang.String r1 = "pkgname = '"
            java.lang.String r2 = "SELECT * FROM ApplicationControl WHERE pkgname = '"
            r3 = 0
            android.content.Context r8 = r8.mContext     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L82
            android.database.sqlite.SQLiteDatabase r8 = getAppControlDB(r8)     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L82
            if (r8 != 0) goto L11
            return
        L11:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L80
            r4.<init>(r2)     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L80
            r4.append(r13)     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L80
            r4.append(r0)     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L80
            java.lang.String r2 = r4.toString()     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L80
            android.database.Cursor r2 = r8.rawQuery(r2, r3)     // Catch: java.lang.Throwable -> L7e java.lang.Exception -> L80
            boolean r4 = r2.moveToFirst()     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            java.lang.String r5 = "ApplicationControl"
            java.lang.String r6 = "applastservicestoptime"
            java.lang.String r7 = "applastservicestarttime"
            if (r4 == 0) goto L5e
            android.content.ContentValues r4 = new android.content.ContentValues     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            r4.<init>()     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            java.lang.Long r9 = java.lang.Long.valueOf(r9)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            r4.put(r7, r9)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            java.lang.Long r9 = java.lang.Long.valueOf(r11)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            r4.put(r6, r9)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            r9.<init>(r1)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            r9.append(r13)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            r9.append(r0)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            r8.update(r5, r4, r9, r3)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            goto L7a
        L58:
            r8 = move-exception
            r3 = r2
            goto L92
        L5b:
            r9 = move-exception
            r3 = r2
            goto L84
        L5e:
            android.content.ContentValues r0 = new android.content.ContentValues     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            r0.<init>()     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            java.lang.String r1 = "pkgname"
            r0.put(r1, r13)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            java.lang.Long r9 = java.lang.Long.valueOf(r9)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            r0.put(r7, r9)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            java.lang.Long r9 = java.lang.Long.valueOf(r11)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            r0.put(r6, r9)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            r8.insert(r5, r3, r0)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
        L7a:
            r2.close()
            goto L8c
        L7e:
            r8 = move-exception
            goto L92
        L80:
            r9 = move-exception
            goto L84
        L82:
            r9 = move-exception
            r8 = r3
        L84:
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L7e
            if (r3 == 0) goto L8c
            r3.close()
        L8c:
            if (r8 == 0) goto L91
            r8.close()
        L91:
            return
        L92:
            if (r3 == 0) goto L97
            r3.close()
        L97:
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.application.ApplicationUsageDb.updateBackGroundUsageDetails(long,"
                    + " long, java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateForeGroundUsageDetails(
            int r10, long r11, long r13, java.lang.String r15) {
        /*
            r9 = this;
            java.lang.String r0 = "'"
            java.lang.String r1 = "pkgname = '"
            java.lang.String r2 = "SELECT * FROM ApplicationControl WHERE pkgname = '"
            r3 = 0
            android.content.Context r9 = r9.mContext     // Catch: java.lang.Throwable -> L98 java.lang.Exception -> L9c
            android.database.sqlite.SQLiteDatabase r9 = getAppControlDB(r9)     // Catch: java.lang.Throwable -> L98 java.lang.Exception -> L9c
            if (r9 != 0) goto L11
            return
        L11:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L98 java.lang.Exception -> L9a
            r4.<init>(r2)     // Catch: java.lang.Throwable -> L98 java.lang.Exception -> L9a
            r4.append(r15)     // Catch: java.lang.Throwable -> L98 java.lang.Exception -> L9a
            r4.append(r0)     // Catch: java.lang.Throwable -> L98 java.lang.Exception -> L9a
            java.lang.String r2 = r4.toString()     // Catch: java.lang.Throwable -> L98 java.lang.Exception -> L9a
            android.database.Cursor r2 = r9.rawQuery(r2, r3)     // Catch: java.lang.Throwable -> L98 java.lang.Exception -> L9a
            boolean r4 = r2.moveToFirst()     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            java.lang.String r5 = "ApplicationControl"
            java.lang.String r6 = "lastpausetime"
            java.lang.String r7 = "lastlaunchtime"
            java.lang.String r8 = "launchcount"
            if (r4 == 0) goto L71
            int r4 = r2.getColumnIndex(r8)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            int r4 = r2.getInt(r4)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            int r10 = r10 + r4
            android.content.ContentValues r4 = new android.content.ContentValues     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            r4.<init>()     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            r4.put(r8, r10)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            java.lang.Long r10 = java.lang.Long.valueOf(r11)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            r4.put(r7, r10)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            java.lang.Long r10 = java.lang.Long.valueOf(r13)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            r4.put(r6, r10)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            r10.<init>(r1)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            r10.append(r15)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            r10.append(r0)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            java.lang.String r10 = r10.toString()     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            r9.update(r5, r4, r10, r3)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            goto L94
        L6b:
            r9 = move-exception
            r3 = r2
            goto Lac
        L6e:
            r10 = move-exception
            r3 = r2
            goto L9e
        L71:
            android.content.ContentValues r0 = new android.content.ContentValues     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            r0.<init>()     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            r0.put(r8, r10)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            java.lang.String r10 = "pkgname"
            r0.put(r10, r15)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            java.lang.Long r10 = java.lang.Long.valueOf(r11)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            r0.put(r7, r10)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            java.lang.Long r10 = java.lang.Long.valueOf(r13)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            r0.put(r6, r10)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
            r9.insert(r5, r3, r0)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L6e
        L94:
            r2.close()
            goto La6
        L98:
            r9 = move-exception
            goto Lac
        L9a:
            r10 = move-exception
            goto L9e
        L9c:
            r10 = move-exception
            r9 = r3
        L9e:
            r10.printStackTrace()     // Catch: java.lang.Throwable -> L98
            if (r3 == 0) goto La6
            r3.close()
        La6:
            if (r9 == 0) goto Lab
            r9.close()
        Lab:
            return
        Lac:
            if (r3 == 0) goto Lb1
            r3.close()
        Lb1:
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.application.ApplicationUsageDb.updateForeGroundUsageDetails(int,"
                    + " long, long, java.lang.String):void");
    }
}

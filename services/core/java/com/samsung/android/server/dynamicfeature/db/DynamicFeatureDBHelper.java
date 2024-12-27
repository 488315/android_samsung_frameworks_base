package com.samsung.android.server.dynamicfeature.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.samsung.android.server.dynamicfeature.DFeature;
import com.samsung.android.server.dynamicfeature.InfoBoard;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DynamicFeatureDBHelper extends SQLiteOpenHelper {
    public static final String TAG = "dynamicfeature_".concat(DynamicFeatureDBHelper.class.getName());

    /* JADX WARN: Code restructure failed: missing block: B:117:0x00ec, code lost:
    
        if (r11 == null) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x01bf, code lost:
    
        if (r12 == null) goto L45;
     */
    /* JADX WARN: Removed duplicated region for block: B:107:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0290  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x02ad  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void dumpDB(android.database.sqlite.SQLiteDatabase r17, java.io.PrintWriter r18) {
        /*
            Method dump skipped, instructions count: 719
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.server.dynamicfeature.db.DynamicFeatureDBHelper.dumpDB(android.database.sqlite.SQLiteDatabase, java.io.PrintWriter):void");
    }

    public static void insertFeature(SQLiteDatabase sQLiteDatabase, DFeature dFeature) {
        String str;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("namespace", dFeature.namespace);
            contentValues.put("name", dFeature.name);
            contentValues.put("value", dFeature.value);
            contentValues.put("version", Integer.valueOf(dFeature.version));
            contentValues.put("property", Boolean.valueOf(dFeature.property));
            contentValues.put("reboot", Boolean.valueOf(dFeature.reboot));
            contentValues.put("type", dFeature.abType);
            contentValues.put("property", Boolean.valueOf(dFeature.property));
            ArrayList arrayList = dFeature.packagenames;
            String str2 = "";
            if (arrayList == null) {
                str = "";
            } else {
                Iterator it = arrayList.iterator();
                str = "";
                while (it.hasNext()) {
                    str = str + ((String) it.next()) + ",,";
                }
            }
            contentValues.put("package", str);
            ArrayList arrayList2 = dFeature.signatures;
            if (arrayList2 != null) {
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    str2 = str2 + ((String) it2.next()) + ",,";
                }
            }
            contentValues.put("signature", str2);
            sQLiteDatabase.insert("DF_FEATURE_TABLE", null, contentValues);
        } catch (Exception e) {
            NandswapManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("insertFeature : "), TAG);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x00c4, code lost:
    
        if (r1 == null) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.ArrayList loadFeatures(android.database.sqlite.SQLiteDatabase r5) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            java.lang.String r2 = "SELECT * FROM DF_FEATURE_TABLE"
            android.database.Cursor r1 = r5.rawQuery(r2, r1)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            boolean r5 = r1.moveToFirst()     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            if (r5 == 0) goto La6
        L12:
            com.samsung.android.server.dynamicfeature.DFeature r5 = new com.samsung.android.server.dynamicfeature.DFeature     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            r5.<init>()     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            java.lang.String r2 = "namespace"
            int r2 = r1.getColumnIndex(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            java.lang.String r2 = r1.getString(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            r5.namespace = r2     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            java.lang.String r2 = "name"
            int r2 = r1.getColumnIndex(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            java.lang.String r2 = r1.getString(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            r5.name = r2     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            java.lang.String r2 = "value"
            int r2 = r1.getColumnIndex(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            java.lang.String r2 = r1.getString(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            r5.value = r2     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            java.lang.String r2 = "version"
            int r2 = r1.getColumnIndex(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            int r2 = r1.getInt(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            r5.version = r2     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            java.lang.String r2 = "reboot"
            int r2 = r1.getColumnIndex(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            int r2 = r1.getInt(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            r3 = 0
            r4 = 1
            if (r2 <= 0) goto L5c
            r2 = r4
            goto L5d
        L5c:
            r2 = r3
        L5d:
            r5.reboot = r2     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            java.lang.String r2 = "type"
            int r2 = r1.getColumnIndex(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            java.lang.String r2 = r1.getString(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            r5.abType = r2     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            java.lang.String r2 = "package"
            int r2 = r1.getColumnIndex(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            java.lang.String r2 = r1.getString(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            r5.setPackageNames(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            java.lang.String r2 = "signature"
            int r2 = r1.getColumnIndex(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            java.lang.String r2 = r1.getString(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            r5.setSignatures(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            java.lang.String r2 = "property"
            int r2 = r1.getColumnIndex(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            int r2 = r1.getInt(r2)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            if (r2 <= 0) goto L96
            r3 = r4
        L96:
            r5.property = r3     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            r0.add(r5)     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            boolean r5 = r1.moveToNext()     // Catch: java.lang.Throwable -> La2 java.lang.Exception -> La4
            if (r5 != 0) goto L12
            goto La6
        La2:
            r5 = move-exception
            goto Lc8
        La4:
            r5 = move-exception
            goto Laa
        La6:
            r1.close()
            goto Lc7
        Laa:
            java.lang.String r2 = com.samsung.android.server.dynamicfeature.db.DynamicFeatureDBHelper.TAG     // Catch: java.lang.Throwable -> La2
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La2
            r3.<init>()     // Catch: java.lang.Throwable -> La2
            java.lang.String r4 = "loadFeatures "
            r3.append(r4)     // Catch: java.lang.Throwable -> La2
            java.lang.String r5 = r5.getMessage()     // Catch: java.lang.Throwable -> La2
            r3.append(r5)     // Catch: java.lang.Throwable -> La2
            java.lang.String r5 = r3.toString()     // Catch: java.lang.Throwable -> La2
            android.util.Slog.e(r2, r5)     // Catch: java.lang.Throwable -> La2
            if (r1 == 0) goto Lc7
            goto La6
        Lc7:
            return r0
        Lc8:
            if (r1 == 0) goto Lcd
            r1.close()
        Lcd:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.server.dynamicfeature.db.DynamicFeatureDBHelper.loadFeatures(android.database.sqlite.SQLiteDatabase):java.util.ArrayList");
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void loadParams(android.database.sqlite.SQLiteDatabase r19) {
        /*
            Method dump skipped, instructions count: 282
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.server.dynamicfeature.db.DynamicFeatureDBHelper.loadParams(android.database.sqlite.SQLiteDatabase):void");
    }

    public static void replaceBasicInfo(SQLiteDatabase sQLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put("_ID", (Integer) 1);
            InfoBoard.BasicInfos basicInfos = InfoBoard.basicInfo;
            contentValues.put("virtualid", basicInfos.vid);
            contentValues.put("jobIntervalMill", Long.valueOf(basicInfos.jobIntervalMill));
            contentValues.put("usertrial", Boolean.valueOf(basicInfos.isUT));
            contentValues.put("lastUpdateTime", basicInfos.lastUpdatedTime);
            sQLiteDatabase.replace("DF_INFO_TABLE", null, contentValues);
        } catch (Exception e) {
            NandswapManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("replaceBasicInfo : "), TAG);
        }
    }

    public static void replaceParamInfo(SQLiteDatabase sQLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put("_ID", (Integer) 1);
            InfoBoard.ParamInfos paramInfos = InfoBoard.paramInfo;
            contentValues.put("mcc", paramInfos.mcc);
            contentValues.put("mnc", paramInfos.mnc);
            contentValues.put("csc", paramInfos.csc);
            contentValues.put("sdkVersion", Integer.valueOf(paramInfos.sdkVersion));
            contentValues.put("oneUiVersion", paramInfos.oneUiVersion);
            contentValues.put("binaryType", paramInfos.binaryType);
            sQLiteDatabase.replace("DF_PARAM_TABLE", null, contentValues);
        } catch (Exception e) {
            NandswapManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("replaceBasicInfo : "), TAG);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS DF_FEATURE_TABLE (namespace TEXT,name TEXT,value TEXT,version INTEGER,property BOOLEAN,reboot BOOLEAN,package TEXT, signature TEXT, type TEXT,  PRIMARY KEY(namespace, name))");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS DF_INFO_TABLE (_ID INTEGER PRIMARY KEY AUTOINCREMENT, virtualid TEXT,jobIntervalMill INTEGER,usertrial TEXT,lastUpdateTime TEXT )");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS DF_PARAM_TABLE (_ID INTEGER PRIMARY KEY AUTOINCREMENT, mcc TEXT,mnc TEXT,csc TEXT,sdkVersion INTEGER,oneUiVersion TEXT, binaryType TEXT )");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS DF_FEATURE_TABLE");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS DF_FEATURE_TABLE (namespace TEXT,name TEXT,value TEXT,version INTEGER,property BOOLEAN,reboot BOOLEAN,package TEXT, signature TEXT, type TEXT,  PRIMARY KEY(namespace, name))");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS DF_INFO_TABLE (_ID INTEGER PRIMARY KEY AUTOINCREMENT, virtualid TEXT,jobIntervalMill INTEGER,usertrial TEXT,lastUpdateTime TEXT )");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS DF_PARAM_TABLE (_ID INTEGER PRIMARY KEY AUTOINCREMENT, mcc TEXT,mnc TEXT,csc TEXT,sdkVersion INTEGER,oneUiVersion TEXT, binaryType TEXT )");
    }
}

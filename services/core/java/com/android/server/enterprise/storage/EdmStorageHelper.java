package com.android.server.enterprise.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Build;
import android.os.SystemProperties;
import android.util.Log;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EdmStorageHelper extends SQLiteOpenHelper {
    public static EdmStorageHelper mInstance;
    public Context mContext;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.storage.EdmStorageHelper$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final /* synthetic */ SQLiteDatabase val$db;

        public AnonymousClass1(SQLiteDatabase sQLiteDatabase) {
            this.val$db = sQLiteDatabase;
        }

        public final void onTableFound(Table table) {
            String str = table.mTableName;
            try {
                if (!EdmStorageHelper.isTableExists(this.val$db, str)) {
                    EdmStorageHelper.createTable(this.val$db, table);
                    EdmStorageHelper.m527$$Nest$smpostTableCreate(this.val$db, table);
                    return;
                }
                if (EdmStorageHelper.m528$$Nest$smpreTableUpdate(this.val$db, table)) {
                    return;
                }
                ArrayList m525$$Nest$smgetMissingColumns =
                        EdmStorageHelper.m525$$Nest$smgetMissingColumns(this.val$db, str, table);
                Iterator it = m525$$Nest$smgetMissingColumns.iterator();
                while (it.hasNext()) {
                    Column column = (Column) it.next();
                    this.val$db.execSQL(
                            "ALTER TABLE "
                                    + str
                                    + " ADD COLUMN "
                                    + column.getSQLDeclaration()
                                    + ";");
                }
                Log.d(
                        "EdmStorageHelper",
                        String.format(
                                "onTableFound Altered Table %s with Columns %d",
                                str, Integer.valueOf(m525$$Nest$smgetMissingColumns.size())));
            } catch (Exception e) {
                Log.w("EdmStorageHelper", "Table " + str + " creation/update EX:", e);
            }
        }
    }

    /* renamed from: -$$Nest$smgetMissingColumns, reason: not valid java name */
    public static ArrayList m525$$Nest$smgetMissingColumns(
            SQLiteDatabase sQLiteDatabase, String str, Table table) {
        Cursor cursor = null;
        try {
            cursor = sQLiteDatabase.rawQuery("SELECT * FROM " + str + " LIMIT 1", null);
            List asList = Arrays.asList(cursor.getColumnNames());
            ArrayList arrayList = new ArrayList();
            Iterator it = table.mColumns.iterator();
            while (it.hasNext()) {
                Column column = (Column) it.next();
                if (!asList.contains(column.mColumnName)) {
                    arrayList.add(column);
                }
            }
            cursor.close();
            return arrayList;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* renamed from: -$$Nest$sminsertIntoEntAppMgmtTable, reason: not valid java name */
    public static void m526$$Nest$sminsertIntoEntAppMgmtTable(
            SQLiteDatabase sQLiteDatabase, String str, byte[] bArr, byte[] bArr2) {
        try {
            SQLiteStatement compileStatement =
                    sQLiteDatabase.compileStatement("INSERT INTO ENT_APP_MGMT_RT VALUES (?,?,?)");
            try {
                compileStatement.bindString(1, str);
                compileStatement.bindBlob(2, bArr);
                compileStatement.bindBlob(3, bArr2);
                compileStatement.execute();
                compileStatement.close();
            } finally {
            }
        } catch (Exception e) {
            KnoxsdkFileLog.e(
                    "EdmStorageHelper", "Error inserting package into database: " + e.getMessage());
        }
    }

    /* renamed from: -$$Nest$smpostTableCreate, reason: not valid java name */
    public static void m527$$Nest$smpostTableCreate(SQLiteDatabase sQLiteDatabase, Table table) {
        String str = table.mTableName;
        if (str.compareToIgnoreCase("ADMIN_INFO") == 0) {
            try {
                sQLiteDatabase.execSQL(
                        "INSERT INTO ADMIN_INFO VALUES (0, 'SYSTEM-LEVEL-ADMIN', 0, 0);");
                sQLiteDatabase.execSQL(
                        "INSERT INTO ADMIN_INFO VALUES (1000, 'KNOX-CUSTOM', 0, 0);");
                if (isTableExists(sQLiteDatabase, "ADMIN")) {
                    sQLiteDatabase.execSQL(
                            "INSERT INTO ADMIN_INFO(adminUid,adminName,canRemove) SELECT * from"
                                + " ADMIN WHERE adminUid!=1000;");
                    Log.d(
                            "EdmStorageHelper",
                            "In postAdminInfoTableCreate - Start adding"
                                + " KnoxCustomManagerService.DB_UID to ADMIN table...");
                    sQLiteDatabase.execSQL("INSERT INTO ADMIN VALUES (1000,1000,0);");
                    Log.d(
                            "EdmStorageHelper",
                            "In postAdminInfoTableCreate - Finished adding"
                                + " KnoxCustomManagerService.DB_UID to ADMIN table");
                    return;
                }
                return;
            } catch (Exception e) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        e, "ADMIN_INFO postAdminInfoTableCreate failed  EX: ", "EdmStorageHelper");
                return;
            }
        }
        if (str.compareToIgnoreCase("CONTAINER") == 0) {
            try {
                sQLiteDatabase.execSQL("INSERT INTO CONTAINER(containerID,adminUid) VALUES (0,0);");
                return;
            } catch (Exception e2) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        e2, "CONTAINER postContainerTableCreate failed  EX: ", "EdmStorageHelper");
                return;
            }
        }
        if (str.compareToIgnoreCase("ADMIN") == 0) {
            postAdminTableCreate(sQLiteDatabase);
            return;
        }
        if (str.compareToIgnoreCase("KNOX_CUSTOM") == 0) {
            Log.d("EdmStorageHelper", "Calling postKnoxCustomTableCreate");
            Log.d("EdmStorageHelper", "postKnoxCustomTableCreate()");
            try {
                Log.d("EdmStorageHelper", "Initialise KNOX_CUSTOM table...");
                sQLiteDatabase.execSQL("INSERT INTO KNOX_CUSTOM (adminUid) VALUES (1000);");
                Log.d("EdmStorageHelper", "Finished initialising KNOX_CUSTOM table");
                return;
            } catch (Exception e3) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        e3,
                        "ADMIN_INFO postKnoxCustomTableCreate failed  EX: ",
                        "EdmStorageHelper");
                return;
            }
        }
        if (str.compareToIgnoreCase("ENT_APP_MGMT_RT") == 0) {
            KnoxsdkFileLog.d("EdmStorageHelper", "postEntAppMgntRtTableCreate()");
            try {
                ArrayList arrayList = new ArrayList();
                arrayList.add(
                        "E4:15:1E:38:2B:51:07:8C:AA:2E:3E:0C:71:9A:95:DF:17:72:E4:CA:F1:94:96:26:48:33:AB:66:1D:86:12:65");
                arrayList.add(
                        "F7:8E:EE:60:32:9A:02:EA:D2:BE:02:06:96:0E:6E:01:8F:FF:FA:5C:AC:FD:0E:0D:76:A2:1A:62:29:0C:A9:35");
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add("com.samsung.android.knox.permission.KNOX_CCM_KEYSTORE");
                m526$$Nest$sminsertIntoEntAppMgmtTable(
                        sQLiteDatabase,
                        "com.microsoft.windowsintune.companyportal",
                        serializeObject(arrayList),
                        serializeObject(arrayList2));
                ArrayList arrayList3 = new ArrayList();
                arrayList3.add(
                        "F7:8E:EE:60:32:9A:02:EA:D2:BE:02:06:96:0E:6E:01:8F:FF:FA:5C:AC:FD:0E:0D:76:A2:1A:62:29:0C:A9:35");
                ArrayList arrayList4 = new ArrayList();
                arrayList4.add("com.samsung.android.knox.permission.KNOX_CCM_KEYSTORE");
                m526$$Nest$sminsertIntoEntAppMgmtTable(
                        sQLiteDatabase,
                        "com.microsoft.mdm.testappclient",
                        serializeObject(arrayList3),
                        serializeObject(arrayList4));
                ArrayList arrayList5 = new ArrayList();
                arrayList5.add(
                        "A4:0D:A8:0A:59:D1:70:CA:A9:50:CF:15:C1:8C:45:4D:47:A3:9B:26:98:9D:8B:64:0E:CD:74:5B:A7:1B:F5:DC");
                ArrayList arrayList6 = new ArrayList();
                arrayList6.add("com.samsung.android.knox.permission.KNOX_CCM_KEYSTORE");
                byte[] serializeObject = serializeObject(arrayList5);
                byte[] serializeObject2 = serializeObject(arrayList6);
                String str2 = Build.TYPE;
                str2.getClass();
                if (str2.equals("eng") || str2.equals("userdebug")) {
                    m526$$Nest$sminsertIntoEntAppMgmtTable(
                            sQLiteDatabase,
                            "com.samsung.edmtest",
                            serializeObject,
                            serializeObject2);
                    m526$$Nest$sminsertIntoEntAppMgmtTable(
                            sQLiteDatabase,
                            "om.samsung.android.knox.zt.sdk.consumer",
                            serializeObject,
                            serializeObject2);
                }
            } catch (Exception e4) {
                KnoxsdkFileLog.e(
                        "EdmStorageHelper",
                        "ENT_APP_MGMT_RT postEntAppMgntRtTableCreate failed  EX: " + e4);
            }
        }
    }

    /* renamed from: -$$Nest$smpreTableUpdate, reason: not valid java name */
    public static boolean m528$$Nest$smpreTableUpdate(SQLiteDatabase sQLiteDatabase, Table table) {
        ContentValues tableColumns;
        ContentValues tableColumns2;
        ContentValues tableColumns3;
        String str = table.mTableName;
        boolean z = true;
        if (str.compareToIgnoreCase("ADMIN") == 0) {
            try {
                sQLiteDatabase.execSQL("DROP TRIGGER ADMIN_INFO_ONINSERT");
                sQLiteDatabase.execSQL(
                        "CREATE TRIGGER ADMIN_INFO_ONINSERT  AFTER INSERT  ON ADMIN_INFO BEGIN"
                            + " INSERT INTO ADMIN VALUES (NEW.adminUid,NEW.adminUid,0,"
                            + " NEW.adminUid/100000); END;");
                sQLiteDatabase.execSQL(
                        "CREATE TRIGGER IF NOT EXISTS ADMIN_INFO_ONUPDATE  UPDATE  OF adminUid ON"
                            + " ADMIN_INFO BEGIN UPDATE ADMIN SET adminUid = NEW.adminUid WHERE"
                            + " adminUid = OLD.adminUid; END;");
            } catch (Exception e) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        e,
                        "ADMIN_INFOUpdate of ADMIN_INFO_ONINSERT trigger has failed : ",
                        "EdmStorageHelper");
            }
            try {
                try {
                    tableColumns = getTableColumns(sQLiteDatabase, "ADMIN");
                } catch (Exception e2) {
                    Log.e("EdmStorageHelper", "ADMIN upgrade failed  EX: " + e2);
                }
                if (tableColumns != null && tableColumns.containsKey("containerID")) {
                    Log.w("EdmStorageHelper", "Admin Table is already updated.");
                    sQLiteDatabase.setForeignKeyConstraintsEnabled(true);
                    z = false;
                    return z;
                }
                sQLiteDatabase.setForeignKeyConstraintsEnabled(false);
                sQLiteDatabase.execSQL("DROP TABLE ADMIN");
                createTable(sQLiteDatabase, table);
                postAdminTableCreate(sQLiteDatabase);
                return z;
            } finally {
                sQLiteDatabase.setForeignKeyConstraintsEnabled(true);
            }
        }
        if (str.compareToIgnoreCase("generic") != 0
                && str.compareToIgnoreCase("WebFilterLogTable") != 0) {
            int compareToIgnoreCase = str.compareToIgnoreCase("EnterpriseIslFpTable");
            String str2 = table.mTableName;
            if (compareToIgnoreCase == 0) {
                Log.e("EdmStorageHelper", "Coming inside ISL Pretable update");
                sQLiteDatabase.beginTransaction();
                try {
                    try {
                        tableColumns3 = getTableColumns(sQLiteDatabase, str2);
                    } catch (Exception e3) {
                        Log.e("EdmStorageHelper", str2 + " upgrade ISL Table: " + e3);
                    }
                    if (tableColumns3 != null && tableColumns3.containsKey("isaPackageName")) {
                        Log.e("EdmStorageHelper", "ISL Table is already updated.");
                        z = false;
                        return z;
                    }
                    Log.e("EdmStorageHelper", "Upgrading " + str2 + " Table");
                    String str3 = str2 + "_temp";
                    sQLiteDatabase.execSQL(
                            "CREATE TABLE " + str3 + " AS SELECT * FROM " + str2 + ";");
                    StringBuilder sb = new StringBuilder("DROP TABLE ");
                    sb.append(str2);
                    sQLiteDatabase.execSQL(sb.toString());
                    createTable(sQLiteDatabase, table);
                    sQLiteDatabase.execSQL(
                            "INSERT INTO "
                                    + str2
                                    + " (adminUid,fpBaseLined,packageName,fpCurrent,fpDirty,fpNewRow)"
                                    + " SELECT"
                                    + " adminUid,fpBaseLined,packageName,fpCurrent,fpDirty,fpNewRow"
                                    + " from "
                                    + str3
                                    + ";");
                    StringBuilder sb2 = new StringBuilder("DROP TABLE ");
                    sb2.append(str3);
                    sQLiteDatabase.execSQL(sb2.toString());
                    sQLiteDatabase.setTransactionSuccessful();
                    sQLiteDatabase.endTransaction();
                } finally {
                    sQLiteDatabase.endTransaction();
                }
            } else {
                if (str.compareToIgnoreCase("RCP_DATA") != 0) {
                    return false;
                }
                Log.e("EdmStorageHelper", "Coming inside RCP TABLE ".concat(str));
                sQLiteDatabase.beginTransaction();
                try {
                    try {
                        tableColumns2 = getTableColumns(sQLiteDatabase, str2);
                        Log.e("EdmStorageHelper", "Content Values is" + tableColumns2);
                    } catch (Exception e4) {
                        Log.w("EdmStorageHelper", str2 + "inside exception for rcp data " + e4);
                    }
                    if (tableColumns2 != null && tableColumns2.containsKey("cid")) {
                        Log.w("EdmStorageHelper", "Generic Table is already updated. for rcp ");
                        sQLiteDatabase.endTransaction();
                        Log.e("EdmStorageHelper", "after end transaction");
                        z = false;
                        return z;
                    }
                    Log.w("EdmStorageHelper", "Upgrading " + str2 + " Table");
                    String str4 = str2 + "_temp";
                    sQLiteDatabase.execSQL(
                            "CREATE TABLE " + str4 + " AS SELECT * FROM " + str2 + ";");
                    StringBuilder sb3 = new StringBuilder("DROP TABLE ");
                    sb3.append(str2);
                    sQLiteDatabase.execSQL(sb3.toString());
                    createTable(sQLiteDatabase, table);
                    sQLiteDatabase.execSQL(
                            "INSERT INTO "
                                    + str2
                                    + "( adminUid,name,propertyName,propertyValue)  SELECT * FROM "
                                    + str4
                                    + ";");
                    StringBuilder sb4 = new StringBuilder("DROP TABLE ");
                    sb4.append(str4);
                    sQLiteDatabase.execSQL(sb4.toString());
                    sQLiteDatabase.setTransactionSuccessful();
                } finally {
                    sQLiteDatabase.endTransaction();
                    Log.e("EdmStorageHelper", "after end transaction");
                }
            }
            z = true;
            return z;
        }
        return addContainerIdColumn(sQLiteDatabase, table);
    }

    public static boolean addContainerIdColumn(SQLiteDatabase sQLiteDatabase, Table table) {
        String str;
        ContentValues tableColumns;
        sQLiteDatabase.beginTransaction();
        try {
            try {
                str = table.mTableName;
                tableColumns = getTableColumns(sQLiteDatabase, str);
            } catch (Exception e) {
                Log.w("EdmStorageHelper", table.mTableName + " upgrade EX: " + e);
            }
            if (tableColumns != null && tableColumns.containsKey("containerID")) {
                Log.w("EdmStorageHelper", "Generic Table is already updated.");
                sQLiteDatabase.endTransaction();
                return false;
            }
            Log.w("EdmStorageHelper", "Upgrading " + str + " Table");
            String str2 = str + "_temp";
            sQLiteDatabase.execSQL("CREATE TABLE " + str2 + " AS SELECT * FROM " + str + ";");
            StringBuilder sb = new StringBuilder("DROP TABLE ");
            sb.append(str);
            sQLiteDatabase.execSQL(sb.toString());
            createTable(sQLiteDatabase, table);
            sQLiteDatabase.execSQL("INSERT INTO " + str + " SELECT *,0,0 from " + str2 + ";");
            StringBuilder sb2 = new StringBuilder("DROP TABLE ");
            sb2.append(str2);
            sQLiteDatabase.execSQL(sb2.toString());
            sQLiteDatabase.setTransactionSuccessful();
            return true;
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    public static void createTable(SQLiteDatabase sQLiteDatabase, Table table) {
        String str;
        String str2;
        String str3 = table.mTableName;
        Iterator it = table.mColumns.iterator();
        String str4 = "";
        String str5 = "";
        while (it.hasNext()) {
            Column column = (Column) it.next();
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str5);
            m.append(column.getSQLDeclaration());
            m.append(", ");
            str5 = m.toString();
        }
        String m2 =
                BootReceiver$$ExternalSyntheticOutline0.m(
                        "CREATE TABLE ",
                        str3,
                        " (",
                        str5.length() > 0
                                ? DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(
                                        2, 0, str5)
                                : "");
        Iterator it2 = table.mColumns.iterator();
        while (it2.hasNext()) {
            Column column2 = (Column) it2.next();
            if (column2.mIsPrimaryKey) {
                str4 =
                        AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                BootReceiver$$ExternalSyntheticOutline0.m(str4),
                                column2.mColumnName,
                                ", ");
            }
        }
        String m3 =
                str4.length() > 0
                        ? DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(2, 0, str4)
                        : null;
        if (m3 != null) {
            m2 = m2 + ", PRIMARY KEY (" + m3 + ")";
        }
        String str6 = table.mForeignReferTable;
        if (str6 != null
                && (str = table.mForeignReferKey) != null
                && (str2 = table.mForeignKeyName) != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(m2);
            sb.append(" FOREIGN KEY (");
            sb.append(str2);
            sb.append(") REFERENCES ");
            sb.append(str6);
            m2 =
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            sb, "(", str, ") ON DELETE CASCADE ON UPDATE CASCADE");
        }
        sQLiteDatabase.execSQL(m2.concat(");"));
        Log.d(
                "EdmStorageHelper",
                String.format(
                        "onTableFound Created Table %s with Columns %d",
                        table.mTableName, Integer.valueOf(table.mColumns.size())));
    }

    public static void doCreationOrUpdatePostCommands(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL(
                    "CREATE TRIGGER IF NOT EXISTS webFilterLoggingPolicy_TbSize  AFTER INSERT  ON"
                        + " WebFilterLogTable WHEN (SELECT COUNT(*) FROM WebFilterLogTable) > 1000 "
                        + " BEGIN  DELETE FROM WebFilterLogTable WHERE id = (SELECT id FROM"
                        + " WebFilterLogTable ORDER BY id LIMIT 1); END;  END;");
        } catch (Exception e) {
            Log.d("EdmStorageHelper", "doCreationOrUpdatePostCommands EX1:", e);
        }
        try {
            sQLiteDatabase.execSQL(
                    "CREATE TRIGGER IF NOT EXISTS bluetoothLoggingPolicy_TbSize  AFTER INSERT  ON"
                        + " BluetoothLogTable WHEN (SELECT COUNT(*) FROM BluetoothLogTable) > 1000 "
                        + " BEGIN  DELETE FROM BluetoothLogTable WHERE id = (SELECT id FROM"
                        + " BluetoothLogTable ORDER BY id LIMIT 1); END;  END;");
        } catch (Exception e2) {
            Log.d("EdmStorageHelper", "doCreationOrUpdatePostCommands EX2:", e2);
        }
        try {
            sQLiteDatabase.execSQL(
                    "INSERT INTO EnumLSOItemTypes(Item_Type, Item_Description) VALUES (1,"
                        + " 'SpaceView');");
            sQLiteDatabase.execSQL(
                    "INSERT INTO EnumLSOItemTypes(Item_Type, Item_Description) VALUES (2,"
                        + " 'TextView');");
            sQLiteDatabase.execSQL(
                    "INSERT INTO EnumLSOItemTypes(Item_Type, Item_Description) VALUES (3,"
                        + " 'ImageView');");
            sQLiteDatabase.execSQL(
                    "INSERT INTO EnumLSOItemTypes(Item_Type, Item_Description) VALUES (4,"
                        + " 'ContainerView');");
            sQLiteDatabase.execSQL(
                    "INSERT INTO EnumLSOItemTypes(Item_Type, Item_Description) VALUES (5,"
                        + " 'CustomWidget');");
        } catch (Exception unused) {
        }
        try {
            sQLiteDatabase.execSQL(
                    "INSERT INTO EnumLSOOrientation(Id, Description ) VALUES (0, 'VERTICAL');");
            sQLiteDatabase.execSQL(
                    "INSERT INTO EnumLSOOrientation(Id, Description ) VALUES (1, 'HORIZONTAL');");
        } catch (Exception unused2) {
        }
        Cursor cursor = null;
        try {
            String str = SystemProperties.get("ro.build.fingerprint", "unknown");
            if (str.equalsIgnoreCase("unknown")) {
                str = null;
            }
            sQLiteDatabase.execSQL(
                    String.format(
                            "INSERT INTO generic VALUES ('PlatformSoftwareVersion', '%s', %d);",
                            str, 0));
        } catch (Exception unused3) {
        }
        try {
            if (isTableExists(sQLiteDatabase, "APPLICATION_SIGNATURE")) {
                try {
                    Cursor query =
                            sQLiteDatabase.query(
                                    "APPLICATION_SIGNATURE", null, null, null, null, null, null);
                    try {
                        Log.d(
                                "EdmStorageHelper",
                                "APPLICATION_SIGNATURE Current Count : " + query.getCount());
                        while (query.moveToNext()) {
                            int i = query.getInt(query.getColumnIndex("adminUid"));
                            String string = query.getString(query.getColumnIndex("signature"));
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("signature", string);
                            contentValues.put("adminUid", Integer.valueOf(i));
                            contentValues.put("controlState", (Integer) 1);
                            sQLiteDatabase.insert("APPLICATION_SIGNATURE2", null, contentValues);
                        }
                        query.close();
                        sQLiteDatabase.execSQL("DROP TABLE APPLICATION_SIGNATURE;");
                    } catch (Throwable th) {
                        th = th;
                        cursor = query;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        } catch (Exception unused4) {
        }
        try {
            sQLiteDatabase.execSQL(
                    "CREATE TRIGGER IF NOT EXISTS  domainFilterReportTableCircularBuffer  AFTER"
                        + " INSERT  ON DomainFilterReportTable WHEN (SELECT COUNT(*) FROM"
                        + " DomainFilterReportTable) > 1000 BEGIN  DELETE FROM"
                        + " DomainFilterReportTable WHERE id = (SELECT id FROM"
                        + " DomainFilterReportTable ORDER BY id LIMIT 1); END;  END;");
        } catch (Exception e3) {
            Log.d("EdmStorageHelper", "doCreationOrUpdatePostCommands EX1:", e3);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x002f, code lost:

       if (r2 == null) goto L10;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getCount(android.database.sqlite.SQLiteDatabase r5) {
        /*
            java.lang.String r0 = "getCount(ADMIN_INFO) with Condition adminUid!=0 = "
            java.lang.String r1 = "EdmStorageHelper"
            r2 = 0
            r3 = 0
            java.lang.String r4 = "SELECT COUNT(*) from ADMIN_INFO WHERE adminUid!=0"
            android.database.Cursor r2 = r5.rawQuery(r4, r2)     // Catch: java.lang.Throwable -> L27 java.lang.Exception -> L29
            r2.moveToNext()     // Catch: java.lang.Throwable -> L27 java.lang.Exception -> L29
            int r3 = r2.getInt(r3)     // Catch: java.lang.Throwable -> L27 java.lang.Exception -> L29
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L27 java.lang.Exception -> L29
            r5.<init>(r0)     // Catch: java.lang.Throwable -> L27 java.lang.Exception -> L29
            r5.append(r3)     // Catch: java.lang.Throwable -> L27 java.lang.Exception -> L29
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L27 java.lang.Exception -> L29
            android.util.Log.d(r1, r5)     // Catch: java.lang.Throwable -> L27 java.lang.Exception -> L29
        L23:
            r2.close()
            goto L32
        L27:
            r5 = move-exception
            goto L33
        L29:
            java.lang.String r5 = "getCount() failed"
            android.util.Log.w(r1, r5)     // Catch: java.lang.Throwable -> L27
            if (r2 == 0) goto L32
            goto L23
        L32:
            return r3
        L33:
            if (r2 == 0) goto L38
            r2.close()
        L38:
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.storage.EdmStorageHelper.getCount(android.database.sqlite.SQLiteDatabase):int");
    }

    public static ContentValues getTableColumns(SQLiteDatabase sQLiteDatabase, String str) {
        ContentValues contentValues;
        Cursor rawQuery;
        Cursor cursor = null;
        try {
            try {
                rawQuery =
                        sQLiteDatabase.rawQuery(
                                XmlUtils$$ExternalSyntheticOutline0.m(
                                        "PRAGMA table_info(", str, ")"),
                                null);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception unused) {
            contentValues = null;
        }
        if (rawQuery != null) {
            try {
                try {
                } catch (Exception unused2) {
                    contentValues = null;
                }
                if (rawQuery.getCount() != 0) {
                    contentValues = new ContentValues();
                    while (rawQuery.moveToNext() && rawQuery.getCount() > 0) {
                        try {
                            contentValues.put(rawQuery.getString(1), rawQuery.getString(2));
                        } catch (Exception unused3) {
                            cursor = rawQuery;
                            Log.d("EdmStorageHelper", str + "does not exists");
                            if (cursor != null) {
                                cursor.close();
                            }
                            return contentValues;
                        }
                    }
                    rawQuery.close();
                    return contentValues;
                }
            } catch (Throwable th2) {
                th = th2;
                cursor = rawQuery;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }
        Log.d("EdmStorageHelper", "Failed to get list of columns for table: " + str);
        if (rawQuery != null) {
            rawQuery.close();
        }
        return null;
    }

    public static boolean insertDomainListData(
            SQLiteDatabase sQLiteDatabase, int i, String str, List list, String str2) {
        if (list != null) {
            try {
                if (!list.isEmpty()) {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        String str3 = (String) it.next();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("adminUid", Integer.valueOf(i));
                        contentValues.put("packageName", str);
                        contentValues.put("domain", str3);
                        contentValues.put("typeList", str2);
                        sQLiteDatabase.insert("DomainFilterListTable", null, contentValues);
                    }
                    return true;
                }
            } catch (Exception e) {
                Log.e("EdmStorageHelper", "insertDomainListData() - failed");
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static boolean isTableExists(SQLiteDatabase sQLiteDatabase, String str) {
        if (sQLiteDatabase == null || str == null) {
            return false;
        }
        String trim = str.trim();
        if (trim.length() <= 0) {
            return false;
        }
        try {
            sQLiteDatabase.execSQL("SELECT 1 FROM " + trim + " WHERE 1=0");
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static void postAdminTableCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL(
                    "CREATE TRIGGER ADMIN_INFO_ONINSERT  AFTER INSERT  ON ADMIN_INFO BEGIN INSERT"
                        + " INTO ADMIN VALUES (NEW.adminUid,NEW.adminUid,0, NEW.adminUid/100000);"
                        + " END;");
            if (getCount(sQLiteDatabase) > 0) {
                sQLiteDatabase.execSQL(
                        "INSERT INTO ADMIN SELECT adminUid,adminUid,0,adminUid/100000 FROM"
                            + " ADMIN_INFO WHERE adminUid!=0;");
            }
            sQLiteDatabase.execSQL(
                    "CREATE TRIGGER ADMIN_INFO_ONUPDATE  UPDATE  OF adminUid ON ADMIN_INFO BEGIN"
                        + " UPDATE ADMIN SET adminUid = NEW.adminUid WHERE adminUid = OLD.adminUid;"
                        + " END;");
            try {
                Log.d(
                        "EdmStorageHelper",
                        "Start adding KnoxCustomManagerService.DB_UID to ADMIN table...");
                sQLiteDatabase.execSQL("INSERT INTO ADMIN VALUES (1000,1000,0);");
                Log.d(
                        "EdmStorageHelper",
                        "Finished adding KnoxCustomManagerService.DB_UID to ADMIN table");
            } catch (Exception unused) {
                Log.d(
                        "EdmStorageHelper",
                        "KnoxCustomManagerService.DB_UID already exists in ADMIN table");
            }
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "ADMIN_INFO postAdminTableCreate failed  EX: ", "EdmStorageHelper");
        }
    }

    public static byte[] serializeObject(Object obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                ObjectOutputStream objectOutputStream =
                        new ObjectOutputStream(byteArrayOutputStream);
                try {
                    objectOutputStream.writeObject(obj);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    objectOutputStream.close();
                    byteArrayOutputStream.close();
                    return byteArray;
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0045, code lost:

       if (r0 == null) goto L12;
    */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0048, code lost:

       android.util.Log.d("EdmStorageHelper", "doTableCreationOrUpdate Done + " + java.lang.System.currentTimeMillis());
    */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x005e, code lost:

       return;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void doTablesCreationOrUpdate(android.database.sqlite.SQLiteDatabase r5) {
        /*
            r4 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "doTableCreationOrUpdate Starting + "
            r0.<init>(r1)
            long r1 = java.lang.System.currentTimeMillis()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "EdmStorageHelper"
            android.util.Log.d(r1, r0)
            r0 = 0
            android.content.Context r4 = r4.mContext     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3e
            android.content.res.Resources r4 = r4.getResources()     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3e
            r2 = 18284557(0x117000d, float:2.7734356E-38)
            android.content.res.XmlResourceParser r0 = r4.getXml(r2)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3e
            com.android.server.enterprise.storage.EdmStorageHelper$1 r4 = new com.android.server.enterprise.storage.EdmStorageHelper$1     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3e
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3e
            com.android.server.enterprise.storage.EnterpriseXmlParser r2 = new com.android.server.enterprise.storage.EnterpriseXmlParser     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3e
            r2.<init>(r0, r4)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3e
            r2.parseXML()     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3e
            doCreationOrUpdatePostCommands(r5)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3e
            if (r0 == 0) goto L48
        L38:
            r0.close()
            goto L48
        L3c:
            r4 = move-exception
            goto L5f
        L3e:
            r4 = move-exception
            java.lang.String r5 = "doTableCreationOrUpdate EX:"
            android.util.Log.w(r1, r5, r4)     // Catch: java.lang.Throwable -> L3c
            if (r0 == 0) goto L48
            goto L38
        L48:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "doTableCreationOrUpdate Done + "
            r4.<init>(r5)
            long r2 = java.lang.System.currentTimeMillis()
            r4.append(r2)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r1, r4)
            return
        L5f:
            if (r0 == 0) goto L64
            r0.close()
        L64:
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.storage.EdmStorageHelper.doTablesCreationOrUpdate(android.database.sqlite.SQLiteDatabase):void");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onConfigure(SQLiteDatabase sQLiteDatabase) {
        super.onConfigure(sQLiteDatabase);
        try {
            sQLiteDatabase.setForeignKeyConstraintsEnabled(true);
        } catch (Exception e) {
            Log.w("EdmStorageHelper", "Foreign Key Config failed", e);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        doTablesCreationOrUpdate(sQLiteDatabase);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        Log.w("EdmStorageHelper", "Downgrading not supported");
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00bd, code lost:

       if (r2 != null) goto L34;
    */
    @Override // android.database.sqlite.SQLiteOpenHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onUpgrade(android.database.sqlite.SQLiteDatabase r10, int r11, int r12) {
        /*
            Method dump skipped, instructions count: 249
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.storage.EdmStorageHelper.onUpgrade(android.database.sqlite.SQLiteDatabase,"
                    + " int, int):void");
    }
}

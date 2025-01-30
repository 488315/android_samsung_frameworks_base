package com.android.server.enterprise.storage;

import android.R;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class EdmStorageHelper extends SQLiteOpenHelper {
    public static EdmStorageHelper mInstance;
    public Context mContext;

    public static void preTableCreate(SQLiteDatabase sQLiteDatabase, Table table) {
    }

    public static synchronized EdmStorageHelper getInstance(Context context) {
        EdmStorageHelper edmStorageHelper;
        synchronized (EdmStorageHelper.class) {
            if (mInstance == null) {
                mInstance = new EdmStorageHelper(context);
            }
            edmStorageHelper = mInstance;
        }
        return edmStorageHelper;
    }

    public EdmStorageHelper(Context context) {
        super(context, "enterprise.db", (SQLiteDatabase.CursorFactory) null, 9);
        this.mContext = context;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onConfigure(SQLiteDatabase sQLiteDatabase) {
        super.onConfigure(sQLiteDatabase);
        try {
            sQLiteDatabase.setForeignKeyConstraintsEnabled(true);
        } catch (Exception e) {
            Log.w("EdmStorageHelper", "Foreign Key Config failed", e);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        doTablesCreationOrUpdate(sQLiteDatabase);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        Log.w("EdmStorageHelper", "Downgrading not supported");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i2 == 8) {
            updateProfilePoicyTable(sQLiteDatabase);
        }
        if (i2 == 9) {
            updateDomainFilterTable(sQLiteDatabase);
        }
    }

    public final void updateProfilePoicyTable(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("alter table ProfilePolicyPORestrictions rename to ProfilePolicyPORestrictions_tmp");
            sQLiteDatabase.execSQL("CREATE TABLE ProfilePolicyPORestrictions (adminUid INTEGER, ProfilePolicyPORestrictionsValue NUMERIC DEFAULT 1, ProfilePolicyPORestrictionsProperty TEXT, PRIMARY KEY (adminUid, ProfilePolicyPORestrictionsProperty) FOREIGN KEY (adminUid) REFERENCES ADMIN(adminUid) ON DELETE CASCADE ON UPDATE CASCADE)");
            StringBuilder sb = new StringBuilder();
            sb.append("insert into ");
            sb.append("ProfilePolicyPORestrictions");
            sb.append(" select * from ");
            sb.append("ProfilePolicyPORestrictions");
            sb.append("_tmp");
            sQLiteDatabase.execSQL(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("drop table ");
            sb2.append("ProfilePolicyPORestrictions");
            sb2.append("_tmp");
            sQLiteDatabase.execSQL(sb2.toString());
            Log.d("EdmStorageHelper", "ProfilePolicy table is migrated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void updateDomainFilterTable(SQLiteDatabase sQLiteDatabase) {
        if (createDomainFilterListTable(sQLiteDatabase)) {
            Log.i("EdmStorageHelper", "DomainFilterListTable is created");
            if (insertDomainFilterData(sQLiteDatabase)) {
                Log.i("EdmStorageHelper", "DomainFilterListTable is updated");
                if (alterDomainFilterTable(sQLiteDatabase)) {
                    Log.i("EdmStorageHelper", "DomainFilterTable is migrated");
                }
            }
        }
    }

    public final boolean createDomainFilterListTable(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE DomainFilterListTable (adminUid INTEGER, packageName TEXT,domain TEXT,typeList TEXT, PRIMARY KEY (adminUid, packageName, domain) FOREIGN KEY (adminUid) REFERENCES ADMIN(adminUid) ON DELETE CASCADE ON UPDATE CASCADE)");
            return true;
        } catch (Exception e) {
            Log.e("EdmStorageHelper", "createDomainFilterListTable() - failed");
            e.printStackTrace();
            return false;
        }
    }

    public final boolean alterDomainFilterTable(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("ALTER TABLE DomainFilterTable rename to DomainFilterTable_tmp");
            sQLiteDatabase.execSQL("CREATE TABLE DomainFilterTable (adminUid INTEGER, packageName TEXT, signature TEXT, dns1 TEXT, dns2 TEXT, networkId INT, PRIMARY KEY (adminUid, packageName) FOREIGN KEY (adminUid) REFERENCES ADMIN(adminUid) ON DELETE CASCADE ON UPDATE CASCADE)");
            sQLiteDatabase.execSQL("INSERT INTO DomainFilterTable SELECT adminUid, packageName, signature, dns1, dns2, networkId from DomainFilterTable_tmp");
            sQLiteDatabase.execSQL("DROP TABLE DomainFilterTable_tmp");
            return true;
        } catch (Exception e) {
            Log.e("EdmStorageHelper", "alterDomainFilterTable() - failed");
            e.printStackTrace();
            return false;
        }
    }

    public final boolean insertDomainListData(SQLiteDatabase sQLiteDatabase, int i, String str, List list, String str2) {
        if (list == null) {
            return true;
        }
        try {
            if (list.isEmpty()) {
                return true;
            }
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
        } catch (Exception e) {
            Log.e("EdmStorageHelper", "insertDomainListData() - failed");
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0097  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean insertDomainFilterData(SQLiteDatabase sQLiteDatabase) {
        int i;
        String string;
        String string2;
        boolean z;
        boolean z2 = false;
        Cursor cursor = null;
        try {
            try {
                cursor = sQLiteDatabase.rawQuery("SELECT adminUid, packageName, blacklist, whitelist FROM DomainFilterTable", null);
                boolean z3 = true;
                while (cursor.moveToNext()) {
                    try {
                        i = cursor.getInt(cursor.getColumnIndex("adminUid"));
                        string = cursor.getString(cursor.getColumnIndex("packageName"));
                        String string3 = cursor.getString(cursor.getColumnIndex("blacklist"));
                        string2 = cursor.getString(cursor.getColumnIndex("whitelist"));
                        z = TextUtils.isEmpty(string3) || insertDomainListData(sQLiteDatabase, i, string, Arrays.asList(string3.split(KnoxVpnFirewallHelper.DELIMITER)), "blacklist");
                    } catch (Throwable unused) {
                        z2 = z3;
                    }
                    try {
                        z3 = (TextUtils.isEmpty(string2) || !z || insertDomainListData(sQLiteDatabase, i, string, Arrays.asList(string2.split(KnoxVpnFirewallHelper.DELIMITER)), "whitelist")) ? z : false;
                    } catch (Throwable unused2) {
                        z2 = z;
                        if (cursor != null) {
                        }
                        return z2;
                    }
                }
                cursor.close();
                return z3;
            } catch (Throwable unused3) {
                z2 = true;
            }
        } catch (Exception e) {
            try {
                Log.e("EdmStorageHelper", "insertDomainFilterData() - failed");
                e.printStackTrace();
                if (0 != 0) {
                    cursor.close();
                }
                return false;
            } catch (Throwable unused4) {
                if (cursor != null) {
                    cursor.close();
                }
                return z2;
            }
        }
    }

    public static void createTable(SQLiteDatabase sQLiteDatabase, Table table) {
        String str;
        String str2;
        String format = String.format("CREATE TABLE %s (%s", table.mTableName, table.buildTableColumns());
        String buildPrimaryKeys = table.buildPrimaryKeys();
        if (buildPrimaryKeys != null) {
            format = String.format("%s, PRIMARY KEY (%s)", format, buildPrimaryKeys);
        }
        String str3 = table.mForeignReferTable;
        if (str3 != null && (str = table.mForeignReferKey) != null && (str2 = table.mForeignKeyName) != null) {
            format = String.format("%s FOREIGN KEY (%s) REFERENCES %s(%s) ON DELETE CASCADE ON UPDATE CASCADE", format, str2, str3, str);
        }
        sQLiteDatabase.execSQL(format + ");");
        Log.d("EdmStorageHelper", String.format("onTableFound Created Table %s with Columns %d", table.mTableName, Integer.valueOf(table.mColumns.size())));
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0043, code lost:
    
        if (r0 == null) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4:0x0038, code lost:
    
        if (r0 != null) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0048, code lost:
    
        android.util.Log.d("EdmStorageHelper", "doTableCreationOrUpdate Done + " + java.lang.System.currentTimeMillis());
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0060, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0045, code lost:
    
        r0.close();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void doTablesCreationOrUpdate(final SQLiteDatabase sQLiteDatabase) {
        Log.d("EdmStorageHelper", "doTableCreationOrUpdate Starting + " + System.currentTimeMillis());
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                xmlResourceParser = this.mContext.getResources().getXml(R.xml.password_kbd_numeric);
                new EnterpriseXmlParser(xmlResourceParser, new TableCallback() { // from class: com.android.server.enterprise.storage.EdmStorageHelper.1
                    @Override // com.android.server.enterprise.storage.TableCallback
                    public void onTableFound(Table table) {
                        String str = table.mTableName;
                        try {
                            if (!EdmStorageHelper.isTableExists(sQLiteDatabase, str)) {
                                EdmStorageHelper.preTableCreate(sQLiteDatabase, table);
                                EdmStorageHelper.createTable(sQLiteDatabase, table);
                                EdmStorageHelper.postTableCreate(sQLiteDatabase, table);
                            } else {
                                if (EdmStorageHelper.preTableUpdate(sQLiteDatabase, table)) {
                                    return;
                                }
                                ArrayList missingColumns = EdmStorageHelper.getMissingColumns(sQLiteDatabase, str, table);
                                Iterator it = missingColumns.iterator();
                                while (it.hasNext()) {
                                    sQLiteDatabase.execSQL(String.format("ALTER TABLE %s ADD COLUMN %s;", str, ((Column) it.next()).getSQLDeclaration()));
                                }
                                Log.d("EdmStorageHelper", String.format("onTableFound Altered Table %s with Columns %d", str, Integer.valueOf(missingColumns.size())));
                            }
                        } catch (Exception e) {
                            Log.w("EdmStorageHelper", "Table " + str + " creation/update EX:", e);
                        }
                    }
                }).parseXML();
                doCreationOrUpdatePostCommands(sQLiteDatabase);
            } catch (Exception e) {
                Log.w("EdmStorageHelper", "doTableCreationOrUpdate EX:", e);
            }
        } catch (Throwable th) {
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
            throw th;
        }
    }

    public void doCreationOrUpdatePostCommands(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TRIGGER IF NOT EXISTS webFilterLoggingPolicy_TbSize  AFTER INSERT  ON WebFilterLogTable WHEN (SELECT COUNT(*) FROM WebFilterLogTable) > 1000  BEGIN  DELETE FROM WebFilterLogTable WHERE id = (SELECT id FROM WebFilterLogTable ORDER BY id LIMIT 1); END;  END;");
        } catch (Exception e) {
            Log.d("EdmStorageHelper", "doCreationOrUpdatePostCommands EX1:", e);
        }
        try {
            sQLiteDatabase.execSQL("CREATE TRIGGER IF NOT EXISTS bluetoothLoggingPolicy_TbSize  AFTER INSERT  ON BluetoothLogTable WHEN (SELECT COUNT(*) FROM BluetoothLogTable) > 1000  BEGIN  DELETE FROM BluetoothLogTable WHERE id = (SELECT id FROM BluetoothLogTable ORDER BY id LIMIT 1); END;  END;");
        } catch (Exception e2) {
            Log.d("EdmStorageHelper", "doCreationOrUpdatePostCommands EX2:", e2);
        }
        try {
            sQLiteDatabase.execSQL("INSERT INTO EnumLSOItemTypes(Item_Type, Item_Description) VALUES (1, 'SpaceView');");
            sQLiteDatabase.execSQL("INSERT INTO EnumLSOItemTypes(Item_Type, Item_Description) VALUES (2, 'TextView');");
            sQLiteDatabase.execSQL("INSERT INTO EnumLSOItemTypes(Item_Type, Item_Description) VALUES (3, 'ImageView');");
            sQLiteDatabase.execSQL("INSERT INTO EnumLSOItemTypes(Item_Type, Item_Description) VALUES (4, 'ContainerView');");
            sQLiteDatabase.execSQL("INSERT INTO EnumLSOItemTypes(Item_Type, Item_Description) VALUES (5, 'CustomWidget');");
        } catch (Exception unused) {
        }
        try {
            sQLiteDatabase.execSQL("INSERT INTO EnumLSOOrientation(Id, Description ) VALUES (0, 'VERTICAL');");
            sQLiteDatabase.execSQL("INSERT INTO EnumLSOOrientation(Id, Description ) VALUES (1, 'HORIZONTAL');");
        } catch (Exception unused2) {
        }
        Cursor cursor = null;
        try {
            String str = SystemProperties.get("ro.build.fingerprint", "unknown");
            if (str.equalsIgnoreCase("unknown")) {
                str = null;
            }
            sQLiteDatabase.execSQL(String.format("INSERT INTO generic VALUES ('PlatformSoftwareVersion', '%s', %d);", str, 0));
        } catch (Exception unused3) {
        }
        try {
            if (isTableExists(sQLiteDatabase, "APPLICATION_SIGNATURE")) {
                try {
                    Cursor query = sQLiteDatabase.query("APPLICATION_SIGNATURE", null, null, null, null, null, null);
                    try {
                        Log.d("EdmStorageHelper", "APPLICATION_SIGNATURE Current Count : " + query.getCount());
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
            sQLiteDatabase.execSQL("CREATE TRIGGER IF NOT EXISTS  domainFilterReportTableCircularBuffer  AFTER INSERT  ON DomainFilterReportTable WHEN (SELECT COUNT(*) FROM DomainFilterReportTable) > 1000 BEGIN  DELETE FROM DomainFilterReportTable WHERE id = (SELECT id FROM DomainFilterReportTable ORDER BY id LIMIT 1); END;  END;");
        } catch (Exception e3) {
            Log.d("EdmStorageHelper", "doCreationOrUpdatePostCommands EX1:", e3);
        }
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

    public static ArrayList getMissingColumns(SQLiteDatabase sQLiteDatabase, String str, Table table) {
        Cursor cursor = null;
        try {
            cursor = sQLiteDatabase.rawQuery("SELECT * FROM " + str + " LIMIT 1", null);
            ArrayList missingColumns = table.getMissingColumns(Arrays.asList(cursor.getColumnNames()));
            cursor.close();
            return missingColumns;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public static void postTableCreate(SQLiteDatabase sQLiteDatabase, Table table) {
        String str = table.mTableName;
        if (str.compareToIgnoreCase("ADMIN_INFO") == 0) {
            postAdminInfoTableCreate(sQLiteDatabase);
            return;
        }
        if (str.compareToIgnoreCase("CONTAINER") == 0) {
            postContainerTableCreate(sQLiteDatabase);
            return;
        }
        if (str.compareToIgnoreCase("ADMIN") == 0) {
            postAdminTableCreate(sQLiteDatabase);
        } else if (str.compareToIgnoreCase("KNOX_CUSTOM") == 0) {
            Log.d("EdmStorageHelper", "Calling postKnoxCustomTableCreate");
            postKnoxCustomTableCreate(sQLiteDatabase);
        }
    }

    public static boolean preTableUpdate(SQLiteDatabase sQLiteDatabase, Table table) {
        String str = table.mTableName;
        if (str.compareToIgnoreCase("ADMIN") == 0) {
            updateAdminInfoTrigger(sQLiteDatabase);
            return upgradeAdminTable(sQLiteDatabase, table);
        }
        if (str.compareToIgnoreCase("generic") == 0) {
            return addContainerIdColumn(sQLiteDatabase, table);
        }
        if (str.compareToIgnoreCase("WebFilterLogTable") == 0) {
            return addContainerIdColumn(sQLiteDatabase, table);
        }
        if (str.compareToIgnoreCase("EnterpriseIslFpTable") == 0) {
            Log.e("EdmStorageHelper", "Coming inside ISL Pretable update");
            return addISAPackageNameColumn(sQLiteDatabase, table);
        }
        if (str.compareToIgnoreCase("RCP_DATA") != 0) {
            return false;
        }
        Log.e("EdmStorageHelper", "Coming inside RCP TABLE " + str);
        return addCidColumnForRCP(sQLiteDatabase, table);
    }

    public static boolean addCidColumnForRCP(SQLiteDatabase sQLiteDatabase, Table table) {
        ContentValues tableColumns;
        sQLiteDatabase.beginTransaction();
        try {
            try {
                tableColumns = getTableColumns(sQLiteDatabase, table.mTableName);
                Log.e("EdmStorageHelper", "Content Values is" + tableColumns);
            } catch (Exception e) {
                Log.w("EdmStorageHelper", table.mTableName + "inside exception for rcp data " + e);
            }
            if (tableColumns != null && tableColumns.containsKey("cid")) {
                Log.w("EdmStorageHelper", "Generic Table is already updated. for rcp ");
                sQLiteDatabase.endTransaction();
                Log.e("EdmStorageHelper", "after end transaction");
                return false;
            }
            Log.w("EdmStorageHelper", "Upgrading " + table.mTableName + " Table");
            StringBuilder sb = new StringBuilder();
            sb.append(table.mTableName);
            sb.append("_temp");
            String sb2 = sb.toString();
            sQLiteDatabase.execSQL("CREATE TABLE " + sb2 + " AS SELECT * FROM " + table.mTableName + KnoxVpnFirewallHelper.DELIMITER);
            StringBuilder sb3 = new StringBuilder();
            sb3.append("DROP TABLE ");
            sb3.append(table.mTableName);
            sQLiteDatabase.execSQL(sb3.toString());
            createTable(sQLiteDatabase, table);
            sQLiteDatabase.execSQL("INSERT INTO " + table.mTableName + "( adminUid,name,propertyName,propertyValue)  SELECT * FROM " + sb2 + KnoxVpnFirewallHelper.DELIMITER);
            StringBuilder sb4 = new StringBuilder();
            sb4.append("DROP TABLE ");
            sb4.append(sb2);
            sQLiteDatabase.execSQL(sb4.toString());
            sQLiteDatabase.setTransactionSuccessful();
            sQLiteDatabase.endTransaction();
            Log.e("EdmStorageHelper", "after end transaction");
            return true;
        } catch (Throwable th) {
            sQLiteDatabase.endTransaction();
            Log.e("EdmStorageHelper", "after end transaction");
            throw th;
        }
    }

    public static void postAdminInfoTableCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("INSERT INTO ADMIN_INFO VALUES (0, 'SYSTEM-LEVEL-ADMIN', 0, 0);");
            sQLiteDatabase.execSQL("INSERT INTO ADMIN_INFO VALUES (1000, 'KNOX-CUSTOM', 0, 0);");
            if (isTableExists(sQLiteDatabase, "ADMIN")) {
                sQLiteDatabase.execSQL("INSERT INTO ADMIN_INFO(adminUid,adminName,canRemove) SELECT * from ADMIN WHERE adminUid!=1000" + KnoxVpnFirewallHelper.DELIMITER);
                Log.d("EdmStorageHelper", "In postAdminInfoTableCreate - Start adding KnoxCustomManagerService.DB_UID to ADMIN table...");
                sQLiteDatabase.execSQL("INSERT INTO ADMIN VALUES (1000,1000,0);");
                Log.d("EdmStorageHelper", "In postAdminInfoTableCreate - Finished adding KnoxCustomManagerService.DB_UID to ADMIN table");
            }
        } catch (Exception e) {
            Log.e("EdmStorageHelper", "ADMIN_INFO postAdminInfoTableCreate failed  EX: " + e);
        }
    }

    public static void postContainerTableCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("INSERT INTO CONTAINER(containerID,adminUid) VALUES (0,0);");
        } catch (Exception e) {
            Log.e("EdmStorageHelper", "CONTAINER postContainerTableCreate failed  EX: " + e);
        }
    }

    public static void postAdminTableCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TRIGGER ADMIN_INFO_ONINSERT  AFTER INSERT  ON ADMIN_INFO BEGIN INSERT INTO ADMIN VALUES (NEW.adminUid,NEW.adminUid,0, NEW.adminUid/100000); END;");
            if (getCount(sQLiteDatabase, "ADMIN_INFO", "adminUid!=0") > 0) {
                sQLiteDatabase.execSQL("INSERT INTO ADMIN SELECT adminUid,adminUid,0,adminUid/100000 FROM ADMIN_INFO WHERE adminUid!=0" + KnoxVpnFirewallHelper.DELIMITER);
            }
            sQLiteDatabase.execSQL("CREATE TRIGGER ADMIN_INFO_ONUPDATE  UPDATE  OF adminUid ON ADMIN_INFO BEGIN UPDATE ADMIN SET adminUid = NEW.adminUid WHERE adminUid = OLD.adminUid; END;");
            try {
                Log.d("EdmStorageHelper", "Start adding KnoxCustomManagerService.DB_UID to ADMIN table...");
                sQLiteDatabase.execSQL("INSERT INTO ADMIN VALUES (1000,1000,0);");
                Log.d("EdmStorageHelper", "Finished adding KnoxCustomManagerService.DB_UID to ADMIN table");
            } catch (Exception unused) {
                Log.d("EdmStorageHelper", "KnoxCustomManagerService.DB_UID already exists in ADMIN table");
            }
        } catch (Exception e) {
            Log.e("EdmStorageHelper", "ADMIN_INFO postAdminTableCreate failed  EX: " + e);
        }
    }

    public static void postKnoxCustomTableCreate(SQLiteDatabase sQLiteDatabase) {
        Log.d("EdmStorageHelper", "postKnoxCustomTableCreate()");
        try {
            Log.d("EdmStorageHelper", "Initialise KNOX_CUSTOM table...");
            sQLiteDatabase.execSQL("INSERT INTO KNOX_CUSTOM (adminUid) VALUES (1000);");
            Log.d("EdmStorageHelper", "Finished initialising KNOX_CUSTOM table");
        } catch (Exception e) {
            Log.e("EdmStorageHelper", "ADMIN_INFO postKnoxCustomTableCreate failed  EX: " + e);
        }
    }

    public static void updateAdminInfoTrigger(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("DROP TRIGGER ADMIN_INFO_ONINSERT");
            sQLiteDatabase.execSQL("CREATE TRIGGER ADMIN_INFO_ONINSERT  AFTER INSERT  ON ADMIN_INFO BEGIN INSERT INTO ADMIN VALUES (NEW.adminUid,NEW.adminUid,0, NEW.adminUid/100000); END;");
            sQLiteDatabase.execSQL("CREATE TRIGGER IF NOT EXISTS ADMIN_INFO_ONUPDATE  UPDATE  OF adminUid ON ADMIN_INFO BEGIN UPDATE ADMIN SET adminUid = NEW.adminUid WHERE adminUid = OLD.adminUid; END;");
        } catch (Exception e) {
            Log.e("EdmStorageHelper", "ADMIN_INFOUpdate of ADMIN_INFO_ONINSERT trigger has failed : " + e);
        }
    }

    public static boolean upgradeAdminTable(SQLiteDatabase sQLiteDatabase, Table table) {
        ContentValues tableColumns;
        try {
            try {
                tableColumns = getTableColumns(sQLiteDatabase, "ADMIN");
            } catch (Exception e) {
                Log.e("EdmStorageHelper", "ADMIN upgrade failed  EX: " + e);
            }
            if (tableColumns != null && tableColumns.containsKey("containerID")) {
                Log.w("EdmStorageHelper", "Admin Table is already updated.");
                return false;
            }
            sQLiteDatabase.setForeignKeyConstraintsEnabled(false);
            sQLiteDatabase.execSQL("DROP TABLE ADMIN");
            createTable(sQLiteDatabase, table);
            postAdminTableCreate(sQLiteDatabase);
            return true;
        } finally {
            sQLiteDatabase.setForeignKeyConstraintsEnabled(true);
        }
    }

    public static boolean addContainerIdColumn(SQLiteDatabase sQLiteDatabase, Table table) {
        ContentValues tableColumns;
        sQLiteDatabase.beginTransaction();
        try {
            try {
                tableColumns = getTableColumns(sQLiteDatabase, table.mTableName);
            } catch (Exception e) {
                Log.w("EdmStorageHelper", table.mTableName + " upgrade EX: " + e);
            }
            if (tableColumns != null && tableColumns.containsKey("containerID")) {
                Log.w("EdmStorageHelper", "Generic Table is already updated.");
                return false;
            }
            Log.w("EdmStorageHelper", "Upgrading " + table.mTableName + " Table");
            StringBuilder sb = new StringBuilder();
            sb.append(table.mTableName);
            sb.append("_temp");
            String sb2 = sb.toString();
            sQLiteDatabase.execSQL("CREATE TABLE " + sb2 + " AS SELECT * FROM " + table.mTableName + KnoxVpnFirewallHelper.DELIMITER);
            StringBuilder sb3 = new StringBuilder();
            sb3.append("DROP TABLE ");
            sb3.append(table.mTableName);
            sQLiteDatabase.execSQL(sb3.toString());
            createTable(sQLiteDatabase, table);
            sQLiteDatabase.execSQL("INSERT INTO " + table.mTableName + " SELECT *,0,0 from " + sb2 + KnoxVpnFirewallHelper.DELIMITER);
            StringBuilder sb4 = new StringBuilder();
            sb4.append("DROP TABLE ");
            sb4.append(sb2);
            sQLiteDatabase.execSQL(sb4.toString());
            sQLiteDatabase.setTransactionSuccessful();
            sQLiteDatabase.endTransaction();
            return true;
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    public static boolean addISAPackageNameColumn(SQLiteDatabase sQLiteDatabase, Table table) {
        ContentValues tableColumns;
        sQLiteDatabase.beginTransaction();
        try {
            try {
                tableColumns = getTableColumns(sQLiteDatabase, table.mTableName);
            } catch (Exception e) {
                Log.e("EdmStorageHelper", table.mTableName + " upgrade ISL Table: " + e);
            }
            if (tableColumns != null && tableColumns.containsKey("isaPackageName")) {
                Log.e("EdmStorageHelper", "ISL Table is already updated.");
                sQLiteDatabase.endTransaction();
                return false;
            }
            Log.e("EdmStorageHelper", "Upgrading " + table.mTableName + " Table");
            StringBuilder sb = new StringBuilder();
            sb.append(table.mTableName);
            sb.append("_temp");
            String sb2 = sb.toString();
            sQLiteDatabase.execSQL("CREATE TABLE " + sb2 + " AS SELECT * FROM " + table.mTableName + KnoxVpnFirewallHelper.DELIMITER);
            StringBuilder sb3 = new StringBuilder();
            sb3.append("DROP TABLE ");
            sb3.append(table.mTableName);
            sQLiteDatabase.execSQL(sb3.toString());
            createTable(sQLiteDatabase, table);
            sQLiteDatabase.execSQL("INSERT INTO " + table.mTableName + " (adminUid,fpBaseLined,packageName,fpCurrent,fpDirty,fpNewRow) SELECT adminUid,fpBaseLined,packageName,fpCurrent,fpDirty,fpNewRow from " + sb2 + KnoxVpnFirewallHelper.DELIMITER);
            StringBuilder sb4 = new StringBuilder();
            sb4.append("DROP TABLE ");
            sb4.append(sb2);
            sQLiteDatabase.execSQL(sb4.toString());
            sQLiteDatabase.setTransactionSuccessful();
            sQLiteDatabase.endTransaction();
            return true;
        } catch (Throwable th) {
            sQLiteDatabase.endTransaction();
            throw th;
        }
    }

    public static ContentValues getTableColumns(SQLiteDatabase sQLiteDatabase, String str) {
        ContentValues contentValues;
        Cursor rawQuery;
        Cursor cursor = null;
        try {
            try {
                rawQuery = sQLiteDatabase.rawQuery("PRAGMA table_info(" + str + ")", null);
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
                } catch (Throwable th) {
                    th = th;
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
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0065, code lost:
    
        if (r1 == null) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int getCount(SQLiteDatabase sQLiteDatabase, String str, String str2) {
        Cursor cursor = null;
        int i = 0;
        try {
            try {
                String str3 = "SELECT COUNT(*) from " + str;
                if (str2 != null) {
                    str3 = str3 + " WHERE " + str2;
                }
                cursor = sQLiteDatabase.rawQuery(str3, null);
                cursor.moveToNext();
                i = cursor.getInt(0);
                Log.d("EdmStorageHelper", "getCount(" + str + ") with Condition " + str2 + " = " + i);
            } catch (Exception unused) {
                Log.w("EdmStorageHelper", "getCount() failed");
            }
            cursor.close();
            return i;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }
}

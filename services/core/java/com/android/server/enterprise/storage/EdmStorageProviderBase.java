package com.android.server.enterprise.storage;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0;
import com.android.server.enterprise.container.KnoxMUMContainerPolicy$$ExternalSyntheticOutline0;
import com.samsung.android.knox.SemPersonaManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class EdmStorageProviderBase {
    public final EdmStorageHelper mEdmDbHelper;

    public EdmStorageProviderBase(Context context) {
        EdmStorageHelper edmStorageHelper;
        EdmStorageHelper edmStorageHelper2 = EdmStorageHelper.mInstance;
        synchronized (EdmStorageHelper.class) {
            try {
                if (EdmStorageHelper.mInstance == null) {
                    EdmStorageHelper edmStorageHelper3 = new EdmStorageHelper(context, "enterprise.db", null, 9);
                    edmStorageHelper3.mContext = context;
                    EdmStorageHelper.mInstance = edmStorageHelper3;
                }
                edmStorageHelper = EdmStorageHelper.mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mEdmDbHelper = edmStorageHelper;
        try {
            edmStorageHelper.getWritableDatabase().enableWriteAheadLogging();
        } catch (Exception unused) {
            Log.e("EdmStorageProvider", "Failed to OPEN/CREATE the database");
        }
    }

    public static void convertAdminIdToLUID(ContentValues contentValues) {
        boolean containsKey = contentValues.containsKey("containerID");
        if (!contentValues.containsKey("adminUid") || !containsKey || contentValues.getAsInteger("containerID") == null || contentValues.getAsInteger("adminUid") == null) {
            return;
        }
        int intValue = contentValues.getAsInteger("containerID").intValue();
        int intValue2 = contentValues.getAsInteger("adminUid").intValue();
        contentValues.remove("containerID");
        contentValues.remove("adminUid");
        contentValues.put("adminUid", Long.valueOf(translateToAdminLUID(intValue2, intValue)));
    }

    public static String[] formatContentValues(ContentValues contentValues, StringBuilder sb) {
        if (contentValues != null && contentValues.size() > 0) {
            Set<Map.Entry<String, Object>> valueSet = contentValues.valueSet();
            ArrayList arrayList = new ArrayList();
            int i = 0;
            for (Map.Entry<String, Object> entry : valueSet) {
                String key = entry.getKey();
                String valueOf = String.valueOf(entry.getValue());
                boolean equals = valueOf.equals("#SelectClause#");
                String str = (equals || key.contains("=")) ? "" : "=?";
                if (i == 0) {
                    sb.append(key + str);
                } else {
                    sb.append(" AND " + key + str);
                }
                if (!equals) {
                    arrayList.add(valueOf);
                }
                i++;
            }
            int size = arrayList.size();
            if (size > 0) {
                return (String[]) arrayList.toArray(new String[size]);
            }
        }
        return null;
    }

    public static String getAdminLUIDWhereIn(int i, int i2) {
        StringBuilder sb = new StringBuilder("adminUid IN (");
        if (i == -1) {
            throw new IllegalArgumentException("Invalid container id!!");
        }
        if (SemPersonaManager.isKnoxId(i)) {
            i = 0;
        }
        sb.append(new StringBuilder(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "SELECT adminUid FROM ADMIN WHERE containerID=", " AND userID=")).toString());
        sb.append(")");
        return sb.toString();
    }

    public static void parseContentValues(ContentValues contentValues) {
        int i;
        if (contentValues == null) {
            return;
        }
        convertAdminIdToLUID(contentValues);
        if (!contentValues.containsKey("containerID") || contentValues.getAsInteger("containerID") == null) {
            return;
        }
        int intValue = contentValues.getAsInteger("containerID").intValue();
        contentValues.remove("containerID");
        if (!contentValues.containsKey("userID") || contentValues.getAsInteger("userID") == null) {
            i = 0;
        } else {
            i = contentValues.getAsInteger("userID").intValue();
            contentValues.remove("userID");
        }
        contentValues.put(getAdminLUIDWhereIn(intValue, i), "#SelectClause#");
    }

    public static long translateToAdminLUID(int i, int i2) {
        if (i == 0 || i2 <= 0 || SemPersonaManager.isKnoxId(i2)) {
            return i;
        }
        return i | (i2 << 32);
    }

    public final void addAdmin(int i, String str) {
        ContentValues contentValues = new ContentValues();
        KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(i, contentValues, "adminUid", "adminName", str);
        contentValues.put("canRemove", Boolean.TRUE);
        insert("ADMIN_INFO", contentValues);
    }

    public final boolean addMUMContainer(int i, int i2) {
        ContentValues contentValues = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(i, contentValues, "containerID", i2, "adminUid");
        return insert("MUMCONTAINER", contentValues) != -1;
    }

    public final boolean addorUpdateAdmin(int i, String str, boolean z) {
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("adminName", str);
        m.put("canRemove", Boolean.valueOf(z));
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        if (update("ADMIN_INFO", m, contentValues) > 0) {
            return true;
        }
        m.putAll(contentValues);
        return insert("ADMIN_INFO", m) != -1;
    }

    public final boolean addorUpdateAdminWithPseudo(int i, String str) {
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("adminName", str);
        m.put("canRemove", Boolean.FALSE);
        m.put("isPseudoAdmin", Boolean.TRUE);
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        if (update("ADMIN_INFO", m, contentValues) > 0) {
            return true;
        }
        m.putAll(contentValues);
        return insert("ADMIN_INFO", m) != -1;
    }

    public final boolean canRemoveAdmin(int i) {
        try {
            return getBoolean(i, 0, "ADMIN_INFO", "canRemove");
        } catch (Exception unused) {
            Log.e("EdmStorageProvider", "Admin not in database, something went wrong");
            return true;
        }
    }

    public final boolean checkPseudoAdminForUid(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        ArrayList arrayList = (ArrayList) getBooleanList(contentValues, "ADMIN_INFO", "isPseudoAdmin");
        if (arrayList.size() > 0) {
            return ((Boolean) arrayList.get(0)).booleanValue();
        }
        throw new SettingNotFoundException("Admin data is null");
    }

    public final int delete(String str, ContentValues contentValues) {
        StringBuilder sb = new StringBuilder();
        return this.mEdmDbHelper.getWritableDatabase().delete(str, sb.length() > 0 ? sb.toString() : null, formatContentValues(contentValues, sb));
    }

    public final ArrayList getAdminUidList() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid!=?", (Integer) 0);
        return new ArrayList(getIntList(contentValues, "ADMIN_INFO", "adminUid"));
    }

    public final ArrayList getAdminUidListAsUser(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid!=?", (Integer) 0);
        contentValues.put("userID", Integer.valueOf(i));
        return new ArrayList(getIntList(contentValues, "ADMIN", "adminUid"));
    }

    public final byte[] getBlob(ContentValues contentValues, String str, String str2) {
        ArrayList arrayList = (ArrayList) getBlobList(contentValues, str, str2);
        if (arrayList.size() > 0) {
            return (byte[]) arrayList.get(0);
        }
        return null;
    }

    public final List getBlobList(ContentValues contentValues, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = getCursor(str, new String[]{str2}, contentValues);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    try {
                        if (!cursor.isNull(0)) {
                            arrayList.add(cursor.getBlob(0));
                        }
                    } catch (SQLException e) {
                        Log.e("EdmStorageProvider", "Exception occurred accessing Enterprise db " + e.getMessage());
                    }
                } finally {
                    cursor.close();
                }
            }
        }
        return arrayList;
    }

    public final boolean getBoolean(int i, int i2, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        ArrayList arrayList = (ArrayList) getBooleanList(contentValues, str, str2);
        if (arrayList.size() > 0) {
            return ((Boolean) arrayList.get(0)).booleanValue();
        }
        throw new SettingNotFoundException("Admin data is null");
    }

    public final List getBooleanList(ContentValues contentValues, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = getCursor(str, new String[]{str2}, contentValues);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    try {
                        if (!cursor.isNull(0)) {
                            arrayList.add(Boolean.valueOf(cursor.getInt(0) == 1));
                        }
                    } catch (SQLException e) {
                        Log.e("EdmStorageProvider", "Exception occurred accessing Enterprise db " + e.getMessage());
                    }
                } finally {
                    cursor.close();
                }
            }
        }
        return arrayList;
    }

    public final ArrayList getBooleanListAsUser(int i, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(getAdminLUIDWhereIn(0, i), "#SelectClause#");
        return new ArrayList(getBooleanList(contentValues, str, str2));
    }

    public final ComponentName getComponentNameForUid(int i) {
        String string = getString(i, 0, "ADMIN_INFO", "adminName");
        if (string == null) {
            return null;
        }
        return ComponentName.unflattenFromString(string);
    }

    public final Cursor getCursor(String str, String[] strArr, ContentValues contentValues) {
        StringBuilder sb = new StringBuilder();
        return this.mEdmDbHelper.getReadableDatabase().query(str, strArr, sb.length() > 0 ? sb.toString() : null, formatContentValues(contentValues, sb), null, null, null);
    }

    public final Cursor getCursorByAdmin(int i, int i2, String str, String[] strArr) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        return getCursor(str, strArr, contentValues);
    }

    public final String getDatabaseUpgradeValue(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", str);
        return getString(contentValues, "generic", "value");
    }

    public final String getGenericValueAsUser(int i, String str) {
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("name", str);
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(0, m, "containerID", i, "userID");
        return getString(m, "generic", "value");
    }

    public final int getInt(int i, int i2, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        ArrayList arrayList = (ArrayList) getIntList(contentValues, str, str2);
        if (arrayList.size() > 0) {
            return ((Integer) arrayList.get(0)).intValue();
        }
        throw new SettingNotFoundException("Admin data is null");
    }

    public final int getInt(ContentValues contentValues, String str, String str2) {
        ArrayList arrayList = (ArrayList) getIntList(contentValues, str, str2);
        if (arrayList.size() > 0) {
            return ((Integer) arrayList.get(0)).intValue();
        }
        return -1;
    }

    public final List getIntList(ContentValues contentValues, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = getCursor(str, new String[]{str2}, contentValues);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    try {
                        if (!cursor.isNull(0)) {
                            arrayList.add(Integer.valueOf(cursor.getInt(0)));
                        }
                    } catch (SQLException e) {
                        Log.e("EdmStorageProvider", "Exception occurred accessing Enterprise db " + e.getMessage());
                    }
                } finally {
                    cursor.close();
                }
            }
        }
        return arrayList;
    }

    public final ArrayList getIntListAsUser(int i, int i2, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(getAdminLUIDWhereIn(i, i2), "#SelectClause#");
        return new ArrayList(getIntList(contentValues, str, str2));
    }

    public final List getLongList(ContentValues contentValues, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = getCursor(str, new String[]{str2}, contentValues);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    try {
                        if (!cursor.isNull(0)) {
                            arrayList.add(Long.valueOf(cursor.getLong(0)));
                        }
                    } catch (SQLException e) {
                        Log.e("EdmStorageProvider", "Exception occurred accessing Enterprise db " + e.getMessage());
                    }
                } finally {
                    cursor.close();
                }
            }
        }
        return arrayList;
    }

    public final ArrayList getLongListAsUser(int i, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(getAdminLUIDWhereIn(0, i), "#SelectClause#");
        return new ArrayList(getLongList(contentValues, str, str2));
    }

    public final int getMUMContainerOwnerUid(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("containerID", Integer.valueOf(i));
        return getInt(contentValues, "MUMCONTAINER", "adminUid");
    }

    public final String getPackageNameForUid(int i) {
        ComponentName componentNameForUid = getComponentNameForUid(i);
        if (componentNameForUid == null) {
            return null;
        }
        return componentNameForUid.getPackageName();
    }

    public final String getString(int i, int i2, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        return getString(contentValues, str, str2);
    }

    public final String getString(ContentValues contentValues, String str, String str2) {
        ArrayList arrayList = (ArrayList) getStringList(contentValues, str, str2);
        if (arrayList.size() > 0) {
            return (String) arrayList.get(0);
        }
        return null;
    }

    public final List getStringList(ContentValues contentValues, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = getCursor(str, new String[]{str2}, contentValues);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    try {
                        if (!cursor.isNull(0)) {
                            arrayList.add(cursor.getString(0));
                        }
                    } catch (SQLException e) {
                        Log.e("EdmStorageProvider", "Exception occurred accessing Enterprise db " + e.getMessage());
                    }
                } finally {
                    cursor.close();
                }
            }
        }
        return arrayList;
    }

    public final List getStringListAsUser(int i, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(getAdminLUIDWhereIn(0, i), "#SelectClause#");
        return getStringList(contentValues, str, str2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x008b, code lost:
    
        if (r1 != null) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x008d, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ae, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ab, code lost:
    
        if (r1 == null) goto L37;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getValues(java.lang.String r5, java.lang.String[] r6, android.content.ContentValues r7) {
        /*
            r4 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            android.database.Cursor r1 = r4.getCursor(r5, r6, r7)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            if (r1 == 0) goto L8b
            r1.moveToFirst()     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            int r4 = r1.getCount()     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            if (r4 <= 0) goto L8b
        L15:
            android.content.ContentValues r4 = new android.content.ContentValues     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            r4.<init>()     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            r5 = 0
        L1b:
            int r6 = r1.getColumnCount()     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            if (r5 >= r6) goto L7c
            int r6 = r1.getType(r5)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            r7 = 3
            if (r6 != r7) goto L39
            java.lang.String r6 = r1.getColumnName(r5)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            java.lang.String r7 = r1.getString(r5)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            r4.put(r6, r7)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            goto L79
        L34:
            r4 = move-exception
            goto Laf
        L37:
            r4 = move-exception
            goto L91
        L39:
            int r6 = r1.getType(r5)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            r7 = 1
            if (r6 != r7) goto L50
            java.lang.String r6 = r1.getColumnName(r5)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            long r2 = r1.getLong(r5)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            java.lang.Long r7 = java.lang.Long.valueOf(r2)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            r4.put(r6, r7)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            goto L79
        L50:
            int r6 = r1.getType(r5)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            r7 = 2
            if (r6 != r7) goto L67
            java.lang.String r6 = r1.getColumnName(r5)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            float r7 = r1.getFloat(r5)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            java.lang.Float r7 = java.lang.Float.valueOf(r7)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            r4.put(r6, r7)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            goto L79
        L67:
            int r6 = r1.getType(r5)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            r7 = 4
            if (r6 != r7) goto L79
            java.lang.String r6 = r1.getColumnName(r5)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            byte[] r7 = r1.getBlob(r5)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            r4.put(r6, r7)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
        L79:
            int r5 = r5 + 1
            goto L1b
        L7c:
            int r5 = r4.size()     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            if (r5 <= 0) goto L85
            r0.add(r4)     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
        L85:
            boolean r4 = r1.moveToNext()     // Catch: java.lang.Throwable -> L34 android.database.SQLException -> L37
            if (r4 != 0) goto L15
        L8b:
            if (r1 == 0) goto Lae
        L8d:
            r1.close()
            goto Lae
        L91:
            java.lang.String r5 = "EdmStorageProvider"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L34
            r6.<init>()     // Catch: java.lang.Throwable -> L34
            java.lang.String r7 = "Exception occurred accessing Enterprise db "
            r6.append(r7)     // Catch: java.lang.Throwable -> L34
            java.lang.String r4 = r4.getMessage()     // Catch: java.lang.Throwable -> L34
            r6.append(r4)     // Catch: java.lang.Throwable -> L34
            java.lang.String r4 = r6.toString()     // Catch: java.lang.Throwable -> L34
            android.util.Log.e(r5, r4)     // Catch: java.lang.Throwable -> L34
            if (r1 == 0) goto Lae
            goto L8d
        Lae:
            return r0
        Laf:
            if (r1 == 0) goto Lb4
            r1.close()
        Lb4:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.storage.EdmStorageProviderBase.getValues(java.lang.String, java.lang.String[], android.content.ContentValues):java.util.List");
    }

    public final List getValuesListAsUser(int i, int i2, String str, String[] strArr) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(getAdminLUIDWhereIn(i, i2), "#SelectClause#");
        return getValues(str, strArr, contentValues);
    }

    public final long insert(String str, ContentValues contentValues) {
        try {
            return this.mEdmDbHelper.getWritableDatabase().insert(str, null, contentValues);
        } catch (SQLiteException e) {
            Log.e("EdmStorageProvider", "insert()", e);
            return -1L;
        }
    }

    public final boolean put(String str, ContentValues contentValues, ContentValues contentValues2) {
        StringBuilder sb = new StringBuilder();
        if (update(str, contentValues, sb.length() > 0 ? sb.toString() : null, formatContentValues(contentValues2, sb)) > 0) {
            return true;
        }
        contentValues.putAll(contentValues2);
        return insert(str, contentValues) != -1;
    }

    public final boolean putBoolean(String str, int i, boolean z, int i2, String str2) {
        ContentValues contentValues;
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(str2, Integer.valueOf(z ? 1 : 0));
        long translateToAdminLUID = translateToAdminLUID(i, i2);
        if (translateToAdminLUID != 0) {
            contentValues = new ContentValues();
            contentValues.put("adminUid", Long.valueOf(translateToAdminLUID));
        } else {
            contentValues = null;
        }
        return put(str, contentValues2, contentValues);
    }

    public final boolean putGenericValueAsUser(int i, String str, String str2) {
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("name", str);
        m.put("containerID", (Integer) 0);
        m.put("userID", Integer.valueOf(i));
        ContentValues contentValues = new ContentValues();
        contentValues.put("value", str2);
        return put("generic", contentValues, m);
    }

    public final boolean putInt(int i, int i2, int i3, String str, String str2) {
        ContentValues contentValues;
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(str2, Integer.valueOf(i3));
        long translateToAdminLUID = translateToAdminLUID(i, i2);
        if (translateToAdminLUID != 0) {
            contentValues = new ContentValues();
            contentValues.put("adminUid", Long.valueOf(translateToAdminLUID));
        } else {
            contentValues = null;
        }
        return put(str, contentValues2, contentValues);
    }

    public final boolean putString(int i, int i2, String str, String str2, String str3) {
        ContentValues contentValues;
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m(str2, str3);
        long translateToAdminLUID = translateToAdminLUID(i, i2);
        if (translateToAdminLUID != 0) {
            contentValues = new ContentValues();
            contentValues.put("adminUid", Long.valueOf(translateToAdminLUID));
        } else {
            contentValues = null;
        }
        return put(str, m, contentValues);
    }

    public final boolean putValues(int i, int i2, String str, ContentValues contentValues) {
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        convertAdminIdToLUID(contentValues);
        return put(str, contentValues, contentValues2);
    }

    public final void removeAdminFromDatabase(int i) {
        ContentValues contentValues = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(i, contentValues, "adminUid", 1, "canRemove");
        delete("ADMIN_INFO", contentValues);
    }

    public final boolean removeByAdmin(int i, int i2, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        return delete(str, contentValues) > 0;
    }

    public final int removeByFieldsAsUser(int i, String str, HashMap hashMap) {
        ContentValues contentValues = new ContentValues();
        if (hashMap.size() > 0) {
            for (Map.Entry entry : hashMap.entrySet()) {
                contentValues.put((String) entry.getKey(), (String) entry.getValue());
            }
        }
        contentValues.put(getAdminLUIDWhereIn(0, i), "#SelectClause#");
        return delete(str, contentValues);
    }

    public final void resetControlStateBits(int i) {
        SQLiteDatabase writableDatabase = this.mEdmDbHelper.getWritableDatabase();
        try {
            String str = "UPDATE APPLICATION SET controlState = (controlState & -15329266) WHERE adminUid = " + i;
            Log.d("EdmStorageProvider", "resetControlStateBits: query -> " + str);
            writableDatabase.execSQL(str);
        } catch (SQLiteException e) {
            Log.e("EdmStorageProvider", "resetControlStateBits()", e);
        }
    }

    public int update(String str, ContentValues contentValues, ContentValues contentValues2) {
        StringBuilder sb = new StringBuilder();
        return update(str, contentValues, sb.length() > 0 ? sb.toString() : null, formatContentValues(contentValues2, sb));
    }

    public final int update(String str, ContentValues contentValues, String str2, String[] strArr) {
        try {
            return this.mEdmDbHelper.getWritableDatabase().update(str, contentValues, str2, strArr);
        } catch (SQLiteException e) {
            Log.e("EdmStorageProvider", "update()", e);
            return -1;
        }
    }
}

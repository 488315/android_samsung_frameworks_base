package com.android.server.enterprise.storage;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import com.samsung.android.knox.SemPersonaManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class EdmStorageProviderBase {
    public EdmStorageHelper mEdmDbHelper;

    public static int getAdminUidFromLUID(long j) {
        return (int) j;
    }

    public static int getContainerIdFromLUID(long j) {
        return (int) (j >>> 32);
    }

    public EdmStorageProviderBase(Context context) {
        EdmStorageHelper edmStorageHelper = EdmStorageHelper.getInstance(context);
        this.mEdmDbHelper = edmStorageHelper;
        try {
            edmStorageHelper.getWritableDatabase().enableWriteAheadLogging();
        } catch (Exception unused) {
            Log.e("EdmStorageProvider", "Failed to OPEN/CREATE the database");
        }
    }

    public boolean getBoolean(String str, String str2, ContentValues contentValues) {
        List booleanList = getBooleanList(str, str2, contentValues);
        if (booleanList.size() > 0) {
            return ((Boolean) booleanList.get(0)).booleanValue();
        }
        throw new SettingNotFoundException("Admin data is null");
    }

    public List getBooleanList(String str, String str2, ContentValues contentValues) {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = getCursor(str, new String[]{str2}, contentValues, null);
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

    public int getInt(String str, String str2, ContentValues contentValues) {
        List intList = getIntList(str, str2, contentValues);
        if (intList.size() > 0) {
            return ((Integer) intList.get(0)).intValue();
        }
        return -1;
    }

    public List getIntList(String str, String str2, ContentValues contentValues) {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = getCursor(str, new String[]{str2}, contentValues, null);
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

    public long getLong(String str, String str2, ContentValues contentValues) {
        List longList = getLongList(str, str2, contentValues);
        if (longList.size() > 0) {
            return ((Long) longList.get(0)).longValue();
        }
        return -1L;
    }

    public List getLongList(String str, String str2, ContentValues contentValues) {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = getCursor(str, new String[]{str2}, contentValues, null);
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

    public boolean updateBlob(int i, int i2, String str, String str2, byte[] bArr) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(str2, bArr);
        return put(str, contentValues2, contentValues);
    }

    public byte[] getBlob(String str, String str2, ContentValues contentValues) {
        List blobList = getBlobList(str, str2, contentValues);
        if (blobList.size() > 0) {
            return (byte[]) blobList.get(0);
        }
        return null;
    }

    public List getBlobList(String str, String str2, ContentValues contentValues) {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = getCursor(str, new String[]{str2}, contentValues, null);
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

    public String getString(String str, String str2, ContentValues contentValues) {
        List stringList = getStringList(str, str2, contentValues);
        if (stringList.size() > 0) {
            return (String) stringList.get(0);
        }
        return null;
    }

    public List getStringList(String str, String str2, ContentValues contentValues) {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = getCursor(str, new String[]{str2}, contentValues, null);
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

    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ac, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00a9, code lost:
    
        if (r1 == null) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List getValues(String str, String[] strArr, ContentValues contentValues) {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            try {
                cursor = getCursor(str, strArr, contentValues, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    if (cursor.getCount() > 0) {
                        do {
                            ContentValues contentValues2 = new ContentValues();
                            for (int i = 0; i < cursor.getColumnCount(); i++) {
                                if (cursor.getType(i) == 3) {
                                    contentValues2.put(cursor.getColumnName(i), cursor.getString(i));
                                } else if (cursor.getType(i) == 1) {
                                    contentValues2.put(cursor.getColumnName(i), Long.valueOf(cursor.getLong(i)));
                                } else if (cursor.getType(i) == 2) {
                                    contentValues2.put(cursor.getColumnName(i), Float.valueOf(cursor.getFloat(i)));
                                } else if (cursor.getType(i) == 4) {
                                    contentValues2.put(cursor.getColumnName(i), cursor.getBlob(i));
                                }
                            }
                            if (contentValues2.size() > 0) {
                                arrayList.add(contentValues2);
                            }
                        } while (cursor.moveToNext());
                    }
                }
            } catch (SQLException e) {
                Log.e("EdmStorageProvider", "Exception occurred accessing Enterprise db " + e.getMessage());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Cursor getCursor(String str, String[] strArr, ContentValues contentValues, String str2) {
        StringBuilder sb = new StringBuilder();
        return getCursor(str, strArr, sb.length() > 0 ? sb.toString() : null, formatContentValues(contentValues, sb), str2);
    }

    public Cursor getCursor(String str, String[] strArr, String str2, String[] strArr2, String str3) {
        return this.mEdmDbHelper.getReadableDatabase().query(str, strArr, str2, strArr2, null, null, str3);
    }

    public int getCount(String str, ContentValues contentValues) {
        SQLiteDatabase writableDatabase = this.mEdmDbHelper.getWritableDatabase();
        if (contentValues == null || contentValues.size() <= 0) {
            Cursor rawQuery = writableDatabase.rawQuery("SELECT COUNT(*) from " + str, null);
            int i = -1;
            if (rawQuery != null) {
                try {
                    try {
                        rawQuery.moveToNext();
                        i = rawQuery.getInt(0);
                        Log.d("EdmStorageProvider", "getCount(" + str + ") - " + i);
                    } catch (SQLException e) {
                        Log.e("EdmStorageProvider", "Exception occurred accessing Enterprise db " + e.getMessage());
                    }
                } finally {
                    rawQuery.close();
                }
            }
            return i;
        }
        parseContentValues(contentValues);
        return getInt(str, "COUNT(*)", contentValues);
    }

    public void handleFirmwareUpgrade() {
        try {
            EdmStorageHelper edmStorageHelper = this.mEdmDbHelper;
            edmStorageHelper.doTablesCreationOrUpdate(edmStorageHelper.getWritableDatabase());
        } catch (Exception e) {
            Log.e("EdmStorageProvider", "handleUpgrade EX:", e);
        }
    }

    public int delete(String str, ContentValues contentValues) {
        StringBuilder sb = new StringBuilder();
        return delete(str, sb.length() > 0 ? sb.toString() : null, formatContentValues(contentValues, sb));
    }

    public int delete(String str, String str2, String[] strArr) {
        return this.mEdmDbHelper.getWritableDatabase().delete(str, str2, strArr);
    }

    public boolean put(String str, ContentValues contentValues, ContentValues contentValues2) {
        StringBuilder sb = new StringBuilder();
        if (update(str, contentValues, sb.length() > 0 ? sb.toString() : null, formatContentValues(contentValues2, sb)) > 0) {
            return true;
        }
        contentValues.putAll(contentValues2);
        return insert(str, contentValues) != -1;
    }

    public int update(String str, ContentValues contentValues, ContentValues contentValues2) {
        StringBuilder sb = new StringBuilder();
        return update(str, contentValues, sb.length() > 0 ? sb.toString() : null, formatContentValues(contentValues2, sb));
    }

    public int update(String str, ContentValues contentValues, String str2, String[] strArr) {
        try {
            return this.mEdmDbHelper.getWritableDatabase().update(str, contentValues, str2, strArr);
        } catch (SQLiteException e) {
            Log.e("EdmStorageProvider", "update()", e);
            return -1;
        }
    }

    public long insert(String str, ContentValues contentValues) {
        try {
            return this.mEdmDbHelper.getWritableDatabase().insert(str, null, contentValues);
        } catch (SQLiteException e) {
            Log.e("EdmStorageProvider", "insert()", e);
            return -1L;
        }
    }

    public final String[] formatContentValues(ContentValues contentValues, StringBuilder sb) {
        if (contentValues != null && sb != null && contentValues.size() > 0) {
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

    public static long translateToAdminLUID(int i, int i2) {
        if (i == 0 || i2 <= 0 || SemPersonaManager.isKnoxId(i2)) {
            return i;
        }
        return i | (i2 << 32);
    }

    public static String getAdminLUIDWhereIn(int i, int i2) {
        return "adminUid IN (" + getAdminLUIDWhereInArgs(i, i2) + ")";
    }

    public final void parseContentValues(ContentValues contentValues) {
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

    public final void convertAdminIdToLUID(ContentValues contentValues) {
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

    public static String getAdminLUIDWhereInArgs(int i, int i2) {
        if (i == -1) {
            throw new IllegalArgumentException("Invalid container id!!");
        }
        if (SemPersonaManager.isKnoxId(i)) {
            i = 0;
        }
        return new StringBuilder("SELECT adminUid FROM ADMIN WHERE containerID=" + i + " AND userID=" + i2).toString();
    }

    public final boolean addAdminLUID(int i, int i2) {
        if (i2 == 0 || i <= 0) {
            return true;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(translateToAdminLUID(i2, i)));
        contentValues.put("containerID", Integer.valueOf(i));
        contentValues.put("adminID", Integer.valueOf(i2));
        return insert("ADMIN", contentValues) != -1;
    }

    public boolean putString(int i, int i2, String str, String str2, String str3) {
        ContentValues contentValues;
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(str2, str3);
        long translateToAdminLUID = translateToAdminLUID(i, i2);
        if (translateToAdminLUID != 0) {
            contentValues = new ContentValues();
            contentValues.put("adminUid", Long.valueOf(translateToAdminLUID));
        } else {
            contentValues = null;
        }
        return put(str, contentValues2, contentValues);
    }

    public String getString(int i, int i2, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        return getString(str, str2, contentValues);
    }

    public List getStringListAsUser(int i, String str, String str2, int i2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(getAdminLUIDWhereIn(i, i2), "#SelectClause#");
        return getStringList(str, str2, contentValues);
    }

    public boolean putInt(int i, int i2, String str, String str2, int i3) {
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

    public boolean putLong(int i, int i2, String str, String str2, long j) {
        ContentValues contentValues;
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(str2, Long.valueOf(j));
        long translateToAdminLUID = translateToAdminLUID(i, i2);
        if (translateToAdminLUID != 0) {
            contentValues = new ContentValues();
            contentValues.put("adminUid", Long.valueOf(translateToAdminLUID));
        } else {
            contentValues = null;
        }
        return put(str, contentValues2, contentValues);
    }

    public int getInt(int i, int i2, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        List intList = getIntList(str, str2, contentValues);
        if (intList.size() > 0) {
            return ((Integer) intList.get(0)).intValue();
        }
        throw new SettingNotFoundException("Admin data is null");
    }

    public ArrayList getIntList(int i, String str, String str2) {
        return getIntListAsUser(i, str, str2, 0);
    }

    public ArrayList getIntListAsUser(int i, String str, String str2, int i2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(getAdminLUIDWhereIn(i, i2), "#SelectClause#");
        return new ArrayList(getIntList(str, str2, contentValues));
    }

    public boolean putBoolean(int i, int i2, String str, String str2, boolean z) {
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

    public boolean getBoolean(int i, int i2, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        List booleanList = getBooleanList(str, str2, contentValues);
        if (booleanList.size() > 0) {
            return ((Boolean) booleanList.get(0)).booleanValue();
        }
        throw new SettingNotFoundException("Admin data is null");
    }

    public ArrayList getBooleanListAsUser(int i, String str, String str2, int i2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(getAdminLUIDWhereIn(i, i2), "#SelectClause#");
        return new ArrayList(getBooleanList(str, str2, contentValues));
    }

    public ContentValues getValues(int i, int i2, String str, String[] strArr) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        List values = getValues(str, strArr, contentValues);
        if (!values.isEmpty()) {
            return (ContentValues) values.get(0);
        }
        return new ContentValues();
    }

    public List getValuesListAsUser(int i, String str, String[] strArr, int i2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(getAdminLUIDWhereIn(i, i2), "#SelectClause#");
        return getValues(str, strArr, contentValues);
    }

    public List getValuesList(String str, String[] strArr, ContentValues contentValues) {
        parseContentValues(contentValues);
        return getValues(str, strArr, contentValues);
    }

    public boolean putValuesNoUpdate(String str, ContentValues contentValues) {
        convertAdminIdToLUID(contentValues);
        return insert(str, contentValues) != -1;
    }

    public int insertValuesNoUpdate(String str, ContentValues contentValues) {
        convertAdminIdToLUID(contentValues);
        return (int) insert(str, contentValues);
    }

    public boolean putValues(int i, int i2, String str, ContentValues contentValues) {
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        convertAdminIdToLUID(contentValues);
        return put(str, contentValues, contentValues2);
    }

    public boolean putValues(String str, ContentValues contentValues, ContentValues contentValues2) {
        if (contentValues == null || contentValues2 == null) {
            return false;
        }
        convertAdminIdToLUID(contentValues);
        convertAdminIdToLUID(contentValues2);
        return (contentValues2.size() > 0 && update(str, contentValues, contentValues2) > 0) || insert(str, contentValues) != -1;
    }

    public boolean putValues(String str, ContentValues contentValues) {
        return update(str, contentValues, null) > 0 || insert(str, contentValues) != -1;
    }

    public boolean addAdmin(int i, String str, boolean z, int i2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        contentValues.put("adminName", str);
        contentValues.put("canRemove", Boolean.valueOf(z));
        if (insert("ADMIN_INFO", contentValues) == -1) {
            return false;
        }
        addAdminLUID(i2, i);
        return true;
    }

    public boolean addorUpdateAdmin(int i, String str, boolean z, int i2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminName", str);
        contentValues.put("canRemove", Boolean.valueOf(z));
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Integer.valueOf(i));
        if (update("ADMIN_INFO", contentValues, contentValues2) > 0) {
            return true;
        }
        contentValues.putAll(contentValues2);
        if (insert("ADMIN_INFO", contentValues) == -1) {
            return false;
        }
        addAdminLUID(i2, i);
        return true;
    }

    public boolean removeAdminFromDatabase(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        contentValues.put("canRemove", (Integer) 1);
        return delete("ADMIN_INFO", contentValues) > 0;
    }

    public boolean addMUMContainer(int i, int i2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("containerID", Integer.valueOf(i));
        contentValues.put("adminUid", Integer.valueOf(i2));
        return insert("MUMCONTAINER", contentValues) != -1;
    }

    public boolean updateApplicationTable(ContentValues contentValues, String str) {
        Log.d("EdmStorageProvider", "Updating application table  ");
        boolean z = update("APPLICATION", contentValues, str, null) > 0;
        Log.d("EdmStorageProvider", "Return value  " + z);
        return z;
    }

    public boolean removeMUMContainer(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("containerID", Integer.valueOf(i));
        return delete("MUMCONTAINER", contentValues) > 0;
    }

    public int getMUMContainerOwnerUid(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("containerID", Integer.valueOf(i));
        return getInt("MUMCONTAINER", "adminUid", contentValues);
    }

    public int getAdminByField(String str, String str2, String str3) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(str2, str3);
        return getInt(str, "adminUid", contentValues);
    }

    public boolean putGenericValueAsUser(int i, String str, String str2, int i2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", str);
        contentValues.put("containerID", Integer.valueOf(i));
        contentValues.put("userID", Integer.valueOf(i2));
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("value", str2);
        return put("generic", contentValues2, contentValues);
    }

    public String getGenericValueAsUser(int i, String str, int i2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", str);
        contentValues.put("containerID", Integer.valueOf(i));
        contentValues.put("userID", Integer.valueOf(i2));
        return getString("generic", "value", contentValues);
    }

    public ArrayList getDataByFields(String str, String[] strArr, String[] strArr2, String[] strArr3) {
        ContentValues contentValues;
        if (strArr == null || strArr.length <= 0 || strArr2 == null || strArr2.length <= 0) {
            contentValues = null;
        } else {
            contentValues = new ContentValues();
            for (int i = 0; i < strArr.length; i++) {
                contentValues.put(strArr[i], strArr2[i]);
            }
        }
        return new ArrayList(getValues(str, strArr3, contentValues));
    }

    public boolean putDataByFields(String str, String[] strArr, String[] strArr2, ContentValues contentValues) {
        ContentValues contentValues2;
        if (strArr == null || strArr.length <= 0 || strArr2 == null || strArr2.length <= 0) {
            contentValues2 = null;
        } else {
            contentValues2 = new ContentValues();
            for (int i = 0; i < strArr.length; i++) {
                contentValues2.put(strArr[i], strArr2[i]);
            }
        }
        if (contentValues2 == null) {
            return insert(str, contentValues) != -1;
        }
        return put(str, contentValues, contentValues2);
    }

    public boolean deleteDataByFields(String str, String[] strArr, String[] strArr2) {
        ContentValues contentValues;
        if (strArr == null || strArr.length <= 0 || strArr2 == null || strArr2.length <= 0) {
            contentValues = null;
        } else {
            contentValues = new ContentValues();
            for (int i = 0; i < strArr.length; i++) {
                contentValues.put(strArr[i], strArr2[i]);
            }
        }
        return delete(str, contentValues) > 0;
    }

    public int getIntByAdminAndField(String str, int i, int i2, String str2, String str3, String str4) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        contentValues.put(str2, str3);
        return getInt(str, str4, contentValues);
    }

    public Cursor getCursorByAdminAndField(String str, int i, int i2, String str2, String str3, String[] strArr) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        if (str3 != null) {
            contentValues.put(str2, str3);
        }
        return getCursor(str, strArr, contentValues, null);
    }

    public Cursor getCursorByAdmin(String str, int i, int i2, String[] strArr) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        return getCursor(str, strArr, contentValues, null);
    }

    public boolean putValuesForAdminAndField(String str, int i, int i2, String str2, String str3, ContentValues contentValues) {
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        contentValues2.put(str2, str3);
        convertAdminIdToLUID(contentValues);
        return put(str, contentValues, contentValues2);
    }

    public boolean removeByAdminAndField(String str, int i, int i2, String str2, String str3) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        contentValues.put(str2, str3);
        return delete(str, contentValues) > 0;
    }

    public boolean removeByAdmin(String str, int i, int i2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(translateToAdminLUID(i, i2)));
        return delete(str, contentValues) > 0;
    }

    public int removeByFieldsAsUser(String str, int i, HashMap hashMap, int i2) {
        ContentValues contentValues = new ContentValues();
        if (hashMap != null && hashMap.size() > 0) {
            for (Map.Entry entry : hashMap.entrySet()) {
                contentValues.put((String) entry.getKey(), (String) entry.getValue());
            }
        }
        contentValues.put(getAdminLUIDWhereIn(i, i2), "#SelectClause#");
        return delete(str, contentValues);
    }

    public ArrayList getLongListAsUser(int i, String str, String str2, int i2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(getAdminLUIDWhereIn(i, i2), "#SelectClause#");
        return new ArrayList(getLongList(str, str2, contentValues));
    }

    public String getString(int i, String str, String str2, String str3, String str4) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(getAdminLUIDWhereIn(i, 0), "#SelectClause#");
        contentValues.put(str2, str3);
        return getString(str, str4, contentValues);
    }

    public boolean remove(String str) {
        return delete(str, null) > 0;
    }

    public void insertConfiguration(String str, ContentValues contentValues) {
        convertAdminIdToLUID(contentValues);
        if (insert(str, contentValues) != -1) {
            return;
        }
        Log.d("EdmStorageProvider", "insertConfiguration was failed");
    }

    public boolean removeByFilterSmallerThan(String str, ContentValues contentValues) {
        return contentValues == null ? this.mEdmDbHelper.getWritableDatabase().delete(str, "1", null) >= 0 : delete(str, contentValues) > 0;
    }

    public byte[] getBlob(String str, String str2, String str3, String str4) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(str2, str3);
        return getBlob(str, str4, contentValues);
    }

    public ContentValues getValue(String str, String str2, ContentValues contentValues) {
        List values = getValues(str, new String[]{str2}, contentValues);
        if (values.isEmpty()) {
            return null;
        }
        return (ContentValues) values.get(0);
    }

    public ComponentName getComponentNameForUid(int i) {
        String string = getString(i, 0, "ADMIN_INFO", "adminName");
        if (string == null) {
            return null;
        }
        return ComponentName.unflattenFromString(string);
    }

    public String getPackageNameForUid(int i) {
        ComponentName componentNameForUid = getComponentNameForUid(i);
        if (componentNameForUid == null) {
            return null;
        }
        return componentNameForUid.getPackageName();
    }

    public ArrayList getAdminUidList() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid!=?", (Integer) 0);
        return new ArrayList(getIntList("ADMIN_INFO", "adminUid", contentValues));
    }

    public ArrayList getAdminUidListAsUser(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid!=?", (Integer) 0);
        contentValues.put("userID", Integer.valueOf(i));
        return new ArrayList(getIntList("ADMIN", "adminUid", contentValues));
    }

    public ArrayList getAdminLUidList() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid!=?", (Integer) 0);
        return new ArrayList(getLongList("ADMIN", "adminUid", contentValues));
    }

    public ArrayList getAdminLUidListAsUser(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid!=?", (Integer) 0);
        contentValues.put("userID", Integer.valueOf(i));
        return new ArrayList(getLongList("ADMIN", "adminUid", contentValues));
    }

    public boolean canRemoveAdmin(int i) {
        try {
            return getBoolean(i, 0, "ADMIN_INFO", "canRemove");
        } catch (Exception unused) {
            Log.e("EdmStorageProvider", "Admin not in database, something went wrong");
            return true;
        }
    }

    public boolean setDatabaseUpgradeValue(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", str);
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("value", str2);
        return put("generic", contentValues2, contentValues);
    }

    public String getDatabaseUpgradeValue(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", str);
        return getString("generic", "value", contentValues);
    }

    public boolean addorUpdateAdminWithPseudo(int i, String str, boolean z, int i2, boolean z2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminName", str);
        contentValues.put("canRemove", Boolean.valueOf(z));
        contentValues.put("isPseudoAdmin", Boolean.valueOf(z2));
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Integer.valueOf(i));
        if (update("ADMIN_INFO", contentValues, contentValues2) > 0) {
            return true;
        }
        contentValues.putAll(contentValues2);
        if (insert("ADMIN_INFO", contentValues) == -1) {
            return false;
        }
        addAdminLUID(i2, i);
        return true;
    }

    public boolean checkPseudoAdminForUid(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        return getBoolean("ADMIN_INFO", "isPseudoAdmin", contentValues);
    }

    public int getPseudoAdminUid() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("isPseudoAdmin", Boolean.TRUE);
        return getInt("ADMIN_INFO", "adminUid", contentValues);
    }

    public void resetControlStateBits(int i, int i2) {
        SQLiteDatabase writableDatabase = this.mEdmDbHelper.getWritableDatabase();
        try {
            String str = "UPDATE APPLICATION SET controlState = (controlState & " + i2 + ") WHERE adminUid = " + i;
            Log.d("EdmStorageProvider", "resetControlStateBits: query -> " + str);
            writableDatabase.execSQL(str);
        } catch (SQLiteException e) {
            Log.e("EdmStorageProvider", "resetControlStateBits()", e);
        }
    }
}

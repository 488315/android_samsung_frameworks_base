package com.android.server.enterprise.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class EdmStorageProvider extends EdmStorageProviderBase {
    public final boolean deleteDataByFields(String str, String[] strArr, String[] strArr2) {
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

    public final int getAdminByField(String str, String str2, String str3) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(str2, str3);
        return getInt(contentValues, str, "adminUid");
    }

    public final ArrayList getAdminLUidListAsUser(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid!=?", (Integer) 0);
        contentValues.put("userID", Integer.valueOf(i));
        return new ArrayList(getLongList(contentValues, "ADMIN", "adminUid"));
    }

    public final byte[] getBlob(int i, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(
                "adminUid", Long.valueOf(EdmStorageProviderBase.translateToAdminLUID(i, 0)));
        return getBlob(contentValues, str, str2);
    }

    public final int getCount(String str, ContentValues contentValues) {
        SQLiteDatabase writableDatabase = this.mEdmDbHelper.getWritableDatabase();
        if (contentValues != null && contentValues.size() > 0) {
            EdmStorageProviderBase.parseContentValues(contentValues);
            return getInt(contentValues, str, "COUNT(*)");
        }
        Cursor rawQuery = writableDatabase.rawQuery("SELECT COUNT(*) from ".concat(str), null);
        int i = -1;
        if (rawQuery != null) {
            try {
                try {
                    rawQuery.moveToNext();
                    i = rawQuery.getInt(0);
                    Log.d("EdmStorageProvider", "getCount(" + str + ") - " + i);
                } catch (SQLException e) {
                    Log.e(
                            "EdmStorageProvider",
                            "Exception occurred accessing Enterprise db " + e.getMessage());
                }
            } finally {
                rawQuery.close();
            }
        }
        return i;
    }

    public final ArrayList getDataByFields(
            String str, String[] strArr, String[] strArr2, String[] strArr3) {
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

    public final int getIntByAdminAndField(
            int i, String str, String str2, String str3, String str4) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(
                "adminUid", Long.valueOf(EdmStorageProviderBase.translateToAdminLUID(i, 0)));
        contentValues.put(str2, str3);
        return getInt(contentValues, str, str4);
    }

    public final String getString(String str, String str2, String str3, String str4) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EdmStorageProviderBase.getAdminLUIDWhereIn(0, 0), "#SelectClause#");
        contentValues.put(str2, str3);
        return getString(contentValues, str, str4);
    }

    public final ContentValues getValue(ContentValues contentValues, String str, String str2) {
        ArrayList arrayList = (ArrayList) getValues(str, new String[] {str2}, contentValues);
        if (arrayList.isEmpty()) {
            return null;
        }
        return (ContentValues) arrayList.get(0);
    }

    public final List getValuesList(String str, String[] strArr, ContentValues contentValues) {
        EdmStorageProviderBase.parseContentValues(contentValues);
        return getValues(str, strArr, contentValues);
    }

    public final void insertConfiguration(String str, ContentValues contentValues) {
        EdmStorageProviderBase.convertAdminIdToLUID(contentValues);
        if (insert(str, contentValues) != -1) {
            return;
        }
        Log.d("EdmStorageProvider", "insertConfiguration was failed");
    }

    public final boolean putDataByFields(
            String str, String[] strArr, String[] strArr2, ContentValues contentValues) {
        ContentValues contentValues2;
        if (strArr == null || strArr.length <= 0 || strArr2 == null || strArr2.length <= 0) {
            contentValues2 = null;
        } else {
            contentValues2 = new ContentValues();
            for (int i = 0; i < strArr.length; i++) {
                contentValues2.put(strArr[i], strArr2[i]);
            }
        }
        return contentValues2 == null
                ? insert(str, contentValues) != -1
                : put(str, contentValues, contentValues2);
    }

    public final boolean putLong(int i, String str, long j, String str2) {
        ContentValues contentValues;
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(str2, Long.valueOf(j));
        long translateToAdminLUID = EdmStorageProviderBase.translateToAdminLUID(i, 0);
        if (translateToAdminLUID != 0) {
            contentValues = new ContentValues();
            contentValues.put("adminUid", Long.valueOf(translateToAdminLUID));
        } else {
            contentValues = null;
        }
        return put(str, contentValues2, contentValues);
    }

    public final boolean putValues(String str, ContentValues contentValues) {
        return super.update(str, contentValues, null) > 0 || insert(str, contentValues) != -1;
    }

    public final boolean putValues(
            String str, ContentValues contentValues, ContentValues contentValues2) {
        EdmStorageProviderBase.convertAdminIdToLUID(contentValues);
        EdmStorageProviderBase.convertAdminIdToLUID(contentValues2);
        return (contentValues2.size() > 0 && super.update(str, contentValues, contentValues2) > 0)
                || insert(str, contentValues) != -1;
    }

    public final boolean putValuesForAdminAndField(
            int i, ContentValues contentValues, String str, String str2, String str3) {
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(
                "adminUid", Long.valueOf(EdmStorageProviderBase.translateToAdminLUID(i, 0)));
        contentValues2.put(str2, str3);
        EdmStorageProviderBase.convertAdminIdToLUID(contentValues);
        return put(str, contentValues, contentValues2);
    }

    public final boolean putValuesNoUpdate(String str, ContentValues contentValues) {
        EdmStorageProviderBase.convertAdminIdToLUID(contentValues);
        return insert(str, contentValues) != -1;
    }

    public final boolean removeByAdminAndField(int i, String str, String str2, String str3) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(
                "adminUid", Long.valueOf(EdmStorageProviderBase.translateToAdminLUID(i, 0)));
        contentValues.put(str2, str3);
        return delete(str, contentValues) > 0;
    }

    public final boolean updateBlob(int i, String str, byte[] bArr) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(
                "adminUid", Long.valueOf(EdmStorageProviderBase.translateToAdminLUID(i, 0)));
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(str, bArr);
        return put("KNOX_CUSTOM", contentValues2, contentValues);
    }
}

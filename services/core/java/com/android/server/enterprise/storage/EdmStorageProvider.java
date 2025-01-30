package com.android.server.enterprise.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class EdmStorageProvider extends EdmStorageProviderBase {
    public EdmStorageProvider(Context context) {
        super(context);
    }

    public boolean putString(int i, String str, String str2, String str3) {
        return super.putString(i, 0, str, str2, str3);
    }

    public String getString(int i, String str, String str2) {
        return super.getString(i, 0, str, str2);
    }

    public int removeByFields(String str, HashMap hashMap) {
        return removeByFieldsAsUser(str, 0, hashMap, 0);
    }

    public int removeByFieldsAsUser(String str, HashMap hashMap, int i) {
        return removeByFieldsAsUser(str, 0, hashMap, i);
    }

    public List getStringList(String str, String str2) {
        return getStringListAsUser(0, str, str2, 0);
    }

    public List getStringListAsUser(String str, String str2, int i) {
        return getStringListAsUser(0, str, str2, i);
    }

    @Override // com.android.server.enterprise.storage.EdmStorageProviderBase
    public ArrayList getAdminLUidListAsUser(int i) {
        return super.getAdminLUidListAsUser(i);
    }

    public boolean putInt(int i, String str, String str2, int i2) {
        return super.putInt(i, 0, str, str2, i2);
    }

    public boolean putLong(int i, String str, String str2, long j) {
        return super.putLong(i, 0, str, str2, j);
    }

    public int getInt(int i, String str, String str2) {
        return super.getInt(i, 0, str, str2);
    }

    public ArrayList getIntList(String str, String str2) {
        return getIntListAsUser(0, str, str2, 0);
    }

    public ArrayList getIntListAsUser(String str, String str2, int i) {
        return getIntListAsUser(0, str, str2, i);
    }

    public boolean putBoolean(int i, String str, String str2, boolean z) {
        return super.putBoolean(i, 0, str, str2, z);
    }

    public boolean getBoolean(int i, String str, String str2) {
        return super.getBoolean(i, 0, str, str2);
    }

    public ArrayList getBooleanList(String str, String str2) {
        return getBooleanListAsUser(0, str, str2, 0);
    }

    public ArrayList getBooleanListAsUser(String str, String str2, int i) {
        return getBooleanListAsUser(0, str, str2, i);
    }

    public ContentValues getValues(int i, String str, String[] strArr) {
        return super.getValues(i, 0, str, strArr);
    }

    public List getValuesList(String str, String[] strArr) {
        return getValuesListAsUser(0, str, strArr, 0);
    }

    public List getValuesListAsUser(String str, String[] strArr, int i) {
        return getValuesListAsUser(0, str, strArr, i);
    }

    @Override // com.android.server.enterprise.storage.EdmStorageProviderBase
    public List getValuesList(String str, String[] strArr, ContentValues contentValues) {
        return super.getValuesList(str, strArr, contentValues);
    }

    @Override // com.android.server.enterprise.storage.EdmStorageProviderBase
    public boolean putValuesNoUpdate(String str, ContentValues contentValues) {
        return super.putValuesNoUpdate(str, contentValues);
    }

    @Override // com.android.server.enterprise.storage.EdmStorageProviderBase
    public int insertValuesNoUpdate(String str, ContentValues contentValues) {
        return super.insertValuesNoUpdate(str, contentValues);
    }

    public boolean putValues(int i, String str, ContentValues contentValues) {
        return super.putValues(i, 0, str, contentValues);
    }

    @Override // com.android.server.enterprise.storage.EdmStorageProviderBase
    public boolean putValues(String str, ContentValues contentValues, ContentValues contentValues2) {
        return super.putValues(str, contentValues, contentValues2);
    }

    @Override // com.android.server.enterprise.storage.EdmStorageProviderBase
    public boolean putValues(String str, ContentValues contentValues) {
        return super.putValues(str, contentValues);
    }

    @Override // com.android.server.enterprise.storage.EdmStorageProviderBase
    public int getAdminByField(String str, String str2, String str3) {
        return super.getAdminByField(str, str2, str3);
    }

    public boolean putGenericValue(String str, String str2) {
        return putGenericValueAsUser(0, str, str2, 0);
    }

    public boolean putGenericValueAsUser(String str, String str2, int i) {
        return putGenericValueAsUser(0, str, str2, i);
    }

    public String getGenericValue(String str) {
        return getGenericValueAsUser(0, str, 0);
    }

    public String getGenericValueAsUser(String str, int i) {
        return super.getGenericValueAsUser(0, str, i);
    }

    @Override // com.android.server.enterprise.storage.EdmStorageProviderBase
    public ArrayList getDataByFields(String str, String[] strArr, String[] strArr2, String[] strArr3) {
        return super.getDataByFields(str, strArr, strArr2, strArr3);
    }

    @Override // com.android.server.enterprise.storage.EdmStorageProviderBase
    public boolean putDataByFields(String str, String[] strArr, String[] strArr2, ContentValues contentValues) {
        return super.putDataByFields(str, strArr, strArr2, contentValues);
    }

    @Override // com.android.server.enterprise.storage.EdmStorageProviderBase
    public boolean deleteDataByFields(String str, String[] strArr, String[] strArr2) {
        return super.deleteDataByFields(str, strArr, strArr2);
    }

    public int getIntByAdminAndField(String str, int i, String str2, String str3, String str4) {
        return super.getIntByAdminAndField(str, i, 0, str2, str3, str4);
    }

    public Cursor getCursorByAdminAndField(String str, int i, String str2, String str3, String[] strArr) {
        return super.getCursorByAdminAndField(str, i, 0, str2, str3, strArr);
    }

    public Cursor getCursorByAdmin(String str, int i, String[] strArr) {
        return super.getCursorByAdmin(str, i, 0, strArr);
    }

    public boolean putValuesForAdminAndField(String str, int i, String str2, String str3, ContentValues contentValues) {
        return super.putValuesForAdminAndField(str, i, 0, str2, str3, contentValues);
    }

    public boolean removeByAdminAndField(String str, int i, String str2, String str3) {
        return super.removeByAdminAndField(str, i, 0, str2, str3);
    }

    public boolean removeByAdmin(String str, int i) {
        return super.removeByAdmin(str, i, 0);
    }

    public ArrayList getLongList(String str, String str2) {
        return getLongListAsUser(0, str, str2, 0);
    }

    public ArrayList getLongListAsUser(String str, String str2, int i) {
        return getLongListAsUser(0, str, str2, i);
    }

    public String getString(String str, String str2, String str3, String str4) {
        return super.getString(0, str, str2, str3, str4);
    }

    @Override // com.android.server.enterprise.storage.EdmStorageProviderBase
    public boolean remove(String str) {
        return super.remove(str);
    }

    @Override // com.android.server.enterprise.storage.EdmStorageProviderBase
    public void insertConfiguration(String str, ContentValues contentValues) {
        super.insertConfiguration(str, contentValues);
    }

    @Override // com.android.server.enterprise.storage.EdmStorageProviderBase
    public int update(String str, ContentValues contentValues, ContentValues contentValues2) {
        return super.update(str, contentValues, contentValues2);
    }

    @Override // com.android.server.enterprise.storage.EdmStorageProviderBase
    public int getCount(String str, ContentValues contentValues) {
        return super.getCount(str, contentValues);
    }

    @Override // com.android.server.enterprise.storage.EdmStorageProviderBase
    public byte[] getBlob(String str, String str2, String str3, String str4) {
        return super.getBlob(str, str2, str3, str4);
    }

    public byte[] getBlob(int i, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Long.valueOf(EdmStorageProviderBase.translateToAdminLUID(i, 0)));
        return super.getBlob(str, str2, contentValues);
    }

    public boolean updateBlob(int i, String str, String str2, byte[] bArr) {
        return super.updateBlob(i, 0, str, str2, bArr);
    }

    @Override // com.android.server.enterprise.storage.EdmStorageProviderBase
    public ContentValues getValue(String str, String str2, ContentValues contentValues) {
        return super.getValue(str, str2, contentValues);
    }
}

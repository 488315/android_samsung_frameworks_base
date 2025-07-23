package com.android.internal.content;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.TextUtils;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class SelectionBuilder {
    private StringBuilder mSelection = new StringBuilder();
    private ArrayList<String> mSelectionArgs = new ArrayList<>();

    public SelectionBuilder reset() {
        this.mSelection.setLength(0);
        this.mSelectionArgs.clear();
        return this;
    }

    public SelectionBuilder append(String str, Object... objArr) {
        if (TextUtils.isEmpty(str)) {
            if (objArr != null && objArr.length > 0) {
                throw new IllegalArgumentException("Valid selection required when including arguments");
            }
        } else {
            if (this.mSelection.length() > 0) {
                this.mSelection.append(" AND ");
            }
            StringBuilder sb = this.mSelection;
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(str);
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
            if (objArr != null) {
                for (Object obj : objArr) {
                    this.mSelectionArgs.add(String.valueOf(obj));
                }
            }
        }
        return this;
    }

    public String getSelection() {
        return this.mSelection.toString();
    }

    public String[] getSelectionArgs() {
        ArrayList<String> arrayList = this.mSelectionArgs;
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public Cursor query(SQLiteDatabase sQLiteDatabase, String str, String[] strArr, String str2) {
        return query(sQLiteDatabase, str, strArr, null, null, str2, null);
    }

    public Cursor query(SQLiteDatabase sQLiteDatabase, String str, String[] strArr, String str2, String str3, String str4, String str5) {
        return sQLiteDatabase.query(str, strArr, getSelection(), getSelectionArgs(), str2, str3, str4, str5);
    }

    public int update(SQLiteDatabase sQLiteDatabase, String str, ContentValues contentValues) {
        return sQLiteDatabase.update(str, contentValues, getSelection(), getSelectionArgs());
    }

    public int delete(SQLiteDatabase sQLiteDatabase, String str) {
        return sQLiteDatabase.delete(str, getSelection(), getSelectionArgs());
    }
}

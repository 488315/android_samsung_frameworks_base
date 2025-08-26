package com.samsung.context.sdk.samsunganalytics;

import android.database.sqlite.SQLiteDatabase;

/* loaded from: classes4.dex */
public interface DBOpenHelper {
    SQLiteDatabase getReadableDatabase();

    SQLiteDatabase getWritableDatabase();
}

package com.android.systemui.highlight.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class HighlightDbHelper extends SQLiteOpenHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Map mTables;

    public HighlightDbHelper(Context context, String str, int i) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, i);
        HashMap hashMap = new HashMap();
        this.mTables = hashMap;
        init(hashMap);
    }

    public abstract void init(Map map);

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        Log.d("HighlightDbHelper", "onCreate() " + getDatabaseName());
        ((HashMap) this.mTables).forEach(new HighlightDbHelper$$ExternalSyntheticLambda0(sQLiteDatabase, 1));
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        Log.d("HighlightDbHelper", "onUpgrade()" + getDatabaseName());
        ((HashMap) this.mTables).forEach(new HighlightDbHelper$$ExternalSyntheticLambda0(sQLiteDatabase, 0));
        onCreate(sQLiteDatabase);
    }
}

package com.android.systemui.highlight.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public abstract class HighlightDbHelper extends SQLiteOpenHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Map mTables;

    public HighlightDbHelper(Context context, String str, int i) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, i);
        HashMap map = new HashMap();
        this.mTables = map;
        init(map);
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

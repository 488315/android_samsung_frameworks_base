package com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.view.menu.SubMenuBuilder$$ExternalSyntheticOutline0;
import com.samsung.context.sdk.samsunganalytics.DBOpenHelper;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes.dex */
public final class DbManager {
    private DBOpenHelper dbOpenHelper;
    private Queue<SimpleLog> list;

    public DbManager(Context context) {
        this(new DefaultDBOpenHelper(context));
    }

    private Queue<SimpleLog> select(String str) {
        ((LinkedBlockingQueue) this.list).clear();
        Cursor rawQuery = ((DefaultDBOpenHelper) this.dbOpenHelper).getReadableDatabase().rawQuery(str, null);
        while (rawQuery.moveToNext()) {
            SimpleLog simpleLog = new SimpleLog();
            simpleLog.setId(rawQuery.getString(rawQuery.getColumnIndex("_id")));
            simpleLog.setData(rawQuery.getString(rawQuery.getColumnIndex("data")));
            simpleLog.setTimestamp(rawQuery.getLong(rawQuery.getColumnIndex("timestamp")));
            String string = rawQuery.getString(rawQuery.getColumnIndex("logtype"));
            LogType logType = LogType.DEVICE;
            if (!string.equals(logType.getAbbrev())) {
                logType = LogType.UIX;
            }
            simpleLog.setType(logType);
            this.list.add(simpleLog);
        }
        rawQuery.close();
        return this.list;
    }

    public final void delete(long j) {
        ((DefaultDBOpenHelper) this.dbOpenHelper).getWritableDatabase().delete("logs_v2", "timestamp <= " + j, null);
    }

    public final void insert(SimpleLog simpleLog) {
        SQLiteDatabase writableDatabase = ((DefaultDBOpenHelper) this.dbOpenHelper).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("timestamp", Long.valueOf(simpleLog.getTimestamp()));
        contentValues.put("data", simpleLog.getData());
        contentValues.put("logtype", simpleLog.getType().getAbbrev());
        writableDatabase.insert("logs_v2", null, contentValues);
    }

    public final Queue<SimpleLog> selectAll() {
        return select("select * from logs_v2");
    }

    public final Queue<SimpleLog> selectSome(int i) {
        return select(SubMenuBuilder$$ExternalSyntheticOutline0.m0m("select * from logs_v2 LIMIT ", i));
    }

    public DbManager(DefaultDBOpenHelper defaultDBOpenHelper) {
        this.list = new LinkedBlockingQueue();
        this.dbOpenHelper = defaultDBOpenHelper;
        defaultDBOpenHelper.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS logs_v2 (_id INTEGER PRIMARY KEY AUTOINCREMENT, timestamp INTEGER, logtype TEXT, data TEXT)");
        delete(5L);
    }

    public final void delete(List<String> list) {
        SQLiteDatabase writableDatabase = ((DefaultDBOpenHelper) this.dbOpenHelper).getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            try {
                ArrayList arrayList = (ArrayList) list;
                int size = arrayList.size();
                int i = 0;
                while (size > 0) {
                    int i2 = 900;
                    if (size < 900) {
                        i2 = size;
                    }
                    int i3 = i + i2;
                    List subList = arrayList.subList(i, i3);
                    writableDatabase.delete("logs_v2", ("_id IN(" + new String(new char[subList.size() - 1]).replaceAll("\u0000", "?,")) + "?)", (String[]) subList.toArray(new String[0]));
                    size -= i2;
                    i = i3;
                }
                arrayList.clear();
                writableDatabase.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            writableDatabase.endTransaction();
        }
    }
}

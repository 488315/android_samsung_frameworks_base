package com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.context.sdk.samsunganalytics.DBOpenHelper;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes4.dex */
public class DbManager {
    public final DBOpenHelper dbOpenHelper;
    public final Queue list;

    public DbManager(Context context) {
        this(new DefaultDBOpenHelper(context));
    }

    public final void insert(SimpleLog simpleLog) {
        SQLiteDatabase writableDatabase = this.dbOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PhoneRestrictionPolicy.TIMESTAMP, Long.valueOf(simpleLog.timestamp));
        contentValues.put("data", simpleLog.data);
        contentValues.put("logtype", simpleLog.type.getAbbrev());
        writableDatabase.insert("logs_v2", null, contentValues);
    }

    public final Queue select(String str) {
        ((LinkedBlockingQueue) this.list).clear();
        Cursor cursorRawQuery = this.dbOpenHelper.getReadableDatabase().rawQuery(str, null);
        while (cursorRawQuery.moveToNext()) {
            SimpleLog simpleLog = new SimpleLog();
            simpleLog._id = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("_id"));
            simpleLog.data = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("data"));
            simpleLog.timestamp = cursorRawQuery.getLong(cursorRawQuery.getColumnIndex(PhoneRestrictionPolicy.TIMESTAMP));
            String string = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("logtype"));
            LogType logType = LogType.DEVICE;
            if (!string.equals(logType.getAbbrev())) {
                logType = LogType.UIX;
            }
            simpleLog.type = logType;
            this.list.add(simpleLog);
        }
        cursorRawQuery.close();
        return this.list;
    }

    public DbManager(DBOpenHelper dBOpenHelper) throws SQLException {
        this.list = new LinkedBlockingQueue();
        if (dBOpenHelper != null) {
            this.dbOpenHelper = dBOpenHelper;
            dBOpenHelper.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS logs_v2 (_id INTEGER PRIMARY KEY AUTOINCREMENT, timestamp INTEGER, logtype TEXT, data TEXT)");
        }
        this.dbOpenHelper.getWritableDatabase().delete("logs_v2", "timestamp <= 5", null);
    }
}

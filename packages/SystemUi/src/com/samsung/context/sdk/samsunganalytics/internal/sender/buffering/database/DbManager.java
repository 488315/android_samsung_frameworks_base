package com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.context.sdk.samsunganalytics.DBOpenHelper;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DbManager {
    public final DBOpenHelper dbOpenHelper;
    public final Queue list;

    public DbManager(Context context) {
        this(new DefaultDBOpenHelper(context));
    }

    public final void delete(long j) {
        ((DefaultDBOpenHelper) this.dbOpenHelper).getWritableDatabase().delete("logs_v2", ValueAnimator$$ExternalSyntheticOutline0.m25m("timestamp <= ", j), null);
    }

    public final void insert(SimpleLog simpleLog) {
        SQLiteDatabase writableDatabase = ((DefaultDBOpenHelper) this.dbOpenHelper).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PhoneRestrictionPolicy.TIMESTAMP, Long.valueOf(simpleLog.timestamp));
        contentValues.put("data", simpleLog.data);
        contentValues.put("logtype", simpleLog.type.getAbbrev());
        writableDatabase.insert("logs_v2", null, contentValues);
    }

    public final Queue select(String str) {
        Queue queue = this.list;
        ((LinkedBlockingQueue) queue).clear();
        Cursor rawQuery = ((DefaultDBOpenHelper) this.dbOpenHelper).getReadableDatabase().rawQuery(str, null);
        while (rawQuery.moveToNext()) {
            SimpleLog simpleLog = new SimpleLog();
            simpleLog._id = rawQuery.getString(rawQuery.getColumnIndex("_id"));
            simpleLog.data = rawQuery.getString(rawQuery.getColumnIndex("data"));
            simpleLog.timestamp = rawQuery.getLong(rawQuery.getColumnIndex(PhoneRestrictionPolicy.TIMESTAMP));
            String string = rawQuery.getString(rawQuery.getColumnIndex("logtype"));
            LogType logType = LogType.DEVICE;
            if (!string.equals(logType.getAbbrev())) {
                logType = LogType.UIX;
            }
            simpleLog.type = logType;
            queue.add(simpleLog);
        }
        rawQuery.close();
        return queue;
    }

    public DbManager(DBOpenHelper dBOpenHelper) {
        this.list = new LinkedBlockingQueue();
        if (dBOpenHelper != null) {
            this.dbOpenHelper = dBOpenHelper;
            ((DefaultDBOpenHelper) dBOpenHelper).getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS logs_v2 (_id INTEGER PRIMARY KEY AUTOINCREMENT, timestamp INTEGER, logtype TEXT, data TEXT)");
        }
        delete(5L);
    }
}

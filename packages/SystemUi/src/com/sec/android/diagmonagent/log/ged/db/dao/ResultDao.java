package com.sec.android.diagmonagent.log.ged.db.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.sec.android.diagmonagent.log.ged.db.model.Event;
import com.sec.android.diagmonagent.log.ged.db.model.Result;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ResultDao {
    public final long MAX_KEEP_TIME = TimeUnit.DAYS.toMillis(30);

    /* renamed from: db */
    public final SQLiteDatabase f636db;

    public ResultDao(SQLiteDatabase sQLiteDatabase) {
        this.f636db = sQLiteDatabase;
    }

    public static Result makeResult(Event event) {
        Result result = new Result();
        result.serviceId = event.serviceId;
        result.eventId = event.eventId;
        result.clientStatusCode = event.status;
        result.timestamp = event.timestamp;
        return result;
    }

    public final void insert(Result result) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventId", result.eventId);
        contentValues.put("serviceId", result.serviceId);
        contentValues.put("clientStatusCode", Integer.valueOf(result.clientStatusCode));
        contentValues.put(PhoneRestrictionPolicy.TIMESTAMP, Long.valueOf(result.timestamp));
        this.f636db.insert("Result", null, contentValues);
    }
}

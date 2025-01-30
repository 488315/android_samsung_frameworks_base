package com.sec.android.diagmonagent.log.ged.p050db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.samsung.android.knox.p045ex.peripheral.PeripheralManager;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.ged.p050db.model.Event;
import com.sec.ims.IMSParameter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class EventDao {
    public final long MAX_KEEP_TIME = TimeUnit.DAYS.toMillis(30);

    /* renamed from: db */
    public final SQLiteDatabase f635db;

    public EventDao(SQLiteDatabase sQLiteDatabase) {
        this.f635db = sQLiteDatabase;
    }

    public final List getEvents(String str, String[] strArr) {
        ArrayList arrayList = new ArrayList();
        try {
            Cursor query = this.f635db.query("Event", null, str, strArr, null, null, null);
            try {
                if (query == null) {
                    AppLog.m269d("cursor is null");
                    if (query != null) {
                        query.close();
                    }
                    return arrayList;
                }
                while (query.moveToNext()) {
                    Event event = new Event();
                    event.f637id = query.getInt(query.getColumnIndexOrThrow("id"));
                    event.serviceId = query.getString(query.getColumnIndexOrThrow("serviceId"));
                    event.deviceId = query.getString(query.getColumnIndexOrThrow("deviceId"));
                    event.serviceVersion = query.getString(query.getColumnIndexOrThrow("serviceVersion"));
                    event.serviceAgreeType = query.getString(query.getColumnIndexOrThrow("serviceAgreeType"));
                    event.sdkVersion = query.getString(query.getColumnIndexOrThrow(PeripheralManager.Temp.EXTRA_SDK_VERSION));
                    event.sdkType = query.getString(query.getColumnIndexOrThrow("sdkType"));
                    event.serviceDefinedKey = query.getString(query.getColumnIndexOrThrow("serviceDefinedKey"));
                    event.errorCode = query.getString(query.getColumnIndexOrThrow("errorCode"));
                    event.logPath = query.getString(query.getColumnIndexOrThrow("logPath"));
                    event.description = query.getString(query.getColumnIndexOrThrow("description"));
                    event.relayClientVersion = query.getString(query.getColumnIndexOrThrow("relayClientVersion"));
                    event.relayClientType = query.getString(query.getColumnIndexOrThrow("relayClientType"));
                    event.extension = query.getString(query.getColumnIndexOrThrow("extension"));
                    boolean z = true;
                    if (query.getInt(query.getColumnIndexOrThrow("networkMode")) != 1) {
                        z = false;
                    }
                    event.networkMode = z;
                    event.memory = query.getString(query.getColumnIndexOrThrow("memory"));
                    event.storage = query.getString(query.getColumnIndexOrThrow("storage"));
                    event.status = query.getInt(query.getColumnIndexOrThrow(IMSParameter.CALL.STATUS));
                    event.retryCount = query.getInt(query.getColumnIndexOrThrow("retryCount"));
                    event.eventId = query.getString(query.getColumnIndexOrThrow("eventId"));
                    event.s3Path = query.getString(query.getColumnIndexOrThrow("s3Path"));
                    event.timestamp = query.getLong(query.getColumnIndexOrThrow(PhoneRestrictionPolicy.TIMESTAMP));
                    event.expirationTime = query.getLong(query.getColumnIndexOrThrow("expirationTime"));
                    arrayList.add(event);
                }
                query.close();
                return arrayList;
            } finally {
            }
        } catch (Exception unused) {
            AppLog.m270e("fail to get events");
            return arrayList;
        }
    }

    public final void update(Event event) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("serviceId", event.serviceId);
        contentValues.put("deviceId", event.deviceId);
        contentValues.put("serviceVersion", event.serviceVersion);
        contentValues.put("serviceAgreeType", event.serviceAgreeType);
        contentValues.put(PeripheralManager.Temp.EXTRA_SDK_VERSION, event.sdkVersion);
        contentValues.put("sdkType", event.sdkType);
        contentValues.put("serviceDefinedKey", event.serviceDefinedKey);
        contentValues.put("errorCode", event.errorCode);
        contentValues.put("logPath", event.logPath);
        contentValues.put("description", event.description);
        contentValues.put("relayClientVersion", event.relayClientVersion);
        contentValues.put("relayClientType", event.relayClientType);
        contentValues.put("extension", event.extension);
        contentValues.put("networkMode", Integer.valueOf(event.networkMode ? 1 : 0));
        contentValues.put("memory", event.memory);
        contentValues.put("storage", event.storage);
        contentValues.put(IMSParameter.CALL.STATUS, Integer.valueOf(event.status));
        contentValues.put("retryCount", Integer.valueOf(event.retryCount));
        contentValues.put("eventId", event.eventId);
        contentValues.put("s3Path", event.s3Path);
        contentValues.put(PhoneRestrictionPolicy.TIMESTAMP, Long.valueOf(event.timestamp));
        contentValues.put("expirationTime", Long.valueOf(event.expirationTime));
        this.f635db.update("Event", contentValues, "id=?", new String[]{String.valueOf(event.f637id)});
    }
}

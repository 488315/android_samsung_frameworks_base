package com.android.server.soundtrigger;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.soundtrigger.SoundTrigger;
import android.util.Slog;

import java.util.UUID;

public final class SoundTriggerDbHelper extends SQLiteOpenHelper {
    public final void deleteGenericSoundModel(UUID uuid) {
        synchronized (this) {
            try {
                SoundTrigger.GenericSoundModel genericSoundModel = getGenericSoundModel(uuid);
                if (genericSoundModel == null) {
                    return;
                }
                SQLiteDatabase writableDatabase = getWritableDatabase();
                try {
                    writableDatabase.delete(
                            "st_sound_model",
                            "model_uuid='" + genericSoundModel.getUuid().toString() + "'",
                            null);
                } finally {
                    writableDatabase.close();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final SoundTrigger.GenericSoundModel getGenericSoundModel(UUID uuid) {
        synchronized (this) {
            try {
                SQLiteDatabase readableDatabase = getReadableDatabase();
                Cursor rawQuery =
                        readableDatabase.rawQuery(
                                "SELECT  * FROM st_sound_model WHERE model_uuid= '" + uuid + "'",
                                null);
                try {
                    if (!rawQuery.moveToFirst()) {
                        rawQuery.close();
                        readableDatabase.close();
                        return null;
                    }
                    SoundTrigger.GenericSoundModel genericSoundModel =
                            new SoundTrigger.GenericSoundModel(
                                    uuid,
                                    UUID.fromString(
                                            rawQuery.getString(
                                                    rawQuery.getColumnIndex("vendor_uuid"))),
                                    rawQuery.getBlob(rawQuery.getColumnIndex("data")),
                                    rawQuery.getInt(rawQuery.getColumnIndex("model_version")));
                    rawQuery.close();
                    readableDatabase.close();
                    return genericSoundModel;
                } catch (Throwable th) {
                    rawQuery.close();
                    readableDatabase.close();
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(
                "CREATE TABLE st_sound_model(model_uuid TEXT PRIMARY KEY,vendor_uuid TEXT,data"
                    + " BLOB,model_version INTEGER )");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i == 1) {
            Slog.d("SoundTriggerDbHelper", "Adding model version column");
            sQLiteDatabase.execSQL(
                    "ALTER TABLE st_sound_model ADD COLUMN model_version INTEGER DEFAULT -1");
        }
    }

    public final void updateGenericSoundModel(SoundTrigger.GenericSoundModel genericSoundModel) {
        synchronized (this) {
            try {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("model_uuid", genericSoundModel.getUuid().toString());
                contentValues.put("vendor_uuid", genericSoundModel.getVendorUuid().toString());
                contentValues.put("data", genericSoundModel.getData());
                contentValues.put("model_version", Integer.valueOf(genericSoundModel.getVersion()));
                try {
                    writableDatabase.insertWithOnConflict("st_sound_model", null, contentValues, 5);
                } finally {
                    writableDatabase.close();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}

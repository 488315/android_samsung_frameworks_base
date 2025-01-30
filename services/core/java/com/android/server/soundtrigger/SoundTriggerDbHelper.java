package com.android.server.soundtrigger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.soundtrigger.SoundTrigger;
import android.util.Slog;
import java.io.PrintWriter;
import java.util.UUID;

/* loaded from: classes3.dex */
public class SoundTriggerDbHelper extends SQLiteOpenHelper {
    public SoundTriggerDbHelper(Context context) {
        super(context, "st_sound_model.db", (SQLiteDatabase.CursorFactory) null, 2);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE st_sound_model(model_uuid TEXT PRIMARY KEY,vendor_uuid TEXT,data BLOB,model_version INTEGER )");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i == 1) {
            Slog.d("SoundTriggerDbHelper", "Adding model version column");
            sQLiteDatabase.execSQL("ALTER TABLE st_sound_model ADD COLUMN model_version INTEGER DEFAULT -1");
        }
    }

    public boolean updateGenericSoundModel(SoundTrigger.GenericSoundModel genericSoundModel) {
        boolean z;
        synchronized (this) {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("model_uuid", genericSoundModel.getUuid().toString());
            contentValues.put("vendor_uuid", genericSoundModel.getVendorUuid().toString());
            contentValues.put("data", genericSoundModel.getData());
            contentValues.put("model_version", Integer.valueOf(genericSoundModel.getVersion()));
            try {
                z = writableDatabase.insertWithOnConflict("st_sound_model", null, contentValues, 5) != -1;
            } finally {
                writableDatabase.close();
            }
        }
        return z;
    }

    public SoundTrigger.GenericSoundModel getGenericSoundModel(UUID uuid) {
        synchronized (this) {
            SQLiteDatabase readableDatabase = getReadableDatabase();
            Cursor rawQuery = readableDatabase.rawQuery("SELECT  * FROM st_sound_model WHERE model_uuid= '" + uuid + "'", null);
            try {
                if (!rawQuery.moveToFirst()) {
                    return null;
                }
                return new SoundTrigger.GenericSoundModel(uuid, UUID.fromString(rawQuery.getString(rawQuery.getColumnIndex("vendor_uuid"))), rawQuery.getBlob(rawQuery.getColumnIndex("data")), rawQuery.getInt(rawQuery.getColumnIndex("model_version")));
            } finally {
                rawQuery.close();
                readableDatabase.close();
            }
        }
    }

    public boolean deleteGenericSoundModel(UUID uuid) {
        synchronized (this) {
            SoundTrigger.GenericSoundModel genericSoundModel = getGenericSoundModel(uuid);
            if (genericSoundModel == null) {
                return false;
            }
            SQLiteDatabase writableDatabase = getWritableDatabase();
            StringBuilder sb = new StringBuilder();
            sb.append("model_uuid='");
            sb.append(genericSoundModel.getUuid().toString());
            sb.append("'");
            try {
                return writableDatabase.delete("st_sound_model", sb.toString(), null) != 0;
            } finally {
                writableDatabase.close();
            }
        }
    }

    public void dump(PrintWriter printWriter) {
        synchronized (this) {
            SQLiteDatabase readableDatabase = getReadableDatabase();
            Cursor rawQuery = readableDatabase.rawQuery("SELECT  * FROM st_sound_model", null);
            try {
                printWriter.println("  Enrolled GenericSoundModels:");
                if (rawQuery.moveToFirst()) {
                    String[] columnNames = rawQuery.getColumnNames();
                    do {
                        for (String str : columnNames) {
                            int columnIndex = rawQuery.getColumnIndex(str);
                            int type = rawQuery.getType(columnIndex);
                            if (type == 0) {
                                printWriter.printf("    %s: null\n", str);
                            } else if (type == 1) {
                                printWriter.printf("    %s: %d\n", str, Integer.valueOf(rawQuery.getInt(columnIndex)));
                            } else if (type == 2) {
                                printWriter.printf("    %s: %f\n", str, Float.valueOf(rawQuery.getFloat(columnIndex)));
                            } else if (type == 3) {
                                printWriter.printf("    %s: %s\n", str, rawQuery.getString(columnIndex));
                            } else if (type == 4) {
                                printWriter.printf("    %s: data blob\n", str);
                            }
                        }
                        printWriter.println();
                    } while (rawQuery.moveToNext());
                }
            } finally {
                rawQuery.close();
                readableDatabase.close();
            }
        }
    }
}

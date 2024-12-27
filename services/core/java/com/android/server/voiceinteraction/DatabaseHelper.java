package com.android.server.voiceinteraction;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.soundtrigger.SoundTrigger;
import java.io.PrintWriter;
import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DatabaseHelper extends SQLiteOpenHelper implements IEnrolledModelDb {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SoundModelRecord {
        public final byte[] data;
        public final String hintText;
        public final int keyphraseId;
        public final String locale;
        public final String modelUuid;
        public final int recognitionModes;
        public final int type;
        public final String users;
        public final String vendorUuid;

        public SoundModelRecord(Cursor cursor) {
            this.modelUuid = cursor.getString(cursor.getColumnIndex("model_uuid"));
            this.vendorUuid = cursor.getString(cursor.getColumnIndex("vendor_uuid"));
            this.keyphraseId = cursor.getInt(cursor.getColumnIndex("keyphrase_id"));
            this.type = cursor.getInt(cursor.getColumnIndex("type"));
            this.data = cursor.getBlob(cursor.getColumnIndex("data"));
            this.recognitionModes = cursor.getInt(cursor.getColumnIndex("recognition_modes"));
            this.locale = cursor.getString(cursor.getColumnIndex("locale"));
            this.hintText = cursor.getString(cursor.getColumnIndex("hint_text"));
            this.users = cursor.getString(cursor.getColumnIndex("users"));
        }

        public final boolean V6PrimaryKeyMatches(SoundModelRecord soundModelRecord) {
            if (this.keyphraseId != soundModelRecord.keyphraseId) {
                return false;
            }
            String str = this.locale;
            String str2 = soundModelRecord.locale;
            if (!(str != null ? str.equals(str2) : str == str2)) {
                return false;
            }
            String str3 = this.users;
            String str4 = soundModelRecord.users;
            return str3 != null ? str3.equals(str4) : str3 == str4;
        }

        public final long writeToDatabase(SQLiteDatabase sQLiteDatabase) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("model_uuid", this.modelUuid);
            contentValues.put("vendor_uuid", this.vendorUuid);
            contentValues.put("keyphrase_id", Integer.valueOf(this.keyphraseId));
            contentValues.put("type", Integer.valueOf(this.type));
            contentValues.put("data", this.data);
            contentValues.put("recognition_modes", Integer.valueOf(this.recognitionModes));
            contentValues.put("locale", this.locale);
            contentValues.put("hint_text", this.hintText);
            contentValues.put("users", this.users);
            return sQLiteDatabase.insertWithOnConflict("sound_model", null, contentValues, 5);
        }
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public final boolean deleteKeyphraseSoundModel(int i, int i2, String str) {
        String languageTag = Locale.forLanguageTag(str).toLanguageTag();
        synchronized (this) {
            try {
                SoundTrigger.KeyphraseSoundModel keyphraseSoundModel = getKeyphraseSoundModel(i, i2, languageTag);
                if (keyphraseSoundModel == null) {
                    return false;
                }
                SQLiteDatabase writableDatabase = getWritableDatabase();
                StringBuilder sb = new StringBuilder("model_uuid='");
                sb.append(keyphraseSoundModel.getUuid().toString());
                sb.append("'");
                try {
                    return writableDatabase.delete("sound_model", sb.toString(), null) != 0;
                } finally {
                    writableDatabase.close();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public final void dump(PrintWriter printWriter) {
        synchronized (this) {
            try {
                SQLiteDatabase readableDatabase = getReadableDatabase();
                Cursor rawQuery = readableDatabase.rawQuery("SELECT  * FROM sound_model", null);
                try {
                    printWriter.println("  Enrolled KeyphraseSoundModels:");
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
                    rawQuery.close();
                    readableDatabase.close();
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

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public final SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, int i2, String str) {
        SoundTrigger.KeyphraseSoundModel validKeyphraseSoundModelForUser;
        String languageTag = Locale.forLanguageTag(str).toLanguageTag();
        synchronized (this) {
            validKeyphraseSoundModelForUser = getValidKeyphraseSoundModelForUser(i2, "SELECT  * FROM sound_model WHERE keyphrase_id= '" + i + "' AND locale='" + languageTag + "'");
        }
        return validKeyphraseSoundModelForUser;
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public final SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, String str, String str2) {
        SoundTrigger.KeyphraseSoundModel validKeyphraseSoundModelForUser;
        String languageTag = Locale.forLanguageTag(str2).toLanguageTag();
        synchronized (this) {
            validKeyphraseSoundModelForUser = getValidKeyphraseSoundModelForUser(i, "SELECT  * FROM sound_model WHERE hint_text= '" + str + "' AND locale='" + languageTag + "'");
        }
        return validKeyphraseSoundModelForUser;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00cc, code lost:
    
        r0 = new android.hardware.soundtrigger.SoundTrigger.Keyphrase[]{new android.hardware.soundtrigger.SoundTrigger.Keyphrase(r8, r9, r10, r11, r12)};
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00d7, code lost:
    
        if (r5 == null) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00d9, code lost:
    
        r11 = java.util.UUID.fromString(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00e0, code lost:
    
        r4 = new android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel(java.util.UUID.fromString(r3), r11, r6, r0, r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00ec, code lost:
    
        r2.close();
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00f2, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00df, code lost:
    
        r11 = null;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel getValidKeyphraseSoundModelForUser(int r17, java.lang.String r18) {
        /*
            Method dump skipped, instructions count: 272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.voiceinteraction.DatabaseHelper.getValidKeyphraseSoundModelForUser(int, java.lang.String):android.hardware.soundtrigger.SoundTrigger$KeyphraseSoundModel");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE sound_model(model_uuid TEXT,vendor_uuid TEXT,keyphrase_id INTEGER,type INTEGER,data BLOB,recognition_modes INTEGER,locale TEXT,hint_text TEXT,users TEXT,model_version INTEGER,PRIMARY KEY (keyphrase_id,locale,users))");
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x00a5, code lost:
    
        r3 = r10.writeToDatabase(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ad, code lost:
    
        if (r3 != (-1)) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00af, code lost:
    
        android.util.Slog.e("SoundModelDBHelper", "Database write failed " + r1 + ": " + r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00cc, code lost:
    
        r10 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00cd, code lost:
    
        android.util.Slog.e("SoundModelDBHelper", "Failed to update V6 record " + r1, r10);
     */
    @Override // android.database.sqlite.SQLiteOpenHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onUpgrade(android.database.sqlite.SQLiteDatabase r8, int r9, int r10) {
        /*
            r7 = this;
            java.lang.String r7 = "CREATE TABLE sound_model(model_uuid TEXT,vendor_uuid TEXT,keyphrase_id INTEGER,type INTEGER,data BLOB,recognition_modes INTEGER,locale TEXT,hint_text TEXT,users TEXT,model_version INTEGER,PRIMARY KEY (keyphrase_id,locale,users))"
            java.lang.String r10 = "DROP TABLE IF EXISTS sound_model"
            java.lang.String r0 = "SoundModelDBHelper"
            r1 = 4
            if (r9 >= r1) goto L10
            r8.execSQL(r10)
            r8.execSQL(r7)
            goto L1e
        L10:
            if (r9 != r1) goto L1e
            java.lang.String r1 = "Adding vendor UUID column"
            android.util.Slog.d(r0, r1)
            java.lang.String r1 = "ALTER TABLE sound_model ADD COLUMN vendor_uuid TEXT"
            r8.execSQL(r1)
            int r9 = r9 + 1
        L1e:
            r1 = 5
            if (r9 != r1) goto Le7
            java.lang.String r1 = "SELECT * FROM sound_model"
            r2 = 0
            android.database.Cursor r1 = r8.rawQuery(r1, r2)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            boolean r3 = r1.moveToFirst()     // Catch: java.lang.Throwable -> L3c
            if (r3 == 0) goto L4b
        L33:
            com.android.server.voiceinteraction.DatabaseHelper$SoundModelRecord r3 = new com.android.server.voiceinteraction.DatabaseHelper$SoundModelRecord     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            r2.add(r3)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            goto L45
        L3c:
            r7 = move-exception
            goto Le3
        L3f:
            r3 = move-exception
            java.lang.String r4 = "Failed to extract V5 record"
            android.util.Slog.e(r0, r4, r3)     // Catch: java.lang.Throwable -> L3c
        L45:
            boolean r3 = r1.moveToNext()     // Catch: java.lang.Throwable -> L3c
            if (r3 != 0) goto L33
        L4b:
            r1.close()
            r8.execSQL(r10)
            r8.execSQL(r7)
            java.util.Iterator r7 = r2.iterator()
        L58:
            boolean r10 = r7.hasNext()
            if (r10 == 0) goto Le0
            java.lang.Object r10 = r7.next()
            com.android.server.voiceinteraction.DatabaseHelper$SoundModelRecord r10 = (com.android.server.voiceinteraction.DatabaseHelper.SoundModelRecord) r10
            r10.getClass()
            java.lang.String r1 = r10.modelUuid
            java.util.Iterator r3 = r2.iterator()
        L6d:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L8d
            java.lang.Object r4 = r3.next()
            com.android.server.voiceinteraction.DatabaseHelper$SoundModelRecord r4 = (com.android.server.voiceinteraction.DatabaseHelper.SoundModelRecord) r4
            if (r10 != r4) goto L7c
            goto L6d
        L7c:
            boolean r5 = r10.V6PrimaryKeyMatches(r4)
            if (r5 == 0) goto L6d
            byte[] r5 = r10.data
            byte[] r4 = r4.data
            boolean r4 = java.util.Arrays.equals(r5, r4)
            if (r4 != 0) goto L6d
            goto L58
        L8d:
            java.util.Iterator r3 = r2.iterator()
        L91:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto La5
            java.lang.Object r4 = r3.next()
            com.android.server.voiceinteraction.DatabaseHelper$SoundModelRecord r4 = (com.android.server.voiceinteraction.DatabaseHelper.SoundModelRecord) r4
            boolean r5 = r10.V6PrimaryKeyMatches(r4)
            if (r5 == 0) goto L91
            if (r10 != r4) goto L58
        La5:
            long r3 = r10.writeToDatabase(r8)     // Catch: java.lang.Exception -> Lcc
            r5 = -1
            int r10 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r10 != 0) goto L58
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Lcc
            r10.<init>()     // Catch: java.lang.Exception -> Lcc
            java.lang.String r5 = "Database write failed "
            r10.append(r5)     // Catch: java.lang.Exception -> Lcc
            r10.append(r1)     // Catch: java.lang.Exception -> Lcc
            java.lang.String r5 = ": "
            r10.append(r5)     // Catch: java.lang.Exception -> Lcc
            r10.append(r3)     // Catch: java.lang.Exception -> Lcc
            java.lang.String r10 = r10.toString()     // Catch: java.lang.Exception -> Lcc
            android.util.Slog.e(r0, r10)     // Catch: java.lang.Exception -> Lcc
            goto L58
        Lcc:
            r10 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Failed to update V6 record "
            r3.<init>(r4)
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            android.util.Slog.e(r0, r1, r10)
            goto L58
        Le0:
            int r9 = r9 + 1
            goto Le7
        Le3:
            r1.close()
            throw r7
        Le7:
            r7 = 6
            if (r9 != r7) goto Lf4
            java.lang.String r7 = "Adding model version column"
            android.util.Slog.d(r0, r7)
            java.lang.String r7 = "ALTER TABLE sound_model ADD COLUMN model_version INTEGER DEFAULT -1"
            r8.execSQL(r7)
        Lf4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.voiceinteraction.DatabaseHelper.onUpgrade(android.database.sqlite.SQLiteDatabase, int, int):void");
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public final boolean updateKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) {
        String sb;
        synchronized (this) {
            try {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("model_uuid", keyphraseSoundModel.getUuid().toString());
                if (keyphraseSoundModel.getVendorUuid() != null) {
                    contentValues.put("vendor_uuid", keyphraseSoundModel.getVendorUuid().toString());
                }
                contentValues.put("type", (Integer) 0);
                contentValues.put("data", keyphraseSoundModel.getData());
                contentValues.put("model_version", Integer.valueOf(keyphraseSoundModel.getVersion()));
                if (keyphraseSoundModel.getKeyphrases() == null || keyphraseSoundModel.getKeyphrases().length != 1) {
                    return false;
                }
                contentValues.put("keyphrase_id", Integer.valueOf(keyphraseSoundModel.getKeyphrases()[0].getId()));
                contentValues.put("recognition_modes", Integer.valueOf(keyphraseSoundModel.getKeyphrases()[0].getRecognitionModes()));
                int[] users = keyphraseSoundModel.getKeyphrases()[0].getUsers();
                if (users == null) {
                    sb = "";
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    for (int i = 0; i < users.length; i++) {
                        if (i != 0) {
                            sb2.append(',');
                        }
                        sb2.append(users[i]);
                    }
                    sb = sb2.toString();
                }
                contentValues.put("users", sb);
                contentValues.put("locale", keyphraseSoundModel.getKeyphrases()[0].getLocale().toLanguageTag());
                contentValues.put("hint_text", keyphraseSoundModel.getKeyphrases()[0].getText());
                try {
                    return writableDatabase.insertWithOnConflict("sound_model", null, contentValues, 5) != -1;
                } finally {
                    writableDatabase.close();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}

package com.samsung.android.server.audio;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.UserInfo;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0;

import com.samsung.android.audio.Rune;
import com.samsung.android.vibrator.VibRune;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AudioSettingsHelper extends SQLiteOpenHelper {
    public static AudioSettingsHelper sInstance;
    public CopyOnWriteArrayList mCallPolicyAllowList;
    public final Context mContext;
    public final SQLiteDatabase mDatabase;
    public CopyOnWriteArrayList mSoundAppPolicyAllowAppList;

    public AudioSettingsHelper(final Context context) {
        super(
                context,
                "audioservice_sec.db",
                null,
                14,
                new DatabaseErrorHandler() { // from class:
                                             // com.samsung.android.server.audio.AudioSettingsHelper$$ExternalSyntheticLambda0
                    @Override // android.database.DatabaseErrorHandler
                    public final void onCorruption(SQLiteDatabase sQLiteDatabase) {
                        Context context2 = context;
                        try {
                            Log.e(
                                    "AudioService.DB",
                                    "database is corrupted= "
                                            + sQLiteDatabase.semIsDatabaseCorrupted());
                            if (sQLiteDatabase.semIsDatabaseCorrupted()) {
                                Log.e(
                                        "AudioService.DB",
                                        "delete database "
                                                + context2.deleteDatabase("audioservice_sec.db"));
                            }
                        } catch (Exception e) {
                            RCPManagerService$$ExternalSyntheticOutline0.m(
                                    e,
                                    new StringBuilder("DatabaseErrorHandler "),
                                    "AudioService.DB");
                        }
                    }
                });
        this.mContext = context;
        this.mDatabase = getWritableDatabase();
        this.mSoundAppPolicyAllowAppList = new CopyOnWriteArrayList();
        if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
            this.mCallPolicyAllowList = new CopyOnWriteArrayList();
        }
    }

    public static void destroy() {
        sInstance = null;
    }

    public static synchronized AudioSettingsHelper getInstance(Context context) {
        AudioSettingsHelper audioSettingsHelper;
        synchronized (AudioSettingsHelper.class) {
            try {
                if (sInstance == null) {
                    sInstance = new AudioSettingsHelper(context);
                }
                audioSettingsHelper = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return audioSettingsHelper;
    }

    public final void add(String str, ContentValues contentValues) {
        try {
            this.mDatabase.insert(str, null, contentValues);
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("add error "), "AudioService.DB");
        }
    }

    public final boolean checkAppCategory(String str, String str2) {
        if (this.mSoundAppPolicyAllowAppList.isEmpty()) {
            return false;
        }
        return this.mSoundAppPolicyAllowAppList.stream()
                .anyMatch(new AudioSettingsHelper$$ExternalSyntheticLambda1(str, str2));
    }

    public final void enableSyncParentSound() {
        try {
            UserManager userManager = (UserManager) this.mContext.getSystemService("user");
            if (userManager == null) {
                return;
            }
            for (UserInfo userInfo : userManager.getProfiles(UserHandle.myUserId())) {
                if (userInfo.isDualAppProfile()) {
                    Settings.Secure.putIntForUser(
                            this.mContext.getContentResolver(),
                            "sync_parent_sounds",
                            1,
                            userInfo.id);
                    return;
                }
            }
        } catch (Exception e) {
            Log.e("AudioService.DB", "Enable sync sound error ", e);
        }
    }

    public final Hashtable getAppList() {
        Hashtable hashtable = new Hashtable();
        if (this.mSoundAppPolicyAllowAppList.isEmpty()) {
            try {
                Cursor query =
                        this.mDatabase.query(
                                "category_packages",
                                new String[] {"_package", "_category"},
                                null,
                                null,
                                null,
                                null,
                                null);
                try {
                    if (query.moveToFirst()) {
                        do {
                            this.mSoundAppPolicyAllowAppList.add(
                                    new Pair(query.getString(0), query.getString(1)));
                        } while (query.moveToNext());
                    }
                    query.close();
                } finally {
                }
            } catch (Exception e) {
                RCPManagerService$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("getAllSoundAppPolicyAllowAppList error "),
                        "AudioService.DB");
            }
        }
        Iterator it = this.mSoundAppPolicyAllowAppList.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            hashtable.put((String) pair.first, (String) pair.second);
        }
        return hashtable;
    }

    public final int getInt(String str, String[] strArr, String str2, int i) {
        try {
            Cursor query = this.mDatabase.query(str, strArr, str2, null, null, null, null);
            try {
                if (query.getCount() == 1) {
                    query.moveToFirst();
                    i = query.getInt(1);
                }
                query.close();
            } finally {
            }
        } catch (Exception e) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("getInt error "), "AudioService.DB");
        }
        return i;
    }

    public final int getIntValue(int i, String str) {
        return getInt(
                "audio_settings",
                new String[] {"_key", "_value"},
                XmlUtils$$ExternalSyntheticOutline0.m("_key='", str, "'"),
                i);
    }

    public final Hashtable getPackageList() {
        Hashtable hashtable = new Hashtable();
        try {
            Cursor query =
                    this.mDatabase.query(
                            "selectedpkg",
                            new String[] {"_uid", "_package"},
                            null,
                            null,
                            null,
                            null,
                            null);
            try {
                if (query.moveToFirst()) {
                    do {
                        int i = query.getInt(0);
                        hashtable.put(Integer.valueOf(i), query.getString(1));
                    } while (query.moveToNext());
                }
                query.close();
            } finally {
            }
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("getPackageList error "), "AudioService.DB");
        }
        return hashtable;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0074, code lost:

       if (r8 == false) goto L31;
    */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0076, code lost:

       android.provider.Settings.System.putIntForUser(r10, r12, r11, -2);
    */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x007a, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:21:?, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0071, code lost:

       if (r9 == null) goto L23;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initSystemVibration(int r11, java.lang.String r12) {
        /*
            r10 = this;
            java.lang.String r0 = "content://settings/system/"
            java.lang.String r1 = "initSystemVibration error "
            android.content.Context r10 = r10.mContext
            android.content.ContentResolver r10 = r10.getContentResolver()
            r8 = 1
            r9 = 0
            java.lang.String r0 = r0.concat(r12)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            android.net.Uri r3 = android.net.Uri.parse(r0)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r2 = r10
            android.database.Cursor r9 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            if (r9 == 0) goto L56
            int r0 = r9.getCount()     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            if (r0 <= 0) goto L56
            boolean r0 = r9.moveToFirst()     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            if (r0 == 0) goto L56
            java.lang.String r0 = "value"
            int r0 = r9.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            r9.getString(r0)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            java.lang.String r0 = "package"
            int r0 = r9.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            java.lang.String r0 = r9.getString(r0)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            java.lang.String r2 = "android"
            boolean r2 = r2.equals(r0)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            if (r2 != 0) goto L56
            java.lang.String r2 = "com.android.providers.settings"
            boolean r0 = r2.equals(r0)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            if (r0 != 0) goto L56
            r8 = 0
            goto L56
        L52:
            r10 = move-exception
            goto L7b
        L54:
            r0 = move-exception
            goto L5c
        L56:
            if (r9 == 0) goto L74
        L58:
            r9.close()
            goto L74
        L5c:
            java.lang.String r2 = "AudioService.DB"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L52
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L52
            java.lang.String r0 = r0.getMessage()     // Catch: java.lang.Throwable -> L52
            r3.append(r0)     // Catch: java.lang.Throwable -> L52
            java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> L52
            android.util.Log.e(r2, r0)     // Catch: java.lang.Throwable -> L52
            if (r9 == 0) goto L74
            goto L58
        L74:
            if (r8 == 0) goto L7a
            r0 = -2
            android.provider.Settings.System.putIntForUser(r10, r12, r11, r0)
        L7a:
            return
        L7b:
            if (r9 == 0) goto L80
            r9.close()
        L80:
            throw r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.audio.AudioSettingsHelper.initSystemVibration(int,"
                    + " java.lang.String):void");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL(
                    "CREATE TABLE audio_settings (_id INTEGER PRIMARY KEY AUTOINCREMENT, _key TEXT"
                        + " UNIQUE, _value INTEGER);");
            sQLiteDatabase.execSQL(
                    "CREATE TABLE device_addr (_id INTEGER PRIMARY KEY AUTOINCREMENT, _addr TEXT"
                        + " UNIQUE, _index INTEGER);");
            sQLiteDatabase.execSQL(
                    "CREATE TABLE app_volume (_id INTEGER PRIMARY KEY AUTOINCREMENT, _uid INTEGER"
                        + " UNIQUE, _index INTEGER);");
            sQLiteDatabase.execSQL(
                    "CREATE TABLE selectedpkg (_id INTEGER PRIMARY KEY AUTOINCREMENT, _uid"
                        + " INTERGER, _package TEXT);");
            sQLiteDatabase.execSQL(
                    "CREATE TABLE category_packages (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + " _package TEXT,_category TEXT);");
            sQLiteDatabase.execSQL(
                    "CREATE TABLE call_policy_category_packages (_id INTEGER PRIMARY KEY"
                        + " AUTOINCREMENT, _package TEXT,_category TEXT);");
        } catch (Exception e) {
            Log.e("AudioService.DB", "Create DB Create failed ", e);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        try {
            sQLiteDatabase.setVersion(i);
        } catch (Exception e) {
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("onDowngrade error "), "AudioService.DB");
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        int i3 = i;
        Log.w("AudioService.DB", "Upgrading settings database from version " + i3 + " to " + i2);
        if (i3 == 1) {
            try {
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS category_packages");
                sQLiteDatabase.execSQL(
                        "CREATE TABLE selectedpkg (_id INTEGER PRIMARY KEY AUTOINCREMENT, _uid"
                            + " INTEGER, _package TEXT);");
                i3 = 2;
            } catch (Exception e) {
                RCPManagerService$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("onUpgrade error "), "AudioService.DB");
                return;
            }
        }
        if (i3 == 2) {
            sQLiteDatabase.execSQL(
                    "CREATE TABLE category_packages (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + " _package TEXT,_category TEXT);");
            i3 = 3;
        }
        if (i3 == 3) {
            sQLiteDatabase.execSQL("delete from device_addr");
            i3 = 4;
        }
        if (i3 == 4) {
            i3 = 5;
        }
        if (i3 == 5) {
            if (Settings.System.getIntForUser(
                            this.mContext.getContentResolver(), "adjust_media_volume_only", -1, -2)
                    == -1) {
                Settings.System.putIntForUser(
                        this.mContext.getContentResolver(), "adjust_media_volume_only", 0, -2);
            }
            i3 = 6;
        }
        if (i3 == 6) {
            sQLiteDatabase.execSQL(
                    "update audio_settings set _value = 0 where _key = 'APP_LIST_VERSION'");
            i3 = 7;
        }
        if (i3 == 7) {
            enableSyncParentSound();
            i3 = 8;
        }
        if (i3 == 8) {
            Vibrator vibrator = (Vibrator) this.mContext.getSystemService("vibrator");
            if (vibrator != null
                    && vibrator.hasVibrator()
                    && vibrator.semGetSupportedVibrationType() == 1) {
                if (VibRune.SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR) {
                    initSystemVibration(1, "haptic_feedback_enabled");
                }
                initSystemVibration(0, "sip_key_feedback_vibration");
                initSystemVibration(1, "dialing_keypad_vibrate");
                initSystemVibration(1, "navigation_gestures_vibrate");
                initSystemVibration(1, "VIB_FEEDBACK_MAGNITUDE");
            }
            i3 = 9;
        }
        if (i3 == 9) {
            String string =
                    Settings.Secure.getString(
                            this.mContext.getContentResolver(), "volumelimit_secure_password");
            if (string != null && !string.isEmpty()) {
                try {
                    Charset charset = StandardCharsets.UTF_8;
                    byte[] copyOf =
                            Arrays.copyOf(
                                    MessageDigest.getInstance("SHA-1")
                                            .digest("encrypt_password".getBytes(charset)),
                                    16);
                    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
                    cipher.init(2, new SecretKeySpec(copyOf, "AES"));
                    String str =
                            new String(cipher.doFinal(Base64.getDecoder().decode(string)), charset);
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                    messageDigest.update(str.getBytes(charset));
                    Settings.Secure.putString(
                            this.mContext.getContentResolver(),
                            "volumelimit_secure_password",
                            String.format("%064x", new BigInteger(1, messageDigest.digest())));
                } catch (InvalidKeyException
                        | NoSuchAlgorithmException
                        | BadPaddingException
                        | IllegalBlockSizeException
                        | NoSuchPaddingException e2) {
                    Log.e(
                            "AudioService.DB",
                            "Change of volume limiter pin encryption failed. Pin is initialized.",
                            e2);
                    Settings.Secure.putString(
                            this.mContext.getContentResolver(), "volumelimit_secure_password", "");
                    Settings.System.putInt(
                            this.mContext.getContentResolver(), "volumelimit_set_password", 0);
                }
            }
            i3 = 10;
        }
        if (i3 == 10) {
            i3 = 11;
        }
        if (i3 == 11) {
            if (VibRune.SUPPORT_ACH) {
                int[] iArr = {1, 128, 2, 256};
                String[] strArr = {
                    "ringtone_CONSTANT_PATH",
                    "ringtone_2_CONSTANT_PATH",
                    "notification_sound_CONSTANT_PATH",
                    "notification_sound_2_CONSTANT_PATH"
                };
                String[] strArr2 = {
                    "sync_vibration_with_ringtone",
                    "sync_vibration_with_ringtone_2",
                    "sync_vibration_with_notification",
                    "sync_vibration_with_notification"
                };
                for (int i4 = 0; i4 < 4; i4++) {
                    if (RingtoneManager.isInternalRingtoneUri(
                            RingtoneManager.getActualDefaultRingtoneUri(this.mContext, iArr[i4]))) {
                        try {
                            if (!AudioManager.hasHapticChannels(
                                    this.mContext,
                                    Uri.fromFile(
                                            new File(
                                                    Uri.parse(
                                                                    Settings.System
                                                                            .getStringForUser(
                                                                                    this.mContext
                                                                                            .getContentResolver(),
                                                                                    strArr[i4],
                                                                                    this.mContext
                                                                                            .getUserId()))
                                                            .getQueryParameter("path"))))) {
                                Log.i("AudioService.DB", strArr[i4] + " has not haptic channels");
                                Settings.System.putInt(
                                        this.mContext.getContentResolver(), strArr2[i4], 0);
                            }
                        } catch (Exception e3) {
                            Log.e("AudioService.DB", "Uri error ", e3);
                        }
                    } else {
                        Settings.System.putInt(this.mContext.getContentResolver(), strArr2[i4], 0);
                    }
                }
            }
            i3 = 12;
        }
        if (i3 == 12) {
            sQLiteDatabase.execSQL("DELETE FROM selectedpkg");
            sQLiteDatabase.execSQL("DELETE FROM category_packages");
            sQLiteDatabase.execSQL(
                    "update audio_settings set _value = 0 where _key = 'APP_LIST_VERSION'");
            i3 = 13;
        }
        if (i3 == 13) {
            sQLiteDatabase.execSQL(
                    "CREATE TABLE IF NOT EXISTS call_policy_category_packages (_id INTEGER PRIMARY"
                        + " KEY AUTOINCREMENT, _package TEXT,_category TEXT);");
        }
    }

    public final void putCallPolicyAllowList(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_package", str);
        contentValues.put("_category", str2);
        add("call_policy_category_packages", contentValues);
        this.mCallPolicyAllowList.add(new Pair(str, str2));
    }

    public final int remove(String str, String str2) {
        try {
            return this.mDatabase.delete(str, str2, null);
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("remove error "), "AudioService.DB");
            return 0;
        }
    }

    public final int removeValue(String str) {
        return remove("audio_settings", "_key='" + str + "'");
    }

    public final void resetAllowedListTable() {
        Trace.traceBegin(256L, "resetAllowedListTable");
        try {
            try {
                this.mDatabase.execSQL("delete from category_packages");
                this.mSoundAppPolicyAllowAppList = new CopyOnWriteArrayList();
            } catch (Exception e) {
                Log.e("AudioService.DB", "resetAllowedListTable error " + e.getMessage());
            }
        } finally {
            Trace.endSection();
        }
    }

    public final void resetCallPolicyTable() {
        Trace.traceBegin(256L, "resetCallPolicyTable");
        try {
            try {
                this.mDatabase.execSQL("delete from call_policy_category_packages");
                this.mCallPolicyAllowList = new CopyOnWriteArrayList();
            } catch (Exception e) {
                Log.e("AudioService.DB", "resetCallPolicyTable error " + e.getMessage());
            }
        } finally {
            Trace.endSection();
        }
    }

    public final void set(ContentValues contentValues, String str, String str2) {
        try {
            if (this.mDatabase.update(str, contentValues, str2, null) <= 0) {
                this.mDatabase.insert(str, null, contentValues);
            }
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("set error "), "AudioService.DB");
        }
    }

    public final void setIntValue(int i, String str) {
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("_key", str);
        m.put("_value", Integer.valueOf(i));
        set(m, "audio_settings", "_key='" + str + "'");
    }
}

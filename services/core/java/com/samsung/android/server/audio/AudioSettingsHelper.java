package com.samsung.android.server.audio;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.UserInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Debug;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.audio.Rune;
import com.samsung.android.vibrator.VibRune;
import java.io.File;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public class AudioSettingsHelper extends SQLiteOpenHelper {
    public static AudioSettingsHelper sInstance;
    public CopyOnWriteArrayList mCallPolicyAllowList;
    public final Context mContext;
    public final SQLiteDatabase mDatabase;
    public CopyOnWriteArrayList mSoundAppPolicyAllowAppList;

    public AudioSettingsHelper(Context context) {
        super(context, "audioservice_sec.db", (SQLiteDatabase.CursorFactory) null, 14);
        this.mContext = context;
        this.mDatabase = getWritableDatabase();
        this.mSoundAppPolicyAllowAppList = new CopyOnWriteArrayList();
        if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
            this.mCallPolicyAllowList = new CopyOnWriteArrayList();
        }
    }

    public static synchronized AudioSettingsHelper getInstance(Context context) {
        AudioSettingsHelper audioSettingsHelper;
        synchronized (AudioSettingsHelper.class) {
            if (sInstance == null) {
                sInstance = new AudioSettingsHelper(context);
            }
            audioSettingsHelper = sInstance;
        }
        return audioSettingsHelper;
    }

    public static void destroy() {
        sInstance = null;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE audio_settings (_id INTEGER PRIMARY KEY AUTOINCREMENT, _key TEXT UNIQUE, _value INTEGER);");
            sQLiteDatabase.execSQL("CREATE TABLE device_addr (_id INTEGER PRIMARY KEY AUTOINCREMENT, _addr TEXT UNIQUE, _index INTEGER);");
            sQLiteDatabase.execSQL("CREATE TABLE app_volume (_id INTEGER PRIMARY KEY AUTOINCREMENT, _uid INTEGER UNIQUE, _index INTEGER);");
            sQLiteDatabase.execSQL("CREATE TABLE selectedpkg (_id INTEGER PRIMARY KEY AUTOINCREMENT, _uid INTERGER, _package TEXT);");
            sQLiteDatabase.execSQL("CREATE TABLE category_packages (_id INTEGER PRIMARY KEY AUTOINCREMENT, _package TEXT,_category TEXT);");
            sQLiteDatabase.execSQL("CREATE TABLE call_policy_category_packages (_id INTEGER PRIMARY KEY AUTOINCREMENT, _package TEXT,_category TEXT);");
        } catch (SQLException e) {
            Log.e("AudioService.DB", "Create DB Create failed", e);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        Log.w("AudioService.DB", "Upgrading settings database from version " + i + " to " + i2);
        if (i == 1) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS category_packages");
            sQLiteDatabase.execSQL("CREATE TABLE selectedpkg (_id INTEGER PRIMARY KEY AUTOINCREMENT, _uid INTEGER, _package TEXT);");
            i = 2;
        }
        if (i == 2) {
            sQLiteDatabase.execSQL("CREATE TABLE category_packages (_id INTEGER PRIMARY KEY AUTOINCREMENT, _package TEXT,_category TEXT);");
            i = 3;
        }
        if (i == 3) {
            sQLiteDatabase.execSQL("delete from device_addr");
            i = 4;
        }
        if (i == 4) {
            i = 5;
        }
        if (i == 5) {
            if (Settings.System.getIntForUser(this.mContext.getContentResolver(), "adjust_media_volume_only", -1, -2) == -1) {
                Settings.System.putIntForUser(this.mContext.getContentResolver(), "adjust_media_volume_only", 0, -2);
            }
            i = 6;
        }
        if (i == 6) {
            sQLiteDatabase.execSQL("update audio_settings set _value = 0 where _key = 'APP_LIST_VERSION'");
            i = 7;
        }
        if (i == 7) {
            enableSyncParentSound();
            i = 8;
        }
        if (i == 8) {
            Vibrator vibrator = (Vibrator) this.mContext.getSystemService("vibrator");
            if (vibrator != null && vibrator.hasVibrator() && vibrator.semGetSupportedVibrationType() == 1) {
                if (VibRune.SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR) {
                    initSystemVibration("haptic_feedback_enabled", 1);
                }
                initSystemVibration("sip_key_feedback_vibration", 0);
                initSystemVibration("dialing_keypad_vibrate", 1);
                initSystemVibration("navigation_gestures_vibrate", 1);
                initSystemVibration("VIB_FEEDBACK_MAGNITUDE", 1);
            }
            i = 9;
        }
        if (i == 9) {
            String string = Settings.Secure.getString(this.mContext.getContentResolver(), "volumelimit_secure_password");
            if (string != null && !string.isEmpty()) {
                try {
                    byte[] copyOf = Arrays.copyOf(MessageDigest.getInstance("SHA-1").digest("encrypt_password".getBytes(StandardCharsets.UTF_8)), 16);
                    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
                    cipher.init(2, new SecretKeySpec(copyOf, "AES"));
                    String str = new String(cipher.doFinal(Base64.getDecoder().decode(string)), StandardCharsets.UTF_8);
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                    messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
                    Settings.Secure.putString(this.mContext.getContentResolver(), "volumelimit_secure_password", String.format("%064x", new BigInteger(1, messageDigest.digest())));
                } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
                    Log.e("AudioService.DB", "Change of volume limiter pin encryption failed. Pin is initialized.", e);
                    Settings.Secure.putString(this.mContext.getContentResolver(), "volumelimit_secure_password", "");
                    Settings.System.putInt(this.mContext.getContentResolver(), "volumelimit_set_password", 0);
                }
            }
            i = 10;
        }
        if (i == 10) {
            i = 11;
        }
        if (i == 11) {
            if (VibRune.SUPPORT_ACH) {
                int[] iArr = {1, 128, 2, 256};
                String[] strArr = {"ringtone_CONSTANT_PATH", "ringtone_2_CONSTANT_PATH", "notification_sound_CONSTANT_PATH", "notification_sound_2_CONSTANT_PATH"};
                String[] strArr2 = {"sync_vibration_with_ringtone", "sync_vibration_with_ringtone_2", "sync_vibration_with_notification", "sync_vibration_with_notification"};
                for (int i3 = 0; i3 < 4; i3++) {
                    if (!RingtoneManager.isInternalRingtoneUri(RingtoneManager.getActualDefaultRingtoneUri(this.mContext, iArr[i3]))) {
                        Settings.System.putInt(this.mContext.getContentResolver(), strArr2[i3], 0);
                    } else {
                        try {
                            if (!AudioManager.hasHapticChannels(this.mContext, Uri.fromFile(new File(Uri.parse(Settings.System.getStringForUser(this.mContext.getContentResolver(), strArr[i3], this.mContext.getUserId())).getQueryParameter("path"))))) {
                                Log.i("AudioService.DB", strArr[i3] + " has not haptic channels");
                                Settings.System.putInt(this.mContext.getContentResolver(), strArr2[i3], 0);
                            }
                        } catch (Exception e2) {
                            Log.e("AudioService.DB", "Uri error", e2);
                        }
                    }
                }
            }
            i = 12;
        }
        if (i == 12) {
            sQLiteDatabase.execSQL("DELETE FROM selectedpkg");
            sQLiteDatabase.execSQL("DELETE FROM category_packages");
            sQLiteDatabase.execSQL("update audio_settings set _value = 0 where _key = 'APP_LIST_VERSION'");
            i = 13;
        }
        if (i == 13) {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS call_policy_category_packages (_id INTEGER PRIMARY KEY AUTOINCREMENT, _package TEXT,_category TEXT);");
        }
    }

    public final void enableSyncParentSound() {
        try {
            UserManager userManager = (UserManager) this.mContext.getSystemService("user");
            if (userManager == null) {
                return;
            }
            for (UserInfo userInfo : userManager.getProfiles(UserHandle.myUserId())) {
                if (userInfo.isDualAppProfile()) {
                    Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "sync_parent_sounds", 1, userInfo.id);
                    return;
                }
            }
        } catch (Exception e) {
            Log.e("AudioService.DB", "Enable sync sound error", e);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.setVersion(i);
    }

    public void resetTable() {
        this.mDatabase.execSQL("delete from audio_settings");
        this.mDatabase.execSQL("delete from device_addr");
        this.mDatabase.execSQL("delete from app_volume");
        this.mDatabase.execSQL("delete from selectedpkg");
    }

    public final int getInt(String str, String[] strArr, String str2, int i, int i2) {
        try {
            Cursor query = this.mDatabase.query(str, strArr, str2, null, null, null, null);
            if (query != null) {
                try {
                    if (query.getCount() == 1) {
                        query.moveToFirst();
                        i2 = query.getInt(i);
                    }
                } finally {
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return i2;
    }

    public final void set(String str, ContentValues contentValues, String str2) {
        try {
            if (this.mDatabase.update(str, contentValues, str2, null) <= 0) {
                this.mDatabase.insert(str, null, contentValues);
            }
        } catch (SQLiteException e) {
            Log.e("AudioService.DB", "DB error", e);
        }
    }

    public final void add(String str, ContentValues contentValues) {
        try {
            this.mDatabase.insert(str, null, contentValues);
        } catch (SQLiteException e) {
            Log.e("AudioService.DB", "DB error", e);
        }
    }

    public final int remove(String str, String str2) {
        try {
            return this.mDatabase.delete(str, str2, null);
        } catch (SQLiteException e) {
            Log.e("AudioService.DB", "DB error", e);
            return 0;
        }
    }

    public boolean getBooleanValue(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("_key='");
        sb.append(str);
        sb.append("'");
        return getInt("audio_settings", new String[]{"_key", "_value"}, sb.toString(), 1, -1) > 0;
    }

    public int getIntValue(String str, int i) {
        return getInt("audio_settings", new String[]{"_key", "_value"}, "_key='" + str + "'", 1, i);
    }

    public void setBooleanValue(String str, boolean z) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_key", str);
        contentValues.put("_value", Integer.valueOf(z ? 1 : 0));
        set("audio_settings", contentValues, "_key='" + str + "'");
    }

    public void setIntValue(String str, int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_key", str);
        contentValues.put("_value", Integer.valueOf(i));
        set("audio_settings", contentValues, "_key='" + str + "'");
    }

    public int removeValue(String str) {
        return remove("audio_settings", "_key='" + str + "'");
    }

    public int getBTVolumeIndex(String str) {
        return getInt("device_addr", new String[]{"_addr", "_index"}, "_addr='" + str + "'", 1, -1);
    }

    public void setBTVolumeIndex(String str, int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_addr", str);
        contentValues.put("_index", Integer.valueOf(i));
        set("device_addr", contentValues, "_addr='" + str + "'");
    }

    public Hashtable getPackageList() {
        Hashtable hashtable = new Hashtable();
        try {
            Cursor query = this.mDatabase.query("selectedpkg", new String[]{"_uid", "_package"}, null, null, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        do {
                            hashtable.put(Integer.valueOf(query.getInt(0)), query.getString(1));
                        } while (query.moveToNext());
                    }
                } finally {
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            Log.e("AudioService.DB", "DB error", e);
        }
        return hashtable;
    }

    public void putPackage(int i, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_uid", Integer.valueOf(i));
        contentValues.put("_package", str);
        set("selectedpkg", contentValues, "_uid='" + i + "'");
    }

    public void removePackage(int i) {
        remove("selectedpkg", "_uid='" + i + "'");
    }

    public Hashtable getAppList() {
        Hashtable hashtable = new Hashtable();
        Iterator it = getAllSoundAppPolicyAllowAppList().iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            hashtable.put((String) pair.first, (String) pair.second);
        }
        return hashtable;
    }

    public final CopyOnWriteArrayList getAllSoundAppPolicyAllowAppList() {
        if (this.mSoundAppPolicyAllowAppList.isEmpty()) {
            Log.i("AudioService.DB", "getAllSoundAppPolicyAllowAppList: isEmpty -  " + Debug.getCaller());
            try {
                Cursor query = this.mDatabase.query("category_packages", new String[]{"_package", "_category"}, null, null, null, null, null);
                if (query != null) {
                    try {
                        if (query.moveToFirst()) {
                            do {
                                this.mSoundAppPolicyAllowAppList.add(new Pair(query.getString(0), query.getString(1)));
                            } while (query.moveToNext());
                        }
                    } finally {
                    }
                }
                if (query != null) {
                    query.close();
                }
            } catch (Exception e) {
                Log.e("AudioService.DB", "DB error", e);
            }
        }
        return this.mSoundAppPolicyAllowAppList;
    }

    public void putAppList(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_package", str);
        contentValues.put("_category", str2);
        add("category_packages", contentValues);
    }

    public boolean checkAppCategory(final String str, final String str2) {
        if (this.mSoundAppPolicyAllowAppList.isEmpty()) {
            return false;
        }
        return this.mSoundAppPolicyAllowAppList.stream().anyMatch(new Predicate() { // from class: com.samsung.android.server.audio.AudioSettingsHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$checkAppCategory$0;
                lambda$checkAppCategory$0 = AudioSettingsHelper.lambda$checkAppCategory$0(str, str2, (Pair) obj);
                return lambda$checkAppCategory$0;
            }
        });
    }

    public static /* synthetic */ boolean lambda$checkAppCategory$0(String str, String str2, Pair pair) {
        return ((String) pair.first).equals(str) && ((String) pair.second).equals(str2);
    }

    public void resetAllowedListTable() {
        Trace.traceBegin(256L, "resetAllowedListTable");
        try {
            this.mDatabase.execSQL("delete from category_packages");
            this.mSoundAppPolicyAllowAppList = new CopyOnWriteArrayList();
        } finally {
            Trace.endSection();
        }
    }

    public boolean initCallPolicyAllowListFromDb() {
        if (this.mCallPolicyAllowList.isEmpty()) {
            try {
                Cursor query = this.mDatabase.query("call_policy_category_packages", new String[]{"_package", "_category"}, null, null, null, null, null);
                if (query != null) {
                    try {
                        if (query.moveToFirst()) {
                            do {
                                this.mCallPolicyAllowList.add(new Pair(query.getString(0), query.getString(1)));
                            } while (query.moveToNext());
                        }
                    } finally {
                    }
                }
                if (query != null) {
                    query.close();
                }
            } catch (Exception e) {
                Log.e("AudioService.DB", "DB error", e);
            }
        }
        return !this.mCallPolicyAllowList.isEmpty();
    }

    public void putCallPolicyAllowList(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_package", str);
        contentValues.put("_category", str2);
        add("call_policy_category_packages", contentValues);
        this.mCallPolicyAllowList.add(new Pair(str, str2));
    }

    public boolean checkCallPolicyCategory(final String str, final String str2) {
        if (this.mCallPolicyAllowList.isEmpty()) {
            return false;
        }
        return this.mCallPolicyAllowList.stream().anyMatch(new Predicate() { // from class: com.samsung.android.server.audio.AudioSettingsHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$checkCallPolicyCategory$1;
                lambda$checkCallPolicyCategory$1 = AudioSettingsHelper.lambda$checkCallPolicyCategory$1(str, str2, (Pair) obj);
                return lambda$checkCallPolicyCategory$1;
            }
        });
    }

    public static /* synthetic */ boolean lambda$checkCallPolicyCategory$1(String str, String str2, Pair pair) {
        return ((String) pair.first).equals(str) && ((String) pair.second).equals(str2);
    }

    public void resetCallPolicyTable() {
        Trace.traceBegin(256L, "resetCallPolicyTable");
        try {
            this.mDatabase.execSQL("delete from call_policy_category_packages");
            this.mCallPolicyAllowList = new CopyOnWriteArrayList();
        } finally {
            Trace.endSection();
        }
    }

    public boolean isCallPolicyEmpty() {
        return this.mCallPolicyAllowList.isEmpty();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x006a, code lost:
    
        if (r6 == false) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x006c, code lost:
    
        android.util.Log.d("AudioService.DB", "init >> " + r9 + " : " + r10);
        android.provider.Settings.System.putIntForUser(r8, r9, r10, -2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x008e, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0067, code lost:
    
        if (r7 == null) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initSystemVibration(String str, int i) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean z = true;
        Cursor cursor = null;
        try {
            try {
                cursor = contentResolver.query(Uri.parse("content://settings/system/" + str), null, null, null, null);
                if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
                    cursor.getString(cursor.getColumnIndex("value"));
                    String string = cursor.getString(cursor.getColumnIndex("package"));
                    if (!"android".equals(string)) {
                        if (!"com.android.providers.settings".equals(string)) {
                            z = false;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void dumpAppList(PrintWriter printWriter) {
        printWriter.print("  SCPM App List version = ");
        printWriter.println(getIntValue("APP_LIST_VERSION", 0));
        printWriter.print("  SCPM Live-Translate Allow List version = ");
        printWriter.println(getIntValue("LIVE_TRANSLATE_ALLOW_LIST_VERSION", 0));
    }
}

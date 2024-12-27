package com.android.server.locksettings;

import android.app.backup.BackupManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.UserInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.IRemoteCallback;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Slog;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.enterprise.container.KnoxMUMContainerPolicy$$ExternalSyntheticOutline0;
import com.android.server.pdb.PersistentDataBlockManagerInternal;
import com.android.server.pdb.PersistentDataBlockService;

import com.samsung.android.knox.dar.VirtualLockUtils;
import com.samsung.android.lock.LsLog;
import com.samsung.android.lock.SPBnRManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LockSettingsStorage {
    public LockSettingsService.Injector.AnonymousClass1 mCallback;
    public final Context mContext;
    public IRemoteCallback mLockTypeCallback;
    public DatabaseHelper mOpenHelper;
    public PersistentDataBlockManagerInternal mPersistentDataBlockManagerInternal;
    public static final String[] COLUMNS_FOR_QUERY = {"value"};
    public static final String[] COLUMNS_FOR_PREFETCH = {"name", "value"};
    public static final Object DEFAULT = new Object();
    public static final String[] SETTINGS_TO_BACKUP = {
        "lock_screen_owner_info_enabled",
        "lock_screen_owner_info",
        "lock_pattern_visible_pattern",
        "lockscreen.power_button_instantly_locks"
    };
    public static final IvParameterSpec ivParamSpec =
            new IvParameterSpec("i_love_office_tg".getBytes());
    public static final String[] CACHED_KEY_TO_LOCKSCREEN = {
        "lockscreen.samsung_biometric",
        "lockscreen.lockout_biometric_attempt_deadline",
        "lockscreen.lockoutattemptdeadline",
        "lock_screen_owner_info_enabled",
        "lock_screen_owner_info",
        "lockscreen.device_owner_info",
        "lockscreen.disabled"
    };
    public static final int[] SECURE_STATE = {16, 64, 32, 128, 128, 256, 512};
    public static final String[] KEY_TO_DB_BACKUP = {
        "locksettings_db_backup",
        "lockscreen.password_salt",
        "lockscreen.samsung_biometric",
        "migrated_all_users_to_sp_and_bound_ce",
        "migrated_all_users_to_sp_and_bound_keys"
    };
    public static final String[] KEY_TO_DB_RESTORE = {"locksettings_db_restore"};
    public final Cache mCache = new Cache();
    public final Object mFileWriteLock = new Object();
    public int mSKTLockState = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Cache {
        public final ArrayMap mCache = new ArrayMap();
        public final CacheKey mCacheKey = new CacheKey();
        public int mVersion = 0;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class CacheKey {
            public String key;
            public int type;
            public int userId;

            public final boolean equals(Object obj) {
                if (!(obj instanceof CacheKey)) {
                    return false;
                }
                CacheKey cacheKey = (CacheKey) obj;
                return this.userId == cacheKey.userId
                        && this.type == cacheKey.type
                        && Objects.equals(this.key, cacheKey.key);
            }

            public final int hashCode() {
                return (((Objects.hashCode(this.key) * 31) + this.userId) * 31) + this.type;
            }
        }

        public final synchronized boolean contains(int i, int i2, String str) {
            ArrayMap arrayMap;
            CacheKey cacheKey;
            arrayMap = this.mCache;
            cacheKey = this.mCacheKey;
            cacheKey.type = i;
            cacheKey.key = str;
            cacheKey.userId = i2;
            return arrayMap.containsKey(cacheKey);
        }

        public final synchronized void purgePath(File file) {
            try {
                String file2 = file.toString();
                for (int size = this.mCache.size() - 1; size >= 0; size--) {
                    CacheKey cacheKey = (CacheKey) this.mCache.keyAt(size);
                    if (cacheKey.type == 1 && cacheKey.key.startsWith(file2)) {
                        this.mCache.removeAt(size);
                    }
                }
                this.mVersion++;
            } catch (Throwable th) {
                throw th;
            }
        }

        public final synchronized void put(int i, String str, Object obj, int i2) {
            ArrayMap arrayMap = this.mCache;
            CacheKey cacheKey = new CacheKey();
            cacheKey.type = i;
            cacheKey.key = str;
            cacheKey.userId = i2;
            arrayMap.put(cacheKey, obj);
            this.mVersion++;
        }

        public final synchronized void putIfUnchanged(
                int i, String str, Object obj, int i2, int i3) {
            if (!contains(i, i2, str) && this.mVersion == i3) {
                ArrayMap arrayMap = this.mCache;
                CacheKey cacheKey = new CacheKey();
                cacheKey.type = i;
                cacheKey.key = str;
                cacheKey.userId = i2;
                arrayMap.put(cacheKey, obj);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DatabaseHelper extends SQLiteOpenHelper {
        public LockSettingsService.Injector.AnonymousClass1 mCallback;

        public DatabaseHelper(Context context) {
            super(context, "locksettings.db", (SQLiteDatabase.CursorFactory) null, 3);
            setWriteAheadLoggingEnabled(false);
            setIdleConnectionTimeout(30000L);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(
                    "CREATE TABLE locksettings (_id INTEGER PRIMARY KEY AUTOINCREMENT,name"
                        + " TEXT,user INTEGER,value TEXT);");
            LockSettingsService.Injector.AnonymousClass1 anonymousClass1 = this.mCallback;
            if (anonymousClass1 != null) {
                anonymousClass1.getClass();
                if (SystemProperties.getBoolean("ro.lockscreen.disable.default", false)) {
                    ((LockSettingsStorage) anonymousClass1.val$storage)
                            .writeKeyValue(sQLiteDatabase, "lockscreen.disabled", "1", 0);
                }
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            if (i == 1) {
                i = 2;
            }
            if (i != 3) {
                Slog.w("LockSettingsDB", "Failed to upgrade database!");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PersistentData {
        public static final PersistentData NONE = new PersistentData(0, -10000, 0, null);
        public final byte[] payload;
        public final int qualityForUi;
        public final int type;
        public final int userId;

        public PersistentData(int i, int i2, int i3, byte[] bArr) {
            this.type = i;
            this.userId = i2;
            this.qualityForUi = i3;
            this.payload = bArr;
        }

        public static PersistentData fromBytes(byte[] bArr) {
            PersistentData persistentData = NONE;
            if (bArr != null && bArr.length != 0) {
                DataInputStream dataInputStream =
                        new DataInputStream(new ByteArrayInputStream(bArr));
                try {
                    byte readByte = dataInputStream.readByte();
                    if (readByte != 1) {
                        Slog.wtf(
                                "LockSettingsStorage",
                                "Unknown PersistentData version code: " + ((int) readByte));
                        return persistentData;
                    }
                    int readByte2 = dataInputStream.readByte() & 255;
                    int readInt = dataInputStream.readInt();
                    int readInt2 = dataInputStream.readInt();
                    int length = bArr.length - 10;
                    byte[] bArr2 = new byte[length];
                    System.arraycopy(bArr, 10, bArr2, 0, length);
                    return new PersistentData(readByte2, readInt, readInt2, bArr2);
                } catch (IOException e) {
                    Slog.wtf("LockSettingsStorage", "Could not parse PersistentData", e);
                }
            }
            return persistentData;
        }

        public static byte[] toBytes(int i, int i2, int i3, byte[] bArr) {
            if (i == 0) {
                Preconditions.checkArgument(bArr == null, "TYPE_NONE must have empty payload");
                return null;
            }
            if (bArr != null && bArr.length > 0) {
                r0 = true;
            }
            Preconditions.checkArgument(r0, "empty payload must only be used with TYPE_NONE");
            ByteArrayOutputStream byteArrayOutputStream =
                    new ByteArrayOutputStream(bArr.length + 10);
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeByte(1);
                dataOutputStream.writeByte(i);
                dataOutputStream.writeInt(i2);
                dataOutputStream.writeInt(i3);
                dataOutputStream.write(bArr);
                return byteArrayOutputStream.toByteArray();
            } catch (IOException unused) {
                throw new IllegalStateException("ByteArrayOutputStream cannot throw IOException");
            }
        }
    }

    public LockSettingsStorage(Context context) {
        this.mContext = context;
        this.mOpenHelper = new DatabaseHelper(context);
    }

    public static void fsyncDirectory(File file) {
        try {
            FileChannel open = FileChannel.open(file.toPath(), StandardOpenOption.READ);
            try {
                open.force(true);
                open.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.e("LockSettingsStorage", "Error syncing directory: " + file, e);
            LsLog.enroll("Error syncing directory: " + file + "\n" + e);
        }
    }

    public static SecretKeySpec getCarrierLockKey() {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update("SKT : Find lost phone plus !!!".getBytes());
        return new SecretKeySpec(messageDigest.digest(), "AES");
    }

    public static File getLockCredentialFileForUser(int i, String str) {
        return i == 0
                ? new File(Environment.getDataSystemDirectory(), str)
                : new File(Environment.getUserSystemDirectory(i), str);
    }

    public void clearCache() {
        Cache cache = this.mCache;
        synchronized (cache) {
            cache.mCache.clear();
            cache.mVersion++;
        }
    }

    public void closeDatabase() {
        this.mOpenHelper.close();
    }

    public final String decryptCarrierLockMsg(String str) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, getCarrierLockKey(), ivParamSpec);
            return new String(cipher.doFinal(Base64.decode(str, 0)));
        } catch (InvalidAlgorithmParameterException e) {
            Slog.i(
                    "LockSettingsStorage",
                    "sec_encrypt.decrypt() InvalidAlgorithmParameterException = " + e.toString());
            return null;
        } catch (InvalidKeyException e2) {
            Slog.i(
                    "LockSettingsStorage",
                    "sec_encrypt.decrypt() InvalidKeyException = " + e2.toString());
            return null;
        } catch (NoSuchAlgorithmException e3) {
            Slog.i(
                    "LockSettingsStorage",
                    "sec_encrypt.decrypt() NoSuchAlgorithmException = " + e3.toString());
            return null;
        } catch (NoSuchPaddingException e4) {
            Slog.i(
                    "LockSettingsStorage",
                    "sec_encrypt.decrypt() NoSuchPaddingException = " + e4.toString());
            return null;
        } catch (Exception e5) {
            Slog.i("LockSettingsStorage", "sec_encrypt.decrypt() Exception = " + e5.toString());
            return null;
        }
    }

    public final void deleteFile(File file) {
        synchronized (this.mFileWriteLock) {
            SPBnRManager.deleteFile(file);
            Cache cache = this.mCache;
            cache.getClass();
            cache.put(1, file.toString(), null, -1);
        }
    }

    public final String getBackupDbName() {
        StringBuilder sb = new StringBuilder();
        String str = "/data/sec_backup_sys_de/locksetting/";
        File file = new File("/data/sec_backup_sys_de/locksetting/");
        if (!file.exists() && !file.mkdir()) {
            LsLog.restore("LockSettingsDB, Failed mkdir : /data/sec_backup_sys_de/locksetting/");
            str = "/data/system/";
        }
        sb.append(str);
        sb.append(this.mOpenHelper.getDatabaseName());
        sb.append(".bak");
        return sb.toString();
    }

    public final boolean getBoolean(String str, boolean z, int i) {
        String string = getString(str, null, i);
        return TextUtils.isEmpty(string) ? z : string.equals("1") || string.equals("true");
    }

    public final boolean getCarrierLockFromFile() {
        boolean z = true;
        if (BatteryService$$ExternalSyntheticOutline0.m45m(
                "/efs/sec_efs/sktdm_mem/enclawlock.txt")) {
            File file = new File("/efs/sec_efs/sktdm_mem/enclawlock.txt");
            if (!file.exists()) {
                return false;
            }
            try {
                String decryptCarrierLockMsg =
                        decryptCarrierLockMsg(FileUtils.readTextFile(file, 32, null));
                if (decryptCarrierLockMsg == null || !decryptCarrierLockMsg.contains("ON")) {
                    z = false;
                } else {
                    Slog.d("LockSettingsStorage", "getCarrierLock() is true");
                }
                return z;
            } catch (IOException unused) {
                Slog.e("LockSettingsStorage", "IOException while read file");
                return false;
            }
        }
        File file2 = new File("/efs/sec_efs/sktdm_mem/lawlock.txt");
        if (!file2.exists()) {
            return false;
        }
        try {
            String readTextFile = FileUtils.readTextFile(file2, 32, null);
            if (readTextFile == null || !readTextFile.contains("ON")) {
                z = false;
            } else {
                Slog.d("LockSettingsStorage", "getCarrierLock() is true");
            }
            return z;
        } catch (IOException unused2) {
            Slog.e("LockSettingsStorage", "IOException while read file");
            return false;
        }
    }

    public File getChildProfileLockFile(int i) {
        return getLockCredentialFileForUser(i, "gatekeeper.profile.key");
    }

    public final int getInt(int i, int i2, String str) {
        String string = getString(str, null, i2);
        return TextUtils.isEmpty(string) ? i : Integer.parseInt(string);
    }

    public final String getOriginDbName() {
        return "/data/system/" + this.mOpenHelper.getDatabaseName();
    }

    public final PersistentDataBlockManagerInternal getPersistentDataBlockManager() {
        if (this.mPersistentDataBlockManagerInternal == null) {
            this.mPersistentDataBlockManagerInternal =
                    (PersistentDataBlockManagerInternal)
                            LocalServices.getService(PersistentDataBlockManagerInternal.class);
        }
        return this.mPersistentDataBlockManagerInternal;
    }

    public File getRebootEscrowFile(int i) {
        return getLockCredentialFileForUser(i, "reboot.escrow.key");
    }

    public File getRebootEscrowServerBlobFile() {
        return getLockCredentialFileForUser(0, "reboot.escrow.server.blob.key");
    }

    public File getRepairModePersistentDataFile() {
        return new File(new File(Environment.getMetadataDirectory(), "repair-mode/"), "pst");
    }

    public final String getString(String str, String str2, int i) {
        if (LockPatternUtils.isSpecialUserId(i)) {
            return null;
        }
        return readKeyValue(str, str2, i);
    }

    public File getSyntheticPasswordDirectoryForUser(int i) {
        try {
            UserInfo userInfo =
                    ((UserManager) this.mContext.getSystemService("user")).getUserInfo(i);
            if (userInfo != null && userInfo.isVirtualUser()) {
                return Environment.buildPath(
                        Environment.getDataSystemDirectory(),
                        new String[] {"users", Integer.toString(i), "spblob/"});
            }
        } catch (Exception unused) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    i, "Unexpected error while get sp path for user ", "LockSettingsStorage");
        }
        return new File(Environment.getDataSystemDeDirectory(i), "spblob/");
    }

    public final File getSyntheticPasswordStateFileForUser(int i, String str, long j) {
        return new File(
                getSyntheticPasswordDirectoryForUser(i),
                TextUtils.formatSimple("%016x.%s", new Object[] {Long.valueOf(j), str}));
    }

    public final boolean hasFile(File file) {
        byte[] readFile = readFile(file);
        return readFile != null && readFile.length > 0;
    }

    public final boolean hasLockSettingsBackup() {
        if (new File(getBackupDbName()).exists()) {
            Slog.w("LockSettingsStorage", "LockSettingsDB, hasLockSettingsBackup : true");
            return true;
        }
        Slog.w(
                "LockSettingsStorage",
                "LockSettingsDB, hasLockSettingsBackup : false, Req Db = " + getBackupDbName());
        return false;
    }

    public boolean isAutoPinConfirmSettingEnabled(int i) {
        return getBoolean("lockscreen.auto_pin_confirm", false, i);
    }

    public boolean isKeyValueCached(String str, int i) {
        return this.mCache.contains(0, i, str);
    }

    public boolean isUserPrefetched(int i) {
        return this.mCache.contains(2, i, "");
    }

    public final Map listSyntheticPasswordProtectorsForAllUsers(String str) {
        ArrayMap arrayMap = new ArrayMap();
        for (UserInfo userInfo : UserManager.get(this.mContext).getUsers()) {
            arrayMap.put(
                    Integer.valueOf(userInfo.id),
                    listSyntheticPasswordProtectorsForUser(userInfo.id, str));
        }
        for (int i : new VirtualLockUtils().getVirtualUsers()) {
            arrayMap.put(Integer.valueOf(i), listSyntheticPasswordProtectorsForUser(i, str));
        }
        return arrayMap;
    }

    public final List listSyntheticPasswordProtectorsForUser(int i, String str) {
        File syntheticPasswordDirectoryForUser = getSyntheticPasswordDirectoryForUser(i);
        ArrayList arrayList = new ArrayList();
        File[] listFiles = syntheticPasswordDirectoryForUser.listFiles();
        if (listFiles == null) {
            return arrayList;
        }
        for (File file : listFiles) {
            String[] split = file.getName().split("\\.");
            if (split.length == 2 && split[1].equals(str)) {
                try {
                    arrayList.add(Long.valueOf(Long.parseUnsignedLong(split[0], 16)));
                } catch (NumberFormatException unused) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                            new StringBuilder("Failed to parse protector ID "),
                            split[0],
                            "LockSettingsStorage");
                }
            }
        }
        return arrayList;
    }

    public final void prefetchUser(int i) {
        int i2;
        synchronized (this.mCache) {
            try {
                if (this.mCache.contains(2, i, "")) {
                    return;
                }
                this.mCache.put(2, "", "true", i);
                Cache cache = this.mCache;
                synchronized (cache) {
                    i2 = cache.mVersion;
                }
                Cursor query =
                        this.mOpenHelper
                                .getReadableDatabase()
                                .query(
                                        "locksettings",
                                        COLUMNS_FOR_PREFETCH,
                                        "user=?",
                                        new String[] {Integer.toString(i)},
                                        null,
                                        null,
                                        null);
                if (query != null) {
                    while (query.moveToNext()) {
                        this.mCache.putIfUnchanged(
                                0, query.getString(0), query.getString(1), i, i2);
                    }
                    query.close();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0087  */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.io.RandomAccessFile] */
    /* JADX WARN: Type inference failed for: r1v9, types: [android.util.ArrayMap] */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v11, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte[] readFile(java.io.File r10) {
        /*
            r9 = this;
            com.android.server.locksettings.LockSettingsStorage$Cache r0 = r9.mCache
            monitor-enter(r0)
            com.android.server.locksettings.LockSettingsStorage$Cache r1 = r9.mCache     // Catch: java.lang.Throwable -> L39
            r1.getClass()     // Catch: java.lang.Throwable -> L39
            java.lang.String r2 = r10.toString()     // Catch: java.lang.Throwable -> L39
            r3 = 1
            r4 = -1
            boolean r1 = r1.contains(r3, r4, r2)     // Catch: java.lang.Throwable -> L39
            r2 = 0
            if (r1 == 0) goto L3e
            com.android.server.locksettings.LockSettingsStorage$Cache r9 = r9.mCache     // Catch: java.lang.Throwable -> L39
            r9.getClass()     // Catch: java.lang.Throwable -> L39
            java.lang.String r10 = r10.toString()     // Catch: java.lang.Throwable -> L39
            monitor-enter(r9)     // Catch: java.lang.Throwable -> L39
            android.util.ArrayMap r1 = r9.mCache     // Catch: java.lang.Throwable -> L3b
            com.android.server.locksettings.LockSettingsStorage$Cache$CacheKey r5 = r9.mCacheKey     // Catch: java.lang.Throwable -> L3b
            r5.type = r3     // Catch: java.lang.Throwable -> L3b
            r5.key = r10     // Catch: java.lang.Throwable -> L3b
            r5.userId = r4     // Catch: java.lang.Throwable -> L3b
            java.lang.Object r10 = r1.get(r5)     // Catch: java.lang.Throwable -> L3b
            monitor-exit(r9)     // Catch: java.lang.Throwable -> L39
            byte[] r10 = (byte[]) r10     // Catch: java.lang.Throwable -> L39
            if (r10 == 0) goto L37
            int r9 = r10.length     // Catch: java.lang.Throwable -> L39
            byte[] r2 = java.util.Arrays.copyOf(r10, r9)     // Catch: java.lang.Throwable -> L39
        L37:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L39
            return r2
        L39:
            r9 = move-exception
            goto L96
        L3b:
            r10 = move-exception
            monitor-exit(r9)     // Catch: java.lang.Throwable -> L39
            throw r10     // Catch: java.lang.Throwable -> L39
        L3e:
            com.android.server.locksettings.LockSettingsStorage$Cache r1 = r9.mCache     // Catch: java.lang.Throwable -> L39
            monitor-enter(r1)     // Catch: java.lang.Throwable -> L39
            int r8 = r1.mVersion     // Catch: java.lang.Throwable -> L93
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L39
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L39
            java.io.RandomAccessFile r0 = new java.io.RandomAccessFile     // Catch: java.io.IOException -> L6f java.io.FileNotFoundException -> L72
            java.lang.String r1 = "r"
            r0.<init>(r10, r1)     // Catch: java.io.IOException -> L6f java.io.FileNotFoundException -> L72
            long r3 = r0.length()     // Catch: java.lang.Throwable -> L64
            int r1 = (int) r3     // Catch: java.lang.Throwable -> L64
            byte[] r3 = new byte[r1]     // Catch: java.lang.Throwable -> L64
            r4 = 0
            r0.readFully(r3, r4, r1)     // Catch: java.lang.Throwable -> L62
            r0.close()     // Catch: java.lang.Throwable -> L62
            r0.close()     // Catch: java.io.FileNotFoundException -> L5e java.io.IOException -> L60
        L5e:
            r0 = r3
            goto L7c
        L60:
            r0 = move-exception
            goto L74
        L62:
            r1 = move-exception
            goto L66
        L64:
            r1 = move-exception
            r3 = r2
        L66:
            r0.close()     // Catch: java.lang.Throwable -> L6a
            goto L6e
        L6a:
            r0 = move-exception
            r1.addSuppressed(r0)     // Catch: java.io.FileNotFoundException -> L5e java.io.IOException -> L60
        L6e:
            throw r1     // Catch: java.io.FileNotFoundException -> L5e java.io.IOException -> L60
        L6f:
            r0 = move-exception
            r3 = r2
            goto L74
        L72:
            r3 = r2
            goto L5e
        L74:
            java.lang.String r1 = "LockSettingsStorage"
            java.lang.String r4 = "Cannot read file "
            com.android.server.BootReceiver$$ExternalSyntheticOutline0.m(r4, r0, r1)
            goto L5e
        L7c:
            com.android.server.locksettings.LockSettingsStorage$Cache r3 = r9.mCache
            r3.getClass()
            java.lang.String r5 = r10.toString()
            if (r0 == 0) goto L8c
            int r9 = r0.length
            byte[] r2 = java.util.Arrays.copyOf(r0, r9)
        L8c:
            r6 = r2
            r7 = -1
            r4 = 1
            r3.putIfUnchanged(r4, r5, r6, r7, r8)
            return r0
        L93:
            r9 = move-exception
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L39
            throw r9     // Catch: java.lang.Throwable -> L39
        L96:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L39
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.locksettings.LockSettingsStorage.readFile(java.io.File):byte[]");
    }

    public String readKeyValue(String str, String str2, int i) {
        int i2;
        Object obj;
        Object obj2;
        synchronized (this.mCache) {
            try {
                if (this.mCache.contains(0, i, str)) {
                    Cache cache = this.mCache;
                    synchronized (cache) {
                        ArrayMap arrayMap = cache.mCache;
                        Cache.CacheKey cacheKey = cache.mCacheKey;
                        cacheKey.type = 0;
                        cacheKey.key = str;
                        cacheKey.userId = i;
                        obj2 = arrayMap.get(cacheKey);
                    }
                    return obj2 == DEFAULT ? str2 : (String) obj2;
                }
                Cache cache2 = this.mCache;
                synchronized (cache2) {
                    i2 = cache2.mVersion;
                }
                Object obj3 = DEFAULT;
                Cursor query =
                        this.mOpenHelper
                                .getReadableDatabase()
                                .query(
                                        "locksettings",
                                        COLUMNS_FOR_QUERY,
                                        "user=? AND name=?",
                                        new String[] {Integer.toString(i), str},
                                        null,
                                        null,
                                        null);
                if (query != null) {
                    Object string = query.moveToFirst() ? query.getString(0) : obj3;
                    query.close();
                    obj = string;
                } else {
                    obj = obj3;
                }
                this.mCache.putIfUnchanged(0, str, obj, i, i2);
                return obj == obj3 ? str2 : (String) obj;
            } finally {
            }
        }
    }

    public final PersistentData readPersistentDataBlock() {
        PersistentDataBlockManagerInternal persistentDataBlockManager =
                getPersistentDataBlockManager();
        PersistentData persistentData = PersistentData.NONE;
        if (persistentDataBlockManager == null) {
            return persistentData;
        }
        try {
            PersistentDataBlockService.InternalService internalService =
                    (PersistentDataBlockService.InternalService) persistentDataBlockManager;
            return PersistentData.fromBytes(
                    internalService.readInternal(
                            996, PersistentDataBlockService.this.getFrpCredentialDataOffset()));
        } catch (IllegalStateException e) {
            Slog.e("LockSettingsStorage", "Error reading persistent data block", e);
            return persistentData;
        }
    }

    public final byte[] readSyntheticPasswordState(int i, String str, long j) {
        return readFile(getSyntheticPasswordStateFileForUser(i, str, j));
    }

    public void removeKey(String str, int i) {
        SQLiteDatabase writableDatabase = this.mOpenHelper.getWritableDatabase();
        AccountManagerService$$ExternalSyntheticOutline0.m("name", str)
                .put("user", Integer.valueOf(i));
        writableDatabase.beginTransaction();
        try {
            writableDatabase.delete(
                    "locksettings", "name=? AND user=?", new String[] {str, Integer.toString(i)});
            writableDatabase.setTransactionSuccessful();
            Cache cache = this.mCache;
            synchronized (cache) {
                ArrayMap arrayMap = cache.mCache;
                Cache.CacheKey cacheKey = cache.mCacheKey;
                cacheKey.type = 0;
                cacheKey.key = str;
                cacheKey.userId = i;
                arrayMap.remove(cacheKey);
                cache.mVersion++;
            }
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public final void removeUser(int i) {
        SQLiteDatabase writableDatabase = this.mOpenHelper.getWritableDatabase();
        if (((UserManager) this.mContext.getSystemService("user")).getProfileParent(i) == null) {
            deleteFile(getRebootEscrowFile(i));
        } else {
            deleteFile(getChildProfileLockFile(i));
        }
        File syntheticPasswordDirectoryForUser = getSyntheticPasswordDirectoryForUser(i);
        try {
            writableDatabase.beginTransaction();
            writableDatabase.delete("locksettings", "user='" + i + "'", null);
            writableDatabase.setTransactionSuccessful();
            Cache cache = this.mCache;
            synchronized (cache) {
                try {
                    for (int size = cache.mCache.size() - 1; size >= 0; size--) {
                        if (((Cache.CacheKey) cache.mCache.keyAt(size)).userId == i) {
                            cache.mCache.removeAt(size);
                        }
                    }
                    cache.mVersion++;
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mCache.purgePath(syntheticPasswordDirectoryForUser);
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public final void sendLockTypeChangedInfo(int i) {
        if (this.mLockTypeCallback == null) {
            Slog.d("LockSettingsStorage", "mLockTypeCallback is null!!");
            return;
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("secureState", i);
            this.mLockTypeCallback.sendResult(bundle);
        } catch (RemoteException e) {
            Slog.d(
                    "LockSettingsStorage",
                    "sendLockTypeChangedInfo failed!!  mLockTypeCallback = "
                            + this.mLockTypeCallback);
            e.printStackTrace();
        }
    }

    public final void setString(String str, String str2, int i) {
        Object obj;
        Preconditions.checkArgument(
                !LockPatternUtils.isSpecialUserId(i),
                "cannot store lock settings for special user: %d",
                new Object[] {Integer.valueOf(i)});
        if (ArrayUtils.contains(KEY_TO_DB_RESTORE, str)) {
            LsLog.restore("LockSettingsDB, Restore! User " + i + ", " + str);
            if (!new File(getBackupDbName()).exists()) {
                LsLog.restore(
                        "LockSettingsDB, restore fail : no backuped DB File, " + getBackupDbName(),
                        true);
            } else if (((ArrayList) listSyntheticPasswordProtectorsForUser(0, "spblob")).size()
                    < 1) {
                LsLog.restore(
                        "LockSettingsDB, restore fail : no protector Id. Can not restore DB!",
                        true);
            } else {
                int semRestoreDatabaseFile =
                        SQLiteDatabase.semRestoreDatabaseFile(getBackupDbName(), getOriginDbName());
                if (semRestoreDatabaseFile != 0) {
                    LsLog.restore(
                            "LockSettingsDB, restore fail : semRestoreDatabaseFile ret = "
                                    + semRestoreDatabaseFile,
                            true);
                } else {
                    Slog.e("LockSettingsStorage", "LockSettingsDB, semRestoreDatabaseFile Success");
                    if (this.mOpenHelper.getWritableDatabase().diagnoseError()) {
                        Slog.e("LockSettingsStorage", "LockSettingsDB, diagnoseError() Success");
                    } else {
                        Slog.e(
                                "LockSettingsStorage",
                                "LockSettingsDB, diagnoseError() failed! reopen DB Helper!");
                        DatabaseHelper databaseHelper = new DatabaseHelper(this.mContext);
                        this.mOpenHelper = databaseHelper;
                        databaseHelper.mCallback = this.mCallback;
                    }
                    Slog.e("LockSettingsStorage", "LockSettingsDB, clearCache() Success");
                    clearCache();
                    LsLog.restore("LockSettingsDB, restore Success!", true);
                    sendLockTypeChangedInfo(4094);
                }
            }
            LsLog.restore("LockSettingsDB restore failed!");
            return;
        }
        writeKeyValue(str, str2, i);
        if (ArrayUtils.contains(SETTINGS_TO_BACKUP, str)) {
            BackupManager.dataChanged("com.android.providers.settings");
        }
        int indexOf = ArrayUtils.indexOf(CACHED_KEY_TO_LOCKSCREEN, str);
        if (indexOf >= 0) {
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    "sendLockTypeChangedInfo : ", str, " = ", str2, "LockSettingsStorage");
            sendLockTypeChangedInfo(SECURE_STATE[indexOf]);
        }
        if (ArrayUtils.contains(KEY_TO_DB_BACKUP, str)) {
            LsLog.restore("LockSettingsDB, Backup! User " + i + ", " + str);
            if (new File(getOriginDbName()).exists()) {
                Object obj2 = DEFAULT;
                Cursor query =
                        this.mOpenHelper
                                .getReadableDatabase()
                                .query(
                                        "locksettings",
                                        COLUMNS_FOR_QUERY,
                                        "user=? AND name=?",
                                        new String[] {Integer.toString(0), "sp-handle"},
                                        null,
                                        null,
                                        null);
                if (query != null) {
                    obj = query.moveToFirst() ? query.getString(0) : obj2;
                    query.close();
                } else {
                    obj = obj2;
                }
                String str3 = obj == obj2 ? null : (String) obj;
                long parseLong = TextUtils.isEmpty(str3) ? 0L : Long.parseLong(str3);
                if (parseLong == 0
                        || ArrayUtils.isEmpty(readSyntheticPasswordState(0, "spblob", parseLong))) {
                    LsLog.restore("LockSettingsDB, current protector id is null!");
                } else {
                    int semBackupDatabaseFile =
                            SQLiteDatabase.semBackupDatabaseFile(
                                    getOriginDbName(), getBackupDbName());
                    if (semBackupDatabaseFile == 0) {
                        LsLog.restore("LockSettingsDB, backup success!");
                        return;
                    } else {
                        LsLog.restore(
                                "LockSettingsDB, Failed semBackupDatabaseFile : error code = "
                                        + semBackupDatabaseFile);
                    }
                }
            } else {
                LsLog.restore(
                        "LockSettingsDB, backup failed : no sourceFile, " + getOriginDbName());
            }
            LsLog.restore("LockSettingsDB, Backup failed! It will try again on the next reboot.");
            removeKey("locksettings_db_backup", 0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0078 A[Catch: all -> 0x0028, TryCatch #2 {all -> 0x0028, blocks: (B:4:0x000a, B:6:0x0010, B:7:0x002b, B:13:0x003b, B:14:0x0073, B:16:0x0078, B:17:0x007f, B:19:0x008a, B:20:0x008f, B:21:0x0094, B:31:0x0096, B:32:0x0099, B:27:0x0070), top: B:3:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x008a A[Catch: all -> 0x0028, TryCatch #2 {all -> 0x0028, blocks: (B:4:0x000a, B:6:0x0010, B:7:0x002b, B:13:0x003b, B:14:0x0073, B:16:0x0078, B:17:0x007f, B:19:0x008a, B:20:0x008f, B:21:0x0094, B:31:0x0096, B:32:0x0099, B:27:0x0070), top: B:3:0x000a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void writeFile(java.io.File r11, byte[] r12, boolean r13) {
        /*
            r10 = this;
            java.lang.String r0 = "Error writing file "
            java.lang.String r1 = "Error writing file "
            java.lang.String r2 = "writing file : "
            java.lang.Object r3 = r10.mFileWriteLock
            monitor-enter(r3)
            java.io.File r4 = com.samsung.android.lock.SPBnRManager.startWrite(r11)     // Catch: java.lang.Throwable -> L28
            if (r4 == 0) goto L2b
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L28
            r5.<init>(r2)     // Catch: java.lang.Throwable -> L28
            r5.append(r11)     // Catch: java.lang.Throwable -> L28
            java.lang.String r2 = ", bak : "
            r5.append(r2)     // Catch: java.lang.Throwable -> L28
            r5.append(r4)     // Catch: java.lang.Throwable -> L28
            java.lang.String r2 = r5.toString()     // Catch: java.lang.Throwable -> L28
            com.samsung.android.lock.LsLog.enroll(r2)     // Catch: java.lang.Throwable -> L28
            goto L2b
        L28:
            r10 = move-exception
            goto L9a
        L2b:
            android.util.AtomicFile r2 = new android.util.AtomicFile     // Catch: java.lang.Throwable -> L28
            r2.<init>(r11)     // Catch: java.lang.Throwable -> L28
            r5 = 0
            java.io.FileOutputStream r6 = r2.startWrite()     // Catch: java.lang.Throwable -> L44 java.io.IOException -> L46
            r6.write(r12)     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L42
            r2.finishWrite(r6)     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L42
            r2.failWrite(r5)     // Catch: java.lang.Throwable -> L28
            goto L73
        L3f:
            r10 = move-exception
            r5 = r6
            goto L96
        L42:
            r7 = move-exception
            goto L48
        L44:
            r10 = move-exception
            goto L96
        L46:
            r7 = move-exception
            r6 = r5
        L48:
            java.lang.String r8 = "LockSettingsStorage"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3f
            r9.<init>(r1)     // Catch: java.lang.Throwable -> L3f
            r9.append(r11)     // Catch: java.lang.Throwable -> L3f
            java.lang.String r1 = r9.toString()     // Catch: java.lang.Throwable -> L3f
            android.util.Slog.e(r8, r1, r7)     // Catch: java.lang.Throwable -> L3f
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3f
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L3f
            r1.append(r11)     // Catch: java.lang.Throwable -> L3f
            java.lang.String r0 = "\n"
            r1.append(r0)     // Catch: java.lang.Throwable -> L3f
            r1.append(r7)     // Catch: java.lang.Throwable -> L3f
            java.lang.String r0 = r1.toString()     // Catch: java.lang.Throwable -> L3f
            com.samsung.android.lock.LsLog.enroll(r0)     // Catch: java.lang.Throwable -> L3f
            r2.failWrite(r6)     // Catch: java.lang.Throwable -> L28
        L73:
            com.samsung.android.lock.SPBnRManager.finishWrite(r4)     // Catch: java.lang.Throwable -> L28
            if (r13 == 0) goto L7f
            java.io.File r13 = r11.getParentFile()     // Catch: java.lang.Throwable -> L28
            fsyncDirectory(r13)     // Catch: java.lang.Throwable -> L28
        L7f:
            com.android.server.locksettings.LockSettingsStorage$Cache r10 = r10.mCache     // Catch: java.lang.Throwable -> L28
            r10.getClass()     // Catch: java.lang.Throwable -> L28
            java.lang.String r11 = r11.toString()     // Catch: java.lang.Throwable -> L28
            if (r12 == 0) goto L8f
            int r13 = r12.length     // Catch: java.lang.Throwable -> L28
            byte[] r5 = java.util.Arrays.copyOf(r12, r13)     // Catch: java.lang.Throwable -> L28
        L8f:
            r12 = -1
            r13 = 1
            r10.put(r13, r11, r5, r12)     // Catch: java.lang.Throwable -> L28
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L28
            return
        L96:
            r2.failWrite(r5)     // Catch: java.lang.Throwable -> L28
            throw r10     // Catch: java.lang.Throwable -> L28
        L9a:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L28
            throw r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.locksettings.LockSettingsStorage.writeFile(java.io.File,"
                    + " byte[], boolean):void");
    }

    public void writeKeyValue(SQLiteDatabase sQLiteDatabase, String str, String str2, int i) {
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("name", str);
        KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(i, m, "user", "value", str2);
        sQLiteDatabase.beginTransaction();
        try {
            sQLiteDatabase.delete(
                    "locksettings", "name=? AND user=?", new String[] {str, Integer.toString(i)});
            sQLiteDatabase.insert("locksettings", null, m);
            sQLiteDatabase.setTransactionSuccessful();
            this.mCache.put(0, str, str2, i);
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    public void writeKeyValue(String str, String str2, int i) {
        writeKeyValue(this.mOpenHelper.getWritableDatabase(), str, str2, i);
    }

    public final void writePersistentDataBlock(int i, int i2, int i3, byte[] bArr) {
        PersistentDataBlockManagerInternal persistentDataBlockManager =
                getPersistentDataBlockManager();
        if (persistentDataBlockManager == null) {
            return;
        }
        PersistentDataBlockService.InternalService internalService =
                (PersistentDataBlockService.InternalService) persistentDataBlockManager;
        internalService.writeInternal(
                PersistentDataBlockService.this.getFrpCredentialDataOffset(),
                PersistentData.toBytes(i, i2, i3, bArr),
                996);
    }
}

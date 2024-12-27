package com.android.server.locksettings.recoverablekeystore.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.android.server.am.Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0;
import com.android.server.locksettings.recoverablekeystore.TestOnlyInsecureCertificateHelper;
import com.android.server.locksettings.recoverablekeystore.WrappedKey;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.security.cert.CertPath;
import java.security.cert.CertificateEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RecoverableKeyStoreDb {
    public final RecoverableKeyStoreDbHelper mKeyStoreDbHelper;
    public final TestOnlyInsecureCertificateHelper mTestOnlyInsecureCertificateHelper =
            new TestOnlyInsecureCertificateHelper();

    public RecoverableKeyStoreDb(RecoverableKeyStoreDbHelper recoverableKeyStoreDbHelper) {
        this.mKeyStoreDbHelper = recoverableKeyStoreDbHelper;
    }

    public final void ensureRecoveryServiceMetadataEntryExists(int i, int i2) {
        SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                i, contentValues, "user_id", i2, "uid");
        writableDatabase.insertWithOnConflict("recovery_service_metadata", null, contentValues, 4);
    }

    public final void ensureRootOfTrustEntryExists(int i, int i2, String str) {
        SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                i, contentValues, "user_id", i2, "uid");
        contentValues.put("root_alias", str);
        writableDatabase.insertWithOnConflict("root_of_trust", null, contentValues, 4);
    }

    public final void ensureUserMetadataEntryExists(int i) {
        SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", Integer.valueOf(i));
        writableDatabase.insertWithOnConflict("user_metadata", null, contentValues, 4);
    }

    public final String getActiveRootOfTrust(int i, int i2) {
        Cursor query =
                this.mKeyStoreDbHelper
                        .getReadableDatabase()
                        .query(
                                "recovery_service_metadata",
                                new String[] {
                                    KnoxCustomManagerService.ID,
                                    "user_id",
                                    "uid",
                                    "active_root_of_trust"
                                },
                                "user_id = ? AND uid = ?",
                                new String[] {Integer.toString(i), Integer.toString(i2)},
                                null,
                                null,
                                null);
        try {
            int count = query.getCount();
            if (count == 0) {
                query.close();
                return null;
            }
            if (count > 1) {
                Locale locale = Locale.US;
                Log.wtf(
                        "RecoverableKeyStoreDb",
                        count
                                + " deviceId entries found for userId="
                                + i
                                + " uid="
                                + i2
                                + ". Should only ever be 0 or 1.");
                query.close();
                return null;
            }
            query.moveToFirst();
            int columnIndexOrThrow = query.getColumnIndexOrThrow("active_root_of_trust");
            if (query.isNull(columnIndexOrThrow)) {
                query.close();
                return null;
            }
            String string = query.getString(columnIndexOrThrow);
            if (TextUtils.isEmpty(string)) {
                query.close();
                return null;
            }
            query.close();
            return string;
        } catch (Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final Map getAllKeys(int i, int i2, int i3) {
        Cursor query =
                this.mKeyStoreDbHelper
                        .getReadableDatabase()
                        .query(
                                "keys",
                                new String[] {
                                    KnoxCustomManagerService.ID,
                                    "nonce",
                                    "wrapped_key",
                                    "alias",
                                    "recovery_status",
                                    "key_metadata"
                                },
                                "user_id = ? AND uid = ? AND platform_key_generation_id = ?",
                                new String[] {
                                    Integer.toString(i), Integer.toString(i2), Integer.toString(i3)
                                },
                                null,
                                null,
                                null);
        try {
            HashMap hashMap = new HashMap();
            while (query.moveToNext()) {
                byte[] blob = query.getBlob(query.getColumnIndexOrThrow("nonce"));
                byte[] blob2 = query.getBlob(query.getColumnIndexOrThrow("wrapped_key"));
                String string = query.getString(query.getColumnIndexOrThrow("alias"));
                int i4 = query.getInt(query.getColumnIndexOrThrow("recovery_status"));
                int columnIndexOrThrow = query.getColumnIndexOrThrow("key_metadata");
                hashMap.put(
                        string,
                        new WrappedKey(
                                blob,
                                blob2,
                                query.isNull(columnIndexOrThrow)
                                        ? null
                                        : query.getBlob(columnIndexOrThrow),
                                i3,
                                i4));
            }
            query.close();
            return hashMap;
        } catch (Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final int getBadRemoteGuessCounter(int i) {
        Cursor query =
                this.mKeyStoreDbHelper
                        .getReadableDatabase()
                        .query(
                                "user_metadata",
                                new String[] {"bad_remote_guess_counter"},
                                "user_id = ?",
                                new String[] {Integer.toString(i)},
                                null,
                                null,
                                null);
        try {
            if (query.getCount() == 0) {
                query.close();
                return 0;
            }
            query.moveToFirst();
            int i2 = query.getInt(query.getColumnIndexOrThrow("bad_remote_guess_counter"));
            query.close();
            return i2;
        } catch (Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final byte[] getBytes(int i, int i2, String str) {
        Cursor query =
                this.mKeyStoreDbHelper
                        .getReadableDatabase()
                        .query(
                                "recovery_service_metadata",
                                new String[] {KnoxCustomManagerService.ID, "user_id", "uid", str},
                                "user_id = ? AND uid = ?",
                                new String[] {Integer.toString(i), Integer.toString(i2)},
                                null,
                                null,
                                null);
        try {
            int count = query.getCount();
            if (count == 0) {
                query.close();
                return null;
            }
            if (count <= 1) {
                query.moveToFirst();
                int columnIndexOrThrow = query.getColumnIndexOrThrow(str);
                if (query.isNull(columnIndexOrThrow)) {
                    query.close();
                    return null;
                }
                byte[] blob = query.getBlob(columnIndexOrThrow);
                query.close();
                return blob;
            }
            Locale locale = Locale.US;
            Log.wtf(
                    "RecoverableKeyStoreDb",
                    count
                            + " entries found for userId="
                            + i
                            + " uid="
                            + i2
                            + ". Should only ever be 0 or 1.");
            query.close();
            return null;
        } catch (Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final Long getLong(int i, int i2, String str) {
        Cursor query =
                this.mKeyStoreDbHelper
                        .getReadableDatabase()
                        .query(
                                "recovery_service_metadata",
                                new String[] {KnoxCustomManagerService.ID, "user_id", "uid", str},
                                "user_id = ? AND uid = ?",
                                new String[] {Integer.toString(i), Integer.toString(i2)},
                                null,
                                null,
                                null);
        try {
            int count = query.getCount();
            if (count == 0) {
                query.close();
                return null;
            }
            if (count <= 1) {
                query.moveToFirst();
                int columnIndexOrThrow = query.getColumnIndexOrThrow(str);
                if (query.isNull(columnIndexOrThrow)) {
                    query.close();
                    return null;
                }
                Long valueOf = Long.valueOf(query.getLong(columnIndexOrThrow));
                query.close();
                return valueOf;
            }
            Locale locale = Locale.US;
            Log.wtf(
                    "RecoverableKeyStoreDb",
                    count
                            + " entries found for userId="
                            + i
                            + " uid="
                            + i2
                            + ". Should only ever be 0 or 1.");
            query.close();
            return null;
        } catch (Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final int getPlatformKeyGenerationId(int i) {
        Cursor query =
                this.mKeyStoreDbHelper
                        .getReadableDatabase()
                        .query(
                                "user_metadata",
                                new String[] {"platform_key_generation_id"},
                                "user_id = ?",
                                new String[] {Integer.toString(i)},
                                null,
                                null,
                                null);
        try {
            if (query.getCount() == 0) {
                query.close();
                return -1;
            }
            query.moveToFirst();
            int i2 = query.getInt(query.getColumnIndexOrThrow("platform_key_generation_id"));
            query.close();
            return i2;
        } catch (Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final List getRecoveryAgents(int i) {
        Cursor query =
                this.mKeyStoreDbHelper
                        .getReadableDatabase()
                        .query(
                                "recovery_service_metadata",
                                new String[] {"uid"},
                                "user_id = ?",
                                new String[] {Integer.toString(i)},
                                null,
                                null,
                                null);
        try {
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                arrayList.add(Integer.valueOf(query.getInt(query.getColumnIndexOrThrow("uid"))));
            }
            query.close();
            return arrayList;
        } catch (Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final int[] getRecoverySecretTypes(int i, int i2) {
        Cursor query =
                this.mKeyStoreDbHelper
                        .getReadableDatabase()
                        .query(
                                "recovery_service_metadata",
                                new String[] {
                                    KnoxCustomManagerService.ID, "user_id", "uid", "secret_types"
                                },
                                "user_id = ? AND uid = ?",
                                new String[] {Integer.toString(i), Integer.toString(i2)},
                                null,
                                null,
                                null);
        try {
            int count = query.getCount();
            if (count == 0) {
                int[] iArr = new int[0];
                query.close();
                return iArr;
            }
            if (count > 1) {
                Locale locale = Locale.US;
                Log.wtf(
                        "RecoverableKeyStoreDb",
                        count
                                + " deviceId entries found for userId="
                                + i
                                + " uid="
                                + i2
                                + ". Should only ever be 0 or 1.");
                int[] iArr2 = new int[0];
                query.close();
                return iArr2;
            }
            query.moveToFirst();
            int columnIndexOrThrow = query.getColumnIndexOrThrow("secret_types");
            if (query.isNull(columnIndexOrThrow)) {
                int[] iArr3 = new int[0];
                query.close();
                return iArr3;
            }
            String string = query.getString(columnIndexOrThrow);
            if (TextUtils.isEmpty(string)) {
                int[] iArr4 = new int[0];
                query.close();
                return iArr4;
            }
            String[] split = string.split(",");
            int[] iArr5 = new int[split.length];
            for (int i3 = 0; i3 < split.length; i3++) {
                try {
                    iArr5[i3] = Integer.parseInt(split[i3]);
                } catch (NumberFormatException e) {
                    Log.wtf("RecoverableKeyStoreDb", "String format error " + e);
                }
            }
            query.close();
            return iArr5;
        } catch (Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final Map getUserSerialNumbers() {
        Cursor query =
                this.mKeyStoreDbHelper
                        .getReadableDatabase()
                        .query(
                                "user_metadata",
                                new String[] {"user_id", "user_serial_number"},
                                null,
                                new String[0],
                                null,
                                null,
                                null);
        try {
            ArrayMap arrayMap = new ArrayMap();
            while (query.moveToNext()) {
                arrayMap.put(
                        Integer.valueOf(query.getInt(query.getColumnIndexOrThrow("user_id"))),
                        Long.valueOf(
                                query.getLong(query.getColumnIndexOrThrow("user_serial_number"))));
            }
            query.close();
            return arrayMap;
        } catch (Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final long insertKey(int i, int i2, String str, WrappedKey wrappedKey) {
        SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                i, contentValues, "user_id", i2, "uid");
        contentValues.put("alias", str);
        contentValues.put("nonce", wrappedKey.mNonce);
        contentValues.put("wrapped_key", wrappedKey.mKeyMaterial);
        contentValues.put("last_synced_at", (Integer) (-1));
        contentValues.put(
                "platform_key_generation_id", Integer.valueOf(wrappedKey.mPlatformKeyGenerationId));
        contentValues.put("recovery_status", Integer.valueOf(wrappedKey.mRecoveryStatus));
        byte[] bArr = wrappedKey.mKeyMetadata;
        if (bArr == null) {
            contentValues.putNull("key_metadata");
        } else {
            contentValues.put("key_metadata", bArr);
        }
        return writableDatabase.replace("keys", null, contentValues);
    }

    public final void setBadRemoteGuessCounter(int i, int i2) {
        SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                i, contentValues, "user_id", i2, "bad_remote_guess_counter");
        String[] strArr = {String.valueOf(i)};
        ensureUserMetadataEntryExists(i);
        writableDatabase.update("user_metadata", contentValues, "user_id = ?", strArr);
    }

    public final long setLong(int i, int i2, long j, String str) {
        SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        new ContentValues().put(str, Long.valueOf(j));
        String[] strArr = {Integer.toString(i), Integer.toString(i2)};
        ensureRecoveryServiceMetadataEntryExists(i, i2);
        return writableDatabase.update(
                "recovery_service_metadata", r1, "user_id = ? AND uid = ?", strArr);
    }

    public final long setRecoveryServiceCertPath(int i, int i2, String str, CertPath certPath) {
        if (certPath.getCertificates().size() == 0) {
            throw new CertificateEncodingException("No certificate contained in the cert path.");
        }
        byte[] encoded = certPath.getEncoded("PkiPath");
        this.mTestOnlyInsecureCertificateHelper.getClass();
        String defaultCertificateAliasIfEmpty =
                TestOnlyInsecureCertificateHelper.getDefaultCertificateAliasIfEmpty(str);
        SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        new ContentValues().put("cert_path", encoded);
        String[] strArr = {
            Integer.toString(i), Integer.toString(i2), defaultCertificateAliasIfEmpty
        };
        ensureRootOfTrustEntryExists(i, i2, defaultCertificateAliasIfEmpty);
        return writableDatabase.update(
                "root_of_trust", r1, "user_id = ? AND uid = ? AND root_alias = ?", strArr);
    }

    public final long setRecoveryServiceCertSerial(int i, int i2, long j, String str) {
        this.mTestOnlyInsecureCertificateHelper.getClass();
        String defaultCertificateAliasIfEmpty =
                TestOnlyInsecureCertificateHelper.getDefaultCertificateAliasIfEmpty(str);
        SQLiteDatabase writableDatabase = this.mKeyStoreDbHelper.getWritableDatabase();
        new ContentValues().put("cert_serial", Long.valueOf(j));
        String[] strArr = {
            Integer.toString(i), Integer.toString(i2), defaultCertificateAliasIfEmpty
        };
        ensureRootOfTrustEntryExists(i, i2, defaultCertificateAliasIfEmpty);
        return writableDatabase.update(
                "root_of_trust", r1, "user_id = ? AND uid = ? AND root_alias = ?", strArr);
    }

    public final long setShouldCreateSnapshot(int i, int i2) {
        return setLong(i, i2, 1L, "should_create_snapshot");
    }
}

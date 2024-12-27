package com.android.server.locksettings.recoverablekeystore;

import android.app.KeyguardManager;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.security.Scrypt;
import android.util.Log;
import android.util.Pair;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;
import com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb;
import com.android.server.locksettings.recoverablekeystore.storage.RecoverySnapshotStorage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KeySyncTask implements Runnable {
    static final int SCRYPT_PARAM_N = 4096;
    static final int SCRYPT_PARAM_OUTLEN_BYTES = 32;
    static final int SCRYPT_PARAM_P = 1;
    static final int SCRYPT_PARAM_R = 8;
    public final byte[] mCredential;
    public final int mCredentialType;
    public final boolean mCredentialUpdated;
    public final PlatformKeyManager mPlatformKeyManager;
    public final RecoverableKeyStoreDb mRecoverableKeyStoreDb;
    public final RecoverySnapshotStorage mRecoverySnapshotStorage;
    public final Scrypt mScrypt;
    public final RecoverySnapshotListenersStorage mSnapshotListenersStorage;
    public final TestOnlyInsecureCertificateHelper mTestOnlyInsecureCertificateHelper;
    public final int mUserId;

    public KeySyncTask(
            RecoverableKeyStoreDb recoverableKeyStoreDb,
            RecoverySnapshotStorage recoverySnapshotStorage,
            RecoverySnapshotListenersStorage recoverySnapshotListenersStorage,
            int i,
            int i2,
            byte[] bArr,
            boolean z,
            PlatformKeyManager platformKeyManager,
            TestOnlyInsecureCertificateHelper testOnlyInsecureCertificateHelper,
            Scrypt scrypt) {
        this.mSnapshotListenersStorage = recoverySnapshotListenersStorage;
        this.mRecoverableKeyStoreDb = recoverableKeyStoreDb;
        this.mUserId = i;
        this.mCredentialType = i2;
        this.mCredential = bArr != null ? Arrays.copyOf(bArr, bArr.length) : null;
        this.mCredentialUpdated = z;
        this.mPlatformKeyManager = platformKeyManager;
        this.mRecoverySnapshotStorage = recoverySnapshotStorage;
        this.mTestOnlyInsecureCertificateHelper = testOnlyInsecureCertificateHelper;
        this.mScrypt = scrypt;
    }

    public static int getUiFormat(int i) {
        if (i == 1) {
            return 3;
        }
        return i == 3 ? 1 : 2;
    }

    public static byte[] hashCredentialsBySaltedSha256(byte[] bArr, byte[] bArr2) {
        ByteBuffer allocate = ByteBuffer.allocate(bArr.length + bArr2.length + 8);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        allocate.putInt(bArr.length);
        allocate.put(bArr);
        allocate.putInt(bArr2.length);
        allocate.put(bArr2);
        byte[] array = allocate.array();
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(array);
            Arrays.fill(array, (byte) 0);
            return digest;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public final long generateAndStoreCounterId(int i) {
        long nextLong = new SecureRandom().nextLong();
        if (this.mRecoverableKeyStoreDb.setLong(this.mUserId, i, nextLong, "counter_id") >= 0) {
            return nextLong;
        }
        Log.e("KeySyncTask", "Failed to set the snapshot version in the local DB.");
        throw new IOException("Failed to set counterId in the local DB.");
    }

    public final Map getKeysToSync(int i) {
        PlatformDecryptionKey decryptKeyInternal;
        PlatformKeyManager platformKeyManager = this.mPlatformKeyManager;
        int i2 = this.mUserId;
        platformKeyManager.init(i2);
        try {
            decryptKeyInternal = platformKeyManager.getDecryptKeyInternal(i2);
            PlatformKeyManager.ensureDecryptionKeyIsValid(i2, decryptKeyInternal);
        } catch (UnrecoverableKeyException unused) {
            Locale locale = Locale.US;
            Log.i(
                    "PlatformKeyManager",
                    "Regenerating permanently invalid Platform key for user " + i2 + ".");
            platformKeyManager.regenerate(i2);
            decryptKeyInternal = platformKeyManager.getDecryptKeyInternal(i2);
        }
        RecoverableKeyStoreDb recoverableKeyStoreDb = this.mRecoverableKeyStoreDb;
        int i3 = this.mUserId;
        int i4 = decryptKeyInternal.mGenerationId;
        Map allKeys = recoverableKeyStoreDb.getAllKeys(i3, i, i4);
        HashMap hashMap = new HashMap();
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        HashMap hashMap2 = (HashMap) allKeys;
        for (String str : hashMap2.keySet()) {
            WrappedKey wrappedKey = (WrappedKey) hashMap2.get(str);
            if (wrappedKey.mPlatformKeyGenerationId != i4) {
                Locale locale2 = Locale.US;
                StringBuilder m =
                        DumpUtils$$ExternalSyntheticOutline0.m(
                                "WrappedKey with alias '", str, "' was wrapped with platform key ");
                m.append(wrappedKey.mPlatformKeyGenerationId);
                m.append(", not platform key ");
                m.append(i4);
                throw new BadPlatformKeyException(m.toString());
            }
            cipher.init(4, decryptKeyInternal.mKey, new GCMParameterSpec(128, wrappedKey.mNonce));
            try {
                hashMap.put(
                        str,
                        Pair.create(
                                (SecretKey) cipher.unwrap(wrappedKey.mKeyMaterial, "AES", 3),
                                wrappedKey.mKeyMetadata));
            } catch (InvalidKeyException | NoSuchAlgorithmException e) {
                Locale locale3 = Locale.US;
                Log.e("WrappedKey", "Error unwrapping recoverable key with alias '" + str + "'", e);
            }
        }
        return hashMap;
    }

    public int getSnapshotVersion(int i, boolean z) throws IOException {
        Long valueOf;
        Long l = this.mRecoverableKeyStoreDb.getLong(this.mUserId, i, "snapshot_version");
        if (z) {
            valueOf = Long.valueOf(l != null ? l.longValue() : 1L);
        } else {
            valueOf = Long.valueOf(l != null ? 1 + l.longValue() : 1L);
        }
        if (this.mRecoverableKeyStoreDb.setLong(
                        this.mUserId, i, valueOf.longValue(), "snapshot_version")
                >= 0) {
            return valueOf.intValue();
        }
        Log.e("KeySyncTask", "Failed to set the snapshot version in the local DB.");
        throw new IOException("Failed to set the snapshot version in the local DB.");
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            try {
                synchronized (KeySyncTask.class) {
                    syncKeys();
                }
                byte[] bArr = this.mCredential;
                if (bArr != null) {
                    Arrays.fill(bArr, (byte) 0);
                }
            } catch (Exception e) {
                Log.e("KeySyncTask", "Unexpected exception thrown during KeySyncTask", e);
                byte[] bArr2 = this.mCredential;
                if (bArr2 != null) {
                    Arrays.fill(bArr2, (byte) 0);
                }
            }
        } catch (Throwable th) {
            byte[] bArr3 = this.mCredential;
            if (bArr3 != null) {
                Arrays.fill(bArr3, (byte) 0);
            }
            throw th;
        }
    }

    public final void syncKeys() {
        if (this.mCredentialUpdated
                && this.mRecoverableKeyStoreDb.getBadRemoteGuessCounter(this.mUserId) != 0) {
            this.mRecoverableKeyStoreDb.setBadRemoteGuessCounter(this.mUserId, 0);
        }
        int platformKeyGenerationId =
                this.mPlatformKeyManager.mDatabase.getPlatformKeyGenerationId(this.mUserId);
        int i = this.mCredentialType;
        if (i == -1) {
            AudioService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Credentials are not set for user "),
                    this.mUserId,
                    "KeySyncTask");
            if (platformKeyGenerationId < 1001000) {
                this.mPlatformKeyManager.invalidatePlatformKey(
                        this.mUserId, platformKeyGenerationId);
                return;
            }
            return;
        }
        if (i != -1 && i != 1 && i != 3 && i != 4) {
            StringBuilder sb = new StringBuilder("Unsupported credential type ");
            sb.append(this.mCredentialType);
            sb.append(" for user ");
            AudioService$$ExternalSyntheticOutline0.m(sb, this.mUserId, "KeySyncTask");
            if (platformKeyGenerationId < 1001000) {
                RecoverableKeyStoreDb recoverableKeyStoreDb = this.mRecoverableKeyStoreDb;
                int i2 = this.mUserId;
                SQLiteDatabase writableDatabase =
                        recoverableKeyStoreDb.mKeyStoreDbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("recovery_status", (Integer) 3);
                writableDatabase.update(
                        "keys", contentValues, "user_id = ?", new String[] {String.valueOf(i2)});
                return;
            }
            return;
        }
        if (((KeyguardManager)
                                this.mPlatformKeyManager.mContext.getSystemService(
                                        KeyguardManager.class))
                        .isDeviceLocked(this.mUserId)
                && this.mUserId == 0) {
            AudioService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Can't sync keys for locked user "),
                    this.mUserId,
                    "KeySyncTask");
            return;
        }
        ArrayList arrayList =
                (ArrayList) this.mRecoverableKeyStoreDb.getRecoveryAgents(this.mUserId);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            try {
                syncKeysForAgent(intValue);
            } catch (IOException e) {
                Log.e("KeySyncTask", "IOException during sync for agent " + intValue, e);
            }
        }
        if (arrayList.isEmpty()) {
            AudioService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("No recovery agent initialized for user "),
                    this.mUserId,
                    "KeySyncTask");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:198:0x0045, code lost:

       if (r0.longValue() != 0) goto L14;
    */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0112 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x019c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void syncKeysForAgent(int r20) {
        /*
            Method dump skipped, instructions count: 1233
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.locksettings.recoverablekeystore.KeySyncTask.syncKeysForAgent(int):void");
    }
}

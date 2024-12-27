package com.android.server.locksettings.recoverablekeystore.storage;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.ServiceSpecificException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.Log;

import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CleanupManager {
    public final ApplicationKeyStorage mApplicationKeyStorage;
    public final RecoverableKeyStoreDb mDatabase;
    public Map mSerialNumbers;
    public final RecoverySnapshotStorage mSnapshotStorage;
    public final UserManager mUserManager;

    public CleanupManager(
            RecoverySnapshotStorage recoverySnapshotStorage,
            RecoverableKeyStoreDb recoverableKeyStoreDb,
            UserManager userManager,
            ApplicationKeyStorage applicationKeyStorage) {
        this.mSnapshotStorage = recoverySnapshotStorage;
        this.mDatabase = recoverableKeyStoreDb;
        this.mUserManager = userManager;
        this.mApplicationKeyStorage = applicationKeyStorage;
    }

    public final synchronized void registerRecoveryAgent(int i) {
        try {
            if (this.mSerialNumbers == null) {
                verifyKnownUsers();
            }
            Long l = (Long) ((ArrayMap) this.mSerialNumbers).get(Integer.valueOf(i));
            if (l == null) {
                l = -1L;
            }
            if (l.longValue() != -1) {
                return;
            }
            long serialNumberForUser = this.mUserManager.getSerialNumberForUser(UserHandle.of(i));
            if (serialNumberForUser != -1) {
                storeUserSerialNumber(i, serialNumberForUser);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void removeDataForUser(int i) {
        NetworkScoreService$$ExternalSyntheticOutline0.m(
                i, "Removing data for user ", ".", "CleanupManager");
        Iterator it = ((ArrayList) this.mDatabase.getRecoveryAgents(i)).iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            RecoverySnapshotStorage recoverySnapshotStorage = this.mSnapshotStorage;
            int intValue = num.intValue();
            synchronized (recoverySnapshotStorage) {
                recoverySnapshotStorage.mSnapshotByUid.remove(intValue);
                recoverySnapshotStorage.getSnapshotFile(intValue).delete();
            }
            int intValue2 = num.intValue();
            RecoverableKeyStoreDb recoverableKeyStoreDb = this.mDatabase;
            for (String str :
                    ((HashMap)
                                    recoverableKeyStoreDb.getAllKeys(
                                            i,
                                            intValue2,
                                            recoverableKeyStoreDb.getPlatformKeyGenerationId(i)))
                            .keySet()) {
                try {
                    this.mApplicationKeyStorage.deleteEntry(i, intValue2, str);
                } catch (ServiceSpecificException e) {
                    Log.e(
                            "CleanupManager",
                            "Error while removing recoverable key " + str + " : " + e);
                }
            }
        }
        RecoverableKeyStoreDbHelper recoverableKeyStoreDbHelper = this.mDatabase.mKeyStoreDbHelper;
        recoverableKeyStoreDbHelper
                .getWritableDatabase()
                .delete("keys", "user_id = ?", new String[] {Integer.toString(i)});
        recoverableKeyStoreDbHelper
                .getWritableDatabase()
                .delete("user_metadata", "user_id = ?", new String[] {Integer.toString(i)});
        recoverableKeyStoreDbHelper
                .getWritableDatabase()
                .delete(
                        "recovery_service_metadata",
                        "user_id = ?",
                        new String[] {Integer.toString(i)});
        recoverableKeyStoreDbHelper
                .getWritableDatabase()
                .delete("root_of_trust", "user_id = ?", new String[] {Integer.toString(i)});
    }

    public final void storeUserSerialNumber(int i, long j) {
        NetworkScoreService$$ExternalSyntheticOutline0.m(
                i, "Storing serial number for user ", ".", "CleanupManager");
        this.mSerialNumbers.put(Integer.valueOf(i), Long.valueOf(j));
        RecoverableKeyStoreDb recoverableKeyStoreDb = this.mDatabase;
        SQLiteDatabase writableDatabase =
                recoverableKeyStoreDb.mKeyStoreDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", Integer.valueOf(i));
        contentValues.put("user_serial_number", Long.valueOf(j));
        String[] strArr = {String.valueOf(i)};
        recoverableKeyStoreDb.ensureUserMetadataEntryExists(i);
        writableDatabase.update("user_metadata", contentValues, "user_id = ?", strArr);
    }

    public final synchronized void verifyKnownUsers() {
        try {
            this.mSerialNumbers = this.mDatabase.getUserSerialNumbers();
            ArrayList arrayList =
                    new ArrayList() { // from class:
                                      // com.android.server.locksettings.recoverablekeystore.storage.CleanupManager.1
                    };
            for (Map.Entry entry : this.mSerialNumbers.entrySet()) {
                Integer num = (Integer) entry.getKey();
                Long l = (Long) entry.getValue();
                if (l == null) {
                    l = -1L;
                }
                long serialNumberForUser =
                        this.mUserManager.getSerialNumberForUser(UserHandle.of(num.intValue()));
                if (serialNumberForUser == -1) {
                    arrayList.add(num);
                    removeDataForUser(num.intValue());
                } else if (l.longValue() == -1) {
                    storeUserSerialNumber(num.intValue(), serialNumberForUser);
                } else if (l.longValue() != serialNumberForUser) {
                    arrayList.add(num);
                    removeDataForUser(num.intValue());
                    storeUserSerialNumber(num.intValue(), serialNumberForUser);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.mSerialNumbers.remove((Integer) it.next());
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}

package com.android.server.locksettings.recoverablekeystore.storage;

import android.content.Context;
import android.os.ServiceSpecificException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class CleanupManager {
    public final ApplicationKeyStorage mApplicationKeyStorage;
    public final RecoverableKeyStoreDb mDatabase;
    public Map mSerialNumbers;
    public final RecoverySnapshotStorage mSnapshotStorage;
    public final UserManager mUserManager;

    public static CleanupManager getInstance(Context context, RecoverySnapshotStorage recoverySnapshotStorage, RecoverableKeyStoreDb recoverableKeyStoreDb, ApplicationKeyStorage applicationKeyStorage) {
        return new CleanupManager(recoverySnapshotStorage, recoverableKeyStoreDb, UserManager.get(context), applicationKeyStorage);
    }

    public CleanupManager(RecoverySnapshotStorage recoverySnapshotStorage, RecoverableKeyStoreDb recoverableKeyStoreDb, UserManager userManager, ApplicationKeyStorage applicationKeyStorage) {
        this.mSnapshotStorage = recoverySnapshotStorage;
        this.mDatabase = recoverableKeyStoreDb;
        this.mUserManager = userManager;
        this.mApplicationKeyStorage = applicationKeyStorage;
    }

    public synchronized void registerRecoveryAgent(int i, int i2) {
        if (this.mSerialNumbers == null) {
            verifyKnownUsers();
        }
        Long l = (Long) this.mSerialNumbers.get(Integer.valueOf(i));
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
    }

    public synchronized void verifyKnownUsers() {
        this.mSerialNumbers = this.mDatabase.getUserSerialNumbers();
        ArrayList arrayList = new ArrayList() { // from class: com.android.server.locksettings.recoverablekeystore.storage.CleanupManager.1
        };
        for (Map.Entry entry : this.mSerialNumbers.entrySet()) {
            Integer num = (Integer) entry.getKey();
            Long l = (Long) entry.getValue();
            if (l == null) {
                l = -1L;
            }
            long serialNumberForUser = this.mUserManager.getSerialNumberForUser(UserHandle.of(num.intValue()));
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
    }

    public final void storeUserSerialNumber(int i, long j) {
        Log.d("CleanupManager", "Storing serial number for user " + i + ".");
        this.mSerialNumbers.put(Integer.valueOf(i), Long.valueOf(j));
        this.mDatabase.setUserSerialNumber(i, j);
    }

    public final void removeDataForUser(int i) {
        Log.d("CleanupManager", "Removing data for user " + i + ".");
        for (Integer num : this.mDatabase.getRecoveryAgents(i)) {
            this.mSnapshotStorage.remove(num.intValue());
            removeAllKeysForRecoveryAgent(i, num.intValue());
        }
        this.mDatabase.removeUserFromAllTables(i);
    }

    public final void removeAllKeysForRecoveryAgent(int i, int i2) {
        for (String str : this.mDatabase.getAllKeys(i, i2, this.mDatabase.getPlatformKeyGenerationId(i)).keySet()) {
            try {
                this.mApplicationKeyStorage.deleteEntry(i, i2, str);
            } catch (ServiceSpecificException e) {
                Log.e("CleanupManager", "Error while removing recoverable key " + str + " : " + e);
            }
        }
    }
}

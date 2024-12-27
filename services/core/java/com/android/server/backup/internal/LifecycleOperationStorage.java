package com.android.server.backup.internal;

import android.util.Slog;
import android.util.SparseArray;

import com.android.server.backup.BackupRestoreTask;
import com.android.server.backup.OperationStorage;

import com.google.android.collect.Sets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.IntConsumer;

public final class LifecycleOperationStorage implements OperationStorage {
    public final int mUserId;
    public final Object mOperationsLock = new Object();
    public final SparseArray mOperations = new SparseArray();
    public final Map mOpTokensByPackage = new HashMap();

    public LifecycleOperationStorage(int i) {
        this.mUserId = i;
    }

    public final void cancelOperation(int i, boolean z, IntConsumer intConsumer) {
        Operation operation;
        BackupRestoreTask backupRestoreTask;
        synchronized (this.mOperationsLock) {
            try {
                operation = (Operation) this.mOperations.get(i);
                int i2 = operation != null ? operation.state : -1;
                if (i2 == 1) {
                    Slog.w(
                            "LifecycleOperationStorage",
                            "[UserID:"
                                    + this.mUserId
                                    + "] Operation already got an ack.Should have been removed from"
                                    + " mCurrentOperations.");
                    this.mOperations.delete(i);
                    operation = null;
                } else if (i2 == 0) {
                    Slog.v(
                            "LifecycleOperationStorage",
                            "[UserID:"
                                    + this.mUserId
                                    + "] Cancel: token="
                                    + Integer.toHexString(i));
                    operation.state = -1;
                    intConsumer.accept(operation.type);
                }
                this.mOperationsLock.notifyAll();
            } catch (Throwable th) {
                throw th;
            }
        }
        if (operation == null || (backupRestoreTask = operation.callback) == null) {
            return;
        }
        backupRestoreTask.handleCancel(z);
    }

    public final Set operationTokensForOpType() {
        HashSet newHashSet = Sets.newHashSet();
        synchronized (this.mOperationsLock) {
            for (int i = 0; i < this.mOperations.size(); i++) {
                try {
                    Operation operation = (Operation) this.mOperations.valueAt(i);
                    int keyAt = this.mOperations.keyAt(i);
                    if (operation.type == 2) {
                        newHashSet.add(Integer.valueOf(keyAt));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return newHashSet;
    }

    public final void registerOperationForPackages(
            int i, Set set, BackupRestoreTask backupRestoreTask, int i2) {
        synchronized (this.mOperationsLock) {
            try {
                this.mOperations.put(i, new Operation(backupRestoreTask, i2));
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    Set set2 = (Set) ((HashMap) this.mOpTokensByPackage).get(str);
                    if (set2 == null) {
                        set2 = new HashSet();
                    }
                    set2.add(Integer.valueOf(i));
                    ((HashMap) this.mOpTokensByPackage).put(str, set2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeOperation(int i) {
        synchronized (this.mOperationsLock) {
            try {
                this.mOperations.remove(i);
                for (String str : ((HashMap) this.mOpTokensByPackage).keySet()) {
                    Set set = (Set) ((HashMap) this.mOpTokensByPackage).get(str);
                    if (set != null) {
                        set.remove(Integer.valueOf(i));
                        ((HashMap) this.mOpTokensByPackage).put(str, set);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}

package com.android.server.backup.internal;

import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IFullBackupRestoreObserver;
import android.app.backup.IRestoreObserver;
import android.app.backup.RestoreSet;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.RemoteException;
import android.util.EventLog;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.infra.AndroidFuture;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.backup.BackupRestoreTask;
import com.android.server.backup.DataChangedJournal;
import com.android.server.backup.OperationStorage;
import com.android.server.backup.TransportManager;
import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda0;
import com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda14;
import com.android.server.backup.fullbackup.PerformAdbBackupTask;
import com.android.server.backup.keyvalue.BackupRequest;
import com.android.server.backup.keyvalue.KeyValueBackupTask;
import com.android.server.backup.params.AdbBackupParams;
import com.android.server.backup.params.AdbParams;
import com.android.server.backup.params.AdbRestoreParams;
import com.android.server.backup.params.BackupParams;
import com.android.server.backup.params.ClearParams;
import com.android.server.backup.params.ClearRetryParams;
import com.android.server.backup.params.RestoreGetSetsParams;
import com.android.server.backup.params.RestoreParams;
import com.android.server.backup.restore.ActiveRestoreSession;
import com.android.server.backup.restore.PerformAdbRestoreTask;
import com.android.server.backup.restore.PerformUnifiedRestoreTask;
import com.android.server.backup.transport.BackupTransportClient;
import com.android.server.backup.transport.TransportConnection;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class BackupHandler extends Handler {
    public final UserBackupManagerService backupManagerService;
    public final BackupAgentTimeoutParameters mAgentTimeoutParameters;
    public final HandlerThread mBackupThread;
    volatile boolean mIsStopping;
    public final OperationStorage mOperationStorage;

    public BackupHandler(UserBackupManagerService userBackupManagerService, LifecycleOperationStorage lifecycleOperationStorage, HandlerThread handlerThread) {
        super(handlerThread.getLooper());
        this.mIsStopping = false;
        this.mBackupThread = handlerThread;
        this.backupManagerService = userBackupManagerService;
        this.mOperationStorage = lifecycleOperationStorage;
        BackupAgentTimeoutParameters backupAgentTimeoutParameters = userBackupManagerService.mAgentTimeoutParameters;
        Objects.requireNonNull(backupAgentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = backupAgentTimeoutParameters;
    }

    @Override // android.os.Handler
    public final void dispatchMessage(Message message) {
        try {
            dispatchMessageInternal(message);
        } catch (Exception e) {
            if (!this.mIsStopping) {
                throw e;
            }
        }
    }

    public void dispatchMessageInternal(Message message) {
        super.dispatchMessage(message);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v42 */
    /* JADX WARN: Type inference failed for: r0v43, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v51 */
    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        Throwable th;
        ?? r0;
        List list;
        String str;
        StringBuilder sb;
        IRestoreObserver iRestoreObserver;
        if (message.what == 22) {
            Slog.v("BackupManagerService", "Stopping backup handler");
            this.backupManagerService.mWakelock.quit();
            this.mBackupThread.quitSafely();
        }
        if (this.mIsStopping) {
            return;
        }
        UserBackupManagerService userBackupManagerService = this.backupManagerService;
        final TransportManager transportManager = userBackupManagerService.mTransportManager;
        int i = message.what;
        IBackupManagerMonitor iBackupManagerMonitor = null;
        if (i == 1) {
            userBackupManagerService.mLastBackupPass = System.currentTimeMillis();
            final TransportConnection currentTransportClient = transportManager.getCurrentTransportClient("BH/MSG_RUN_BACKUP");
            BackupTransportClient connect = currentTransportClient != null ? currentTransportClient.connect("BH/MSG_RUN_BACKUP") : null;
            if (connect == null) {
                if (currentTransportClient != null) {
                    transportManager.mTransportConnectionManager.disposeOfTransportClient(currentTransportClient, "BH/MSG_RUN_BACKUP");
                }
                Slog.v("BackupManagerService", "Backup requested but no transport available");
                return;
            }
            ArrayList arrayList = new ArrayList();
            UserBackupManagerService userBackupManagerService2 = this.backupManagerService;
            DataChangedJournal dataChangedJournal = userBackupManagerService2.mJournal;
            synchronized (userBackupManagerService2.mQueueLock) {
                try {
                    if (this.backupManagerService.mBackupRunning) {
                        Slog.i("BackupManagerService", "Backup time but one already running");
                        return;
                    }
                    Slog.v("BackupManagerService", "Running a backup pass");
                    this.backupManagerService.mBackupRunning = true;
                    this.backupManagerService.mWakelock.acquire();
                    if (this.backupManagerService.mPendingBackups.size() > 0) {
                        Iterator it = this.backupManagerService.mPendingBackups.values().iterator();
                        while (it.hasNext()) {
                            arrayList.add(((BackupRequest) it.next()).packageName);
                        }
                        Slog.v("BackupManagerService", "clearing pending backups");
                        this.backupManagerService.mPendingBackups.clear();
                        this.backupManagerService.mJournal = null;
                    }
                    try {
                        iBackupManagerMonitor = connect.getBackupManagerMonitor();
                    } catch (RemoteException unused) {
                        Slog.i("BackupManagerService", "Failed to retrieve monitor from transport");
                    }
                    IBackupManagerMonitor iBackupManagerMonitor2 = iBackupManagerMonitor;
                    if (arrayList.size() > 0) {
                        try {
                            OnTaskFinishedListener onTaskFinishedListener = new OnTaskFinishedListener() { // from class: com.android.server.backup.internal.BackupHandler$$ExternalSyntheticLambda0
                                @Override // com.android.server.backup.internal.OnTaskFinishedListener
                                public final void onFinished(String str2) {
                                    TransportManager.this.disposeOfTransportClient(currentTransportClient, str2);
                                }
                            };
                            UserBackupManagerService userBackupManagerService3 = this.backupManagerService;
                            OperationStorage operationStorage = this.mOperationStorage;
                            AndroidFuture newFuture = connect.mTransportFutures.newFuture();
                            connect.mTransportBinder.transportDirName(newFuture);
                            KeyValueBackupTask.start(userBackupManagerService3, (LifecycleOperationStorage) operationStorage, currentTransportClient, (String) connect.getFutureResult(newFuture), arrayList, dataChangedJournal, null, iBackupManagerMonitor2, onTaskFinishedListener, Collections.emptyList(), false, false, this.backupManagerService.getEligibilityRulesForOperation(0));
                            return;
                        } catch (Exception e) {
                            Slog.e("BackupManagerService", "Transport became unavailable attempting backup or error initializing backup task", e);
                        }
                    } else {
                        Slog.v("BackupManagerService", "Backup requested but nothing pending");
                    }
                    transportManager.disposeOfTransportClient(currentTransportClient, "BH/MSG_RUN_BACKUP");
                    synchronized (this.backupManagerService.mQueueLock) {
                        this.backupManagerService.mBackupRunning = false;
                    }
                    this.backupManagerService.mWakelock.release();
                    return;
                } finally {
                }
            }
        }
        if (i == 2) {
            AdbBackupParams adbBackupParams = (AdbBackupParams) message.obj;
            new Thread(new PerformAdbBackupTask(userBackupManagerService, (LifecycleOperationStorage) this.mOperationStorage, adbBackupParams.fd, adbBackupParams.observer, adbBackupParams.includeApks, adbBackupParams.includeObbs, adbBackupParams.includeShared, adbBackupParams.doWidgets, adbBackupParams.curPassword, adbBackupParams.encryptPassword, adbBackupParams.allApps, adbBackupParams.includeSystem, adbBackupParams.doCompress, adbBackupParams.includeKeyValue, adbBackupParams.packages, adbBackupParams.latch, adbBackupParams.backupEligibilityRules, adbBackupParams.privilegeApp, adbBackupParams.extraFlag, adbBackupParams.smartswitchBackupPath, adbBackupParams.transportFlags), "adb-backup").start();
            return;
        }
        if (i == 3) {
            RestoreParams restoreParams = (RestoreParams) message.obj;
            Slog.d("BackupManagerService", "MSG_RUN_RESTORE observer=" + restoreParams.observer);
            PerformUnifiedRestoreTask performUnifiedRestoreTask = new PerformUnifiedRestoreTask(this.backupManagerService, (LifecycleOperationStorage) this.mOperationStorage, restoreParams.mTransportConnection, restoreParams.observer, restoreParams.monitor, restoreParams.token, restoreParams.packageInfo, restoreParams.pmToken, restoreParams.isSystemRestore, restoreParams.filterSet, restoreParams.listener, restoreParams.backupEligibilityRules);
            synchronized (this.backupManagerService.mPendingRestores) {
                try {
                    if (this.backupManagerService.mIsRestoreInProgress) {
                        Slog.d("BackupManagerService", "Restore in progress, queueing.");
                        ((ArrayDeque) this.backupManagerService.mPendingRestores).add(performUnifiedRestoreTask);
                    } else {
                        Slog.d("BackupManagerService", "Starting restore.");
                        this.backupManagerService.mIsRestoreInProgress = true;
                        sendMessage(obtainMessage(20, performUnifiedRestoreTask));
                    }
                } finally {
                }
            }
            return;
        }
        if (i == 4) {
            ClearParams clearParams = (ClearParams) message.obj;
            new PerformClearTask(userBackupManagerService, clearParams.mTransportConnection, clearParams.packageInfo, (UserBackupManagerService$$ExternalSyntheticLambda0) clearParams.listener).run();
            return;
        }
        if (i != 6) {
            if (i == 12) {
                ClearRetryParams clearRetryParams = (ClearRetryParams) message.obj;
                userBackupManagerService.clearBackupData(clearRetryParams.transportName, clearRetryParams.packageName);
                return;
            }
            if (i == 101) {
                AdbParams adbParams = (AdbParams) message.obj;
                if (adbParams == null) {
                    Slog.d("BackupManagerService", "params is null");
                    return;
                }
                Slog.i("BackupManagerService", "sep backup/restore timed out waiting for success");
                this.backupManagerService.getClass();
                UserBackupManagerService.signalAdbBackupRestoreCompletion(adbParams);
                if (this.backupManagerService.mWakelock.isHeld()) {
                    UserBackupManagerService userBackupManagerService4 = this.backupManagerService;
                    if (userBackupManagerService4.mSepWakeLock) {
                        userBackupManagerService4.mWakelock.release();
                        this.backupManagerService.mSepWakeLock = false;
                    }
                }
                IFullBackupRestoreObserver iFullBackupRestoreObserver = adbParams.observer;
                if (iFullBackupRestoreObserver != null) {
                    try {
                        iFullBackupRestoreObserver.onTimeout();
                        return;
                    } catch (RemoteException unused2) {
                        return;
                    }
                }
                return;
            }
            if (i == 20) {
                try {
                    ((BackupRestoreTask) message.obj).execute();
                    return;
                } catch (ClassCastException unused3) {
                    Slog.e("BackupManagerService", "Invalid backup/restore task in flight, obj=" + message.obj);
                    return;
                }
            }
            if (i == 21) {
                try {
                    Pair pair = (Pair) message.obj;
                    ((BackupRestoreTask) pair.first).operationComplete(((Long) pair.second).longValue());
                    return;
                } catch (ClassCastException unused4) {
                    Slog.e("BackupManagerService", "Invalid completion in flight, obj=" + message.obj);
                    return;
                }
            }
            switch (i) {
                case 8:
                    synchronized (userBackupManagerService) {
                        try {
                            if (this.backupManagerService.mActiveRestoreSession != null) {
                                Slog.w("BackupManagerService", "Restore session timed out; aborting");
                                UserBackupManagerService userBackupManagerService5 = this.backupManagerService;
                                ActiveRestoreSession activeRestoreSession = userBackupManagerService5.mActiveRestoreSession;
                                activeRestoreSession.mTimedOut = true;
                                post(new ActiveRestoreSession.EndRestoreRunnable(userBackupManagerService5, activeRestoreSession));
                            }
                        } finally {
                        }
                    }
                    return;
                case 9:
                    synchronized (userBackupManagerService.mAdbBackupRestoreConfirmations) {
                        AdbParams adbParams2 = (AdbParams) this.backupManagerService.mAdbBackupRestoreConfirmations.get(message.arg1);
                        if (adbParams2 != null) {
                            Slog.i("BackupManagerService", "Full backup/restore timed out waiting for user confirmation");
                            this.backupManagerService.getClass();
                            UserBackupManagerService.signalAdbBackupRestoreCompletion(adbParams2);
                            this.backupManagerService.mAdbBackupRestoreConfirmations.delete(message.arg1);
                            IFullBackupRestoreObserver iFullBackupRestoreObserver2 = adbParams2.observer;
                            if (iFullBackupRestoreObserver2 != null) {
                                try {
                                    iFullBackupRestoreObserver2.onTimeout();
                                } catch (RemoteException unused5) {
                                }
                            }
                        } else {
                            Slog.d("BackupManagerService", "couldn't find params for token " + message.arg1);
                        }
                    }
                    return;
                case 10:
                    AdbRestoreParams adbRestoreParams = (AdbRestoreParams) message.obj;
                    new Thread(new PerformAdbRestoreTask(userBackupManagerService, (LifecycleOperationStorage) this.mOperationStorage, adbRestoreParams.fd, adbRestoreParams.curPassword, adbRestoreParams.encryptPassword, adbRestoreParams.observer, adbRestoreParams.latch, adbRestoreParams.privilegeApp, adbRestoreParams.typeMigration), "adb-restore").start();
                    return;
                default:
                    switch (i) {
                        case 15:
                            BackupParams backupParams = (BackupParams) message.obj;
                            userBackupManagerService.mBackupRunning = true;
                            this.backupManagerService.mWakelock.acquire();
                            KeyValueBackupTask.start(this.backupManagerService, (LifecycleOperationStorage) this.mOperationStorage, backupParams.mTransportConnection, backupParams.dirName, backupParams.kvPackages, null, backupParams.observer, backupParams.monitor, backupParams.listener, backupParams.fullPackages, true, backupParams.nonIncrementalBackup, backupParams.mBackupEligibilityRules);
                            return;
                        case 16:
                            userBackupManagerService.dataChangedImpl((String) message.obj);
                            return;
                        case 17:
                        case 18:
                            Slog.d("BackupManagerService", "Timeout message received for token=" + Integer.toHexString(message.arg1));
                            UserBackupManagerService userBackupManagerService6 = this.backupManagerService;
                            int i2 = message.arg1;
                            userBackupManagerService6.getClass();
                            userBackupManagerService6.mOperationStorage.cancelOperation(i2, false, new UserBackupManagerService$$ExternalSyntheticLambda14(0, userBackupManagerService6));
                            return;
                        default:
                            return;
                    }
            }
        }
        RestoreGetSetsParams restoreGetSetsParams = (RestoreGetSetsParams) message.obj;
        try {
            try {
                try {
                    BackupTransportClient connectOrThrow = restoreGetSetsParams.mTransportConnection.connectOrThrow("BH/MSG_RUN_GET_RESTORE_SETS");
                    AndroidFuture newFuture2 = connectOrThrow.mTransportFutures.newFuture();
                    connectOrThrow.mTransportBinder.getAvailableRestoreSets(newFuture2);
                    list = (List) connectOrThrow.getFutureResult(newFuture2);
                } catch (Exception e2) {
                    e = e2;
                    list = null;
                } catch (Throwable th2) {
                    th = th2;
                    r0 = 0;
                    IRestoreObserver iRestoreObserver2 = restoreGetSetsParams.observer;
                    if (iRestoreObserver2 != null) {
                        try {
                            if (r0 == 0) {
                                iRestoreObserver2.restoreSetsAvailable((RestoreSet[]) null);
                            } else {
                                iRestoreObserver2.restoreSetsAvailable((RestoreSet[]) r0.toArray(new RestoreSet[0]));
                            }
                        } catch (RemoteException unused6) {
                            Slog.e("BackupManagerService", "Unable to report listing to observer");
                        } catch (Exception e3) {
                            NandswapManager$$ExternalSyntheticOutline0.m(e3, new StringBuilder("Restore observer threw: "), "BackupManagerService");
                        }
                    }
                    removeMessages(8);
                    sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
                    restoreGetSetsParams.listener.onFinished("BH/MSG_RUN_GET_RESTORE_SETS");
                    throw th;
                }
                try {
                    synchronized (restoreGetSetsParams.session) {
                        restoreGetSetsParams.session.mRestoreSets = list;
                    }
                    if (list == null) {
                        EventLog.writeEvent(2831, new Object[0]);
                    }
                    iRestoreObserver = restoreGetSetsParams.observer;
                } catch (Exception e4) {
                    e = e4;
                    Slog.e("BackupManagerService", "Error from transport getting set list: " + e.getMessage());
                    IRestoreObserver iRestoreObserver3 = restoreGetSetsParams.observer;
                    if (iRestoreObserver3 != null) {
                        try {
                            if (list == null) {
                                iRestoreObserver3.restoreSetsAvailable((RestoreSet[]) null);
                            } else {
                                iRestoreObserver3.restoreSetsAvailable((RestoreSet[]) list.toArray(new RestoreSet[0]));
                            }
                        } catch (Exception e5) {
                            e = e5;
                            str = "BackupManagerService";
                            sb = new StringBuilder("Restore observer threw: ");
                            NandswapManager$$ExternalSyntheticOutline0.m(e, sb, str);
                            removeMessages(8);
                            sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
                            restoreGetSetsParams.listener.onFinished("BH/MSG_RUN_GET_RESTORE_SETS");
                        }
                    }
                    removeMessages(8);
                    sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
                    restoreGetSetsParams.listener.onFinished("BH/MSG_RUN_GET_RESTORE_SETS");
                }
                if (iRestoreObserver != null) {
                    try {
                        if (list == null) {
                            iRestoreObserver.restoreSetsAvailable((RestoreSet[]) null);
                        } else {
                            iRestoreObserver.restoreSetsAvailable((RestoreSet[]) list.toArray(new RestoreSet[0]));
                        }
                    } catch (Exception e6) {
                        e = e6;
                        str = "BackupManagerService";
                        sb = new StringBuilder("Restore observer threw: ");
                        NandswapManager$$ExternalSyntheticOutline0.m(e, sb, str);
                        removeMessages(8);
                        sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
                        restoreGetSetsParams.listener.onFinished("BH/MSG_RUN_GET_RESTORE_SETS");
                    }
                }
            } catch (RemoteException unused7) {
                Slog.e("BackupManagerService", "Unable to report listing to observer");
            }
            removeMessages(8);
            sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
            restoreGetSetsParams.listener.onFinished("BH/MSG_RUN_GET_RESTORE_SETS");
        } catch (Throwable th3) {
            th = th3;
            r0 = userBackupManagerService;
        }
    }

    public final void stop() {
        this.mIsStopping = true;
        sendMessage(obtainMessage(22));
    }
}

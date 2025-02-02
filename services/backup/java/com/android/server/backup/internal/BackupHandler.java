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
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.backup.BackupRestoreTask;
import com.android.server.backup.DataChangedJournal;
import com.android.server.backup.OperationStorage;
import com.android.server.backup.TransportManager;
import com.android.server.backup.UserBackupManagerService;
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
import com.android.server.backup.restore.ActiveRestoreSession.EndRestoreRunnable;
import com.android.server.backup.restore.PerformAdbRestoreTask;
import com.android.server.backup.restore.PerformUnifiedRestoreTask;
import com.android.server.backup.transport.BackupTransportClient;
import com.android.server.backup.transport.TransportConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class BackupHandler extends Handler {
    public final UserBackupManagerService backupManagerService;
    public final BackupAgentTimeoutParameters mAgentTimeoutParameters;
    public final HandlerThread mBackupThread;
    volatile boolean mIsStopping;
    public final OperationStorage mOperationStorage;

    public BackupHandler(UserBackupManagerService userBackupManagerService, OperationStorage operationStorage, HandlerThread handlerThread) {
        super(handlerThread.getLooper());
        this.mIsStopping = false;
        this.mBackupThread = handlerThread;
        this.backupManagerService = userBackupManagerService;
        this.mOperationStorage = operationStorage;
        BackupAgentTimeoutParameters agentTimeoutParameters = userBackupManagerService.getAgentTimeoutParameters();
        Objects.requireNonNull(agentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = agentTimeoutParameters;
    }

    public void stop() {
        this.mIsStopping = true;
        sendMessage(obtainMessage(22));
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message message) {
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
    /* JADX WARN: Removed duplicated region for block: B:150:0x02cc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:215:0x04ef  */
    /* JADX WARN: Removed duplicated region for block: B:226:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v12, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r7v8 */
    @Override // android.os.Handler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handleMessage(Message message) {
        TransportConnection transportConnection;
        boolean z;
        Throwable th;
        List list;
        IRestoreObserver iRestoreObserver;
        StringBuilder sb;
        String str;
        Exception e;
        IRestoreObserver iRestoreObserver2;
        if (message.what == 22) {
            Slog.v("BackupManagerService", "Stopping backup handler");
            this.backupManagerService.getWakelock().quit();
            this.mBackupThread.quitSafely();
        }
        if (this.mIsStopping) {
            return;
        }
        final TransportManager transportManager = this.backupManagerService.getTransportManager();
        int i = message.what;
        IBackupManagerMonitor iBackupManagerMonitor = null;
        if (i != 1) {
            if (i == 2) {
                AdbBackupParams adbBackupParams = (AdbBackupParams) message.obj;
                new Thread(new PerformAdbBackupTask(this.backupManagerService, this.mOperationStorage, adbBackupParams.f1647fd, adbBackupParams.observer, adbBackupParams.includeApks, adbBackupParams.includeObbs, adbBackupParams.includeShared, adbBackupParams.doWidgets, adbBackupParams.curPassword, adbBackupParams.encryptPassword, adbBackupParams.allApps, adbBackupParams.includeSystem, adbBackupParams.doCompress, adbBackupParams.includeKeyValue, adbBackupParams.packages, adbBackupParams.latch, adbBackupParams.backupEligibilityRules, adbBackupParams.privilegeApp, adbBackupParams.extraFlag, adbBackupParams.smartswitchBackupPath, adbBackupParams.transportFlags), "adb-backup").start();
                return;
            }
            ?? r7 = 20;
            if (i == 3) {
                RestoreParams restoreParams = (RestoreParams) message.obj;
                Slog.d("BackupManagerService", "MSG_RUN_RESTORE observer=" + restoreParams.observer);
                PerformUnifiedRestoreTask performUnifiedRestoreTask = new PerformUnifiedRestoreTask(this.backupManagerService, this.mOperationStorage, restoreParams.mTransportConnection, restoreParams.observer, restoreParams.monitor, restoreParams.token, restoreParams.packageInfo, restoreParams.pmToken, restoreParams.isSystemRestore, restoreParams.filterSet, restoreParams.listener, restoreParams.backupEligibilityRules);
                synchronized (this.backupManagerService.getPendingRestores()) {
                    if (this.backupManagerService.isRestoreInProgress()) {
                        Slog.d("BackupManagerService", "Restore in progress, queueing.");
                        this.backupManagerService.getPendingRestores().add(performUnifiedRestoreTask);
                    } else {
                        Slog.d("BackupManagerService", "Starting restore.");
                        this.backupManagerService.setRestoreInProgress(true);
                        sendMessage(obtainMessage(20, performUnifiedRestoreTask));
                    }
                }
                return;
            }
            if (i == 4) {
                ClearParams clearParams = (ClearParams) message.obj;
                new PerformClearTask(this.backupManagerService, clearParams.mTransportConnection, clearParams.packageInfo, clearParams.listener).run();
                return;
            }
            if (i == 6) {
                RestoreGetSetsParams restoreGetSetsParams = (RestoreGetSetsParams) message.obj;
                try {
                    try {
                        try {
                            list = restoreGetSetsParams.mTransportConnection.connectOrThrow("BH/MSG_RUN_GET_RESTORE_SETS").getAvailableRestoreSets();
                            try {
                                synchronized (restoreGetSetsParams.session) {
                                    restoreGetSetsParams.session.setRestoreSets(list);
                                }
                                if (list == null) {
                                    EventLog.writeEvent(2831, new Object[0]);
                                }
                                iRestoreObserver2 = restoreGetSetsParams.observer;
                            } catch (Exception e2) {
                                e = e2;
                                Slog.e("BackupManagerService", "Error from transport getting set list: " + e.getMessage());
                                IRestoreObserver iRestoreObserver3 = restoreGetSetsParams.observer;
                                if (iRestoreObserver3 != null) {
                                    try {
                                        if (list == null) {
                                            iRestoreObserver3.restoreSetsAvailable((RestoreSet[]) null);
                                        } else {
                                            iRestoreObserver3.restoreSetsAvailable((RestoreSet[]) list.toArray(new RestoreSet[0]));
                                        }
                                    } catch (Exception e3) {
                                        e = e3;
                                        str = "BackupManagerService";
                                        sb = new StringBuilder();
                                        sb.append("Restore observer threw: ");
                                        sb.append(e.getMessage());
                                        Slog.e(str, sb.toString());
                                        removeMessages(8);
                                        sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
                                        restoreGetSetsParams.listener.onFinished("BH/MSG_RUN_GET_RESTORE_SETS");
                                        return;
                                    }
                                }
                                removeMessages(8);
                                sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
                                restoreGetSetsParams.listener.onFinished("BH/MSG_RUN_GET_RESTORE_SETS");
                                return;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            iRestoreObserver = restoreGetSetsParams.observer;
                            if (iRestoreObserver != null) {
                                try {
                                    if (r7 == 0) {
                                        iRestoreObserver.restoreSetsAvailable((RestoreSet[]) null);
                                    } else {
                                        iRestoreObserver.restoreSetsAvailable((RestoreSet[]) r7.toArray(new RestoreSet[0]));
                                    }
                                } catch (RemoteException unused) {
                                    Slog.e("BackupManagerService", "Unable to report listing to observer");
                                } catch (Exception e4) {
                                    Slog.e("BackupManagerService", "Restore observer threw: " + e4.getMessage());
                                }
                            }
                            removeMessages(8);
                            sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
                            restoreGetSetsParams.listener.onFinished("BH/MSG_RUN_GET_RESTORE_SETS");
                            throw th;
                        }
                    } catch (Exception e5) {
                        e = e5;
                        list = null;
                    } catch (Throwable th3) {
                        th = th3;
                        r7 = 0;
                        iRestoreObserver = restoreGetSetsParams.observer;
                        if (iRestoreObserver != null) {
                        }
                        removeMessages(8);
                        sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
                        restoreGetSetsParams.listener.onFinished("BH/MSG_RUN_GET_RESTORE_SETS");
                        throw th;
                    }
                    if (iRestoreObserver2 != null) {
                        try {
                            if (list == null) {
                                iRestoreObserver2.restoreSetsAvailable((RestoreSet[]) null);
                            } else {
                                iRestoreObserver2.restoreSetsAvailable((RestoreSet[]) list.toArray(new RestoreSet[0]));
                            }
                        } catch (Exception e6) {
                            e = e6;
                            str = "BackupManagerService";
                            sb = new StringBuilder();
                            sb.append("Restore observer threw: ");
                            sb.append(e.getMessage());
                            Slog.e(str, sb.toString());
                            removeMessages(8);
                            sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
                            restoreGetSetsParams.listener.onFinished("BH/MSG_RUN_GET_RESTORE_SETS");
                            return;
                        }
                    }
                } catch (RemoteException unused2) {
                    Slog.e("BackupManagerService", "Unable to report listing to observer");
                }
                removeMessages(8);
                sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
                restoreGetSetsParams.listener.onFinished("BH/MSG_RUN_GET_RESTORE_SETS");
                return;
            }
            if (i == 12) {
                ClearRetryParams clearRetryParams = (ClearRetryParams) message.obj;
                this.backupManagerService.clearBackupData(clearRetryParams.transportName, clearRetryParams.packageName);
                return;
            }
            if (i == 101) {
                AdbParams adbParams = (AdbParams) message.obj;
                if (adbParams == null) {
                    Slog.d("BackupManagerService", "params is null");
                    return;
                }
                Slog.i("BackupManagerService", "sep backup/restore timed out waiting for success");
                this.backupManagerService.signalAdbBackupRestoreCompletion(adbParams);
                if (this.backupManagerService.getWakelock().isHeld() && this.backupManagerService.getSepWakeLock()) {
                    this.backupManagerService.getWakelock().release();
                    this.backupManagerService.setSepWakeLock(false);
                }
                IFullBackupRestoreObserver iFullBackupRestoreObserver = adbParams.observer;
                if (iFullBackupRestoreObserver != null) {
                    try {
                        iFullBackupRestoreObserver.onTimeout();
                        return;
                    } catch (RemoteException unused3) {
                        return;
                    }
                }
                return;
            }
            if (i == 20) {
                try {
                    ((BackupRestoreTask) message.obj).execute();
                    return;
                } catch (ClassCastException unused4) {
                    Slog.e("BackupManagerService", "Invalid backup/restore task in flight, obj=" + message.obj);
                    return;
                }
            }
            if (i == 21) {
                try {
                    Pair pair = (Pair) message.obj;
                    ((BackupRestoreTask) pair.first).operationComplete(((Long) pair.second).longValue());
                    return;
                } catch (ClassCastException unused5) {
                    Slog.e("BackupManagerService", "Invalid completion in flight, obj=" + message.obj);
                    return;
                }
            }
            switch (i) {
                case 8:
                    synchronized (this.backupManagerService) {
                        if (this.backupManagerService.getActiveRestoreSession() != null) {
                            Slog.w("BackupManagerService", "Restore session timed out; aborting");
                            this.backupManagerService.getActiveRestoreSession().markTimedOut();
                            ActiveRestoreSession activeRestoreSession = this.backupManagerService.getActiveRestoreSession();
                            Objects.requireNonNull(activeRestoreSession);
                            UserBackupManagerService userBackupManagerService = this.backupManagerService;
                            post(activeRestoreSession.new EndRestoreRunnable(userBackupManagerService, userBackupManagerService.getActiveRestoreSession()));
                        }
                    }
                    return;
                case 9:
                    synchronized (this.backupManagerService.getAdbBackupRestoreConfirmations()) {
                        AdbParams adbParams2 = (AdbParams) this.backupManagerService.getAdbBackupRestoreConfirmations().get(message.arg1);
                        if (adbParams2 != null) {
                            Slog.i("BackupManagerService", "Full backup/restore timed out waiting for user confirmation");
                            this.backupManagerService.signalAdbBackupRestoreCompletion(adbParams2);
                            this.backupManagerService.getAdbBackupRestoreConfirmations().delete(message.arg1);
                            IFullBackupRestoreObserver iFullBackupRestoreObserver2 = adbParams2.observer;
                            if (iFullBackupRestoreObserver2 != null) {
                                try {
                                    iFullBackupRestoreObserver2.onTimeout();
                                } catch (RemoteException unused6) {
                                }
                            }
                        } else {
                            Slog.d("BackupManagerService", "couldn't find params for token " + message.arg1);
                        }
                    }
                    return;
                case 10:
                    AdbRestoreParams adbRestoreParams = (AdbRestoreParams) message.obj;
                    new Thread(new PerformAdbRestoreTask(this.backupManagerService, this.mOperationStorage, adbRestoreParams.f1647fd, adbRestoreParams.curPassword, adbRestoreParams.encryptPassword, adbRestoreParams.observer, adbRestoreParams.latch, adbRestoreParams.privilegeApp, adbRestoreParams.typeMigration), "adb-restore").start();
                    return;
                default:
                    switch (i) {
                        case 15:
                            BackupParams backupParams = (BackupParams) message.obj;
                            this.backupManagerService.setBackupRunning(true);
                            this.backupManagerService.getWakelock().acquire();
                            KeyValueBackupTask.start(this.backupManagerService, this.mOperationStorage, backupParams.mTransportConnection, backupParams.dirName, backupParams.kvPackages, null, backupParams.observer, backupParams.monitor, backupParams.listener, backupParams.fullPackages, true, backupParams.nonIncrementalBackup, backupParams.mBackupEligibilityRules);
                            return;
                        case 16:
                            this.backupManagerService.dataChangedImpl((String) message.obj);
                            return;
                        case 17:
                        case 18:
                            Slog.d("BackupManagerService", "Timeout message received for token=" + Integer.toHexString(message.arg1));
                            this.backupManagerService.handleCancel(message.arg1, false);
                            return;
                        default:
                            return;
                    }
            }
        }
        this.backupManagerService.setLastBackupPass(System.currentTimeMillis());
        final TransportConnection currentTransportClient = transportManager.getCurrentTransportClient("BH/MSG_RUN_BACKUP");
        BackupTransportClient connect = currentTransportClient != null ? currentTransportClient.connect("BH/MSG_RUN_BACKUP") : null;
        if (connect == null) {
            if (currentTransportClient != null) {
                transportManager.disposeOfTransportClient(currentTransportClient, "BH/MSG_RUN_BACKUP");
            }
            Slog.v("BackupManagerService", "Backup requested but no transport available");
            return;
        }
        ArrayList arrayList = new ArrayList();
        DataChangedJournal journal = this.backupManagerService.getJournal();
        synchronized (this.backupManagerService.getQueueLock()) {
            if (this.backupManagerService.isBackupRunning()) {
                Slog.i("BackupManagerService", "Backup time but one already running");
                return;
            }
            Slog.v("BackupManagerService", "Running a backup pass");
            this.backupManagerService.setBackupRunning(true);
            this.backupManagerService.getWakelock().acquire();
            if (this.backupManagerService.getPendingBackups().size() > 0) {
                Iterator it = this.backupManagerService.getPendingBackups().values().iterator();
                while (it.hasNext()) {
                    arrayList.add(((BackupRequest) it.next()).packageName);
                }
                Slog.v("BackupManagerService", "clearing pending backups");
                this.backupManagerService.getPendingBackups().clear();
                this.backupManagerService.setJournal(null);
            }
            try {
                iBackupManagerMonitor = connect.getBackupManagerMonitor();
            } catch (RemoteException unused7) {
                Slog.i("BackupManagerService", "Failed to retrieve monitor from transport");
            }
            IBackupManagerMonitor iBackupManagerMonitor2 = iBackupManagerMonitor;
            if (arrayList.size() > 0) {
                try {
                    transportConnection = currentTransportClient;
                    try {
                        KeyValueBackupTask.start(this.backupManagerService, this.mOperationStorage, currentTransportClient, connect.transportDirName(), arrayList, journal, null, iBackupManagerMonitor2, new OnTaskFinishedListener() { // from class: com.android.server.backup.internal.BackupHandler$$ExternalSyntheticLambda0
                            @Override // com.android.server.backup.internal.OnTaskFinishedListener
                            public final void onFinished(String str2) {
                                TransportManager.this.disposeOfTransportClient(currentTransportClient, str2);
                            }
                        }, Collections.emptyList(), false, false, this.backupManagerService.getEligibilityRulesForOperation(0));
                        z = true;
                    } catch (Exception e7) {
                        e = e7;
                        Slog.e("BackupManagerService", "Transport became unavailable attempting backup or error initializing backup task", e);
                        z = false;
                        if (z) {
                        }
                    }
                } catch (Exception e8) {
                    e = e8;
                    transportConnection = currentTransportClient;
                }
                if (z) {
                    return;
                }
                transportManager.disposeOfTransportClient(transportConnection, "BH/MSG_RUN_BACKUP");
                synchronized (this.backupManagerService.getQueueLock()) {
                    this.backupManagerService.setBackupRunning(false);
                }
                this.backupManagerService.getWakelock().release();
                return;
            }
            transportConnection = currentTransportClient;
            Slog.v("BackupManagerService", "Backup requested but nothing pending");
            z = false;
            if (z) {
            }
        }
    }
}

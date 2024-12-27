package com.android.server.backup.keyvalue;

import android.app.IBackupAgent;
import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IBackupObserver;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.SELinux;
import android.os.UserHandle;
import android.os.WorkSource;
import android.util.EventLog;
import android.util.Log;
import android.util.Slog;

import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.Preconditions;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.backup.BackupRestoreTask;
import com.android.server.backup.DataChangedJournal;
import com.android.server.backup.KeyValueBackupJob;
import com.android.server.backup.OperationStorage;
import com.android.server.backup.PackageManagerBackupAgent;
import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.fullbackup.PerformFullTransportBackupTask;
import com.android.server.backup.internal.LifecycleOperationStorage;
import com.android.server.backup.internal.OnTaskFinishedListener;
import com.android.server.backup.remote.FutureBackupCallback;
import com.android.server.backup.remote.RemoteCall;
import com.android.server.backup.remote.RemoteCallable;
import com.android.server.backup.remote.RemoteResult;
import com.android.server.backup.transport.BackupTransportClient;
import com.android.server.backup.transport.TransportConnection;
import com.android.server.backup.transport.TransportNotAvailableException;
import com.android.server.backup.transport.TransportStatusCallback;
import com.android.server.backup.utils.BackupEligibilityRules;
import com.android.server.backup.utils.BackupManagerMonitorEventSender;
import com.android.server.backup.utils.BackupObserverUtils;

import libcore.io.IoUtils;

import com.google.android.collect.Sets;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public final class KeyValueBackupTask implements BackupRestoreTask, Runnable {
    public static final String NEW_STATE_FILE_SUFFIX = ".new";
    static final String NO_DATA_END_SENTINEL = "@end@";
    public static final String STAGING_FILE_SUFFIX = ".data";
    public static final AtomicInteger THREAD_COUNT = new AtomicInteger();
    public IBackupAgent mAgent;
    public final BackupAgentTimeoutParameters mAgentTimeoutParameters;
    public ParcelFileDescriptor mBackupData;
    public File mBackupDataFile;
    public final BackupEligibilityRules mBackupEligibilityRules;
    public final UserBackupManagerService mBackupManagerService;
    public final File mBlankStateFile;
    public final ConditionVariable mCancelAcknowledged = new ConditionVariable(false);
    public volatile boolean mCancelled = false;
    public final int mCurrentOpToken;
    public PackageInfo mCurrentPackage;
    public final File mDataDirectory;
    public PerformFullTransportBackupTask mFullBackupTask;
    public boolean mHasDataToBackup;
    public final DataChangedJournal mJournal;
    public ParcelFileDescriptor mNewState;
    public File mNewStateFile;
    public boolean mNonIncremental;
    public final OperationStorage mOperationStorage;
    public final List mOriginalQueue;
    public final PackageManager mPackageManager;
    public volatile RemoteCall mPendingCall;
    public final List mPendingFullBackups;
    public final List mQueue;
    public final Object mQueueLock;
    public final KeyValueBackupReporter mReporter;
    public ParcelFileDescriptor mSavedState;
    public File mSavedStateFile;
    public final File mStateDirectory;
    public final OnTaskFinishedListener mTaskFinishedListener;
    public final TransportConnection mTransportConnection;
    public final int mUserId;
    public final boolean mUserInitiated;

    public KeyValueBackupTask(
            UserBackupManagerService userBackupManagerService,
            OperationStorage operationStorage,
            TransportConnection transportConnection,
            String str,
            List list,
            DataChangedJournal dataChangedJournal,
            KeyValueBackupReporter keyValueBackupReporter,
            OnTaskFinishedListener onTaskFinishedListener,
            List list2,
            boolean z,
            boolean z2,
            BackupEligibilityRules backupEligibilityRules) {
        this.mBackupManagerService = userBackupManagerService;
        this.mOperationStorage = operationStorage;
        this.mPackageManager = userBackupManagerService.mPackageManager;
        this.mTransportConnection = transportConnection;
        this.mOriginalQueue = list;
        this.mQueue = new ArrayList(list);
        this.mJournal = dataChangedJournal;
        this.mReporter = keyValueBackupReporter;
        this.mTaskFinishedListener = onTaskFinishedListener;
        this.mPendingFullBackups = list2;
        this.mUserInitiated = z;
        this.mNonIncremental = z2;
        BackupAgentTimeoutParameters backupAgentTimeoutParameters =
                userBackupManagerService.mAgentTimeoutParameters;
        Objects.requireNonNull(backupAgentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = backupAgentTimeoutParameters;
        File file = new File(userBackupManagerService.mBaseStateDir, str);
        this.mStateDirectory = file;
        this.mDataDirectory = userBackupManagerService.mDataDir;
        this.mCurrentOpToken = userBackupManagerService.generateRandomIntegerToken();
        this.mQueueLock = userBackupManagerService.mQueueLock;
        this.mBlankStateFile = new File(file, "blank_state");
        this.mUserId = userBackupManagerService.mUserId;
        this.mBackupEligibilityRules = backupEligibilityRules;
    }

    public static void clearStatus(File file, String str) {
        if (!file.exists() || file.delete()) {
            return;
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                "Unable to remove status file for ", str, "KVBT");
    }

    public static void start(
            UserBackupManagerService userBackupManagerService,
            LifecycleOperationStorage lifecycleOperationStorage,
            TransportConnection transportConnection,
            String str,
            List list,
            DataChangedJournal dataChangedJournal,
            IBackupObserver iBackupObserver,
            IBackupManagerMonitor iBackupManagerMonitor,
            OnTaskFinishedListener onTaskFinishedListener,
            List list2,
            boolean z,
            boolean z2,
            BackupEligibilityRules backupEligibilityRules) {
        Thread thread =
                new Thread(
                        new KeyValueBackupTask(
                                userBackupManagerService,
                                lifecycleOperationStorage,
                                transportConnection,
                                str,
                                list,
                                dataChangedJournal,
                                new KeyValueBackupReporter(
                                        userBackupManagerService,
                                        iBackupObserver,
                                        new BackupManagerMonitorEventSender(iBackupManagerMonitor)),
                                onTaskFinishedListener,
                                list2,
                                z,
                                z2,
                                backupEligibilityRules),
                        "key-value-backup-" + THREAD_COUNT.incrementAndGet());
        thread.start();
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                "Spinning thread ", thread.getName(), "KeyValueBackupTask");
    }

    public final void backupPackage(String str) {
        this.mReporter.getClass();
        Slog.d("KeyValueBackupTask", "Starting key-value backup of " + str);
        try {
            PackageInfo packageInfoAsUser =
                    this.mPackageManager.getPackageInfoAsUser(str, 134217728, this.mUserId);
            ApplicationInfo applicationInfo = packageInfoAsUser.applicationInfo;
            if (!this.mBackupEligibilityRules.appIsEligibleForBackup(applicationInfo)) {
                KeyValueBackupReporter keyValueBackupReporter = this.mReporter;
                keyValueBackupReporter.getClass();
                Slog.i(
                        "KeyValueBackupTask",
                        "Package " + str + " no longer supports backup, skipping");
                BackupObserverUtils.sendBackupOnPackageResult(
                        keyValueBackupReporter.mObserver, str, -2001);
                throw AgentException.permanent();
            }
            if (this.mBackupEligibilityRules.appGetsFullBackup(packageInfoAsUser)) {
                KeyValueBackupReporter keyValueBackupReporter2 = this.mReporter;
                keyValueBackupReporter2.getClass();
                Slog.i(
                        "KeyValueBackupTask",
                        "Package " + str + " performs full-backup rather than key-value, skipping");
                BackupObserverUtils.sendBackupOnPackageResult(
                        keyValueBackupReporter2.mObserver, str, -2001);
                throw AgentException.permanent();
            }
            this.mBackupEligibilityRules.getClass();
            if (BackupEligibilityRules.appIsStopped(applicationInfo)) {
                BackupObserverUtils.sendBackupOnPackageResult(this.mReporter.mObserver, str, -2001);
                throw AgentException.permanent();
            }
            this.mCurrentPackage = packageInfoAsUser;
            try {
                extractAgentData(packageInfoAsUser);
                new BackupManagerMonitorEventSender(
                                this.mReporter.mBackupManagerMonitorEventSender.mMonitor)
                        .monitorAgentLoggingResults(this.mCurrentPackage, this.mAgent);
                int sendDataToTransport = sendDataToTransport(this.mCurrentPackage);
                if (sendDataToTransport == -1006) {
                    cleanUpAgent(2);
                } else {
                    if (sendDataToTransport != 0) {
                        throw new AssertionError();
                    }
                    cleanUpAgent(0);
                }
            } catch (AgentException | TaskException e) {
                cleanUpAgent(1);
                throw e;
            }
        } catch (PackageManager.NameNotFoundException e2) {
            KeyValueBackupReporter keyValueBackupReporter3 = this.mReporter;
            keyValueBackupReporter3.getClass();
            Slog.d("KeyValueBackupTask", "Package does not exist, skipping");
            BackupObserverUtils.sendBackupOnPackageResult(
                    keyValueBackupReporter3.mObserver, str, -2002);
            throw new AgentException(false, e2);
        }
    }

    public final void backupPm() {
        this.mReporter.getClass();
        Slog.d("KeyValueBackupTask", "Starting key-value backup of @pm@");
        PackageInfo packageInfo = new PackageInfo();
        this.mCurrentPackage = packageInfo;
        packageInfo.packageName = "@pm@";
        try {
            try {
                extractPmAgentData(packageInfo);
                int sendDataToTransport = sendDataToTransport(this.mCurrentPackage);
                if (sendDataToTransport == -1006) {
                    cleanUpAgent(2);
                } else {
                    if (sendDataToTransport != 0) {
                        throw new AssertionError();
                    }
                    cleanUpAgent(0);
                }
            } catch (TaskException e) {
                throw TaskException.stateCompromised(e);
            }
        } catch (AgentException | TaskException e2) {
            this.mReporter.getClass();
            Slog.e("KeyValueBackupTask", "Error during PM metadata backup", e2);
            cleanUpAgent(1);
            if (!(e2 instanceof TaskException)) {
                throw TaskException.stateCompromised(e2);
            }
            throw ((TaskException) e2);
        }
    }

    public final IBackupAgent bindAgent(PackageInfo packageInfo) {
        String str = packageInfo.packageName;
        try {
            IBackupAgent bindToAgentSynchronous =
                    this.mBackupManagerService.bindToAgentSynchronous(
                            packageInfo.applicationInfo,
                            0,
                            this.mBackupEligibilityRules.mBackupDestination);
            if (bindToAgentSynchronous != null) {
                return bindToAgentSynchronous;
            }
            BackupObserverUtils.sendBackupOnPackageResult(this.mReporter.mObserver, str, -1003);
            throw new AgentException(true);
        } catch (SecurityException e) {
            KeyValueBackupReporter keyValueBackupReporter = this.mReporter;
            keyValueBackupReporter.getClass();
            Slog.d("KeyValueBackupTask", "Error in bind/backup", e);
            BackupObserverUtils.sendBackupOnPackageResult(
                    keyValueBackupReporter.mObserver, str, -1003);
            throw new AgentException(true, e);
        }
    }

    public final void cleanUpAgent(int i) {
        if (i == 0) {
            this.mNewStateFile.renameTo(this.mSavedStateFile);
        } else if (i == 1) {
            File file = this.mNewStateFile;
            if (file != null) {
                file.delete();
            }
        } else {
            if (i != 2) {
                throw new IllegalArgumentException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(
                                i, "Unknown state transaction "));
            }
            this.mSavedStateFile.delete();
            this.mNewStateFile.delete();
        }
        File file2 = this.mBackupDataFile;
        if (file2 != null) {
            file2.delete();
        }
        this.mBlankStateFile.delete();
        this.mSavedStateFile = null;
        this.mBackupDataFile = null;
        this.mNewStateFile = null;
        tryCloseFileDescriptor(this.mSavedState, "old state");
        tryCloseFileDescriptor(this.mBackupData, "backup data");
        tryCloseFileDescriptor(this.mNewState, "new state");
        this.mSavedState = null;
        this.mBackupData = null;
        this.mNewState = null;
        ApplicationInfo applicationInfo = this.mCurrentPackage.applicationInfo;
        if (applicationInfo != null) {
            UserBackupManagerService userBackupManagerService = this.mBackupManagerService;
            userBackupManagerService.getClass();
            try {
                userBackupManagerService.mActivityManager.unbindBackupAgent(applicationInfo);
            } catch (RemoteException unused) {
            }
        }
        this.mAgent = null;
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public final void execute() {}

    public final void extractAgentData(PackageInfo packageInfo) {
        this.mBackupManagerService.setWorkSource(new WorkSource(packageInfo.applicationInfo.uid));
        try {
            IBackupAgent bindAgent = bindAgent(packageInfo);
            this.mAgent = bindAgent;
            extractAgentData(packageInfo, bindAgent);
        } finally {
            this.mBackupManagerService.setWorkSource(null);
        }
    }

    public final void extractAgentData(PackageInfo packageInfo, final IBackupAgent iBackupAgent) {
        boolean z;
        final long backupQuota;
        final int transportFlags;
        String str = packageInfo.packageName;
        this.mReporter.getClass();
        Slog.d("KeyValueBackupTask", "Invoking agent on " + str);
        this.mSavedStateFile = new File(this.mStateDirectory, str);
        this.mBackupDataFile =
                new File(
                        this.mDataDirectory,
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                str, STAGING_FILE_SUFFIX));
        this.mNewStateFile =
                new File(
                        this.mStateDirectory,
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                str, NEW_STATE_FILE_SUFFIX));
        this.mReporter.getClass();
        try {
            this.mSavedState =
                    ParcelFileDescriptor.open(
                            this.mNonIncremental ? this.mBlankStateFile : this.mSavedStateFile,
                            402653184);
            this.mBackupData = ParcelFileDescriptor.open(this.mBackupDataFile, 1006632960);
            this.mNewState = ParcelFileDescriptor.open(this.mNewStateFile, 1006632960);
            if (this.mUserId == 0 && !SELinux.restorecon(this.mBackupDataFile)) {
                KeyValueBackupReporter keyValueBackupReporter = this.mReporter;
                File file = this.mBackupDataFile;
                keyValueBackupReporter.getClass();
                Slog.e("KeyValueBackupTask", "SELinux restorecon failed on " + file);
            }
            BackupTransportClient connectOrThrow =
                    this.mTransportConnection.connectOrThrow("KVBT.extractAgentData()");
            backupQuota = connectOrThrow.getBackupQuota(str, false);
            transportFlags = connectOrThrow.getTransportFlags();
        } catch (Exception e) {
            e = e;
            z = false;
        }
        try {
            this.mPendingCall =
                    new RemoteCall(
                            this.mCancelled,
                            new RemoteCallable() { // from class:
                                                   // com.android.server.backup.keyvalue.KeyValueBackupTask$$ExternalSyntheticLambda1
                                @Override // com.android.server.backup.remote.RemoteCallable
                                public final void call(FutureBackupCallback futureBackupCallback) {
                                    IBackupAgent iBackupAgent2 = iBackupAgent;
                                    KeyValueBackupTask keyValueBackupTask = KeyValueBackupTask.this;
                                    iBackupAgent2.doBackup(
                                            keyValueBackupTask.mSavedState,
                                            keyValueBackupTask.mBackupData,
                                            keyValueBackupTask.mNewState,
                                            backupQuota,
                                            futureBackupCallback,
                                            transportFlags);
                                }
                            },
                            this.mAgentTimeoutParameters.getKvBackupAgentTimeoutMillis());
            RemoteResult call = this.mPendingCall.call();
            this.mReporter.getClass();
            this.mPendingCall = null;
            if (call == RemoteResult.FAILED_THREAD_INTERRUPTED) {
                this.mCancelled = true;
                this.mReporter.onAgentCancelled(packageInfo);
                throw new TaskException(-1000, false);
            }
            if (call == RemoteResult.FAILED_CANCELLED) {
                this.mReporter.onAgentCancelled(packageInfo);
                throw new TaskException(-1000, false);
            }
            if (call == RemoteResult.FAILED_TIMED_OUT) {
                KeyValueBackupReporter keyValueBackupReporter2 = this.mReporter;
                keyValueBackupReporter2.getClass();
                String str2 = packageInfo.packageName;
                BootReceiver$$ExternalSyntheticOutline0.m58m(
                        "Agent ", str2, " timed out", "KeyValueBackupTask");
                EventLog.writeEvent(2823, str2);
                Bundle bundle = new Bundle();
                bundle.putBoolean("android.app.backup.extra.LOG_CANCEL_ALL", false);
                keyValueBackupReporter2.mBackupManagerMonitorEventSender.monitorEvent(
                        21, packageInfo, 2, bundle);
                throw new AgentException(true);
            }
            Preconditions.checkState(call.mType == 0);
            Preconditions.checkState(call.mType == 0, "Can't obtain value of failed result");
            long j = call.mValue;
            if (j != -1) {
                Preconditions.checkState(j == 0);
                return;
            }
            KeyValueBackupReporter keyValueBackupReporter3 = this.mReporter;
            keyValueBackupReporter3.getClass();
            String str3 = packageInfo.packageName;
            BackupObserverUtils.sendBackupOnPackageResult(
                    keyValueBackupReporter3.mObserver, str3, -1003);
            EventLog.writeEvent(2823, str3, "result error");
            Slog.w("KeyValueBackupTask", "Agent " + str3 + " error in onBackup()");
            throw new AgentException(true);
        } catch (Exception e2) {
            e = e2;
            z = true;
            KeyValueBackupReporter keyValueBackupReporter4 = this.mReporter;
            keyValueBackupReporter4.getClass();
            if (z) {
                Slog.e("KeyValueBackupTask", "Error invoking agent on " + str + ": " + e);
                BackupObserverUtils.sendBackupOnPackageResult(
                        keyValueBackupReporter4.mObserver, str, -1003);
            } else {
                Slog.e("KeyValueBackupTask", "Error before invoking agent on " + str + ": " + e);
            }
            EventLog.writeEvent(2823, str, e.toString());
            if (!z) {
                throw new TaskException(-1000, false);
            }
            throw new AgentException(true, e);
        }
    }

    public final void extractPmAgentData(PackageInfo packageInfo) {
        Preconditions.checkArgument(packageInfo.packageName.equals("@pm@"));
        UserBackupManagerService userBackupManagerService = this.mBackupManagerService;
        BackupEligibilityRules backupEligibilityRules = this.mBackupEligibilityRules;
        userBackupManagerService.getClass();
        PackageManager packageManager = userBackupManagerService.mPackageManager;
        int i = userBackupManagerService.mUserId;
        PackageManagerBackupAgent packageManagerBackupAgent =
                new PackageManagerBackupAgent(packageManager, i, backupEligibilityRules);
        packageManagerBackupAgent.attach(userBackupManagerService.mContext);
        packageManagerBackupAgent.onCreate(UserHandle.of(i));
        IBackupAgent asInterface =
                IBackupAgent.Stub.asInterface(packageManagerBackupAgent.onBind());
        this.mAgent = asInterface;
        extractAgentData(packageInfo, asInterface);
    }

    public final File getTopLevelSuccessStateDirectory(boolean z) {
        File file = new File(this.mStateDirectory, "backing-up");
        if (file.exists() || !z || file.mkdirs()) {
            return file;
        }
        Log.e("KVBT", "Unable to create backing-up state directory");
        return null;
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public final void handleCancel(boolean z) {
        Preconditions.checkArgument(z, "Can't partially cancel a key-value backup task");
        markCancel();
        waitCancel();
    }

    public final void informTransportOfUnchangedApps(Set set) {
        File topLevelSuccessStateDirectory = getTopLevelSuccessStateDirectory(false);
        String[] list =
                topLevelSuccessStateDirectory == null ? null : topLevelSuccessStateDirectory.list();
        if (list == null) {
            return;
        }
        int i = this.mUserInitiated ? 9 : 8;
        try {
            BackupTransportClient connectOrThrow =
                    this.mTransportConnection.connectOrThrow(
                            "KVBT.informTransportOfEmptyBackups()");
            boolean z = false;
            for (String str : list) {
                if (((HashSet) set).contains(str)) {
                    Log.v("KVBT", "Skipping package which was backed up this time: " + str);
                } else {
                    try {
                        PackageInfo packageInfo = this.mPackageManager.getPackageInfo(str, 0);
                        if (isEligibleForNoDataCall(packageInfo)) {
                            sendNoDataChangedTo(connectOrThrow, packageInfo, i);
                            z = true;
                        } else {
                            File topLevelSuccessStateDirectory2 =
                                    getTopLevelSuccessStateDirectory(true);
                            File file =
                                    topLevelSuccessStateDirectory2 == null
                                            ? null
                                            : new File(topLevelSuccessStateDirectory2, str);
                            if (file != null) {
                                clearStatus(file, str);
                            }
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                        File topLevelSuccessStateDirectory3 =
                                getTopLevelSuccessStateDirectory(true);
                        File file2 =
                                topLevelSuccessStateDirectory3 == null
                                        ? null
                                        : new File(topLevelSuccessStateDirectory3, str);
                        if (file2 != null) {
                            clearStatus(file2, str);
                        }
                    }
                }
            }
            if (z) {
                PackageInfo packageInfo2 = new PackageInfo();
                packageInfo2.packageName = NO_DATA_END_SENTINEL;
                sendNoDataChangedTo(connectOrThrow, packageInfo2, i);
            }
        } catch (RemoteException | TransportNotAvailableException e) {
            Log.e("KVBT", "Could not inform transport of all unchanged apps", e);
        }
    }

    public final boolean isEligibleForNoDataCall(PackageInfo packageInfo) {
        return (this.mBackupEligibilityRules.appGetsFullBackup(packageInfo) ^ true)
                && this.mBackupEligibilityRules.appIsRunningAndEligibleForBackupWithTransport(
                        this.mTransportConnection, packageInfo.packageName);
    }

    public void markCancel() {
        this.mReporter.getClass();
        this.mCancelled = true;
        RemoteCall remoteCall = this.mPendingCall;
        if (remoteCall != null) {
            remoteCall.mFuture.complete(RemoteResult.FAILED_CANCELLED);
        }
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public final void operationComplete(long j) {}

    @Override // java.lang.Runnable
    public final void run() {
        long j;
        int status;
        Process.setThreadPriority(10);
        int i = 0;
        this.mHasDataToBackup = false;
        HashSet hashSet = new HashSet();
        long j2 = -1000;
        try {
            startTask();
            while (!((ArrayList) this.mQueue).isEmpty() && !this.mCancelled) {
                String str = (String) ((ArrayList) this.mQueue).remove(0);
                try {
                    if ("@pm@".equals(str)) {
                        backupPm();
                    } else {
                        backupPackage(str);
                    }
                    setSuccessState(str, true);
                    hashSet.add(str);
                } catch (AgentException e) {
                    setSuccessState(str, false);
                    if (e.isTransitory()) {
                        this.mBackupManagerService.dataChangedImpl(str);
                    }
                }
            }
            informTransportOfUnchangedApps(hashSet);
            status = 0;
        } catch (TaskException e2) {
            if (e2.isStateCompromised()) {
                this.mBackupManagerService.resetBackupState(this.mStateDirectory);
            }
            this.mReporter.getClass();
            try {
                BackupTransportClient connectOrThrow =
                        this.mTransportConnection.connectOrThrow("KVBT.revertTask()");
                AndroidFuture newFuture = connectOrThrow.mTransportFutures.newFuture();
                connectOrThrow.mTransportBinder.requestBackupTime(newFuture);
                Long l = (Long) connectOrThrow.getFutureResult(newFuture);
                j = l == null ? -1000L : l.longValue();
            } catch (Exception e3) {
                this.mReporter.getClass();
                Slog.w(
                        "KeyValueBackupTask",
                        "Unable to contact transport for recommended backoff: " + e3);
                j = 0L;
            }
            UserBackupManagerService userBackupManagerService = this.mBackupManagerService;
            KeyValueBackupJob.schedule(
                    userBackupManagerService.mUserId,
                    userBackupManagerService.mContext,
                    j,
                    userBackupManagerService);
            Iterator it = this.mOriginalQueue.iterator();
            while (it.hasNext()) {
                this.mBackupManagerService.dataChangedImpl((String) it.next());
            }
            status = e2.getStatus();
        }
        Iterator it2 = ((ArrayList) this.mQueue).iterator();
        while (it2.hasNext()) {
            this.mBackupManagerService.dataChangedImpl((String) it2.next());
        }
        DataChangedJournal dataChangedJournal = this.mJournal;
        if (dataChangedJournal != null && !dataChangedJournal.mFile.delete()) {
            KeyValueBackupReporter keyValueBackupReporter = this.mReporter;
            DataChangedJournal dataChangedJournal2 = this.mJournal;
            keyValueBackupReporter.getClass();
            Slog.e(
                    "KeyValueBackupTask",
                    "Unable to remove backup journal file " + dataChangedJournal2);
        }
        long j3 = this.mBackupManagerService.mCurrentToken;
        String str2 = null;
        if (this.mHasDataToBackup && status == 0 && j3 == 0) {
            try {
                BackupTransportClient connectOrThrow2 =
                        this.mTransportConnection.connectOrThrow("KVBT.finishTask()");
                str2 = connectOrThrow2.name();
                UserBackupManagerService userBackupManagerService2 = this.mBackupManagerService;
                AndroidFuture newFuture2 = connectOrThrow2.mTransportFutures.newFuture();
                connectOrThrow2.mTransportBinder.getCurrentRestoreSet(newFuture2);
                Long l2 = (Long) connectOrThrow2.getFutureResult(newFuture2);
                if (l2 != null) {
                    j2 = l2.longValue();
                }
                userBackupManagerService2.mCurrentToken = j2;
                this.mBackupManagerService.writeRestoreTokens();
            } catch (Exception e4) {
                this.mReporter.getClass();
                Slog.e("KeyValueBackupTask", "Transport threw reporting restore set: " + e4);
            }
        }
        synchronized (this.mQueueLock) {
            this.mBackupManagerService.mBackupRunning = false;
            if (status == -1001) {
                this.mReporter.getClass();
                EventLog.writeEvent(2826, str2);
                try {
                    triggerTransportInitializationLocked();
                } catch (Exception e5) {
                    this.mReporter.getClass();
                    Slog.w(
                            "KeyValueBackupTask",
                            "Failed to query transport name for pending init: " + e5);
                    status = -1000;
                }
            }
        }
        ((LifecycleOperationStorage) this.mOperationStorage).removeOperation(this.mCurrentOpToken);
        this.mReporter.getClass();
        Slog.i("KeyValueBackupTask", "K/V backup pass finished");
        if (this.mCancelled) {
            this.mCancelAcknowledged.open();
        }
        if (!this.mCancelled
                && status == 0
                && this.mFullBackupTask != null
                && !this.mPendingFullBackups.isEmpty()) {
            KeyValueBackupReporter keyValueBackupReporter2 = this.mReporter;
            List list = this.mPendingFullBackups;
            keyValueBackupReporter2.getClass();
            Slog.d("KeyValueBackupTask", "Starting full backups for: " + list);
            new Thread(this.mFullBackupTask, "full-transport-requested").start();
            return;
        }
        PerformFullTransportBackupTask performFullTransportBackupTask = this.mFullBackupTask;
        if (performFullTransportBackupTask != null) {
            performFullTransportBackupTask.unregisterTask();
        }
        this.mTaskFinishedListener.onFinished("KVBT.finishTask()");
        KeyValueBackupReporter keyValueBackupReporter3 = this.mReporter;
        if (this.mCancelled) {
            i = -2003;
        } else if (status != -1005 && status != -1002 && status != 0) {
            i = -1000;
        }
        BackupObserverUtils.sendBackupFinished(keyValueBackupReporter3.mObserver, i);
        this.mBackupManagerService.mWakelock.release();
    }

    /* JADX WARN: Code restructure failed: missing block: B:174:0x0136, code lost:

       if (java.util.Objects.equals(r2, r13) != false) goto L96;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int sendDataToTransport() {
        /*
            Method dump skipped, instructions count: 857
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.keyvalue.KeyValueBackupTask.sendDataToTransport():int");
    }

    public final int sendDataToTransport(PackageInfo packageInfo) {
        try {
            return sendDataToTransport();
        } catch (IOException e) {
            KeyValueBackupReporter keyValueBackupReporter = this.mReporter;
            String str = packageInfo.packageName;
            keyValueBackupReporter.getClass();
            Slog.w("KeyValueBackupTask", "Unable to read/write agent data for " + str + ": " + e);
            int i = TaskException.$r8$clinit;
            if (e instanceof TaskException) {
                throw ((TaskException) e);
            }
            throw new TaskException(e, false, -1000);
        }
    }

    public final void sendNoDataChangedTo(
            BackupTransportClient backupTransportClient, PackageInfo packageInfo, int i) {
        try {
            ParcelFileDescriptor open = ParcelFileDescriptor.open(this.mBlankStateFile, 402653184);
            try {
                BackupTransportClient.TransportStatusCallbackPool transportStatusCallbackPool =
                        backupTransportClient.mCallbackPool;
                TransportStatusCallback acquire = transportStatusCallbackPool.acquire();
                try {
                    backupTransportClient.mTransportBinder.performBackup(
                            packageInfo, open, i, acquire);
                    int operationStatus = acquire.getOperationStatus();
                    transportStatusCallbackPool.recycle(acquire);
                    if (operationStatus != -1000 && operationStatus != -1001) {
                        backupTransportClient.finishBackup();
                        return;
                    }
                    Log.w(
                            "KVBT",
                            "Aborting informing transport of unchanged apps, transport errored");
                } catch (Throwable th) {
                    transportStatusCallbackPool.recycle(acquire);
                    throw th;
                }
            } finally {
                IoUtils.closeQuietly(open);
            }
        } catch (FileNotFoundException unused) {
            Log.e("KVBT", "Unable to find blank state file, aborting unchanged apps signal.");
        }
    }

    public final void setSuccessState(String str, boolean z) {
        File topLevelSuccessStateDirectory = getTopLevelSuccessStateDirectory(true);
        File file =
                topLevelSuccessStateDirectory == null
                        ? null
                        : new File(topLevelSuccessStateDirectory, str);
        if (file == null || file.exists() == z) {
            return;
        }
        if (!z) {
            clearStatus(file, str);
            return;
        }
        try {
            if (file.createNewFile()) {
                return;
            }
            Log.w("KVBT", "Unable to permanently record success for " + str);
        } catch (IOException e) {
            Log.w("KVBT", "Unable to permanently record success for " + str, e);
        }
    }

    public final void startTask() {
        if (this.mBackupManagerService.isBackupOperationInProgress()) {
            this.mReporter.getClass();
            Slog.d("KeyValueBackupTask", "Skipping backup since one is already in progress");
            throw new TaskException(-1000, false);
        }
        List list = this.mPendingFullBackups;
        UserBackupManagerService userBackupManagerService = this.mBackupManagerService;
        OperationStorage operationStorage = this.mOperationStorage;
        TransportConnection transportConnection = this.mTransportConnection;
        String[] strArr = (String[]) list.toArray(new String[list.size()]);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        KeyValueBackupReporter keyValueBackupReporter = this.mReporter;
        this.mFullBackupTask =
                new PerformFullTransportBackupTask(
                        userBackupManagerService,
                        operationStorage,
                        transportConnection,
                        strArr,
                        false,
                        null,
                        countDownLatch,
                        keyValueBackupReporter.mObserver,
                        keyValueBackupReporter.mBackupManagerMonitorEventSender.mMonitor,
                        this.mTaskFinishedListener,
                        this.mUserInitiated,
                        this.mBackupEligibilityRules);
        OperationStorage operationStorage2 = this.mOperationStorage;
        int i = this.mCurrentOpToken;
        LifecycleOperationStorage lifecycleOperationStorage =
                (LifecycleOperationStorage) operationStorage2;
        lifecycleOperationStorage.getClass();
        lifecycleOperationStorage.registerOperationForPackages(i, Sets.newHashSet(), this, 2);
        if (((ArrayList) this.mQueue).isEmpty() && this.mPendingFullBackups.isEmpty()) {
            this.mReporter.getClass();
            Slog.w("KeyValueBackupTask", "Backup begun with an empty queue, nothing to do");
            return;
        }
        if (((ArrayList) this.mQueue).remove("@pm@") || !this.mNonIncremental) {
            ((ArrayList) this.mQueue).add(0, "@pm@");
        } else {
            this.mReporter.getClass();
            Slog.d("KeyValueBackupTask", "Skipping backup of PM metadata");
        }
        KeyValueBackupReporter keyValueBackupReporter2 = this.mReporter;
        List list2 = this.mQueue;
        keyValueBackupReporter2.getClass();
        Slog.i(
                "KeyValueBackupTask",
                "Beginning backup of " + ((ArrayList) list2).size() + " targets");
        File file = new File(this.mStateDirectory, "@pm@");
        try {
            BackupTransportClient connectOrThrow =
                    this.mTransportConnection.connectOrThrow("KVBT.startTask()");
            String name = connectOrThrow.name();
            if (name.contains("EncryptedLocalTransport")) {
                this.mNonIncremental = true;
            }
            this.mReporter.getClass();
            EventLog.writeEvent(2821, name);
            if (file.length() <= 0) {
                this.mReporter.getClass();
                Slog.i("KeyValueBackupTask", "Initializing transport and resetting backup state");
                this.mBackupManagerService.resetBackupState(this.mStateDirectory);
                BackupTransportClient.TransportStatusCallbackPool transportStatusCallbackPool =
                        connectOrThrow.mCallbackPool;
                TransportStatusCallback acquire = transportStatusCallbackPool.acquire();
                try {
                    connectOrThrow.mTransportBinder.initializeDevice(acquire);
                    int operationStatus = acquire.getOperationStatus();
                    transportStatusCallbackPool.recycle(acquire);
                    this.mReporter.getClass();
                    if (operationStatus == 0) {
                        EventLog.writeEvent(2827, new Object[0]);
                    } else {
                        EventLog.writeEvent(2822, "(initialize)");
                        Slog.e("KeyValueBackupTask", "Transport error in initializeDevice()");
                    }
                    if (operationStatus != 0) {
                        throw new TaskException(-1000, true);
                    }
                } catch (Throwable th) {
                    transportStatusCallbackPool.recycle(acquire);
                    throw th;
                }
            }
        } catch (TaskException e) {
            throw e;
        } catch (Exception e2) {
            this.mReporter.getClass();
            Slog.e("KeyValueBackupTask", "Error during initialization", e2);
            throw new TaskException(-1000, true);
        }
    }

    public final void triggerTransportInitializationLocked() {
        this.mBackupManagerService.mPendingInits.add(
                this.mTransportConnection
                        .connectOrThrow("KVBT.triggerTransportInitializationLocked")
                        .name());
        new File(this.mStateDirectory, "@pm@").delete();
        this.mBackupManagerService.backupNow();
    }

    public final void tryCloseFileDescriptor(Closeable closeable, String str) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
                this.mReporter.getClass();
                Slog.w("KeyValueBackupTask", "Error closing " + str + " file-descriptor");
            }
        }
    }

    public void waitCancel() {
        this.mCancelAcknowledged.block();
    }
}

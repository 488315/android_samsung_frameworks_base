package com.android.server.backup.restore;

import android.app.IActivityManager;
import android.app.IBackupAgent;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IRestoreObserver;
import android.app.backup.RestoreDescription;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.EventLog;
import android.util.Slog;

import com.android.internal.infra.AndroidFuture;
import com.android.server.AppWidgetBackupBridge;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.backup.BackupRestoreTask;
import com.android.server.backup.BackupUtils;
import com.android.server.backup.Flags;
import com.android.server.backup.OperationStorage;
import com.android.server.backup.PackageManagerBackupAgent;
import com.android.server.backup.TransportManager;
import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.internal.BackupHandler;
import com.android.server.backup.internal.LifecycleOperationStorage;
import com.android.server.backup.internal.OnTaskFinishedListener;
import com.android.server.backup.keyvalue.KeyValueBackupTask;
import com.android.server.backup.transport.BackupTransportClient;
import com.android.server.backup.transport.TransportConnection;
import com.android.server.backup.transport.TransportStatusCallback;
import com.android.server.backup.utils.BackupEligibilityRules;
import com.android.server.backup.utils.BackupManagerMonitorEventSender;

import libcore.io.IoUtils;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class PerformUnifiedRestoreTask implements BackupRestoreTask {
    public final UserBackupManagerService backupManagerService;
    public final List mAcceptSet;
    public IBackupAgent mAgent;
    public final BackupAgentTimeoutParameters mAgentTimeoutParameters;
    public Boolean mAreVToUListsSet;
    public ParcelFileDescriptor mBackupData;
    public File mBackupDataName;
    public final BackupEligibilityRules mBackupEligibilityRules;
    public final BackupManagerMonitorEventSender mBackupManagerMonitorEventSender;
    public PackageInfo mCurrentPackage;
    public boolean mDidLaunch;
    public final int mEphemeralOpToken;
    public boolean mFinished;
    public final boolean mIsSystemRestore;
    public final OnTaskFinishedListener mListener;
    public ParcelFileDescriptor mNewState;
    public File mNewStateName;
    public IRestoreObserver mObserver;
    public final OperationStorage mOperationStorage;
    public PackageManagerBackupAgent mPmAgent;
    public final int mPmToken;
    public int mRestoreAttemptedAppsCount;
    public RestoreDescription mRestoreDescription;
    public File mStageName;
    public final long mStartRealtime;
    public UnifiedRestoreState mState;
    public File mStateDir;
    public int mStatus;
    public final PackageInfo mTargetPackage;
    public final long mToken;
    public final TransportConnection mTransportConnection;
    public final TransportManager mTransportManager;
    public final int mUserId;
    public List mVToUAllowlist;
    public List mVToUDenylist;
    public byte[] mWidgetData;

    public final class StreamFeederThread extends RestoreEngine
            implements Runnable, BackupRestoreTask {
        public FullRestoreEngine mEngine;
        public FullRestoreEngineThread mEngineThread;
        public final int mEphemeralOpToken;
        public final ParcelFileDescriptor[] mTransportPipes = ParcelFileDescriptor.createPipe();
        public final ParcelFileDescriptor[] mEnginePipes = ParcelFileDescriptor.createPipe();

        public StreamFeederThread() {
            this.mEphemeralOpToken =
                    PerformUnifiedRestoreTask.this.backupManagerService
                            .generateRandomIntegerToken();
            setRunning(true);
        }

        @Override // com.android.server.backup.BackupRestoreTask
        public final void execute() {}

        @Override // com.android.server.backup.BackupRestoreTask
        public final void handleCancel(boolean z) {
            ((LifecycleOperationStorage) PerformUnifiedRestoreTask.this.mOperationStorage)
                    .removeOperation(this.mEphemeralOpToken);
            Slog.w("StreamFeederThread", "Full-data restore target timed out; shutting down");
            PerformUnifiedRestoreTask.this.getClass();
            Bundle addRestoreOperationTypeToEvent =
                    PerformUnifiedRestoreTask.addRestoreOperationTypeToEvent(null);
            PerformUnifiedRestoreTask performUnifiedRestoreTask = PerformUnifiedRestoreTask.this;
            performUnifiedRestoreTask.mBackupManagerMonitorEventSender.monitorEvent(
                    45,
                    performUnifiedRestoreTask.mCurrentPackage,
                    2,
                    addRestoreOperationTypeToEvent);
            FullRestoreEngineThread fullRestoreEngineThread = this.mEngineThread;
            IoUtils.closeQuietly(fullRestoreEngineThread.mEngineStream);
            FullRestoreEngine fullRestoreEngine = fullRestoreEngineThread.mEngine;
            fullRestoreEngine.tearDownPipes();
            fullRestoreEngine.mResult.set(-2);
            fullRestoreEngine.setRunning(false);
            IoUtils.closeQuietly(this.mEnginePipes[1]);
            ParcelFileDescriptor[] parcelFileDescriptorArr = this.mEnginePipes;
            parcelFileDescriptorArr[1] = null;
            IoUtils.closeQuietly(parcelFileDescriptorArr[0]);
            this.mEnginePipes[0] = null;
        }

        @Override // com.android.server.backup.BackupRestoreTask
        public final void operationComplete(long j) {}

        /* JADX WARN: Removed duplicated region for block: B:20:0x02f1  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x0324  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x02f8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:37:0x02f3  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 822
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.backup.restore.PerformUnifiedRestoreTask.StreamFeederThread.run():void");
        }
    }

    public PerformUnifiedRestoreTask(
            UserBackupManagerService userBackupManagerService,
            LifecycleOperationStorage lifecycleOperationStorage,
            TransportConnection transportConnection,
            IRestoreObserver iRestoreObserver,
            IBackupManagerMonitor iBackupManagerMonitor,
            long j,
            PackageInfo packageInfo,
            int i,
            boolean z,
            String[] strArr,
            OnTaskFinishedListener onTaskFinishedListener,
            BackupEligibilityRules backupEligibilityRules) {
        this.mAreVToUListsSet = Boolean.FALSE;
        this.backupManagerService = userBackupManagerService;
        this.mOperationStorage = lifecycleOperationStorage;
        int i2 = userBackupManagerService.mUserId;
        this.mUserId = i2;
        this.mTransportManager = userBackupManagerService.mTransportManager;
        this.mEphemeralOpToken = userBackupManagerService.generateRandomIntegerToken();
        this.mState = UnifiedRestoreState.INITIAL;
        this.mStartRealtime = SystemClock.elapsedRealtime();
        this.mTransportConnection = transportConnection;
        this.mObserver = iRestoreObserver;
        this.mBackupManagerMonitorEventSender =
                new BackupManagerMonitorEventSender(iBackupManagerMonitor);
        this.mToken = j;
        this.mPmToken = i;
        this.mTargetPackage = packageInfo;
        this.mIsSystemRestore = z;
        this.mFinished = false;
        this.mDidLaunch = false;
        this.mListener = onTaskFinishedListener;
        BackupAgentTimeoutParameters backupAgentTimeoutParameters =
                userBackupManagerService.mAgentTimeoutParameters;
        Objects.requireNonNull(backupAgentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = backupAgentTimeoutParameters;
        this.mBackupEligibilityRules = backupEligibilityRules;
        if (packageInfo != null) {
            ArrayList arrayList = new ArrayList();
            this.mAcceptSet = arrayList;
            arrayList.add(packageInfo);
        } else {
            if (strArr == null) {
                List storableApplications =
                        PackageManagerBackupAgent.getStorableApplications(
                                userBackupManagerService.mPackageManager,
                                i2,
                                backupEligibilityRules);
                int size = storableApplications.size();
                strArr = new String[size];
                for (int i3 = 0; i3 < size; i3++) {
                    strArr[i3] = ((PackageInfo) storableApplications.get(i3)).packageName;
                }
                BootReceiver$$ExternalSyntheticOutline0.m(
                        size, "Full restore; asking about ", " apps", "BackupManagerService");
            }
            this.mAcceptSet = new ArrayList(strArr.length);
            boolean z2 = false;
            boolean z3 = false;
            for (String str : strArr) {
                try {
                    PackageInfo packageInfoAsUser =
                            userBackupManagerService.mPackageManager.getPackageInfoAsUser(
                                    str, 0, this.mUserId);
                    boolean z4 = true;
                    if ("android".equals(packageInfoAsUser.packageName)) {
                        z2 = true;
                    } else if ("com.android.providers.settings"
                            .equals(packageInfoAsUser.packageName)) {
                        z3 = true;
                    } else {
                        ApplicationInfo applicationInfo = packageInfoAsUser.applicationInfo;
                        if (backupEligibilityRules.appIsEligibleForBackup(applicationInfo)) {
                            if (Flags.enableSkippingRestoreLaunchedApps()) {
                                if (backupEligibilityRules.mSkipRestoreForLaunchedApps
                                        && applicationInfo.backupAgentName == null) {
                                    z4 =
                                            true
                                                    ^ backupEligibilityRules.mPackageManagerInternal
                                                            .wasPackageEverLaunched(
                                                                    backupEligibilityRules.mUserId,
                                                                    applicationInfo.packageName);
                                }
                                if (!z4) {}
                            }
                            this.mAcceptSet.add(packageInfoAsUser);
                        }
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
            if (z2) {
                try {
                    this.mAcceptSet.add(
                            0,
                            userBackupManagerService.mPackageManager.getPackageInfoAsUser(
                                    "android", 0, this.mUserId));
                } catch (PackageManager.NameNotFoundException unused2) {
                }
            }
            if (z3) {
                try {
                    this.mAcceptSet.add(
                            userBackupManagerService.mPackageManager.getPackageInfoAsUser(
                                    "com.android.providers.settings", 0, this.mUserId));
                } catch (PackageManager.NameNotFoundException unused3) {
                }
            }
        }
        this.mAcceptSet = userBackupManagerService.filterUserFacingPackages(this.mAcceptSet);
    }

    public PerformUnifiedRestoreTask(
            UserBackupManagerService userBackupManagerService,
            TransportConnection transportConnection,
            String str,
            String str2) {
        this.mAreVToUListsSet = Boolean.FALSE;
        this.mListener = null;
        this.mAgentTimeoutParameters = null;
        this.mOperationStorage = null;
        this.mTransportConnection = transportConnection;
        this.mTransportManager = null;
        this.mEphemeralOpToken = 0;
        this.mUserId = 0;
        this.mBackupEligibilityRules = null;
        this.backupManagerService = userBackupManagerService;
        this.mBackupManagerMonitorEventSender = new BackupManagerMonitorEventSender(null);
        this.mVToUAllowlist = createVToUList(str);
        this.mVToUDenylist = createVToUList(str2);
    }

    public static Bundle addRestoreOperationTypeToEvent(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt("android.app.backup.extra.OPERATION_TYPE", 1);
        return bundle;
    }

    public List createVToUList(String str) {
        return str != null ? Arrays.asList(str.split(",")) : new ArrayList();
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public final void execute() {
        UnifiedRestoreState unifiedRestoreState;
        UnifiedRestoreState unifiedRestoreState2;
        String str;
        String packageName;
        PackageManagerBackupAgent.Metadata metadata;
        PackageManagerBackupAgent.Metadata metadata2;
        long j;
        PackageManagerBackupAgent packageManagerBackupAgent;
        Set keySet;
        int ordinal = this.mState.ordinal();
        if (ordinal == 0) {
            startRestore();
            return;
        }
        if (ordinal != 1) {
            if (ordinal == 2) {
                PackageInfo packageInfo = this.mCurrentPackage;
                String str2 = packageInfo.packageName;
                Bundle addRestoreOperationTypeToEvent = addRestoreOperationTypeToEvent(null);
                BackupManagerMonitorEventSender backupManagerMonitorEventSender =
                        this.mBackupManagerMonitorEventSender;
                backupManagerMonitorEventSender.monitorEvent(
                        58, packageInfo, 3, addRestoreOperationTypeToEvent);
                String str3 = this.mCurrentPackage.applicationInfo.backupAgentName;
                UnifiedRestoreState unifiedRestoreState3 = UnifiedRestoreState.RUNNING_QUEUE;
                if (str3 == null || "".equals(str3)) {
                    backupManagerMonitorEventSender.monitorEvent(
                            28, this.mCurrentPackage, 2, addRestoreOperationTypeToEvent(null));
                    EventLog.writeEvent(2832, str2, "Package has no agent");
                    executeNextState(unifiedRestoreState3);
                    return;
                }
                HashMap hashMap = this.mPmAgent.mRestoredSignatures;
                if (hashMap == null) {
                    Slog.w("PMBA", "getRestoredMetadata() before metadata read!");
                    metadata2 = null;
                } else {
                    metadata2 = (PackageManagerBackupAgent.Metadata) hashMap.get(str2);
                }
                if (!BackupUtils.signaturesMatch(
                        metadata2.sigHashes,
                        this.mCurrentPackage,
                        (PackageManagerInternal)
                                LocalServices.getService(PackageManagerInternal.class))) {
                    Slog.w("BackupManagerService", "Signature mismatch restoring " + str2);
                    backupManagerMonitorEventSender.monitorEvent(
                            29, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
                    EventLog.writeEvent(2832, str2, "Signature mismatch");
                    executeNextState(unifiedRestoreState3);
                    return;
                }
                IBackupAgent bindToAgentSynchronous =
                        this.backupManagerService.bindToAgentSynchronous(
                                this.mCurrentPackage.applicationInfo,
                                2,
                                this.mBackupEligibilityRules.mBackupDestination);
                this.mAgent = bindToAgentSynchronous;
                if (bindToAgentSynchronous == null) {
                    Slog.w("BackupManagerService", "Can't find backup agent for " + str2);
                    backupManagerMonitorEventSender.monitorEvent(
                            30, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
                    EventLog.writeEvent(2832, str2, "Restore agent missing");
                    executeNextState(unifiedRestoreState3);
                    return;
                }
                this.mDidLaunch = true;
                try {
                    initiateOneRestore(this.mCurrentPackage, metadata2.versionCode);
                    return;
                } catch (Exception e) {
                    Slog.e(
                            "BackupManagerService",
                            "Error when attempting restore: " + e.toString());
                    backupManagerMonitorEventSender.monitorEvent(
                            61, this.mCurrentPackage, 2, addRestoreOperationTypeToEvent(null));
                    keyValueAgentCleanup();
                    executeNextState(unifiedRestoreState3);
                    return;
                }
            }
            if (ordinal == 3) {
                PackageInfo packageInfo2 = this.mCurrentPackage;
                Bundle addRestoreOperationTypeToEvent2 = addRestoreOperationTypeToEvent(null);
                BackupManagerMonitorEventSender backupManagerMonitorEventSender2 =
                        this.mBackupManagerMonitorEventSender;
                backupManagerMonitorEventSender2.monitorEvent(
                        59, packageInfo2, 3, addRestoreOperationTypeToEvent2);
                try {
                    new Thread(new StreamFeederThread(), "unified-stream-feeder").start();
                    return;
                } catch (IOException unused) {
                    Slog.e("BackupManagerService", "Unable to construct pipes for stream restore!");
                    backupManagerMonitorEventSender2.monitorEvent(
                            64, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
                    executeNextState(UnifiedRestoreState.RUNNING_QUEUE);
                    return;
                }
            }
            if (ordinal == 4) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        new StringBuilder("restoreFinished packageName="),
                        this.mCurrentPackage.packageName,
                        "BackupManagerService");
                try {
                    BackupAgentTimeoutParameters backupAgentTimeoutParameters =
                            this.mAgentTimeoutParameters;
                    synchronized (backupAgentTimeoutParameters.mLock) {
                        j = backupAgentTimeoutParameters.mRestoreAgentFinishedTimeoutMillis;
                    }
                    this.backupManagerService.prepareOperationTimeout(
                            this.mEphemeralOpToken, j, this, 1);
                    this.mAgent.doRestoreFinished(
                            this.mEphemeralOpToken, this.backupManagerService.mBackupManagerBinder);
                    return;
                } catch (Exception e2) {
                    String str4 = this.mCurrentPackage.packageName;
                    Slog.e("BackupManagerService", "Unable to finalize restore of " + str4);
                    this.mBackupManagerMonitorEventSender.monitorEvent(
                            69, this.mCurrentPackage, 2, addRestoreOperationTypeToEvent(null));
                    EventLog.writeEvent(2832, str4, e2.toString());
                    keyValueAgentErrorCleanup(true);
                    executeNextState(UnifiedRestoreState.RUNNING_QUEUE);
                    return;
                }
            }
            if (ordinal != 5) {
                return;
            }
            if (this.mFinished) {
                Slog.e("BackupManagerService", "Duplicate finish");
            } else {
                try {
                    BackupTransportClient connectOrThrow =
                            this.mTransportConnection.connectOrThrow(
                                    "PerformUnifiedRestoreTask.finalizeRestore()");
                    BackupTransportClient.TransportStatusCallbackPool transportStatusCallbackPool =
                            connectOrThrow.mCallbackPool;
                    TransportStatusCallback acquire = transportStatusCallbackPool.acquire();
                    try {
                        connectOrThrow.mTransportBinder.finishRestore(acquire);
                        acquire.getOperationStatus();
                        transportStatusCallbackPool.recycle(acquire);
                    } catch (Throwable th) {
                        transportStatusCallbackPool.recycle(acquire);
                        throw th;
                    }
                } catch (Exception e3) {
                    Slog.e("BackupManagerService", "Error finishing restore", e3);
                }
                IRestoreObserver iRestoreObserver = this.mObserver;
                if (iRestoreObserver != null) {
                    try {
                        iRestoreObserver.restoreFinished(this.mStatus);
                    } catch (RemoteException unused2) {
                        Slog.w("BackupManagerService", "Restore observer went away: endRestore");
                        this.mObserver = null;
                    }
                }
                this.backupManagerService.mBackupHandler.removeMessages(8);
                int i = this.mPmToken;
                if (i > 0) {
                    try {
                        this.backupManagerService.mPackageManagerBinder.finishPackageInstall(
                                i, this.mDidLaunch);
                    } catch (RemoteException unused3) {
                    }
                } else {
                    this.backupManagerService.mBackupHandler.sendEmptyMessageDelayed(
                            8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
                }
                if (this.mIsSystemRestore) {
                    AppWidgetBackupBridge.systemRestoreFinished(this.mUserId);
                }
                if (this.mIsSystemRestore && (packageManagerBackupAgent = this.mPmAgent) != null) {
                    UserBackupManagerService userBackupManagerService = this.backupManagerService;
                    HashMap hashMap2 = packageManagerBackupAgent.mRestoredSignatures;
                    if (hashMap2 == null) {
                        Slog.w("PMBA", "getRestoredPackages() before metadata read!");
                        keySet = null;
                    } else {
                        keySet = hashMap2.keySet();
                    }
                    userBackupManagerService.mAncestralPackages = keySet;
                    UserBackupManagerService userBackupManagerService2 = this.backupManagerService;
                    userBackupManagerService2.mAncestralToken = this.mToken;
                    userBackupManagerService2.mAncestralBackupDestination =
                            this.mBackupEligibilityRules.mBackupDestination;
                    this.backupManagerService.writeRestoreTokens();
                }
                synchronized (this.backupManagerService.mPendingRestores) {
                    try {
                        if (((ArrayDeque) this.backupManagerService.mPendingRestores).size() > 0) {
                            Slog.d("BackupManagerService", "Starting next pending restore.");
                            PerformUnifiedRestoreTask performUnifiedRestoreTask =
                                    (PerformUnifiedRestoreTask)
                                            ((ArrayDeque)
                                                            this.backupManagerService
                                                                    .mPendingRestores)
                                                    .remove();
                            BackupHandler backupHandler = this.backupManagerService.mBackupHandler;
                            backupHandler.sendMessage(
                                    backupHandler.obtainMessage(20, performUnifiedRestoreTask));
                        } else {
                            this.backupManagerService.mIsRestoreInProgress = false;
                        }
                    } finally {
                    }
                }
                Slog.i("BackupManagerService", "Restore complete.");
                this.mBackupManagerMonitorEventSender.monitorEvent(
                        68, null, 3, addRestoreOperationTypeToEvent(null));
                this.mListener.onFinished("PerformUnifiedRestoreTask.finalizeRestore()");
            }
            this.mFinished = true;
            return;
        }
        int i2 = this.mUserId;
        UserBackupManagerService userBackupManagerService3 = this.backupManagerService;
        BackupManagerMonitorEventSender backupManagerMonitorEventSender3 =
                this.mBackupManagerMonitorEventSender;
        UnifiedRestoreState unifiedRestoreState4 = UnifiedRestoreState.FINAL;
        try {
            try {
                try {
                    BackupTransportClient connectOrThrow2 =
                            this.mTransportConnection.connectOrThrow(
                                    "PerformUnifiedRestoreTask.dispatchNextRestore()");
                    AndroidFuture newFuture = connectOrThrow2.mTransportFutures.newFuture();
                    connectOrThrow2.mTransportBinder.nextRestorePackage(newFuture);
                    RestoreDescription restoreDescription =
                            (RestoreDescription) connectOrThrow2.getFutureResult(newFuture);
                    this.mRestoreDescription = restoreDescription;
                    if (restoreDescription != null) {
                        try {
                            packageName = restoreDescription.getPackageName();
                        } catch (Throwable th2) {
                            th = th2;
                            unifiedRestoreState = unifiedRestoreState4;
                            executeNextState(unifiedRestoreState);
                            throw th;
                        }
                    } else {
                        packageName = null;
                    }
                    if (packageName == null) {
                        backupManagerMonitorEventSender3.monitorEvent(
                                56, null, 1, addRestoreOperationTypeToEvent(null));
                        Slog.e("BackupManagerService", "Failure getting next package name");
                        EventLog.writeEvent(2831, new Object[0]);
                    } else {
                        if (this.mRestoreDescription != RestoreDescription.NO_MORE_PACKAGES) {
                            Slog.i(
                                    "BackupManagerService",
                                    "Next restore package: " + this.mRestoreDescription);
                            int i3 = this.mRestoreAttemptedAppsCount + 1;
                            this.mRestoreAttemptedAppsCount = i3;
                            try {
                                IRestoreObserver iRestoreObserver2 = this.mObserver;
                                if (iRestoreObserver2 != null) {
                                    try {
                                        iRestoreObserver2.onUpdate(i3, packageName);
                                    } catch (RemoteException unused4) {
                                        Slog.d(
                                                "BackupManagerService",
                                                "Restore observer died in onUpdate");
                                        this.mObserver = null;
                                    }
                                }
                                HashMap hashMap3 = this.mPmAgent.mRestoredSignatures;
                                if (hashMap3 == null) {
                                    Slog.w("PMBA", "getRestoredMetadata() before metadata read!");
                                    metadata = null;
                                } else {
                                    metadata =
                                            (PackageManagerBackupAgent.Metadata)
                                                    hashMap3.get(packageName);
                                }
                                UnifiedRestoreState unifiedRestoreState5 =
                                        UnifiedRestoreState.RUNNING_QUEUE;
                                if (metadata == null) {
                                    PackageInfo packageInfo3 = new PackageInfo();
                                    packageInfo3.packageName = packageName;
                                    backupManagerMonitorEventSender3.monitorEvent(
                                            24,
                                            packageInfo3,
                                            3,
                                            addRestoreOperationTypeToEvent(null));
                                    Slog.e(
                                            "BackupManagerService",
                                            "No metadata for ".concat(packageName));
                                    EventLog.writeEvent(
                                            2832, packageName, "Package metadata missing");
                                } else {
                                    try {
                                        this.mCurrentPackage =
                                                userBackupManagerService3.mPackageManager
                                                        .getPackageInfoAsUser(
                                                                packageName, 134217728, i2);
                                        unifiedRestoreState2 = unifiedRestoreState4;
                                        str =
                                                "Can't get next restore target from transport;"
                                                    + " halting: ";
                                    } catch (PackageManager.NameNotFoundException unused5) {
                                    }
                                    try {
                                        try {
                                            backupManagerMonitorEventSender3.monitorEvent(
                                                    67,
                                                    this.mCurrentPackage,
                                                    3,
                                                    addRestoreOperationTypeToEvent(null));
                                            if (metadata.versionCode
                                                    > this.mCurrentPackage.getLongVersionCode()) {
                                                if (this.mIsSystemRestore
                                                        && isVToUDowngrade(
                                                                this.mPmAgent.mStoredSdkVersion,
                                                                Build.VERSION.SDK_INT)) {
                                                    if (!this.mAreVToUListsSet.booleanValue()) {
                                                        this.mVToUAllowlist =
                                                                createVToUList(
                                                                        Settings.Secure
                                                                                .getStringForUser(
                                                                                        userBackupManagerService3
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "v_to_u_restore_allowlist",
                                                                                        i2));
                                                        this.mVToUDenylist =
                                                                createVToUList(
                                                                        Settings.Secure
                                                                                .getStringForUser(
                                                                                        userBackupManagerService3
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "v_to_u_restore_denylist",
                                                                                        i2));
                                                        logVToUListsToBMM();
                                                        this.mAreVToUListsSet = Boolean.TRUE;
                                                    }
                                                    if (isPackageEligibleForVToURestore(
                                                            this.mCurrentPackage)) {
                                                        backupManagerMonitorEventSender3
                                                                .monitorEvent(
                                                                        70,
                                                                        this.mCurrentPackage,
                                                                        3,
                                                                        addRestoreOperationTypeToEvent(
                                                                                null));
                                                        Slog.i(
                                                                "BackupManagerService",
                                                                "Package "
                                                                        + packageName
                                                                        + " is eligible for V to U"
                                                                        + " downgrade scenario");
                                                    } else {
                                                        backupManagerMonitorEventSender3
                                                                .monitorEvent(
                                                                        71,
                                                                        this.mCurrentPackage,
                                                                        3,
                                                                        addRestoreOperationTypeToEvent(
                                                                                null));
                                                        Slog.i(
                                                                "BackupManagerService",
                                                                packageName.concat(
                                                                        " : Package not eligible"
                                                                            + " for V to U"
                                                                            + " downgrade"
                                                                            + " scenario"));
                                                        EventLog.writeEvent(
                                                                2832,
                                                                packageName,
                                                                "Package not eligible for V to U"
                                                                    + " downgrade scenario");
                                                    }
                                                } else if ((this.mCurrentPackage
                                                                        .applicationInfo
                                                                        .flags
                                                                & 131072)
                                                        == 0) {
                                                    logDowngradeScenario(false, metadata);
                                                } else {
                                                    logDowngradeScenario(true, metadata);
                                                }
                                            }
                                            this.mWidgetData = null;
                                            int dataType = this.mRestoreDescription.getDataType();
                                            if (dataType == 1) {
                                                unifiedRestoreState5 =
                                                        UnifiedRestoreState.RESTORE_KEYVALUE;
                                            } else if (dataType == 2) {
                                                unifiedRestoreState5 =
                                                        UnifiedRestoreState.RESTORE_FULL;
                                            } else {
                                                Slog.e(
                                                        "BackupManagerService",
                                                        "Unrecognized restore type " + dataType);
                                                backupManagerMonitorEventSender3.monitorEvent(
                                                        57,
                                                        this.mCurrentPackage,
                                                        3,
                                                        addRestoreOperationTypeToEvent(null));
                                            }
                                        } catch (Exception e4) {
                                            e = e4;
                                            Slog.e("BackupManagerService", str + e.getMessage());
                                            backupManagerMonitorEventSender3.monitorEvent(
                                                    60,
                                                    null,
                                                    3,
                                                    addRestoreOperationTypeToEvent(null));
                                            EventLog.writeEvent(2831, new Object[0]);
                                            this.mStatus = -1000;
                                            executeNextState(unifiedRestoreState2);
                                            return;
                                        }
                                    } catch (PackageManager.NameNotFoundException unused6) {
                                        Slog.e(
                                                "BackupManagerService",
                                                "Package not present: ".concat(packageName));
                                        Bundle addRestoreOperationTypeToEvent3 =
                                                addRestoreOperationTypeToEvent(null);
                                        PackageInfo packageInfo4 = new PackageInfo();
                                        packageInfo4.packageName = packageName;
                                        backupManagerMonitorEventSender3.monitorEvent(
                                                26,
                                                packageInfo4,
                                                3,
                                                addRestoreOperationTypeToEvent3);
                                        EventLog.writeEvent(
                                                2832, packageName, "Package missing on device");
                                        executeNextState(unifiedRestoreState5);
                                        return;
                                    }
                                }
                                executeNextState(unifiedRestoreState5);
                                return;
                            } catch (Throwable th3) {
                                th = th3;
                                unifiedRestoreState = unifiedRestoreState4;
                                executeNextState(unifiedRestoreState);
                                throw th;
                            }
                        }
                        Slog.v("BackupManagerService", "No more packages; finishing restore");
                        EventLog.writeEvent(
                                2834,
                                Integer.valueOf(this.mRestoreAttemptedAppsCount),
                                Integer.valueOf(
                                        (int)
                                                (SystemClock.elapsedRealtime()
                                                        - this.mStartRealtime)));
                    }
                    executeNextState(unifiedRestoreState4);
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (Exception e5) {
                e = e5;
                unifiedRestoreState2 = unifiedRestoreState4;
                str = "Can't get next restore target from transport; halting: ";
            }
        } catch (Throwable th5) {
            th = th5;
        }
    }

    public void executeNextState(UnifiedRestoreState unifiedRestoreState) {
        this.mState = unifiedRestoreState;
        UserBackupManagerService userBackupManagerService = this.backupManagerService;
        userBackupManagerService.mBackupHandler.sendMessage(
                userBackupManagerService.mBackupHandler.obtainMessage(20, this));
    }

    public void filterExcludedKeys(
            String str, BackupDataInput backupDataInput, BackupDataOutput backupDataOutput)
            throws Exception {
        Set excludedKeysForPackage = getExcludedKeysForPackage(str);
        byte[] bArr =
                new byte
                        [Flags.enableMaxSizeWritesToPipes()
                                ? EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT
                                : 8192];
        while (backupDataInput.readNextHeader()) {
            String key = backupDataInput.getKey();
            int dataSize = backupDataInput.getDataSize();
            if (excludedKeysForPackage != null && excludedKeysForPackage.contains(key)) {
                Slog.i("BackupManagerService", "Skipping blocked key " + key);
                backupDataInput.skipEntityData();
            } else if (key.equals("￭￭widget")) {
                Slog.i("BackupManagerService", "Restoring widget state for " + str);
                byte[] bArr2 = new byte[dataSize];
                this.mWidgetData = bArr2;
                backupDataInput.readEntityData(bArr2, 0, dataSize);
            } else {
                if (dataSize > bArr.length) {
                    bArr = new byte[dataSize];
                }
                backupDataInput.readEntityData(bArr, 0, dataSize);
                backupDataOutput.writeEntityHeader(key, dataSize);
                backupDataOutput.writeEntityData(bArr, dataSize);
            }
        }
    }

    public UnifiedRestoreState getCurrentUnifiedRestoreStateForTesting() {
        return this.mState;
    }

    public Set getExcludedKeysForPackage(String str) {
        return this.backupManagerService.mBackupPreferences.mPreferences.getStringSet(
                str, Collections.emptySet());
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public final void handleCancel(boolean z) {
        ((LifecycleOperationStorage) this.mOperationStorage)
                .removeOperation(this.mEphemeralOpToken);
        Slog.e(
                "BackupManagerService",
                "Timeout restoring application " + this.mCurrentPackage.packageName);
        this.mBackupManagerMonitorEventSender.monitorEvent(
                31, this.mCurrentPackage, 2, addRestoreOperationTypeToEvent(null));
        EventLog.writeEvent(2832, this.mCurrentPackage.packageName, "restore timeout");
        keyValueAgentErrorCleanup(true);
        executeNextState(UnifiedRestoreState.RUNNING_QUEUE);
    }

    public final void initiateOneRestore(PackageInfo packageInfo, long j) {
        UnifiedRestoreState unifiedRestoreState = UnifiedRestoreState.RUNNING_QUEUE;
        BackupManagerMonitorEventSender backupManagerMonitorEventSender =
                this.mBackupManagerMonitorEventSender;
        String str = packageInfo.packageName;
        Slog.d("BackupManagerService", "initiateOneRestore packageName=" + str);
        UserBackupManagerService userBackupManagerService = this.backupManagerService;
        this.mBackupDataName =
                new File(
                        userBackupManagerService.mDataDir,
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                str, ".restore"));
        this.mStageName =
                new File(
                        userBackupManagerService.mDataDir,
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, ".stage"));
        this.mNewStateName =
                new File(
                        this.mStateDir,
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                str, KeyValueBackupTask.NEW_STATE_FILE_SUFFIX));
        boolean shouldStageBackupData = shouldStageBackupData(str);
        File file = shouldStageBackupData ? this.mStageName : this.mBackupDataName;
        boolean z = false;
        try {
            BackupTransportClient connectOrThrow =
                    this.mTransportConnection.connectOrThrow(
                            "PerformUnifiedRestoreTask.initiateOneRestore()");
            ParcelFileDescriptor open = ParcelFileDescriptor.open(file, 1006632960);
            BackupTransportClient.TransportStatusCallbackPool transportStatusCallbackPool =
                    connectOrThrow.mCallbackPool;
            TransportStatusCallback acquire = transportStatusCallbackPool.acquire();
            try {
                connectOrThrow.mTransportBinder.getRestoreData(open, acquire);
                int operationStatus = acquire.getOperationStatus();
                transportStatusCallbackPool.recycle(acquire);
                if (operationStatus != 0) {
                    Slog.e("BackupManagerService", "Error getting restore data for " + str);
                    backupManagerMonitorEventSender.monitorEvent(
                            63, this.mCurrentPackage, 1, addRestoreOperationTypeToEvent(null));
                    EventLog.writeEvent(2831, new Object[0]);
                    open.close();
                    file.delete();
                    executeNextState(
                            DeviceConfig.getBoolean(
                                            "backup_and_restore",
                                            "unified_restore_continue_after_transport_failure_in_kv_restore",
                                            true)
                                    ? unifiedRestoreState
                                    : UnifiedRestoreState.FINAL);
                    return;
                }
                if (shouldStageBackupData) {
                    open.close();
                    open = ParcelFileDescriptor.open(file, 268435456);
                    this.mBackupData = ParcelFileDescriptor.open(this.mBackupDataName, 1006632960);
                    filterExcludedKeys(
                            str,
                            new BackupDataInput(open.getFileDescriptor()),
                            new BackupDataOutput(this.mBackupData.getFileDescriptor()));
                    this.mBackupData.close();
                }
                open.close();
                this.mBackupData = ParcelFileDescriptor.open(this.mBackupDataName, 268435456);
                this.mNewState = ParcelFileDescriptor.open(this.mNewStateName, 1006632960);
                this.backupManagerService.prepareOperationTimeout(
                        this.mEphemeralOpToken,
                        this.mAgentTimeoutParameters.getRestoreAgentTimeoutMillis(
                                packageInfo.applicationInfo.uid),
                        this,
                        1);
                try {
                    this.mAgent.doRestoreWithExcludedKeys(
                            this.mBackupData,
                            j,
                            this.mNewState,
                            this.mEphemeralOpToken,
                            userBackupManagerService.mBackupManagerBinder,
                            new ArrayList(getExcludedKeysForPackage(str)));
                } catch (Exception e) {
                    e = e;
                    z = true;
                    Slog.e("BackupManagerService", "Unable to call app for restore: " + str, e);
                    backupManagerMonitorEventSender.monitorEvent(
                            61, this.mCurrentPackage, 2, addRestoreOperationTypeToEvent(null));
                    EventLog.writeEvent(2832, str, e.toString());
                    keyValueAgentErrorCleanup(z);
                    executeNextState(unifiedRestoreState);
                }
            } catch (Throwable th) {
                transportStatusCallbackPool.recycle(acquire);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public void initiateOneRestoreForTesting(PackageInfo packageInfo, long j) {
        initiateOneRestore(packageInfo, j);
    }

    public boolean isPackageEligibleForVToURestore(PackageInfo packageInfo) {
        if (this.mVToUDenylist.contains(packageInfo.packageName)) {
            BootReceiver$$ExternalSyntheticOutline0.m59m(
                    new StringBuilder(),
                    packageInfo.packageName,
                    " : Package is in V to U denylist",
                    "BackupManagerService");
            return false;
        }
        if ((packageInfo.applicationInfo.flags & 131072) == 0) {
            BootReceiver$$ExternalSyntheticOutline0.m59m(
                    new StringBuilder(),
                    packageInfo.packageName,
                    " : Package has restoreAnyVersion=false and is in V to U allowlist",
                    "BackupManagerService");
            return this.mVToUAllowlist.contains(packageInfo.packageName);
        }
        BootReceiver$$ExternalSyntheticOutline0.m59m(
                new StringBuilder(),
                packageInfo.packageName,
                " : Package has restoreAnyVersion=true and is not in V to U denylist",
                "BackupManagerService");
        return true;
    }

    public boolean isVToUDowngrade(int i, int i2) {
        return Flags.enableVToURestoreForSystemComponentsInAllowlist() && i > 34 && i2 == 34;
    }

    public final void keyValueAgentCleanup() {
        this.mBackupDataName.delete();
        this.mStageName.delete();
        try {
            ParcelFileDescriptor parcelFileDescriptor = this.mBackupData;
            if (parcelFileDescriptor != null) {
                parcelFileDescriptor.close();
            }
        } catch (IOException unused) {
        }
        try {
            ParcelFileDescriptor parcelFileDescriptor2 = this.mNewState;
            if (parcelFileDescriptor2 != null) {
                parcelFileDescriptor2.close();
            }
        } catch (IOException unused2) {
        }
        this.mNewState = null;
        this.mBackupData = null;
        this.mNewStateName.delete();
        ApplicationInfo applicationInfo = this.mCurrentPackage.applicationInfo;
        UserBackupManagerService userBackupManagerService = this.backupManagerService;
        if (applicationInfo != null) {
            try {
                userBackupManagerService.mActivityManager.unbindBackupAgent(applicationInfo);
                ApplicationInfo applicationInfo2 = this.mCurrentPackage.applicationInfo;
                boolean z =
                        !UserHandle.isCore(applicationInfo2.uid)
                                && (this.mRestoreDescription.getDataType() == 2
                                        || (65536 & applicationInfo2.flags) != 0);
                if (this.mTargetPackage == null && z) {
                    Slog.d(
                            "BackupManagerService",
                            "Restore complete, killing host process of "
                                    + this.mCurrentPackage.applicationInfo.processName);
                    IActivityManager iActivityManager = userBackupManagerService.mActivityManager;
                    ApplicationInfo applicationInfo3 = this.mCurrentPackage.applicationInfo;
                    iActivityManager.killApplicationProcess(
                            applicationInfo3.processName, applicationInfo3.uid);
                }
            } catch (RemoteException unused3) {
            }
        }
        userBackupManagerService.mBackupHandler.removeMessages(18, this);
    }

    public final void keyValueAgentErrorCleanup(boolean z) {
        if (z) {
            this.backupManagerService.clearApplicationDataSynchronous(
                    this.mCurrentPackage.packageName, true, false);
        }
        keyValueAgentCleanup();
    }

    public final void logDowngradeScenario(boolean z, PackageManagerBackupAgent.Metadata metadata) {
        String str;
        long j = metadata.versionCode;
        Bundle bundle = new Bundle();
        bundle.putLong("android.app.backup.extra.LOG_RESTORE_VERSION", j);
        long j2 = metadata.versionCode;
        if (z) {
            bundle.putBoolean("android.app.backup.extra.LOG_RESTORE_ANYWAY", true);
            str =
                    "Source version "
                            + j2
                            + " > installed version "
                            + this.mCurrentPackage.getLongVersionCode()
                            + " but restoreAnyVersion";
        } else {
            bundle.putBoolean("android.app.backup.extra.LOG_RESTORE_ANYWAY", false);
            str =
                    "Source version "
                            + j2
                            + " > installed version "
                            + this.mCurrentPackage.getLongVersionCode();
            EventLog.writeEvent(2832, this.mCurrentPackage.packageName, str);
        }
        Slog.i("BackupManagerService", "Package " + this.mCurrentPackage.packageName + ": " + str);
        bundle.putInt("android.app.backup.extra.OPERATION_TYPE", 1);
        this.mBackupManagerMonitorEventSender.monitorEvent(27, this.mCurrentPackage, 3, bundle);
    }

    public final void logVToUListsToBMM() {
        Bundle putMonitoringExtra =
                BackupManagerMonitorEventSender.putMonitoringExtra(
                        "android.app.backup.extra.V_TO_U_ALLOWLIST",
                        this.mVToUAllowlist.toString());
        putMonitoringExtra.putInt("android.app.backup.extra.OPERATION_TYPE", 1);
        BackupManagerMonitorEventSender backupManagerMonitorEventSender =
                this.mBackupManagerMonitorEventSender;
        backupManagerMonitorEventSender.monitorEvent(72, null, 3, putMonitoringExtra);
        Bundle putMonitoringExtra2 =
                BackupManagerMonitorEventSender.putMonitoringExtra(
                        "android.app.backup.extra.V_TO_U_DENYLIST", this.mVToUDenylist.toString());
        putMonitoringExtra2.putInt("android.app.backup.extra.OPERATION_TYPE", 1);
        backupManagerMonitorEventSender.monitorEvent(72, null, 3, putMonitoringExtra2);
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public final void operationComplete(long j) {
        ((LifecycleOperationStorage) this.mOperationStorage)
                .removeOperation(this.mEphemeralOpToken);
        int ordinal = this.mState.ordinal();
        UnifiedRestoreState unifiedRestoreState = UnifiedRestoreState.RUNNING_QUEUE;
        if (ordinal != 0) {
            if (ordinal == 2 || ordinal == 3) {
                unifiedRestoreState = UnifiedRestoreState.RESTORE_FINISHED;
            } else if (ordinal != 4) {
                Slog.e(
                        "BackupManagerService",
                        "Unexpected restore callback into state " + this.mState);
                keyValueAgentErrorCleanup(true);
                unifiedRestoreState = UnifiedRestoreState.FINAL;
            } else {
                int length = (int) this.mBackupDataName.length();
                Bundle addRestoreOperationTypeToEvent = addRestoreOperationTypeToEvent(null);
                PackageInfo packageInfo = this.mCurrentPackage;
                BackupManagerMonitorEventSender backupManagerMonitorEventSender =
                        this.mBackupManagerMonitorEventSender;
                backupManagerMonitorEventSender.monitorEvent(
                        62, packageInfo, 3, addRestoreOperationTypeToEvent);
                EventLog.writeEvent(
                        2833, this.mCurrentPackage.packageName, Integer.valueOf(length));
                backupManagerMonitorEventSender.monitorAgentLoggingResults(
                        this.mCurrentPackage, this.mAgent);
                keyValueAgentCleanup();
                byte[] bArr = this.mWidgetData;
                if (bArr != null) {
                    AppWidgetBackupBridge.restoreWidgetState(
                            this.mCurrentPackage.packageName,
                            bArr,
                            this.backupManagerService.mUserId);
                }
            }
        }
        executeNextState(unifiedRestoreState);
    }

    public void setCurrentUnifiedRestoreStateForTesting(UnifiedRestoreState unifiedRestoreState) {
        this.mState = unifiedRestoreState;
    }

    public void setStateDirForTesting(File file) {
        this.mStateDir = file;
    }

    public boolean shouldStageBackupData(String str) {
        return (str.equals("android") && getExcludedKeysForPackage("android").isEmpty())
                ? false
                : true;
    }

    public final void startRestore() {
        TransportConnection transportConnection = this.mTransportConnection;
        UnifiedRestoreState unifiedRestoreState = UnifiedRestoreState.FINAL;
        UserBackupManagerService userBackupManagerService = this.backupManagerService;
        BackupManagerMonitorEventSender backupManagerMonitorEventSender =
                this.mBackupManagerMonitorEventSender;
        int size = this.mAcceptSet.size();
        IRestoreObserver iRestoreObserver = this.mObserver;
        if (iRestoreObserver != null) {
            try {
                iRestoreObserver.restoreStarting(size);
            } catch (RemoteException unused) {
                Slog.w("BackupManagerService", "Restore observer went away: startRestore");
                this.mObserver = null;
            }
        }
        try {
            String transportDirName =
                    this.mTransportManager.getTransportDirName(
                            transportConnection.mTransportComponent);
            File file = userBackupManagerService.mBaseStateDir;
            BackupHandler backupHandler = userBackupManagerService.mBackupHandler;
            this.mStateDir = new File(file, transportDirName);
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = "@pm@";
            this.mAcceptSet.add(0, packageInfo);
            PackageInfo[] packageInfoArr =
                    (PackageInfo[]) this.mAcceptSet.toArray(new PackageInfo[0]);
            BackupTransportClient connectOrThrow =
                    transportConnection.connectOrThrow("PerformUnifiedRestoreTask.startRestore()");
            if (backupManagerMonitorEventSender.mMonitor == null) {
                backupManagerMonitorEventSender.mMonitor = connectOrThrow.getBackupManagerMonitor();
            }
            if (Flags.enableIncreasedBmmLoggingForRestoreAtInstall()) {
                Iterator it = this.mAcceptSet.iterator();
                while (it.hasNext()) {
                    backupManagerMonitorEventSender.monitorEvent(
                            75, (PackageInfo) it.next(), 3, addRestoreOperationTypeToEvent(null));
                }
            }
            if (this.mIsSystemRestore) {
                AppWidgetBackupBridge.systemRestoreStarting(this.mUserId);
                backupManagerMonitorEventSender.monitorEvent(
                        53, null, 3, addRestoreOperationTypeToEvent(null));
            } else {
                backupManagerMonitorEventSender.monitorEvent(
                        54, null, 3, addRestoreOperationTypeToEvent(null));
            }
            long j = this.mToken;
            BackupTransportClient.TransportStatusCallbackPool transportStatusCallbackPool =
                    connectOrThrow.mCallbackPool;
            TransportStatusCallback acquire = transportStatusCallbackPool.acquire();
            try {
                connectOrThrow.mTransportBinder.startRestore(j, packageInfoArr, acquire);
                int operationStatus = acquire.getOperationStatus();
                transportStatusCallbackPool.recycle(acquire);
                this.mStatus = operationStatus;
                if (operationStatus != 0) {
                    Slog.e(
                            "BackupManagerService",
                            "Transport error " + this.mStatus + "; no restore possible");
                    backupManagerMonitorEventSender.monitorEvent(
                            55, this.mCurrentPackage, 1, addRestoreOperationTypeToEvent(null));
                    this.mStatus = -1000;
                    executeNextState(unifiedRestoreState);
                    return;
                }
                AndroidFuture newFuture = connectOrThrow.mTransportFutures.newFuture();
                connectOrThrow.mTransportBinder.nextRestorePackage(newFuture);
                RestoreDescription restoreDescription =
                        (RestoreDescription) connectOrThrow.getFutureResult(newFuture);
                if (restoreDescription == null) {
                    Slog.e("BackupManagerService", "No restore metadata available; halting");
                    backupManagerMonitorEventSender.monitorEvent(
                            22, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
                    this.mStatus = -1000;
                    executeNextState(unifiedRestoreState);
                    return;
                }
                if (!"@pm@".equals(restoreDescription.getPackageName())) {
                    Slog.e(
                            "BackupManagerService",
                            "Required package metadata but got "
                                    + restoreDescription.getPackageName());
                    backupManagerMonitorEventSender.monitorEvent(
                            23, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
                    this.mStatus = -1000;
                    executeNextState(unifiedRestoreState);
                    return;
                }
                PackageInfo packageInfo2 = new PackageInfo();
                this.mCurrentPackage = packageInfo2;
                packageInfo2.packageName = "@pm@";
                packageInfo2.applicationInfo = new ApplicationInfo();
                this.mCurrentPackage.applicationInfo.uid = 1000;
                PackageManager packageManager = userBackupManagerService.mPackageManager;
                int i = userBackupManagerService.mUserId;
                PackageManagerBackupAgent packageManagerBackupAgent =
                        new PackageManagerBackupAgent(packageManager, i);
                packageManagerBackupAgent.attach(userBackupManagerService.mContext);
                packageManagerBackupAgent.onCreate(UserHandle.of(i));
                this.mPmAgent = packageManagerBackupAgent;
                this.mAgent = IBackupAgent.Stub.asInterface(packageManagerBackupAgent.onBind());
                initiateOneRestore(this.mCurrentPackage, 0L);
                backupHandler.removeMessages(18);
                if (this.mPmAgent.mHasMetadata) {
                    return;
                }
                Slog.e("BackupManagerService", "PM agent has no metadata, so not restoring");
                backupManagerMonitorEventSender.monitorEvent(
                        24, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
                EventLog.writeEvent(2832, "@pm@", "Package manager restore metadata missing");
                this.mStatus = -1000;
                backupHandler.removeMessages(20, this);
                executeNextState(unifiedRestoreState);
            } catch (Throwable th) {
                transportStatusCallbackPool.recycle(acquire);
                throw th;
            }
        } catch (Exception e) {
            Slog.e(
                    "BackupManagerService",
                    "Unable to contact transport for restore: " + e.getMessage());
            backupManagerMonitorEventSender.monitorEvent(
                    25, null, 1, addRestoreOperationTypeToEvent(null));
            this.mStatus = -1000;
            userBackupManagerService.mBackupHandler.removeMessages(20, this);
            executeNextState(unifiedRestoreState);
        }
    }
}

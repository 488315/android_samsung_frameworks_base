package com.android.server.backup.fullbackup;

import android.app.ActivityManagerInternal;
import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IBackupObserver;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.backup.BackupRestoreTask;
import com.android.server.backup.FullBackupJob;
import com.android.server.backup.OperationStorage;
import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda14;
import com.android.server.backup.internal.LifecycleOperationStorage;
import com.android.server.backup.internal.OnTaskFinishedListener;
import com.android.server.backup.remote.FutureBackupCallback;
import com.android.server.backup.remote.RemoteCall;
import com.android.server.backup.remote.RemoteCallable;
import com.android.server.backup.transport.BackupTransportClient;
import com.android.server.backup.transport.TransportConnection;
import com.android.server.backup.transport.TransportNotAvailableException;
import com.android.server.backup.transport.TransportStatusCallback;
import com.android.server.backup.utils.BackupEligibilityRules;
import com.android.server.backup.utils.BackupManagerMonitorEventSender;
import com.android.server.backup.utils.BackupObserverUtils;

import com.google.android.collect.Sets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PerformFullTransportBackupTask extends FullBackupTask
        implements BackupRestoreTask {
    public final BackupAgentTimeoutParameters mAgentTimeoutParameters;
    public ActivityManagerInternal mAmInternal;
    public final BackupEligibilityRules mBackupEligibilityRules;
    public final BackupManagerMonitorEventSender mBackupManagerMonitorEventSender;
    public final IBackupObserver mBackupObserver;
    public SinglePackageBackupRunner mBackupRunner;
    public final int mBackupRunnerOpToken;
    public volatile boolean mCancelAll;
    public final Object mCancelLock;
    public final int mCurrentOpToken;
    public final PackageInfo mCurrentPackage;
    public volatile boolean mIsDoingBackup;
    public final FullBackupJob mJob;
    public final CountDownLatch mLatch;
    public final OnTaskFinishedListener mListener;
    public final OperationStorage mOperationStorage;
    public final List mPackages;
    public final TransportConnection mTransportConnection;
    public boolean mUpdateSchedule;
    public final UserBackupManagerService mUserBackupManagerService;
    public final int mUserId;
    public final boolean mUserInitiated;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SinglePackageBackupPreflight implements BackupRestoreTask {
        public final int mCurrentOpToken;
        public final long mQuota;
        public final TransportConnection mTransportConnection;
        public final int mTransportFlags;
        public final AtomicLong mResult = new AtomicLong(-1003);
        public final CountDownLatch mLatch = new CountDownLatch(1);

        public SinglePackageBackupPreflight(
                TransportConnection transportConnection, long j, int i, int i2) {
            this.mTransportConnection = transportConnection;
            this.mQuota = j;
            this.mCurrentOpToken = i;
            this.mTransportFlags = i2;
        }

        @Override // com.android.server.backup.BackupRestoreTask
        public final void execute() {}

        @Override // com.android.server.backup.BackupRestoreTask
        public final void handleCancel(boolean z) {
            this.mResult.set(-1003L);
            this.mLatch.countDown();
            ((LifecycleOperationStorage) PerformFullTransportBackupTask.this.mOperationStorage)
                    .removeOperation(this.mCurrentOpToken);
        }

        @Override // com.android.server.backup.BackupRestoreTask
        public final void operationComplete(long j) {
            this.mResult.set(j);
            this.mLatch.countDown();
            ((LifecycleOperationStorage) PerformFullTransportBackupTask.this.mOperationStorage)
                    .removeOperation(this.mCurrentOpToken);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SinglePackageBackupRunner implements Runnable, BackupRestoreTask {
        public final CountDownLatch mBackupLatch;
        public volatile int mBackupResult;
        public final int mCurrentOpToken;
        public FullBackupEngine mEngine;
        public final int mEphemeralToken;
        public volatile boolean mIsCancelled;
        public final ParcelFileDescriptor mOutput;
        public final SinglePackageBackupPreflight mPreflight;
        public final CountDownLatch mPreflightLatch;
        public volatile int mPreflightResult;
        public final long mQuota;
        public final PackageInfo mTarget;
        public final int mTransportFlags;

        public SinglePackageBackupRunner(
                ParcelFileDescriptor parcelFileDescriptor,
                PackageInfo packageInfo,
                TransportConnection transportConnection,
                long j,
                int i,
                int i2) {
            this.mOutput = ParcelFileDescriptor.dup(parcelFileDescriptor.getFileDescriptor());
            this.mTarget = packageInfo;
            this.mCurrentOpToken = i;
            int generateRandomIntegerToken =
                    PerformFullTransportBackupTask.this.mUserBackupManagerService
                            .generateRandomIntegerToken();
            this.mEphemeralToken = generateRandomIntegerToken;
            this.mPreflight =
                    PerformFullTransportBackupTask.this
                    .new SinglePackageBackupPreflight(
                            transportConnection, j, generateRandomIntegerToken, i2);
            this.mPreflightLatch = new CountDownLatch(1);
            this.mBackupLatch = new CountDownLatch(1);
            this.mPreflightResult = -1003;
            this.mBackupResult = -1003;
            this.mQuota = j;
            this.mTransportFlags = i2;
            ((LifecycleOperationStorage) PerformFullTransportBackupTask.this.mOperationStorage)
                    .registerOperationForPackages(
                            i, Sets.newHashSet(new String[] {packageInfo.packageName}), this, 0);
        }

        @Override // com.android.server.backup.BackupRestoreTask
        public final void execute() {}

        public final int getBackupResultBlocking() {
            try {
                this.mBackupLatch.await(
                        PerformFullTransportBackupTask.this.mAgentTimeoutParameters
                                .getFullBackupAgentTimeoutMillis(),
                        TimeUnit.MILLISECONDS);
                if (this.mIsCancelled) {
                    return -2003;
                }
                return this.mBackupResult;
            } catch (InterruptedException unused) {
                return -1003;
            }
        }

        public final long getPreflightResultBlocking() {
            long fullBackupAgentTimeoutMillis =
                    PerformFullTransportBackupTask.this.mAgentTimeoutParameters
                            .getFullBackupAgentTimeoutMillis();
            try {
                CountDownLatch countDownLatch = this.mPreflightLatch;
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                countDownLatch.await(fullBackupAgentTimeoutMillis, timeUnit);
                if (this.mIsCancelled) {
                    return -2003L;
                }
                if (this.mPreflightResult != 0) {
                    return this.mPreflightResult;
                }
                SinglePackageBackupPreflight singlePackageBackupPreflight = this.mPreflight;
                try {
                    singlePackageBackupPreflight.mLatch.await(
                            PerformFullTransportBackupTask.this.mAgentTimeoutParameters
                                    .getFullBackupAgentTimeoutMillis(),
                            timeUnit);
                    return singlePackageBackupPreflight.mResult.get();
                } catch (InterruptedException unused) {
                    return -1L;
                }
            } catch (InterruptedException unused2) {
                return -1003L;
            }
        }

        @Override // com.android.server.backup.BackupRestoreTask
        public final void handleCancel(boolean z) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Full backup cancel of "), this.mTarget.packageName, "PFTBT");
            PerformFullTransportBackupTask performFullTransportBackupTask =
                    PerformFullTransportBackupTask.this;
            performFullTransportBackupTask.mBackupManagerMonitorEventSender.monitorEvent(
                    4, performFullTransportBackupTask.mCurrentPackage, 2, null);
            this.mIsCancelled = true;
            UserBackupManagerService userBackupManagerService =
                    PerformFullTransportBackupTask.this.mUserBackupManagerService;
            int i = this.mEphemeralToken;
            userBackupManagerService.getClass();
            userBackupManagerService.mOperationStorage.cancelOperation(
                    i,
                    z,
                    new UserBackupManagerService$$ExternalSyntheticLambda14(
                            0, userBackupManagerService));
            PerformFullTransportBackupTask.this.mUserBackupManagerService.tearDownAgentAndKill(
                    this.mTarget.applicationInfo);
            this.mPreflightLatch.countDown();
            this.mBackupLatch.countDown();
            ((LifecycleOperationStorage) PerformFullTransportBackupTask.this.mOperationStorage)
                    .removeOperation(this.mCurrentOpToken);
        }

        @Override // com.android.server.backup.BackupRestoreTask
        public final void operationComplete(long j) {}

        /* JADX WARN: Removed duplicated region for block: B:29:0x00fb  */
        /* JADX WARN: Removed duplicated region for block: B:38:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 292
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.backup.fullbackup.PerformFullTransportBackupTask.SinglePackageBackupRunner.run():void");
        }

        public final void sendQuotaExceeded(final long j, final long j2) {
            final FullBackupEngine fullBackupEngine = this.mEngine;
            if (fullBackupEngine.initializeAgent()) {
                try {
                    new RemoteCall(
                                    false,
                                    new RemoteCallable() { // from class:
                                                           // com.android.server.backup.fullbackup.FullBackupEngine$$ExternalSyntheticLambda0
                                        @Override // com.android.server.backup.remote.RemoteCallable
                                        public final void call(
                                                FutureBackupCallback futureBackupCallback) {
                                            FullBackupEngine.this.mAgent.doQuotaExceeded(
                                                    j, j2, futureBackupCallback);
                                        }
                                    },
                                    fullBackupEngine.mAgentTimeoutParameters
                                            .getQuotaExceededTimeoutMillis())
                            .call();
                } catch (RemoteException unused) {
                    Slog.e(
                            "BackupManagerService",
                            "Remote exception while telling agent about quota exceeded");
                }
            }
        }
    }

    public PerformFullTransportBackupTask(
            UserBackupManagerService userBackupManagerService,
            OperationStorage operationStorage,
            TransportConnection transportConnection,
            String[] strArr,
            boolean z,
            FullBackupJob fullBackupJob,
            CountDownLatch countDownLatch,
            IBackupObserver iBackupObserver,
            IBackupManagerMonitor iBackupManagerMonitor,
            OnTaskFinishedListener onTaskFinishedListener,
            boolean z2,
            BackupEligibilityRules backupEligibilityRules) {
        super(null);
        this.mCancelLock = new Object();
        this.mUserBackupManagerService = userBackupManagerService;
        this.mOperationStorage = operationStorage;
        this.mTransportConnection = transportConnection;
        this.mUpdateSchedule = z;
        this.mLatch = countDownLatch;
        this.mJob = fullBackupJob;
        this.mPackages = new ArrayList(strArr.length);
        this.mBackupObserver = iBackupObserver;
        this.mListener =
                onTaskFinishedListener == null
                        ? OnTaskFinishedListener.NOP
                        : onTaskFinishedListener;
        this.mUserInitiated = z2;
        this.mCurrentOpToken = userBackupManagerService.generateRandomIntegerToken();
        this.mBackupRunnerOpToken = userBackupManagerService.generateRandomIntegerToken();
        this.mBackupManagerMonitorEventSender =
                new BackupManagerMonitorEventSender(iBackupManagerMonitor);
        BackupAgentTimeoutParameters backupAgentTimeoutParameters =
                userBackupManagerService.mAgentTimeoutParameters;
        Objects.requireNonNull(backupAgentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = backupAgentTimeoutParameters;
        this.mUserId = userBackupManagerService.mUserId;
        this.mBackupEligibilityRules = backupEligibilityRules;
        if (userBackupManagerService.isBackupOperationInProgress()) {
            Slog.d("PFTBT", "Skipping full backup. A backup is already in progress.");
            this.mCancelAll = true;
            return;
        }
        for (String str : strArr) {
            try {
                PackageInfo packageInfoAsUser =
                        userBackupManagerService.mPackageManager.getPackageInfoAsUser(
                                str, 134217728, this.mUserId);
                this.mCurrentPackage = packageInfoAsUser;
                if (!this.mBackupEligibilityRules.appIsEligibleForBackup(
                        packageInfoAsUser.applicationInfo)) {
                    this.mBackupManagerMonitorEventSender.monitorEvent(
                            9, packageInfoAsUser, 3, null);
                    BackupObserverUtils.sendBackupOnPackageResult(this.mBackupObserver, str, -2001);
                } else if (this.mBackupEligibilityRules.appGetsFullBackup(packageInfoAsUser)) {
                    BackupEligibilityRules backupEligibilityRules2 = this.mBackupEligibilityRules;
                    ApplicationInfo applicationInfo = packageInfoAsUser.applicationInfo;
                    backupEligibilityRules2.getClass();
                    if (BackupEligibilityRules.appIsStopped(applicationInfo)) {
                        this.mBackupManagerMonitorEventSender.monitorEvent(
                                11, packageInfoAsUser, 3, null);
                        BackupObserverUtils.sendBackupOnPackageResult(
                                this.mBackupObserver, str, -2001);
                    } else {
                        this.mPackages.add(packageInfoAsUser);
                    }
                } else {
                    this.mBackupManagerMonitorEventSender.monitorEvent(
                            10, packageInfoAsUser, 3, null);
                    BackupObserverUtils.sendBackupOnPackageResult(this.mBackupObserver, str, -2001);
                }
            } catch (PackageManager.NameNotFoundException unused) {
                BootReceiver$$ExternalSyntheticOutline0.m58m(
                        "Requested package ", str, " not found; ignoring", "PFTBT");
                this.mBackupManagerMonitorEventSender.monitorEvent(
                        12, this.mCurrentPackage, 3, null);
            }
        }
        List filterUserFacingPackages =
                userBackupManagerService.filterUserFacingPackages(this.mPackages);
        this.mPackages = filterUserFacingPackages;
        HashSet newHashSet = Sets.newHashSet();
        Iterator it = filterUserFacingPackages.iterator();
        while (it.hasNext()) {
            newHashSet.add(((PackageInfo) it.next()).packageName);
        }
        Slog.d("PFTBT", "backupmanager pftbt token=" + Integer.toHexString(this.mCurrentOpToken));
        ((LifecycleOperationStorage) this.mOperationStorage)
                .registerOperationForPackages(this.mCurrentOpToken, newHashSet, this, 2);
    }

    public static void cleanUpPipes(ParcelFileDescriptor[] parcelFileDescriptorArr) {
        if (parcelFileDescriptorArr != null) {
            ParcelFileDescriptor parcelFileDescriptor = parcelFileDescriptorArr[0];
            if (parcelFileDescriptor != null) {
                parcelFileDescriptorArr[0] = null;
                try {
                    parcelFileDescriptor.close();
                } catch (IOException unused) {
                    Slog.w("PFTBT", "Unable to close pipe!");
                }
            }
            ParcelFileDescriptor parcelFileDescriptor2 = parcelFileDescriptorArr[1];
            if (parcelFileDescriptor2 != null) {
                parcelFileDescriptorArr[1] = null;
                try {
                    parcelFileDescriptor2.close();
                } catch (IOException unused2) {
                    Slog.w("PFTBT", "Unable to close pipe!");
                }
            }
        }
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public final void execute() {}

    @Override // com.android.server.backup.BackupRestoreTask
    public final void handleCancel(boolean z) {
        synchronized (this.mCancelLock) {
            if (!z) {
                try {
                    Slog.wtf("PFTBT", "Expected cancelAll to be true.");
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (this.mCancelAll) {
                Slog.d("PFTBT", "Ignoring duplicate cancel call.");
                return;
            }
            this.mCancelAll = true;
            if (this.mIsDoingBackup) {
                UserBackupManagerService userBackupManagerService = this.mUserBackupManagerService;
                int i = this.mBackupRunnerOpToken;
                userBackupManagerService.getClass();
                userBackupManagerService.mOperationStorage.cancelOperation(
                        i,
                        z,
                        new UserBackupManagerService$$ExternalSyntheticLambda14(
                                0, userBackupManagerService));
                try {
                    BackupTransportClient connectedTransport =
                            this.mTransportConnection.getConnectedTransport();
                    BackupTransportClient.TransportStatusCallbackPool transportStatusCallbackPool =
                            connectedTransport.mCallbackPool;
                    TransportStatusCallback acquire = transportStatusCallbackPool.acquire();
                    try {
                        connectedTransport.mTransportBinder.cancelFullBackup(acquire);
                        acquire.getOperationStatus();
                        transportStatusCallbackPool.recycle(acquire);
                    } catch (Throwable th2) {
                        transportStatusCallbackPool.recycle(acquire);
                        throw th2;
                    }
                } catch (RemoteException | TransportNotAvailableException e) {
                    Slog.w("PFTBT", "Error calling cancelFullBackup() on transport: " + e);
                }
            }
        }
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public final void operationComplete(long j) {}

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
    jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:359:0x051f
    	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
    	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x06b1  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x04ca A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:241:0x04e9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x06fe  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x071a  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0726 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0700  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x06af  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x06cb  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x06d7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r11v15 */
    /* JADX WARN: Type inference failed for: r11v18 */
    /* JADX WARN: Type inference failed for: r11v7 */
    /* JADX WARN: Type inference failed for: r5v31 */
    /* JADX WARN: Type inference failed for: r5v32 */
    /* JADX WARN: Type inference failed for: r5v33 */
    /* JADX WARN: Type inference failed for: r5v34 */
    /* JADX WARN: Type inference failed for: r5v35 */
    /* JADX WARN: Type inference failed for: r5v36 */
    /* JADX WARN: Type inference failed for: r5v37 */
    /* JADX WARN: Type inference failed for: r6v30 */
    /* JADX WARN: Type inference failed for: r6v50 */
    /* JADX WARN: Type inference failed for: r6v9, types: [com.android.server.backup.transport.BackupTransportClient] */
    /* JADX WARN: Type inference failed for: r7v10, types: [boolean] */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v9 */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            Method dump skipped, instructions count: 1878
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.fullbackup.PerformFullTransportBackupTask.run():void");
    }

    public final void unregisterTask() {
        ((LifecycleOperationStorage) this.mOperationStorage).removeOperation(this.mCurrentOpToken);
    }
}

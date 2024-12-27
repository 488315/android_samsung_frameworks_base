package com.android.server.backup.fullbackup;

import android.app.IBackupAgent;
import android.app.backup.FullBackupDataOutput;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.AppWidgetBackupBridge;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.backup.BackupManagerYuva;
import com.android.server.backup.BackupRestoreTask;
import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.remote.FutureBackupCallback;
import com.android.server.backup.remote.RemoteCall;
import com.android.server.backup.remote.RemoteCallable;
import com.android.server.backup.transport.BackupTransportClient;
import com.android.server.backup.transport.TransportStatusCallback;
import com.android.server.backup.utils.BackupEligibilityRules;
import com.android.server.backup.utils.BackupManagerMonitorEventSender;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FullBackupEngine {
    public static BackupManagerYuva mBackupManagerYuva;
    public final UserBackupManagerService backupManagerService;
    public IBackupAgent mAgent;
    public final BackupAgentTimeoutParameters mAgentTimeoutParameters;
    public final BackupEligibilityRules mBackupEligibilityRules;
    public final BackupManagerMonitorEventSender mBackupManagerMonitorEventSender;
    public final int mExtraFlag;
    public final boolean mIncludeApks;
    public final int mOpToken;
    public final OutputStream mOutput;
    public final PackageInfo mPkg;
    public final PerformFullTransportBackupTask.SinglePackageBackupPreflight mPreflightHook;
    public final long mQuota;
    public final String[] mSmartSwitchBackupPath;
    public final BackupRestoreTask mTimeoutMonitor;
    public final int mTransportFlags;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FullBackupRunner implements Runnable {
        public final boolean isDisableDataExtractionRules;
        public final IBackupAgent mAgent;
        public final File mFilesDir;
        public final boolean mIncludeApks;
        public final PackageInfo mPackage;
        public final PackageManager mPackageManager;
        public final ParcelFileDescriptor mPipe;
        public final String[] mSmartSwitchBackupPath;
        public final int mToken;
        public final int mUserId;

        public FullBackupRunner(
                UserBackupManagerService userBackupManagerService,
                PackageInfo packageInfo,
                IBackupAgent iBackupAgent,
                ParcelFileDescriptor parcelFileDescriptor,
                int i,
                boolean z,
                String[] strArr) {
            this.isDisableDataExtractionRules = false;
            this.mUserId = userBackupManagerService.mUserId;
            this.mPackageManager = FullBackupEngine.this.backupManagerService.mPackageManager;
            this.mPackage = packageInfo;
            this.mAgent = iBackupAgent;
            this.mPipe = ParcelFileDescriptor.dup(parcelFileDescriptor.getFileDescriptor());
            this.mToken = i;
            this.mIncludeApks = z;
            this.mFilesDir = userBackupManagerService.mDataDir;
            this.mSmartSwitchBackupPath = strArr;
            this.isDisableDataExtractionRules = userBackupManagerService.mDisableDataExtractionRule;
        }

        @Override // java.lang.Runnable
        public final void run() {
            long fullBackupAgentTimeoutMillis;
            try {
                try {
                    try {
                        AppMetadataBackupWriter appMetadataBackupWriter =
                                new AppMetadataBackupWriter(
                                        new FullBackupDataOutput(
                                                this.mPipe,
                                                -1L,
                                                FullBackupEngine.this.mTransportFlags),
                                        this.mPackageManager);
                        String str = this.mPackage.packageName;
                        boolean equals = "com.android.sharedstoragebackup".equals(str);
                        ApplicationInfo applicationInfo = this.mPackage.applicationInfo;
                        boolean z = this.mIncludeApks;
                        int i = applicationInfo.flags;
                        boolean z2 = z && !equals && (!((i & 1) != 0) || ((i & 128) != 0));
                        if (!equals) {
                            File file = new File(this.mFilesDir, "_manifest");
                            appMetadataBackupWriter.backupManifest(
                                    this.mPackage, file, this.mFilesDir, null, z2);
                            file.delete();
                            byte[] widgetState =
                                    AppWidgetBackupBridge.getWidgetState(str, this.mUserId);
                            if (widgetState != null && widgetState.length > 0) {
                                File file2 = new File(this.mFilesDir, "_meta");
                                appMetadataBackupWriter.backupWidget(
                                        this.mPackage, file2, this.mFilesDir, widgetState);
                                file2.delete();
                            }
                        }
                        if (z2) {
                            appMetadataBackupWriter.backupApk(this.mPackage);
                            appMetadataBackupWriter.backupObb(this.mUserId, this.mPackage);
                        }
                        Slog.d("BackupManagerService", "Calling doFullBackup() on " + str);
                        if (equals) {
                            BackupAgentTimeoutParameters backupAgentTimeoutParameters =
                                    FullBackupEngine.this.mAgentTimeoutParameters;
                            synchronized (backupAgentTimeoutParameters.mLock) {
                                fullBackupAgentTimeoutMillis =
                                        backupAgentTimeoutParameters
                                                .mSharedBackupAgentTimeoutMillis;
                            }
                        } else {
                            fullBackupAgentTimeoutMillis =
                                    FullBackupEngine.this.mAgentTimeoutParameters
                                            .getFullBackupAgentTimeoutMillis();
                        }
                        long j = fullBackupAgentTimeoutMillis;
                        FullBackupEngine fullBackupEngine = FullBackupEngine.this;
                        fullBackupEngine.backupManagerService.prepareOperationTimeout(
                                this.mToken, j, fullBackupEngine.mTimeoutMonitor, 0);
                        if (this.mSmartSwitchBackupPath != null) {
                            this.mAgent.doDisableDataExtractionRules(
                                    this.isDisableDataExtractionRules);
                            IBackupAgent iBackupAgent = this.mAgent;
                            ParcelFileDescriptor parcelFileDescriptor = this.mPipe;
                            FullBackupEngine fullBackupEngine2 = FullBackupEngine.this;
                            iBackupAgent.doFullBackupPath(
                                    parcelFileDescriptor,
                                    fullBackupEngine2.mQuota,
                                    this.mToken,
                                    fullBackupEngine2.backupManagerService.mBackupManagerBinder,
                                    fullBackupEngine2.mTransportFlags,
                                    this.mSmartSwitchBackupPath);
                        } else {
                            IBackupAgent iBackupAgent2 = this.mAgent;
                            ParcelFileDescriptor parcelFileDescriptor2 = this.mPipe;
                            FullBackupEngine fullBackupEngine3 = FullBackupEngine.this;
                            iBackupAgent2.doFullBackup(
                                    parcelFileDescriptor2,
                                    fullBackupEngine3.mQuota,
                                    this.mToken,
                                    fullBackupEngine3.backupManagerService.mBackupManagerBinder,
                                    fullBackupEngine3.mTransportFlags);
                        }
                    } catch (IOException e) {
                        Slog.e(
                                "BackupManagerService",
                                "Error running full backup for " + this.mPackage.packageName,
                                e);
                        BackupManagerYuva backupManagerYuva = FullBackupEngine.mBackupManagerYuva;
                        if (backupManagerYuva != null) {
                            backupManagerYuva.isMemorySaverBackupFail = true;
                            backupManagerYuva.sendEndBackupCallback();
                        }
                    }
                } catch (RemoteException e2) {
                    Slog.e(
                            "BackupManagerService",
                            "Remote agent vanished during full backup of "
                                    + this.mPackage.packageName,
                            e2);
                    BackupManagerYuva backupManagerYuva2 = FullBackupEngine.mBackupManagerYuva;
                    if (backupManagerYuva2 != null) {
                        backupManagerYuva2.isMemorySaverBackupFail = true;
                        backupManagerYuva2.sendEndBackupCallback();
                    }
                }
                try {
                    this.mPipe.close();
                } catch (IOException unused) {
                }
            } catch (Throwable th) {
                try {
                    this.mPipe.close();
                } catch (IOException unused2) {
                }
                throw th;
            }
        }
    }

    public FullBackupEngine(
            UserBackupManagerService userBackupManagerService,
            OutputStream outputStream,
            PerformFullTransportBackupTask.SinglePackageBackupPreflight
                    singlePackageBackupPreflight,
            PackageInfo packageInfo,
            boolean z,
            BackupRestoreTask backupRestoreTask,
            long j,
            int i,
            int i2,
            BackupEligibilityRules backupEligibilityRules,
            int i3,
            String[] strArr,
            BackupManagerMonitorEventSender backupManagerMonitorEventSender) {
        this.backupManagerService = userBackupManagerService;
        this.mOutput = outputStream;
        this.mPreflightHook = singlePackageBackupPreflight;
        this.mPkg = packageInfo;
        this.mIncludeApks = z;
        this.mTimeoutMonitor = backupRestoreTask;
        this.mQuota = j;
        this.mOpToken = i;
        this.mTransportFlags = i2;
        this.mSmartSwitchBackupPath = strArr;
        BackupAgentTimeoutParameters backupAgentTimeoutParameters =
                userBackupManagerService.mAgentTimeoutParameters;
        Objects.requireNonNull(backupAgentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = backupAgentTimeoutParameters;
        this.mBackupEligibilityRules = backupEligibilityRules;
        mBackupManagerYuva = BackupManagerYuva.getInstanceBackupYuva();
        this.mBackupManagerMonitorEventSender = backupManagerMonitorEventSender;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00ad, code lost:

       if (r0 == null) goto L30;
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00af, code lost:

       r0.isMemorySaverBackupFail = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b1, code lost:

       r16 = -1000;
    */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00f8, code lost:

       if (r0 == null) goto L30;
    */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0133  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int backupOnePackage() {
        /*
            Method dump skipped, instructions count: 313
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.fullbackup.FullBackupEngine.backupOnePackage():int");
    }

    public final boolean initializeAgent() {
        if (this.mAgent == null) {
            this.mAgent =
                    this.backupManagerService.bindToAgentSynchronous(
                            this.mPkg.applicationInfo,
                            1,
                            this.mBackupEligibilityRules.mBackupDestination);
        }
        return this.mAgent != null;
    }

    public final int preflightCheck() {
        int i;
        final long j;
        final PerformFullTransportBackupTask.SinglePackageBackupPreflight
                singlePackageBackupPreflight = this.mPreflightHook;
        if (singlePackageBackupPreflight == null) {
            return 0;
        }
        int i2 = -1003;
        if (!initializeAgent()) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Unable to bind to full agent for "),
                    this.mPkg.packageName,
                    "BackupManagerService");
            return -1003;
        }
        PackageInfo packageInfo = this.mPkg;
        final IBackupAgent iBackupAgent = this.mAgent;
        PerformFullTransportBackupTask performFullTransportBackupTask =
                PerformFullTransportBackupTask.this;
        long fullBackupAgentTimeoutMillis =
                performFullTransportBackupTask.mAgentTimeoutParameters
                        .getFullBackupAgentTimeoutMillis();
        try {
            performFullTransportBackupTask.mUserBackupManagerService.prepareOperationTimeout(
                    singlePackageBackupPreflight.mCurrentOpToken,
                    fullBackupAgentTimeoutMillis,
                    singlePackageBackupPreflight,
                    0);
            iBackupAgent.doMeasureFullBackup(
                    singlePackageBackupPreflight.mQuota,
                    singlePackageBackupPreflight.mCurrentOpToken,
                    performFullTransportBackupTask.mUserBackupManagerService.mBackupManagerBinder,
                    singlePackageBackupPreflight.mTransportFlags);
            singlePackageBackupPreflight.mLatch.await(
                    fullBackupAgentTimeoutMillis, TimeUnit.MILLISECONDS);
            j = singlePackageBackupPreflight.mResult.get();
        } catch (Exception e) {
            Slog.w(
                    "PFTBT",
                    "Exception preflighting " + packageInfo.packageName + ": " + e.getMessage());
        }
        if (j < 0) {
            i = (int) j;
            this.mAgent.clearBackupRestoreEventLogger();
            return i;
        }
        BackupTransportClient connectOrThrow =
                singlePackageBackupPreflight.mTransportConnection.connectOrThrow(
                        "PFTBT$SPBP.preflightFullBackup()");
        BackupTransportClient.TransportStatusCallbackPool transportStatusCallbackPool =
                connectOrThrow.mCallbackPool;
        TransportStatusCallback acquire = transportStatusCallbackPool.acquire();
        try {
            connectOrThrow.mTransportBinder.checkFullBackupSize(j, acquire);
            int operationStatus = acquire.getOperationStatus();
            transportStatusCallbackPool.recycle(acquire);
            if (operationStatus == -1005) {
                new RemoteCall(
                                false,
                                new RemoteCallable() { // from class:
                                                       // com.android.server.backup.fullbackup.PerformFullTransportBackupTask$SinglePackageBackupPreflight$$ExternalSyntheticLambda0
                                    @Override // com.android.server.backup.remote.RemoteCallable
                                    public final void call(
                                            FutureBackupCallback futureBackupCallback) {
                                        iBackupAgent.doQuotaExceeded(
                                                j,
                                                PerformFullTransportBackupTask
                                                        .SinglePackageBackupPreflight.this
                                                        .mQuota,
                                                futureBackupCallback);
                                    }
                                },
                                performFullTransportBackupTask.mAgentTimeoutParameters
                                        .getQuotaExceededTimeoutMillis())
                        .call();
            }
            i2 = operationStatus;
            i = i2;
            this.mAgent.clearBackupRestoreEventLogger();
            return i;
        } catch (Throwable th) {
            transportStatusCallbackPool.recycle(acquire);
            throw th;
        }
    }
}

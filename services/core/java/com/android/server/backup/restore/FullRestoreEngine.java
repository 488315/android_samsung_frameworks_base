package com.android.server.backup.restore;

import android.app.IBackupAgent;
import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IFullBackupRestoreObserver;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.provider.Settings;
import android.system.OsConstants;
import android.text.TextUtils;
import android.util.Slog;

import com.android.server.ProfileService$1$$ExternalSyntheticOutline0;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.backup.BackupManagerYuva;
import com.android.server.backup.BackupRestoreTask;
import com.android.server.backup.FileMetadata;
import com.android.server.backup.Flags;
import com.android.server.backup.OperationStorage;
import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.internal.LifecycleOperationStorage;
import com.android.server.backup.utils.BackupEligibilityRules;
import com.android.server.backup.utils.BackupManagerMonitorEventSender;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class FullRestoreEngine extends RestoreEngine {
    public static BackupManagerYuva mBackupManagerYuva;
    public IBackupAgent mAgent;
    public String mAgentPackage;
    public final BackupAgentTimeoutParameters mAgentTimeoutParameters;
    public final boolean mAllowApks;
    public final HashMap mApkCount;
    public long mAppVersion;
    public final BackupEligibilityRules mBackupEligibilityRules;
    public final BackupManagerMonitorEventSender mBackupManagerMonitorEventSender;
    public final UserBackupManagerService mBackupManagerService;
    public final byte[] mBuffer;
    public final HashSet mClearedPackages;
    public final RestoreDeleteObserver mDeleteObserver;
    public final int mEphemeralOpToken;
    public final boolean mIsAdbRestore;
    public final HashMap mManifestSignatures;
    public final IBackupManagerMonitor mMonitor;
    public final BackupRestoreTask mMonitorTask;
    public IFullBackupRestoreObserver mObserver;
    public final PackageInfo mOnlyPackage;
    public final OperationStorage mOperationStorage;
    public final HashMap mPackageInstallers;
    public final HashMap mPackagePolicies;
    public ParcelFileDescriptor[] mPipes;
    public boolean mPipesClosed;
    public final Object mPipesLock;
    public boolean mPrivilegeApp;
    public FileMetadata mReadOnlyParent;
    public final HashMap mSessionFlag;
    public final HashMap mSessionId;
    public ApplicationInfo mTargetApp;
    public final int mUserId;
    public byte[] mWidgetData;
    public boolean restorePass;

    public FullRestoreEngine() {
        this.mDeleteObserver = new RestoreDeleteObserver();
        this.mPackagePolicies = new HashMap();
        this.mPackageInstallers = new HashMap();
        this.mApkCount = new HashMap();
        this.mSessionFlag = new HashMap();
        this.mSessionId = new HashMap();
        this.mManifestSignatures = new HashMap();
        this.mClearedPackages = new HashSet();
        this.mPipes = null;
        this.mPipesLock = new Object();
        this.mWidgetData = null;
        this.restorePass = false;
        this.mPrivilegeApp = false;
        this.mReadOnlyParent = null;
        this.mIsAdbRestore = false;
        this.mAllowApks = false;
        this.mEphemeralOpToken = 0;
        this.mUserId = 0;
        this.mBackupEligibilityRules = null;
        this.mAgentTimeoutParameters = null;
        this.mBuffer = null;
        this.mBackupManagerService = null;
        this.mOperationStorage = null;
        this.mMonitor = null;
        this.mMonitorTask = null;
        this.mOnlyPackage = null;
    }

    public FullRestoreEngine(
            UserBackupManagerService userBackupManagerService,
            LifecycleOperationStorage lifecycleOperationStorage,
            BackupRestoreTask backupRestoreTask,
            IFullBackupRestoreObserver iFullBackupRestoreObserver,
            IBackupManagerMonitor iBackupManagerMonitor,
            PackageInfo packageInfo,
            boolean z,
            int i,
            boolean z2,
            BackupEligibilityRules backupEligibilityRules) {
        this.mDeleteObserver = new RestoreDeleteObserver();
        this.mPackagePolicies = new HashMap();
        this.mPackageInstallers = new HashMap();
        this.mApkCount = new HashMap();
        this.mSessionFlag = new HashMap();
        this.mSessionId = new HashMap();
        this.mManifestSignatures = new HashMap();
        this.mClearedPackages = new HashSet();
        this.mPipes = null;
        this.mPipesLock = new Object();
        this.mWidgetData = null;
        this.restorePass = false;
        this.mPrivilegeApp = false;
        this.mReadOnlyParent = null;
        this.mBackupManagerService = userBackupManagerService;
        this.mOperationStorage = lifecycleOperationStorage;
        this.mEphemeralOpToken = i;
        this.mMonitorTask = backupRestoreTask;
        this.mObserver = iFullBackupRestoreObserver;
        this.mMonitor = iBackupManagerMonitor;
        this.mBackupManagerMonitorEventSender =
                new BackupManagerMonitorEventSender(iBackupManagerMonitor);
        this.mOnlyPackage = packageInfo;
        this.mAllowApks = z;
        BackupAgentTimeoutParameters backupAgentTimeoutParameters =
                userBackupManagerService.mAgentTimeoutParameters;
        Objects.requireNonNull(backupAgentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = backupAgentTimeoutParameters;
        this.mIsAdbRestore = z2;
        this.mUserId = userBackupManagerService.mUserId;
        this.mBackupEligibilityRules = backupEligibilityRules;
        if (Flags.enableMaxSizeWritesToPipes()) {
            this.mBuffer = new byte[EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT];
        } else {
            this.mBuffer = new byte[32768];
        }
        if (userBackupManagerService.isYuvaSupported()) {
            Slog.d("BackupManagerService", "Backup Manager Yuva is Supported");
            mBackupManagerYuva = BackupManagerYuva.getInstanceBackupYuva();
        }
    }

    public static boolean isCanonicalFilePath(String str) {
        return (str.contains("..") || str.contains("//")) ? false : true;
    }

    public final boolean isRestorableFile(FileMetadata fileMetadata) {
        if (this.mBackupEligibilityRules.mBackupDestination == 1) {
            return true;
        }
        if ("c".equals(fileMetadata.domain)) {
            return false;
        }
        return ("r".equals(fileMetadata.domain) && fileMetadata.path.startsWith("no_backup/"))
                ? false
                : true;
    }

    public final void logBMMEvent(int i, PackageInfo packageInfo) {
        if (Flags.enableIncreasedBmmLoggingForRestoreAtInstall()) {
            this.mBackupManagerMonitorEventSender.monitorEvent(
                    i,
                    packageInfo,
                    3,
                    SystemUpdateManagerService$$ExternalSyntheticOutline0.m(
                            1, "android.app.backup.extra.OPERATION_TYPE"));
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void restoreOneFile(
            java.io.InputStream r43,
            boolean r44,
            byte[] r45,
            android.content.pm.PackageInfo r46,
            boolean r47,
            int r48,
            android.app.backup.IBackupManagerMonitor r49) {
        /*
            Method dump skipped, instructions count: 1725
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.restore.FullRestoreEngine.restoreOneFile(java.io.InputStream,"
                    + " boolean, byte[], android.content.pm.PackageInfo, boolean, int,"
                    + " android.app.backup.IBackupManagerMonitor):void");
    }

    public final void setUpPipes() {
        synchronized (this.mPipesLock) {
            this.mPipes = ParcelFileDescriptor.createPipe();
            this.mPipesClosed = false;
        }
    }

    public final boolean shouldForceClearAppDataOnFullRestore(String str) {
        String stringForUser =
                Settings.Secure.getStringForUser(
                        this.mBackupManagerService.mContext.getContentResolver(),
                        "packages_to_clear_data_before_full_restore",
                        this.mUserId);
        if (TextUtils.isEmpty(stringForUser)) {
            return false;
        }
        return Arrays.asList(stringForUser.split(";")).contains(str);
    }

    public final boolean shouldSkipReadOnlyDir(FileMetadata fileMetadata) {
        FileMetadata fileMetadata2 = this.mReadOnlyParent;
        if (fileMetadata2 != null
                && fileMetadata.packageName.equals(fileMetadata2.packageName)
                && fileMetadata.domain.equals(fileMetadata2.domain)) {
            String str = fileMetadata.path;
            String str2 = fileMetadata2.path;
            String str3 = File.separator;
            if (!str2.endsWith(str3)) {
                str2 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, str3);
            }
            if (str.startsWith(str2)) {
                return true;
            }
        }
        if (fileMetadata.type != 2 || (fileMetadata.mode & OsConstants.S_IWUSR) != 0) {
            this.mReadOnlyParent = null;
            return false;
        }
        this.mReadOnlyParent = fileMetadata;
        ProfileService$1$$ExternalSyntheticOutline0.m(
                new StringBuilder("Skipping restore of "),
                fileMetadata.path,
                " and its contents as read-only dirs are currently not supported.",
                "BackupManagerService");
        return true;
    }

    public final void tearDownAgent(ApplicationInfo applicationInfo, boolean z) {
        if (this.mAgent != null) {
            UserBackupManagerService userBackupManagerService = this.mBackupManagerService;
            if (z) {
                try {
                    int generateRandomIntegerToken =
                            userBackupManagerService.generateRandomIntegerToken();
                    long fullBackupAgentTimeoutMillis =
                            this.mAgentTimeoutParameters.getFullBackupAgentTimeoutMillis();
                    AdbRestoreFinishedLatch adbRestoreFinishedLatch =
                            new AdbRestoreFinishedLatch(
                                    userBackupManagerService,
                                    this.mOperationStorage,
                                    generateRandomIntegerToken);
                    this.mBackupManagerService.prepareOperationTimeout(
                            generateRandomIntegerToken,
                            fullBackupAgentTimeoutMillis,
                            adbRestoreFinishedLatch,
                            1);
                    if (this.mTargetApp.processName.equals("system")) {
                        new Thread(
                                        new AdbRestoreFinishedRunnable(
                                                this.mAgent,
                                                generateRandomIntegerToken,
                                                userBackupManagerService),
                                        "restore-sys-finished-runner")
                                .start();
                    } else {
                        this.mAgent.doRestoreFinished(
                                generateRandomIntegerToken,
                                userBackupManagerService.mBackupManagerBinder);
                    }
                    try {
                        adbRestoreFinishedLatch.mLatch.await(
                                adbRestoreFinishedLatch.mAgentTimeoutParameters
                                        .getFullBackupAgentTimeoutMillis(),
                                TimeUnit.MILLISECONDS);
                    } catch (InterruptedException unused) {
                        Slog.w("AdbRestoreFinishedLatch", "Interrupted!");
                    }
                } catch (RemoteException unused2) {
                    Slog.d("BackupManagerService", "Lost app trying to shut down");
                }
            }
            userBackupManagerService.tearDownAgentAndKill(applicationInfo);
            this.mAgent = null;
        }
    }

    public final void tearDownPipes() {
        ParcelFileDescriptor[] parcelFileDescriptorArr;
        synchronized (this.mPipesLock) {
            if (!this.mPipesClosed && (parcelFileDescriptorArr = this.mPipes) != null) {
                try {
                    parcelFileDescriptorArr[0].close();
                    this.mPipes[1].close();
                    this.mPipesClosed = true;
                } catch (IOException e) {
                    Slog.w("BackupManagerService", "Couldn't close agent pipes", e);
                }
            }
        }
    }
}

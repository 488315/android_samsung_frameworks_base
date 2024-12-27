package com.android.server.backup;

import android.app.IBackupAgent;
import android.app.backup.IBackupManager;
import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IBackupObserver;
import android.app.backup.IFullBackupRestoreObserver;
import android.app.backup.IMemorySaverBackupRestoreObserver;
import android.app.backup.ISelectBackupTransportCallback;
import android.app.compat.CompatChanges;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.util.DumpUtils;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.SystemConfig;
import com.android.server.SystemService;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.backup.fullbackup.PerformFullTransportBackupTask;
import com.android.server.backup.fullbackup.PerformFullTransportBackupTask$$ExternalSyntheticLambda0;
import com.android.server.backup.internal.BackupHandler;
import com.android.server.backup.internal.LifecycleOperationStorage;
import com.android.server.backup.internal.Operation;
import com.android.server.backup.params.AdbBackupParams;
import com.android.server.backup.params.AdbRestoreParams;
import com.android.server.backup.transport.TransportConnection;
import com.android.server.backup.transport.TransportNotAvailableException;
import com.android.server.backup.transport.TransportNotRegisteredException;
import com.android.server.backup.utils.BackupEligibilityRules;
import com.android.server.backup.utils.BackupManagerMonitorEventSender;
import com.android.server.backup.utils.BackupObserverUtils;
import com.android.server.backup.utils.DataStreamFileCodec;
import com.android.server.backup.utils.PasswordUtils;
import com.android.server.clipboard.ClipboardService;

import libcore.util.HexEncoding;

import com.samsung.android.knox.analytics.activation.ActivationMonitor;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import javax.crypto.SecretKey;

public final class BackupManagerService extends IBackupManager.Stub {
    static final String DUMP_RUNNING_USERS_MESSAGE = "Backup Manager is running for users:";
    public static BackupManagerService sInstance;
    public final Context mContext;
    public int mDefaultBackupUserId;
    public final boolean mGlobalDisable;
    public final Handler mHandler;
    public boolean mHasFirstUserUnlockedSinceBoot;
    public final Object mStateLock = new Object();
    public final Set mTransportWhitelist;
    public final UserManager mUserManager;
    public final AnonymousClass1 mUserRemovedReceiver;
    public final SparseArray mUserServices;

    /* renamed from: com.android.server.backup.BackupManagerService$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            int intExtra;
            if (!"android.intent.action.USER_REMOVED".equals(intent.getAction())
                    || (intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000))
                            <= 0) {
                return;
            }
            BackupManagerService.this.mHandler.post(
                    new BackupManagerService$$ExternalSyntheticLambda0(intExtra, 1, this));
        }
    }

    public final class Lifecycle extends SystemService {
        public Lifecycle(Context context) {
            this(context, new BackupManagerService(context));
        }

        public Lifecycle(Context context, BackupManagerService backupManagerService) {
            super(context);
            BackupManagerService.sInstance = backupManagerService;
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            publishService("backup", BackupManagerService.sInstance);
        }

        @Override // com.android.server.SystemService
        public final void onUserStopping(SystemService.TargetUser targetUser) {
            BackupManagerService backupManagerService = BackupManagerService.sInstance;
            int userIdentifier = targetUser.getUserIdentifier();
            backupManagerService.getClass();
            backupManagerService.postToHandler(
                    new BackupManagerService$$ExternalSyntheticLambda0(
                            userIdentifier, 0, backupManagerService));
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(final SystemService.TargetUser targetUser) {
            BackupManagerService.sInstance.postToHandler(
                    new Runnable() { // from class:
                                     // com.android.server.backup.BackupManagerService$Lifecycle$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            UserHandle mainUser;
                            SystemService.TargetUser targetUser2 = SystemService.TargetUser.this;
                            BackupManagerService backupManagerService =
                                    BackupManagerService.sInstance;
                            if (!backupManagerService.mHasFirstUserUnlockedSinceBoot
                                    && backupManagerService.mDefaultBackupUserId == 0
                                    && (mainUser =
                                                    backupManagerService
                                                            .getUserManager()
                                                            .getMainUser())
                                            != null
                                    && backupManagerService.mDefaultBackupUserId
                                            != mainUser.getIdentifier()) {
                                int i = backupManagerService.mDefaultBackupUserId;
                                backupManagerService.mDefaultBackupUserId =
                                        mainUser.getIdentifier();
                                if (!backupManagerService.isBackupActivatedForUser(i)) {
                                    backupManagerService.stopServiceForUser(i);
                                }
                                SystemServiceManager$$ExternalSyntheticOutline0.m(
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                i, "Default backup user changed from ", " to "),
                                        backupManagerService.mDefaultBackupUserId,
                                        "BackupManagerService");
                            }
                            BackupManagerService.sInstance.startServiceForUser(
                                    targetUser2.getUserIdentifier());
                            BackupManagerService.sInstance.mHasFirstUserUnlockedSinceBoot = true;
                        }
                    });
        }

        public void publishService(String str, IBinder iBinder) {
            publishBinderService(str, iBinder);
        }
    }

    public BackupManagerService(Context context) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mHasFirstUserUnlockedSinceBoot = false;
        this.mContext = context;
        this.mGlobalDisable = isBackupDisabled();
        HandlerThread handlerThread = new HandlerThread("backup", 10);
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
        this.mUserManager = UserManager.get(context);
        this.mUserServices = new SparseArray();
        Set set = SystemConfig.getInstance().mBackupTransportWhitelist;
        this.mTransportWhitelist = set == null ? Collections.emptySet() : set;
        context.registerReceiver(
                anonymousClass1, new IntentFilter("android.intent.action.USER_REMOVED"));
        UserHandle mainUser = getUserManager().getMainUser();
        this.mDefaultBackupUserId = mainUser != null ? mainUser.getIdentifier() : 0;
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("Default backup user id = "),
                this.mDefaultBackupUserId,
                "BackupManagerService");
    }

    public static void createFile(File file) {
        if (file.exists()) {
            return;
        }
        file.getParentFile().mkdirs();
        if (file.createNewFile()) {
            return;
        }
        Slog.w("BackupManagerService", "Failed to create file " + file.getPath());
    }

    public static void deleteFile(File file) {
        if (file.exists() && !file.delete()) {
            Slog.w("BackupManagerService", "Failed to delete file " + file.getPath());
        }
    }

    public static void showDumpUsage(PrintWriter printWriter) {
        BatteryService$$ExternalSyntheticOutline0.m(
                printWriter,
                "'dumpsys backup' optional arguments:",
                "  --help    : this help text",
                "  a[gents] : dump information about defined backup agents",
                "  transportclients : dump information about transport clients");
        BatteryService$$ExternalSyntheticOutline0.m(
                printWriter,
                "  transportstats : dump transport stats",
                "  users    : dump the list of users for which backup service is running",
                "  --user <userId> : dump information for user userId");
    }

    public final void acknowledgeFullBackupOrRestore(
            int i,
            boolean z,
            String str,
            String str2,
            IFullBackupRestoreObserver iFullBackupRestoreObserver) {
        acknowledgeFullBackupOrRestoreForUser(
                binderGetCallingUserId(), i, z, str, str2, iFullBackupRestoreObserver);
    }

    public final void acknowledgeFullBackupOrRestoreForUser(
            int i,
            int i2,
            boolean z,
            String str,
            String str2,
            IFullBackupRestoreObserver iFullBackupRestoreObserver) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(
                                        i, "acknowledgeAdbBackupOrRestore()"))
                        == null) {
            return;
        }
        serviceForUserIfCallerHasPermission.acknowledgeAdbBackupOrRestore(
                i2, z, str, str2, iFullBackupRestoreObserver);
    }

    public final void adbBackup(
            int i,
            ParcelFileDescriptor parcelFileDescriptor,
            boolean z,
            boolean z2,
            boolean z3,
            boolean z4,
            boolean z5,
            boolean z6,
            boolean z7,
            boolean z8,
            String[] strArr) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (isUserReadyForBackup(i)
                && (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(i, "adbBackup()"))
                        != null) {
            serviceForUserIfCallerHasPermission.adbBackup(
                    parcelFileDescriptor, z, z2, z3, z4, z5, z6, z7, z8, strArr, true);
        }
    }

    public final void adbRestore(int i, ParcelFileDescriptor parcelFileDescriptor) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (isUserReadyForBackup(i)
                && (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(i, "adbRestore()"))
                        != null) {
            serviceForUserIfCallerHasPermission.adbRestore(parcelFileDescriptor, true);
        }
    }

    public final void agentConnected(String str, IBinder iBinder) {
        agentConnectedForUser(binderGetCallingUserId(), str, iBinder);
    }

    public final void agentConnectedForUser(int i, String str, IBinder iBinder) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(i, "agentConnected()"))
                        == null) {
            return;
        }
        synchronized (serviceForUserIfCallerHasPermission.mAgentConnectLock) {
            try {
                if (Binder.getCallingUid() == 1000) {
                    Slog.d(
                            "BackupManagerService",
                            UserBackupManagerService.addUserIdToLogMessage(
                                    serviceForUserIfCallerHasPermission.mUserId,
                                    "agentConnected pkg=" + str + " agent=" + iBinder));
                    serviceForUserIfCallerHasPermission.mConnectedAgent =
                            IBackupAgent.Stub.asInterface(iBinder);
                    serviceForUserIfCallerHasPermission.mConnecting = false;
                } else {
                    Slog.w(
                            "BackupManagerService",
                            UserBackupManagerService.addUserIdToLogMessage(
                                    serviceForUserIfCallerHasPermission.mUserId,
                                    "Non-system process uid="
                                            + Binder.getCallingUid()
                                            + " claiming agent connected"));
                }
                serviceForUserIfCallerHasPermission.mAgentConnectLock.notifyAll();
            } finally {
            }
        }
    }

    public final void agentDisconnected(String str) {
        agentDisconnectedForUser(binderGetCallingUserId(), str);
    }

    public final void agentDisconnectedForUser(int i, String str) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(i, "agentDisconnected()"))
                        == null) {
            return;
        }
        synchronized (serviceForUserIfCallerHasPermission.mAgentConnectLock) {
            try {
                if (Binder.getCallingUid() == 1000) {
                    serviceForUserIfCallerHasPermission.mConnectedAgent = null;
                    serviceForUserIfCallerHasPermission.mConnecting = false;
                } else {
                    Slog.w(
                            "BackupManagerService",
                            UserBackupManagerService.addUserIdToLogMessage(
                                    serviceForUserIfCallerHasPermission.mUserId,
                                    "Non-system process uid="
                                            + Binder.getCallingUid()
                                            + " claiming agent disconnected"));
                }
                Slog.w(
                        "BackupManagerService",
                        "agentDisconnected: the backup agent for "
                                + str
                                + " died: cancel current operations");
                serviceForUserIfCallerHasPermission
                        .getThreadForAsyncOperation(
                                "agent-disconnected",
                                new UserBackupManagerService$$ExternalSyntheticLambda11(
                                        str, 0, serviceForUserIfCallerHasPermission))
                        .start();
                serviceForUserIfCallerHasPermission.mAgentConnectLock.notifyAll();
            } catch (Throwable th) {
                throw th;
            }
        }
        AdbBackupParams adbBackupParams = serviceForUserIfCallerHasPermission.mAdbBackupParams;
        if (adbBackupParams != null) {
            serviceForUserIfCallerHasPermission.mBackupHandler.removeMessages(101, adbBackupParams);
            serviceForUserIfCallerHasPermission.mBackupHandler.sendMessage(
                    serviceForUserIfCallerHasPermission.mBackupHandler.obtainMessage(
                            101, serviceForUserIfCallerHasPermission.mAdbBackupParams));
            return;
        }
        AdbRestoreParams adbRestoreParams = serviceForUserIfCallerHasPermission.mAdbRestoreParams;
        if (adbRestoreParams != null) {
            serviceForUserIfCallerHasPermission.mBackupHandler.removeMessages(
                    101, adbRestoreParams);
            serviceForUserIfCallerHasPermission.mBackupHandler.sendMessage(
                    serviceForUserIfCallerHasPermission.mBackupHandler.obtainMessage(
                            101, serviceForUserIfCallerHasPermission.mAdbRestoreParams));
        }
    }

    public final void backupNow() {
        backupNowForUser(binderGetCallingUserId());
    }

    public final void backupNowForUser(int i) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(i, "backupNow()"))
                        == null) {
            return;
        }
        serviceForUserIfCallerHasPermission.backupNow();
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x0112  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.app.backup.IRestoreSession beginRestoreSessionForUser(
            int r6, java.lang.String r7, java.lang.String r8) {
        /*
            Method dump skipped, instructions count: 285
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.BackupManagerService.beginRestoreSessionForUser(int,"
                    + " java.lang.String, java.lang.String):android.app.backup.IRestoreSession");
    }

    public int binderGetCallingUid() {
        return Binder.getCallingUid();
    }

    public int binderGetCallingUserId() {
        return Binder.getCallingUserHandle().getIdentifier();
    }

    public final void cancelBackups() {
        cancelBackupsForUser(binderGetCallingUserId());
    }

    public final void cancelBackupsForUser(int i) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(i, "cancelBackups()"))
                        == null) {
            return;
        }
        int i2 = serviceForUserIfCallerHasPermission.mUserId;
        LifecycleOperationStorage lifecycleOperationStorage =
                serviceForUserIfCallerHasPermission.mOperationStorage;
        serviceForUserIfCallerHasPermission.mContext.enforceCallingPermission(
                "android.permission.BACKUP", "cancelBackups");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Iterator it = lifecycleOperationStorage.operationTokensForOpType().iterator();
            while (it.hasNext()) {
                lifecycleOperationStorage.cancelOperation(
                        ((Integer) it.next()).intValue(),
                        true,
                        new UserBackupManagerService$$ExternalSyntheticLambda12());
            }
            KeyValueBackupJob.schedule(
                    i2,
                    serviceForUserIfCallerHasPermission.mContext,
                    ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS,
                    serviceForUserIfCallerHasPermission);
            FullBackupJob.schedule(
                    i2,
                    serviceForUserIfCallerHasPermission.mContext,
                    7200000L,
                    serviceForUserIfCallerHasPermission);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void clearBackupData(String str, String str2) {
        clearBackupDataForUser(binderGetCallingUserId(), str, str2);
    }

    public final void clearBackupDataForUser(int i, String str, String str2) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(i, "clearBackupData()"))
                        == null) {
            return;
        }
        serviceForUserIfCallerHasPermission.clearBackupData(str, str2);
    }

    public final void dataChanged(String str) {
        dataChangedForUser(binderGetCallingUserId(), str);
    }

    public final void dataChangedForUser(int i, final String str) {
        final UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(i, "dataChanged()"))
                        == null) {
            return;
        }
        final HashSet dataChangedTargets =
                serviceForUserIfCallerHasPermission.dataChangedTargets(str);
        if (dataChangedTargets != null) {
            serviceForUserIfCallerHasPermission.mBackupHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.backup.UserBackupManagerService.4
                        public final /* synthetic */ String val$packageName;
                        public final /* synthetic */ HashSet val$targets;

                        public AnonymousClass4(
                                final String str2, final HashSet dataChangedTargets2) {
                            r2 = str2;
                            r3 = dataChangedTargets2;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            UserBackupManagerService.this.dataChangedImpl(r2, r3);
                        }
                    });
            return;
        }
        StringBuilder m =
                DumpUtils$$ExternalSyntheticOutline0.m(
                        "dataChanged but no participant pkg='", str2, "' uid=");
        m.append(Binder.getCallingUid());
        Slog.w(
                "BackupManagerService",
                UserBackupManagerService.addUserIdToLogMessage(
                        serviceForUserIfCallerHasPermission.mUserId, m.toString()));
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpAndUsageStatsPermission(
                this.mContext, "BackupManagerService", printWriter)) {
            int i = 0;
            String str = strArr.length <= 0 ? null : strArr[0];
            if ("--help".equals(str) || "-h".equals(str)) {
                showDumpUsage(printWriter);
                return;
            }
            if ("users".equals(str)) {
                printWriter.print(DUMP_RUNNING_USERS_MESSAGE);
                while (i < this.mUserServices.size()) {
                    UserBackupManagerService serviceForUserIfCallerHasPermission =
                            getServiceForUserIfCallerHasPermission(
                                    this.mUserServices.keyAt(i), "dump()");
                    if (serviceForUserIfCallerHasPermission != null) {
                        printWriter.print(" " + serviceForUserIfCallerHasPermission.mUserId);
                    }
                    i++;
                }
                printWriter.println();
                return;
            }
            if (!"--user".equals(str)) {
                while (i < this.mUserServices.size()) {
                    UserBackupManagerService serviceForUserIfCallerHasPermission2 =
                            getServiceForUserIfCallerHasPermission(
                                    this.mUserServices.keyAt(i), "dump()");
                    if (serviceForUserIfCallerHasPermission2 != null) {
                        serviceForUserIfCallerHasPermission2.dump(printWriter, strArr);
                    }
                    i++;
                }
                return;
            }
            String str2 = 1 < strArr.length ? strArr[1] : null;
            if (str2 == null) {
                showDumpUsage(printWriter);
                return;
            }
            UserBackupManagerService serviceForUserIfCallerHasPermission3 =
                    getServiceForUserIfCallerHasPermission(UserHandle.parseUserArg(str2), "dump()");
            if (serviceForUserIfCallerHasPermission3 != null) {
                serviceForUserIfCallerHasPermission3.dump(printWriter, strArr);
            }
        }
    }

    public final void excludeKeysFromRestore(String str, List list) {
        int identifier = Binder.getCallingUserHandle().getIdentifier();
        if (!isUserReadyForBackup(identifier)) {
            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                    identifier,
                    "Returning from excludeKeysFromRestore as backup for user",
                    " is not initialized yet",
                    "BackupManagerService");
            return;
        }
        UserBackupManagerService serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(identifier, "excludeKeysFromRestore()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                    "android.permission.BACKUP", "excludeKeysFromRestore");
            UserBackupPreferences userBackupPreferences =
                    serviceForUserIfCallerHasPermission.mBackupPreferences;
            userBackupPreferences.getClass();
            HashSet hashSet =
                    new HashSet(
                            userBackupPreferences.mPreferences.getStringSet(
                                    str, Collections.emptySet()));
            hashSet.addAll(list);
            userBackupPreferences.mEditor.putStringSet(str, hashSet);
            userBackupPreferences.mEditor.commit();
        }
    }

    public final String[] filterAppsEligibleForBackupForUser(int i, String[] strArr) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(
                                        i, "filterAppsEligibleForBackup()"))
                        == null) {
            return null;
        }
        TransportManager transportManager = serviceForUserIfCallerHasPermission.mTransportManager;
        serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                "android.permission.BACKUP", "filterAppsEligibleForBackup");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            TransportConnection currentTransportClient =
                    transportManager.getCurrentTransportClient("BMS.filterAppsEligibleForBackup");
            ArrayList arrayList = new ArrayList();
            for (String str : strArr) {
                if (serviceForUserIfCallerHasPermission.mScheduledBackupEligibility
                        .appIsRunningAndEligibleForBackupWithTransport(
                                currentTransportClient, str)) {
                    arrayList.add(str);
                }
            }
            if (currentTransportClient != null) {
                transportManager.mTransportConnectionManager.disposeOfTransportClient(
                        currentTransportClient, "BMS.filterAppsEligibleForBackup");
            }
            String[] strArr2 = (String[]) arrayList.toArray(new String[0]);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return strArr2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void fullBackupCustomized(
            int i,
            String str,
            boolean z,
            boolean z2,
            boolean z3,
            boolean z4,
            boolean z5,
            boolean z6,
            boolean z7,
            String[] strArr,
            boolean z8,
            String str2,
            boolean z9,
            IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver) {
        try {
            UserBackupManagerService serviceForUserIfCallerHasPermission =
                    getServiceForUserIfCallerHasPermission(i, "fullBackupCustomized()");
            if (serviceForUserIfCallerHasPermission != null) {
                ParcelFileDescriptor open = ParcelFileDescriptor.open(new File(str), 805306368);
                serviceForUserIfCallerHasPermission.mExtraFlag = 512;
                BackupManagerYuva backupManagerYuva = UserBackupManagerService.mBackupManagerYuva;
                if (backupManagerYuva != null) {
                    backupManagerYuva.mMemorySaverObserver = iMemorySaverBackupRestoreObserver;
                    backupManagerYuva.isMemorySaverRestoreFail = false;
                    backupManagerYuva.isMemorySaverBackupFail = false;
                    backupManagerYuva.mBackupPackageName = null;
                    backupManagerYuva.mRestorePackageName = null;
                }
                serviceForUserIfCallerHasPermission.mEncPassword = str2;
                UserBackupManagerService.mSplitBackupFlag = Boolean.TRUE;
                serviceForUserIfCallerHasPermission.adbBackup(
                        open, z, z2, z3, z4, z5, z6, z7, z9, strArr, true);
                UserBackupManagerService.mSplitBackupFlag = Boolean.FALSE;
            }
        } catch (Exception unused) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    i, "userId ", " fullbackupCustomized error", "BackupManagerService");
        }
    }

    public final void fullRestoreCustomized(
            int i,
            String str,
            boolean z,
            String str2,
            IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver) {
        try {
            UserBackupManagerService serviceForUserIfCallerHasPermission =
                    getServiceForUserIfCallerHasPermission(i, "fullRestoreCustomized()");
            if (serviceForUserIfCallerHasPermission != null) {
                serviceForUserIfCallerHasPermission.fullRestoreCustomized(
                        str, str2, iMemorySaverBackupRestoreObserver);
            }
        } catch (Exception unused) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    i, "userId ", " fullRestoreCustomized error ", "BackupManagerService");
        }
    }

    public final void fullTransportBackupForUser(int i, String[] strArr) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(i, "fullTransportBackup()"))
                        == null) {
            return;
        }
        serviceForUserIfCallerHasPermission.mContext.enforceCallingPermission(
                "android.permission.BACKUP", "fullTransportBackup");
        if (UserHandle.getCallingUserId() != 0) {
            throw new IllegalStateException("Restore supported only for the device owner");
        }
        if (serviceForUserIfCallerHasPermission.fullBackupAllowable(
                serviceForUserIfCallerHasPermission.mTransportManager.mCurrentTransportName)) {
            Slog.d(
                    "BackupManagerService",
                    UserBackupManagerService.addUserIdToLogMessage(
                            serviceForUserIfCallerHasPermission.mUserId, "fullTransportBackup()"));
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                CountDownLatch countDownLatch = new CountDownLatch(1);
                LifecycleOperationStorage lifecycleOperationStorage =
                        serviceForUserIfCallerHasPermission.mOperationStorage;
                BackupEligibilityRules eligibilityRulesForOperation =
                        serviceForUserIfCallerHasPermission.getEligibilityRulesForOperation(0);
                TransportManager transportManager =
                        serviceForUserIfCallerHasPermission.mTransportManager;
                TransportConnection currentTransportClient =
                        transportManager.getCurrentTransportClient("BMS.fullTransportBackup()");
                if (currentTransportClient == null) {
                    throw new IllegalStateException("No TransportConnection available");
                }
                PerformFullTransportBackupTask performFullTransportBackupTask =
                        new PerformFullTransportBackupTask(
                                serviceForUserIfCallerHasPermission,
                                lifecycleOperationStorage,
                                currentTransportClient,
                                strArr,
                                false,
                                null,
                                countDownLatch,
                                null,
                                null,
                                new PerformFullTransportBackupTask$$ExternalSyntheticLambda0(
                                        transportManager, currentTransportClient),
                                false,
                                eligibilityRulesForOperation);
                serviceForUserIfCallerHasPermission.mWakelock.acquire();
                new Thread(performFullTransportBackupTask, "full-transport-master").start();
                while (true) {
                    try {
                        countDownLatch.await();
                        break;
                    } catch (InterruptedException unused) {
                    }
                }
                long currentTimeMillis = System.currentTimeMillis();
                for (String str : strArr) {
                    serviceForUserIfCallerHasPermission.enqueueFullBackup(currentTimeMillis, str);
                }
            } catch (IllegalStateException e) {
                Slog.w("BackupManagerService", "Failed to start backup: ", e);
                return;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } else {
            Slog.i(
                    "BackupManagerService",
                    UserBackupManagerService.addUserIdToLogMessage(
                            serviceForUserIfCallerHasPermission.mUserId,
                            "Full backup not currently possible -- key/value backup not yet run?"));
        }
        Slog.d(
                "BackupManagerService",
                UserBackupManagerService.addUserIdToLogMessage(
                        serviceForUserIfCallerHasPermission.mUserId,
                        "Done with full transport backup."));
    }

    public File getActivatedFileForUser(int i) {
        return new File(
                new File(
                        UserBackupManagerFiles.getBaseStateDir(0),
                        VibrationParam$1$$ExternalSyntheticOutline0.m(i, "")),
                "backup-activated");
    }

    public final long getAvailableRestoreTokenForUser(int i, String str) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(
                                        i, "getAvailableRestoreToken()"))
                        == null) {
            return 0L;
        }
        return serviceForUserIfCallerHasPermission.getAvailableRestoreToken(str);
    }

    public Handler getBackupHandler() {
        return this.mHandler;
    }

    public final Intent getConfigurationIntent(String str) {
        return getConfigurationIntentForUser(binderGetCallingUserId(), str);
    }

    public final Intent getConfigurationIntentForUser(int i, String str) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        Intent intent;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(
                                        i, "getConfigurationIntent()"))
                        == null) {
            return null;
        }
        serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                "android.permission.BACKUP", "getConfigurationIntent");
        try {
            TransportManager transportManager =
                    serviceForUserIfCallerHasPermission.mTransportManager;
            synchronized (transportManager.mTransportLock) {
                intent =
                        transportManager.getRegisteredTransportDescriptionOrThrowLocked(str)
                                .configurationIntent;
            }
            return intent;
        } catch (TransportNotRegisteredException e) {
            Slog.e(
                    "BackupManagerService",
                    UserBackupManagerService.addUserIdToLogMessage(
                            serviceForUserIfCallerHasPermission.mUserId,
                            "Unable to get configuration intent from transport: "
                                    + e.getMessage()));
            return null;
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public final String getCurrentTransport() {
        return getCurrentTransportForUser(binderGetCallingUserId());
    }

    public final ComponentName getCurrentTransportComponentForUser(int i) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        ComponentName componentName = null;
        if (isUserReadyForBackup(i)
                && (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(
                                        i, "getCurrentTransportComponent()"))
                        != null) {
            serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                    "android.permission.BACKUP", "getCurrentTransportComponent");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                componentName =
                        serviceForUserIfCallerHasPermission.mTransportManager
                                .getCurrentTransportComponent();
            } catch (TransportNotRegisteredException unused) {
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return componentName;
    }

    public final String getCurrentTransportForUser(int i) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(i, "getCurrentTransport()"))
                        == null) {
            return null;
        }
        serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                "android.permission.BACKUP", "getCurrentTransport");
        return serviceForUserIfCallerHasPermission.mTransportManager.mCurrentTransportName;
    }

    public final Intent getDataManagementIntent(String str) {
        return getDataManagementIntentForUser(binderGetCallingUserId(), str);
    }

    public final Intent getDataManagementIntentForUser(int i, String str) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        Intent intent;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(
                                        i, "getDataManagementIntent()"))
                        == null) {
            return null;
        }
        serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                "android.permission.BACKUP", "getDataManagementIntent");
        try {
            TransportManager transportManager =
                    serviceForUserIfCallerHasPermission.mTransportManager;
            synchronized (transportManager.mTransportLock) {
                intent =
                        transportManager.getRegisteredTransportDescriptionOrThrowLocked(str)
                                .dataManagementIntent;
            }
            return intent;
        } catch (TransportNotRegisteredException e) {
            Slog.e(
                    "BackupManagerService",
                    UserBackupManagerService.addUserIdToLogMessage(
                            serviceForUserIfCallerHasPermission.mUserId,
                            "Unable to get management intent from transport: " + e.getMessage()));
            return null;
        }
    }

    public final CharSequence getDataManagementLabelForUser(int i, String str) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        CharSequence charSequence;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(
                                        i, "getDataManagementLabel()"))
                        == null) {
            return null;
        }
        serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                "android.permission.BACKUP", "getDataManagementLabel");
        try {
            TransportManager transportManager =
                    serviceForUserIfCallerHasPermission.mTransportManager;
            synchronized (transportManager.mTransportLock) {
                charSequence =
                        transportManager.getRegisteredTransportDescriptionOrThrowLocked(str)
                                .dataManagementLabel;
            }
            return charSequence;
        } catch (TransportNotRegisteredException e) {
            Slog.e(
                    "BackupManagerService",
                    UserBackupManagerService.addUserIdToLogMessage(
                            serviceForUserIfCallerHasPermission.mUserId,
                            "Unable to get management label from transport: " + e.getMessage()));
            return null;
        }
    }

    public final String getDestinationString(String str) {
        return getDestinationStringForUser(binderGetCallingUserId(), str);
    }

    public final String getDestinationStringForUser(int i, String str) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        String str2;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(i, "getDestinationString()"))
                        == null) {
            return null;
        }
        serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                "android.permission.BACKUP", "getDestinationString");
        try {
            TransportManager transportManager =
                    serviceForUserIfCallerHasPermission.mTransportManager;
            synchronized (transportManager.mTransportLock) {
                str2 =
                        transportManager.getRegisteredTransportDescriptionOrThrowLocked(str)
                                .currentDestinationString;
            }
            return str2;
        } catch (TransportNotRegisteredException e) {
            Slog.e(
                    "BackupManagerService",
                    UserBackupManagerService.addUserIdToLogMessage(
                            serviceForUserIfCallerHasPermission.mUserId,
                            "Unable to get destination string from transport: " + e.getMessage()));
            return null;
        }
    }

    public File getRememberActivatedFileForNonSystemUser(int i) {
        return new File(
                new File(
                        UserBackupManagerFiles.getBaseStateDir(0),
                        VibrationParam$1$$ExternalSyntheticOutline0.m(i, "")),
                "backup-remember-activated");
    }

    public UserBackupManagerService getServiceForUserIfCallerHasPermission(int i, String str) {
        if (binderGetCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.INTERACT_ACROSS_USERS_FULL", str);
        }
        UserBackupManagerService userBackupManagerService =
                (UserBackupManagerService) this.mUserServices.get(i);
        if (userBackupManagerService == null) {
            Slog.w("BackupManagerService", "Called " + str + " for unknown user: " + i);
        }
        return userBackupManagerService;
    }

    public File getSuppressFileForUser(int i) {
        return new File(UserBackupManagerFiles.getBaseStateDir(i), "backup-suppress");
    }

    public final String[] getTransportWhitelist() {
        if (!isUserReadyForBackup(binderGetCallingUserId())) {
            return null;
        }
        String[] strArr = new String[this.mTransportWhitelist.size()];
        Iterator it = this.mTransportWhitelist.iterator();
        int i = 0;
        while (it.hasNext()) {
            strArr[i] = ((ComponentName) it.next()).flattenToShortString();
            i++;
        }
        return strArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0072 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0077 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.UserHandle getUserForAncestralSerialNumber(long r11) {
        /*
            r10 = this;
            boolean r0 = r10.mGlobalDisable
            r1 = 0
            if (r0 == 0) goto L6
            return r1
        L6:
            android.os.UserHandle r0 = android.os.Binder.getCallingUserHandle()
            int r0 = r0.getIdentifier()
            long r2 = android.os.Binder.clearCallingIdentity()
            android.os.UserManager r4 = r10.getUserManager()     // Catch: java.lang.Throwable -> L7b
            r5 = 0
            int[] r0 = r4.getProfileIds(r0, r5)     // Catch: java.lang.Throwable -> L7b
            android.os.Binder.restoreCallingIdentity(r2)
            int r2 = r0.length
        L1f:
            if (r5 >= r2) goto L7a
            r3 = r0[r5]
            android.util.SparseArray r4 = r10.mUserServices
            java.lang.Object r4 = r4.get(r3)
            com.android.server.backup.UserBackupManagerService r4 = (com.android.server.backup.UserBackupManagerService) r4
            if (r4 == 0) goto L77
            java.io.RandomAccessFile r6 = new java.io.RandomAccessFile     // Catch: java.io.IOException -> L53 java.io.FileNotFoundException -> L6c
            java.io.File r7 = r4.mAncestralSerialNumberFile     // Catch: java.io.IOException -> L53 java.io.FileNotFoundException -> L6c
            if (r7 != 0) goto L43
            java.io.File r7 = new java.io.File     // Catch: java.io.IOException -> L53 java.io.FileNotFoundException -> L6c
            int r8 = r4.mUserId     // Catch: java.io.IOException -> L53 java.io.FileNotFoundException -> L6c
            java.io.File r8 = com.android.server.backup.UserBackupManagerFiles.getBaseStateDir(r8)     // Catch: java.io.IOException -> L53 java.io.FileNotFoundException -> L6c
            java.lang.String r9 = "serial_id"
            r7.<init>(r8, r9)     // Catch: java.io.IOException -> L53 java.io.FileNotFoundException -> L6c
            r4.mAncestralSerialNumberFile = r7     // Catch: java.io.IOException -> L53 java.io.FileNotFoundException -> L6c
        L43:
            java.io.File r7 = r4.mAncestralSerialNumberFile     // Catch: java.io.IOException -> L53 java.io.FileNotFoundException -> L6c
            java.lang.String r8 = "r"
            r6.<init>(r7, r8)     // Catch: java.io.IOException -> L53 java.io.FileNotFoundException -> L6c
            long r7 = r6.readLong()     // Catch: java.lang.Throwable -> L55
            r6.close()     // Catch: java.io.IOException -> L53 java.io.FileNotFoundException -> L6c
            goto L6e
        L53:
            r6 = move-exception
            goto L5f
        L55:
            r7 = move-exception
            r6.close()     // Catch: java.lang.Throwable -> L5a
            goto L5e
        L5a:
            r6 = move-exception
            r7.addSuppressed(r6)     // Catch: java.io.IOException -> L53 java.io.FileNotFoundException -> L6c
        L5e:
            throw r7     // Catch: java.io.IOException -> L53 java.io.FileNotFoundException -> L6c
        L5f:
            int r4 = r4.mUserId
            java.lang.String r7 = "Unable to read work profile serial number file:"
            java.lang.String r4 = com.android.server.backup.UserBackupManagerService.addUserIdToLogMessage(r4, r7)
            java.lang.String r7 = "BackupManagerService"
            android.util.Slog.w(r7, r4, r6)
        L6c:
            r7 = -1
        L6e:
            int r4 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r4 != 0) goto L77
            android.os.UserHandle r10 = android.os.UserHandle.of(r3)
            return r10
        L77:
            int r5 = r5 + 1
            goto L1f
        L7a:
            return r1
        L7b:
            r10 = move-exception
            android.os.Binder.restoreCallingIdentity(r2)
            throw r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.BackupManagerService.getUserForAncestralSerialNumber(long):android.os.UserHandle");
    }

    public UserManager getUserManager() {
        return this.mUserManager;
    }

    public SparseArray getUserServices() {
        return this.mUserServices;
    }

    public final boolean hasBackupPassword() {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(binderGetCallingUserId())
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(0, "hasBackupPassword()"))
                        == null) {
            return false;
        }
        BackupPasswordManager backupPasswordManager =
                serviceForUserIfCallerHasPermission.mBackupPasswordManager;
        backupPasswordManager.mContext.enforceCallingOrSelfPermission(
                "android.permission.BACKUP", "hasBackupPassword");
        String str = backupPasswordManager.mPasswordHash;
        return str != null && str.length() > 0;
    }

    public final void initializeTransportsForUser(
            int i, String[] strArr, IBackupObserver iBackupObserver) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(i, "initializeTransports()"))
                        == null) {
            return;
        }
        serviceForUserIfCallerHasPermission.initializeTransports(strArr, iBackupObserver);
    }

    public final boolean isAppEligibleForBackupForUser(int i, String str) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (isUserReadyForBackup(i)
                && (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(
                                        i, "isAppEligibleForBackup()"))
                        != null) {
            TransportManager transportManager =
                    serviceForUserIfCallerHasPermission.mTransportManager;
            serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                    "android.permission.BACKUP", "isAppEligibleForBackup");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                TransportConnection currentTransportClient =
                        transportManager.getCurrentTransportClient("BMS.isAppEligibleForBackup");
                boolean appIsRunningAndEligibleForBackupWithTransport =
                        serviceForUserIfCallerHasPermission.mScheduledBackupEligibility
                                .appIsRunningAndEligibleForBackupWithTransport(
                                        currentTransportClient, str);
                if (currentTransportClient != null) {
                    transportManager.mTransportConnectionManager.disposeOfTransportClient(
                            currentTransportClient, "BMS.isAppEligibleForBackup");
                }
                if (appIsRunningAndEligibleForBackupWithTransport) {
                    return true;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return false;
    }

    public final boolean isBackupActivatedForUser(int i) {
        if (getSuppressFileForUser(0).exists()) {
            return false;
        }
        boolean z = i == this.mDefaultBackupUserId;
        if (i == 0 && !z) {
            return false;
        }
        if (z && getSuppressFileForUser(i).exists()) {
            return false;
        }
        return z || getActivatedFileForUser(i).exists();
    }

    public boolean isBackupDisabled() {
        return SystemProperties.getBoolean("ro.backup.disable", false);
    }

    public final boolean isBackupEnabled() {
        return isBackupEnabledForUser(binderGetCallingUserId());
    }

    public final boolean isBackupEnabledForUser(int i) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (isUserReadyForBackup(i)
                && (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(i, "isBackupEnabled()"))
                        != null) {
            serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                    "android.permission.BACKUP", "isBackupEnabled");
            if (serviceForUserIfCallerHasPermission.mEnabled) {
                return true;
            }
        }
        return false;
    }

    public final boolean isBackupServiceActive(int i) {
        boolean z;
        if (CompatChanges.isChangeEnabled(158482162L, Binder.getCallingUid())) {
            this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.BACKUP", "isBackupServiceActive");
        }
        synchronized (this.mStateLock) {
            try {
                z = !this.mGlobalDisable && isBackupActivatedForUser(i);
            } finally {
            }
        }
        return z;
    }

    public final boolean isSubUserSupported() {
        return true;
    }

    public final boolean isUserReadyForBackup(int i) {
        if (binderGetCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.INTERACT_ACROSS_USERS_FULL", "isUserReadyForBackup()");
        }
        return this.mUserServices.get(i) != null;
    }

    public final ComponentName[] listAllTransportComponentsForUser(int i) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        ComponentName[] componentNameArr = null;
        if (isUserReadyForBackup(i)
                && (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(
                                        i, "listAllTransportComponents()"))
                        != null) {
            serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                    "android.permission.BACKUP", "listAllTransportComponents");
            TransportManager transportManager =
                    serviceForUserIfCallerHasPermission.mTransportManager;
            synchronized (transportManager.mTransportLock) {
                componentNameArr =
                        (ComponentName[])
                                ((ArrayMap) transportManager.mRegisteredTransportsDescriptionMap)
                                        .keySet()
                                        .toArray(
                                                new ComponentName
                                                        [((ArrayMap)
                                                                        transportManager
                                                                                .mRegisteredTransportsDescriptionMap)
                                                                .size()]);
            }
        }
        return componentNameArr;
    }

    public final String[] listAllTransports() {
        return listAllTransportsForUser(binderGetCallingUserId());
    }

    public final String[] listAllTransportsForUser(int i) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(i, "listAllTransports()"))
                        == null) {
            return null;
        }
        return serviceForUserIfCallerHasPermission.listAllTransports();
    }

    public final void opComplete(int i, long j) {
        opCompleteForUser(binderGetCallingUserId(), i, j);
    }

    public final void opCompleteForUser(int i, int i2, long j) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        Operation operation;
        BackupRestoreTask backupRestoreTask;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(i, "opComplete()"))
                        == null) {
            return;
        }
        LifecycleOperationStorage lifecycleOperationStorage =
                serviceForUserIfCallerHasPermission.mOperationStorage;
        synchronized (lifecycleOperationStorage.mOperationsLock) {
            try {
                operation = (Operation) lifecycleOperationStorage.mOperations.get(i2);
                if (operation != null) {
                    int i3 = operation.state;
                    if (i3 == -1) {
                        lifecycleOperationStorage.mOperations.remove(i2);
                    } else if (i3 == 1) {
                        Slog.w(
                                "LifecycleOperationStorage",
                                "[UserID:"
                                        + lifecycleOperationStorage.mUserId
                                        + "] Received duplicate ack for token="
                                        + Integer.toHexString(i2));
                        lifecycleOperationStorage.mOperations.remove(i2);
                    } else if (i3 == 0) {
                        operation.state = 1;
                    }
                    operation = null;
                }
                lifecycleOperationStorage.mOperationsLock.notifyAll();
            } catch (Throwable th) {
                throw th;
            }
        }
        if (operation == null || (backupRestoreTask = operation.callback) == null) {
            return;
        }
        Pair create = Pair.create(backupRestoreTask, Long.valueOf(j));
        BackupHandler backupHandler = serviceForUserIfCallerHasPermission.mBackupHandler;
        backupHandler.sendMessage(backupHandler.obtainMessage(21, create));
    }

    public void postToHandler(Runnable runnable) {
        this.mHandler.post(runnable);
    }

    public final void reportDelayedRestoreResult(String str, List list) {
        int identifier = Binder.getCallingUserHandle().getIdentifier();
        if (!isUserReadyForBackup(identifier)) {
            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                    identifier,
                    "Returning from reportDelayedRestoreResult as backup for user",
                    " is not initialized yet",
                    "BackupManagerService");
            return;
        }
        UserBackupManagerService serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(identifier, "reportDelayedRestoreResult()");
        if (serviceForUserIfCallerHasPermission != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                serviceForUserIfCallerHasPermission.reportDelayedRestoreResult(str, list);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final int requestBackup(
            int i,
            String[] strArr,
            IBackupObserver iBackupObserver,
            IBackupManagerMonitor iBackupManagerMonitor,
            int i2) {
        UserBackupManagerService serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(i, "requestBackup()");
        if (serviceForUserIfCallerHasPermission == null) {
            return -2001;
        }
        BackupManagerMonitorEventSender bMMEventSender =
                serviceForUserIfCallerHasPermission.getBMMEventSender(iBackupManagerMonitor);
        serviceForUserIfCallerHasPermission.mContext.enforceCallingPermission(
                "android.permission.BACKUP", "requestBackup");
        if (strArr == null || strArr.length < 1) {
            Slog.e(
                    "BackupManagerService",
                    UserBackupManagerService.addUserIdToLogMessage(
                            serviceForUserIfCallerHasPermission.mUserId,
                            "No packages named for backup request"));
            BackupObserverUtils.sendBackupFinished(iBackupObserver, -1000);
            bMMEventSender.monitorEvent(49, null, 1, null);
            throw new IllegalArgumentException("No packages are provided for backup");
        }
        if (!serviceForUserIfCallerHasPermission.mEnabled
                || !serviceForUserIfCallerHasPermission.mSetupComplete) {
            Slog.i(
                    "BackupManagerService",
                    UserBackupManagerService.addUserIdToLogMessage(
                            serviceForUserIfCallerHasPermission.mUserId,
                            "Backup requested but enabled="
                                    + serviceForUserIfCallerHasPermission.mEnabled
                                    + " setupComplete="
                                    + serviceForUserIfCallerHasPermission.mSetupComplete));
            BackupObserverUtils.sendBackupFinished(iBackupObserver, -2001);
            bMMEventSender.monitorEvent(
                    serviceForUserIfCallerHasPermission.mSetupComplete ? 13 : 14, null, 3, null);
            return -2001;
        }
        try {
            TransportManager transportManager =
                    serviceForUserIfCallerHasPermission.mTransportManager;
            String transportDirName =
                    transportManager.getTransportDirName(transportManager.mCurrentTransportName);
            TransportConnection currentTransportClientOrThrow =
                    serviceForUserIfCallerHasPermission.mTransportManager
                            .getCurrentTransportClientOrThrow();
            int backupDestinationFromTransport =
                    serviceForUserIfCallerHasPermission.getBackupDestinationFromTransport(
                            currentTransportClientOrThrow);
            UserBackupManagerService$$ExternalSyntheticLambda0
                    userBackupManagerService$$ExternalSyntheticLambda0 =
                            new UserBackupManagerService$$ExternalSyntheticLambda0(
                                    serviceForUserIfCallerHasPermission,
                                    currentTransportClientOrThrow,
                                    1);
            BackupEligibilityRules eligibilityRulesForOperation =
                    serviceForUserIfCallerHasPermission.getEligibilityRulesForOperation(
                            backupDestinationFromTransport);
            Message obtainMessage =
                    serviceForUserIfCallerHasPermission.mBackupHandler.obtainMessage(15);
            obtainMessage.obj =
                    serviceForUserIfCallerHasPermission.getRequestBackupParams(
                            strArr,
                            iBackupObserver,
                            iBackupManagerMonitor,
                            i2,
                            eligibilityRulesForOperation,
                            currentTransportClientOrThrow,
                            transportDirName,
                            userBackupManagerService$$ExternalSyntheticLambda0);
            serviceForUserIfCallerHasPermission.mBackupHandler.sendMessage(obtainMessage);
            return 0;
        } catch (RemoteException
                | TransportNotAvailableException
                | TransportNotRegisteredException unused) {
            BackupObserverUtils.sendBackupFinished(iBackupObserver, -1000);
            bMMEventSender.monitorEvent(50, null, 1, null);
            return -1000;
        }
    }

    public final int requestBackup(
            String[] strArr,
            IBackupObserver iBackupObserver,
            IBackupManagerMonitor iBackupManagerMonitor,
            int i) {
        return requestBackup(
                binderGetCallingUserId(), strArr, iBackupObserver, iBackupManagerMonitor, i);
    }

    public final int requestBackupForUser(
            int i,
            String[] strArr,
            IBackupObserver iBackupObserver,
            IBackupManagerMonitor iBackupManagerMonitor,
            int i2) {
        if (isUserReadyForBackup(i)) {
            return requestBackup(i, strArr, iBackupObserver, iBackupManagerMonitor, i2);
        }
        return -2001;
    }

    public final void restoreAtInstall(String str, int i) {
        restoreAtInstallForUser(binderGetCallingUserId(), str, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void restoreAtInstallForUser(int r28, java.lang.String r29, int r30) {
        /*
            Method dump skipped, instructions count: 442
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.BackupManagerService.restoreAtInstallForUser(int,"
                    + " java.lang.String, int):void");
    }

    public final String selectBackupTransport(String str) {
        return selectBackupTransportForUser(binderGetCallingUserId(), str);
    }

    public final void selectBackupTransportAsyncForUser(
            int i,
            ComponentName componentName,
            ISelectBackupTransportCallback iSelectBackupTransportCallback) {
        if (!isUserReadyForBackup(i)) {
            if (iSelectBackupTransportCallback != null) {
                try {
                    iSelectBackupTransportCallback.onFailure(-2001);
                    return;
                } catch (RemoteException unused) {
                    return;
                }
            }
            return;
        }
        UserBackupManagerService serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(i, "selectBackupTransportAsync()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                    "android.permission.BACKUP", "selectBackupTransportAsync");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                String flattenToShortString = componentName.flattenToShortString();
                Slog.v(
                        "BackupManagerService",
                        UserBackupManagerService.addUserIdToLogMessage(
                                serviceForUserIfCallerHasPermission.mUserId,
                                "selectBackupTransportAsync(transport = "
                                        + flattenToShortString
                                        + ")"));
                serviceForUserIfCallerHasPermission.mBackupHandler.post(
                        new UserBackupManagerService$$ExternalSyntheticLambda6(
                                serviceForUserIfCallerHasPermission,
                                componentName,
                                iSelectBackupTransportCallback));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final String selectBackupTransportForUser(int i, String str) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        String str2 = null;
        if (isUserReadyForBackup(i)
                && (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(
                                        i, "selectBackupTransport()"))
                        != null) {
            TransportManager transportManager =
                    serviceForUserIfCallerHasPermission.mTransportManager;
            serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                    "android.permission.BACKUP", "selectBackupTransport");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                boolean isTransportRegistered = transportManager.isTransportRegistered(str);
                int i2 = serviceForUserIfCallerHasPermission.mUserId;
                if (isTransportRegistered) {
                    str2 = transportManager.selectTransport(str);
                    serviceForUserIfCallerHasPermission.updateStateForTransport(str);
                    Slog.v(
                            "BackupManagerService",
                            UserBackupManagerService.addUserIdToLogMessage(
                                    i2,
                                    "selectBackupTransport(transport = "
                                            + str
                                            + "): previous transport = "
                                            + str2));
                } else {
                    Slog.v(
                            "BackupManagerService",
                            UserBackupManagerService.addUserIdToLogMessage(
                                    i2,
                                    "Could not select transport "
                                            + str
                                            + ", as the transport is not registered."));
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return str2;
    }

    public final Map semBackupPackage(
            ParcelFileDescriptor parcelFileDescriptor, String[] strArr, String str, int i) {
        UserBackupManagerService serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(
                        binderGetCallingUserId(), "semBackupPackage()");
        if (serviceForUserIfCallerHasPermission == null) {
            return null;
        }
        if (strArr == null || strArr.length <= 0 || parcelFileDescriptor == null) {
            throw new IllegalArgumentException("packageName is not available");
        }
        serviceForUserIfCallerHasPermission.mSepTimeOut = true;
        synchronized (serviceForUserIfCallerHasPermission.mBackupRestoreLock) {
            boolean z = (i & 1) != 0;
            boolean z2 = (i & 2) != 0;
            boolean z3 = (i & 4) != 0;
            boolean z4 = (i & 8) != 0;
            boolean z5 = (i & 16) != 0;
            boolean z6 = (i & 32) != 0;
            boolean z7 = (i & 64) != 0;
            boolean z8 = (i & 128) != 0;
            boolean z9 = (i & 256) != 0;
            boolean z10 = (i & 2048) != 0;
            try {
                if (!z9) {
                    serviceForUserIfCallerHasPermission.mEncPassword = str;
                    serviceForUserIfCallerHasPermission.mExtraFlag = i;
                    if (z8) {
                        z2 = true;
                    }
                    serviceForUserIfCallerHasPermission.adbBackup(
                            parcelFileDescriptor, z, z2, z3, z4, z5, z6, z7, z10, strArr, false);
                }
            } finally {
            }
        }
        return null;
    }

    public final Map semBackupPackagePath(
            ParcelFileDescriptor parcelFileDescriptor,
            String[] strArr,
            String str,
            int i,
            String[] strArr2) {
        UserBackupManagerService serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(
                        binderGetCallingUserId(), "semBackupPackagePath()");
        if (serviceForUserIfCallerHasPermission == null) {
            return null;
        }
        if (strArr == null || strArr.length <= 0 || parcelFileDescriptor == null) {
            throw new IllegalArgumentException("packageName is not available");
        }
        serviceForUserIfCallerHasPermission.mSepTimeOut = true;
        boolean equalsIgnoreCase =
                "CHINA"
                        .equalsIgnoreCase(
                                SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY));
        if (strArr2 != null && equalsIgnoreCase) {
            serviceForUserIfCallerHasPermission.mSmartSwitchBackupPath = strArr2;
            for (String str2 : strArr2) {
                Log.i("BackupManagerService", "mSmartSwitchBackupPath = " + str2);
            }
        }
        synchronized (serviceForUserIfCallerHasPermission.mBackupRestoreLock) {
            boolean z = (i & 1) != 0;
            boolean z2 = (i & 2) != 0;
            boolean z3 = (i & 4) != 0;
            boolean z4 = (i & 8) != 0;
            boolean z5 = (i & 16) != 0;
            boolean z6 = (i & 32) != 0;
            boolean z7 = (i & 64) != 0;
            boolean z8 = (i & 128) != 0;
            boolean z9 = (i & 256) != 0;
            boolean z10 = (i & 2048) != 0;
            try {
                if (!z9) {
                    serviceForUserIfCallerHasPermission.mEncPassword = str;
                    serviceForUserIfCallerHasPermission.mExtraFlag = i;
                    serviceForUserIfCallerHasPermission.adbBackup(
                            parcelFileDescriptor,
                            z,
                            z8 ? true : z2,
                            z3,
                            z4,
                            z5,
                            z6,
                            z7,
                            z10,
                            strArr,
                            false);
                }
            } finally {
            }
        }
        return null;
    }

    public final boolean semCancelBackupAndRestore() {
        Slog.i("BackupManagerService", "semCancelBackupAndRestore Start");
        UserBackupManagerService serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(
                        binderGetCallingUserId(), "semCancelBackupAndRestore()");
        if (serviceForUserIfCallerHasPermission == null) {
            Slog.e("BackupManagerService", "Fail - UserBackupManagerService null");
            return false;
        }
        AdbBackupParams adbBackupParams = serviceForUserIfCallerHasPermission.mAdbBackupParams;
        if (adbBackupParams == null
                && serviceForUserIfCallerHasPermission.mAdbRestoreParams == null) {
            Slog.w("BackupManagerService", "all of adbParams null");
        } else {
            if (adbBackupParams != null) {
                Slog.i("BackupManagerService", "BackupParams latch");
                synchronized (serviceForUserIfCallerHasPermission.mAdbBackupParams.latch) {
                    serviceForUserIfCallerHasPermission.mAdbBackupParams.latch.set(true);
                    serviceForUserIfCallerHasPermission.mAdbBackupParams.latch.notifyAll();
                }
            }
            if (serviceForUserIfCallerHasPermission.mAdbRestoreParams != null) {
                Slog.i("BackupManagerService", "RestoreParams latch");
                synchronized (serviceForUserIfCallerHasPermission.mAdbRestoreParams.latch) {
                    serviceForUserIfCallerHasPermission.mAdbRestoreParams.latch.set(true);
                    serviceForUserIfCallerHasPermission.mAdbRestoreParams.latch.notifyAll();
                }
            }
        }
        return true;
    }

    public final boolean semDisableDataExtractionRule(boolean z) {
        Slog.i("BackupManagerService", "semDisableDataExtractionRule Start, set  = " + z);
        UserBackupManagerService serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(
                        binderGetCallingUserId(), "semDisableDataExtractionRule()");
        if (serviceForUserIfCallerHasPermission == null) {
            Slog.e("BackupManagerService", "Fail - UserBackupManagerService null");
            return false;
        }
        if ("CHINA"
                .equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
            serviceForUserIfCallerHasPermission.mDisableDataExtractionRule = z;
            return true;
        }
        serviceForUserIfCallerHasPermission.mDisableDataExtractionRule = false;
        return false;
    }

    public final boolean semIsBackupEnabled() {
        int binderGetCallingUserId = binderGetCallingUserId();
        UserBackupManagerService serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(
                        binderGetCallingUserId, "semIsBackupEnabled()");
        if (!isUserReadyForBackup(binderGetCallingUserId)
                || serviceForUserIfCallerHasPermission == null) {
            return false;
        }
        serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                "android.permission.BACKUP", "semIsBackupEnabled");
        return serviceForUserIfCallerHasPermission.mEnabled;
    }

    public final void semRestorePackage(ParcelFileDescriptor parcelFileDescriptor, String str) {
        UserBackupManagerService serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(
                        binderGetCallingUserId(), "semRestorePackage()");
        if (serviceForUserIfCallerHasPermission != null) {
            if (parcelFileDescriptor == null) {
                throw new IllegalArgumentException("fd is null");
            }
            serviceForUserIfCallerHasPermission.mSepTimeOut = true;
            synchronized (serviceForUserIfCallerHasPermission.mBackupRestoreLock) {
                serviceForUserIfCallerHasPermission.mEncPassword = str;
                serviceForUserIfCallerHasPermission.adbRestore(parcelFileDescriptor, false);
            }
        }
    }

    public final void semSetAutoRestoreEnabled(boolean z) {
        int binderGetCallingUserId = binderGetCallingUserId();
        UserBackupManagerService serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(
                        binderGetCallingUserId, "semSetAutoRestoreEnabled()");
        if (isUserReadyForBackup(binderGetCallingUserId)
                && serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                    "android.permission.BACKUP", "setAutoRestoreEnabled");
            Slog.i(
                    "BackupManagerService",
                    UserBackupManagerService.addUserIdToLogMessage(
                            serviceForUserIfCallerHasPermission.mUserId, "Auto restore => " + z));
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (serviceForUserIfCallerHasPermission) {
                    Settings.Secure.putIntForUser(
                            serviceForUserIfCallerHasPermission.mContext.getContentResolver(),
                            "backup_auto_restore",
                            z ? 1 : 0,
                            serviceForUserIfCallerHasPermission.mUserId);
                    serviceForUserIfCallerHasPermission.mAutoRestore = z;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void semSetBackupEnabled(boolean z) {
        int binderGetCallingUserId = binderGetCallingUserId();
        UserBackupManagerService serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(
                        binderGetCallingUserId, "semSetBackupEnabled()");
        if (isUserReadyForBackup(binderGetCallingUserId)
                && serviceForUserIfCallerHasPermission != null) {
            EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance();
            if (z && !enterpriseDeviceManager.getRestrictionPolicy().isBackupAllowed(true)) {
                Slog.w("BackupManagerService", "Backup is not allowed by MDM");
                return;
            }
            serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                    "android.permission.BACKUP", "setBackupEnabled");
            Slog.i(
                    "BackupManagerService",
                    UserBackupManagerService.addUserIdToLogMessage(
                            serviceForUserIfCallerHasPermission.mUserId, "Backup enabled => " + z));
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                boolean z2 = serviceForUserIfCallerHasPermission.mEnabled;
                synchronized (serviceForUserIfCallerHasPermission) {
                    UserBackupManagerFilePersistedSettings.writeBackupEnableState(
                            serviceForUserIfCallerHasPermission.mUserId, z);
                    serviceForUserIfCallerHasPermission.mEnabled = z;
                }
                synchronized (serviceForUserIfCallerHasPermission.mQueueLock) {
                    if (z && !z2) {
                        try {
                            if (serviceForUserIfCallerHasPermission.mSetupComplete) {
                                KeyValueBackupJob.schedule(
                                        serviceForUserIfCallerHasPermission.mUserId,
                                        serviceForUserIfCallerHasPermission.mContext,
                                        serviceForUserIfCallerHasPermission);
                                serviceForUserIfCallerHasPermission.scheduleNextFullBackupJob(0L);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    if (!z) {
                        KeyValueBackupJob.cancel(
                                serviceForUserIfCallerHasPermission.mContext,
                                serviceForUserIfCallerHasPermission.mUserId);
                        if (z2 && serviceForUserIfCallerHasPermission.mSetupComplete) {
                            ArrayList arrayList = new ArrayList();
                            ArrayList arrayList2 = new ArrayList();
                            serviceForUserIfCallerHasPermission.mTransportManager
                                    .forEachRegisteredTransport(
                                            new UserBackupManagerService$$ExternalSyntheticLambda7(
                                                    serviceForUserIfCallerHasPermission,
                                                    arrayList,
                                                    arrayList2,
                                                    0));
                            for (int i = 0; i < arrayList.size(); i++) {
                                serviceForUserIfCallerHasPermission.recordInitPending(
                                        (String) arrayList.get(i),
                                        (String) arrayList2.get(i),
                                        true);
                            }
                            serviceForUserIfCallerHasPermission.mAlarmManager.set(
                                    0,
                                    System.currentTimeMillis(),
                                    serviceForUserIfCallerHasPermission.mRunInitIntent);
                        }
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final boolean semSetTimeoutBackupAndRestore(int i) {
        Slog.i("BackupManagerService", "semSetTimeoutBackupAndRestore Start");
        UserBackupManagerService serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(
                        binderGetCallingUserId(), "semSetTimeoutBackupAndRestore()");
        boolean z = false;
        if (serviceForUserIfCallerHasPermission == null) {
            Slog.e("BackupManagerService", "Fail - UserBackupManagerService null");
            return false;
        }
        if (i >= 0 && i <= 300) {
            serviceForUserIfCallerHasPermission.mSepTimeoutMin = i;
            z = true;
        }
        Slog.i(
                "BackupManagerService",
                "semSetTimeoutBackupAndRestore, timeout(min) : "
                        + serviceForUserIfCallerHasPermission.mSepTimeoutMin
                        + ", "
                        + z);
        return z;
    }

    public final boolean semSetTransportFlagsForAdbBackup(int i) {
        Slog.i("BackupManagerService", "semSetTransportFlagsForAdbBackup Start");
        UserBackupManagerService serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(
                        binderGetCallingUserId(), "semSetTransportFlagsForAdbBackup()");
        if (serviceForUserIfCallerHasPermission == null) {
            Slog.e("BackupManagerService", "Fail - UserBackupManagerService null");
            return false;
        }
        serviceForUserIfCallerHasPermission.mTransportFlagsForAdbBackup = i;
        SystemServiceManager$$ExternalSyntheticOutline0.m(
                new StringBuilder("semSetTransportFlagsForAdbBackup, set flags : "),
                serviceForUserIfCallerHasPermission.mTransportFlagsForAdbBackup,
                "BackupManagerService");
        return true;
    }

    public final void setAncestralSerialNumber(long j) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (this.mGlobalDisable
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(
                                        Binder.getCallingUserHandle().getIdentifier(),
                                        "setAncestralSerialNumber()"))
                        == null) {
            return;
        }
        serviceForUserIfCallerHasPermission.mContext.enforceCallingPermission(
                "android.permission.BACKUP", "setAncestralSerialNumber");
        int i = serviceForUserIfCallerHasPermission.mUserId;
        Slog.v(
                "BackupManagerService",
                UserBackupManagerService.addUserIdToLogMessage(
                        i, "Setting ancestral work profile id to " + j));
        try {
            if (serviceForUserIfCallerHasPermission.mAncestralSerialNumberFile == null) {
                serviceForUserIfCallerHasPermission.mAncestralSerialNumberFile =
                        new File(UserBackupManagerFiles.getBaseStateDir(i), "serial_id");
            }
            RandomAccessFile randomAccessFile =
                    new RandomAccessFile(
                            serviceForUserIfCallerHasPermission.mAncestralSerialNumberFile, "rwd");
            try {
                randomAccessFile.writeLong(j);
                randomAccessFile.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.w(
                    "BackupManagerService",
                    UserBackupManagerService.addUserIdToLogMessage(
                            i, "Unable to write to work profile serial mapping file:"),
                    e);
        }
    }

    public final void setAutoRestore(boolean z) {
        setAutoRestoreForUser(binderGetCallingUserId(), z);
    }

    public final void setAutoRestoreForUser(int i, boolean z) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(i, "setAutoRestore()"))
                        == null) {
            return;
        }
        serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                "android.permission.BACKUP", "setAutoRestore");
        Slog.i(
                "BackupManagerService",
                UserBackupManagerService.addUserIdToLogMessage(
                        serviceForUserIfCallerHasPermission.mUserId, "Auto restore => " + z));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (serviceForUserIfCallerHasPermission) {
                Settings.Secure.putIntForUser(
                        serviceForUserIfCallerHasPermission.mContext.getContentResolver(),
                        "backup_auto_restore",
                        z ? 1 : 0,
                        serviceForUserIfCallerHasPermission.mUserId);
                serviceForUserIfCallerHasPermission.mAutoRestore = z;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setBackupEnabled(boolean z) {
        setBackupEnabledForUser(binderGetCallingUserId(), z);
    }

    public final void setBackupEnabledForUser(int i, boolean z) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(i, "setBackupEnabled()"))
                        == null) {
            return;
        }
        serviceForUserIfCallerHasPermission.setBackupEnabled(z, true);
    }

    public final boolean setBackupPassword(String str, String str2) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(binderGetCallingUserId())
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(0, "setBackupPassword()"))
                        == null) {
            return false;
        }
        BackupPasswordManager backupPasswordManager =
                serviceForUserIfCallerHasPermission.mBackupPasswordManager;
        backupPasswordManager.mContext.enforceCallingOrSelfPermission(
                "android.permission.BACKUP", "setBackupPassword");
        if (!backupPasswordManager.passwordMatchesSaved("PBKDF2WithHmacSHA1", str)
                && (backupPasswordManager.mPasswordVersion >= 2
                        || !backupPasswordManager.passwordMatchesSaved(
                                "PBKDF2WithHmacSHA1And8bit", str))) {
            return false;
        }
        try {
            new DataStreamFileCodec(
                            new File(backupPasswordManager.mBaseStateDir, "pwversion"),
                            new BackupPasswordManager.PasswordHashFileCodec(1))
                    .serialize(2);
            backupPasswordManager.mPasswordVersion = 2;
            if (str2 == null || str2.isEmpty()) {
                File file = new File(backupPasswordManager.mBaseStateDir, "pwhash");
                if (file.exists() && !file.delete()) {
                    Slog.e("BackupPasswordManager", "Unable to clear backup password");
                    return false;
                }
                backupPasswordManager.mPasswordHash = null;
                backupPasswordManager.mPasswordSalt = null;
            } else {
                try {
                    byte[] bArr = new byte[64];
                    backupPasswordManager.mRng.nextBytes(bArr);
                    SecretKey buildCharArrayKey =
                            PasswordUtils.buildCharArrayKey(
                                    "PBKDF2WithHmacSHA1", str2.toCharArray(), bArr, 10000);
                    String encodeToString =
                            buildCharArrayKey != null
                                    ? HexEncoding.encodeToString(
                                            buildCharArrayKey.getEncoded(), true)
                                    : null;
                    new DataStreamFileCodec(
                                    new File(backupPasswordManager.mBaseStateDir, "pwhash"),
                                    new BackupPasswordManager.PasswordHashFileCodec(0))
                            .serialize(
                                    new BackupPasswordManager.BackupPasswordHash(
                                            encodeToString, bArr));
                    backupPasswordManager.mPasswordHash = encodeToString;
                    backupPasswordManager.mPasswordSalt = bArr;
                } catch (IOException unused) {
                    Slog.e("BackupPasswordManager", "Unable to set backup password");
                    return false;
                }
            }
            return true;
        } catch (IOException unused2) {
            Slog.e(
                    "BackupPasswordManager",
                    "Unable to write backup pw version; password not changed");
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00a4 A[Catch: all -> 0x0065, TRY_LEAVE, TryCatch #0 {all -> 0x0065, blocks: (B:15:0x0059, B:18:0x006b, B:38:0x007e, B:41:0x0083, B:23:0x009a, B:25:0x00a4, B:28:0x00ab, B:31:0x00b0, B:32:0x00b3, B:33:0x00db, B:21:0x008b, B:36:0x0093, B:49:0x00b6, B:52:0x00bb, B:45:0x00d2, B:43:0x00c3, B:47:0x00cb, B:27:0x00a8), top: B:14:0x0059, inners: #1, #3, #4 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setBackupServiceActive(int r5, boolean r6) {
        /*
            r4 = this;
            java.lang.String r0 = "Making backup "
            java.lang.String r1 = "No permission to configure backup activity"
            if (r5 == 0) goto L24
            android.os.UserManager r2 = r4.getUserManager()
            android.content.pm.UserInfo r2 = r2.getUserInfo(r5)
            boolean r2 = r2.isManagedProfile()
            if (r2 == 0) goto L15
            goto L24
        L15:
            android.content.Context r2 = r4.mContext
            java.lang.String r3 = "android.permission.BACKUP"
            r2.enforceCallingOrSelfPermission(r3, r1)
            android.content.Context r2 = r4.mContext
            java.lang.String r3 = "android.permission.INTERACT_ACROSS_USERS_FULL"
            r2.enforceCallingOrSelfPermission(r3, r1)
            goto L35
        L24:
            int r2 = r4.binderGetCallingUid()
            r3 = 1000(0x3e8, float:1.401E-42)
            if (r2 == r3) goto L35
            if (r2 != 0) goto L2f
            goto L35
        L2f:
            java.lang.SecurityException r4 = new java.lang.SecurityException
            r4.<init>(r1)
            throw r4
        L35:
            if (r5 == 0) goto L4a
            java.io.File r1 = r4.getRememberActivatedFileForNonSystemUser(r5)     // Catch: java.io.IOException -> L42
            createFile(r1)     // Catch: java.io.IOException -> L42
            com.android.server.backup.utils.RandomAccessFileUtils.writeBoolean(r1, r6)     // Catch: java.io.IOException -> L42
            goto L4a
        L42:
            r1 = move-exception
            java.lang.String r2 = "BackupManagerService"
            java.lang.String r3 = "Unable to persist backup service activity"
            android.util.Slog.e(r2, r3, r1)
        L4a:
            boolean r1 = r4.mGlobalDisable
            if (r1 == 0) goto L56
            java.lang.String r4 = "BackupManagerService"
            java.lang.String r5 = "Backup service not supported"
            android.util.Slog.i(r4, r5)
            return
        L56:
            java.lang.Object r1 = r4.mStateLock
            monitor-enter(r1)
            java.lang.String r2 = "BackupManagerService"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L65
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L65
            if (r6 == 0) goto L68
            java.lang.String r0 = ""
            goto L6b
        L65:
            r4 = move-exception
            goto Ldd
        L68:
            java.lang.String r0 = "in"
        L6b:
            r3.append(r0)     // Catch: java.lang.Throwable -> L65
            java.lang.String r0 = "active"
            r3.append(r0)     // Catch: java.lang.Throwable -> L65
            java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> L65
            android.util.Slog.i(r2, r0)     // Catch: java.lang.Throwable -> L65
            if (r6 == 0) goto Lb4
            if (r5 == 0) goto L8b
            int r6 = r4.mDefaultBackupUserId     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L93
            if (r5 != r6) goto L83
            goto L8b
        L83:
            java.io.File r6 = r4.getActivatedFileForUser(r5)     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L93
            createFile(r6)     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L93
            goto L9a
        L8b:
            java.io.File r6 = r4.getSuppressFileForUser(r5)     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L93
            deleteFile(r6)     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L93
            goto L9a
        L93:
            java.lang.String r6 = "BackupManagerService"
            java.lang.String r0 = "Unable to persist backup service activity"
            android.util.Slog.e(r6, r0)     // Catch: java.lang.Throwable -> L65
        L9a:
            android.os.UserManager r6 = r4.getUserManager()     // Catch: java.lang.Throwable -> L65
            boolean r6 = r6.isUserUnlocked(r5)     // Catch: java.lang.Throwable -> L65
            if (r6 == 0) goto Ldb
            long r2 = android.os.Binder.clearCallingIdentity()     // Catch: java.lang.Throwable -> L65
            r4.startServiceForUser(r5)     // Catch: java.lang.Throwable -> Laf
            android.os.Binder.restoreCallingIdentity(r2)     // Catch: java.lang.Throwable -> L65
            goto Ldb
        Laf:
            r4 = move-exception
            android.os.Binder.restoreCallingIdentity(r2)     // Catch: java.lang.Throwable -> L65
            throw r4     // Catch: java.lang.Throwable -> L65
        Lb4:
            if (r5 == 0) goto Lc3
            int r6 = r4.mDefaultBackupUserId     // Catch: java.lang.Throwable -> L65 java.io.IOException -> Lcb
            if (r5 != r6) goto Lbb
            goto Lc3
        Lbb:
            java.io.File r6 = r4.getActivatedFileForUser(r5)     // Catch: java.lang.Throwable -> L65 java.io.IOException -> Lcb
            deleteFile(r6)     // Catch: java.lang.Throwable -> L65 java.io.IOException -> Lcb
            goto Ld2
        Lc3:
            java.io.File r6 = r4.getSuppressFileForUser(r5)     // Catch: java.lang.Throwable -> L65 java.io.IOException -> Lcb
            createFile(r6)     // Catch: java.lang.Throwable -> L65 java.io.IOException -> Lcb
            goto Ld2
        Lcb:
            java.lang.String r6 = "BackupManagerService"
            java.lang.String r0 = "Unable to persist backup service inactivity"
            android.util.Slog.e(r6, r0)     // Catch: java.lang.Throwable -> L65
        Ld2:
            com.android.server.backup.BackupManagerService$$ExternalSyntheticLambda0 r6 = new com.android.server.backup.BackupManagerService$$ExternalSyntheticLambda0     // Catch: java.lang.Throwable -> L65
            r0 = 0
            r6.<init>(r5, r0, r4)     // Catch: java.lang.Throwable -> L65
            r4.postToHandler(r6)     // Catch: java.lang.Throwable -> L65
        Ldb:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L65
            return
        Ldd:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L65
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.BackupManagerService.setBackupServiceActive(int,"
                    + " boolean):void");
    }

    public final void setFrameworkSchedulingEnabledForUser(int i, boolean z) {
        UserBackupManagerService serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(i, "setFrameworkSchedulingEnabledForUser()");
        if (serviceForUserIfCallerHasPermission != null) {
            synchronized (serviceForUserIfCallerHasPermission) {
                try {
                    serviceForUserIfCallerHasPermission.mContext.enforceCallingOrSelfPermission(
                            "android.permission.BACKUP", "setFrameworkSchedulingEnabled");
                    if (serviceForUserIfCallerHasPermission.isFrameworkSchedulingEnabled() == z) {
                        return;
                    }
                    int i2 = serviceForUserIfCallerHasPermission.mUserId;
                    StringBuilder sb = new StringBuilder();
                    sb.append(z ? "Enabling" : "Disabling");
                    sb.append(" backup scheduling");
                    Slog.i(
                            "BackupManagerService",
                            UserBackupManagerService.addUserIdToLogMessage(i2, sb.toString()));
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        Settings.Secure.putIntForUser(
                                serviceForUserIfCallerHasPermission.mContext.getContentResolver(),
                                "backup_scheduling_enabled",
                                z ? 1 : 0,
                                serviceForUserIfCallerHasPermission.mUserId);
                        if (z) {
                            KeyValueBackupJob.schedule(
                                    serviceForUserIfCallerHasPermission.mUserId,
                                    serviceForUserIfCallerHasPermission.mContext,
                                    serviceForUserIfCallerHasPermission);
                            serviceForUserIfCallerHasPermission.scheduleNextFullBackupJob(0L);
                        } else {
                            KeyValueBackupJob.cancel(
                                    serviceForUserIfCallerHasPermission.mContext,
                                    serviceForUserIfCallerHasPermission.mUserId);
                            int i3 = serviceForUserIfCallerHasPermission.mUserId;
                            Context context = serviceForUserIfCallerHasPermission.mContext;
                            int i4 = FullBackupJob.MIN_JOB_ID;
                            ((JobScheduler) context.getSystemService("jobscheduler"))
                                    .cancel(FullBackupJob.getJobIdForUserId(i3));
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }
    }

    public void startServiceForUser(int i) {
        if (this.mGlobalDisable) {
            Slog.i("BackupManagerService", "Backup service not supported");
            return;
        }
        if (!isBackupActivatedForUser(i)) {
            HermesService$3$$ExternalSyntheticOutline0.m(
                    i, "Backup not activated for user ", "BackupManagerService");
            return;
        }
        if (this.mUserServices.get(i) != null) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    i,
                    "userId ",
                    " already started, so not starting again",
                    "BackupManagerService");
            return;
        }
        HermesService$3$$ExternalSyntheticOutline0.m(
                i, "Starting service for user: ", "BackupManagerService");
        Context context = this.mContext;
        Set set = this.mTransportWhitelist;
        String stringForUser =
                Settings.Secure.getStringForUser(
                        context.getContentResolver(), "backup_transport", i);
        if (TextUtils.isEmpty(stringForUser)) {
            stringForUser = null;
        }
        Slog.v(
                "BackupManagerService",
                UserBackupManagerService.addUserIdToLogMessage(
                        i, "Starting with transport " + stringForUser));
        TransportManager transportManager = new TransportManager(i, context, set, stringForUser);
        File baseStateDir = UserBackupManagerFiles.getBaseStateDir(i);
        File file =
                i != 0
                        ? new File(Environment.getDataSystemCeDirectory(i), "backup_stage")
                        : new File(Environment.getDownloadCacheDirectory(), "backup_stage");
        HandlerThread handlerThread =
                new HandlerThread(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "backup-"), 10);
        handlerThread.start();
        Slog.d(
                "BackupManagerService",
                UserBackupManagerService.addUserIdToLogMessage(
                        i, "Started thread " + handlerThread.getName()));
        startServiceForUser(
                i,
                UserBackupManagerService.createAndInitializeService(
                        i, context, this, handlerThread, baseStateDir, file, transportManager));
    }

    public void startServiceForUser(int i, UserBackupManagerService userBackupManagerService) {
        this.mUserServices.put(i, userBackupManagerService);
        Trace.traceBegin(64L, "backup enable");
        userBackupManagerService.initializeBackupEnableState();
        Trace.traceEnd(64L);
    }

    public void stopServiceForUser(int i) {
        UserBackupManagerService userBackupManagerService =
                (UserBackupManagerService) this.mUserServices.removeReturnOld(i);
        if (userBackupManagerService != null) {
            userBackupManagerService.tearDownService();
            KeyValueBackupJob.cancel(this.mContext, i);
            Context context = this.mContext;
            int i2 = FullBackupJob.MIN_JOB_ID;
            ((JobScheduler) context.getSystemService("jobscheduler"))
                    .cancel(FullBackupJob.getJobIdForUserId(i));
        }
    }

    public final void updateTransportAttributesForUser(
            int i,
            ComponentName componentName,
            String str,
            Intent intent,
            String str2,
            Intent intent2,
            CharSequence charSequence) {
        UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!isUserReadyForBackup(i)
                || (serviceForUserIfCallerHasPermission =
                                getServiceForUserIfCallerHasPermission(
                                        i, "updateTransportAttributes()"))
                        == null) {
            return;
        }
        serviceForUserIfCallerHasPermission.updateTransportAttributes(
                Binder.getCallingUid(), componentName, str, intent, str2, intent2, charSequence);
    }
}

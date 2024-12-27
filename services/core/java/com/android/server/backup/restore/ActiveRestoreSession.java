package com.android.server.backup.restore;

import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IRestoreObserver;
import android.app.backup.IRestoreSession;
import android.app.backup.RestoreSet;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.os.Binder;
import android.os.Message;
import android.util.Slog;

import com.android.server.LocalServices;
import com.android.server.backup.Flags;
import com.android.server.backup.TransportManager;
import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.internal.BackupHandler;
import com.android.server.backup.internal.OnTaskFinishedListener;
import com.android.server.backup.params.RestoreGetSetsParams;
import com.android.server.backup.params.RestoreParams;
import com.android.server.backup.transport.TransportConnection;
import com.android.server.backup.utils.BackupEligibilityRules;

import java.util.List;
import java.util.function.BiFunction;

public final class ActiveRestoreSession extends IRestoreSession.Stub {
    public final BackupEligibilityRules mBackupEligibilityRules;
    public final UserBackupManagerService mBackupManagerService;
    public final String mPackageName;
    public final TransportManager mTransportManager;
    public final String mTransportName;
    public final int mUserId;
    public List mRestoreSets = null;
    public boolean mEnded = false;
    public boolean mTimedOut = false;

    public final class EndRestoreRunnable implements Runnable {
        public final UserBackupManagerService mBackupManager;
        public final ActiveRestoreSession mSession;

        public EndRestoreRunnable(
                UserBackupManagerService userBackupManagerService,
                ActiveRestoreSession activeRestoreSession) {
            this.mBackupManager = userBackupManagerService;
            this.mSession = activeRestoreSession;
        }

        @Override // java.lang.Runnable
        public final void run() {
            ActiveRestoreSession activeRestoreSession;
            synchronized (this.mSession) {
                activeRestoreSession = this.mSession;
                activeRestoreSession.mEnded = true;
            }
            UserBackupManagerService userBackupManagerService = this.mBackupManager;
            synchronized (userBackupManagerService) {
                try {
                    if (activeRestoreSession != userBackupManagerService.mActiveRestoreSession) {
                        Slog.e(
                                "BackupManagerService",
                                UserBackupManagerService.addUserIdToLogMessage(
                                        userBackupManagerService.mUserId,
                                        "ending non-current restore session"));
                    } else {
                        Slog.v(
                                "BackupManagerService",
                                UserBackupManagerService.addUserIdToLogMessage(
                                        userBackupManagerService.mUserId,
                                        "Clearing restore session and halting timeout"));
                        userBackupManagerService.mActiveRestoreSession = null;
                        userBackupManagerService.mBackupHandler.removeMessages(8);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public ActiveRestoreSession(
            UserBackupManagerService userBackupManagerService,
            String str,
            String str2,
            BackupEligibilityRules backupEligibilityRules) {
        this.mBackupManagerService = userBackupManagerService;
        this.mPackageName = str;
        this.mTransportManager = userBackupManagerService.mTransportManager;
        this.mTransportName = str2;
        this.mUserId = userBackupManagerService.mUserId;
        this.mBackupEligibilityRules = backupEligibilityRules;
    }

    public final synchronized void endRestoreSession() {
        Slog.d("RestoreSession", "endRestoreSession");
        if (this.mTimedOut) {
            Slog.i("RestoreSession", "Session already timed out");
        } else {
            if (this.mEnded) {
                throw new IllegalStateException("Restore session already ended");
            }
            UserBackupManagerService userBackupManagerService = this.mBackupManagerService;
            userBackupManagerService.mBackupHandler.post(
                    new EndRestoreRunnable(userBackupManagerService, this));
        }
    }

    public final synchronized int getAvailableRestoreSets(
            IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) {
        this.mBackupManagerService.mContext.enforceCallingOrSelfPermission(
                "android.permission.BACKUP", "getAvailableRestoreSets");
        if (iRestoreObserver == null) {
            throw new IllegalArgumentException("Observer must not be null");
        }
        if (this.mEnded) {
            throw new IllegalStateException("Restore session already ended");
        }
        if (this.mTimedOut) {
            Slog.i("RestoreSession", "Session already timed out");
            return -1;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                TransportConnection transportClient =
                        this.mTransportManager.getTransportClient(
                                this.mTransportName, "RestoreSession.getAvailableRestoreSets()");
                if (transportClient == null) {
                    Slog.w("RestoreSession", "Null transport client getting restore sets");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -1;
                }
                this.mBackupManagerService.mBackupHandler.removeMessages(8);
                UserBackupManagerService.BackupWakeLock backupWakeLock =
                        this.mBackupManagerService.mWakelock;
                backupWakeLock.acquire();
                this.mBackupManagerService.mBackupHandler.sendMessage(
                        this.mBackupManagerService.mBackupHandler.obtainMessage(
                                6,
                                new RestoreGetSetsParams(
                                        transportClient,
                                        this,
                                        iRestoreObserver,
                                        new ActiveRestoreSession$$ExternalSyntheticLambda0(
                                                this.mTransportManager,
                                                transportClient,
                                                backupWakeLock,
                                                0))));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            } catch (Exception e) {
                Slog.e("RestoreSession", "Error in getAvailableRestoreSets", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity("getAvailableRestoreSets");
            throw th;
        }
    }

    public BackupEligibilityRules getBackupEligibilityRules(RestoreSet restoreSet) {
        boolean equals = "D2D".equals(restoreSet.device);
        if (Flags.enableSkippingRestoreLaunchedApps()) {
            return new BackupEligibilityRules(
                    this.mBackupManagerService.mPackageManager,
                    (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class),
                    this.mUserId,
                    this.mBackupManagerService.mContext,
                    equals ? 1 : 0,
                    (restoreSet.backupTransportFlags & 4) != 0);
        }
        return this.mBackupManagerService.getEligibilityRulesForOperation(equals ? 1 : 0);
    }

    public final synchronized int restoreAll(
            long j,
            IRestoreObserver iRestoreObserver,
            IBackupManagerMonitor iBackupManagerMonitor) {
        this.mBackupManagerService.mContext.enforceCallingOrSelfPermission(
                "android.permission.BACKUP", "performRestore");
        Slog.d(
                "RestoreSession",
                "restoreAll token=" + Long.toHexString(j) + " observer=" + iRestoreObserver);
        if (this.mEnded) {
            throw new IllegalStateException("Restore session already ended");
        }
        if (this.mTimedOut) {
            Slog.i("RestoreSession", "Session already timed out");
            return -1;
        }
        if (this.mRestoreSets == null) {
            Slog.e("RestoreSession", "Ignoring restoreAll() with no restore set");
            return -1;
        }
        if (this.mPackageName != null) {
            Slog.e("RestoreSession", "Ignoring restoreAll() on single-package session");
            return -1;
        }
        if (!this.mTransportManager.isTransportRegistered(this.mTransportName)) {
            Slog.e("RestoreSession", "Transport " + this.mTransportName + " not registered");
            return -1;
        }
        synchronized (this.mBackupManagerService.mQueueLock) {
            for (int i = 0; i < this.mRestoreSets.size(); i++) {
                if (j == ((RestoreSet) this.mRestoreSets.get(i)).token) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        return sendRestoreToHandlerLocked(
                                new ActiveRestoreSession$$ExternalSyntheticLambda2(
                                        this,
                                        iRestoreObserver,
                                        iBackupManagerMonitor,
                                        j,
                                        (RestoreSet) this.mRestoreSets.get(i)),
                                "RestoreSession.restoreAll()");
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
            Slog.w("RestoreSession", "Restore token " + Long.toHexString(j) + " not found");
            return -1;
        }
    }

    public final synchronized int restorePackage(
            String str,
            IRestoreObserver iRestoreObserver,
            IBackupManagerMonitor iBackupManagerMonitor) {
        Slog.v(
                "RestoreSession",
                "restorePackage pkg="
                        + str
                        + " obs="
                        + iRestoreObserver
                        + "monitor="
                        + iBackupManagerMonitor);
        if (this.mEnded) {
            throw new IllegalStateException("Restore session already ended");
        }
        if (this.mTimedOut) {
            Slog.i("RestoreSession", "Session already timed out");
            return -1;
        }
        String str2 = this.mPackageName;
        if (str2 != null && !str2.equals(str)) {
            Slog.e(
                    "RestoreSession",
                    "Ignoring attempt to restore pkg="
                            + str
                            + " on session for package "
                            + this.mPackageName);
            return -1;
        }
        try {
            PackageInfo packageInfoAsUser =
                    this.mBackupManagerService.mPackageManager.getPackageInfoAsUser(
                            str, 0, this.mUserId);
            if (this.mBackupManagerService.mContext.checkPermission(
                                    "android.permission.BACKUP",
                                    Binder.getCallingPid(),
                                    Binder.getCallingUid())
                            == -1
                    && packageInfoAsUser.applicationInfo.uid != Binder.getCallingUid()) {
                Slog.w(
                        "RestoreSession",
                        "restorePackage: bad packageName="
                                + str
                                + " or calling uid="
                                + Binder.getCallingUid());
                throw new SecurityException("No permission to restore other packages");
            }
            if (!this.mTransportManager.isTransportRegistered(this.mTransportName)) {
                Slog.e("RestoreSession", "Transport " + this.mTransportName + " not registered");
                return -1;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                long availableRestoreToken =
                        this.mBackupManagerService.getAvailableRestoreToken(str);
                Slog.v(
                        "RestoreSession",
                        "restorePackage pkg="
                                + str
                                + " token="
                                + Long.toHexString(availableRestoreToken));
                if (availableRestoreToken == 0) {
                    Slog.w("RestoreSession", "No data available for this package; not restoring");
                    return -1;
                }
                return sendRestoreToHandlerLocked(
                        new ActiveRestoreSession$$ExternalSyntheticLambda2(
                                this,
                                iRestoreObserver,
                                iBackupManagerMonitor,
                                availableRestoreToken,
                                packageInfoAsUser),
                        "RestoreSession.restorePackage(" + str + ")");
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.w("RestoreSession", "Asked to restore nonexistent pkg " + str);
            return -1;
        }
    }

    public final synchronized int restorePackages(
            final long j,
            final IRestoreObserver iRestoreObserver,
            final String[] strArr,
            final IBackupManagerMonitor iBackupManagerMonitor) {
        try {
            this.mBackupManagerService.mContext.enforceCallingOrSelfPermission(
                    "android.permission.BACKUP", "performRestore");
            StringBuilder sb = new StringBuilder(128);
            sb.append("restorePackages token=");
            sb.append(Long.toHexString(j));
            sb.append(" observer=");
            if (iRestoreObserver == null) {
                sb.append("null");
            } else {
                sb.append(iRestoreObserver.toString());
            }
            sb.append(" monitor=");
            if (iBackupManagerMonitor == null) {
                sb.append("null");
            } else {
                sb.append(iBackupManagerMonitor.toString());
            }
            sb.append(" packages=");
            if (strArr == null) {
                sb.append("null");
            } else {
                sb.append('{');
                boolean z = true;
                for (String str : strArr) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append(", ");
                    }
                    sb.append(str);
                }
                sb.append('}');
            }
            Slog.d("RestoreSession", sb.toString());
            if (this.mEnded) {
                throw new IllegalStateException("Restore session already ended");
            }
            if (this.mTimedOut) {
                Slog.i("RestoreSession", "Session already timed out");
                return -1;
            }
            if (this.mRestoreSets == null) {
                Slog.e("RestoreSession", "Ignoring restoreAll() with no restore set");
                return -1;
            }
            if (this.mPackageName != null) {
                Slog.e("RestoreSession", "Ignoring restoreAll() on single-package session");
                return -1;
            }
            if (!this.mTransportManager.isTransportRegistered(this.mTransportName)) {
                Slog.e("RestoreSession", "Transport " + this.mTransportName + " not registered");
                return -1;
            }
            synchronized (this.mBackupManagerService.mQueueLock) {
                for (int i = 0; i < this.mRestoreSets.size(); i++) {
                    if (j == ((RestoreSet) this.mRestoreSets.get(i)).token) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        final RestoreSet restoreSet = (RestoreSet) this.mRestoreSets.get(i);
                        try {
                            return sendRestoreToHandlerLocked(
                                    new BiFunction() { // from class:
                                                       // com.android.server.backup.restore.ActiveRestoreSession$$ExternalSyntheticLambda1
                                        @Override // java.util.function.BiFunction
                                        public final Object apply(Object obj, Object obj2) {
                                            ActiveRestoreSession activeRestoreSession =
                                                    ActiveRestoreSession.this;
                                            IRestoreObserver iRestoreObserver2 = iRestoreObserver;
                                            IBackupManagerMonitor iBackupManagerMonitor2 =
                                                    iBackupManagerMonitor;
                                            long j2 = j;
                                            String[] strArr2 = strArr;
                                            RestoreSet restoreSet2 = restoreSet;
                                            TransportConnection transportConnection =
                                                    (TransportConnection) obj;
                                            OnTaskFinishedListener onTaskFinishedListener =
                                                    (OnTaskFinishedListener) obj2;
                                            activeRestoreSession.getClass();
                                            return new RestoreParams(
                                                    transportConnection,
                                                    iRestoreObserver2,
                                                    iBackupManagerMonitor2,
                                                    j2,
                                                    null,
                                                    0,
                                                    strArr2.length > 1,
                                                    strArr2,
                                                    onTaskFinishedListener,
                                                    activeRestoreSession.getBackupEligibilityRules(
                                                            restoreSet2));
                                        }
                                    },
                                    "RestoreSession.restorePackages("
                                            + strArr.length
                                            + " packages)");
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
                Slog.w("RestoreSession", "Restore token " + Long.toHexString(j) + " not found");
                return -1;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final int sendRestoreToHandlerLocked(BiFunction biFunction, String str) {
        TransportConnection transportClient =
                this.mTransportManager.getTransportClient(this.mTransportName, str);
        if (transportClient == null) {
            Slog.e("RestoreSession", "Transport " + this.mTransportName + " got unregistered");
            return -1;
        }
        BackupHandler backupHandler = this.mBackupManagerService.mBackupHandler;
        backupHandler.removeMessages(8);
        UserBackupManagerService.BackupWakeLock backupWakeLock =
                this.mBackupManagerService.mWakelock;
        backupWakeLock.acquire();
        ActiveRestoreSession$$ExternalSyntheticLambda0
                activeRestoreSession$$ExternalSyntheticLambda0 =
                        new ActiveRestoreSession$$ExternalSyntheticLambda0(
                                this.mTransportManager, transportClient, backupWakeLock, 1);
        Message obtainMessage = backupHandler.obtainMessage(3);
        obtainMessage.obj =
                biFunction.apply(transportClient, activeRestoreSession$$ExternalSyntheticLambda0);
        backupHandler.sendMessage(obtainMessage);
        return 0;
    }
}

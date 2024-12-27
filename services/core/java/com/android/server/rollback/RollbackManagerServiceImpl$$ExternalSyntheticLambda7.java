package com.android.server.rollback;

import android.content.Context;
import android.content.pm.PackageInstaller;
import android.content.pm.VersionedPackage;
import android.content.rollback.PackageRollbackInfo;
import android.content.rollback.RollbackInfo;
import android.content.rollback.RollbackManager;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;

import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.PackageWatchdog;
import com.android.server.PackageWatchdog$$ExternalSyntheticLambda0;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public final /* synthetic */ class RollbackManagerServiceImpl$$ExternalSyntheticLambda7
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RollbackManagerServiceImpl f$0;

    public /* synthetic */ RollbackManagerServiceImpl$$ExternalSyntheticLambda7(
            RollbackManagerServiceImpl rollbackManagerServiceImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = rollbackManagerServiceImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        RollbackManagerServiceImpl rollbackManagerServiceImpl = this.f$0;
        switch (i) {
            case 0:
                rollbackManagerServiceImpl.runExpiration();
                break;
            case 1:
                rollbackManagerServiceImpl.assertInWorkerThread();
                ((ArrayList) rollbackManagerServiceImpl.mRollbacks).clear();
                ((ArrayList) rollbackManagerServiceImpl.mRollbacks)
                        .addAll(
                                RollbackStore.loadRollbacks(
                                        rollbackManagerServiceImpl
                                                .mRollbackStore
                                                .mRollbackDataDir));
                break;
            default:
                rollbackManagerServiceImpl.assertInWorkerThread();
                rollbackManagerServiceImpl.updateRollbackLifetimeDurationInMillis();
                rollbackManagerServiceImpl.runExpiration();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                HashSet hashSet = new HashSet();
                Iterator it = ((ArrayList) rollbackManagerServiceImpl.mRollbacks).iterator();
                while (it.hasNext()) {
                    Rollback rollback = (Rollback) it.next();
                    if (rollback.info.isStaged()) {
                        PackageInstaller packageInstaller =
                                rollbackManagerServiceImpl
                                        .mContext
                                        .getPackageManager()
                                        .getPackageInstaller();
                        int i2 = rollback.mOriginalSessionId;
                        PackageInstaller.SessionInfo sessionInfo =
                                packageInstaller.getSessionInfo(i2);
                        if (sessionInfo != null && !sessionInfo.isStagedSessionFailed()) {
                            if (sessionInfo.isStagedSessionApplied()) {
                                if (rollback.isEnabling()) {
                                    arrayList.add(rollback);
                                } else {
                                    rollback.assertInWorkerThread();
                                    if (rollback.mRestoreUserDataInProgress) {
                                        arrayList2.add(rollback);
                                    }
                                }
                            }
                            rollback.assertInWorkerThread();
                            ArrayList arrayList3 = new ArrayList();
                            for (PackageRollbackInfo packageRollbackInfo :
                                    rollback.info.getPackages()) {
                                if (packageRollbackInfo.isApex()) {
                                    arrayList3.add(packageRollbackInfo.getPackageName());
                                }
                            }
                            hashSet.addAll(arrayList3);
                        } else if (rollback.isEnabling()) {
                            it.remove();
                            rollbackManagerServiceImpl.deleteRollback(
                                    rollback, "Session " + i2 + " not existed or failed");
                        }
                    }
                }
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    rollbackManagerServiceImpl.makeRollbackAvailable((Rollback) it2.next());
                }
                Iterator it3 = arrayList2.iterator();
                while (it3.hasNext()) {
                    Rollback rollback2 = (Rollback) it3.next();
                    rollback2.assertInWorkerThread();
                    rollback2.mRestoreUserDataInProgress = false;
                    RollbackStore.saveRollback(rollback2, rollback2.mBackupDir);
                }
                Iterator it4 = hashSet.iterator();
                while (it4.hasNext()) {
                    rollbackManagerServiceImpl.onPackageReplaced((String) it4.next());
                }
                final RollbackPackageHealthObserver rollbackPackageHealthObserver =
                        rollbackManagerServiceImpl.mPackageHealthObserver;
                rollbackPackageHealthObserver.mHandler.post(
                        new Runnable() { // from class:
                            // com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                VersionedPackage versionedPackage;
                                RollbackInfo rollbackInfo;
                                String str;
                                RollbackPackageHealthObserver rollbackPackageHealthObserver2 =
                                        RollbackPackageHealthObserver.this;
                                rollbackPackageHealthObserver2.assertInWorkerThread();
                                RollbackManager rollbackManager =
                                        (RollbackManager)
                                                rollbackPackageHealthObserver2.mContext
                                                        .getSystemService(RollbackManager.class);
                                if (!rollbackManager.getAvailableRollbacks().isEmpty()) {
                                    PackageWatchdog packageWatchdog =
                                            PackageWatchdog.getInstance(
                                                    rollbackPackageHealthObserver2.mContext);
                                    Slog.i(
                                            "PackageWatchdog",
                                            "Scheduling "
                                                    + packageWatchdog
                                                            .mNumberOfNativeCrashPollsRemaining
                                                    + " polls to check and mitigate native"
                                                    + " crashes");
                                    packageWatchdog.mShortTaskHandler.post(
                                            new PackageWatchdog$$ExternalSyntheticLambda0(
                                                    packageWatchdog, 5));
                                }
                                rollbackPackageHealthObserver2.assertInWorkerThread();
                                try {
                                    SparseArray readStagedRollbackIds =
                                            RollbackPackageHealthObserver.readStagedRollbackIds(
                                                    rollbackPackageHealthObserver2
                                                            .mLastStagedRollbackIdsFile);
                                    rollbackPackageHealthObserver2.mLastStagedRollbackIdsFile
                                            .delete();
                                    for (int i3 = 0; i3 < readStagedRollbackIds.size(); i3++) {
                                        Context context = rollbackPackageHealthObserver2.mContext;
                                        int keyAt = readStagedRollbackIds.keyAt(i3);
                                        String str2 = (String) readStagedRollbackIds.valueAt(i3);
                                        List recentlyCommittedRollbacks =
                                                rollbackManager.getRecentlyCommittedRollbacks();
                                        PackageInstaller packageInstaller2 =
                                                context.getPackageManager().getPackageInstaller();
                                        Iterator it5 = recentlyCommittedRollbacks.iterator();
                                        while (true) {
                                            versionedPackage = null;
                                            if (it5.hasNext()) {
                                                rollbackInfo = (RollbackInfo) it5.next();
                                                if (keyAt == rollbackInfo.getRollbackId()) {
                                                    break;
                                                }
                                            } else {
                                                rollbackInfo = null;
                                                break;
                                            }
                                        }
                                        if (rollbackInfo == null) {
                                            str =
                                                    "rollback info not found for last staged"
                                                        + " rollback: ";
                                        } else {
                                            if (!TextUtils.isEmpty(str2)) {
                                                Iterator it6 =
                                                        rollbackInfo.getPackages().iterator();
                                                while (true) {
                                                    if (!it6.hasNext()) {
                                                        break;
                                                    }
                                                    PackageRollbackInfo packageRollbackInfo2 =
                                                            (PackageRollbackInfo) it6.next();
                                                    if (str2.equals(
                                                            packageRollbackInfo2
                                                                    .getPackageName())) {
                                                        versionedPackage =
                                                                packageRollbackInfo2
                                                                        .getVersionRolledBackFrom();
                                                        break;
                                                    }
                                                }
                                            }
                                            keyAt = rollbackInfo.getCommittedSessionId();
                                            PackageInstaller.SessionInfo sessionInfo2 =
                                                    packageInstaller2.getSessionInfo(keyAt);
                                            if (sessionInfo2 == null) {
                                                str =
                                                        "On boot completed, could not load session"
                                                            + " id ";
                                            } else {
                                                if (sessionInfo2.isStagedSessionApplied()) {
                                                    WatchdogRollbackLogger.logEvent(
                                                            versionedPackage, 2, 0, "");
                                                } else if (sessionInfo2.isStagedSessionFailed()) {
                                                    WatchdogRollbackLogger.logEvent(
                                                            versionedPackage, 3, 0, "");
                                                }
                                            }
                                        }
                                        NandswapManager$$ExternalSyntheticOutline0.m(
                                                keyAt, str, "WatchdogRollbackLogger");
                                    }
                                } catch (Throwable th) {
                                    rollbackPackageHealthObserver2.mLastStagedRollbackIdsFile
                                            .delete();
                                    throw th;
                                }
                            }
                        });
                break;
        }
    }
}

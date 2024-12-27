package com.android.server.pm;

import android.app.ActivityThread;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInstaller;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Slog;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public final class GentleUpdateHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long PENDING_CHECK_MILLIS = TimeUnit.SECONDS.toMillis(10);
    public final AppStateHelper mAppStateHelper;
    public final Context mContext;
    public final Handler mHandler;
    public boolean mHasPendingIdleJob;
    public final ArrayDeque mPendingChecks = new ArrayDeque();
    public final ArrayList mPendingIdleFutures = new ArrayList();

    public final class PendingInstallConstraintsCheck {
        public final PackageInstaller.InstallConstraints constraints;
        public final CompletableFuture future;
        public final long mFinishTime;
        public final List packageNames;

        public PendingInstallConstraintsCheck(
                List list,
                PackageInstaller.InstallConstraints installConstraints,
                CompletableFuture completableFuture,
                long j) {
            this.packageNames = list;
            this.constraints = installConstraints;
            this.future = completableFuture;
            this.mFinishTime =
                    SystemClock.elapsedRealtime() + Math.max(0L, Math.min(604800000L, j));
        }
    }

    public class Service extends JobService {
        public static final /* synthetic */ int $r8$clinit = 0;

        @Override // android.app.job.JobService
        public final boolean onStartJob(JobParameters jobParameters) {
            try {
                GentleUpdateHelper gentleUpdateHelper =
                        ActivityThread.getPackageManager()
                                .getPackageInstaller()
                                .mGentleUpdateHelper;
                gentleUpdateHelper.mHandler.post(
                        new GentleUpdateHelper$$ExternalSyntheticLambda0(1, gentleUpdateHelper));
                return false;
            } catch (Exception e) {
                Slog.e("GentleUpdateHelper", "Failed to get PackageInstallerService", e);
                return false;
            }
        }

        @Override // android.app.job.JobService
        public final boolean onStopJob(JobParameters jobParameters) {
            return false;
        }
    }

    public static void $r8$lambda$JQiy908NuzFx59XC1nxYVaiXKqM(
            GentleUpdateHelper gentleUpdateHelper, int i, int i2) {
        gentleUpdateHelper.getClass();
        try {
            gentleUpdateHelper.mHandler.post(
                    new GentleUpdateHelper$$ExternalSyntheticLambda3(
                            gentleUpdateHelper,
                            ActivityThread.getPackageManager().getNameForUid(i),
                            i2));
        } catch (RemoteException unused) {
        }
    }

    public GentleUpdateHelper(Context context, Looper looper, AppStateHelper appStateHelper) {
        this.mContext = context;
        this.mHandler = new Handler(looper);
        this.mAppStateHelper = appStateHelper;
    }

    /* JADX WARN: Code restructure failed: missing block: B:102:0x0151, code lost:

       if (r9 != false) goto L86;
    */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x014d, code lost:

       if (r9 != 3) goto L83;
    */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00d7, code lost:

       r11 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00e7, code lost:

       if (r11 == false) goto L56;
    */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0119, code lost:

       if (r11 == false) goto L69;
    */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00d7 A[EDGE_INSN: B:63:0x00d7->B:58:0x00d7 BREAK  A[LOOP:1: B:31:0x0059->B:60:?], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean processPendingCheck(
            com.android.server.pm.GentleUpdateHelper.PendingInstallConstraintsCheck r10,
            boolean r11) {
        /*
            Method dump skipped, instructions count: 371
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.GentleUpdateHelper.processPendingCheck(com.android.server.pm.GentleUpdateHelper$PendingInstallConstraintsCheck,"
                    + " boolean):boolean");
    }

    public final void runIdleJob() {
        this.mHasPendingIdleJob = false;
        int size = this.mPendingChecks.size();
        for (int i = 0; i < size; i++) {
            PendingInstallConstraintsCheck pendingInstallConstraintsCheck =
                    (PendingInstallConstraintsCheck) this.mPendingChecks.remove();
            if (!processPendingCheck(pendingInstallConstraintsCheck, true)) {
                this.mPendingChecks.add(pendingInstallConstraintsCheck);
            }
        }
        if (!this.mPendingChecks.isEmpty()) {
            scheduleIdleJob();
        }
        Iterator it = this.mPendingIdleFutures.iterator();
        while (it.hasNext()) {
            ((CompletableFuture) it.next()).complete(Boolean.TRUE);
        }
        this.mPendingIdleFutures.clear();
    }

    public final void scheduleIdleJob() {
        if (SystemProperties.getBoolean("debug.pm.gentle_update_test.is_idle", false)) {
            this.mHandler.post(new GentleUpdateHelper$$ExternalSyntheticLambda0(0, this));
        } else {
            if (this.mHasPendingIdleJob) {
                return;
            }
            this.mHasPendingIdleJob = true;
            ((JobScheduler) this.mContext.getSystemService(JobScheduler.class))
                    .schedule(
                            new JobInfo.Builder(
                                            235306967,
                                            new ComponentName(
                                                    this.mContext.getPackageName(),
                                                    Service.class.getName()))
                                    .setRequiresDeviceIdle(true)
                                    .build());
        }
    }
}

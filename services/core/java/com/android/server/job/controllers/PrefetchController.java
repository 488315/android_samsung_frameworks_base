package com.android.server.job.controllers;

import android.app.usage.UsageStatsManagerInternal;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.pm.UserPackage;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArrayMap;
import android.util.TimeUtils;
import com.android.internal.os.SomeArgs;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.LocalServices;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.clipboard.ClipboardService;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda5;
import com.android.server.job.JobSchedulerService$Constants$$ExternalSyntheticOutline0;
import com.android.server.job.controllers.FlexibilityController;
import com.android.server.usage.UsageStatsService;
import com.android.server.utils.AlarmQueue;
import java.io.PrintWriter;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PrefetchController extends StateController {
    public static final boolean DEBUG;
    public AppWidgetManager mAppWidgetManager;
    public final AnonymousClass1 mEstimatedLaunchTimeChangedListener;
    public final SparseArrayMap mEstimatedLaunchTimes;
    public final PcHandler mHandler;
    public long mLaunchTimeAllowanceMs;
    public long mLaunchTimeThresholdMs;
    public final PcConstants mPcConstants;
    public final ArraySet mPrefetchChangedListeners;
    public final ThresholdAlarmListener mThresholdAlarmListener;
    public final SparseArrayMap mTrackedJobs;
    public final UsageStatsManagerInternal mUsageStatsManagerInternal;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.job.controllers.PrefetchController$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class PcConstants {
        static final String KEY_LAUNCH_TIME_ALLOWANCE_MS = "pc_launch_time_allowance_ms";
        static final String KEY_LAUNCH_TIME_THRESHOLD_MS = "pc_launch_time_threshold_ms";
        public boolean mShouldReevaluateConstraints = false;
        public long LAUNCH_TIME_THRESHOLD_MS = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
        public long LAUNCH_TIME_ALLOWANCE_MS = 1800000;

        public PcConstants() {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PcHandler extends Handler {
        public PcHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                int i2 = message.arg1;
                String str = (String) message.obj;
                long estimatedPackageLaunchTime = UsageStatsService.this.getEstimatedPackageLaunchTime(i2, str);
                if (PrefetchController.DEBUG) {
                    Slog.d("JobScheduler.Prefetch", "Retrieved launch time for " + StateController.packageToString(i2, str) + " of " + estimatedPackageLaunchTime + " (" + TimeUtils.formatDuration(estimatedPackageLaunchTime - JobSchedulerService.sSystemClock.millis()) + " from now)");
                }
                synchronized (PrefetchController.this.mLock) {
                    try {
                        Long l = (Long) PrefetchController.this.mEstimatedLaunchTimes.get(i2, str);
                        if (l != null) {
                            if (estimatedPackageLaunchTime != l.longValue()) {
                            }
                        }
                        PrefetchController.m626$$Nest$mprocessUpdatedEstimatedLaunchTime(PrefetchController.this, i2, str, estimatedPackageLaunchTime);
                    } finally {
                    }
                }
                return;
            }
            if (i == 1) {
                SomeArgs someArgs = (SomeArgs) message.obj;
                PrefetchController.m626$$Nest$mprocessUpdatedEstimatedLaunchTime(PrefetchController.this, someArgs.argi1, (String) someArgs.arg1, someArgs.argl1);
                someArgs.recycle();
                return;
            }
            if (i != 2) {
                return;
            }
            int i3 = message.arg1;
            PrefetchController prefetchController = PrefetchController.this;
            synchronized (prefetchController.mLock) {
                try {
                    ArraySet packagesForUidLocked = prefetchController.mService.getPackagesForUidLocked(i3);
                    if (packagesForUidLocked == null) {
                        return;
                    }
                    int userId = UserHandle.getUserId(i3);
                    ArraySet arraySet = new ArraySet();
                    long millis = JobSchedulerService.sSystemClock.millis();
                    JobSchedulerService.sElapsedRealtimeClock.getClass();
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    int size = packagesForUidLocked.size() - 1;
                    while (size >= 0) {
                        ArraySet arraySet2 = (ArraySet) prefetchController.mTrackedJobs.get(userId, (String) packagesForUidLocked.valueAt(size));
                        if (arraySet2 != null) {
                            int i4 = 0;
                            while (i4 < arraySet2.size()) {
                                JobStatus jobStatus = (JobStatus) arraySet2.valueAt(i4);
                                int i5 = userId;
                                ArraySet arraySet3 = arraySet2;
                                int i6 = i4;
                                if (prefetchController.updateConstraintLocked(jobStatus, millis, elapsedRealtime)) {
                                    arraySet.add(jobStatus);
                                }
                                i4 = i6 + 1;
                                userId = i5;
                                arraySet2 = arraySet3;
                            }
                        }
                        size--;
                        userId = userId;
                    }
                    if (arraySet.size() > 0) {
                        prefetchController.mStateChangedListener.onControllerStateChanged(arraySet);
                    }
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface PrefetchChangedListener {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ThresholdAlarmListener extends AlarmQueue {
        public ThresholdAlarmListener(Context context, Looper looper) {
            super(context, looper, "*job.prefetch*", "Prefetch threshold", false, 360000L);
        }

        @Override // com.android.server.utils.AlarmQueue
        public final boolean isForUser(int i, Object obj) {
            return ((UserPackage) obj).userId == i;
        }

        @Override // com.android.server.utils.AlarmQueue
        public final void processExpiredAlarms(ArraySet arraySet) {
            long j;
            ArraySet arraySet2 = new ArraySet();
            synchronized (PrefetchController.this.mLock) {
                try {
                    long millis = JobSchedulerService.sSystemClock.millis();
                    JobSchedulerService.sElapsedRealtimeClock.getClass();
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    int i = 0;
                    while (i < arraySet.size()) {
                        UserPackage userPackage = (UserPackage) arraySet.valueAt(i);
                        if (PrefetchController.this.willBeLaunchedSoonLocked(userPackage.userId, userPackage.packageName, millis)) {
                            j = millis;
                            if (PrefetchController.this.maybeUpdateConstraintForPkgLocked(userPackage.userId, millis, elapsedRealtime, userPackage.packageName)) {
                                arraySet2.addAll((ArraySet) PrefetchController.this.mTrackedJobs.get(userPackage.userId, userPackage.packageName));
                            }
                        } else {
                            Slog.e("JobScheduler.Prefetch", "Alarm expired for " + StateController.packageToString(userPackage.userId, userPackage.packageName) + " at the wrong time");
                            PrefetchController.this.updateThresholdAlarmLocked(userPackage.userId, millis, elapsedRealtime, userPackage.packageName);
                            j = millis;
                        }
                        i++;
                        millis = j;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (arraySet2.size() > 0) {
                PrefetchController.this.mStateChangedListener.onControllerStateChanged(arraySet2);
            }
        }
    }

    /* renamed from: -$$Nest$mprocessUpdatedEstimatedLaunchTime, reason: not valid java name */
    public static void m626$$Nest$mprocessUpdatedEstimatedLaunchTime(PrefetchController prefetchController, int i, String str, long j) {
        boolean z = DEBUG;
        if (z) {
            prefetchController.getClass();
            Slog.d("JobScheduler.Prefetch", "Estimated launch time for " + StateController.packageToString(i, str) + " changed to " + j + " (" + TimeUtils.formatDuration(j - JobSchedulerService.sSystemClock.millis()) + " from now)");
        }
        synchronized (prefetchController.mLock) {
            try {
                ArraySet arraySet = (ArraySet) prefetchController.mTrackedJobs.get(i, str);
                if (arraySet != null) {
                    long longValue = ((Long) prefetchController.mEstimatedLaunchTimes.get(i, str)).longValue();
                    prefetchController.mEstimatedLaunchTimes.add(i, str, Long.valueOf(j));
                    if (!arraySet.isEmpty()) {
                        long millis = JobSchedulerService.sSystemClock.millis();
                        JobSchedulerService.sElapsedRealtimeClock.getClass();
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        prefetchController.updateThresholdAlarmLocked(i, millis, elapsedRealtime, str);
                        int i2 = 0;
                        while (i2 < prefetchController.mPrefetchChangedListeners.size()) {
                            ((FlexibilityController.AnonymousClass1) ((PrefetchChangedListener) prefetchController.mPrefetchChangedListeners.valueAt(i2))).onPrefetchCacheUpdated(arraySet, i, str, longValue, j, elapsedRealtime);
                            i2++;
                            arraySet = arraySet;
                        }
                        ArraySet arraySet2 = arraySet;
                        if (prefetchController.maybeUpdateConstraintForPkgLocked(i, millis, elapsedRealtime, str)) {
                            prefetchController.mStateChangedListener.onControllerStateChanged(arraySet2);
                        }
                    }
                } else if (z) {
                    Slog.i("JobScheduler.Prefetch", "Not caching launch time since we haven't seen any prefetch jobs for " + StateController.packageToString(i, str));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    static {
        DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.Prefetch", 3);
    }

    public PrefetchController(JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService);
        this.mTrackedJobs = new SparseArrayMap();
        this.mEstimatedLaunchTimes = new SparseArrayMap();
        this.mPrefetchChangedListeners = new ArraySet();
        this.mLaunchTimeThresholdMs = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
        this.mLaunchTimeAllowanceMs = 1800000L;
        this.mEstimatedLaunchTimeChangedListener = new AnonymousClass1();
        this.mPcConstants = new PcConstants();
        this.mHandler = new PcHandler(AppSchedulingModuleThread.get().getLooper());
        this.mThresholdAlarmListener = new ThresholdAlarmListener(this.mContext, AppSchedulingModuleThread.get().getLooper());
        this.mUsageStatsManagerInternal = (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpConstants(IndentingPrintWriter indentingPrintWriter) {
        PcConstants pcConstants = this.mPcConstants;
        pcConstants.getClass();
        indentingPrintWriter.println();
        indentingPrintWriter.print("PrefetchController");
        indentingPrintWriter.println(":");
        indentingPrintWriter.increaseIndent();
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(pcConstants.LAUNCH_TIME_THRESHOLD_MS, indentingPrintWriter, "pc_launch_time_threshold_ms");
        indentingPrintWriter.print("pc_launch_time_allowance_ms", Long.valueOf(pcConstants.LAUNCH_TIME_ALLOWANCE_MS)).println();
        indentingPrintWriter.decreaseIndent();
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(final IndentingPrintWriter indentingPrintWriter, final JobSchedulerService$$ExternalSyntheticLambda5 jobSchedulerService$$ExternalSyntheticLambda5) {
        long millis = JobSchedulerService.sSystemClock.millis();
        indentingPrintWriter.println("Cached launch times:");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mEstimatedLaunchTimes.numMaps(); i++) {
            int keyAt = this.mEstimatedLaunchTimes.keyAt(i);
            for (int i2 = 0; i2 < this.mEstimatedLaunchTimes.numElementsForKey(keyAt); i2++) {
                String str = (String) this.mEstimatedLaunchTimes.keyAt(i, i2);
                long longValue = ((Long) this.mEstimatedLaunchTimes.valueAt(i, i2)).longValue();
                indentingPrintWriter.print(StateController.packageToString(keyAt, str));
                indentingPrintWriter.print(": ");
                indentingPrintWriter.print(longValue);
                indentingPrintWriter.print(" (");
                TimeUtils.formatDuration(longValue - millis, indentingPrintWriter, 19);
                indentingPrintWriter.println(" from now)");
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        this.mTrackedJobs.forEach(new Consumer() { // from class: com.android.server.job.controllers.PrefetchController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Predicate predicate = jobSchedulerService$$ExternalSyntheticLambda5;
                PrintWriter printWriter = indentingPrintWriter;
                ArraySet arraySet = (ArraySet) obj;
                for (int i3 = 0; i3 < arraySet.size(); i3++) {
                    JobStatus jobStatus = (JobStatus) arraySet.valueAt(i3);
                    if (predicate.test(jobStatus)) {
                        printWriter.print("#");
                        jobStatus.printUniqueId(printWriter);
                        printWriter.print(" from ");
                        UserHandle.formatUid(printWriter, jobStatus.sourceUid);
                        printWriter.println();
                    }
                }
            }
        });
        indentingPrintWriter.println();
        this.mThresholdAlarmListener.dump(indentingPrintWriter);
    }

    public long getLaunchTimeAllowanceMs() {
        return this.mLaunchTimeAllowanceMs;
    }

    public long getLaunchTimeThresholdMs() {
        return this.mLaunchTimeThresholdMs;
    }

    public final long getNextEstimatedLaunchTimeLocked(int i, String str, long j) {
        Long l = (Long) this.mEstimatedLaunchTimes.get(i, str);
        if (l != null && l.longValue() >= j - this.mLaunchTimeAllowanceMs) {
            return l.longValue();
        }
        this.mHandler.obtainMessage(0, i, 0, str).sendToTarget();
        this.mEstimatedLaunchTimes.add(i, str, Long.MAX_VALUE);
        return Long.MAX_VALUE;
    }

    public PcConstants getPcConstants() {
        return this.mPcConstants;
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (jobStatus.job.isPrefetch()) {
            SparseArrayMap sparseArrayMap = this.mTrackedJobs;
            int i = jobStatus.sourceUserId;
            String str = jobStatus.sourcePackageName;
            ArraySet arraySet = (ArraySet) sparseArrayMap.get(i, str);
            if (arraySet == null) {
                arraySet = new ArraySet();
                this.mTrackedJobs.add(i, str, arraySet);
            }
            long millis = JobSchedulerService.sSystemClock.millis();
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (arraySet.add(jobStatus) && arraySet.size() == 1 && !willBeLaunchedSoonLocked(i, str, millis)) {
                updateThresholdAlarmLocked(i, millis, elapsedRealtime, str);
            }
            updateConstraintLocked(jobStatus, millis, elapsedRealtime);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        int i = jobStatus.sourceUserId;
        SparseArrayMap sparseArrayMap = this.mTrackedJobs;
        String str = jobStatus.sourcePackageName;
        ArraySet arraySet = (ArraySet) sparseArrayMap.get(i, str);
        if (arraySet != null && arraySet.remove(jobStatus) && arraySet.size() == 0) {
            this.mThresholdAlarmListener.removeAlarmForKey(UserPackage.of(i, str));
        }
    }

    public final boolean maybeUpdateConstraintForPkgLocked(int i, long j, long j2, String str) {
        ArraySet arraySet = (ArraySet) this.mTrackedJobs.get(i, str);
        if (arraySet == null) {
            return false;
        }
        boolean z = false;
        for (int i2 = 0; i2 < arraySet.size(); i2++) {
            z |= updateConstraintLocked((JobStatus) arraySet.valueAt(i2), j, j2);
        }
        return z;
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onAppRemovedLocked(int i, String str) {
        if (str == null) {
            Slog.wtf("JobScheduler.Prefetch", "Told app removed but given null package name.");
            return;
        }
        int userId = UserHandle.getUserId(i);
        this.mTrackedJobs.delete(userId, str);
        this.mEstimatedLaunchTimes.delete(userId, str);
        this.mThresholdAlarmListener.removeAlarmForKey(UserPackage.of(userId, str));
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onConstantsUpdatedLocked() {
        if (this.mPcConstants.mShouldReevaluateConstraints) {
            AppSchedulingModuleThread.getHandler().post(new Runnable() { // from class: com.android.server.job.controllers.PrefetchController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PrefetchController prefetchController = PrefetchController.this;
                    prefetchController.getClass();
                    ArraySet arraySet = new ArraySet();
                    synchronized (prefetchController.mLock) {
                        try {
                            JobSchedulerService.sElapsedRealtimeClock.getClass();
                            long elapsedRealtime = SystemClock.elapsedRealtime();
                            long millis = JobSchedulerService.sSystemClock.millis();
                            for (int i = 0; i < prefetchController.mTrackedJobs.numMaps(); i++) {
                                int keyAt = prefetchController.mTrackedJobs.keyAt(i);
                                int i2 = 0;
                                while (i2 < prefetchController.mTrackedJobs.numElementsForKey(keyAt)) {
                                    String str = (String) prefetchController.mTrackedJobs.keyAt(i, i2);
                                    int i3 = i2;
                                    long j = elapsedRealtime;
                                    long j2 = elapsedRealtime;
                                    int i4 = keyAt;
                                    if (prefetchController.maybeUpdateConstraintForPkgLocked(keyAt, millis, j, str)) {
                                        arraySet.addAll((ArraySet) prefetchController.mTrackedJobs.valueAt(i, i3));
                                    }
                                    if (!prefetchController.willBeLaunchedSoonLocked(i4, str, millis)) {
                                        prefetchController.updateThresholdAlarmLocked(i4, millis, j2, str);
                                    }
                                    i2 = i3 + 1;
                                    keyAt = i4;
                                    elapsedRealtime = j2;
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    if (arraySet.size() > 0) {
                        prefetchController.mStateChangedListener.onControllerStateChanged(arraySet);
                    }
                }
            });
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onSystemServicesReady() {
        this.mAppWidgetManager = (AppWidgetManager) this.mContext.getSystemService(AppWidgetManager.class);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onUidBiasChangedLocked(int i, int i2, int i3) {
        if ((i3 == 40) != (i2 == 40)) {
            this.mHandler.obtainMessage(2, i, 0).sendToTarget();
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onUserRemovedLocked(int i) {
        this.mTrackedJobs.delete(i);
        this.mEstimatedLaunchTimes.delete(i);
        this.mThresholdAlarmListener.removeAlarmsForUserId(i);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void prepareForUpdatedConstantsLocked() {
        this.mPcConstants.mShouldReevaluateConstraints = false;
    }

    @Override // com.android.server.job.controllers.StateController
    public final void processConstantLocked(DeviceConfig.Properties properties, String str) {
        PcConstants pcConstants = this.mPcConstants;
        pcConstants.getClass();
        PrefetchController prefetchController = PrefetchController.this;
        if (str.equals("pc_launch_time_allowance_ms")) {
            long j = properties.getLong(str, 1800000L);
            pcConstants.LAUNCH_TIME_ALLOWANCE_MS = j;
            long min = Math.min(7200000L, Math.max(0L, j));
            if (prefetchController.mLaunchTimeAllowanceMs != min) {
                prefetchController.mLaunchTimeAllowanceMs = min;
                pcConstants.mShouldReevaluateConstraints = true;
                return;
            }
            return;
        }
        if (str.equals("pc_launch_time_threshold_ms")) {
            long j2 = properties.getLong(str, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
            pcConstants.LAUNCH_TIME_THRESHOLD_MS = j2;
            long min2 = Math.min(BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, Math.max(ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS, j2));
            if (prefetchController.mLaunchTimeThresholdMs != min2) {
                prefetchController.mLaunchTimeThresholdMs = min2;
                pcConstants.mShouldReevaluateConstraints = true;
                prefetchController.mThresholdAlarmListener.setMinTimeBetweenAlarmsMs(min2 / 10);
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void startTrackingLocked() {
        UsageStatsService.this.mEstimatedLaunchTimeChangedListeners.add(this.mEstimatedLaunchTimeChangedListener);
    }

    public final boolean updateConstraintLocked(JobStatus jobStatus, long j, long j2) {
        boolean z;
        AppWidgetManager appWidgetManager;
        int i = jobStatus.sourceUid;
        JobSchedulerService jobSchedulerService = this.mService;
        if (jobSchedulerService.getUidBias(i) == 40) {
            z = jobSchedulerService.isCurrentlyRunningLocked(jobStatus);
        } else {
            int i2 = jobStatus.sourceUserId;
            String str = jobStatus.sourcePackageName;
            z = willBeLaunchedSoonLocked(i2, str, j) || ((appWidgetManager = this.mAppWidgetManager) != null && appWidgetManager.isBoundWidgetPackage(str, i2));
        }
        return jobStatus.setConstraintSatisfied(8388608, j2, z);
    }

    public final void updateThresholdAlarmLocked(int i, long j, long j2, String str) {
        ArraySet arraySet = (ArraySet) this.mTrackedJobs.get(i, str);
        ThresholdAlarmListener thresholdAlarmListener = this.mThresholdAlarmListener;
        if (arraySet == null || arraySet.size() == 0) {
            thresholdAlarmListener.removeAlarmForKey(UserPackage.of(i, str));
            return;
        }
        long nextEstimatedLaunchTimeLocked = getNextEstimatedLaunchTimeLocked(i, str, j);
        if (nextEstimatedLaunchTimeLocked != Long.MAX_VALUE) {
            long j3 = nextEstimatedLaunchTimeLocked - j;
            long j4 = this.mLaunchTimeThresholdMs;
            if (j3 > j4) {
                thresholdAlarmListener.addAlarm(j2 + (nextEstimatedLaunchTimeLocked - (j + j4)), UserPackage.of(i, str));
                return;
            }
        }
        thresholdAlarmListener.removeAlarmForKey(UserPackage.of(i, str));
    }

    public final boolean willBeLaunchedSoonLocked(int i, String str, long j) {
        return getNextEstimatedLaunchTimeLocked(i, str, j) <= (j + this.mLaunchTimeThresholdMs) - this.mLaunchTimeAllowanceMs;
    }
}

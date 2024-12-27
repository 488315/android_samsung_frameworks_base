package com.android.server.job;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AppGlobals;
import android.app.UidObserver;
import android.app.compat.CompatChanges;
import android.app.job.IJobScheduler;
import android.app.job.IUserVisibleJobObserver;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobSnapshot;
import android.app.job.JobWorkItem;
import android.app.job.UserVisibleJobSummary;
import android.app.usage.UsageStatsManagerInternal;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.PermissionChecker;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.BatteryManager;
import android.os.BatteryManagerInternal;
import android.os.BatteryStatsInternal;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.LimitExceededException;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.os.WorkSource;
import android.os.storage.StorageManagerInternal;
import android.provider.DeviceConfig;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.IntArray;
import android.util.KeyValueListParser;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseArrayMap;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseSetArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.RingBuffer;
import com.android.internal.util.jobs.ArrayUtils;
import com.android.internal.util.jobs.DumpUtils;
import com.android.modules.expresslog.Counter;
import com.android.modules.expresslog.Histogram;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.AppStateTracker;
import com.android.server.AppStateTrackerImpl;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleInternal;
import com.android.server.LocalServices;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.am.mars.filter.filter.JobSchedulerPackageFilter;
import com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.clipboard.ClipboardService;
import com.android.server.input.KeyboardMetricsCollector;
import com.android.server.job.JobConcurrencyManager;
import com.android.server.job.JobPackageTracker;
import com.android.server.job.JobSchedulerInternal;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.JobStore.ReadJobMapFromDiskRunnable;
import com.android.server.job.PendingJobQueue;
import com.android.server.job.controllers.BackgroundJobsController;
import com.android.server.job.controllers.BatteryController;
import com.android.server.job.controllers.ComponentController;
import com.android.server.job.controllers.ConnectivityController;
import com.android.server.job.controllers.ContentObserverController;
import com.android.server.job.controllers.DeviceIdleJobsController;
import com.android.server.job.controllers.FlexibilityController;
import com.android.server.job.controllers.IdleController;
import com.android.server.job.controllers.JobStatus;
import com.android.server.job.controllers.PrefetchController;
import com.android.server.job.controllers.QuotaController;
import com.android.server.job.controllers.StateController;
import com.android.server.job.controllers.StorageController;
import com.android.server.job.controllers.TimeController;
import com.android.server.job.controllers.UidRestrictController;
import com.android.server.job.restrictions.JobRestriction;
import com.android.server.job.restrictions.OlafRestriction;
import com.android.server.job.restrictions.ThermalStatusRestriction;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.pm.UserManagerInternal;
import com.android.server.usage.AppStandbyInternal;
import com.android.server.usage.UsageStatsService;
import com.android.server.utils.quota.Categorizer;
import com.android.server.utils.quota.Category;
import com.android.server.utils.quota.CountQuotaTracker;
import dalvik.annotation.optimization.NeverCompile;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import libcore.util.EmptyArray;

public final class JobSchedulerService extends SystemService {
    public static final boolean DEBUG;
    public static final boolean DEBUG_STANDBY;
    public static final Category QUOTA_TRACKER_CATEGORY_ANR;
    public static final Category QUOTA_TRACKER_CATEGORY_DISABLED;
    public static final Category QUOTA_TRACKER_CATEGORY_SCHEDULE_LOGGED;
    public static final Category QUOTA_TRACKER_CATEGORY_SCHEDULE_PERSISTED;
    public static final Category QUOTA_TRACKER_CATEGORY_TIMEOUT_EJ;
    public static final Category QUOTA_TRACKER_CATEGORY_TIMEOUT_REG;
    public static final Category QUOTA_TRACKER_CATEGORY_TIMEOUT_TOTAL;
    public static final Category QUOTA_TRACKER_CATEGORY_TIMEOUT_UIJ;
    public static final AnonymousClass1 sElapsedRealtimeClock;
    public static final Histogram sEnqueuedJwiHighWaterMarkLogger;
    public static final Histogram sInitialJobEstimatedNetworkDownloadKBLogger;
    public static final Histogram sInitialJobEstimatedNetworkUploadKBLogger;
    public static final Histogram sInitialJwiEstimatedNetworkDownloadKBLogger;
    public static final Histogram sInitialJwiEstimatedNetworkUploadKBLogger;
    public static final Histogram sJobMinimumChunkKBLogger;
    public static final Histogram sJwiMinimumChunkKBLogger;
    public static Clock sSystemClock;
    public static Clock sUptimeMillisClock;
    public static UsageStatsManagerInternal sUsageStatsManagerInternal;
    public final AcceptCounter mAcceptCounter;
    public final ActivityManagerInternal mActivityManagerInternal;
    public final AppStandbyInternal mAppStandbyInternal;
    AppStateTrackerImpl mAppStateTracker;
    public final SparseBooleanArray mBackingUpUids;
    final BatteryStateTracker mBatteryStateTracker;
    public final BatteryStatsInternal mBatteryStatsInternal;
    public final AnonymousClass3 mBroadcastReceiver;
    public final JobSchedulerService$$ExternalSyntheticLambda0 mCancelJobDueToUserRemovalConsumer;
    public final ArraySet mChangedJobList;
    public final SparseArray mCloudMediaProviderPackages;
    public final JobConcurrencyManager mConcurrencyManager;
    public final ConnectivityController mConnectivityController;
    public final Constants mConstants;
    public final ConstantsObserver mConstantsObserver;
    public final List mControllers;
    public final ArrayMap mDebuggableApps;
    public final DeferrableNetworkJobCounter mDeferrableNetworkJobCounter;
    public final DeviceIdleJobsController mDeviceIdleJobsController;
    public final FlexibilityController mFlexibilityController;
    public final JobHandler mHandler;
    public final JobSchedulerService$$ExternalSyntheticLambda1 mIsUidActivePredicate;
    public final RingBuffer mJobCountBuffer;
    public final MaybeReadyJobCounter mJobCounter;
    public final JobPackageTracker mJobPackageTracker;
    final List mJobRestrictions;
    public final JobSchedulerStub mJobSchedulerStub;
    public final CountDownLatch mJobStoreLoadedLatch;
    public final JobSchedulerService$$ExternalSyntheticLambda3 mJobTimeUpdater;
    public final JobStore mJobs;
    public int mLastCancelledJobIndex;
    public final long[] mLastCancelledJobTimeElapsed;
    public final JobStatus[] mLastCancelledJobs;
    public int mLastCompletedJobIndex;
    public final long[] mLastCompletedJobTimeElapsed;
    public final JobStatus[] mLastCompletedJobs;
    public DeviceIdleInternal mLocalDeviceIdleController;
    public final PackageManagerInternal mLocalPM;
    public final Object mLock;
    public final MaybeReadyJobQueueFunctor mMaybeQueueFunctor;
    public final PendingJobQueue mPendingJobQueue;
    public final SparseArrayMap mPendingJobReasonCache;
    public final SparseArray mPermissionCache;
    public final PrefetchController mPrefetchController;
    public final QuotaController mQuotaController;
    public final CountQuotaTracker mQuotaTracker;
    public final ReadyJobQueueFunctor mReadyQueueFunctor;
    public boolean mReadyToRock;
    public boolean mReportedActive;
    public final List mRestrictiveControllers;
    public final CountDownLatch mStartControllerTrackingLatch;
    public int[] mStartedUsers;
    public final StorageController mStorageController;
    public final AnonymousClass3 mTimeSetReceiver;
    public final SparseIntArray mUidBiasOverride;
    public final SparseIntArray mUidCapabilities;
    public final AnonymousClass4 mUidObserver;
    public final SparseIntArray mUidProcStates;
    public final SparseSetArray mUidToPackageCache;
    public final RemoteCallbackList mUserVisibleJobObservers;

    /* renamed from: com.android.server.job.JobSchedulerService$1, reason: invalid class name */
    public final class AnonymousClass1 extends MySimpleClock {
        public final /* synthetic */ int $r8$classId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ AnonymousClass1(ZoneId zoneId, int i) {
            super(zoneId);
            this.$r8$classId = i;
        }

        @Override // com.android.server.job.JobSchedulerService.MySimpleClock, java.time.Clock, java.time.InstantSource
        public final long millis() {
            switch (this.$r8$classId) {
                case 0:
                    return SystemClock.uptimeMillis();
                default:
                    return SystemClock.elapsedRealtime();
            }
        }
    }

    /* renamed from: com.android.server.job.JobSchedulerService$3, reason: invalid class name */
    public final class AnonymousClass3 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass3(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            ArraySet jobsByUid;
            switch (this.$r8$classId) {
                case 0:
                    String action = intent.getAction();
                    boolean z = JobSchedulerService.DEBUG;
                    if (z) {
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m("Receieved: ", action, "JobScheduler");
                    }
                    Uri data = intent.getData();
                    String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                    int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                    int i = 0;
                    if ("android.intent.action.PACKAGE_CHANGED".equals(action)) {
                        synchronized (((JobSchedulerService) this.this$0).mPermissionCache) {
                            ((JobSchedulerService) this.this$0).mPermissionCache.remove(intExtra);
                        }
                        if (schemeSpecificPart == null || intExtra == -1) {
                            Slog.w("JobScheduler", "PACKAGE_CHANGED for " + schemeSpecificPart + " / uid " + intExtra);
                            return;
                        }
                        String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_component_name_list");
                        if (stringArrayExtra != null) {
                            int length = stringArrayExtra.length;
                            while (true) {
                                if (i < length) {
                                    if (stringArrayExtra[i].equals(schemeSpecificPart)) {
                                        boolean z2 = JobSchedulerService.DEBUG;
                                        if (z2) {
                                            Slog.d("JobScheduler", "Package state change: ".concat(schemeSpecificPart));
                                        }
                                        try {
                                            int userId = UserHandle.getUserId(intExtra);
                                            int applicationEnabledSetting = AppGlobals.getPackageManager().getApplicationEnabledSetting(schemeSpecificPart, userId);
                                            if (applicationEnabledSetting == 2 || applicationEnabledSetting == 3) {
                                                if (z2) {
                                                    Slog.d("JobScheduler", "Removing jobs for package " + schemeSpecificPart + " in user " + userId);
                                                }
                                                synchronized (((JobSchedulerService) this.this$0).mLock) {
                                                    JobSchedulerService.m608$$Nest$mcancelJobsForPackageAndUidLocked((JobSchedulerService) this.this$0, schemeSpecificPart, intExtra, true, 7, "app disabled");
                                                }
                                            }
                                        } catch (RemoteException | IllegalArgumentException unused) {
                                        }
                                    } else {
                                        i++;
                                    }
                                }
                            }
                            if (JobSchedulerService.DEBUG) {
                                DeviceIdleController$$ExternalSyntheticOutline0.m("Something in ", schemeSpecificPart, " changed. Reevaluating controller states.", "JobScheduler");
                            }
                            synchronized (((JobSchedulerService) this.this$0).mLock) {
                                try {
                                    for (int size = ((ArrayList) ((JobSchedulerService) this.this$0).mControllers).size() - 1; size >= 0; size--) {
                                        ((StateController) ((ArrayList) ((JobSchedulerService) this.this$0).mControllers).get(size)).reevaluateStateLocked(intExtra);
                                    }
                                } finally {
                                }
                            }
                            return;
                        }
                        return;
                    }
                    if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                        synchronized (((JobSchedulerService) this.this$0).mPermissionCache) {
                            ((JobSchedulerService) this.this$0).mPermissionCache.remove(intExtra);
                        }
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            return;
                        }
                        synchronized (((JobSchedulerService) this.this$0).mLock) {
                            ((JobSchedulerService) this.this$0).mUidToPackageCache.remove(intExtra);
                        }
                        return;
                    }
                    if ("android.intent.action.PACKAGE_FULLY_REMOVED".equals(action)) {
                        synchronized (((JobSchedulerService) this.this$0).mPermissionCache) {
                            ((JobSchedulerService) this.this$0).mPermissionCache.remove(intExtra);
                        }
                        if (z) {
                            Slog.d("JobScheduler", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(intExtra, "Removing jobs for ", schemeSpecificPart, " (uid=", ")"));
                        }
                        synchronized (((JobSchedulerService) this.this$0).mLock) {
                            try {
                                ((JobSchedulerService) this.this$0).mUidToPackageCache.remove(intExtra);
                                JobSchedulerService.m608$$Nest$mcancelJobsForPackageAndUidLocked((JobSchedulerService) this.this$0, schemeSpecificPart, intExtra, true, 7, "app uninstalled");
                                while (i < ((ArrayList) ((JobSchedulerService) this.this$0).mControllers).size()) {
                                    ((StateController) ((ArrayList) ((JobSchedulerService) this.this$0).mControllers).get(i)).onAppRemovedLocked(intExtra, schemeSpecificPart);
                                    i++;
                                }
                                ((JobSchedulerService) this.this$0).mDebuggableApps.remove(schemeSpecificPart);
                                ((JobSchedulerService) this.this$0).mConcurrencyManager.onAppRemovedLocked(intExtra, schemeSpecificPart);
                            } finally {
                            }
                        }
                        return;
                    }
                    if ("android.intent.action.UID_REMOVED".equals(action)) {
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            return;
                        }
                        synchronized (((JobSchedulerService) this.this$0).mLock) {
                            ((JobSchedulerService) this.this$0).mUidBiasOverride.delete(intExtra);
                            ((JobSchedulerService) this.this$0).mUidCapabilities.delete(intExtra);
                            ((JobSchedulerService) this.this$0).mUidProcStates.delete(intExtra);
                        }
                        return;
                    }
                    if ("android.intent.action.USER_ADDED".equals(action)) {
                        int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", 0);
                        synchronized (((JobSchedulerService) this.this$0).mLock) {
                            while (i < ((ArrayList) ((JobSchedulerService) this.this$0).mControllers).size()) {
                                try {
                                    ((StateController) ((ArrayList) ((JobSchedulerService) this.this$0).mControllers).get(i)).onUserAddedLocked(intExtra2);
                                    i++;
                                } finally {
                                }
                            }
                        }
                        return;
                    }
                    if ("android.intent.action.USER_REMOVED".equals(action)) {
                        int intExtra3 = intent.getIntExtra("android.intent.extra.user_handle", 0);
                        if (z) {
                            AnyMotionDetector$$ExternalSyntheticOutline0.m(intExtra3, "Removing jobs for user: ", "JobScheduler");
                        }
                        synchronized (((JobSchedulerService) this.this$0).mLock) {
                            try {
                                ((JobSchedulerService) this.this$0).mUidToPackageCache.clear();
                                JobSchedulerService jobSchedulerService = (JobSchedulerService) this.this$0;
                                jobSchedulerService.getClass();
                                jobSchedulerService.mJobs.forEachJob(new JobSchedulerService$$ExternalSyntheticLambda5(intExtra3, 2), jobSchedulerService.mCancelJobDueToUserRemovalConsumer);
                                while (i < ((ArrayList) ((JobSchedulerService) this.this$0).mControllers).size()) {
                                    ((StateController) ((ArrayList) ((JobSchedulerService) this.this$0).mControllers).get(i)).onUserRemovedLocked(intExtra3);
                                    i++;
                                }
                            } finally {
                            }
                        }
                        ((JobSchedulerService) this.this$0).mConcurrencyManager.onUserRemoved(intExtra3);
                        synchronized (((JobSchedulerService) this.this$0).mPermissionCache) {
                            try {
                                for (int size2 = ((JobSchedulerService) this.this$0).mPermissionCache.size() - 1; size2 >= 0; size2--) {
                                    if (intExtra3 == UserHandle.getUserId(((JobSchedulerService) this.this$0).mPermissionCache.keyAt(size2))) {
                                        ((JobSchedulerService) this.this$0).mPermissionCache.removeAt(size2);
                                    }
                                }
                            } finally {
                            }
                        }
                        return;
                    }
                    if (!"android.intent.action.QUERY_PACKAGE_RESTART".equals(action)) {
                        if (!"android.intent.action.PACKAGE_RESTARTED".equals(action) || intExtra == -1) {
                            return;
                        }
                        if (z) {
                            Slog.d("JobScheduler", "Removing jobs for pkg " + schemeSpecificPart + " at uid " + intExtra);
                        }
                        synchronized (((JobSchedulerService) this.this$0).mLock) {
                            JobSchedulerService.m608$$Nest$mcancelJobsForPackageAndUidLocked((JobSchedulerService) this.this$0, schemeSpecificPart, intExtra, false, 0, "app force stopped");
                        }
                        return;
                    }
                    if (intExtra != -1) {
                        synchronized (((JobSchedulerService) this.this$0).mLock) {
                            jobsByUid = ((JobSchedulerService) this.this$0).mJobs.getJobsByUid(intExtra);
                        }
                        for (int size3 = jobsByUid.size() - 1; size3 >= 0; size3--) {
                            if (((JobStatus) jobsByUid.valueAt(size3)).sourcePackageName.equals(schemeSpecificPart)) {
                                if (JobSchedulerService.DEBUG) {
                                    Slog.d("JobScheduler", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(intExtra, "Restart query: package ", schemeSpecificPart, " at uid ", " has jobs"));
                                }
                                setResultCode(-1);
                                return;
                            }
                        }
                        return;
                    }
                    return;
                case 1:
                    if ("android.intent.action.TIME_SET".equals(intent.getAction())) {
                        if (JobSchedulerService.sSystemClock.millis() >= ((JobSchedulerService) this.this$0).mJobs.mXmlTimestamp) {
                            Slog.i("JobScheduler", "RTC now valid; recalculating persisted job windows");
                            context.unregisterReceiver(this);
                            JobSchedulerService jobSchedulerService2 = (JobSchedulerService) this.this$0;
                            jobSchedulerService2.mJobs.mIoHandler.post(jobSchedulerService2.mJobTimeUpdater);
                            return;
                        }
                        return;
                    }
                    return;
                default:
                    ((BatteryStateTracker) this.this$0).onReceiveInternal(intent);
                    return;
            }
        }
    }

    /* renamed from: com.android.server.job.JobSchedulerService$6, reason: invalid class name */
    public final class AnonymousClass6 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            JobStatus jobStatus = (JobStatus) obj;
            JobStatus jobStatus2 = (JobStatus) obj2;
            int i = jobStatus.callingUid;
            int i2 = jobStatus2.callingUid;
            int id = jobStatus.job.getId();
            int id2 = jobStatus2.job.getId();
            if (i != i2) {
                if (i >= i2) {
                    return 1;
                }
            } else if (id >= id2) {
                return id > id2 ? 1 : 0;
            }
            return -1;
        }
    }

    public final class AcceptCounter {
        public long batchNetwork;
        public long batchOthers;
        public long batchPrefetch;
        public long batchStandbyBucket;
        public long unbatchAttempts;
        public long unbatchExpedited;
        public long unbatchNetwork;
        public long unbatchOthers;
        public long unbatchOverrideNone;
        public long unbatchPrefetch;

        public final String toString() {
            return "u:" + String.format("%d,%d,%d,%d,%d,%d", Long.valueOf(this.unbatchOverrideNone), Long.valueOf(this.unbatchExpedited), Long.valueOf(this.unbatchPrefetch), Long.valueOf(this.unbatchAttempts), Long.valueOf(this.unbatchNetwork), Long.valueOf(this.unbatchOthers)) + ",b:" + String.format("%d,%d,%d,%d", Long.valueOf(this.batchStandbyBucket), Long.valueOf(this.batchPrefetch), Long.valueOf(this.batchNetwork), Long.valueOf(this.batchOthers));
        }
    }

    final class BatteryStateTracker extends BroadcastReceiver implements BatteryManagerInternal.ChargingPolicyChangeListener {
        public int mBatteryLevel;
        public boolean mBatteryNotLow;
        public boolean mCharging;
        public int mChargingPolicy;
        public AnonymousClass3 mMonitor;
        public boolean mPowerConnected;
        public int mLastBatterySeq = -1;
        public final BatteryManagerInternal mBatteryManagerInternal = (BatteryManagerInternal) LocalServices.getService(BatteryManagerInternal.class);

        public BatteryStateTracker() {
        }

        public final boolean isConsideredCharging() {
            int i;
            if (this.mCharging) {
                return true;
            }
            if (this.mPowerConnected && (i = this.mChargingPolicy) != Integer.MIN_VALUE) {
                return this.mBatteryLevel >= 70 && BatteryManager.isAdaptiveChargingPolicy(i);
            }
            return false;
        }

        public final boolean isMonitoring() {
            return this.mMonitor != null;
        }

        public final void onChargingPolicyChanged(int i) {
            synchronized (JobSchedulerService.this.mLock) {
                try {
                    if (this.mChargingPolicy == i) {
                        return;
                    }
                    if (JobSchedulerService.DEBUG) {
                        Slog.i("JobScheduler", "Charging policy changed from " + this.mChargingPolicy + " to " + i);
                    }
                    boolean isConsideredCharging = isConsideredCharging();
                    this.mChargingPolicy = i;
                    if (Trace.isTagEnabled(524288L)) {
                        Trace.instantForTrack(524288L, "JobScheduler", "CHARGING POLICY CHANGED#" + this.mChargingPolicy);
                    }
                    if (isConsideredCharging() != isConsideredCharging) {
                        for (int size = ((ArrayList) JobSchedulerService.this.mControllers).size() - 1; size >= 0; size--) {
                            ((StateController) ((ArrayList) JobSchedulerService.this.mControllers).get(size)).onBatteryStateChangedLocked();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            onReceiveInternal(intent);
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x018f A[Catch: all -> 0x0040, TryCatch #0 {all -> 0x0040, blocks: (B:4:0x0013, B:6:0x0021, B:8:0x0025, B:9:0x0043, B:11:0x0047, B:13:0x0182, B:15:0x018f, B:17:0x019c, B:19:0x01ae, B:22:0x004c, B:24:0x0054, B:26:0x0058, B:27:0x0072, B:29:0x0076, B:30:0x0079, B:32:0x0081, B:34:0x0085, B:35:0x009f, B:38:0x00b2, B:40:0x00ba, B:42:0x00be, B:43:0x00d8, B:45:0x00dc, B:47:0x00de, B:48:0x00e2, B:50:0x00ea, B:52:0x00ee, B:53:0x0108, B:55:0x010c, B:57:0x010e, B:58:0x0112, B:60:0x011a, B:62:0x011e, B:63:0x0138, B:65:0x013c, B:68:0x014a, B:70:0x0152, B:72:0x0156, B:73:0x0170, B:75:0x0174), top: B:3:0x0013 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceiveInternal(android.content.Intent r13) {
            /*
                Method dump skipped, instructions count: 434
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.JobSchedulerService.BatteryStateTracker.onReceiveInternal(android.content.Intent):void");
        }

        public final void setMonitorBatteryLocked(boolean z) {
            if (!z) {
                if (this.mMonitor != null) {
                    JobSchedulerService.this.getContext().unregisterReceiver(this.mMonitor);
                    this.mMonitor = null;
                    return;
                }
                return;
            }
            if (this.mMonitor == null) {
                this.mMonitor = new AnonymousClass3(2, this);
                JobSchedulerService.this.getContext().registerReceiver(this.mMonitor, BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.BATTERY_CHANGED"));
            }
        }
    }

    public final class CloudProviderChangeListener implements StorageManagerInternal.CloudProviderChangeListener {
        public CloudProviderChangeListener() {
        }

        public final void onCloudProviderChanged(int i, String str) {
            ProviderInfo resolveContentProvider = JobSchedulerService.this.getContext().createContextAsUser(UserHandle.of(i), 0).getPackageManager().resolveContentProvider(str, PackageManager.ComponentInfoFlags.of(0L));
            String str2 = resolveContentProvider == null ? null : resolveContentProvider.packageName;
            synchronized (JobSchedulerService.this.mLock) {
                try {
                    String str3 = (String) JobSchedulerService.this.mCloudMediaProviderPackages.get(i);
                    if (!Objects.equals(str3, str2)) {
                        if (JobSchedulerService.DEBUG) {
                            Slog.d("JobScheduler", "Cloud provider of user " + i + " changed from " + str3 + " to " + str2);
                        }
                        JobSchedulerService.this.mCloudMediaProviderPackages.put(i, str2);
                        SomeArgs obtain = SomeArgs.obtain();
                        obtain.argi1 = i;
                        obtain.arg1 = str3;
                        obtain.arg2 = str2;
                        JobSchedulerService.this.mHandler.obtainMessage(9, obtain).sendToTarget();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class Constants {
        public static final SparseIntArray DEFAULT_CONN_TRANSPORT_BATCH_THRESHOLD;
        public static final int DEFAULT_MIN_READY_CPU_ONLY_JOBS_COUNT;
        public static final int DEFAULT_MIN_READY_NON_ACTIVE_JOBS_COUNT;
        public static final long DEFAULT_RUNTIME_FREE_QUOTA_MAX_LIMIT_MS = 1800000;
        public static final long DEFAULT_RUNTIME_MIN_EJ_GUARANTEE_MS = 180000;
        public static final long DEFAULT_RUNTIME_MIN_GUARANTEE_MS = 600000;
        public static final long DEFAULT_RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS;
        public static final long DEFAULT_RUNTIME_MIN_UI_GUARANTEE_MS;
        public static final long DEFAULT_RUNTIME_UI_LIMIT_MS;
        public int API_QUOTA_SCHEDULE_COUNT;
        public boolean API_QUOTA_SCHEDULE_RETURN_FAILURE_RESULT;
        public boolean API_QUOTA_SCHEDULE_THROW_EXCEPTION;
        public long API_QUOTA_SCHEDULE_WINDOW_MS;
        public float CONN_CONGESTION_DELAY_FRAC;
        public float CONN_LOW_SIGNAL_STRENGTH_RELAX_FRAC;
        public long CONN_MAX_CONNECTIVITY_JOB_BATCH_DELAY_MS;
        public float CONN_PREFETCH_RELAX_FRAC;
        public SparseIntArray CONN_TRANSPORT_BATCH_THRESHOLD;
        public long CONN_UPDATE_ALL_JOBS_MIN_INTERVAL_MS;
        public boolean CONN_USE_CELL_SIGNAL_STRENGTH;
        public boolean ENABLE_API_QUOTAS;
        public boolean ENABLE_EXECUTION_SAFEGUARDS_UDC;
        public int EXECUTION_SAFEGUARDS_UDC_ANR_COUNT;
        public long EXECUTION_SAFEGUARDS_UDC_ANR_WINDOW_MS;
        public int EXECUTION_SAFEGUARDS_UDC_TIMEOUT_EJ_COUNT;
        public int EXECUTION_SAFEGUARDS_UDC_TIMEOUT_REG_COUNT;
        public int EXECUTION_SAFEGUARDS_UDC_TIMEOUT_TOTAL_COUNT;
        public int EXECUTION_SAFEGUARDS_UDC_TIMEOUT_UIJ_COUNT;
        public long EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS;
        public float HEAVY_USE_FACTOR;
        public long MAX_CPU_ONLY_JOB_BATCH_DELAY_MS;
        public long MAX_NON_ACTIVE_JOB_BATCH_DELAY_MS;
        public int MAX_NUM_PERSISTED_JOB_WORK_ITEMS;
        public long MIN_EXP_BACKOFF_TIME_MS;
        public long MIN_LINEAR_BACKOFF_TIME_MS;
        public int MIN_READY_CPU_ONLY_JOBS_COUNT;
        public int MIN_READY_NON_ACTIVE_JOBS_COUNT;
        public float MODERATE_USE_FACTOR;
        public boolean PERSIST_IN_SPLIT_FILES;
        public long PREFETCH_FORCE_BATCH_RELAX_THRESHOLD_MS;
        public long RUNTIME_CUMULATIVE_UI_LIMIT_MS;
        public long RUNTIME_FREE_QUOTA_MAX_LIMIT_MS;
        public long RUNTIME_MIN_EJ_GUARANTEE_MS;
        public long RUNTIME_MIN_GUARANTEE_MS;
        public float RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_BUFFER_FACTOR;
        public long RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS;
        public long RUNTIME_MIN_UI_GUARANTEE_MS;
        public long RUNTIME_UI_LIMIT_MS;
        public boolean RUNTIME_USE_DATA_ESTIMATES_FOR_LIMITS;
        public int SYSTEM_STOP_TO_FAILURE_RATIO;

        /* renamed from: -$$Nest$mupdateApiQuotaConstantsLocked, reason: not valid java name */
        public static void m617$$Nest$mupdateApiQuotaConstantsLocked(Constants constants) {
            constants.getClass();
            constants.ENABLE_API_QUOTAS = DeviceConfig.getBoolean("jobscheduler", "enable_api_quotas", true);
            constants.ENABLE_EXECUTION_SAFEGUARDS_UDC = DeviceConfig.getBoolean("jobscheduler", "enable_execution_safeguards_udc", true);
            constants.API_QUOTA_SCHEDULE_COUNT = Math.max(FrameworkStatsLog.CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_MACRO_RAW_SR_MERGE, DeviceConfig.getInt("jobscheduler", "aq_schedule_count", FrameworkStatsLog.CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_MACRO_RAW_SR_MERGE));
            constants.API_QUOTA_SCHEDULE_WINDOW_MS = DeviceConfig.getLong("jobscheduler", "aq_schedule_window_ms", 60000L);
            constants.API_QUOTA_SCHEDULE_THROW_EXCEPTION = DeviceConfig.getBoolean("jobscheduler", "aq_schedule_throw_exception", true);
            constants.API_QUOTA_SCHEDULE_RETURN_FAILURE_RESULT = DeviceConfig.getBoolean("jobscheduler", "aq_schedule_return_failure", false);
            constants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_UIJ_COUNT = Math.max(2, DeviceConfig.getInt("jobscheduler", "es_u_timeout_uij_count", 2));
            constants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_EJ_COUNT = Math.max(2, DeviceConfig.getInt("jobscheduler", "es_u_timeout_ej_count", 5));
            int max = Math.max(2, DeviceConfig.getInt("jobscheduler", "es_u_timeout_reg_count", 3));
            constants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_REG_COUNT = max;
            constants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_TOTAL_COUNT = Math.max(Math.max(constants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_UIJ_COUNT, Math.max(constants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_EJ_COUNT, max)), DeviceConfig.getInt("jobscheduler", "es_u_timeout_total_count", 10));
            constants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS = DeviceConfig.getLong("jobscheduler", "es_u_timeout_window_ms", BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
            constants.EXECUTION_SAFEGUARDS_UDC_ANR_COUNT = Math.max(1, DeviceConfig.getInt("jobscheduler", "es_u_anr_count", 3));
            constants.EXECUTION_SAFEGUARDS_UDC_ANR_WINDOW_MS = DeviceConfig.getLong("jobscheduler", "es_u_anr_window_ms", 21600000L);
        }

        /* renamed from: -$$Nest$mupdateBatchingConstantsLocked, reason: not valid java name */
        public static void m618$$Nest$mupdateBatchingConstantsLocked(Constants constants) {
            constants.getClass();
            int i = JobConcurrencyManager.DEFAULT_CONCURRENCY_LIMIT / 3;
            constants.MIN_READY_CPU_ONLY_JOBS_COUNT = Math.max(0, Math.min(i, DeviceConfig.getInt("jobscheduler", "min_ready_cpu_only_jobs_count", DEFAULT_MIN_READY_CPU_ONLY_JOBS_COUNT)));
            constants.MIN_READY_NON_ACTIVE_JOBS_COUNT = Math.max(0, Math.min(i, DeviceConfig.getInt("jobscheduler", "min_ready_non_active_jobs_count", DEFAULT_MIN_READY_NON_ACTIVE_JOBS_COUNT)));
            constants.MAX_CPU_ONLY_JOB_BATCH_DELAY_MS = DeviceConfig.getLong("jobscheduler", "max_cpu_only_job_batch_delay_ms", 1860000L);
            constants.MAX_NON_ACTIVE_JOB_BATCH_DELAY_MS = DeviceConfig.getLong("jobscheduler", "max_non_active_job_batch_delay_ms", 1860000L);
        }

        /* renamed from: -$$Nest$mupdateConnectivityConstantsLocked, reason: not valid java name */
        public static void m619$$Nest$mupdateConnectivityConstantsLocked(Constants constants) {
            constants.getClass();
            constants.CONN_CONGESTION_DELAY_FRAC = DeviceConfig.getFloat("jobscheduler", "conn_congestion_delay_frac", 0.5f);
            constants.CONN_PREFETCH_RELAX_FRAC = DeviceConfig.getFloat("jobscheduler", "conn_prefetch_relax_frac", 0.5f);
            constants.CONN_USE_CELL_SIGNAL_STRENGTH = DeviceConfig.getBoolean("jobscheduler", "conn_use_cell_signal_strength", true);
            constants.CONN_UPDATE_ALL_JOBS_MIN_INTERVAL_MS = DeviceConfig.getLong("jobscheduler", "conn_update_all_jobs_min_interval_ms", 60000L);
            constants.CONN_LOW_SIGNAL_STRENGTH_RELAX_FRAC = DeviceConfig.getFloat("jobscheduler", "conn_low_signal_strength_relax_frac", 0.5f);
            String string = DeviceConfig.getString("jobscheduler", "conn_transport_batch_threshold", (String) null);
            KeyValueListParser keyValueListParser = new KeyValueListParser(',');
            constants.CONN_TRANSPORT_BATCH_THRESHOLD.clear();
            try {
                keyValueListParser.setString(string);
                for (int size = keyValueListParser.size() - 1; size >= 0; size--) {
                    String keyAt = keyValueListParser.keyAt(size);
                    try {
                        constants.CONN_TRANSPORT_BATCH_THRESHOLD.put(Integer.parseInt(keyAt), Math.max(0, Math.min(JobConcurrencyManager.DEFAULT_CONCURRENCY_LIMIT / 3, keyValueListParser.getInt(keyAt, 1))));
                    } catch (NumberFormatException e) {
                        Slog.e("JobScheduler", "Bad transport string", e);
                    }
                }
            } catch (IllegalArgumentException e2) {
                Slog.wtf("JobScheduler", "Bad string for conn_transport_batch_threshold", e2);
                constants.copyTransportBatchThresholdDefaults();
            }
            constants.CONN_MAX_CONNECTIVITY_JOB_BATCH_DELAY_MS = Math.max(0L, Math.min(BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, DeviceConfig.getLong("jobscheduler", "conn_max_connectivity_job_batch_delay_ms", 1860000L)));
        }

        /* renamed from: -$$Nest$mupdateRuntimeConstantsLocked, reason: not valid java name */
        public static void m620$$Nest$mupdateRuntimeConstantsLocked(Constants constants) {
            constants.getClass();
            DeviceConfig.Properties properties = DeviceConfig.getProperties("jobscheduler", new String[]{"runtime_free_quota_max_limit_ms", "runtime_min_guarantee_ms", "runtime_min_ej_guarantee_ms", "runtime_min_ui_data_transfer_guarantee_buffer_factor", "runtime_min_ui_guarantee_ms", "runtime_ui_limit_ms", "runtime_min_ui_data_transfer_guarantee_ms", "runtime_cumulative_ui_limit_ms", "runtime_use_data_estimates_for_limits"});
            constants.RUNTIME_MIN_GUARANTEE_MS = Math.max(600000L, properties.getLong("runtime_min_guarantee_ms", 600000L));
            constants.RUNTIME_MIN_EJ_GUARANTEE_MS = Math.max(60000L, properties.getLong("runtime_min_ej_guarantee_ms", 180000L));
            constants.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS = Math.max(constants.RUNTIME_MIN_GUARANTEE_MS, properties.getLong("runtime_free_quota_max_limit_ms", 1800000L));
            long max = Math.max(constants.RUNTIME_MIN_GUARANTEE_MS, properties.getLong("runtime_min_ui_guarantee_ms", DEFAULT_RUNTIME_MIN_UI_GUARANTEE_MS));
            constants.RUNTIME_MIN_UI_GUARANTEE_MS = max;
            constants.RUNTIME_UI_LIMIT_MS = Math.max(constants.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS, Math.max(max, properties.getLong("runtime_ui_limit_ms", DEFAULT_RUNTIME_UI_LIMIT_MS)));
            constants.RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_BUFFER_FACTOR = Math.max(1.0f, properties.getFloat("runtime_min_ui_data_transfer_guarantee_buffer_factor", 1.35f));
            constants.RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS = Math.max(constants.RUNTIME_MIN_UI_GUARANTEE_MS, properties.getLong("runtime_min_ui_data_transfer_guarantee_ms", DEFAULT_RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS));
            constants.RUNTIME_CUMULATIVE_UI_LIMIT_MS = Math.max(constants.RUNTIME_UI_LIMIT_MS, properties.getLong("runtime_cumulative_ui_limit_ms", BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS));
            constants.RUNTIME_USE_DATA_ESTIMATES_FOR_LIMITS = properties.getBoolean("runtime_use_data_estimates_for_limits", false);
        }

        static {
            int i = JobConcurrencyManager.DEFAULT_CONCURRENCY_LIMIT;
            DEFAULT_MIN_READY_CPU_ONLY_JOBS_COUNT = Math.min(3, i / 3);
            DEFAULT_MIN_READY_NON_ACTIVE_JOBS_COUNT = Math.min(5, i / 3);
            SparseIntArray sparseIntArray = new SparseIntArray();
            DEFAULT_CONN_TRANSPORT_BATCH_THRESHOLD = sparseIntArray;
            sparseIntArray.put(0, Math.min(3, i / 3));
            long max = Math.max(21600000L, 600000L);
            DEFAULT_RUNTIME_MIN_UI_GUARANTEE_MS = max;
            DEFAULT_RUNTIME_UI_LIMIT_MS = Math.max(43200000L, 1800000L);
            DEFAULT_RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS = Math.max(600000L, max);
        }

        public final void copyTransportBatchThresholdDefaults() {
            for (int size = DEFAULT_CONN_TRANSPORT_BATCH_THRESHOLD.size() - 1; size >= 0; size--) {
                SparseIntArray sparseIntArray = this.CONN_TRANSPORT_BATCH_THRESHOLD;
                SparseIntArray sparseIntArray2 = DEFAULT_CONN_TRANSPORT_BATCH_THRESHOLD;
                sparseIntArray.put(sparseIntArray2.keyAt(size), sparseIntArray2.valueAt(size));
            }
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("Settings:");
            indentingPrintWriter.increaseIndent();
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.MIN_READY_CPU_ONLY_JOBS_COUNT, indentingPrintWriter, "min_ready_cpu_only_jobs_count");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.MIN_READY_NON_ACTIVE_JOBS_COUNT, indentingPrintWriter, "min_ready_non_active_jobs_count");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.MAX_CPU_ONLY_JOB_BATCH_DELAY_MS, indentingPrintWriter, "max_cpu_only_job_batch_delay_ms");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.MAX_NON_ACTIVE_JOB_BATCH_DELAY_MS, indentingPrintWriter, "max_non_active_job_batch_delay_ms");
            indentingPrintWriter.print("heavy_use_factor", Float.valueOf(this.HEAVY_USE_FACTOR)).println();
            indentingPrintWriter.print("moderate_use_factor", Float.valueOf(this.MODERATE_USE_FACTOR)).println();
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.MIN_LINEAR_BACKOFF_TIME_MS, indentingPrintWriter, "min_linear_backoff_time_ms");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.MIN_EXP_BACKOFF_TIME_MS, indentingPrintWriter, "min_exp_backoff_time_ms");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.SYSTEM_STOP_TO_FAILURE_RATIO, indentingPrintWriter, "system_stop_to_failure_ratio");
            indentingPrintWriter.print("conn_congestion_delay_frac", Float.valueOf(this.CONN_CONGESTION_DELAY_FRAC)).println();
            indentingPrintWriter.print("conn_prefetch_relax_frac", Float.valueOf(this.CONN_PREFETCH_RELAX_FRAC)).println();
            indentingPrintWriter.print("conn_use_cell_signal_strength", Boolean.valueOf(this.CONN_USE_CELL_SIGNAL_STRENGTH)).println();
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.CONN_UPDATE_ALL_JOBS_MIN_INTERVAL_MS, indentingPrintWriter, "conn_update_all_jobs_min_interval_ms");
            indentingPrintWriter.print("conn_low_signal_strength_relax_frac", Float.valueOf(this.CONN_LOW_SIGNAL_STRENGTH_RELAX_FRAC)).println();
            indentingPrintWriter.print("conn_transport_batch_threshold", this.CONN_TRANSPORT_BATCH_THRESHOLD.toString()).println();
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.CONN_MAX_CONNECTIVITY_JOB_BATCH_DELAY_MS, indentingPrintWriter, "conn_max_connectivity_job_batch_delay_ms");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(120000L, indentingPrintWriter, "sec_conn_max_connectivity_job_batch_delay_ms");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.PREFETCH_FORCE_BATCH_RELAX_THRESHOLD_MS, indentingPrintWriter, "prefetch_force_batch_relax_threshold_ms");
            indentingPrintWriter.print("enable_api_quotas", Boolean.valueOf(this.ENABLE_API_QUOTAS)).println();
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.API_QUOTA_SCHEDULE_COUNT, indentingPrintWriter, "aq_schedule_count");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.API_QUOTA_SCHEDULE_WINDOW_MS, indentingPrintWriter, "aq_schedule_window_ms");
            indentingPrintWriter.print("aq_schedule_throw_exception", Boolean.valueOf(this.API_QUOTA_SCHEDULE_THROW_EXCEPTION)).println();
            indentingPrintWriter.print("aq_schedule_return_failure", Boolean.valueOf(this.API_QUOTA_SCHEDULE_RETURN_FAILURE_RESULT)).println();
            indentingPrintWriter.print("enable_execution_safeguards_udc", Boolean.valueOf(this.ENABLE_EXECUTION_SAFEGUARDS_UDC)).println();
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_UIJ_COUNT, indentingPrintWriter, "es_u_timeout_uij_count");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_EJ_COUNT, indentingPrintWriter, "es_u_timeout_ej_count");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_REG_COUNT, indentingPrintWriter, "es_u_timeout_reg_count");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_TOTAL_COUNT, indentingPrintWriter, "es_u_timeout_total_count");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS, indentingPrintWriter, "es_u_timeout_window_ms");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.EXECUTION_SAFEGUARDS_UDC_ANR_COUNT, indentingPrintWriter, "es_u_anr_count");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.EXECUTION_SAFEGUARDS_UDC_ANR_WINDOW_MS, indentingPrintWriter, "es_u_anr_window_ms");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.RUNTIME_MIN_GUARANTEE_MS, indentingPrintWriter, "runtime_min_guarantee_ms");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.RUNTIME_MIN_EJ_GUARANTEE_MS, indentingPrintWriter, "runtime_min_ej_guarantee_ms");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS, indentingPrintWriter, "runtime_free_quota_max_limit_ms");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.RUNTIME_MIN_UI_GUARANTEE_MS, indentingPrintWriter, "runtime_min_ui_guarantee_ms");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.RUNTIME_UI_LIMIT_MS, indentingPrintWriter, "runtime_ui_limit_ms");
            indentingPrintWriter.print("runtime_min_ui_data_transfer_guarantee_buffer_factor", Float.valueOf(this.RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_BUFFER_FACTOR)).println();
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS, indentingPrintWriter, "runtime_min_ui_data_transfer_guarantee_ms");
            JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(this.RUNTIME_CUMULATIVE_UI_LIMIT_MS, indentingPrintWriter, "runtime_cumulative_ui_limit_ms");
            indentingPrintWriter.print("runtime_use_data_estimates_for_limits", Boolean.valueOf(this.RUNTIME_USE_DATA_ESTIMATES_FOR_LIMITS)).println();
            indentingPrintWriter.print("persist_in_split_files", Boolean.valueOf(this.PERSIST_IN_SPLIT_FILES)).println();
            indentingPrintWriter.print("max_num_persisted_job_work_items", Integer.valueOf(this.MAX_NUM_PERSISTED_JOB_WORK_ITEMS)).println();
            indentingPrintWriter.decreaseIndent();
        }

        public final void dump(ProtoOutputStream protoOutputStream) {
            protoOutputStream.write(1120986464285L, this.MIN_READY_NON_ACTIVE_JOBS_COUNT);
            protoOutputStream.write(1112396529694L, this.MAX_NON_ACTIVE_JOB_BATCH_DELAY_MS);
            protoOutputStream.write(1103806595080L, this.HEAVY_USE_FACTOR);
            protoOutputStream.write(1103806595081L, this.MODERATE_USE_FACTOR);
            protoOutputStream.write(1112396529681L, this.MIN_LINEAR_BACKOFF_TIME_MS);
            protoOutputStream.write(1112396529682L, this.MIN_EXP_BACKOFF_TIME_MS);
            protoOutputStream.write(1103806595093L, this.CONN_CONGESTION_DELAY_FRAC);
            protoOutputStream.write(1103806595094L, this.CONN_PREFETCH_RELAX_FRAC);
            protoOutputStream.write(1133871366175L, this.ENABLE_API_QUOTAS);
            protoOutputStream.write(1120986464288L, this.API_QUOTA_SCHEDULE_COUNT);
            protoOutputStream.write(1112396529697L, this.API_QUOTA_SCHEDULE_WINDOW_MS);
            protoOutputStream.write(1133871366178L, this.API_QUOTA_SCHEDULE_THROW_EXCEPTION);
            protoOutputStream.write(1133871366179L, this.API_QUOTA_SCHEDULE_RETURN_FAILURE_RESULT);
        }
    }

    public final class ConstantsObserver implements DeviceConfig.OnPropertiesChangedListener {
        public boolean mCacheConfigChanges = false;
        public DeviceConfig.Properties mLastPropertiesPulled;

        public ConstantsObserver() {
        }

        /* JADX WARN: Removed duplicated region for block: B:27:0x0296 A[Catch: all -> 0x0039, TryCatch #1 {all -> 0x0039, blocks: (B:9:0x0029, B:11:0x002d, B:12:0x003e, B:13:0x004a, B:15:0x0050, B:18:0x0059, B:20:0x005d, B:22:0x0084, B:23:0x0089, B:26:0x0293, B:27:0x0296, B:30:0x02a1, B:36:0x02ae, B:38:0x02ba, B:41:0x02cd, B:42:0x02f8, B:53:0x0309, B:57:0x030c, B:58:0x0315, B:59:0x032f, B:60:0x0338, B:73:0x008e, B:76:0x009b, B:79:0x00a8, B:82:0x00b5, B:85:0x00c2, B:88:0x00cf, B:91:0x00dc, B:94:0x00e8, B:97:0x00f5, B:100:0x0102, B:103:0x010f, B:106:0x011c, B:109:0x0129, B:112:0x0135, B:115:0x0142, B:118:0x014f, B:121:0x015c, B:124:0x0169, B:127:0x0175, B:130:0x0182, B:133:0x018f, B:136:0x019c, B:139:0x01a8, B:142:0x01b5, B:145:0x01c2, B:148:0x01cf, B:151:0x01dc, B:154:0x01e9, B:157:0x01f6, B:160:0x0203, B:163:0x0210, B:166:0x021d, B:169:0x022a, B:172:0x0235, B:175:0x0240, B:178:0x024c, B:181:0x0258, B:184:0x0264, B:187:0x0270, B:190:0x027b, B:193:0x0286, B:196:0x0061, B:44:0x02f9, B:46:0x02fd, B:47:0x0305), top: B:8:0x0029, inners: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:40:0x02cb  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x030a  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x0315 A[Catch: all -> 0x0039, TryCatch #1 {all -> 0x0039, blocks: (B:9:0x0029, B:11:0x002d, B:12:0x003e, B:13:0x004a, B:15:0x0050, B:18:0x0059, B:20:0x005d, B:22:0x0084, B:23:0x0089, B:26:0x0293, B:27:0x0296, B:30:0x02a1, B:36:0x02ae, B:38:0x02ba, B:41:0x02cd, B:42:0x02f8, B:53:0x0309, B:57:0x030c, B:58:0x0315, B:59:0x032f, B:60:0x0338, B:73:0x008e, B:76:0x009b, B:79:0x00a8, B:82:0x00b5, B:85:0x00c2, B:88:0x00cf, B:91:0x00dc, B:94:0x00e8, B:97:0x00f5, B:100:0x0102, B:103:0x010f, B:106:0x011c, B:109:0x0129, B:112:0x0135, B:115:0x0142, B:118:0x014f, B:121:0x015c, B:124:0x0169, B:127:0x0175, B:130:0x0182, B:133:0x018f, B:136:0x019c, B:139:0x01a8, B:142:0x01b5, B:145:0x01c2, B:148:0x01cf, B:151:0x01dc, B:154:0x01e9, B:157:0x01f6, B:160:0x0203, B:163:0x0210, B:166:0x021d, B:169:0x022a, B:172:0x0235, B:175:0x0240, B:178:0x024c, B:181:0x0258, B:184:0x0264, B:187:0x0270, B:190:0x027b, B:193:0x0286, B:196:0x0061, B:44:0x02f9, B:46:0x02fd, B:47:0x0305), top: B:8:0x0029, inners: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:59:0x032f A[Catch: all -> 0x0039, TryCatch #1 {all -> 0x0039, blocks: (B:9:0x0029, B:11:0x002d, B:12:0x003e, B:13:0x004a, B:15:0x0050, B:18:0x0059, B:20:0x005d, B:22:0x0084, B:23:0x0089, B:26:0x0293, B:27:0x0296, B:30:0x02a1, B:36:0x02ae, B:38:0x02ba, B:41:0x02cd, B:42:0x02f8, B:53:0x0309, B:57:0x030c, B:58:0x0315, B:59:0x032f, B:60:0x0338, B:73:0x008e, B:76:0x009b, B:79:0x00a8, B:82:0x00b5, B:85:0x00c2, B:88:0x00cf, B:91:0x00dc, B:94:0x00e8, B:97:0x00f5, B:100:0x0102, B:103:0x010f, B:106:0x011c, B:109:0x0129, B:112:0x0135, B:115:0x0142, B:118:0x014f, B:121:0x015c, B:124:0x0169, B:127:0x0175, B:130:0x0182, B:133:0x018f, B:136:0x019c, B:139:0x01a8, B:142:0x01b5, B:145:0x01c2, B:148:0x01cf, B:151:0x01dc, B:154:0x01e9, B:157:0x01f6, B:160:0x0203, B:163:0x0210, B:166:0x021d, B:169:0x022a, B:172:0x0235, B:175:0x0240, B:178:0x024c, B:181:0x0258, B:184:0x0264, B:187:0x0270, B:190:0x027b, B:193:0x0286, B:196:0x0061, B:44:0x02f9, B:46:0x02fd, B:47:0x0305), top: B:8:0x0029, inners: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:60:0x0338 A[Catch: all -> 0x0039, TRY_LEAVE, TryCatch #1 {all -> 0x0039, blocks: (B:9:0x0029, B:11:0x002d, B:12:0x003e, B:13:0x004a, B:15:0x0050, B:18:0x0059, B:20:0x005d, B:22:0x0084, B:23:0x0089, B:26:0x0293, B:27:0x0296, B:30:0x02a1, B:36:0x02ae, B:38:0x02ba, B:41:0x02cd, B:42:0x02f8, B:53:0x0309, B:57:0x030c, B:58:0x0315, B:59:0x032f, B:60:0x0338, B:73:0x008e, B:76:0x009b, B:79:0x00a8, B:82:0x00b5, B:85:0x00c2, B:88:0x00cf, B:91:0x00dc, B:94:0x00e8, B:97:0x00f5, B:100:0x0102, B:103:0x010f, B:106:0x011c, B:109:0x0129, B:112:0x0135, B:115:0x0142, B:118:0x014f, B:121:0x015c, B:124:0x0169, B:127:0x0175, B:130:0x0182, B:133:0x018f, B:136:0x019c, B:139:0x01a8, B:142:0x01b5, B:145:0x01c2, B:148:0x01cf, B:151:0x01dc, B:154:0x01e9, B:157:0x01f6, B:160:0x0203, B:163:0x0210, B:166:0x021d, B:169:0x022a, B:172:0x0235, B:175:0x0240, B:178:0x024c, B:181:0x0258, B:184:0x0264, B:187:0x0270, B:190:0x027b, B:193:0x0286, B:196:0x0061, B:44:0x02f9, B:46:0x02fd, B:47:0x0305), top: B:8:0x0029, inners: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:68:0x0364 A[Catch: all -> 0x038a, TryCatch #2 {all -> 0x038a, blocks: (B:63:0x034b, B:66:0x03d5, B:68:0x0364, B:69:0x038c, B:72:0x039a, B:202:0x03af, B:204:0x03bb, B:206:0x03cc), top: B:62:0x034b }] */
        /* JADX WARN: Removed duplicated region for block: B:69:0x038c A[Catch: all -> 0x038a, TryCatch #2 {all -> 0x038a, blocks: (B:63:0x034b, B:66:0x03d5, B:68:0x0364, B:69:0x038c, B:72:0x039a, B:202:0x03af, B:204:0x03bb, B:206:0x03cc), top: B:62:0x034b }] */
        /* JADX WARN: Removed duplicated region for block: B:70:0x0396  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onPropertiesChanged(android.provider.DeviceConfig.Properties r18) {
            /*
                Method dump skipped, instructions count: 1236
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.JobSchedulerService.ConstantsObserver.onPropertiesChanged(android.provider.DeviceConfig$Properties):void");
        }
    }

    public final class DeferrableNetworkJobCounter {
        public long activeNetwork;
        public long batchDealyExpired;
        public long deferrable;
        public long exemptedStandbyBucket;
        public long notEnoughDeadline;
        public long procState;

        public final String toString() {
            return "n:" + String.format("%d,%d,%d,%d,%d", Long.valueOf(this.batchDealyExpired), Long.valueOf(this.notEnoughDeadline), Long.valueOf(this.exemptedStandbyBucket), Long.valueOf(this.activeNetwork), Long.valueOf(this.procState)) + ",d:" + String.format("%d", Long.valueOf(this.deferrable));
        }
    }

    public final class DeferredJobCounter implements Consumer {
        public int mDeferred;

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            if (((JobStatus) obj).whenStandbyDeferred > 0) {
                this.mDeferred++;
            }
        }
    }

    public final class JobCountPerNetwork {
        public final Network network;
        public int totalJobCount = 0;
        public int unbatchJobCount = 0;
        public int deferedJobCount = 0;
        public boolean hasNetworkJob = false;

        public JobCountPerNetwork(Network network) {
            this.network = network;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            Network network = this.network;
            sb.append(network == null ? "cpu" : network.toString());
            sb.append(String.format(",T:%d,U:%d,D:%d", Integer.valueOf(this.totalJobCount), Integer.valueOf(this.unbatchJobCount), Integer.valueOf(this.deferedJobCount)));
            return sb.toString();
        }
    }

    public final class JobHandler extends Handler {
        public JobHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            synchronized (JobSchedulerService.this.mLock) {
                try {
                    JobSchedulerService jobSchedulerService = JobSchedulerService.this;
                    if (jobSchedulerService.mReadyToRock) {
                        boolean z = true;
                        switch (message.what) {
                            case 0:
                                JobStatus jobStatus = (JobStatus) message.obj;
                                if (jobStatus != null) {
                                    if (jobSchedulerService.isReadyToBeExecutedLocked(jobStatus)) {
                                        JobSchedulerService.this.mJobPackageTracker.notePending(jobStatus);
                                        JobSchedulerService.this.mPendingJobQueue.add(jobStatus);
                                    }
                                    JobSchedulerService.this.mChangedJobList.remove(jobStatus);
                                } else {
                                    Slog.e("JobScheduler", "Given null job to check individually");
                                }
                                JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 1:
                                if (JobSchedulerService.DEBUG) {
                                    Slog.d("JobScheduler", "MSG_CHECK_JOB");
                                }
                                JobSchedulerService jobSchedulerService2 = JobSchedulerService.this;
                                if (jobSchedulerService2.mReportedActive) {
                                    jobSchedulerService2.queueReadyJobsForExecutionLocked();
                                } else {
                                    JobSchedulerService.m613$$Nest$mmaybeQueueReadyJobsForExecutionLocked(jobSchedulerService2);
                                }
                                JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 2:
                                jobSchedulerService.cancelJobImplLocked((JobStatus) message.obj, null, message.arg1, 1, "app no longer allowed to run");
                                JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 3:
                                if (JobSchedulerService.DEBUG) {
                                    Slog.d("JobScheduler", "MSG_CHECK_JOB_GREEDY");
                                }
                                JobSchedulerService.this.queueReadyJobsForExecutionLocked();
                                JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 4:
                                SomeArgs someArgs = (SomeArgs) message.obj;
                                jobSchedulerService.updateUidState(someArgs.argi1, someArgs.argi2, someArgs.argi3);
                                someArgs.recycle();
                                JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 5:
                                int i = message.arg1;
                                if (message.arg2 == 0) {
                                    z = false;
                                }
                                jobSchedulerService.updateUidState(i, 19, 0);
                                if (z) {
                                    JobSchedulerService.this.cancelJobsForUid(i, 11, 1, true, false, null, "uid gone");
                                }
                                synchronized (JobSchedulerService.this.mLock) {
                                    JobSchedulerService.this.mDeviceIdleJobsController.setUidActiveLocked(i, false);
                                }
                                JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 6:
                                int i2 = message.arg1;
                                synchronized (jobSchedulerService.mLock) {
                                    JobSchedulerService.this.mDeviceIdleJobsController.setUidActiveLocked(i2, true);
                                }
                                JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 7:
                                int i3 = message.arg1;
                                if (message.arg2 != 0) {
                                    jobSchedulerService.cancelJobsForUid(i3, 11, 1, true, false, null, "app uid idle");
                                }
                                synchronized (JobSchedulerService.this.mLock) {
                                    JobSchedulerService.this.mDeviceIdleJobsController.setUidActiveLocked(i3, false);
                                }
                                JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 8:
                                boolean z2 = JobSchedulerService.DEBUG;
                                if (z2) {
                                    Slog.d("JobScheduler", "MSG_CHECK_CHANGED_JOB_LIST");
                                }
                                JobSchedulerService jobSchedulerService3 = JobSchedulerService.this;
                                jobSchedulerService3.mHandler.removeMessages(8);
                                if (z2) {
                                    Slog.d("JobScheduler", "Check changed jobs...");
                                }
                                if (jobSchedulerService3.mChangedJobList.size() != 0) {
                                    ArraySet arraySet = jobSchedulerService3.mChangedJobList;
                                    MaybeReadyJobQueueFunctor maybeReadyJobQueueFunctor = jobSchedulerService3.mMaybeQueueFunctor;
                                    arraySet.forEach(maybeReadyJobQueueFunctor);
                                    maybeReadyJobQueueFunctor.postProcessLocked();
                                    jobSchedulerService3.mChangedJobList.clear();
                                }
                                JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 9:
                                SomeArgs someArgs2 = (SomeArgs) message.obj;
                                synchronized (jobSchedulerService.mLock) {
                                    JobSchedulerService jobSchedulerService4 = JobSchedulerService.this;
                                    final int i4 = someArgs2.argi1;
                                    final String str = (String) someArgs2.arg1;
                                    final String str2 = (String) someArgs2.arg2;
                                    jobSchedulerService4.getClass();
                                    jobSchedulerService4.mJobs.forEachJob(new Predicate() { // from class: com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda10
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            int i5 = i4;
                                            String str3 = str;
                                            String str4 = str2;
                                            JobStatus jobStatus2 = (JobStatus) obj;
                                            if (jobStatus2.sourceUserId == i5) {
                                                String str5 = jobStatus2.sourcePackageName;
                                                if (str5.equals(str3) || str5.equals(str4)) {
                                                    return true;
                                                }
                                            }
                                            return false;
                                        }
                                    }, new JobSchedulerService$$ExternalSyntheticLambda0(jobSchedulerService4, 1));
                                    jobSchedulerService4.mHandler.sendEmptyMessage(8);
                                }
                                someArgs2.recycle();
                                JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 10:
                                IUserVisibleJobObserver iUserVisibleJobObserver = (IUserVisibleJobObserver) message.obj;
                                synchronized (jobSchedulerService.mLock) {
                                    for (int size = ((ArrayList) JobSchedulerService.this.mConcurrencyManager.mActiveServices).size() - 1; size >= 0; size--) {
                                        JobStatus jobStatus2 = ((JobServiceContext) ((ArrayList) JobSchedulerService.this.mConcurrencyManager.mActiveServices).get(size)).mRunningJob;
                                        if (jobStatus2 != null && jobStatus2.isUserVisibleJob()) {
                                            try {
                                                iUserVisibleJobObserver.onUserVisibleJobStateChanged(jobStatus2.getUserVisibleJobSummary(), true);
                                            } catch (RemoteException unused) {
                                            }
                                        }
                                    }
                                }
                                JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 11:
                                SomeArgs someArgs3 = (SomeArgs) message.obj;
                                UserVisibleJobSummary userVisibleJobSummary = ((JobStatus) someArgs3.arg2).getUserVisibleJobSummary();
                                boolean z3 = someArgs3.argi1 == 1;
                                for (int beginBroadcast = JobSchedulerService.this.mUserVisibleJobObservers.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                                    try {
                                        JobSchedulerService.this.mUserVisibleJobObservers.getBroadcastItem(beginBroadcast).onUserVisibleJobStateChanged(userVisibleJobSummary, z3);
                                    } catch (RemoteException unused2) {
                                    }
                                }
                                JobSchedulerService.this.mUserVisibleJobObservers.finishBroadcast();
                                someArgs3.recycle();
                                JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            default:
                                JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class JobOperationGroupBlockData {
        public long begin;
        public int deferedCount;
        public int networkJobGroupCount;
        public int operationCount;

        public final String toString() {
            return DateFormat.format("yyyy-MM-dd HH:mm:ss", this.begin) + "," + this.operationCount + "," + this.networkJobGroupCount + "," + this.deferedCount;
        }
    }

    public final class JobSchedulerStub extends IJobScheduler.Stub {
        public JobSchedulerStub() {
        }

        public static String validateNamespace(String str) {
            String sanitizeNamespace = JobScheduler.sanitizeNamespace(str);
            if (sanitizeNamespace == null) {
                return sanitizeNamespace;
            }
            if (sanitizeNamespace.isEmpty()) {
                throw new IllegalArgumentException("namespace cannot be empty");
            }
            if (sanitizeNamespace.length() <= 1000) {
                return sanitizeNamespace.intern();
            }
            throw new IllegalArgumentException("namespace cannot be more than 1000 characters");
        }

        public final boolean canRunUserInitiatedJobs(String str) {
            int callingUid = Binder.getCallingUid();
            int packageUid = JobSchedulerService.this.mLocalPM.getPackageUid(str, 0L, UserHandle.getUserId(callingUid));
            if (callingUid == packageUid) {
                return PermissionChecker.checkPermissionForPreflight(JobSchedulerService.this.getContext(), "android.permission.RUN_USER_INITIATED_JOBS", -1, packageUid, str) == 0;
            }
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "Uid ", " cannot query canRunUserInitiatedJobs for package ", str));
        }

        public final void cancel(String str, int i) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                JobSchedulerService.this.cancelJob(callingUid, i, callingUid, 1, validateNamespace(str));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void cancelAll() {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                JobSchedulerService.this.cancelJobsForUid(callingUid, 1, 0, false, false, null, "cancelAll() called by app, callingUid=" + callingUid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void cancelAllInNamespace(String str) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                JobSchedulerService.this.cancelJobsForUid(callingUid, 1, 0, false, true, validateNamespace(str), "cancelAllInNamespace() called by app, callingUid=" + callingUid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpAndUsageStatsPermission(JobSchedulerService.this.getContext(), "JobScheduler", printWriter)) {
                boolean z = false;
                int i = -1;
                if (!ArrayUtils.isEmpty(strArr)) {
                    int i2 = 0;
                    boolean z2 = false;
                    while (true) {
                        if (i2 >= strArr.length) {
                            break;
                        }
                        String str = strArr[i2];
                        if ("-h".equals(str)) {
                            BatteryService$$ExternalSyntheticOutline0.m(printWriter, "Job Scheduler (jobscheduler) dump options:", "  [-h] [package] ...", "    -h: print this help", "  [package] is an optional package name to limit the output to.");
                            return;
                        }
                        if (!"-a".equals(str)) {
                            if ("--proto".equals(str)) {
                                z2 = true;
                            } else {
                                if ("--force-olaf-restrict".equals(str)) {
                                    int i3 = i2 + 1;
                                    if (i3 >= strArr.length) {
                                        printWriter.println("Error: --force-olaf-restrict needs an argument [true|false]");
                                        return;
                                    }
                                    for (JobRestriction jobRestriction : JobSchedulerService.this.mJobRestrictions) {
                                        if (jobRestriction instanceof OlafRestriction) {
                                            OlafRestriction olafRestriction = (OlafRestriction) jobRestriction;
                                            printWriter.println("Setting force restrict as " + Boolean.parseBoolean(strArr[i3]) + " for olafRest...");
                                            boolean parseBoolean = Boolean.parseBoolean(strArr[i3]);
                                            if (olafRestriction.mForceRestricted != parseBoolean) {
                                                olafRestriction.mForceRestricted = parseBoolean;
                                                olafRestriction.mService.onRestrictionStateChanged(olafRestriction, parseBoolean);
                                            }
                                        }
                                    }
                                    return;
                                }
                                if ("--disable-olaf-restrict".equals(str)) {
                                    int i4 = i2 + 1;
                                    if (i4 >= strArr.length) {
                                        printWriter.println("Error: --disable-olaf-restrict needs an argument [true|false]");
                                        return;
                                    }
                                    for (JobRestriction jobRestriction2 : JobSchedulerService.this.mJobRestrictions) {
                                        if (jobRestriction2 instanceof OlafRestriction) {
                                            OlafRestriction olafRestriction2 = (OlafRestriction) jobRestriction2;
                                            printWriter.println("Setting force disabled as " + Boolean.parseBoolean(strArr[i4]) + " for olafRest...");
                                            boolean parseBoolean2 = Boolean.parseBoolean(strArr[i4]);
                                            if (olafRestriction2.mForceDisabled != parseBoolean2) {
                                                olafRestriction2.mForceDisabled = parseBoolean2;
                                                olafRestriction2.mService.onRestrictionStateChanged(olafRestriction2, !parseBoolean2);
                                            }
                                        }
                                    }
                                    return;
                                }
                                if (str.length() > 0 && str.charAt(0) == '-') {
                                    printWriter.println("Unknown option: ".concat(str));
                                    return;
                                }
                            }
                        }
                        i2++;
                    }
                    if (i2 < strArr.length) {
                        String str2 = strArr[i2];
                        try {
                            i = JobSchedulerService.this.getContext().getPackageManager().getPackageUid(str2, 4194304);
                        } catch (PackageManager.NameNotFoundException unused) {
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(printWriter, "Invalid package: ", str2);
                            return;
                        }
                    }
                    z = z2;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (z) {
                        JobSchedulerService.this.dumpInternalProto(fileDescriptor, i);
                    } else {
                        JobSchedulerService.this.dumpInternal(new IndentingPrintWriter(printWriter, "  "), i);
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }

        public final JobInfo enforceBuilderApiPermissions(int i, int i2, JobInfo jobInfo) {
            if (jobInfo.getBias() == 0 || JobSchedulerService.m612$$Nest$mhasPermission(i, i2, JobSchedulerService.this, "android.permission.UPDATE_DEVICE_STATS")) {
                return jobInfo;
            }
            if (CompatChanges.isChangeEnabled(300477393L, i)) {
                throw new SecurityException("Apps may not call setBias()");
            }
            Slog.w("JobScheduler", "Uid " + i + " set bias on its job");
            return new JobInfo.Builder(jobInfo).setBias(0).build(false, false, false, false);
        }

        public final void enforceValidJobRequest(int i, int i2, JobInfo jobInfo) {
            PackageManager packageManager = JobSchedulerService.this.getContext().createContextAsUser(UserHandle.getUserHandleForUid(i), 0).getPackageManager();
            ComponentName service = jobInfo.getService();
            try {
                ServiceInfo serviceInfo = packageManager.getServiceInfo(service, 786432);
                if (serviceInfo == null) {
                    throw new IllegalArgumentException("No such service " + service);
                }
                if (serviceInfo.applicationInfo.uid != i) {
                    throw new IllegalArgumentException("uid " + i + " cannot schedule job in " + service.getPackageName());
                }
                if (!"android.permission.BIND_JOB_SERVICE".equals(serviceInfo.permission)) {
                    throw new IllegalArgumentException("Scheduled service " + service + " does not require android.permission.BIND_JOB_SERVICE permission");
                }
                if (jobInfo.isPersisted() && !JobSchedulerService.m612$$Nest$mhasPermission(i, i2, JobSchedulerService.this, "android.permission.RECEIVE_BOOT_COMPLETED")) {
                    throw new IllegalArgumentException("Requested job cannot be persisted without holding android.permission.RECEIVE_BOOT_COMPLETED permission");
                }
                if (jobInfo.getRequiredNetwork() != null && CompatChanges.isChangeEnabled(271850009L, i) && !JobSchedulerService.m612$$Nest$mhasPermission(i, i2, JobSchedulerService.this, "android.permission.ACCESS_NETWORK_STATE")) {
                    throw new SecurityException("android.permission.ACCESS_NETWORK_STATE required for jobs with a connectivity constraint");
                }
            } catch (PackageManager.NameNotFoundException unused) {
                throw new IllegalArgumentException(AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(service, "Tried to schedule job for non-existent component: "));
            }
        }

        public final int enqueue(String str, JobInfo jobInfo, JobWorkItem jobWorkItem) {
            if (JobSchedulerService.DEBUG) {
                Slog.d("JobScheduler", "Enqueueing job: " + jobInfo.toString() + " work: " + jobWorkItem);
            }
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            int userId = UserHandle.getUserId(callingUid);
            enforceValidJobRequest(callingUid, callingPid, jobInfo);
            if (jobWorkItem == null) {
                throw new NullPointerException("work is null");
            }
            int validateJob = validateJob(jobInfo, callingUid, callingPid, -1, null, jobWorkItem);
            if (validateJob != 1) {
                return validateJob;
            }
            String validateNamespace = validateNamespace(str);
            JobInfo enforceBuilderApiPermissions = enforceBuilderApiPermissions(callingUid, callingPid, jobInfo);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return JobSchedulerService.this.scheduleAsPackage(enforceBuilderApiPermissions, jobWorkItem, callingUid, null, userId, validateNamespace, null);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final ParceledListSlice getAllJobSnapshots() {
            ParceledListSlice parceledListSlice;
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("getAllJobSnapshots() is system internal use only.");
            }
            synchronized (JobSchedulerService.this.mLock) {
                final ArrayList arrayList = new ArrayList(JobSchedulerService.this.mJobs.mJobSet.size());
                JobSchedulerService.this.mJobs.forEachJob(new Consumer() { // from class: com.android.server.job.JobSchedulerService$JobSchedulerStub$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        JobSchedulerService.JobSchedulerStub jobSchedulerStub = JobSchedulerService.JobSchedulerStub.this;
                        ArrayList arrayList2 = arrayList;
                        JobStatus jobStatus = (JobStatus) obj;
                        jobSchedulerStub.getClass();
                        arrayList2.add(new JobSnapshot(jobStatus.job, jobStatus.satisfiedConstraints, JobSchedulerService.this.isReadyToBeExecutedLocked(jobStatus)));
                    }
                });
                parceledListSlice = new ParceledListSlice(arrayList);
            }
            return parceledListSlice;
        }

        public final Map getAllPendingJobs() {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ArrayMap m610$$Nest$mgetPendingJobs = JobSchedulerService.m610$$Nest$mgetPendingJobs(JobSchedulerService.this, callingUid);
                ArrayMap arrayMap = new ArrayMap();
                for (int i = 0; i < m610$$Nest$mgetPendingJobs.size(); i++) {
                    arrayMap.put((String) m610$$Nest$mgetPendingJobs.keyAt(i), new ParceledListSlice((List) m610$$Nest$mgetPendingJobs.valueAt(i)));
                }
                return arrayMap;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final ParceledListSlice getAllPendingJobsInNamespace(String str) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return new ParceledListSlice(JobSchedulerService.m611$$Nest$mgetPendingJobsInNamespace(JobSchedulerService.this, callingUid, validateNamespace(str)));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final JobInfo getPendingJob(String str, int i) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return JobSchedulerService.m609$$Nest$mgetPendingJob(callingUid, i, JobSchedulerService.this, validateNamespace(str));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getPendingJobReason(String str, int i) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return JobSchedulerService.this.getPendingJobReason(callingUid, i, validateNamespace(str));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final List getStartedJobs() {
            ArrayList arrayList;
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("getStartedJobs() is system internal use only.");
            }
            synchronized (JobSchedulerService.this.mLock) {
                try {
                    ArraySet arraySet = JobSchedulerService.this.mConcurrencyManager.mRunningJobs;
                    arrayList = new ArrayList(arraySet.size());
                    for (int size = arraySet.size() - 1; size >= 0; size--) {
                        JobStatus jobStatus = (JobStatus) arraySet.valueAt(size);
                        if (jobStatus != null) {
                            arrayList.add(jobStatus.job);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return arrayList;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int handleShellCommand(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, String[] strArr) {
            JobSchedulerService jobSchedulerService = JobSchedulerService.this;
            JobSchedulerShellCommand jobSchedulerShellCommand = new JobSchedulerShellCommand();
            jobSchedulerShellCommand.mInternal = jobSchedulerService;
            jobSchedulerShellCommand.mPM = AppGlobals.getPackageManager();
            return jobSchedulerShellCommand.exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
        }

        public final boolean hasRunUserInitiatedJobsPermission(String str, int i) {
            int packageUid = JobSchedulerService.this.mLocalPM.getPackageUid(str, 0L, i);
            int callingUid = Binder.getCallingUid();
            if (callingUid == packageUid || UserHandle.isCore(callingUid)) {
                return PermissionChecker.checkPermissionForPreflight(JobSchedulerService.this.getContext(), "android.permission.RUN_USER_INITIATED_JOBS", -1, packageUid, str) == 0;
            }
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "Uid ", " cannot query hasRunUserInitiatedJobsPermission for package ", str));
        }

        public final boolean isInStateToScheduleUserInitiatedJobs(int i, int i2, String str) {
            int uidProcessState = JobSchedulerService.this.mActivityManagerInternal.getUidProcessState(i);
            boolean z = JobSchedulerService.DEBUG;
            if (z) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Uid ", " proc state=");
                m.append(ActivityManager.procStateToString(uidProcessState));
                Slog.d("JobScheduler", m.toString());
            }
            if (uidProcessState == 2) {
                return true;
            }
            boolean canScheduleUserInitiatedJobs = JobSchedulerService.this.mActivityManagerInternal.canScheduleUserInitiatedJobs(i, i2, str);
            if (z) {
                Slog.d("JobScheduler", "Uid " + i + " AM.canScheduleUserInitiatedJobs= " + canScheduleUserInitiatedJobs);
            }
            return canScheduleUserInitiatedJobs;
        }

        public final void notePendingUserRequestedAppStop(String str, int i, String str2) {
            notePendingUserRequestedAppStop_enforcePermission();
            if (str == null) {
                throw new NullPointerException("packageName");
            }
            JobSchedulerService.this.notePendingUserRequestedAppStopInternal(str, i, str2);
        }

        public final void registerUserVisibleJobObserver(IUserVisibleJobObserver iUserVisibleJobObserver) {
            registerUserVisibleJobObserver_enforcePermission();
            if (iUserVisibleJobObserver == null) {
                throw new NullPointerException("observer");
            }
            JobSchedulerService.this.mUserVisibleJobObservers.register(iUserVisibleJobObserver);
            JobSchedulerService.this.mHandler.obtainMessage(10, iUserVisibleJobObserver).sendToTarget();
        }

        public final int schedule(String str, JobInfo jobInfo) {
            if (JobSchedulerService.DEBUG) {
                Slog.d("JobScheduler", "Scheduling job: " + jobInfo.toString());
            }
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(callingUid);
            enforceValidJobRequest(callingUid, callingPid, jobInfo);
            int validateJob = validateJob(jobInfo, callingUid, callingPid, -1, null, null);
            if (validateJob != 1) {
                return validateJob;
            }
            String validateNamespace = validateNamespace(str);
            JobInfo enforceBuilderApiPermissions = enforceBuilderApiPermissions(callingUid, callingPid, jobInfo);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return JobSchedulerService.this.scheduleAsPackage(enforceBuilderApiPermissions, null, callingUid, null, userId, validateNamespace, null);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int scheduleAsPackage(String str, JobInfo jobInfo, String str2, int i, String str3) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            if (JobSchedulerService.DEBUG) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(callingUid, "Caller uid ", " scheduling job: ");
                m.append(jobInfo.toString());
                m.append(" on behalf of ");
                m.append(str2);
                m.append("/");
                Slog.d("JobScheduler", m.toString());
            }
            if (str2 == null) {
                throw new NullPointerException("Must specify a package for scheduleAsPackage()");
            }
            if (JobSchedulerService.this.getContext().checkCallingOrSelfPermission("android.permission.UPDATE_DEVICE_STATS") != 0) {
                throw new SecurityException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(callingUid, "Caller uid ", " not permitted to schedule jobs for other apps"));
            }
            enforceValidJobRequest(callingUid, callingPid, jobInfo);
            int validateJob = validateJob(jobInfo, callingUid, callingPid, i, str2, null);
            if (validateJob != 1) {
                return validateJob;
            }
            String validateNamespace = validateNamespace(str);
            JobInfo enforceBuilderApiPermissions = enforceBuilderApiPermissions(callingUid, callingPid, jobInfo);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return JobSchedulerService.this.scheduleAsPackage(enforceBuilderApiPermissions, null, callingUid, str2, i, validateNamespace, str3);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void unregisterUserVisibleJobObserver(IUserVisibleJobObserver iUserVisibleJobObserver) {
            unregisterUserVisibleJobObserver_enforcePermission();
            if (iUserVisibleJobObserver == null) {
                throw new NullPointerException("observer");
            }
            JobSchedulerService.this.mUserVisibleJobObservers.unregister(iUserVisibleJobObserver);
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x0082  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00a6 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:42:0x00a7  */
        /* JADX WARN: Removed duplicated region for block: B:44:0x0097  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int validateJob(android.app.job.JobInfo r8, int r9, int r10, int r11, java.lang.String r12, android.app.job.JobWorkItem r13) {
            /*
                Method dump skipped, instructions count: 279
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.JobSchedulerService.JobSchedulerStub.validateJob(android.app.job.JobInfo, int, int, int, java.lang.String, android.app.job.JobWorkItem):int");
        }

        public final int validateRunUserInitiatedJobsPermission(int i, String str) {
            int checkPermissionForPreflight = PermissionChecker.checkPermissionForPreflight(JobSchedulerService.this.getContext(), "android.permission.RUN_USER_INITIATED_JOBS", -1, i, str);
            if (checkPermissionForPreflight == 2) {
                Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_schedule_failure_uij_no_permission", i);
                throw new SecurityException("android.permission.RUN_USER_INITIATED_JOBS required to schedule user-initiated jobs.");
            }
            if (checkPermissionForPreflight != 1) {
                return 1;
            }
            Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_schedule_failure_uij_no_permission", i);
            return 0;
        }
    }

    public final class LocalService implements JobSchedulerInternal {
        public LocalService() {
        }

        public final void addBackingUpUid(int i) {
            synchronized (JobSchedulerService.this.mLock) {
                JobSchedulerService.this.mBackingUpUids.put(i, true);
            }
        }

        public final void cancelJobsForUid(int i, boolean z, int i2, int i3, String str) {
            JobSchedulerService.this.cancelJobsForUid(i, i2, i3, z, false, null, str);
        }

        public final void clearAllBackingUpUids() {
            synchronized (JobSchedulerService.this.mLock) {
                try {
                    if (JobSchedulerService.this.mBackingUpUids.size() > 0) {
                        JobSchedulerService.this.mBackingUpUids.clear();
                        JobSchedulerService.this.mHandler.obtainMessage(1).sendToTarget();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final String getCloudMediaProviderPackage(int i) {
            return (String) JobSchedulerService.this.mCloudMediaProviderPackages.get(i);
        }

        public final JobSchedulerInternal.JobStorePersistStats getPersistStats() {
            JobSchedulerInternal.JobStorePersistStats jobStorePersistStats;
            synchronized (JobSchedulerService.this.mLock) {
                jobStorePersistStats = new JobSchedulerInternal.JobStorePersistStats(JobSchedulerService.this.mJobs.mPersistInfo);
            }
            return jobStorePersistStats;
        }

        public final List getSystemScheduledOwnJobs(String str) {
            ArrayList arrayList;
            synchronized (JobSchedulerService.this.mLock) {
                arrayList = new ArrayList();
                ArraySet arraySet = (ArraySet) JobSchedulerService.this.mJobs.mJobSet.mJobs.get(1000);
                if (arraySet != null) {
                    for (int size = arraySet.size() - 1; size >= 0; size--) {
                        JobStatus jobStatus = (JobStatus) arraySet.valueAt(size);
                        if (jobStatus.sourceUid == 1000 && Objects.equals(jobStatus.mNamespace, str) && "android".equals(jobStatus.sourcePackageName)) {
                            arrayList.add(jobStatus.job);
                        }
                    }
                }
            }
            return arrayList;
        }

        public final boolean isAppConsideredBuggy(int i, String str, int i2, String str2) {
            return (JobSchedulerService.this.mQuotaTracker.isWithinQuota(i, str, "anr") && JobSchedulerService.this.mQuotaTracker.isWithinQuota(i, str, ".schedulePersisted()") && JobSchedulerService.this.mQuotaTracker.isWithinQuota(i2, str2, "timeout-total")) ? false : true;
        }

        public final boolean isNotificationAssociatedWithAnyUserInitiatedJobs(int i, int i2, String str) {
            if (str == null) {
                return false;
            }
            JobNotificationCoordinator jobNotificationCoordinator = JobSchedulerService.this.mConcurrencyManager.mNotificationCoordinator;
            synchronized (jobNotificationCoordinator.mUijLock) {
                try {
                    IntArray intArray = (IntArray) jobNotificationCoordinator.mUijNotifications.get(i2, str);
                    if (intArray != null) {
                        r0 = intArray.indexOf(i) != -1;
                    }
                } finally {
                }
            }
            return r0;
        }

        public final boolean isNotificationChannelAssociatedWithAnyUserInitiatedJobs(String str, int i, String str2) {
            boolean z = false;
            if (str2 == null || str == null) {
                return false;
            }
            JobNotificationCoordinator jobNotificationCoordinator = JobSchedulerService.this.mConcurrencyManager.mNotificationCoordinator;
            synchronized (jobNotificationCoordinator.mUijLock) {
                try {
                    ArraySet arraySet = (ArraySet) jobNotificationCoordinator.mUijNotificationChannels.get(i, str2);
                    if (arraySet != null) {
                        z = arraySet.contains(str);
                    }
                } finally {
                }
            }
            return z;
        }

        public final void removeBackingUpUid(int i) {
            synchronized (JobSchedulerService.this.mLock) {
                try {
                    JobSchedulerService.this.mBackingUpUids.delete(i);
                    if (JobSchedulerService.this.mJobs.countJobsForUid(i) > 0) {
                        JobSchedulerService.this.mHandler.obtainMessage(1).sendToTarget();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void reportAppUsage(String str, int i) {
            JobSchedulerService.this.getClass();
        }
    }

    public final class MaybeReadyJobCounter {
        public long mBeginTime = 0;
        public int mOperationCount = 0;
        public int mDeferedCount = 0;
        public int mNetworkJobGroupCount = 0;

        public MaybeReadyJobCounter() {
        }
    }

    public final class MaybeReadyJobQueueFunctor implements Consumer {
        final ArrayMap mBatches = new ArrayMap();
        final List runnableJobs = new ArrayList();
        public final ArraySet mUnbatchedJobs = new ArraySet();
        public final ArrayMap mUnbatchedJobCount = new ArrayMap();

        public MaybeReadyJobQueueFunctor() {
            reset();
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x01f3  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0202  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x0231  */
        /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:34:0x0214  */
        /* JADX WARN: Removed duplicated region for block: B:65:0x0188  */
        /* JADX WARN: Removed duplicated region for block: B:66:0x018e  */
        @Override // java.util.function.Consumer
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void accept(java.lang.Object r20) {
            /*
                Method dump skipped, instructions count: 669
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.JobSchedulerService.MaybeReadyJobQueueFunctor.accept(java.lang.Object):void");
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v28 */
        /* JADX WARN: Type inference failed for: r7v29, types: [int] */
        /* JADX WARN: Type inference failed for: r7v33 */
        public void postProcessLocked() {
            int i;
            String sb;
            int i2;
            int i3;
            ArraySet arraySet = this.mUnbatchedJobs;
            if (JobSchedulerService.DEBUG) {
                Slog.d("JobScheduler", "maybeQueueReadyJobsForExecutionLocked: " + this.mUnbatchedJobs.size() + " unbatched jobs.");
            }
            long millis = JobSchedulerService.sSystemClock.millis();
            ArrayList arrayList = new ArrayList(this.mBatches.size());
            boolean z = true;
            int size = this.mBatches.size() - 1;
            int i4 = 0;
            while (true) {
                if (size < 0) {
                    break;
                }
                Network network = (Network) this.mBatches.keyAt(size);
                Integer num = (Integer) this.mUnbatchedJobCount.get(network);
                if (num != null) {
                    i2 = num.intValue();
                    i4 += i2;
                } else {
                    i2 = 0;
                }
                ArraySet arraySet2 = (ArraySet) this.mBatches.valueAt(size);
                JobCountPerNetwork jobCountPerNetwork = new JobCountPerNetwork(network);
                int size2 = arraySet2.size();
                jobCountPerNetwork.totalJobCount = size2;
                if (network != null && size2 > 0) {
                    jobCountPerNetwork.hasNetworkJob = z;
                }
                if (network == null) {
                    arrayList.add(jobCountPerNetwork);
                } else if (i2 > 0) {
                    if (JobSchedulerService.DEBUG) {
                        StringBuilder sb2 = new StringBuilder("maybeQueueReadyJobsForExecutionLocked: piggybacking ");
                        sb2.append(arraySet2.size() - i2);
                        sb2.append(" jobs on ");
                        sb2.append(network);
                        BootReceiver$$ExternalSyntheticOutline0.m(sb2, " because of unbatched job", "JobScheduler");
                    }
                    jobCountPerNetwork.unbatchJobCount = i2;
                    arrayList.add(jobCountPerNetwork);
                    arraySet.addAll(arraySet2);
                } else {
                    ConnectivityController.CachedNetworkMetadata networkMetadata = JobSchedulerService.this.mConnectivityController.getNetworkMetadata(network);
                    NetworkCapabilities networkCapabilities = networkMetadata != null ? networkMetadata.networkCapabilities : null;
                    if (networkCapabilities == null) {
                        Slog.e("JobScheduler", "Couldn't get NetworkCapabilities for network " + network);
                    } else {
                        int[] transportTypes = networkCapabilities.getTransportTypes();
                        int length = transportTypes.length;
                        ?? r7 = z;
                        int i5 = 0;
                        while (i5 < length) {
                            int max = Math.max((int) r7, JobSchedulerService.this.mConstants.CONN_TRANSPORT_BATCH_THRESHOLD.get(transportTypes[i5]));
                            i5++;
                            i4 = i4;
                            r7 = max;
                        }
                        i3 = i4;
                        if (arraySet2.size() >= r7) {
                            Slog.i("JobScheduler", "maybeQueueReadyJobsForExecutionLocked: [BatchNetworkJob] " + arraySet2.size() + " batched network jobs meet requirement for " + network);
                            jobCountPerNetwork.unbatchJobCount = i2;
                            arrayList.add(jobCountPerNetwork);
                            arraySet.addAll(arraySet2);
                        } else {
                            jobCountPerNetwork.deferedJobCount = arraySet2.size();
                            arrayList.add(jobCountPerNetwork);
                        }
                        size--;
                        i4 = i3;
                        z = true;
                    }
                }
                i3 = i4;
                size--;
                i4 = i3;
                z = true;
            }
            ArraySet arraySet3 = (ArraySet) this.mBatches.get(null);
            if (arraySet3 != null) {
                Flags.batchActiveBucketJobs();
                int i6 = JobSchedulerService.this.mConstants.MIN_READY_NON_ACTIVE_JOBS_COUNT;
                if (arraySet.size() > 0) {
                    if (JobSchedulerService.DEBUG) {
                        Integer num2 = (Integer) this.mUnbatchedJobCount.get(null);
                        Slog.d("JobScheduler", "maybeQueueReadyJobsForExecutionLocked: piggybacking " + (arraySet3.size() - (num2 == null ? 0 : num2.intValue())) + " non-network jobs");
                    }
                    arraySet.addAll(arraySet3);
                } else if (arraySet3.size() >= i6) {
                    if (JobSchedulerService.DEBUG) {
                        Slog.d("JobScheduler", "maybeQueueReadyJobsForExecutionLocked: adding " + arraySet3.size() + " batched non-network jobs.");
                    }
                    arraySet.addAll(arraySet3);
                }
            }
            arraySet.removeIf(new JobSchedulerService$$ExternalSyntheticLambda1(JobSchedulerService.this, 1));
            if (i4 > 0 || arraySet.size() > 0) {
                boolean z2 = JobSchedulerService.DEBUG;
                if (z2) {
                    Slog.d("JobScheduler", "maybeQueueReadyJobsForExecutionLocked: Running " + arraySet + " jobs.");
                }
                JobSchedulerService jobSchedulerService = JobSchedulerService.this;
                jobSchedulerService.getClass();
                for (int size3 = arraySet.size() - 1; size3 >= 0; size3--) {
                    jobSchedulerService.mJobPackageTracker.notePending((JobStatus) arraySet.valueAt(size3));
                }
                JobSchedulerService.this.mPendingJobQueue.addAll(arraySet);
                MaybeReadyJobCounter maybeReadyJobCounter = JobSchedulerService.this.mJobCounter;
                maybeReadyJobCounter.getClass();
                long j = maybeReadyJobCounter.mBeginTime;
                if (millis > 600000 + j) {
                    int i7 = maybeReadyJobCounter.mOperationCount;
                    int i8 = maybeReadyJobCounter.mDeferedCount;
                    int i9 = maybeReadyJobCounter.mNetworkJobGroupCount;
                    JobOperationGroupBlockData jobOperationGroupBlockData = new JobOperationGroupBlockData();
                    jobOperationGroupBlockData.begin = j;
                    jobOperationGroupBlockData.operationCount = i7;
                    jobOperationGroupBlockData.deferedCount = i8;
                    jobOperationGroupBlockData.networkJobGroupCount = i9;
                    JobSchedulerService.this.mJobCountBuffer.append(jobOperationGroupBlockData);
                    maybeReadyJobCounter.mBeginTime = millis;
                    i = 0;
                    maybeReadyJobCounter.mOperationCount = 0;
                    maybeReadyJobCounter.mDeferedCount = 0;
                    maybeReadyJobCounter.mNetworkJobGroupCount = 0;
                } else {
                    i = 0;
                }
                maybeReadyJobCounter.mOperationCount++;
                final int i10 = 1;
                if (arrayList.stream().anyMatch(new Predicate() { // from class: com.android.server.job.JobSchedulerService$JobOperationGroup$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        JobSchedulerService.JobCountPerNetwork jobCountPerNetwork2 = (JobSchedulerService.JobCountPerNetwork) obj;
                        switch (i10) {
                            case 0:
                                return jobCountPerNetwork2.hasNetworkJob;
                            default:
                                return jobCountPerNetwork2.deferedJobCount > 0;
                        }
                    }
                })) {
                    maybeReadyJobCounter.mDeferedCount++;
                }
                final int i11 = 0;
                if (arrayList.stream().anyMatch(new Predicate() { // from class: com.android.server.job.JobSchedulerService$JobOperationGroup$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        JobSchedulerService.JobCountPerNetwork jobCountPerNetwork2 = (JobSchedulerService.JobCountPerNetwork) obj;
                        switch (i11) {
                            case 0:
                                return jobCountPerNetwork2.hasNetworkJob;
                            default:
                                return jobCountPerNetwork2.deferedJobCount > 0;
                        }
                    }
                })) {
                    maybeReadyJobCounter.mNetworkJobGroupCount++;
                }
                if (z2) {
                    StringBuilder sb3 = new StringBuilder("maybeQueueReadyJobsForExecutionLocked: ");
                    if (arrayList.isEmpty()) {
                        sb = "empty";
                    } else {
                        StringBuilder sb4 = new StringBuilder();
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            sb4.append(((JobCountPerNetwork) it.next()).toString());
                            sb4.append("/");
                        }
                        sb = sb4.toString();
                    }
                    sb3.append(sb);
                    sb3.append(", acceptCounter= ");
                    sb3.append(JobSchedulerService.this.mAcceptCounter.toString());
                    Slog.d("JobScheduler", sb3.toString());
                }
            } else {
                if (JobSchedulerService.DEBUG) {
                    Slog.d("JobScheduler", "maybeQueueReadyJobsForExecutionLocked: Not running anything.");
                }
                i = 0;
            }
            int size4 = this.runnableJobs.size();
            if (size4 > 0 && size4 != arraySet.size()) {
                synchronized (JobSchedulerService.this.mPendingJobReasonCache) {
                    for (int i12 = i; i12 < size4; i12++) {
                        try {
                            JobStatus jobStatus = (JobStatus) this.runnableJobs.get(i12);
                            if (!arraySet.contains(jobStatus)) {
                                SparseIntArray sparseIntArray = (SparseIntArray) JobSchedulerService.this.mPendingJobReasonCache.get(jobStatus.callingUid, jobStatus.mNamespace);
                                if (sparseIntArray == null) {
                                    sparseIntArray = new SparseIntArray();
                                    JobSchedulerService.this.mPendingJobReasonCache.add(jobStatus.callingUid, jobStatus.mNamespace, sparseIntArray);
                                }
                                sparseIntArray.put(jobStatus.job.getId(), 13);
                            }
                        } finally {
                        }
                    }
                }
            }
            reset();
        }

        public void reset() {
            this.runnableJobs.clear();
            this.mBatches.clear();
            this.mUnbatchedJobs.clear();
            this.mUnbatchedJobCount.clear();
        }
    }

    public abstract class MySimpleClock extends Clock {
        public final ZoneId mZoneId;

        public MySimpleClock(ZoneId zoneId) {
            this.mZoneId = zoneId;
        }

        @Override // java.time.Clock
        public final ZoneId getZone() {
            return this.mZoneId;
        }

        @Override // java.time.Clock, java.time.InstantSource
        public final Instant instant() {
            return Instant.ofEpochMilli(millis());
        }

        @Override // java.time.Clock, java.time.InstantSource
        public abstract long millis();

        @Override // java.time.Clock, java.time.InstantSource
        public final Clock withZone(ZoneId zoneId) {
            return new MySimpleClock(zoneId) { // from class: com.android.server.job.JobSchedulerService.MySimpleClock.1
                @Override // com.android.server.job.JobSchedulerService.MySimpleClock, java.time.Clock, java.time.InstantSource
                public final long millis() {
                    return MySimpleClock.this.millis();
                }
            };
        }
    }

    public final class ReadyJobQueueFunctor implements Consumer {
        public final ArraySet newReadyJobs = new ArraySet();

        public ReadyJobQueueFunctor() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            JobStatus jobStatus = (JobStatus) obj;
            if (JobSchedulerService.this.isReadyToBeExecutedLocked(jobStatus)) {
                if (JobSchedulerService.DEBUG) {
                    Slog.d("JobScheduler", "    queued " + jobStatus.toShortString());
                }
                this.newReadyJobs.add(jobStatus);
            }
        }
    }

    public final class StandbyTracker extends AppStandbyInternal.AppIdleStateChangeListener {
        public StandbyTracker() {
        }

        public final void onAppIdleStateChanged(String str, int i, boolean z, int i2, int i3) {
        }

        public final void onUserInteractionStarted(String str, int i) {
            int packageUid = JobSchedulerService.this.mLocalPM.getPackageUid(str, 8192L, i);
            if (packageUid < 0) {
                return;
            }
            long timeSinceLastJobRun = UsageStatsService.this.mAppStandby.getTimeSinceLastJobRun(str, i);
            long j = timeSinceLastJobRun > 172800000 ? 0L : timeSinceLastJobRun;
            DeferredJobCounter deferredJobCounter = new DeferredJobCounter();
            deferredJobCounter.mDeferred = 0;
            synchronized (JobSchedulerService.this.mLock) {
                JobSchedulerService.this.mJobs.forEachJobForSourceUid(packageUid, deferredJobCounter);
            }
            int i2 = deferredJobCounter.mDeferred;
            if (i2 > 0 || j > 0) {
                JobSchedulerService.this.mBatteryStatsInternal.noteJobsDeferred(packageUid, i2, j);
                FrameworkStatsLog.write_non_chained(85, packageUid, (String) null, deferredJobCounter.mDeferred, j);
            }
        }
    }

    /* renamed from: -$$Nest$mcancelJobsForPackageAndUidLocked, reason: not valid java name */
    public static void m608$$Nest$mcancelJobsForPackageAndUidLocked(JobSchedulerService jobSchedulerService, String str, int i, boolean z, int i2, String str2) {
        jobSchedulerService.getClass();
        if ("android".equals(str)) {
            Slog.wtfStack("JobScheduler", "Can't cancel all jobs for system package");
            return;
        }
        ArraySet arraySet = new ArraySet();
        JobStore jobStore = jobSchedulerService.mJobs;
        ArraySet arraySet2 = (ArraySet) jobStore.mJobSet.mJobs.get(i);
        if (arraySet2 != null) {
            arraySet.addAll((Collection) arraySet2);
        }
        if (z) {
            jobStore.mJobSet.getJobsBySourceUid(i, arraySet);
        }
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            JobStatus jobStatus = (JobStatus) arraySet.valueAt(size);
            if (jobStatus.job.getService().getPackageName().equals(str) || (z && jobStatus.sourcePackageName.equals(str))) {
                jobSchedulerService.cancelJobImplLocked(jobStatus, null, 13, i2, str2);
            }
        }
    }

    /* renamed from: -$$Nest$mgetPendingJob, reason: not valid java name */
    public static JobInfo m609$$Nest$mgetPendingJob(int i, int i2, JobSchedulerService jobSchedulerService, String str) {
        synchronized (jobSchedulerService.mLock) {
            try {
                ArraySet jobsByUid = jobSchedulerService.mJobs.getJobsByUid(i);
                for (int size = jobsByUid.size() - 1; size >= 0; size--) {
                    JobStatus jobStatus = (JobStatus) jobsByUid.valueAt(size);
                    if (jobStatus.job.getId() == i2 && Objects.equals(str, jobStatus.mNamespace)) {
                        return jobStatus.job;
                    }
                }
                return null;
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mgetPendingJobs, reason: not valid java name */
    public static ArrayMap m610$$Nest$mgetPendingJobs(JobSchedulerService jobSchedulerService, int i) {
        jobSchedulerService.getClass();
        ArrayMap arrayMap = new ArrayMap();
        synchronized (jobSchedulerService.mLock) {
            try {
                ArraySet jobsByUid = jobSchedulerService.mJobs.getJobsByUid(i);
                for (int size = jobsByUid.size() - 1; size >= 0; size--) {
                    JobStatus jobStatus = (JobStatus) jobsByUid.valueAt(size);
                    List list = (List) arrayMap.get(jobStatus.mNamespace);
                    if (list == null) {
                        list = new ArrayList();
                        arrayMap.put(jobStatus.mNamespace, list);
                    }
                    list.add(jobStatus.job);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayMap;
    }

    /* renamed from: -$$Nest$mgetPendingJobsInNamespace, reason: not valid java name */
    public static List m611$$Nest$mgetPendingJobsInNamespace(JobSchedulerService jobSchedulerService, int i, String str) {
        ArrayList arrayList;
        synchronized (jobSchedulerService.mLock) {
            try {
                ArraySet jobsByUid = jobSchedulerService.mJobs.getJobsByUid(i);
                arrayList = new ArrayList();
                for (int size = jobsByUid.size() - 1; size >= 0; size--) {
                    JobStatus jobStatus = (JobStatus) jobsByUid.valueAt(size);
                    if (Objects.equals(str, jobStatus.mNamespace)) {
                        arrayList.add(jobStatus.job);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    /* renamed from: -$$Nest$mhasPermission, reason: not valid java name */
    public static boolean m612$$Nest$mhasPermission(int i, int i2, JobSchedulerService jobSchedulerService, String str) {
        boolean z;
        synchronized (jobSchedulerService.mPermissionCache) {
            try {
                SparseArrayMap sparseArrayMap = (SparseArrayMap) jobSchedulerService.mPermissionCache.get(i);
                if (sparseArrayMap == null) {
                    sparseArrayMap = new SparseArrayMap();
                    jobSchedulerService.mPermissionCache.put(i, sparseArrayMap);
                }
                Boolean bool = (Boolean) sparseArrayMap.get(i2, str);
                if (bool != null) {
                    z = bool.booleanValue();
                } else {
                    z = jobSchedulerService.getContext().checkPermission(str, i2, i) == 0;
                    sparseArrayMap.add(i2, str, Boolean.valueOf(z));
                }
            } finally {
            }
        }
        return z;
    }

    /* renamed from: -$$Nest$mmaybeQueueReadyJobsForExecutionLocked, reason: not valid java name */
    public static void m613$$Nest$mmaybeQueueReadyJobsForExecutionLocked(JobSchedulerService jobSchedulerService) {
        JobHandler jobHandler = jobSchedulerService.mHandler;
        jobHandler.removeMessages(1);
        jobHandler.removeMessages(8);
        jobSchedulerService.mChangedJobList.clear();
        if (DEBUG) {
            Slog.d("JobScheduler", "Maybe queuing ready jobs...");
        }
        jobSchedulerService.clearPendingJobQueue();
        jobSchedulerService.stopNonReadyActiveJobsLocked();
        JobStore jobStore = jobSchedulerService.mJobs;
        MaybeReadyJobQueueFunctor maybeReadyJobQueueFunctor = jobSchedulerService.mMaybeQueueFunctor;
        jobStore.forEachJob(maybeReadyJobQueueFunctor);
        maybeReadyJobQueueFunctor.postProcessLocked();
    }

    static {
        boolean isLoggable = Log.isLoggable("JobScheduler", 3);
        DEBUG = isLoggable;
        DEBUG_STANDBY = isLoggable;
        sSystemClock = Clock.systemUTC();
        ZoneOffset zoneOffset = ZoneOffset.UTC;
        sUptimeMillisClock = new AnonymousClass1(zoneOffset, 0);
        sElapsedRealtimeClock = new AnonymousClass1(zoneOffset, 1);
        QUOTA_TRACKER_CATEGORY_SCHEDULE_PERSISTED = new Category(".schedulePersisted()");
        QUOTA_TRACKER_CATEGORY_SCHEDULE_LOGGED = new Category(".schedulePersisted out-of-quota logged");
        QUOTA_TRACKER_CATEGORY_TIMEOUT_UIJ = new Category("timeout-uij");
        QUOTA_TRACKER_CATEGORY_TIMEOUT_EJ = new Category("timeout-ej");
        QUOTA_TRACKER_CATEGORY_TIMEOUT_REG = new Category("timeout-reg");
        QUOTA_TRACKER_CATEGORY_TIMEOUT_TOTAL = new Category("timeout-total");
        QUOTA_TRACKER_CATEGORY_ANR = new Category("anr");
        QUOTA_TRACKER_CATEGORY_DISABLED = new Category("disabled");
        sEnqueuedJwiHighWaterMarkLogger = new Histogram("job_scheduler.value_hist_w_uid_enqueued_work_items_high_water_mark", new Histogram.ScaledRangeOptions(25, 0, 5.0f, 1.4f));
        sInitialJobEstimatedNetworkDownloadKBLogger = new Histogram("job_scheduler.value_hist_initial_job_estimated_network_download_kilobytes", new Histogram.ScaledRangeOptions(50, 0, 32.0f, 1.31f));
        sInitialJwiEstimatedNetworkDownloadKBLogger = new Histogram("job_scheduler.value_hist_initial_jwi_estimated_network_download_kilobytes", new Histogram.ScaledRangeOptions(50, 0, 32.0f, 1.31f));
        sInitialJobEstimatedNetworkUploadKBLogger = new Histogram("job_scheduler.value_hist_initial_job_estimated_network_upload_kilobytes", new Histogram.ScaledRangeOptions(50, 0, 32.0f, 1.31f));
        sInitialJwiEstimatedNetworkUploadKBLogger = new Histogram("job_scheduler.value_hist_initial_jwi_estimated_network_upload_kilobytes", new Histogram.ScaledRangeOptions(50, 0, 32.0f, 1.31f));
        sJobMinimumChunkKBLogger = new Histogram("job_scheduler.value_hist_w_uid_job_minimum_chunk_kilobytes", new Histogram.ScaledRangeOptions(25, 0, 5.0f, 1.76f));
        sJwiMinimumChunkKBLogger = new Histogram("job_scheduler.value_hist_w_uid_jwi_minimum_chunk_kilobytes", new Histogram.ScaledRangeOptions(25, 0, 5.0f, 1.76f));
    }

    /* JADX WARN: Type inference failed for: r2v13, types: [com.android.server.job.JobSchedulerService$4] */
    /* JADX WARN: Type inference failed for: r3v6, types: [com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda3] */
    public JobSchedulerService(Context context) {
        super(context);
        JobStore jobStore;
        this.mJobCountBuffer = new RingBuffer(JobOperationGroupBlockData.class, 150);
        Object obj = new Object();
        this.mLock = obj;
        this.mJobPackageTracker = new JobPackageTracker();
        this.mCloudMediaProviderPackages = new SparseArray();
        this.mUserVisibleJobObservers = new RemoteCallbackList();
        this.mPermissionCache = new SparseArray();
        this.mPendingJobQueue = new PendingJobQueue();
        this.mStartedUsers = EmptyArray.INT;
        this.mLastCompletedJobIndex = 0;
        this.mLastCompletedJobs = new JobStatus[20];
        this.mLastCompletedJobTimeElapsed = new long[20];
        this.mLastCancelledJobIndex = 0;
        boolean z = DEBUG;
        this.mLastCancelledJobs = new JobStatus[z ? 20 : 0];
        this.mLastCancelledJobTimeElapsed = new long[z ? 20 : 0];
        this.mUidBiasOverride = new SparseIntArray();
        this.mUidCapabilities = new SparseIntArray();
        this.mUidProcStates = new SparseIntArray();
        this.mBackingUpUids = new SparseBooleanArray();
        this.mDebuggableApps = new ArrayMap();
        this.mUidToPackageCache = new SparseSetArray();
        this.mChangedJobList = new ArraySet();
        this.mPendingJobReasonCache = new SparseArrayMap();
        int i = 0;
        this.mBroadcastReceiver = new AnonymousClass3(i, this);
        this.mUidObserver = new UidObserver() { // from class: com.android.server.job.JobSchedulerService.4
            public final void onUidActive(int i2) {
                JobSchedulerService.this.mHandler.obtainMessage(6, i2, 0).sendToTarget();
            }

            public final void onUidGone(int i2, boolean z2) {
                JobSchedulerService.this.mHandler.obtainMessage(5, i2, z2 ? 1 : 0).sendToTarget();
            }

            public final void onUidIdle(int i2, boolean z2) {
                JobSchedulerService.this.mHandler.obtainMessage(7, i2, z2 ? 1 : 0).sendToTarget();
            }

            public final void onUidStateChanged(int i2, int i3, long j, int i4) {
                SomeArgs obtain = SomeArgs.obtain();
                obtain.argi1 = i2;
                obtain.argi2 = i3;
                obtain.argi3 = i4;
                JobSchedulerService.this.mHandler.obtainMessage(4, obtain).sendToTarget();
            }
        };
        this.mIsUidActivePredicate = new JobSchedulerService$$ExternalSyntheticLambda1(this, i);
        this.mCancelJobDueToUserRemovalConsumer = new JobSchedulerService$$ExternalSyntheticLambda0(this, 2);
        AnonymousClass3 anonymousClass3 = new AnonymousClass3(1, this);
        final int i2 = 0;
        this.mJobTimeUpdater = new Runnable(this) { // from class: com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda3
            public final /* synthetic */ JobSchedulerService f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i3 = i2;
                JobSchedulerService jobSchedulerService = this.f$0;
                switch (i3) {
                    case 0:
                        jobSchedulerService.getClass();
                        Process.setThreadPriority(-2);
                        final ArrayList arrayList = new ArrayList();
                        final ArrayList arrayList2 = new ArrayList();
                        synchronized (jobSchedulerService.mLock) {
                            try {
                                JobStore jobStore2 = jobSchedulerService.mJobs;
                                jobStore2.getClass();
                                JobSchedulerService.sElapsedRealtimeClock.getClass();
                                final long elapsedRealtime = SystemClock.elapsedRealtime();
                                jobStore2.forEachJob(new Consumer() { // from class: com.android.server.job.JobStore$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj2) {
                                        long j = elapsedRealtime;
                                        ArrayList arrayList3 = arrayList2;
                                        ArrayList arrayList4 = arrayList;
                                        JobStatus jobStatus = (JobStatus) obj2;
                                        Pair pair = jobStatus.mPersistedUtcTimes;
                                        if (pair != null) {
                                            Pair convertRtcBoundsToElapsed = JobStore.convertRtcBoundsToElapsed(pair, j);
                                            JobStatus jobStatus2 = new JobStatus(jobStatus, ((Long) convertRtcBoundsToElapsed.first).longValue(), ((Long) convertRtcBoundsToElapsed.second).longValue(), 0, 0, jobStatus.mLastSuccessfulRunTime, jobStatus.mLastFailedRunTime, jobStatus.mCumulativeExecutionTimeMs);
                                            jobStatus2.prepareLocked();
                                            arrayList3.add(jobStatus2);
                                            arrayList4.add(jobStatus);
                                        }
                                    }
                                });
                                int size = arrayList2.size();
                                for (int i4 = 0; i4 < size; i4++) {
                                    JobStatus jobStatus = (JobStatus) arrayList.get(i4);
                                    JobStatus jobStatus2 = (JobStatus) arrayList2.get(i4);
                                    if (JobSchedulerService.DEBUG) {
                                        Slog.v("JobScheduler", "  replacing " + jobStatus + " with " + jobStatus2);
                                    }
                                    jobSchedulerService.cancelJobImplLocked(jobStatus, jobStatus2, 14, 9, "deferred rtc calculation");
                                }
                            } finally {
                            }
                        }
                        return;
                    default:
                        synchronized (jobSchedulerService.mLock) {
                            try {
                                for (int size2 = ((ArrayList) jobSchedulerService.mControllers).size() - 1; size2 >= 0; size2--) {
                                    ((StateController) ((ArrayList) jobSchedulerService.mControllers).get(size2)).startTrackingLocked();
                                }
                            } finally {
                            }
                        }
                        jobSchedulerService.mStartControllerTrackingLatch.countDown();
                        return;
                }
            }
        };
        this.mReadyQueueFunctor = new ReadyJobQueueFunctor();
        this.mMaybeQueueFunctor = new MaybeReadyJobQueueFunctor();
        this.mLocalPM = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        Objects.requireNonNull(activityManagerInternal);
        this.mActivityManagerInternal = activityManagerInternal;
        JobHandler jobHandler = new JobHandler(AppSchedulingModuleThread.get().getLooper());
        this.mHandler = jobHandler;
        Constants constants = new Constants();
        constants.MIN_READY_CPU_ONLY_JOBS_COUNT = Constants.DEFAULT_MIN_READY_CPU_ONLY_JOBS_COUNT;
        constants.MIN_READY_NON_ACTIVE_JOBS_COUNT = Constants.DEFAULT_MIN_READY_NON_ACTIVE_JOBS_COUNT;
        constants.MAX_CPU_ONLY_JOB_BATCH_DELAY_MS = 1860000L;
        constants.MAX_NON_ACTIVE_JOB_BATCH_DELAY_MS = 1860000L;
        constants.HEAVY_USE_FACTOR = 0.9f;
        constants.MODERATE_USE_FACTOR = 0.5f;
        constants.MIN_LINEAR_BACKOFF_TIME_MS = 10000L;
        constants.MIN_EXP_BACKOFF_TIME_MS = 10000L;
        constants.SYSTEM_STOP_TO_FAILURE_RATIO = 3;
        constants.CONN_CONGESTION_DELAY_FRAC = 0.5f;
        constants.CONN_PREFETCH_RELAX_FRAC = 0.5f;
        constants.CONN_USE_CELL_SIGNAL_STRENGTH = true;
        constants.CONN_UPDATE_ALL_JOBS_MIN_INTERVAL_MS = 60000L;
        constants.CONN_LOW_SIGNAL_STRENGTH_RELAX_FRAC = 0.5f;
        constants.CONN_TRANSPORT_BATCH_THRESHOLD = new SparseIntArray();
        constants.CONN_MAX_CONNECTIVITY_JOB_BATCH_DELAY_MS = 1860000L;
        constants.PREFETCH_FORCE_BATCH_RELAX_THRESHOLD_MS = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
        constants.ENABLE_API_QUOTAS = true;
        constants.API_QUOTA_SCHEDULE_COUNT = FrameworkStatsLog.CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_MACRO_RAW_SR_MERGE;
        constants.API_QUOTA_SCHEDULE_WINDOW_MS = 60000L;
        constants.API_QUOTA_SCHEDULE_THROW_EXCEPTION = true;
        constants.API_QUOTA_SCHEDULE_RETURN_FAILURE_RESULT = false;
        constants.ENABLE_EXECUTION_SAFEGUARDS_UDC = true;
        constants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_UIJ_COUNT = 2;
        constants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_EJ_COUNT = 5;
        constants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_REG_COUNT = 3;
        constants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_TOTAL_COUNT = 10;
        constants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS = BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        constants.EXECUTION_SAFEGUARDS_UDC_ANR_COUNT = 3;
        constants.EXECUTION_SAFEGUARDS_UDC_ANR_WINDOW_MS = 21600000L;
        constants.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS = 1800000L;
        constants.RUNTIME_MIN_GUARANTEE_MS = 600000L;
        constants.RUNTIME_MIN_EJ_GUARANTEE_MS = 180000L;
        constants.RUNTIME_MIN_UI_GUARANTEE_MS = Constants.DEFAULT_RUNTIME_MIN_UI_GUARANTEE_MS;
        constants.RUNTIME_UI_LIMIT_MS = Constants.DEFAULT_RUNTIME_UI_LIMIT_MS;
        constants.RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_BUFFER_FACTOR = 1.35f;
        constants.RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS = Constants.DEFAULT_RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS;
        constants.RUNTIME_CUMULATIVE_UI_LIMIT_MS = BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        constants.RUNTIME_USE_DATA_ESTIMATES_FOR_LIMITS = false;
        constants.PERSIST_IN_SPLIT_FILES = true;
        constants.MAX_NUM_PERSISTED_JOB_WORK_ITEMS = 100000;
        constants.copyTransportBatchThresholdDefaults();
        this.mConstants = constants;
        this.mConstantsObserver = new ConstantsObserver();
        this.mJobSchedulerStub = new JobSchedulerStub();
        this.mConcurrencyManager = new JobConcurrencyManager(this, new JobConcurrencyManager.Injector());
        StandbyTracker standbyTracker = new StandbyTracker();
        sUsageStatsManagerInternal = (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
        CountQuotaTracker countQuotaTracker = new CountQuotaTracker(context, new Categorizer() { // from class: com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda4
            @Override // com.android.server.utils.quota.Categorizer
            public final Category getCategory(String str) {
                JobSchedulerService jobSchedulerService = JobSchedulerService.this;
                jobSchedulerService.getClass();
                boolean equals = "timeout-uij".equals(str);
                Category category = JobSchedulerService.QUOTA_TRACKER_CATEGORY_DISABLED;
                JobSchedulerService.Constants constants2 = jobSchedulerService.mConstants;
                if (equals) {
                    return constants2.ENABLE_EXECUTION_SAFEGUARDS_UDC ? JobSchedulerService.QUOTA_TRACKER_CATEGORY_TIMEOUT_UIJ : category;
                }
                if ("timeout-ej".equals(str)) {
                    return constants2.ENABLE_EXECUTION_SAFEGUARDS_UDC ? JobSchedulerService.QUOTA_TRACKER_CATEGORY_TIMEOUT_EJ : category;
                }
                if ("timeout-reg".equals(str)) {
                    return constants2.ENABLE_EXECUTION_SAFEGUARDS_UDC ? JobSchedulerService.QUOTA_TRACKER_CATEGORY_TIMEOUT_REG : category;
                }
                if ("timeout-total".equals(str)) {
                    return constants2.ENABLE_EXECUTION_SAFEGUARDS_UDC ? JobSchedulerService.QUOTA_TRACKER_CATEGORY_TIMEOUT_TOTAL : category;
                }
                if ("anr".equals(str)) {
                    return constants2.ENABLE_EXECUTION_SAFEGUARDS_UDC ? JobSchedulerService.QUOTA_TRACKER_CATEGORY_ANR : category;
                }
                if (".schedulePersisted()".equals(str)) {
                    return constants2.ENABLE_API_QUOTAS ? JobSchedulerService.QUOTA_TRACKER_CATEGORY_SCHEDULE_PERSISTED : category;
                }
                if (".schedulePersisted out-of-quota logged".equals(str)) {
                    return constants2.ENABLE_API_QUOTAS ? JobSchedulerService.QUOTA_TRACKER_CATEGORY_SCHEDULE_LOGGED : category;
                }
                Slog.wtf("JobScheduler", "Unexpected category tag: " + str);
                return category;
            }
        });
        this.mQuotaTracker = countQuotaTracker;
        updateQuotaTracker();
        countQuotaTracker.setCountLimit(QUOTA_TRACKER_CATEGORY_SCHEDULE_LOGGED, 1, 60000L);
        countQuotaTracker.setCountLimit(QUOTA_TRACKER_CATEGORY_DISABLED, Integer.MAX_VALUE, 60000L);
        AppStandbyInternal appStandbyInternal = (AppStandbyInternal) LocalServices.getService(AppStandbyInternal.class);
        this.mAppStandbyInternal = appStandbyInternal;
        appStandbyInternal.addListener(standbyTracker);
        this.mBatteryStatsInternal = (BatteryStatsInternal) LocalServices.getService(BatteryStatsInternal.class);
        publishLocalService(JobSchedulerInternal.class, new LocalService());
        CountDownLatch countDownLatch = new CountDownLatch(1);
        this.mJobStoreLoadedLatch = countDownLatch;
        synchronized (JobStore.sSingletonLock) {
            try {
                if (JobStore.sSingleton == null) {
                    getContext();
                    JobStore.sSingleton = new JobStore(obj, Environment.getDataDirectory());
                }
                jobStore = JobStore.sSingleton;
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mJobs = jobStore;
        jobStore.mIoHandler.post(jobStore.new ReadJobMapFromDiskRunnable(jobStore.mJobSet, jobStore.mRtcGood, countDownLatch));
        BatteryStateTracker batteryStateTracker = new BatteryStateTracker();
        this.mBatteryStateTracker = batteryStateTracker;
        IntentFilter m = VcnManagementService$$ExternalSyntheticOutline0.m("android.intent.action.BATTERY_LOW", "android.intent.action.BATTERY_OKAY", "android.os.action.CHARGING", "android.os.action.DISCHARGING", "android.intent.action.BATTERY_LEVEL_CHANGED");
        m.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        m.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        getContext().registerReceiver(batteryStateTracker, m);
        batteryStateTracker.mBatteryManagerInternal.registerChargingPolicyChangeListener(batteryStateTracker);
        batteryStateTracker.mBatteryLevel = batteryStateTracker.mBatteryManagerInternal.getBatteryLevel();
        batteryStateTracker.mBatteryNotLow = !batteryStateTracker.mBatteryManagerInternal.getBatteryLevelLow();
        batteryStateTracker.mCharging = batteryStateTracker.mBatteryManagerInternal.isPowered(15);
        batteryStateTracker.mChargingPolicy = batteryStateTracker.mBatteryManagerInternal.getChargingPolicy();
        this.mStartControllerTrackingLatch = new CountDownLatch(1);
        ArrayList arrayList = new ArrayList();
        this.mControllers = arrayList;
        PrefetchController prefetchController = new PrefetchController(this);
        this.mPrefetchController = prefetchController;
        arrayList.add(prefetchController);
        FlexibilityController flexibilityController = new FlexibilityController(this, prefetchController);
        this.mFlexibilityController = flexibilityController;
        arrayList.add(flexibilityController);
        ConnectivityController connectivityController = new ConnectivityController(this, flexibilityController);
        this.mConnectivityController = connectivityController;
        arrayList.add(connectivityController);
        arrayList.add(new TimeController(this));
        IdleController idleController = new IdleController(this, flexibilityController);
        arrayList.add(idleController);
        BatteryController batteryController = new BatteryController(this, flexibilityController);
        arrayList.add(batteryController);
        StorageController storageController = new StorageController(this);
        this.mStorageController = storageController;
        arrayList.add(storageController);
        BackgroundJobsController backgroundJobsController = new BackgroundJobsController(this);
        arrayList.add(backgroundJobsController);
        arrayList.add(new ContentObserverController(this));
        DeviceIdleJobsController deviceIdleJobsController = new DeviceIdleJobsController(this);
        this.mDeviceIdleJobsController = deviceIdleJobsController;
        arrayList.add(deviceIdleJobsController);
        QuotaController quotaController = new QuotaController(this, backgroundJobsController, connectivityController);
        this.mQuotaController = quotaController;
        arrayList.add(quotaController);
        arrayList.add(new ComponentController(this));
        arrayList.add(new UidRestrictController(this));
        final int i3 = 1;
        jobHandler.post(new Runnable(this) { // from class: com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda3
            public final /* synthetic */ JobSchedulerService f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i32 = i3;
                JobSchedulerService jobSchedulerService = this.f$0;
                switch (i32) {
                    case 0:
                        jobSchedulerService.getClass();
                        Process.setThreadPriority(-2);
                        final ArrayList arrayList2 = new ArrayList();
                        final ArrayList arrayList22 = new ArrayList();
                        synchronized (jobSchedulerService.mLock) {
                            try {
                                JobStore jobStore2 = jobSchedulerService.mJobs;
                                jobStore2.getClass();
                                JobSchedulerService.sElapsedRealtimeClock.getClass();
                                final long elapsedRealtime = SystemClock.elapsedRealtime();
                                jobStore2.forEachJob(new Consumer() { // from class: com.android.server.job.JobStore$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj2) {
                                        long j = elapsedRealtime;
                                        ArrayList arrayList3 = arrayList22;
                                        ArrayList arrayList4 = arrayList2;
                                        JobStatus jobStatus = (JobStatus) obj2;
                                        Pair pair = jobStatus.mPersistedUtcTimes;
                                        if (pair != null) {
                                            Pair convertRtcBoundsToElapsed = JobStore.convertRtcBoundsToElapsed(pair, j);
                                            JobStatus jobStatus2 = new JobStatus(jobStatus, ((Long) convertRtcBoundsToElapsed.first).longValue(), ((Long) convertRtcBoundsToElapsed.second).longValue(), 0, 0, jobStatus.mLastSuccessfulRunTime, jobStatus.mLastFailedRunTime, jobStatus.mCumulativeExecutionTimeMs);
                                            jobStatus2.prepareLocked();
                                            arrayList3.add(jobStatus2);
                                            arrayList4.add(jobStatus);
                                        }
                                    }
                                });
                                int size = arrayList22.size();
                                for (int i4 = 0; i4 < size; i4++) {
                                    JobStatus jobStatus = (JobStatus) arrayList2.get(i4);
                                    JobStatus jobStatus2 = (JobStatus) arrayList22.get(i4);
                                    if (JobSchedulerService.DEBUG) {
                                        Slog.v("JobScheduler", "  replacing " + jobStatus + " with " + jobStatus2);
                                    }
                                    jobSchedulerService.cancelJobImplLocked(jobStatus, jobStatus2, 14, 9, "deferred rtc calculation");
                                }
                            } finally {
                            }
                        }
                        return;
                    default:
                        synchronized (jobSchedulerService.mLock) {
                            try {
                                for (int size2 = ((ArrayList) jobSchedulerService.mControllers).size() - 1; size2 >= 0; size2--) {
                                    ((StateController) ((ArrayList) jobSchedulerService.mControllers).get(size2)).startTrackingLocked();
                                }
                            } finally {
                            }
                        }
                        jobSchedulerService.mStartControllerTrackingLatch.countDown();
                        return;
                }
            }
        });
        ArrayList arrayList2 = new ArrayList();
        this.mRestrictiveControllers = arrayList2;
        arrayList2.add(batteryController);
        arrayList2.add(connectivityController);
        arrayList2.add(idleController);
        ArrayList arrayList3 = new ArrayList();
        this.mJobRestrictions = arrayList3;
        final ThermalStatusRestriction thermalStatusRestriction = new ThermalStatusRestriction(this, 4, 12, 5);
        thermalStatusRestriction.mThermalStatus = 0;
        BroadcastReceiver anonymousClass2 = new BroadcastReceiver() { // from class: com.android.server.job.restrictions.ThermalStatusRestriction.2
            public AnonymousClass2() {
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                boolean booleanExtra = intent.getBooleanExtra("job_restriction", false);
                ThermalStatusRestriction thermalStatusRestriction2 = ThermalStatusRestriction.this;
                if (thermalStatusRestriction2.mForceRestricted != booleanExtra) {
                    thermalStatusRestriction2.mForceRestricted = booleanExtra;
                    thermalStatusRestriction2.mService.onRestrictionStateChanged(thermalStatusRestriction2, booleanExtra || thermalStatusRestriction2.mIncreased);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.intent.action.SIOP_LEVEL_CHANGED");
        getContext().registerReceiver(anonymousClass2, intentFilter);
        arrayList3.add(thermalStatusRestriction);
        final OlafRestriction olafRestriction = new OlafRestriction(this, 11, 3, 13);
        olafRestriction.mForceRestricted = false;
        olafRestriction.mOlafOn = false;
        olafRestriction.mOlafUid = 0;
        olafRestriction.mForceDisabled = false;
        getContext().registerReceiver(new BroadcastReceiver() { // from class: com.android.server.job.restrictions.OlafRestriction.1
            public AnonymousClass1() {
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                boolean booleanExtra = intent.getBooleanExtra("job_restriction", false);
                DeviceIdleController$$ExternalSyntheticOutline0.m(" olaf state update: state=", "OlafRestriction", booleanExtra);
                OlafRestriction olafRestriction2 = OlafRestriction.this;
                if (olafRestriction2.mOlafOn != booleanExtra) {
                    olafRestriction2.mOlafOn = booleanExtra;
                    if (booleanExtra) {
                        olafRestriction2.mOlafUid = intent.getIntExtra("olaf_target_uid", 0);
                    } else {
                        olafRestriction2.mOlafUid = 0;
                    }
                    Slog.d("OlafRestriction", " trigger onRestrictionStateChanged.");
                    OlafRestriction olafRestriction3 = OlafRestriction.this;
                    olafRestriction3.mService.onRestrictionStateChanged(olafRestriction3, olafRestriction3.mOlafOn);
                }
            }
        }, BatteryService$$ExternalSyntheticOutline0.m("com.samsung.intent.action.OLAF_STATE_CHANGED"));
        arrayList3.add(olafRestriction);
        if (!jobStore.mRtcGood) {
            Slog.w("JobScheduler", "!!! RTC not yet good; tracking time updates for job scheduling");
            context.registerReceiver(anonymousClass3, new IntentFilter("android.intent.action.TIME_SET"));
        }
        this.mJobCounter = new MaybeReadyJobCounter();
        AcceptCounter acceptCounter = new AcceptCounter();
        acceptCounter.unbatchOverrideNone = 0L;
        acceptCounter.unbatchExpedited = 0L;
        acceptCounter.batchStandbyBucket = 0L;
        acceptCounter.unbatchPrefetch = 0L;
        acceptCounter.batchPrefetch = 0L;
        acceptCounter.unbatchAttempts = 0L;
        acceptCounter.unbatchNetwork = 0L;
        acceptCounter.batchNetwork = 0L;
        acceptCounter.unbatchOthers = 0L;
        acceptCounter.batchOthers = 0L;
        this.mAcceptCounter = acceptCounter;
        DeferrableNetworkJobCounter deferrableNetworkJobCounter = new DeferrableNetworkJobCounter();
        deferrableNetworkJobCounter.batchDealyExpired = 0L;
        deferrableNetworkJobCounter.notEnoughDeadline = 0L;
        deferrableNetworkJobCounter.exemptedStandbyBucket = 0L;
        deferrableNetworkJobCounter.activeNetwork = 0L;
        deferrableNetworkJobCounter.procState = 0L;
        deferrableNetworkJobCounter.deferrable = 0L;
        this.mDeferrableNetworkJobCounter = deferrableNetworkJobCounter;
    }

    public static int safelyScaleBytesToKBForHistogram(long j) {
        long j2 = j / 1000;
        if (j2 > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        if (j2 < -2147483648L) {
            return Integer.MIN_VALUE;
        }
        return (int) j2;
    }

    public static int standbyBucketForPackage(int i, String str, long j) {
        UsageStatsManagerInternal usageStatsManagerInternal = sUsageStatsManagerInternal;
        int standbyBucketToBucketIndex = standbyBucketToBucketIndex(usageStatsManagerInternal != null ? usageStatsManagerInternal.getAppStandbyBucket(i, str, j) : 0);
        if (DEBUG_STANDBY) {
            Slog.v("JobScheduler", str + "/" + i + " standby bucket index: " + standbyBucketToBucketIndex);
        }
        return standbyBucketToBucketIndex;
    }

    public static int standbyBucketToBucketIndex(int i) {
        if (i == 50) {
            return 4;
        }
        if (i > 40) {
            return 5;
        }
        if (i > 30) {
            return 3;
        }
        if (i > 20) {
            return 2;
        }
        if (i > 10) {
            return 1;
        }
        return i > 5 ? 0 : 6;
    }

    public final int adjustJobBias(JobStatus jobStatus, int i) {
        float f;
        ArrayMap arrayMap;
        if (i < 40) {
            JobPackageTracker jobPackageTracker = this.mJobPackageTracker;
            jobPackageTracker.getClass();
            int i2 = jobStatus.sourceUid;
            ArrayMap arrayMap2 = (ArrayMap) jobPackageTracker.mCurDataSet.mEntries.get(i2);
            JobPackageTracker.PackageEntry packageEntry = null;
            String str = jobStatus.sourcePackageName;
            JobPackageTracker.PackageEntry packageEntry2 = arrayMap2 == null ? null : (JobPackageTracker.PackageEntry) arrayMap2.get(str);
            JobPackageTracker.DataSet[] dataSetArr = jobPackageTracker.mLastDataSets;
            JobPackageTracker.DataSet dataSet = dataSetArr[0];
            if (dataSet != null && (arrayMap = (ArrayMap) dataSet.mEntries.get(i2)) != null) {
                packageEntry = (JobPackageTracker.PackageEntry) arrayMap.get(str);
            }
            if (packageEntry2 == null && packageEntry == null) {
                f = FullScreenMagnificationGestureHandler.MAX_SCALE;
            } else {
                long millis = sUptimeMillisClock.millis();
                long pendingTime = packageEntry2 != null ? packageEntry2.getPendingTime(millis) + packageEntry2.getActiveTime(millis) : 0L;
                long totalTime = jobPackageTracker.mCurDataSet.getTotalTime(millis);
                if (packageEntry != null) {
                    pendingTime += packageEntry.getPendingTime(millis) + packageEntry.getActiveTime(millis);
                    totalTime += dataSetArr[0].getTotalTime(millis);
                }
                f = pendingTime / totalTime;
            }
            Constants constants = this.mConstants;
            if (f >= constants.HEAVY_USE_FACTOR) {
                return i - 80;
            }
            if (f >= constants.MODERATE_USE_FACTOR) {
                return i - 40;
            }
        }
        return i;
    }

    public final boolean areUsersStartedLocked(JobStatus jobStatus) {
        boolean contains = ArrayUtils.contains(this.mStartedUsers, jobStatus.sourceUserId);
        int i = jobStatus.callingUid;
        return UserHandle.getUserId(i) == jobStatus.sourceUserId ? contains : contains && ArrayUtils.contains(this.mStartedUsers, UserHandle.getUserId(i));
    }

    public final boolean cancelJob(int i, int i2, int i3, int i4, String str) {
        boolean z;
        synchronized (this.mLock) {
            try {
                JobStatus jobStatus = this.mJobs.mJobSet.get(i, i2, str);
                if (jobStatus != null) {
                    cancelJobImplLocked(jobStatus, null, i4, 0, "cancel() called by app, callingUid=" + i3 + " uid=" + i + " jobId=" + i2);
                }
                z = jobStatus != null;
            } finally {
            }
        }
        return z;
    }

    public final void cancelJobImplLocked(JobStatus jobStatus, JobStatus jobStatus2, int i, int i2, String str) {
        String str2;
        JobSchedulerService jobSchedulerService;
        boolean z = DEBUG;
        if (z) {
            Slog.d("JobScheduler", "CANCEL: " + jobStatus.toShortString());
        }
        jobStatus.unprepareLocked();
        stopTrackingJobLocked(jobStatus, jobStatus2, true);
        if (this.mPendingJobQueue.remove(jobStatus)) {
            this.mJobPackageTracker.noteNonpending(jobStatus);
        }
        this.mChangedJobList.remove(jobStatus);
        if (this.mConcurrencyManager.stopJobOnServiceContextLocked(jobStatus, i, i2, str)) {
            str2 = "JobScheduler";
        } else {
            int i3 = jobStatus.callingUid;
            int i4 = jobStatus.sourceUid;
            boolean z2 = jobStatus.mIsProxyJob;
            int[] iArr = z2 ? new int[]{i4, i3} : new int[]{i4};
            String str3 = jobStatus.sourceTag;
            str2 = "JobScheduler";
            FrameworkStatsLog.write(8, iArr, z2 ? new String[]{null, str3} : new String[]{str3}, jobStatus.batteryName, 3, i2, jobStatus.standbyBucket, jobStatus.mLoggingJobId, jobStatus.hasConstraint(1), jobStatus.hasConstraint(2), jobStatus.hasConstraint(8), jobStatus.hasConstraint(Integer.MIN_VALUE), jobStatus.hasConstraint(1073741824), jobStatus.hasConstraint(4), jobStatus.hasConnectivityConstraint(), jobStatus.hasContentTriggerConstraint(), jobStatus.isRequestedExpeditedJob(), false, i, jobStatus.job.isPrefetch(), jobStatus.job.getPriority(), jobStatus.getEffectivePriority(), jobStatus.getNumPreviousAttempts(), jobStatus.job.getMaxExecutionDelayMillis(), jobStatus.isConstraintSatisfied(1073741824), jobStatus.isConstraintSatisfied(1), jobStatus.isConstraintSatisfied(2), jobStatus.isConstraintSatisfied(8), jobStatus.isConstraintSatisfied(Integer.MIN_VALUE), jobStatus.isConstraintSatisfied(4), jobStatus.isConstraintSatisfied(268435456), jobStatus.isConstraintSatisfied(67108864), 0L, jobStatus.job.isUserInitiated(), false, jobStatus.job.isPeriodic(), jobStatus.job.getMinLatencyMillis(), jobStatus.mTotalNetworkDownloadBytes, jobStatus.mTotalNetworkUploadBytes, jobStatus.getWorkCount(), ActivityManager.processStateAmToProto(this.mUidProcStates.get(i3)), jobStatus.mNamespaceHash, 0L, 0L, 0L, 0L, jobStatus.job.getIntervalMillis(), jobStatus.job.getFlexMillis(), jobStatus.hasFlexibilityConstraint(), jobStatus.isConstraintSatisfied(2097152), jobStatus.mCanApplyTransportAffinities, jobStatus.mNumAppliedFlexibleConstraints, jobStatus.mNumDroppedFlexibleConstraints, jobStatus.getFilteredTraceTag(), jobStatus.getFilteredDebugTags());
        }
        if (jobStatus2 != null) {
            if (z) {
                Slog.i(str2, "Tracking replacement job " + jobStatus2.toShortString());
            }
            jobSchedulerService = this;
            jobSchedulerService.startTrackingJobLocked(jobStatus2, jobStatus);
        } else {
            jobSchedulerService = this;
        }
        reportActiveLocked();
        JobStatus[] jobStatusArr = jobSchedulerService.mLastCancelledJobs;
        if (jobStatusArr.length <= 0 || i2 != 0) {
            return;
        }
        int i5 = jobSchedulerService.mLastCancelledJobIndex;
        jobStatusArr[i5] = jobStatus;
        sElapsedRealtimeClock.getClass();
        jobSchedulerService.mLastCancelledJobTimeElapsed[i5] = SystemClock.elapsedRealtime();
        jobSchedulerService.mLastCancelledJobIndex = (jobSchedulerService.mLastCancelledJobIndex + 1) % jobStatusArr.length;
    }

    public final boolean cancelJobsForUid(int i, int i2, int i3, boolean z, boolean z2, String str, String str2) {
        boolean z3 = false;
        if (i == 1000 && (!z2 || str == null)) {
            Slog.wtfStack("JobScheduler", "Can't cancel all jobs for system uid");
            return false;
        }
        synchronized (this.mLock) {
            try {
                ArraySet arraySet = new ArraySet();
                ArraySet arraySet2 = (ArraySet) this.mJobs.mJobSet.mJobs.get(i);
                if (arraySet2 != null) {
                    arraySet.addAll((Collection) arraySet2);
                }
                if (z) {
                    this.mJobs.mJobSet.getJobsBySourceUid(i, arraySet);
                }
                for (int i4 = 0; i4 < arraySet.size(); i4++) {
                    JobStatus jobStatus = (JobStatus) arraySet.valueAt(i4);
                    if (!z2 || Objects.equals(str, jobStatus.mNamespace)) {
                        cancelJobImplLocked(jobStatus, null, i2, i3, str2);
                        z3 = true;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z3;
    }

    public final JobRestriction checkIfRestricted(JobStatus jobStatus) {
        for (int size = this.mJobRestrictions.size() - 1; size >= 0; size--) {
            JobRestriction jobRestriction = (JobRestriction) this.mJobRestrictions.get(size);
            if (jobRestriction.isJobRestricted(jobStatus, evaluateJobBiasLocked(jobStatus))) {
                return jobRestriction;
            }
        }
        return null;
    }

    public final void clearPendingJobQueue() {
        PendingJobQueue pendingJobQueue = this.mPendingJobQueue;
        pendingJobQueue.mNeedToResetIterators = true;
        while (true) {
            JobStatus next = pendingJobQueue.next();
            if (next == null) {
                break;
            } else {
                this.mJobPackageTracker.noteNonpending(next);
            }
        }
        pendingJobQueue.mSize = 0;
        for (int size = pendingJobQueue.mCurrentQueues.size() - 1; size >= 0; size--) {
            PendingJobQueue.AppJobQueue appJobQueue = (PendingJobQueue.AppJobQueue) pendingJobQueue.mCurrentQueues.valueAt(size);
            ((ArrayList) appJobQueue.mJobs).clear();
            appJobQueue.mCurIndex = 0;
            pendingJobQueue.mAppJobQueuePool.release(appJobQueue);
        }
        pendingJobQueue.mCurrentQueues.clear();
        pendingJobQueue.mOrderedQueues.clear();
    }

    public final WorkSource deriveWorkSource(int i, String str) {
        if (!WorkSource.isChainedBatteryAttributionEnabled(getContext())) {
            return str == null ? new WorkSource(i) : new WorkSource(i, str);
        }
        WorkSource workSource = new WorkSource();
        workSource.createWorkChain().addNode(i, str).addNode(1000, "JobScheduler");
        return workSource;
    }

    @NeverCompile
    public final void dumpInternal(IndentingPrintWriter indentingPrintWriter, int i) {
        long j;
        boolean z;
        Iterator it;
        long j2;
        int appId = UserHandle.getAppId(i);
        long millis = sSystemClock.millis();
        long millis2 = sElapsedRealtimeClock.millis();
        long millis3 = sUptimeMillisClock.millis();
        JobSchedulerService$$ExternalSyntheticLambda5 jobSchedulerService$$ExternalSyntheticLambda5 = new JobSchedulerService$$ExternalSyntheticLambda5(appId, 1);
        synchronized (this.mLock) {
            try {
                this.mConstants.dump(indentingPrintWriter);
                Iterator it2 = ((ArrayList) this.mControllers).iterator();
                while (it2.hasNext()) {
                    StateController stateController = (StateController) it2.next();
                    indentingPrintWriter.increaseIndent();
                    stateController.dumpConstants(indentingPrintWriter);
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.println();
                indentingPrintWriter.println();
                indentingPrintWriter.println("  [BatchNetworkJob] JobCountForMaybeReadyJobs: " + this.mJobCountBuffer.size());
                for (JobOperationGroupBlockData jobOperationGroupBlockData : (JobOperationGroupBlockData[]) this.mJobCountBuffer.toArray()) {
                    indentingPrintWriter.print(jobOperationGroupBlockData.toString());
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.println("  [BatchNetworkJob] version = 2");
                indentingPrintWriter.println("  [BatchNetworkJob] DeferrableNetworkJob = " + this.mDeferrableNetworkJobCounter.toString());
                indentingPrintWriter.println("  [BatchNetworkJob] AcceptCount: " + this.mAcceptCounter.toString());
                indentingPrintWriter.println("Aconfig flags:");
                indentingPrintWriter.increaseIndent();
                Flags.batchActiveBucketJobs();
                Boolean bool = Boolean.FALSE;
                indentingPrintWriter.print("com.android.server.job.batch_active_bucket_jobs", bool);
                indentingPrintWriter.println();
                Flags.batchConnectivityJobsPerNetwork();
                indentingPrintWriter.print("com.android.server.job.batch_connectivity_jobs_per_network", bool);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Force batch connectivity jobs: true");
                indentingPrintWriter.println();
                Flags.doNotForceRushExecutionAtBoot();
                indentingPrintWriter.print("com.android.server.job.do_not_force_rush_execution_at_boot", bool);
                indentingPrintWriter.println();
                indentingPrintWriter.print("android.app.job.backup_jobs_exemption", Boolean.valueOf(com.android.internal.hidden_from_bootclasspath.android.app.job.Flags.backupJobsExemption()));
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                boolean z2 = true;
                for (int size = this.mJobRestrictions.size() - 1; size >= 0; size--) {
                    ((JobRestriction) this.mJobRestrictions.get(size)).dumpConstants(indentingPrintWriter);
                }
                indentingPrintWriter.println();
                this.mQuotaTracker.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Power connected: ");
                indentingPrintWriter.println(this.mBatteryStateTracker.mPowerConnected);
                indentingPrintWriter.print("Battery charging: ");
                indentingPrintWriter.println(this.mBatteryStateTracker.mCharging);
                indentingPrintWriter.print("Considered charging: ");
                indentingPrintWriter.println(this.mBatteryStateTracker.isConsideredCharging());
                indentingPrintWriter.print("Battery level: ");
                indentingPrintWriter.println(this.mBatteryStateTracker.mBatteryLevel);
                indentingPrintWriter.print("Battery not low: ");
                indentingPrintWriter.println(this.mBatteryStateTracker.mBatteryNotLow);
                if (this.mBatteryStateTracker.isMonitoring()) {
                    indentingPrintWriter.print("MONITORING: seq=");
                    indentingPrintWriter.println(this.mBatteryStateTracker.mLastBatterySeq);
                }
                indentingPrintWriter.println();
                indentingPrintWriter.println("Started users: " + Arrays.toString(this.mStartedUsers));
                indentingPrintWriter.println();
                indentingPrintWriter.print("Media Cloud Providers: ");
                indentingPrintWriter.println(this.mCloudMediaProviderPackages);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Registered ");
                indentingPrintWriter.print(this.mJobs.mJobSet.size());
                indentingPrintWriter.println(" jobs:");
                indentingPrintWriter.increaseIndent();
                if (this.mJobs.mJobSet.size() > 0) {
                    List allJobs = this.mJobs.mJobSet.getAllJobs();
                    Collections.sort(allJobs, new AnonymousClass6());
                    Iterator it3 = ((ArrayList) allJobs).iterator();
                    z = false;
                    while (it3.hasNext()) {
                        JobStatus jobStatus = (JobStatus) it3.next();
                        if (jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus)) {
                            indentingPrintWriter.print("JOB ");
                            jobStatus.printUniqueId(indentingPrintWriter);
                            indentingPrintWriter.print(": ");
                            indentingPrintWriter.println(jobStatus.toShortStringExceptUniqueId());
                            indentingPrintWriter.increaseIndent();
                            jobStatus.dump(indentingPrintWriter, z2, millis2);
                            indentingPrintWriter.print("Restricted due to:");
                            boolean z3 = checkIfRestricted(jobStatus) != null ? z2 : false;
                            if (z3) {
                                int size2 = this.mJobRestrictions.size() - 1;
                                while (size2 >= 0) {
                                    Iterator it4 = it3;
                                    JobRestriction jobRestriction = (JobRestriction) this.mJobRestrictions.get(size2);
                                    long j3 = millis;
                                    if (jobRestriction.isJobRestricted(jobStatus, evaluateJobBiasLocked(jobStatus))) {
                                        int i2 = jobRestriction.mInternalReason;
                                        indentingPrintWriter.print(" ");
                                        indentingPrintWriter.print(JobParameters.getInternalReasonCodeDescription(i2));
                                    }
                                    size2--;
                                    it3 = it4;
                                    millis = j3;
                                }
                                it = it3;
                                j2 = millis;
                            } else {
                                it = it3;
                                j2 = millis;
                                indentingPrintWriter.print(" none");
                            }
                            indentingPrintWriter.println(".");
                            indentingPrintWriter.print("Ready: ");
                            indentingPrintWriter.print(isReadyToBeExecutedLocked(jobStatus));
                            indentingPrintWriter.print(" (job=");
                            indentingPrintWriter.print(jobStatus.isReady(jobStatus.mSatisfiedConstraintsOfInterest));
                            indentingPrintWriter.print(" user=");
                            indentingPrintWriter.print(areUsersStartedLocked(jobStatus));
                            indentingPrintWriter.print(" !restricted=");
                            indentingPrintWriter.print(!z3);
                            indentingPrintWriter.print(" !pending=");
                            indentingPrintWriter.print(!this.mPendingJobQueue.contains(jobStatus));
                            indentingPrintWriter.print(" !active=");
                            indentingPrintWriter.print(!this.mConcurrencyManager.mRunningJobs.contains(jobStatus));
                            indentingPrintWriter.print(" !backingup=");
                            indentingPrintWriter.print(!this.mBackingUpUids.get(jobStatus.sourceUid));
                            indentingPrintWriter.print(" comp=");
                            indentingPrintWriter.print(isComponentUsable(jobStatus));
                            indentingPrintWriter.println(")");
                            indentingPrintWriter.decreaseIndent();
                            it3 = it;
                            millis = j2;
                            z = true;
                            z2 = true;
                        }
                    }
                    j = millis;
                } else {
                    j = millis;
                    z = false;
                }
                if (!z) {
                    indentingPrintWriter.println("None.");
                }
                indentingPrintWriter.decreaseIndent();
                for (int i3 = 0; i3 < ((ArrayList) this.mControllers).size(); i3++) {
                    indentingPrintWriter.println();
                    indentingPrintWriter.println(((StateController) ((ArrayList) this.mControllers).get(i3)).getClass().getSimpleName() + ":");
                    indentingPrintWriter.increaseIndent();
                    ((StateController) ((ArrayList) this.mControllers).get(i3)).dumpControllerStateLocked(indentingPrintWriter, jobSchedulerService$$ExternalSyntheticLambda5);
                    indentingPrintWriter.decreaseIndent();
                }
                boolean z4 = false;
                for (int i4 = 0; i4 < this.mUidProcStates.size(); i4++) {
                    int keyAt = this.mUidProcStates.keyAt(i4);
                    if (appId == -1 || appId == UserHandle.getAppId(keyAt)) {
                        if (!z4) {
                            indentingPrintWriter.println();
                            indentingPrintWriter.println("Uid proc states:");
                            indentingPrintWriter.increaseIndent();
                            z4 = true;
                        }
                        indentingPrintWriter.print(UserHandle.formatUid(keyAt));
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(ActivityManager.procStateToString(this.mUidProcStates.valueAt(i4)));
                    }
                }
                if (z4) {
                    indentingPrintWriter.decreaseIndent();
                }
                boolean z5 = false;
                for (int i5 = 0; i5 < this.mUidBiasOverride.size(); i5++) {
                    int keyAt2 = this.mUidBiasOverride.keyAt(i5);
                    if (appId == -1 || appId == UserHandle.getAppId(keyAt2)) {
                        if (!z5) {
                            indentingPrintWriter.println();
                            indentingPrintWriter.println("Uid bias overrides:");
                            indentingPrintWriter.increaseIndent();
                            z5 = true;
                        }
                        indentingPrintWriter.print(UserHandle.formatUid(keyAt2));
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(this.mUidBiasOverride.valueAt(i5));
                    }
                }
                if (z5) {
                    indentingPrintWriter.decreaseIndent();
                }
                boolean z6 = false;
                for (int i6 = 0; i6 < this.mUidCapabilities.size(); i6++) {
                    int keyAt3 = this.mUidCapabilities.keyAt(i6);
                    if (appId == -1 || appId == UserHandle.getAppId(keyAt3)) {
                        if (!z6) {
                            indentingPrintWriter.println();
                            indentingPrintWriter.println("Uid capabilities:");
                            indentingPrintWriter.increaseIndent();
                            z6 = true;
                        }
                        indentingPrintWriter.print(UserHandle.formatUid(keyAt3));
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(ActivityManager.getCapabilitiesSummary(this.mUidCapabilities.valueAt(i6)));
                    }
                }
                if (z6) {
                    indentingPrintWriter.decreaseIndent();
                }
                boolean z7 = false;
                for (int i7 = 0; i7 < this.mUidToPackageCache.size(); i7++) {
                    int keyAt4 = this.mUidToPackageCache.keyAt(i7);
                    if (i == -1 || i == keyAt4) {
                        if (!z7) {
                            indentingPrintWriter.println();
                            indentingPrintWriter.println("Cached UID->package map:");
                            indentingPrintWriter.increaseIndent();
                            z7 = true;
                        }
                        indentingPrintWriter.print(keyAt4);
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(this.mUidToPackageCache.get(keyAt4));
                    }
                }
                if (z7) {
                    indentingPrintWriter.decreaseIndent();
                }
                boolean z8 = false;
                for (int i8 = 0; i8 < this.mBackingUpUids.size(); i8++) {
                    int keyAt5 = this.mBackingUpUids.keyAt(i8);
                    if (appId == -1 || appId == UserHandle.getAppId(keyAt5)) {
                        if (z8) {
                            indentingPrintWriter.print(", ");
                        } else {
                            indentingPrintWriter.println();
                            indentingPrintWriter.println("Backing up uids:");
                            indentingPrintWriter.increaseIndent();
                            z8 = true;
                        }
                        indentingPrintWriter.print(UserHandle.formatUid(keyAt5));
                    }
                }
                if (z8) {
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.println();
                this.mJobPackageTracker.dump(indentingPrintWriter, appId);
                indentingPrintWriter.println();
                if (this.mJobPackageTracker.dumpHistory(indentingPrintWriter, appId)) {
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.println("Pending queue:");
                indentingPrintWriter.increaseIndent();
                this.mPendingJobQueue.mNeedToResetIterators = true;
                int i9 = 0;
                boolean z9 = false;
                while (true) {
                    JobStatus next = this.mPendingJobQueue.next();
                    if (next == null) {
                        break;
                    }
                    i9++;
                    if (jobSchedulerService$$ExternalSyntheticLambda5.test(next)) {
                        if (!z9) {
                            z9 = true;
                        }
                        indentingPrintWriter.print("Pending #");
                        indentingPrintWriter.print(i9);
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(next.toShortString());
                        indentingPrintWriter.increaseIndent();
                        next.dump(indentingPrintWriter, false, millis2);
                        int evaluateJobBiasLocked = evaluateJobBiasLocked(next);
                        indentingPrintWriter.print("Evaluated bias: ");
                        indentingPrintWriter.println(JobInfo.getBiasString(evaluateJobBiasLocked));
                        indentingPrintWriter.print("Enq: ");
                        TimeUtils.formatDuration(next.madePending - millis3, indentingPrintWriter);
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                    }
                }
                if (!z9) {
                    indentingPrintWriter.println(KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                this.mConcurrencyManager.dumpContextInfoLocked(indentingPrintWriter, jobSchedulerService$$ExternalSyntheticLambda5, millis2, millis3);
                indentingPrintWriter.println();
                indentingPrintWriter.println("Recently completed jobs:");
                indentingPrintWriter.increaseIndent();
                boolean z10 = false;
                for (int i10 = 1; i10 <= 20; i10++) {
                    int i11 = ((this.mLastCompletedJobIndex + 20) - i10) % 20;
                    JobStatus jobStatus2 = this.mLastCompletedJobs[i11];
                    if (jobStatus2 != null && jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus2)) {
                        TimeUtils.formatDuration(this.mLastCompletedJobTimeElapsed[i11], millis2, indentingPrintWriter);
                        indentingPrintWriter.println();
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.println(jobStatus2.toShortString());
                        jobStatus2.dump(indentingPrintWriter, true, millis2);
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.decreaseIndent();
                        z10 = true;
                    }
                }
                if (!z10) {
                    indentingPrintWriter.println(KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                int i12 = 1;
                boolean z11 = false;
                while (true) {
                    JobStatus[] jobStatusArr = this.mLastCancelledJobs;
                    if (i12 > jobStatusArr.length) {
                        break;
                    }
                    int length = ((this.mLastCancelledJobIndex + jobStatusArr.length) - i12) % jobStatusArr.length;
                    JobStatus jobStatus3 = jobStatusArr[length];
                    if (jobStatus3 != null && jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus3)) {
                        if (!z11) {
                            indentingPrintWriter.println();
                            indentingPrintWriter.println("Recently cancelled jobs:");
                            indentingPrintWriter.increaseIndent();
                            z11 = true;
                        }
                        TimeUtils.formatDuration(this.mLastCancelledJobTimeElapsed[length], millis2, indentingPrintWriter);
                        indentingPrintWriter.println();
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.println(jobStatus3.toShortString());
                        jobStatus3.dump(indentingPrintWriter, true, millis2);
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.decreaseIndent();
                    }
                    i12++;
                }
                if (!z11) {
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
                if (i == -1) {
                    indentingPrintWriter.println();
                    indentingPrintWriter.print("mReadyToRock=");
                    indentingPrintWriter.println(this.mReadyToRock);
                    indentingPrintWriter.print("mReportedActive=");
                    indentingPrintWriter.println(this.mReportedActive);
                }
                indentingPrintWriter.println();
                this.mConcurrencyManager.dumpLocked(indentingPrintWriter, j, millis2);
                indentingPrintWriter.println();
                indentingPrintWriter.print("PersistStats: ");
                indentingPrintWriter.println(this.mJobs.mPersistInfo);
            } catch (Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.println();
    }

    public final void dumpInternalProto(FileDescriptor fileDescriptor, int i) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
        int appId = UserHandle.getAppId(i);
        sSystemClock.millis();
        sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long millis = sUptimeMillisClock.millis();
        JobSchedulerService$$ExternalSyntheticLambda5 jobSchedulerService$$ExternalSyntheticLambda5 = new JobSchedulerService$$ExternalSyntheticLambda5(appId, 0);
        synchronized (this.mLock) {
            long j = 1146756268033L;
            try {
                long start = protoOutputStream.start(1146756268033L);
                this.mConstants.dump(protoOutputStream);
                Iterator it = ((ArrayList) this.mControllers).iterator();
                while (it.hasNext()) {
                    ((StateController) it.next()).dumpConstants(protoOutputStream);
                }
                protoOutputStream.end(start);
                for (int size = this.mJobRestrictions.size() - 1; size >= 0; size--) {
                    ((JobRestriction) this.mJobRestrictions.get(size)).getClass();
                }
                int[] iArr = this.mStartedUsers;
                int i2 = 0;
                for (int length = iArr.length; i2 < length; length = length) {
                    protoOutputStream.write(2220498092034L, iArr[i2]);
                    i2++;
                }
                this.mQuotaTracker.dump(protoOutputStream);
                if (this.mJobs.mJobSet.size() > 0) {
                    List allJobs = this.mJobs.mJobSet.getAllJobs();
                    Collections.sort(allJobs, new AnonymousClass6());
                    Iterator it2 = ((ArrayList) allJobs).iterator();
                    while (it2.hasNext()) {
                        JobStatus jobStatus = (JobStatus) it2.next();
                        long start2 = protoOutputStream.start(2246267895811L);
                        jobStatus.writeToShortProto(protoOutputStream, j);
                        if (jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus)) {
                            jobStatus.dump(protoOutputStream, true, elapsedRealtime);
                            protoOutputStream.write(1133871366154L, isReadyToBeExecutedLocked(jobStatus));
                            Iterator it3 = it2;
                            protoOutputStream.write(1133871366147L, jobStatus.isReady(jobStatus.mSatisfiedConstraintsOfInterest));
                            protoOutputStream.write(1133871366148L, areUsersStartedLocked(jobStatus));
                            protoOutputStream.write(1133871366155L, checkIfRestricted(jobStatus) != null);
                            protoOutputStream.write(1133871366149L, this.mPendingJobQueue.contains(jobStatus));
                            protoOutputStream.write(1133871366150L, this.mConcurrencyManager.mRunningJobs.contains(jobStatus));
                            protoOutputStream.write(1133871366151L, this.mBackingUpUids.get(jobStatus.sourceUid));
                            protoOutputStream.write(1133871366152L, isComponentUsable(jobStatus));
                            for (JobRestriction jobRestriction : this.mJobRestrictions) {
                                long j2 = millis;
                                long start3 = protoOutputStream.start(2246267895820L);
                                protoOutputStream.write(1159641169921L, jobRestriction.mInternalReason);
                                protoOutputStream.write(1133871366146L, jobRestriction.isJobRestricted(jobStatus, evaluateJobBiasLocked(jobStatus)));
                                protoOutputStream.end(start3);
                                millis = j2;
                                elapsedRealtime = elapsedRealtime;
                            }
                            protoOutputStream.end(start2);
                            it2 = it3;
                            millis = millis;
                            elapsedRealtime = elapsedRealtime;
                            j = 1146756268033L;
                        }
                    }
                }
                long j3 = elapsedRealtime;
                long j4 = millis;
                Iterator it4 = ((ArrayList) this.mControllers).iterator();
                while (it4.hasNext()) {
                    ((StateController) it4.next()).dumpControllerStateLocked(protoOutputStream, jobSchedulerService$$ExternalSyntheticLambda5);
                }
                for (int i3 = 0; i3 < this.mUidBiasOverride.size(); i3++) {
                    int keyAt = this.mUidBiasOverride.keyAt(i3);
                    if (appId == -1 || appId == UserHandle.getAppId(keyAt)) {
                        long start4 = protoOutputStream.start(2246267895813L);
                        protoOutputStream.write(1120986464257L, keyAt);
                        protoOutputStream.write(1172526071810L, this.mUidBiasOverride.valueAt(i3));
                        protoOutputStream.end(start4);
                    }
                }
                for (int i4 = 0; i4 < this.mBackingUpUids.size(); i4++) {
                    int keyAt2 = this.mBackingUpUids.keyAt(i4);
                    if (appId == -1 || appId == UserHandle.getAppId(keyAt2)) {
                        protoOutputStream.write(2220498092038L, keyAt2);
                    }
                }
                this.mJobPackageTracker.dump(appId, protoOutputStream);
                this.mJobPackageTracker.dumpHistory(appId, protoOutputStream);
                this.mPendingJobQueue.mNeedToResetIterators = true;
                while (true) {
                    JobStatus next = this.mPendingJobQueue.next();
                    if (next == null) {
                        break;
                    }
                    long start5 = protoOutputStream.start(2246267895817L);
                    next.writeToShortProto(protoOutputStream, 1146756268033L);
                    long j5 = j3;
                    next.dump(protoOutputStream, false, j5);
                    protoOutputStream.write(1172526071811L, evaluateJobBiasLocked(next));
                    protoOutputStream.write(1112396529668L, j4 - next.madePending);
                    protoOutputStream.end(start5);
                    j3 = j5;
                }
                long j6 = j3;
                if (i == -1) {
                    protoOutputStream.write(1133871366155L, this.mReadyToRock);
                    protoOutputStream.write(1133871366156L, this.mReportedActive);
                }
                this.mConcurrencyManager.dumpProtoLocked(protoOutputStream, j6);
                this.mJobs.mPersistInfo.dumpDebug(protoOutputStream, 1146756268053L);
            } catch (Throwable th) {
                throw th;
            }
        }
        protoOutputStream.flush();
    }

    public boolean evaluateControllerStatesLocked(JobStatus jobStatus) {
        for (int size = ((ArrayList) this.mControllers).size() - 1; size >= 0; size--) {
            ((StateController) ((ArrayList) this.mControllers).get(size)).evaluateStateLocked(jobStatus);
        }
        return jobStatus.isReady(jobStatus.mSatisfiedConstraintsOfInterest);
    }

    public final int evaluateJobBiasLocked(JobStatus jobStatus) {
        int i;
        int bias = jobStatus.job.getBias();
        if (bias < 30 && (i = this.mUidBiasOverride.get(jobStatus.sourceUid, 0)) != 0) {
            return adjustJobBias(jobStatus, i);
        }
        return adjustJobBias(jobStatus, bias);
    }

    public final int executeCancelCommand(PrintWriter printWriter, String str, int i, String str2, boolean z, int i2) {
        int i3;
        if (DEBUG) {
            GmsAlarmManager$$ExternalSyntheticOutline0.m(StorageManagerService$$ExternalSyntheticOutline0.m(i, "executeCancelCommand(): ", str, "/", " "), i2, "JobScheduler");
        }
        try {
            i3 = AppGlobals.getPackageManager().getPackageUid(str, 0L, i);
        } catch (RemoteException unused) {
            i3 = -1;
        }
        int i4 = i3;
        if (i4 < 0) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "Package ", str, " not found.");
            return -1000;
        }
        if (z) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(StorageManagerService$$ExternalSyntheticOutline0.m(i2, "Canceling job ", str, "/#", " in user "), i, printWriter);
            if (cancelJob(i4, i2, 2000, 13, str2)) {
                return 0;
            }
            printWriter.println("No matching job found.");
            return 0;
        }
        printWriter.println("Canceling all jobs for " + str + " in user " + i);
        if (cancelJobsForUid(i4, 13, 0, false, false, null, "cancel shell command for package")) {
            return 0;
        }
        printWriter.println("No matching jobs found.");
        return 0;
    }

    public final int executeRunCommand(int i, int i2, String str, String str2, boolean z, boolean z2) {
        StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("executeRunCommand(): ", str, "/", str2, "/");
        ServiceKeeper$$ExternalSyntheticOutline0.m(i, i2, " ", " s=", m);
        m.append(z);
        m.append(" f=");
        m.append(z2);
        Slog.d("JobScheduler", m.toString());
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            IPackageManager packageManager = AppGlobals.getPackageManager();
            if (i == -1) {
                i = 0;
            }
            int packageUid = packageManager.getPackageUid(str, 0L, i);
            if (packageUid < 0) {
                return -1000;
            }
            synchronized (this.mLock) {
                try {
                    JobStatus jobStatus = this.mJobs.mJobSet.get(packageUid, i2, str2);
                    if (jobStatus == null) {
                        return -1001;
                    }
                    jobStatus.overrideState = z2 ? 3 : z ? 1 : 2;
                    for (int size = ((ArrayList) this.mControllers).size() - 1; size >= 0; size--) {
                        ((StateController) ((ArrayList) this.mControllers).get(size)).evaluateStateLocked(jobStatus);
                    }
                    if (jobStatus.isConstraintsSatisfied(jobStatus.mSatisfiedConstraintsOfInterest)) {
                        countDownLatch.countDown();
                    } else {
                        if (!jobStatus.hasConnectivityConstraint() || jobStatus.isConstraintSatisfied(268435456) || !jobStatus.readinessStatusWithConstraint(268435456, true)) {
                            jobStatus.overrideState = 0;
                            return -1002;
                        }
                        JobHandler jobHandler = this.mHandler;
                        jobHandler.postDelayed(new JobSchedulerService$$ExternalSyntheticLambda7(5, jobStatus, countDownLatch, jobHandler), 1000L);
                    }
                    try {
                        countDownLatch.await(7L, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        Slog.e("JobScheduler", "Couldn't wait for asynchronous constraint change", e);
                    }
                    synchronized (this.mLock) {
                        try {
                            if (!jobStatus.isConstraintsSatisfied(jobStatus.mSatisfiedConstraintsOfInterest)) {
                                jobStatus.overrideState = 0;
                                return -1002;
                            }
                            queueReadyJobsForExecutionLocked();
                            maybeRunPendingJobsLocked();
                            return 0;
                        } finally {
                        }
                    }
                } finally {
                }
            }
        } catch (RemoteException unused) {
            return 0;
        }
    }

    public final void executeStopCommand(PrintWriter printWriter, String str, int i, String str2, boolean z, int i2, int i3, int i4) {
        if (DEBUG) {
            StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "executeStopJobCommand(): ", str, "/", " ");
            ServiceKeeper$$ExternalSyntheticOutline0.m(i2, i3, ": ", "(", m);
            m.append(JobParameters.getInternalReasonCodeDescription(i4));
            m.append(")");
            Slog.v("JobScheduler", m.toString());
        }
        synchronized (this.mLock) {
            try {
                if (!this.mConcurrencyManager.executeStopCommandLocked(printWriter, str, i, str2, z, i2, i3, i4)) {
                    printWriter.println("No matching executing jobs found.");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public ConnectivityController getConnectivityController() {
        return this.mConnectivityController;
    }

    public final int getEstimatedNetworkBytes(int i, int i2, int i3, PrintWriter printWriter, String str, String str2) {
        int packageUid;
        long longValue;
        long j;
        try {
            IPackageManager packageManager = AppGlobals.getPackageManager();
            if (i == -1) {
                i = 0;
            }
            packageUid = packageManager.getPackageUid(str, 0L, i);
        } catch (RemoteException unused) {
        }
        if (packageUid < 0) {
            printWriter.print("unknown(");
            printWriter.print(str);
            printWriter.println(")");
            return -1000;
        }
        synchronized (this.mLock) {
            try {
                JobStatus jobStatus = this.mJobs.mJobSet.get(packageUid, i2, str2);
                if (DEBUG) {
                    Slog.d("JobScheduler", "get-estimated-network-bytes " + packageUid + "/" + str2 + "/" + i2 + ": " + jobStatus);
                }
                if (jobStatus == null) {
                    printWriter.print("unknown(");
                    UserHandle.formatUid(printWriter, packageUid);
                    printWriter.print("/jid");
                    printWriter.print(i2);
                    printWriter.println(")");
                    return -1001;
                }
                Pair estimatedNetworkBytesLocked = this.mConcurrencyManager.getEstimatedNetworkBytesLocked(packageUid, i2, str, str2);
                if (estimatedNetworkBytesLocked == null) {
                    j = jobStatus.mTotalNetworkDownloadBytes;
                    longValue = jobStatus.mTotalNetworkUploadBytes;
                } else {
                    long longValue2 = ((Long) estimatedNetworkBytesLocked.first).longValue();
                    longValue = ((Long) estimatedNetworkBytesLocked.second).longValue();
                    j = longValue2;
                }
                if (i3 == 0) {
                    printWriter.println(j);
                } else {
                    printWriter.println(longValue);
                }
                printWriter.println();
                return 0;
            } finally {
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x010a, code lost:
    
        if (android.app.AppGlobals.getPackageManager().getServiceInfo(r2.job.getService(), 268435456, android.os.UserHandle.getUserId(r2.callingUid)) != null) goto L54;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getJobState(java.io.PrintWriter r6, java.lang.String r7, int r8, java.lang.String r9, int r10) {
        /*
            Method dump skipped, instructions count: 322
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.JobSchedulerService.getJobState(java.io.PrintWriter, java.lang.String, int, java.lang.String, int):int");
    }

    public final long getMaxJobExecutionTimeMs(JobStatus jobStatus) {
        synchronized (this.mLock) {
            try {
                if (jobStatus.shouldTreatAsUserInitiatedJob()) {
                    if (PermissionChecker.checkPermissionForPreflight(getContext(), "android.permission.RUN_USER_INITIATED_JOBS", -1, jobStatus.sourceUid, jobStatus.sourcePackageName) == 0 && this.mQuotaTracker.isWithinQuota(jobStatus.getTimeoutBlameUserId(), jobStatus.getTimeoutBlamePackageName(), "timeout-uij")) {
                        return this.mConstants.RUNTIME_UI_LIMIT_MS;
                    }
                }
                if (jobStatus.shouldTreatAsUserInitiatedJob()) {
                    return this.mConstants.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS;
                }
                String str = jobStatus.shouldTreatAsExpeditedJob() ? "timeout-ej" : "timeout-reg";
                long j = jobStatus.shouldTreatAsExpeditedJob() ? this.mConstants.RUNTIME_MIN_GUARANTEE_MS : this.mConstants.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS;
                if (!this.mQuotaTracker.isWithinQuota(jobStatus.getTimeoutBlameUserId(), jobStatus.getTimeoutBlamePackageName(), str)) {
                    j = this.mConstants.RUNTIME_MIN_GUARANTEE_MS;
                }
                return Math.min(j, this.mQuotaController.getMaxJobExecutionTimeMsLocked(jobStatus));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0090 A[Catch: all -> 0x0032, TryCatch #0 {all -> 0x0032, blocks: (B:4:0x0003, B:6:0x0009, B:8:0x001a, B:10:0x002d, B:11:0x0039, B:13:0x0041, B:15:0x0047, B:22:0x0090, B:23:0x0098, B:26:0x009a, B:27:0x00ab, B:29:0x005c, B:32:0x0061, B:37:0x006e, B:43:0x008b, B:44:0x0069, B:45:0x00ad, B:46:0x00b9, B:48:0x00bb, B:49:0x00c3, B:51:0x0035, B:52:0x00c5, B:54:0x00cb, B:56:0x00d2, B:57:0x00e2, B:59:0x00d7, B:60:0x00e4, B:61:0x00e8), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x009a A[Catch: all -> 0x0032, TryCatch #0 {all -> 0x0032, blocks: (B:4:0x0003, B:6:0x0009, B:8:0x001a, B:10:0x002d, B:11:0x0039, B:13:0x0041, B:15:0x0047, B:22:0x0090, B:23:0x0098, B:26:0x009a, B:27:0x00ab, B:29:0x005c, B:32:0x0061, B:37:0x006e, B:43:0x008b, B:44:0x0069, B:45:0x00ad, B:46:0x00b9, B:48:0x00bb, B:49:0x00c3, B:51:0x0035, B:52:0x00c5, B:54:0x00cb, B:56:0x00d2, B:57:0x00e2, B:59:0x00d7, B:60:0x00e4, B:61:0x00e8), top: B:3:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long getMinJobExecutionGuaranteeMs(com.android.server.job.controllers.JobStatus r13) {
        /*
            Method dump skipped, instructions count: 236
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.JobSchedulerService.getMinJobExecutionGuaranteeMs(com.android.server.job.controllers.JobStatus):long");
    }

    public final ArraySet getPackagesForUidLocked(int i) {
        ArraySet arraySet = this.mUidToPackageCache.get(i);
        if (arraySet != null) {
            return arraySet;
        }
        try {
            String[] packagesForUid = AppGlobals.getPackageManager().getPackagesForUid(i);
            if (packagesForUid == null) {
                return arraySet;
            }
            for (String str : packagesForUid) {
                this.mUidToPackageCache.add(i, str);
            }
            return this.mUidToPackageCache.get(i);
        } catch (RemoteException unused) {
            return arraySet;
        }
    }

    public final int getPendingJobReason(int i, int i2, String str) {
        int pendingJobReasonLocked;
        int i3;
        synchronized (this.mPendingJobReasonCache) {
            try {
                SparseIntArray sparseIntArray = (SparseIntArray) this.mPendingJobReasonCache.get(i, str);
                if (sparseIntArray != null && (i3 = sparseIntArray.get(i2, 0)) != 0) {
                    return i3;
                }
                synchronized (this.mLock) {
                    try {
                        pendingJobReasonLocked = getPendingJobReasonLocked(i, i2, str);
                        if (DEBUG) {
                            Slog.v("JobScheduler", "getPendingJobReason(" + i + "," + str + "," + i2 + ")=" + pendingJobReasonLocked);
                        }
                    } finally {
                    }
                }
                synchronized (this.mPendingJobReasonCache) {
                    try {
                        SparseIntArray sparseIntArray2 = (SparseIntArray) this.mPendingJobReasonCache.get(i, str);
                        if (sparseIntArray2 == null) {
                            sparseIntArray2 = new SparseIntArray();
                            this.mPendingJobReasonCache.add(i, str, sparseIntArray2);
                        }
                        sparseIntArray2.put(i2, pendingJobReasonLocked);
                    } finally {
                    }
                }
                return pendingJobReasonLocked;
            } finally {
            }
        }
    }

    public int getPendingJobReason(JobStatus jobStatus) {
        return getPendingJobReason(jobStatus.callingUid, jobStatus.job.getId(), jobStatus.mNamespace);
    }

    public final int getPendingJobReasonLocked(int i, int i2, String str) {
        JobStatus jobStatus = this.mJobs.mJobSet.get(i, i2, str);
        if (jobStatus == null) {
            return -2;
        }
        if (isCurrentlyRunningLocked(jobStatus)) {
            return -1;
        }
        boolean isReady = jobStatus.isReady(jobStatus.mSatisfiedConstraintsOfInterest);
        boolean z = DEBUG;
        if (z) {
            Slog.v("JobScheduler", "getPendingJobReasonLocked: " + jobStatus.toShortString() + " ready=" + isReady);
        }
        if (!isReady) {
            int i3 = ~jobStatus.satisfiedConstraints;
            int i4 = jobStatus.mDynamicConstraints;
            int i5 = jobStatus.requiredConstraints;
            int i6 = i3 & (i4 | i5 | 56623104);
            if ((4194304 & i6) == 0) {
                if ((i6 & 2) != 0) {
                    if ((i5 & 2) != 0) {
                        return 4;
                    }
                } else if ((i6 & 1) != 0) {
                    if ((i5 & 1) != 0) {
                        return 5;
                    }
                } else {
                    if ((268435456 & i6) != 0) {
                        return 6;
                    }
                    if ((67108864 & i6) != 0) {
                        return 7;
                    }
                    if ((33554432 & i6) == 0) {
                        if ((2097152 & i6) != 0) {
                            return 13;
                        }
                        if ((i6 & 4) == 0) {
                            if ((8388608 & i6) != 0) {
                                return 10;
                            }
                            if ((i6 & 8) != 0) {
                                return 11;
                            }
                            if ((Integer.MIN_VALUE & i6) != 0) {
                                return 9;
                            }
                            if ((i6 & 16777216) != 0) {
                                return 14;
                            }
                            if (jobStatus.getEffectiveStandbyBucket() == 4) {
                                Slog.wtf("JobScheduler.JobStatus", "App in NEVER bucket querying pending job reason");
                                return 15;
                            }
                            if (jobStatus.serviceProcessName != null) {
                                return 1;
                            }
                            if (!jobStatus.isReady(jobStatus.mSatisfiedConstraintsOfInterest)) {
                                Slog.wtf("JobScheduler.JobStatus", "Unknown reason job isn't ready");
                            }
                            return 0;
                        }
                        if ((i5 & 4) != 0) {
                            return 8;
                        }
                    }
                }
                return 2;
            }
            if (jobStatus.mIsUserBgRestricted) {
                return 3;
            }
            return 12;
        }
        boolean areUsersStartedLocked = areUsersStartedLocked(jobStatus);
        if (z) {
            Slog.v("JobScheduler", "getPendingJobReasonLocked: " + jobStatus.toShortString() + " userStarted=" + areUsersStartedLocked);
        }
        if (!areUsersStartedLocked) {
            return 15;
        }
        boolean z2 = this.mBackingUpUids.get(jobStatus.sourceUid);
        if (z) {
            Slog.v("JobScheduler", "getPendingJobReasonLocked: " + jobStatus.toShortString() + " backingUp=" + z2);
        }
        if (z2) {
            return 1;
        }
        JobRestriction checkIfRestricted = checkIfRestricted(jobStatus);
        if (z) {
            Slog.v("JobScheduler", "getPendingJobReasonLocked: " + jobStatus.toShortString() + " restriction=" + checkIfRestricted);
        }
        if (checkIfRestricted != null) {
            return checkIfRestricted.mPendingReason;
        }
        boolean contains = this.mPendingJobQueue.contains(jobStatus);
        if (z) {
            Slog.v("JobScheduler", "getPendingJobReasonLocked: " + jobStatus.toShortString() + " pending=" + contains);
        }
        if (contains) {
            return 12;
        }
        boolean contains2 = this.mConcurrencyManager.mRunningJobs.contains(jobStatus);
        if (z) {
            Slog.v("JobScheduler", "getPendingJobReasonLocked: " + jobStatus.toShortString() + " active=" + contains2);
        }
        if (contains2) {
            return 0;
        }
        boolean isComponentUsable = isComponentUsable(jobStatus);
        if (z) {
            Slog.v("JobScheduler", "getPendingJobReasonLocked: " + jobStatus.toShortString() + " componentUsable=" + isComponentUsable);
        }
        return !isComponentUsable ? 1 : 0;
    }

    public QuotaController getQuotaController() {
        return this.mQuotaController;
    }

    public JobStatus getRescheduleJobForFailureLocked(JobStatus jobStatus, int i, int i2) {
        long j;
        long min;
        JobStatus jobStatus2;
        if (i2 == 11 && jobStatus.isUserVisibleJob()) {
            Slog.i("JobScheduler", "Dropping " + jobStatus.toShortString() + " because of user stop");
            return null;
        }
        sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        JobInfo jobInfo = jobStatus.job;
        long initialBackoffMillis = jobInfo.getInitialBackoffMillis();
        int i3 = jobStatus.numFailures;
        int i4 = jobStatus.mNumSystemStops;
        if (i2 == 10 || i2 == 3 || i2 == 12 || i == 13) {
            i3++;
        } else {
            i4++;
        }
        Constants constants = this.mConstants;
        int i5 = (i4 / constants.SYSTEM_STOP_TO_FAILURE_RATIO) + i3;
        if (i5 == 0) {
            min = 0;
        } else {
            int backoffPolicy = jobInfo.getBackoffPolicy();
            if (backoffPolicy != 0) {
                if (backoffPolicy != 1 && DEBUG) {
                    Slog.v("JobScheduler", "Unrecognised back-off policy, defaulting to exponential.");
                }
                long j2 = constants.MIN_EXP_BACKOFF_TIME_MS;
                if (initialBackoffMillis < j2) {
                    initialBackoffMillis = j2;
                }
                j = (long) Math.scalb(initialBackoffMillis, i5 - 1);
            } else {
                long j3 = constants.MIN_LINEAR_BACKOFF_TIME_MS;
                if (initialBackoffMillis < j3) {
                    initialBackoffMillis = j3;
                }
                j = i5 * initialBackoffMillis;
            }
            min = Math.min(j, 18000000L) + elapsedRealtime;
        }
        JobStatus jobStatus3 = new JobStatus(jobStatus, min, Long.MAX_VALUE, i3, i4, jobStatus.mLastSuccessfulRunTime, sSystemClock.millis(), jobStatus.mCumulativeExecutionTimeMs);
        if (i == 13) {
            jobStatus2 = jobStatus3;
            jobStatus2.mInternalFlags |= 2;
        } else {
            jobStatus2 = jobStatus3;
        }
        if (jobStatus2.mCumulativeExecutionTimeMs >= constants.RUNTIME_CUMULATIVE_UI_LIMIT_MS && jobStatus2.shouldTreatAsUserInitiatedJob()) {
            jobStatus2.mInternalFlags |= 4;
        }
        if (jobInfo.isPeriodic()) {
            jobStatus2.mOriginalLatestRunTimeElapsedMillis = jobStatus.mOriginalLatestRunTimeElapsedMillis;
        }
        for (int i6 = 0; i6 < ((ArrayList) this.mControllers).size(); i6++) {
            ((StateController) ((ArrayList) this.mControllers).get(i6)).rescheduleForFailureLocked(jobStatus2, jobStatus);
        }
        return jobStatus2;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00e9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.android.server.job.controllers.JobStatus getRescheduleJobForPeriodic(com.android.server.job.controllers.JobStatus r23) {
        /*
            Method dump skipped, instructions count: 303
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.JobSchedulerService.getRescheduleJobForPeriodic(com.android.server.job.controllers.JobStatus):com.android.server.job.controllers.JobStatus");
    }

    public final int getTransferredNetworkBytes(int i, int i2, int i3, PrintWriter printWriter, String str, String str2) {
        long j;
        int packageUid;
        long j2;
        try {
            IPackageManager packageManager = AppGlobals.getPackageManager();
            int i4 = i;
            if (i4 == -1) {
                i4 = 0;
            }
            j = 0;
            packageUid = packageManager.getPackageUid(str, 0L, i4);
        } catch (RemoteException unused) {
        }
        if (packageUid < 0) {
            printWriter.print("unknown(");
            printWriter.print(str);
            printWriter.println(")");
            return -1000;
        }
        synchronized (this.mLock) {
            try {
                JobStatus jobStatus = this.mJobs.mJobSet.get(packageUid, i2, str2);
                if (DEBUG) {
                    Slog.d("JobScheduler", "get-transferred-network-bytes " + packageUid + str2 + "//" + i2 + ": " + jobStatus);
                }
                if (jobStatus == null) {
                    printWriter.print("unknown(");
                    UserHandle.formatUid(printWriter, packageUid);
                    printWriter.print("/jid");
                    printWriter.print(i2);
                    printWriter.println(")");
                    return -1001;
                }
                Pair transferredNetworkBytesLocked = this.mConcurrencyManager.getTransferredNetworkBytesLocked(packageUid, i2, str, str2);
                if (transferredNetworkBytesLocked == null) {
                    j2 = 0;
                } else {
                    long longValue = ((Long) transferredNetworkBytesLocked.first).longValue();
                    long longValue2 = ((Long) transferredNetworkBytesLocked.second).longValue();
                    j2 = longValue;
                    j = longValue2;
                }
                if (i3 == 0) {
                    printWriter.println(j2);
                } else {
                    printWriter.println(j);
                }
                printWriter.println();
                return 0;
            } finally {
            }
        }
    }

    public final int getUidBias(int i) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mUidBiasOverride.get(i, 0);
        }
        return i2;
    }

    public final int getUidProcState(int i) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mUidProcStates.get(i, -1);
        }
        return i2;
    }

    public final boolean isBatteryCharging() {
        boolean isConsideredCharging;
        synchronized (this.mLock) {
            isConsideredCharging = this.mBatteryStateTracker.isConsideredCharging();
        }
        return isConsideredCharging;
    }

    public final boolean isBatteryNotLow() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mBatteryStateTracker.mBatteryNotLow;
        }
        return z;
    }

    public final boolean isComponentUsable(JobStatus jobStatus) {
        String str = jobStatus.serviceProcessName;
        boolean z = DEBUG;
        if (str == null) {
            if (!z) {
                return false;
            }
            Slog.v("JobScheduler", "isComponentUsable: " + jobStatus.toShortString() + " component not present");
            return false;
        }
        boolean isAppBad = this.mActivityManagerInternal.isAppBad(str, jobStatus.callingUid);
        if (z && isAppBad) {
            Slog.i("JobScheduler", "App is bad for " + jobStatus.toShortString() + " so not runnable");
        }
        return !isAppBad;
    }

    public final boolean isCurrentlyRunningLocked(JobStatus jobStatus) {
        return this.mConcurrencyManager.mRunningJobs.contains(jobStatus);
    }

    public final boolean isJobInOvertimeLocked(JobStatus jobStatus) {
        JobConcurrencyManager jobConcurrencyManager = this.mConcurrencyManager;
        if (!jobConcurrencyManager.mRunningJobs.contains(jobStatus)) {
            return false;
        }
        for (int size = ((ArrayList) jobConcurrencyManager.mActiveServices).size() - 1; size >= 0; size--) {
            if (((JobServiceContext) ((ArrayList) jobConcurrencyManager.mActiveServices).get(size)).mRunningJob == jobStatus) {
                return !r2.isWithinExecutionGuaranteeTime();
            }
        }
        Slog.wtf("JobScheduler.Concurrency", "Couldn't find long running job on a context");
        jobConcurrencyManager.mRunningJobs.remove(jobStatus);
        if (jobStatus == null) {
            return false;
        }
        JobSchedulerPackageFilter jobSchedulerPackageFilter = JobSchedulerPackageFilter.JobSchedulerPackageFilterHolder.INSTANCE;
        JobInfo jobInfo = jobStatus.job;
        if (jobInfo != null) {
            ((HashSet) jobSchedulerPackageFilter.mRunningJobSet).remove(jobInfo);
            return false;
        }
        jobSchedulerPackageFilter.getClass();
        return false;
    }

    public final boolean isPowerConnected() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mBatteryStateTracker.mPowerConnected;
        }
        return z;
    }

    public boolean isReadyToBeExecutedLocked(JobStatus jobStatus) {
        return isReadyToBeExecutedLocked(jobStatus, true);
    }

    public final boolean isReadyToBeExecutedLocked(JobStatus jobStatus, boolean z) {
        boolean z2 = jobStatus.isReady(jobStatus.mSatisfiedConstraintsOfInterest) || evaluateControllerStatesLocked(jobStatus);
        boolean z3 = DEBUG;
        if (z3) {
            Slog.v("JobScheduler", "isReadyToBeExecutedLocked: " + jobStatus.toShortString() + " ready=" + z2);
        }
        if (!z2) {
            if (jobStatus.sourcePackageName.equals("android.jobscheduler.cts.jobtestapp")) {
                Slog.v("JobScheduler", "    NOT READY: " + jobStatus);
            }
            return false;
        }
        boolean containsJob = this.mJobs.containsJob(jobStatus);
        boolean areUsersStartedLocked = areUsersStartedLocked(jobStatus);
        boolean z4 = this.mBackingUpUids.get(jobStatus.sourceUid);
        if (z3) {
            Slog.v("JobScheduler", "isReadyToBeExecutedLocked: " + jobStatus.toShortString() + " exists=" + containsJob + " userStarted=" + areUsersStartedLocked + " backingUp=" + z4);
        }
        if (!containsJob || !areUsersStartedLocked || z4 || checkIfRestricted(jobStatus) != null) {
            return false;
        }
        boolean contains = this.mPendingJobQueue.contains(jobStatus);
        boolean z5 = z && this.mConcurrencyManager.mRunningJobs.contains(jobStatus);
        if (z3) {
            StringBuilder sb = new StringBuilder("isReadyToBeExecutedLocked: ");
            sb.append(jobStatus.toShortString());
            sb.append(" pending=");
            sb.append(contains);
            sb.append(" active=");
            ProxyManager$$ExternalSyntheticOutline0.m("JobScheduler", sb, z5);
        }
        if (!contains && !z5) {
            return isComponentUsable(jobStatus);
        }
        return false;
    }

    public void maybeProcessBuggyJob(JobStatus jobStatus, int i) {
        boolean z = false;
        boolean z2 = i == 3;
        if (!z2 && jobStatus.madeActive > 0) {
            long millis = sUptimeMillisClock.millis() - jobStatus.madeActive;
            boolean z3 = jobStatus.startedAsUserInitiatedJob;
            Constants constants = this.mConstants;
            if (!z3 ? !(!jobStatus.startedAsExpeditedJob ? millis < constants.RUNTIME_MIN_GUARANTEE_MS : millis < constants.RUNTIME_MIN_EJ_GUARANTEE_MS) : millis >= constants.RUNTIME_MIN_UI_GUARANTEE_MS) {
                z = true;
            }
            z2 = z;
        }
        CountQuotaTracker countQuotaTracker = this.mQuotaTracker;
        if (z2) {
            int timeoutBlameUserId = jobStatus.getTimeoutBlameUserId();
            String timeoutBlamePackageName = jobStatus.getTimeoutBlamePackageName();
            countQuotaTracker.noteEvent(timeoutBlameUserId, timeoutBlamePackageName, jobStatus.startedAsUserInitiatedJob ? "timeout-uij" : jobStatus.startedAsExpeditedJob ? "timeout-ej" : "timeout-reg");
            if (!countQuotaTracker.noteEvent(timeoutBlameUserId, timeoutBlamePackageName, "timeout-total")) {
                this.mAppStandbyInternal.restrictApp(timeoutBlamePackageName, timeoutBlameUserId, 4);
            }
        }
        if (i == 12) {
            int userId = UserHandle.getUserId(jobStatus.callingUid);
            String packageName = jobStatus.job.getService().getPackageName();
            if (countQuotaTracker.noteEvent(userId, packageName, "anr")) {
                return;
            }
            this.mAppStandbyInternal.restrictApp(packageName, userId, 4);
        }
    }

    public final void maybeRunPendingJobsLocked() {
        if (DEBUG) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("pending queue: "), this.mPendingJobQueue.mSize, " jobs.", "JobScheduler");
        }
        this.mConcurrencyManager.assignJobsToContextsLocked();
        reportActiveLocked();
    }

    public void notePendingUserRequestedAppStopInternal(String str, int i, String str2) {
        int packageUid = this.mLocalPM.getPackageUid(str, 0L, i);
        if (packageUid < 0) {
            Slog.wtf("JobScheduler", "Asked to stop jobs of an unknown package");
            return;
        }
        synchronized (this.mLock) {
            try {
                this.mConcurrencyManager.markJobsForUserStopLocked(i, str, str2);
                ArraySet jobsByUid = this.mJobs.getJobsByUid(packageUid);
                for (int size = jobsByUid.size() - 1; size >= 0; size--) {
                    JobStatus jobStatus = (JobStatus) jobsByUid.valueAt(size);
                    jobStatus.mInternalFlags |= 2;
                    if (this.mPendingJobQueue.remove(jobStatus)) {
                        synchronized (this.mPendingJobReasonCache) {
                            try {
                                SparseIntArray sparseIntArray = (SparseIntArray) this.mPendingJobReasonCache.get(jobStatus.callingUid, jobStatus.mNamespace);
                                if (sparseIntArray == null) {
                                    sparseIntArray = new SparseIntArray();
                                    this.mPendingJobReasonCache.add(jobStatus.callingUid, jobStatus.mNamespace, sparseIntArray);
                                }
                                sparseIntArray.put(jobStatus.job.getId(), 15);
                            } finally {
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (480 == i) {
            try {
                this.mStartControllerTrackingLatch.await();
            } catch (InterruptedException unused) {
                Slog.e("JobScheduler", "Couldn't wait on controller tracking start latch");
            }
            try {
                this.mJobStoreLoadedLatch.await();
                return;
            } catch (InterruptedException unused2) {
                Slog.e("JobScheduler", "Couldn't wait on job store loading latch");
                return;
            }
        }
        if (500 != i) {
            if (i == 600) {
                synchronized (this.mLock) {
                    this.mReadyToRock = true;
                    this.mLocalDeviceIdleController = (DeviceIdleInternal) LocalServices.getService(DeviceIdleInternal.class);
                    this.mConcurrencyManager.onThirdPartyAppsCanStart();
                    this.mJobs.forEachJob(new JobSchedulerService$$ExternalSyntheticLambda0(this, 0));
                    Flags.doNotForceRushExecutionAtBoot();
                    this.mHandler.obtainMessage(1).sendToTarget();
                }
                return;
            }
            return;
        }
        ConstantsObserver constantsObserver = this.mConstantsObserver;
        constantsObserver.getClass();
        DeviceConfig.addOnPropertiesChangedListener("jobscheduler", AppSchedulingModuleThread.getExecutor(), constantsObserver);
        constantsObserver.onPropertiesChanged(DeviceConfig.getProperties("jobscheduler", new String[0]));
        for (int size = ((ArrayList) this.mControllers).size() - 1; size >= 0; size--) {
            ((StateController) ((ArrayList) this.mControllers).get(size)).onSystemServicesReady();
        }
        AppStateTracker appStateTracker = (AppStateTracker) LocalServices.getService(AppStateTracker.class);
        Objects.requireNonNull(appStateTracker);
        this.mAppStateTracker = (AppStateTrackerImpl) appStateTracker;
        ((StorageManagerInternal) LocalServices.getService(StorageManagerInternal.class)).registerCloudProviderChangeListener(new CloudProviderChangeListener());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addAction("android.intent.action.QUERY_PACKAGE_RESTART");
        intentFilter.addDataScheme("package");
        Context context = getContext();
        AnonymousClass3 anonymousClass3 = this.mBroadcastReceiver;
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(anonymousClass3, userHandle, intentFilter, null, null);
        getContext().registerReceiverAsUser(this.mBroadcastReceiver, userHandle, new IntentFilter("android.intent.action.UID_REMOVED"), null, null);
        IntentFilter intentFilter2 = new IntentFilter("android.intent.action.USER_REMOVED");
        intentFilter2.addAction("android.intent.action.USER_ADDED");
        getContext().registerReceiverAsUser(this.mBroadcastReceiver, userHandle, intentFilter2, null, null);
        try {
            ActivityManager.getService().registerUidObserver(this.mUidObserver, 15, -1, (String) null);
        } catch (RemoteException unused3) {
        }
        JobConcurrencyManager jobConcurrencyManager = this.mConcurrencyManager;
        jobConcurrencyManager.mPowerManager = (PowerManager) jobConcurrencyManager.mContext.getSystemService(PowerManager.class);
        IntentFilter intentFilter3 = new IntentFilter("android.intent.action.SCREEN_ON");
        intentFilter3.addAction("android.intent.action.SCREEN_OFF");
        intentFilter3.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
        intentFilter3.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        jobConcurrencyManager.mContext.registerReceiver(jobConcurrencyManager.mReceiver, intentFilter3);
        try {
            ActivityManager.getService().registerUserSwitchObserver(jobConcurrencyManager.mGracePeriodObserver, "JobScheduler.Concurrency");
        } catch (RemoteException unused4) {
        }
        jobConcurrencyManager.onInteractiveStateChanged(jobConcurrencyManager.mPowerManager.isInteractive());
        UserManagerInternal userManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        synchronized (this.mLock) {
            this.mJobs.removeJobsOfUnlistedUsers(userManagerInternal.getUserIds());
        }
        synchronized (this.mPendingJobReasonCache) {
            this.mPendingJobReasonCache.clear();
        }
        for (int size2 = this.mJobRestrictions.size() - 1; size2 >= 0; size2--) {
            ((JobRestriction) this.mJobRestrictions.get(size2)).onSystemServicesReady();
        }
    }

    public final void onControllerStateChanged(ArraySet arraySet) {
        if (arraySet == null) {
            this.mHandler.obtainMessage(1).sendToTarget();
            synchronized (this.mPendingJobReasonCache) {
                this.mPendingJobReasonCache.clear();
            }
            return;
        }
        if (arraySet.size() > 0) {
            synchronized (this.mLock) {
                this.mChangedJobList.addAll(arraySet);
            }
            this.mHandler.obtainMessage(8).sendToTarget();
            synchronized (this.mPendingJobReasonCache) {
                try {
                    for (int size = arraySet.size() - 1; size >= 0; size--) {
                        resetPendingJobReasonCache((JobStatus) arraySet.valueAt(size));
                    }
                } finally {
                }
            }
        }
    }

    public final void onRestrictionStateChanged(JobRestriction jobRestriction, boolean z) {
        this.mHandler.obtainMessage(1).sendToTarget();
        if (z) {
            synchronized (this.mLock) {
                this.mConcurrencyManager.maybeStopOvertimeJobsLocked(jobRestriction);
            }
        }
    }

    public final void onRunJobNow(JobStatus jobStatus) {
        JobHandler jobHandler = this.mHandler;
        if (jobStatus == null) {
            jobHandler.obtainMessage(3).sendToTarget();
        } else {
            jobHandler.obtainMessage(0, jobStatus).sendToTarget();
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("jobscheduler", this.mJobSchedulerStub);
    }

    @Override // com.android.server.SystemService
    public final void onUserCompletedEvent(SystemService.TargetUser targetUser, SystemService.UserCompletedEventType userCompletedEventType) {
        if (userCompletedEventType.includesOnUserStarting() || userCompletedEventType.includesOnUserUnlocked()) {
            this.mHandler.obtainMessage(1).sendToTarget();
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserStarting(SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            this.mStartedUsers = ArrayUtils.appendInt(this.mStartedUsers, targetUser.getUserIdentifier());
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserStopping(SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            this.mStartedUsers = ArrayUtils.removeInt(this.mStartedUsers, targetUser.getUserIdentifier());
        }
    }

    public final void queueReadyJobsForExecutionLocked() {
        JobHandler jobHandler = this.mHandler;
        jobHandler.removeMessages(3);
        jobHandler.removeMessages(0);
        jobHandler.removeMessages(1);
        jobHandler.removeMessages(8);
        this.mChangedJobList.clear();
        boolean z = DEBUG;
        if (z) {
            Slog.d("JobScheduler", "queuing all ready jobs for execution:");
        }
        clearPendingJobQueue();
        stopNonReadyActiveJobsLocked();
        JobStore jobStore = this.mJobs;
        ReadyJobQueueFunctor readyJobQueueFunctor = this.mReadyQueueFunctor;
        jobStore.forEachJob(readyJobQueueFunctor);
        JobSchedulerService jobSchedulerService = JobSchedulerService.this;
        ArraySet arraySet = readyJobQueueFunctor.newReadyJobs;
        jobSchedulerService.getClass();
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            jobSchedulerService.mJobPackageTracker.notePending((JobStatus) arraySet.valueAt(size));
        }
        JobSchedulerService.this.mPendingJobQueue.addAll(readyJobQueueFunctor.newReadyJobs);
        readyJobQueueFunctor.newReadyJobs.clear();
        if (z) {
            int i = this.mPendingJobQueue.mSize;
            if (i == 0) {
                Slog.d("JobScheduler", "No jobs pending.");
                return;
            }
            Slog.d("JobScheduler", i + " jobs queued.");
        }
    }

    public final void reportActiveLocked() {
        boolean z = true;
        boolean z2 = this.mPendingJobQueue.mSize > 0;
        if (!z2) {
            ArraySet arraySet = this.mConcurrencyManager.mRunningJobs;
            for (int size = arraySet.size() - 1; size >= 0; size--) {
                if (!((JobStatus) arraySet.valueAt(size)).canRunInDoze()) {
                    break;
                }
            }
        }
        z = z2;
        if (this.mReportedActive != z) {
            this.mReportedActive = z;
            DeviceIdleInternal deviceIdleInternal = this.mLocalDeviceIdleController;
            if (deviceIdleInternal != null) {
                deviceIdleInternal.setJobsActive(z);
            }
        }
    }

    public final void resetPendingJobReasonCache(JobStatus jobStatus) {
        synchronized (this.mPendingJobReasonCache) {
            try {
                SparseIntArray sparseIntArray = (SparseIntArray) this.mPendingJobReasonCache.get(jobStatus.callingUid, jobStatus.mNamespace);
                if (sparseIntArray != null) {
                    sparseIntArray.delete(jobStatus.job.getId());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int scheduleAsPackage(JobInfo jobInfo, JobWorkItem jobWorkItem, int i, String str, int i2, String str2, String str3) {
        String str4;
        String str5;
        JobStatus jobStatus;
        int intValue;
        long longValue;
        String packageName = jobInfo.getService().getPackageName();
        if (jobInfo.isPersisted() && (str == null || str.equals(packageName))) {
            String str6 = str == null ? packageName : str;
            if (this.mQuotaTracker.isWithinQuota(i2, str6, ".schedulePersisted()")) {
                str4 = "Apps may not persist more than ";
                str5 = "Too many JWIs for uid ";
            } else {
                if (this.mQuotaTracker.isWithinQuota(i2, str6, ".schedulePersisted out-of-quota logged")) {
                    Slog.wtf("JobScheduler", i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + str6 + " has called schedule() too many times");
                    this.mQuotaTracker.noteEvent(i2, str6, ".schedulePersisted out-of-quota logged");
                }
                this.mAppStandbyInternal.restrictApp(str6, i2, 4);
                if (this.mConstants.API_QUOTA_SCHEDULE_THROW_EXCEPTION) {
                    synchronized (this.mLock) {
                        if (this.mDebuggableApps.containsKey(str)) {
                            str4 = "Apps may not persist more than ";
                            str5 = "Too many JWIs for uid ";
                        } else {
                            try {
                                str4 = "Apps may not persist more than ";
                                str5 = "Too many JWIs for uid ";
                                ApplicationInfo applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(str6, 0L, i2);
                                if (applicationInfo == null) {
                                    return 0;
                                }
                                this.mDebuggableApps.put(str, Boolean.valueOf((applicationInfo.flags & 2) != 0));
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        boolean booleanValue = ((Boolean) this.mDebuggableApps.get(str)).booleanValue();
                        if (booleanValue) {
                            StringBuilder sb = new StringBuilder("schedule()/enqueue() called more than ");
                            CountQuotaTracker countQuotaTracker = this.mQuotaTracker;
                            Category category = QUOTA_TRACKER_CATEGORY_SCHEDULE_PERSISTED;
                            synchronized (countQuotaTracker.mLock) {
                                Integer num = (Integer) countQuotaTracker.mMaxCategoryCounts.get(category);
                                if (num == null) {
                                    throw new IllegalArgumentException("Limit for " + category + " not defined");
                                }
                                intValue = num.intValue();
                            }
                            sb.append(intValue);
                            sb.append(" times in the past ");
                            CountQuotaTracker countQuotaTracker2 = this.mQuotaTracker;
                            synchronized (countQuotaTracker2.mLock) {
                                Long l = (Long) countQuotaTracker2.mCategoryCountWindowSizesMs.get(category);
                                if (l == null) {
                                    throw new IllegalArgumentException("Limit for " + category + " not defined");
                                }
                                longValue = l.longValue();
                            }
                            throw new LimitExceededException(AudioConfig$$ExternalSyntheticOutline0.m(sb, longValue, "ms. See the documentation for more information."));
                        }
                    }
                } else {
                    str4 = "Apps may not persist more than ";
                    str5 = "Too many JWIs for uid ";
                }
                if (this.mConstants.API_QUOTA_SCHEDULE_RETURN_FAILURE_RESULT) {
                    return 0;
                }
            }
            this.mQuotaTracker.noteEvent(i2, str6, ".schedulePersisted()");
        } else {
            str4 = "Apps may not persist more than ";
            str5 = "Too many JWIs for uid ";
        }
        if (this.mActivityManagerInternal.isAppStartModeDisabled(i, packageName)) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Not scheduling job for ", ":");
            m.append(jobInfo.toString());
            m.append(" -- package not allowed to start");
            Slog.w("JobScheduler", m.toString());
            Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_schedule_failure_app_start_mode_disabled", i);
            return 0;
        }
        if (jobInfo.getRequiredNetwork() != null) {
            sInitialJobEstimatedNetworkDownloadKBLogger.logSample(safelyScaleBytesToKBForHistogram(jobInfo.getEstimatedNetworkDownloadBytes()));
            sInitialJobEstimatedNetworkUploadKBLogger.logSample(safelyScaleBytesToKBForHistogram(jobInfo.getEstimatedNetworkUploadBytes()));
            sJobMinimumChunkKBLogger.logSampleWithUid(i, safelyScaleBytesToKBForHistogram(jobInfo.getMinimumNetworkChunkBytes()));
            if (jobWorkItem != null) {
                sInitialJwiEstimatedNetworkDownloadKBLogger.logSample(safelyScaleBytesToKBForHistogram(jobWorkItem.getEstimatedNetworkDownloadBytes()));
                sInitialJwiEstimatedNetworkUploadKBLogger.logSample(safelyScaleBytesToKBForHistogram(jobWorkItem.getEstimatedNetworkUploadBytes()));
                sJwiMinimumChunkKBLogger.logSampleWithUid(i, safelyScaleBytesToKBForHistogram(jobWorkItem.getMinimumNetworkChunkBytes()));
            }
        }
        if (jobWorkItem != null) {
            Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_job_work_items_enqueued", i);
        }
        synchronized (this.mLock) {
            try {
                JobStatus jobStatus2 = this.mJobs.mJobSet.get(i, jobInfo.getId(), str2);
                if (jobWorkItem != null && jobStatus2 != null) {
                    if (jobStatus2.job.equals(jobInfo)) {
                        if (jobStatus2.getWorkCount() >= this.mConstants.MAX_NUM_PERSISTED_JOB_WORK_ITEMS && jobStatus2.job.isPersisted()) {
                            Slog.w("JobScheduler", "Too many JWIs for uid " + i);
                            throw new IllegalStateException("Apps may not persist more than " + this.mConstants.MAX_NUM_PERSISTED_JOB_WORK_ITEMS + " JobWorkItems per job");
                        }
                        jobStatus2.enqueueWorkLocked(jobWorkItem);
                        if (jobStatus2.job.isUserInitiated()) {
                            jobStatus2.mInternalFlags &= -7;
                        }
                        this.mJobs.touchJob(jobStatus2);
                        sEnqueuedJwiHighWaterMarkLogger.logSampleWithUid(i, jobStatus2.getWorkCount());
                        jobStatus2.maybeAddForegroundExemption(this.mIsUidActivePredicate);
                        return 1;
                    }
                }
                JobStatus createFromJobInfo = JobStatus.createFromJobInfo(jobInfo, i, str, i2, str2, str3);
                if (createFromJobInfo.isRequestedExpeditedJob() && !this.mQuotaController.isWithinEJQuotaLocked(createFromJobInfo)) {
                    Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_schedule_failure_ej_out_of_quota", i);
                    return 0;
                }
                createFromJobInfo.maybeAddForegroundExemption(this.mIsUidActivePredicate);
                if (DEBUG) {
                    Slog.d("JobScheduler", "SCHEDULE: " + createFromJobInfo.toShortString());
                }
                if (str == null && this.mJobs.countJobsForUid(i) > 150) {
                    Slog.w("JobScheduler", "Too many jobs for uid " + i);
                    Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_max_scheduling_limit_hit", i);
                    throw new IllegalStateException("Apps may not schedule more than 150 distinct jobs");
                }
                createFromJobInfo.prepareLocked();
                if (jobStatus2 != null) {
                    if (jobWorkItem != null && jobStatus2.job.isPersisted() && jobStatus2.getWorkCount() >= this.mConstants.MAX_NUM_PERSISTED_JOB_WORK_ITEMS) {
                        Slog.w("JobScheduler", str5 + i);
                        throw new IllegalStateException(str4 + this.mConstants.MAX_NUM_PERSISTED_JOB_WORK_ITEMS + " JobWorkItems per job");
                    }
                    jobStatus = createFromJobInfo;
                    cancelJobImplLocked(jobStatus2, createFromJobInfo, 1, 0, "job rescheduled by app");
                } else {
                    jobStatus = createFromJobInfo;
                    startTrackingJobLocked(jobStatus, null);
                }
                if (jobWorkItem != null) {
                    jobStatus.enqueueWorkLocked(jobWorkItem);
                    sEnqueuedJwiHighWaterMarkLogger.logSampleWithUid(i, jobStatus.getWorkCount());
                }
                int i3 = jobStatus.sourceUid;
                boolean z = jobStatus.mIsProxyJob;
                FrameworkStatsLog.write(8, z ? new int[]{i3, i} : new int[]{i3}, z ? new String[]{null, jobStatus.sourceTag} : new String[]{jobStatus.sourceTag}, jobStatus.batteryName, 2, -1, jobStatus.standbyBucket, jobStatus.mLoggingJobId, jobStatus.hasConstraint(1), jobStatus.hasConstraint(2), jobStatus.hasConstraint(8), jobStatus.hasConstraint(Integer.MIN_VALUE), jobStatus.hasConstraint(1073741824), jobStatus.hasConstraint(4), jobStatus.hasConnectivityConstraint(), jobStatus.hasContentTriggerConstraint(), jobStatus.isRequestedExpeditedJob(), false, 0, jobStatus.job.isPrefetch(), jobStatus.job.getPriority(), jobStatus.getEffectivePriority(), jobStatus.getNumPreviousAttempts(), jobStatus.job.getMaxExecutionDelayMillis(), false, false, false, false, false, false, false, false, 0L, jobStatus.job.isUserInitiated(), false, jobStatus.job.isPeriodic(), jobStatus.job.getMinLatencyMillis(), jobStatus.mTotalNetworkDownloadBytes, jobStatus.mTotalNetworkUploadBytes, jobStatus.getWorkCount(), ActivityManager.processStateAmToProto(this.mUidProcStates.get(jobStatus.callingUid)), jobStatus.mNamespaceHash, 0L, 0L, 0L, 0L, jobStatus.job.getIntervalMillis(), jobStatus.job.getFlexMillis(), jobStatus.hasFlexibilityConstraint(), false, jobStatus.mCanApplyTransportAffinities, jobStatus.mNumAppliedFlexibleConstraints, jobStatus.mNumDroppedFlexibleConstraints, jobStatus.getFilteredTraceTag(), jobStatus.getFilteredDebugTags());
                if (isReadyToBeExecutedLocked(jobStatus)) {
                    this.mJobPackageTracker.notePending(jobStatus);
                    this.mPendingJobQueue.add(jobStatus);
                    maybeRunPendingJobsLocked();
                }
                return 1;
            } finally {
            }
        }
    }

    public final void setFlexPolicy(int i, boolean z) {
        if (DEBUG) {
            Slog.v("JobScheduler", "setFlexPolicy(): " + z + "/" + i);
        }
        this.mFlexibilityController.setLocalPolicyForTesting(i, z);
    }

    public final void startTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (!jobStatus.prepared) {
            Slog.wtf("JobScheduler", "Not yet prepared when started tracking: " + jobStatus);
        }
        sElapsedRealtimeClock.getClass();
        jobStatus.enqueueTime = SystemClock.elapsedRealtime();
        boolean z = jobStatus2 != null;
        JobStore jobStore = this.mJobs;
        if (jobStore.mJobSet.add(jobStatus)) {
            int i = jobStore.mCurrentJobSetSize + 1;
            jobStore.mCurrentJobSetSize = i;
            if (jobStore.mScheduledJob30MinHighWaterMark < i) {
                jobStore.mScheduledJob30MinHighWaterMark = i;
            }
        }
        if (jobStatus.job.isPersisted()) {
            jobStore.mPendingJobWriteUids.put(jobStatus.callingUid, true);
            jobStore.maybeWriteStatusToDiskAsync();
        }
        if (JobStore.DEBUG) {
            Slog.d("JobStore", "Added job status to store: " + jobStatus);
        }
        resetPendingJobReasonCache(jobStatus);
        if (this.mReadyToRock) {
            for (int i2 = 0; i2 < ((ArrayList) this.mControllers).size(); i2++) {
                StateController stateController = (StateController) ((ArrayList) this.mControllers).get(i2);
                if (z) {
                    stateController.maybeStopTrackingJobLocked(jobStatus, null);
                }
                stateController.maybeStartTrackingJobLocked(jobStatus, jobStatus2);
            }
        }
    }

    public final void stopNonReadyActiveJobsLocked() {
        int i;
        int i2 = 0;
        while (true) {
            JobConcurrencyManager jobConcurrencyManager = this.mConcurrencyManager;
            if (i2 >= ((ArrayList) jobConcurrencyManager.mActiveServices).size()) {
                return;
            }
            JobServiceContext jobServiceContext = (JobServiceContext) ((ArrayList) jobConcurrencyManager.mActiveServices).get(i2);
            JobStatus jobStatus = jobServiceContext.mRunningJob;
            if (jobStatus != null) {
                if (jobStatus.isReady(jobStatus.mSatisfiedConstraintsOfInterest)) {
                    JobRestriction checkIfRestricted = jobConcurrencyManager.mService.checkIfRestricted(jobStatus);
                    if (checkIfRestricted != null) {
                        StringBuilder sb = new StringBuilder("restricted due to ");
                        int i3 = checkIfRestricted.mInternalReason;
                        sb.append(JobParameters.getInternalReasonCodeDescription(i3));
                        jobServiceContext.cancelExecutingJobLocked(checkIfRestricted.mStopReason, i3, sb.toString());
                    }
                } else if (jobStatus.getEffectiveStandbyBucket() == 5 && (i = jobStatus.mReasonReadyToUnready) == 12) {
                    jobServiceContext.cancelExecutingJobLocked(i, 6, "cancelled due to restricted bucket");
                } else {
                    jobServiceContext.cancelExecutingJobLocked(jobStatus.mReasonReadyToUnready, 1, "cancelled due to unsatisfied constraints");
                }
            }
            i2++;
        }
    }

    public final boolean stopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2, boolean z) {
        if (jobStatus2 != null) {
            ArrayList arrayList = jobStatus.executingWork;
            if (arrayList != null && arrayList.size() > 0) {
                jobStatus2.pendingWork = jobStatus.executingWork;
            }
            if (jobStatus2.pendingWork == null) {
                jobStatus2.pendingWork = jobStatus.pendingWork;
            } else {
                ArrayList arrayList2 = jobStatus.pendingWork;
                if (arrayList2 != null && arrayList2.size() > 0) {
                    jobStatus2.pendingWork.addAll(jobStatus.pendingWork);
                }
            }
            jobStatus.pendingWork = null;
            jobStatus.executingWork = null;
            jobStatus2.nextPendingWorkId = jobStatus.nextPendingWorkId;
            jobStatus2.updateNetworkBytesLocked();
        } else {
            ArrayList arrayList3 = jobStatus.pendingWork;
            if (arrayList3 != null) {
                int size = arrayList3.size();
                for (int i = 0; i < size; i++) {
                    JobWorkItem jobWorkItem = (JobWorkItem) arrayList3.get(i);
                    if (jobWorkItem.getGrants() != null) {
                        ((GrantedUriPermissions) jobWorkItem.getGrants()).revoke();
                    }
                }
            }
            jobStatus.pendingWork = null;
            ArrayList arrayList4 = jobStatus.executingWork;
            if (arrayList4 != null) {
                int size2 = arrayList4.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    JobWorkItem jobWorkItem2 = (JobWorkItem) arrayList4.get(i2);
                    if (jobWorkItem2.getGrants() != null) {
                        ((GrantedUriPermissions) jobWorkItem2.getGrants()).revoke();
                    }
                }
            }
            jobStatus.executingWork = null;
        }
        jobStatus.updateNetworkBytesLocked();
        synchronized (this.mPendingJobReasonCache) {
            try {
                SparseIntArray sparseIntArray = (SparseIntArray) this.mPendingJobReasonCache.get(jobStatus.callingUid, jobStatus.mNamespace);
                if (sparseIntArray != null) {
                    sparseIntArray.delete(jobStatus.job.getId());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        JobStore jobStore = this.mJobs;
        boolean remove = jobStore.mJobSet.remove(jobStatus);
        if (remove) {
            jobStore.mCurrentJobSetSize--;
            if (z && jobStatus.job.isPersisted()) {
                jobStore.mPendingJobWriteUids.put(jobStatus.callingUid, true);
                jobStore.maybeWriteStatusToDiskAsync();
            }
        } else {
            if (JobStore.DEBUG) {
                Slog.d("JobStore", "Couldn't remove job: didn't exist: " + jobStatus);
            }
            remove = false;
        }
        if (!remove) {
            Slog.w("JobScheduler", "Job didn't exist in JobStore: " + jobStatus.toShortString());
        }
        if (this.mReadyToRock) {
            for (int i3 = 0; i3 < ((ArrayList) this.mControllers).size(); i3++) {
                ((StateController) ((ArrayList) this.mControllers).get(i3)).maybeStopTrackingJobLocked(jobStatus, jobStatus2);
            }
        }
        return remove;
    }

    public void updateQuotaTracker() {
        CountQuotaTracker countQuotaTracker = this.mQuotaTracker;
        Constants constants = this.mConstants;
        boolean z = constants.ENABLE_API_QUOTAS || constants.ENABLE_EXECUTION_SAFEGUARDS_UDC;
        synchronized (countQuotaTracker.mLock) {
            try {
                if (countQuotaTracker.mIsEnabled != z) {
                    countQuotaTracker.mIsEnabled = z;
                    if (!z) {
                        countQuotaTracker.clear();
                    }
                }
            } finally {
            }
        }
        CountQuotaTracker countQuotaTracker2 = this.mQuotaTracker;
        Category category = QUOTA_TRACKER_CATEGORY_SCHEDULE_PERSISTED;
        Constants constants2 = this.mConstants;
        countQuotaTracker2.setCountLimit(category, constants2.API_QUOTA_SCHEDULE_COUNT, constants2.API_QUOTA_SCHEDULE_WINDOW_MS);
        CountQuotaTracker countQuotaTracker3 = this.mQuotaTracker;
        Category category2 = QUOTA_TRACKER_CATEGORY_TIMEOUT_UIJ;
        Constants constants3 = this.mConstants;
        countQuotaTracker3.setCountLimit(category2, constants3.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_UIJ_COUNT, constants3.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS);
        CountQuotaTracker countQuotaTracker4 = this.mQuotaTracker;
        Category category3 = QUOTA_TRACKER_CATEGORY_TIMEOUT_EJ;
        Constants constants4 = this.mConstants;
        countQuotaTracker4.setCountLimit(category3, constants4.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_EJ_COUNT, constants4.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS);
        CountQuotaTracker countQuotaTracker5 = this.mQuotaTracker;
        Category category4 = QUOTA_TRACKER_CATEGORY_TIMEOUT_REG;
        Constants constants5 = this.mConstants;
        countQuotaTracker5.setCountLimit(category4, constants5.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_REG_COUNT, constants5.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS);
        CountQuotaTracker countQuotaTracker6 = this.mQuotaTracker;
        Category category5 = QUOTA_TRACKER_CATEGORY_TIMEOUT_TOTAL;
        Constants constants6 = this.mConstants;
        countQuotaTracker6.setCountLimit(category5, constants6.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_TOTAL_COUNT, constants6.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS);
        CountQuotaTracker countQuotaTracker7 = this.mQuotaTracker;
        Category category6 = QUOTA_TRACKER_CATEGORY_ANR;
        Constants constants7 = this.mConstants;
        countQuotaTracker7.setCountLimit(category6, constants7.EXECUTION_SAFEGUARDS_UDC_ANR_COUNT, constants7.EXECUTION_SAFEGUARDS_UDC_ANR_WINDOW_MS);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x007c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateUidState(int r9, int r10, int r11) {
        /*
            r8 = this;
            java.lang.String r0 = "UID "
            boolean r1 = com.android.server.job.JobSchedulerService.DEBUG
            if (r1 == 0) goto L2a
            java.lang.String r2 = "JobScheduler"
            java.lang.String r3 = "UID "
            java.lang.String r4 = " proc state changed to "
            java.lang.StringBuilder r3 = com.android.server.BatteryService$$ExternalSyntheticOutline0.m(r9, r3, r4)
            java.lang.String r4 = android.app.ActivityManager.procStateToString(r10)
            r3.append(r4)
            java.lang.String r4 = " with capabilities="
            r3.append(r4)
            java.lang.String r4 = android.app.ActivityManager.getCapabilitiesSummary(r11)
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.util.Slog.d(r2, r3)
        L2a:
            java.lang.Object r2 = r8.mLock
            monitor-enter(r2)
            android.util.SparseIntArray r3 = r8.mUidProcStates     // Catch: java.lang.Throwable -> L44
            r3.put(r9, r10)     // Catch: java.lang.Throwable -> L44
            android.util.SparseIntArray r3 = r8.mUidBiasOverride     // Catch: java.lang.Throwable -> L44
            r4 = 0
            int r3 = r3.get(r9, r4)     // Catch: java.lang.Throwable -> L44
            r5 = 2
            r6 = 40
            if (r10 != r5) goto L47
            android.util.SparseIntArray r5 = r8.mUidBiasOverride     // Catch: java.lang.Throwable -> L44
            r5.put(r9, r6)     // Catch: java.lang.Throwable -> L44
            goto L62
        L44:
            r8 = move-exception
            goto Ld1
        L47:
            r5 = 4
            if (r10 > r5) goto L52
            android.util.SparseIntArray r5 = r8.mUidBiasOverride     // Catch: java.lang.Throwable -> L44
            r7 = 35
            r5.put(r9, r7)     // Catch: java.lang.Throwable -> L44
            goto L62
        L52:
            r5 = 5
            if (r10 > r5) goto L5d
            android.util.SparseIntArray r5 = r8.mUidBiasOverride     // Catch: java.lang.Throwable -> L44
            r7 = 30
            r5.put(r9, r7)     // Catch: java.lang.Throwable -> L44
            goto L62
        L5d:
            android.util.SparseIntArray r5 = r8.mUidBiasOverride     // Catch: java.lang.Throwable -> L44
            r5.delete(r9)     // Catch: java.lang.Throwable -> L44
        L62:
            if (r11 == 0) goto L6f
            r5 = 20
            if (r10 != r5) goto L69
            goto L6f
        L69:
            android.util.SparseIntArray r10 = r8.mUidCapabilities     // Catch: java.lang.Throwable -> L44
            r10.put(r9, r11)     // Catch: java.lang.Throwable -> L44
            goto L74
        L6f:
            android.util.SparseIntArray r10 = r8.mUidCapabilities     // Catch: java.lang.Throwable -> L44
            r10.delete(r9)     // Catch: java.lang.Throwable -> L44
        L74:
            android.util.SparseIntArray r10 = r8.mUidBiasOverride     // Catch: java.lang.Throwable -> L44
            int r10 = r10.get(r9, r4)     // Catch: java.lang.Throwable -> L44
            if (r3 == r10) goto Lcf
            if (r1 == 0) goto L9f
            java.lang.String r11 = "JobScheduler"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L44
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L44
            r1.append(r9)     // Catch: java.lang.Throwable -> L44
            java.lang.String r0 = " bias changed from "
            r1.append(r0)     // Catch: java.lang.Throwable -> L44
            r1.append(r3)     // Catch: java.lang.Throwable -> L44
            java.lang.String r0 = " to "
            r1.append(r0)     // Catch: java.lang.Throwable -> L44
            r1.append(r10)     // Catch: java.lang.Throwable -> L44
            java.lang.String r0 = r1.toString()     // Catch: java.lang.Throwable -> L44
            android.util.Slog.d(r11, r0)     // Catch: java.lang.Throwable -> L44
        L9f:
            java.util.List r11 = r8.mControllers     // Catch: java.lang.Throwable -> L44
            java.util.ArrayList r11 = (java.util.ArrayList) r11     // Catch: java.lang.Throwable -> L44
            int r11 = r11.size()     // Catch: java.lang.Throwable -> L44
            if (r4 >= r11) goto Lb9
            java.util.List r11 = r8.mControllers     // Catch: java.lang.Throwable -> L44
            java.util.ArrayList r11 = (java.util.ArrayList) r11     // Catch: java.lang.Throwable -> L44
            java.lang.Object r11 = r11.get(r4)     // Catch: java.lang.Throwable -> L44
            com.android.server.job.controllers.StateController r11 = (com.android.server.job.controllers.StateController) r11     // Catch: java.lang.Throwable -> L44
            r11.onUidBiasChangedLocked(r9, r3, r10)     // Catch: java.lang.Throwable -> L44
            int r4 = r4 + 1
            goto L9f
        Lb9:
            com.android.server.job.JobConcurrencyManager r8 = r8.mConcurrencyManager     // Catch: java.lang.Throwable -> L44
            r8.getClass()     // Catch: java.lang.Throwable -> L44
            if (r3 == r6) goto Lc3
            if (r10 == r6) goto Lc3
            goto Lcf
        Lc3:
            com.android.server.job.JobSchedulerService r9 = r8.mService     // Catch: java.lang.Throwable -> L44
            com.android.server.job.PendingJobQueue r9 = r9.mPendingJobQueue     // Catch: java.lang.Throwable -> L44
            int r9 = r9.mSize     // Catch: java.lang.Throwable -> L44
            if (r9 != 0) goto Lcc
            goto Lcf
        Lcc:
            r8.assignJobsToContextsLocked()     // Catch: java.lang.Throwable -> L44
        Lcf:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L44
            return
        Ld1:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L44
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.JobSchedulerService.updateUidState(int, int, int):void");
    }

    public void waitOnAsyncLoadingForTesting() throws Exception {
        this.mStartControllerTrackingLatch.await();
    }
}

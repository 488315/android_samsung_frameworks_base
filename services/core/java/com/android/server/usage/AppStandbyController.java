package com.android.server.usage;

import android.R;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.compat.CompatChanges;
import android.app.usage.AppStandbyInfo;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.app.usage.UsageStatsManagerInternal;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.CrossProfileAppsInternal;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManager;
import android.net.NetworkScoreManager;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.BatteryManager;
import android.os.Environment;
import android.os.Handler;
import android.os.IDeviceIdleController;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.SparseSetArray;
import android.util.TimeUtils;
import com.android.internal.app.IAppOpsCallback;
import com.android.internal.app.IAppOpsService;
import com.android.internal.app.IBatteryStats;
import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.ArrayUtils;
import com.android.internal.util.jobs.ConcurrentUtils;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.alarm.AlarmManagerService;
import com.android.server.autofill.ui.InlineSuggestionFactory$$ExternalSyntheticLambda2;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.clipboard.ClipboardService;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.usage.AppIdleHistory;
import com.android.server.usage.AppStandbyInternal;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class AppStandbyController implements AppStandbyInternal, UsageStatsManagerInternal.UsageEventListener {
    public final SparseArray mActiveAdminApps;
    public final CountDownLatch mAdminDataAvailableLatch;
    public final SparseArray mAdminProtectedPackages;
    public volatile boolean mAppIdleEnabled;
    public final AppIdleHistory mAppIdleHistory;
    public final Lock mAppIdleLock;
    public AppOpsManager mAppOpsManager;
    public long[] mAppStandbyElapsedThresholds;
    public final Map mAppStandbyProperties;
    public long[] mAppStandbyScreenThresholds;
    public AppWidgetManager mAppWidgetManager;
    public final SparseSetArray mAppsToRestoreToRare;
    public volatile String mBroadcastResponseExemptedPermissions;
    public volatile List mBroadcastResponseExemptedPermissionsList;
    public volatile String mBroadcastResponseExemptedRoles;
    public volatile List mBroadcastResponseExemptedRolesList;
    public volatile int mBroadcastResponseFgThresholdState;
    public volatile long mBroadcastResponseWindowDurationMillis;
    public volatile long mBroadcastSessionsDurationMs;
    public volatile long mBroadcastSessionsWithResponseDurationMs;
    public String mCachedDeviceProvisioningPackage;
    public volatile String mCachedNetworkScorer;
    public volatile long mCachedNetworkScorerAtMillis;
    public List mCarrierPrivilegedApps;
    public final Lock mCarrierPrivilegedLock;
    public long mCheckIdleIntervalMillis;
    public final Context mContext;
    public final AnonymousClass2 mDisplayListener;
    public long mExemptedSyncScheduledDozeTimeoutMillis;
    public long mExemptedSyncScheduledNonDozeTimeoutMillis;
    public long mExemptedSyncStartTimeoutMillis;
    public final AppStandbyHandler mHandler;
    public boolean mHaveCarrierPrivilegedApps;
    public final ArraySet mHeadlessSystemApps;
    public long mInitialForegroundServiceStartTimeoutMillis;
    public final Injector mInjector;
    public volatile boolean mIsCharging;
    public boolean mLinkCrossProfileApps;
    public volatile boolean mNoteResponseEventForAllBroadcastSessions;
    public int mNotificationSeenPromotedBucket;
    public long mNotificationSeenTimeoutMillis;
    public final ArrayList mPackageAccessListeners;
    public final PackageManager mPackageManager;
    public final SparseLongArray mPendingIdleStateChecks;
    public boolean mPendingInitializeDefaults;
    public volatile boolean mPendingOneTimeCheckIdleStates;
    public long mPredictionTimeoutMillis;
    public boolean mRetainNotificationSeenImpactForPreTApps;
    public long mSlicePinnedTimeoutMillis;
    public long mStrongUsageTimeoutMillis;
    public long mSyncAdapterTimeoutMillis;
    public final SparseIntArray mSystemExemptionAppOpMode;
    public long mSystemInteractionTimeoutMillis;
    public final ArrayList mSystemPackagesAppIds;
    public boolean mSystemServicesReady;
    public long mSystemUpdateUsageTimeoutMillis;
    public boolean mTriggerQuotaBumpOnNotificationSeen;
    public long mUnexemptedSyncScheduledTimeoutMillis;
    static final long[] DEFAULT_SCREEN_TIME_THRESHOLDS = {0, 0, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS, 7200000, 21600000};
    static final long[] MINIMUM_SCREEN_TIME_THRESHOLDS = {0, 0, 0, 1800000, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS};
    static final long[] DEFAULT_ELAPSED_TIME_THRESHOLDS = {0, 43200000, BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, 172800000, 691200000};
    static final long[] MINIMUM_ELAPSED_TIME_THRESHOLDS = {0, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS, 7200000, BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS};
    public static final int[] THRESHOLD_BUCKETS = {10, 20, 30, 40, 45};

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppStandbyHandler extends Handler {
        public AppStandbyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            long j;
            long j2;
            int i;
            int i2 = 10;
            switch (message.what) {
                case 3:
                    StandbyUpdateRecord standbyUpdateRecord = (StandbyUpdateRecord) message.obj;
                    AppStandbyController.m1004$$Nest$minformListeners(AppStandbyController.this, standbyUpdateRecord.packageName, standbyUpdateRecord.userId, standbyUpdateRecord.bucket, standbyUpdateRecord.reason, standbyUpdateRecord.isUserInteraction);
                    StandbyUpdateRecord.sPool.recycle(standbyUpdateRecord);
                    return;
                case 4:
                    AppStandbyController.this.forceIdleState((String) message.obj, message.arg1, message.arg2 == 1);
                    return;
                case 5:
                    removeMessages(5);
                    AppStandbyController.this.mInjector.getClass();
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    synchronized (AppStandbyController.this.mPendingIdleStateChecks) {
                        try {
                            j = Long.MAX_VALUE;
                            for (int size = AppStandbyController.this.mPendingIdleStateChecks.size() - 1; size >= 0; size--) {
                                long valueAt = AppStandbyController.this.mPendingIdleStateChecks.valueAt(size);
                                if (valueAt <= elapsedRealtime) {
                                    int keyAt = AppStandbyController.this.mPendingIdleStateChecks.keyAt(size);
                                    if (AppStandbyController.this.checkIdleStates(keyAt) && AppStandbyController.this.mAppIdleEnabled) {
                                        AppStandbyController appStandbyController = AppStandbyController.this;
                                        long j3 = appStandbyController.mCheckIdleIntervalMillis + elapsedRealtime;
                                        appStandbyController.mPendingIdleStateChecks.put(keyAt, j3);
                                        valueAt = j3;
                                    } else {
                                        AppStandbyController.this.mPendingIdleStateChecks.removeAt(size);
                                    }
                                }
                                j = Math.min(j, valueAt);
                            }
                        } finally {
                        }
                    }
                    if (j != Long.MAX_VALUE) {
                        AppStandbyHandler appStandbyHandler = AppStandbyController.this.mHandler;
                        appStandbyHandler.sendMessageDelayed(appStandbyHandler.obtainMessage(5), j - elapsedRealtime);
                        return;
                    }
                    return;
                case 6:
                default:
                    super.handleMessage(message);
                    return;
                case 7:
                    AppStandbyController.m1007$$Nest$mtriggerListenerQuotaBump(AppStandbyController.this, (String) message.obj, message.arg1);
                    return;
                case 8:
                    ContentProviderUsageRecord contentProviderUsageRecord = (ContentProviderUsageRecord) message.obj;
                    AppStandbyController.m1006$$Nest$mreportContentProviderUsage(AppStandbyController.this, contentProviderUsageRecord.name, contentProviderUsageRecord.packageName, contentProviderUsageRecord.userId);
                    ContentProviderUsageRecord.sPool.recycle(contentProviderUsageRecord);
                    return;
                case 9:
                    AppStandbyController.m1005$$Nest$minformParoleStateChanged(AppStandbyController.this);
                    return;
                case 10:
                    AppStandbyController.this.mHandler.removeMessages(10);
                    AppStandbyController appStandbyController2 = AppStandbyController.this;
                    if (appStandbyController2.mContext.getPackageManager().hasSystemFeature("android.software.device_admin")) {
                        ConcurrentUtils.waitForCountDownNoInterrupt(appStandbyController2.mAdminDataAvailableLatch, 10000L, "Wait for admin data");
                    }
                    AppStandbyController.this.checkIdleStates(-1);
                    return;
                case 11:
                    AppStandbyController appStandbyController3 = AppStandbyController.this;
                    String str = (String) message.obj;
                    int i3 = message.arg1;
                    int i4 = message.arg2;
                    appStandbyController3.mInjector.getClass();
                    appStandbyController3.checkAndUpdateStandbyState(i3, i4, SystemClock.elapsedRealtime(), str);
                    return;
                case 12:
                    if (message.arg2 <= 0) {
                        AppStandbyController appStandbyController4 = AppStandbyController.this;
                        String str2 = (String) message.obj;
                        int i5 = message.arg1;
                        if (appStandbyController4.mAppIdleEnabled) {
                            appStandbyController4.mInjector.getClass();
                            long elapsedRealtime2 = SystemClock.elapsedRealtime();
                            synchronized (appStandbyController4.mAppIdleLock) {
                                try {
                                    AppIdleHistory.AppUsageHistory packageHistory = AppIdleHistory.getPackageHistory(appStandbyController4.mAppIdleHistory.getUserHistory(i5), str2, false);
                                    if ((packageHistory == null ? 50 : packageHistory.currentBucket) == 50) {
                                        appStandbyController4.reportNoninteractiveUsageCrossUserLocked(str2, i5, 20, 14, elapsedRealtime2, appStandbyController4.mUnexemptedSyncScheduledTimeoutMillis, appStandbyController4.getCrossProfileTargets(i5, str2));
                                    }
                                } finally {
                                }
                            }
                            return;
                        }
                        return;
                    }
                    AppStandbyController appStandbyController5 = AppStandbyController.this;
                    String str3 = (String) message.obj;
                    int i6 = message.arg1;
                    if (appStandbyController5.mAppIdleEnabled) {
                        if (appStandbyController5.mInjector.mPowerManager.isDeviceIdleMode()) {
                            j2 = appStandbyController5.mExemptedSyncScheduledDozeTimeoutMillis;
                            i2 = 20;
                            i = 12;
                        } else {
                            j2 = appStandbyController5.mExemptedSyncScheduledNonDozeTimeoutMillis;
                            i = 11;
                        }
                        long j4 = j2;
                        int i7 = i;
                        int i8 = i2;
                        appStandbyController5.mInjector.getClass();
                        long elapsedRealtime3 = SystemClock.elapsedRealtime();
                        List crossProfileTargets = appStandbyController5.getCrossProfileTargets(i6, str3);
                        synchronized (appStandbyController5.mAppIdleLock) {
                            appStandbyController5.reportNoninteractiveUsageCrossUserLocked(str3, i6, i8, i7, elapsedRealtime3, j4, crossProfileTargets);
                        }
                        return;
                    }
                    return;
                case 13:
                    AppStandbyController appStandbyController6 = AppStandbyController.this;
                    String str4 = (String) message.obj;
                    int i9 = message.arg1;
                    if (appStandbyController6.mAppIdleEnabled) {
                        appStandbyController6.mInjector.getClass();
                        long elapsedRealtime4 = SystemClock.elapsedRealtime();
                        List crossProfileTargets2 = appStandbyController6.getCrossProfileTargets(i9, str4);
                        synchronized (appStandbyController6.mAppIdleLock) {
                            appStandbyController6.reportNoninteractiveUsageCrossUserLocked(str4, i9, 10, 13, elapsedRealtime4, appStandbyController6.mExemptedSyncStartTimeoutMillis, crossProfileTargets2);
                        }
                        return;
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ConstantsObserver extends ContentObserver implements DeviceConfig.OnPropertiesChangedListener {
        public final String[] KEYS_ELAPSED_TIME_THRESHOLDS;
        public final String[] KEYS_SCREEN_TIME_THRESHOLDS;
        public final TextUtils.SimpleStringSplitter mStringPipeSplitter;

        public ConstantsObserver(Handler handler) {
            super(handler);
            this.KEYS_SCREEN_TIME_THRESHOLDS = new String[]{"screen_threshold_active", "screen_threshold_working_set", "screen_threshold_frequent", "screen_threshold_rare", "screen_threshold_restricted"};
            this.KEYS_ELAPSED_TIME_THRESHOLDS = new String[]{"elapsed_threshold_active", "elapsed_threshold_working_set", "elapsed_threshold_frequent", "elapsed_threshold_rare", "elapsed_threshold_restricted"};
            this.mStringPipeSplitter = new TextUtils.SimpleStringSplitter('|');
        }

        public static long[] generateThresholdArray(DeviceConfig.Properties properties, String[] strArr, long[] jArr, long[] jArr2) {
            if (properties.getKeyset().isEmpty()) {
                return jArr;
            }
            if (strArr.length != 5) {
                throw new IllegalStateException(AmFmBandRange$$ExternalSyntheticOutline0.m(strArr.length, new StringBuilder("# keys ("), ") != # buckets (5)"));
            }
            if (jArr.length != 5) {
                throw new IllegalStateException(AmFmBandRange$$ExternalSyntheticOutline0.m(jArr.length, new StringBuilder("# defaults ("), ") != # buckets (5)"));
            }
            if (jArr2.length != 5) {
                Slog.wtf("AppStandbyController", "minValues array is the wrong size");
                jArr2 = new long[5];
            }
            long[] jArr3 = new long[5];
            for (int i = 0; i < 5; i++) {
                jArr3[i] = Math.max(jArr2[i], properties.getLong(strArr[i], jArr[i]));
            }
            return jArr3;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            updateSettings();
            AppStandbyController.this.postOneTimeCheckIdleStates();
        }

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            processProperties(properties);
            AppStandbyController.this.postOneTimeCheckIdleStates();
        }

        public final void processProperties(DeviceConfig.Properties properties) {
            char c;
            synchronized (AppStandbyController.this.mAppIdleLock) {
                try {
                    boolean z = false;
                    for (String str : properties.getKeyset()) {
                        if (str != null) {
                            switch (str.hashCode()) {
                                case -1991469656:
                                    if (str.equals("sync_adapter_duration")) {
                                        c = '\f';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1963219299:
                                    if (str.equals("brodacast_response_exempted_permissions")) {
                                        c = 23;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1794959158:
                                    if (str.equals("trigger_quota_bump_on_notification_seen")) {
                                        c = 6;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1610671326:
                                    if (str.equals("unexempted_sync_scheduled_duration")) {
                                        c = 16;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1525033432:
                                    if (str.equals("broadcast_sessions_with_response_duration_ms")) {
                                        c = 20;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1063555730:
                                    if (str.equals("slice_pinned_duration")) {
                                        c = 7;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -973233853:
                                    if (str.equals("auto_restricted_bucket_delay_ms")) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -695619964:
                                    if (str.equals("notification_seen_duration")) {
                                        c = 3;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -654339791:
                                    if (str.equals("system_interaction_duration")) {
                                        c = '\n';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -641750299:
                                    if (str.equals("note_response_event_for_all_broadcast_sessions")) {
                                        c = 21;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -557676904:
                                    if (str.equals("system_update_usage_duration")) {
                                        c = 11;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -294320234:
                                    if (str.equals("brodacast_response_exempted_roles")) {
                                        c = 22;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -129077581:
                                    if (str.equals("broadcast_response_window_timeout_ms")) {
                                        c = 17;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -57661244:
                                    if (str.equals("exempted_sync_scheduled_d_duration")) {
                                        c = '\r';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 276460958:
                                    if (str.equals("retain_notification_seen_impact_for_pre_t_apps")) {
                                        c = 5;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 456604392:
                                    if (str.equals("exempted_sync_scheduled_nd_duration")) {
                                        c = 14;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 742365823:
                                    if (str.equals("broadcast_response_fg_threshold_state")) {
                                        c = 18;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 938381045:
                                    if (str.equals("notification_seen_promoted_bucket")) {
                                        c = 4;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 992238669:
                                    if (str.equals("broadcast_sessions_duration_ms")) {
                                        c = 19;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1105744372:
                                    if (str.equals("exempted_sync_start_duration")) {
                                        c = 15;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1288386175:
                                    if (str.equals("cross_profile_apps_share_standby_buckets")) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1378352561:
                                    if (str.equals("prediction_timeout")) {
                                        c = '\t';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1400233242:
                                    if (str.equals("strong_usage_duration")) {
                                        c = '\b';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1915246556:
                                    if (str.equals("initial_foreground_service_start_duration")) {
                                        c = 2;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                default:
                                    c = 65535;
                                    break;
                            }
                            switch (c) {
                                case 0:
                                    AppStandbyController.this.mInjector.mAutoRestrictedBucketDelayMs = Math.max(BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS, properties.getLong("auto_restricted_bucket_delay_ms", ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS));
                                    break;
                                case 1:
                                    AppStandbyController.this.mLinkCrossProfileApps = properties.getBoolean("cross_profile_apps_share_standby_buckets", true);
                                    break;
                                case 2:
                                    AppStandbyController.this.mInitialForegroundServiceStartTimeoutMillis = properties.getLong("initial_foreground_service_start_duration", 1800000L);
                                    break;
                                case 3:
                                    AppStandbyController.this.mNotificationSeenTimeoutMillis = properties.getLong("notification_seen_duration", 43200000L);
                                    break;
                                case 4:
                                    AppStandbyController.this.mNotificationSeenPromotedBucket = properties.getInt("notification_seen_promoted_bucket", 20);
                                    break;
                                case 5:
                                    AppStandbyController.this.mRetainNotificationSeenImpactForPreTApps = properties.getBoolean("retain_notification_seen_impact_for_pre_t_apps", false);
                                    break;
                                case 6:
                                    AppStandbyController.this.mTriggerQuotaBumpOnNotificationSeen = properties.getBoolean("trigger_quota_bump_on_notification_seen", false);
                                    break;
                                case 7:
                                    AppStandbyController.this.mSlicePinnedTimeoutMillis = properties.getLong("slice_pinned_duration", 43200000L);
                                    break;
                                case '\b':
                                    AppStandbyController.this.mStrongUsageTimeoutMillis = properties.getLong("strong_usage_duration", ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
                                    break;
                                case '\t':
                                    AppStandbyController.this.mPredictionTimeoutMillis = properties.getLong("prediction_timeout", 43200000L);
                                    break;
                                case '\n':
                                    AppStandbyController.this.mSystemInteractionTimeoutMillis = properties.getLong("system_interaction_duration", 600000L);
                                    break;
                                case 11:
                                    AppStandbyController.this.mSystemUpdateUsageTimeoutMillis = properties.getLong("system_update_usage_duration", 7200000L);
                                    break;
                                case '\f':
                                    AppStandbyController.this.mSyncAdapterTimeoutMillis = properties.getLong("sync_adapter_duration", 600000L);
                                    break;
                                case '\r':
                                    AppStandbyController.this.mExemptedSyncScheduledDozeTimeoutMillis = properties.getLong("exempted_sync_scheduled_d_duration", BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
                                    break;
                                case 14:
                                    AppStandbyController.this.mExemptedSyncScheduledNonDozeTimeoutMillis = properties.getLong("exempted_sync_scheduled_nd_duration", 600000L);
                                    break;
                                case 15:
                                    AppStandbyController.this.mExemptedSyncStartTimeoutMillis = properties.getLong("exempted_sync_start_duration", 600000L);
                                    break;
                                case 16:
                                    AppStandbyController.this.mUnexemptedSyncScheduledTimeoutMillis = properties.getLong("unexempted_sync_scheduled_duration", 600000L);
                                    break;
                                case 17:
                                    AppStandbyController.this.mBroadcastResponseWindowDurationMillis = properties.getLong("broadcast_response_window_timeout_ms", 120000L);
                                    break;
                                case 18:
                                    AppStandbyController.this.mBroadcastResponseFgThresholdState = properties.getInt("broadcast_response_fg_threshold_state", 2);
                                    break;
                                case 19:
                                    AppStandbyController.this.mBroadcastSessionsDurationMs = properties.getLong("broadcast_sessions_duration_ms", 120000L);
                                    break;
                                case 20:
                                    AppStandbyController.this.mBroadcastSessionsWithResponseDurationMs = properties.getLong("broadcast_sessions_with_response_duration_ms", 120000L);
                                    break;
                                case 21:
                                    AppStandbyController.this.mNoteResponseEventForAllBroadcastSessions = properties.getBoolean("note_response_event_for_all_broadcast_sessions", true);
                                    break;
                                case 22:
                                    AppStandbyController.this.mBroadcastResponseExemptedRoles = properties.getString("brodacast_response_exempted_roles", "");
                                    AppStandbyController appStandbyController = AppStandbyController.this;
                                    String str2 = appStandbyController.mBroadcastResponseExemptedRoles;
                                    ArrayList arrayList = new ArrayList();
                                    this.mStringPipeSplitter.setString(str2);
                                    while (this.mStringPipeSplitter.hasNext()) {
                                        arrayList.add(this.mStringPipeSplitter.next());
                                    }
                                    appStandbyController.mBroadcastResponseExemptedRolesList = arrayList;
                                    break;
                                case 23:
                                    AppStandbyController.this.mBroadcastResponseExemptedPermissions = properties.getString("brodacast_response_exempted_permissions", "");
                                    AppStandbyController appStandbyController2 = AppStandbyController.this;
                                    String str3 = appStandbyController2.mBroadcastResponseExemptedPermissions;
                                    ArrayList arrayList2 = new ArrayList();
                                    this.mStringPipeSplitter.setString(str3);
                                    while (this.mStringPipeSplitter.hasNext()) {
                                        arrayList2.add(this.mStringPipeSplitter.next());
                                    }
                                    appStandbyController2.mBroadcastResponseExemptedPermissionsList = arrayList2;
                                    break;
                                default:
                                    if (!z && (str.startsWith("screen_threshold_") || str.startsWith("elapsed_threshold_"))) {
                                        updateTimeThresholds();
                                        z = true;
                                        break;
                                    }
                                    break;
                            }
                            ((ArrayMap) AppStandbyController.this.mAppStandbyProperties).put(str, properties.getString(str, (String) null));
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void updateSettings() {
            AppStandbyController appStandbyController = AppStandbyController.this;
            Injector injector = appStandbyController.mInjector;
            appStandbyController.setAppIdleEnabled(injector.mContext.getResources().getBoolean(R.bool.config_enableDefaultHdrConversionPassthrough) && (Settings.Global.getInt(injector.mContext.getContentResolver(), "app_standby_enabled", 1) == 1 && Settings.Global.getInt(injector.mContext.getContentResolver(), "adaptive_battery_management_enabled", 1) == 1));
        }

        public final void updateTimeThresholds() {
            Injector injector = AppStandbyController.this.mInjector;
            String[] strArr = this.KEYS_SCREEN_TIME_THRESHOLDS;
            injector.getClass();
            DeviceConfig.Properties properties = DeviceConfig.getProperties("app_standby", strArr);
            Injector injector2 = AppStandbyController.this.mInjector;
            String[] strArr2 = this.KEYS_ELAPSED_TIME_THRESHOLDS;
            injector2.getClass();
            DeviceConfig.Properties properties2 = DeviceConfig.getProperties("app_standby", strArr2);
            AppStandbyController.this.mAppStandbyScreenThresholds = generateThresholdArray(properties, this.KEYS_SCREEN_TIME_THRESHOLDS, AppStandbyController.DEFAULT_SCREEN_TIME_THRESHOLDS, AppStandbyController.MINIMUM_SCREEN_TIME_THRESHOLDS);
            AppStandbyController.this.mAppStandbyElapsedThresholds = generateThresholdArray(properties2, this.KEYS_ELAPSED_TIME_THRESHOLDS, AppStandbyController.DEFAULT_ELAPSED_TIME_THRESHOLDS, AppStandbyController.MINIMUM_ELAPSED_TIME_THRESHOLDS);
            AppStandbyController appStandbyController = AppStandbyController.this;
            appStandbyController.mCheckIdleIntervalMillis = Math.min(appStandbyController.mAppStandbyElapsedThresholds[1] / 4, BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ContentProviderUsageRecord {
        public static final Pool sPool = new Pool(new ContentProviderUsageRecord[10]);
        public String name;
        public String packageName;
        public int userId;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {
        public AlarmManagerService.LocalService mAlarmManagerInternal;
        public BatteryManager mBatteryManager;
        public IBatteryStats mBatteryStats;
        public int mBootPhase;
        public final Context mContext;
        public CrossProfileAppsInternal mCrossProfileAppsInternal;
        public IDeviceIdleController mDeviceIdleController;
        public DisplayManager mDisplayManager;
        public final Looper mLooper;
        public PackageManagerInternal mPackageManagerInternal;
        public PowerManager mPowerManager;
        public long mAutoRestrictedBucketDelayMs = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
        public final ArraySet mPowerWhitelistedApps = new ArraySet();
        public String mWellbeingApp = null;

        public Injector(Context context, Looper looper) {
            this.mContext = context;
            this.mLooper = looper;
        }

        public final void dump(PrintWriter printWriter) {
            printWriter.println("mPowerWhitelistedApps=[");
            synchronized (this.mPowerWhitelistedApps) {
                try {
                    for (int size = this.mPowerWhitelistedApps.size() - 1; size >= 0; size--) {
                        printWriter.print("  ");
                        printWriter.print((String) this.mPowerWhitelistedApps.valueAt(size));
                        printWriter.println(",");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            printWriter.println("]");
            printWriter.println();
        }

        public final boolean isPackageInstalled(int i, String str) {
            return this.mPackageManagerInternal.getPackageUid(str, (long) 0, i) >= 0;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lock {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageReceiver extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AppStandbyController this$0;

        public /* synthetic */ PackageReceiver(AppStandbyController appStandbyController, int i) {
            this.$r8$classId = i;
            this.this$0 = appStandbyController;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    String action = intent.getAction();
                    String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    int sendingUserId = getSendingUserId();
                    if ("android.intent.action.PACKAGE_ADDED".equals(action) || "android.intent.action.PACKAGE_CHANGED".equals(action)) {
                        String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_component_name_list");
                        if (stringArrayExtra == null || (stringArrayExtra.length == 1 && schemeSpecificPart.equals(stringArrayExtra[0]))) {
                            this.this$0.clearCarrierPrivilegedApps();
                            AppStandbyController appStandbyController = this.this$0;
                            if (appStandbyController.mSystemServicesReady) {
                                try {
                                    appStandbyController.maybeUpdateHeadlessSystemAppCache(appStandbyController.mPackageManager.getPackageInfoAsUser(schemeSpecificPart, 1835520, sendingUserId));
                                } catch (PackageManager.NameNotFoundException unused) {
                                    synchronized (appStandbyController.mHeadlessSystemApps) {
                                        appStandbyController.mHeadlessSystemApps.remove(schemeSpecificPart);
                                    }
                                }
                            }
                        }
                        if ("android.intent.action.PACKAGE_CHANGED".equals(action)) {
                            this.this$0.mHandler.obtainMessage(11, sendingUserId, -1, schemeSpecificPart).sendToTarget();
                        }
                    }
                    if ("android.intent.action.PACKAGE_REMOVED".equals(action) || "android.intent.action.PACKAGE_ADDED".equals(action)) {
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            this.this$0.maybeUnrestrictBuggyApp(schemeSpecificPart, sendingUserId);
                        } else if (!"android.intent.action.PACKAGE_ADDED".equals(action)) {
                            this.this$0.clearAppIdleForPackage(schemeSpecificPart, sendingUserId);
                        } else if (this.this$0.mAppsToRestoreToRare.contains(sendingUserId, schemeSpecificPart)) {
                            AppStandbyController appStandbyController2 = this.this$0;
                            appStandbyController2.mInjector.getClass();
                            long elapsedRealtime = SystemClock.elapsedRealtime();
                            if (appStandbyController2.getAppStandbyBucket(schemeSpecificPart, sendingUserId, elapsedRealtime, false) == 50) {
                                appStandbyController2.setAppStandbyBucket(sendingUserId, 40, 258, elapsedRealtime, schemeSpecificPart, false);
                            }
                            this.this$0.mAppsToRestoreToRare.remove(sendingUserId, schemeSpecificPart);
                        }
                    }
                    synchronized (this.this$0.mSystemExemptionAppOpMode) {
                        try {
                            if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                                this.this$0.mSystemExemptionAppOpMode.delete(intent.getIntExtra("android.intent.extra.UID", -1));
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return;
                default:
                    String action2 = intent.getAction();
                    action2.getClass();
                    switch (action2) {
                        case "android.os.action.POWER_SAVE_WHITELIST_CHANGED":
                            AppStandbyController appStandbyController3 = this.this$0;
                            if (appStandbyController3.mSystemServicesReady) {
                                appStandbyController3.mHandler.post(new AppStandbyController$$ExternalSyntheticLambda0(appStandbyController3, 0));
                                return;
                            }
                            return;
                        case "android.os.action.DISCHARGING":
                            this.this$0.setChargingState(false);
                            return;
                        case "android.os.action.CHARGING":
                            this.this$0.setChargingState(true);
                            return;
                        default:
                            return;
                    }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Pool {
        public final Object[] mArray;
        public int mSize = 0;

        public Pool(Object[] objArr) {
            this.mArray = objArr;
        }

        public final synchronized Object obtain() {
            Object obj;
            int i = this.mSize;
            if (i > 0) {
                Object[] objArr = this.mArray;
                int i2 = i - 1;
                this.mSize = i2;
                obj = objArr[i2];
            } else {
                obj = null;
            }
            return obj;
        }

        public final synchronized void recycle(Object obj) {
            int i = this.mSize;
            Object[] objArr = this.mArray;
            if (i < objArr.length) {
                this.mSize = i + 1;
                objArr[i] = obj;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StandbyUpdateRecord {
        public static final Pool sPool = new Pool(new StandbyUpdateRecord[10]);
        public int bucket;
        public boolean isUserInteraction;
        public String packageName;
        public int reason;
        public int userId;
    }

    /* renamed from: -$$Nest$minformListeners, reason: not valid java name */
    public static void m1004$$Nest$minformListeners(AppStandbyController appStandbyController, String str, int i, int i2, int i3, boolean z) {
        appStandbyController.getClass();
        boolean z2 = i2 >= 40;
        synchronized (appStandbyController.mPackageAccessListeners) {
            try {
                Iterator it = appStandbyController.mPackageAccessListeners.iterator();
                while (it.hasNext()) {
                    AppStandbyInternal.AppIdleStateChangeListener appIdleStateChangeListener = (AppStandbyInternal.AppIdleStateChangeListener) it.next();
                    appIdleStateChangeListener.onAppIdleStateChanged(str, i, z2, i2, i3);
                    if (z) {
                        appIdleStateChangeListener.onUserInteractionStarted(str, i);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$minformParoleStateChanged, reason: not valid java name */
    public static void m1005$$Nest$minformParoleStateChanged(AppStandbyController appStandbyController) {
        boolean isInParole = appStandbyController.isInParole();
        synchronized (appStandbyController.mPackageAccessListeners) {
            try {
                Iterator it = appStandbyController.mPackageAccessListeners.iterator();
                while (it.hasNext()) {
                    ((AppStandbyInternal.AppIdleStateChangeListener) it.next()).onParoleStateChanged(isInParole);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:? -> B:20:0x0068). Please report as a decompilation issue!!! */
    /* renamed from: -$$Nest$mreportContentProviderUsage, reason: not valid java name */
    public static void m1006$$Nest$mreportContentProviderUsage(AppStandbyController appStandbyController, String str, String str2, int i) {
        Lock lock;
        int i2;
        int i3;
        if (appStandbyController.mAppIdleEnabled) {
            String[] syncAdapterPackagesForAuthorityAsUser = ContentResolver.getSyncAdapterPackagesForAuthorityAsUser(str, i);
            PackageManagerInternal packageManagerInternal = appStandbyController.mInjector.mPackageManagerInternal;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            int length = syncAdapterPackagesForAuthorityAsUser.length;
            int i4 = 0;
            while (i4 < length) {
                String str3 = syncAdapterPackagesForAuthorityAsUser[i4];
                if (!str3.equals(str2)) {
                    if (appStandbyController.mSystemPackagesAppIds.contains(Integer.valueOf(UserHandle.getAppId(packageManagerInternal.getPackageUid(str3, 0L, i))))) {
                        List crossProfileTargets = appStandbyController.getCrossProfileTargets(i, str3);
                        Lock lock2 = appStandbyController.mAppIdleLock;
                        synchronized (lock2) {
                            try {
                                lock = lock2;
                                i2 = i4;
                                i3 = length;
                            } catch (Throwable th) {
                                th = th;
                                lock = lock2;
                                throw th;
                            }
                            try {
                                appStandbyController.reportNoninteractiveUsageCrossUserLocked(str3, i, 10, 8, elapsedRealtime, appStandbyController.mSyncAdapterTimeoutMillis, crossProfileTargets);
                                i4 = i2 + 1;
                                length = i3;
                            } catch (Throwable th2) {
                                th = th2;
                                throw th;
                            }
                        }
                    }
                }
                i2 = i4;
                i3 = length;
                i4 = i2 + 1;
                length = i3;
            }
        }
    }

    /* renamed from: -$$Nest$mtriggerListenerQuotaBump, reason: not valid java name */
    public static void m1007$$Nest$mtriggerListenerQuotaBump(AppStandbyController appStandbyController, String str, int i) {
        if (appStandbyController.mAppIdleEnabled) {
            synchronized (appStandbyController.mPackageAccessListeners) {
                try {
                    Iterator it = appStandbyController.mPackageAccessListeners.iterator();
                    while (it.hasNext()) {
                        ((AppStandbyInternal.AppIdleStateChangeListener) it.next()).triggerTemporaryQuotaBump(str, i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v14, types: [com.android.server.usage.AppStandbyController$2] */
    public AppStandbyController(Context context) {
        Injector injector = new Injector(context, AppSchedulingModuleThread.get().getLooper());
        Lock lock = new Lock();
        this.mAppIdleLock = lock;
        this.mPackageAccessListeners = new ArrayList();
        this.mCarrierPrivilegedLock = new Lock();
        this.mActiveAdminApps = new SparseArray();
        this.mAdminProtectedPackages = new SparseArray();
        this.mHeadlessSystemApps = new ArraySet();
        this.mAdminDataAvailableLatch = new CountDownLatch(1);
        this.mPendingIdleStateChecks = new SparseLongArray();
        this.mSystemExemptionAppOpMode = new SparseIntArray();
        this.mCachedNetworkScorer = null;
        this.mCachedNetworkScorerAtMillis = 0L;
        this.mCachedDeviceProvisioningPackage = null;
        long[] jArr = DEFAULT_ELAPSED_TIME_THRESHOLDS;
        this.mCheckIdleIntervalMillis = Math.min(jArr[1] / 4, BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
        this.mAppStandbyScreenThresholds = DEFAULT_SCREEN_TIME_THRESHOLDS;
        this.mAppStandbyElapsedThresholds = jArr;
        this.mStrongUsageTimeoutMillis = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
        this.mNotificationSeenTimeoutMillis = 43200000L;
        this.mSlicePinnedTimeoutMillis = 43200000L;
        this.mNotificationSeenPromotedBucket = 20;
        this.mTriggerQuotaBumpOnNotificationSeen = false;
        this.mRetainNotificationSeenImpactForPreTApps = false;
        this.mSystemUpdateUsageTimeoutMillis = 7200000L;
        this.mPredictionTimeoutMillis = 43200000L;
        this.mSyncAdapterTimeoutMillis = 600000L;
        this.mExemptedSyncScheduledNonDozeTimeoutMillis = 600000L;
        this.mExemptedSyncScheduledDozeTimeoutMillis = BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS;
        this.mExemptedSyncStartTimeoutMillis = 600000L;
        this.mUnexemptedSyncScheduledTimeoutMillis = 600000L;
        this.mSystemInteractionTimeoutMillis = 600000L;
        this.mInitialForegroundServiceStartTimeoutMillis = 1800000L;
        this.mLinkCrossProfileApps = true;
        this.mBroadcastResponseWindowDurationMillis = 120000L;
        this.mBroadcastResponseFgThresholdState = 2;
        this.mBroadcastSessionsDurationMs = 120000L;
        this.mBroadcastSessionsWithResponseDurationMs = 120000L;
        this.mNoteResponseEventForAllBroadcastSessions = true;
        this.mBroadcastResponseExemptedRoles = "";
        List list = Collections.EMPTY_LIST;
        this.mBroadcastResponseExemptedRolesList = list;
        this.mBroadcastResponseExemptedPermissions = "";
        this.mBroadcastResponseExemptedPermissionsList = list;
        this.mAppStandbyProperties = new ArrayMap();
        this.mAppsToRestoreToRare = new SparseSetArray();
        this.mSystemPackagesAppIds = new ArrayList();
        this.mSystemServicesReady = false;
        this.mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.android.server.usage.AppStandbyController.2
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i) {
                if (i == 0) {
                    boolean z = AppStandbyController.this.mInjector.mDisplayManager.getDisplay(0).getState() == 2;
                    synchronized (AppStandbyController.this.mAppIdleLock) {
                        AppStandbyController appStandbyController = AppStandbyController.this;
                        AppIdleHistory appIdleHistory = appStandbyController.mAppIdleHistory;
                        appStandbyController.mInjector.getClass();
                        appIdleHistory.updateDisplay(SystemClock.elapsedRealtime(), z);
                    }
                }
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i) {
            }
        };
        this.mInjector = injector;
        Context context2 = injector.mContext;
        this.mContext = context2;
        AppStandbyHandler appStandbyHandler = new AppStandbyHandler(injector.mLooper);
        this.mHandler = appStandbyHandler;
        this.mPackageManager = context2.getPackageManager();
        PackageReceiver packageReceiver = new PackageReceiver(this, 1);
        IntentFilter intentFilter = new IntentFilter("android.os.action.CHARGING");
        intentFilter.addAction("android.os.action.DISCHARGING");
        intentFilter.addAction("android.os.action.POWER_SAVE_WHITELIST_CHANGED");
        context2.registerReceiver(packageReceiver, intentFilter);
        synchronized (lock) {
            this.mAppIdleHistory = new AppIdleHistory(Environment.getDataSystemDirectory(), SystemClock.elapsedRealtime());
        }
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addDataScheme("package");
        context2.registerReceiverAsUser(new PackageReceiver(this, 0), UserHandle.ALL, intentFilter2, null, appStandbyHandler);
    }

    public static int getMinBucketWithValidExpiryTime(AppIdleHistory.AppUsageHistory appUsageHistory, int i, long j) {
        SparseLongArray sparseLongArray = appUsageHistory.bucketExpiryTimesMs;
        if (sparseLongArray == null) {
            return -1;
        }
        int size = sparseLongArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = appUsageHistory.bucketExpiryTimesMs.keyAt(i2);
            if (i <= keyAt) {
                break;
            }
            if (appUsageHistory.bucketExpiryTimesMs.valueAt(i2) > j) {
                return keyAt;
            }
        }
        return -1;
    }

    public final void addActiveDeviceAdmin(String str, int i) {
        synchronized (this.mActiveAdminApps) {
            try {
                Set set = (Set) this.mActiveAdminApps.get(i);
                if (set == null) {
                    set = new ArraySet();
                    this.mActiveAdminApps.put(i, set);
                }
                set.add(str);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addListener(AppStandbyInternal.AppIdleStateChangeListener appIdleStateChangeListener) {
        synchronized (this.mPackageAccessListeners) {
            try {
                if (!this.mPackageAccessListeners.contains(appIdleStateChangeListener)) {
                    this.mPackageAccessListeners.add(appIdleStateChangeListener);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0106 A[Catch: all -> 0x0080, TryCatch #0 {all -> 0x0080, blocks: (B:32:0x0068, B:34:0x007e, B:36:0x0083, B:38:0x0089, B:40:0x008b, B:42:0x0097, B:52:0x00e0, B:55:0x00f1, B:60:0x0100, B:62:0x0106, B:64:0x010c, B:66:0x0115, B:74:0x0152, B:75:0x012a, B:77:0x00fb, B:80:0x00c1, B:86:0x00cf, B:89:0x00da), top: B:31:0x0068 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0122 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00cf A[Catch: all -> 0x0080, TryCatch #0 {all -> 0x0080, blocks: (B:32:0x0068, B:34:0x007e, B:36:0x0083, B:38:0x0089, B:40:0x008b, B:42:0x0097, B:52:0x00e0, B:55:0x00f1, B:60:0x0100, B:62:0x0106, B:64:0x010c, B:66:0x0115, B:74:0x0152, B:75:0x012a, B:77:0x00fb, B:80:0x00c1, B:86:0x00cf, B:89:0x00da), top: B:31:0x0068 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void checkAndUpdateStandbyState(int r20, int r21, long r22, java.lang.String r24) {
        /*
            Method dump skipped, instructions count: 349
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.usage.AppStandbyController.checkAndUpdateStandbyState(int, int, long, java.lang.String):void");
    }

    public boolean checkIdleStates(int i) {
        if (!this.mAppIdleEnabled) {
            return false;
        }
        try {
            this.mInjector.getClass();
            int[] runningUserIds = ActivityManager.getService().getRunningUserIds();
            if (i != -1) {
                if (!ArrayUtils.contains(runningUserIds, i)) {
                    return false;
                }
            }
            this.mInjector.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            for (int i2 : runningUserIds) {
                if (i == -1 || i == i2) {
                    List installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(512, i2);
                    int i3 = 0;
                    for (int size = installedPackagesAsUser.size(); i3 < size; size = size) {
                        PackageInfo packageInfo = (PackageInfo) installedPackagesAsUser.get(i3);
                        checkAndUpdateStandbyState(i2, packageInfo.applicationInfo.uid, elapsedRealtime, packageInfo.packageName);
                        i3++;
                    }
                }
            }
            return true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearAppIdleForPackage(String str, int i) {
        synchronized (this.mAppIdleLock) {
            this.mAppIdleHistory.getUserHistory(i).remove(str);
        }
    }

    public final void clearCarrierPrivilegedApps() {
        synchronized (this.mCarrierPrivilegedLock) {
            this.mHaveCarrierPrivilegedApps = false;
            this.mCarrierPrivilegedApps = null;
        }
    }

    public final void clearLastUsedTimestampsForTest(String str, int i) {
        synchronized (this.mAppIdleLock) {
            ArrayMap userHistory = this.mAppIdleHistory.getUserHistory(i);
            SystemClock.elapsedRealtime();
            AppIdleHistory.AppUsageHistory packageHistory = AppIdleHistory.getPackageHistory(userHistory, str, false);
            if (packageHistory != null) {
                packageHistory.lastUsedByUserElapsedTime = -2147483648L;
                packageHistory.lastUsedElapsedTime = -2147483648L;
                packageHistory.lastUsedScreenTime = -2147483648L;
            }
        }
    }

    public final void dumpState(String[] strArr, PrintWriter printWriter) {
        int i;
        printWriter.println("Flags: ");
        Flags.avoidIdleCheck();
        printWriter.println("    com.android.server.usage.avoid_idle_check: false");
        printWriter.println();
        synchronized (this.mCarrierPrivilegedLock) {
            printWriter.println("Carrier privileged apps (have=" + this.mHaveCarrierPrivilegedApps + "): " + this.mCarrierPrivilegedApps);
        }
        printWriter.println();
        printWriter.println("Settings:");
        printWriter.print("  mCheckIdleIntervalMillis=");
        TimeUtils.formatDuration(this.mCheckIdleIntervalMillis, printWriter);
        printWriter.println();
        printWriter.print("  mStrongUsageTimeoutMillis=");
        TimeUtils.formatDuration(this.mStrongUsageTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mNotificationSeenTimeoutMillis=");
        TimeUtils.formatDuration(this.mNotificationSeenTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mNotificationSeenPromotedBucket=");
        printWriter.print(UsageStatsManager.standbyBucketToString(this.mNotificationSeenPromotedBucket));
        printWriter.println();
        printWriter.print("  mTriggerQuotaBumpOnNotificationSeen=");
        printWriter.print(this.mTriggerQuotaBumpOnNotificationSeen);
        printWriter.println();
        printWriter.print("  mRetainNotificationSeenImpactForPreTApps=");
        printWriter.print(this.mRetainNotificationSeenImpactForPreTApps);
        printWriter.println();
        printWriter.print("  mSlicePinnedTimeoutMillis=");
        TimeUtils.formatDuration(this.mSlicePinnedTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mSyncAdapterTimeoutMillis=");
        TimeUtils.formatDuration(this.mSyncAdapterTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mSystemInteractionTimeoutMillis=");
        TimeUtils.formatDuration(this.mSystemInteractionTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mInitialForegroundServiceStartTimeoutMillis=");
        TimeUtils.formatDuration(this.mInitialForegroundServiceStartTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mPredictionTimeoutMillis=");
        TimeUtils.formatDuration(this.mPredictionTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mExemptedSyncScheduledNonDozeTimeoutMillis=");
        TimeUtils.formatDuration(this.mExemptedSyncScheduledNonDozeTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mExemptedSyncScheduledDozeTimeoutMillis=");
        TimeUtils.formatDuration(this.mExemptedSyncScheduledDozeTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mExemptedSyncStartTimeoutMillis=");
        TimeUtils.formatDuration(this.mExemptedSyncStartTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mUnexemptedSyncScheduledTimeoutMillis=");
        TimeUtils.formatDuration(this.mUnexemptedSyncScheduledTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mSystemUpdateUsageTimeoutMillis=");
        TimeUtils.formatDuration(this.mSystemUpdateUsageTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mBroadcastResponseWindowDurationMillis=");
        TimeUtils.formatDuration(this.mBroadcastResponseWindowDurationMillis, printWriter);
        printWriter.println();
        printWriter.print("  mBroadcastResponseFgThresholdState=");
        printWriter.print(ActivityManager.procStateToString(this.mBroadcastResponseFgThresholdState));
        printWriter.println();
        printWriter.print("  mBroadcastSessionsDurationMs=");
        TimeUtils.formatDuration(this.mBroadcastSessionsDurationMs, printWriter);
        printWriter.println();
        printWriter.print("  mBroadcastSessionsWithResponseDurationMs=");
        TimeUtils.formatDuration(this.mBroadcastSessionsWithResponseDurationMs, printWriter);
        printWriter.println();
        printWriter.print("  mNoteResponseEventForAllBroadcastSessions=");
        printWriter.print(this.mNoteResponseEventForAllBroadcastSessions);
        printWriter.println();
        printWriter.print("  mBroadcastResponseExemptedRoles=");
        printWriter.print(this.mBroadcastResponseExemptedRoles);
        printWriter.println();
        printWriter.print("  mBroadcastResponseExemptedPermissions=");
        printWriter.print(this.mBroadcastResponseExemptedPermissions);
        printWriter.println();
        printWriter.println();
        printWriter.print("mAppIdleEnabled=");
        printWriter.print(this.mAppIdleEnabled);
        printWriter.print(" mIsCharging=");
        printWriter.print(this.mIsCharging);
        printWriter.println();
        printWriter.print("mScreenThresholds=");
        printWriter.println(Arrays.toString(this.mAppStandbyScreenThresholds));
        printWriter.print("mElapsedThresholds=");
        printWriter.println(Arrays.toString(this.mAppStandbyElapsedThresholds));
        printWriter.println();
        printWriter.println("mHeadlessSystemApps=[");
        synchronized (this.mHeadlessSystemApps) {
            try {
                for (int size = this.mHeadlessSystemApps.size() - 1; size >= 0; size--) {
                    printWriter.print("  ");
                    printWriter.print((String) this.mHeadlessSystemApps.valueAt(size));
                    if (size != 0) {
                        printWriter.println(",");
                    }
                }
            } finally {
            }
        }
        printWriter.println("]");
        printWriter.println();
        printWriter.println("mSystemPackagesAppIds=[");
        synchronized (this.mSystemPackagesAppIds) {
            try {
                for (int size2 = this.mSystemPackagesAppIds.size() - 1; size2 >= 0; size2--) {
                    printWriter.print("  ");
                    printWriter.print(this.mSystemPackagesAppIds.get(size2));
                    if (size2 != 0) {
                        printWriter.println(",");
                    }
                }
            } finally {
            }
        }
        printWriter.println("]");
        printWriter.println();
        printWriter.println("mActiveAdminApps=[");
        synchronized (this.mActiveAdminApps) {
            try {
                int size3 = this.mActiveAdminApps.size();
                for (int i2 = 0; i2 < size3; i2++) {
                    int keyAt = this.mActiveAdminApps.keyAt(i2);
                    printWriter.print(" ");
                    printWriter.print(keyAt);
                    printWriter.print(": ");
                    printWriter.print(this.mActiveAdminApps.valueAt(i2));
                    if (i2 != size3 - 1) {
                        printWriter.print(",");
                    }
                    printWriter.println();
                }
            } finally {
            }
        }
        printWriter.println("]");
        printWriter.println();
        printWriter.println("mAdminProtectedPackages=[");
        synchronized (this.mAdminProtectedPackages) {
            try {
                int size4 = this.mAdminProtectedPackages.size();
                for (i = 0; i < size4; i++) {
                    int keyAt2 = this.mAdminProtectedPackages.keyAt(i);
                    printWriter.print(" ");
                    printWriter.print(keyAt2);
                    printWriter.print(": ");
                    printWriter.print(this.mAdminProtectedPackages.valueAt(i));
                    if (i != size4 - 1) {
                        printWriter.print(",");
                    }
                    printWriter.println();
                }
            } finally {
            }
        }
        printWriter.println("]");
        printWriter.println();
        this.mInjector.dump(printWriter);
    }

    public final void dumpUsers(IndentingPrintWriter indentingPrintWriter, int[] iArr, List list) {
        synchronized (this.mAppIdleLock) {
            this.mAppIdleHistory.dumpUsers(indentingPrintWriter, iArr, list);
        }
    }

    public boolean flushHandler(long j) {
        return this.mHandler.runWithScissors(new InlineSuggestionFactory$$ExternalSyntheticLambda2(), j);
    }

    public final void flushToDisk() {
        synchronized (this.mAppIdleLock) {
            AppIdleHistory appIdleHistory = this.mAppIdleHistory;
            this.mInjector.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            int size = appIdleHistory.mIdleHistory.size();
            for (int i = 0; i < size; i++) {
                appIdleHistory.writeAppIdleTimes(appIdleHistory.mIdleHistory.keyAt(i), elapsedRealtime);
            }
            AppIdleHistory appIdleHistory2 = this.mAppIdleHistory;
            appIdleHistory2.getClass();
            long elapsedRealtime2 = SystemClock.elapsedRealtime();
            appIdleHistory2.mElapsedDuration = (elapsedRealtime2 - appIdleHistory2.mElapsedSnapshot) + appIdleHistory2.mElapsedDuration;
            appIdleHistory2.mElapsedSnapshot = elapsedRealtime2;
            appIdleHistory2.writeScreenOnTime();
        }
    }

    public void forceIdleState(String str, int i, boolean z) {
        int appId;
        int i2;
        int i3;
        if (this.mAppIdleEnabled && (appId = getAppId(str)) >= 0) {
            int appMinBucket = getAppMinBucket(appId, i, str);
            if (z && appMinBucket < 40) {
                Slog.e("AppStandbyController", "Tried to force an app to be idle when its min bucket is " + UsageStatsManager.standbyBucketToString(appMinBucket));
                return;
            }
            this.mInjector.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            boolean isAppIdleFiltered = isAppIdleFiltered(str, appId, i, elapsedRealtime);
            synchronized (this.mAppIdleLock) {
                AppIdleHistory appIdleHistory = this.mAppIdleHistory;
                if (z) {
                    AppIdleHistory.AppUsageHistory appUsageHistory = appIdleHistory.getAppUsageHistory(i, str, elapsedRealtime);
                    SparseLongArray sparseLongArray = appUsageHistory.bucketExpiryTimesMs;
                    if (sparseLongArray != null) {
                        for (int size = sparseLongArray.size() - 1; size >= 0; size--) {
                            if (appUsageHistory.bucketExpiryTimesMs.keyAt(size) < 40) {
                                appUsageHistory.bucketExpiryTimesMs.removeAt(size);
                            }
                        }
                    }
                    i3 = 1024;
                    i2 = 40;
                } else {
                    i2 = 10;
                    i3 = 771;
                }
                appIdleHistory.setAppStandbyBucket(i, i2, i3, elapsedRealtime, str, false);
            }
            boolean isAppIdleFiltered2 = isAppIdleFiltered(str, appId, i, elapsedRealtime);
            maybeInformListeners(i, i2, 1024, elapsedRealtime, str, false);
            if (isAppIdleFiltered != isAppIdleFiltered2) {
                notifyBatteryStats(i, str, isAppIdleFiltered2);
            }
        }
    }

    public Set getActiveAdminAppsForTest(int i) {
        Set set;
        synchronized (this.mActiveAdminApps) {
            set = (Set) this.mActiveAdminApps.get(i);
        }
        return set;
    }

    public Set getAdminProtectedPackagesForTest(int i) {
        Set set;
        synchronized (this.mAdminProtectedPackages) {
            set = (Set) this.mAdminProtectedPackages.get(i);
        }
        return set;
    }

    public final int getAppId(String str) {
        try {
            return this.mPackageManager.getApplicationInfo(str, 4194816).uid;
        } catch (PackageManager.NameNotFoundException unused) {
            return -1;
        }
    }

    public AppIdleHistory getAppIdleHistoryForTest() {
        AppIdleHistory appIdleHistory;
        synchronized (this.mAppIdleLock) {
            appIdleHistory = this.mAppIdleHistory;
        }
        return appIdleHistory;
    }

    public final int getAppMinBucket(int i, int i2, String str) {
        boolean contains;
        boolean z;
        if (str == null) {
            return 50;
        }
        if (!this.mAppIdleEnabled || i < 10000 || str.equals("android")) {
            return 5;
        }
        boolean z2 = false;
        if (this.mSystemServicesReady) {
            Injector injector = this.mInjector;
            if (injector.mBootPhase < 500) {
                contains = false;
            } else {
                synchronized (injector.mPowerWhitelistedApps) {
                    contains = injector.mPowerWhitelistedApps.contains(str);
                }
            }
            if (contains || isActiveDeviceAdmin(str, i2)) {
                return 5;
            }
            synchronized (this.mAdminProtectedPackages) {
                try {
                    z = (this.mAdminProtectedPackages.contains(-1) && ((Set) this.mAdminProtectedPackages.get(-1)).contains(str)) ? true : this.mAdminProtectedPackages.contains(i2) && ((Set) this.mAdminProtectedPackages.get(i2)).contains(str);
                } finally {
                }
            }
            if (z) {
                return 5;
            }
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (this.mCachedNetworkScorer == null || this.mCachedNetworkScorerAtMillis < elapsedRealtime - 5000) {
                this.mCachedNetworkScorer = ((NetworkScoreManager) this.mInjector.mContext.getSystemService("network_score")).getActiveScorerPackage();
                this.mCachedNetworkScorerAtMillis = elapsedRealtime;
            }
            if (str.equals(this.mCachedNetworkScorer)) {
                return 5;
            }
            int uid = UserHandle.getUid(i2, i);
            synchronized (this.mSystemExemptionAppOpMode) {
                try {
                    if (this.mSystemExemptionAppOpMode.indexOfKey(uid) < 0) {
                        int checkOpNoThrow = this.mAppOpsManager.checkOpNoThrow(128, uid, str);
                        this.mSystemExemptionAppOpMode.put(uid, checkOpNoThrow);
                        if (checkOpNoThrow == 0) {
                            return 5;
                        }
                    } else if (this.mSystemExemptionAppOpMode.get(uid) == 0) {
                        return 5;
                    }
                    AppWidgetManager appWidgetManager = this.mAppWidgetManager;
                    if (appWidgetManager != null) {
                        this.mInjector.getClass();
                        if (appWidgetManager.isBoundWidgetPackage(str, i2)) {
                            return 10;
                        }
                    }
                    if (this.mCachedDeviceProvisioningPackage == null) {
                        this.mCachedDeviceProvisioningPackage = this.mContext.getResources().getString(R.string.display_manager_built_in_display_name);
                    }
                    if (this.mCachedDeviceProvisioningPackage.equals(str)) {
                        return 5;
                    }
                    if (str.equals(this.mInjector.mWellbeingApp)) {
                        return 20;
                    }
                    Injector injector2 = this.mInjector;
                    int uid2 = UserHandle.getUid(i2, i);
                    AlarmManagerService alarmManagerService = AlarmManagerService.this;
                    if (alarmManagerService.hasUseExactAlarmInternal(uid2, str) || (!CompatChanges.isChangeEnabled(262645982L, str, UserHandle.getUserHandleForUid(uid2)) && alarmManagerService.hasScheduleExactAlarmInternal(uid2, str))) {
                        return 20;
                    }
                } finally {
                }
            }
        }
        synchronized (this.mCarrierPrivilegedLock) {
            try {
                if (!this.mHaveCarrierPrivilegedApps) {
                    this.mCarrierPrivilegedApps = ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class)).getCarrierPrivilegedPackagesForAllActiveSubscriptions();
                    this.mHaveCarrierPrivilegedApps = true;
                }
                List list = this.mCarrierPrivilegedApps;
                if (list != null) {
                    z2 = list.contains(str);
                }
            } finally {
            }
        }
        if (z2) {
            return 5;
        }
        if (isHeadlessSystemApp(str)) {
            return 10;
        }
        return this.mPackageManager.checkPermission("android.permission.ACCESS_BACKGROUND_LOCATION", str) == 0 ? 30 : 50;
    }

    public final int getAppMinStandbyBucket(String str, int i, int i2, boolean z) {
        int appMinBucket;
        if (z && this.mInjector.mPackageManagerInternal.isPackageEphemeral(i2, str)) {
            return 50;
        }
        synchronized (this.mAppIdleLock) {
            appMinBucket = getAppMinBucket(i, i2, str);
        }
        return appMinBucket;
    }

    public final int getAppStandbyBucket(String str, int i, long j, boolean z) {
        int i2;
        if (!this.mAppIdleEnabled) {
            return 5;
        }
        if (z && this.mInjector.mPackageManagerInternal.isPackageEphemeral(i, str)) {
            return 10;
        }
        synchronized (this.mAppIdleLock) {
            AppIdleHistory.AppUsageHistory packageHistory = AppIdleHistory.getPackageHistory(this.mAppIdleHistory.getUserHistory(i), str, false);
            i2 = packageHistory == null ? 50 : packageHistory.currentBucket;
        }
        return i2;
    }

    public final int getAppStandbyBucketReason(String str, int i, long j) {
        int i2;
        synchronized (this.mAppIdleLock) {
            AppIdleHistory.AppUsageHistory packageHistory = AppIdleHistory.getPackageHistory(this.mAppIdleHistory.getUserHistory(i), str, false);
            i2 = packageHistory != null ? packageHistory.bucketingReason : 0;
        }
        return i2;
    }

    public final List getAppStandbyBuckets(int i) {
        ArrayList arrayList;
        synchronized (this.mAppIdleLock) {
            AppIdleHistory appIdleHistory = this.mAppIdleHistory;
            boolean z = this.mAppIdleEnabled;
            ArrayMap userHistory = appIdleHistory.getUserHistory(i);
            int size = userHistory.size();
            arrayList = new ArrayList(size);
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(new AppStandbyInfo((String) userHistory.keyAt(i2), z ? ((AppIdleHistory.AppUsageHistory) userHistory.valueAt(i2)).currentBucket : 10));
            }
        }
        return arrayList;
    }

    public final String getAppStandbyConstant(String str) {
        return (String) ((ArrayMap) this.mAppStandbyProperties).get(str);
    }

    public final List getBroadcastResponseExemptedPermissions() {
        return this.mBroadcastResponseExemptedPermissionsList;
    }

    public final List getBroadcastResponseExemptedRoles() {
        return this.mBroadcastResponseExemptedRolesList;
    }

    public final int getBroadcastResponseFgThresholdState() {
        return this.mBroadcastResponseFgThresholdState;
    }

    public final long getBroadcastResponseWindowDurationMs() {
        return this.mBroadcastResponseWindowDurationMillis;
    }

    public final long getBroadcastSessionsDurationMs() {
        return this.mBroadcastSessionsDurationMs;
    }

    public final long getBroadcastSessionsWithResponseDurationMs() {
        return this.mBroadcastSessionsWithResponseDurationMs;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0051 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getBucketForLocked(int r10, java.lang.String r11, long r12) {
        /*
            r9 = this;
            com.android.server.usage.AppIdleHistory r0 = r9.mAppIdleHistory
            long[] r1 = r9.mAppStandbyScreenThresholds
            long[] r9 = r9.mAppStandbyElapsedThresholds
            android.util.ArrayMap r10 = r0.getUserHistory(r10)
            r2 = 0
            com.android.server.usage.AppIdleHistory$AppUsageHistory r10 = com.android.server.usage.AppIdleHistory.getPackageHistory(r10, r11, r2)
            if (r10 == 0) goto L49
            long r3 = r10.lastUsedElapsedTime
            r5 = 0
            int r11 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r11 < 0) goto L49
            long r3 = r10.lastUsedScreenTime
            int r11 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r11 >= 0) goto L20
            goto L49
        L20:
            long r5 = r0.mScreenOnDuration
            boolean r11 = r0.mScreenOn
            if (r11 == 0) goto L2b
            long r7 = r0.mScreenOnSnapshot
            long r7 = r12 - r7
            long r5 = r5 + r7
        L2b:
            long r5 = r5 - r3
            long r11 = r0.getElapsedTime(r12)
            long r3 = r10.lastUsedElapsedTime
            long r11 = r11 - r3
            int r10 = r1.length
            int r10 = r10 + (-1)
        L36:
            if (r10 < 0) goto L4a
            r3 = r1[r10]
            int r13 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r13 < 0) goto L46
            r3 = r9[r10]
            int r13 = (r11 > r3 ? 1 : (r11 == r3 ? 0 : -1))
            if (r13 < 0) goto L46
            r2 = r10
            goto L4a
        L46:
            int r10 = r10 + (-1)
            goto L36
        L49:
            r2 = -1
        L4a:
            if (r2 < 0) goto L51
            int[] r9 = com.android.server.usage.AppStandbyController.THRESHOLD_BUCKETS
            r9 = r9[r2]
            goto L53
        L51:
            r9 = 50
        L53:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.usage.AppStandbyController.getBucketForLocked(int, java.lang.String, long):int");
    }

    public final List getCrossProfileTargets(int i, String str) {
        synchronized (this.mAppIdleLock) {
            try {
                if (!this.mLinkCrossProfileApps) {
                    return Collections.emptyList();
                }
                Injector injector = this.mInjector;
                int packageUid = injector.mPackageManagerInternal.getPackageUid(str, 0L, i);
                AndroidPackage androidPackage = injector.mPackageManagerInternal.getPackage(packageUid);
                if (packageUid >= 0 && androidPackage != null && androidPackage.isCrossProfile() && injector.mCrossProfileAppsInternal.verifyUidHasInteractAcrossProfilePermission(str, packageUid)) {
                    return injector.mCrossProfileAppsInternal.getTargetUserProfiles(str, i);
                }
                if (packageUid >= 0 && androidPackage == null) {
                    Slog.wtf("AppStandbyController", "Null package retrieved for UID " + packageUid);
                }
                return Collections.emptyList();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final long getEstimatedLaunchTime(String str, int i) {
        long j;
        this.mInjector.getClass();
        SystemClock.elapsedRealtime();
        synchronized (this.mAppIdleLock) {
            AppIdleHistory.AppUsageHistory packageHistory = AppIdleHistory.getPackageHistory(this.mAppIdleHistory.getUserHistory(i), str, false);
            j = (packageHistory != null && packageHistory.nextEstimatedLaunchTime >= System.currentTimeMillis()) ? packageHistory.nextEstimatedLaunchTime : Long.MAX_VALUE;
        }
        return j;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int[] getIdleUidsForUser(int r20) {
        /*
            r19 = this;
            r6 = r19
            boolean r0 = r6.mAppIdleEnabled
            if (r0 != 0) goto L9
            int[] r0 = libcore.util.EmptyArray.INT
            return r0
        L9:
            java.lang.String r0 = "getIdleUidsForUser"
            r7 = 64
            android.os.Trace.traceBegin(r7, r0)
            com.android.server.usage.AppStandbyController$Injector r0 = r6.mInjector
            r0.getClass()
            long r9 = android.os.SystemClock.elapsedRealtime()
            com.android.server.usage.AppStandbyController$Injector r0 = r6.mInjector
            android.content.pm.PackageManagerInternal r0 = r0.mPackageManagerInternal
            r1 = 0
            int r3 = android.os.Process.myUid()
            r11 = r20
            java.util.List r12 = r0.getInstalledApplications(r11, r3, r1)
            if (r12 != 0) goto L2e
            int[] r0 = libcore.util.EmptyArray.INT
            return r0
        L2e:
            android.util.SparseBooleanArray r13 = new android.util.SparseBooleanArray
            r13.<init>()
            int r0 = r12.size()
            r14 = 1
            int r0 = r0 - r14
            r4 = r0
            r16 = 0
        L3c:
            if (r4 < 0) goto L8c
            java.lang.Object r0 = r12.get(r4)
            r5 = r0
            android.content.pm.ApplicationInfo r5 = (android.content.pm.ApplicationInfo) r5
            int r0 = r5.uid
            int r3 = r13.indexOfKey(r0)
            if (r3 >= 0) goto L50
            r17 = r14
            goto L56
        L50:
            boolean r0 = r13.valueAt(r3)
            r17 = r0
        L56:
            if (r17 == 0) goto L71
            java.lang.String r1 = r5.packageName
            int r0 = r5.uid
            int r2 = android.os.UserHandle.getAppId(r0)
            r0 = r19
            r15 = r3
            r3 = r20
            r18 = r4
            r7 = r5
            r4 = r9
            boolean r0 = r0.isAppIdleFiltered(r1, r2, r3, r4)
            if (r0 == 0) goto L75
            r0 = r14
            goto L76
        L71:
            r15 = r3
            r18 = r4
            r7 = r5
        L75:
            r0 = 0
        L76:
            if (r17 == 0) goto L7c
            if (r0 != 0) goto L7c
            int r16 = r16 + 1
        L7c:
            if (r15 >= 0) goto L84
            int r1 = r7.uid
            r13.put(r1, r0)
            goto L87
        L84:
            r13.setValueAt(r15, r0)
        L87:
            int r4 = r18 + (-1)
            r7 = 64
            goto L3c
        L8c:
            int r0 = r13.size()
            int r0 = r0 - r16
            int[] r1 = new int[r0]
            int r2 = r13.size()
            int r2 = r2 - r14
        L99:
            if (r2 < 0) goto Lac
            boolean r3 = r13.valueAt(r2)
            if (r3 == 0) goto La9
            int r0 = r0 + (-1)
            int r3 = r13.keyAt(r2)
            r1[r0] = r3
        La9:
            int r2 = r2 + (-1)
            goto L99
        Lac:
            r2 = 64
            android.os.Trace.traceEnd(r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.usage.AppStandbyController.getIdleUidsForUser(int):int[]");
    }

    public final long getTimeSinceLastJobRun(String str, int i) {
        long elapsedTime;
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        synchronized (this.mAppIdleLock) {
            AppIdleHistory appIdleHistory = this.mAppIdleHistory;
            AppIdleHistory.AppUsageHistory packageHistory = AppIdleHistory.getPackageHistory(appIdleHistory.getUserHistory(i), str, false);
            elapsedTime = (packageHistory == null || packageHistory.lastJobRunTime == Long.MIN_VALUE) ? Long.MAX_VALUE : appIdleHistory.getElapsedTime(elapsedRealtime) - packageHistory.lastJobRunTime;
        }
        return elapsedTime;
    }

    public final long getTimeSinceLastUsedByUser(String str, int i) {
        long elapsedTime;
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        synchronized (this.mAppIdleLock) {
            AppIdleHistory appIdleHistory = this.mAppIdleHistory;
            AppIdleHistory.AppUsageHistory packageHistory = AppIdleHistory.getPackageHistory(appIdleHistory.getUserHistory(i), str, false);
            if (packageHistory != null) {
                long j = packageHistory.lastUsedByUserElapsedTime;
                elapsedTime = (j != Long.MIN_VALUE && j > 0) ? appIdleHistory.getElapsedTime(elapsedRealtime) - packageHistory.lastUsedByUserElapsedTime : Long.MAX_VALUE;
            }
        }
        return elapsedTime;
    }

    public final void initializeDefaultsForSystemApps(int i) {
        Lock lock;
        int i2;
        int i3;
        if (!this.mSystemServicesReady) {
            this.mPendingInitializeDefaults = true;
            return;
        }
        AnyMotionDetector$$ExternalSyntheticOutline0.m("AppStandbyController", BatteryService$$ExternalSyntheticOutline0.m(i, "Initializing defaults for system apps on user ", ", appIdleEnabled="), this.mAppIdleEnabled);
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        List installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(512, i);
        int size = installedPackagesAsUser.size();
        Lock lock2 = this.mAppIdleLock;
        synchronized (lock2) {
            int i4 = 0;
            while (i4 < size) {
                try {
                    PackageInfo packageInfo = (PackageInfo) installedPackagesAsUser.get(i4);
                    String str = packageInfo.packageName;
                    ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                    if (applicationInfo == null || !applicationInfo.isSystemApp()) {
                        i2 = i4;
                        i3 = size;
                        lock = lock2;
                    } else {
                        AppIdleHistory appIdleHistory = this.mAppIdleHistory;
                        i2 = i4;
                        i3 = size;
                        lock = lock2;
                        try {
                            appIdleHistory.reportUsage(AppIdleHistory.getPackageHistory(appIdleHistory.getUserHistory(i), str, true), str, i, 10, 6, 0L, elapsedRealtime + this.mSystemUpdateUsageTimeoutMillis);
                        } catch (Throwable th) {
                            th = th;
                            throw th;
                        }
                    }
                    i4 = i2 + 1;
                    size = i3;
                    lock2 = lock;
                } catch (Throwable th2) {
                    th = th2;
                    lock = lock2;
                }
            }
            Lock lock3 = lock2;
            this.mAppIdleHistory.writeAppIdleTimes(i, elapsedRealtime);
        }
    }

    public boolean isActiveDeviceAdmin(String str, int i) {
        boolean z;
        synchronized (this.mActiveAdminApps) {
            try {
                Set set = (Set) this.mActiveAdminApps.get(i);
                z = set != null && set.contains(str);
            } finally {
            }
        }
        return z;
    }

    public final boolean isAppIdleEnabled() {
        return this.mAppIdleEnabled;
    }

    public final boolean isAppIdleFiltered(String str, int i, int i2, long j) {
        boolean isIdle;
        if (!this.mAppIdleEnabled || this.mIsCharging) {
            return false;
        }
        synchronized (this.mAppIdleLock) {
            isIdle = this.mAppIdleHistory.isIdle(i2, str, j);
        }
        return isIdle && getAppMinBucket(i, i2, str) >= 40;
    }

    public final boolean isAppIdleFiltered(String str, int i, long j, boolean z) {
        if (z && this.mInjector.mPackageManagerInternal.isPackageEphemeral(i, str)) {
            return false;
        }
        return isAppIdleFiltered(str, getAppId(str), i, j);
    }

    public final boolean isHeadlessSystemApp(String str) {
        boolean contains;
        synchronized (this.mHeadlessSystemApps) {
            contains = this.mHeadlessSystemApps.contains(str);
        }
        return contains;
    }

    public final boolean isInParole() {
        return !this.mAppIdleEnabled || this.mIsCharging;
    }

    public final void maybeInformListeners(int i, int i2, int i3, long j, String str, boolean z) {
        synchronized (this.mAppIdleLock) {
            try {
                AppIdleHistory.AppUsageHistory packageHistory = AppIdleHistory.getPackageHistory(this.mAppIdleHistory.getUserHistory(i), str, true);
                if (packageHistory.lastInformedBucket != i2) {
                    packageHistory.lastInformedBucket = i2;
                    StandbyUpdateRecord standbyUpdateRecord = (StandbyUpdateRecord) StandbyUpdateRecord.sPool.obtain();
                    if (standbyUpdateRecord == null) {
                        standbyUpdateRecord = new StandbyUpdateRecord();
                    }
                    standbyUpdateRecord.packageName = str;
                    standbyUpdateRecord.userId = i;
                    standbyUpdateRecord.bucket = i2;
                    standbyUpdateRecord.reason = i3;
                    standbyUpdateRecord.isUserInteraction = z;
                    AppStandbyHandler appStandbyHandler = this.mHandler;
                    appStandbyHandler.sendMessage(appStandbyHandler.obtainMessage(3, standbyUpdateRecord));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void maybeUnrestrictApp(String str, int i, int i2, int i3, int i4, int i5) {
        int i6;
        synchronized (this.mAppIdleLock) {
            try {
                this.mInjector.getClass();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                AppIdleHistory.AppUsageHistory appUsageHistory = this.mAppIdleHistory.getAppUsageHistory(i, str, elapsedRealtime);
                int i7 = 45;
                if (appUsageHistory.currentBucket == 45) {
                    int i8 = appUsageHistory.bucketingReason;
                    if ((65280 & i8) == i2) {
                        if ((i8 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT) == i3) {
                            i6 = i4 | i5;
                            i7 = 40;
                        } else {
                            i6 = (~i3) & i8;
                        }
                        this.mAppIdleHistory.setAppStandbyBucket(i, i7, i6, elapsedRealtime, str, false);
                        maybeInformListeners(i, i7, i6, elapsedRealtime, str, false);
                    }
                }
            } finally {
            }
        }
    }

    public void maybeUnrestrictBuggyApp(String str, int i) {
        maybeUnrestrictApp(str, i, FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM, 4, 256, 1);
    }

    public final void maybeUpdateHeadlessSystemAppCache(PackageInfo packageInfo) {
        ApplicationInfo applicationInfo;
        if (packageInfo == null || (applicationInfo = packageInfo.applicationInfo) == null) {
            return;
        }
        if (applicationInfo.isSystemApp() || packageInfo.applicationInfo.isUpdatedSystemApp()) {
            updateHeadlessSystemAppCache(packageInfo.packageName, ArrayUtils.isEmpty(this.mPackageManager.queryIntentActivitiesAsUser(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setPackage(packageInfo.packageName), 1835520, 0)));
        }
    }

    public final void notifyBatteryStats(int i, String str, boolean z) {
        try {
            int packageUidAsUser = this.mPackageManager.getPackageUidAsUser(str, 8192, i);
            if (z) {
                IBatteryStats iBatteryStats = this.mInjector.mBatteryStats;
                if (iBatteryStats != null) {
                    iBatteryStats.noteEvent(15, str, packageUidAsUser);
                }
            } else {
                IBatteryStats iBatteryStats2 = this.mInjector.mBatteryStats;
                if (iBatteryStats2 != null) {
                    iBatteryStats2.noteEvent(16, str, packageUidAsUser);
                }
            }
        } catch (PackageManager.NameNotFoundException | RemoteException unused) {
        }
    }

    public final void onAdminDataAvailable() {
        this.mAdminDataAvailableLatch.countDown();
    }

    public final void onBootPhase(int i) {
        boolean exists;
        Injector injector = this.mInjector;
        if (i == 500) {
            injector.getClass();
            injector.mDeviceIdleController = IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle"));
            injector.mBatteryStats = IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats"));
            injector.mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            injector.mDisplayManager = (DisplayManager) injector.mContext.getSystemService("display");
            injector.mPowerManager = (PowerManager) injector.mContext.getSystemService(PowerManager.class);
            injector.mBatteryManager = (BatteryManager) injector.mContext.getSystemService(BatteryManager.class);
            injector.mCrossProfileAppsInternal = (CrossProfileAppsInternal) LocalServices.getService(CrossProfileAppsInternal.class);
            injector.mAlarmManagerInternal = (AlarmManagerService.LocalService) LocalServices.getService(AlarmManagerService.LocalService.class);
            if (((ActivityManager) injector.mContext.getSystemService("activity")).isLowRamDevice() || ActivityManager.isSmallBatteryDevice()) {
                injector.mAutoRestrictedBucketDelayMs = 43200000L;
            }
        } else if (i == 1000) {
            injector.mWellbeingApp = injector.mContext.getPackageManager().getWellbeingPackageName();
        }
        injector.mBootPhase = i;
        if (i != 500) {
            if (i == 1000) {
                setChargingState(this.mInjector.mBatteryManager.isCharging());
                this.mHandler.post(new AppStandbyController$$ExternalSyntheticLambda0(this, 0));
                this.mHandler.post(new AppStandbyController$$ExternalSyntheticLambda0(this, 1));
                return;
            }
            return;
        }
        Slog.d("AppStandbyController", "Setting app idle enabled state");
        if (this.mAppIdleEnabled) {
            ((UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class)).registerListener(this);
        }
        ConstantsObserver constantsObserver = new ConstantsObserver(this.mHandler);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        contentResolver.registerContentObserver(Settings.Global.getUriFor("app_standby_enabled"), false, constantsObserver);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("adaptive_battery_management_enabled"), false, constantsObserver);
        this.mInjector.getClass();
        DeviceConfig.addOnPropertiesChangedListener("app_standby", AppSchedulingModuleThread.getExecutor(), constantsObserver);
        this.mInjector.getClass();
        constantsObserver.processProperties(DeviceConfig.getProperties("app_standby", new String[0]));
        constantsObserver.updateSettings();
        this.mAppWidgetManager = (AppWidgetManager) this.mContext.getSystemService(AppWidgetManager.class);
        this.mAppOpsManager = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        this.mInjector.getClass();
        try {
            IAppOpsService.Stub.asInterface(ServiceManager.getService("appops")).startWatchingMode(128, (String) null, new IAppOpsCallback.Stub() { // from class: com.android.server.usage.AppStandbyController.1
                public final void opChanged(int i2, int i3, String str, String str2) {
                    int userId = UserHandle.getUserId(i3);
                    synchronized (AppStandbyController.this.mSystemExemptionAppOpMode) {
                        AppStandbyController.this.mSystemExemptionAppOpMode.delete(i3);
                    }
                    AppStandbyController.this.mHandler.obtainMessage(11, userId, i3, str).sendToTarget();
                }
            });
        } catch (RemoteException e) {
            Slog.wtf("AppStandbyController", "Failed start watching for app op", e);
        }
        this.mInjector.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mHandler);
        synchronized (this.mAppIdleLock) {
            AppIdleHistory appIdleHistory = this.mAppIdleHistory;
            boolean z = this.mInjector.mDisplayManager.getDisplay(0).getState() == 2;
            this.mInjector.getClass();
            appIdleHistory.updateDisplay(SystemClock.elapsedRealtime(), z);
        }
        this.mSystemServicesReady = true;
        synchronized (this.mAppIdleLock) {
            exists = this.mAppIdleHistory.getUserFile(0).exists();
        }
        if (this.mPendingInitializeDefaults || !exists) {
            initializeDefaultsForSystemApps(0);
        }
        Flags.avoidIdleCheck();
        if (this.mPendingOneTimeCheckIdleStates) {
            postOneTimeCheckIdleStates();
        }
        List<ApplicationInfo> installedApplications = this.mPackageManager.getInstalledApplications(542908416);
        int size = installedApplications.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mSystemPackagesAppIds.add(Integer.valueOf(UserHandle.getAppId(installedApplications.get(i2).uid)));
        }
    }

    @Override // android.app.usage.UsageStatsManagerInternal.UsageEventListener
    public final void onUsageEvent(int i, UsageEvents.Event event) {
        if (this.mAppIdleEnabled) {
            int eventType = event.getEventType();
            if (eventType == 1 || eventType == 2 || eventType == 6 || eventType == 7 || eventType == 10 || eventType == 14 || eventType == 13 || eventType == 19) {
                String packageName = event.getPackageName();
                List crossProfileTargets = getCrossProfileTargets(i, packageName);
                synchronized (this.mAppIdleLock) {
                    try {
                        this.mInjector.getClass();
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        reportEventLocked(eventType, i, elapsedRealtime, packageName);
                        int size = crossProfileTargets.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            reportEventLocked(eventType, ((UserHandle) crossProfileTargets.get(i2)).getIdentifier(), elapsedRealtime, packageName);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    public final void onUserRemoved(int i) {
        synchronized (this.mAppIdleLock) {
            this.mAppIdleHistory.mIdleHistory.remove(i);
            synchronized (this.mActiveAdminApps) {
                this.mActiveAdminApps.remove(i);
            }
            synchronized (this.mAdminProtectedPackages) {
                this.mAdminProtectedPackages.remove(i);
            }
        }
    }

    public final void postCheckIdleStates(int i) {
        if (i == -1) {
            postOneTimeCheckIdleStates();
            return;
        }
        synchronized (this.mPendingIdleStateChecks) {
            SparseLongArray sparseLongArray = this.mPendingIdleStateChecks;
            this.mInjector.getClass();
            sparseLongArray.put(i, SystemClock.elapsedRealtime());
        }
        this.mHandler.obtainMessage(5).sendToTarget();
    }

    public final void postOneTimeCheckIdleStates() {
        if (this.mInjector.mBootPhase < 500) {
            this.mPendingOneTimeCheckIdleStates = true;
        } else {
            this.mHandler.sendEmptyMessage(10);
            this.mPendingOneTimeCheckIdleStates = false;
        }
    }

    public final void postReportContentProviderUsage(String str, String str2, int i) {
        ContentProviderUsageRecord contentProviderUsageRecord = (ContentProviderUsageRecord) ContentProviderUsageRecord.sPool.obtain();
        if (contentProviderUsageRecord == null) {
            contentProviderUsageRecord = new ContentProviderUsageRecord();
        }
        contentProviderUsageRecord.name = str;
        contentProviderUsageRecord.packageName = str2;
        contentProviderUsageRecord.userId = i;
        this.mHandler.obtainMessage(8, contentProviderUsageRecord).sendToTarget();
    }

    public final void postReportExemptedSyncStart(String str, int i) {
        this.mHandler.obtainMessage(13, i, 0, str).sendToTarget();
    }

    public final void postReportSyncScheduled(String str, int i, boolean z) {
        this.mHandler.obtainMessage(12, i, z ? 1 : 0, str).sendToTarget();
    }

    public final void removeListener(AppStandbyInternal.AppIdleStateChangeListener appIdleStateChangeListener) {
        synchronized (this.mPackageAccessListeners) {
            this.mPackageAccessListeners.remove(appIdleStateChangeListener);
        }
    }

    public final void reportEventLocked(int i, int i2, long j, String str) {
        int i3;
        boolean z;
        int i4;
        int i5;
        int i6;
        boolean z2;
        int i7;
        long j2;
        AppIdleHistory.AppUsageHistory appUsageHistory;
        String str2;
        int i8;
        boolean z3;
        int i9;
        long j3;
        AndroidPackageInternal androidPackageInternal;
        boolean isIdle = this.mAppIdleHistory.isIdle(i2, str, j);
        AppIdleHistory.AppUsageHistory appUsageHistory2 = this.mAppIdleHistory.getAppUsageHistory(i2, str, j);
        int i10 = appUsageHistory2.currentBucket;
        int i11 = appUsageHistory2.bucketingReason;
        if (i != 1) {
            i3 = 2;
            if (i == 2) {
                i3 = 5;
            } else if (i == 6) {
                i3 = 1;
            } else if (i == 7) {
                i3 = 3;
            } else if (i != 10) {
                i3 = i != 19 ? i != 13 ? i != 14 ? 0 : 9 : 10 : 15;
            }
        } else {
            i3 = 4;
        }
        int i12 = i3 | FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_USAGE;
        if (i == 10) {
            if (this.mRetainNotificationSeenImpactForPreTApps) {
                PackageStateInternal packageStateInternal = ((PackageManagerService.PackageManagerInternalImpl) this.mInjector.mPackageManagerInternal).getPackageStateInternal(str);
                if (((packageStateInternal == null || (androidPackageInternal = ((PackageSetting) packageStateInternal).pkg) == null) ? 10000 : androidPackageInternal.getTargetSdkVersion()) < 33) {
                    i9 = 20;
                    j3 = 43200000;
                    long j4 = j3;
                    int i13 = i3;
                    z2 = true;
                    int i14 = i9;
                    i7 = 10;
                    z = isIdle;
                    i4 = -1;
                    i5 = i11;
                    i6 = i12;
                    this.mAppIdleHistory.reportUsage(appUsageHistory2, str, i2, i14, i13, 0L, j + j4);
                    j2 = j4;
                }
            }
            if (this.mTriggerQuotaBumpOnNotificationSeen) {
                this.mHandler.obtainMessage(7, i2, -1, str).sendToTarget();
            }
            i9 = this.mNotificationSeenPromotedBucket;
            j3 = this.mNotificationSeenTimeoutMillis;
            long j42 = j3;
            int i132 = i3;
            z2 = true;
            int i142 = i9;
            i7 = 10;
            z = isIdle;
            i4 = -1;
            i5 = i11;
            i6 = i12;
            this.mAppIdleHistory.reportUsage(appUsageHistory2, str, i2, i142, i132, 0L, j + j42);
            j2 = j42;
        } else {
            z = isIdle;
            int i15 = i3;
            i4 = -1;
            i5 = i11;
            i6 = i12;
            z2 = true;
            if (i == 14) {
                i7 = 10;
                this.mAppIdleHistory.reportUsage(appUsageHistory2, str, i2, 20, i15, 0L, j + this.mSlicePinnedTimeoutMillis);
                j2 = this.mSlicePinnedTimeoutMillis;
            } else if (i == 6) {
                i7 = 10;
                this.mAppIdleHistory.reportUsage(appUsageHistory2, str, i2, 10, i15, 0L, j + this.mSystemInteractionTimeoutMillis);
                j2 = this.mSystemInteractionTimeoutMillis;
            } else if (i != 19) {
                i7 = 10;
                this.mAppIdleHistory.reportUsage(appUsageHistory2, str, i2, 10, i15, j, j + this.mStrongUsageTimeoutMillis);
                j2 = this.mStrongUsageTimeoutMillis;
            } else {
                if (i10 != 50) {
                    return;
                }
                i7 = 10;
                this.mAppIdleHistory.reportUsage(appUsageHistory2, str, i2, 10, i15, 0L, j + this.mInitialForegroundServiceStartTimeoutMillis);
                j2 = this.mInitialForegroundServiceStartTimeoutMillis;
            }
        }
        if (appUsageHistory2.currentBucket != i10) {
            AppStandbyHandler appStandbyHandler = this.mHandler;
            appStandbyHandler.sendMessageDelayed(appStandbyHandler.obtainMessage(11, i2, i4, str), j2);
            int i16 = appUsageHistory2.currentBucket;
            appUsageHistory = appUsageHistory2;
            z3 = z;
            str2 = str;
            i8 = i2;
            maybeInformListeners(i2, i16, i6, j, str, (i16 != i7 || (i5 & 65280) == 768) ? false : z2);
        } else {
            appUsageHistory = appUsageHistory2;
            str2 = str;
            i8 = i2;
            z3 = z;
        }
        boolean z4 = appUsageHistory.currentBucket >= 40 ? z2 : false;
        if (z3 != z4) {
            notifyBatteryStats(i8, str2, z4);
        }
    }

    public final void reportNoninteractiveUsageCrossUserLocked(String str, int i, int i2, int i3, long j, long j2, List list) {
        reportNoninteractiveUsageLocked(i, j, i2, i3, str, j2);
        int size = list.size();
        for (int i4 = 0; i4 < size; i4++) {
            reportNoninteractiveUsageLocked(((UserHandle) list.get(i4)).getIdentifier(), j, i2, i3, str, j2);
        }
    }

    public final void reportNoninteractiveUsageLocked(int i, long j, int i2, int i3, String str, long j2) {
        AppIdleHistory appIdleHistory = this.mAppIdleHistory;
        AppIdleHistory.AppUsageHistory packageHistory = AppIdleHistory.getPackageHistory(appIdleHistory.getUserHistory(i), str, true);
        appIdleHistory.reportUsage(packageHistory, str, i, i2, i3, 0L, j + j2);
        AppStandbyHandler appStandbyHandler = this.mHandler;
        appStandbyHandler.sendMessageDelayed(appStandbyHandler.obtainMessage(11, i, -1, str), j2);
        maybeInformListeners(i, packageHistory.currentBucket, packageHistory.bucketingReason, j, str, false);
    }

    public final void restoreAppsToRare(Set set, final int i) {
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (!this.mInjector.isPackageInstalled(i, str)) {
                Slog.i("AppStandbyController", "Tried to restore bucket for uninstalled app: " + str);
                this.mAppsToRestoreToRare.add(i, str);
            } else if (getAppStandbyBucket(str, i, elapsedRealtime, false) == 50) {
                setAppStandbyBucket(i, 40, 258, elapsedRealtime, str, false);
            }
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.usage.AppStandbyController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AppStandbyController appStandbyController = AppStandbyController.this;
                appStandbyController.mAppsToRestoreToRare.remove(i);
            }
        }, 28800000L);
    }

    public final void restrictApp(String str, int i, int i2) {
        restrictApp(str, i, FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM, i2);
    }

    public final void restrictApp(String str, int i, int i2, int i3) {
        if (i2 != 1536 && i2 != 1024) {
            Slog.e("AppStandbyController", "Tried to restrict app " + str + " for an unsupported reason");
            return;
        }
        if (!this.mInjector.isPackageInstalled(i, str)) {
            BootReceiver$$ExternalSyntheticOutline0.m("Tried to restrict uninstalled app: ", str, "AppStandbyController");
            return;
        }
        int i4 = (i2 & 65280) | (i3 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        this.mInjector.getClass();
        setAppStandbyBucket(i, 45, i4, SystemClock.elapsedRealtime(), str, false);
    }

    public final void setActiveAdminApps(Set set, int i) {
        synchronized (this.mActiveAdminApps) {
            try {
                if (set == null) {
                    this.mActiveAdminApps.remove(i);
                } else {
                    this.mActiveAdminApps.put(i, set);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setAdminProtectedPackages(Set set, int i) {
        synchronized (this.mAdminProtectedPackages) {
            if (set != null) {
                try {
                    if (!set.isEmpty()) {
                        this.mAdminProtectedPackages.put(i, set);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mAdminProtectedPackages.remove(i);
        }
        if (android.app.admin.flags.Flags.disallowUserControlBgUsageFix()) {
            Flags.avoidIdleCheck();
            postCheckIdleStates(i);
        }
    }

    public final void setAppIdleAsync(String str, boolean z, int i) {
        if (str == null || !this.mAppIdleEnabled) {
            return;
        }
        this.mHandler.obtainMessage(4, i, z ? 1 : 0, str).sendToTarget();
    }

    public void setAppIdleEnabled(boolean z) {
        UsageStatsManagerInternal usageStatsManagerInternal = (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
        if (z) {
            usageStatsManagerInternal.registerListener(this);
        } else {
            UsageStatsService usageStatsService = UsageStatsService.this;
            synchronized (usageStatsService.mUsageEventListeners) {
                usageStatsService.mUsageEventListeners.remove(this);
            }
        }
        synchronized (this.mAppIdleLock) {
            try {
                if (this.mAppIdleEnabled != z) {
                    boolean isInParole = isInParole();
                    this.mAppIdleEnabled = z;
                    if (isInParole() != isInParole) {
                        this.mHandler.removeMessages(9);
                        this.mHandler.sendEmptyMessage(9);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(17:51|(1:53)(1:123)|54|(1:(1:(2:60|61))(2:62|(2:70|71)))|122|(12:81|(4:83|(2:88|89)|107|89)(2:108|(1:113))|90|91|92|(1:94)(1:104)|95|(1:97)(1:103)|(1:99)|100|101|102)|114|90|91|92|(0)(0)|95|(0)(0)|(0)|100|101|102) */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x01ce, code lost:
    
        r8 = 50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x012d, code lost:
    
        android.util.Slog.i("AppStandbyController", r26 + " restricted by user");
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0145, code lost:
    
        r4 = (r3.lastUsedByUserElapsedTime + r20.mInjector.mAutoRestrictedBucketDelayMs) - r24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0152, code lost:
    
        if (r4 <= 0) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0154, code lost:
    
        android.util.Slog.w("AppStandbyController", "Tried to restrict recently used app: " + r26 + " due to " + r23);
        r0 = r20.mHandler;
        r0.sendMessageDelayed(r0.obtainMessage(11, r21, -1, r26), r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x017e, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x017f, code lost:
    
        r9 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00ed, code lost:
    
        r5 = r20.mAppIdleHistory;
        r15 = true;
        r7 = com.android.server.usage.AppIdleHistory.getPackageHistory(r5.getUserHistory(r9), r26, true);
        r7.lastRestrictAttemptElapsedTime = r5.getElapsedTime(r24);
        r7.lastRestrictReason = r23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0100, code lost:
    
        if (r8 == false) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0104, code lost:
    
        if (android.os.Build.IS_DEBUGGABLE == false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0109, code lost:
    
        if ((r23 & android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT) == 2) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x010b, code lost:
    
        android.widget.Toast.makeText(r20.mContext, r20.mHandler.getLooper(), r20.mContext.getResources().getString(android.R.string.config_chooseTypeAndAccountActivity, r26), 0).show();
     */
    /* JADX WARN: Removed duplicated region for block: B:103:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01f9 A[Catch: all -> 0x0033, TryCatch #0 {all -> 0x0033, blocks: (B:8:0x0018, B:10:0x0020, B:11:0x0031, B:13:0x0036, B:16:0x0048, B:18:0x004e, B:23:0x005a, B:30:0x006e, B:39:0x0083, B:40:0x0096, B:43:0x00a8, B:47:0x00be, B:48:0x00c1, B:60:0x00d8, B:64:0x00de, B:70:0x00e9, B:73:0x00ed, B:75:0x0102, B:77:0x0106, B:79:0x010b, B:81:0x0185, B:85:0x019f, B:91:0x01bf, B:92:0x01d0, B:95:0x01dd, B:99:0x01f9, B:100:0x01fc, B:107:0x01a9, B:110:0x01b0, B:115:0x012d, B:116:0x0145, B:118:0x0154, B:119:0x017d, B:127:0x0058), top: B:7:0x0018 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setAppStandbyBucket(int r21, int r22, int r23, long r24, java.lang.String r26, boolean r27) {
        /*
            Method dump skipped, instructions count: 527
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.usage.AppStandbyController.setAppStandbyBucket(int, int, int, long, java.lang.String, boolean):void");
    }

    public void setAppStandbyBucket(String str, int i, int i2, int i3) {
        this.mInjector.getClass();
        setAppStandbyBucket(i, i2, i3, SystemClock.elapsedRealtime(), str, false);
    }

    public final void setAppStandbyBucket(String str, int i, int i2, int i3, int i4) {
        setAppStandbyBuckets(Collections.singletonList(new AppStandbyInfo(str, i)), i2, i3, i4);
    }

    public final void setAppStandbyBucketForMARs(String str, int i, int i2, int i3, boolean z, boolean z2) {
        int i4;
        int i5 = i2;
        if (i3 != 1792) {
            return;
        }
        if (i5 < 10 || i5 > 50) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i5, "Cannot set the standby bucket to "));
        }
        if (!this.mInjector.isPackageInstalled(i, str)) {
            BootReceiver$$ExternalSyntheticOutline0.m("Tried to restrict uninstalled app: ", str, "AppStandbyController");
            return;
        }
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        synchronized (this.mAppIdleLock) {
            try {
                AppIdleHistory.AppUsageHistory appUsageHistory = this.mAppIdleHistory.getAppUsageHistory(i, str, elapsedRealtime);
                if (!z2 && (appUsageHistory.bucketingReason & 65280) == 1792) {
                    i5 = 45;
                }
                i4 = i5;
                this.mAppIdleHistory.setAppStandbyBucket(i, i4, i3, elapsedRealtime, str, z);
            } catch (Throwable th) {
                throw th;
            }
        }
        maybeInformListeners(i, i4, i3, elapsedRealtime, str, false);
    }

    public final void setAppStandbyBuckets(List list, int i, int i2, int i3) {
        int handleIncomingUser = ActivityManager.handleIncomingUser(i3, i2, i, false, true, "setAppStandbyBucket", null);
        boolean z = i2 == 0 || i2 == 2000;
        int i4 = ((!UserHandle.isSameApp(i2, 1000) || i3 == Process.myPid()) && !z) ? UserHandle.isCore(i2) ? FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM : 1280 : 1024;
        int size = list.size();
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        for (int i5 = 0; i5 < size; i5++) {
            AppStandbyInfo appStandbyInfo = (AppStandbyInfo) list.get(i5);
            String str = appStandbyInfo.mPackageName;
            int i6 = appStandbyInfo.mStandbyBucket;
            if (i6 < 10 || i6 > 50) {
                throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i6, "Cannot set the standby bucket to "));
            }
            int packageUid = this.mInjector.mPackageManagerInternal.getPackageUid(str, 4980736L, handleIncomingUser);
            if (packageUid == i2) {
                throw new IllegalArgumentException("Cannot set your own standby bucket");
            }
            if (packageUid < 0) {
                throw new IllegalArgumentException(XmlUtils$$ExternalSyntheticOutline0.m("Cannot set standby bucket for non existent package (", str, ")"));
            }
            setAppStandbyBucket(handleIncomingUser, i6, i4, elapsedRealtime, str, z);
        }
    }

    public final void setAppStandbyBucketsForMARs(List list, int i, int i2, int i3, boolean z, boolean z2) {
        if (i3 != 1792) {
            return;
        }
        int size = list.size();
        for (int i4 = 0; i4 < size; i4++) {
            setAppStandbyBucketForMARs(((AppStandbyInfo) list.get(i4)).mPackageName, i, i2, i3, z, z2);
        }
    }

    public void setChargingState(boolean z) {
        if (this.mIsCharging != z) {
            this.mIsCharging = z;
            this.mHandler.removeMessages(9);
            this.mHandler.sendEmptyMessage(9);
        }
    }

    public final void setEstimatedLaunchTime(String str, int i, long j) {
        this.mInjector.getClass();
        SystemClock.elapsedRealtime();
        synchronized (this.mAppIdleLock) {
            AppIdleHistory.getPackageHistory(this.mAppIdleHistory.getUserHistory(i), str, true).nextEstimatedLaunchTime = j;
        }
    }

    public final void setLastJobRunTime(String str, int i, long j) {
        synchronized (this.mAppIdleLock) {
            AppIdleHistory appIdleHistory = this.mAppIdleHistory;
            AppIdleHistory.getPackageHistory(appIdleHistory.getUserHistory(i), str, true).lastJobRunTime = appIdleHistory.getElapsedTime(j);
        }
    }

    public final boolean shouldNoteResponseEventForAllBroadcastSessions() {
        return this.mNoteResponseEventForAllBroadcastSessions;
    }

    public final boolean updateHeadlessSystemAppCache(String str, boolean z) {
        synchronized (this.mHeadlessSystemApps) {
            try {
                if (z) {
                    return this.mHeadlessSystemApps.add(str);
                }
                return this.mHeadlessSystemApps.remove(str);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}

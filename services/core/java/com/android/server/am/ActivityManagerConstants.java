package com.android.server.am;

import android.R;
import android.app.ForegroundServiceTypePolicy;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.KeyValueListParser;
import android.util.Slog;
import android.util.SparseBooleanArray;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda5;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import dalvik.annotation.optimization.NeverCompile;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ActivityManagerConstants extends ContentObserver {
    public static final Uri ACTIVITY_MANAGER_CONSTANTS_URI;
    public static final Uri ACTIVITY_STARTS_LOGGING_ENABLED_URI;
    public static int BINDER_HEAVY_HITTER_AUTO_SAMPLER_BATCHSIZE;
    public static boolean BINDER_HEAVY_HITTER_AUTO_SAMPLER_ENABLED;
    public static float BINDER_HEAVY_HITTER_AUTO_SAMPLER_THRESHOLD;
    public static int BINDER_HEAVY_HITTER_WATCHER_BATCHSIZE;
    public static boolean BINDER_HEAVY_HITTER_WATCHER_ENABLED;
    public static float BINDER_HEAVY_HITTER_WATCHER_THRESHOLD;
    public static final boolean DEFAULT_ENABLE_NEW_OOM_ADJ;
    public static final long[] DEFAULT_EXTRA_SERVICE_RESTART_DELAY_ON_MEM_PRESSURE;
    public static int DEFAULT_MAX_CACHED_PROCESSES =
            Integer.parseInt(SystemProperties.get("ro.slmk.fha_cached_max", "1024"));
    public static final long DEFAULT_SERVICE_BACKGROUND_TIMEOUT;
    public static final long DEFAULT_SERVICE_TIMEOUT;
    public static float EMPTY_RATE;
    public static final Uri ENABLE_AUTOMATIC_SYSTEM_SERVER_HEAP_DUMPS_URI;
    public static final Uri FORCE_ENABLE_PSS_PROFILING_URI;
    public static final Uri FOREGROUND_SERVICE_STARTS_LOGGING_ENABLED_URI;
    public static float LOW_SWAP_THRESHOLD_PERCENT;
    public static long MAX_PREVIOUS_TIME;
    public static long MIN_ASSOC_LOG_DURATION;
    public static int MIN_CRASH_INTERVAL;
    public static boolean PROACTIVE_KILLS_ENABLED;
    public static int PROCESS_CRASH_COUNT_LIMIT;
    public static long PROCESS_CRASH_COUNT_RESET_INTERVAL;
    public boolean APP_PROFILER_PSS_PROFILING_DISABLED;
    public long BACKGROUND_SETTLE_TIME;
    public long BG_START_TIMEOUT;
    public long BOUND_SERVICE_CRASH_RESTART_DURATION;
    public long BOUND_SERVICE_MAX_CRASH_RETRY;
    public long CONTENT_PROVIDER_RETAIN_TIME;
    public int CUR_MAX_CACHED_PROCESSES;
    public int CUR_MAX_EMPTY_PROCESSES;
    public int CUR_TRIM_CACHED_PROCESSES;
    public int CUR_TRIM_EMPTY_PROCESSES;
    public int CUSTOM_CUR_TRIM_CACHED_PROCESSES;
    public int CUSTOM_CUR_TRIM_EMPTY_PROCESSES;
    public final boolean ENABLE_BATCHING_OOM_ADJ;
    public final boolean ENABLE_NEW_OOMADJ;
    public long FGSERVICE_MIN_REPORT_TIME;
    public long FGSERVICE_MIN_SHOWN_TIME;
    public long FGSERVICE_SCREEN_ON_AFTER_TIME;
    public long FGSERVICE_SCREEN_ON_BEFORE_TIME;
    public int FGS_BOOT_COMPLETED_ALLOWLIST;
    public boolean FLAG_PROCESS_START_ASYNC;
    public long FOLLOW_UP_OOMADJ_UPDATE_WAIT_DURATION;
    public boolean FORCE_BACKGROUND_CHECK_ON_RESTRICTED_APPS;
    public long FULL_PSS_LOWERED_INTERVAL;
    public long FULL_PSS_MIN_INTERVAL;
    public long GC_MIN_INTERVAL;
    public long GC_TIMEOUT;
    public final ArraySet IMPERCEPTIBLE_KILL_EXEMPT_PACKAGES;
    public final ArraySet IMPERCEPTIBLE_KILL_EXEMPT_PROC_STATES;
    public final ArraySet KEEP_WARMING_SERVICES;
    public int MAX_CACHED_PROCESSES;
    public int MAX_PHANTOM_PROCESSES;
    public long MAX_SERVICE_INACTIVITY;
    public long MEMORY_INFO_THROTTLE_TIME;
    public boolean OOMADJ_UPDATE_QUICK;
    public int PENDINGINTENT_WARNING_THRESHOLD;
    public long POWER_CHECK_INTERVAL;
    public int POWER_CHECK_MAX_CPU_1;
    public int POWER_CHECK_MAX_CPU_2;
    public int POWER_CHECK_MAX_CPU_3;
    public int POWER_CHECK_MAX_CPU_4;
    public float PSS_TO_RSS_THRESHOLD_MODIFIER;
    public final long SERVICE_BACKGROUND_TIMEOUT;
    public long SERVICE_BG_ACTIVITY_START_TIMEOUT;
    public long SERVICE_MIN_RESTART_TIME_BETWEEN;
    public long SERVICE_RESET_RUN_DURATION;
    public long SERVICE_RESTART_DURATION;
    public int SERVICE_RESTART_DURATION_FACTOR;
    public final long SERVICE_TIMEOUT;
    public long SERVICE_USAGE_INTERACTION_TIME_POST_S;
    public long SERVICE_USAGE_INTERACTION_TIME_PRE_S;
    public long TIERED_CACHED_ADJ_DECAY_TIME;
    public long TOP_TO_ALMOST_PERCEPTIBLE_GRACE_DURATION;
    public volatile long TOP_TO_FGS_GRACE_DURATION;
    public long USAGE_STATS_INTERACTION_INTERVAL_POST_S;
    public long USAGE_STATS_INTERACTION_INTERVAL_PRE_S;
    public boolean USE_TIERED_CACHED_ADJ;
    public volatile long mBootTimeTempAllowlistDuration;
    public volatile String mComponentAliasOverrides;
    public final int mCustomizedMaxCachedProcesses;
    public volatile long mDataSyncFgsTimeoutDuration;
    public final int mDefaultBinderHeavyHitterAutoSamplerBatchSize;
    public final boolean mDefaultBinderHeavyHitterAutoSamplerEnabled;
    public final float mDefaultBinderHeavyHitterAutoSamplerThreshold;
    public final int mDefaultBinderHeavyHitterWatcherBatchSize;
    public final boolean mDefaultBinderHeavyHitterWatcherEnabled;
    public final float mDefaultBinderHeavyHitterWatcherThreshold;
    public final boolean mDefaultDisableAppProfilerPssProfiling;
    public final List mDefaultImperceptibleKillExemptPackages;
    public final List mDefaultImperceptibleKillExemptProcStates;
    public final float mDefaultPssToRssThresholdModifier;
    public volatile int mDeferBootCompletedBroadcast;
    public volatile boolean mEnableComponentAlias;
    public boolean mEnableExtraServiceRestartDelayOnMemPressure;
    public volatile boolean mEnableProcStateStacktrace;
    public volatile boolean mEnableWaitForFinishAttachApplication;
    public long[] mExtraServiceRestartDelayOnMemPressure;
    public volatile long mFgToBgFgsGraceDuration;
    public volatile boolean mFgsAllowOptOut;
    public volatile float mFgsAtomSampleRate;
    public volatile long mFgsCrashExtraWaitDuration;
    public volatile long mFgsNotificationDeferralExclusionTime;
    public volatile long mFgsNotificationDeferralExclusionTimeForShort;
    public volatile long mFgsNotificationDeferralInterval;
    public volatile long mFgsNotificationDeferralIntervalForShort;
    public volatile float mFgsStartAllowedLogSampleRate;
    public volatile float mFgsStartDeniedLogSampleRate;
    public volatile long mFgsStartForegroundTimeoutMs;
    public volatile boolean mFgsStartRestrictionCheckCallerTargetSdk;
    public volatile boolean mFgsStartRestrictionNotificationEnabled;
    public volatile boolean mFlagActivityStartsLoggingEnabled;
    public volatile boolean mFlagBackgroundActivityStartsEnabled;
    public volatile boolean mFlagBackgroundFgsStartRestrictionEnabled;
    public volatile boolean mFlagFgsNotificationDeferralApiGated;
    public volatile boolean mFlagFgsNotificationDeferralEnabled;
    public volatile boolean mFlagFgsStartRestrictionEnabled;
    public volatile boolean mFlagSystemExemptPowerRestrictionsEnabled;
    public volatile boolean mForceEnablePssProfiling;
    public volatile boolean mKillBgRestrictedAndCachedIdle;
    public volatile long mKillBgRestrictedAndCachedIdleSettleTimeMs;
    public volatile long mMaxEmptyTimeMillis;
    public volatile int mMaxServiceConnectionsPerProcess;
    public volatile long mMediaProcessingFgsTimeoutDuration;
    public volatile long mNetworkAccessTimeoutMs;
    public volatile long mNoKillCachedProcessesPostBootCompletedDurationMillis;
    public volatile boolean mNoKillCachedProcessesUntilBootCompleted;
    public final AnonymousClass1 mOnDeviceConfigChangedForComponentAliasListener;
    public final AnonymousClass1 mOnDeviceConfigChangedListener;
    public int mOverrideMaxCachedProcesses;
    public final KeyValueListParser mParser;
    public volatile boolean mPrioritizeAlarmBroadcasts;
    public volatile int mProcStateDebugSetProcStateDelay;
    public volatile int mProcStateDebugSetUidStateDelay;
    public volatile SparseBooleanArray mProcStateDebugUids;
    public volatile long mProcessKillTimeoutMs;
    public volatile int mPushMessagingOverQuotaBehavior;
    public ContentResolver mResolver;
    public final ActivityManagerService mService;
    public volatile long mServiceBindAlmostPerceptibleTimeoutMs;
    public volatile int mServiceStartForegroundAnrDelayMs;
    public volatile int mServiceStartForegroundTimeoutMs;
    public volatile long mShortFgsAnrExtraWaitDuration;
    public volatile long mShortFgsProcStateExtraWaitDuration;
    public volatile long mShortFgsTimeoutDuration;
    public final boolean mSystemServerAutomaticHeapDumpEnabled;
    public final String mSystemServerAutomaticHeapDumpPackageName;
    public final long mSystemServerAutomaticHeapDumpPssThresholdBytes;
    public volatile long mVisibleToInvisibleUijScheduleGraceDurationMs;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.am.ActivityManagerConstants$1, reason: invalid class name */
    public final class AnonymousClass1 implements DeviceConfig.OnPropertiesChangedListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ ActivityManagerConstants this$0;

        public /* synthetic */ AnonymousClass1(
                ActivityManagerConstants activityManagerConstants, int i) {
            this.$r8$classId = i;
            this.this$0 = activityManagerConstants;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            String str;
            char c;
            boolean z;
            String str2;
            switch (this.$r8$classId) {
                case 0:
                    Iterator it = properties.getKeyset().iterator();
                    while (it.hasNext() && (str = (String) it.next()) != null) {
                        switch (str.hashCode()) {
                            case -2074391906:
                                if (str.equals("imperceptible_kill_exempt_proc_states")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -2038720731:
                                if (str.equals("short_fgs_anr_extra_wait_duration")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1996097272:
                                if (str.equals("kill_bg_restricted_cached_idle_settle_time")) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1905817813:
                                if (str.equals("default_fgs_starts_restriction_enabled")) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1903697007:
                                if (str.equals("service_start_foreground_anr_delay_ms")) {
                                    c = 4;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1830853932:
                                if (str.equals("binder_heavy_hitter_watcher_batchsize")) {
                                    c = 5;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1782036688:
                                if (str.equals("default_background_activity_starts_enabled")) {
                                    c = 6;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1660341473:
                                if (str.equals("fgs_allow_opt_out")) {
                                    c = 7;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1640024320:
                                if (str.equals("fgs_start_denied_log_sample_rate")) {
                                    c = '\b';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1600089364:
                                if (str.equals(
                                        "deferred_fgs_notification_exclusion_time_for_short")) {
                                    c = '\t';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1483050242:
                                if (str.equals("max_service_connections_per_process")) {
                                    c = '\n';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1453165935:
                                if (str.equals("vis_to_invis_uij_schedule_grace_duration")) {
                                    c = 11;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1406935837:
                                if (str.equals("oomadj_update_policy")) {
                                    c = '\f';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1327576198:
                                if (str.equals("max_previous_time")) {
                                    c = '\r';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1303617396:
                                if (str.equals("deferred_fgs_notification_interval")) {
                                    c = 14;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1220759920:
                                if (str.equals("max_phantom_processes")) {
                                    c = 15;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1213341854:
                                if (str.equals("short_fgs_timeout_duration")) {
                                    c = 16;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1198352864:
                                if (str.equals(
                                        "default_background_fgs_starts_restriction_enabled")) {
                                    c = 17;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1191409506:
                                if (str.equals("force_bg_check_on_restricted")) {
                                    c = 18;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1092962821:
                                if (str.equals("max_cached_processes")) {
                                    c = 19;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1055864341:
                                if (str.equals("max_empty_time_millis")) {
                                    c = 20;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1034565042:
                                if (str.equals("data_sync_fgs_timeout_duration")) {
                                    c = 21;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -964261074:
                                if (str.equals("network_access_timeout_ms")) {
                                    c = 22;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -815375578:
                                if (str.equals("min_assoc_log_duration")) {
                                    c = 23;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -769365680:
                                if (str.equals("process_kill_timeout")) {
                                    c = 24;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -728096000:
                                if (str.equals("prioritize_alarm_broadcasts")) {
                                    c = 25;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -682752716:
                                if (str.equals("fgs_atom_sample_rate")) {
                                    c = 26;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -577670375:
                                if (str.equals("service_start_foreground_timeout_ms")) {
                                    c = 27;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -528913203:
                                if (str.equals("pss_to_rss_threshold_modifier")) {
                                    c = 28;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -449032007:
                                if (str.equals("fgs_start_allowed_log_sample_rate")) {
                                    c = 29;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -329920445:
                                if (str.equals(
                                        "default_fgs_starts_restriction_notification_enabled")) {
                                    c = 30;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -292047334:
                                if (str.equals("binder_heavy_hitter_watcher_enabled")) {
                                    c = 31;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -253203740:
                                if (str.equals("push_messaging_over_quota_behavior")) {
                                    c = ' ';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -224039213:
                                if (str.equals(
                                        "no_kill_cached_processes_post_boot_completed_duration_millis")) {
                                    c = '!';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -216971728:
                                if (str.equals("deferred_fgs_notifications_api_gated")) {
                                    c = '\"';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -84078814:
                                if (str.equals("top_to_fgs_grace_duration")) {
                                    c = '#';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -48740806:
                                if (str.equals("imperceptible_kill_exempt_packages")) {
                                    c = '$';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -10632086:
                                if (str.equals("fgs_crash_extra_wait_duration")) {
                                    c = '%';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 21817133:
                                if (str.equals("defer_boot_completed_broadcast")) {
                                    c = PackageManagerShellCommandDataLoader.ARGS_DELIM;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 74597321:
                                if (str.equals("tiered_cached_adj_decay_time")) {
                                    c = '\'';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 102688395:
                                if (str.equals("proactive_kills_enabled")) {
                                    c = '(';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 174194291:
                                if (str.equals("system_exempt_power_restrictions_enabled")) {
                                    c = ')';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 273690789:
                                if (str.equals("enable_extra_delay_svc_restart_mem_pressure")) {
                                    c = '*';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 628754725:
                                if (str.equals("deferred_fgs_notification_exclusion_time")) {
                                    c = '+';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 886770227:
                                if (str.equals(
                                        "default_fgs_starts_restriction_check_caller_target_sdk")) {
                                    c = ',';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 889934779:
                                if (str.equals("no_kill_cached_processes_until_boot_completed")) {
                                    c = '-';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 969545596:
                                if (str.equals("fg_to_bg_fgs_grace_duration")) {
                                    c = '.';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1113517584:
                                if (str.equals("low_swap_threshold_percent")) {
                                    c = '/';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1163990130:
                                if (str.equals("boot_time_temp_allowlist_duration")) {
                                    c = '0';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1199252102:
                                if (str.equals("kill_bg_restricted_cached_idle")) {
                                    c = '1';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1239401352:
                                if (str.equals("short_fgs_proc_state_extra_wait_duration")) {
                                    c = '2';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1293360336:
                                if (str.equals("media_processing_fgs_timeout_duration")) {
                                    c = '3';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1335775030:
                                if (str.equals("disable_app_profiler_pss_profiling")) {
                                    c = '4';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1351914345:
                                if (str.equals("extra_delay_svc_restart_mem_pressure")) {
                                    c = '5';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1380211165:
                                if (str.equals("deferred_fgs_notifications_enabled")) {
                                    c = '6';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1444000894:
                                if (str.equals("enable_wait_for_finish_attach_application")) {
                                    c = '7';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1488827127:
                                if (str.equals("follow_up_oomadj_update_wait_duration")) {
                                    c = '8';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1509013190:
                                if (str.equals("proc_state_debug_uids")) {
                                    c = '9';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1509836936:
                                if (str.equals("binder_heavy_hitter_auto_sampler_threshold")) {
                                    c = ':';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1577406544:
                                if (str.equals("use_tiered_cached_adj")) {
                                    c = ';';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1598050974:
                                if (str.equals("binder_heavy_hitter_auto_sampler_enabled")) {
                                    c = '<';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1626346799:
                                if (str.equals("fgs_start_foreground_timeout")) {
                                    c = '=';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1874204051:
                                if (str.equals("deferred_fgs_notification_interval_for_short")) {
                                    c = '>';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1896529156:
                                if (str.equals("binder_heavy_hitter_watcher_threshold")) {
                                    c = '?';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 2013655783:
                                if (str.equals("service_bind_almost_perceptible_timeout_ms")) {
                                    c = '@';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 2077421144:
                                if (str.equals("binder_heavy_hitter_auto_sampler_batchsize")) {
                                    c = 'A';
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
                            case '$':
                                final ActivityManagerConstants activityManagerConstants =
                                        this.this$0;
                                activityManagerConstants.IMPERCEPTIBLE_KILL_EXEMPT_PACKAGES.clear();
                                activityManagerConstants.IMPERCEPTIBLE_KILL_EXEMPT_PACKAGES.addAll(
                                        activityManagerConstants
                                                .mDefaultImperceptibleKillExemptPackages);
                                String string =
                                        DeviceConfig.getString(
                                                "activity_manager",
                                                "imperceptible_kill_exempt_packages",
                                                (String) null);
                                if (!TextUtils.isEmpty(string)) {
                                    activityManagerConstants.IMPERCEPTIBLE_KILL_EXEMPT_PACKAGES
                                            .addAll(Arrays.asList(string.split(",")));
                                }
                                activityManagerConstants.IMPERCEPTIBLE_KILL_EXEMPT_PROC_STATES
                                        .clear();
                                activityManagerConstants.IMPERCEPTIBLE_KILL_EXEMPT_PROC_STATES
                                        .addAll(
                                                activityManagerConstants
                                                        .mDefaultImperceptibleKillExemptProcStates);
                                String string2 =
                                        DeviceConfig.getString(
                                                "activity_manager",
                                                "imperceptible_kill_exempt_proc_states",
                                                (String) null);
                                if (!TextUtils.isEmpty(string2)) {
                                    Arrays.asList(string2.split(",")).stream()
                                            .forEach(
                                                    new Consumer() { // from class:
                                                                     // com.android.server.am.ActivityManagerConstants$$ExternalSyntheticLambda2
                                                        @Override // java.util.function.Consumer
                                                        public final void accept(Object obj) {
                                                            ActivityManagerConstants
                                                                    activityManagerConstants2 =
                                                                            ActivityManagerConstants
                                                                                    .this;
                                                            String str3 = (String) obj;
                                                            activityManagerConstants2.getClass();
                                                            try {
                                                                activityManagerConstants2
                                                                        .IMPERCEPTIBLE_KILL_EXEMPT_PROC_STATES
                                                                        .add(
                                                                                Integer.valueOf(
                                                                                        Integer
                                                                                                .parseInt(
                                                                                                        str3)));
                                                            } catch (NumberFormatException unused) {
                                                            }
                                                        }
                                                    });
                                }
                            case 1:
                                ActivityManagerConstants activityManagerConstants2 = this.this$0;
                                activityManagerConstants2.getClass();
                                activityManagerConstants2.mShortFgsAnrExtraWaitDuration =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "short_fgs_anr_extra_wait_duration",
                                                10000L);
                            case 2:
                                ActivityManagerConstants activityManagerConstants3 = this.this$0;
                                long j =
                                        activityManagerConstants3
                                                .mKillBgRestrictedAndCachedIdleSettleTimeMs;
                                activityManagerConstants3
                                                .mKillBgRestrictedAndCachedIdleSettleTimeMs =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "kill_bg_restricted_cached_idle_settle_time",
                                                60000L);
                                if (activityManagerConstants3
                                                .mKillBgRestrictedAndCachedIdleSettleTimeMs
                                        < j) {
                                    activityManagerConstants3.mService.mHandler
                                            .sendEmptyMessageDelayed(
                                                    58,
                                                    activityManagerConstants3
                                                            .mKillBgRestrictedAndCachedIdleSettleTimeMs);
                                }
                            case 3:
                                ActivityManagerConstants activityManagerConstants4 = this.this$0;
                                activityManagerConstants4.getClass();
                                activityManagerConstants4.mFlagFgsStartRestrictionEnabled =
                                        DeviceConfig.getBoolean(
                                                "activity_manager",
                                                "default_fgs_starts_restriction_enabled",
                                                true);
                            case 4:
                                ActivityManagerConstants activityManagerConstants5 = this.this$0;
                                activityManagerConstants5.getClass();
                                activityManagerConstants5.mServiceStartForegroundAnrDelayMs =
                                        DeviceConfig.getInt(
                                                "activity_manager",
                                                "service_start_foreground_anr_delay_ms",
                                                10000);
                            case 5:
                            case 31:
                            case ':':
                            case '<':
                            case '?':
                            case 'A':
                                ActivityManagerConstants activityManagerConstants6 = this.this$0;
                                ActivityManagerConstants.BINDER_HEAVY_HITTER_WATCHER_ENABLED =
                                        DeviceConfig.getBoolean(
                                                "activity_manager",
                                                "binder_heavy_hitter_watcher_enabled",
                                                activityManagerConstants6
                                                        .mDefaultBinderHeavyHitterWatcherEnabled);
                                ActivityManagerConstants.BINDER_HEAVY_HITTER_WATCHER_BATCHSIZE =
                                        DeviceConfig.getInt(
                                                "activity_manager",
                                                "binder_heavy_hitter_watcher_batchsize",
                                                activityManagerConstants6
                                                        .mDefaultBinderHeavyHitterWatcherBatchSize);
                                ActivityManagerConstants.BINDER_HEAVY_HITTER_WATCHER_THRESHOLD =
                                        DeviceConfig.getFloat(
                                                "activity_manager",
                                                "binder_heavy_hitter_watcher_threshold",
                                                activityManagerConstants6
                                                        .mDefaultBinderHeavyHitterWatcherThreshold);
                                ActivityManagerConstants.BINDER_HEAVY_HITTER_AUTO_SAMPLER_ENABLED =
                                        DeviceConfig.getBoolean(
                                                "activity_manager",
                                                "binder_heavy_hitter_auto_sampler_enabled",
                                                activityManagerConstants6
                                                        .mDefaultBinderHeavyHitterAutoSamplerEnabled);
                                ActivityManagerConstants
                                                .BINDER_HEAVY_HITTER_AUTO_SAMPLER_BATCHSIZE =
                                        DeviceConfig.getInt(
                                                "activity_manager",
                                                "binder_heavy_hitter_auto_sampler_batchsize",
                                                activityManagerConstants6
                                                        .mDefaultBinderHeavyHitterAutoSamplerBatchSize);
                                ActivityManagerConstants.BINDER_HEAVY_HITTER_WATCHER_THRESHOLD =
                                        DeviceConfig.getFloat(
                                                "activity_manager",
                                                "binder_heavy_hitter_auto_sampler_threshold",
                                                activityManagerConstants6
                                                        .mDefaultBinderHeavyHitterAutoSamplerThreshold);
                                ActivityManagerService activityManagerService =
                                        activityManagerConstants6.mService;
                                activityManagerService.mHandler.post(
                                        new ActivityManagerService$$ExternalSyntheticLambda11(
                                                activityManagerService, 2));
                            case 6:
                                ActivityManagerConstants activityManagerConstants7 = this.this$0;
                                activityManagerConstants7.getClass();
                                activityManagerConstants7.mFlagBackgroundActivityStartsEnabled =
                                        DeviceConfig.getBoolean(
                                                "activity_manager",
                                                "default_background_activity_starts_enabled",
                                                false);
                            case 7:
                                ActivityManagerConstants activityManagerConstants8 = this.this$0;
                                activityManagerConstants8.getClass();
                                activityManagerConstants8.mFgsAllowOptOut =
                                        DeviceConfig.getBoolean(
                                                "activity_manager", "fgs_allow_opt_out", false);
                            case '\b':
                                ActivityManagerConstants activityManagerConstants9 = this.this$0;
                                activityManagerConstants9.getClass();
                                activityManagerConstants9.mFgsStartDeniedLogSampleRate =
                                        DeviceConfig.getFloat(
                                                "activity_manager",
                                                "fgs_start_denied_log_sample_rate",
                                                1.0f);
                            case '\t':
                                ActivityManagerConstants activityManagerConstants10 = this.this$0;
                                activityManagerConstants10.getClass();
                                activityManagerConstants10
                                                .mFgsNotificationDeferralExclusionTimeForShort =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "deferred_fgs_notification_exclusion_time_for_short",
                                                120000L);
                            case '\n':
                                ActivityManagerConstants activityManagerConstants11 = this.this$0;
                                activityManagerConstants11.getClass();
                                activityManagerConstants11.mMaxServiceConnectionsPerProcess =
                                        DeviceConfig.getInt(
                                                "activity_manager",
                                                "max_service_connections_per_process",
                                                3000);
                            case 11:
                                ActivityManagerConstants activityManagerConstants12 = this.this$0;
                                activityManagerConstants12.getClass();
                                activityManagerConstants12.mFgToBgFgsGraceDuration =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "fg_to_bg_fgs_grace_duration",
                                                5000L);
                            case '\f':
                                ActivityManagerConstants activityManagerConstants13 = this.this$0;
                                activityManagerConstants13.getClass();
                                activityManagerConstants13.OOMADJ_UPDATE_QUICK =
                                        DeviceConfig.getInt(
                                                        "activity_manager",
                                                        "oomadj_update_policy",
                                                        1)
                                                == 1;
                            case '\r':
                                this.this$0.getClass();
                                ActivityManagerConstants.MAX_PREVIOUS_TIME =
                                        DeviceConfig.getLong(
                                                "activity_manager", "max_previous_time", 60000L);
                            case 14:
                                ActivityManagerConstants activityManagerConstants14 = this.this$0;
                                activityManagerConstants14.getClass();
                                activityManagerConstants14.mFgsNotificationDeferralInterval =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "deferred_fgs_notification_interval",
                                                10000L);
                            case 15:
                                ActivityManagerConstants activityManagerConstants15 = this.this$0;
                                int i = activityManagerConstants15.MAX_PHANTOM_PROCESSES;
                                int i2 =
                                        DeviceConfig.getInt(
                                                "activity_manager", "max_phantom_processes", 32);
                                activityManagerConstants15.MAX_PHANTOM_PROCESSES = i2;
                                if (i > i2) {
                                    ActivityManagerService activityManagerService2 =
                                            activityManagerConstants15.mService;
                                    ActivityManagerService.UiHandler uiHandler =
                                            activityManagerService2.mHandler;
                                    PhantomProcessList phantomProcessList =
                                            activityManagerService2.mPhantomProcessList;
                                    Objects.requireNonNull(phantomProcessList);
                                    uiHandler.post(
                                            new ActivityManagerConstants$$ExternalSyntheticLambda1(
                                                    phantomProcessList));
                                }
                            case 16:
                                ActivityManagerConstants activityManagerConstants16 = this.this$0;
                                activityManagerConstants16.getClass();
                                activityManagerConstants16.mShortFgsTimeoutDuration =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "short_fgs_timeout_duration",
                                                180000L);
                            case 17:
                                ActivityManagerConstants activityManagerConstants17 = this.this$0;
                                activityManagerConstants17.getClass();
                                activityManagerConstants17
                                                .mFlagBackgroundFgsStartRestrictionEnabled =
                                        DeviceConfig.getBoolean(
                                                "activity_manager",
                                                "default_background_fgs_starts_restriction_enabled",
                                                true);
                            case 18:
                                ActivityManagerConstants activityManagerConstants18 = this.this$0;
                                activityManagerConstants18.getClass();
                                activityManagerConstants18
                                                .FORCE_BACKGROUND_CHECK_ON_RESTRICTED_APPS =
                                        DeviceConfig.getBoolean(
                                                "activity_manager",
                                                "force_bg_check_on_restricted",
                                                true);
                            case 19:
                                this.this$0.updateMaxCachedProcesses();
                            case 20:
                                ActivityManagerConstants activityManagerConstants19 = this.this$0;
                                activityManagerConstants19.getClass();
                                activityManagerConstants19.mMaxEmptyTimeMillis =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "max_empty_time_millis",
                                                3600000000L);
                            case 21:
                                ActivityManagerConstants activityManagerConstants20 = this.this$0;
                                activityManagerConstants20.getClass();
                                activityManagerConstants20.mDataSyncFgsTimeoutDuration =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "data_sync_fgs_timeout_duration",
                                                21600000L);
                            case 22:
                                ActivityManagerConstants activityManagerConstants21 = this.this$0;
                                activityManagerConstants21.getClass();
                                activityManagerConstants21.mNetworkAccessTimeoutMs =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "network_access_timeout_ms",
                                                200L);
                            case 23:
                                this.this$0.getClass();
                                ActivityManagerConstants.MIN_ASSOC_LOG_DURATION =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "min_assoc_log_duration",
                                                300000L);
                            case 24:
                                ActivityManagerConstants activityManagerConstants22 = this.this$0;
                                activityManagerConstants22.getClass();
                                activityManagerConstants22.mProcessKillTimeoutMs =
                                        DeviceConfig.getLong(
                                                "activity_manager", "process_kill_timeout", 10000L);
                            case 25:
                                ActivityManagerConstants activityManagerConstants23 = this.this$0;
                                activityManagerConstants23.getClass();
                                String string3 =
                                        DeviceConfig.getString(
                                                "activity_manager",
                                                "prioritize_alarm_broadcasts",
                                                "");
                                activityManagerConstants23.mPrioritizeAlarmBroadcasts =
                                        TextUtils.isEmpty(string3)
                                                ? true
                                                : Boolean.parseBoolean(string3);
                            case 26:
                                ActivityManagerConstants activityManagerConstants24 = this.this$0;
                                activityManagerConstants24.getClass();
                                activityManagerConstants24.mFgsAtomSampleRate =
                                        DeviceConfig.getFloat(
                                                "activity_manager", "fgs_atom_sample_rate", 1.0f);
                            case 27:
                                ActivityManagerConstants activityManagerConstants25 = this.this$0;
                                activityManagerConstants25.getClass();
                                activityManagerConstants25.mServiceStartForegroundTimeoutMs =
                                        DeviceConfig.getInt(
                                                "activity_manager",
                                                "service_start_foreground_timeout_ms",
                                                30000);
                            case 28:
                                ActivityManagerConstants activityManagerConstants26 = this.this$0;
                                activityManagerConstants26.PSS_TO_RSS_THRESHOLD_MODIFIER =
                                        DeviceConfig.getFloat(
                                                "activity_manager",
                                                "pss_to_rss_threshold_modifier",
                                                activityManagerConstants26
                                                        .mDefaultPssToRssThresholdModifier);
                            case 29:
                                ActivityManagerConstants activityManagerConstants27 = this.this$0;
                                activityManagerConstants27.getClass();
                                activityManagerConstants27.mFgsStartAllowedLogSampleRate =
                                        DeviceConfig.getFloat(
                                                "activity_manager",
                                                "fgs_start_allowed_log_sample_rate",
                                                0.25f);
                            case 30:
                                ActivityManagerConstants activityManagerConstants28 = this.this$0;
                                activityManagerConstants28.getClass();
                                activityManagerConstants28.mFgsStartRestrictionNotificationEnabled =
                                        DeviceConfig.getBoolean(
                                                "activity_manager",
                                                "default_fgs_starts_restriction_notification_enabled",
                                                false);
                            case ' ':
                                ActivityManagerConstants activityManagerConstants29 = this.this$0;
                                activityManagerConstants29.getClass();
                                activityManagerConstants29.mPushMessagingOverQuotaBehavior =
                                        DeviceConfig.getInt(
                                                "activity_manager",
                                                "push_messaging_over_quota_behavior",
                                                1);
                                if (activityManagerConstants29.mPushMessagingOverQuotaBehavior < -1
                                        || activityManagerConstants29
                                                        .mPushMessagingOverQuotaBehavior
                                                > 1) {
                                    activityManagerConstants29.mPushMessagingOverQuotaBehavior = 1;
                                }
                                break;
                            case '!':
                                ActivityManagerConstants activityManagerConstants30 = this.this$0;
                                activityManagerConstants30.getClass();
                                activityManagerConstants30
                                                .mNoKillCachedProcessesPostBootCompletedDurationMillis =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "no_kill_cached_processes_post_boot_completed_duration_millis",
                                                0L);
                            case '\"':
                                ActivityManagerConstants activityManagerConstants31 = this.this$0;
                                activityManagerConstants31.getClass();
                                activityManagerConstants31.mFlagFgsNotificationDeferralApiGated =
                                        DeviceConfig.getBoolean(
                                                "activity_manager",
                                                "deferred_fgs_notifications_api_gated",
                                                false);
                            case '#':
                                ActivityManagerConstants activityManagerConstants32 = this.this$0;
                                activityManagerConstants32.getClass();
                                activityManagerConstants32.TOP_TO_FGS_GRACE_DURATION =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "top_to_fgs_grace_duration",
                                                15000L);
                            case '%':
                                ActivityManagerConstants activityManagerConstants33 = this.this$0;
                                activityManagerConstants33.getClass();
                                activityManagerConstants33.mFgsCrashExtraWaitDuration =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "fgs_crash_extra_wait_duration",
                                                10000L);
                            case '&':
                                ActivityManagerConstants activityManagerConstants34 = this.this$0;
                                activityManagerConstants34.getClass();
                                activityManagerConstants34.mDeferBootCompletedBroadcast =
                                        DeviceConfig.getInt(
                                                "activity_manager",
                                                "defer_boot_completed_broadcast",
                                                6);
                            case '\'':
                            case ';':
                                ActivityManagerConstants activityManagerConstants35 = this.this$0;
                                activityManagerConstants35.getClass();
                                activityManagerConstants35.USE_TIERED_CACHED_ADJ =
                                        DeviceConfig.getBoolean(
                                                "activity_manager", "use_tiered_cached_adj", false);
                                activityManagerConstants35.TIERED_CACHED_ADJ_DECAY_TIME =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "tiered_cached_adj_decay_time",
                                                60000L);
                            case '(':
                                this.this$0.getClass();
                                ActivityManagerConstants.PROACTIVE_KILLS_ENABLED =
                                        DeviceConfig.getBoolean(
                                                "activity_manager",
                                                "proactive_kills_enabled",
                                                false);
                            case ')':
                                ActivityManagerConstants activityManagerConstants36 = this.this$0;
                                activityManagerConstants36.getClass();
                                activityManagerConstants36
                                                .mFlagSystemExemptPowerRestrictionsEnabled =
                                        DeviceConfig.getBoolean(
                                                "activity_manager",
                                                "system_exempt_power_restrictions_enabled",
                                                true);
                            case '*':
                                ActivityManagerConstants activityManagerConstants37 = this.this$0;
                                ActivityManagerService activityManagerService3 =
                                        activityManagerConstants37.mService;
                                ActivityManagerService.boostPriorityForLockedSection();
                                synchronized (activityManagerService3) {
                                    try {
                                        boolean z2 =
                                                activityManagerConstants37
                                                        .mEnableExtraServiceRestartDelayOnMemPressure;
                                        boolean z3 =
                                                DeviceConfig.getBoolean(
                                                        "activity_manager",
                                                        "enable_extra_delay_svc_restart_mem_pressure",
                                                        true);
                                        activityManagerConstants37
                                                        .mEnableExtraServiceRestartDelayOnMemPressure =
                                                z3;
                                        ActiveServices activeServices =
                                                activityManagerConstants37.mService.mServices;
                                        long uptimeMillis = SystemClock.uptimeMillis();
                                        if (z2 == z3) {
                                            activeServices.getClass();
                                        } else {
                                            ActivityManagerService activityManagerService4 =
                                                    activeServices.mAm;
                                            long j2 =
                                                    activityManagerService4
                                                            .mConstants
                                                            .mExtraServiceRestartDelayOnMemPressure[
                                                            activityManagerService4.mAppProfiler
                                                                    .getLastMemoryLevelLocked()];
                                            activeServices
                                                    .performRescheduleServiceRestartOnMemoryPressureLocked(
                                                            z2 ? j2 : 0L,
                                                            z3 ? j2 : 0L,
                                                            uptimeMillis,
                                                            "config");
                                        }
                                    } finally {
                                    }
                                }
                                ActivityManagerService.resetPriorityAfterLockedSection();
                            case '+':
                                ActivityManagerConstants activityManagerConstants38 = this.this$0;
                                activityManagerConstants38.getClass();
                                activityManagerConstants38.mFgsNotificationDeferralExclusionTime =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "deferred_fgs_notification_exclusion_time",
                                                120000L);
                            case ',':
                                ActivityManagerConstants activityManagerConstants39 = this.this$0;
                                activityManagerConstants39.getClass();
                                activityManagerConstants39
                                                .mFgsStartRestrictionCheckCallerTargetSdk =
                                        DeviceConfig.getBoolean(
                                                "activity_manager",
                                                "default_fgs_starts_restriction_check_caller_target_sdk",
                                                true);
                            case '-':
                                ActivityManagerConstants activityManagerConstants40 = this.this$0;
                                activityManagerConstants40.getClass();
                                activityManagerConstants40
                                                .mNoKillCachedProcessesUntilBootCompleted =
                                        DeviceConfig.getBoolean(
                                                "activity_manager",
                                                "no_kill_cached_processes_until_boot_completed",
                                                false);
                            case '.':
                                ActivityManagerConstants activityManagerConstants41 = this.this$0;
                                activityManagerConstants41.getClass();
                                activityManagerConstants41.mFgToBgFgsGraceDuration =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "fg_to_bg_fgs_grace_duration",
                                                5000L);
                            case '/':
                                this.this$0.getClass();
                                ActivityManagerConstants.LOW_SWAP_THRESHOLD_PERCENT =
                                        DeviceConfig.getFloat(
                                                "activity_manager",
                                                "low_swap_threshold_percent",
                                                0.1f);
                            case '0':
                                ActivityManagerConstants activityManagerConstants42 = this.this$0;
                                activityManagerConstants42.getClass();
                                activityManagerConstants42.mBootTimeTempAllowlistDuration =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "boot_time_temp_allowlist_duration",
                                                20000L);
                            case '1':
                                ActivityManagerConstants activityManagerConstants43 = this.this$0;
                                activityManagerConstants43.getClass();
                                activityManagerConstants43.mKillBgRestrictedAndCachedIdle =
                                        DeviceConfig.getBoolean(
                                                "activity_manager",
                                                "kill_bg_restricted_cached_idle",
                                                false);
                            case '2':
                                ActivityManagerConstants activityManagerConstants44 = this.this$0;
                                activityManagerConstants44.getClass();
                                activityManagerConstants44.mShortFgsProcStateExtraWaitDuration =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "short_fgs_proc_state_extra_wait_duration",
                                                5000L);
                            case '3':
                                ActivityManagerConstants activityManagerConstants45 = this.this$0;
                                activityManagerConstants45.getClass();
                                activityManagerConstants45.mMediaProcessingFgsTimeoutDuration =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "media_processing_fgs_timeout_duration",
                                                21600000L);
                            case '4':
                                ActivityManagerConstants activityManagerConstants46 = this.this$0;
                                activityManagerConstants46.APP_PROFILER_PSS_PROFILING_DISABLED =
                                        DeviceConfig.getBoolean(
                                                "activity_manager",
                                                "disable_app_profiler_pss_profiling",
                                                activityManagerConstants46
                                                        .mDefaultDisableAppProfilerPssProfiling);
                            case '5':
                                ActivityManagerConstants activityManagerConstants47 = this.this$0;
                                ActivityManagerService activityManagerService5 =
                                        activityManagerConstants47.mService;
                                ActivityManagerService.boostPriorityForLockedSection();
                                synchronized (activityManagerService5) {
                                    try {
                                        int lastMemoryLevelLocked =
                                                activityManagerConstants47.mService.mAppProfiler
                                                        .getLastMemoryLevelLocked();
                                        long[] jArr =
                                                activityManagerConstants47
                                                        .mExtraServiceRestartDelayOnMemPressure;
                                        long[] jArr2 =
                                                ActivityManagerConstants
                                                        .DEFAULT_EXTRA_SERVICE_RESTART_DELAY_ON_MEM_PRESSURE;
                                        String string4 =
                                                DeviceConfig.getString(
                                                        "activity_manager",
                                                        "extra_delay_svc_restart_mem_pressure",
                                                        (String) null);
                                        if (!TextUtils.isEmpty(string4)) {
                                            String[] split = string4.split(",");
                                            if (split.length == jArr2.length) {
                                                long[] jArr3 = new long[split.length];
                                                for (int i3 = 0; i3 < split.length; i3++) {
                                                    try {
                                                        jArr3[i3] = Long.parseLong(split[i3]);
                                                    } catch (NumberFormatException unused) {
                                                    }
                                                }
                                                jArr2 = jArr3;
                                            }
                                        }
                                        activityManagerConstants47
                                                        .mExtraServiceRestartDelayOnMemPressure =
                                                jArr2;
                                        activityManagerConstants47.mService.mServices
                                                .performRescheduleServiceRestartOnMemoryPressureLocked(
                                                        jArr2[lastMemoryLevelLocked],
                                                        jArr[lastMemoryLevelLocked],
                                                        SystemClock.uptimeMillis(),
                                                        "config");
                                    } finally {
                                    }
                                }
                                ActivityManagerService.resetPriorityAfterLockedSection();
                            case '6':
                                ActivityManagerConstants activityManagerConstants48 = this.this$0;
                                activityManagerConstants48.getClass();
                                activityManagerConstants48.mFlagFgsNotificationDeferralEnabled =
                                        DeviceConfig.getBoolean(
                                                "activity_manager",
                                                "deferred_fgs_notifications_enabled",
                                                true);
                            case '7':
                                ActivityManagerConstants activityManagerConstants49 = this.this$0;
                                activityManagerConstants49.getClass();
                                activityManagerConstants49.mEnableWaitForFinishAttachApplication =
                                        DeviceConfig.getBoolean(
                                                "activity_manager",
                                                "enable_wait_for_finish_attach_application",
                                                true);
                            case '8':
                                ActivityManagerConstants activityManagerConstants50 = this.this$0;
                                activityManagerConstants50.getClass();
                                activityManagerConstants50.FOLLOW_UP_OOMADJ_UPDATE_WAIT_DURATION =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "follow_up_oomadj_update_wait_duration",
                                                1000L);
                            case '9':
                                ActivityManagerConstants activityManagerConstants51 = this.this$0;
                                activityManagerConstants51.getClass();
                                String trim =
                                        DeviceConfig.getString(
                                                        "activity_manager",
                                                        "proc_state_debug_uids",
                                                        "")
                                                .trim();
                                activityManagerConstants51.mEnableProcStateStacktrace = false;
                                activityManagerConstants51.mProcStateDebugSetProcStateDelay = 0;
                                activityManagerConstants51.mProcStateDebugSetUidStateDelay = 0;
                                if (trim.length() == 0) {
                                    activityManagerConstants51.mProcStateDebugUids =
                                            new SparseBooleanArray(0);
                                } else {
                                    String[] split2 = trim.split(",");
                                    SparseBooleanArray sparseBooleanArray =
                                            new SparseBooleanArray(0);
                                    int length = split2.length;
                                    for (int i4 = 0; i4 < length; i4++) {
                                        String str3 = split2[i4];
                                        if (str3.length() != 0) {
                                            if ("stack".equals(str3)) {
                                                activityManagerConstants51
                                                                .mEnableProcStateStacktrace =
                                                        true;
                                            } else {
                                                char charAt = str3.charAt(0);
                                                if ('a' > charAt || charAt > 'z') {
                                                    z = true;
                                                } else {
                                                    str3 = str3.substring(1);
                                                    z = false;
                                                }
                                                try {
                                                    int parseInt = Integer.parseInt(str3.trim());
                                                    if (z) {
                                                        sparseBooleanArray.put(parseInt, true);
                                                    } else if (charAt == 'p') {
                                                        activityManagerConstants51
                                                                        .mProcStateDebugSetProcStateDelay =
                                                                parseInt;
                                                    } else if (charAt == 'u') {
                                                        activityManagerConstants51
                                                                        .mProcStateDebugSetUidStateDelay =
                                                                parseInt;
                                                    } else {
                                                        Slog.w(
                                                                "ActivityManagerConstants",
                                                                "Invalid prefix "
                                                                        + charAt
                                                                        + " in "
                                                                        + trim);
                                                    }
                                                } catch (NumberFormatException unused2) {
                                                    Slog.w(
                                                            "ActivityManagerConstants",
                                                            "Invalid number "
                                                                    + str3
                                                                    + " in "
                                                                    + trim);
                                                }
                                            }
                                        }
                                    }
                                    activityManagerConstants51.mProcStateDebugUids =
                                            sparseBooleanArray;
                                }
                                break;
                            case '=':
                                ActivityManagerConstants activityManagerConstants52 = this.this$0;
                                activityManagerConstants52.getClass();
                                activityManagerConstants52.mFgsStartForegroundTimeoutMs =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "fgs_start_foreground_timeout",
                                                10000L);
                            case '>':
                                ActivityManagerConstants activityManagerConstants53 = this.this$0;
                                activityManagerConstants53.getClass();
                                activityManagerConstants53
                                                .mFgsNotificationDeferralIntervalForShort =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "deferred_fgs_notification_interval_for_short",
                                                10000L);
                            case '@':
                                ActivityManagerConstants activityManagerConstants54 = this.this$0;
                                activityManagerConstants54.getClass();
                                activityManagerConstants54.mServiceBindAlmostPerceptibleTimeoutMs =
                                        DeviceConfig.getLong(
                                                "activity_manager",
                                                "service_bind_almost_perceptible_timeout_ms",
                                                15000L);
                            default:
                                this.this$0.getClass();
                                ForegroundServiceTypePolicy.getDefaultPolicy()
                                        .updatePermissionEnforcementFlagIfNecessary(str);
                        }
                    }
                    return;
                default:
                    Iterator it2 = properties.getKeyset().iterator();
                    while (it2.hasNext() && (str2 = (String) it2.next()) != null) {
                        if (str2.equals("enable_experimental_component_alias")
                                || str2.equals("component_alias_overrides")) {
                            ActivityManagerConstants activityManagerConstants55 = this.this$0;
                            activityManagerConstants55.getClass();
                            activityManagerConstants55.mEnableComponentAlias =
                                    DeviceConfig.getBoolean(
                                            "activity_manager_ca",
                                            "enable_experimental_component_alias",
                                            false);
                            activityManagerConstants55.mComponentAliasOverrides =
                                    DeviceConfig.getString(
                                            "activity_manager_ca", "component_alias_overrides", "");
                            activityManagerConstants55.mService.mComponentAliasResolver.update(
                                    activityManagerConstants55.mComponentAliasOverrides,
                                    activityManagerConstants55.mEnableComponentAlias);
                        }
                    }
                    return;
            }
        }
    }

    static {
        Flags.oomadjusterCorrectnessRewrite();
        DEFAULT_ENABLE_NEW_OOM_ADJ = true;
        Flags.batchingOomAdj();
        DEFAULT_EXTRA_SERVICE_RESTART_DELAY_ON_MEM_PRESSURE = new long[] {0, 10000, 20000, 30000};
        long j = Build.HW_TIMEOUT_MULTIPLIER * 30000;
        DEFAULT_SERVICE_TIMEOUT = j;
        DEFAULT_SERVICE_BACKGROUND_TIMEOUT = j * 10;
        EMPTY_RATE = Float.parseFloat(SystemProperties.get("ro.slmk.fha_empty_rate", "0.5"));
        MAX_PREVIOUS_TIME = 60000L;
        MIN_CRASH_INTERVAL = 120000;
        PROCESS_CRASH_COUNT_RESET_INTERVAL = 43200000L;
        PROCESS_CRASH_COUNT_LIMIT = 12;
        ACTIVITY_MANAGER_CONSTANTS_URI = Settings.Global.getUriFor("activity_manager_constants");
        ACTIVITY_STARTS_LOGGING_ENABLED_URI =
                Settings.Global.getUriFor("activity_starts_logging_enabled");
        FOREGROUND_SERVICE_STARTS_LOGGING_ENABLED_URI =
                Settings.Global.getUriFor("foreground_service_starts_logging_enabled");
        ENABLE_AUTOMATIC_SYSTEM_SERVER_HEAP_DUMPS_URI =
                Settings.Global.getUriFor("enable_automatic_system_server_heap_dumps");
        FORCE_ENABLE_PSS_PROFILING_URI = Settings.Global.getUriFor("force_enable_pss_profiling");
        MIN_ASSOC_LOG_DURATION = 300000L;
        PROACTIVE_KILLS_ENABLED = false;
        LOW_SWAP_THRESHOLD_PERCENT = 0.1f;
    }

    public ActivityManagerConstants(
            Context context,
            ActivityManagerService activityManagerService,
            ActivityManagerService.UiHandler uiHandler) {
        super(uiHandler);
        this.mProcStateDebugUids = new SparseBooleanArray(0);
        this.mEnableProcStateStacktrace = false;
        this.mProcStateDebugSetProcStateDelay = 0;
        this.mProcStateDebugSetUidStateDelay = 0;
        this.MAX_CACHED_PROCESSES = DEFAULT_MAX_CACHED_PROCESSES;
        this.BACKGROUND_SETTLE_TIME = 60000L;
        this.FGSERVICE_MIN_SHOWN_TIME = 2000L;
        this.FGSERVICE_MIN_REPORT_TIME = 3000L;
        this.FGSERVICE_SCREEN_ON_BEFORE_TIME = 1000L;
        this.FGSERVICE_SCREEN_ON_AFTER_TIME = 5000L;
        this.FGS_BOOT_COMPLETED_ALLOWLIST = 1073743640;
        this.CONTENT_PROVIDER_RETAIN_TIME = 20000L;
        this.GC_TIMEOUT = 5000L;
        this.GC_MIN_INTERVAL = 60000L;
        this.FORCE_BACKGROUND_CHECK_ON_RESTRICTED_APPS = true;
        this.FULL_PSS_MIN_INTERVAL = 1200000L;
        this.FULL_PSS_LOWERED_INTERVAL = 300000L;
        this.POWER_CHECK_INTERVAL = 300000L;
        this.POWER_CHECK_MAX_CPU_1 = 25;
        this.POWER_CHECK_MAX_CPU_2 = 25;
        this.POWER_CHECK_MAX_CPU_3 = 10;
        this.POWER_CHECK_MAX_CPU_4 = 2;
        this.SERVICE_USAGE_INTERACTION_TIME_PRE_S = 1800000L;
        this.SERVICE_USAGE_INTERACTION_TIME_POST_S = 60000L;
        this.USAGE_STATS_INTERACTION_INTERVAL_PRE_S = 7200000L;
        this.USAGE_STATS_INTERACTION_INTERVAL_POST_S = 600000L;
        this.SERVICE_RESTART_DURATION = 1000L;
        this.SERVICE_RESET_RUN_DURATION = 60000L;
        this.SERVICE_RESTART_DURATION_FACTOR = 4;
        this.SERVICE_MIN_RESTART_TIME_BETWEEN = 10000L;
        this.SERVICE_TIMEOUT = DEFAULT_SERVICE_TIMEOUT;
        this.SERVICE_BACKGROUND_TIMEOUT = DEFAULT_SERVICE_BACKGROUND_TIMEOUT;
        this.MAX_SERVICE_INACTIVITY = 1800000L;
        this.BG_START_TIMEOUT = 15000L;
        this.SERVICE_BG_ACTIVITY_START_TIMEOUT = 10000L;
        this.BOUND_SERVICE_CRASH_RESTART_DURATION = 1800000L;
        this.BOUND_SERVICE_MAX_CRASH_RETRY = 16L;
        this.FLAG_PROCESS_START_ASYNC = true;
        this.MEMORY_INFO_THROTTLE_TIME = 300000L;
        this.TOP_TO_FGS_GRACE_DURATION = 15000L;
        this.TOP_TO_ALMOST_PERCEPTIBLE_GRACE_DURATION = 15000L;
        this.mFlagBackgroundFgsStartRestrictionEnabled = true;
        this.mFlagFgsStartRestrictionEnabled = true;
        this.mFgsStartRestrictionNotificationEnabled = false;
        this.mForceEnablePssProfiling = false;
        this.mFgsStartRestrictionCheckCallerTargetSdk = true;
        this.mFlagFgsNotificationDeferralEnabled = true;
        this.mFlagFgsNotificationDeferralApiGated = false;
        this.mFgsNotificationDeferralInterval = 10000L;
        this.mFgsNotificationDeferralIntervalForShort = this.mFgsNotificationDeferralInterval;
        this.mFgsNotificationDeferralExclusionTime = 120000L;
        this.mFgsNotificationDeferralExclusionTimeForShort =
                this.mFgsNotificationDeferralExclusionTime;
        this.mFlagSystemExemptPowerRestrictionsEnabled = true;
        this.mPushMessagingOverQuotaBehavior = 1;
        this.mBootTimeTempAllowlistDuration = 20000L;
        this.mFgToBgFgsGraceDuration = 5000L;
        this.mVisibleToInvisibleUijScheduleGraceDurationMs = 5000L;
        this.mFgsStartForegroundTimeoutMs = 10000L;
        this.mFgsAtomSampleRate = 1.0f;
        this.mFgsStartAllowedLogSampleRate = 0.25f;
        this.mFgsStartDeniedLogSampleRate = 1.0f;
        this.mKillBgRestrictedAndCachedIdle = false;
        this.mKillBgRestrictedAndCachedIdleSettleTimeMs = 60000L;
        this.mProcessKillTimeoutMs = 10000L;
        this.mFgsAllowOptOut = false;
        this.mExtraServiceRestartDelayOnMemPressure =
                DEFAULT_EXTRA_SERVICE_RESTART_DELAY_ON_MEM_PRESSURE;
        this.mEnableExtraServiceRestartDelayOnMemPressure = true;
        this.mEnableComponentAlias = false;
        this.mDeferBootCompletedBroadcast = 6;
        this.mPrioritizeAlarmBroadcasts = true;
        this.mServiceStartForegroundTimeoutMs = 30000;
        this.mServiceStartForegroundAnrDelayMs = 10000;
        this.mServiceBindAlmostPerceptibleTimeoutMs = 15000L;
        this.mComponentAliasOverrides = "";
        this.mMaxServiceConnectionsPerProcess = 3000;
        this.mParser = new KeyValueListParser(',');
        this.mOverrideMaxCachedProcesses = -1;
        this.mNoKillCachedProcessesUntilBootCompleted = false;
        this.mNoKillCachedProcessesPostBootCompletedDurationMillis = 0L;
        this.CUR_TRIM_EMPTY_PROCESSES = computeEmptyProcessLimit(this.MAX_CACHED_PROCESSES) / 2;
        int i = this.MAX_CACHED_PROCESSES;
        this.CUR_TRIM_CACHED_PROCESSES = (i - computeEmptyProcessLimit(i)) / 3;
        this.CUSTOM_CUR_TRIM_EMPTY_PROCESSES = 0;
        this.CUSTOM_CUR_TRIM_CACHED_PROCESSES = 0;
        this.mMaxEmptyTimeMillis = 3600000000L;
        ArraySet arraySet = new ArraySet();
        this.IMPERCEPTIBLE_KILL_EXEMPT_PACKAGES = arraySet;
        ArraySet arraySet2 = new ArraySet();
        this.IMPERCEPTIBLE_KILL_EXEMPT_PROC_STATES = arraySet2;
        this.PENDINGINTENT_WARNING_THRESHOLD = 2000;
        ArraySet arraySet3 = new ArraySet();
        this.KEEP_WARMING_SERVICES = arraySet3;
        this.MAX_PHANTOM_PROCESSES = 32;
        this.mNetworkAccessTimeoutMs = 200L;
        this.OOMADJ_UPDATE_QUICK = true;
        this.mShortFgsTimeoutDuration = 180000L;
        this.mShortFgsProcStateExtraWaitDuration = 5000L;
        this.mMediaProcessingFgsTimeoutDuration = 21600000L;
        this.mDataSyncFgsTimeoutDuration = 21600000L;
        this.mEnableWaitForFinishAttachApplication = true;
        this.mShortFgsAnrExtraWaitDuration = 10000L;
        this.mFgsCrashExtraWaitDuration = 10000L;
        this.USE_TIERED_CACHED_ADJ = false;
        this.TIERED_CACHED_ADJ_DECAY_TIME = 60000L;
        boolean z = DEFAULT_ENABLE_NEW_OOM_ADJ;
        this.ENABLE_NEW_OOMADJ = z;
        this.ENABLE_BATCHING_OOM_ADJ = false;
        this.FOLLOW_UP_OOMADJ_UPDATE_WAIT_DURATION = 1000L;
        this.mOnDeviceConfigChangedListener = new AnonymousClass1(this, 0);
        this.mOnDeviceConfigChangedForComponentAliasListener =
                new AnonymousClass1(this, true ? 1 : 0);
        this.mService = activityManagerService;
        this.mSystemServerAutomaticHeapDumpEnabled =
                Build.IS_DEBUGGABLE
                        && context.getResources()
                                .getBoolean(R.bool.config_defaultBinderHeavyHitterWatcherEnabled);
        this.mSystemServerAutomaticHeapDumpPackageName = context.getPackageName();
        this.mSystemServerAutomaticHeapDumpPssThresholdBytes =
                Math.max(
                        102400L,
                        context.getResources()
                                .getInteger(R.integer.config_deviceStateConcurrentRearDisplay));
        List asList =
                Arrays.asList(
                        context.getResources().getStringArray(R.array.config_sfps_sensor_props));
        this.mDefaultImperceptibleKillExemptPackages = asList;
        List list =
                (List)
                        Arrays.stream(
                                        context.getResources()
                                                .getIntArray(
                                                        R.array
                                                                .config_sharedLibrariesLoadedAfterApp))
                                .boxed()
                                .collect(Collectors.toList());
        this.mDefaultImperceptibleKillExemptProcStates = list;
        arraySet.addAll(asList);
        arraySet2.addAll(list);
        boolean z2 = context.getResources().getBoolean(R.bool.config_defaultRingtonePickerEnabled);
        this.mDefaultBinderHeavyHitterWatcherEnabled = z2;
        int integer =
                context.getResources()
                        .getInteger(
                                R.integer.config_displayWhiteBalanceColorTemperatureFilterHorizon);
        this.mDefaultBinderHeavyHitterWatcherBatchSize = integer;
        float f =
                context.getResources()
                        .getFloat(R.dimen.config_minPercentageMultiWindowSupportHeight);
        this.mDefaultBinderHeavyHitterWatcherThreshold = f;
        boolean z3 =
                context.getResources().getBoolean(R.bool.config_defaultKeyboardVibrationEnabled);
        this.mDefaultBinderHeavyHitterAutoSamplerEnabled = z3;
        int integer2 =
                context.getResources()
                        .getInteger(R.integer.config_displayWhiteBalanceColorTemperatureDefault);
        this.mDefaultBinderHeavyHitterAutoSamplerBatchSize = integer2;
        float f2 = context.getResources().getFloat(R.dimen.config_mediaMetadataBitmapMaxSize);
        this.mDefaultBinderHeavyHitterAutoSamplerThreshold = f2;
        BINDER_HEAVY_HITTER_WATCHER_ENABLED = z2;
        BINDER_HEAVY_HITTER_WATCHER_BATCHSIZE = integer;
        BINDER_HEAVY_HITTER_WATCHER_THRESHOLD = f;
        BINDER_HEAVY_HITTER_AUTO_SAMPLER_ENABLED = z3;
        BINDER_HEAVY_HITTER_AUTO_SAMPLER_BATCHSIZE = integer2;
        BINDER_HEAVY_HITTER_AUTO_SAMPLER_THRESHOLD = f2;
        activityManagerService.mHandler.post(
                new ActivityManagerService$$ExternalSyntheticLambda11(activityManagerService, 2));
        arraySet3.addAll(
                (Collection)
                        Arrays.stream(
                                        context.getResources()
                                                .getStringArray(
                                                        R.array
                                                                .vendor_disallowed_apps_managed_user))
                                .map(new AccessibilityManagerService$$ExternalSyntheticLambda5(3))
                                .collect(Collectors.toSet()));
        this.mCustomizedMaxCachedProcesses =
                context.getResources().getInteger(R.integer.config_defaultRefreshRateInHbmSunlight);
        int i2 = DEFAULT_MAX_CACHED_PROCESSES;
        this.CUR_MAX_CACHED_PROCESSES = i2;
        this.CUR_MAX_EMPTY_PROCESSES = computeEmptyProcessLimit(i2);
        int computeEmptyProcessLimit =
                computeEmptyProcessLimit(
                        Integer.min(this.CUR_MAX_CACHED_PROCESSES, this.MAX_CACHED_PROCESSES));
        this.CUR_TRIM_EMPTY_PROCESSES = computeEmptyProcessLimit / 2;
        this.CUR_TRIM_CACHED_PROCESSES =
                (Integer.min(this.CUR_MAX_CACHED_PROCESSES, this.MAX_CACHED_PROCESSES)
                                - computeEmptyProcessLimit)
                        / 3;
        int i3 = BroadcastConstants.MAX_HISTORY_ABORTED_BROADCAST;
        this.ENABLE_NEW_OOMADJ =
                SystemProperties.getBoolean(
                        "persist.sys.activity_manager_native_boot.".concat("enable_new_oom_adj"),
                        SystemProperties.getBoolean(
                                "persist.device_config.activity_manager_native_boot."
                                        .concat("enable_new_oom_adj"),
                                z));
        this.ENABLE_BATCHING_OOM_ADJ =
                SystemProperties.getBoolean(
                        "persist.sys.activity_manager_native_boot."
                                .concat("enable_batching_oom_adj"),
                        SystemProperties.getBoolean(
                                "persist.device_config.activity_manager_native_boot."
                                        .concat("enable_batching_oom_adj"),
                                false));
        boolean z4 =
                context.getResources()
                        .getBoolean(R.bool.config_appCompatUserAppAspectRatioFullscreenIsEnabled);
        this.mDefaultDisableAppProfilerPssProfiling = z4;
        this.APP_PROFILER_PSS_PROFILING_DISABLED = z4;
        float f3 =
                context.getResources()
                        .getFloat(R.dimen.config_letterboxBackgroundWallpaperBlurRadius);
        this.mDefaultPssToRssThresholdModifier = f3;
        this.PSS_TO_RSS_THRESHOLD_MODIFIER = f3;
    }

    public static int computeEmptyProcessLimit(int i) {
        return (int) ((i * EMPTY_RATE) + 0.1d);
    }

    @NeverCompile
    public final void dump(PrintWriter printWriter) {
        printWriter.println(
                "ACTIVITY MANAGER SETTINGS (dumpsys activity settings)"
                    + " activity_manager_constants:");
        printWriter.print("  ");
        printWriter.print("max_cached_processes");
        printWriter.print("=");
        printWriter.println(this.MAX_CACHED_PROCESSES);
        printWriter.print("  ");
        printWriter.print("background_settle_time");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.BACKGROUND_SETTLE_TIME, printWriter, "  ", "fgservice_min_shown_time");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.FGSERVICE_MIN_SHOWN_TIME, printWriter, "  ", "fgservice_min_report_time");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.FGSERVICE_MIN_REPORT_TIME,
                printWriter,
                "  ",
                "fgservice_screen_on_before_time");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.FGSERVICE_SCREEN_ON_BEFORE_TIME,
                printWriter,
                "  ",
                "fgservice_screen_on_after_time");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.FGSERVICE_SCREEN_ON_AFTER_TIME,
                printWriter,
                "  ",
                "fgs_boot_completed_allowlist");
        printWriter.print("=");
        printWriter.println(this.FGS_BOOT_COMPLETED_ALLOWLIST);
        printWriter.print("  ");
        printWriter.print("content_provider_retain_time");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.CONTENT_PROVIDER_RETAIN_TIME, printWriter, "  ", "gc_timeout");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.GC_TIMEOUT, printWriter, "  ", "gc_min_interval");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.GC_MIN_INTERVAL, printWriter, "  ", "force_bg_check_on_restricted");
        printWriter.print("=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter,
                this.FORCE_BACKGROUND_CHECK_ON_RESTRICTED_APPS,
                "  ",
                "full_pss_min_interval",
                "=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.FULL_PSS_MIN_INTERVAL, printWriter, "  ", "full_pss_lowered_interval");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.FULL_PSS_LOWERED_INTERVAL, printWriter, "  ", "power_check_interval");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.POWER_CHECK_INTERVAL, printWriter, "  ", "power_check_max_cpu_1");
        printWriter.print("=");
        printWriter.println(this.POWER_CHECK_MAX_CPU_1);
        printWriter.print("  ");
        printWriter.print("power_check_max_cpu_2");
        printWriter.print("=");
        printWriter.println(this.POWER_CHECK_MAX_CPU_2);
        printWriter.print("  ");
        printWriter.print("power_check_max_cpu_3");
        printWriter.print("=");
        printWriter.println(this.POWER_CHECK_MAX_CPU_3);
        printWriter.print("  ");
        printWriter.print("power_check_max_cpu_4");
        printWriter.print("=");
        printWriter.println(this.POWER_CHECK_MAX_CPU_4);
        printWriter.print("  ");
        printWriter.print("service_usage_interaction_time");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.SERVICE_USAGE_INTERACTION_TIME_PRE_S,
                printWriter,
                "  ",
                "service_usage_interaction_time_post_s");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.SERVICE_USAGE_INTERACTION_TIME_POST_S,
                printWriter,
                "  ",
                "usage_stats_interaction_interval");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.USAGE_STATS_INTERACTION_INTERVAL_PRE_S,
                printWriter,
                "  ",
                "usage_stats_interaction_interval_post_s");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.USAGE_STATS_INTERACTION_INTERVAL_POST_S,
                printWriter,
                "  ",
                "service_restart_duration");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.SERVICE_RESTART_DURATION, printWriter, "  ", "service_reset_run_duration");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.SERVICE_RESET_RUN_DURATION,
                printWriter,
                "  ",
                "service_restart_duration_factor");
        printWriter.print("=");
        printWriter.println(this.SERVICE_RESTART_DURATION_FACTOR);
        printWriter.print("  ");
        printWriter.print("service_min_restart_time_between");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.SERVICE_MIN_RESTART_TIME_BETWEEN, printWriter, "  ", "service_max_inactivity");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.MAX_SERVICE_INACTIVITY, printWriter, "  ", "service_bg_start_timeout");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.BG_START_TIMEOUT, printWriter, "  ", "service_bg_activity_start_timeout");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.SERVICE_BG_ACTIVITY_START_TIMEOUT,
                printWriter,
                "  ",
                "service_crash_restart_duration");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.BOUND_SERVICE_CRASH_RESTART_DURATION,
                printWriter,
                "  ",
                "service_crash_max_retry");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.BOUND_SERVICE_MAX_CRASH_RETRY, printWriter, "  ", "process_start_async");
        printWriter.print("=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter, this.FLAG_PROCESS_START_ASYNC, "  ", "memory_info_throttle_time", "=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.MEMORY_INFO_THROTTLE_TIME, printWriter, "  ", "top_to_fgs_grace_duration");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.TOP_TO_FGS_GRACE_DURATION,
                printWriter,
                "  ",
                "top_to_almost_perceptible_grace_duration");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.TOP_TO_ALMOST_PERCEPTIBLE_GRACE_DURATION,
                printWriter,
                "  ",
                "min_crash_interval");
        printWriter.print("=");
        printWriter.println(MIN_CRASH_INTERVAL);
        printWriter.print("  ");
        printWriter.print("process_crash_count_reset_interval");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                PROCESS_CRASH_COUNT_RESET_INTERVAL, printWriter, "  ", "process_crash_count_limit");
        printWriter.print("=");
        printWriter.println(PROCESS_CRASH_COUNT_LIMIT);
        printWriter.print("  ");
        printWriter.print("imperceptible_kill_exempt_proc_states");
        printWriter.print("=");
        printWriter.println(Arrays.toString(this.IMPERCEPTIBLE_KILL_EXEMPT_PROC_STATES.toArray()));
        printWriter.print("  ");
        printWriter.print("imperceptible_kill_exempt_packages");
        printWriter.print("=");
        printWriter.println(Arrays.toString(this.IMPERCEPTIBLE_KILL_EXEMPT_PACKAGES.toArray()));
        printWriter.print("  ");
        printWriter.print("min_assoc_log_duration");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                MIN_ASSOC_LOG_DURATION, printWriter, "  ", "binder_heavy_hitter_watcher_enabled");
        printWriter.print("=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter,
                BINDER_HEAVY_HITTER_WATCHER_ENABLED,
                "  ",
                "binder_heavy_hitter_watcher_batchsize",
                "=");
        printWriter.println(BINDER_HEAVY_HITTER_WATCHER_BATCHSIZE);
        printWriter.print("  ");
        printWriter.print("binder_heavy_hitter_watcher_threshold");
        printWriter.print("=");
        printWriter.println(BINDER_HEAVY_HITTER_WATCHER_THRESHOLD);
        printWriter.print("  ");
        printWriter.print("binder_heavy_hitter_auto_sampler_enabled");
        printWriter.print("=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter,
                BINDER_HEAVY_HITTER_AUTO_SAMPLER_ENABLED,
                "  ",
                "binder_heavy_hitter_auto_sampler_batchsize",
                "=");
        printWriter.println(BINDER_HEAVY_HITTER_AUTO_SAMPLER_BATCHSIZE);
        printWriter.print("  ");
        printWriter.print("binder_heavy_hitter_auto_sampler_threshold");
        printWriter.print("=");
        printWriter.println(BINDER_HEAVY_HITTER_AUTO_SAMPLER_THRESHOLD);
        printWriter.print("  ");
        printWriter.print("max_phantom_processes");
        printWriter.print("=");
        printWriter.println(this.MAX_PHANTOM_PROCESSES);
        printWriter.print("  ");
        printWriter.print("boot_time_temp_allowlist_duration");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mBootTimeTempAllowlistDuration,
                printWriter,
                "  ",
                "fg_to_bg_fgs_grace_duration");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mFgToBgFgsGraceDuration, printWriter, "  ", "fgs_start_foreground_timeout");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mFgsStartForegroundTimeoutMs,
                printWriter,
                "  ",
                "default_background_activity_starts_enabled");
        printWriter.print("=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter,
                this.mFlagBackgroundActivityStartsEnabled,
                "  ",
                "default_background_fgs_starts_restriction_enabled",
                "=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter,
                this.mFlagBackgroundFgsStartRestrictionEnabled,
                "  ",
                "default_fgs_starts_restriction_enabled",
                "=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter,
                this.mFlagFgsStartRestrictionEnabled,
                "  ",
                "default_fgs_starts_restriction_notification_enabled",
                "=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter,
                this.mFgsStartRestrictionNotificationEnabled,
                "  ",
                "default_fgs_starts_restriction_check_caller_target_sdk",
                "=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter,
                this.mFgsStartRestrictionCheckCallerTargetSdk,
                "  ",
                "fgs_atom_sample_rate",
                "=");
        printWriter.println(this.mFgsAtomSampleRate);
        printWriter.print("  ");
        printWriter.print("fgs_start_allowed_log_sample_rate");
        printWriter.print("=");
        printWriter.println(this.mFgsStartAllowedLogSampleRate);
        printWriter.print("  ");
        printWriter.print("fgs_start_denied_log_sample_rate");
        printWriter.print("=");
        printWriter.println(this.mFgsStartDeniedLogSampleRate);
        printWriter.print("  ");
        printWriter.print("push_messaging_over_quota_behavior");
        printWriter.print("=");
        printWriter.println(this.mPushMessagingOverQuotaBehavior);
        printWriter.print("  ");
        printWriter.print("fgs_allow_opt_out");
        printWriter.print("=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter,
                this.mFgsAllowOptOut,
                "  ",
                "enable_experimental_component_alias",
                "=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter, this.mEnableComponentAlias, "  ", "component_alias_overrides", "=");
        printWriter.println(this.mComponentAliasOverrides);
        printWriter.print("  ");
        printWriter.print("defer_boot_completed_broadcast");
        printWriter.print("=");
        printWriter.println(this.mDeferBootCompletedBroadcast);
        printWriter.print("  ");
        printWriter.print("prioritize_alarm_broadcasts");
        printWriter.print("=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter,
                this.mPrioritizeAlarmBroadcasts,
                "  ",
                "no_kill_cached_processes_until_boot_completed",
                "=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter,
                this.mNoKillCachedProcessesUntilBootCompleted,
                "  ",
                "no_kill_cached_processes_post_boot_completed_duration_millis",
                "=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mNoKillCachedProcessesPostBootCompletedDurationMillis,
                printWriter,
                "  ",
                "max_empty_time_millis");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mMaxEmptyTimeMillis, printWriter, "  ", "service_start_foreground_timeout_ms");
        printWriter.print("=");
        printWriter.println(this.mServiceStartForegroundTimeoutMs);
        printWriter.print("  ");
        printWriter.print("service_start_foreground_anr_delay_ms");
        printWriter.print("=");
        printWriter.println(this.mServiceStartForegroundAnrDelayMs);
        printWriter.print("  ");
        printWriter.print("service_bind_almost_perceptible_timeout_ms");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mServiceBindAlmostPerceptibleTimeoutMs,
                printWriter,
                "  ",
                "network_access_timeout_ms");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mNetworkAccessTimeoutMs,
                printWriter,
                "  ",
                "max_service_connections_per_process");
        printWriter.print("=");
        printWriter.println(this.mMaxServiceConnectionsPerProcess);
        printWriter.print("  ");
        printWriter.print("proactive_kills_enabled");
        printWriter.print("=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter, PROACTIVE_KILLS_ENABLED, "  ", "low_swap_threshold_percent", "=");
        printWriter.println(LOW_SWAP_THRESHOLD_PERCENT);
        printWriter.print("  ");
        printWriter.print("deferred_fgs_notifications_enabled");
        printWriter.print("=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter,
                this.mFlagFgsNotificationDeferralEnabled,
                "  ",
                "deferred_fgs_notifications_api_gated",
                "=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter,
                this.mFlagFgsNotificationDeferralApiGated,
                "  ",
                "deferred_fgs_notification_interval",
                "=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mFgsNotificationDeferralInterval,
                printWriter,
                "  ",
                "deferred_fgs_notification_interval_for_short");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mFgsNotificationDeferralIntervalForShort,
                printWriter,
                "  ",
                "deferred_fgs_notification_exclusion_time");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mFgsNotificationDeferralExclusionTime,
                printWriter,
                "  ",
                "deferred_fgs_notification_exclusion_time_for_short");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mFgsNotificationDeferralExclusionTimeForShort,
                printWriter,
                "  ",
                "system_exempt_power_restrictions_enabled");
        printWriter.print("=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter,
                this.mFlagSystemExemptPowerRestrictionsEnabled,
                "  ",
                "short_fgs_timeout_duration",
                "=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mShortFgsTimeoutDuration,
                printWriter,
                "  ",
                "short_fgs_proc_state_extra_wait_duration");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mShortFgsProcStateExtraWaitDuration,
                printWriter,
                "  ",
                "short_fgs_anr_extra_wait_duration");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mShortFgsAnrExtraWaitDuration,
                printWriter,
                "  ",
                "media_processing_fgs_timeout_duration");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mMediaProcessingFgsTimeoutDuration,
                printWriter,
                "  ",
                "data_sync_fgs_timeout_duration");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mDataSyncFgsTimeoutDuration,
                printWriter,
                "  ",
                "fgs_crash_extra_wait_duration");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mFgsCrashExtraWaitDuration, printWriter, "  ", "use_tiered_cached_adj");
        printWriter.print("=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter, this.USE_TIERED_CACHED_ADJ, "  ", "tiered_cached_adj_decay_time", "=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.TIERED_CACHED_ADJ_DECAY_TIME, printWriter, "  ", "enable_new_oom_adj");
        printWriter.print("=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter,
                this.ENABLE_NEW_OOMADJ,
                "  ",
                "disable_app_profiler_pss_profiling",
                "=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter,
                this.APP_PROFILER_PSS_PROFILING_DISABLED,
                "  ",
                "pss_to_rss_threshold_modifier",
                "=");
        printWriter.println(this.PSS_TO_RSS_THRESHOLD_MODIFIER);
        printWriter.print("  ");
        printWriter.print("max_previous_time");
        printWriter.print("=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                MAX_PREVIOUS_TIME, printWriter, "  ", "enable_batching_oom_adj");
        printWriter.print("=");
        printWriter.println(this.ENABLE_BATCHING_OOM_ADJ);
        printWriter.println();
        if (this.mOverrideMaxCachedProcesses >= 0) {
            printWriter.print("  mOverrideMaxCachedProcesses=");
            printWriter.println(this.mOverrideMaxCachedProcesses);
        }
        printWriter.print("  mCustomizedMaxCachedProcesses=");
        printWriter.println(this.mCustomizedMaxCachedProcesses);
        printWriter.print("  CUR_MAX_CACHED_PROCESSES=");
        printWriter.println(this.CUR_MAX_CACHED_PROCESSES);
        printWriter.print("  CUR_MAX_EMPTY_PROCESSES=");
        printWriter.println(this.CUR_MAX_EMPTY_PROCESSES);
        printWriter.print("  CUR_TRIM_EMPTY_PROCESSES=");
        printWriter.println(this.CUR_TRIM_EMPTY_PROCESSES);
        printWriter.print("  CUR_TRIM_CACHED_PROCESSES=");
        printWriter.println(this.CUR_TRIM_CACHED_PROCESSES);
        printWriter.print("  OOMADJ_UPDATE_QUICK=");
        printWriter.println(this.OOMADJ_UPDATE_QUICK);
        printWriter.print("  ENABLE_WAIT_FOR_FINISH_ATTACH_APPLICATION=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                printWriter,
                this.mEnableWaitForFinishAttachApplication,
                "  ",
                "follow_up_oomadj_update_wait_duration",
                "=");
        printWriter.println(this.FOLLOW_UP_OOMADJ_UPDATE_WAIT_DURATION);
        synchronized (this.mProcStateDebugUids) {
            printWriter.print("  ");
            printWriter.print("proc_state_debug_uids");
            printWriter.print("=");
            printWriter.println(this.mProcStateDebugUids);
            printWriter.print("    uid-state-delay=");
            printWriter.println(this.mProcStateDebugSetUidStateDelay);
            printWriter.print("    proc-state-delay=");
            printWriter.println(this.mProcStateDebugSetProcStateDelay);
        }
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z, Uri uri) {
        if (uri == null) {
            return;
        }
        if (ACTIVITY_MANAGER_CONSTANTS_URI.equals(uri)) {
            updateConstants();
            return;
        }
        if (ACTIVITY_STARTS_LOGGING_ENABLED_URI.equals(uri)) {
            this.mFlagActivityStartsLoggingEnabled =
                    Settings.Global.getInt(this.mResolver, "activity_starts_logging_enabled", 1)
                            == 1;
            return;
        }
        if (FOREGROUND_SERVICE_STARTS_LOGGING_ENABLED_URI.equals(uri)) {
            Settings.Global.getInt(this.mResolver, "foreground_service_starts_logging_enabled", 1);
        } else if (ENABLE_AUTOMATIC_SYSTEM_SERVER_HEAP_DUMPS_URI.equals(uri)) {
            updateEnableAutomaticSystemServerHeapDumps();
        } else if (FORCE_ENABLE_PSS_PROFILING_URI.equals(uri)) {
            this.mForceEnablePssProfiling =
                    Settings.Global.getInt(this.mResolver, "force_enable_pss_profiling", 0) == 1;
        }
    }

    public final void updateConstants() {
        String string = Settings.Global.getString(this.mResolver, "activity_manager_constants");
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                try {
                    this.mParser.setString(string);
                } catch (IllegalArgumentException e) {
                    Slog.e("ActivityManagerConstants", "Bad activity manager config settings", e);
                }
                long j = this.POWER_CHECK_INTERVAL;
                this.BACKGROUND_SETTLE_TIME =
                        this.mParser.getLong("background_settle_time", 60000L);
                this.FGSERVICE_MIN_SHOWN_TIME =
                        this.mParser.getLong("fgservice_min_shown_time", 2000L);
                this.FGSERVICE_MIN_REPORT_TIME =
                        this.mParser.getLong("fgservice_min_report_time", 3000L);
                this.FGSERVICE_SCREEN_ON_BEFORE_TIME =
                        this.mParser.getLong("fgservice_screen_on_before_time", 1000L);
                this.FGSERVICE_SCREEN_ON_AFTER_TIME =
                        this.mParser.getLong("fgservice_screen_on_after_time", 5000L);
                this.FGS_BOOT_COMPLETED_ALLOWLIST =
                        this.mParser.getInt("fgs_boot_completed_allowlist", 1073743640);
                this.CONTENT_PROVIDER_RETAIN_TIME =
                        this.mParser.getLong("content_provider_retain_time", 20000L);
                this.GC_TIMEOUT = this.mParser.getLong("gc_timeout", 5000L);
                this.GC_MIN_INTERVAL = this.mParser.getLong("gc_min_interval", 60000L);
                this.FULL_PSS_MIN_INTERVAL =
                        this.mParser.getLong("full_pss_min_interval", 1200000L);
                this.FULL_PSS_LOWERED_INTERVAL =
                        this.mParser.getLong("full_pss_lowered_interval", 300000L);
                this.POWER_CHECK_INTERVAL = this.mParser.getLong("power_check_interval", 300000L);
                this.POWER_CHECK_MAX_CPU_1 = this.mParser.getInt("power_check_max_cpu_1", 25);
                this.POWER_CHECK_MAX_CPU_2 = this.mParser.getInt("power_check_max_cpu_2", 25);
                this.POWER_CHECK_MAX_CPU_3 = this.mParser.getInt("power_check_max_cpu_3", 10);
                this.POWER_CHECK_MAX_CPU_4 = this.mParser.getInt("power_check_max_cpu_4", 2);
                this.SERVICE_USAGE_INTERACTION_TIME_PRE_S =
                        this.mParser.getLong("service_usage_interaction_time", 1800000L);
                this.SERVICE_USAGE_INTERACTION_TIME_POST_S =
                        this.mParser.getLong("service_usage_interaction_time_post_s", 60000L);
                this.USAGE_STATS_INTERACTION_INTERVAL_PRE_S =
                        this.mParser.getLong("usage_stats_interaction_interval", 7200000L);
                this.USAGE_STATS_INTERACTION_INTERVAL_POST_S =
                        this.mParser.getLong("usage_stats_interaction_interval_post_s", 600000L);
                this.SERVICE_RESTART_DURATION =
                        this.mParser.getLong("service_restart_duration", 1000L);
                this.SERVICE_RESET_RUN_DURATION =
                        this.mParser.getLong("service_reset_run_duration", 60000L);
                this.SERVICE_RESTART_DURATION_FACTOR =
                        this.mParser.getInt("service_restart_duration_factor", 4);
                this.SERVICE_MIN_RESTART_TIME_BETWEEN =
                        this.mParser.getLong("service_min_restart_time_between", 10000L);
                this.MAX_SERVICE_INACTIVITY =
                        this.mParser.getLong("service_max_inactivity", 1800000L);
                this.BG_START_TIMEOUT = this.mParser.getLong("service_bg_start_timeout", 15000L);
                this.SERVICE_BG_ACTIVITY_START_TIMEOUT =
                        this.mParser.getLong("service_bg_activity_start_timeout", 10000L);
                this.BOUND_SERVICE_CRASH_RESTART_DURATION =
                        this.mParser.getLong("service_crash_restart_duration", 1800000L);
                this.BOUND_SERVICE_MAX_CRASH_RETRY =
                        this.mParser.getInt("service_crash_max_retry", 16);
                this.FLAG_PROCESS_START_ASYNC =
                        this.mParser.getBoolean("process_start_async", true);
                this.MEMORY_INFO_THROTTLE_TIME =
                        this.mParser.getLong("memory_info_throttle_time", 300000L);
                this.TOP_TO_ALMOST_PERCEPTIBLE_GRACE_DURATION =
                        this.mParser.getDurationMillis(
                                "top_to_almost_perceptible_grace_duration", 15000L);
                MIN_CRASH_INTERVAL = this.mParser.getInt("min_crash_interval", 120000);
                this.PENDINGINTENT_WARNING_THRESHOLD =
                        this.mParser.getInt("pendingintent_warning_threshold", 2000);
                PROCESS_CRASH_COUNT_RESET_INTERVAL =
                        this.mParser.getInt("process_crash_count_reset_interval", 43200000);
                PROCESS_CRASH_COUNT_LIMIT = this.mParser.getInt("process_crash_count_limit", 12);
                if (this.POWER_CHECK_INTERVAL != j) {
                    this.mService.mHandler.removeMessages(27);
                    this.mService.mHandler.sendMessageDelayed(
                            this.mService.mHandler.obtainMessage(27), this.POWER_CHECK_INTERVAL);
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public final void updateEnableAutomaticSystemServerHeapDumps() {
        if (this.mSystemServerAutomaticHeapDumpEnabled) {
            this.mService.setDumpHeapDebugLimit(
                    null,
                    0,
                    Settings.Global.getInt(
                                            this.mResolver,
                                            "enable_automatic_system_server_heap_dumps",
                                            1)
                                    == 1
                            ? this.mSystemServerAutomaticHeapDumpPssThresholdBytes
                            : 0L,
                    this.mSystemServerAutomaticHeapDumpPackageName);
        } else {
            Slog.wtf(
                    "ActivityManagerConstants",
                    "updateEnableAutomaticSystemServerHeapDumps called when leak detection"
                        + " disabled");
        }
    }

    public final void updateMaxCachedProcesses() {
        String property = DeviceConfig.getProperty("activity_manager", "max_cached_processes");
        try {
            int i = this.mOverrideMaxCachedProcesses;
            if (i < 0) {
                i =
                        TextUtils.isEmpty(property)
                                ? DEFAULT_MAX_CACHED_PROCESSES
                                : Integer.parseInt(property);
            }
            this.CUR_MAX_CACHED_PROCESSES = i;
        } catch (NumberFormatException e) {
            Slog.e(
                    "ActivityManagerConstants",
                    "Unable to parse flag for max_cached_processes: " + property,
                    e);
            this.CUR_MAX_CACHED_PROCESSES = DEFAULT_MAX_CACHED_PROCESSES;
        }
        this.CUR_MAX_EMPTY_PROCESSES = computeEmptyProcessLimit(this.CUR_MAX_CACHED_PROCESSES);
        int computeEmptyProcessLimit =
                computeEmptyProcessLimit(
                        Integer.min(this.CUR_MAX_CACHED_PROCESSES, this.MAX_CACHED_PROCESSES));
        this.CUR_TRIM_EMPTY_PROCESSES = computeEmptyProcessLimit / 2;
        this.CUR_TRIM_CACHED_PROCESSES =
                (Integer.min(this.CUR_MAX_CACHED_PROCESSES, this.MAX_CACHED_PROCESSES)
                                - computeEmptyProcessLimit)
                        / 3;
        int i2 = this.CUSTOM_CUR_TRIM_EMPTY_PROCESSES;
        if (i2 != 0) {
            this.CUR_TRIM_EMPTY_PROCESSES = i2;
        }
        int i3 = this.CUSTOM_CUR_TRIM_CACHED_PROCESSES;
        if (i3 != 0) {
            this.CUR_TRIM_CACHED_PROCESSES = i3;
        }
    }
}

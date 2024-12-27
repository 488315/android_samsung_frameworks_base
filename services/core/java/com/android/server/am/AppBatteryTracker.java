package com.android.server.am;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.BatteryConsumer;
import android.os.BatteryStatsInternal;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.MessageQueue;
import android.os.PowerExemptionManager;
import android.os.SystemClock;
import android.os.UidBatteryConsumer;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.ArraySet;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseLongArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.pm.UserManagerInternal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public final class AppBatteryTracker extends BaseAppStateTracker {
    public static final ImmutableBatteryUsage BATTERY_USAGE_NONE = new ImmutableBatteryUsage();
    public final SparseBooleanArray mActiveUserIdStates;
    public final long mBatteryUsageStatsPollingIntervalMs;
    public final long mBatteryUsageStatsPollingMinIntervalMs;
    public boolean mBatteryUsageStatsUpdatePending;
    public final AppBatteryTracker$$ExternalSyntheticLambda0 mBgBatteryUsageStatsCheck;
    public final AppBatteryTracker$$ExternalSyntheticLambda0 mBgBatteryUsageStatsPolling;
    public final SparseArray mDebugUidPercentages;
    public long mLastBatteryUsageSamplingTs;
    public long mLastReportTime;
    public final SparseArray mLastUidBatteryUsage;
    public long mLastUidBatteryUsageStartTs;
    public final SparseArray mTmpUidBatteryUsage;
    public final SparseArray mTmpUidBatteryUsage2;
    public final SparseArray mTmpUidBatteryUsageInWindow;
    public final ArraySet mTmpUserIds;
    public final SparseArray mUidBatteryUsage;
    public final SparseArray mUidBatteryUsageInWindow;

    public final class AppBatteryPolicy extends BaseAppStatePolicy {
        public volatile BatteryConsumer.Dimensions[] mBatteryDimensions;
        public int mBatteryFullChargeMah;
        public volatile boolean mBgCurrentDrainAutoRestrictAbusiveAppsEnabled;
        public volatile float[] mBgCurrentDrainBgRestrictedThreshold;
        public volatile int mBgCurrentDrainBgRestrictedTypes;
        public volatile boolean mBgCurrentDrainDecoupleThresholds;
        public volatile boolean mBgCurrentDrainEventDurationBasedThresholdEnabled;
        public volatile int mBgCurrentDrainExemptedTypes;
        public volatile boolean mBgCurrentDrainHighThresholdByBgLocation;
        public volatile long mBgCurrentDrainInteractionGracePeriodMs;
        public volatile long mBgCurrentDrainLocationMinDuration;
        public volatile long mBgCurrentDrainMediaPlaybackMinDuration;
        public volatile int mBgCurrentDrainPowerComponents;
        public volatile float[] mBgCurrentDrainRestrictedBucketThreshold;
        public volatile int mBgCurrentDrainRestrictedBucketTypes;
        public volatile long mBgCurrentDrainWindowMs;
        public final boolean mDefaultBgCurrentDrainAutoRestrictAbusiveAppsEnabled;
        public final float mDefaultBgCurrentDrainBgRestrictedHighThreshold;
        public final float mDefaultBgCurrentDrainBgRestrictedThreshold;
        public final boolean mDefaultBgCurrentDrainEventDurationBasedThresholdEnabled;
        public final int mDefaultBgCurrentDrainExemptedTypes;
        public final boolean mDefaultBgCurrentDrainHighThresholdByBgLocation;
        public final long mDefaultBgCurrentDrainInteractionGracePeriodMs;
        public final long mDefaultBgCurrentDrainLocationMinDuration;
        public final long mDefaultBgCurrentDrainMediaPlaybackMinDuration;
        public final int mDefaultBgCurrentDrainPowerComponent;
        public final float mDefaultBgCurrentDrainRestrictedBucket;
        public final float mDefaultBgCurrentDrainRestrictedBucketHighThreshold;
        public final int mDefaultBgCurrentDrainTypesToBgRestricted;
        public final long mDefaultBgCurrentDrainWindowMs;
        public final int mDefaultCurrentDrainTypesToRestrictedBucket;
        public final SparseArray mHighBgBatteryPackages;
        public final SparseLongArray mLastInteractionTime;
        public final Object mLock;

        public AppBatteryPolicy(
                BaseAppStateTracker.Injector injector, AppBatteryTracker appBatteryTracker) {
            super(
                    injector,
                    appBatteryTracker,
                    "bg_current_drain_monitor_enabled",
                    appBatteryTracker
                            .mContext
                            .getResources()
                            .getBoolean(R.bool.config_biometricFrrNotificationEnabled));
            this.mBgCurrentDrainRestrictedBucketThreshold = new float[2];
            this.mBgCurrentDrainBgRestrictedThreshold = new float[2];
            this.mHighBgBatteryPackages = new SparseArray();
            this.mLastInteractionTime = new SparseLongArray();
            this.mLock = appBatteryTracker.mLock;
            Resources resources = appBatteryTracker.mContext.getResources();
            float[] floatArray =
                    getFloatArray(
                            resources.obtainTypedArray(
                                    R.array.config_primaryCredentialProviderService));
            float f = ActivityManager.isLowRamDeviceStatic() ? floatArray[1] : floatArray[0];
            this.mDefaultBgCurrentDrainRestrictedBucket = f;
            float[] floatArray2 =
                    getFloatArray(
                            resources.obtainTypedArray(
                                    R.array.config_perDeviceStateRotationLockDefaults));
            float f2 = ActivityManager.isLowRamDeviceStatic() ? floatArray2[1] : floatArray2[0];
            this.mDefaultBgCurrentDrainBgRestrictedThreshold = f2;
            long integer = resources.getInteger(R.integer.config_datause_throttle_kbitsps) * 1000;
            this.mDefaultBgCurrentDrainWindowMs = integer;
            this.mDefaultBgCurrentDrainInteractionGracePeriodMs = integer;
            float[] floatArray3 =
                    getFloatArray(
                            resources.obtainTypedArray(
                                    R.array.config_packagesExemptFromSuspension));
            float f3 = ActivityManager.isLowRamDeviceStatic() ? floatArray3[1] : floatArray3[0];
            this.mDefaultBgCurrentDrainRestrictedBucketHighThreshold = f3;
            float[] floatArray4 =
                    getFloatArray(resources.obtainTypedArray(R.array.config_openDeviceStates));
            float f4 = ActivityManager.isLowRamDeviceStatic() ? floatArray4[1] : floatArray4[0];
            this.mDefaultBgCurrentDrainBgRestrictedHighThreshold = f4;
            long integer2 =
                    resources.getInteger(
                                    R.integer
                                            .config_datagram_wait_for_connected_state_timeout_millis)
                            * 1000;
            this.mDefaultBgCurrentDrainMediaPlaybackMinDuration = integer2;
            long integer3 =
                    resources.getInteger(R.integer.config_customizedMaxCachedProcesses) * 1000;
            this.mDefaultBgCurrentDrainLocationMinDuration = integer3;
            this.mDefaultBgCurrentDrainEventDurationBasedThresholdEnabled =
                    resources.getBoolean(R.bool.config_bg_prompt_abusive_apps_to_bg_restricted);
            this.mDefaultBgCurrentDrainAutoRestrictAbusiveAppsEnabled =
                    resources.getBoolean(R.bool.config_bg_current_drain_monitor_enabled);
            this.mDefaultCurrentDrainTypesToRestrictedBucket =
                    resources.getInteger(R.integer.config_datause_threshold_bytes);
            this.mDefaultBgCurrentDrainTypesToBgRestricted =
                    resources.getInteger(R.integer.config_datause_polling_period_sec);
            this.mDefaultBgCurrentDrainPowerComponent =
                    resources.getInteger(R.integer.config_datause_notification_type);
            this.mDefaultBgCurrentDrainExemptedTypes =
                    resources.getInteger(R.integer.config_cursorWindowSize);
            this.mDefaultBgCurrentDrainHighThresholdByBgLocation =
                    resources.getBoolean(R.bool.config_bg_prompt_fgs_with_noti_to_bg_restricted);
            this.mBgCurrentDrainRestrictedBucketThreshold[0] = f;
            this.mBgCurrentDrainRestrictedBucketThreshold[1] = f3;
            this.mBgCurrentDrainBgRestrictedThreshold[0] = f2;
            this.mBgCurrentDrainBgRestrictedThreshold[1] = f4;
            this.mBgCurrentDrainWindowMs = integer;
            this.mBgCurrentDrainInteractionGracePeriodMs = integer;
            this.mBgCurrentDrainMediaPlaybackMinDuration = integer2;
            this.mBgCurrentDrainLocationMinDuration = integer3;
        }

        public static String batteryUsageTypesToString(int i) {
            StringBuilder sb = new StringBuilder("[");
            int highestOneBit = Integer.highestOneBit(i);
            boolean z = false;
            while (highestOneBit != 0) {
                if (z) {
                    sb.append('|');
                }
                z = true;
                if (highestOneBit == 1) {
                    sb.append("UNSPECIFIED");
                } else if (highestOneBit == 2) {
                    sb.append("FOREGROUND");
                } else if (highestOneBit == 4) {
                    sb.append("BACKGROUND");
                } else if (highestOneBit == 8) {
                    sb.append("FOREGROUND_SERVICE");
                } else {
                    if (highestOneBit != 16) {
                        return "[UNKNOWN(" + Integer.toHexString(i) + ")]";
                    }
                    sb.append("CACHED");
                }
                i &= ~highestOneBit;
                highestOneBit = Integer.highestOneBit(i);
            }
            sb.append("]");
            return sb.toString();
        }

        public static String formatHighBgBatteryRecord(
                long j, long j2, ImmutableBatteryUsage immutableBatteryUsage) {
            if (j <= 0 || immutableBatteryUsage == null) {
                return "0";
            }
            return TimeUtils.formatTime(j, j2)
                    + " "
                    + immutableBatteryUsage.toString()
                    + " ("
                    + immutableBatteryUsage.percentageToString()
                    + ")";
        }

        public static float[] getFloatArray(TypedArray typedArray) {
            int length = typedArray.length();
            float[] fArr = new float[length];
            for (int i = 0; i < length; i++) {
                fArr[i] = typedArray.getFloat(i, Float.NaN);
            }
            typedArray.recycle();
            return fArr;
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public final void dump(PrintWriter printWriter, String str) {
            String sb;
            printWriter.print(str);
            printWriter.println("APP BATTERY TRACKER POLICY SETTINGS:");
            String str2 = "  " + str;
            super.dump(printWriter, str2);
            if (this.mTrackerEnabled) {
                printWriter.print(str2);
                printWriter.print("bg_current_drain_threshold_to_restricted_bucket");
                printWriter.print('=');
                char c = 0;
                printWriter.println(this.mBgCurrentDrainRestrictedBucketThreshold[0]);
                printWriter.print(str2);
                printWriter.print("bg_current_drain_high_threshold_to_restricted_bucket");
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainRestrictedBucketThreshold[1]);
                printWriter.print(str2);
                printWriter.print("bg_current_drain_threshold_to_bg_restricted");
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainBgRestrictedThreshold[0]);
                printWriter.print(str2);
                printWriter.print("bg_current_drain_high_threshold_to_bg_restricted");
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainBgRestrictedThreshold[1]);
                printWriter.print(str2);
                printWriter.print("bg_current_drain_window");
                printWriter.print('=');
                ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                        this.mBgCurrentDrainWindowMs,
                        printWriter,
                        str2,
                        "bg_current_drain_interaction_grace_period");
                printWriter.print('=');
                ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                        this.mBgCurrentDrainInteractionGracePeriodMs,
                        printWriter,
                        str2,
                        "bg_current_drain_media_playback_min_duration");
                printWriter.print('=');
                ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                        this.mBgCurrentDrainMediaPlaybackMinDuration,
                        printWriter,
                        str2,
                        "bg_current_drain_location_min_duration");
                printWriter.print('=');
                ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                        this.mBgCurrentDrainLocationMinDuration,
                        printWriter,
                        str2,
                        "bg_current_drain_event_duration_based_threshold_enabled");
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainEventDurationBasedThresholdEnabled);
                printWriter.print(str2);
                printWriter.print("bg_current_drain_auto_restrict_abusive_apps_enabled");
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainAutoRestrictAbusiveAppsEnabled);
                printWriter.print(str2);
                printWriter.print("bg_current_drain_types_to_restricted_bucket");
                printWriter.print('=');
                printWriter.println(
                        batteryUsageTypesToString(this.mBgCurrentDrainRestrictedBucketTypes));
                printWriter.print(str2);
                printWriter.print("bg_current_drain_types_to_bg_restricted");
                printWriter.print('=');
                printWriter.println(
                        batteryUsageTypesToString(this.mBgCurrentDrainBgRestrictedTypes));
                printWriter.print(str2);
                printWriter.print("bg_current_drain_power_components");
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainPowerComponents);
                printWriter.print(str2);
                printWriter.print("bg_current_drain_exempted_types");
                printWriter.print('=');
                int i = this.mBgCurrentDrainExemptedTypes;
                StringBuilder sb2 = new StringBuilder("[");
                int highestOneBit = Integer.highestOneBit(i);
                boolean z = false;
                while (true) {
                    if (highestOneBit == 0) {
                        sb2.append("]");
                        sb = sb2.toString();
                        break;
                    }
                    if (z) {
                        sb2.append('|');
                    }
                    if (highestOneBit == 1) {
                        sb2.append("MEDIA_SESSION");
                    } else if (highestOneBit == 2) {
                        sb2.append("FGS_MEDIA_PLAYBACK");
                    } else if (highestOneBit == 4) {
                        sb2.append("FGS_LOCATION");
                    } else if (highestOneBit == 8) {
                        sb2.append("FGS_NOTIFICATION");
                    } else {
                        if (highestOneBit != 16) {
                            sb = "[UNKNOWN(" + Integer.toHexString(i) + ")]";
                            break;
                        }
                        sb2.append("PERMISSION");
                    }
                    i &= ~highestOneBit;
                    highestOneBit = Integer.highestOneBit(i);
                    z = true;
                }
                printWriter.println(sb);
                printWriter.print(str2);
                printWriter.print("bg_current_drain_high_threshold_by_bg_location");
                printWriter.print('=');
                AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                        printWriter,
                        str2,
                        "Full charge capacity=",
                        this.mBgCurrentDrainHighThresholdByBgLocation);
                printWriter.print(this.mBatteryFullChargeMah);
                printWriter.println(" mAh");
                printWriter.print(str2);
                printWriter.println("Excessive current drain detected:");
                synchronized (this.mLock) {
                    try {
                        int size = this.mHighBgBatteryPackages.size();
                        String str3 = "  " + str2;
                        if (size > 0) {
                            long elapsedRealtime = SystemClock.elapsedRealtime();
                            int i2 = 0;
                            while (i2 < size) {
                                int keyAt = this.mHighBgBatteryPackages.keyAt(i2);
                                Pair pair = (Pair) this.mHighBgBatteryPackages.valueAt(i2);
                                long[] jArr = (long[]) pair.first;
                                ImmutableBatteryUsage[] immutableBatteryUsageArr =
                                        (ImmutableBatteryUsage[]) pair.second;
                                int currentDrainThresholdIndex =
                                        getCurrentDrainThresholdIndex(
                                                keyAt,
                                                elapsedRealtime,
                                                this.mBgCurrentDrainWindowMs);
                                printWriter.format(
                                        "%s%s: (threshold=%4.2f%%/%4.2f%%) %s / %s\n",
                                        str3,
                                        UserHandle.formatUid(keyAt),
                                        Float.valueOf(
                                                this.mBgCurrentDrainRestrictedBucketThreshold[
                                                        currentDrainThresholdIndex]),
                                        Float.valueOf(
                                                this.mBgCurrentDrainBgRestrictedThreshold[
                                                        currentDrainThresholdIndex]),
                                        formatHighBgBatteryRecord(
                                                jArr[c],
                                                elapsedRealtime,
                                                immutableBatteryUsageArr[c]),
                                        formatHighBgBatteryRecord(
                                                jArr[1],
                                                elapsedRealtime,
                                                immutableBatteryUsageArr[1]));
                                i2++;
                                str3 = str3;
                                elapsedRealtime = elapsedRealtime;
                                c = 0;
                            }
                        } else {
                            printWriter.print(str3);
                            printWriter.println("(none)");
                        }
                    } finally {
                    }
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:4:0x003e, code lost:

           if (java.lang.Math.max(r10.mInjector.mAppMediaSessionTracker.getTotalDurationsSince(r16, 0, r11, r17), r10.mInjector.mAppFGSTracker.getTotalDurationsSince(r16, com.android.server.am.AppFGSTracker.foregroundServiceTypeToIndex(2), r11, r17)) >= r15.mBgCurrentDrainMediaPlaybackMinDuration) goto L18;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int getCurrentDrainThresholdIndex(int r16, long r17, long r19) {
            /*
                r15 = this;
                r0 = r15
                boolean r1 = r0.mBgCurrentDrainEventDurationBasedThresholdEnabled
                r8 = 0
                if (r1 == 0) goto L41
                com.android.server.am.BaseAppStateTracker r1 = r0.mTracker
                com.android.server.am.AppBatteryTracker r1 = (com.android.server.am.AppBatteryTracker) r1
                com.android.server.am.AppRestrictionController r10 = r1.mAppRestrictionController
                r10.getClass()
                long r1 = r17 - r19
                long r11 = java.lang.Math.max(r8, r1)
                com.android.server.am.AppRestrictionController$Injector r1 = r10.mInjector
                com.android.server.am.AppMediaSessionTracker r1 = r1.mAppMediaSessionTracker
                r3 = 0
                r2 = r16
                r4 = r11
                r6 = r17
                long r13 = r1.getTotalDurationsSince(r2, r3, r4, r6)
                com.android.server.am.AppRestrictionController$Injector r1 = r10.mInjector
                com.android.server.am.AppFGSTracker r1 = r1.mAppFGSTracker
                r2 = 2
                int r3 = com.android.server.am.AppFGSTracker.foregroundServiceTypeToIndex(r2)
                r2 = r16
                r4 = r11
                r6 = r17
                long r1 = r1.getTotalDurationsSince(r2, r3, r4, r6)
                long r1 = java.lang.Math.max(r13, r1)
                long r3 = r0.mBgCurrentDrainMediaPlaybackMinDuration
                int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                if (r1 < 0) goto L41
                goto L84
            L41:
                boolean r1 = r0.mBgCurrentDrainHighThresholdByBgLocation
                if (r1 != 0) goto L46
                goto L86
            L46:
                com.android.server.am.BaseAppStateTracker r1 = r0.mTracker
                com.android.server.am.AppBatteryTracker r1 = (com.android.server.am.AppBatteryTracker) r1
                com.android.server.am.BaseAppStateTracker$Injector r1 = r1.mInjector
                android.content.Context r1 = r1.mContext
                java.lang.String r2 = "android.permission.ACCESS_BACKGROUND_LOCATION"
                r3 = -1
                r4 = r16
                int r1 = r1.checkPermission(r2, r3, r4)
                if (r1 != 0) goto L5a
                goto L84
            L5a:
                boolean r1 = r0.mBgCurrentDrainEventDurationBasedThresholdEnabled
                if (r1 != 0) goto L5f
                goto L86
            L5f:
                long r1 = r17 - r19
                long r5 = java.lang.Math.max(r8, r1)
                com.android.server.am.BaseAppStateTracker r1 = r0.mTracker
                com.android.server.am.AppBatteryTracker r1 = (com.android.server.am.AppBatteryTracker) r1
                com.android.server.am.AppRestrictionController r1 = r1.mAppRestrictionController
                com.android.server.am.AppRestrictionController$Injector r1 = r1.mInjector
                com.android.server.am.AppFGSTracker r1 = r1.mAppFGSTracker
                r2 = 8
                int r3 = com.android.server.am.AppFGSTracker.foregroundServiceTypeToIndex(r2)
                r2 = r16
                r4 = r5
                r6 = r17
                long r1 = r1.getTotalDurationsSince(r2, r3, r4, r6)
                long r3 = r0.mBgCurrentDrainLocationMinDuration
                int r0 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                if (r0 < 0) goto L86
            L84:
                r0 = 1
                goto L87
            L86:
                r0 = 0
            L87:
                return r0
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.am.AppBatteryTracker.AppBatteryPolicy.getCurrentDrainThresholdIndex(int,"
                        + " long, long):int");
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public final int getProposedRestrictionLevel(int i, int i2, String str) {
            boolean z = false;
            if (i2 <= 30) {
                return 0;
            }
            synchronized (this.mLock) {
                try {
                    Pair pair = (Pair) this.mHighBgBatteryPackages.get(i);
                    if (pair != null) {
                        long j = this.mLastInteractionTime.get(i, 0L);
                        long[] jArr = (long[]) pair.first;
                        boolean z2 = jArr[0] > j + this.mBgCurrentDrainInteractionGracePeriodMs;
                        if (((AppBatteryTracker) this.mTracker)
                                        .mAppRestrictionController
                                        .mConstantsObserver
                                        .mBgAutoRestrictAbusiveApps
                                && this.mBgCurrentDrainAutoRestrictAbusiveAppsEnabled) {
                            z = true;
                        }
                        int i3 = (z2 && z) ? 40 : 30;
                        if (i2 > 50) {
                            if (jArr[1] > 0) {
                                i3 = 50;
                            }
                            return i3;
                        }
                        if (i2 == 50) {
                            return i3;
                        }
                    }
                    return 30;
                } finally {
                }
            }
        }

        public final void handleUidBatteryUsage(
                int i, ImmutableBatteryUsage immutableBatteryUsage) {
            boolean z;
            boolean z2;
            long[] jArr;
            ImmutableBatteryUsage[] immutableBatteryUsageArr;
            if (this.mTracker.mAppRestrictionController.getBackgroundRestrictionExemptionReason(i)
                    != -1) {
                return;
            }
            double[] dArr = immutableBatteryUsage.mPercentage;
            int i2 = this.mBgCurrentDrainRestrictedBucketTypes;
            int highestOneBit = Integer.highestOneBit(i2);
            double d = 0.0d;
            while (highestOneBit != 0) {
                d += dArr[Integer.numberOfTrailingZeros(highestOneBit)];
                i2 &= ~highestOneBit;
                highestOneBit = Integer.highestOneBit(i2);
            }
            double[] dArr2 = immutableBatteryUsage.mPercentage;
            int i3 = this.mBgCurrentDrainBgRestrictedTypes;
            int highestOneBit2 = Integer.highestOneBit(i3);
            double d2 = 0.0d;
            while (highestOneBit2 != 0) {
                d2 += dArr2[Integer.numberOfTrailingZeros(highestOneBit2)];
                i3 &= ~highestOneBit2;
                highestOneBit2 = Integer.highestOneBit(i3);
            }
            synchronized (this.mLock) {
                try {
                    int restrictionLevel =
                            ((AppBatteryTracker) this.mTracker)
                                    .mAppRestrictionController.mRestrictionSettings
                                            .getRestrictionLevel(i);
                    if (restrictionLevel >= 50) {
                        return;
                    }
                    long j = this.mLastInteractionTime.get(i, 0L);
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    int currentDrainThresholdIndex =
                            getCurrentDrainThresholdIndex(
                                    i, elapsedRealtime, this.mBgCurrentDrainWindowMs);
                    int indexOfKey = this.mHighBgBatteryPackages.indexOfKey(i);
                    boolean z3 = this.mBgCurrentDrainDecoupleThresholds;
                    double d3 =
                            this.mBgCurrentDrainRestrictedBucketThreshold[
                                    currentDrainThresholdIndex];
                    double d4 = d2;
                    double d5 =
                            this.mBgCurrentDrainBgRestrictedThreshold[currentDrainThresholdIndex];
                    boolean z4 = false;
                    if (indexOfKey < 0) {
                        if (d >= d3) {
                            if (elapsedRealtime
                                    > j + this.mBgCurrentDrainInteractionGracePeriodMs) {
                                jArr = new long[] {elapsedRealtime, 0};
                                immutableBatteryUsageArr = new ImmutableBatteryUsage[2];
                                immutableBatteryUsageArr[0] = immutableBatteryUsage;
                                this.mHighBgBatteryPackages.put(
                                        i, Pair.create(jArr, immutableBatteryUsageArr));
                                z4 = true;
                            } else {
                                jArr = null;
                                immutableBatteryUsageArr = null;
                            }
                            z2 = true;
                        } else {
                            z2 = false;
                            jArr = null;
                            immutableBatteryUsageArr = null;
                        }
                        if (z3 && d4 >= d5) {
                            if (jArr == null) {
                                jArr = new long[2];
                                immutableBatteryUsageArr = new ImmutableBatteryUsage[2];
                                this.mHighBgBatteryPackages.put(
                                        i, Pair.create(jArr, immutableBatteryUsageArr));
                            }
                            jArr[1] = elapsedRealtime;
                            immutableBatteryUsageArr[1] = immutableBatteryUsage;
                            z2 = true;
                            z4 = true;
                        }
                    } else {
                        Pair pair = (Pair) this.mHighBgBatteryPackages.valueAt(indexOfKey);
                        long[] jArr2 = (long[]) pair.first;
                        long j2 = jArr2[0];
                        if (d >= d3) {
                            if (elapsedRealtime
                                    > j + this.mBgCurrentDrainInteractionGracePeriodMs) {
                                if (j2 == 0) {
                                    jArr2[0] = elapsedRealtime;
                                    ((ImmutableBatteryUsage[]) pair.second)[0] =
                                            immutableBatteryUsage;
                                }
                                z = true;
                            } else {
                                z = false;
                            }
                            z2 = true;
                        } else {
                            z = false;
                            z2 = false;
                        }
                        if (d4 >= d5) {
                            if (z3
                                    || (restrictionLevel == 40
                                            && elapsedRealtime
                                                    > j2 + this.mBgCurrentDrainWindowMs)) {
                                z4 = true;
                            }
                            if (z4) {
                                jArr2[1] = elapsedRealtime;
                                ((ImmutableBatteryUsage[]) pair.second)[1] = immutableBatteryUsage;
                            }
                            z2 = true;
                        } else {
                            jArr2[1] = 0;
                            ((ImmutableBatteryUsage[]) pair.second)[1] = null;
                            z4 = z;
                        }
                    }
                    if (z2 && z4) {
                        ((AppBatteryTracker) this.mTracker)
                                .mAppRestrictionController.refreshAppRestrictionLevelForUid(
                                        i,
                                        FrameworkStatsLog
                                                .APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM,
                                        2,
                                        true);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public final void onPropertiesChanged(String str) {
            switch (str) {
                case "bg_current_drain_event_duration_based_threshold_enabled":
                    this.mBgCurrentDrainEventDurationBasedThresholdEnabled =
                            DeviceConfig.getBoolean(
                                    "activity_manager",
                                    "bg_current_drain_event_duration_based_threshold_enabled",
                                    this.mDefaultBgCurrentDrainEventDurationBasedThresholdEnabled);
                    break;
                case "bg_current_drain_decouple_thresholds":
                    this.mBgCurrentDrainDecoupleThresholds =
                            DeviceConfig.getBoolean(
                                    "activity_manager",
                                    "bg_current_drain_decouple_thresholds",
                                    true);
                    break;
                case "bg_current_drain_media_playback_min_duration":
                    this.mBgCurrentDrainMediaPlaybackMinDuration =
                            DeviceConfig.getLong(
                                    "activity_manager",
                                    "bg_current_drain_media_playback_min_duration",
                                    this.mDefaultBgCurrentDrainMediaPlaybackMinDuration);
                    break;
                case "bg_current_drain_power_components":
                case "bg_current_drain_high_threshold_to_restricted_bucket":
                case "bg_current_drain_high_threshold_to_bg_restricted":
                case "bg_current_drain_high_threshold_by_bg_location":
                case "bg_current_drain_types_to_restricted_bucket":
                case "bg_current_drain_types_to_bg_restricted":
                case "bg_current_drain_threshold_to_bg_restricted":
                case "bg_current_drain_threshold_to_restricted_bucket":
                    updateCurrentDrainThreshold();
                    break;
                case "bg_current_drain_location_min_duration":
                    this.mBgCurrentDrainLocationMinDuration =
                            DeviceConfig.getLong(
                                    "activity_manager",
                                    "bg_current_drain_location_min_duration",
                                    this.mDefaultBgCurrentDrainLocationMinDuration);
                    break;
                case "bg_current_drain_auto_restrict_abusive_apps_enabled":
                    this.mBgCurrentDrainAutoRestrictAbusiveAppsEnabled =
                            DeviceConfig.getBoolean(
                                    "activity_manager",
                                    "bg_current_drain_auto_restrict_abusive_apps_enabled",
                                    this.mDefaultBgCurrentDrainAutoRestrictAbusiveAppsEnabled);
                    break;
                case "bg_current_drain_interaction_grace_period":
                    this.mBgCurrentDrainInteractionGracePeriodMs =
                            DeviceConfig.getLong(
                                    "activity_manager",
                                    "bg_current_drain_interaction_grace_period",
                                    this.mDefaultBgCurrentDrainInteractionGracePeriodMs);
                    break;
                case "bg_current_drain_exempted_types":
                    this.mBgCurrentDrainExemptedTypes =
                            DeviceConfig.getInt(
                                    "activity_manager",
                                    "bg_current_drain_exempted_types",
                                    this.mDefaultBgCurrentDrainExemptedTypes);
                    break;
                case "bg_current_drain_window":
                    this.mBgCurrentDrainWindowMs =
                            DeviceConfig.getLong(
                                    "activity_manager",
                                    "bg_current_drain_window",
                                    this.mDefaultBgCurrentDrainWindowMs);
                    break;
                default:
                    if (this.mKeyTrackerEnabled.equals(str)) {
                        updateTrackerEnabled();
                        break;
                    }
                    break;
            }
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public final void onSystemReady() {
            this.mBatteryFullChargeMah =
                    this.mInjector.mBatteryManagerInternal.getBatteryFullCharge() / 1000;
            updateTrackerEnabled();
            updateCurrentDrainThreshold();
            this.mBgCurrentDrainWindowMs =
                    DeviceConfig.getLong(
                            "activity_manager",
                            "bg_current_drain_window",
                            this.mDefaultBgCurrentDrainWindowMs);
            this.mBgCurrentDrainInteractionGracePeriodMs =
                    DeviceConfig.getLong(
                            "activity_manager",
                            "bg_current_drain_interaction_grace_period",
                            this.mDefaultBgCurrentDrainInteractionGracePeriodMs);
            this.mBgCurrentDrainMediaPlaybackMinDuration =
                    DeviceConfig.getLong(
                            "activity_manager",
                            "bg_current_drain_media_playback_min_duration",
                            this.mDefaultBgCurrentDrainMediaPlaybackMinDuration);
            this.mBgCurrentDrainLocationMinDuration =
                    DeviceConfig.getLong(
                            "activity_manager",
                            "bg_current_drain_location_min_duration",
                            this.mDefaultBgCurrentDrainLocationMinDuration);
            this.mBgCurrentDrainEventDurationBasedThresholdEnabled =
                    DeviceConfig.getBoolean(
                            "activity_manager",
                            "bg_current_drain_event_duration_based_threshold_enabled",
                            this.mDefaultBgCurrentDrainEventDurationBasedThresholdEnabled);
            this.mBgCurrentDrainExemptedTypes =
                    DeviceConfig.getInt(
                            "activity_manager",
                            "bg_current_drain_exempted_types",
                            this.mDefaultBgCurrentDrainExemptedTypes);
            this.mBgCurrentDrainDecoupleThresholds =
                    DeviceConfig.getBoolean(
                            "activity_manager", "bg_current_drain_decouple_thresholds", true);
            this.mBgCurrentDrainAutoRestrictAbusiveAppsEnabled =
                    DeviceConfig.getBoolean(
                            "activity_manager",
                            "bg_current_drain_auto_restrict_abusive_apps_enabled",
                            this.mDefaultBgCurrentDrainAutoRestrictAbusiveAppsEnabled);
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public final void onTrackerEnabled(boolean z) {
            AppBatteryTracker appBatteryTracker = (AppBatteryTracker) this.mTracker;
            if (z) {
                if (appBatteryTracker.mBgHandler.hasCallbacks(
                        appBatteryTracker.mBgBatteryUsageStatsPolling)) {
                    return;
                }
                appBatteryTracker.mBgHandler.postDelayed(
                        appBatteryTracker.mBgBatteryUsageStatsPolling,
                        appBatteryTracker.mBatteryUsageStatsPollingIntervalMs);
                return;
            }
            appBatteryTracker.mBgHandler.removeCallbacks(
                    appBatteryTracker.mBgBatteryUsageStatsPolling);
            synchronized (appBatteryTracker.mLock) {
                if (appBatteryTracker.mBatteryUsageStatsUpdatePending) {
                    try {
                        appBatteryTracker.mLock.wait();
                    } catch (InterruptedException unused) {
                    }
                }
                appBatteryTracker.mUidBatteryUsage.clear();
                appBatteryTracker.mUidBatteryUsageInWindow.clear();
                appBatteryTracker.mLastUidBatteryUsage.clear();
                appBatteryTracker.mLastBatteryUsageSamplingTs = 0L;
                appBatteryTracker.mLastUidBatteryUsageStartTs = 0L;
            }
        }

        public void reset() {
            this.mHighBgBatteryPackages.clear();
            this.mLastInteractionTime.clear();
            ((AppBatteryTracker) this.mTracker).reset();
        }

        public final void updateCurrentDrainThreshold() {
            this.mBgCurrentDrainRestrictedBucketThreshold[0] =
                    DeviceConfig.getFloat(
                            "activity_manager",
                            "bg_current_drain_threshold_to_restricted_bucket",
                            this.mDefaultBgCurrentDrainRestrictedBucket);
            this.mBgCurrentDrainRestrictedBucketThreshold[1] =
                    DeviceConfig.getFloat(
                            "activity_manager",
                            "bg_current_drain_high_threshold_to_restricted_bucket",
                            this.mDefaultBgCurrentDrainRestrictedBucketHighThreshold);
            this.mBgCurrentDrainBgRestrictedThreshold[0] =
                    DeviceConfig.getFloat(
                            "activity_manager",
                            "bg_current_drain_threshold_to_bg_restricted",
                            this.mDefaultBgCurrentDrainBgRestrictedThreshold);
            this.mBgCurrentDrainBgRestrictedThreshold[1] =
                    DeviceConfig.getFloat(
                            "activity_manager",
                            "bg_current_drain_high_threshold_to_bg_restricted",
                            this.mDefaultBgCurrentDrainBgRestrictedHighThreshold);
            this.mBgCurrentDrainRestrictedBucketTypes =
                    DeviceConfig.getInt(
                            "activity_manager",
                            "bg_current_drain_types_to_restricted_bucket",
                            this.mDefaultCurrentDrainTypesToRestrictedBucket);
            this.mBgCurrentDrainBgRestrictedTypes =
                    DeviceConfig.getInt(
                            "activity_manager",
                            "bg_current_drain_types_to_bg_restricted",
                            this.mDefaultBgCurrentDrainTypesToBgRestricted);
            this.mBgCurrentDrainPowerComponents =
                    DeviceConfig.getInt(
                            "activity_manager",
                            "bg_current_drain_power_components",
                            this.mDefaultBgCurrentDrainPowerComponent);
            if (this.mBgCurrentDrainPowerComponents == -1) {
                this.mBatteryDimensions = BatteryUsage.BATT_DIMENS;
            } else {
                this.mBatteryDimensions = new BatteryConsumer.Dimensions[5];
                for (int i = 0; i < 5; i++) {
                    this.mBatteryDimensions[i] =
                            new BatteryConsumer.Dimensions(this.mBgCurrentDrainPowerComponents, i);
                }
            }
            this.mBgCurrentDrainHighThresholdByBgLocation =
                    DeviceConfig.getBoolean(
                            "activity_manager",
                            "bg_current_drain_high_threshold_by_bg_location",
                            this.mDefaultBgCurrentDrainHighThresholdByBgLocation);
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public final void updateTrackerEnabled() {
            if (this.mBatteryFullChargeMah > 0) {
                super.updateTrackerEnabled();
            } else {
                this.mTrackerEnabled = false;
                onTrackerEnabled(false);
            }
        }
    }

    public class BatteryUsage {
        public static final BatteryConsumer.Dimensions[] BATT_DIMENS = {
            new BatteryConsumer.Dimensions(-1, 0),
            new BatteryConsumer.Dimensions(-1, 1),
            new BatteryConsumer.Dimensions(-1, 2),
            new BatteryConsumer.Dimensions(-1, 3),
            new BatteryConsumer.Dimensions(-1, 4)
        };
        public double[] mPercentage;
        public double[] mUsage;

        public BatteryUsage() {
            this.mUsage = new double[] {0.0d, 0.0d, 0.0d, 0.0d, 0.0d};
        }

        public BatteryUsage(BatteryUsage batteryUsage) {
            double[] dArr = new double[batteryUsage.mUsage.length];
            this.mUsage = dArr;
            double[] dArr2 = batteryUsage.mUsage;
            System.arraycopy(dArr2, 0, dArr, 0, dArr2.length);
            double[] dArr3 = batteryUsage.mPercentage;
            if (dArr3 == null) {
                this.mPercentage = null;
                return;
            }
            double[] dArr4 = new double[dArr3.length];
            this.mPercentage = dArr4;
            double[] dArr5 = batteryUsage.mPercentage;
            System.arraycopy(dArr5, 0, dArr4, 0, dArr5.length);
        }

        public void add(BatteryUsage batteryUsage) {
            int i = 0;
            while (true) {
                double[] dArr = batteryUsage.mUsage;
                if (i >= dArr.length) {
                    return;
                }
                double[] dArr2 = this.mUsage;
                dArr2[i] = dArr2[i] + dArr[i];
                i++;
            }
        }

        public final void calcPercentage(int i, AppBatteryPolicy appBatteryPolicy) {
            BatteryUsage batteryUsage;
            double[] dArr = this.mPercentage;
            double[] dArr2 = this.mUsage;
            if (dArr == null || dArr.length != dArr2.length) {
                this.mPercentage = new double[dArr2.length];
            }
            double[] dArr3 = this.mPercentage;
            if (i > 0) {
                batteryUsage =
                        (BatteryUsage)
                                ((AppBatteryTracker) appBatteryPolicy.mTracker)
                                        .mDebugUidPercentages.get(i);
            } else {
                appBatteryPolicy.getClass();
                batteryUsage = null;
            }
            double[] dArr4 = batteryUsage != null ? batteryUsage.mPercentage : null;
            for (int i2 = 0; i2 < dArr2.length; i2++) {
                dArr3[i2] =
                        dArr4 != null
                                ? dArr4[i2]
                                : (dArr2[i2] / appBatteryPolicy.mBatteryFullChargeMah) * 100.0d;
            }
        }

        public final boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            BatteryUsage batteryUsage = (BatteryUsage) obj;
            int i = 0;
            while (true) {
                double[] dArr = this.mUsage;
                if (i >= dArr.length) {
                    return true;
                }
                if (Double.compare(dArr[i], batteryUsage.mUsage[i]) != 0) {
                    return false;
                }
                i++;
            }
        }

        public final double getUsagePowerMah(int i) {
            double[] dArr = this.mUsage;
            if (i == 1) {
                return dArr[1];
            }
            if (i == 2) {
                return dArr[2];
            }
            if (i == 3) {
                return dArr[3];
            }
            if (i != 4) {
                return 0.0d;
            }
            return dArr[4];
        }

        public final int hashCode() {
            int i = 0;
            int i2 = 0;
            while (true) {
                double[] dArr = this.mUsage;
                if (i >= dArr.length) {
                    return i2;
                }
                i2 = (i2 * 31) + Double.hashCode(dArr[i]);
                i++;
            }
        }

        public final String percentageToString() {
            double[] dArr = this.mPercentage;
            return String.format(
                    "%4.2f%% %4.2f%% %4.2f%% %4.2f%% %4.2f%%",
                    Double.valueOf(dArr[0]),
                    Double.valueOf(dArr[1]),
                    Double.valueOf(dArr[2]),
                    Double.valueOf(dArr[3]),
                    Double.valueOf(dArr[4]));
        }

        public final void scaleInternal(double d) {
            int i = 0;
            while (true) {
                double[] dArr = this.mUsage;
                if (i >= dArr.length) {
                    return;
                }
                dArr[i] = dArr[i] * d;
                i++;
            }
        }

        public BatteryUsage subtract(BatteryUsage batteryUsage) {
            int i = 0;
            while (true) {
                double[] dArr = batteryUsage.mUsage;
                if (i >= dArr.length) {
                    return this;
                }
                double[] dArr2 = this.mUsage;
                dArr2[i] = Math.max(0.0d, dArr2[i] - dArr[i]);
                i++;
            }
        }

        public final String toString() {
            double[] dArr = this.mUsage;
            return String.format(
                    "%.3f %.3f %.3f %.3f %.3f mAh",
                    Double.valueOf(dArr[0]),
                    Double.valueOf(dArr[1]),
                    Double.valueOf(dArr[2]),
                    Double.valueOf(dArr[3]),
                    Double.valueOf(dArr[4]));
        }
    }

    public final class ImmutableBatteryUsage extends BatteryUsage {
        @Override // com.android.server.am.AppBatteryTracker.BatteryUsage
        public final void add(BatteryUsage batteryUsage) {
            throw new RuntimeException("Readonly");
        }

        @Override // com.android.server.am.AppBatteryTracker.BatteryUsage
        public final BatteryUsage subtract(BatteryUsage batteryUsage) {
            throw new RuntimeException("Readonly");
        }
    }

    public AppBatteryTracker(Context context, AppRestrictionController appRestrictionController) {
        super(context, appRestrictionController);
        final int i = 0;
        this.mBgBatteryUsageStatsPolling =
                new Runnable(
                        this) { // from class:
                                // com.android.server.am.AppBatteryTracker$$ExternalSyntheticLambda0
                    public final /* synthetic */ AppBatteryTracker f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i2 = i;
                        AppBatteryTracker appBatteryTracker = this.f$0;
                        switch (i2) {
                            case 0:
                                appBatteryTracker.updateBatteryUsageStatsAndCheck();
                                break;
                            default:
                                appBatteryTracker.checkBatteryUsageStats();
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mBgBatteryUsageStatsCheck =
                new Runnable(
                        this) { // from class:
                                // com.android.server.am.AppBatteryTracker$$ExternalSyntheticLambda0
                    public final /* synthetic */ AppBatteryTracker f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i22 = i2;
                        AppBatteryTracker appBatteryTracker = this.f$0;
                        switch (i22) {
                            case 0:
                                appBatteryTracker.updateBatteryUsageStatsAndCheck();
                                break;
                            default:
                                appBatteryTracker.checkBatteryUsageStats();
                                break;
                        }
                    }
                };
        this.mActiveUserIdStates = new SparseBooleanArray();
        this.mUidBatteryUsage = new SparseArray();
        this.mUidBatteryUsageInWindow = new SparseArray();
        this.mLastUidBatteryUsage = new SparseArray();
        this.mTmpUidBatteryUsage = new SparseArray();
        this.mTmpUidBatteryUsage2 = new SparseArray();
        this.mTmpUidBatteryUsageInWindow = new SparseArray();
        this.mTmpUserIds = new ArraySet();
        this.mLastReportTime = 0L;
        this.mDebugUidPercentages = new SparseArray();
        this.mBatteryUsageStatsPollingIntervalMs = 1800000L;
        this.mBatteryUsageStatsPollingMinIntervalMs = 300000L;
        BaseAppStateTracker.Injector injector = this.mInjector;
        injector.mAppStatePolicy = new AppBatteryPolicy(injector, this);
    }

    public static void copyUidBatteryUsage(SparseArray sparseArray, SparseArray sparseArray2) {
        sparseArray2.clear();
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            sparseArray2.put(
                    sparseArray.keyAt(size),
                    new ImmutableBatteryUsage((BatteryUsage) sparseArray.valueAt(size)));
        }
    }

    public static void copyUidBatteryUsage(
            SparseArray sparseArray, SparseArray sparseArray2, double d) {
        sparseArray2.clear();
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            int keyAt = sparseArray.keyAt(size);
            ImmutableBatteryUsage immutableBatteryUsage =
                    new ImmutableBatteryUsage((BatteryUsage) sparseArray.valueAt(size));
            immutableBatteryUsage.scaleInternal(d);
            sparseArray2.put(keyAt, immutableBatteryUsage);
        }
    }

    public static void dumpProcessStateStats(ProtoOutputStream protoOutputStream, int i, double d) {
        if (d == 0.0d) {
            return;
        }
        long start = protoOutputStream.start(2246267895810L);
        protoOutputStream.write(1159641169921L, i);
        protoOutputStream.write(1103806595075L, d);
        protoOutputStream.end(start);
    }

    public static void dumpUidStats(
            ProtoOutputStream protoOutputStream, int i, BatteryUsage batteryUsage) {
        if (batteryUsage.mUsage == null) {
            return;
        }
        double usagePowerMah = batteryUsage.getUsagePowerMah(1);
        double usagePowerMah2 = batteryUsage.getUsagePowerMah(2);
        double usagePowerMah3 = batteryUsage.getUsagePowerMah(3);
        double usagePowerMah4 = batteryUsage.getUsagePowerMah(4);
        if (usagePowerMah == 0.0d && usagePowerMah2 == 0.0d && usagePowerMah3 == 0.0d) {
            return;
        }
        long start = protoOutputStream.start(2246267895809L);
        protoOutputStream.write(1120986464257L, i);
        dumpProcessStateStats(protoOutputStream, 1, usagePowerMah);
        dumpProcessStateStats(protoOutputStream, 2, usagePowerMah2);
        dumpProcessStateStats(protoOutputStream, 3, usagePowerMah3);
        dumpProcessStateStats(protoOutputStream, 4, usagePowerMah4);
        protoOutputStream.end(start);
    }

    public final void checkBatteryUsageStats() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        AppBatteryPolicy appBatteryPolicy = (AppBatteryPolicy) this.mInjector.mAppStatePolicy;
        try {
            SparseArray sparseArray = this.mTmpUidBatteryUsageInWindow;
            synchronized (this.mLock) {
                copyUidBatteryUsage(this.mUidBatteryUsageInWindow, sparseArray);
            }
            long max = Math.max(0L, elapsedRealtime - appBatteryPolicy.mBgCurrentDrainWindowMs);
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                int keyAt = sparseArray.keyAt(i);
                ImmutableBatteryUsage immutableBatteryUsage =
                        (ImmutableBatteryUsage) sparseArray.valueAt(i);
                ImmutableBatteryUsage uidBatteryExemptedUsageSince =
                        this.mAppRestrictionController.getUidBatteryExemptedUsageSince(
                                keyAt,
                                appBatteryPolicy.mBgCurrentDrainExemptedTypes,
                                max,
                                elapsedRealtime);
                immutableBatteryUsage.getClass();
                BatteryUsage batteryUsage = new BatteryUsage(immutableBatteryUsage);
                batteryUsage.subtract(uidBatteryExemptedUsageSince);
                batteryUsage.calcPercentage(keyAt, appBatteryPolicy);
                appBatteryPolicy.handleUidBatteryUsage(
                        keyAt, new ImmutableBatteryUsage(batteryUsage));
            }
            int size2 = this.mDebugUidPercentages.size();
            for (int i2 = 0; i2 < size2; i2++) {
                appBatteryPolicy.handleUidBatteryUsage(
                        this.mDebugUidPercentages.keyAt(i2),
                        (ImmutableBatteryUsage) this.mDebugUidPercentages.valueAt(i2));
            }
            scheduleBatteryUsageStatsUpdateIfNecessary(this.mBatteryUsageStatsPollingIntervalMs);
        } catch (Throwable th) {
            scheduleBatteryUsageStatsUpdateIfNecessary(this.mBatteryUsageStatsPollingIntervalMs);
            throw th;
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.println("APP BATTERY STATE TRACKER:");
        this.mInjector.getClass();
        updateBatteryUsageStatsIfNecessary(System.currentTimeMillis(), true);
        scheduleBgBatteryUsageStatsCheck();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.mBgHandler
                .getLooper()
                .getQueue()
                .addIdleHandler(
                        new MessageQueue
                                .IdleHandler() { // from class:
                                                 // com.android.server.am.AppBatteryTracker$$ExternalSyntheticLambda2
                            @Override // android.os.MessageQueue.IdleHandler
                            public final boolean queueIdle() {
                                countDownLatch.countDown();
                                return false;
                            }
                        });
        try {
            countDownLatch.await();
        } catch (InterruptedException unused) {
        }
        synchronized (this.mLock) {
            try {
                SparseArray sparseArray = this.mUidBatteryUsageInWindow;
                printWriter.print("  " + str);
                printWriter.print("  Last battery usage start=");
                TimeUtils.dumpTime(printWriter, this.mLastUidBatteryUsageStartTs);
                printWriter.println();
                printWriter.print("  " + str);
                printWriter.print("Battery usage over last ");
                String str2 = "    " + str;
                AppBatteryPolicy appBatteryPolicy =
                        (AppBatteryPolicy) this.mInjector.mAppStatePolicy;
                long elapsedRealtime = SystemClock.elapsedRealtime();
                long max = Math.max(0L, elapsedRealtime - appBatteryPolicy.mBgCurrentDrainWindowMs);
                printWriter.println(TimeUtils.formatDuration(elapsedRealtime - max));
                if (sparseArray.size() == 0) {
                    printWriter.print(str2);
                    printWriter.println("(none)");
                } else {
                    int size = sparseArray.size();
                    int i = 0;
                    while (i < size) {
                        int keyAt = sparseArray.keyAt(i);
                        ImmutableBatteryUsage immutableBatteryUsage =
                                (ImmutableBatteryUsage) sparseArray.valueAt(i);
                        immutableBatteryUsage.calcPercentage(keyAt, appBatteryPolicy);
                        ImmutableBatteryUsage uidBatteryExemptedUsageSince =
                                this.mAppRestrictionController.getUidBatteryExemptedUsageSince(
                                        keyAt,
                                        appBatteryPolicy.mBgCurrentDrainExemptedTypes,
                                        max,
                                        elapsedRealtime);
                        uidBatteryExemptedUsageSince.calcPercentage(keyAt, appBatteryPolicy);
                        BatteryUsage batteryUsage = new BatteryUsage(immutableBatteryUsage);
                        batteryUsage.subtract(uidBatteryExemptedUsageSince);
                        batteryUsage.calcPercentage(keyAt, appBatteryPolicy);
                        printWriter.format(
                                "%s%s: [%s] %s (%s) | %s (%s) | %s (%s) | %s\n",
                                str2,
                                UserHandle.formatUid(keyAt),
                                PowerExemptionManager.reasonCodeToString(
                                        appBatteryPolicy.mTracker.mAppRestrictionController
                                                .getBackgroundRestrictionExemptionReason(keyAt)),
                                immutableBatteryUsage.toString(),
                                immutableBatteryUsage.percentageToString(),
                                uidBatteryExemptedUsageSince.toString(),
                                uidBatteryExemptedUsageSince.percentageToString(),
                                batteryUsage.toString(),
                                batteryUsage.percentageToString(),
                                ((BatteryUsage)
                                                this.mUidBatteryUsage.get(
                                                        keyAt, BATTERY_USAGE_NONE))
                                        .toString());
                        i++;
                        size = size;
                        sparseArray = sparseArray;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mInjector.mAppStatePolicy.dump(printWriter, "  " + str);
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final void dumpAsProto(int i, ProtoOutputStream protoOutputStream) {
        this.mInjector.getClass();
        updateBatteryUsageStatsIfNecessary(System.currentTimeMillis(), true);
        synchronized (this.mLock) {
            try {
                SparseArray sparseArray = this.mUidBatteryUsageInWindow;
                if (i != -1) {
                    BatteryUsage batteryUsage = (BatteryUsage) sparseArray.get(i);
                    if (batteryUsage != null) {
                        dumpUidStats(protoOutputStream, i, batteryUsage);
                    }
                } else {
                    int size = sparseArray.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        dumpUidStats(
                                protoOutputStream,
                                sparseArray.keyAt(i2),
                                (BatteryUsage) sparseArray.valueAt(i2));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final byte[] getTrackerInfoForStatsd(int i) {
        ImmutableBatteryUsage immutableBatteryUsage;
        synchronized (this.mLock) {
            immutableBatteryUsage = (ImmutableBatteryUsage) this.mUidBatteryUsageInWindow.get(i);
        }
        if (immutableBatteryUsage == null) {
            return null;
        }
        immutableBatteryUsage.calcPercentage(i, (AppBatteryPolicy) this.mInjector.mAppStatePolicy);
        double[] dArr = immutableBatteryUsage.mPercentage;
        double d = dArr[0];
        double d2 = dArr[1];
        double d3 = dArr[2];
        double d4 = dArr[3];
        double d5 = dArr[4];
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        protoOutputStream.write(1120986464257L, (d + d2 + d3 + d4 + d5) * 10000.0d);
        protoOutputStream.write(1120986464258L, d3 * 10000.0d);
        protoOutputStream.write(1120986464259L, d4 * 10000.0d);
        protoOutputStream.write(1120986464260L, d2 * 10000.0d);
        protoOutputStream.write(1120986464261L, d5 * 10000.0d);
        protoOutputStream.flush();
        return protoOutputStream.getBytes();
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final int getType() {
        return 1;
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final void onBackgroundRestrictionChanged(int i, boolean z) {
        AppBatteryPolicy appBatteryPolicy = (AppBatteryPolicy) this.mInjector.mAppStatePolicy;
        if (z) {
            appBatteryPolicy.getClass();
            return;
        }
        synchronized (appBatteryPolicy.mLock) {
            try {
                Pair pair = (Pair) appBatteryPolicy.mHighBgBatteryPackages.get(i);
                if (pair != null) {
                    ((long[]) pair.first)[1] = 0;
                    ((ImmutableBatteryUsage[]) pair.second)[1] = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final void onSystemReady() {
        super.onSystemReady();
        UserManagerInternal userManagerInternal = this.mInjector.mUserManagerInternal;
        for (int i : userManagerInternal.getUserIds()) {
            if (userManagerInternal.isUserRunning(i)) {
                synchronized (this.mLock) {
                    this.mActiveUserIdStates.put(i, true);
                }
            }
        }
        scheduleBatteryUsageStatsUpdateIfNecessary(this.mBatteryUsageStatsPollingIntervalMs);
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final void onUidRemoved(int i) {
        synchronized (this.mLock) {
            this.mUidBatteryUsage.delete(i);
            this.mUidBatteryUsageInWindow.delete(i);
            AppBatteryPolicy appBatteryPolicy = (AppBatteryPolicy) this.mInjector.mAppStatePolicy;
            appBatteryPolicy.mHighBgBatteryPackages.remove(i);
            appBatteryPolicy.mLastInteractionTime.delete(i);
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final void onUserInteractionStarted(String str, int i) {
        int indexOfKey;
        boolean z;
        AppBatteryPolicy appBatteryPolicy = (AppBatteryPolicy) this.mInjector.mAppStatePolicy;
        synchronized (appBatteryPolicy.mLock) {
            try {
                appBatteryPolicy.mLastInteractionTime.put(i, SystemClock.elapsedRealtime());
                if (((AppBatteryTracker) appBatteryPolicy.mTracker)
                                        .mAppRestrictionController.mRestrictionSettings
                                                .getRestrictionLevel(i, str)
                                != 50
                        && (indexOfKey = appBatteryPolicy.mHighBgBatteryPackages.indexOfKey(i))
                                >= 0) {
                    appBatteryPolicy.mHighBgBatteryPackages.removeAt(indexOfKey);
                    z = true;
                }
                z = false;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z) {
            ((AppBatteryTracker) appBatteryPolicy.mTracker)
                    .mAppRestrictionController.refreshAppRestrictionLevelForUid(
                            i,
                            FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_USAGE,
                            3,
                            true);
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final void onUserRemoved(int i) {
        synchronized (this.mLock) {
            try {
                this.mActiveUserIdStates.delete(i);
                for (int size = this.mUidBatteryUsage.size() - 1; size >= 0; size--) {
                    if (UserHandle.getUserId(this.mUidBatteryUsage.keyAt(size)) == i) {
                        this.mUidBatteryUsage.removeAt(size);
                    }
                }
                for (int size2 = this.mUidBatteryUsageInWindow.size() - 1; size2 >= 0; size2--) {
                    if (UserHandle.getUserId(this.mUidBatteryUsageInWindow.keyAt(size2)) == i) {
                        this.mUidBatteryUsageInWindow.removeAt(size2);
                    }
                }
                AppBatteryPolicy appBatteryPolicy =
                        (AppBatteryPolicy) this.mInjector.mAppStatePolicy;
                for (int size3 = appBatteryPolicy.mHighBgBatteryPackages.size() - 1;
                        size3 >= 0;
                        size3--) {
                    if (UserHandle.getUserId(appBatteryPolicy.mHighBgBatteryPackages.keyAt(size3))
                            == i) {
                        appBatteryPolicy.mHighBgBatteryPackages.removeAt(size3);
                    }
                }
                for (int size4 = appBatteryPolicy.mLastInteractionTime.size() - 1;
                        size4 >= 0;
                        size4--) {
                    if (UserHandle.getUserId(appBatteryPolicy.mLastInteractionTime.keyAt(size4))
                            == i) {
                        appBatteryPolicy.mLastInteractionTime.removeAt(size4);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final void onUserStarted(int i) {
        synchronized (this.mLock) {
            this.mActiveUserIdStates.put(i, true);
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final void onUserStopped(int i) {
        synchronized (this.mLock) {
            this.mActiveUserIdStates.put(i, false);
        }
    }

    public void reset() {
        synchronized (this.mLock) {
            this.mUidBatteryUsage.clear();
            this.mUidBatteryUsageInWindow.clear();
            this.mLastUidBatteryUsage.clear();
            this.mLastBatteryUsageSamplingTs = 0L;
            this.mLastUidBatteryUsageStartTs = 0L;
        }
        this.mBgHandler.removeCallbacks(this.mBgBatteryUsageStatsPolling);
        updateBatteryUsageStatsAndCheck();
    }

    public final void scheduleBatteryUsageStatsUpdateIfNecessary(long j) {
        if (((AppBatteryPolicy) this.mInjector.mAppStatePolicy).mTrackerEnabled) {
            synchronized (this.mLock) {
                try {
                    if (!this.mBgHandler.hasCallbacks(this.mBgBatteryUsageStatsPolling)) {
                        this.mBgHandler.postDelayed(this.mBgBatteryUsageStatsPolling, j);
                    }
                } finally {
                }
            }
            long elapsedRealtime = SystemClock.elapsedRealtime();
            synchronized (this.mLock) {
                try {
                    if (elapsedRealtime - this.mLastReportTime
                            >= ((AppBatteryPolicy) this.mInjector.mAppStatePolicy)
                                    .mBgCurrentDrainWindowMs) {
                        this.mLastReportTime = elapsedRealtime;
                        this.mInjector.getClass();
                        updateBatteryUsageStatsIfNecessary(System.currentTimeMillis(), true);
                        synchronized (this.mLock) {
                            try {
                                int size = this.mUidBatteryUsageInWindow.size();
                                for (int i = 0; i < size; i++) {
                                    int keyAt = this.mUidBatteryUsageInWindow.keyAt(i);
                                    if ((UserHandle.isCore(keyAt) || UserHandle.isApp(keyAt))
                                            && !BATTERY_USAGE_NONE.equals(
                                                    this.mUidBatteryUsageInWindow.valueAt(i))) {
                                        FrameworkStatsLog.write(
                                                FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO,
                                                keyAt,
                                                0,
                                                0,
                                                0,
                                                (byte[]) null,
                                                getTrackerInfoForStatsd(keyAt),
                                                (byte[]) null,
                                                (byte[]) null,
                                                0,
                                                0,
                                                0,
                                                ActivityManager.isLowRamDeviceStatic(),
                                                0);
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                } finally {
                }
            }
        }
    }

    public final void scheduleBgBatteryUsageStatsCheck() {
        AppRestrictionController.BgHandler bgHandler = this.mBgHandler;
        AppBatteryTracker$$ExternalSyntheticLambda0 appBatteryTracker$$ExternalSyntheticLambda0 =
                this.mBgBatteryUsageStatsCheck;
        if (bgHandler.hasCallbacks(appBatteryTracker$$ExternalSyntheticLambda0)) {
            return;
        }
        bgHandler.post(appBatteryTracker$$ExternalSyntheticLambda0);
    }

    public final void updateBatteryUsageStatsAndCheck() {
        this.mInjector.getClass();
        long currentTimeMillis = System.currentTimeMillis();
        if (updateBatteryUsageStatsIfNecessary(currentTimeMillis, false)) {
            checkBatteryUsageStats();
            return;
        }
        synchronized (this.mLock) {
            scheduleBatteryUsageStatsUpdateIfNecessary(
                    (this.mLastBatteryUsageSamplingTs + this.mBatteryUsageStatsPollingMinIntervalMs)
                            - currentTimeMillis);
        }
    }

    public final boolean updateBatteryUsageStatsIfNecessary(long j, boolean z) {
        boolean z2;
        synchronized (this.mLock) {
            try {
                if (this.mLastBatteryUsageSamplingTs + this.mBatteryUsageStatsPollingMinIntervalMs
                                >= j
                        && !z) {
                    return false;
                }
                if (this.mBatteryUsageStatsUpdatePending) {
                    try {
                        this.mLock.wait();
                    } catch (InterruptedException unused) {
                    }
                    z2 = false;
                } else {
                    this.mBatteryUsageStatsUpdatePending = true;
                    z2 = true;
                }
                if (z2) {
                    updateBatteryUsageStatsOnce(j);
                    synchronized (this.mLock) {
                        this.mLastBatteryUsageSamplingTs = j;
                        this.mBatteryUsageStatsUpdatePending = false;
                        this.mLock.notifyAll();
                    }
                }
                return true;
            } finally {
            }
        }
    }

    public final void updateBatteryUsageStatsOnce(long j) {
        boolean z;
        long j2;
        long j3;
        long j4;
        long j5;
        BaseAppStateTracker.Injector injector = this.mInjector;
        AppBatteryPolicy appBatteryPolicy = (AppBatteryPolicy) injector.mAppStatePolicy;
        ArraySet arraySet = this.mTmpUserIds;
        SparseArray sparseArray = this.mTmpUidBatteryUsage;
        BatteryStatsInternal batteryStatsInternal = injector.mBatteryStatsInternal;
        long j6 = appBatteryPolicy.mBgCurrentDrainWindowMs;
        sparseArray.clear();
        arraySet.clear();
        synchronized (this.mLock) {
            try {
                z = true;
                for (int size = this.mActiveUserIdStates.size() - 1; size >= 0; size--) {
                    arraySet.add(UserHandle.of(this.mActiveUserIdStates.keyAt(size)));
                    if (!this.mActiveUserIdStates.valueAt(size)) {
                        this.mActiveUserIdStates.removeAt(size);
                    }
                }
            } finally {
            }
        }
        BatteryUsageStats updateBatteryUsageStatsOnceInternal =
                updateBatteryUsageStatsOnceInternal(
                        0L,
                        sparseArray,
                        new BatteryUsageStatsQuery.Builder()
                                .includeProcessStateData()
                                .setMaxStatsAgeMs(0L),
                        arraySet,
                        batteryStatsInternal);
        long statsStartTimestamp =
                updateBatteryUsageStatsOnceInternal != null
                        ? updateBatteryUsageStatsOnceInternal.getStatsStartTimestamp()
                        : 0L;
        long statsEndTimestamp =
                (updateBatteryUsageStatsOnceInternal != null
                                ? updateBatteryUsageStatsOnceInternal.getStatsEndTimestamp()
                                : j)
                        - statsStartTimestamp;
        if (statsEndTimestamp >= j6) {
            synchronized (this.mLock) {
                j2 = j6;
                copyUidBatteryUsage(
                        sparseArray,
                        this.mUidBatteryUsageInWindow,
                        (j6 * 1.0d) / statsEndTimestamp);
            }
            z = false;
        } else {
            j2 = j6;
        }
        this.mTmpUidBatteryUsage2.clear();
        copyUidBatteryUsage(sparseArray, this.mTmpUidBatteryUsage2);
        synchronized (this.mLock) {
            j3 = this.mLastUidBatteryUsageStartTs;
            this.mLastUidBatteryUsageStartTs = statsStartTimestamp;
        }
        if (statsStartTimestamp <= j3 || j3 <= 0) {
            j4 = statsEndTimestamp;
        } else {
            BatteryUsageStats updateBatteryUsageStatsOnceInternal2 =
                    updateBatteryUsageStatsOnceInternal(
                            0L,
                            sparseArray,
                            new BatteryUsageStatsQuery.Builder()
                                    .includeProcessStateData()
                                    .aggregateSnapshots(j3, statsStartTimestamp),
                            arraySet,
                            batteryStatsInternal);
            j4 = (statsStartTimestamp - j3) + statsEndTimestamp;
            try {
                if (updateBatteryUsageStatsOnceInternal2 != null) {
                    updateBatteryUsageStatsOnceInternal2.close();
                } else {
                    Slog.w("ActivityManager", "Stat was null");
                }
            } catch (IOException unused) {
                Slog.w("ActivityManager", "Failed to close a stat");
            }
        }
        if (!z || j4 < j2) {
            j5 = j2;
        } else {
            synchronized (this.mLock) {
                j5 = j2;
                copyUidBatteryUsage(sparseArray, this.mUidBatteryUsageInWindow, (j5 * 1.0d) / j4);
            }
            z = false;
        }
        synchronized (this.mLock) {
            try {
                int size2 = sparseArray.size();
                for (int i = 0; i < size2; i++) {
                    int keyAt = sparseArray.keyAt(i);
                    int indexOfKey = this.mUidBatteryUsage.indexOfKey(keyAt);
                    BatteryUsage batteryUsage =
                            (BatteryUsage) this.mLastUidBatteryUsage.get(keyAt, BATTERY_USAGE_NONE);
                    BatteryUsage batteryUsage2 = (BatteryUsage) sparseArray.valueAt(i);
                    if (indexOfKey >= 0) {
                        ((BatteryUsage) this.mUidBatteryUsage.valueAt(indexOfKey))
                                .subtract(batteryUsage)
                                .add(batteryUsage2);
                    } else {
                        this.mUidBatteryUsage.put(keyAt, batteryUsage2);
                    }
                }
                copyUidBatteryUsage(this.mTmpUidBatteryUsage2, this.mLastUidBatteryUsage);
            } finally {
            }
        }
        this.mTmpUidBatteryUsage2.clear();
        if (z) {
            long j7 = j - j5;
            long j8 = j3 - 1;
            updateBatteryUsageStatsOnceInternal(
                    j8 - j7,
                    sparseArray,
                    new BatteryUsageStatsQuery.Builder()
                            .includeProcessStateData()
                            .aggregateSnapshots(j7, j8),
                    arraySet,
                    batteryStatsInternal);
            synchronized (this.mLock) {
                copyUidBatteryUsage(sparseArray, this.mUidBatteryUsageInWindow);
            }
        }
        try {
            if (updateBatteryUsageStatsOnceInternal != null) {
                updateBatteryUsageStatsOnceInternal.close();
            } else {
                Slog.w("ActivityManager", "Stat was null");
            }
        } catch (IOException unused2) {
            Slog.w("ActivityManager", "Failed to close a stat");
        }
    }

    public final BatteryUsageStats updateBatteryUsageStatsOnceInternal(
            long j,
            SparseArray sparseArray,
            BatteryUsageStatsQuery.Builder builder,
            ArraySet arraySet,
            BatteryStatsInternal batteryStatsInternal) {
        double d;
        double d2;
        double d3;
        double d4;
        char c = 2;
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            builder.addUser((UserHandle) arraySet.valueAt(i));
        }
        List batteryUsageStats =
                batteryStatsInternal.getBatteryUsageStats(Arrays.asList(builder.build()));
        if (ArrayUtils.isEmpty(batteryUsageStats)) {
            return null;
        }
        BatteryUsageStats batteryUsageStats2 = (BatteryUsageStats) batteryUsageStats.get(0);
        for (int i2 = 1; i2 < batteryUsageStats.size(); i2++) {
            try {
                if (batteryUsageStats.get(i2) != null) {
                    ((BatteryUsageStats) batteryUsageStats.get(i2)).close();
                } else {
                    Slog.w("ActivityManager", "Stat was null");
                }
            } catch (IOException unused) {
                Slog.w("ActivityManager", "Failed to close a stat in BatteryUsageStats List");
            }
        }
        List<UidBatteryConsumer> uidBatteryConsumers = batteryUsageStats2.getUidBatteryConsumers();
        if (uidBatteryConsumers != null) {
            double min =
                    j > 0
                            ? Math.min(
                                    (j * 1.0d)
                                            / (batteryUsageStats2.getStatsEndTimestamp()
                                                    - batteryUsageStats2.getStatsStartTimestamp()),
                                    1.0d)
                            : 1.0d;
            AppBatteryPolicy appBatteryPolicy = (AppBatteryPolicy) this.mInjector.mAppStatePolicy;
            for (UidBatteryConsumer uidBatteryConsumer : uidBatteryConsumers) {
                int uid = uidBatteryConsumer.getUid();
                if (!UserHandle.isIsolated(uid)) {
                    int appIdFromSharedAppGid = UserHandle.getAppIdFromSharedAppGid(uid);
                    if (appIdFromSharedAppGid > 0) {
                        uid = UserHandle.getUid(0, appIdFromSharedAppGid);
                    }
                    BatteryUsage batteryUsage = new BatteryUsage();
                    BatteryConsumer.Dimensions[] dimensionsArr =
                            appBatteryPolicy.mBatteryDimensions;
                    double d5 = 0.0d;
                    try {
                        d = uidBatteryConsumer.getConsumedPower(dimensionsArr[0]);
                    } catch (IllegalArgumentException unused2) {
                        d = 0.0d;
                    }
                    try {
                        d2 = uidBatteryConsumer.getConsumedPower(dimensionsArr[1]);
                    } catch (IllegalArgumentException unused3) {
                        d2 = 0.0d;
                    }
                    try {
                        d3 = uidBatteryConsumer.getConsumedPower(dimensionsArr[c]);
                    } catch (IllegalArgumentException unused4) {
                        d3 = 0.0d;
                    }
                    try {
                        d4 = uidBatteryConsumer.getConsumedPower(dimensionsArr[3]);
                    } catch (IllegalArgumentException unused5) {
                        d4 = 0.0d;
                    }
                    try {
                        d5 = uidBatteryConsumer.getConsumedPower(dimensionsArr[4]);
                    } catch (IllegalArgumentException unused6) {
                    }
                    batteryUsage.mUsage = new double[] {d, d2, d3, d4, d5};
                    batteryUsage.scaleInternal(min);
                    int indexOfKey = sparseArray.indexOfKey(uid);
                    if (indexOfKey < 0) {
                        sparseArray.put(uid, batteryUsage);
                    } else {
                        ((BatteryUsage) sparseArray.valueAt(indexOfKey)).add(batteryUsage);
                    }
                    c = 2;
                }
            }
        }
        return batteryUsageStats2;
    }
}

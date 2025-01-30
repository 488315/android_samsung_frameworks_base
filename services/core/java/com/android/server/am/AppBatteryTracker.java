package com.android.server.am;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.BatteryConsumer;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.MessageQueue;
import android.os.PowerExemptionManager;
import android.os.SystemClock;
import android.os.UidBatteryConsumer;
import android.os.UserHandle;
import android.p005os.BatteryStatsInternal;
import android.provider.DeviceConfig;
import android.util.ArraySet;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseLongArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.am.AppRestrictionController;
import com.android.server.am.BaseAppStateTracker;
import com.android.server.pm.UserManagerInternal;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes.dex */
public final class AppBatteryTracker extends BaseAppStateTracker implements AppRestrictionController.UidBatteryUsageProvider {
    public static final ImmutableBatteryUsage BATTERY_USAGE_NONE = new ImmutableBatteryUsage();
    public final SparseBooleanArray mActiveUserIdStates;
    public final long mBatteryUsageStatsPollingIntervalMs;
    public final long mBatteryUsageStatsPollingMinIntervalMs;
    public boolean mBatteryUsageStatsUpdatePending;
    public final Runnable mBgBatteryUsageStatsCheck;
    public final Runnable mBgBatteryUsageStatsPolling;
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

    @Override // com.android.server.am.BaseAppStateTracker
    public int getType() {
        return 1;
    }

    public AppBatteryTracker(Context context, AppRestrictionController appRestrictionController) {
        this(context, appRestrictionController, null, null);
    }

    public AppBatteryTracker(Context context, AppRestrictionController appRestrictionController, Constructor constructor, Object obj) {
        super(context, appRestrictionController, constructor, obj);
        this.mBgBatteryUsageStatsPolling = new Runnable() { // from class: com.android.server.am.AppBatteryTracker$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AppBatteryTracker.this.updateBatteryUsageStatsAndCheck();
            }
        };
        this.mBgBatteryUsageStatsCheck = new Runnable() { // from class: com.android.server.am.AppBatteryTracker$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AppBatteryTracker.this.checkBatteryUsageStats();
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
        if (constructor == null) {
            this.mBatteryUsageStatsPollingIntervalMs = 1800000L;
            this.mBatteryUsageStatsPollingMinIntervalMs = BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
        } else {
            this.mBatteryUsageStatsPollingIntervalMs = 2000L;
            this.mBatteryUsageStatsPollingMinIntervalMs = 2000L;
        }
        BaseAppStateTracker.Injector injector = this.mInjector;
        injector.setPolicy(new AppBatteryPolicy(injector, this));
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public void onSystemReady() {
        super.onSystemReady();
        UserManagerInternal userManagerInternal = this.mInjector.getUserManagerInternal();
        for (int i : userManagerInternal.getUserIds()) {
            if (userManagerInternal.isUserRunning(i)) {
                synchronized (this.mLock) {
                    this.mActiveUserIdStates.put(i, true);
                }
            }
        }
        scheduleBatteryUsageStatsUpdateIfNecessary(this.mBatteryUsageStatsPollingIntervalMs);
    }

    public final void scheduleBatteryUsageStatsUpdateIfNecessary(long j) {
        if (((AppBatteryPolicy) this.mInjector.getPolicy()).isEnabled()) {
            synchronized (this.mLock) {
                if (!this.mBgHandler.hasCallbacks(this.mBgBatteryUsageStatsPolling)) {
                    this.mBgHandler.postDelayed(this.mBgBatteryUsageStatsPolling, j);
                }
            }
            logAppBatteryTrackerIfNeeded();
        }
    }

    public final void logAppBatteryTrackerIfNeeded() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        synchronized (this.mLock) {
            if (elapsedRealtime - this.mLastReportTime < ((AppBatteryPolicy) this.mInjector.getPolicy()).mBgCurrentDrainWindowMs) {
                return;
            }
            this.mLastReportTime = elapsedRealtime;
            updateBatteryUsageStatsIfNecessary(this.mInjector.currentTimeMillis(), true);
            synchronized (this.mLock) {
                int size = this.mUidBatteryUsageInWindow.size();
                for (int i = 0; i < size; i++) {
                    int keyAt = this.mUidBatteryUsageInWindow.keyAt(i);
                    if ((UserHandle.isCore(keyAt) || UserHandle.isApp(keyAt)) && !BATTERY_USAGE_NONE.equals(this.mUidBatteryUsageInWindow.valueAt(i))) {
                        FrameworkStatsLog.write(FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO, keyAt, 0, 0, 0, (byte[]) null, getTrackerInfoForStatsd(keyAt), (byte[]) null, (byte[]) null, 0, 0, 0, ActivityManager.isLowRamDeviceStatic(), 0);
                    }
                }
            }
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public byte[] getTrackerInfoForStatsd(int i) {
        ImmutableBatteryUsage immutableBatteryUsage;
        synchronized (this.mLock) {
            immutableBatteryUsage = (ImmutableBatteryUsage) this.mUidBatteryUsageInWindow.get(i);
        }
        if (immutableBatteryUsage == null) {
            return null;
        }
        double[] dArr = immutableBatteryUsage.calcPercentage(i, (AppBatteryPolicy) this.mInjector.getPolicy()).mPercentage;
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
    public void onUserStarted(int i) {
        synchronized (this.mLock) {
            this.mActiveUserIdStates.put(i, true);
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public void onUserStopped(int i) {
        synchronized (this.mLock) {
            this.mActiveUserIdStates.put(i, false);
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public void onUserRemoved(int i) {
        synchronized (this.mLock) {
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
            ((AppBatteryPolicy) this.mInjector.getPolicy()).onUserRemovedLocked(i);
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public void onUidRemoved(int i) {
        synchronized (this.mLock) {
            this.mUidBatteryUsage.delete(i);
            this.mUidBatteryUsageInWindow.delete(i);
            ((AppBatteryPolicy) this.mInjector.getPolicy()).onUidRemovedLocked(i);
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public void onUserInteractionStarted(String str, int i) {
        ((AppBatteryPolicy) this.mInjector.getPolicy()).onUserInteractionStarted(str, i);
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public void onBackgroundRestrictionChanged(int i, String str, boolean z) {
        ((AppBatteryPolicy) this.mInjector.getPolicy()).onBackgroundRestrictionChanged(i, str, z);
    }

    @Override // com.android.server.am.AppRestrictionController.UidBatteryUsageProvider
    public ImmutableBatteryUsage getUidBatteryUsage(int i) {
        ImmutableBatteryUsage immutableBatteryUsage;
        boolean updateBatteryUsageStatsIfNecessary = updateBatteryUsageStatsIfNecessary(this.mInjector.currentTimeMillis(), false);
        synchronized (this.mLock) {
            if (updateBatteryUsageStatsIfNecessary) {
                this.mBgHandler.removeCallbacks(this.mBgBatteryUsageStatsPolling);
                scheduleBgBatteryUsageStatsCheck();
            }
            BatteryUsage batteryUsage = (BatteryUsage) this.mUidBatteryUsage.get(i);
            immutableBatteryUsage = batteryUsage != null ? new ImmutableBatteryUsage(batteryUsage) : BATTERY_USAGE_NONE;
        }
        return immutableBatteryUsage;
    }

    public final void scheduleBgBatteryUsageStatsCheck() {
        if (this.mBgHandler.hasCallbacks(this.mBgBatteryUsageStatsCheck)) {
            return;
        }
        this.mBgHandler.post(this.mBgBatteryUsageStatsCheck);
    }

    public final void updateBatteryUsageStatsAndCheck() {
        long currentTimeMillis = this.mInjector.currentTimeMillis();
        if (updateBatteryUsageStatsIfNecessary(currentTimeMillis, false)) {
            checkBatteryUsageStats();
            return;
        }
        synchronized (this.mLock) {
            scheduleBatteryUsageStatsUpdateIfNecessary((this.mLastBatteryUsageSamplingTs + this.mBatteryUsageStatsPollingMinIntervalMs) - currentTimeMillis);
        }
    }

    public final void checkBatteryUsageStats() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        AppBatteryPolicy appBatteryPolicy = (AppBatteryPolicy) this.mInjector.getPolicy();
        try {
            SparseArray sparseArray = this.mTmpUidBatteryUsageInWindow;
            synchronized (this.mLock) {
                copyUidBatteryUsage(this.mUidBatteryUsageInWindow, sparseArray);
            }
            long max = Math.max(0L, elapsedRealtime - appBatteryPolicy.mBgCurrentDrainWindowMs);
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                int keyAt = sparseArray.keyAt(i);
                ImmutableBatteryUsage immutableBatteryUsage = (ImmutableBatteryUsage) sparseArray.valueAt(i);
                appBatteryPolicy.handleUidBatteryUsage(keyAt, immutableBatteryUsage.mutate().subtract(this.mAppRestrictionController.getUidBatteryExemptedUsageSince(keyAt, max, elapsedRealtime, appBatteryPolicy.mBgCurrentDrainExemptedTypes)).calcPercentage(keyAt, appBatteryPolicy).unmutate());
            }
            int size2 = this.mDebugUidPercentages.size();
            for (int i2 = 0; i2 < size2; i2++) {
                appBatteryPolicy.handleUidBatteryUsage(this.mDebugUidPercentages.keyAt(i2), (ImmutableBatteryUsage) this.mDebugUidPercentages.valueAt(i2));
            }
        } finally {
            scheduleBatteryUsageStatsUpdateIfNecessary(this.mBatteryUsageStatsPollingIntervalMs);
        }
    }

    public final boolean updateBatteryUsageStatsIfNecessary(long j, boolean z) {
        boolean z2;
        synchronized (this.mLock) {
            if (this.mLastBatteryUsageSamplingTs + this.mBatteryUsageStatsPollingMinIntervalMs >= j && !z) {
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
        }
    }

    public final void updateBatteryUsageStatsOnce(long j) {
        boolean z;
        long j2;
        long j3;
        long j4;
        long j5;
        AppBatteryPolicy appBatteryPolicy = (AppBatteryPolicy) this.mInjector.getPolicy();
        ArraySet arraySet = this.mTmpUserIds;
        SparseArray sparseArray = this.mTmpUidBatteryUsage;
        BatteryStatsInternal batteryStatsInternal = this.mInjector.getBatteryStatsInternal();
        long j6 = appBatteryPolicy.mBgCurrentDrainWindowMs;
        sparseArray.clear();
        arraySet.clear();
        synchronized (this.mLock) {
            z = true;
            for (int size = this.mActiveUserIdStates.size() - 1; size >= 0; size--) {
                arraySet.add(UserHandle.of(this.mActiveUserIdStates.keyAt(size)));
                if (!this.mActiveUserIdStates.valueAt(size)) {
                    this.mActiveUserIdStates.removeAt(size);
                }
            }
        }
        BatteryUsageStats updateBatteryUsageStatsOnceInternal = updateBatteryUsageStatsOnceInternal(0L, sparseArray, new BatteryUsageStatsQuery.Builder().includeProcessStateData().setMaxStatsAgeMs(0L), arraySet, batteryStatsInternal);
        long statsStartTimestamp = updateBatteryUsageStatsOnceInternal != null ? updateBatteryUsageStatsOnceInternal.getStatsStartTimestamp() : 0L;
        long statsEndTimestamp = (updateBatteryUsageStatsOnceInternal != null ? updateBatteryUsageStatsOnceInternal.getStatsEndTimestamp() : j) - statsStartTimestamp;
        if (statsEndTimestamp >= j6) {
            synchronized (this.mLock) {
                j2 = j6;
                copyUidBatteryUsage(sparseArray, this.mUidBatteryUsageInWindow, (j6 * 1.0d) / statsEndTimestamp);
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
            updateBatteryUsageStatsOnceInternal(0L, sparseArray, new BatteryUsageStatsQuery.Builder().includeProcessStateData().aggregateSnapshots(j3, statsStartTimestamp), arraySet, batteryStatsInternal);
            j4 = statsEndTimestamp + (statsStartTimestamp - j3);
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
            int size2 = sparseArray.size();
            for (int i = 0; i < size2; i++) {
                int keyAt = sparseArray.keyAt(i);
                int indexOfKey = this.mUidBatteryUsage.indexOfKey(keyAt);
                BatteryUsage batteryUsage = (BatteryUsage) this.mLastUidBatteryUsage.get(keyAt, BATTERY_USAGE_NONE);
                BatteryUsage batteryUsage2 = (BatteryUsage) sparseArray.valueAt(i);
                if (indexOfKey >= 0) {
                    ((BatteryUsage) this.mUidBatteryUsage.valueAt(indexOfKey)).subtract(batteryUsage).add(batteryUsage2);
                } else {
                    this.mUidBatteryUsage.put(keyAt, batteryUsage2);
                }
            }
            copyUidBatteryUsage(this.mTmpUidBatteryUsage2, this.mLastUidBatteryUsage);
        }
        this.mTmpUidBatteryUsage2.clear();
        if (z) {
            long j7 = j - j5;
            long j8 = j3 - 1;
            updateBatteryUsageStatsOnceInternal(j8 - j7, sparseArray, new BatteryUsageStatsQuery.Builder().includeProcessStateData().aggregateSnapshots(j7, j8), arraySet, batteryStatsInternal);
            synchronized (this.mLock) {
                copyUidBatteryUsage(sparseArray, this.mUidBatteryUsageInWindow);
            }
        }
    }

    public final BatteryUsageStats updateBatteryUsageStatsOnceInternal(long j, SparseArray sparseArray, BatteryUsageStatsQuery.Builder builder, ArraySet arraySet, BatteryStatsInternal batteryStatsInternal) {
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            builder.addUser((UserHandle) arraySet.valueAt(i));
        }
        List batteryUsageStats = batteryStatsInternal.getBatteryUsageStats(Arrays.asList(builder.build()));
        if (ArrayUtils.isEmpty(batteryUsageStats)) {
            return null;
        }
        BatteryUsageStats batteryUsageStats2 = (BatteryUsageStats) batteryUsageStats.get(0);
        List<UidBatteryConsumer> uidBatteryConsumers = batteryUsageStats2.getUidBatteryConsumers();
        if (uidBatteryConsumers != null) {
            double min = j > 0 ? Math.min((j * 1.0d) / (batteryUsageStats2.getStatsEndTimestamp() - batteryUsageStats2.getStatsStartTimestamp()), 1.0d) : 1.0d;
            AppBatteryPolicy appBatteryPolicy = (AppBatteryPolicy) this.mInjector.getPolicy();
            for (UidBatteryConsumer uidBatteryConsumer : uidBatteryConsumers) {
                int uid = uidBatteryConsumer.getUid();
                if (!UserHandle.isIsolated(uid)) {
                    int appIdFromSharedAppGid = UserHandle.getAppIdFromSharedAppGid(uid);
                    if (appIdFromSharedAppGid > 0) {
                        uid = UserHandle.getUid(0, appIdFromSharedAppGid);
                    }
                    BatteryUsage scale = new BatteryUsage(uidBatteryConsumer, appBatteryPolicy).scale(min);
                    int indexOfKey = sparseArray.indexOfKey(uid);
                    if (indexOfKey < 0) {
                        sparseArray.put(uid, scale);
                    } else {
                        ((BatteryUsage) sparseArray.valueAt(indexOfKey)).add(scale);
                    }
                }
            }
        }
        return batteryUsageStats2;
    }

    public static void copyUidBatteryUsage(SparseArray sparseArray, SparseArray sparseArray2) {
        sparseArray2.clear();
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            sparseArray2.put(sparseArray.keyAt(size), new ImmutableBatteryUsage((BatteryUsage) sparseArray.valueAt(size)));
        }
    }

    public static void copyUidBatteryUsage(SparseArray sparseArray, SparseArray sparseArray2, double d) {
        sparseArray2.clear();
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            sparseArray2.put(sparseArray.keyAt(size), new ImmutableBatteryUsage((BatteryUsage) sparseArray.valueAt(size), d));
        }
    }

    public final void onCurrentDrainMonitorEnabled(boolean z) {
        if (z) {
            if (this.mBgHandler.hasCallbacks(this.mBgBatteryUsageStatsPolling)) {
                return;
            }
            this.mBgHandler.postDelayed(this.mBgBatteryUsageStatsPolling, this.mBatteryUsageStatsPollingIntervalMs);
            return;
        }
        this.mBgHandler.removeCallbacks(this.mBgBatteryUsageStatsPolling);
        synchronized (this.mLock) {
            if (this.mBatteryUsageStatsUpdatePending) {
                try {
                    this.mLock.wait();
                } catch (InterruptedException unused) {
                }
            }
            this.mUidBatteryUsage.clear();
            this.mUidBatteryUsageInWindow.clear();
            this.mLastUidBatteryUsage.clear();
            this.mLastBatteryUsageSamplingTs = 0L;
            this.mLastUidBatteryUsageStartTs = 0L;
        }
    }

    public void setDebugUidPercentage(int[] iArr, double[][] dArr) {
        this.mDebugUidPercentages.clear();
        for (int i = 0; i < iArr.length; i++) {
            this.mDebugUidPercentages.put(iArr[i], new BatteryUsage().setPercentage(dArr[i]).unmutate());
        }
        scheduleBgBatteryUsageStatsCheck();
    }

    public void clearDebugUidPercentage() {
        this.mDebugUidPercentages.clear();
        scheduleBgBatteryUsageStatsCheck();
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

    @Override // com.android.server.am.BaseAppStateTracker
    public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.println("APP BATTERY STATE TRACKER:");
        updateBatteryUsageStatsIfNecessary(this.mInjector.currentTimeMillis(), true);
        scheduleBgBatteryUsageStatsCheck();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.mBgHandler.getLooper().getQueue().addIdleHandler(new MessageQueue.IdleHandler() { // from class: com.android.server.am.AppBatteryTracker$$ExternalSyntheticLambda0
            @Override // android.os.MessageQueue.IdleHandler
            public final boolean queueIdle() {
                boolean lambda$dump$0;
                lambda$dump$0 = AppBatteryTracker.lambda$dump$0(countDownLatch);
                return lambda$dump$0;
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException unused) {
        }
        synchronized (this.mLock) {
            SparseArray sparseArray = this.mUidBatteryUsageInWindow;
            printWriter.print("  " + str);
            printWriter.print("  Last battery usage start=");
            TimeUtils.dumpTime(printWriter, this.mLastUidBatteryUsageStartTs);
            printWriter.println();
            printWriter.print("  " + str);
            printWriter.print("Battery usage over last ");
            String str2 = "    " + str;
            AppBatteryPolicy appBatteryPolicy = (AppBatteryPolicy) this.mInjector.getPolicy();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long max = Math.max(0L, elapsedRealtime - appBatteryPolicy.mBgCurrentDrainWindowMs);
            printWriter.println(TimeUtils.formatDuration(elapsedRealtime - max));
            if (sparseArray.size() == 0) {
                printWriter.print(str2);
                printWriter.println("(none)");
            } else {
                int i = 0;
                for (int size = sparseArray.size(); i < size; size = size) {
                    int keyAt = sparseArray.keyAt(i);
                    BatteryUsage calcPercentage = ((ImmutableBatteryUsage) sparseArray.valueAt(i)).calcPercentage(keyAt, appBatteryPolicy);
                    BatteryUsage calcPercentage2 = this.mAppRestrictionController.getUidBatteryExemptedUsageSince(keyAt, max, elapsedRealtime, appBatteryPolicy.mBgCurrentDrainExemptedTypes).calcPercentage(keyAt, appBatteryPolicy);
                    BatteryUsage calcPercentage3 = new BatteryUsage(calcPercentage).subtract(calcPercentage2).calcPercentage(keyAt, appBatteryPolicy);
                    printWriter.format("%s%s: [%s] %s (%s) | %s (%s) | %s (%s) | %s\n", str2, UserHandle.formatUid(keyAt), PowerExemptionManager.reasonCodeToString(appBatteryPolicy.shouldExemptUid(keyAt)), calcPercentage.toString(), calcPercentage.percentageToString(), calcPercentage2.toString(), calcPercentage2.percentageToString(), calcPercentage3.toString(), calcPercentage3.percentageToString(), ((BatteryUsage) this.mUidBatteryUsage.get(keyAt, BATTERY_USAGE_NONE)).toString());
                    i++;
                }
            }
        }
        super.dump(printWriter, str);
    }

    public static /* synthetic */ boolean lambda$dump$0(CountDownLatch countDownLatch) {
        countDownLatch.countDown();
        return false;
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public void dumpAsProto(ProtoOutputStream protoOutputStream, int i) {
        updateBatteryUsageStatsIfNecessary(this.mInjector.currentTimeMillis(), true);
        synchronized (this.mLock) {
            SparseArray sparseArray = this.mUidBatteryUsageInWindow;
            if (i != -1) {
                BatteryUsage batteryUsage = (BatteryUsage) sparseArray.get(i);
                if (batteryUsage != null) {
                    dumpUidStats(protoOutputStream, i, batteryUsage);
                }
            } else {
                int size = sparseArray.size();
                for (int i2 = 0; i2 < size; i2++) {
                    dumpUidStats(protoOutputStream, sparseArray.keyAt(i2), (BatteryUsage) sparseArray.valueAt(i2));
                }
            }
        }
    }

    public final void dumpUidStats(ProtoOutputStream protoOutputStream, int i, BatteryUsage batteryUsage) {
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

    public final void dumpProcessStateStats(ProtoOutputStream protoOutputStream, int i, double d) {
        if (d == 0.0d) {
            return;
        }
        long start = protoOutputStream.start(2246267895810L);
        protoOutputStream.write(1159641169921L, i);
        protoOutputStream.write(1103806595075L, d);
        protoOutputStream.end(start);
    }

    public class BatteryUsage {
        public static final BatteryConsumer.Dimensions[] BATT_DIMENS = {new BatteryConsumer.Dimensions(-1, 0), new BatteryConsumer.Dimensions(-1, 1), new BatteryConsumer.Dimensions(-1, 2), new BatteryConsumer.Dimensions(-1, 3), new BatteryConsumer.Dimensions(-1, 4)};
        public double[] mPercentage;
        public double[] mUsage;

        public BatteryUsage() {
            this(0.0d, 0.0d, 0.0d, 0.0d, 0.0d);
        }

        public BatteryUsage(double d, double d2, double d3, double d4, double d5) {
            this.mUsage = new double[]{d, d2, d3, d4, d5};
        }

        public BatteryUsage(BatteryUsage batteryUsage, double d) {
            this(batteryUsage);
            scaleInternal(d);
        }

        public BatteryUsage(BatteryUsage batteryUsage) {
            this.mUsage = new double[batteryUsage.mUsage.length];
            setToInternal(batteryUsage);
        }

        public BatteryUsage(UidBatteryConsumer uidBatteryConsumer, AppBatteryPolicy appBatteryPolicy) {
            BatteryConsumer.Dimensions[] dimensionsArr = appBatteryPolicy.mBatteryDimensions;
            this.mUsage = new double[]{getConsumedPowerNoThrow(uidBatteryConsumer, dimensionsArr[0]), getConsumedPowerNoThrow(uidBatteryConsumer, dimensionsArr[1]), getConsumedPowerNoThrow(uidBatteryConsumer, dimensionsArr[2]), getConsumedPowerNoThrow(uidBatteryConsumer, dimensionsArr[3]), getConsumedPowerNoThrow(uidBatteryConsumer, dimensionsArr[4])};
        }

        public BatteryUsage setTo(BatteryUsage batteryUsage) {
            return setToInternal(batteryUsage);
        }

        public final BatteryUsage setToInternal(BatteryUsage batteryUsage) {
            double[] dArr = batteryUsage.mUsage;
            System.arraycopy(dArr, 0, this.mUsage, 0, dArr.length);
            double[] dArr2 = batteryUsage.mPercentage;
            if (dArr2 != null) {
                double[] dArr3 = new double[dArr2.length];
                this.mPercentage = dArr3;
                double[] dArr4 = batteryUsage.mPercentage;
                System.arraycopy(dArr4, 0, dArr3, 0, dArr4.length);
            } else {
                this.mPercentage = null;
            }
            return this;
        }

        public BatteryUsage add(BatteryUsage batteryUsage) {
            int i = 0;
            while (true) {
                double[] dArr = batteryUsage.mUsage;
                if (i >= dArr.length) {
                    return this;
                }
                double[] dArr2 = this.mUsage;
                dArr2[i] = dArr2[i] + dArr[i];
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

        public BatteryUsage scale(double d) {
            return scaleInternal(d);
        }

        public final BatteryUsage scaleInternal(double d) {
            int i = 0;
            while (true) {
                double[] dArr = this.mUsage;
                if (i >= dArr.length) {
                    return this;
                }
                dArr[i] = dArr[i] * d;
                i++;
            }
        }

        public ImmutableBatteryUsage unmutate() {
            return new ImmutableBatteryUsage(this);
        }

        public BatteryUsage calcPercentage(int i, AppBatteryPolicy appBatteryPolicy) {
            double[] dArr = this.mPercentage;
            if (dArr == null || dArr.length != this.mUsage.length) {
                this.mPercentage = new double[this.mUsage.length];
            }
            appBatteryPolicy.calcPercentage(i, this.mUsage, this.mPercentage);
            return this;
        }

        public BatteryUsage setPercentage(double[] dArr) {
            this.mPercentage = dArr;
            return this;
        }

        public double[] getPercentage() {
            return this.mPercentage;
        }

        public String percentageToString() {
            return formatBatteryUsagePercentage(this.mPercentage);
        }

        public String toString() {
            return formatBatteryUsage(this.mUsage);
        }

        public double getUsagePowerMah(int i) {
            if (i == 1) {
                return this.mUsage[1];
            }
            if (i == 2) {
                return this.mUsage[2];
            }
            if (i == 3) {
                return this.mUsage[3];
            }
            if (i != 4) {
                return 0.0d;
            }
            return this.mUsage[4];
        }

        public boolean isEmpty() {
            int i = 0;
            while (true) {
                double[] dArr = this.mUsage;
                if (i >= dArr.length) {
                    return true;
                }
                if (dArr[i] > 0.0d) {
                    return false;
                }
                i++;
            }
        }

        public boolean equals(Object obj) {
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

        public int hashCode() {
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

        public static String formatBatteryUsage(double[] dArr) {
            return String.format("%.3f %.3f %.3f %.3f %.3f mAh", Double.valueOf(dArr[0]), Double.valueOf(dArr[1]), Double.valueOf(dArr[2]), Double.valueOf(dArr[3]), Double.valueOf(dArr[4]));
        }

        public static String formatBatteryUsagePercentage(double[] dArr) {
            return String.format("%4.2f%% %4.2f%% %4.2f%% %4.2f%% %4.2f%%", Double.valueOf(dArr[0]), Double.valueOf(dArr[1]), Double.valueOf(dArr[2]), Double.valueOf(dArr[3]), Double.valueOf(dArr[4]));
        }

        public static double getConsumedPowerNoThrow(UidBatteryConsumer uidBatteryConsumer, BatteryConsumer.Dimensions dimensions) {
            try {
                return uidBatteryConsumer.getConsumedPower(dimensions);
            } catch (IllegalArgumentException unused) {
                return 0.0d;
            }
        }
    }

    public final class ImmutableBatteryUsage extends BatteryUsage {
        public ImmutableBatteryUsage() {
        }

        public ImmutableBatteryUsage(BatteryUsage batteryUsage, double d) {
            super(batteryUsage, d);
        }

        public ImmutableBatteryUsage(BatteryUsage batteryUsage) {
            super(batteryUsage);
        }

        @Override // com.android.server.am.AppBatteryTracker.BatteryUsage
        public BatteryUsage setTo(BatteryUsage batteryUsage) {
            throw new RuntimeException("Readonly");
        }

        @Override // com.android.server.am.AppBatteryTracker.BatteryUsage
        public BatteryUsage add(BatteryUsage batteryUsage) {
            throw new RuntimeException("Readonly");
        }

        @Override // com.android.server.am.AppBatteryTracker.BatteryUsage
        public BatteryUsage subtract(BatteryUsage batteryUsage) {
            throw new RuntimeException("Readonly");
        }

        @Override // com.android.server.am.AppBatteryTracker.BatteryUsage
        public BatteryUsage scale(double d) {
            throw new RuntimeException("Readonly");
        }

        @Override // com.android.server.am.AppBatteryTracker.BatteryUsage
        public BatteryUsage setPercentage(double[] dArr) {
            throw new RuntimeException("Readonly");
        }

        public BatteryUsage mutate() {
            return new BatteryUsage(this);
        }
    }

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

        public AppBatteryPolicy(BaseAppStateTracker.Injector injector, AppBatteryTracker appBatteryTracker) {
            super(injector, appBatteryTracker, "bg_current_drain_monitor_enabled", appBatteryTracker.mContext.getResources().getBoolean(R.bool.config_batterySaver_full_disableAod));
            this.mBgCurrentDrainRestrictedBucketThreshold = new float[2];
            this.mBgCurrentDrainBgRestrictedThreshold = new float[2];
            this.mHighBgBatteryPackages = new SparseArray();
            this.mLastInteractionTime = new SparseLongArray();
            this.mLock = appBatteryTracker.mLock;
            Resources resources = appBatteryTracker.mContext.getResources();
            float[] floatArray = getFloatArray(resources.obtainTypedArray(R.array.config_protectedNetworks));
            float f = ActivityManager.isLowRamDeviceStatic() ? floatArray[1] : floatArray[0];
            this.mDefaultBgCurrentDrainRestrictedBucket = f;
            float[] floatArray2 = getFloatArray(resources.obtainTypedArray(R.array.config_priorityOnlyDndExemptPackages));
            float f2 = ActivityManager.isLowRamDeviceStatic() ? floatArray2[1] : floatArray2[0];
            this.mDefaultBgCurrentDrainBgRestrictedThreshold = f2;
            long integer = resources.getInteger(R.integer.config_criticalBatteryWarningLevel) * 1000;
            this.mDefaultBgCurrentDrainWindowMs = integer;
            this.mDefaultBgCurrentDrainInteractionGracePeriodMs = integer;
            float[] floatArray3 = getFloatArray(resources.obtainTypedArray(R.array.config_primaryCredentialProviderService));
            float f3 = ActivityManager.isLowRamDeviceStatic() ? floatArray3[1] : floatArray3[0];
            this.mDefaultBgCurrentDrainRestrictedBucketHighThreshold = f3;
            float[] floatArray4 = getFloatArray(resources.obtainTypedArray(R.array.config_perDeviceStateRotationLockDefaults));
            float f4 = ActivityManager.isLowRamDeviceStatic() ? floatArray4[1] : floatArray4[0];
            this.mDefaultBgCurrentDrainBgRestrictedHighThreshold = f4;
            long integer2 = resources.getInteger(R.integer.config_carDockKeepsScreenOn) * 1000;
            this.mDefaultBgCurrentDrainMediaPlaybackMinDuration = integer2;
            long integer3 = resources.getInteger(R.integer.config_cameraPrivacyLightAlsAveragingIntervalMillis) * 1000;
            this.mDefaultBgCurrentDrainLocationMinDuration = integer3;
            this.mDefaultBgCurrentDrainEventDurationBasedThresholdEnabled = resources.getBoolean(R.bool.config_batterySaver_full_deferKeyValueBackup);
            this.mDefaultBgCurrentDrainAutoRestrictAbusiveAppsEnabled = resources.getBoolean(R.bool.config_batterySaver_full_deferFullBackup);
            this.mDefaultCurrentDrainTypesToRestrictedBucket = resources.getInteger(R.integer.config_chooser_max_targets_per_row);
            this.mDefaultBgCurrentDrainTypesToBgRestricted = resources.getInteger(R.integer.config_cdma_3waycall_flash_delay);
            this.mDefaultBgCurrentDrainPowerComponent = resources.getInteger(R.integer.config_carDockRotation);
            this.mDefaultBgCurrentDrainExemptedTypes = resources.getInteger(R.integer.config_cameraLiftTriggerSensorType);
            this.mDefaultBgCurrentDrainHighThresholdByBgLocation = resources.getBoolean(R.bool.config_batterySaver_full_disableAnimation);
            this.mBgCurrentDrainRestrictedBucketThreshold[0] = f;
            this.mBgCurrentDrainRestrictedBucketThreshold[1] = f3;
            this.mBgCurrentDrainBgRestrictedThreshold[0] = f2;
            this.mBgCurrentDrainBgRestrictedThreshold[1] = f4;
            this.mBgCurrentDrainWindowMs = integer;
            this.mBgCurrentDrainInteractionGracePeriodMs = integer;
            this.mBgCurrentDrainMediaPlaybackMinDuration = integer2;
            this.mBgCurrentDrainLocationMinDuration = integer3;
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
        public void onPropertiesChanged(String str) {
            str.hashCode();
            switch (str) {
                case "bg_current_drain_event_duration_based_threshold_enabled":
                    updateCurrentDrainEventDurationBasedThresholdEnabled();
                    break;
                case "bg_current_drain_decouple_thresholds":
                    updateCurrentDrainDecoupleThresholds();
                    break;
                case "bg_current_drain_media_playback_min_duration":
                    updateCurrentDrainMediaPlaybackMinDuration();
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
                    updateCurrentDrainLocationMinDuration();
                    break;
                case "bg_current_drain_auto_restrict_abusive_apps_enabled":
                    updateBgCurrentDrainAutoRestrictAbusiveAppsEnabled();
                    break;
                case "bg_current_drain_interaction_grace_period":
                    updateCurrentDrainInteractionGracePeriod();
                    break;
                case "bg_current_drain_exempted_types":
                    updateCurrentDrainExemptedTypes();
                    break;
                case "bg_current_drain_window":
                    updateCurrentDrainWindow();
                    break;
                default:
                    super.onPropertiesChanged(str);
                    break;
            }
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void updateTrackerEnabled() {
            if (this.mBatteryFullChargeMah > 0) {
                super.updateTrackerEnabled();
            } else {
                this.mTrackerEnabled = false;
                onTrackerEnabled(false);
            }
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void onTrackerEnabled(boolean z) {
            ((AppBatteryTracker) this.mTracker).onCurrentDrainMonitorEnabled(z);
        }

        public final void updateCurrentDrainThreshold() {
            this.mBgCurrentDrainRestrictedBucketThreshold[0] = DeviceConfig.getFloat("activity_manager", "bg_current_drain_threshold_to_restricted_bucket", this.mDefaultBgCurrentDrainRestrictedBucket);
            this.mBgCurrentDrainRestrictedBucketThreshold[1] = DeviceConfig.getFloat("activity_manager", "bg_current_drain_high_threshold_to_restricted_bucket", this.mDefaultBgCurrentDrainRestrictedBucketHighThreshold);
            this.mBgCurrentDrainBgRestrictedThreshold[0] = DeviceConfig.getFloat("activity_manager", "bg_current_drain_threshold_to_bg_restricted", this.mDefaultBgCurrentDrainBgRestrictedThreshold);
            this.mBgCurrentDrainBgRestrictedThreshold[1] = DeviceConfig.getFloat("activity_manager", "bg_current_drain_high_threshold_to_bg_restricted", this.mDefaultBgCurrentDrainBgRestrictedHighThreshold);
            this.mBgCurrentDrainRestrictedBucketTypes = DeviceConfig.getInt("activity_manager", "bg_current_drain_types_to_restricted_bucket", this.mDefaultCurrentDrainTypesToRestrictedBucket);
            this.mBgCurrentDrainBgRestrictedTypes = DeviceConfig.getInt("activity_manager", "bg_current_drain_types_to_bg_restricted", this.mDefaultBgCurrentDrainTypesToBgRestricted);
            this.mBgCurrentDrainPowerComponents = DeviceConfig.getInt("activity_manager", "bg_current_drain_power_components", this.mDefaultBgCurrentDrainPowerComponent);
            if (this.mBgCurrentDrainPowerComponents == -1) {
                this.mBatteryDimensions = BatteryUsage.BATT_DIMENS;
            } else {
                this.mBatteryDimensions = new BatteryConsumer.Dimensions[5];
                for (int i = 0; i < 5; i++) {
                    this.mBatteryDimensions[i] = new BatteryConsumer.Dimensions(this.mBgCurrentDrainPowerComponents, i);
                }
            }
            this.mBgCurrentDrainHighThresholdByBgLocation = DeviceConfig.getBoolean("activity_manager", "bg_current_drain_high_threshold_by_bg_location", this.mDefaultBgCurrentDrainHighThresholdByBgLocation);
        }

        public final void updateCurrentDrainWindow() {
            this.mBgCurrentDrainWindowMs = DeviceConfig.getLong("activity_manager", "bg_current_drain_window", this.mDefaultBgCurrentDrainWindowMs);
        }

        public final void updateCurrentDrainInteractionGracePeriod() {
            this.mBgCurrentDrainInteractionGracePeriodMs = DeviceConfig.getLong("activity_manager", "bg_current_drain_interaction_grace_period", this.mDefaultBgCurrentDrainInteractionGracePeriodMs);
        }

        public final void updateCurrentDrainMediaPlaybackMinDuration() {
            this.mBgCurrentDrainMediaPlaybackMinDuration = DeviceConfig.getLong("activity_manager", "bg_current_drain_media_playback_min_duration", this.mDefaultBgCurrentDrainMediaPlaybackMinDuration);
        }

        public final void updateCurrentDrainLocationMinDuration() {
            this.mBgCurrentDrainLocationMinDuration = DeviceConfig.getLong("activity_manager", "bg_current_drain_location_min_duration", this.mDefaultBgCurrentDrainLocationMinDuration);
        }

        public final void updateCurrentDrainEventDurationBasedThresholdEnabled() {
            this.mBgCurrentDrainEventDurationBasedThresholdEnabled = DeviceConfig.getBoolean("activity_manager", "bg_current_drain_event_duration_based_threshold_enabled", this.mDefaultBgCurrentDrainEventDurationBasedThresholdEnabled);
        }

        public final void updateCurrentDrainExemptedTypes() {
            this.mBgCurrentDrainExemptedTypes = DeviceConfig.getInt("activity_manager", "bg_current_drain_exempted_types", this.mDefaultBgCurrentDrainExemptedTypes);
        }

        public final void updateCurrentDrainDecoupleThresholds() {
            this.mBgCurrentDrainDecoupleThresholds = DeviceConfig.getBoolean("activity_manager", "bg_current_drain_decouple_thresholds", true);
        }

        public final void updateBgCurrentDrainAutoRestrictAbusiveAppsEnabled() {
            this.mBgCurrentDrainAutoRestrictAbusiveAppsEnabled = DeviceConfig.getBoolean("activity_manager", "bg_current_drain_auto_restrict_abusive_apps_enabled", this.mDefaultBgCurrentDrainAutoRestrictAbusiveAppsEnabled);
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void onSystemReady() {
            this.mBatteryFullChargeMah = this.mInjector.getBatteryManagerInternal().getBatteryFullCharge() / 1000;
            super.onSystemReady();
            updateCurrentDrainThreshold();
            updateCurrentDrainWindow();
            updateCurrentDrainInteractionGracePeriod();
            updateCurrentDrainMediaPlaybackMinDuration();
            updateCurrentDrainLocationMinDuration();
            updateCurrentDrainEventDurationBasedThresholdEnabled();
            updateCurrentDrainExemptedTypes();
            updateCurrentDrainDecoupleThresholds();
            updateBgCurrentDrainAutoRestrictAbusiveAppsEnabled();
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public int getProposedRestrictionLevel(String str, int i, int i2) {
            boolean z = false;
            if (i2 <= 30) {
                return 0;
            }
            synchronized (this.mLock) {
                Pair pair = (Pair) this.mHighBgBatteryPackages.get(i);
                if (pair != null) {
                    long j = this.mLastInteractionTime.get(i, 0L);
                    long[] jArr = (long[]) pair.first;
                    boolean z2 = jArr[0] > j + this.mBgCurrentDrainInteractionGracePeriodMs;
                    if (((AppBatteryTracker) this.mTracker).mAppRestrictionController.isAutoRestrictAbusiveAppEnabled() && this.mBgCurrentDrainAutoRestrictAbusiveAppsEnabled) {
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
            }
        }

        public double[] calcPercentage(int i, double[] dArr, double[] dArr2) {
            BatteryUsage batteryUsage = i > 0 ? (BatteryUsage) ((AppBatteryTracker) this.mTracker).mDebugUidPercentages.get(i) : null;
            double[] percentage = batteryUsage != null ? batteryUsage.getPercentage() : null;
            for (int i2 = 0; i2 < dArr.length; i2++) {
                dArr2[i2] = percentage != null ? percentage[i2] : (dArr[i2] / this.mBatteryFullChargeMah) * 100.0d;
            }
            return dArr2;
        }

        public final double sumPercentageOfTypes(double[] dArr, int i) {
            int highestOneBit = Integer.highestOneBit(i);
            double d = 0.0d;
            while (highestOneBit != 0) {
                d += dArr[Integer.numberOfTrailingZeros(highestOneBit)];
                i &= ~highestOneBit;
                highestOneBit = Integer.highestOneBit(i);
            }
            return d;
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
                } else if (highestOneBit == 16) {
                    sb.append("CACHED");
                } else {
                    return "[UNKNOWN(" + Integer.toHexString(i) + ")]";
                }
                i &= ~highestOneBit;
                highestOneBit = Integer.highestOneBit(i);
            }
            sb.append("]");
            return sb.toString();
        }

        public void handleUidBatteryUsage(int i, ImmutableBatteryUsage immutableBatteryUsage) {
            boolean z;
            boolean z2;
            long[] jArr;
            ImmutableBatteryUsage[] immutableBatteryUsageArr;
            if (shouldExemptUid(i) != -1) {
                return;
            }
            double sumPercentageOfTypes = sumPercentageOfTypes(immutableBatteryUsage.getPercentage(), this.mBgCurrentDrainRestrictedBucketTypes);
            double sumPercentageOfTypes2 = sumPercentageOfTypes(immutableBatteryUsage.getPercentage(), this.mBgCurrentDrainBgRestrictedTypes);
            synchronized (this.mLock) {
                int restrictionLevel = ((AppBatteryTracker) this.mTracker).mAppRestrictionController.getRestrictionLevel(i);
                if (restrictionLevel >= 50) {
                    return;
                }
                long j = this.mLastInteractionTime.get(i, 0L);
                long elapsedRealtime = SystemClock.elapsedRealtime();
                int currentDrainThresholdIndex = getCurrentDrainThresholdIndex(i, elapsedRealtime, this.mBgCurrentDrainWindowMs);
                int indexOfKey = this.mHighBgBatteryPackages.indexOfKey(i);
                boolean z3 = this.mBgCurrentDrainDecoupleThresholds;
                double d = this.mBgCurrentDrainRestrictedBucketThreshold[currentDrainThresholdIndex];
                double d2 = this.mBgCurrentDrainBgRestrictedThreshold[currentDrainThresholdIndex];
                boolean z4 = false;
                if (indexOfKey < 0) {
                    if (sumPercentageOfTypes >= d) {
                        if (elapsedRealtime > j + this.mBgCurrentDrainInteractionGracePeriodMs) {
                            jArr = new long[]{elapsedRealtime, 0};
                            immutableBatteryUsageArr = new ImmutableBatteryUsage[2];
                            immutableBatteryUsageArr[0] = immutableBatteryUsage;
                            this.mHighBgBatteryPackages.put(i, Pair.create(jArr, immutableBatteryUsageArr));
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
                    if (z3 && sumPercentageOfTypes2 >= d2) {
                        if (jArr == null) {
                            jArr = new long[2];
                            immutableBatteryUsageArr = new ImmutableBatteryUsage[2];
                            this.mHighBgBatteryPackages.put(i, Pair.create(jArr, immutableBatteryUsageArr));
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
                    if (sumPercentageOfTypes >= d) {
                        if (elapsedRealtime > j + this.mBgCurrentDrainInteractionGracePeriodMs) {
                            if (j2 == 0) {
                                jArr2[0] = elapsedRealtime;
                                ((ImmutableBatteryUsage[]) pair.second)[0] = immutableBatteryUsage;
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
                    if (sumPercentageOfTypes2 >= d2) {
                        if (z3 || (restrictionLevel == 40 && elapsedRealtime > j2 + this.mBgCurrentDrainWindowMs)) {
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
                    ((AppBatteryTracker) this.mTracker).mAppRestrictionController.refreshAppRestrictionLevelForUid(i, FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM, 2, true);
                }
            }
        }

        public final int getCurrentDrainThresholdIndex(int i, long j, long j2) {
            return (hasMediaPlayback(i, j, j2) || hasLocation(i, j, j2)) ? 1 : 0;
        }

        public final boolean hasMediaPlayback(int i, long j, long j2) {
            return this.mBgCurrentDrainEventDurationBasedThresholdEnabled && ((AppBatteryTracker) this.mTracker).mAppRestrictionController.getCompositeMediaPlaybackDurations(i, j, j2) >= this.mBgCurrentDrainMediaPlaybackMinDuration;
        }

        public final boolean hasLocation(int i, long j, long j2) {
            if (!this.mBgCurrentDrainHighThresholdByBgLocation) {
                return false;
            }
            AppRestrictionController appRestrictionController = ((AppBatteryTracker) this.mTracker).mAppRestrictionController;
            if (this.mInjector.getPermissionManagerServiceInternal().checkUidPermission(i, "android.permission.ACCESS_BACKGROUND_LOCATION") == 0) {
                return true;
            }
            return this.mBgCurrentDrainEventDurationBasedThresholdEnabled && appRestrictionController.getForegroundServiceTotalDurationsSince(i, Math.max(0L, j - j2), j, 8) >= this.mBgCurrentDrainLocationMinDuration;
        }

        public void onUserInteractionStarted(String str, int i) {
            int indexOfKey;
            boolean z;
            synchronized (this.mLock) {
                this.mLastInteractionTime.put(i, SystemClock.elapsedRealtime());
                if (((AppBatteryTracker) this.mTracker).mAppRestrictionController.getRestrictionLevel(i, str) != 50 && (indexOfKey = this.mHighBgBatteryPackages.indexOfKey(i)) >= 0) {
                    this.mHighBgBatteryPackages.removeAt(indexOfKey);
                    z = true;
                }
                z = false;
            }
            if (z) {
                ((AppBatteryTracker) this.mTracker).mAppRestrictionController.refreshAppRestrictionLevelForUid(i, FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_USAGE, 3, true);
            }
        }

        public void onBackgroundRestrictionChanged(int i, String str, boolean z) {
            if (z) {
                return;
            }
            synchronized (this.mLock) {
                Pair pair = (Pair) this.mHighBgBatteryPackages.get(i);
                if (pair != null) {
                    ((long[]) pair.first)[1] = 0;
                    ((ImmutableBatteryUsage[]) pair.second)[1] = null;
                }
            }
        }

        public void reset() {
            this.mHighBgBatteryPackages.clear();
            this.mLastInteractionTime.clear();
            ((AppBatteryTracker) this.mTracker).reset();
        }

        public void onUserRemovedLocked(int i) {
            for (int size = this.mHighBgBatteryPackages.size() - 1; size >= 0; size--) {
                if (UserHandle.getUserId(this.mHighBgBatteryPackages.keyAt(size)) == i) {
                    this.mHighBgBatteryPackages.removeAt(size);
                }
            }
            for (int size2 = this.mLastInteractionTime.size() - 1; size2 >= 0; size2--) {
                if (UserHandle.getUserId(this.mLastInteractionTime.keyAt(size2)) == i) {
                    this.mLastInteractionTime.removeAt(size2);
                }
            }
        }

        public void onUidRemovedLocked(int i) {
            this.mHighBgBatteryPackages.remove(i);
            this.mLastInteractionTime.delete(i);
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void dump(PrintWriter printWriter, String str) {
            printWriter.print(str);
            printWriter.println("APP BATTERY TRACKER POLICY SETTINGS:");
            String str2 = "  " + str;
            super.dump(printWriter, str2);
            if (isEnabled()) {
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
                printWriter.println(this.mBgCurrentDrainWindowMs);
                printWriter.print(str2);
                printWriter.print("bg_current_drain_interaction_grace_period");
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainInteractionGracePeriodMs);
                printWriter.print(str2);
                printWriter.print("bg_current_drain_media_playback_min_duration");
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainMediaPlaybackMinDuration);
                printWriter.print(str2);
                printWriter.print("bg_current_drain_location_min_duration");
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainLocationMinDuration);
                printWriter.print(str2);
                printWriter.print("bg_current_drain_event_duration_based_threshold_enabled");
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainEventDurationBasedThresholdEnabled);
                printWriter.print(str2);
                printWriter.print("bg_current_drain_auto_restrict_abusive_apps_enabled");
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainAutoRestrictAbusiveAppsEnabled);
                printWriter.print(str2);
                printWriter.print("bg_current_drain_types_to_restricted_bucket");
                printWriter.print('=');
                printWriter.println(batteryUsageTypesToString(this.mBgCurrentDrainRestrictedBucketTypes));
                printWriter.print(str2);
                printWriter.print("bg_current_drain_types_to_bg_restricted");
                printWriter.print('=');
                printWriter.println(batteryUsageTypesToString(this.mBgCurrentDrainBgRestrictedTypes));
                printWriter.print(str2);
                printWriter.print("bg_current_drain_power_components");
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainPowerComponents);
                printWriter.print(str2);
                printWriter.print("bg_current_drain_exempted_types");
                printWriter.print('=');
                printWriter.println(BaseAppStateTracker.stateTypesToString(this.mBgCurrentDrainExemptedTypes));
                printWriter.print(str2);
                printWriter.print("bg_current_drain_high_threshold_by_bg_location");
                printWriter.print('=');
                printWriter.println(this.mBgCurrentDrainHighThresholdByBgLocation);
                printWriter.print(str2);
                printWriter.print("Full charge capacity=");
                printWriter.print(this.mBatteryFullChargeMah);
                printWriter.println(" mAh");
                printWriter.print(str2);
                printWriter.println("Excessive current drain detected:");
                synchronized (this.mLock) {
                    int size = this.mHighBgBatteryPackages.size();
                    String str3 = "  " + str2;
                    if (size > 0) {
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        int i = 0;
                        while (i < size) {
                            int keyAt = this.mHighBgBatteryPackages.keyAt(i);
                            Pair pair = (Pair) this.mHighBgBatteryPackages.valueAt(i);
                            long[] jArr = (long[]) pair.first;
                            ImmutableBatteryUsage[] immutableBatteryUsageArr = (ImmutableBatteryUsage[]) pair.second;
                            int currentDrainThresholdIndex = getCurrentDrainThresholdIndex(keyAt, elapsedRealtime, this.mBgCurrentDrainWindowMs);
                            Object[] objArr = new Object[6];
                            objArr[c] = str3;
                            objArr[1] = UserHandle.formatUid(keyAt);
                            objArr[2] = Float.valueOf(this.mBgCurrentDrainRestrictedBucketThreshold[currentDrainThresholdIndex]);
                            objArr[3] = Float.valueOf(this.mBgCurrentDrainBgRestrictedThreshold[currentDrainThresholdIndex]);
                            objArr[4] = formatHighBgBatteryRecord(jArr[c], elapsedRealtime, immutableBatteryUsageArr[c]);
                            objArr[5] = formatHighBgBatteryRecord(jArr[1], elapsedRealtime, immutableBatteryUsageArr[1]);
                            printWriter.format("%s%s: (threshold=%4.2f%%/%4.2f%%) %s / %s\n", objArr);
                            i++;
                            c = 0;
                        }
                    } else {
                        printWriter.print(str3);
                        printWriter.println("(none)");
                    }
                }
            }
        }

        public final String formatHighBgBatteryRecord(long j, long j2, ImmutableBatteryUsage immutableBatteryUsage) {
            return (j <= 0 || immutableBatteryUsage == null) ? "0" : String.format("%s %s (%s)", TimeUtils.formatTime(j, j2), immutableBatteryUsage.toString(), immutableBatteryUsage.percentageToString());
        }
    }
}

package com.android.server.am;

import android.app.IApplicationThread;
import android.app.servertransaction.ClientTransaction;
import android.app.servertransaction.ConfigurationChangeItem;
import android.content.pm.ApplicationInfo;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Slog;

import com.android.server.wm.ClientLifecycleManager;
import com.android.server.wm.WindowProcessController;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProcessStateRecord {
    public int mAdjSeq;
    public Object mAdjSource;
    public int mAdjSourceProcState;
    public Object mAdjTarget;
    public String mAdjType;
    public int mAdjTypeCode;
    public final ProcessRecord mApp;
    public long mCacheOomRankerRss;
    public long mCacheOomRankerRssTimeMs;
    public int mCacheOomRankerUseCount;
    public int mCompletedAdjSeq;
    public boolean mContainsCycle;
    public long mFgInteractionTime;
    public Object mForcingToImportant;
    public boolean mHasForegroundActivities;
    public boolean mHasOverlayUi;
    public boolean mHasShownUi;
    public boolean mHasStartedServices;
    public boolean mHasTopUi;
    public long mInteractionEventTime;
    public long mLastCanKillOnBgRestrictedAndIdleTime;
    public long mLastInvisibleTime;
    public long mLastStateTime;
    public boolean mNoKillOnBgRestrictedAndIdle;
    public boolean mNotCachedSinceIdle;
    public final ActivityManagerGlobalLock mProcLock;
    public boolean mProcStateChanged;
    public boolean mReachable;
    public boolean mRepForegroundActivities;
    public boolean mReportedInteraction;
    public boolean mRunningRemoteAnimation;
    public int mSavedPriority;
    public final ActivityManagerService mService;
    public boolean mServiceB;
    public boolean mServiceHighRam;
    public boolean mSetCached;
    public boolean mSetNoKillOnBgRestrictedAndIdle;
    public boolean mSlowAbnormal;
    public boolean mSystemNoUi;
    public long mWhenUnimportant;
    public int mMaxAdj = 1001;
    public int mCurRawAdj = -10000;
    public int mSetRawAdj = -10000;
    public int mCurAdj = -10000;
    public int mSetAdj = -10000;
    public int mVerifiedAdj = -10000;
    public int mCurCapability = 0;
    public int mSetCapability = 0;
    public int mCurSchedGroup = 0;
    public int mSetSchedGroup = 0;
    public int mCurProcState = 20;
    public int mRepProcState = 20;
    public int mCurRawProcState = 20;
    public int mSetProcState = 20;
    public long mLastTopTime = Long.MIN_VALUE;
    public boolean mBackgroundRestricted = false;
    public boolean mCurBoundByNonBgRestrictedApp = false;
    public boolean mSetBoundByNonBgRestrictedApp = false;
    public int mCachedHasActivities = -1;
    public int mCachedIsHeavyWeight = -1;
    public int mCachedHasVisibleActivities = -1;
    public int mCachedIsHomeProcess = -1;
    public int mCachedIsPreviousProcess = -1;
    public int mCachedHasRecentTasks = -1;
    public int mCachedIsReceivingBroadcast = -1;
    public final int[] mCachedCompatChanges = {-1, -1, -1};
    public String mCachedAdjType = null;
    public int mCachedAdj = -10000;
    public boolean mCachedForegroundActivities = false;
    public int mCachedProcState = 19;
    public int mCachedSchedGroup = 0;
    public boolean mScheduleLikeTopApp = false;
    public long mFollowupUpdateUptimeMs = Long.MAX_VALUE;

    public ProcessStateRecord(ProcessRecord processRecord) {
        this.mApp = processRecord;
        ActivityManagerService activityManagerService = processRecord.mService;
        this.mService = activityManagerService;
        this.mProcLock = activityManagerService.mProcLock;
    }

    public final void forceProcessStateUpTo(int i) {
        if (this.mRepProcState > i) {
            ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    int i2 = this.mRepProcState;
                    setReportedProcState(i);
                    setCurProcState(i);
                    this.mCurRawProcState = i;
                    this.mService.mOomAdjuster.onProcessStateChanged(i2, this.mApp);
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean getCachedCompatChange(int i) {
        int i2;
        int[] iArr = this.mCachedCompatChanges;
        if (iArr[i] == -1) {
            OomAdjuster oomAdjuster = this.mService.mOomAdjuster;
            ApplicationInfo applicationInfo = this.mApp.info;
            oomAdjuster.mInjector.getClass();
            PlatformCompatCache.getInstance();
            PlatformCompatCache platformCompatCache = PlatformCompatCache.getInstance();
            long j = PlatformCompatCache.CACHED_COMPAT_CHANGE_IDS_MAPPING[i];
            platformCompatCache.getClass();
            try {
                i2 =
                        platformCompatCache.mCacheEnabled
                                ? ((PlatformCompatCache.CacheItem)
                                                platformCompatCache.mCaches.get(j))
                                        .isChangeEnabled(applicationInfo)
                                : platformCompatCache.mIPlatformCompatProxy.isChangeEnabled(
                                        j, applicationInfo);
            } catch (RemoteException e) {
                Slog.w("ActivityManager", "Error reading platform compat change " + j, e);
                i2 = 0;
            }
            iArr[i] = i2;
        }
        return this.mCachedCompatChanges[i] == 1;
    }

    public final boolean getCachedHasActivities() {
        if (this.mCachedHasActivities == -1) {
            boolean z = this.mApp.mWindowProcessController.mHasActivities;
            this.mCachedHasActivities = z ? 1 : 0;
            if (z) {
                this.mApp.mProfile.addHostingComponentType(16);
            } else {
                this.mApp.mProfile.clearHostingComponentType(16);
            }
        }
        return this.mCachedHasActivities == 1;
    }

    public final boolean getCachedIsHomeProcess() {
        if (this.mCachedIsHomeProcess == -1) {
            WindowProcessController windowProcessController = this.mApp.mWindowProcessController;
            if (windowProcessController == windowProcessController.mAtm.mHomeProcess) {
                this.mCachedIsHomeProcess = 1;
                this.mService.mAppProfiler.getClass();
            } else {
                this.mCachedIsHomeProcess = 0;
            }
        }
        return this.mCachedIsHomeProcess == 1;
    }

    public final int getSetAdjWithServices() {
        int i = this.mSetAdj;
        if (i < 900 || !this.mHasStartedServices) {
            return i;
        }
        return 800;
    }

    public final int getVerifiedAdj() {
        return this.mVerifiedAdj;
    }

    public final boolean isCached() {
        return this.mCurAdj >= 900;
    }

    public final void resetCachedInfo() {
        this.mCachedHasActivities = -1;
        this.mCachedIsHeavyWeight = -1;
        this.mCachedHasVisibleActivities = -1;
        this.mCachedIsHomeProcess = -1;
        this.mCachedIsPreviousProcess = -1;
        this.mCachedHasRecentTasks = -1;
        this.mCachedIsReceivingBroadcast = -1;
        this.mCachedAdj = -10000;
        this.mCachedForegroundActivities = false;
        this.mCachedProcState = 19;
        this.mCachedSchedGroup = 0;
        this.mCachedAdjType = null;
    }

    public final void setCurAdj(int i) {
        this.mCurAdj = i;
        this.mApp.mWindowProcessController.mCurAdj = i;
    }

    public final void setCurProcState(int i) {
        this.mCurProcState = i;
        this.mApp.mWindowProcessController.mCurProcState = i;
    }

    public final boolean setCurRawAdj(int i, boolean z) {
        if (z) {
            return this.mCurRawAdj > i;
        }
        this.mCurRawAdj = i;
        this.mApp.mWindowProcessController.mPerceptible = i <= 200;
        return false;
    }

    public final void setCurrentSchedulingGroup(int i) {
        this.mCurSchedGroup = i;
        this.mApp.mWindowProcessController.mCurSchedGroup = i;
    }

    public final void setReportedProcState(int i) {
        ConfigurationChangeItem obtain;
        this.mRepProcState = i;
        WindowProcessController windowProcessController = this.mApp.mWindowProcessController;
        int i2 = windowProcessController.mRepProcState;
        windowProcessController.mRepProcState = i;
        IApplicationThread iApplicationThread = windowProcessController.mThread;
        if (i2 < 16
                || i >= 16
                || iApplicationThread == null
                || !windowProcessController.mHasCachedConfiguration) {
            return;
        }
        synchronized (windowProcessController.mLastReportedConfiguration) {
            windowProcessController.onConfigurationChangePreScheduled(
                    windowProcessController.mLastReportedConfiguration);
            obtain =
                    ConfigurationChangeItem.obtain(
                            windowProcessController.mLastReportedConfiguration,
                            windowProcessController.mLastTopActivityDeviceId);
        }
        try {
            windowProcessController.mAtm.mLifecycleManager.getClass();
            ClientTransaction obtain2 = ClientTransaction.obtain(iApplicationThread);
            obtain2.addTransactionItem(obtain);
            ClientLifecycleManager.scheduleTransaction(obtain2);
        } catch (Exception e) {
            Slog.e(
                    "ActivityTaskManager",
                    "Failed to schedule ConfigurationChangeItem="
                            + obtain
                            + " owner="
                            + windowProcessController.mOwner,
                    e);
        }
    }

    public final void updateLastInvisibleTime(boolean z) {
        if (z) {
            this.mLastInvisibleTime = Long.MAX_VALUE;
        } else if (this.mLastInvisibleTime == Long.MAX_VALUE) {
            this.mLastInvisibleTime = SystemClock.elapsedRealtime();
        }
    }
}

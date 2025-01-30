package com.android.server.am;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.os.SystemClock;
import android.util.TimeUtils;
import com.android.server.am.OomAdjuster;
import java.io.PrintWriter;

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
    public boolean mCached;
    public int mCompletedAdjSeq;
    public boolean mContainsCycle;
    public boolean mEmpty;
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
    public long mLastTopTime;
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
    public int[] mCachedCompatChanges = {-1, -1, -1};
    public int mCachedAdj = -10000;
    public boolean mCachedForegroundActivities = false;
    public int mCachedProcState = 19;
    public int mCachedSchedGroup = 0;
    public int mHasTopUiRequester = 0;

    public ProcessStateRecord(ProcessRecord processRecord) {
        this.mApp = processRecord;
        ActivityManagerService activityManagerService = processRecord.mService;
        this.mService = activityManagerService;
        this.mProcLock = activityManagerService.mProcLock;
    }

    public void init(long j) {
        this.mLastStateTime = j;
    }

    public void setMaxAdj(int i) {
        this.mMaxAdj = i;
    }

    public int getMaxAdj() {
        return this.mMaxAdj;
    }

    public void setCurRawAdj(int i) {
        this.mCurRawAdj = i;
        this.mApp.getWindowProcessController().setPerceptible(i <= 200);
    }

    public int getCurRawAdj() {
        return this.mCurRawAdj;
    }

    public void setSetRawAdj(int i) {
        this.mSetRawAdj = i;
    }

    public int getSetRawAdj() {
        return this.mSetRawAdj;
    }

    public void setCurAdj(int i) {
        this.mCurAdj = i;
        this.mApp.getWindowProcessController().setCurrentAdj(i);
    }

    public int getCurAdj() {
        return this.mCurAdj;
    }

    public void setSetAdj(int i) {
        this.mSetAdj = i;
    }

    public int getSetAdj() {
        return this.mSetAdj;
    }

    public int getSetAdjWithServices() {
        int i = this.mSetAdj;
        if (i < 900 || !this.mHasStartedServices) {
            return i;
        }
        return 800;
    }

    public void setVerifiedAdj(int i) {
        this.mVerifiedAdj = i;
    }

    public int getVerifiedAdj() {
        return this.mVerifiedAdj;
    }

    public void setCurCapability(int i) {
        this.mCurCapability = i;
    }

    public int getCurCapability() {
        return this.mCurCapability;
    }

    public void setSetCapability(int i) {
        this.mSetCapability = i;
    }

    public int getSetCapability() {
        return this.mSetCapability;
    }

    public void setCurrentSchedulingGroup(int i) {
        this.mCurSchedGroup = i;
        this.mApp.getWindowProcessController().setCurrentSchedulingGroup(i);
    }

    public int getCurrentSchedulingGroup() {
        return this.mCurSchedGroup;
    }

    public void setSetSchedGroup(int i) {
        this.mSetSchedGroup = i;
    }

    public int getSetSchedGroup() {
        return this.mSetSchedGroup;
    }

    public void setCurProcState(int i) {
        this.mCurProcState = i;
        this.mApp.getWindowProcessController().setCurrentProcState(this.mCurProcState);
    }

    public int getCurProcState() {
        return this.mCurProcState;
    }

    public void setCurRawProcState(int i) {
        this.mCurRawProcState = i;
    }

    public int getCurRawProcState() {
        return this.mCurRawProcState;
    }

    public void setReportedProcState(int i) {
        this.mRepProcState = i;
        this.mApp.getWindowProcessController().setReportedProcState(i);
    }

    public int getReportedProcState() {
        return this.mRepProcState;
    }

    public void forceProcessStateUpTo(int i) {
        if (this.mRepProcState > i) {
            ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    setReportedProcState(i);
                    setCurProcState(i);
                    setCurRawProcState(i);
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
        }
    }

    public void setSetProcState(int i) {
        if (ActivityManager.isProcStateCached(this.mSetProcState) && !ActivityManager.isProcStateCached(i)) {
            this.mCacheOomRankerUseCount++;
        }
        this.mSetProcState = i;
    }

    public int getSetProcState() {
        return this.mSetProcState;
    }

    public void setLastStateTime(long j) {
        this.mLastStateTime = j;
    }

    public long getLastStateTime() {
        return this.mLastStateTime;
    }

    public void setSavedPriority(int i) {
        this.mSavedPriority = i;
    }

    public int getSavedPriority() {
        return this.mSavedPriority;
    }

    public void setServiceB(boolean z) {
        this.mServiceB = z;
    }

    public boolean isServiceB() {
        return this.mServiceB;
    }

    public void setServiceHighRam(boolean z) {
        this.mServiceHighRam = z;
    }

    public void setNotCachedSinceIdle(boolean z) {
        this.mNotCachedSinceIdle = z;
    }

    public boolean isNotCachedSinceIdle() {
        return this.mNotCachedSinceIdle;
    }

    public void setHasStartedServices(boolean z) {
        this.mHasStartedServices = z;
        if (z) {
            this.mApp.mProfile.addHostingComponentType(128);
        } else {
            this.mApp.mProfile.clearHostingComponentType(128);
        }
    }

    public void setHasForegroundActivities(boolean z) {
        this.mHasForegroundActivities = z;
    }

    public boolean hasForegroundActivities() {
        return this.mHasForegroundActivities;
    }

    public void setRepForegroundActivities(boolean z) {
        this.mRepForegroundActivities = z;
    }

    public boolean hasRepForegroundActivities() {
        return this.mRepForegroundActivities;
    }

    public void setHasShownUi(boolean z) {
        this.mHasShownUi = z;
    }

    public boolean hasShownUi() {
        return this.mHasShownUi;
    }

    public void setHasTopUi(boolean z) {
        this.mHasTopUi = z;
        this.mApp.getWindowProcessController().setHasTopUi(z);
    }

    public void requestHasTopUi(boolean z) {
        if (z) {
            this.mHasTopUiRequester++;
        } else {
            this.mHasTopUiRequester--;
        }
        if (this.mHasTopUiRequester < 0) {
            this.mHasTopUiRequester = 0;
        }
        this.mHasTopUi = this.mHasTopUiRequester > 0;
        this.mApp.getWindowProcessController().setHasTopUi(this.mHasTopUi);
    }

    public boolean hasTopUi() {
        return this.mHasTopUi;
    }

    public void setHasOverlayUi(boolean z) {
        this.mHasOverlayUi = z;
        this.mApp.getWindowProcessController().setHasOverlayUi(z);
    }

    public boolean hasOverlayUi() {
        return this.mHasOverlayUi;
    }

    public boolean isRunningRemoteAnimation() {
        return this.mRunningRemoteAnimation;
    }

    public void setRunningRemoteAnimation(boolean z) {
        if (this.mRunningRemoteAnimation == z) {
            return;
        }
        this.mRunningRemoteAnimation = z;
        this.mService.updateOomAdjLocked(this.mApp, 9);
    }

    public void setProcStateChanged(boolean z) {
        this.mProcStateChanged = z;
    }

    public boolean hasProcStateChanged() {
        return this.mProcStateChanged;
    }

    public void setReportedInteraction(boolean z) {
        this.mReportedInteraction = z;
    }

    public boolean hasReportedInteraction() {
        return this.mReportedInteraction;
    }

    public void setInteractionEventTime(long j) {
        this.mInteractionEventTime = j;
        this.mApp.getWindowProcessController().setInteractionEventTime(j);
    }

    public long getInteractionEventTime() {
        return this.mInteractionEventTime;
    }

    public void setFgInteractionTime(long j) {
        this.mFgInteractionTime = j;
        this.mApp.getWindowProcessController().setFgInteractionTime(j);
    }

    public long getFgInteractionTime() {
        return this.mFgInteractionTime;
    }

    public void setForcingToImportant(Object obj) {
        this.mForcingToImportant = obj;
    }

    public Object getForcingToImportant() {
        return this.mForcingToImportant;
    }

    public void setAdjSeq(int i) {
        this.mAdjSeq = i;
    }

    public void decAdjSeq() {
        this.mAdjSeq--;
    }

    public int getAdjSeq() {
        return this.mAdjSeq;
    }

    public void setCompletedAdjSeq(int i) {
        this.mCompletedAdjSeq = i;
    }

    public void decCompletedAdjSeq() {
        this.mCompletedAdjSeq--;
    }

    public int getCompletedAdjSeq() {
        return this.mCompletedAdjSeq;
    }

    public void setContainsCycle(boolean z) {
        this.mContainsCycle = z;
    }

    public boolean containsCycle() {
        return this.mContainsCycle;
    }

    public void setWhenUnimportant(long j) {
        this.mWhenUnimportant = j;
        this.mApp.getWindowProcessController().setWhenUnimportant(j);
    }

    public long getWhenUnimportant() {
        return this.mWhenUnimportant;
    }

    public void setLastTopTime(long j) {
        this.mLastTopTime = j;
    }

    public long getLastTopTime() {
        return this.mLastTopTime;
    }

    public void setEmpty(boolean z) {
        this.mEmpty = z;
    }

    public boolean isEmpty() {
        return this.mEmpty;
    }

    public void setCached(boolean z) {
        this.mCached = z;
    }

    public boolean isCached() {
        return this.mCached;
    }

    public int getCacheOomRankerUseCount() {
        return this.mCacheOomRankerUseCount;
    }

    public void setSystemNoUi(boolean z) {
        this.mSystemNoUi = z;
    }

    public boolean isSystemNoUi() {
        return this.mSystemNoUi;
    }

    public void setAdjType(String str) {
        this.mAdjType = str;
    }

    public String getAdjType() {
        return this.mAdjType;
    }

    public void setAdjTypeCode(int i) {
        this.mAdjTypeCode = i;
    }

    public int getAdjTypeCode() {
        return this.mAdjTypeCode;
    }

    public void setAdjSource(Object obj) {
        this.mAdjSource = obj;
    }

    public Object getAdjSource() {
        return this.mAdjSource;
    }

    public void setAdjSourceProcState(int i) {
        this.mAdjSourceProcState = i;
    }

    public int getAdjSourceProcState() {
        return this.mAdjSourceProcState;
    }

    public void setAdjTarget(Object obj) {
        this.mAdjTarget = obj;
    }

    public Object getAdjTarget() {
        return this.mAdjTarget;
    }

    public void setAbnormalStatus(boolean z) {
        this.mSlowAbnormal = z;
    }

    public boolean getAbnormalStatus() {
        return this.mSlowAbnormal;
    }

    public boolean isReachable() {
        return this.mReachable;
    }

    public void setReachable(boolean z) {
        this.mReachable = z;
    }

    public void resetCachedInfo() {
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
    }

    public boolean getCachedHasActivities() {
        if (this.mCachedHasActivities == -1) {
            boolean hasActivities = this.mApp.getWindowProcessController().hasActivities();
            this.mCachedHasActivities = hasActivities ? 1 : 0;
            if (hasActivities) {
                this.mApp.mProfile.addHostingComponentType(16);
            } else {
                this.mApp.mProfile.clearHostingComponentType(16);
            }
        }
        return this.mCachedHasActivities == 1;
    }

    public boolean getCachedIsHeavyWeight() {
        if (this.mCachedIsHeavyWeight == -1) {
            this.mCachedIsHeavyWeight = this.mApp.getWindowProcessController().isHeavyWeightProcess() ? 1 : 0;
        }
        return this.mCachedIsHeavyWeight == 1;
    }

    public boolean getCachedHasVisibleActivities() {
        if (this.mCachedHasVisibleActivities == -1) {
            this.mCachedHasVisibleActivities = this.mApp.getWindowProcessController().hasVisibleActivities() ? 1 : 0;
        }
        return this.mCachedHasVisibleActivities == 1;
    }

    public boolean getCachedIsHomeProcess() {
        if (this.mCachedIsHomeProcess == -1) {
            if (this.mApp.getWindowProcessController().isHomeProcess()) {
                this.mCachedIsHomeProcess = 1;
                this.mService.mAppProfiler.mHasHomeProcess = true;
            } else {
                this.mCachedIsHomeProcess = 0;
            }
        }
        return this.mCachedIsHomeProcess == 1;
    }

    public boolean getCachedIsPreviousProcess() {
        if (this.mCachedIsPreviousProcess == -1) {
            if (this.mApp.getWindowProcessController().isPreviousProcess()) {
                this.mCachedIsPreviousProcess = 1;
                this.mService.mAppProfiler.mHasPreviousProcess = true;
            } else {
                this.mCachedIsPreviousProcess = 0;
            }
        }
        return this.mCachedIsPreviousProcess == 1;
    }

    public boolean getCachedHasRecentTasks() {
        if (this.mCachedHasRecentTasks == -1) {
            this.mCachedHasRecentTasks = this.mApp.getWindowProcessController().hasRecentTasks() ? 1 : 0;
        }
        return this.mCachedHasRecentTasks == 1;
    }

    public boolean getCachedIsReceivingBroadcast(int[] iArr) {
        if (this.mCachedIsReceivingBroadcast == -1) {
            boolean isReceivingBroadcastLocked = this.mService.isReceivingBroadcastLocked(this.mApp, iArr);
            this.mCachedIsReceivingBroadcast = isReceivingBroadcastLocked ? 1 : 0;
            if (isReceivingBroadcastLocked) {
                this.mCachedSchedGroup = iArr[0];
                this.mApp.mProfile.addHostingComponentType(32);
            } else {
                this.mApp.mProfile.clearHostingComponentType(32);
            }
        }
        return this.mCachedIsReceivingBroadcast == 1;
    }

    public boolean getCachedCompatChange(int i) {
        int[] iArr = this.mCachedCompatChanges;
        if (iArr[i] == -1) {
            iArr[i] = this.mService.mOomAdjuster.isChangeEnabled(i, this.mApp.info, false) ? 1 : 0;
        }
        return this.mCachedCompatChanges[i] == 1;
    }

    public void computeOomAdjFromActivitiesIfNecessary(OomAdjuster.ComputeOomAdjWindowCallback computeOomAdjWindowCallback, int i, boolean z, boolean z2, int i2, int i3, int i4, int i5, int i6) {
        if (this.mCachedAdj != -10000) {
            return;
        }
        computeOomAdjWindowCallback.initialize(this.mApp, i, z, z2, i2, i3, i4, i5, i6);
        int min = Math.min(99, this.mApp.getWindowProcessController().computeOomAdjFromActivities(computeOomAdjWindowCallback));
        int i7 = computeOomAdjWindowCallback.adj;
        this.mCachedAdj = i7;
        this.mCachedForegroundActivities = computeOomAdjWindowCallback.foregroundActivities;
        this.mCachedHasVisibleActivities = computeOomAdjWindowCallback.mHasVisibleActivities ? 1 : 0;
        this.mCachedProcState = computeOomAdjWindowCallback.procState;
        this.mCachedSchedGroup = computeOomAdjWindowCallback.schedGroup;
        if (i7 == 100) {
            this.mCachedAdj = i7 + min;
        }
    }

    public int getCachedAdj() {
        return this.mCachedAdj;
    }

    public boolean getCachedForegroundActivities() {
        return this.mCachedForegroundActivities;
    }

    public int getCachedProcState() {
        return this.mCachedProcState;
    }

    public int getCachedSchedGroup() {
        return this.mCachedSchedGroup;
    }

    public String makeAdjReason() {
        if (this.mAdjSource == null && this.mAdjTarget == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(128);
        sb.append(' ');
        Object obj = this.mAdjTarget;
        if (obj instanceof ComponentName) {
            sb.append(((ComponentName) obj).flattenToShortString());
        } else if (obj != null) {
            sb.append(obj.toString());
        } else {
            sb.append("{null}");
        }
        sb.append("<=");
        Object obj2 = this.mAdjSource;
        if (obj2 instanceof ProcessRecord) {
            sb.append("Proc{");
            sb.append(((ProcessRecord) this.mAdjSource).toShortString());
            sb.append("}");
        } else if (obj2 != null) {
            sb.append(obj2.toString());
        } else {
            sb.append("{null}");
        }
        return sb.toString();
    }

    public void onCleanupApplicationRecordLSP() {
        int i = 0;
        setHasForegroundActivities(false);
        this.mHasShownUi = false;
        this.mForcingToImportant = null;
        this.mVerifiedAdj = -10000;
        this.mSetAdj = -10000;
        this.mCurAdj = -10000;
        this.mSetRawAdj = -10000;
        this.mCurRawAdj = -10000;
        this.mSetCapability = 0;
        this.mCurCapability = 0;
        this.mSetSchedGroup = 0;
        this.mCurSchedGroup = 0;
        this.mSetProcState = 20;
        this.mCurRawProcState = 20;
        this.mCurProcState = 20;
        while (true) {
            int[] iArr = this.mCachedCompatChanges;
            if (i >= iArr.length) {
                return;
            }
            iArr[i] = -1;
            i++;
        }
    }

    public boolean isBackgroundRestricted() {
        return this.mBackgroundRestricted;
    }

    public void setBackgroundRestricted(boolean z) {
        this.mBackgroundRestricted = z;
    }

    public boolean isCurBoundByNonBgRestrictedApp() {
        return this.mCurBoundByNonBgRestrictedApp;
    }

    public void setCurBoundByNonBgRestrictedApp(boolean z) {
        this.mCurBoundByNonBgRestrictedApp = z;
    }

    public boolean isSetBoundByNonBgRestrictedApp() {
        return this.mSetBoundByNonBgRestrictedApp;
    }

    public void setSetBoundByNonBgRestrictedApp(boolean z) {
        this.mSetBoundByNonBgRestrictedApp = z;
    }

    public void updateLastInvisibleTime(boolean z) {
        if (z) {
            this.mLastInvisibleTime = Long.MAX_VALUE;
        } else if (this.mLastInvisibleTime == Long.MAX_VALUE) {
            this.mLastInvisibleTime = SystemClock.elapsedRealtime();
        }
    }

    public long getLastInvisibleTime() {
        return this.mLastInvisibleTime;
    }

    public void setNoKillOnBgRestrictedAndIdle(boolean z) {
        this.mNoKillOnBgRestrictedAndIdle = z;
    }

    public boolean shouldNotKillOnBgRestrictedAndIdle() {
        return this.mNoKillOnBgRestrictedAndIdle;
    }

    public void setSetCached(boolean z) {
        this.mSetCached = z;
    }

    public boolean isSetCached() {
        return this.mSetCached;
    }

    public void setSetNoKillOnBgRestrictedAndIdle(boolean z) {
        this.mSetNoKillOnBgRestrictedAndIdle = z;
    }

    public boolean isSetNoKillOnBgRestrictedAndIdle() {
        return this.mSetNoKillOnBgRestrictedAndIdle;
    }

    public void setLastCanKillOnBgRestrictedAndIdleTime(long j) {
        this.mLastCanKillOnBgRestrictedAndIdleTime = j;
    }

    public long getLastCanKillOnBgRestrictedAndIdleTime() {
        return this.mLastCanKillOnBgRestrictedAndIdleTime;
    }

    public void setCacheOomRankerRss(long j, long j2) {
        this.mCacheOomRankerRss = j;
        this.mCacheOomRankerRssTimeMs = j2;
    }

    public long getCacheOomRankerRss() {
        return this.mCacheOomRankerRss;
    }

    public long getCacheOomRankerRssTimeMs() {
        return this.mCacheOomRankerRssTimeMs;
    }

    public void dump(PrintWriter printWriter, String str, long j) {
        if (this.mReportedInteraction || this.mFgInteractionTime != 0) {
            printWriter.print(str);
            printWriter.print("reportedInteraction=");
            printWriter.print(this.mReportedInteraction);
            if (this.mInteractionEventTime != 0) {
                printWriter.print(" time=");
                TimeUtils.formatDuration(this.mInteractionEventTime, SystemClock.elapsedRealtime(), printWriter);
            }
            if (this.mFgInteractionTime != 0) {
                printWriter.print(" fgInteractionTime=");
                TimeUtils.formatDuration(this.mFgInteractionTime, SystemClock.elapsedRealtime(), printWriter);
            }
            printWriter.println();
        }
        printWriter.print(str);
        printWriter.print("adjSeq=");
        printWriter.print(this.mAdjSeq);
        printWriter.print(" lruSeq=");
        printWriter.println(this.mApp.getLruSeq());
        printWriter.print(str);
        printWriter.print("oom adj: max=");
        printWriter.print(this.mMaxAdj);
        printWriter.print(" curRaw=");
        printWriter.print(this.mCurRawAdj);
        printWriter.print(" setRaw=");
        printWriter.print(this.mSetRawAdj);
        printWriter.print(" cur=");
        printWriter.print(this.mCurAdj);
        printWriter.print(" set=");
        printWriter.println(this.mSetAdj);
        printWriter.print(str);
        printWriter.print("mCurSchedGroup=");
        printWriter.print(this.mCurSchedGroup);
        printWriter.print(" setSchedGroup=");
        printWriter.print(this.mSetSchedGroup);
        printWriter.print(" systemNoUi=");
        printWriter.println(this.mSystemNoUi);
        printWriter.print(str);
        printWriter.print("curProcState=");
        printWriter.print(getCurProcState());
        printWriter.print(" mRepProcState=");
        printWriter.print(this.mRepProcState);
        printWriter.print(" setProcState=");
        printWriter.print(this.mSetProcState);
        printWriter.print(" lastStateTime=");
        TimeUtils.formatDuration(getLastStateTime(), j, printWriter);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("curCapability=");
        ActivityManager.printCapabilitiesFull(printWriter, this.mCurCapability);
        printWriter.print(" setCapability=");
        ActivityManager.printCapabilitiesFull(printWriter, this.mSetCapability);
        printWriter.println();
        if (this.mBackgroundRestricted) {
            printWriter.print(" backgroundRestricted=");
            printWriter.print(this.mBackgroundRestricted);
            printWriter.print(" boundByNonBgRestrictedApp=");
            printWriter.print(this.mSetBoundByNonBgRestrictedApp);
        }
        printWriter.println();
        if (this.mHasShownUi || this.mApp.mProfile.hasPendingUiClean()) {
            printWriter.print(str);
            printWriter.print("hasShownUi=");
            printWriter.print(this.mHasShownUi);
            printWriter.print(" pendingUiClean=");
            printWriter.println(this.mApp.mProfile.hasPendingUiClean());
        }
        printWriter.print(str);
        printWriter.print("cached=");
        printWriter.print(this.mCached);
        printWriter.print(" empty=");
        printWriter.println(this.mEmpty);
        if (this.mServiceB) {
            printWriter.print(str);
            printWriter.print("serviceb=");
            printWriter.print(this.mServiceB);
            printWriter.print(" serviceHighRam=");
            printWriter.println(this.mServiceHighRam);
        }
        if (this.mNotCachedSinceIdle) {
            printWriter.print(str);
            printWriter.print("notCachedSinceIdle=");
            printWriter.print(this.mNotCachedSinceIdle);
            printWriter.print(" initialIdlePss=");
            printWriter.println(this.mApp.mProfile.getInitialIdlePss());
        }
        if (hasTopUi() || hasOverlayUi() || this.mRunningRemoteAnimation) {
            printWriter.print(str);
            printWriter.print("hasTopUi=");
            printWriter.print(hasTopUi());
            printWriter.print(" hasOverlayUi=");
            printWriter.print(hasOverlayUi());
            printWriter.print(" runningRemoteAnimation=");
            printWriter.println(this.mRunningRemoteAnimation);
            if (this.mHasTopUiRequester > 0) {
                printWriter.print(" mHasTopUiRequester=");
                printWriter.print(this.mHasTopUiRequester);
            }
        }
        if (this.mHasForegroundActivities || this.mRepForegroundActivities) {
            printWriter.print(str);
            printWriter.print("foregroundActivities=");
            printWriter.print(this.mHasForegroundActivities);
            printWriter.print(" (rep=");
            printWriter.print(this.mRepForegroundActivities);
            printWriter.println(")");
        }
        if (this.mSetProcState > 10) {
            printWriter.print(str);
            printWriter.print("whenUnimportant=");
            TimeUtils.formatDuration(this.mWhenUnimportant - j, printWriter);
            printWriter.println();
        }
        if (this.mLastTopTime > 0) {
            printWriter.print(str);
            printWriter.print("lastTopTime=");
            TimeUtils.formatDuration(this.mLastTopTime, j, printWriter);
            printWriter.println();
        }
        long j2 = this.mLastInvisibleTime;
        if (j2 > 0 && j2 < Long.MAX_VALUE) {
            printWriter.print(str);
            printWriter.print("lastInvisibleTime=");
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long currentTimeMillis = System.currentTimeMillis();
            TimeUtils.dumpTimeWithDelta(printWriter, (currentTimeMillis - elapsedRealtime) + this.mLastInvisibleTime, currentTimeMillis);
            printWriter.println();
        }
        if (this.mHasStartedServices) {
            printWriter.print(str);
            printWriter.print("hasStartedServices=");
            printWriter.println(this.mHasStartedServices);
        }
    }
}

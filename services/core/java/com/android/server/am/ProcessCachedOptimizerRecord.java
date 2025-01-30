package com.android.server.am;

import android.util.TimeUtils;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public final class ProcessCachedOptimizerRecord {
  static final String IS_FROZEN = "isFrozen";
  public final ProcessRecord mApp;
  public boolean mDoingTrim;
  public long mEarliestFreezableTimeMillis;
  public boolean mForceCompact;
  public boolean mFreezeExempt;
  public boolean mFreezeSticky;
  public long mFreezeUnfreezeTime;
  public boolean mFreezerOverride;
  public boolean mFrozen;
  public boolean mHasCollectedFrozenPSS;
  public CachedAppOptimizer.CompactProfile mLastCompactProfile;
  public long mLastCompactTime;
  public int mLastOomAdjChangeReason;
  public long mLastUsedTimeout;
  public boolean mPendingCompact;
  public boolean mPendingFreeze;
  public final ActivityManagerGlobalLock mProcLock;
  public CachedAppOptimizer.CompactProfile mReqCompactProfile;
  public CachedAppOptimizer.CompactSource mReqCompactSource;
  public boolean mShouldNotFreeze;

  public long getLastCompactTime() {
    return this.mLastCompactTime;
  }

  public void setLastCompactTime(long j) {
    this.mLastCompactTime = j;
  }

  public CachedAppOptimizer.CompactProfile getReqCompactProfile() {
    return this.mReqCompactProfile;
  }

  public void setReqCompactProfile(CachedAppOptimizer.CompactProfile compactProfile) {
    this.mReqCompactProfile = compactProfile;
  }

  public CachedAppOptimizer.CompactSource getReqCompactSource() {
    return this.mReqCompactSource;
  }

  public void setReqCompactSource(CachedAppOptimizer.CompactSource compactSource) {
    this.mReqCompactSource = compactSource;
  }

  public void setLastOomAdjChangeReason(int i) {
    this.mLastOomAdjChangeReason = i;
  }

  public int getLastOomAdjChangeReason() {
    return this.mLastOomAdjChangeReason;
  }

  public CachedAppOptimizer.CompactProfile getLastCompactProfile() {
    if (this.mLastCompactProfile == null) {
      this.mLastCompactProfile = CachedAppOptimizer.CompactProfile.SOME;
    }
    return this.mLastCompactProfile;
  }

  public void setLastCompactProfile(CachedAppOptimizer.CompactProfile compactProfile) {
    this.mLastCompactProfile = compactProfile;
  }

  public boolean hasPendingCompact() {
    return this.mPendingCompact;
  }

  public void setHasPendingCompact(boolean z) {
    this.mPendingCompact = z;
  }

  public boolean isForceCompact() {
    return this.mForceCompact;
  }

  public void setForceCompact(boolean z) {
    this.mForceCompact = z;
  }

  public boolean isFrozen() {
    return this.mFrozen;
  }

  public void setFrozen(boolean z) {
    this.mFrozen = z;
  }

  public void setFreezeSticky(boolean z) {
    this.mFreezeSticky = z;
  }

  public boolean isFreezeSticky() {
    return this.mFreezeSticky;
  }

  public boolean skipPSSCollectionBecauseFrozen() {
    boolean z = this.mHasCollectedFrozenPSS;
    if (!this.mFrozen) {
      return false;
    }
    this.mHasCollectedFrozenPSS = true;
    return z;
  }

  public void setHasCollectedFrozenPSS(boolean z) {
    this.mHasCollectedFrozenPSS = z;
  }

  public boolean hasFreezerOverride() {
    return this.mFreezerOverride;
  }

  public void setFreezerOverride(boolean z) {
    this.mFreezerOverride = z;
  }

  public long getFreezeUnfreezeTime() {
    return this.mFreezeUnfreezeTime;
  }

  public void setFreezeUnfreezeTime(long j) {
    this.mFreezeUnfreezeTime = j;
  }

  public boolean shouldNotFreeze() {
    return this.mShouldNotFreeze;
  }

  public void setShouldNotFreeze(boolean z) {
    this.mShouldNotFreeze = z;
  }

  public long getEarliestFreezableTime() {
    return this.mEarliestFreezableTimeMillis;
  }

  public void setEarliestFreezableTime(long j) {
    this.mEarliestFreezableTimeMillis = j;
  }

  public long getLastUsedTimeout() {
    return this.mLastUsedTimeout;
  }

  public void setLastUsedTimeout(long j) {
    this.mLastUsedTimeout = j;
  }

  public boolean isFreezeExempt() {
    return this.mFreezeExempt;
  }

  public void setPendingFreeze(boolean z) {
    this.mPendingFreeze = z;
  }

  public boolean isPendingFreeze() {
    return this.mPendingFreeze;
  }

  public void setDoingTrim(boolean z) {
    this.mDoingTrim = z;
  }

  public boolean isDoingTrim() {
    return this.mDoingTrim;
  }

  public void setFreezeExempt(boolean z) {
    this.mFreezeExempt = z;
  }

  public ProcessCachedOptimizerRecord(ProcessRecord processRecord) {
    this.mApp = processRecord;
    this.mProcLock = processRecord.mService.mProcLock;
  }

  public void init(long j) {
    this.mFreezeUnfreezeTime = j;
  }

  public void dump(PrintWriter printWriter, String str, long j) {
    printWriter.print(str);
    printWriter.print("lastCompactTime=");
    printWriter.print(this.mLastCompactTime);
    printWriter.print(" lastCompactProfile=");
    printWriter.println(this.mLastCompactProfile);
    printWriter.print(str);
    printWriter.print("hasPendingCompaction=");
    printWriter.print(this.mPendingCompact);
    printWriter.print(str);
    printWriter.print("isFreezeExempt=");
    printWriter.print(this.mFreezeExempt);
    printWriter.print(" isPendingFreeze=");
    printWriter.print(this.mPendingFreeze);
    printWriter.print(" isFrozen=");
    printWriter.println(this.mFrozen);
    printWriter.print(str);
    printWriter.print("earliestFreezableTimeMs=");
    TimeUtils.formatDuration(this.mEarliestFreezableTimeMillis, j, printWriter);
    printWriter.println();
  }
}

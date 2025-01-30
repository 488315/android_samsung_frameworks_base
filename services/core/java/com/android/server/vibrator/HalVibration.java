package com.android.server.vibrator;

import android.os.CombinedVibration;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.util.SparseArray;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.server.vibrator.CommonPatternInfo;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes3.dex */
public final class HalVibration extends Vibration {
  public CommonPatternInfo[] mCommonData;
  public final CountDownLatch mCompletionLatch;
  public CombinedVibration mEffect;
  public int[] mEngineData;
  public final SparseArray mFallbacks;
  public int mFrequency;
  public int mMagnitude;
  public CombinedVibration mOriginalEffect;
  public long[] mPattern;
  public Vibration.Status mStatus;
  public long mTimeout;

  public HalVibration(
      IBinder iBinder, CombinedVibration combinedVibration, Vibration.CallerInfo callerInfo) {
    super(iBinder, callerInfo);
    this.mFallbacks = new SparseArray();
    this.mCompletionLatch = new CountDownLatch(1);
    this.mTimeout = -1L;
    this.mPattern = null;
    this.mEngineData = null;
    this.mMagnitude = 0;
    this.mFrequency = 0;
    this.mCommonData = null;
    this.mEffect = combinedVibration;
    this.mStatus = Vibration.Status.RUNNING;
  }

  public void end(Vibration.EndInfo endInfo) {
    if (hasEnded()) {
      return;
    }
    this.mStatus = endInfo.status;
    this.stats.reportEnded(endInfo.endedBy);
    this.mCompletionLatch.countDown();
  }

  public void waitForEnd() {
    this.mCompletionLatch.await();
  }

  public VibrationEffect getFallback(int i) {
    return (VibrationEffect) this.mFallbacks.get(i);
  }

  public void addFallback(int i, VibrationEffect vibrationEffect) {
    this.mFallbacks.put(i, vibrationEffect);
  }

  public boolean hasEnded() {
    return this.mStatus != Vibration.Status.RUNNING;
  }

  @Override // com.android.server.vibrator.Vibration
  public boolean isRepeating() {
    return this.mEffect.getDuration() == Long.MAX_VALUE;
  }

  public CombinedVibration getEffect() {
    return this.mEffect;
  }

  public Vibration.DebugInfo getDebugInfo() {
    return new Vibration.DebugInfo(
        this.mStatus,
        this.stats,
        this.mEffect,
        this.mOriginalEffect,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
        this.callerInfo);
  }

  public VibrationStats.StatsInfo getStatsInfo(long j) {
    int i = isRepeating() ? 2 : 1;
    Vibration.CallerInfo callerInfo = this.callerInfo;
    return new VibrationStats.StatsInfo(
        callerInfo.uid, i, callerInfo.attrs.getUsage(), this.mStatus, this.stats, j);
  }

  public boolean canPipelineWith(HalVibration halVibration) {
    Vibration.CallerInfo callerInfo = this.callerInfo;
    return callerInfo.uid == halVibration.callerInfo.uid
        && callerInfo.attrs.isFlagSet(8)
        && halVibration.callerInfo.attrs.isFlagSet(8)
        && !isRepeating();
  }

  public HalVibration(
      IBinder iBinder,
      CombinedVibration combinedVibration,
      long j,
      long[] jArr,
      int i,
      int i2,
      int[] iArr,
      CommonPatternInfo[] commonPatternInfoArr,
      Vibration.CallerInfo callerInfo) {
    this(iBinder, combinedVibration, callerInfo);
    this.mTimeout = j;
    this.mPattern = jArr;
    this.mMagnitude = i;
    this.mFrequency = i2;
    this.mEngineData = iArr;
    this.mCommonData = commonPatternInfoArr;
  }

  public int[] getEngineData() {
    return this.mEngineData;
  }

  public int getMagnitude() {
    return this.mMagnitude;
  }

  public long getTimeOut() {
    return this.mTimeout;
  }

  public void setMagnitude(int i) {
    this.mMagnitude = i;
  }

  public CommonPatternInfo[] getCommonData() {
    return this.mCommonData;
  }
}

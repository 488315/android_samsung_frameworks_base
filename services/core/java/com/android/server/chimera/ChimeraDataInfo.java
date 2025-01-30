package com.android.server.chimera;


/* loaded from: classes.dex */
public class ChimeraDataInfo {
  public long mAvgAvaMem;
  public long mAvgReleasedMem;
  public boolean mDynamicStrategyUse;
  public int mKillCnt;
  public float mLruWeight;
  public float mMemWeight;
  public float mStdBktWeight;
  public long mTargetAvaMem;
  public int[] mTriggerCntSrc = new int[ChimeraCommonUtil.TriggerSource.values().length];
  public int[] mActionCntSrc = new int[ChimeraCommonUtil.TriggerSource.values().length];
  public int[] mAdjKillCnt = new int[ChimeraCommonUtil.ADJ_LEVELS.length];
  public int[] mGroupKillCnt = new int[3];

  public int[] getTriggerCntSrc() {
    return this.mTriggerCntSrc;
  }

  public void setTriggerCntSrc(int[] iArr) {
    System.arraycopy(iArr, 0, this.mTriggerCntSrc, 0, iArr.length);
  }

  public int[] getActionCntSrc() {
    return this.mActionCntSrc;
  }

  public void setActionCntSrc(int[] iArr) {
    System.arraycopy(iArr, 0, this.mActionCntSrc, 0, iArr.length);
  }

  public int getKillCnt() {
    return this.mKillCnt;
  }

  public void setKillCnt(int i) {
    this.mKillCnt = i;
  }

  public void setAvgReleasedMem(long j) {
    this.mAvgReleasedMem = j;
  }

  public void setAvgAvaMem(long j) {
    this.mAvgAvaMem = j;
  }

  public float getLruWight() {
    return this.mLruWeight;
  }

  public void setLruWight(float f) {
    this.mLruWeight = f;
  }

  public float getStdBktWeight() {
    return this.mStdBktWeight;
  }

  public void setStdBktWeight(float f) {
    this.mStdBktWeight = f;
  }

  public float getMemWeight() {
    return this.mMemWeight;
  }

  public void setMemWeight(float f) {
    this.mMemWeight = f;
  }

  public void setTargetAvaMem(long j) {
    this.mTargetAvaMem = j;
  }

  public void setDynamicStrategyUse(boolean z) {
    this.mDynamicStrategyUse = z;
  }

  public int[] getAdjKillCnt() {
    return this.mAdjKillCnt;
  }

  public void setAdjKillCnt(int[] iArr) {
    System.arraycopy(iArr, 0, this.mAdjKillCnt, 0, iArr.length);
  }

  public int[] getGroupKillCnt() {
    return this.mGroupKillCnt;
  }

  public void setGroupKillCnt(int[] iArr) {
    System.arraycopy(iArr, 0, this.mGroupKillCnt, 0, iArr.length);
  }

  public static ChimeraDataInfo getDiff(
      ChimeraDataInfo chimeraDataInfo, ChimeraDataInfo chimeraDataInfo2) {
    ChimeraDataInfo chimeraDataInfo3 = new ChimeraDataInfo();
    int i = 0;
    int i2 = 0;
    while (true) {
      int[] iArr = chimeraDataInfo3.mTriggerCntSrc;
      if (i2 >= iArr.length) {
        break;
      }
      iArr[i2] = chimeraDataInfo.mTriggerCntSrc[i2] - chimeraDataInfo2.mTriggerCntSrc[i2];
      i2++;
    }
    int i3 = 0;
    while (true) {
      int[] iArr2 = chimeraDataInfo3.mActionCntSrc;
      if (i3 >= iArr2.length) {
        break;
      }
      iArr2[i3] = chimeraDataInfo.mActionCntSrc[i3] - chimeraDataInfo2.mActionCntSrc[i3];
      i3++;
    }
    int i4 = 0;
    while (true) {
      int[] iArr3 = chimeraDataInfo3.mAdjKillCnt;
      if (i4 >= iArr3.length) {
        break;
      }
      iArr3[i4] = chimeraDataInfo.mAdjKillCnt[i4] - chimeraDataInfo2.mAdjKillCnt[i4];
      i4++;
    }
    while (true) {
      int[] iArr4 = chimeraDataInfo3.mGroupKillCnt;
      if (i < iArr4.length) {
        iArr4[i] = chimeraDataInfo.mGroupKillCnt[i] - chimeraDataInfo2.mGroupKillCnt[i];
        i++;
      } else {
        chimeraDataInfo3.mAvgAvaMem = chimeraDataInfo.mAvgAvaMem - chimeraDataInfo2.mAvgAvaMem;
        chimeraDataInfo3.mAvgReleasedMem =
            chimeraDataInfo.mAvgReleasedMem - chimeraDataInfo2.mAvgReleasedMem;
        chimeraDataInfo3.mKillCnt = chimeraDataInfo.mKillCnt - chimeraDataInfo2.mKillCnt;
        chimeraDataInfo3.mDynamicStrategyUse = chimeraDataInfo.mDynamicStrategyUse;
        chimeraDataInfo3.mLruWeight = chimeraDataInfo.mLruWeight;
        chimeraDataInfo3.mMemWeight = chimeraDataInfo.mMemWeight;
        chimeraDataInfo3.mStdBktWeight = chimeraDataInfo.mStdBktWeight;
        return chimeraDataInfo3;
      }
    }
  }
}

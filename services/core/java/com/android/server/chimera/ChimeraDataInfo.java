package com.android.server.chimera;


public final class ChimeraDataInfo {
    public final int[] mAdjKillCnt;
    public final int[] mGroupKillCnt;
    public int mKillCnt;
    public float mLruWeight;
    public float mMemWeight;
    public float mStdBktWeight;
    public final int[] mTriggerCntSrc = new int[ChimeraCommonUtil.TriggerSource.values().length];
    public final int[] mActionCntSrc = new int[ChimeraCommonUtil.TriggerSource.values().length];

    public ChimeraDataInfo() {
        int[] iArr = ChimeraCommonUtil.ADJ_LEVELS;
        this.mAdjKillCnt = new int[13];
        this.mGroupKillCnt = new int[3];
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
            if (i >= iArr4.length) {
                chimeraDataInfo2.getClass();
                chimeraDataInfo3.mKillCnt = chimeraDataInfo.mKillCnt - chimeraDataInfo2.mKillCnt;
                chimeraDataInfo3.mLruWeight = chimeraDataInfo.mLruWeight;
                chimeraDataInfo3.mMemWeight = chimeraDataInfo.mMemWeight;
                chimeraDataInfo3.mStdBktWeight = chimeraDataInfo.mStdBktWeight;
                return chimeraDataInfo3;
            }
            iArr4[i] = chimeraDataInfo.mGroupKillCnt[i] - chimeraDataInfo2.mGroupKillCnt[i];
            i++;
        }
    }
}

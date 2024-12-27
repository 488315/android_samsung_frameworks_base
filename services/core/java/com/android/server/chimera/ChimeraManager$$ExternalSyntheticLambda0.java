package com.android.server.chimera;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ChimeraManager$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        PolicyHandler policyHandler = (PolicyHandler) obj;
        policyHandler.getClass();
        ChimeraDataInfo chimeraDataInfo = new ChimeraDataInfo();
        int[] iArr = policyHandler.mTriggerCntSrc;
        System.arraycopy(iArr, 0, chimeraDataInfo.mTriggerCntSrc, 0, iArr.length);
        int[] iArr2 = policyHandler.mActionCntSrc;
        System.arraycopy(iArr2, 0, chimeraDataInfo.mActionCntSrc, 0, iArr2.length);
        chimeraDataInfo.mKillCnt = policyHandler.mKillCnt;
        chimeraDataInfo.mLruWeight = policyHandler.mWeightLru;
        chimeraDataInfo.mStdBktWeight = policyHandler.mWeightStandbyBucket;
        chimeraDataInfo.mMemWeight = policyHandler.mWeightMem;
        long j = policyHandler.mChimeraStrategy.mMemFreeTarget;
        System.arraycopy(policyHandler.getKillCntByAdj(), 0, chimeraDataInfo.mAdjKillCnt, 0, 13);
        int[] iArr3 = policyHandler.mKillCntByGrp;
        System.arraycopy(iArr3, 0, chimeraDataInfo.mGroupKillCnt, 0, iArr3.length);
        return chimeraDataInfo;
    }
}

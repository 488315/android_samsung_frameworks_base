package com.android.systemui.kairos.internal;

import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class DepthTracker$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Integer num = (Integer) obj2;
        switch (this.$r8$classId) {
            case 0:
                if (num != null) {
                    int iIntValue = num.intValue() - 1;
                    Integer numValueOf = Integer.valueOf(iIntValue);
                    if (iIntValue > 0) {
                        return numValueOf;
                    }
                }
                return null;
            case 1:
                return Integer.valueOf(num != null ? 1 + num.intValue() : 1);
            case 2:
                if (num != null) {
                    int iIntValue2 = num.intValue() - 1;
                    Integer numValueOf2 = Integer.valueOf(iIntValue2);
                    if (iIntValue2 > 0) {
                        return numValueOf2;
                    }
                }
                return null;
            case 3:
                if (num != null) {
                    int iIntValue3 = num.intValue() - 1;
                    Integer numValueOf3 = Integer.valueOf(iIntValue3);
                    if (iIntValue3 > 0) {
                        return numValueOf3;
                    }
                }
                return null;
            case 4:
                if (num != null) {
                    int iIntValue4 = num.intValue() - 1;
                    Integer numValueOf4 = Integer.valueOf(iIntValue4);
                    if (iIntValue4 > 0) {
                        return numValueOf4;
                    }
                }
                return null;
            default:
                return Integer.valueOf(num != null ? 1 + num.intValue() : 1);
        }
    }
}

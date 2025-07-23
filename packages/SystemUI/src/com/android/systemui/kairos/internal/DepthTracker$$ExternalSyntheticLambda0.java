package com.android.systemui.kairos.internal;

import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class DepthTracker$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Integer num = (Integer) obj2;
        switch (this.$r8$classId) {
            case 0:
                if (num != null) {
                    int intValue = num.intValue() - 1;
                    Integer valueOf = Integer.valueOf(intValue);
                    if (intValue > 0) {
                        return valueOf;
                    }
                }
                return null;
            case 1:
                return Integer.valueOf(num != null ? 1 + num.intValue() : 1);
            case 2:
                if (num != null) {
                    int intValue2 = num.intValue() - 1;
                    Integer valueOf2 = Integer.valueOf(intValue2);
                    if (intValue2 > 0) {
                        return valueOf2;
                    }
                }
                return null;
            case 3:
                if (num != null) {
                    int intValue3 = num.intValue() - 1;
                    Integer valueOf3 = Integer.valueOf(intValue3);
                    if (intValue3 > 0) {
                        return valueOf3;
                    }
                }
                return null;
            case 4:
                if (num != null) {
                    int intValue4 = num.intValue() - 1;
                    Integer valueOf4 = Integer.valueOf(intValue4);
                    if (intValue4 > 0) {
                        return valueOf4;
                    }
                }
                return null;
            default:
                return Integer.valueOf(num != null ? 1 + num.intValue() : 1);
        }
    }
}

package com.android.server.chimera.ppn;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class PerProcessNandswap$NandswapHandler$$ExternalSyntheticLambda0
        implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Integer num = (Integer) obj;
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(
                        (num.intValue() >= 100 && num.intValue() <= 250)
                                || num.intValue() == -700
                                || num.intValue() == -800
                                || num.intValue() == -1000);
            default:
                int i = PerProcessNandswap.NandswapHandler.$r8$clinit;
                return Boolean.TRUE;
        }
    }
}

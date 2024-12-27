package com.android.server.chimera.ppn;

import java.util.function.Function;

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

package com.android.server.chimera.ppn;

import java.util.function.ToIntFunction;

public final /* synthetic */ class PerProcessNandswap$$ExternalSyntheticLambda0
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        boolean z = PerProcessNandswap.FAST_MADVISE_ENABLED;
        return ((PerProcessNandswap.NandswapRecord) obj).pid;
    }
}

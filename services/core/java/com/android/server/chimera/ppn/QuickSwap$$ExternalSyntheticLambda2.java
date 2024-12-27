package com.android.server.chimera.ppn;

import com.android.server.chimera.SystemRepository;

import java.util.List;
import java.util.function.Function;

public final /* synthetic */ class QuickSwap$$ExternalSyntheticLambda2 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        List list = QuickSwap.QUICKSWAP_BLOCKLIST;
        return Integer.valueOf(((SystemRepository.RunningAppProcessInfo) obj).pid);
    }
}

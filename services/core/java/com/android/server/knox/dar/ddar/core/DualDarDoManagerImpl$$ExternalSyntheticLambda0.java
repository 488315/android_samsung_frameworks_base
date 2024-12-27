package com.android.server.knox.dar.ddar.core;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.ddar.IDualDARPolicy;

import java.util.function.Function;

public final /* synthetic */ class DualDarDoManagerImpl$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        try {
            return Integer.valueOf(
                    ((IDualDARPolicy) obj).getPasswordMinimumLengthForInner(new ContextInfo()));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

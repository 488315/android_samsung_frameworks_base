package com.android.server.knox.dar.ddar.core;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.ddar.IDualDARPolicy;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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

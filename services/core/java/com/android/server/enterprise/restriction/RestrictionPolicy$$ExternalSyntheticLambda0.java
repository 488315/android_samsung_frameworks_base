package com.android.server.enterprise.restriction;

import android.telephony.SubscriptionInfo;

import java.util.function.ToIntFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class RestrictionPolicy$$ExternalSyntheticLambda0
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        String[] strArr = RestrictionPolicy.excludedAdminList;
        return ((SubscriptionInfo) obj).getSubscriptionId();
    }
}

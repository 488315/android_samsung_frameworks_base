package com.android.server.enterprise.restriction;

import android.telephony.SubscriptionInfo;

import java.util.function.ToIntFunction;

public final /* synthetic */ class RestrictionPolicy$$ExternalSyntheticLambda0
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        String[] strArr = RestrictionPolicy.excludedAdminList;
        return ((SubscriptionInfo) obj).getSubscriptionId();
    }
}

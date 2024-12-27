package com.android.keyguard;

import android.telephony.SubscriptionInfo;
import java.util.function.Predicate;

public final /* synthetic */ class KeyguardUpdateMonitor$$ExternalSyntheticLambda6 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((SubscriptionInfo) obj).getProfileClass() != 1;
    }
}

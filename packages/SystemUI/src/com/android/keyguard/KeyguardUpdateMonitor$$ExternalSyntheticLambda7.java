package com.android.keyguard;

import android.telephony.SubscriptionInfo;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardUpdateMonitor$$ExternalSyntheticLambda7 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
        int i = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
        return (subscriptionInfo.getProfileClass() == 1 || subscriptionInfo.getSubscriptionType() == 1) ? false : true;
    }
}

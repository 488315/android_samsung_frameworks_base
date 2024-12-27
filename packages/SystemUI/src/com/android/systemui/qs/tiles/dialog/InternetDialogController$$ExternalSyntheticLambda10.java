package com.android.systemui.qs.tiles.dialog;

import android.telephony.SubscriptionInfo;
import java.util.function.Predicate;

public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda10 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
        return (subscriptionInfo == null || subscriptionInfo.getDisplayName() == null) ? false : true;
    }
}

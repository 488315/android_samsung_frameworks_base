package com.android.systemui.qs.tiles.dialog;

import android.telephony.SubscriptionInfo;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda10 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
        return (subscriptionInfo == null || subscriptionInfo.getDisplayName() == null) ? false : true;
    }
}

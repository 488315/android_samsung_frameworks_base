package com.android.server.soundtrigger;

import android.telephony.SubscriptionInfo;

import java.util.function.Predicate;

public final /* synthetic */ class PhoneCallStateHandler$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
        switch (this.$r8$classId) {
            case 0:
                if (subscriptionInfo.getSubscriptionId() != -1) {}
                break;
            case 1:
                if (subscriptionInfo.getSubscriptionId() != -1) {}
                break;
            default:
                if (subscriptionInfo.getSubscriptionId() != -1) {}
                break;
        }
        return false;
    }
}

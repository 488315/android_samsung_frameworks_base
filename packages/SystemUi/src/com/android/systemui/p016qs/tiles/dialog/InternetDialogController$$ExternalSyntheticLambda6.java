package com.android.systemui.p016qs.tiles.dialog;

import android.telephony.SubscriptionInfo;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda6 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
        return (subscriptionInfo == null || subscriptionInfo.getDisplayName() == null) ? false : true;
    }
}

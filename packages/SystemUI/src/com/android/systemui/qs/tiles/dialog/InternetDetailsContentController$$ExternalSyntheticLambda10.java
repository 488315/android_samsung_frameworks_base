package com.android.systemui.qs.tiles.dialog;

import android.graphics.drawable.Drawable;
import android.telephony.SubscriptionInfo;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final /* synthetic */ class InternetDetailsContentController$$ExternalSyntheticLambda10 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
        Drawable drawable = InternetDetailsContentController.EMPTY_DRAWABLE;
        return (subscriptionInfo == null || subscriptionInfo.getDisplayName() == null) ? false : true;
    }
}

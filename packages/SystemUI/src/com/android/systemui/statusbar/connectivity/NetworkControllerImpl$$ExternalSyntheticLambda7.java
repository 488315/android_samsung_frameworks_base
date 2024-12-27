package com.android.systemui.statusbar.connectivity;

import android.telephony.SubscriptionInfo;
import java.util.function.Function;

public final /* synthetic */ class NetworkControllerImpl$$ExternalSyntheticLambda7 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((SubscriptionInfo) obj).getSubscriptionId());
    }
}

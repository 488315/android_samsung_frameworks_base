package com.android.systemui.statusbar.connectivity;

import android.telephony.SubscriptionInfo;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NetworkControllerImpl$$ExternalSyntheticLambda7 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((SubscriptionInfo) obj).getSubscriptionId());
    }
}

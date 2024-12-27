package com.android.systemui.statusbar.pipeline.mobile.util;

import android.content.Context;
import android.telephony.SubscriptionManager;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SubscriptionManagerProxyImpl implements SubscriptionManagerProxy {
    public final CoroutineDispatcher backgroundDispatcher;
    public final SubscriptionManager subscriptionManager;

    public SubscriptionManagerProxyImpl(Context context, CoroutineDispatcher coroutineDispatcher, SubscriptionManager subscriptionManager) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.subscriptionManager = subscriptionManager;
    }

    public final Object getActiveSubscriptionInfo(int i, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new SubscriptionManagerProxyImpl$getActiveSubscriptionInfo$2(this, i, null), continuation);
    }
}

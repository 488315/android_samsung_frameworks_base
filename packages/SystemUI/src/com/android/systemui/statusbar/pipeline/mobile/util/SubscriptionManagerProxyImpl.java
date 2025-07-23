package com.android.systemui.statusbar.pipeline.mobile.util;

import android.content.Context;
import android.telephony.SubscriptionManager;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

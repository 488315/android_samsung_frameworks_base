package com.android.systemui.statusbar.pipeline.mobile.util;

import android.content.Context;
import android.telephony.SubscriptionManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class SubscriptionManagerProxyImpl implements SubscriptionManagerProxy {
    public final CoroutineDispatcher backgroundDispatcher;
    public final SubscriptionManager subscriptionManager;

    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.util.SubscriptionManagerProxyImpl$getActiveSubscriptionInfo$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $subId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(int i, Continuation continuation) {
            super(2, continuation);
            this.$subId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SubscriptionManagerProxyImpl.this.new AnonymousClass2(this.$subId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return SubscriptionManagerProxyImpl.this.subscriptionManager.getActiveSubscriptionInfo(this.$subId);
        }
    }

    public SubscriptionManagerProxyImpl(Context context, CoroutineDispatcher coroutineDispatcher, SubscriptionManager subscriptionManager) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.subscriptionManager = subscriptionManager;
    }

    public final Object getActiveSubscriptionInfo(int i, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AnonymousClass2(i, null), continuation);
    }
}

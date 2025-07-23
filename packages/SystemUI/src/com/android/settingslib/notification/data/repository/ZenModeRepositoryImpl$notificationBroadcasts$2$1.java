package com.android.settingslib.notification.data.repository;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ZenModeRepositoryImpl$notificationBroadcasts$2$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ZenModeRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ZenModeRepositoryImpl$notificationBroadcasts$2$1(ZenModeRepositoryImpl zenModeRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = zenModeRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ZenModeRepositoryImpl$notificationBroadcasts$2$1 zenModeRepositoryImpl$notificationBroadcasts$2$1 = new ZenModeRepositoryImpl$notificationBroadcasts$2$1(this.this$0, continuation);
        zenModeRepositoryImpl$notificationBroadcasts$2$1.L$0 = obj;
        return zenModeRepositoryImpl$notificationBroadcasts$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ZenModeRepositoryImpl$notificationBroadcasts$2$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.content.BroadcastReceiver, com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$notificationBroadcasts$2$1$receiver$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new BroadcastReceiver() { // from class: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$notificationBroadcasts$2$1$receiver$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (intent != null) {
                        ProducerScope producerScope2 = ProducerScope.this;
                        BuildersKt.launch$default(producerScope2, null, null, new ZenModeRepositoryImpl$notificationBroadcasts$2$1$receiver$1$onReceive$1$1(producerScope2, intent, null), 3);
                    }
                }
            };
            Context context = this.this$0.context;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.app.action.INTERRUPTION_FILTER_CHANGED");
            intentFilter.addAction("android.app.action.NOTIFICATION_POLICY_CHANGED");
            intentFilter.addAction("android.app.action.CONSOLIDATED_NOTIFICATION_POLICY_CHANGED");
            Unit unit = Unit.INSTANCE;
            context.registerReceiver(r1, intentFilter, null, this.this$0.backgroundHandler);
            final ZenModeRepositoryImpl zenModeRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$notificationBroadcasts$2$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ZenModeRepositoryImpl.this.context.unregisterReceiver(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}

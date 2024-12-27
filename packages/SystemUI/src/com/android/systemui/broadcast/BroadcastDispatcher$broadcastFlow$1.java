package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.systemui.common.coroutine.ChannelExt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BroadcastDispatcher$broadcastFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ IntentFilter $filter;
    final /* synthetic */ int $flags;
    final /* synthetic */ Function2 $map;
    final /* synthetic */ String $permission;
    final /* synthetic */ UserHandle $user;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BroadcastDispatcher this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BroadcastDispatcher$broadcastFlow$1(BroadcastDispatcher broadcastDispatcher, IntentFilter intentFilter, UserHandle userHandle, int i, String str, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = broadcastDispatcher;
        this.$filter = intentFilter;
        this.$user = userHandle;
        this.$flags = i;
        this.$permission = str;
        this.$map = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BroadcastDispatcher$broadcastFlow$1 broadcastDispatcher$broadcastFlow$1 = new BroadcastDispatcher$broadcastFlow$1(this.this$0, this.$filter, this.$user, this.$flags, this.$permission, this.$map, continuation);
        broadcastDispatcher$broadcastFlow$1.L$0 = obj;
        return broadcastDispatcher$broadcastFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BroadcastDispatcher$broadcastFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.content.BroadcastReceiver, com.android.systemui.broadcast.BroadcastDispatcher$broadcastFlow$1$receiver$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final Function2 function2 = this.$map;
            final ?? r1 = new BroadcastReceiver() { // from class: com.android.systemui.broadcast.BroadcastDispatcher$broadcastFlow$1$receiver$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, ProducerScope.this, function2.invoke(intent, this), "BroadcastDispatcher");
                }
            };
            BroadcastDispatcher broadcastDispatcher = this.this$0;
            broadcastDispatcher.registerReceiver(r1, this.$filter, broadcastDispatcher.broadcastExecutor, this.$user, this.$flags, this.$permission);
            final BroadcastDispatcher broadcastDispatcher2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.broadcast.BroadcastDispatcher$broadcastFlow$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    BroadcastDispatcher.this.unregisterReceiver(r1);
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

package com.android.systemui.kairos.internal;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferedChannel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class Network$transaction$1$job$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $block;
    final /* synthetic */ CompletableDeferred $onResult;
    final /* synthetic */ String $reason;
    int label;
    final /* synthetic */ Network this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Network$transaction$1$job$1(Network network, String str, CompletableDeferred completableDeferred, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = network;
        this.$reason = str;
        this.$onResult = completableDeferred;
        this.$block = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new Network$transaction$1$job$1(this.this$0, this.$reason, this.$onResult, this.$block, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((Network$transaction$1$job$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            BufferedChannel bufferedChannel = this.this$0.inputScheduleChan;
            ScheduledAction scheduledAction = new ScheduledAction(this.$reason, this.$onResult, this.$block);
            this.label = 1;
            if (bufferedChannel.send(scheduledAction, this) == coroutineSingletons) {
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

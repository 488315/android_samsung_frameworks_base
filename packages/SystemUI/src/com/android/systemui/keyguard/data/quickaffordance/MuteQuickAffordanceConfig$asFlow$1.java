package com.android.systemui.keyguard.data.quickaffordance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class MuteQuickAffordanceConfig$asFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LiveData $this_asFlow;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    public MuteQuickAffordanceConfig$asFlow$1(LiveData liveData, Continuation continuation) {
        super(2, continuation);
        this.$this_asFlow = liveData;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MuteQuickAffordanceConfig$asFlow$1 muteQuickAffordanceConfig$asFlow$1 = new MuteQuickAffordanceConfig$asFlow$1(this.$this_asFlow, continuation);
        muteQuickAffordanceConfig$asFlow$1.L$0 = obj;
        return muteQuickAffordanceConfig$asFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MuteQuickAffordanceConfig$asFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        final Observer observer;
        ProducerScope producerScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope2 = (ProducerScope) this.L$0;
            observer = new Observer() { // from class: com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceConfig$asFlow$1$observer$1
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj2) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(obj2);
                }
            };
            this.$this_asFlow.observeForever(observer);
            Object value = this.$this_asFlow.getValue();
            this.L$0 = producerScope2;
            this.L$1 = observer;
            this.label = 1;
            if (((ChannelCoroutine) producerScope2)._channel.send(value, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            producerScope = producerScope2;
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            observer = (Observer) this.L$1;
            producerScope = (ProducerScope) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        final LiveData liveData = this.$this_asFlow;
        Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceConfig$asFlow$1.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                LiveData.this.removeObserver(observer);
                return Unit.INSTANCE;
            }
        };
        this.L$0 = null;
        this.L$1 = null;
        this.label = 2;
        if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}

package com.android.systemui.lowlightclock;

import com.android.systemui.shared.condition.Monitor;
import com.android.systemui.shared.condition.Monitor$$ExternalSyntheticLambda0;
import java.util.Set;
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

/* loaded from: classes2.dex */
final class LowLightMonitor$isLowLight$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ LowLightMonitor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LowLightMonitor$isLowLight$1(LowLightMonitor lowLightMonitor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lowLightMonitor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LowLightMonitor$isLowLight$1 lowLightMonitor$isLowLight$1 = new LowLightMonitor$isLowLight$1(this.this$0, continuation);
        lowLightMonitor$isLowLight$1.L$0 = obj;
        return lowLightMonitor$isLowLight$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LowLightMonitor$isLowLight$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            Monitor monitor = this.this$0.conditionsMonitor;
            Monitor.Subscription.Builder builder = new Monitor.Subscription.Builder(new Monitor.Callback() { // from class: com.android.systemui.lowlightclock.LowLightMonitor$isLowLight$1$token$1
                @Override // com.android.systemui.shared.condition.Monitor.Callback
                public final void onConditionsChanged(boolean z) {
                    ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Boolean.valueOf(z));
                }
            });
            Set set = (Set) this.this$0.lowLightConditions.get();
            if (set != null) {
                builder.mConditions.addAll(set);
            }
            final Monitor.Subscription.Token tokenAddSubscription = monitor.addSubscription(builder.build(), monitor.mPreconditions);
            final LowLightMonitor lowLightMonitor = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.lowlightclock.LowLightMonitor$isLowLight$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Monitor monitor2 = lowLightMonitor.conditionsMonitor;
                    monitor2.mExecutor.execute(new Monitor$$ExternalSyntheticLambda0(0, monitor2, tokenAddSubscription));
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

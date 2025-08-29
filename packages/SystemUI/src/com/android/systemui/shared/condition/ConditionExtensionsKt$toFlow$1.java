package com.android.systemui.shared.condition;

import com.android.systemui.shared.condition.Condition;
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
import kotlinx.coroutines.channels.SendChannel;

/* loaded from: classes3.dex */
final class ConditionExtensionsKt$toFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Condition $this_toFlow;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConditionExtensionsKt$toFlow$1(Condition condition, Continuation continuation) {
        super(2, continuation);
        this.$this_toFlow = condition;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ConditionExtensionsKt$toFlow$1 conditionExtensionsKt$toFlow$1 = new ConditionExtensionsKt$toFlow$1(this.$this_toFlow, continuation);
        conditionExtensionsKt$toFlow$1.L$0 = obj;
        return conditionExtensionsKt$toFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ConditionExtensionsKt$toFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.shared.condition.Condition$Callback, com.android.systemui.shared.condition.ConditionExtensionsKt$toFlow$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new Condition.Callback() { // from class: com.android.systemui.shared.condition.ConditionExtensionsKt$toFlow$1$callback$1
                @Override // com.android.systemui.shared.condition.Condition.Callback
                public final void onConditionChanged(Condition condition) {
                    Boolean bool = condition._isConditionMet;
                    boolean z = bool != null;
                    SendChannel sendChannel = producerScope;
                    if (z) {
                        ((ChannelCoroutine) sendChannel).mo3475trySendJP2dKIU(Boolean.valueOf(Boolean.TRUE.equals(bool)));
                    } else {
                        ((ChannelCoroutine) sendChannel).mo3475trySendJP2dKIU(null);
                    }
                }
            };
            this.$this_toFlow.addCallback(r1);
            r1.onConditionChanged(this.$this_toFlow);
            final Condition condition = this.$this_toFlow;
            Function0 function0 = new Function0() { // from class: com.android.systemui.shared.condition.ConditionExtensionsKt$toFlow$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    condition.removeCallback(r1);
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

package com.android.systemui.shared.condition;

import java.util.Collection;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CombinedCondition$start$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Map $groupedConditions$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CombinedCondition this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CombinedCondition$start$$inlined$flatMapLatest$1(Continuation continuation, CombinedCondition combinedCondition, Map map) {
        super(3, continuation);
        this.this$0 = combinedCondition;
        this.$groupedConditions$inlined = map;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CombinedCondition$start$$inlined$flatMapLatest$1 combinedCondition$start$$inlined$flatMapLatest$1 = new CombinedCondition$start$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$groupedConditions$inlined);
        combinedCondition$start$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        combinedCondition$start$$inlined$flatMapLatest$1.L$1 = obj2;
        return combinedCondition$start$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Boolean bool = (Boolean) this.L$1;
            if (bool == null) {
                CombinedCondition combinedCondition = this.this$0;
                Collection collection = (Collection) this.$groupedConditions$inlined.getOrDefault(Boolean.FALSE, EmptyList.INSTANCE);
                int i2 = CombinedCondition.$r8$clinit;
                combinedCondition.getClass();
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = FlowKt.callbackFlow(new CombinedCondition$lazilyEvaluate$1(collection, false, combinedCondition, null));
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this) == coroutineSingletons) {
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

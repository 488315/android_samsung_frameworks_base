package com.android.systemui.shared.condition;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
final class CombinedCondition$lazilyEvaluate$1$collectFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ProducerScope $$this$callbackFlow;
    final /* synthetic */ Collection<Condition> $conditions;
    final /* synthetic */ boolean $filterUnknown;
    final /* synthetic */ List<Flow> $flows;
    final /* synthetic */ int $index;
    final /* synthetic */ List<Job> $jobs;
    final /* synthetic */ List<Boolean> $values;
    int label;
    final /* synthetic */ CombinedCondition this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public CombinedCondition$lazilyEvaluate$1$collectFlow$1(List<? extends Flow> list, int i, List<Boolean> list2, CombinedCondition combinedCondition, ProducerScope producerScope, List<Job> list3, Collection<? extends Condition> collection, boolean z, Continuation continuation) {
        super(2, continuation);
        this.$flows = list;
        this.$index = i;
        this.$values = list2;
        this.this$0 = combinedCondition;
        this.$$this$callbackFlow = producerScope;
        this.$jobs = list3;
        this.$conditions = collection;
        this.$filterUnknown = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CombinedCondition$lazilyEvaluate$1$collectFlow$1(this.$flows, this.$index, this.$values, this.this$0, this.$$this$callbackFlow, this.$jobs, this.$conditions, this.$filterUnknown, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CombinedCondition$lazilyEvaluate$1$collectFlow$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow flow = this.$flows.get(this.$index);
            final List<Boolean> list = this.$values;
            final int i2 = this.$index;
            final CombinedCondition combinedCondition = this.this$0;
            final ProducerScope producerScope = this.$$this$callbackFlow;
            final List<Job> list2 = this.$jobs;
            final Collection<Condition> collection = this.$conditions;
            final List<Flow> list3 = this.$flows;
            final boolean z = this.$filterUnknown;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.shared.condition.CombinedCondition$lazilyEvaluate$1$collectFlow$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Boolean bool = (Boolean) obj2;
                    List list4 = list;
                    int i3 = i2;
                    list4.set(i3, bool);
                    int i4 = combinedCondition.operand;
                    if (i4 != 0 ? i4 != 1 ? false : Intrinsics.areEqual(bool, Boolean.TRUE) : Intrinsics.areEqual(bool, Boolean.FALSE)) {
                        ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(bool);
                        CombinedCondition$lazilyEvaluate$1.invokeSuspend$cancelAllExcept(list2, collection, list, i3);
                    } else {
                        List list5 = list3;
                        List list6 = list;
                        List list7 = list2;
                        Collection collection2 = collection;
                        Iterator it = list7.iterator();
                        int i5 = 0;
                        while (true) {
                            if (!it.hasNext()) {
                                i5 = -1;
                                break;
                            }
                            if (((Job) it.next()) == null) {
                                break;
                            }
                            i5++;
                        }
                        CombinedCondition$lazilyEvaluate$1.invokeSuspend$collectFlow(list5, i5, combinedCondition, producerScope, list6, collection2, list7, z);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

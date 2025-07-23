package com.android.systemui.util.kotlin;

import java.util.concurrent.ConcurrentHashMap;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SimpleFlowDumper$dumpWhileCollecting$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $dumpName;
    final /* synthetic */ Flow $this_dumpWhileCollecting;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SimpleFlowDumper this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimpleFlowDumper$dumpWhileCollecting$1(String str, SimpleFlowDumper simpleFlowDumper, Flow flow, Continuation continuation) {
        super(2, continuation);
        this.$dumpName = str;
        this.this$0 = simpleFlowDumper;
        this.$this_dumpWhileCollecting = flow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SimpleFlowDumper$dumpWhileCollecting$1 simpleFlowDumper$dumpWhileCollecting$1 = new SimpleFlowDumper$dumpWhileCollecting$1(this.$dumpName, this.this$0, this.$this_dumpWhileCollecting, continuation);
        simpleFlowDumper$dumpWhileCollecting$1.L$0 = obj;
        return simpleFlowDumper$dumpWhileCollecting$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Pair pair;
        ConcurrentHashMap concurrentHashMap;
        ConcurrentHashMap concurrentHashMap2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final FlowCollector flowCollector = (FlowCollector) this.L$0;
            final Pair pair2 = new Pair(this.$dumpName, this.this$0.getIdString(flowCollector));
            try {
                Flow flow = this.$this_dumpWhileCollecting;
                final SimpleFlowDumper simpleFlowDumper = this.this$0;
                FlowCollector flowCollector2 = new FlowCollector() { // from class: com.android.systemui.util.kotlin.SimpleFlowDumper$dumpWhileCollecting$1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(T t, Continuation continuation) {
                        ConcurrentHashMap concurrentHashMap3;
                        concurrentHashMap3 = SimpleFlowDumper.this.flowCollectionMap;
                        concurrentHashMap3.put(pair2, t == null ? "null" : t);
                        SimpleFlowDumper.this.onMapKeysChanged(true);
                        Object emit = flowCollector.emit(t, continuation);
                        return emit == CoroutineSingletons.COROUTINE_SUSPENDED ? emit : Unit.INSTANCE;
                    }
                };
                this.L$0 = pair2;
                this.label = 1;
                if (flow.collect(flowCollector2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                pair = pair2;
            } catch (Throwable th) {
                th = th;
                pair = pair2;
                concurrentHashMap = this.this$0.flowCollectionMap;
                concurrentHashMap.remove(pair);
                this.this$0.onMapKeysChanged(false);
                throw th;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            pair = (Pair) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th2) {
                th = th2;
                concurrentHashMap = this.this$0.flowCollectionMap;
                concurrentHashMap.remove(pair);
                this.this$0.onMapKeysChanged(false);
                throw th;
            }
        }
        concurrentHashMap2 = this.this$0.flowCollectionMap;
        concurrentHashMap2.remove(pair);
        this.this$0.onMapKeysChanged(false);
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
        return ((SimpleFlowDumper$dumpWhileCollecting$1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}

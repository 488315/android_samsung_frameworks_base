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

final class FlowDumperImpl$dumpWhileCollecting$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $dumpName;
    final /* synthetic */ Flow $this_dumpWhileCollecting;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ FlowDumperImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowDumperImpl$dumpWhileCollecting$1(String str, FlowDumperImpl flowDumperImpl, Flow flow, Continuation continuation) {
        super(2, continuation);
        this.$dumpName = str;
        this.this$0 = flowDumperImpl;
        this.$this_dumpWhileCollecting = flow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FlowDumperImpl$dumpWhileCollecting$1 flowDumperImpl$dumpWhileCollecting$1 = new FlowDumperImpl$dumpWhileCollecting$1(this.$dumpName, this.this$0, this.$this_dumpWhileCollecting, continuation);
        flowDumperImpl$dumpWhileCollecting$1.L$0 = obj;
        return flowDumperImpl$dumpWhileCollecting$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String idString;
        Pair pair;
        ConcurrentHashMap concurrentHashMap;
        ConcurrentHashMap concurrentHashMap2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final FlowCollector flowCollector = (FlowCollector) this.L$0;
            String str = this.$dumpName;
            idString = this.this$0.getIdString(flowCollector);
            final Pair pair2 = new Pair(str, idString);
            try {
                Flow flow = this.$this_dumpWhileCollecting;
                final FlowDumperImpl flowDumperImpl = this.this$0;
                FlowCollector flowCollector2 = new FlowCollector() { // from class: com.android.systemui.util.kotlin.FlowDumperImpl$dumpWhileCollecting$1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(T t, Continuation continuation) {
                        ConcurrentHashMap concurrentHashMap3;
                        concurrentHashMap3 = FlowDumperImpl.this.flowCollectionMap;
                        concurrentHashMap3.put(pair2, t == null ? "null" : t);
                        FlowDumperImpl.this.updateRegistration(true);
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
                this.this$0.updateRegistration(false);
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
                this.this$0.updateRegistration(false);
                throw th;
            }
        }
        concurrentHashMap2 = this.this$0.flowCollectionMap;
        concurrentHashMap2.remove(pair);
        this.this$0.updateRegistration(false);
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
        return ((FlowDumperImpl$dumpWhileCollecting$1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}

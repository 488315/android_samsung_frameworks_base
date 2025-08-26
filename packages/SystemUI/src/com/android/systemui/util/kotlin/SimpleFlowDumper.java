package com.android.systemui.util.kotlin;

import android.util.IndentingPrintWriter;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public class SimpleFlowDumper implements FlowDumper {
    public static final int $stable = 8;
    private final ConcurrentHashMap<String, StateFlow> stateFlowMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, SharedFlow> sharedFlowMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Pair<String, String>, Object> flowCollectionMap = new ConcurrentHashMap<>();

    /* renamed from: com.android.systemui.util.kotlin.SimpleFlowDumper$dumpWhileCollecting$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $dumpName;
        final /* synthetic */ Flow $this_dumpWhileCollecting;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ SimpleFlowDumper this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(String str, SimpleFlowDumper simpleFlowDumper, Flow flow, Continuation continuation) {
            super(2, continuation);
            this.$dumpName = str;
            this.this$0 = simpleFlowDumper;
            this.$this_dumpWhileCollecting = flow;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$dumpName, this.this$0, this.$this_dumpWhileCollecting, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Pair pair;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                pair = (Pair) this.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                    this.this$0.flowCollectionMap.remove(pair);
                    this.this$0.onMapKeysChanged(false);
                    return Unit.INSTANCE;
                } catch (Throwable th) {
                    th = th;
                    this.this$0.flowCollectionMap.remove(pair);
                    this.this$0.onMapKeysChanged(false);
                    throw th;
                }
            }
            ResultKt.throwOnFailure(obj);
            final FlowCollector flowCollector = (FlowCollector) this.L$0;
            final Pair pair2 = new Pair(this.$dumpName, this.this$0.getIdString(flowCollector));
            try {
                Flow flow = this.$this_dumpWhileCollecting;
                final SimpleFlowDumper simpleFlowDumper = this.this$0;
                FlowCollector flowCollector2 = new FlowCollector() { // from class: com.android.systemui.util.kotlin.SimpleFlowDumper.dumpWhileCollecting.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(T t, Continuation continuation) {
                        simpleFlowDumper.flowCollectionMap.put(pair2, t == null ? "null" : t);
                        simpleFlowDumper.onMapKeysChanged(true);
                        Object objEmit = flowCollector.emit(t, continuation);
                        return objEmit == CoroutineSingletons.COROUTINE_SUSPENDED ? objEmit : Unit.INSTANCE;
                    }
                };
                this.L$0 = pair2;
                this.label = 1;
                if (flow.collect(flowCollector2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                pair = pair2;
                this.this$0.flowCollectionMap.remove(pair);
                this.this$0.onMapKeysChanged(false);
                return Unit.INSTANCE;
            } catch (Throwable th2) {
                th = th2;
                pair = pair2;
                this.this$0.flowCollectionMap.remove(pair);
                this.this$0.onMapKeysChanged(false);
                throw th;
            }
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((AnonymousClass1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public void dumpFlows(IndentingPrintWriter indentingPrintWriter) {
        Set<Map.Entry> setEntrySet = new TreeMap(this.stateFlowMap).entrySet();
        indentingPrintWriter.append("StateFlow (value)").append((CharSequence) ": ").println(setEntrySet.size());
        indentingPrintWriter.increaseIndent();
        try {
            for (Map.Entry entry : setEntrySet) {
                entry.getClass();
                indentingPrintWriter.append((String) entry.getKey()).append('=').println(((StateFlow) entry.getValue()).getValue());
            }
            indentingPrintWriter.decreaseIndent();
            Set<Map.Entry> setEntrySet2 = new TreeMap(this.sharedFlowMap).entrySet();
            indentingPrintWriter.append("SharedFlow (replayCache)").append((CharSequence) ": ").println(setEntrySet2.size());
            indentingPrintWriter.increaseIndent();
            try {
                for (Map.Entry entry2 : setEntrySet2) {
                    entry2.getClass();
                    indentingPrintWriter.append((String) entry2.getKey()).append('=').println(((SharedFlow) entry2.getValue()).getReplayCache());
                }
                indentingPrintWriter.decreaseIndent();
                final Comparator comparator = new Comparator() { // from class: com.android.systemui.util.kotlin.SimpleFlowDumper$dumpFlows$$inlined$compareBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt__ComparisonsKt.compareValues((Comparable) ((Pair) t).getFirst(), (Comparable) ((Pair) t2).getFirst());
                    }
                };
                Comparator comparator2 = new Comparator() { // from class: com.android.systemui.util.kotlin.SimpleFlowDumper$dumpFlows$$inlined$thenBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        int iCompare = comparator.compare(t, t2);
                        return iCompare != 0 ? iCompare : ComparisonsKt__ComparisonsKt.compareValues((Comparable) ((Pair) t).getSecond(), (Comparable) ((Pair) t2).getSecond());
                    }
                };
                ConcurrentHashMap<Pair<String, String>, Object> concurrentHashMap = this.flowCollectionMap;
                TreeMap treeMap = new TreeMap(comparator2);
                treeMap.putAll(concurrentHashMap);
                Set<Map.Entry> setEntrySet3 = treeMap.entrySet();
                indentingPrintWriter.append("Flow (latest)").append((CharSequence) ": ").println(setEntrySet3.size());
                indentingPrintWriter.increaseIndent();
                try {
                    for (Map.Entry entry3 : setEntrySet3) {
                        entry3.getClass();
                        Pair pair = (Pair) entry3.getKey();
                        indentingPrintWriter.append((CharSequence) pair.getFirst()).append('=').println(entry3.getValue());
                    }
                } finally {
                }
            } finally {
            }
        } finally {
        }
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public <T, F extends SharedFlow> F dumpReplayCache(F f, String str) {
        this.sharedFlowMap.put(str, f);
        onMapKeysChanged(true);
        return f;
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public <T, F extends StateFlow> F dumpValue(F f, String str) {
        this.stateFlowMap.put(str, f);
        onMapKeysChanged(true);
        return f;
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public <T> Flow dumpWhileCollecting(Flow flow, String str) {
        return new SafeFlow(new AnonymousClass1(str, this, flow, null));
    }

    public final String getIdString(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }

    public final boolean isNotEmpty() {
        return (this.stateFlowMap.isEmpty() && this.sharedFlowMap.isEmpty() && this.flowCollectionMap.isEmpty()) ? false : true;
    }

    public void onMapKeysChanged(boolean z) {
    }
}

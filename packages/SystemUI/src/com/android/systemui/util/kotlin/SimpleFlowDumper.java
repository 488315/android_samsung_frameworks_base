package com.android.systemui.util.kotlin;

import android.util.IndentingPrintWriter;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Pair;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SimpleFlowDumper implements FlowDumper {
    public static final int $stable = 8;
    private final ConcurrentHashMap<String, StateFlow> stateFlowMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, SharedFlow> sharedFlowMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Pair<String, String>, Object> flowCollectionMap = new ConcurrentHashMap<>();

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public void dumpFlows(IndentingPrintWriter indentingPrintWriter) {
        Set<Map.Entry> entrySet = new TreeMap(this.stateFlowMap).entrySet();
        indentingPrintWriter.append("StateFlow (value)").append((CharSequence) ": ").println(entrySet.size());
        indentingPrintWriter.increaseIndent();
        try {
            for (Map.Entry entry : entrySet) {
                entry.getClass();
                indentingPrintWriter.append((String) entry.getKey()).append('=').println(((StateFlow) entry.getValue()).getValue());
            }
            indentingPrintWriter.decreaseIndent();
            Set<Map.Entry> entrySet2 = new TreeMap(this.sharedFlowMap).entrySet();
            indentingPrintWriter.append("SharedFlow (replayCache)").append((CharSequence) ": ").println(entrySet2.size());
            indentingPrintWriter.increaseIndent();
            try {
                for (Map.Entry entry2 : entrySet2) {
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
                        int compare = comparator.compare(t, t2);
                        return compare != 0 ? compare : ComparisonsKt__ComparisonsKt.compareValues((Comparable) ((Pair) t).getSecond(), (Comparable) ((Pair) t2).getSecond());
                    }
                };
                ConcurrentHashMap<Pair<String, String>, Object> concurrentHashMap = this.flowCollectionMap;
                TreeMap treeMap = new TreeMap(comparator2);
                treeMap.putAll(concurrentHashMap);
                Set<Map.Entry> entrySet3 = treeMap.entrySet();
                indentingPrintWriter.append("Flow (latest)").append((CharSequence) ": ").println(entrySet3.size());
                indentingPrintWriter.increaseIndent();
                try {
                    for (Map.Entry entry3 : entrySet3) {
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
        return new SafeFlow(new SimpleFlowDumper$dumpWhileCollecting$1(str, this, flow, null));
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

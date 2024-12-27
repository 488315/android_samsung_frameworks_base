package com.android.systemui.util.kotlin;

import android.util.IndentingPrintWriter;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class FlowDumperImpl implements FlowDumper {
    public static final int $stable = 8;
    private final DumpManager dumpManager;
    private final String dumpManagerName;
    private final ConcurrentHashMap<Pair<String, String>, Object> flowCollectionMap;
    private AtomicBoolean registered;
    private final ConcurrentHashMap<String, SharedFlow> sharedFlowMap;
    private final ConcurrentHashMap<String, StateFlow> stateFlowMap;

    public FlowDumperImpl(DumpManager dumpManager, String str) {
        this.dumpManager = dumpManager;
        this.stateFlowMap = new ConcurrentHashMap<>();
        this.sharedFlowMap = new ConcurrentHashMap<>();
        this.flowCollectionMap = new ConcurrentHashMap<>();
        this.dumpManagerName = str == null ? FontProvider$$ExternalSyntheticOutline0.m("[", getIdString(this), "] ", getClass().getSimpleName()) : str;
        this.registered = new AtomicBoolean(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getIdString(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateRegistration(boolean z) {
        if (this.dumpManager == null) {
            return;
        }
        if (z && this.registered.get()) {
            return;
        }
        synchronized (this.registered) {
            try {
                boolean z2 = true;
                if (!(!this.stateFlowMap.isEmpty()) && !(!this.sharedFlowMap.isEmpty()) && !(!this.flowCollectionMap.isEmpty())) {
                    z2 = false;
                }
                if (this.registered.getAndSet(z2) != z2) {
                    if (z2) {
                        this.dumpManager.registerCriticalDumpable(this.dumpManagerName, this);
                    } else {
                        this.dumpManager.unregisterDumpable(this.dumpManagerName);
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public void dumpFlows(IndentingPrintWriter indentingPrintWriter) {
        Set<Map.Entry> entrySet = new TreeMap(this.stateFlowMap).entrySet();
        indentingPrintWriter.append("StateFlow (value)").append((CharSequence) ": ").println(entrySet.size());
        indentingPrintWriter.increaseIndent();
        try {
            for (Map.Entry entry : entrySet) {
                Intrinsics.checkNotNull(entry);
                indentingPrintWriter.append((String) entry.getKey()).append('=').println(((StateFlow) entry.getValue()).getValue());
            }
            indentingPrintWriter.decreaseIndent();
            Set<Map.Entry> entrySet2 = new TreeMap(this.sharedFlowMap).entrySet();
            indentingPrintWriter.append("SharedFlow (replayCache)").append((CharSequence) ": ").println(entrySet2.size());
            indentingPrintWriter.increaseIndent();
            try {
                for (Map.Entry entry2 : entrySet2) {
                    Intrinsics.checkNotNull(entry2);
                    indentingPrintWriter.append((String) entry2.getKey()).append('=').println(((SharedFlow) entry2.getValue()).getReplayCache());
                }
                indentingPrintWriter.decreaseIndent();
                final Comparator comparator = new Comparator() { // from class: com.android.systemui.util.kotlin.FlowDumperImpl$dumpFlows$$inlined$compareBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt__ComparisonsKt.compareValues((Comparable) ((Pair) t).getFirst(), (Comparable) ((Pair) t2).getFirst());
                    }
                };
                Comparator comparator2 = new Comparator() { // from class: com.android.systemui.util.kotlin.FlowDumperImpl$dumpFlows$$inlined$thenBy$1
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
                        Intrinsics.checkNotNull(entry3);
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
        return f;
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public <T, F extends StateFlow> F dumpValue(F f, String str) {
        this.stateFlowMap.put(str, f);
        return f;
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public <T> Flow dumpWhileCollecting(Flow flow, String str) {
        return new SafeFlow(new FlowDumperImpl$dumpWhileCollecting$1(str, this, flow, null));
    }

    public /* synthetic */ FlowDumperImpl(DumpManager dumpManager, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(dumpManager, (i & 2) != 0 ? null : str);
    }
}

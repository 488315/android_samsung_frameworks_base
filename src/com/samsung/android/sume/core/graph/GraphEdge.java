package com.samsung.android.sume.core.graph;

import android.util.Pair;
import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.evaluate.EvalNone;
import com.samsung.android.sume.core.evaluate.Evaluator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes6.dex */
public class GraphEdge {
    public static int INVALID_ID = -1;
    private final BufferChannel bufferChannel;
    private final Evaluator evaluator;
    private int id;
    private Pair<String, String> node;

    public GraphEdge(int i) {
        this((BufferChannel) null, (Evaluator) null);
        this.id = i;
    }

    public GraphEdge(int i, Evaluator evaluator) {
        this((BufferChannel) null, evaluator);
        this.id = i;
    }

    public GraphEdge(BufferChannel bufferChannel) {
        this(bufferChannel, (Evaluator) null);
    }

    public GraphEdge(BufferChannel bufferChannel, Evaluator evaluator) {
        this.id = INVALID_ID;
        this.bufferChannel = bufferChannel;
        this.evaluator = evaluator;
    }

    public int getId() {
        return this.id;
    }

    public Evaluator getEvaluator() {
        return (Evaluator) Optional.ofNullable(this.evaluator).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.graph.GraphEdge$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return new EvalNone();
            }
        });
    }

    public BufferChannel getBufferChannel() {
        return this.bufferChannel;
    }

    public void setNode(String str, String str2) {
        this.node = new Pair<>(str, str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ String lambda$getBeginNode$0(Pair pair) {
        return (String) pair.first;
    }

    public String getBeginNode() {
        return (String) Optional.ofNullable(this.node).map(new Function() { // from class: com.samsung.android.sume.core.graph.GraphEdge$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return GraphEdge.lambda$getBeginNode$0((Pair) obj);
            }
        }).orElse("n/a");
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ String lambda$getEndNode$1(Pair pair) {
        return (String) pair.second;
    }

    public String getEndNode() {
        return (String) Optional.ofNullable(this.node).map(new Function() { // from class: com.samsung.android.sume.core.graph.GraphEdge$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return GraphEdge.lambda$getEndNode$1((Pair) obj);
            }
        }).orElse("n/a");
    }
}

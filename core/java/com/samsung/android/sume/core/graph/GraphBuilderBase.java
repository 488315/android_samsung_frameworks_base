package com.samsung.android.sume.core.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public abstract class GraphBuilderBase<T> implements GraphBuilder<T> {
    protected List<GraphNode<T>> graphNodes = new ArrayList();

    @Override // com.samsung.android.sume.core.graph.GraphBuilder
    public GraphBuilder<T> addNode(GraphNode<? extends T>... nodes) {
        this.graphNodes.addAll((Collection) Arrays.stream(nodes).map(new Function() { // from class: com.samsung.android.sume.core.graph.GraphBuilderBase$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return GraphBuilderBase.lambda$addNode$0((GraphNode) obj);
            }
        }).collect(Collectors.toList()));
        return this;
    }

    static /* synthetic */ GraphNode lambda$addNode$0(GraphNode e) {
        return e;
    }
}

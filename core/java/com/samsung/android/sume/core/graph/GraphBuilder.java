package com.samsung.android.sume.core.graph;

import com.samsung.android.sume.core.channel.BufferChannelDescriptor;
import com.samsung.android.sume.core.evaluate.Evaluator;

/* loaded from: classes4.dex */
public interface GraphBuilder<T> {
    GraphBuilder<T> addNode(GraphNode<? extends T> graphNode, GraphNode<? extends T> graphNode2);

    GraphBuilder<T> addNode(GraphNode<? extends T> graphNode, GraphNode<? extends T> graphNode2, BufferChannelDescriptor bufferChannelDescriptor);

    GraphBuilder<T> addNode(GraphNode<? extends T> graphNode, GraphNode<? extends T> graphNode2, Evaluator evaluator);

    GraphBuilder<T> addNode(GraphNode<? extends T> graphNode, GraphNode<? extends T> graphNode2, Evaluator evaluator, BufferChannelDescriptor bufferChannelDescriptor);

    GraphBuilder<T> addNode(GraphNode<? extends T>... graphNodeArr);

    Graph<T> build();
}

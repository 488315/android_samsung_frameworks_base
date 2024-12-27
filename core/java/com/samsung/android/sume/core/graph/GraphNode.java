package com.samsung.android.sume.core.graph;

import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.message.MessagePublisher;
import com.samsung.android.sume.core.message.MessageSubscriber;

import java.util.function.Function;

/* loaded from: classes6.dex */
public interface GraphNode<T> extends MessageSubscriber {
    GraphNode<T> addInputEdge(GraphEdge graphEdge);

    GraphNode<T> addOutputEdge(GraphEdge graphEdge);

    boolean containsOption(int i);

    T get();

    MFDescriptor getDescriptor();

    Function<Exception, Boolean> getExceptionHandler();

    String getNodeId();

    <V> V getOption(int i);

    <V> V getOption(int i, V v);

    BufferChannel getReceiveChannelRouter();

    BufferChannel getSendChannelRouter();

    boolean hasInputEdge();

    boolean hasOutputEdge();

    void pause();

    void prepare(Graph.Option option);

    void release();

    void resume();

    void setExceptionHandler(Function<Exception, Boolean> function);

    void setMessagePublisher(MessagePublisher messagePublisher);

    void setOption(int i);

    void setOption(int i, Object obj);
}

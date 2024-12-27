package com.samsung.android.sume.core.graph;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda3;
import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.channel.ReceiveChannelRouter;
import com.samsung.android.sume.core.channel.SendChannelRouter;
import com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda8;
import com.samsung.android.sume.core.channel.SendChannelRouter$$ExternalSyntheticLambda9;
import com.samsung.android.sume.core.channel.VoidBufferChannel;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.nn.NNFWDescriptor;
import com.samsung.android.sume.core.evaluate.Evaluator;
import com.samsung.android.sume.core.filter.DecorateFilter;
import com.samsung.android.sume.core.filter.ImgpDecorateFilter;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.filter.MediaFilterGroup;
import com.samsung.android.sume.core.filter.MediaFilterPlaceHolder;
import com.samsung.android.sume.core.filter.MediaFilterRetriever;
import com.samsung.android.sume.core.filter.MediaFilterTracer;
import com.samsung.android.sume.core.filter.MediaInputStreamFilter;
import com.samsung.android.sume.core.filter.MediaOutputStreamFilter;
import com.samsung.android.sume.core.filter.NNFWFilter;
import com.samsung.android.sume.core.message.MessageChannel;
import com.samsung.android.sume.core.message.MessagePublisher;
import com.samsung.android.sume.core.message.MessageSubscriberBase;
import com.samsung.android.sume.core.types.nn.NNFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class GraphNodeBase<T> extends MessageSubscriberBase implements GraphNode<T> {
    private static final String TAG = Def.tagOf((Class<?>) GraphNodeBase.class);
    private Function<Exception, Boolean> exceptionHandler;
    protected final T impl;
    protected final List<GraphEdge> inputEdges;
    protected MessagePublisher messagePublisher;
    protected String nodeId;
    protected Graph.Option option;
    protected final List<GraphEdge> outputEdges;
    private boolean quit;
    protected BufferChannel receiveRouter;
    protected BufferChannel sendRouter;

    protected GraphNodeBase(T impl, MessageChannel messageChannel) {
        super(messageChannel);
        this.inputEdges = new ArrayList();
        this.outputEdges = new ArrayList();
        this.option = new Graph.Option();
        this.quit = false;
        this.impl = impl;
        if (impl instanceof MediaFilter) {
            MediaFilter mediaFilter = (MediaFilter) impl;
            this.nodeId = mediaFilter.getId() + "@" + impl.hashCode();
            MediaFilterRetriever mediaFilterRetriever = new MediaFilterRetriever();
            mediaFilterRetriever.addPredicateHandler(
                    new MediaFilterRetriever
                            .Predictor() { // from class:
                                           // com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda0
                        @Override // com.samsung.android.sume.core.filter.MediaFilterRetriever.Predictor
                        public final boolean predicate(MediaFilter mediaFilter2) {
                            return GraphNodeBase.lambda$new$0(mediaFilter2);
                        }
                    },
                    new MediaFilterRetriever
                            .PredicateHandler() { // from class:
                                                  // com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda1
                        @Override // com.samsung.android.sume.core.filter.MediaFilterRetriever.PredicateHandler
                        public final void onPredicate(
                                MediaFilter mediaFilter2, MediaFilter mediaFilter3) {
                            GraphNodeBase.this
                                    .m9180lambda$new$1$comsamsungandroidsumecoregraphGraphNodeBase(
                                            mediaFilter2, mediaFilter3);
                        }
                    });
            mediaFilterRetriever.retrieve(mediaFilter);
        }
        this.exceptionHandler =
                new Function() { // from class:
                                 // com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        boolean parseException;
                        parseException = GraphNodeBase.this.parseException((Exception) obj);
                        return Boolean.valueOf(parseException);
                    }
                };
    }

    static /* synthetic */ boolean lambda$new$0(MediaFilter filter) {
        return true;
    }

    /* renamed from: lambda$new$1$com-samsung-android-sume-core-graph-GraphNodeBase, reason: not valid java name */
    /* synthetic */ void m9180lambda$new$1$comsamsungandroidsumecoregraphGraphNodeBase(
            MediaFilter filter, MediaFilter parent) {
        addMessageConsumer(filter);
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public String getNodeId() {
        return this.nodeId;
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public T get() {
        return this.impl;
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public MFDescriptor getDescriptor() {
        if (this.impl instanceof MFDescriptor) {
            return (MFDescriptor) this.impl;
        }
        if (this.impl instanceof MediaFilter) {
            return ((MediaFilter) this.impl).getDescriptor();
        }
        throw new IllegalStateException("type is not MediaFilter either MFDescriptor");
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public GraphNode<T> addInputEdge(GraphEdge edge) {
        this.inputEdges.add(edge);
        return this;
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public GraphNode<T> addOutputEdge(GraphEdge edge) {
        this.outputEdges.add(edge);
        return this;
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public BufferChannel getReceiveChannelRouter() {
        return this.receiveRouter;
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public BufferChannel getSendChannelRouter() {
        return this.sendRouter;
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public boolean hasInputEdge() {
        return !this.inputEdges.isEmpty();
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public boolean hasOutputEdge() {
        return !this.outputEdges.isEmpty();
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public void prepare(Graph.Option option) {
        Log.d(TAG, "prepare[" + this.nodeId + "]: " + this.impl);
        if (this.impl instanceof MediaFilter) {
            MediaFilter mediaFilter = (MediaFilter) this.impl;
            applyGraphOption(option);
            MediaFilter.Option filterOption = getDescriptor().getOption();
            if (mediaFilter instanceof MediaInputStreamFilter) {
                if (this.inputEdges.size() > 1) {
                    final Map<Enum<?>, BufferChannel> map =
                            (Map)
                                    this.inputEdges.stream()
                                            .collect(
                                                    Collectors.toMap(
                                                            new Function() { // from class:
                                                                             // com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda3
                                                                @Override // java.util.function.Function
                                                                public final Object apply(
                                                                        Object obj) {
                                                                    return GraphNodeBase
                                                                            .lambda$prepare$2(
                                                                                    (GraphEdge)
                                                                                            obj);
                                                                }
                                                            },
                                                            new SendChannelRouter$$ExternalSyntheticLambda9()));
                    Objects.requireNonNull(map);
                    ((MediaInputStreamFilter) mediaFilter)
                            .setReceiveChannelQuery(
                                    new Function() { // from class:
                                                     // com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda4
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            return (BufferChannel) map.get((Enum) obj);
                                        }
                                    },
                                    map.size());
                } else {
                    ((MediaInputStreamFilter) mediaFilter)
                            .setReceiveChannelQuery(
                                    new Function() { // from class:
                                                     // com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda5
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            return GraphNodeBase.this.m9181x49a0da5f((Enum) obj);
                                        }
                                    },
                                    1);
                }
                this.receiveRouter = new VoidBufferChannel();
            } else {
                this.receiveRouter =
                        new ReceiveChannelRouter(
                                (Map<Evaluator, BufferChannel>)
                                        this.inputEdges.stream()
                                                .collect(
                                                        Collectors.toMap(
                                                                new SendChannelRouter$$ExternalSyntheticLambda8(),
                                                                new SendChannelRouter$$ExternalSyntheticLambda9())),
                                filterOption.isWaitToReceiveAll()
                                        ? ReceiveChannelRouter.Type.ALL
                                        : ReceiveChannelRouter.Type.ANY);
            }
            if (mediaFilter instanceof MediaOutputStreamFilter) {
                if (this.outputEdges.size() > 1) {
                    final Map<Enum<?>, BufferChannel> map2 =
                            (Map)
                                    this.outputEdges.stream()
                                            .collect(
                                                    Collectors.toMap(
                                                            new Function() { // from class:
                                                                             // com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda6
                                                                @Override // java.util.function.Function
                                                                public final Object apply(
                                                                        Object obj) {
                                                                    return GraphNodeBase
                                                                            .lambda$prepare$4(
                                                                                    (GraphEdge)
                                                                                            obj);
                                                                }
                                                            },
                                                            new SendChannelRouter$$ExternalSyntheticLambda9()));
                    Objects.requireNonNull(map2);
                    ((MediaOutputStreamFilter) mediaFilter)
                            .setSendChannelQuery(
                                    new Function() { // from class:
                                                     // com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda4
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            return (BufferChannel) map2.get((Enum) obj);
                                        }
                                    },
                                    map2.size());
                } else {
                    ((MediaOutputStreamFilter) mediaFilter)
                            .setSendChannelQuery(
                                    new Function() { // from class:
                                                     // com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda7
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            return GraphNodeBase.this.m9182xd0b715e1((Enum) obj);
                                        }
                                    },
                                    1);
                }
                this.sendRouter = new VoidBufferChannel();
            } else {
                this.sendRouter =
                        new SendChannelRouter(
                                (Map<Evaluator, BufferChannel>)
                                        this.outputEdges.stream()
                                                .collect(
                                                        Collectors.toMap(
                                                                new SendChannelRouter$$ExternalSyntheticLambda8(),
                                                                new SendChannelRouter$$ExternalSyntheticLambda9())),
                                filterOption.isAllowPartialConnection()
                                        ? SendChannelRouter.Type.ANY
                                        : SendChannelRouter.Type.ALL);
            }
            mediaFilter.prepare();
        }
        Log.i(TAG, "success to prepare MediaFilter");
    }

    static /* synthetic */ Enum lambda$prepare$2(GraphEdge e) {
        return (Enum) e.getEvaluator().getValue();
    }

    /* renamed from: lambda$prepare$3$com-samsung-android-sume-core-graph-GraphNodeBase, reason: not valid java name */
    /* synthetic */ BufferChannel m9181x49a0da5f(Enum type) {
        return (BufferChannel)
                this.inputEdges.stream()
                        .findFirst()
                        .map(new SendChannelRouter$$ExternalSyntheticLambda9())
                        .orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
    }

    static /* synthetic */ Enum lambda$prepare$4(GraphEdge it) {
        return (Enum) it.getEvaluator().getValue();
    }

    /* renamed from: lambda$prepare$5$com-samsung-android-sume-core-graph-GraphNodeBase, reason: not valid java name */
    /* synthetic */ BufferChannel m9182xd0b715e1(Enum type) {
        return (BufferChannel)
                this.outputEdges.stream()
                        .findFirst()
                        .map(new SendChannelRouter$$ExternalSyntheticLambda9())
                        .orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
    }

    void applyGraphOption(Graph.Option option) {
        Log.d(TAG, "applyGraphOption: option=" + option + " => node=" + this);
        final MediaFilterRetriever mediaFilterRetriever = new MediaFilterRetriever();
        if (option.isIgnoreFilterException()) {
            List<Object> keyOfFilterToIgnoreException = (List) option.getIgnoreFilterException();
            keyOfFilterToIgnoreException.forEach(
                    new Consumer() { // from class:
                                     // com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda8
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            GraphNodeBase.this.m9179x9e96944c(mediaFilterRetriever, obj);
                        }
                    });
        }
        if (option.isTraceMediaFilter()) {
            mediaFilterRetriever.addPredicateHandler(
                    new MediaFilterRetriever
                            .Predictor() { // from class:
                                           // com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda9
                        @Override // com.samsung.android.sume.core.filter.MediaFilterRetriever.Predictor
                        public final boolean predicate(MediaFilter mediaFilter) {
                            return GraphNodeBase.lambda$applyGraphOption$9(mediaFilter);
                        }
                    },
                    new MediaFilterRetriever
                            .PredicateHandler() { // from class:
                                                  // com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda10
                        @Override // com.samsung.android.sume.core.filter.MediaFilterRetriever.PredicateHandler
                        public final void onPredicate(
                                MediaFilter mediaFilter, MediaFilter mediaFilter2) {
                            GraphNodeBase.this.m9177x2d42a2d7(mediaFilter, mediaFilter2);
                        }
                    });
        }
        mediaFilterRetriever.retrieve((MediaFilter) get());
    }

    /* renamed from: lambda$applyGraphOption$8$com-samsung-android-sume-core-graph-GraphNodeBase, reason: not valid java name */
    /* synthetic */ void m9179x9e96944c(MediaFilterRetriever mediaFilterRetriever, Object key) {
        if (key instanceof NNFW) {
            final NNFW fw = (NNFW) key;
            mediaFilterRetriever.addPredicateHandler(
                    new MediaFilterRetriever
                            .Predictor() { // from class:
                                           // com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda14
                        @Override // com.samsung.android.sume.core.filter.MediaFilterRetriever.Predictor
                        public final boolean predicate(MediaFilter mediaFilter) {
                            return GraphNodeBase.lambda$applyGraphOption$6(NNFW.this, mediaFilter);
                        }
                    },
                    new MediaFilterRetriever
                            .PredicateHandler() { // from class:
                                                  // com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda15
                        @Override // com.samsung.android.sume.core.filter.MediaFilterRetriever.PredicateHandler
                        public final void onPredicate(
                                MediaFilter mediaFilter, MediaFilter mediaFilter2) {
                            GraphNodeBase.this.m9178x5b0b768b(mediaFilter, mediaFilter2);
                        }
                    });
            return;
        }
        throw new UnsupportedOperationException("unknown key: " + key);
    }

    static /* synthetic */ boolean lambda$applyGraphOption$6(NNFW fw, MediaFilter filter) {
        return (filter instanceof NNFWFilter)
                && ((NNFWDescriptor) filter.getDescriptor()).getFw() == fw;
    }

    /* renamed from: lambda$applyGraphOption$7$com-samsung-android-sume-core-graph-GraphNodeBase, reason: not valid java name */
    /* synthetic */ void m9178x5b0b768b(MediaFilter filter, MediaFilter parent) {
        ((ArrayList) getOption(6, new ArrayList())).add(filter.getDescriptor().getFilterId());
    }

    static /* synthetic */ boolean lambda$applyGraphOption$9(MediaFilter filter) {
        return ((filter instanceof DecorateFilter) || (filter instanceof MediaFilterGroup))
                ? false
                : true;
    }

    /* renamed from: lambda$applyGraphOption$10$com-samsung-android-sume-core-graph-GraphNodeBase, reason: not valid java name */
    /* synthetic */ void m9177x2d42a2d7(MediaFilter filter, MediaFilter parent) {
        Log.d(TAG, "found leaf filter=" + filter + ", parent=" + parent);
        if (filter instanceof MediaFilterPlaceHolder) {
            Log.d(TAG, "skip to trace MediaFilterPlaceHolder");
            return;
        }
        MediaFilter mediaFilterTracer = null;
        if (parent instanceof ImgpDecorateFilter) {
            ImgpDecorateFilter parentFilter = (ImgpDecorateFilter) parent;
            if (parentFilter.getPreFilter() == filter) {
                mediaFilterTracer =
                        new MediaFilterTracer(filter, this.messagePublisher.getMessageProducer());
                parentFilter.setPreFilter(mediaFilterTracer);
            } else if (parentFilter.getPostFilter() == filter) {
                mediaFilterTracer =
                        new MediaFilterTracer(filter, this.messagePublisher.getMessageProducer());
                parentFilter.setPostFilter(mediaFilterTracer);
            } else {
                mediaFilterTracer =
                        new MediaFilterTracer(filter, this.messagePublisher.getMessageProducer());
                parentFilter.setSuccessorFilter(mediaFilterTracer);
            }
        } else if (parent instanceof DecorateFilter) {
            DecorateFilter parentFilter2 = (DecorateFilter) parent;
            mediaFilterTracer =
                    new MediaFilterTracer(
                            filter, this.messagePublisher.getMessageProducer(), parent);
            parentFilter2.setSuccessorFilter(mediaFilterTracer);
        } else if (parent instanceof MediaFilterGroup) {
            MediaFilterGroup parentFilter3 = (MediaFilterGroup) parent;
            mediaFilterTracer =
                    new MediaFilterTracer(filter, this.messagePublisher.getMessageProducer());
            parentFilter3.replaceFilter(filter, mediaFilterTracer);
        }
        if (mediaFilterTracer != null) {
            addMessageConsumer(mediaFilterTracer);
        }
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public void setMessagePublisher(MessagePublisher messagePublisher) {
        messagePublisher.setName(this.nodeId);
        this.messagePublisher = messagePublisher;
        if (this.impl instanceof MediaFilter) {
            MediaFilter mediaFilter = (MediaFilter) this.impl;
            mediaFilter.setMessageProducer(messagePublisher.getMessageProducer());
        }
    }

    @Override // com.samsung.android.sume.core.message.MessageSubscriberBase
    public void release() {
        Log.d(TAG, "release...E: " + this.nodeId);
        super.release();
        Stream.concat(this.inputEdges.stream(), this.outputEdges.stream())
                .filter(
                        new Predicate() { // from class:
                                          // com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda12
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return GraphNodeBase.lambda$release$11((GraphEdge) obj);
                            }
                        })
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda13
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                GraphNodeBase.lambda$release$12((GraphEdge) obj);
                            }
                        });
        if (this.impl instanceof MediaFilter) {
            ((MediaFilter) this.impl).release();
        }
        Log.d(TAG, "release...X: " + this.nodeId);
    }

    static /* synthetic */ boolean lambda$release$11(GraphEdge it) {
        return it.getBufferChannel() != null;
    }

    static /* synthetic */ void lambda$release$12(GraphEdge it) {
        BufferChannel bufferChannel = it.getBufferChannel();
        Log.d(
                TAG,
                NavigationBarInflaterView.SIZE_MOD_START
                        + it.getBeginNode()
                        + " => "
                        + it.getEndNode()
                        + "]cancel buffer channel"
                        + bufferChannel);
        try {
            bufferChannel.cancel();
        } catch (CancellationException e) {
            Log.d(TAG, "canceled buffer-channel: " + e.getMessage());
        }
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public void setOption(int option) {
        this.option.set(option);
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public void setOption(int option, Object data) {
        this.option.set(option, data);
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public boolean containsOption(int option) {
        return this.option.contains(option);
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public <V> V getOption(int i) {
        return (V) this.option.get(i);
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public <V> V getOption(int i, V v) {
        return (V) this.option.get(i, v);
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public void setExceptionHandler(Function<Exception, Boolean> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public Function<Exception, Boolean> getExceptionHandler() {
        return this.exceptionHandler;
    }

    protected boolean isQuit() {
        return this.quit;
    }

    public void setQuit(boolean quit) {
        this.quit = quit;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean parseException(Exception exception) {
        try {
            String msg = exception.getMessage();
            String[] tokens = msg.split("]@");
            final String filterId = tokens[0].substring(2);
            List<String> filters = (List) getOption(6);
            if (filters != null) {
                return filters.stream()
                        .anyMatch(
                                new Predicate() { // from class:
                                                  // com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda11
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        boolean equals;
                                        equals = ((String) obj).equals(filterId);
                                        return equals;
                                    }
                                });
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}

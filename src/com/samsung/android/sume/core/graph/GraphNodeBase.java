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
import com.samsung.android.sume.core.graph.Graph;
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

/* loaded from: classes6.dex */
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

    static /* synthetic */ boolean lambda$new$0(MediaFilter mediaFilter) {
        return true;
    }

    protected GraphNodeBase(T t, MessageChannel messageChannel) {
        super(messageChannel);
        this.inputEdges = new ArrayList();
        this.outputEdges = new ArrayList();
        this.option = new Graph.Option();
        this.quit = false;
        this.impl = t;
        if (t instanceof MediaFilter) {
            MediaFilter mediaFilter = (MediaFilter) t;
            this.nodeId = mediaFilter.getId() + "@" + t.hashCode();
            MediaFilterRetriever mediaFilterRetriever = new MediaFilterRetriever();
            mediaFilterRetriever.addPredicateHandler(new MediaFilterRetriever.Predictor() { // from class: com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda0
                @Override // com.samsung.android.sume.core.filter.MediaFilterRetriever.Predictor
                public final boolean predicate(MediaFilter mediaFilter2) {
                    return GraphNodeBase.lambda$new$0(mediaFilter2);
                }
            }, new MediaFilterRetriever.PredicateHandler() { // from class: com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda1
                @Override // com.samsung.android.sume.core.filter.MediaFilterRetriever.PredicateHandler
                public final void onPredicate(MediaFilter mediaFilter2, MediaFilter mediaFilter3) {
                    GraphNodeBase.this.m9581lambda$new$1$comsamsungandroidsumecoregraphGraphNodeBase(mediaFilter2, mediaFilter3);
                }
            });
            mediaFilterRetriever.retrieve(mediaFilter);
        }
        this.exceptionHandler = new Function() { // from class: com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                boolean parseException;
                parseException = GraphNodeBase.this.parseException((Exception) obj);
                return Boolean.valueOf(parseException);
            }
        };
    }

    /* renamed from: lambda$new$1$com-samsung-android-sume-core-graph-GraphNodeBase, reason: not valid java name */
    /* synthetic */ void m9581lambda$new$1$comsamsungandroidsumecoregraphGraphNodeBase(MediaFilter mediaFilter, MediaFilter mediaFilter2) {
        addMessageConsumer(mediaFilter);
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
        T t = this.impl;
        if (t instanceof MFDescriptor) {
            return (MFDescriptor) t;
        }
        if (t instanceof MediaFilter) {
            return ((MediaFilter) t).getDescriptor();
        }
        throw new IllegalStateException("type is not MediaFilter either MFDescriptor");
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public GraphNode<T> addInputEdge(GraphEdge graphEdge) {
        this.inputEdges.add(graphEdge);
        return this;
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public GraphNode<T> addOutputEdge(GraphEdge graphEdge) {
        this.outputEdges.add(graphEdge);
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
        String str = TAG;
        Log.d(str, "prepare[" + this.nodeId + "]: " + this.impl);
        T t = this.impl;
        if (t instanceof MediaFilter) {
            MediaFilter mediaFilter = (MediaFilter) t;
            applyGraphOption(option);
            MediaFilter.Option option2 = getDescriptor().getOption();
            if (mediaFilter instanceof MediaInputStreamFilter) {
                if (this.inputEdges.size() > 1) {
                    final Map map = (Map) this.inputEdges.stream().collect(Collectors.toMap(new Function() { // from class: com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda3
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return GraphNodeBase.lambda$prepare$2((GraphEdge) obj);
                        }
                    }, new SendChannelRouter$$ExternalSyntheticLambda9()));
                    Objects.requireNonNull(map);
                    ((MediaInputStreamFilter) mediaFilter).setReceiveChannelQuery(new Function() { // from class: com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda4
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return (BufferChannel) map.get((Enum) obj);
                        }
                    }, map.size());
                } else {
                    ((MediaInputStreamFilter) mediaFilter).setReceiveChannelQuery(new Function() { // from class: com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda5
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return GraphNodeBase.this.m9582x49a0da5f((Enum) obj);
                        }
                    }, 1);
                }
                this.receiveRouter = new VoidBufferChannel();
            } else {
                this.receiveRouter = new ReceiveChannelRouter((Map<Evaluator, BufferChannel>) this.inputEdges.stream().collect(Collectors.toMap(new SendChannelRouter$$ExternalSyntheticLambda8(), new SendChannelRouter$$ExternalSyntheticLambda9())), option2.isWaitToReceiveAll() ? ReceiveChannelRouter.Type.ALL : ReceiveChannelRouter.Type.ANY);
            }
            if (mediaFilter instanceof MediaOutputStreamFilter) {
                if (this.outputEdges.size() > 1) {
                    final Map map2 = (Map) this.outputEdges.stream().collect(Collectors.toMap(new Function() { // from class: com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda6
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return GraphNodeBase.lambda$prepare$4((GraphEdge) obj);
                        }
                    }, new SendChannelRouter$$ExternalSyntheticLambda9()));
                    Objects.requireNonNull(map2);
                    ((MediaOutputStreamFilter) mediaFilter).setSendChannelQuery(new Function() { // from class: com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda4
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return (BufferChannel) map2.get((Enum) obj);
                        }
                    }, map2.size());
                } else {
                    ((MediaOutputStreamFilter) mediaFilter).setSendChannelQuery(new Function() { // from class: com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda7
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return GraphNodeBase.this.m9583xd0b715e1((Enum) obj);
                        }
                    }, 1);
                }
                this.sendRouter = new VoidBufferChannel();
            } else {
                this.sendRouter = new SendChannelRouter((Map<Evaluator, BufferChannel>) this.outputEdges.stream().collect(Collectors.toMap(new SendChannelRouter$$ExternalSyntheticLambda8(), new SendChannelRouter$$ExternalSyntheticLambda9())), option2.isAllowPartialConnection() ? SendChannelRouter.Type.ANY : SendChannelRouter.Type.ALL);
            }
            mediaFilter.prepare();
        }
        Log.i(str, "success to prepare MediaFilter");
    }

    static /* synthetic */ Enum lambda$prepare$2(GraphEdge graphEdge) {
        return (Enum) graphEdge.getEvaluator().getValue();
    }

    /* renamed from: lambda$prepare$3$com-samsung-android-sume-core-graph-GraphNodeBase, reason: not valid java name */
    /* synthetic */ BufferChannel m9582x49a0da5f(Enum r1) {
        return (BufferChannel) this.inputEdges.stream().findFirst().map(new SendChannelRouter$$ExternalSyntheticLambda9()).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
    }

    static /* synthetic */ Enum lambda$prepare$4(GraphEdge graphEdge) {
        return (Enum) graphEdge.getEvaluator().getValue();
    }

    /* renamed from: lambda$prepare$5$com-samsung-android-sume-core-graph-GraphNodeBase, reason: not valid java name */
    /* synthetic */ BufferChannel m9583xd0b715e1(Enum r1) {
        return (BufferChannel) this.outputEdges.stream().findFirst().map(new SendChannelRouter$$ExternalSyntheticLambda9()).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
    }

    void applyGraphOption(Graph.Option option) {
        Log.d(TAG, "applyGraphOption: option=" + option + " => node=" + this);
        final MediaFilterRetriever mediaFilterRetriever = new MediaFilterRetriever();
        if (option.isIgnoreFilterException()) {
            ((List) option.getIgnoreFilterException()).forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    GraphNodeBase.this.m9580x9e96944c(mediaFilterRetriever, obj);
                }
            });
        }
        if (option.isTraceMediaFilter()) {
            mediaFilterRetriever.addPredicateHandler(new MediaFilterRetriever.Predictor() { // from class: com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda9
                @Override // com.samsung.android.sume.core.filter.MediaFilterRetriever.Predictor
                public final boolean predicate(MediaFilter mediaFilter) {
                    return GraphNodeBase.lambda$applyGraphOption$9(mediaFilter);
                }
            }, new MediaFilterRetriever.PredicateHandler() { // from class: com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda10
                @Override // com.samsung.android.sume.core.filter.MediaFilterRetriever.PredicateHandler
                public final void onPredicate(MediaFilter mediaFilter, MediaFilter mediaFilter2) {
                    GraphNodeBase.this.m9578x2d42a2d7(mediaFilter, mediaFilter2);
                }
            });
        }
        mediaFilterRetriever.retrieve((MediaFilter) get());
    }

    /* renamed from: lambda$applyGraphOption$8$com-samsung-android-sume-core-graph-GraphNodeBase, reason: not valid java name */
    /* synthetic */ void m9580x9e96944c(MediaFilterRetriever mediaFilterRetriever, Object obj) {
        if (obj instanceof NNFW) {
            final NNFW nnfw = (NNFW) obj;
            mediaFilterRetriever.addPredicateHandler(new MediaFilterRetriever.Predictor() { // from class: com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda14
                @Override // com.samsung.android.sume.core.filter.MediaFilterRetriever.Predictor
                public final boolean predicate(MediaFilter mediaFilter) {
                    return GraphNodeBase.lambda$applyGraphOption$6(NNFW.this, mediaFilter);
                }
            }, new MediaFilterRetriever.PredicateHandler() { // from class: com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda15
                @Override // com.samsung.android.sume.core.filter.MediaFilterRetriever.PredicateHandler
                public final void onPredicate(MediaFilter mediaFilter, MediaFilter mediaFilter2) {
                    GraphNodeBase.this.m9579x5b0b768b(mediaFilter, mediaFilter2);
                }
            });
        } else {
            throw new UnsupportedOperationException("unknown key: " + obj);
        }
    }

    static /* synthetic */ boolean lambda$applyGraphOption$6(NNFW nnfw, MediaFilter mediaFilter) {
        return (mediaFilter instanceof NNFWFilter) && ((NNFWDescriptor) mediaFilter.getDescriptor()).getFw() == nnfw;
    }

    /* renamed from: lambda$applyGraphOption$7$com-samsung-android-sume-core-graph-GraphNodeBase, reason: not valid java name */
    /* synthetic */ void m9579x5b0b768b(MediaFilter mediaFilter, MediaFilter mediaFilter2) {
        ((ArrayList) getOption(6, new ArrayList())).add(mediaFilter.getDescriptor().getFilterId());
    }

    static /* synthetic */ boolean lambda$applyGraphOption$9(MediaFilter mediaFilter) {
        return ((mediaFilter instanceof DecorateFilter) || (mediaFilter instanceof MediaFilterGroup)) ? false : true;
    }

    /* renamed from: lambda$applyGraphOption$10$com-samsung-android-sume-core-graph-GraphNodeBase, reason: not valid java name */
    /* synthetic */ void m9578x2d42a2d7(MediaFilter mediaFilter, MediaFilter mediaFilter2) {
        MediaFilterTracer mediaFilterTracer;
        String str = TAG;
        Log.d(str, "found leaf filter=" + mediaFilter + ", parent=" + mediaFilter2);
        if (mediaFilter instanceof MediaFilterPlaceHolder) {
            Log.d(str, "skip to trace MediaFilterPlaceHolder");
            return;
        }
        if (mediaFilter2 instanceof ImgpDecorateFilter) {
            ImgpDecorateFilter imgpDecorateFilter = (ImgpDecorateFilter) mediaFilter2;
            if (imgpDecorateFilter.getPreFilter() == mediaFilter) {
                mediaFilterTracer = new MediaFilterTracer(mediaFilter, this.messagePublisher.getMessageProducer());
                imgpDecorateFilter.setPreFilter(mediaFilterTracer);
            } else if (imgpDecorateFilter.getPostFilter() == mediaFilter) {
                mediaFilterTracer = new MediaFilterTracer(mediaFilter, this.messagePublisher.getMessageProducer());
                imgpDecorateFilter.setPostFilter(mediaFilterTracer);
            } else {
                mediaFilterTracer = new MediaFilterTracer(mediaFilter, this.messagePublisher.getMessageProducer());
                imgpDecorateFilter.setSuccessorFilter(mediaFilterTracer);
            }
        } else if (mediaFilter2 instanceof DecorateFilter) {
            MediaFilterTracer mediaFilterTracer2 = new MediaFilterTracer(mediaFilter, this.messagePublisher.getMessageProducer(), mediaFilter2);
            ((DecorateFilter) mediaFilter2).setSuccessorFilter(mediaFilterTracer2);
            mediaFilterTracer = mediaFilterTracer2;
        } else if (mediaFilter2 instanceof MediaFilterGroup) {
            mediaFilterTracer = new MediaFilterTracer(mediaFilter, this.messagePublisher.getMessageProducer());
            ((MediaFilterGroup) mediaFilter2).replaceFilter(mediaFilter, mediaFilterTracer);
        } else {
            mediaFilterTracer = null;
        }
        if (mediaFilterTracer != null) {
            addMessageConsumer(mediaFilterTracer);
        }
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public void setMessagePublisher(MessagePublisher messagePublisher) {
        messagePublisher.setName(this.nodeId);
        this.messagePublisher = messagePublisher;
        T t = this.impl;
        if (t instanceof MediaFilter) {
            ((MediaFilter) t).setMessageProducer(messagePublisher.getMessageProducer());
        }
    }

    @Override // com.samsung.android.sume.core.message.MessageSubscriberBase
    public void release() {
        String str = TAG;
        Log.d(str, "release...E: " + this.nodeId);
        super.release();
        Stream.concat(this.inputEdges.stream(), this.outputEdges.stream()).filter(new Predicate() { // from class: com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return GraphNodeBase.lambda$release$11((GraphEdge) obj);
            }
        }).forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                GraphNodeBase.lambda$release$12((GraphEdge) obj);
            }
        });
        T t = this.impl;
        if (t instanceof MediaFilter) {
            ((MediaFilter) t).release();
        }
        Log.d(str, "release...X: " + this.nodeId);
    }

    static /* synthetic */ boolean lambda$release$11(GraphEdge graphEdge) {
        return graphEdge.getBufferChannel() != null;
    }

    static /* synthetic */ void lambda$release$12(GraphEdge graphEdge) {
        BufferChannel bufferChannel = graphEdge.getBufferChannel();
        Log.d(TAG, NavigationBarInflaterView.SIZE_MOD_START + graphEdge.getBeginNode() + " => " + graphEdge.getEndNode() + "]cancel buffer channel" + bufferChannel);
        try {
            bufferChannel.cancel();
        } catch (CancellationException e) {
            Log.d(TAG, "canceled buffer-channel: " + e.getMessage());
        }
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public void setOption(int i) {
        this.option.set(i);
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public void setOption(int i, Object obj) {
        this.option.set(i, obj);
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public boolean containsOption(int i) {
        return this.option.contains(i);
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
    public void setExceptionHandler(Function<Exception, Boolean> function) {
        this.exceptionHandler = function;
    }

    @Override // com.samsung.android.sume.core.graph.GraphNode
    public Function<Exception, Boolean> getExceptionHandler() {
        return this.exceptionHandler;
    }

    protected boolean isQuit() {
        return this.quit;
    }

    public void setQuit(boolean z) {
        this.quit = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean parseException(Exception exc) {
        try {
            final String substring = exc.getMessage().split("]@")[0].substring(2);
            List list = (List) getOption(6);
            if (list != null) {
                if (list.stream().anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.graph.GraphNodeBase$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean equals;
                        equals = ((String) obj).equals(substring);
                        return equals;
                    }
                })) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }
}

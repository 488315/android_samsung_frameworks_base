package com.samsung.android.sume.core.graph;

import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.tv.interactive.TvInteractiveAppService;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.cache.DiskCache;
import com.samsung.android.sume.core.cache.KeyGenerator;
import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.channel.BufferChannelDescriptor;
import com.samsung.android.sume.core.channel.BufferSupplyChannel;
import com.samsung.android.sume.core.channel.SurfaceChannel;
import com.samsung.android.sume.core.evaluate.Evaluator;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.filter.collection.BufferedConveyorFilter$$ExternalSyntheticLambda1;
import com.samsung.android.sume.core.functional.BufferSupplier;
import com.samsung.android.sume.core.graph.Graph;
import com.samsung.android.sume.core.message.Event;
import com.samsung.android.sume.core.message.Message;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class MFGraph extends GraphBase<MediaFilter> {
    private static final String TAG = Def.tagOf((Class<?>) MFGraph.class);

    private MFGraph(List<GraphNode<MediaFilter>> list, final BufferChannel bufferChannel, final BufferChannel bufferChannel2, final Graph.Option option) {
        super(list, option);
        this.inputChannel = bufferChannel;
        this.outputChannel = bufferChannel2;
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        String str = TAG;
        Log.d(str, "prepare each node..." + list.size());
        list.forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.MFGraph$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m9597lambda$new$0$comsamsungandroidsumecoregraphMFGraph(bufferChannel, arrayList, bufferChannel2, arrayList2, option, (GraphNode) obj);
            }
        });
        Def.check(arrayList.isEmpty() ^ true, "no input node given", new Object[0]);
        Def.check(!arrayList2.isEmpty(), "no input node given", new Object[0]);
        Log.i(str, "success to create MediaFilter graph");
    }

    /* renamed from: lambda$new$0$com-samsung-android-sume-core-graph-MFGraph, reason: not valid java name */
    /* synthetic */ void m9597lambda$new$0$comsamsungandroidsumecoregraphMFGraph(BufferChannel bufferChannel, List list, BufferChannel bufferChannel2, List list2, Graph.Option option, GraphNode graphNode) {
        if (!graphNode.hasInputEdge()) {
            graphNode.addInputEdge(new GraphEdge(bufferChannel));
            list.add(graphNode);
        }
        if (!graphNode.hasOutputEdge()) {
            graphNode.addOutputEdge(new GraphEdge(bufferChannel2));
            list2.add(graphNode);
        }
        if (graphNode.getDescriptor().getOption().isAllowPartialConnection()) {
            graphNode.addOutputEdge(new GraphEdge(bufferChannel2));
        }
        graphNode.setMessagePublisher(this.messageChannelRouter.newMessagePublisher());
        graphNode.prepare(option);
        this.messageChannelRouter.addMessageSubscriber(graphNode);
    }

    @Override // com.samsung.android.sume.core.graph.Graph
    public void run(final List<MediaBuffer> list, final List<MediaBuffer> list2) {
        String str = TAG;
        Log.d(str, "run E");
        if (this.option.getMaxDuration(TimeUnit.MICROSECONDS) != 0) {
            list.forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.MFGraph$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m9598lambda$run$1$comsamsungandroidsumecoregraphMFGraph((MediaBuffer) obj);
                }
            });
        }
        if (this.option.isCacheable()) {
            final DiskCache diskCache = (DiskCache) this.option.get(1);
            list.forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.MFGraph$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) throws IllegalArgumentException {
                    MFGraph.lambda$run$2(diskCache, (MediaBuffer) obj);
                }
            });
            this.messagePublisher.sendMessage(Event.of(6, new HashMap<String, Object>(diskCache) { // from class: com.samsung.android.sume.core.graph.MFGraph.1
                final /* synthetic */ DiskCache val$diskCache;

                {
                    this.val$diskCache = diskCache;
                    put("cache", new Pair(diskCache, Boolean.valueOf(MFGraph.this.option.contains(0))));
                }
            }));
        }
        if (this.option.isPackedIOBuffers()) {
            Def.require(list.size() == list2.size());
            List list3 = (List) IntStream.range(0, list2.size()).mapToObj(new IntFunction() { // from class: com.samsung.android.sume.core.graph.MFGraph$$ExternalSyntheticLambda2
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return this.f$0.m9599lambda$run$3$comsamsungandroidsumecoregraphMFGraph(list, list2, i);
                }
            }).collect(Collectors.toList());
            list.clear();
            list2.clear();
            list.addAll(list3);
        } else if (list.size() == list2.size()) {
            Stream<Integer> streamBoxed = IntStream.range(0, list2.size()).boxed();
            Function function = new Function() { // from class: com.samsung.android.sume.core.graph.MFGraph$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return MFGraph.lambda$run$4(list, (Integer) obj);
                }
            };
            Objects.requireNonNull(list2);
            Map<? extends Integer, ? extends MediaBuffer> map = (Map) streamBoxed.collect(Collectors.toMap(function, new Function() { // from class: com.samsung.android.sume.core.graph.MFGraph$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return (MediaBuffer) list2.get(((Integer) obj).intValue());
                }
            }));
            this.outBufferMap.putAll(map);
            map.clear();
            list2.clear();
        }
        if (this.option.isRunOneByOne()) {
            runOneByOne(list, list2);
        } else {
            runBatch(list, list2);
        }
        if (this.option.isRestoreMetadata()) {
            IntStream.range(0, list.size()).forEach(new IntConsumer() { // from class: com.samsung.android.sume.core.graph.MFGraph$$ExternalSyntheticLambda5
                @Override // java.util.function.IntConsumer
                public final void accept(int i) {
                    MFGraph.lambda$run$5(list, list2, i);
                }
            });
        }
        Log.d(str, "run X");
    }

    /* renamed from: lambda$run$1$com-samsung-android-sume-core-graph-MFGraph, reason: not valid java name */
    /* synthetic */ void m9598lambda$run$1$comsamsungandroidsumecoregraphMFGraph(MediaBuffer mediaBuffer) {
        mediaBuffer.setExtra(Message.KEY_END_TIME_US, Long.valueOf(this.option.getMaxDuration(TimeUnit.MICROSECONDS)));
    }

    static /* synthetic */ void lambda$run$2(DiskCache diskCache, MediaBuffer mediaBuffer) throws IllegalArgumentException {
        File file;
        try {
            if (mediaBuffer.containsExtra(Message.KEY_CACHE_ID) && (file = diskCache.get(KeyGenerator.getSimpleKey((String) mediaBuffer.getExtra(Message.KEY_CACHE_ID)))) != null && file.exists()) {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
                mediaBuffer.setExtra(Message.KEY_START_TIME_US, Long.valueOf(TimeUnit.MILLISECONDS.toMicros(Long.parseLong(mediaMetadataRetriever.extractMetadata(9)))));
                mediaMetadataRetriever.release();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$run$3$com-samsung-android-sume-core-graph-MFGraph, reason: not valid java name */
    /* synthetic */ MediaBuffer m9599lambda$run$3$comsamsungandroidsumecoregraphMFGraph(List list, List list2, int i) {
        MediaBuffer mediaBuffer = (MediaBuffer) list.get(i);
        MediaBuffer mediaBuffer2 = (MediaBuffer) list2.get(i);
        MediaBuffer mediaBufferGroupOf = MediaBuffer.groupOf(new ArrayList<MediaBuffer>(mediaBuffer, mediaBuffer2) { // from class: com.samsung.android.sume.core.graph.MFGraph.2
            final /* synthetic */ MediaBuffer val$inBuffer;
            final /* synthetic */ MediaBuffer val$outBuffer;

            {
                this.val$inBuffer = mediaBuffer;
                this.val$outBuffer = mediaBuffer2;
                add(mediaBuffer);
                add(mediaBuffer2);
            }
        });
        if (mediaBuffer.containsExtra(Message.KEY_CONTENTS_ID)) {
            mediaBufferGroupOf.setExtra(Message.KEY_CONTENTS_ID, mediaBuffer.getExtra(Message.KEY_CONTENTS_ID));
        }
        if (mediaBuffer.containsExtra(Message.KEY_IN_FILE)) {
            mediaBufferGroupOf.setExtra(Message.KEY_IN_FILE, mediaBuffer.getExtra(Message.KEY_IN_FILE));
        }
        if (mediaBuffer2.containsExtra(Message.KEY_OUT_FILE)) {
            mediaBufferGroupOf.setExtra(Message.KEY_OUT_FILE, mediaBuffer2.getExtra(Message.KEY_OUT_FILE));
        }
        mediaBufferGroupOf.setFlags(1);
        return mediaBufferGroupOf;
    }

    static /* synthetic */ Integer lambda$run$4(List list, Integer num) {
        return (Integer) ((MediaBuffer) list.get(num.intValue())).getExtra(Message.KEY_CONTENTS_ID);
    }

    static /* synthetic */ void lambda$run$5(List list, List list2, int i) {
        ExifInterface exifInterface = (ExifInterface) ((MediaBuffer) list.get(i)).getExtra("exif");
        if (exifInterface != null) {
            ((MediaBuffer) list2.get(i)).setExtra("exif", exifInterface);
        }
    }

    @Override // com.samsung.android.sume.core.graph.Graph
    public void pause() {
        Log.d(TAG, TvInteractiveAppService.TIME_SHIFT_COMMAND_TYPE_PAUSE);
        this.nodes.forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.MFGraph$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((GraphNode) obj).pause();
            }
        });
    }

    @Override // com.samsung.android.sume.core.graph.Graph
    public void resume() {
        Log.d(TAG, "resume");
        this.nodes.forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.MFGraph$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((GraphNode) obj).resume();
            }
        });
    }

    public static class Builder extends GraphBuilderBase<MediaFilter> {
        private final Supplier<BufferChannel> bufferChannelSupplier;
        private final Graph.Option option;

        public Builder() {
            this(new Graph.Option());
        }

        public Builder(Graph.Option option) {
            this.bufferChannelSupplier = new BufferedConveyorFilter$$ExternalSyntheticLambda1();
            this.option = option;
        }

        public Builder(MFGraphUnitFactory mFGraphUnitFactory) {
            this(mFGraphUnitFactory, new Graph.Option());
        }

        public Builder(MFGraphUnitFactory mFGraphUnitFactory, Graph.Option option) {
            Objects.requireNonNull(mFGraphUnitFactory);
            this.bufferChannelSupplier = new MFGraph$Builder$$ExternalSyntheticLambda0(mFGraphUnitFactory);
            this.option = option;
            if (option.isCacheable() && option.get(1) == null) {
                option.set(1, mFGraphUnitFactory.newDiskCache());
            }
        }

        @Override // com.samsung.android.sume.core.graph.GraphBuilder
        public GraphBuilder<MediaFilter> addNode(GraphNode<? extends MediaFilter> graphNode, GraphNode<? extends MediaFilter> graphNode2) {
            GraphEdge graphEdge = new GraphEdge(this.bufferChannelSupplier.get());
            graphNode.addOutputEdge(graphEdge);
            graphNode2.addInputEdge(graphEdge);
            this.graphNodes.add(graphNode);
            this.graphNodes.add(graphNode2);
            return this;
        }

        @Override // com.samsung.android.sume.core.graph.GraphBuilder
        public GraphBuilder<MediaFilter> addNode(GraphNode<? extends MediaFilter> graphNode, GraphNode<? extends MediaFilter> graphNode2, Evaluator evaluator) {
            return addNode(graphNode, graphNode2, evaluator, new BufferChannelDescriptor());
        }

        @Override // com.samsung.android.sume.core.graph.GraphBuilder
        public GraphBuilder<MediaFilter> addNode(GraphNode<? extends MediaFilter> graphNode, GraphNode<? extends MediaFilter> graphNode2, BufferChannelDescriptor bufferChannelDescriptor) {
            return addNode(graphNode, graphNode2, null, bufferChannelDescriptor);
        }

        @Override // com.samsung.android.sume.core.graph.GraphBuilder
        public GraphBuilder<MediaFilter> addNode(GraphNode<? extends MediaFilter> graphNode, GraphNode<? extends MediaFilter> graphNode2, Evaluator evaluator, BufferChannelDescriptor bufferChannelDescriptor) {
            BufferChannel bufferChannelOf;
            int type = bufferChannelDescriptor.getType();
            if (type == 0) {
                bufferChannelOf = this.bufferChannelSupplier.get();
            } else if (type == 1) {
                BufferSupplyChannel bufferSupplyChannel = new BufferSupplyChannel(this.bufferChannelSupplier.get());
                MediaFilter mediaFilter = graphNode2.get();
                Def.require(mediaFilter instanceof BufferSupplier);
                bufferSupplyChannel.configure(((BufferSupplier) mediaFilter).getBufferSupplier());
                bufferChannelOf = bufferSupplyChannel;
            } else if (type == 2 || type == 3 || type == 4) {
                bufferChannelOf = SurfaceChannel.of(bufferChannelDescriptor.getType(), this.bufferChannelSupplier.get());
            } else {
                throw new IllegalArgumentException("unknown BufferChannel.Type given: " + bufferChannelDescriptor);
            }
            if (bufferChannelDescriptor.getCapacity() != 0) {
                bufferChannelOf.setCapacity(bufferChannelDescriptor.getCapacity());
            }
            GraphEdge graphEdge = new GraphEdge(bufferChannelOf, evaluator);
            graphEdge.setNode(graphNode.getNodeId(), graphNode2.getNodeId());
            graphNode.addOutputEdge(graphEdge);
            graphNode2.addInputEdge(graphEdge);
            this.graphNodes.add(graphNode);
            this.graphNodes.add(graphNode2);
            return this;
        }

        @Override // com.samsung.android.sume.core.graph.GraphBuilder
        public Graph<MediaFilter> build() {
            BufferChannel bufferChannel = this.bufferChannelSupplier.get();
            BufferChannel bufferChannel2 = this.bufferChannelSupplier.get();
            this.graphNodes = (List) this.graphNodes.stream().distinct().collect(Collectors.toList());
            return new MFGraph(this.graphNodes, bufferChannel, bufferChannel2, this.option);
        }
    }
}

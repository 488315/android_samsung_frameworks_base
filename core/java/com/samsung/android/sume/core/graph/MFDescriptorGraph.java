package com.samsung.android.sume.core.graph;

import android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.channel.BufferChannelDescriptor;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.nn.NNDescriptor;
import com.samsung.android.sume.core.evaluate.Equal;
import com.samsung.android.sume.core.evaluate.Evaluator;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.graph.Graph;
import com.samsung.android.sume.core.graph.MFDescriptorGraph;
import com.samsung.android.sume.core.graph.MFGraph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/* loaded from: classes6.dex */
public class MFDescriptorGraph implements Parcelable {
    private final DescriptorNode[] nodes;
    private final Graph.Option option;
    private static final String TAG = Def.tagOf((Class<?>) MFDescriptorGraph.class);
    public static final Parcelable.Creator<MFDescriptorGraph> CREATOR = new Parcelable.Creator<MFDescriptorGraph>() { // from class: com.samsung.android.sume.core.graph.MFDescriptorGraph.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MFDescriptorGraph createFromParcel(Parcel in) {
            return new MFDescriptorGraph(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MFDescriptorGraph[] newArray(int size) {
            return new MFDescriptorGraph[size];
        }
    };

    private MFDescriptorGraph(DescriptorNode[] nodes, Graph.Option option) {
        this.nodes = nodes;
        this.option = option;
    }

    protected MFDescriptorGraph(Parcel in) {
        this.nodes = (DescriptorNode[]) Arrays.stream(in.readParcelableArray(DescriptorNode.class.getClassLoader())).map(new Function() { // from class: com.samsung.android.sume.core.graph.MFDescriptorGraph$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MFDescriptorGraph.lambda$new$0((Parcelable) obj);
            }
        }).toArray(new IntFunction() { // from class: com.samsung.android.sume.core.graph.MFDescriptorGraph$$ExternalSyntheticLambda4
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return MFDescriptorGraph.lambda$new$1(i);
            }
        });
        this.option = (Graph.Option) in.readParcelable(Graph.Option.class.getClassLoader());
        for (DescriptorNode node : this.nodes) {
            if (node.descriptor instanceof NNDescriptor) {
                NNDescriptor nnDescriptor = (NNDescriptor) node.descriptor;
                Log.d(TAG, "node: isKeepFilterDatatype " + nnDescriptor.isKeepFilterDatatype());
                Log.d(TAG, "node: isMultipleInputOutput " + nnDescriptor.isBatchIO());
            }
        }
    }

    static /* synthetic */ DescriptorNode lambda$new$0(Parcelable it) {
        return (DescriptorNode) it;
    }

    static /* synthetic */ DescriptorNode[] lambda$new$1(int x$0) {
        return new DescriptorNode[x$0];
    }

    public Graph.Option getOption() {
        return this.option;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelableArray(this.nodes, flags);
        dest.writeParcelable(this.option, flags);
    }

    public Graph<MediaFilter> toMediaFilterGraph(final MFGraphUnitFactory unitFactory) {
        Log.d(TAG, "toMediaFilterGraph: option=" + this.option);
        final MFGraph.Builder builder = new MFGraph.Builder(unitFactory, this.option);
        try {
            final List<Pair<DescriptorNode, GraphNode<MediaFilter>>> nodeList = (List) Arrays.stream(this.nodes).sorted(Comparator.comparing(new Function() { // from class: com.samsung.android.sume.core.graph.MFDescriptorGraph$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(((MFDescriptorGraph.DescriptorNode) obj).getId());
                }
            })).map(new Function() { // from class: com.samsung.android.sume.core.graph.MFDescriptorGraph$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return MFDescriptorGraph.lambda$toMediaFilterGraph$2(MFGraphUnitFactory.this, (MFDescriptorGraph.DescriptorNode) obj);
                }
            }).collect(Collectors.toList());
            nodeList.forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.MFDescriptorGraph$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MFDescriptorGraph.lambda$toMediaFilterGraph$4(MFGraph.Builder.this, nodeList, (Pair) obj);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    static /* synthetic */ Pair lambda$toMediaFilterGraph$2(MFGraphUnitFactory unitFactory, DescriptorNode it) {
        return new Pair(it, unitFactory.newNode(it.descriptor));
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void lambda$toMediaFilterGraph$4(final MFGraph.Builder builder, final List nodeList, Pair it) {
        List<Integer> children = ((DescriptorNode) it.first).children;
        final Map<Integer, Evaluator> evaluatorMap = ((DescriptorNode) it.first).evaluatorMap;
        final Map<Integer, BufferChannelDescriptor> channelMapMap = ((DescriptorNode) it.first).channelMap;
        final GraphNode<MediaFilter> from = (GraphNode) it.second;
        if (children.isEmpty()) {
            builder.addNode(from);
        } else {
            children.forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.MFDescriptorGraph$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MFDescriptorGraph.lambda$toMediaFilterGraph$3(nodeList, evaluatorMap, channelMapMap, builder, from, (Integer) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$toMediaFilterGraph$3(List nodeList, Map evaluatorMap, Map channelMapMap, MFGraph.Builder builder, GraphNode from, Integer idx) {
        GraphNode<MediaFilter> to = (GraphNode) ((Pair) nodeList.get(idx.intValue())).second;
        Evaluator evaluator = null;
        BufferChannelDescriptor channelDesc = null;
        if (evaluatorMap.containsKey(idx)) {
            evaluator = (Evaluator) evaluatorMap.get(idx);
        }
        if (channelMapMap.containsKey(idx)) {
            channelDesc = (BufferChannelDescriptor) channelMapMap.get(idx);
        }
        if (channelDesc == null) {
            channelDesc = new BufferChannelDescriptor();
        }
        builder.addNode(from, to, evaluator, channelDesc);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class Builder {
        AtomicInteger id = new AtomicInteger(0);
        Map<MFDescriptor, DescriptorNode> nodeMap = new HashMap();

        private DescriptorNode getNode(MFDescriptor descriptor) {
            if (!this.nodeMap.containsKey(descriptor)) {
                this.nodeMap.put(descriptor, new DescriptorNode(this.id.getAndIncrement(), descriptor));
            }
            return this.nodeMap.get(descriptor);
        }

        public Builder addNode(MFDescriptor node) {
            getNode(node);
            return this;
        }

        public Builder addNode(MFDescriptor from, MFDescriptor to) {
            return addNode(getNode(from), getNode(to), (Evaluator) null, new BufferChannelDescriptor(0));
        }

        public Builder addNode(MFDescriptor from, MFDescriptor to, Evaluator evaluator) {
            return addNode(getNode(from), getNode(to), evaluator, new BufferChannelDescriptor(0));
        }

        public Builder addNode(MFDescriptor from, MFDescriptor to, int channelType) {
            return addNode(getNode(from), getNode(to), (Evaluator) null, new BufferChannelDescriptor(channelType, 0));
        }

        public Builder addNode(MFDescriptor from, MFDescriptor to, Evaluator evaluator, int channelType) {
            return addNode(getNode(from), getNode(to), evaluator, new BufferChannelDescriptor(channelType, 0));
        }

        public Builder addNode(MFDescriptor from, MFDescriptor to, BufferChannelDescriptor channelDescriptor) {
            return addNode(getNode(from), getNode(to), (Evaluator) null, channelDescriptor);
        }

        public Builder addNode(MFDescriptor from, MFDescriptor to, Evaluator evaluator, BufferChannelDescriptor channelDescriptor) {
            return addNode(getNode(from), getNode(to), evaluator, channelDescriptor);
        }

        private Builder addNode(DescriptorNode from, DescriptorNode to, Evaluator evaluator, BufferChannelDescriptor channelDescriptor) {
            from.addNode(to, evaluator, channelDescriptor);
            return this;
        }

        public MFDescriptorGraph build() {
            return new MFDescriptorGraph((DescriptorNode[]) this.nodeMap.values().toArray(new DescriptorNode[0]), new Graph.Option());
        }

        public MFDescriptorGraph build(Graph.Option option) {
            return new MFDescriptorGraph((DescriptorNode[]) this.nodeMap.values().toArray(new DescriptorNode[0]), option);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class DescriptorNode implements Parcelable {
        public static final Parcelable.Creator<DescriptorNode> CREATOR = new Parcelable.Creator<DescriptorNode>() { // from class: com.samsung.android.sume.core.graph.MFDescriptorGraph.DescriptorNode.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DescriptorNode createFromParcel(Parcel in) {
                return new DescriptorNode(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DescriptorNode[] newArray(int size) {
                return new DescriptorNode[size];
            }
        };
        Map<Integer, BufferChannelDescriptor> channelMap;
        List<Integer> children;
        MFDescriptor descriptor;
        Map<Integer, Evaluator> evaluatorMap;
        int id;

        protected DescriptorNode(int id, MFDescriptor descriptor) {
            this.children = new ArrayList();
            this.evaluatorMap = new HashMap();
            this.channelMap = new HashMap();
            this.id = id;
            this.descriptor = descriptor;
        }

        int getId() {
            return this.id;
        }

        void addNode(DescriptorNode childNode, Evaluator evaluator, BufferChannelDescriptor channelDescriptor) {
            this.children.add(Integer.valueOf(childNode.getId()));
            if (evaluator != null) {
                this.evaluatorMap.put(Integer.valueOf(childNode.getId()), evaluator);
            }
            this.channelMap.put(Integer.valueOf(childNode.getId()), channelDescriptor);
        }

        protected DescriptorNode(Parcel in) {
            this.children = new ArrayList();
            this.evaluatorMap = new HashMap();
            this.channelMap = new HashMap();
            this.id = in.readInt();
            this.descriptor = (MFDescriptor) in.readSerializable();
            this.children = (List) Arrays.stream(in.createIntArray()).boxed().collect(Collectors.toList());
            in.readMap(this.evaluatorMap, Equal.class.getClassLoader());
            in.readMap(this.channelMap, null);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeSerializable(this.descriptor);
            dest.writeIntArray(this.children.stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray());
            dest.writeMap(this.evaluatorMap);
            dest.writeMap(this.channelMap);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }
}

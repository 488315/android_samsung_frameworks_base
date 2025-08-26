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
import java.io.IOException;
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
        public MFDescriptorGraph createFromParcel(Parcel parcel) {
            return new MFDescriptorGraph(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MFDescriptorGraph[] newArray(int i) {
            return new MFDescriptorGraph[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private MFDescriptorGraph(DescriptorNode[] descriptorNodeArr, Graph.Option option) {
        this.nodes = descriptorNodeArr;
        this.option = option;
    }

    protected MFDescriptorGraph(Parcel parcel) {
        DescriptorNode[] descriptorNodeArr = (DescriptorNode[]) Arrays.stream(parcel.readParcelableArray(DescriptorNode.class.getClassLoader())).map(new Function() { // from class: com.samsung.android.sume.core.graph.MFDescriptorGraph$$ExternalSyntheticLambda3
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
        this.nodes = descriptorNodeArr;
        this.option = (Graph.Option) parcel.readParcelable(Graph.Option.class.getClassLoader());
        for (DescriptorNode descriptorNode : descriptorNodeArr) {
            if (descriptorNode.descriptor instanceof NNDescriptor) {
                NNDescriptor nNDescriptor = (NNDescriptor) descriptorNode.descriptor;
                String str = TAG;
                Log.d(str, "node: isKeepFilterDatatype " + nNDescriptor.isKeepFilterDatatype());
                Log.d(str, "node: isMultipleInputOutput " + nNDescriptor.isBatchIO());
            }
        }
    }

    static /* synthetic */ DescriptorNode lambda$new$0(Parcelable parcelable) {
        return (DescriptorNode) parcelable;
    }

    static /* synthetic */ DescriptorNode[] lambda$new$1(int i) {
        return new DescriptorNode[i];
    }

    public Graph.Option getOption() {
        return this.option;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelableArray(this.nodes, i);
        parcel.writeParcelable(this.option, i);
    }

    public Graph<MediaFilter> toMediaFilterGraph(final MFGraphUnitFactory mFGraphUnitFactory) {
        Log.d(TAG, "toMediaFilterGraph: option=" + this.option);
        final MFGraph.Builder builder = new MFGraph.Builder(mFGraphUnitFactory, this.option);
        try {
            final List list = (List) Arrays.stream(this.nodes).sorted(Comparator.comparing(new Function() { // from class: com.samsung.android.sume.core.graph.MFDescriptorGraph$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(((MFDescriptorGraph.DescriptorNode) obj).getId());
                }
            })).map(new Function() { // from class: com.samsung.android.sume.core.graph.MFDescriptorGraph$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return MFDescriptorGraph.lambda$toMediaFilterGraph$2(mFGraphUnitFactory, (MFDescriptorGraph.DescriptorNode) obj);
                }
            }).collect(Collectors.toList());
            list.forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.MFDescriptorGraph$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MFDescriptorGraph.lambda$toMediaFilterGraph$4(builder, list, (Pair) obj);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    static /* synthetic */ Pair lambda$toMediaFilterGraph$2(MFGraphUnitFactory mFGraphUnitFactory, DescriptorNode descriptorNode) {
        return new Pair(descriptorNode, mFGraphUnitFactory.newNode(descriptorNode.descriptor));
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void lambda$toMediaFilterGraph$4(final MFGraph.Builder builder, final List list, Pair pair) {
        List<Integer> list2 = ((DescriptorNode) pair.first).children;
        final Map<Integer, Evaluator> map = ((DescriptorNode) pair.first).evaluatorMap;
        final Map<Integer, BufferChannelDescriptor> map2 = ((DescriptorNode) pair.first).channelMap;
        final GraphNode graphNode = (GraphNode) pair.second;
        if (list2.isEmpty()) {
            builder.addNode(graphNode);
        } else {
            list2.forEach(new Consumer() { // from class: com.samsung.android.sume.core.graph.MFDescriptorGraph$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MFDescriptorGraph.lambda$toMediaFilterGraph$3(list, map, map2, builder, graphNode, (Integer) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$toMediaFilterGraph$3(List list, Map map, Map map2, MFGraph.Builder builder, GraphNode graphNode, Integer num) {
        GraphNode<? extends MediaFilter> graphNode2 = (GraphNode) ((Pair) list.get(num.intValue())).second;
        Evaluator evaluator = map.containsKey(num) ? (Evaluator) map.get(num) : null;
        BufferChannelDescriptor bufferChannelDescriptor = map2.containsKey(num) ? (BufferChannelDescriptor) map2.get(num) : null;
        if (bufferChannelDescriptor == null) {
            bufferChannelDescriptor = new BufferChannelDescriptor();
        }
        builder.addNode(graphNode, graphNode2, evaluator, bufferChannelDescriptor);
    }

    public static class Builder {
        AtomicInteger id = new AtomicInteger(0);
        Map<MFDescriptor, DescriptorNode> nodeMap = new HashMap();

        private DescriptorNode getNode(MFDescriptor mFDescriptor) {
            if (!this.nodeMap.containsKey(mFDescriptor)) {
                this.nodeMap.put(mFDescriptor, new DescriptorNode(this.id.getAndIncrement(), mFDescriptor));
            }
            return this.nodeMap.get(mFDescriptor);
        }

        public Builder addNode(MFDescriptor mFDescriptor) {
            getNode(mFDescriptor);
            return this;
        }

        public Builder addNode(MFDescriptor mFDescriptor, MFDescriptor mFDescriptor2) {
            return addNode(getNode(mFDescriptor), getNode(mFDescriptor2), (Evaluator) null, new BufferChannelDescriptor(0));
        }

        public Builder addNode(MFDescriptor mFDescriptor, MFDescriptor mFDescriptor2, Evaluator evaluator) {
            return addNode(getNode(mFDescriptor), getNode(mFDescriptor2), evaluator, new BufferChannelDescriptor(0));
        }

        public Builder addNode(MFDescriptor mFDescriptor, MFDescriptor mFDescriptor2, int i) {
            return addNode(getNode(mFDescriptor), getNode(mFDescriptor2), (Evaluator) null, new BufferChannelDescriptor(i, 0));
        }

        public Builder addNode(MFDescriptor mFDescriptor, MFDescriptor mFDescriptor2, Evaluator evaluator, int i) {
            return addNode(getNode(mFDescriptor), getNode(mFDescriptor2), evaluator, new BufferChannelDescriptor(i, 0));
        }

        public Builder addNode(MFDescriptor mFDescriptor, MFDescriptor mFDescriptor2, BufferChannelDescriptor bufferChannelDescriptor) {
            return addNode(getNode(mFDescriptor), getNode(mFDescriptor2), (Evaluator) null, bufferChannelDescriptor);
        }

        public Builder addNode(MFDescriptor mFDescriptor, MFDescriptor mFDescriptor2, Evaluator evaluator, BufferChannelDescriptor bufferChannelDescriptor) {
            return addNode(getNode(mFDescriptor), getNode(mFDescriptor2), evaluator, bufferChannelDescriptor);
        }

        private Builder addNode(DescriptorNode descriptorNode, DescriptorNode descriptorNode2, Evaluator evaluator, BufferChannelDescriptor bufferChannelDescriptor) {
            descriptorNode.addNode(descriptorNode2, evaluator, bufferChannelDescriptor);
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
            public DescriptorNode createFromParcel(Parcel parcel) {
                return new DescriptorNode(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DescriptorNode[] newArray(int i) {
                return new DescriptorNode[i];
            }
        };
        Map<Integer, BufferChannelDescriptor> channelMap;
        List<Integer> children;
        MFDescriptor descriptor;
        Map<Integer, Evaluator> evaluatorMap;
        int id;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        protected DescriptorNode(int i, MFDescriptor mFDescriptor) {
            this.children = new ArrayList();
            this.evaluatorMap = new HashMap();
            this.channelMap = new HashMap();
            this.id = i;
            this.descriptor = mFDescriptor;
        }

        int getId() {
            return this.id;
        }

        void addNode(DescriptorNode descriptorNode, Evaluator evaluator, BufferChannelDescriptor bufferChannelDescriptor) {
            this.children.add(Integer.valueOf(descriptorNode.getId()));
            if (evaluator != null) {
                this.evaluatorMap.put(Integer.valueOf(descriptorNode.getId()), evaluator);
            }
            this.channelMap.put(Integer.valueOf(descriptorNode.getId()), bufferChannelDescriptor);
        }

        protected DescriptorNode(Parcel parcel) throws ClassNotFoundException, IOException {
            this.children = new ArrayList();
            this.evaluatorMap = new HashMap();
            this.channelMap = new HashMap();
            this.id = parcel.readInt();
            this.descriptor = (MFDescriptor) parcel.readSerializable();
            this.children = (List) Arrays.stream(parcel.createIntArray()).boxed().collect(Collectors.toList());
            parcel.readMap(this.evaluatorMap, Equal.class.getClassLoader());
            parcel.readMap(this.channelMap, null);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) throws IOException {
            parcel.writeInt(this.id);
            parcel.writeSerializable(this.descriptor);
            parcel.writeIntArray(this.children.stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray());
            parcel.writeMap(this.evaluatorMap);
            parcel.writeMap(this.channelMap);
        }
    }
}

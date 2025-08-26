package com.samsung.android.sume.core.filter.collection;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda3;
import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.channel.ReceiveChannelRouter$$ExternalSyntheticLambda3;
import com.samsung.android.sume.core.descriptor.ParallelDescriptor;
import com.samsung.android.sume.core.filter.AsyncFilter;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.filter.MediaFilterGroup;
import com.samsung.android.sume.core.functional.BufferComposer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class ParallelSharedFilter extends ParallelFilter {
    List<BufferChannel> inChannels;
    List<BufferChannel> outChannels;

    public ParallelSharedFilter(ParallelDescriptor parallelDescriptor, Supplier<BufferChannel> supplier) {
        super(parallelDescriptor, supplier);
        this.inChannels = new ArrayList();
        this.outChannels = new ArrayList();
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilterGroupBase, com.samsung.android.sume.core.filter.MediaFilterGroup
    public MediaFilterGroup addFilter(List<MediaFilter> list) {
        list.forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.collection.ParallelSharedFilter$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m9571x3bc28532((MediaFilter) obj);
            }
        });
        return super.addFilter(list);
    }

    /* renamed from: lambda$addFilter$0$com-samsung-android-sume-core-filter-collection-ParallelSharedFilter, reason: not valid java name */
    /* synthetic */ void m9571x3bc28532(MediaFilter mediaFilter) {
        BufferChannel bufferChannel = this.channelSupplier.get();
        BufferChannel bufferChannel2 = this.channelSupplier.get();
        this.inChannels.add(bufferChannel);
        this.outChannels.add(bufferChannel2);
        ((AsyncFilter) mediaFilter).addBufferChannels(bufferChannel, bufferChannel2);
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(final MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        this.inChannels.forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.collection.ParallelSharedFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((BufferChannel) obj).send(mediaBuffer);
            }
        });
        List<MediaBuffer> list = (List) ((Stream) this.outChannels.stream().parallel()).map(new ReceiveChannelRouter$$ExternalSyntheticLambda3()).collect(Collectors.toList());
        if (getDescriptor().getOption().getUseExternalBufferComposer()) {
            mutableMediaBuffer.put(((BufferComposer) list.stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.collection.ParallelSharedFilter$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((MediaBuffer) obj).containsExtra("composer");
                }
            }).findFirst().map(new Function() { // from class: com.samsung.android.sume.core.filter.collection.ParallelSharedFilter$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((MediaBuffer) obj).getExtra("composer");
                }
            }).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3())).compose(list, null));
            return mutableMediaBuffer;
        }
        mutableMediaBuffer.put(MediaBuffer.groupOf(list));
        return mutableMediaBuffer;
    }
}

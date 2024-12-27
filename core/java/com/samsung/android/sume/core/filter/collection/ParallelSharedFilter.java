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

public class ParallelSharedFilter extends ParallelFilter {
    List<BufferChannel> inChannels;
    List<BufferChannel> outChannels;

    public ParallelSharedFilter(
            ParallelDescriptor descriptor, Supplier<BufferChannel> channelSupplier) {
        super(descriptor, channelSupplier);
        this.inChannels = new ArrayList();
        this.outChannels = new ArrayList();
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilterGroupBase,
              // com.samsung.android.sume.core.filter.MediaFilterGroup
    public MediaFilterGroup addFilter(List<MediaFilter> filters) {
        filters.forEach(
                new Consumer() { // from class:
                                 // com.samsung.android.sume.core.filter.collection.ParallelSharedFilter$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ParallelSharedFilter.this.m9157x3bc28532((MediaFilter) obj);
                    }
                });
        return super.addFilter(filters);
    }

    /* renamed from: lambda$addFilter$0$com-samsung-android-sume-core-filter-collection-ParallelSharedFilter, reason: not valid java name */
    /* synthetic */ void m9157x3bc28532(MediaFilter it) {
        BufferChannel inChannel = this.channelSupplier.get();
        BufferChannel outChannel = this.channelSupplier.get();
        this.inChannels.add(inChannel);
        this.outChannels.add(outChannel);
        ((AsyncFilter) it).addBufferChannels(inChannel, outChannel);
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(final MediaBuffer ibuf, MutableMediaBuffer obuf) {
        this.inChannels.forEach(
                new Consumer() { // from class:
                                 // com.samsung.android.sume.core.filter.collection.ParallelSharedFilter$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((BufferChannel) obj).send(MediaBuffer.this);
                    }
                });
        List<MediaBuffer> bufferList =
                (List)
                        ((Stream) this.outChannels.stream().parallel())
                                .map(new ReceiveChannelRouter$$ExternalSyntheticLambda3())
                                .collect(Collectors.toList());
        if (getDescriptor().getOption().getUseExternalBufferComposer()) {
            BufferComposer composer =
                    (BufferComposer)
                            bufferList.stream()
                                    .filter(
                                            new Predicate() { // from class:
                                                              // com.samsung.android.sume.core.filter.collection.ParallelSharedFilter$$ExternalSyntheticLambda1
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    boolean containsExtra;
                                                    containsExtra =
                                                            ((MediaBuffer) obj)
                                                                    .containsExtra("composer");
                                                    return containsExtra;
                                                }
                                            })
                                    .findFirst()
                                    .map(
                                            new Function() { // from class:
                                                             // com.samsung.android.sume.core.filter.collection.ParallelSharedFilter$$ExternalSyntheticLambda2
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    Object extra;
                                                    extra =
                                                            ((MediaBuffer) obj)
                                                                    .getExtra("composer");
                                                    return extra;
                                                }
                                            })
                                    .orElseThrow(
                                            new MutableMediaBuffer$$ExternalSyntheticLambda3());
            obuf.put(composer.compose(bufferList, null));
        } else {
            obuf.put(MediaBuffer.groupOf(bufferList));
        }
        return obuf;
    }
}

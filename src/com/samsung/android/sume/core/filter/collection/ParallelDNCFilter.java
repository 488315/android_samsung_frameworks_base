package com.samsung.android.sume.core.filter.collection;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MediaBufferGroup;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.channel.SurfaceChannelImpl$$ExternalSyntheticLambda5;
import com.samsung.android.sume.core.descriptor.ParallelDescriptor;
import com.samsung.android.sume.core.filter.AsyncFilter;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.filter.MediaFilterGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class ParallelDNCFilter extends ParallelFilter {
    private final BufferChannel inChannel;
    private final BufferChannel outChannel;

    public ParallelDNCFilter(ParallelDescriptor parallelDescriptor, Supplier<BufferChannel> supplier) {
        super(parallelDescriptor, supplier);
        this.inChannel = supplier.get();
        this.outChannel = supplier.get();
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilterGroupBase, com.samsung.android.sume.core.filter.MediaFilterGroup
    public MediaFilterGroup addFilter(List<MediaFilter> list) {
        list.forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.collection.ParallelDNCFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ParallelDNCFilter.this.m9557x2b822d1c((MediaFilter) obj);
            }
        });
        return super.addFilter(list);
    }

    /* renamed from: lambda$addFilter$0$com-samsung-android-sume-core-filter-collection-ParallelDNCFilter, reason: not valid java name */
    /* synthetic */ void m9557x2b822d1c(MediaFilter mediaFilter) {
        ((AsyncFilter) mediaFilter).addBufferChannels(this.inChannel, this.outChannel);
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        Def.require(mediaBuffer instanceof MediaBufferGroup);
        Stream<MediaBuffer> stream = mediaBuffer.stream();
        BufferChannel bufferChannel = this.inChannel;
        Objects.requireNonNull(bufferChannel);
        stream.forEach(new SurfaceChannelImpl$$ExternalSyntheticLambda5(bufferChannel));
        ArrayList arrayList = new ArrayList();
        while (arrayList.size() != mediaBuffer.size()) {
            arrayList.add(this.outChannel.receive());
        }
        mutableMediaBuffer.put(MediaBuffer.groupOf(arrayList));
        return mutableMediaBuffer;
    }
}

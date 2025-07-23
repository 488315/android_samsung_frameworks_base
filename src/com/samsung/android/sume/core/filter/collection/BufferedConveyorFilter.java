package com.samsung.android.sume.core.filter.collection;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.channel.BlockingBufferChannel;
import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.descriptor.SequentialDescriptor;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.filter.MediaFilterGroup;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/* loaded from: classes6.dex */
public class BufferedConveyorFilter extends SequentialFilter {
    private final AtomicBoolean done;
    private BufferChannel firstInChannel;
    private BufferChannel lastOutChannel;
    private final ExecutorService threadPool;

    public BufferedConveyorFilter(SequentialDescriptor sequentialDescriptor) {
        super(sequentialDescriptor);
        this.done = new AtomicBoolean(false);
        this.threadPool = Executors.newFixedThreadPool(sequentialDescriptor.getDescriptors().size());
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilterGroupBase, com.samsung.android.sume.core.filter.MediaFilterGroup
    public MediaFilterGroup addFilter(List<MediaFilter> list) {
        list.stream().forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.collection.BufferedConveyorFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BufferedConveyorFilter.this.m9556xdcaa7b8f((MediaFilter) obj);
            }
        });
        return super.addFilter(list);
    }

    /* renamed from: lambda$addFilter$1$com-samsung-android-sume-core-filter-collection-BufferedConveyorFilter, reason: not valid java name */
    /* synthetic */ void m9556xdcaa7b8f(final MediaFilter mediaFilter) {
        final BufferChannel bufferChannel = (BufferChannel) Optional.ofNullable(this.lastOutChannel).orElseGet(new BufferedConveyorFilter$$ExternalSyntheticLambda1());
        if (this.firstInChannel == null) {
            this.firstInChannel = bufferChannel;
        }
        final BlockingBufferChannel blockingBufferChannel = new BlockingBufferChannel();
        this.threadPool.submit(new Runnable() { // from class: com.samsung.android.sume.core.filter.collection.BufferedConveyorFilter$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                BufferedConveyorFilter.this.m9555x5a5fc6b0(bufferChannel, mediaFilter, blockingBufferChannel);
            }
        });
        this.lastOutChannel = blockingBufferChannel;
    }

    /* renamed from: lambda$addFilter$0$com-samsung-android-sume-core-filter-collection-BufferedConveyorFilter, reason: not valid java name */
    /* synthetic */ void m9555x5a5fc6b0(BufferChannel bufferChannel, MediaFilter mediaFilter, BufferChannel bufferChannel2) {
        while (!this.done.get()) {
            bufferChannel2.send(mediaFilter.run(bufferChannel.receive()));
        }
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        this.firstInChannel.send(mediaBuffer);
        mutableMediaBuffer.put(this.lastOutChannel.receive());
        return mutableMediaBuffer;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilterGroupBase, com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
        this.done.set(true);
        super.release();
    }
}

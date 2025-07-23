package com.samsung.android.sume.core.graph;

import com.samsung.android.sume.core.cache.DiskCache;
import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.filter.AsyncFilter;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.filter.factory.MediaFilterCreator;
import com.samsung.android.sume.core.filter.factory.MediaFilterFactory;
import java.util.function.Consumer;

/* loaded from: classes6.dex */
public abstract class MFGraphUnitFactory {
    private final MediaFilterFactory mediaFilterFactory;

    public abstract BufferChannel newBufferChannel();

    public abstract DiskCache newDiskCache();

    public abstract GraphNode<MediaFilter> newNode(MediaFilter mediaFilter);

    protected abstract MediaFilter parallelizeFilter(MediaFilterFactory mediaFilterFactory, MFDescriptor mFDescriptor, MediaFilter mediaFilter);

    protected MFGraphUnitFactory(Consumer<MediaFilterFactory.Builder> consumer) {
        MediaFilterFactory.Builder builder = new MediaFilterFactory.Builder();
        consumer.accept(builder);
        builder.addCreator(AsyncFilter.class, new MediaFilterCreator() { // from class: com.samsung.android.sume.core.graph.MFGraphUnitFactory$$ExternalSyntheticLambda0
            @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreator
            public final MediaFilter newFilter(MediaFilterFactory mediaFilterFactory, MFDescriptor mFDescriptor, MediaFilter mediaFilter) {
                return MFGraphUnitFactory.this.parallelizeFilter(mediaFilterFactory, mFDescriptor, mediaFilter);
            }
        });
        builder.addBufferChannelSupplier(new MFGraph$Builder$$ExternalSyntheticLambda0(this));
        this.mediaFilterFactory = builder.build();
    }

    public GraphNode<MediaFilter> newNode(MFDescriptor mFDescriptor, MediaFilter mediaFilter) {
        return newNode(this.mediaFilterFactory.newFilter(mFDescriptor, mediaFilter));
    }

    public GraphNode<MediaFilter> newNode(MFDescriptor mFDescriptor) {
        return newNode(this.mediaFilterFactory.newFilter(mFDescriptor));
    }

    public MediaFilter newFilter(MFDescriptor mFDescriptor, MediaFilter mediaFilter) {
        return this.mediaFilterFactory.newFilter(mFDescriptor, mediaFilter);
    }

    public MediaFilter newFilter(MFDescriptor mFDescriptor) {
        return this.mediaFilterFactory.newFilter(mFDescriptor);
    }

    protected static String getFilterId(MediaFilter mediaFilter) {
        return mediaFilter.getId();
    }

    public void clear() {
        this.mediaFilterFactory.clear();
    }
}

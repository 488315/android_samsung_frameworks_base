package com.samsung.android.sume.core.graph;

import com.samsung.android.sume.core.cache.DiskCache;
import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.filter.AsyncFilter;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.filter.factory.MediaFilterCreator;
import com.samsung.android.sume.core.filter.factory.MediaFilterFactory;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public abstract class MFGraphUnitFactory {
  private final MediaFilterFactory mediaFilterFactory;

  public abstract BufferChannel newBufferChannel();

  public abstract DiskCache newDiskCache();

  public abstract GraphNode<MediaFilter> newNode(MediaFilter mediaFilter);

  protected abstract MediaFilter parallelizeFilter(
      MediaFilterFactory mediaFilterFactory, MFDescriptor mFDescriptor, MediaFilter mediaFilter);

  protected MFGraphUnitFactory(Consumer<MediaFilterFactory.Builder> builderConstitutor) {
    MediaFilterFactory.Builder builder = new MediaFilterFactory.Builder();
    builderConstitutor.accept(builder);
    builder.addCreator(
        AsyncFilter.class,
        new MediaFilterCreator() { // from class:
                                   // com.samsung.android.sume.core.graph.MFGraphUnitFactory$$ExternalSyntheticLambda0
          @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreator
          public final MediaFilter newFilter(
              MediaFilterFactory mediaFilterFactory,
              MFDescriptor mFDescriptor,
              MediaFilter mediaFilter) {
            return MFGraphUnitFactory.this.parallelizeFilter(
                mediaFilterFactory, mFDescriptor, mediaFilter);
          }
        });
    builder.addBufferChannelSupplier(new MFGraph$Builder$$ExternalSyntheticLambda0(this));
    this.mediaFilterFactory = builder.build();
  }

  public GraphNode<MediaFilter> newNode(MFDescriptor descriptor, MediaFilter successor) {
    return newNode(this.mediaFilterFactory.newFilter(descriptor, successor));
  }

  public GraphNode<MediaFilter> newNode(MFDescriptor descriptor) {
    return newNode(this.mediaFilterFactory.newFilter(descriptor));
  }

  public MediaFilter newFilter(MFDescriptor descriptor, MediaFilter successor) {
    return this.mediaFilterFactory.newFilter(descriptor, successor);
  }

  public MediaFilter newFilter(MFDescriptor descriptor) {
    return this.mediaFilterFactory.newFilter(descriptor);
  }

  protected static String getFilterId(MediaFilter filter) {
    return filter.getId();
  }

  public void clear() {
    this.mediaFilterFactory.clear();
  }
}

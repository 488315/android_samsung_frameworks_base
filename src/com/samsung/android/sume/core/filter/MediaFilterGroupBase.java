package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.channel.BufferChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public abstract class MediaFilterGroupBase implements MediaFilterGroup {
    protected Supplier<BufferChannel> channelSupplier;
    protected List<MediaFilter> filters = new ArrayList();

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public Stream<MediaFilter> stream() {
        return this.filters.stream();
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilterGroup
    public MediaFilterGroup addFilter(List<MediaFilter> list) {
        this.filters.addAll(list);
        return this;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilterGroup
    public boolean removeFilter(MediaFilter... mediaFilterArr) {
        return this.filters.removeAll(Arrays.asList(mediaFilterArr));
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilterGroup
    public boolean replaceFilter(MediaFilter mediaFilter, MediaFilter mediaFilter2) {
        int indexOf = this.filters.indexOf(mediaFilter);
        if (indexOf < 0) {
            return false;
        }
        this.filters.set(indexOf, mediaFilter2);
        return true;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {
        this.filters.forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.MediaFilterGroupBase$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((MediaFilter) obj).prepare();
            }
        });
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
        this.filters.forEach(new MediaFilterGroupBase$$ExternalSyntheticLambda0());
        this.filters.clear();
    }
}

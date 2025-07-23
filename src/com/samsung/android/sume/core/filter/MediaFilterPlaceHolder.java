package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.functional.PlaceHolder;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class MediaFilterPlaceHolder implements MediaFilter, PlaceHolder<MediaFilter> {
    private static final String TAG = Def.tagOf((Class<?>) MediaFilterPlaceHolder.class);
    private final MFDescriptor descriptor;
    private MediaFilter mediaFilter;
    private Supplier<MediaFilter> mediaFilterProvider;
    private MediaFilterRetriever mediaFilterRetriever;
    private final List<Consumer<MediaFilter>> mediaFilterUpdaterList = new LinkedList();
    private WeakReference<MediaFilter> parent;

    public MediaFilterPlaceHolder(MFDescriptor mFDescriptor, Supplier<MediaFilter> supplier) {
        this.descriptor = mFDescriptor;
        this.mediaFilterProvider = supplier;
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        throw new UnsupportedOperationException("MediaFilterPlaceHolder not support this");
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public MFDescriptor getDescriptor() {
        return this.descriptor;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public Stream<MediaFilter> stream() {
        throw new UnsupportedOperationException("MediaFilterPlaceHolder not support this");
    }

    public void setMediaFilterUpdater(Consumer<MediaFilter> consumer) {
        this.mediaFilterUpdaterList.add(consumer);
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public void accept(MediaFilterRetriever mediaFilterRetriever, MediaFilter mediaFilter) {
        this.mediaFilterRetriever = mediaFilterRetriever;
        this.parent = new WeakReference<>(mediaFilter);
        mediaFilterRetriever.retrieve(this, mediaFilter);
    }

    @Override // com.samsung.android.sume.core.functional.PlaceHolder
    public void put(MediaFilter mediaFilter) {
        this.mediaFilter = mediaFilter;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.functional.PlaceHolder
    public MediaFilter reset() {
        if (this.mediaFilter == null) {
            Def.require(this.mediaFilterProvider != null, "duplicated replace call", new Object[0]);
            get();
        }
        this.mediaFilterUpdaterList.forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.MediaFilterPlaceHolder$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MediaFilterPlaceHolder.this.m9539x59f5e82e((Consumer) obj);
            }
        });
        MediaFilterRetriever mediaFilterRetriever = this.mediaFilterRetriever;
        if (mediaFilterRetriever != null) {
            this.mediaFilter.accept(mediaFilterRetriever, this.parent.get());
            this.mediaFilterRetriever = null;
            this.parent = null;
        }
        return this.mediaFilter;
    }

    /* renamed from: lambda$reset$0$com-samsung-android-sume-core-filter-MediaFilterPlaceHolder, reason: not valid java name */
    /* synthetic */ void m9539x59f5e82e(Consumer consumer) {
        consumer.accept(this.mediaFilter);
    }

    public MediaFilter get() {
        if (this.mediaFilter == null) {
            synchronized (this) {
                Supplier<MediaFilter> supplier = this.mediaFilterProvider;
                if (supplier != null) {
                    this.mediaFilter = supplier.get();
                }
            }
        }
        return this.mediaFilter;
    }

    @Override // com.samsung.android.sume.core.functional.PlaceHolder
    public boolean isEmpty() {
        return this.mediaFilter == null;
    }

    @Override // com.samsung.android.sume.core.functional.PlaceHolder
    public boolean isNotEmpty() {
        return this.mediaFilter != null;
    }
}

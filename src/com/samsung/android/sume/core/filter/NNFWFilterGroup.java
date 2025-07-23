package com.samsung.android.sume.core.filter;

import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.descriptor.nn.NNFWDescriptor;
import com.samsung.android.sume.core.functional.ModelSelector;
import com.samsung.android.sume.core.types.Status;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class NNFWFilterGroup extends NNFWFilter implements MediaFilterGroup {
    private static final String TAG = Def.tagOf((Class<?>) NNFWFilterGroup.class);
    private List<MediaFilter> filters;
    private MediaFilter mediaFilter;
    private ModelSelector modelSelector;

    public NNFWFilterGroup(NNFWDescriptor nNFWDescriptor, List<MediaFilter> list) {
        super(nNFWDescriptor);
        this.modelSelector = nNFWDescriptor.getNNDescriptor().getModelSelector();
        nNFWDescriptor.getNNDescriptor().setModelSelector(new ModelSelector() { // from class: com.samsung.android.sume.core.filter.NNFWFilterGroup$$ExternalSyntheticLambda3
            @Override // com.samsung.android.sume.core.functional.ModelSelector
            public final ModelSelector.Item select(MediaBuffer mediaBuffer) {
                return NNFWFilterGroup.this.m9548xd3f4c5ae(mediaBuffer);
            }
        });
        this.filters = list;
        Def.require(this.modelSelector != null, "no model selector is given", new Object[0]);
    }

    public NNFWFilterGroup(NNFWDescriptor nNFWDescriptor) {
        super(nNFWDescriptor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: loadModel, reason: merged with bridge method [inline-methods] */
    public ModelSelector.Item m9548xd3f4c5ae(MediaBuffer mediaBuffer) {
        final ModelSelector.Item select = this.modelSelector.select(mediaBuffer);
        Log.d(TAG, "load model: " + select.name);
        this.mediaFilter = this.filters.stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.NNFWFilterGroup$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean equals;
                equals = ((NNFWDescriptor) ((MediaFilter) obj).getDescriptor()).getNNFileDescriptor().getName().equals(ModelSelector.Item.this.name);
                return equals;
            }
        }).findFirst().orElseThrow(new Supplier() { // from class: com.samsung.android.sume.core.filter.NNFWFilterGroup$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return NNFWFilterGroup.lambda$loadModel$2(ModelSelector.Item.this);
            }
        });
        return select;
    }

    static /* synthetic */ IllegalStateException lambda$loadModel$2(ModelSelector.Item item) {
        return new IllegalStateException("no matched model with " + item);
    }

    @Override // com.samsung.android.sume.core.filter.NNFWFilter, com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {
        this.filters.forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.NNFWFilterGroup$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NNFWFilterGroup.this.m9549x92910fc4((MediaFilter) obj);
            }
        });
    }

    /* renamed from: lambda$prepare$3$com-samsung-android-sume-core-filter-NNFWFilterGroup, reason: not valid java name */
    /* synthetic */ void m9549x92910fc4(MediaFilter mediaFilter) {
        MediaFilter enclosedFilter = mediaFilter instanceof DecorateFilter ? ((DecorateFilter) mediaFilter).getEnclosedFilter() : mediaFilter;
        if (enclosedFilter instanceof NNFWFilter) {
            ((NNFWFilter) enclosedFilter).setExecuteDelegator(this.executeDelegator);
        }
        mediaFilter.prepare();
    }

    @Override // com.samsung.android.sume.core.filter.NNFWFilter, com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        if (this.mediaFilter == null) {
            m9548xd3f4c5ae(mediaBuffer);
        }
        MutableMediaBuffer run = this.mediaFilter.run(mediaBuffer, mutableMediaBuffer);
        if (this.descriptor.isInstant()) {
            this.mediaFilter = null;
        }
        return run;
    }

    @Override // com.samsung.android.sume.core.filter.NNFWFilter
    public Status runAdapter(MediaBuffer mediaBuffer, MediaBuffer mediaBuffer2) {
        throw new UnsupportedOperationException("do not use this");
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
        this.filters.forEach(new MediaFilterGroupBase$$ExternalSyntheticLambda0());
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

    @Override // com.samsung.android.sume.core.filter.NNFWFilter, com.samsung.android.sume.core.filter.MediaFilter
    public Stream<MediaFilter> stream() {
        return this.filters.stream();
    }
}

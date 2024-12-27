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

    public NNFWFilterGroup(NNFWDescriptor descriptor, List<MediaFilter> filters) {
        super(descriptor);
        this.modelSelector = descriptor.getNNDescriptor().getModelSelector();
        descriptor.getNNDescriptor().setModelSelector(new ModelSelector() { // from class: com.samsung.android.sume.core.filter.NNFWFilterGroup$$ExternalSyntheticLambda3
            @Override // com.samsung.android.sume.core.functional.ModelSelector
            public final ModelSelector.Item select(MediaBuffer mediaBuffer) {
                return NNFWFilterGroup.this.m9147xd3f4c5ae(mediaBuffer);
            }
        });
        this.filters = filters;
        Def.require(this.modelSelector != null, "no model selector is given", new Object[0]);
    }

    public NNFWFilterGroup(NNFWDescriptor descriptor) {
        super(descriptor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: loadModel, reason: merged with bridge method [inline-methods] */
    public ModelSelector.Item m9147xd3f4c5ae(MediaBuffer mediaBuffer) {
        final ModelSelector.Item found = this.modelSelector.select(mediaBuffer);
        Log.d(TAG, "load model: " + found.name);
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
        return found;
    }

    static /* synthetic */ IllegalStateException lambda$loadModel$2(ModelSelector.Item found) {
        return new IllegalStateException("no matched model with " + found);
    }

    @Override // com.samsung.android.sume.core.filter.NNFWFilter, com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {
        this.filters.forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.NNFWFilterGroup$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NNFWFilterGroup.this.m9148x92910fc4((MediaFilter) obj);
            }
        });
    }

    /* renamed from: lambda$prepare$3$com-samsung-android-sume-core-filter-NNFWFilterGroup, reason: not valid java name */
    /* synthetic */ void m9148x92910fc4(MediaFilter it) {
        MediaFilter filter = it;
        if (it instanceof DecorateFilter) {
            filter = ((DecorateFilter) it).getEnclosedFilter();
        }
        if (filter instanceof NNFWFilter) {
            ((NNFWFilter) filter).setExecuteDelegator(this.executeDelegator);
        }
        it.prepare();
    }

    @Override // com.samsung.android.sume.core.filter.NNFWFilter, com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) {
        if (this.mediaFilter == null) {
            m9147xd3f4c5ae(ibuf);
        }
        MutableMediaBuffer obuf2 = this.mediaFilter.run(ibuf, obuf);
        if (this.descriptor.isInstant()) {
            this.mediaFilter = null;
        }
        return obuf2;
    }

    @Override // com.samsung.android.sume.core.filter.NNFWFilter
    public Status runAdapter(MediaBuffer ibuf, MediaBuffer obuf) {
        throw new UnsupportedOperationException("do not use this");
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
        this.filters.forEach(new MediaFilterGroupBase$$ExternalSyntheticLambda0());
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilterGroup
    public MediaFilterGroup addFilter(List<MediaFilter> filters) {
        this.filters.addAll(filters);
        return this;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilterGroup
    public boolean removeFilter(MediaFilter... filters) {
        return this.filters.removeAll(Arrays.asList(filters));
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilterGroup
    public boolean replaceFilter(MediaFilter orgFilter, MediaFilter newFilter) {
        int idx = this.filters.indexOf(orgFilter);
        if (idx < 0) {
            return false;
        }
        this.filters.set(idx, newFilter);
        return true;
    }

    @Override // com.samsung.android.sume.core.filter.NNFWFilter, com.samsung.android.sume.core.filter.MediaFilter
    public Stream<MediaFilter> stream() {
        return this.filters.stream();
    }
}

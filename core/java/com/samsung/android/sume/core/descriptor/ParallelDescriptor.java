package com.samsung.android.sume.core.descriptor;

import android.util.Pair;

import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.filter.collection.ParallelFilter;
import com.samsung.android.sume.core.types.PadType;
import com.samsung.android.sume.core.types.SplitType;

import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes6.dex */
public class ParallelDescriptor extends MFDescriptorBase {
    private final List<MFDescriptor> descriptors;
    private final ParallelFilter.Type parallelType;

    public ParallelDescriptor(ParallelFilter.Type type, List<MFDescriptor> descriptors) {
        this.parallelType = type;
        this.descriptors = descriptors;
        setFilterId(type.name());
    }

    public List<MFDescriptor> getDescriptors() {
        return this.descriptors;
    }

    @Override // com.samsung.android.sume.core.descriptor.MFDescriptorBase,
              // com.samsung.android.sume.core.descriptor.MFDescriptor
    public Class<?> getFilterType() {
        return ParallelFilter.class;
    }

    public ParallelFilter.Type getParallelType() {
        return this.parallelType;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter.Option
    public void setSplitType(final SplitType splitType) {
        super.setSplitType(splitType);
        this.descriptors.forEach(
                new Consumer() { // from class:
                                 // com.samsung.android.sume.core.descriptor.ParallelDescriptor$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((MediaFilter.Option) ((MFDescriptor) obj)).setSplitType(SplitType.this);
                    }
                });
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter.Option
    public void setPad(final Pair<PadType, Integer> pad) {
        super.setPad(pad);
        this.descriptors.forEach(
                new Consumer() { // from class:
                                 // com.samsung.android.sume.core.descriptor.ParallelDescriptor$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((MediaFilter.Option) ((MFDescriptor) obj)).setPad(Pair.this);
                    }
                });
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter.Option
    public void setAllowPartialConnection(final boolean allowPartialConnection) {
        super.setAllowPartialConnection(allowPartialConnection);
        this.descriptors.forEach(
                new Consumer() { // from class:
                                 // com.samsung.android.sume.core.descriptor.ParallelDescriptor$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((MediaFilter.Option) ((MFDescriptor) obj))
                                .setAllowPartialConnection(allowPartialConnection);
                    }
                });
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter.Option
    public void setUseExternalBufferComposer(final boolean useExternalBufferComposer) {
        super.setUseExternalBufferComposer(useExternalBufferComposer);
        this.descriptors.forEach(
                new Consumer() { // from class:
                                 // com.samsung.android.sume.core.descriptor.ParallelDescriptor$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((MediaFilter.Option) ((MFDescriptor) obj))
                                .setUseExternalBufferComposer(useExternalBufferComposer);
                    }
                });
    }
}

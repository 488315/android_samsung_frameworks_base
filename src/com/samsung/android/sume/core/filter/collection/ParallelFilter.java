package com.samsung.android.sume.core.filter.collection;

import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.ParallelDescriptor;
import com.samsung.android.sume.core.filter.MediaFilterGroupBase;
import java.util.function.Supplier;

/* loaded from: classes6.dex */
public abstract class ParallelFilter extends MediaFilterGroupBase {
    private final ParallelDescriptor descriptor;

    public enum Type {
        SHARED,
        DNC
    }

    public ParallelFilter(ParallelDescriptor parallelDescriptor, Supplier<BufferChannel> supplier) {
        this.descriptor = parallelDescriptor;
        this.channelSupplier = supplier;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public MFDescriptor getDescriptor() {
        return this.descriptor;
    }
}

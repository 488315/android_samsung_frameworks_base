package com.samsung.android.sume.core.filter.collection;

import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.ParallelDescriptor;
import com.samsung.android.sume.core.filter.MediaFilterGroupBase;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public abstract class ParallelFilter extends MediaFilterGroupBase {
    private final ParallelDescriptor descriptor;

    public enum Type {
        SHARED,
        DNC
    }

    public ParallelFilter(ParallelDescriptor descriptor, Supplier<BufferChannel> channelSupplier) {
        this.descriptor = descriptor;
        this.channelSupplier = channelSupplier;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public MFDescriptor getDescriptor() {
        return this.descriptor;
    }
}

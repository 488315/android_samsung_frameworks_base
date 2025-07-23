package com.samsung.android.sume.core.filter.collection;

import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.descriptor.SequentialDescriptor;
import com.samsung.android.sume.core.filter.MediaFilterGroupBase;
import java.util.function.Supplier;

/* loaded from: classes6.dex */
public abstract class SequentialFilter extends MediaFilterGroupBase {
    protected SequentialDescriptor descriptor;

    public enum Mode {
        BATCHED,
        BUFFERED
    }

    public enum Type {
        PICKER,
        CONVEYOR
    }

    public SequentialFilter(SequentialDescriptor sequentialDescriptor) {
        this.descriptor = sequentialDescriptor;
    }

    public SequentialFilter(SequentialDescriptor sequentialDescriptor, Supplier<BufferChannel> supplier) {
        this.descriptor = sequentialDescriptor;
        this.channelSupplier = supplier;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public SequentialDescriptor getDescriptor() {
        return this.descriptor;
    }
}

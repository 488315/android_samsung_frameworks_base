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

    public SequentialFilter(SequentialDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public SequentialFilter(SequentialDescriptor descriptor, Supplier<BufferChannel> channelSupplier) {
        this.descriptor = descriptor;
        this.channelSupplier = channelSupplier;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public SequentialDescriptor getDescriptor() {
        return this.descriptor;
    }
}

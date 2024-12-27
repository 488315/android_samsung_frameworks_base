package com.samsung.android.sume.core.filter.collection;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.descriptor.SequentialDescriptor;
import com.samsung.android.sume.core.filter.MediaFilter;

/* loaded from: classes6.dex */
public class SimpleConveyorFilter extends SequentialFilter {
    public SimpleConveyorFilter(SequentialDescriptor sequentialDescriptor) {
        super(sequentialDescriptor);
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) {
        MediaBuffer buf = null;
        for (MediaFilter filter : this.filters) {
            buf = filter.run(ibuf);
            ibuf = buf;
        }
        if (buf != null) {
            obuf.put(buf);
        }
        return obuf;
    }
}

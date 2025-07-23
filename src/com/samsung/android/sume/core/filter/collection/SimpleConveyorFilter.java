package com.samsung.android.sume.core.filter.collection;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.descriptor.SequentialDescriptor;
import com.samsung.android.sume.core.filter.MediaFilter;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class SimpleConveyorFilter extends SequentialFilter {
    public SimpleConveyorFilter(SequentialDescriptor sequentialDescriptor) {
        super(sequentialDescriptor);
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        Iterator<MediaFilter> it = this.filters.iterator();
        MutableMediaBuffer mutableMediaBuffer2 = null;
        while (it.hasNext()) {
            mutableMediaBuffer2 = it.next().run(mediaBuffer);
            mediaBuffer = mutableMediaBuffer2;
        }
        if (mutableMediaBuffer2 != null) {
            mutableMediaBuffer.put((MediaBuffer) mutableMediaBuffer2);
        }
        return mutableMediaBuffer;
    }
}

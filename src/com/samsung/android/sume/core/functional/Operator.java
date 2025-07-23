package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;

/* loaded from: classes6.dex */
public interface Operator {
    MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException;

    default MutableMediaBuffer run(MediaBuffer mediaBuffer, MediaBuffer mediaBuffer2) throws UnsupportedOperationException {
        if (mediaBuffer2 instanceof MutableMediaBuffer) {
            return run(mediaBuffer, (MutableMediaBuffer) mediaBuffer2);
        }
        return run(mediaBuffer, MediaBuffer.mutableOf(mediaBuffer2));
    }

    default MutableMediaBuffer run(MediaBuffer mediaBuffer) throws UnsupportedOperationException {
        return run(mediaBuffer, MediaBuffer.mutableOf());
    }
}

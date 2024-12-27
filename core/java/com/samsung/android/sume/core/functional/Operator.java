package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;

/* loaded from: classes6.dex */
public interface Operator {
    MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer)
            throws UnsupportedOperationException;

    default MutableMediaBuffer run(MediaBuffer ibuf, MediaBuffer obuf)
            throws UnsupportedOperationException {
        if (obuf instanceof MutableMediaBuffer) {
            return run(ibuf, (MutableMediaBuffer) obuf);
        }
        return run(ibuf, MediaBuffer.mutableOf(obuf));
    }

    default MutableMediaBuffer run(MediaBuffer ibuf) throws UnsupportedOperationException {
        return run(ibuf, MediaBuffer.mutableOf());
    }
}

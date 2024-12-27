package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.buffer.MediaBuffer;

@FunctionalInterface
public interface BiBufferProcessor {
    void process(MediaBuffer mediaBuffer, MediaBuffer mediaBuffer2);
}

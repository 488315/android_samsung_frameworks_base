package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.buffer.MediaBuffer;

import java.util.function.Supplier;

@FunctionalInterface
public interface BufferSupplier {
    Supplier<MediaBuffer> getBufferSupplier();
}

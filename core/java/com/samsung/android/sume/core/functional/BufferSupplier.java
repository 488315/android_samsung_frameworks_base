package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import java.util.function.Supplier;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface BufferSupplier {
    Supplier<MediaBuffer> getBufferSupplier();
}

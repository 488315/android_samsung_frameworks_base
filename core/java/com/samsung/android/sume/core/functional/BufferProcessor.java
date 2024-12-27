package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.filter.MediaFilter;

@FunctionalInterface
public interface BufferProcessor {
    MediaBuffer process(MediaBuffer mediaBuffer, MediaFilter.Option option);
}

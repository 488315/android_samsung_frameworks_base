package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.filter.MediaFilter;

@FunctionalInterface
/* loaded from: classes6.dex */
public interface BufferProcessor {
    MediaBuffer process(MediaBuffer mediaBuffer, MediaFilter.Option option);
}

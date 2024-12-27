package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.filter.MediaFilter;

import java.util.List;

@FunctionalInterface
/* loaded from: classes6.dex */
public interface BufferComposer {
    MediaBuffer compose(List<MediaBuffer> list, MediaFilter.Option option);
}

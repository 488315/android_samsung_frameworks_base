package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.types.Status;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface ExecuteDelegator {
    Status execute(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer, BiBufferProcessor biBufferProcessor);
}

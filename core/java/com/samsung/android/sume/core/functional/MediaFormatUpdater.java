package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;

@FunctionalInterface
public interface MediaFormatUpdater {
    void update(MediaFormat mediaFormat, MutableMediaFormat mutableMediaFormat);
}

package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;

/* loaded from: classes6.dex */
public class InstantFilter extends DecorateFilter {
    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
    }

    public InstantFilter(MediaFilter mediaFilter) {
        super(mediaFilter);
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        super.prepare();
        MutableMediaBuffer run = super.run(mediaBuffer, mutableMediaBuffer);
        super.release();
        return run;
    }
}

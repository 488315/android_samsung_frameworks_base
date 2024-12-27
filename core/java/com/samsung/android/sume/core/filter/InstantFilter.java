package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;

/* loaded from: classes6.dex */
public class InstantFilter extends DecorateFilter {
    public InstantFilter(MediaFilter filter) {
        super(filter);
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) {
        super.prepare();
        MutableMediaBuffer obuf2 = super.run(ibuf, obuf);
        super.release();
        return obuf2;
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
    }
}

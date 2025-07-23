package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;

/* loaded from: classes6.dex */
public class LazyFilter extends DecorateFilter {
    private boolean prepared;

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {
    }

    public LazyFilter(MediaFilter mediaFilter) {
        super(mediaFilter);
        this.prepared = false;
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        if (!this.prepared) {
            super.prepare();
            this.prepared = true;
        }
        return super.run(mediaBuffer, mutableMediaBuffer);
    }
}

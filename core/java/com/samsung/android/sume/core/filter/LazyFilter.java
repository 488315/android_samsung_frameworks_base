package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;

public class LazyFilter extends DecorateFilter {
    private boolean prepared;

    public LazyFilter(MediaFilter filter) {
        super(filter);
        this.prepared = false;
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter,
              // com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {}

    @Override // com.samsung.android.sume.core.filter.DecorateFilter,
              // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) {
        if (!this.prepared) {
            super.prepare();
            this.prepared = true;
        }
        return super.run(ibuf, obuf);
    }
}

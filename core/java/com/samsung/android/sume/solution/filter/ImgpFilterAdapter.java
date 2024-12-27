package com.samsung.android.sume.solution.filter;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.filter.ImgpFilter;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.functional.Operator;

/* loaded from: classes6.dex */
public class ImgpFilterAdapter implements Operator {
    private final ImgpFilter imgpFilter;

    public ImgpFilterAdapter(ImgpFilter imgpFilter) {
        this.imgpFilter = imgpFilter;
    }

    public ImgpFilter getImgpFilter() {
        return this.imgpFilter;
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf)
            throws UnsupportedOperationException {
        return this.imgpFilter.run(ibuf, obuf);
    }

    public MediaBuffer run(MediaBuffer ibuf, MediaFormat oFormat)
            throws UnsupportedOperationException {
        return run(ibuf, MediaBuffer.mutableOf(oFormat));
    }
}

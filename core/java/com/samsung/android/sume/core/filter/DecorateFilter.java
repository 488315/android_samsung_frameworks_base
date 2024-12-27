package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.functional.PlaceHolder;

import java.util.stream.Stream;

/* loaded from: classes6.dex */
public abstract class DecorateFilter implements MediaFilter {
    private static final String TAG = Def.tagOf((Class<?>) DecorateFilter.class);
    protected MediaFilter successor;

    DecorateFilter(MediaFilter successor) {
        this.successor = successor;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {
        try {
            if (this.successor instanceof PlaceHolder) {
                PlaceHolder<MediaFilter> placeHolder = (PlaceHolder) this.successor;
                MediaFilter replacedFilter = placeHolder.reset();
                if (this.successor instanceof PlaceHolder) {
                    this.successor = replacedFilter;
                }
            }
            this.successor.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) {
        return this.successor.run(ibuf, obuf);
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
        this.successor.release();
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public MFDescriptor getDescriptor() {
        return this.successor.getDescriptor();
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public Stream<MediaFilter> stream() {
        return this.successor.stream();
    }

    public MediaFilter getEnclosedFilter() {
        if (this.successor instanceof DecorateFilter) {
            return ((DecorateFilter) this.successor).getEnclosedFilter();
        }
        return this.successor;
    }

    public MediaFilter getSuccessorFilter() {
        return this.successor;
    }

    public void setSuccessorFilter(MediaFilter successor) {
        this.successor = successor;
    }
}

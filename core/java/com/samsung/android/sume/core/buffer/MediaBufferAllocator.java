package com.samsung.android.sume.core.buffer;

import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.format.UpdatableMediaFormat;

/* loaded from: classes6.dex */
abstract class MediaBufferAllocator {
    private static final String TAG = Def.tagOf((Class<?>) MediaBufferAllocator.class);
    protected Align align;
    protected MediaFormat format;

    abstract MediaBuffer allocate();

    abstract MediaBuffer allocate(Align align);

    abstract MediaBuffer allocateShared();

    abstract <T> MediaBuffer wrap(T t);

    protected MediaBufferAllocator(MediaFormat format) {
        this.format = format;
        this.align = new Align(format.getCols() * format.getChannels(), format.getRows());
    }

    protected MediaBufferAllocator(MediaFormat format, Align align) {
        this.format = format;
        this.align = align;
    }

    static MediaBufferAllocator of(MediaFormat format) {
        if (format instanceof MutableMediaFormat) {
            Log.w(TAG, "mutable format converted as immutable");
            format = ((MutableMediaFormat) format).toMediaFormat();
        }
        MediaFormat fmt = format;
        if ((format instanceof UpdatableMediaFormat) && format.contains(UpdatableMediaFormat.UPDATE_AT_ALLOC)) {
            fmt = ((UpdatableMediaFormat) format).update();
        }
        Align align = Align.setByFormat(fmt);
        return new StapleBufferAllocator(fmt, align);
    }

    static MediaBufferAllocator of(MediaFormat format, Align align) {
        if (format instanceof MutableMediaFormat) {
            Log.w(TAG, "mutable format converted as immutable");
            format = ((MutableMediaFormat) format).toMediaFormat();
        }
        if (align.getDimension() == 0) {
            align = Align.setByFormat(format);
            align.adjustAlign();
        }
        return new StapleBufferAllocator(format, align);
    }

    protected static final class Nothing {
        protected Nothing() {
        }
    }
}

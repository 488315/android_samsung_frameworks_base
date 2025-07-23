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

    protected MediaBufferAllocator(MediaFormat mediaFormat) {
        this.format = mediaFormat;
        this.align = new Align(mediaFormat.getCols() * mediaFormat.getChannels(), mediaFormat.getRows());
    }

    protected MediaBufferAllocator(MediaFormat mediaFormat, Align align) {
        this.format = mediaFormat;
        this.align = align;
    }

    static MediaBufferAllocator of(MediaFormat mediaFormat) {
        if (mediaFormat instanceof MutableMediaFormat) {
            Log.w(TAG, "mutable format converted as immutable");
            mediaFormat = ((MutableMediaFormat) mediaFormat).toMediaFormat();
        }
        if ((mediaFormat instanceof UpdatableMediaFormat) && mediaFormat.contains(UpdatableMediaFormat.UPDATE_AT_ALLOC)) {
            mediaFormat = ((UpdatableMediaFormat) mediaFormat).update();
        }
        return new StapleBufferAllocator(mediaFormat, Align.setByFormat(mediaFormat));
    }

    static MediaBufferAllocator of(MediaFormat mediaFormat, Align align) {
        if (mediaFormat instanceof MutableMediaFormat) {
            Log.w(TAG, "mutable format converted as immutable");
            mediaFormat = ((MutableMediaFormat) mediaFormat).toMediaFormat();
        }
        if (align.getDimension() == 0) {
            align = Align.setByFormat(mediaFormat);
            align.adjustAlign();
        }
        return new StapleBufferAllocator(mediaFormat, align);
    }

    protected static final class Nothing {
        protected Nothing() {
        }
    }
}

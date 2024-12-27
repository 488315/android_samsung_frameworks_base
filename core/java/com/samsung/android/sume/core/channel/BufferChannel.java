package com.samsung.android.sume.core.channel;

import com.samsung.android.sume.core.buffer.MediaBuffer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes6.dex */
public interface BufferChannel extends Channel<MediaBuffer> {
    public static final int DEFAULT = 0;
    public static final int SUPPLY = 1;
    public static final int SURFACE_RECEIVE = 2;
    public static final int SURFACE_SEND = 3;
    public static final int SURFACE_TRANSIT = 4;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {}

    default void setCapacity(int capacity) {
        throw new UnsupportedOperationException();
    }
}

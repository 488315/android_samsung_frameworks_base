package com.samsung.android.sume.core.buffer;

import com.samsung.android.sume.core.format.Shape;

@FunctionalInterface
public interface MediaBufferReader<T> {
    T get();

    static MediaBufferReader<?> of(MediaBuffer buffer) {
        return new RawDataReader(buffer);
    }

    static <V> MediaBufferReader<V> of(final MediaBuffer mediaBuffer, Class<V> cls) {
        if (cls == Shape.class) {
            return new MediaBufferReader() { // from class:
                                             // com.samsung.android.sume.core.buffer.MediaBufferReader$$ExternalSyntheticLambda0
                @Override // com.samsung.android.sume.core.buffer.MediaBufferReader
                public final Object get() {
                    Object shape;
                    shape = MediaBuffer.this.getFormat().getShape();
                    return shape;
                }
            };
        }
        if (Number.class.isAssignableFrom(cls)) {
            return (MediaBufferReader<V>) of(mediaBuffer);
        }
        throw new UnsupportedOperationException("not supported type");
    }
}

package com.samsung.android.sume.core.buffer;

import com.samsung.android.sume.core.format.Shape;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface MediaBufferReader<T> {
  T get();

  /* renamed from: of */
  static MediaBufferReader<?> m342of(MediaBuffer buffer) {
    return new RawDataReader(buffer);
  }

  /* renamed from: of */
  static <V> MediaBufferReader<V> m343of(final MediaBuffer mediaBuffer, Class<V> cls) {
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
      return (MediaBufferReader<V>) m342of(mediaBuffer);
    }
    throw new UnsupportedOperationException("not supported type");
  }
}

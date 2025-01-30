package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public class ByPassFilter implements MediaFilter {
  private static final String TAG = Def.tagOf((Class<?>) ByPassFilter.class);
  private final MFDescriptor mfDescriptor;

  public ByPassFilter(MFDescriptor mfDescriptor) {
    this.mfDescriptor = mfDescriptor;
  }

  @Override // com.samsung.android.sume.core.functional.Operator
  public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) {
    if (obuf.isEmpty()) {
      obuf.put(ibuf.copy());
      return obuf;
    }
    throw new UnsupportedOperationException("not implement yet");
  }

  @Override // com.samsung.android.sume.core.filter.MediaFilter
  public MFDescriptor getDescriptor() {
    return this.mfDescriptor;
  }

  @Override // com.samsung.android.sume.core.filter.MediaFilter
  public Stream<MediaFilter> stream() {
    return Stream.of(this);
  }
}

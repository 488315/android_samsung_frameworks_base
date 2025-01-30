package com.samsung.android.sume.core.descriptor;

import com.samsung.android.sume.core.filter.MediaFilter;
import java.io.Serializable;

/* loaded from: classes4.dex */
public interface MFDescriptor extends Serializable {
  String getFilterId();

  MediaFilter.Option getOption();

  void setOption(MediaFilter.Option option);

  default Class<?> getFilterType() {
    return MediaFilter.class;
  }
}

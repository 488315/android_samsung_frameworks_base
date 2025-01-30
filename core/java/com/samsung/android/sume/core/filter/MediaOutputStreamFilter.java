package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.channel.BufferChannel;
import java.util.function.Function;

/* loaded from: classes4.dex */
public interface MediaOutputStreamFilter {
  int getSendChannelCount();

  Function<Enum<?>, BufferChannel> getSendChannelQuery();

  void setSendChannelQuery(Function<Enum<?>, BufferChannel> function, int i);
}

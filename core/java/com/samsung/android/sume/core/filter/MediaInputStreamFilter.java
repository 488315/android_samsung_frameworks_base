package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.channel.BufferChannel;

import java.util.function.Function;

/* loaded from: classes6.dex */
public interface MediaInputStreamFilter extends MediaFilter {
    int getReceiveChannelCount();

    Function<Enum<?>, BufferChannel> getReceiveChannelQuery();

    void setReceiveChannelQuery(Function<Enum<?>, BufferChannel> function, int i);
}

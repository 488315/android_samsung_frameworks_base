package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.channel.BufferChannel;

import java.util.function.Function;

public interface MediaInputStreamFilter extends MediaFilter {
    int getReceiveChannelCount();

    Function<Enum<?>, BufferChannel> getReceiveChannelQuery();

    void setReceiveChannelQuery(Function<Enum<?>, BufferChannel> function, int i);
}

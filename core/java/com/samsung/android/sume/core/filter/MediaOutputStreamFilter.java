package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.channel.BufferChannel;

import java.util.function.Function;

public interface MediaOutputStreamFilter {
    int getSendChannelCount();

    Function<Enum<?>, BufferChannel> getSendChannelQuery();

    void setSendChannelQuery(Function<Enum<?>, BufferChannel> function, int i);
}

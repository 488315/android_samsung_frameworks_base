package com.samsung.android.sume.core.message;

import com.samsung.android.sume.core.channel.Channel;

public interface MessageChannel extends Channel<Message> {
    String getId();

    void setId(String str);
}

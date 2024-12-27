package com.samsung.android.sume.core.channel;

public interface Channel<T> {
    void cancel();

    void close();

    boolean isClosedForReceive();

    boolean isClosedForSend();

    T receive();

    void send(T t);
}

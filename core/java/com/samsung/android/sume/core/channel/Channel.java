package com.samsung.android.sume.core.channel;

/* loaded from: classes6.dex */
public interface Channel<T> {
    void cancel();

    void close();

    boolean isClosedForReceive();

    boolean isClosedForSend();

    T receive();

    void send(T t);
}

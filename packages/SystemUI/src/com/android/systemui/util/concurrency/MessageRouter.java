package com.android.systemui.util.concurrency;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface MessageRouter {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface DataMessageListener<T> {
        void onMessage(T t);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface SimpleMessageListener {
        void onMessage(int i);
    }

    void cancelMessages(int i);

    <T> void cancelMessages(Class<T> cls);

    default void sendMessage(int i) {
        sendMessageDelayed(i, 0L);
    }

    void sendMessageDelayed(int i, long j);

    void sendMessageDelayed(Object obj, long j);

    void subscribeTo(int i, SimpleMessageListener simpleMessageListener);

    <T> void subscribeTo(Class<T> cls, DataMessageListener<T> dataMessageListener);

    void unsubscribeFrom(int i, SimpleMessageListener simpleMessageListener);

    <T> void unsubscribeFrom(DataMessageListener<T> dataMessageListener);

    void unsubscribeFrom(SimpleMessageListener simpleMessageListener);

    <T> void unsubscribeFrom(Class<T> cls, DataMessageListener<T> dataMessageListener);

    default void sendMessage(Object obj) {
        sendMessageDelayed(obj, 0L);
    }
}

package com.android.systemui.util.concurrency;

public interface MessageRouter {

    public interface DataMessageListener<T> {
        void onMessage(T t);
    }

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

package com.android.systemui.util.concurrency;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface MessageRouter {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface DataMessageListener<T> {
        void onMessage(T t);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

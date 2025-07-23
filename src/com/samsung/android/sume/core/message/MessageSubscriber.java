package com.samsung.android.sume.core.message;

/* loaded from: classes6.dex */
public interface MessageSubscriber {
    default void addMessageConsumer(MessageConsumer messageConsumer) {
    }

    void bindToMessageChannelRouter(MessageChannelRouter messageChannelRouter);

    MessageChannel getMessageChannel();

    Integer[] getSubscribeMessages();

    void onMessageReceived(Message message);

    default void removeMessageConsumer(MessageConsumer messageConsumer) {
    }
}

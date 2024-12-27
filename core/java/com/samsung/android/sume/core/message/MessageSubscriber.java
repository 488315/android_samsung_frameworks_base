package com.samsung.android.sume.core.message;

/* loaded from: classes6.dex */
public interface MessageSubscriber {
    void bindToMessageChannelRouter(MessageChannelRouter messageChannelRouter);

    MessageChannel getMessageChannel();

    Integer[] getSubscribeMessages();

    void onMessageReceived(Message message);

    default void addMessageConsumer(MessageConsumer messageConsumer) {
    }

    default void removeMessageConsumer(MessageConsumer messageConsumer) {
    }
}

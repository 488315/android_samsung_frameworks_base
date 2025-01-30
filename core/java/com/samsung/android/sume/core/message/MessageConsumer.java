package com.samsung.android.sume.core.message;

/* loaded from: classes4.dex */
public interface MessageConsumer {
    default int[] getConsumeMessage() {
        return null;
    }

    default boolean onMessageReceived(Message message) throws UnsupportedOperationException {
        return false;
    }
}

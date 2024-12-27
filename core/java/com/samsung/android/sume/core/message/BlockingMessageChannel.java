package com.samsung.android.sume.core.message;

import java.lang.ref.WeakReference;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingMessageChannel implements MessageChannel {
    private String id;
    protected BlockingQueue<Message> queue;
    private WeakReference<Thread> threadWeakReference;

    public BlockingMessageChannel(String id) {
        this.id = id;
        this.queue = new LinkedBlockingQueue();
    }

    public BlockingMessageChannel(String id, int capacity) {
        this.id = id;
        this.queue = new LinkedBlockingQueue(capacity);
    }

    public void setThreadWeakReference(WeakReference<Thread> threadWeakReference) {
        this.threadWeakReference = threadWeakReference;
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void send(Message data) {
        try {
            this.queue.put(data);
        } catch (InterruptedException e) {
            throw new CancellationException("BlockingMessageChannel is canceled");
        }
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public Message receive() {
        try {
            return this.queue.take();
        } catch (InterruptedException e) {
            throw new CancellationException("BlockingMessageChannel is canceled");
        }
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void close() {}

    @Override // com.samsung.android.sume.core.channel.Channel
    public void cancel() {
        Thread thread = this.threadWeakReference.get();
        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public boolean isClosedForSend() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public boolean isClosedForReceive() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override // com.samsung.android.sume.core.message.MessageChannel
    public String getId() {
        return this.id;
    }

    @Override // com.samsung.android.sume.core.message.MessageChannel
    public void setId(String id) {
        this.id = id;
    }
}

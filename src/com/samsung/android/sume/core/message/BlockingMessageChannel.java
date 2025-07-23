package com.samsung.android.sume.core.message;

import java.lang.ref.WeakReference;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes6.dex */
public class BlockingMessageChannel implements MessageChannel {
    private String id;
    protected BlockingQueue<Message> queue;
    private WeakReference<Thread> threadWeakReference;

    @Override // com.samsung.android.sume.core.channel.Channel
    public void close() {
    }

    public BlockingMessageChannel(String str) {
        this.id = str;
        this.queue = new LinkedBlockingQueue();
    }

    public BlockingMessageChannel(String str, int i) {
        this.id = str;
        this.queue = new LinkedBlockingQueue(i);
    }

    public void setThreadWeakReference(WeakReference<Thread> weakReference) {
        this.threadWeakReference = weakReference;
    }

    @Override // com.samsung.android.sume.core.channel.Channel
    public void send(Message message) {
        try {
            this.queue.put(message);
        } catch (InterruptedException unused) {
            throw new CancellationException("BlockingMessageChannel is canceled");
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.channel.Channel
    public Message receive() {
        try {
            return this.queue.take();
        } catch (InterruptedException unused) {
            throw new CancellationException("BlockingMessageChannel is canceled");
        }
    }

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
    public void setId(String str) {
        this.id = str;
    }
}

package android.os;

import android.util.ArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes3.dex */
public class TestLooperManager {
    private static final ArraySet<Looper> sHeldLoopers = new ArraySet<>();
    private final LinkedBlockingQueue<MessageExecution> mExecuteQueue = new LinkedBlockingQueue<>();
    private final Looper mLooper;
    private final CountDownLatch mLooperHolderLatch;
    private final boolean mLooperIsMyLooper;
    private final MessageQueue mQueue;
    private boolean mReleased;

    public TestLooperManager(Looper looper) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        this.mLooperHolderLatch = countDownLatch;
        ArraySet<Looper> arraySet = sHeldLoopers;
        synchronized (arraySet) {
            if (arraySet.contains(looper)) {
                throw new RuntimeException("TestLooperManager already held for this looper");
            }
            arraySet.add(looper);
        }
        this.mLooper = looper;
        this.mQueue = looper.getQueue();
        boolean z = Looper.myLooper() == looper;
        this.mLooperIsMyLooper = z;
        if (!z) {
            new Handler(looper).post(new LooperHolder());
        } else {
            countDownLatch.countDown();
        }
    }

    public MessageQueue getMessageQueue() {
        checkReleased();
        return this.mQueue;
    }

    @Deprecated
    public MessageQueue getQueue() {
        return getMessageQueue();
    }

    public Message next() throws InterruptedException {
        checkReleased();
        waitForLooperHolder();
        return this.mQueue.next();
    }

    public Message poll() throws InterruptedException {
        checkReleased();
        waitForLooperHolder();
        return this.mQueue.pollForTest();
    }

    public Long peekWhen() throws InterruptedException {
        checkReleased();
        waitForLooperHolder();
        return this.mQueue.peekWhenForTest();
    }

    public boolean isBlockedOnSyncBarrier() throws InterruptedException {
        checkReleased();
        waitForLooperHolder();
        return this.mQueue.isBlockedOnSyncBarrier();
    }

    public void release() {
        ArraySet<Looper> arraySet = sHeldLoopers;
        synchronized (arraySet) {
            arraySet.remove(this.mLooper);
        }
        checkReleased();
        this.mReleased = true;
        this.mExecuteQueue.add(new MessageExecution());
    }

    public void execute(Message message) {
        checkReleased();
        if (this.mLooper.isCurrentThread()) {
            message.target.dispatchMessage(message);
            return;
        }
        if (this.mLooperIsMyLooper) {
            throw new RuntimeException("Cannot call execute from non Looper thread");
        }
        MessageExecution messageExecution = new MessageExecution();
        messageExecution.m = message;
        synchronized (messageExecution) {
            this.mExecuteQueue.add(messageExecution);
            try {
                messageExecution.wait();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
            if (messageExecution.response != null) {
                throw new RuntimeException(messageExecution.response);
            }
        }
    }

    public void recycle(Message message) {
        checkReleased();
        message.recycleUnchecked();
    }

    public boolean hasMessages(Handler handler, Object obj, int i) {
        checkReleased();
        return this.mQueue.hasMessages(handler, i, obj);
    }

    public boolean hasMessages(Handler handler, Object obj, Runnable runnable) {
        checkReleased();
        return this.mQueue.hasMessages(handler, runnable, obj);
    }

    private void checkReleased() {
        if (this.mReleased) {
            throw new RuntimeException("release() has already be called");
        }
    }

    private void waitForLooperHolder() throws InterruptedException {
        try {
            this.mLooperHolderLatch.await();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
        }
    }

    private class LooperHolder implements Runnable {
        private LooperHolder() {
        }

        @Override // java.lang.Runnable
        public void run() {
            TestLooperManager.this.mLooperHolderLatch.countDown();
            while (!TestLooperManager.this.mReleased) {
                try {
                    MessageExecution messageExecution = (MessageExecution) TestLooperManager.this.mExecuteQueue.take();
                    if (messageExecution.m != null) {
                        processMessage(messageExecution);
                    }
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        private void processMessage(MessageExecution messageExecution) {
            synchronized (messageExecution) {
                try {
                    messageExecution.m.target.dispatchMessage(messageExecution.m);
                    messageExecution.response = null;
                } finally {
                    messageExecution.notifyAll();
                }
                messageExecution.notifyAll();
            }
        }
    }

    private static class MessageExecution {
        private Message m;
        private Throwable response;

        private MessageExecution() {
        }
    }
}

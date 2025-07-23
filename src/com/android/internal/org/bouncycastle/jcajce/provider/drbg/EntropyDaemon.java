package com.android.internal.org.bouncycastle.jcajce.provider.drbg;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes5.dex */
class EntropyDaemon implements Runnable {
    private static final Logger LOG = Logger.getLogger(EntropyDaemon.class.getName());
    private final LinkedList<Runnable> tasks = new LinkedList<>();

    void addTask(Runnable runnable) {
        synchronized (this.tasks) {
            this.tasks.add(runnable);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        Runnable poll;
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (this.tasks) {
                poll = this.tasks.poll();
            }
            if (poll != null) {
                try {
                    poll.run();
                } catch (Throwable unused) {
                }
            } else {
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException unused2) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        Logger logger = LOG;
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("entropy thread interrupted - exiting");
        }
    }
}

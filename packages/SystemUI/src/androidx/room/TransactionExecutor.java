package androidx.room;

import java.util.ArrayDeque;
import java.util.concurrent.Executor;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TransactionExecutor implements Executor {
    public Runnable active;
    public final Executor executor;
    public final ArrayDeque tasks = new ArrayDeque();
    public final Object syncLock = new Object();

    public TransactionExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override // java.util.concurrent.Executor
    public final void execute(final Runnable runnable) {
        synchronized (this.syncLock) {
            try {
                this.tasks.offer(new Runnable() { // from class: androidx.room.TransactionExecutor$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        Runnable runnable2 = runnable;
                        TransactionExecutor transactionExecutor = this;
                        try {
                            runnable2.run();
                        } finally {
                            transactionExecutor.scheduleNext();
                        }
                    }
                });
                if (this.active == null) {
                    scheduleNext();
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void scheduleNext() {
        synchronized (this.syncLock) {
            try {
                Object poll = this.tasks.poll();
                Runnable runnable = (Runnable) poll;
                this.active = runnable;
                if (poll != null) {
                    this.executor.execute(runnable);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}

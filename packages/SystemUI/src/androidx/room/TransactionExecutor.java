package androidx.room;

import java.util.ArrayDeque;
import java.util.concurrent.Executor;
import kotlin.Unit;

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
                Object objPoll = this.tasks.poll();
                Runnable runnable = (Runnable) objPoll;
                this.active = runnable;
                if (objPoll != null) {
                    this.executor.execute(runnable);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}

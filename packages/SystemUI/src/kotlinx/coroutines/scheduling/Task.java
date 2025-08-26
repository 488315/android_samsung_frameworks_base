package kotlinx.coroutines.scheduling;

/* loaded from: classes4.dex */
public abstract class Task implements Runnable {
    public long submissionTime;
    public boolean taskContext;

    public Task(long j, boolean z) {
        this.submissionTime = j;
        this.taskContext = z;
    }

    public Task() {
        this(0L, false);
    }
}

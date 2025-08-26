package android.os;

/* loaded from: classes3.dex */
public class ConditionVariable {
    private volatile boolean mCondition;

    public ConditionVariable() {
        this.mCondition = false;
    }

    public ConditionVariable(boolean z) {
        this.mCondition = z;
    }

    public void open() {
        synchronized (this) {
            boolean z = this.mCondition;
            this.mCondition = true;
            if (!z) {
                notifyAll();
            }
        }
    }

    public void close() {
        synchronized (this) {
            this.mCondition = false;
        }
    }

    public void block() {
        synchronized (this) {
            while (!this.mCondition) {
                try {
                    wait();
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    public boolean block(long j) {
        boolean z;
        if (j != 0) {
            synchronized (this) {
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                long j2 = j + jElapsedRealtime;
                while (!this.mCondition && jElapsedRealtime < j2) {
                    try {
                        wait(j2 - jElapsedRealtime);
                    } catch (InterruptedException unused) {
                    }
                    jElapsedRealtime = SystemClock.elapsedRealtime();
                }
                z = this.mCondition;
            }
            return z;
        }
        block();
        return true;
    }
}

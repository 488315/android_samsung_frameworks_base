package com.android.server.notification;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class RankingReconsideration implements Runnable {
    public final long mDelay;
    public final String mKey;
    public int mState = 0;

    public RankingReconsideration(long j, String str) {
        this.mDelay = j;
        this.mKey = str;
    }

    public abstract void applyChangesLocked(NotificationRecord notificationRecord);

    @Override // java.lang.Runnable
    public final void run() {
        if (this.mState == 0) {
            this.mState = 1;
            work();
            this.mState = 2;
            synchronized (this) {
                notifyAll();
            }
        }
    }

    public abstract void work();
}

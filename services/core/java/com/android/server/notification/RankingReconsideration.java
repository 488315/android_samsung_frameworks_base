package com.android.server.notification;

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

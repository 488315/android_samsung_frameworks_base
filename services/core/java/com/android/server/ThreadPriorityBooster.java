package com.android.server;

import android.os.Process;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class ThreadPriorityBooster {
    public static final boolean ENABLE_LOCK_GUARD = false;
    public static final int PRIORITY_NOT_ADJUSTED = Integer.MAX_VALUE;
    public volatile int mBoostToPriority;
    public final int mLockGuardIndex;
    public final ThreadLocal mThreadState = new AnonymousClass1();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.ThreadPriorityBooster$1, reason: invalid class name */
    public final class AnonymousClass1 extends ThreadLocal {
        @Override // java.lang.ThreadLocal
        public final Object initialValue() {
            return new PriorityState();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PriorityState {
        public int regionCounter;
        public final int tid = Process.myTid();
        public int prevPriority = Integer.MAX_VALUE;
    }

    public ThreadPriorityBooster(int i, int i2) {
        this.mBoostToPriority = i;
        this.mLockGuardIndex = i2;
    }

    public void boost() {
        int threadPriority;
        PriorityState priorityState = (PriorityState) this.mThreadState.get();
        if (priorityState.regionCounter == 0
                && (threadPriority = Process.getThreadPriority(priorityState.tid))
                        > this.mBoostToPriority) {
            Process.setThreadPriority(priorityState.tid, this.mBoostToPriority);
            priorityState.prevPriority = threadPriority;
        }
        priorityState.regionCounter++;
    }

    public void reset() {
        int i;
        PriorityState priorityState = (PriorityState) this.mThreadState.get();
        int i2 = priorityState.regionCounter - 1;
        priorityState.regionCounter = i2;
        if (i2 != 0 || (i = priorityState.prevPriority) == Integer.MAX_VALUE) {
            return;
        }
        Process.setThreadPriority(priorityState.tid, i);
        priorityState.prevPriority = Integer.MAX_VALUE;
    }

    public final void setBoostToPriority(int i) {
        this.mBoostToPriority = i;
        PriorityState priorityState = (PriorityState) this.mThreadState.get();
        if (priorityState.regionCounter == 0 || Process.getThreadPriority(priorityState.tid) == i) {
            return;
        }
        Process.setThreadPriority(priorityState.tid, i);
    }
}

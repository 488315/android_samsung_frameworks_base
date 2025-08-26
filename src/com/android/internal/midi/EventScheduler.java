package com.android.internal.midi;

import java.util.SortedMap;
import java.util.TreeMap;

/* loaded from: classes5.dex */
public class EventScheduler {
    public static final long NANOS_PER_MILLI = 1000000;
    private boolean mClosed;
    private final Object mLock = new Object();
    protected FastEventQueue mEventPool = null;
    private int mMaxPoolSize = 200;
    protected volatile SortedMap<Long, FastEventQueue> mEventBuffer = new TreeMap();

    public static class FastEventQueue {
        volatile long mEventsAdded = 1;
        volatile long mEventsRemoved = 0;
        volatile SchedulableEvent mFirst;
        volatile SchedulableEvent mLast;

        public FastEventQueue(SchedulableEvent schedulableEvent) {
            this.mFirst = schedulableEvent;
            this.mLast = this.mFirst;
        }

        int size() {
            return (int) (this.mEventsAdded - this.mEventsRemoved);
        }

        public SchedulableEvent remove() {
            this.mEventsRemoved++;
            SchedulableEvent schedulableEvent = this.mFirst;
            this.mFirst = schedulableEvent.mNext;
            schedulableEvent.mNext = null;
            return schedulableEvent;
        }

        public void add(SchedulableEvent schedulableEvent) {
            schedulableEvent.mNext = null;
            this.mLast.mNext = schedulableEvent;
            this.mLast = schedulableEvent;
            this.mEventsAdded++;
        }
    }

    public static class SchedulableEvent {
        private volatile SchedulableEvent mNext = null;
        private long mTimestamp;

        public SchedulableEvent(long j) {
            this.mTimestamp = j;
        }

        public long getTimestamp() {
            return this.mTimestamp;
        }

        public void setTimestamp(long j) {
            this.mTimestamp = j;
        }
    }

    public SchedulableEvent removeEventfromPool() {
        FastEventQueue fastEventQueue = this.mEventPool;
        if (fastEventQueue == null || fastEventQueue.size() <= 1) {
            return null;
        }
        return this.mEventPool.remove();
    }

    public void addEventToPool(SchedulableEvent schedulableEvent) {
        FastEventQueue fastEventQueue = this.mEventPool;
        if (fastEventQueue == null) {
            this.mEventPool = new FastEventQueue(schedulableEvent);
        } else if (fastEventQueue.size() < this.mMaxPoolSize) {
            this.mEventPool.add(schedulableEvent);
        }
    }

    public void add(SchedulableEvent schedulableEvent) {
        Object lock = getLock();
        synchronized (lock) {
            FastEventQueue fastEventQueue = this.mEventBuffer.get(Long.valueOf(schedulableEvent.getTimestamp()));
            if (fastEventQueue == null) {
                long jLongValue = this.mEventBuffer.isEmpty() ? Long.MAX_VALUE : this.mEventBuffer.firstKey().longValue();
                this.mEventBuffer.put(Long.valueOf(schedulableEvent.getTimestamp()), new FastEventQueue(schedulableEvent));
                if (schedulableEvent.getTimestamp() < jLongValue) {
                    lock.notify();
                }
            } else {
                fastEventQueue.add(schedulableEvent);
            }
        }
    }

    protected SchedulableEvent removeNextEventLocked(long j) {
        FastEventQueue fastEventQueue = this.mEventBuffer.get(Long.valueOf(j));
        if (fastEventQueue.size() == 1) {
            this.mEventBuffer.remove(Long.valueOf(j));
        }
        return fastEventQueue.remove();
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SchedulableEvent getNextEvent(long j) {
        SchedulableEvent schedulableEventRemoveNextEventLocked;
        synchronized (getLock()) {
            if (!this.mEventBuffer.isEmpty()) {
                long jLongValue = this.mEventBuffer.firstKey().longValue();
                schedulableEventRemoveNextEventLocked = jLongValue <= j ? removeNextEventLocked(jLongValue) : null;
            }
        }
        return schedulableEventRemoveNextEventLocked;
    }

    public SchedulableEvent waitNextEvent() throws InterruptedException {
        SchedulableEvent schedulableEventRemoveNextEventLocked;
        Object lock = getLock();
        synchronized (lock) {
            while (true) {
                if (this.mClosed) {
                    schedulableEventRemoveNextEventLocked = null;
                    break;
                }
                long j = 2147483647L;
                if (!this.mEventBuffer.isEmpty()) {
                    long jNanoTime = System.nanoTime();
                    long jLongValue = this.mEventBuffer.firstKey().longValue();
                    if (jLongValue <= jNanoTime) {
                        schedulableEventRemoveNextEventLocked = removeNextEventLocked(jLongValue);
                        break;
                    }
                    long j2 = ((jLongValue - jNanoTime) / 1000000) + 1;
                    if (j2 <= 2147483647L) {
                        j = j2;
                    }
                }
                lock.wait((int) j);
            }
        }
        return schedulableEventRemoveNextEventLocked;
    }

    protected void flush() {
        this.mEventBuffer = new TreeMap();
    }

    public void close() {
        Object lock = getLock();
        synchronized (lock) {
            this.mClosed = true;
            lock.notify();
        }
    }

    protected Object getLock() {
        return this.mLock;
    }
}

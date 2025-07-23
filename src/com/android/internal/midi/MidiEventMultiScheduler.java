package com.android.internal.midi;

/* loaded from: classes5.dex */
public class MidiEventMultiScheduler {
    private MultiLockMidiEventScheduler[] mMidiEventSchedulers;
    private int mNumEventSchedulers;
    private int mNumClosedSchedulers = 0;
    private final Object mMultiLock = new Object();

    private class MultiLockMidiEventScheduler extends MidiEventScheduler {
        private MultiLockMidiEventScheduler() {
        }

        @Override // com.android.internal.midi.EventScheduler
        public void close() {
            synchronized (MidiEventMultiScheduler.this.mMultiLock) {
                MidiEventMultiScheduler.this.mNumClosedSchedulers++;
            }
            super.close();
        }

        @Override // com.android.internal.midi.EventScheduler
        protected Object getLock() {
            return MidiEventMultiScheduler.this.mMultiLock;
        }

        public boolean isEventBufferEmptyLocked() {
            return this.mEventBuffer.isEmpty();
        }

        public long getLowestTimeLocked() {
            return this.mEventBuffer.firstKey().longValue();
        }
    }

    public MidiEventMultiScheduler(int i) {
        this.mNumEventSchedulers = i;
        this.mMidiEventSchedulers = new MultiLockMidiEventScheduler[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.mMidiEventSchedulers[i2] = new MultiLockMidiEventScheduler();
        }
    }

    public boolean waitNextEvent() throws InterruptedException {
        synchronized (this.mMultiLock) {
            while (true) {
                if (this.mNumClosedSchedulers >= this.mNumEventSchedulers) {
                    return false;
                }
                long nanoTime = System.nanoTime();
                long j = Long.MAX_VALUE;
                for (MultiLockMidiEventScheduler multiLockMidiEventScheduler : this.mMidiEventSchedulers) {
                    if (!multiLockMidiEventScheduler.isEventBufferEmptyLocked()) {
                        j = Math.min(j, multiLockMidiEventScheduler.getLowestTimeLocked());
                    }
                }
                if (j <= nanoTime) {
                    return true;
                }
                long j2 = ((j - nanoTime) / 1000000) + 1;
                if (j2 > 2147483647L) {
                    j2 = 2147483647L;
                }
                this.mMultiLock.wait(j2);
            }
        }
    }

    public int getNumEventSchedulers() {
        return this.mNumEventSchedulers;
    }

    public MidiEventScheduler getEventScheduler(int i) {
        return this.mMidiEventSchedulers[i];
    }

    public void close() {
        for (MultiLockMidiEventScheduler multiLockMidiEventScheduler : this.mMidiEventSchedulers) {
            multiLockMidiEventScheduler.close();
        }
    }
}

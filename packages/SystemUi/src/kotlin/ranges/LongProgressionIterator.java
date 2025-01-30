package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.collections.LongIterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LongProgressionIterator extends LongIterator {
    public final long finalElement;
    public boolean hasNext;
    public long next;
    public final long step;

    public LongProgressionIterator(long j, long j2, long j3) {
        this.step = j3;
        this.finalElement = j2;
        boolean z = true;
        if (j3 <= 0 ? j < j2 : j > j2) {
            z = false;
        }
        this.hasNext = z;
        this.next = z ? j : j2;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.hasNext;
    }

    @Override // kotlin.collections.LongIterator
    public final long nextLong() {
        long j = this.next;
        if (j != this.finalElement) {
            this.next = this.step + j;
        } else {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            this.hasNext = false;
        }
        return j;
    }
}

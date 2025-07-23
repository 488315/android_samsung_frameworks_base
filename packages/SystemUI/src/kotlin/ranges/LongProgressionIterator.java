package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.collections.LongIterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class LongProgressionIterator extends LongIterator {
    public final long finalElement;
    public boolean hasNext;
    public long next;
    public final long step;

    public LongProgressionIterator(long j, long j2, long j3) {
        this.step = j3;
        this.finalElement = j2;
        boolean z = false;
        if (j3 <= 0 ? j >= j2 : j <= j2) {
            z = true;
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
            return j;
        }
        if (!this.hasNext) {
            throw new NoSuchElementException();
        }
        this.hasNext = false;
        return j;
    }
}

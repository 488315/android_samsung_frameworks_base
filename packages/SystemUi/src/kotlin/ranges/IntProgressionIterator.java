package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.collections.IntIterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class IntProgressionIterator extends IntIterator {
    public final int finalElement;
    public boolean hasNext;
    public int next;
    public final int step;

    public IntProgressionIterator(int i, int i2, int i3) {
        this.step = i3;
        this.finalElement = i2;
        boolean z = true;
        if (i3 <= 0 ? i < i2 : i > i2) {
            z = false;
        }
        this.hasNext = z;
        this.next = z ? i : i2;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.hasNext;
    }

    @Override // kotlin.collections.IntIterator
    public final int nextInt() {
        int i = this.next;
        if (i != this.finalElement) {
            this.next = this.step + i;
        } else {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            this.hasNext = false;
        }
        return i;
    }
}

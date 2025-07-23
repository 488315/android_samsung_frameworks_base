package kotlin.jvm.internal;

import java.util.NoSuchElementException;
import kotlin.collections.IntIterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ArrayIntIterator extends IntIterator {
    public final int[] array;
    public int index;

    public ArrayIntIterator(int[] iArr) {
        this.array = iArr;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.index < this.array.length;
    }

    @Override // kotlin.collections.IntIterator
    public final int nextInt() {
        try {
            int[] iArr = this.array;
            int i = this.index;
            this.index = i + 1;
            return iArr[i];
        } catch (ArrayIndexOutOfBoundsException e) {
            this.index--;
            throw new NoSuchElementException(e.getMessage());
        }
    }
}

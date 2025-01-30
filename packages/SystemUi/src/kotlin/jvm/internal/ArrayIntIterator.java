package kotlin.jvm.internal;

import java.util.NoSuchElementException;
import kotlin.collections.IntIterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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

package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

import java.util.NoSuchElementException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TrieIterator<E> extends AbstractListIterator<E> {
    public int height;
    public boolean isInRightEdge;
    public Object[] path;

    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v3 */
    public TrieIterator(Object[] objArr, int i, int i2, int i3) {
        super(i, i2);
        this.height = i3;
        Object[] objArr2 = new Object[i3];
        this.path = objArr2;
        ?? r5 = i == i2 ? 1 : 0;
        this.isInRightEdge = r5;
        objArr2[0] = objArr;
        fillPath(i - r5, 1);
    }

    public final Object elementAtCurrentIndex() {
        return ((Object[]) this.path[this.height - 1])[this.index & 31];
    }

    public final void fillPath(int i, int i2) {
        int i3 = (this.height - i2) * 5;
        while (i2 < this.height) {
            Object[] objArr = this.path;
            objArr[i2] = ((Object[]) objArr[i2 - 1])[UtilsKt.indexSegment(i, i3)];
            i3 -= 5;
            i2++;
        }
    }

    public final void fillPathIfNeeded(int i) {
        int i2 = 0;
        while (UtilsKt.indexSegment(this.index, i2) == i) {
            i2 += 5;
        }
        if (i2 > 0) {
            fillPath(this.index, ((this.height - 1) - (i2 / 5)) + 1);
        }
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.AbstractListIterator, java.util.ListIterator, java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Object elementAtCurrentIndex = elementAtCurrentIndex();
        int i = this.index + 1;
        this.index = i;
        if (i == this.size) {
            this.isInRightEdge = true;
            return elementAtCurrentIndex;
        }
        fillPathIfNeeded(0);
        return elementAtCurrentIndex;
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        this.index--;
        if (this.isInRightEdge) {
            this.isInRightEdge = false;
            return elementAtCurrentIndex();
        }
        fillPathIfNeeded(31);
        return elementAtCurrentIndex();
    }
}

package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public final class PersistentVectorMutableIterator<T> extends AbstractListIterator<T> implements ListIterator<T> {
    public final PersistentVectorBuilder builder;
    public int expectedModCount;
    public int lastIteratedIndex;
    public TrieIterator trieIterator;

    public PersistentVectorMutableIterator(PersistentVectorBuilder<T> persistentVectorBuilder, int i) {
        super(i, persistentVectorBuilder.getSize());
        this.builder = persistentVectorBuilder;
        this.expectedModCount = persistentVectorBuilder.getModCount$runtime_release();
        this.lastIteratedIndex = -1;
        setupTrieIterator();
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.AbstractListIterator, java.util.ListIterator
    public final void add(Object obj) {
        checkForComodification();
        this.builder.add(this.index, obj);
        this.index++;
        this.size = this.builder.getSize();
        this.expectedModCount = this.builder.getModCount$runtime_release();
        this.lastIteratedIndex = -1;
        setupTrieIterator();
    }

    public final void checkForComodification() {
        if (this.expectedModCount != this.builder.getModCount$runtime_release()) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.AbstractListIterator, java.util.ListIterator, java.util.Iterator
    public final Object next() {
        checkForComodification();
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int i = this.index;
        this.lastIteratedIndex = i;
        TrieIterator trieIterator = this.trieIterator;
        if (trieIterator == null) {
            Object[] objArr = this.builder.tail;
            this.index = i + 1;
            return objArr[i];
        }
        if (trieIterator.hasNext()) {
            this.index++;
            return trieIterator.next();
        }
        Object[] objArr2 = this.builder.tail;
        int i2 = this.index;
        this.index = i2 + 1;
        return objArr2[i2 - trieIterator.size];
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        checkForComodification();
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        int i = this.index;
        this.lastIteratedIndex = i - 1;
        TrieIterator trieIterator = this.trieIterator;
        if (trieIterator == null) {
            Object[] objArr = this.builder.tail;
            int i2 = i - 1;
            this.index = i2;
            return objArr[i2];
        }
        int i3 = trieIterator.size;
        if (i <= i3) {
            this.index = i - 1;
            return trieIterator.previous();
        }
        Object[] objArr2 = this.builder.tail;
        int i4 = i - 1;
        this.index = i4;
        return objArr2[i4 - i3];
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.AbstractListIterator, java.util.ListIterator, java.util.Iterator
    public final void remove() {
        checkForComodification();
        int i = this.lastIteratedIndex;
        if (i == -1) {
            throw new IllegalStateException();
        }
        this.builder.removeAt(i);
        int i2 = this.lastIteratedIndex;
        if (i2 < this.index) {
            this.index = i2;
        }
        this.size = this.builder.getSize();
        this.expectedModCount = this.builder.getModCount$runtime_release();
        this.lastIteratedIndex = -1;
        setupTrieIterator();
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.AbstractListIterator, java.util.ListIterator
    public final void set(Object obj) {
        checkForComodification();
        int i = this.lastIteratedIndex;
        if (i == -1) {
            throw new IllegalStateException();
        }
        this.builder.set(i, obj);
        this.expectedModCount = this.builder.getModCount$runtime_release();
        setupTrieIterator();
    }

    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    public final void setupTrieIterator() {
        PersistentVectorBuilder persistentVectorBuilder = this.builder;
        Object[] objArr = persistentVectorBuilder.root;
        if (objArr == null) {
            this.trieIterator = null;
            return;
        }
        int i = (persistentVectorBuilder.size - 1) & (-32);
        int i2 = this.index;
        if (i2 > i) {
            i2 = i;
        }
        int i3 = (persistentVectorBuilder.rootShift / 5) + 1;
        TrieIterator trieIterator = this.trieIterator;
        if (trieIterator == null) {
            this.trieIterator = new TrieIterator(objArr, i2, i, i3);
            return;
        }
        trieIterator.index = i2;
        trieIterator.size = i;
        trieIterator.height = i3;
        if (trieIterator.path.length < i3) {
            trieIterator.path = new Object[i3];
        }
        trieIterator.path[0] = objArr;
        ?? r0 = i2 == i ? 1 : 0;
        trieIterator.isInRightEdge = r0;
        trieIterator.fillPath(i2 - r0, 1);
    }
}

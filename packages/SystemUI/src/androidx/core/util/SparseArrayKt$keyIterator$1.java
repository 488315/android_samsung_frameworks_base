package androidx.core.util;

import android.util.SparseArray;
import kotlin.collections.IntIterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SparseArrayKt$keyIterator$1 extends IntIterator {
    public final /* synthetic */ SparseArray $this_keyIterator;
    public int index;

    public SparseArrayKt$keyIterator$1(SparseArray<Object> sparseArray) {
        this.$this_keyIterator = sparseArray;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.index < this.$this_keyIterator.size();
    }

    @Override // kotlin.collections.IntIterator
    public final int nextInt() {
        SparseArray sparseArray = this.$this_keyIterator;
        int i = this.index;
        this.index = i + 1;
        return sparseArray.keyAt(i);
    }
}

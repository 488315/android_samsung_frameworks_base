package androidx.core.util;

import android.util.SparseArray;
import kotlin.collections.IntIterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

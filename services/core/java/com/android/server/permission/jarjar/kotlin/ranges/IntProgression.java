package com.android.server.permission.jarjar.kotlin.ranges;

import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class IntProgression implements Iterable {
    public final int first;
    public final int last;
    public final int step;

    public IntProgression(int i, int i2) {
        this.first = i;
        if (i < i2) {
            int i3 = i2 % 1;
            int i4 = i % 1;
            int i5 = ((i3 < 0 ? i3 + 1 : i3) - (i4 < 0 ? i4 + 1 : i4)) % 1;
            i2 -= i5 < 0 ? i5 + 1 : i5;
        }
        this.last = i2;
        this.step = 1;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new IntProgressionIterator(this.first, this.last, this.step);
    }
}

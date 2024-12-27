package com.android.server.permission.jarjar.kotlin.ranges;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class IntRange extends IntProgression {
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0011, code lost:
    
        if (r1.first > r1.last) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean equals(java.lang.Object r4) {
        /*
            r3 = this;
            boolean r0 = r4 instanceof com.android.server.permission.jarjar.kotlin.ranges.IntRange
            if (r0 == 0) goto L20
            int r0 = r3.first
            int r3 = r3.last
            if (r0 <= r3) goto L14
            r1 = r4
            com.android.server.permission.jarjar.kotlin.ranges.IntRange r1 = (com.android.server.permission.jarjar.kotlin.ranges.IntRange) r1
            int r2 = r1.first
            int r1 = r1.last
            if (r2 <= r1) goto L14
            goto L1e
        L14:
            com.android.server.permission.jarjar.kotlin.ranges.IntRange r4 = (com.android.server.permission.jarjar.kotlin.ranges.IntRange) r4
            int r1 = r4.first
            if (r0 != r1) goto L20
            int r4 = r4.last
            if (r3 != r4) goto L20
        L1e:
            r3 = 1
            goto L21
        L20:
            r3 = 0
        L21:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.permission.jarjar.kotlin.ranges.IntRange.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        int i = this.first;
        int i2 = this.last;
        if (i > i2) {
            return -1;
        }
        return i2 + (i * 31);
    }

    public final String toString() {
        return this.first + ".." + this.last;
    }
}

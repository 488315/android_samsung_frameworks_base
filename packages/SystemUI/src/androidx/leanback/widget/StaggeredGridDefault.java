package androidx.leanback.widget;

import androidx.leanback.widget.StaggeredGrid;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class StaggeredGridDefault extends StaggeredGrid {
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0136, code lost:
    
        return true;
     */
    @Override // androidx.leanback.widget.StaggeredGrid
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean appendVisibleItemsWithoutCache(int r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 355
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.StaggeredGridDefault.appendVisibleItemsWithoutCache(int, boolean):boolean");
    }

    public final int findRowEdgeLimitSearchIndex(boolean z) {
        boolean z2 = false;
        if (z) {
            for (int i = this.mLastVisibleIndex; i >= this.mFirstVisibleIndex; i--) {
                int i2 = getLocation(i).mRow;
                if (i2 == 0) {
                    z2 = true;
                } else if (z2 && i2 == this.mNumRows - 1) {
                    return i;
                }
            }
            return -1;
        }
        for (int i3 = this.mFirstVisibleIndex; i3 <= this.mLastVisibleIndex; i3++) {
            int i4 = getLocation(i3).mRow;
            if (i4 == this.mNumRows - 1) {
                z2 = true;
            } else if (z2 && i4 == 0) {
                return i3;
            }
        }
        return -1;
    }

    @Override // androidx.leanback.widget.Grid
    public final int findRowMax(int[] iArr, int i, boolean z) {
        int i2;
        int edge = this.mProvider.getEdge(i);
        StaggeredGrid.Location location = getLocation(i);
        int i3 = location.mRow;
        if (this.mReversedFlow) {
            i2 = i3;
            int i4 = i2;
            int i5 = 1;
            int i6 = edge;
            for (int i7 = i + 1; i5 < this.mNumRows && i7 <= this.mLastVisibleIndex; i7++) {
                StaggeredGrid.Location location2 = getLocation(i7);
                i6 += location2.mOffset;
                int i8 = location2.mRow;
                if (i8 != i4) {
                    i5++;
                    if (!z ? i6 >= edge : i6 <= edge) {
                        i4 = i8;
                    } else {
                        edge = i6;
                        i = i7;
                        i2 = i8;
                        i4 = i2;
                    }
                }
            }
        } else {
            int i9 = 1;
            int i10 = i3;
            StaggeredGrid.Location location3 = location;
            int i11 = edge;
            edge = this.mProvider.getSize(i) + edge;
            i2 = i10;
            for (int i12 = i - 1; i9 < this.mNumRows && i12 >= this.mFirstVisibleIndex; i12--) {
                i11 -= location3.mOffset;
                location3 = getLocation(i12);
                int i13 = location3.mRow;
                if (i13 != i10) {
                    i9++;
                    int size = this.mProvider.getSize(i12) + i11;
                    if (!z ? size >= edge : size <= edge) {
                        i10 = i13;
                    } else {
                        edge = size;
                        i = i12;
                        i2 = i13;
                        i10 = i2;
                    }
                }
            }
        }
        if (iArr != null) {
            iArr[0] = i2;
            iArr[1] = i;
        }
        return edge;
    }

    @Override // androidx.leanback.widget.Grid
    public final int findRowMin(int[] iArr, int i, boolean z) {
        int i2;
        int edge = this.mProvider.getEdge(i);
        StaggeredGrid.Location location = getLocation(i);
        int i3 = location.mRow;
        if (this.mReversedFlow) {
            int i4 = 1;
            i2 = edge - this.mProvider.getSize(i);
            int i5 = i3;
            for (int i6 = i - 1; i4 < this.mNumRows && i6 >= this.mFirstVisibleIndex; i6--) {
                edge -= location.mOffset;
                location = getLocation(i6);
                int i7 = location.mRow;
                if (i7 != i5) {
                    i4++;
                    int size = edge - this.mProvider.getSize(i6);
                    if (!z ? size >= i2 : size <= i2) {
                        i5 = i7;
                    } else {
                        i2 = size;
                        i = i6;
                        i3 = i7;
                        i5 = i3;
                    }
                }
            }
        } else {
            int i8 = i3;
            int i9 = i8;
            int i10 = 1;
            int i11 = edge;
            for (int i12 = i + 1; i10 < this.mNumRows && i12 <= this.mLastVisibleIndex; i12++) {
                StaggeredGrid.Location location2 = getLocation(i12);
                i11 += location2.mOffset;
                int i13 = location2.mRow;
                if (i13 != i9) {
                    i10++;
                    if (!z ? i11 >= edge : i11 <= edge) {
                        i9 = i13;
                    } else {
                        edge = i11;
                        i = i12;
                        i8 = i13;
                        i9 = i8;
                    }
                }
            }
            i2 = edge;
            i3 = i8;
        }
        if (iArr != null) {
            iArr[0] = i3;
            iArr[1] = i;
        }
        return i2;
    }

    public final int getRowMax(int i) {
        int i2;
        StaggeredGrid.Location location;
        int i3 = this.mFirstVisibleIndex;
        if (i3 < 0) {
            return Integer.MIN_VALUE;
        }
        if (this.mReversedFlow) {
            int edge = this.mProvider.getEdge(i3);
            if (getLocation(this.mFirstVisibleIndex).mRow == i) {
                return edge;
            }
            int i4 = this.mFirstVisibleIndex;
            do {
                i4++;
                if (i4 <= getLastIndex()) {
                    location = getLocation(i4);
                    edge += location.mOffset;
                }
            } while (location.mRow != i);
            return edge;
        }
        int edge2 = this.mProvider.getEdge(this.mLastVisibleIndex);
        StaggeredGrid.Location location2 = getLocation(this.mLastVisibleIndex);
        if (location2.mRow == i) {
            i2 = location2.mSize;
        } else {
            int i5 = this.mLastVisibleIndex;
            do {
                i5--;
                if (i5 >= this.mFirstIndex) {
                    edge2 -= location2.mOffset;
                    location2 = getLocation(i5);
                }
            } while (location2.mRow != i);
            i2 = location2.mSize;
        }
        return edge2 + i2;
        return Integer.MIN_VALUE;
    }

    public final int getRowMin(int i) {
        StaggeredGrid.Location location;
        int i2;
        int i3 = this.mFirstVisibleIndex;
        if (i3 < 0) {
            return Integer.MAX_VALUE;
        }
        if (!this.mReversedFlow) {
            int edge = this.mProvider.getEdge(i3);
            if (getLocation(this.mFirstVisibleIndex).mRow == i) {
                return edge;
            }
            int i4 = this.mFirstVisibleIndex;
            do {
                i4++;
                if (i4 <= getLastIndex()) {
                    location = getLocation(i4);
                    edge += location.mOffset;
                }
            } while (location.mRow != i);
            return edge;
        }
        int edge2 = this.mProvider.getEdge(this.mLastVisibleIndex);
        StaggeredGrid.Location location2 = getLocation(this.mLastVisibleIndex);
        if (location2.mRow == i) {
            i2 = location2.mSize;
        } else {
            int i5 = this.mLastVisibleIndex;
            do {
                i5--;
                if (i5 >= this.mFirstIndex) {
                    edge2 -= location2.mOffset;
                    location2 = getLocation(i5);
                }
            } while (location2.mRow != i);
            i2 = location2.mSize;
        }
        return edge2 - i2;
        return Integer.MAX_VALUE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x012a, code lost:
    
        return true;
     */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00fb A[LOOP:2: B:54:0x00fb->B:68:0x011f, LOOP_START, PHI: r5 r8 r9
      0x00fb: PHI (r5v12 int) = (r5v6 int), (r5v17 int) binds: [B:53:0x00f9, B:68:0x011f] A[DONT_GENERATE, DONT_INLINE]
      0x00fb: PHI (r8v19 int) = (r8v17 int), (r8v20 int) binds: [B:53:0x00f9, B:68:0x011f] A[DONT_GENERATE, DONT_INLINE]
      0x00fb: PHI (r9v8 int) = (r9v6 int), (r9v10 int) binds: [B:53:0x00f9, B:68:0x011f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x012d  */
    @Override // androidx.leanback.widget.StaggeredGrid
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean prependVisibleItemsWithoutCache(int r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 345
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.StaggeredGridDefault.prependVisibleItemsWithoutCache(int, boolean):boolean");
    }
}

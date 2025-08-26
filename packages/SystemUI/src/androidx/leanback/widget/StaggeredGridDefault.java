package androidx.leanback.widget;

import androidx.leanback.widget.StaggeredGrid;

/* loaded from: classes.dex */
public final class StaggeredGridDefault extends StaggeredGrid {
    /* JADX WARN: Code restructure failed: missing block: B:105:0x0136, code lost:
    
        return true;
     */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0107 A[LOOP:2: B:88:0x0107->B:104:0x012b, LOOP_START, PHI: r6 r9 r10
      0x0107: PHI (r6v12 int) = (r6v6 int), (r6v16 int) binds: [B:87:0x0105, B:104:0x012b] A[DONT_GENERATE, DONT_INLINE]
      0x0107: PHI (r9v20 int) = (r9v18 int), (r9v21 int) binds: [B:87:0x0105, B:104:0x012b] A[DONT_GENERATE, DONT_INLINE]
      0x0107: PHI (r10v7 int) = (r10v5 int), (r10v9 int) binds: [B:87:0x0105, B:104:0x012b] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.leanback.widget.StaggeredGrid
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean appendVisibleItemsWithoutCache(int i, boolean z) {
        int i2;
        int i3;
        boolean z2;
        int rowMin;
        int i4;
        int i5;
        int count = this.mProvider.getCount();
        int i6 = this.mLastVisibleIndex;
        if (i6 < 0) {
            int i7 = this.mStartIndex;
            i2 = i7 != -1 ? i7 : 0;
            i3 = (this.mLocations.size() > 0 ? getLocation(getLastIndex()).mRow + 1 : i2) % this.mNumRows;
            z2 = false;
            rowMin = 0;
        } else {
            if (i6 < getLastIndex()) {
                return false;
            }
            int i8 = this.mLastVisibleIndex;
            i2 = i8 + 1;
            i3 = getLocation(i8).mRow;
            int iFindRowEdgeLimitSearchIndex = findRowEdgeLimitSearchIndex(true);
            if (iFindRowEdgeLimitSearchIndex < 0) {
                rowMin = Integer.MIN_VALUE;
                for (int i9 = 0; i9 < this.mNumRows; i9++) {
                    rowMin = this.mReversedFlow ? getRowMin(i9) : getRowMax(i9);
                    if (rowMin != Integer.MIN_VALUE) {
                        break;
                    }
                }
            } else {
                rowMin = this.mReversedFlow ? findRowMin(null, iFindRowEdgeLimitSearchIndex, false) : findRowMax(null, iFindRowEdgeLimitSearchIndex, true);
            }
            if (!this.mReversedFlow ? getRowMax(i3) >= rowMin : getRowMin(i3) <= rowMin) {
                i3++;
                if (i3 == this.mNumRows) {
                    rowMin = this.mReversedFlow ? findRowMin(false, null) : findRowMax(true, null);
                    i3 = 0;
                }
            }
            z2 = true;
        }
        boolean z3 = false;
        loop1: while (true) {
            if (i3 < this.mNumRows) {
                if (i2 == count || (!z && checkAppendOverLimit(i))) {
                    break;
                }
                int rowMin2 = this.mReversedFlow ? getRowMin(i3) : getRowMax(i3);
                if (rowMin2 == Integer.MAX_VALUE || rowMin2 == Integer.MIN_VALUE) {
                    if (i3 == 0) {
                        rowMin2 = this.mReversedFlow ? getRowMin(this.mNumRows - 1) : getRowMax(this.mNumRows - 1);
                        if (rowMin2 != Integer.MAX_VALUE && rowMin2 != Integer.MIN_VALUE) {
                            if (this.mReversedFlow) {
                                i5 = this.mSpacing;
                                i4 = -i5;
                                rowMin2 += i4;
                            } else {
                                i4 = this.mSpacing;
                                rowMin2 += i4;
                            }
                        }
                    } else {
                        rowMin2 = this.mReversedFlow ? getRowMax(i3 - 1) : getRowMin(i3 - 1);
                    }
                    int i10 = i2 + 1;
                    int iAppendVisibleItemToRow = appendVisibleItemToRow(i2, i3, rowMin2);
                    if (z2) {
                        while (true) {
                            if (!this.mReversedFlow) {
                                if (rowMin2 + iAppendVisibleItemToRow >= rowMin) {
                                    break;
                                }
                                if (i10 == count) {
                                    break loop1;
                                }
                                break loop1;
                                break loop1;
                            }
                            if (rowMin2 - iAppendVisibleItemToRow <= rowMin) {
                                break;
                            }
                            if (i10 == count || (!z && checkAppendOverLimit(i))) {
                                break loop1;
                            }
                            rowMin2 += this.mReversedFlow ? (-iAppendVisibleItemToRow) - this.mSpacing : iAppendVisibleItemToRow + this.mSpacing;
                            int i11 = i10 + 1;
                            int iAppendVisibleItemToRow2 = appendVisibleItemToRow(i10, i3, rowMin2);
                            i10 = i11;
                            iAppendVisibleItemToRow = iAppendVisibleItemToRow2;
                        }
                    } else {
                        z2 = true;
                        rowMin = this.mReversedFlow ? getRowMin(i3) : getRowMax(i3);
                    }
                    i2 = i10;
                    i3++;
                    z3 = true;
                } else if (this.mReversedFlow) {
                    i5 = this.mSpacing;
                    i4 = -i5;
                    rowMin2 += i4;
                    int i102 = i2 + 1;
                    int iAppendVisibleItemToRow3 = appendVisibleItemToRow(i2, i3, rowMin2);
                    if (z2) {
                    }
                    i2 = i102;
                    i3++;
                    z3 = true;
                } else {
                    i4 = this.mSpacing;
                    rowMin2 += i4;
                    int i1022 = i2 + 1;
                    int iAppendVisibleItemToRow32 = appendVisibleItemToRow(i2, i3, rowMin2);
                    if (z2) {
                    }
                    i2 = i1022;
                    i3++;
                    z3 = true;
                }
            } else {
                if (z) {
                    break;
                }
                rowMin = this.mReversedFlow ? findRowMin(false, null) : findRowMax(true, null);
                i3 = 0;
            }
        }
        return z3;
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
        int size;
        int edge = this.mProvider.getEdge(i);
        StaggeredGrid.Location location = getLocation(i);
        int i2 = location.mRow;
        if (this.mReversedFlow) {
            int i3 = 1;
            size = edge - this.mProvider.getSize(i);
            int i4 = i2;
            for (int i5 = i - 1; i3 < this.mNumRows && i5 >= this.mFirstVisibleIndex; i5--) {
                edge -= location.mOffset;
                location = getLocation(i5);
                int i6 = location.mRow;
                if (i6 != i4) {
                    i3++;
                    int size2 = edge - this.mProvider.getSize(i5);
                    if (!z ? size2 >= size : size2 <= size) {
                        i4 = i6;
                    } else {
                        size = size2;
                        i = i5;
                        i2 = i6;
                        i4 = i2;
                    }
                }
            }
        } else {
            int i7 = i2;
            int i8 = i7;
            int i9 = 1;
            int i10 = edge;
            for (int i11 = i + 1; i9 < this.mNumRows && i11 <= this.mLastVisibleIndex; i11++) {
                StaggeredGrid.Location location2 = getLocation(i11);
                i10 += location2.mOffset;
                int i12 = location2.mRow;
                if (i12 != i8) {
                    i9++;
                    if (!z ? i10 >= edge : i10 <= edge) {
                        i8 = i12;
                    } else {
                        edge = i10;
                        i = i11;
                        i7 = i12;
                        i8 = i7;
                    }
                }
            }
            size = edge;
            i2 = i7;
        }
        if (iArr != null) {
            iArr[0] = i2;
            iArr[1] = i;
        }
        return size;
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

    /* JADX WARN: Code restructure failed: missing block: B:103:0x012a, code lost:
    
        return true;
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00fb A[LOOP:2: B:86:0x00fb->B:102:0x011f, LOOP_START, PHI: r5 r8 r9
      0x00fb: PHI (r5v12 int) = (r5v6 int), (r5v17 int) binds: [B:85:0x00f9, B:102:0x011f] A[DONT_GENERATE, DONT_INLINE]
      0x00fb: PHI (r8v19 int) = (r8v17 int), (r8v20 int) binds: [B:85:0x00f9, B:102:0x011f] A[DONT_GENERATE, DONT_INLINE]
      0x00fb: PHI (r9v8 int) = (r9v6 int), (r9v10 int) binds: [B:85:0x00f9, B:102:0x011f] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.leanback.widget.StaggeredGrid
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean prependVisibleItemsWithoutCache(int i, boolean z) {
        int i2;
        int i3;
        boolean z2;
        int rowMax;
        int i4;
        int i5;
        int i6 = this.mFirstVisibleIndex;
        if (i6 < 0) {
            int i7 = this.mStartIndex;
            i2 = i7 != -1 ? i7 : 0;
            i3 = (this.mLocations.size() > 0 ? (getLocation(this.mFirstIndex).mRow + this.mNumRows) - 1 : i2) % this.mNumRows;
            z2 = false;
            rowMax = 0;
        } else {
            if (i6 > this.mFirstIndex) {
                return false;
            }
            i2 = i6 - 1;
            i3 = getLocation(i6).mRow;
            int iFindRowEdgeLimitSearchIndex = findRowEdgeLimitSearchIndex(false);
            if (iFindRowEdgeLimitSearchIndex < 0) {
                i3--;
                rowMax = Integer.MAX_VALUE;
                for (int i8 = this.mNumRows - 1; i8 >= 0; i8--) {
                    rowMax = this.mReversedFlow ? getRowMax(i8) : getRowMin(i8);
                    if (rowMax != Integer.MAX_VALUE) {
                        break;
                    }
                }
            } else {
                rowMax = this.mReversedFlow ? findRowMax(null, iFindRowEdgeLimitSearchIndex, true) : findRowMin(null, iFindRowEdgeLimitSearchIndex, false);
            }
            if (!this.mReversedFlow ? getRowMin(i3) <= rowMax : getRowMax(i3) >= rowMax) {
                i3--;
                if (i3 < 0) {
                    i3 = this.mNumRows - 1;
                    rowMax = this.mReversedFlow ? findRowMax(true, null) : findRowMin(false, null);
                }
            }
            z2 = true;
        }
        boolean z3 = false;
        loop1: while (true) {
            if (i3 >= 0) {
                if (i2 < 0 || (!z && checkPrependOverLimit(i))) {
                    break;
                }
                int rowMax2 = this.mReversedFlow ? getRowMax(i3) : getRowMin(i3);
                if (rowMax2 == Integer.MAX_VALUE || rowMax2 == Integer.MIN_VALUE) {
                    if (i3 == this.mNumRows - 1) {
                        rowMax2 = this.mReversedFlow ? getRowMax(0) : getRowMin(0);
                        if (rowMax2 != Integer.MAX_VALUE && rowMax2 != Integer.MIN_VALUE) {
                            if (this.mReversedFlow) {
                                i5 = this.mSpacing;
                                rowMax2 += i5;
                            } else {
                                i4 = this.mSpacing;
                                i5 = -i4;
                                rowMax2 += i5;
                            }
                        }
                    } else {
                        rowMax2 = this.mReversedFlow ? getRowMin(i3 + 1) : getRowMax(i3 + 1);
                    }
                    int i9 = i2 - 1;
                    int iPrependVisibleItemToRow = prependVisibleItemToRow(i2, i3, rowMax2);
                    if (z2) {
                        while (true) {
                            if (!this.mReversedFlow) {
                                if (rowMax2 - iPrependVisibleItemToRow <= rowMax) {
                                    break;
                                }
                                if (i9 < 0) {
                                    break loop1;
                                }
                                break loop1;
                                break loop1;
                            }
                            if (rowMax2 + iPrependVisibleItemToRow >= rowMax) {
                                break;
                            }
                            if (i9 < 0 || (!z && checkPrependOverLimit(i))) {
                                break loop1;
                            }
                            rowMax2 += this.mReversedFlow ? iPrependVisibleItemToRow + this.mSpacing : (-iPrependVisibleItemToRow) - this.mSpacing;
                            int i10 = i9 - 1;
                            int iPrependVisibleItemToRow2 = prependVisibleItemToRow(i9, i3, rowMax2);
                            i9 = i10;
                            iPrependVisibleItemToRow = iPrependVisibleItemToRow2;
                        }
                    } else {
                        z2 = true;
                        rowMax = this.mReversedFlow ? getRowMax(i3) : getRowMin(i3);
                    }
                    i2 = i9;
                    i3--;
                    z3 = true;
                } else if (this.mReversedFlow) {
                    i5 = this.mSpacing;
                    rowMax2 += i5;
                    int i92 = i2 - 1;
                    int iPrependVisibleItemToRow3 = prependVisibleItemToRow(i2, i3, rowMax2);
                    if (z2) {
                    }
                    i2 = i92;
                    i3--;
                    z3 = true;
                } else {
                    i4 = this.mSpacing;
                    i5 = -i4;
                    rowMax2 += i5;
                    int i922 = i2 - 1;
                    int iPrependVisibleItemToRow32 = prependVisibleItemToRow(i2, i3, rowMax2);
                    if (z2) {
                    }
                    i2 = i922;
                    i3--;
                    z3 = true;
                }
            } else {
                if (z) {
                    break;
                }
                rowMax = this.mReversedFlow ? findRowMax(true, null) : findRowMin(false, null);
                i3 = this.mNumRows - 1;
            }
        }
        return z3;
    }
}

package androidx.leanback.widget;

import androidx.leanback.widget.GridLayoutManager;
import androidx.leanback.widget.StaggeredGrid;
import com.samsung.android.nexus.video.VideoPlayer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class StaggeredGridDefault extends StaggeredGrid {
    /* JADX WARN: Code restructure failed: missing block: B:43:0x014e, code lost:
    
        return r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0136, code lost:
    
        return true;
     */
    @Override // androidx.leanback.widget.StaggeredGrid
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean appendVisibleItemsWithoutCache(int i, boolean z) {
        int i2;
        int i3;
        boolean z2;
        int i4;
        int i5;
        int i6;
        int count = ((GridLayoutManager.C02772) this.mProvider).getCount();
        int i7 = this.mLastVisibleIndex;
        if (i7 < 0) {
            int i8 = this.mStartIndex;
            i2 = i8 != -1 ? i8 : 0;
            i3 = (this.mLocations.size() > 0 ? getLocation(getLastIndex()).row + 1 : i2) % this.mNumRows;
            z2 = false;
            i4 = 0;
        } else {
            if (i7 < getLastIndex()) {
                return false;
            }
            int i9 = this.mLastVisibleIndex;
            i2 = i9 + 1;
            i3 = getLocation(i9).row;
            int findRowEdgeLimitSearchIndex = findRowEdgeLimitSearchIndex(true);
            if (findRowEdgeLimitSearchIndex < 0) {
                i4 = Integer.MIN_VALUE;
                for (int i10 = 0; i10 < this.mNumRows; i10++) {
                    i4 = this.mReversedFlow ? getRowMin(i10) : getRowMax(i10);
                    if (i4 != Integer.MIN_VALUE) {
                        break;
                    }
                }
            } else {
                i4 = this.mReversedFlow ? findRowMin(findRowEdgeLimitSearchIndex, false, null) : findRowMax(findRowEdgeLimitSearchIndex, true, null);
            }
            if (!this.mReversedFlow ? getRowMax(i3) >= i4 : getRowMin(i3) <= i4) {
                i3++;
                if (i3 == this.mNumRows) {
                    i4 = this.mReversedFlow ? findRowMin(false, null) : findRowMax(true, null);
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
                int rowMin = this.mReversedFlow ? getRowMin(i3) : getRowMax(i3);
                if (rowMin == Integer.MAX_VALUE || rowMin == Integer.MIN_VALUE) {
                    if (i3 == 0) {
                        rowMin = this.mReversedFlow ? getRowMin(this.mNumRows - 1) : getRowMax(this.mNumRows - 1);
                        if (rowMin != Integer.MAX_VALUE && rowMin != Integer.MIN_VALUE) {
                            if (this.mReversedFlow) {
                                i6 = this.mSpacing;
                                i5 = -i6;
                                rowMin += i5;
                            } else {
                                i5 = this.mSpacing;
                                rowMin += i5;
                            }
                        }
                    } else {
                        rowMin = this.mReversedFlow ? getRowMax(i3 - 1) : getRowMin(i3 - 1);
                    }
                } else if (this.mReversedFlow) {
                    i6 = this.mSpacing;
                    i5 = -i6;
                    rowMin += i5;
                } else {
                    i5 = this.mSpacing;
                    rowMin += i5;
                }
                int i11 = i2 + 1;
                int appendVisibleItemToRow = appendVisibleItemToRow(i2, i3, rowMin);
                if (z2) {
                    while (true) {
                        if (!this.mReversedFlow) {
                            if (rowMin + appendVisibleItemToRow >= i4) {
                                break;
                            }
                            if (i11 == count) {
                                break loop1;
                            }
                            break loop1;
                        }
                        if (rowMin - appendVisibleItemToRow <= i4) {
                            break;
                        }
                        if (i11 == count || (!z && checkAppendOverLimit(i))) {
                            break loop1;
                        }
                        rowMin += this.mReversedFlow ? (-appendVisibleItemToRow) - this.mSpacing : appendVisibleItemToRow + this.mSpacing;
                        int i12 = i11 + 1;
                        int appendVisibleItemToRow2 = appendVisibleItemToRow(i11, i3, rowMin);
                        i11 = i12;
                        appendVisibleItemToRow = appendVisibleItemToRow2;
                    }
                } else {
                    z2 = true;
                    i4 = this.mReversedFlow ? getRowMin(i3) : getRowMax(i3);
                }
                i2 = i11;
                i3++;
                z3 = true;
            } else {
                if (z) {
                    return z3;
                }
                i4 = this.mReversedFlow ? findRowMin(false, null) : findRowMax(true, null);
                i3 = 0;
            }
        }
    }

    public final int findRowEdgeLimitSearchIndex(boolean z) {
        boolean z2 = false;
        if (z) {
            for (int i = this.mLastVisibleIndex; i >= this.mFirstVisibleIndex; i--) {
                int i2 = getLocation(i).row;
                if (i2 == 0) {
                    z2 = true;
                } else if (z2 && i2 == this.mNumRows - 1) {
                    return i;
                }
            }
            return -1;
        }
        for (int i3 = this.mFirstVisibleIndex; i3 <= this.mLastVisibleIndex; i3++) {
            int i4 = getLocation(i3).row;
            if (i4 == this.mNumRows - 1) {
                z2 = true;
            } else if (z2 && i4 == 0) {
                return i3;
            }
        }
        return -1;
    }

    @Override // androidx.leanback.widget.Grid
    public final int findRowMax(int i, boolean z, int[] iArr) {
        int i2;
        int edge = ((GridLayoutManager.C02772) this.mProvider).getEdge(i);
        StaggeredGrid.Location location = getLocation(i);
        int i3 = location.row;
        if (this.mReversedFlow) {
            i2 = i3;
            int i4 = i2;
            int i5 = 1;
            int i6 = edge;
            for (int i7 = i + 1; i5 < this.mNumRows && i7 <= this.mLastVisibleIndex; i7++) {
                StaggeredGrid.Location location2 = getLocation(i7);
                i6 += location2.offset;
                int i8 = location2.row;
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
            edge = ((GridLayoutManager.C02772) this.mProvider).getSize(i) + edge;
            i2 = i10;
            for (int i12 = i - 1; i9 < this.mNumRows && i12 >= this.mFirstVisibleIndex; i12--) {
                i11 -= location3.offset;
                location3 = getLocation(i12);
                int i13 = location3.row;
                if (i13 != i10) {
                    i9++;
                    int size = ((GridLayoutManager.C02772) this.mProvider).getSize(i12) + i11;
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
    public final int findRowMin(int i, boolean z, int[] iArr) {
        int i2;
        int edge = ((GridLayoutManager.C02772) this.mProvider).getEdge(i);
        StaggeredGrid.Location location = getLocation(i);
        int i3 = location.row;
        if (this.mReversedFlow) {
            int i4 = 1;
            i2 = edge - ((GridLayoutManager.C02772) this.mProvider).getSize(i);
            int i5 = i3;
            for (int i6 = i - 1; i4 < this.mNumRows && i6 >= this.mFirstVisibleIndex; i6--) {
                edge -= location.offset;
                location = getLocation(i6);
                int i7 = location.row;
                if (i7 != i5) {
                    i4++;
                    int size = edge - ((GridLayoutManager.C02772) this.mProvider).getSize(i6);
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
                i11 += location2.offset;
                int i13 = location2.row;
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
            return VideoPlayer.MEDIA_ERROR_SYSTEM;
        }
        if (this.mReversedFlow) {
            int edge = ((GridLayoutManager.C02772) this.mProvider).getEdge(i3);
            if (getLocation(this.mFirstVisibleIndex).row == i) {
                return edge;
            }
            int i4 = this.mFirstVisibleIndex;
            do {
                i4++;
                if (i4 <= getLastIndex()) {
                    location = getLocation(i4);
                    edge += location.offset;
                }
            } while (location.row != i);
            return edge;
        }
        int edge2 = ((GridLayoutManager.C02772) this.mProvider).getEdge(this.mLastVisibleIndex);
        StaggeredGrid.Location location2 = getLocation(this.mLastVisibleIndex);
        if (location2.row == i) {
            i2 = location2.size;
        } else {
            int i5 = this.mLastVisibleIndex;
            do {
                i5--;
                if (i5 >= this.mFirstIndex) {
                    edge2 -= location2.offset;
                    location2 = getLocation(i5);
                }
            } while (location2.row != i);
            i2 = location2.size;
        }
        return edge2 + i2;
        return VideoPlayer.MEDIA_ERROR_SYSTEM;
    }

    public final int getRowMin(int i) {
        StaggeredGrid.Location location;
        int i2;
        int i3 = this.mFirstVisibleIndex;
        if (i3 < 0) {
            return Integer.MAX_VALUE;
        }
        if (!this.mReversedFlow) {
            int edge = ((GridLayoutManager.C02772) this.mProvider).getEdge(i3);
            if (getLocation(this.mFirstVisibleIndex).row == i) {
                return edge;
            }
            int i4 = this.mFirstVisibleIndex;
            do {
                i4++;
                if (i4 <= getLastIndex()) {
                    location = getLocation(i4);
                    edge += location.offset;
                }
            } while (location.row != i);
            return edge;
        }
        int edge2 = ((GridLayoutManager.C02772) this.mProvider).getEdge(this.mLastVisibleIndex);
        StaggeredGrid.Location location2 = getLocation(this.mLastVisibleIndex);
        if (location2.row == i) {
            i2 = location2.size;
        } else {
            int i5 = this.mLastVisibleIndex;
            do {
                i5--;
                if (i5 >= this.mFirstIndex) {
                    edge2 -= location2.offset;
                    location2 = getLocation(i5);
                }
            } while (location2.row != i);
            i2 = location2.size;
        }
        return edge2 - i2;
        return Integer.MAX_VALUE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x0140, code lost:
    
        return r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0128, code lost:
    
        return true;
     */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00f9 A[LOOP:2: B:54:0x00f9->B:68:0x011d, LOOP_START, PHI: r5 r8 r9
      0x00f9: PHI (r5v12 int) = (r5v6 int), (r5v17 int) binds: [B:53:0x00f7, B:68:0x011d] A[DONT_GENERATE, DONT_INLINE]
      0x00f9: PHI (r8v19 int) = (r8v17 int), (r8v20 int) binds: [B:53:0x00f7, B:68:0x011d] A[DONT_GENERATE, DONT_INLINE]
      0x00f9: PHI (r9v8 int) = (r9v6 int), (r9v10 int) binds: [B:53:0x00f7, B:68:0x011d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x012b  */
    @Override // androidx.leanback.widget.StaggeredGrid
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean prependVisibleItemsWithoutCache(int i, boolean z) {
        int i2;
        int i3;
        boolean z2;
        int i4;
        int i5;
        int i6;
        int i7 = this.mFirstVisibleIndex;
        if (i7 < 0) {
            int i8 = this.mStartIndex;
            i2 = i8 != -1 ? i8 : 0;
            i3 = (this.mLocations.size() > 0 ? (getLocation(this.mFirstIndex).row + this.mNumRows) - 1 : i2) % this.mNumRows;
            z2 = false;
            i4 = 0;
        } else {
            if (i7 > this.mFirstIndex) {
                return false;
            }
            i2 = i7 - 1;
            i3 = getLocation(i7).row;
            int findRowEdgeLimitSearchIndex = findRowEdgeLimitSearchIndex(false);
            if (findRowEdgeLimitSearchIndex < 0) {
                i3--;
                i4 = Integer.MAX_VALUE;
                for (int i9 = this.mNumRows - 1; i9 >= 0; i9--) {
                    i4 = this.mReversedFlow ? getRowMax(i9) : getRowMin(i9);
                    if (i4 != Integer.MAX_VALUE) {
                        break;
                    }
                }
            } else {
                i4 = this.mReversedFlow ? findRowMax(findRowEdgeLimitSearchIndex, true, null) : findRowMin(findRowEdgeLimitSearchIndex, false, null);
            }
            if (!this.mReversedFlow ? getRowMin(i3) <= i4 : getRowMax(i3) >= i4) {
                i3--;
                if (i3 < 0) {
                    i3 = this.mNumRows - 1;
                    i4 = this.mReversedFlow ? findRowMax(true, null) : findRowMin(false, null);
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
                int rowMax = this.mReversedFlow ? getRowMax(i3) : getRowMin(i3);
                if (rowMax == Integer.MAX_VALUE || rowMax == Integer.MIN_VALUE) {
                    if (i3 == this.mNumRows - 1) {
                        rowMax = this.mReversedFlow ? getRowMax(0) : getRowMin(0);
                        if (rowMax != Integer.MAX_VALUE && rowMax != Integer.MIN_VALUE) {
                            if (this.mReversedFlow) {
                                i6 = this.mSpacing;
                                rowMax += i6;
                            } else {
                                i5 = this.mSpacing;
                                i6 = -i5;
                                rowMax += i6;
                            }
                        }
                    } else {
                        rowMax = this.mReversedFlow ? getRowMin(i3 + 1) : getRowMax(i3 + 1);
                    }
                    int i10 = i2 - 1;
                    int prependVisibleItemToRow = prependVisibleItemToRow(i2, i3, rowMax);
                    if (z2) {
                        while (true) {
                            if (!this.mReversedFlow) {
                                if (rowMax - prependVisibleItemToRow <= i4) {
                                    break;
                                }
                                if (i10 < 0) {
                                    break loop1;
                                }
                                break loop1;
                            }
                            if (rowMax + prependVisibleItemToRow >= i4) {
                                break;
                            }
                            if (i10 < 0 || (!z && checkPrependOverLimit(i))) {
                                break loop1;
                            }
                            rowMax += this.mReversedFlow ? prependVisibleItemToRow + this.mSpacing : (-prependVisibleItemToRow) - this.mSpacing;
                            int i11 = i10 - 1;
                            int prependVisibleItemToRow2 = prependVisibleItemToRow(i10, i3, rowMax);
                            i10 = i11;
                            prependVisibleItemToRow = prependVisibleItemToRow2;
                        }
                    } else {
                        z2 = true;
                        i4 = this.mReversedFlow ? getRowMax(i3) : getRowMin(i3);
                    }
                    i2 = i10;
                    i3--;
                    z3 = true;
                } else if (this.mReversedFlow) {
                    i6 = this.mSpacing;
                    rowMax += i6;
                    int i102 = i2 - 1;
                    int prependVisibleItemToRow3 = prependVisibleItemToRow(i2, i3, rowMax);
                    if (z2) {
                    }
                    i2 = i102;
                    i3--;
                    z3 = true;
                } else {
                    i5 = this.mSpacing;
                    i6 = -i5;
                    rowMax += i6;
                    int i1022 = i2 - 1;
                    int prependVisibleItemToRow32 = prependVisibleItemToRow(i2, i3, rowMax);
                    if (z2) {
                    }
                    i2 = i1022;
                    i3--;
                    z3 = true;
                }
            } else {
                if (z) {
                    return z3;
                }
                i4 = this.mReversedFlow ? findRowMax(true, null) : findRowMin(false, null);
                i3 = this.mNumRows - 1;
            }
        }
    }
}

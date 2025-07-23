package androidx.leanback.widget;

import androidx.collection.CircularIntArray;
import androidx.leanback.widget.Grid;
import androidx.leanback.widget.GridLayoutManager;
import androidx.recyclerview.widget.GapWorker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SingleRow extends Grid {
    public final Grid.Location mTmpLocation = new Grid.Location(0);

    public SingleRow() {
        setNumRows(1);
    }

    @Override // androidx.leanback.widget.Grid
    public final boolean appendVisibleItems(int i, boolean z) {
        int min;
        int i2;
        if (this.mProvider.getCount() == 0 || (!z && checkAppendOverLimit(i))) {
            return false;
        }
        int i3 = this.mLastVisibleIndex;
        if (i3 >= 0) {
            min = i3 + 1;
        } else {
            int i4 = this.mStartIndex;
            min = i4 != -1 ? Math.min(i4, this.mProvider.getCount() - 1) : 0;
        }
        boolean z2 = false;
        while (min < this.mProvider.getCount()) {
            GridLayoutManager.AnonymousClass2 anonymousClass2 = this.mProvider;
            Object[] objArr = this.mTmpItem;
            int createItem = anonymousClass2.createItem(min, true, objArr, false);
            if (this.mFirstVisibleIndex < 0 || this.mLastVisibleIndex < 0) {
                i2 = this.mReversedFlow ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                this.mFirstVisibleIndex = min;
                this.mLastVisibleIndex = min;
            } else {
                if (this.mReversedFlow) {
                    int i5 = min - 1;
                    i2 = (this.mProvider.getEdge(i5) - this.mProvider.getSize(i5)) - this.mSpacing;
                } else {
                    int i6 = min - 1;
                    i2 = this.mSpacing + this.mProvider.getSize(i6) + this.mProvider.getEdge(i6);
                }
                this.mLastVisibleIndex = min;
            }
            this.mProvider.addItem(createItem, 0, i2, objArr[0]);
            if (z || checkAppendOverLimit(i)) {
                return true;
            }
            min++;
            z2 = true;
        }
        return z2;
    }

    @Override // androidx.leanback.widget.Grid
    public final void collectAdjacentPrefetchPositions(int i, int i2, GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl) {
        int startIndexForPrepend;
        int i3;
        if (!this.mReversedFlow ? i2 < 0 : i2 > 0) {
            if (this.mLastVisibleIndex == this.mProvider.getCount() - 1) {
                return;
            }
            int i4 = this.mLastVisibleIndex;
            if (i4 >= 0) {
                startIndexForPrepend = i4 + 1;
            } else {
                int i5 = this.mStartIndex;
                startIndexForPrepend = i5 != -1 ? Math.min(i5, this.mProvider.getCount() - 1) : 0;
            }
            int size = this.mProvider.getSize(this.mLastVisibleIndex) + this.mSpacing;
            int edge = this.mProvider.getEdge(this.mLastVisibleIndex);
            if (this.mReversedFlow) {
                size = -size;
            }
            i3 = size + edge;
        } else {
            if (this.mFirstVisibleIndex == 0) {
                return;
            }
            startIndexForPrepend = getStartIndexForPrepend();
            int edge2 = this.mProvider.getEdge(this.mFirstVisibleIndex);
            boolean z = this.mReversedFlow;
            int i6 = this.mSpacing;
            if (!z) {
                i6 = -i6;
            }
            i3 = edge2 + i6;
        }
        layoutPrefetchRegistryImpl.addPosition(startIndexForPrepend, Math.abs(i3 - i));
    }

    @Override // androidx.leanback.widget.Grid
    public final int findRowMax(int[] iArr, int i, boolean z) {
        if (iArr != null) {
            iArr[0] = 0;
            iArr[1] = i;
        }
        if (this.mReversedFlow) {
            return this.mProvider.getEdge(i);
        }
        return this.mProvider.getSize(i) + this.mProvider.getEdge(i);
    }

    @Override // androidx.leanback.widget.Grid
    public final int findRowMin(int[] iArr, int i, boolean z) {
        if (iArr != null) {
            iArr[0] = 0;
            iArr[1] = i;
        }
        return this.mReversedFlow ? this.mProvider.getEdge(i) - this.mProvider.getSize(i) : this.mProvider.getEdge(i);
    }

    @Override // androidx.leanback.widget.Grid
    public final CircularIntArray[] getItemPositionsInRows(int i, int i2) {
        CircularIntArray circularIntArray = this.mTmpItemPositionsInRows[0];
        circularIntArray.tail = 0;
        circularIntArray.addLast(i);
        this.mTmpItemPositionsInRows[0].addLast(i2);
        return this.mTmpItemPositionsInRows;
    }

    @Override // androidx.leanback.widget.Grid
    public final Grid.Location getLocation(int i) {
        return this.mTmpLocation;
    }

    public final int getStartIndexForPrepend() {
        int i = this.mFirstVisibleIndex;
        if (i >= 0) {
            return i - 1;
        }
        int i2 = this.mStartIndex;
        return i2 != -1 ? Math.min(i2, this.mProvider.getCount() - 1) : this.mProvider.getCount() - 1;
    }

    @Override // androidx.leanback.widget.Grid
    public final boolean prependVisibleItems(int i, boolean z) {
        int i2;
        if (this.mProvider.getCount() == 0 || (!z && checkPrependOverLimit(i))) {
            return false;
        }
        int i3 = GridLayoutManager.this.mPositionDeltaInPreLayout;
        boolean z2 = false;
        for (int startIndexForPrepend = getStartIndexForPrepend(); startIndexForPrepend >= i3; startIndexForPrepend--) {
            GridLayoutManager.AnonymousClass2 anonymousClass2 = this.mProvider;
            Object[] objArr = this.mTmpItem;
            int createItem = anonymousClass2.createItem(startIndexForPrepend, false, objArr, false);
            if (this.mFirstVisibleIndex < 0 || this.mLastVisibleIndex < 0) {
                i2 = this.mReversedFlow ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                this.mFirstVisibleIndex = startIndexForPrepend;
                this.mLastVisibleIndex = startIndexForPrepend;
            } else {
                i2 = this.mReversedFlow ? this.mProvider.getEdge(startIndexForPrepend + 1) + this.mSpacing + createItem : (this.mProvider.getEdge(startIndexForPrepend + 1) - this.mSpacing) - createItem;
                this.mFirstVisibleIndex = startIndexForPrepend;
            }
            this.mProvider.addItem(createItem, 0, i2, objArr[0]);
            z2 = true;
            if (z || checkPrependOverLimit(i)) {
                break;
            }
        }
        return z2;
    }
}

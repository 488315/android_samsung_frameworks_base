package androidx.leanback.widget;

import androidx.collection.CircularIntArray;
import androidx.leanback.widget.GridLayoutManager;
import androidx.recyclerview.widget.GapWorker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class Grid {
    public int mNumRows;
    public GridLayoutManager.AnonymousClass2 mProvider;
    public boolean mReversedFlow;
    public int mSpacing;
    public CircularIntArray[] mTmpItemPositionsInRows;
    public final Object[] mTmpItem = new Object[1];
    public int mFirstVisibleIndex = -1;
    public int mLastVisibleIndex = -1;
    public int mStartIndex = -1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Location {
        public final int mRow;

        public Location(int i) {
            this.mRow = i;
        }
    }

    public final boolean appendOneColumnVisibleItems() {
        return appendVisibleItems(this.mReversedFlow ? Integer.MAX_VALUE : Integer.MIN_VALUE, true);
    }

    public abstract boolean appendVisibleItems(int i, boolean z);

    public final boolean checkAppendOverLimit(int i) {
        return this.mLastVisibleIndex >= 0 && (!this.mReversedFlow ? findRowMax(false, null) < i - this.mSpacing : findRowMin(true, null) > i + this.mSpacing);
    }

    public final boolean checkPrependOverLimit(int i) {
        return this.mLastVisibleIndex >= 0 && (!this.mReversedFlow ? findRowMin(true, null) > i + this.mSpacing : findRowMax(false, null) < i - this.mSpacing);
    }

    public final int findRowMax(boolean z, int[] iArr) {
        return findRowMax(iArr, this.mReversedFlow ? this.mFirstVisibleIndex : this.mLastVisibleIndex, z);
    }

    public abstract int findRowMax(int[] iArr, int i, boolean z);

    public final int findRowMin(boolean z, int[] iArr) {
        return findRowMin(iArr, this.mReversedFlow ? this.mLastVisibleIndex : this.mFirstVisibleIndex, z);
    }

    public abstract int findRowMin(int[] iArr, int i, boolean z);

    public abstract CircularIntArray[] getItemPositionsInRows(int i, int i2);

    public abstract Location getLocation(int i);

    public void invalidateItemsAfter(int i) {
        int i2;
        if (i >= 0 && (i2 = this.mLastVisibleIndex) >= 0) {
            if (i2 >= i) {
                this.mLastVisibleIndex = i - 1;
            }
            if (this.mLastVisibleIndex < this.mFirstVisibleIndex) {
                this.mLastVisibleIndex = -1;
                this.mFirstVisibleIndex = -1;
            }
            if (this.mFirstVisibleIndex < 0) {
                this.mStartIndex = i;
            }
        }
    }

    public abstract boolean prependVisibleItems(int i, boolean z);

    public final void setNumRows(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException();
        }
        if (this.mNumRows == i) {
            return;
        }
        this.mNumRows = i;
        this.mTmpItemPositionsInRows = new CircularIntArray[i];
        for (int i2 = 0; i2 < this.mNumRows; i2++) {
            this.mTmpItemPositionsInRows[i2] = new CircularIntArray();
        }
    }

    public void collectAdjacentPrefetchPositions(int i, int i2, GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl) {
    }
}

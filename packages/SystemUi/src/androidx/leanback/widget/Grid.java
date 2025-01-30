package androidx.leanback.widget;

import androidx.collection.CircularIntArray;
import androidx.recyclerview.widget.GapWorker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class Grid {
    public int mNumRows;
    public Provider mProvider;
    public boolean mReversedFlow;
    public int mSpacing;
    public CircularIntArray[] mTmpItemPositionsInRows;
    public final Object[] mTmpItem = new Object[1];
    public int mFirstVisibleIndex = -1;
    public int mLastVisibleIndex = -1;
    public int mStartIndex = -1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class Location {
        public final int row;

        public Location(int i) {
            this.row = i;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Provider {
    }

    public abstract boolean appendVisibleItems(int i, boolean z);

    public final boolean checkAppendOverLimit(int i) {
        if (this.mLastVisibleIndex < 0) {
            return false;
        }
        if (this.mReversedFlow) {
            if (findRowMin(true, null) > i + this.mSpacing) {
                return false;
            }
        } else if (findRowMax(false, null) < i - this.mSpacing) {
            return false;
        }
        return true;
    }

    public final boolean checkPrependOverLimit(int i) {
        if (this.mLastVisibleIndex < 0) {
            return false;
        }
        if (this.mReversedFlow) {
            if (findRowMax(false, null) < i - this.mSpacing) {
                return false;
            }
        } else if (findRowMin(true, null) > i + this.mSpacing) {
            return false;
        }
        return true;
    }

    public abstract int findRowMax(int i, boolean z, int[] iArr);

    public final int findRowMax(boolean z, int[] iArr) {
        return findRowMax(this.mReversedFlow ? this.mFirstVisibleIndex : this.mLastVisibleIndex, z, iArr);
    }

    public abstract int findRowMin(int i, boolean z, int[] iArr);

    public final int findRowMin(boolean z, int[] iArr) {
        return findRowMin(this.mReversedFlow ? this.mLastVisibleIndex : this.mFirstVisibleIndex, z, iArr);
    }

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

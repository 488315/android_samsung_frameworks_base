package androidx.leanback.widget;

import androidx.collection.CircularIntArray;
import androidx.leanback.widget.Grid;
import androidx.leanback.widget.GridLayoutManager;
import androidx.recyclerview.widget.GapWorker;
import com.samsung.android.nexus.video.VideoPlayer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SingleRow extends Grid {
    public final Grid.Location mTmpLocation = new Grid.Location(0);

    public SingleRow() {
        setNumRows(1);
    }

    @Override // androidx.leanback.widget.Grid
    public final boolean appendVisibleItems(int i, boolean z) {
        int i2;
        if (((GridLayoutManager.C02772) this.mProvider).getCount() == 0) {
            return false;
        }
        if (!z && checkAppendOverLimit(i)) {
            return false;
        }
        int startIndexForAppend = getStartIndexForAppend();
        boolean z2 = false;
        while (startIndexForAppend < ((GridLayoutManager.C02772) this.mProvider).getCount()) {
            GridLayoutManager.C02772 c02772 = (GridLayoutManager.C02772) this.mProvider;
            Object[] objArr = this.mTmpItem;
            int createItem = c02772.createItem(startIndexForAppend, true, objArr, false);
            if (this.mFirstVisibleIndex < 0 || this.mLastVisibleIndex < 0) {
                i2 = this.mReversedFlow ? Integer.MAX_VALUE : VideoPlayer.MEDIA_ERROR_SYSTEM;
                this.mFirstVisibleIndex = startIndexForAppend;
                this.mLastVisibleIndex = startIndexForAppend;
            } else {
                if (this.mReversedFlow) {
                    int i3 = startIndexForAppend - 1;
                    i2 = (((GridLayoutManager.C02772) this.mProvider).getEdge(i3) - ((GridLayoutManager.C02772) this.mProvider).getSize(i3)) - this.mSpacing;
                } else {
                    int i4 = startIndexForAppend - 1;
                    i2 = this.mSpacing + ((GridLayoutManager.C02772) this.mProvider).getSize(i4) + ((GridLayoutManager.C02772) this.mProvider).getEdge(i4);
                }
                this.mLastVisibleIndex = startIndexForAppend;
            }
            ((GridLayoutManager.C02772) this.mProvider).addItem(objArr[0], createItem, 0, i2);
            if (z || checkAppendOverLimit(i)) {
                return true;
            }
            startIndexForAppend++;
            z2 = true;
        }
        return z2;
    }

    @Override // androidx.leanback.widget.Grid
    public final void collectAdjacentPrefetchPositions(int i, int i2, GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl) {
        int startIndexForPrepend;
        int i3;
        if (!this.mReversedFlow ? i2 < 0 : i2 > 0) {
            if (this.mLastVisibleIndex == ((GridLayoutManager.C02772) this.mProvider).getCount() - 1) {
                return;
            }
            startIndexForPrepend = getStartIndexForAppend();
            int size = ((GridLayoutManager.C02772) this.mProvider).getSize(this.mLastVisibleIndex) + this.mSpacing;
            int edge = ((GridLayoutManager.C02772) this.mProvider).getEdge(this.mLastVisibleIndex);
            if (this.mReversedFlow) {
                size = -size;
            }
            i3 = size + edge;
        } else {
            if (this.mFirstVisibleIndex == 0) {
                return;
            }
            startIndexForPrepend = getStartIndexForPrepend();
            int edge2 = ((GridLayoutManager.C02772) this.mProvider).getEdge(this.mFirstVisibleIndex);
            boolean z = this.mReversedFlow;
            int i4 = this.mSpacing;
            if (!z) {
                i4 = -i4;
            }
            i3 = edge2 + i4;
        }
        layoutPrefetchRegistryImpl.addPosition(startIndexForPrepend, Math.abs(i3 - i));
    }

    @Override // androidx.leanback.widget.Grid
    public final int findRowMax(int i, boolean z, int[] iArr) {
        if (iArr != null) {
            iArr[0] = 0;
            iArr[1] = i;
        }
        if (this.mReversedFlow) {
            return ((GridLayoutManager.C02772) this.mProvider).getEdge(i);
        }
        return ((GridLayoutManager.C02772) this.mProvider).getSize(i) + ((GridLayoutManager.C02772) this.mProvider).getEdge(i);
    }

    @Override // androidx.leanback.widget.Grid
    public final int findRowMin(int i, boolean z, int[] iArr) {
        if (iArr != null) {
            iArr[0] = 0;
            iArr[1] = i;
        }
        return this.mReversedFlow ? ((GridLayoutManager.C02772) this.mProvider).getEdge(i) - ((GridLayoutManager.C02772) this.mProvider).getSize(i) : ((GridLayoutManager.C02772) this.mProvider).getEdge(i);
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

    public final int getStartIndexForAppend() {
        int i = this.mLastVisibleIndex;
        if (i >= 0) {
            return i + 1;
        }
        int i2 = this.mStartIndex;
        if (i2 != -1) {
            return Math.min(i2, ((GridLayoutManager.C02772) this.mProvider).getCount() - 1);
        }
        return 0;
    }

    public final int getStartIndexForPrepend() {
        int i = this.mFirstVisibleIndex;
        if (i >= 0) {
            return i - 1;
        }
        int i2 = this.mStartIndex;
        return i2 != -1 ? Math.min(i2, ((GridLayoutManager.C02772) this.mProvider).getCount() - 1) : ((GridLayoutManager.C02772) this.mProvider).getCount() - 1;
    }

    @Override // androidx.leanback.widget.Grid
    public final boolean prependVisibleItems(int i, boolean z) {
        int i2;
        if (((GridLayoutManager.C02772) this.mProvider).getCount() == 0) {
            return false;
        }
        if (!z && checkPrependOverLimit(i)) {
            return false;
        }
        int i3 = GridLayoutManager.this.mPositionDeltaInPreLayout;
        boolean z2 = false;
        for (int startIndexForPrepend = getStartIndexForPrepend(); startIndexForPrepend >= i3; startIndexForPrepend--) {
            GridLayoutManager.C02772 c02772 = (GridLayoutManager.C02772) this.mProvider;
            Object[] objArr = this.mTmpItem;
            int createItem = c02772.createItem(startIndexForPrepend, false, objArr, false);
            if (this.mFirstVisibleIndex < 0 || this.mLastVisibleIndex < 0) {
                i2 = this.mReversedFlow ? VideoPlayer.MEDIA_ERROR_SYSTEM : Integer.MAX_VALUE;
                this.mFirstVisibleIndex = startIndexForPrepend;
                this.mLastVisibleIndex = startIndexForPrepend;
            } else {
                i2 = this.mReversedFlow ? ((GridLayoutManager.C02772) this.mProvider).getEdge(startIndexForPrepend + 1) + this.mSpacing + createItem : (((GridLayoutManager.C02772) this.mProvider).getEdge(startIndexForPrepend + 1) - this.mSpacing) - createItem;
                this.mFirstVisibleIndex = startIndexForPrepend;
            }
            ((GridLayoutManager.C02772) this.mProvider).addItem(objArr[0], createItem, 0, i2);
            z2 = true;
            if (z || checkPrependOverLimit(i)) {
                break;
            }
        }
        return z2;
    }
}

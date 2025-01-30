package androidx.leanback.widget;

import androidx.collection.CircularArray;
import androidx.collection.CircularIntArray;
import androidx.collection.CollectionPlatformUtils;
import androidx.leanback.widget.Grid;
import androidx.leanback.widget.GridLayoutManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class StaggeredGrid extends Grid {
    public Object mPendingItem;
    public int mPendingItemSize;
    public final CircularArray mLocations = new CircularArray(64);
    public int mFirstIndex = -1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Location extends Grid.Location {
        public int offset;
        public int size;

        public Location(int i, int i2, int i3) {
            super(i);
            this.offset = i2;
            this.size = i3;
        }
    }

    public final boolean appendVisbleItemsWithCache(int i, boolean z) {
        int i2;
        int i3;
        CircularArray circularArray = this.mLocations;
        if (circularArray.size() == 0) {
            return false;
        }
        int count = ((GridLayoutManager.C02772) this.mProvider).getCount();
        int i4 = this.mLastVisibleIndex;
        if (i4 >= 0) {
            i2 = i4 + 1;
            i3 = ((GridLayoutManager.C02772) this.mProvider).getEdge(i4);
        } else {
            int i5 = this.mStartIndex;
            i2 = i5 != -1 ? i5 : 0;
            if (i2 > getLastIndex() + 1 || i2 < this.mFirstIndex) {
                circularArray.removeFromStart(circularArray.size());
                return false;
            }
            if (i2 > getLastIndex()) {
                return false;
            }
            i3 = Integer.MAX_VALUE;
        }
        int lastIndex = getLastIndex();
        while (i2 < count && i2 <= lastIndex) {
            Location location = getLocation(i2);
            if (i3 != Integer.MAX_VALUE) {
                i3 += location.offset;
            }
            int i6 = location.row;
            GridLayoutManager.C02772 c02772 = (GridLayoutManager.C02772) this.mProvider;
            Object[] objArr = this.mTmpItem;
            int createItem = c02772.createItem(i2, true, objArr, false);
            if (createItem != location.size) {
                location.size = createItem;
                circularArray.removeFromEnd(lastIndex - i2);
                lastIndex = i2;
            }
            this.mLastVisibleIndex = i2;
            if (this.mFirstVisibleIndex < 0) {
                this.mFirstVisibleIndex = i2;
            }
            ((GridLayoutManager.C02772) this.mProvider).addItem(objArr[0], createItem, i6, i3);
            if (!z && checkAppendOverLimit(i)) {
                return true;
            }
            if (i3 == Integer.MAX_VALUE) {
                i3 = ((GridLayoutManager.C02772) this.mProvider).getEdge(i2);
            }
            if (i6 == this.mNumRows - 1 && z) {
                return true;
            }
            i2++;
        }
        return false;
    }

    public final int appendVisibleItemToRow(int i, int i2, int i3) {
        int edge;
        boolean z;
        int i4 = this.mLastVisibleIndex;
        if (i4 >= 0 && (i4 != getLastIndex() || this.mLastVisibleIndex != i - 1)) {
            throw new IllegalStateException();
        }
        int i5 = this.mLastVisibleIndex;
        CircularArray circularArray = this.mLocations;
        if (i5 >= 0) {
            edge = i3 - ((GridLayoutManager.C02772) this.mProvider).getEdge(i5);
        } else if (circularArray.size() <= 0 || i != getLastIndex() + 1) {
            edge = 0;
        } else {
            int lastIndex = getLastIndex();
            while (true) {
                if (lastIndex < this.mFirstIndex) {
                    z = false;
                    break;
                }
                if (getLocation(lastIndex).row == i2) {
                    z = true;
                    break;
                }
                lastIndex--;
            }
            if (!z) {
                lastIndex = getLastIndex();
            }
            edge = this.mReversedFlow ? (-getLocation(lastIndex).size) - this.mSpacing : getLocation(lastIndex).size + this.mSpacing;
            for (int i6 = lastIndex + 1; i6 <= getLastIndex(); i6++) {
                edge -= getLocation(i6).offset;
            }
        }
        Location location = new Location(i2, edge, 0);
        Object[] objArr = circularArray.elements;
        int i7 = circularArray.tail;
        objArr[i7] = location;
        int i8 = circularArray.capacityBitmask & (i7 + 1);
        circularArray.tail = i8;
        if (i8 == circularArray.head) {
            circularArray.doubleCapacity();
        }
        Object obj = this.mPendingItem;
        if (obj != null) {
            location.size = this.mPendingItemSize;
            this.mPendingItem = null;
        } else {
            GridLayoutManager.C02772 c02772 = (GridLayoutManager.C02772) this.mProvider;
            Object[] objArr2 = this.mTmpItem;
            location.size = c02772.createItem(i, true, objArr2, false);
            obj = objArr2[0];
        }
        if (circularArray.size() == 1) {
            this.mLastVisibleIndex = i;
            this.mFirstVisibleIndex = i;
            this.mFirstIndex = i;
        } else {
            int i9 = this.mLastVisibleIndex;
            if (i9 < 0) {
                this.mLastVisibleIndex = i;
                this.mFirstVisibleIndex = i;
            } else {
                this.mLastVisibleIndex = i9 + 1;
            }
        }
        ((GridLayoutManager.C02772) this.mProvider).addItem(obj, location.size, i2, i3);
        return location.size;
    }

    @Override // androidx.leanback.widget.Grid
    public final boolean appendVisibleItems(int i, boolean z) {
        Object[] objArr = this.mTmpItem;
        if (((GridLayoutManager.C02772) this.mProvider).getCount() == 0) {
            return false;
        }
        if (!z && checkAppendOverLimit(i)) {
            return false;
        }
        try {
            if (!appendVisbleItemsWithCache(i, z)) {
                return appendVisibleItemsWithoutCache(i, z);
            }
            objArr[0] = null;
            this.mPendingItem = null;
            return true;
        } finally {
            objArr[0] = null;
            this.mPendingItem = null;
        }
    }

    public abstract boolean appendVisibleItemsWithoutCache(int i, boolean z);

    @Override // androidx.leanback.widget.Grid
    public final CircularIntArray[] getItemPositionsInRows(int i, int i2) {
        for (int i3 = 0; i3 < this.mNumRows; i3++) {
            this.mTmpItemPositionsInRows[i3].tail = 0;
        }
        if (i >= 0) {
            while (i <= i2) {
                CircularIntArray circularIntArray = this.mTmpItemPositionsInRows[getLocation(i).row];
                int i4 = circularIntArray.tail;
                int i5 = circularIntArray.capacityBitmask;
                if (((i4 + 0) & i5) > 0) {
                    if (i4 == 0) {
                        int i6 = CollectionPlatformUtils.$r8$clinit;
                        throw new ArrayIndexOutOfBoundsException();
                    }
                    int i7 = i5 & (i4 - 1);
                    if (circularIntArray.elements[i7] == i - 1) {
                        if (i4 == 0) {
                            int i8 = CollectionPlatformUtils.$r8$clinit;
                            throw new ArrayIndexOutOfBoundsException();
                        }
                        circularIntArray.tail = i7;
                        circularIntArray.addLast(i);
                        i++;
                    }
                }
                circularIntArray.addLast(i);
                circularIntArray.addLast(i);
                i++;
            }
        }
        return this.mTmpItemPositionsInRows;
    }

    public final int getLastIndex() {
        return (this.mLocations.size() + this.mFirstIndex) - 1;
    }

    @Override // androidx.leanback.widget.Grid
    public final void invalidateItemsAfter(int i) {
        super.invalidateItemsAfter(i);
        int lastIndex = (getLastIndex() - i) + 1;
        CircularArray circularArray = this.mLocations;
        circularArray.removeFromEnd(lastIndex);
        if (circularArray.size() == 0) {
            this.mFirstIndex = -1;
        }
    }

    public final boolean prependVisbleItemsWithCache(int i, boolean z) {
        int i2;
        int i3;
        int i4;
        CircularArray circularArray = this.mLocations;
        if (circularArray.size() == 0) {
            return false;
        }
        int i5 = this.mFirstVisibleIndex;
        if (i5 < 0) {
            int i6 = this.mStartIndex;
            i2 = i6 != -1 ? i6 : 0;
            if (i2 <= getLastIndex()) {
                int i7 = this.mFirstIndex;
                if (i2 >= i7 - 1) {
                    if (i2 < i7) {
                        return false;
                    }
                    i3 = Integer.MAX_VALUE;
                    i4 = 0;
                }
            }
            circularArray.removeFromStart(circularArray.size());
            return false;
        }
        i3 = ((GridLayoutManager.C02772) this.mProvider).getEdge(i5);
        i4 = getLocation(this.mFirstVisibleIndex).offset;
        i2 = this.mFirstVisibleIndex - 1;
        int max = Math.max(GridLayoutManager.this.mPositionDeltaInPreLayout, this.mFirstIndex);
        while (i2 >= max) {
            Location location = getLocation(i2);
            int i8 = location.row;
            GridLayoutManager.C02772 c02772 = (GridLayoutManager.C02772) this.mProvider;
            Object[] objArr = this.mTmpItem;
            int createItem = c02772.createItem(i2, false, objArr, false);
            if (createItem != location.size) {
                circularArray.removeFromStart((i2 + 1) - this.mFirstIndex);
                this.mFirstIndex = this.mFirstVisibleIndex;
                this.mPendingItem = objArr[0];
                this.mPendingItemSize = createItem;
                return false;
            }
            this.mFirstVisibleIndex = i2;
            if (this.mLastVisibleIndex < 0) {
                this.mLastVisibleIndex = i2;
            }
            ((GridLayoutManager.C02772) this.mProvider).addItem(objArr[0], createItem, i8, i3 - i4);
            if (!z && checkPrependOverLimit(i)) {
                return true;
            }
            i3 = ((GridLayoutManager.C02772) this.mProvider).getEdge(i2);
            i4 = location.offset;
            if (i8 == 0 && z) {
                return true;
            }
            i2--;
        }
        return false;
    }

    public final int prependVisibleItemToRow(int i, int i2, int i3) {
        int i4 = this.mFirstVisibleIndex;
        if (i4 >= 0 && (i4 != this.mFirstIndex || i4 != i + 1)) {
            throw new IllegalStateException();
        }
        int i5 = this.mFirstIndex;
        Location location = i5 >= 0 ? getLocation(i5) : null;
        int edge = ((GridLayoutManager.C02772) this.mProvider).getEdge(this.mFirstIndex);
        Location location2 = new Location(i2, 0, 0);
        CircularArray circularArray = this.mLocations;
        int i6 = (circularArray.head - 1) & circularArray.capacityBitmask;
        circularArray.head = i6;
        circularArray.elements[i6] = location2;
        if (i6 == circularArray.tail) {
            circularArray.doubleCapacity();
        }
        Object obj = this.mPendingItem;
        if (obj != null) {
            location2.size = this.mPendingItemSize;
            this.mPendingItem = null;
        } else {
            GridLayoutManager.C02772 c02772 = (GridLayoutManager.C02772) this.mProvider;
            Object[] objArr = this.mTmpItem;
            location2.size = c02772.createItem(i, false, objArr, false);
            obj = objArr[0];
        }
        this.mFirstVisibleIndex = i;
        this.mFirstIndex = i;
        if (this.mLastVisibleIndex < 0) {
            this.mLastVisibleIndex = i;
        }
        int i7 = !this.mReversedFlow ? i3 - location2.size : i3 + location2.size;
        if (location != null) {
            location.offset = edge - i7;
        }
        ((GridLayoutManager.C02772) this.mProvider).addItem(obj, location2.size, i2, i7);
        return location2.size;
    }

    @Override // androidx.leanback.widget.Grid
    public final boolean prependVisibleItems(int i, boolean z) {
        Object[] objArr = this.mTmpItem;
        if (((GridLayoutManager.C02772) this.mProvider).getCount() == 0) {
            return false;
        }
        if (!z && checkPrependOverLimit(i)) {
            return false;
        }
        try {
            if (!prependVisbleItemsWithCache(i, z)) {
                return prependVisibleItemsWithoutCache(i, z);
            }
            objArr[0] = null;
            this.mPendingItem = null;
            return true;
        } finally {
            objArr[0] = null;
            this.mPendingItem = null;
        }
    }

    public abstract boolean prependVisibleItemsWithoutCache(int i, boolean z);

    @Override // androidx.leanback.widget.Grid
    public final Location getLocation(int i) {
        int i2 = i - this.mFirstIndex;
        if (i2 < 0) {
            return null;
        }
        CircularArray circularArray = this.mLocations;
        if (i2 >= circularArray.size()) {
            return null;
        }
        if (i2 < 0) {
            circularArray.getClass();
        } else if (i2 < circularArray.size()) {
            Object obj = circularArray.elements[circularArray.capacityBitmask & (circularArray.head + i2)];
            Intrinsics.checkNotNull(obj);
            return (Location) obj;
        }
        int i3 = CollectionPlatformUtils.$r8$clinit;
        throw new ArrayIndexOutOfBoundsException();
    }
}

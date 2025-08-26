package androidx.leanback.widget;

import androidx.collection.CircularArray;
import androidx.collection.CircularIntArray;
import androidx.collection.CollectionPlatformUtils;
import androidx.leanback.widget.Grid;
import androidx.leanback.widget.GridLayoutManager;

/* loaded from: classes.dex */
public abstract class StaggeredGrid extends Grid {
    public Object mPendingItem;
    public int mPendingItemSize;
    public final CircularArray mLocations = new CircularArray(64);
    public int mFirstIndex = -1;

    public class Location extends Grid.Location {
        public int mOffset;
        public int mSize;

        public Location(int i, int i2, int i3) {
            super(i);
            this.mOffset = i2;
            this.mSize = i3;
        }
    }

    public final boolean appendVisbleItemsWithCache(int i, boolean z) {
        int i2;
        int edge;
        CircularArray circularArray = this.mLocations;
        if (circularArray.size() != 0) {
            int count = this.mProvider.getCount();
            int i3 = this.mLastVisibleIndex;
            if (i3 >= 0) {
                i2 = i3 + 1;
                edge = this.mProvider.getEdge(i3);
            } else {
                int i4 = this.mStartIndex;
                i2 = i4 != -1 ? i4 : 0;
                if (i2 > getLastIndex() + 1 || i2 < this.mFirstIndex) {
                    circularArray.removeFromStart(circularArray.size());
                    return false;
                }
                if (i2 <= getLastIndex()) {
                    edge = Integer.MAX_VALUE;
                }
            }
            int lastIndex = getLastIndex();
            while (i2 < count && i2 <= lastIndex) {
                Location location = getLocation(i2);
                if (edge != Integer.MAX_VALUE) {
                    edge += location.mOffset;
                }
                int i5 = location.mRow;
                GridLayoutManager.AnonymousClass2 anonymousClass2 = this.mProvider;
                Object[] objArr = this.mTmpItem;
                int iCreateItem = anonymousClass2.createItem(i2, true, objArr, false);
                if (iCreateItem != location.mSize) {
                    location.mSize = iCreateItem;
                    circularArray.removeFromEnd(lastIndex - i2);
                    lastIndex = i2;
                }
                this.mLastVisibleIndex = i2;
                if (this.mFirstVisibleIndex < 0) {
                    this.mFirstVisibleIndex = i2;
                }
                this.mProvider.addItem(iCreateItem, i5, edge, objArr[0]);
                if (z || !checkAppendOverLimit(i)) {
                    if (edge == Integer.MAX_VALUE) {
                        edge = this.mProvider.getEdge(i2);
                    }
                    if (i5 != this.mNumRows - 1 || !z) {
                        i2++;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public final int appendVisibleItemToRow(int i, int i2, int i3) {
        int edge;
        int i4 = this.mLastVisibleIndex;
        if (i4 >= 0 && (i4 != getLastIndex() || this.mLastVisibleIndex != i - 1)) {
            throw new IllegalStateException();
        }
        int i5 = this.mLastVisibleIndex;
        CircularArray circularArray = this.mLocations;
        if (i5 >= 0) {
            edge = i3 - this.mProvider.getEdge(i5);
        } else if (circularArray.size() <= 0 || i != getLastIndex() + 1) {
            edge = 0;
        } else {
            int lastIndex = getLastIndex();
            while (true) {
                if (lastIndex < this.mFirstIndex) {
                    lastIndex = getLastIndex();
                    break;
                }
                if (getLocation(lastIndex).mRow == i2) {
                    break;
                }
                lastIndex--;
            }
            edge = this.mReversedFlow ? (-getLocation(lastIndex).mSize) - this.mSpacing : getLocation(lastIndex).mSize + this.mSpacing;
            for (int i6 = lastIndex + 1; i6 <= getLastIndex(); i6++) {
                edge -= getLocation(i6).mOffset;
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
            location.mSize = this.mPendingItemSize;
            this.mPendingItem = null;
        } else {
            GridLayoutManager.AnonymousClass2 anonymousClass2 = this.mProvider;
            Object[] objArr2 = this.mTmpItem;
            location.mSize = anonymousClass2.createItem(i, true, objArr2, false);
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
        this.mProvider.addItem(location.mSize, i2, i3, obj);
        return location.mSize;
    }

    @Override // androidx.leanback.widget.Grid
    public final boolean appendVisibleItems(int i, boolean z) {
        Object[] objArr = this.mTmpItem;
        if (this.mProvider.getCount() == 0 || (!z && checkAppendOverLimit(i))) {
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

    /* JADX WARN: Removed duplicated region for block: B:19:0x004a  */
    @Override // androidx.leanback.widget.Grid
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final CircularIntArray[] getItemPositionsInRows(int i, int i2) {
        for (int i3 = 0; i3 < this.mNumRows; i3++) {
            this.mTmpItemPositionsInRows[i3].tail = 0;
        }
        if (i >= 0) {
            while (i <= i2) {
                CircularIntArray circularIntArray = this.mTmpItemPositionsInRows[getLocation(i).mRow];
                int i4 = circularIntArray.tail;
                int i5 = circularIntArray.capacityBitmask;
                if ((i4 & i5) <= 0) {
                    circularIntArray.addLast(i);
                    circularIntArray.addLast(i);
                } else {
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
                    }
                }
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
        int edge;
        int i3;
        CircularArray circularArray = this.mLocations;
        if (circularArray.size() != 0) {
            int i4 = this.mFirstVisibleIndex;
            if (i4 < 0) {
                int i5 = this.mStartIndex;
                i2 = i5 != -1 ? i5 : 0;
                if (i2 <= getLastIndex()) {
                    int i6 = this.mFirstIndex;
                    if (i2 >= i6 - 1) {
                        if (i2 >= i6) {
                            edge = Integer.MAX_VALUE;
                            i3 = 0;
                        }
                    }
                }
                circularArray.removeFromStart(circularArray.size());
                return false;
            }
            edge = this.mProvider.getEdge(i4);
            i3 = getLocation(this.mFirstVisibleIndex).mOffset;
            i2 = this.mFirstVisibleIndex - 1;
            int iMax = Math.max(GridLayoutManager.this.mPositionDeltaInPreLayout, this.mFirstIndex);
            while (i2 >= iMax) {
                Location location = getLocation(i2);
                int i7 = location.mRow;
                GridLayoutManager.AnonymousClass2 anonymousClass2 = this.mProvider;
                Object[] objArr = this.mTmpItem;
                int iCreateItem = anonymousClass2.createItem(i2, false, objArr, false);
                if (iCreateItem != location.mSize) {
                    circularArray.removeFromStart((i2 + 1) - this.mFirstIndex);
                    this.mFirstIndex = this.mFirstVisibleIndex;
                    this.mPendingItem = objArr[0];
                    this.mPendingItemSize = iCreateItem;
                    return false;
                }
                this.mFirstVisibleIndex = i2;
                if (this.mLastVisibleIndex < 0) {
                    this.mLastVisibleIndex = i2;
                }
                this.mProvider.addItem(iCreateItem, i7, edge - i3, objArr[0]);
                if (z || !checkPrependOverLimit(i)) {
                    edge = this.mProvider.getEdge(i2);
                    i3 = location.mOffset;
                    if (i7 != 0 || !z) {
                        i2--;
                    }
                }
                return true;
            }
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
        int edge = this.mProvider.getEdge(this.mFirstIndex);
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
            location2.mSize = this.mPendingItemSize;
            this.mPendingItem = null;
        } else {
            GridLayoutManager.AnonymousClass2 anonymousClass2 = this.mProvider;
            Object[] objArr = this.mTmpItem;
            location2.mSize = anonymousClass2.createItem(i, false, objArr, false);
            obj = objArr[0];
        }
        this.mFirstVisibleIndex = i;
        this.mFirstIndex = i;
        if (this.mLastVisibleIndex < 0) {
            this.mLastVisibleIndex = i;
        }
        int i7 = !this.mReversedFlow ? i3 - location2.mSize : i3 + location2.mSize;
        if (location != null) {
            location.mOffset = edge - i7;
        }
        this.mProvider.addItem(location2.mSize, i2, i7, obj);
        return location2.mSize;
    }

    @Override // androidx.leanback.widget.Grid
    public final boolean prependVisibleItems(int i, boolean z) {
        Object[] objArr = this.mTmpItem;
        if (this.mProvider.getCount() == 0 || (!z && checkPrependOverLimit(i))) {
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
        if (i2 < 0 || i2 >= circularArray.size()) {
            int i3 = CollectionPlatformUtils.$r8$clinit;
            throw new ArrayIndexOutOfBoundsException();
        }
        Object obj = circularArray.elements[circularArray.capacityBitmask & (circularArray.head + i2)];
        obj.getClass();
        return (Location) obj;
    }
}

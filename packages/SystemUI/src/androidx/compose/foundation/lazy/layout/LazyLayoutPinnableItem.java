package androidx.compose.foundation.lazy.layout;

import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutPinnedItemList;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.layout.PinnableContainer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LazyLayoutPinnableItem implements PinnableContainer, PinnableContainer.PinnedHandle, LazyLayoutPinnedItemList.PinnedItem {
    public boolean isDisposed;
    public final Object key;
    public PinnableContainer.PinnedHandle parentHandle;
    public final LazyLayoutPinnedItemList pinnedItemList;
    public int pinsCount;
    public int index = -1;
    public final MutableState _parentPinnableContainer$delegate = SnapshotStateKt.mutableStateOf$default(null);

    public LazyLayoutPinnableItem(Object obj, LazyLayoutPinnedItemList lazyLayoutPinnedItemList) {
        this.key = obj;
        this.pinnedItemList = lazyLayoutPinnedItemList;
    }

    @Override // androidx.compose.ui.layout.PinnableContainer
    public final PinnableContainer.PinnedHandle pin() {
        LazyLayoutPinnableItem lazyLayoutPinnableItem;
        if (this.isDisposed) {
            InlineClassHelperKt.throwIllegalStateException("Pin should not be called on an already disposed item ");
        }
        if (this.pinsCount == 0) {
            this.pinnedItemList.items.add(this);
            PinnableContainer pinnableContainer = (PinnableContainer) ((SnapshotMutableStateImpl) this._parentPinnableContainer$delegate).getValue();
            if (pinnableContainer != null) {
                lazyLayoutPinnableItem = (LazyLayoutPinnableItem) pinnableContainer;
                lazyLayoutPinnableItem.pin();
            } else {
                lazyLayoutPinnableItem = null;
            }
            this.parentHandle = lazyLayoutPinnableItem;
        }
        this.pinsCount++;
        return this;
    }

    @Override // androidx.compose.ui.layout.PinnableContainer.PinnedHandle
    public final void release() {
        if (this.isDisposed) {
            return;
        }
        if (this.pinsCount <= 0) {
            InlineClassHelperKt.throwIllegalStateException("Release should only be called once");
        }
        int i = this.pinsCount - 1;
        this.pinsCount = i;
        if (i == 0) {
            this.pinnedItemList.items.remove(this);
            PinnableContainer.PinnedHandle pinnedHandle = this.parentHandle;
            if (pinnedHandle != null) {
                ((LazyLayoutPinnableItem) pinnedHandle).release();
            }
            this.parentHandle = null;
        }
    }
}

package androidx.recyclerview.widget;

import androidx.core.util.Pools$SimplePool;
import androidx.recyclerview.widget.OpReorderer;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class AdapterHelper implements OpReorderer.Callback {
    public final Callback mCallback;
    public final boolean mDisableRecycler;
    public int mExistingUpdateTypes;
    public final OpReorderer mOpReorderer;
    public final ArrayList mPendingUpdates;
    public final ArrayList mPostponedList;
    public final Pools$SimplePool mUpdateOpPool;

    public interface Callback {
    }

    public final class UpdateOp {
        public int cmd;
        public int itemCount;
        public Object payload;
        public int positionStart;

        public UpdateOp(int i, int i2, int i3, Object obj) {
            this.cmd = i;
            this.positionStart = i2;
            this.itemCount = i3;
            this.payload = obj;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof UpdateOp)) {
                return false;
            }
            UpdateOp updateOp = (UpdateOp) obj;
            int i = this.cmd;
            if (i != updateOp.cmd) {
                return false;
            }
            if (i == 8 && Math.abs(this.itemCount - this.positionStart) == 1 && this.itemCount == updateOp.positionStart && this.positionStart == updateOp.itemCount) {
                return true;
            }
            if (this.itemCount != updateOp.itemCount || this.positionStart != updateOp.positionStart) {
                return false;
            }
            Object obj2 = this.payload;
            if (obj2 != null) {
                if (!obj2.equals(updateOp.payload)) {
                    return false;
                }
            } else if (updateOp.payload != null) {
                return false;
            }
            return true;
        }

        public final int hashCode() {
            return (((this.cmd * 31) + this.positionStart) * 31) + this.itemCount;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append("[");
            int i = this.cmd;
            sb.append(i != 1 ? i != 2 ? i != 4 ? i != 8 ? "??" : "mv" : "up" : "rm" : "add");
            sb.append(",s:");
            sb.append(this.positionStart);
            sb.append("c:");
            sb.append(this.itemCount);
            sb.append(",p:");
            sb.append(this.payload);
            sb.append("]");
            return sb.toString();
        }
    }

    public AdapterHelper(Callback callback) {
        this(callback, false);
    }

    public final boolean canFindInPreLayout(int i) {
        int size = this.mPostponedList.size();
        for (int i2 = 0; i2 < size; i2++) {
            UpdateOp updateOp = (UpdateOp) this.mPostponedList.get(i2);
            int i3 = updateOp.cmd;
            if (i3 == 8) {
                if (findPositionOffset(updateOp.itemCount, i2 + 1) == i) {
                    return true;
                }
            } else if (i3 == 1) {
                int i4 = updateOp.positionStart;
                int i5 = updateOp.itemCount + i4;
                while (i4 < i5) {
                    if (findPositionOffset(i4, i2 + 1) == i) {
                        return true;
                    }
                    i4++;
                }
            } else {
                continue;
            }
        }
        return false;
    }

    public final void consumePostponedUpdates() {
        int size = this.mPostponedList.size();
        for (int i = 0; i < size; i++) {
            ((RecyclerView.AnonymousClass11) this.mCallback).dispatchUpdate((UpdateOp) this.mPostponedList.get(i));
        }
        recycleUpdateOpsAndClearList(this.mPostponedList);
        this.mExistingUpdateTypes = 0;
    }

    public final void consumeUpdatesInOnePass() {
        consumePostponedUpdates();
        int size = this.mPendingUpdates.size();
        for (int i = 0; i < size; i++) {
            UpdateOp updateOp = (UpdateOp) this.mPendingUpdates.get(i);
            int i2 = updateOp.cmd;
            Callback callback = this.mCallback;
            if (i2 == 1) {
                RecyclerView.AnonymousClass11 anonymousClass11 = (RecyclerView.AnonymousClass11) callback;
                anonymousClass11.dispatchUpdate(updateOp);
                anonymousClass11.offsetPositionsForAdd(updateOp.positionStart, updateOp.itemCount);
            } else if (i2 == 2) {
                RecyclerView.AnonymousClass11 anonymousClass112 = (RecyclerView.AnonymousClass11) callback;
                anonymousClass112.dispatchUpdate(updateOp);
                int i3 = updateOp.positionStart;
                int i4 = updateOp.itemCount;
                RecyclerView recyclerView = RecyclerView.this;
                recyclerView.offsetPositionRecordsForRemove(i3, i4, true);
                recyclerView.mItemsAddedOrRemoved = true;
                recyclerView.mState.mDeletedInvisibleItemCountSincePreviousLayout += i4;
            } else if (i2 == 4) {
                RecyclerView.AnonymousClass11 anonymousClass113 = (RecyclerView.AnonymousClass11) callback;
                anonymousClass113.dispatchUpdate(updateOp);
                anonymousClass113.markViewHoldersUpdated(updateOp.positionStart, updateOp.itemCount, updateOp.payload);
            } else if (i2 == 8) {
                RecyclerView.AnonymousClass11 anonymousClass114 = (RecyclerView.AnonymousClass11) callback;
                anonymousClass114.dispatchUpdate(updateOp);
                anonymousClass114.offsetPositionsForMove(updateOp.positionStart, updateOp.itemCount);
            }
        }
        recycleUpdateOpsAndClearList(this.mPendingUpdates);
        this.mExistingUpdateTypes = 0;
    }

    public final void dispatchAndUpdateViewHolders(UpdateOp updateOp) {
        int i;
        int i2 = updateOp.cmd;
        if (i2 == 1 || i2 == 8) {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int iUpdatePositionWithPostponed = updatePositionWithPostponed(updateOp.positionStart, i2);
        int i3 = updateOp.positionStart;
        int i4 = updateOp.cmd;
        if (i4 == 2) {
            i = 0;
        } else {
            if (i4 != 4) {
                throw new IllegalArgumentException("op should be remove or update." + updateOp);
            }
            i = 1;
        }
        int i5 = 1;
        for (int i6 = 1; i6 < updateOp.itemCount; i6++) {
            int iUpdatePositionWithPostponed2 = updatePositionWithPostponed((i * i6) + updateOp.positionStart, updateOp.cmd);
            int i7 = updateOp.cmd;
            if (i7 == 2 ? iUpdatePositionWithPostponed2 != iUpdatePositionWithPostponed : !(i7 == 4 && iUpdatePositionWithPostponed2 == iUpdatePositionWithPostponed + 1)) {
                UpdateOp updateOpObtainUpdateOp = obtainUpdateOp(i7, iUpdatePositionWithPostponed, i5, updateOp.payload);
                dispatchFirstPassAndUpdateViewHolders(updateOpObtainUpdateOp, i3);
                recycleUpdateOp(updateOpObtainUpdateOp);
                if (updateOp.cmd == 4) {
                    i3 += i5;
                }
                i5 = 1;
                iUpdatePositionWithPostponed = iUpdatePositionWithPostponed2;
            } else {
                i5++;
            }
        }
        Object obj = updateOp.payload;
        recycleUpdateOp(updateOp);
        if (i5 > 0) {
            UpdateOp updateOpObtainUpdateOp2 = obtainUpdateOp(updateOp.cmd, iUpdatePositionWithPostponed, i5, obj);
            dispatchFirstPassAndUpdateViewHolders(updateOpObtainUpdateOp2, i3);
            recycleUpdateOp(updateOpObtainUpdateOp2);
        }
    }

    public final void dispatchFirstPassAndUpdateViewHolders(UpdateOp updateOp, int i) {
        RecyclerView.AnonymousClass11 anonymousClass11 = (RecyclerView.AnonymousClass11) this.mCallback;
        anonymousClass11.dispatchUpdate(updateOp);
        int i2 = updateOp.cmd;
        if (i2 != 2) {
            if (i2 != 4) {
                throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
            }
            anonymousClass11.markViewHoldersUpdated(i, updateOp.itemCount, updateOp.payload);
        } else {
            int i3 = updateOp.itemCount;
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.offsetPositionRecordsForRemove(i, i3, true);
            recyclerView.mItemsAddedOrRemoved = true;
            recyclerView.mState.mDeletedInvisibleItemCountSincePreviousLayout += i3;
        }
    }

    public final int findPositionOffset(int i, int i2) {
        int size = this.mPostponedList.size();
        while (i2 < size) {
            UpdateOp updateOp = (UpdateOp) this.mPostponedList.get(i2);
            int i3 = updateOp.cmd;
            if (i3 == 8) {
                int i4 = updateOp.positionStart;
                if (i4 == i) {
                    i = updateOp.itemCount;
                } else {
                    if (i4 < i) {
                        i--;
                    }
                    if (updateOp.itemCount <= i) {
                        i++;
                    }
                }
            } else {
                int i5 = updateOp.positionStart;
                if (i5 > i) {
                    continue;
                } else if (i3 == 2) {
                    int i6 = updateOp.itemCount;
                    if (i < i5 + i6) {
                        return -1;
                    }
                    i -= i6;
                } else if (i3 == 1) {
                    i += updateOp.itemCount;
                }
            }
            i2++;
        }
        return i;
    }

    public final boolean hasPendingUpdates() {
        return this.mPendingUpdates.size() > 0;
    }

    public final UpdateOp obtainUpdateOp(int i, int i2, int i3, Object obj) {
        UpdateOp updateOp = (UpdateOp) this.mUpdateOpPool.acquire();
        if (updateOp == null) {
            return new UpdateOp(i, i2, i3, obj);
        }
        updateOp.cmd = i;
        updateOp.positionStart = i2;
        updateOp.itemCount = i3;
        updateOp.payload = obj;
        return updateOp;
    }

    public final void postponeAndUpdateViewHolders(UpdateOp updateOp) {
        this.mPostponedList.add(updateOp);
        int i = updateOp.cmd;
        Callback callback = this.mCallback;
        if (i == 1) {
            ((RecyclerView.AnonymousClass11) callback).offsetPositionsForAdd(updateOp.positionStart, updateOp.itemCount);
            return;
        }
        if (i == 2) {
            int i2 = updateOp.positionStart;
            int i3 = updateOp.itemCount;
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.offsetPositionRecordsForRemove(i2, i3, false);
            recyclerView.mItemsAddedOrRemoved = true;
            return;
        }
        if (i == 4) {
            ((RecyclerView.AnonymousClass11) callback).markViewHoldersUpdated(updateOp.positionStart, updateOp.itemCount, updateOp.payload);
        } else if (i == 8) {
            ((RecyclerView.AnonymousClass11) callback).offsetPositionsForMove(updateOp.positionStart, updateOp.itemCount);
        } else {
            throw new IllegalArgumentException("Unknown update op type for " + updateOp);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:194:0x01b4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0118  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void preProcess() {
        int i;
        UpdateOp updateOpObtainUpdateOp;
        UpdateOp updateOp;
        boolean z;
        char c;
        UpdateOp updateOp2;
        boolean z2;
        boolean z3;
        int i2;
        int i3;
        int i4;
        UpdateOp updateOpObtainUpdateOp2;
        int i5;
        int i6;
        int i7 = -1;
        ArrayList arrayList = this.mPendingUpdates;
        OpReorderer opReorderer = this.mOpReorderer;
        opReorderer.getClass();
        while (true) {
            int size = arrayList.size() - 1;
            boolean z4 = false;
            while (true) {
                i = 8;
                if (size < 0) {
                    size = i7;
                    break;
                }
                if (((UpdateOp) arrayList.get(size)).cmd != 8) {
                    z4 = true;
                } else if (z4) {
                    break;
                }
                size += i7;
            }
            updateOpObtainUpdateOp = null;
            if (size == i7) {
                break;
            }
            int i8 = size + 1;
            UpdateOp updateOp3 = (UpdateOp) arrayList.get(size);
            UpdateOp updateOp4 = (UpdateOp) arrayList.get(i8);
            int i9 = updateOp4.cmd;
            if (i9 != 1) {
                OpReorderer.Callback callback = opReorderer.mCallback;
                if (i9 == 2) {
                    int i10 = updateOp3.positionStart;
                    int i11 = updateOp3.itemCount;
                    if (i10 < i11) {
                        if (updateOp4.positionStart == i10 && updateOp4.itemCount == i11 - i10) {
                            z3 = true;
                            z2 = false;
                            i2 = updateOp4.positionStart;
                            if (i11 >= i2) {
                            }
                            i3 = updateOp3.positionStart;
                            i4 = updateOp4.positionStart;
                            if (i3 > i4) {
                            }
                            if (z3) {
                            }
                        } else {
                            z2 = false;
                            z3 = false;
                            i2 = updateOp4.positionStart;
                            if (i11 >= i2) {
                                updateOp4.positionStart = i2 - 1;
                            } else {
                                int i12 = updateOp4.itemCount;
                                if (i11 < i2 + i12) {
                                    updateOp4.itemCount = i12 - 1;
                                    updateOp3.cmd = 2;
                                    updateOp3.itemCount = 1;
                                    if (updateOp4.itemCount == 0) {
                                        arrayList.remove(i8);
                                        ((AdapterHelper) callback).recycleUpdateOp(updateOp4);
                                    }
                                }
                            }
                            i3 = updateOp3.positionStart;
                            i4 = updateOp4.positionStart;
                            if (i3 > i4) {
                                updateOp4.positionStart = i4 + 1;
                            } else {
                                int i13 = i4 + updateOp4.itemCount;
                                if (i3 < i13) {
                                    updateOpObtainUpdateOp = ((AdapterHelper) callback).obtainUpdateOp(2, i3 + 1, i13 - i3, null);
                                    updateOp4.itemCount = updateOp3.positionStart - updateOp4.positionStart;
                                }
                            }
                            if (z3) {
                                if (z2) {
                                    if (updateOpObtainUpdateOp != null) {
                                        int i14 = updateOp3.positionStart;
                                        if (i14 > updateOpObtainUpdateOp.positionStart) {
                                            updateOp3.positionStart = i14 - updateOpObtainUpdateOp.itemCount;
                                        }
                                        int i15 = updateOp3.itemCount;
                                        if (i15 > updateOpObtainUpdateOp.positionStart) {
                                            updateOp3.itemCount = i15 - updateOpObtainUpdateOp.itemCount;
                                        }
                                    }
                                    int i16 = updateOp3.positionStart;
                                    if (i16 > updateOp4.positionStart) {
                                        updateOp3.positionStart = i16 - updateOp4.itemCount;
                                    }
                                    int i17 = updateOp3.itemCount;
                                    if (i17 > updateOp4.positionStart) {
                                        updateOp3.itemCount = i17 - updateOp4.itemCount;
                                    }
                                } else {
                                    if (updateOpObtainUpdateOp != null) {
                                        int i18 = updateOp3.positionStart;
                                        if (i18 >= updateOpObtainUpdateOp.positionStart) {
                                            updateOp3.positionStart = i18 - updateOpObtainUpdateOp.itemCount;
                                        }
                                        int i19 = updateOp3.itemCount;
                                        if (i19 >= updateOpObtainUpdateOp.positionStart) {
                                            updateOp3.itemCount = i19 - updateOpObtainUpdateOp.itemCount;
                                        }
                                    }
                                    int i20 = updateOp3.positionStart;
                                    if (i20 >= updateOp4.positionStart) {
                                        updateOp3.positionStart = i20 - updateOp4.itemCount;
                                    }
                                    int i21 = updateOp3.itemCount;
                                    if (i21 >= updateOp4.positionStart) {
                                        updateOp3.itemCount = i21 - updateOp4.itemCount;
                                    }
                                }
                                arrayList.set(size, updateOp4);
                                if (updateOp3.positionStart != updateOp3.itemCount) {
                                    arrayList.set(i8, updateOp3);
                                } else {
                                    arrayList.remove(i8);
                                }
                                if (updateOpObtainUpdateOp != null) {
                                    arrayList.add(size, updateOpObtainUpdateOp);
                                }
                            } else {
                                arrayList.set(size, updateOp4);
                                arrayList.remove(i8);
                                ((AdapterHelper) callback).recycleUpdateOp(updateOp3);
                            }
                        }
                    } else if (updateOp4.positionStart == i11 + 1 && updateOp4.itemCount == i10 - i11) {
                        z2 = true;
                        z3 = true;
                        i2 = updateOp4.positionStart;
                        if (i11 >= i2) {
                        }
                        i3 = updateOp3.positionStart;
                        i4 = updateOp4.positionStart;
                        if (i3 > i4) {
                        }
                        if (z3) {
                        }
                    } else {
                        z2 = true;
                        z3 = false;
                        i2 = updateOp4.positionStart;
                        if (i11 >= i2) {
                        }
                        i3 = updateOp3.positionStart;
                        i4 = updateOp4.positionStart;
                        if (i3 > i4) {
                        }
                        if (z3) {
                        }
                    }
                } else if (i9 == 4) {
                    int i22 = updateOp3.itemCount;
                    int i23 = updateOp4.positionStart;
                    if (i22 < i23) {
                        updateOp4.positionStart = i23 - 1;
                    } else {
                        int i24 = updateOp4.itemCount;
                        if (i22 < i23 + i24) {
                            updateOp4.itemCount = i24 - 1;
                            updateOpObtainUpdateOp2 = ((AdapterHelper) callback).obtainUpdateOp(4, updateOp3.positionStart, 1, updateOp4.payload);
                        }
                        i5 = updateOp3.positionStart;
                        i6 = updateOp4.positionStart;
                        if (i5 > i6) {
                            updateOp4.positionStart = i6 + 1;
                        } else {
                            int i25 = i6 + updateOp4.itemCount;
                            if (i5 < i25) {
                                int i26 = i25 - i5;
                                updateOpObtainUpdateOp = ((AdapterHelper) callback).obtainUpdateOp(4, i5 + 1, i26, updateOp4.payload);
                                updateOp4.itemCount -= i26;
                            }
                        }
                        arrayList.set(i8, updateOp3);
                        if (updateOp4.itemCount <= 0) {
                            arrayList.set(size, updateOp4);
                        } else {
                            arrayList.remove(size);
                            ((AdapterHelper) callback).recycleUpdateOp(updateOp4);
                        }
                        if (updateOpObtainUpdateOp2 != null) {
                            arrayList.add(size, updateOpObtainUpdateOp2);
                        }
                        if (updateOpObtainUpdateOp == null) {
                            arrayList.add(size, updateOpObtainUpdateOp);
                        }
                    }
                    updateOpObtainUpdateOp2 = null;
                    i5 = updateOp3.positionStart;
                    i6 = updateOp4.positionStart;
                    if (i5 > i6) {
                    }
                    arrayList.set(i8, updateOp3);
                    if (updateOp4.itemCount <= 0) {
                    }
                    if (updateOpObtainUpdateOp2 != null) {
                    }
                    if (updateOpObtainUpdateOp == null) {
                    }
                }
            } else {
                int i27 = updateOp3.itemCount;
                int i28 = updateOp4.positionStart;
                int i29 = i27 < i28 ? -1 : 0;
                int i30 = updateOp3.positionStart;
                if (i30 < i28) {
                    i29++;
                }
                if (i28 <= i30) {
                    updateOp3.positionStart = i30 + updateOp4.itemCount;
                }
                int i31 = updateOp4.positionStart;
                if (i31 <= i27) {
                    updateOp3.itemCount = i27 + updateOp4.itemCount;
                }
                updateOp4.positionStart = i31 + i29;
                arrayList.set(size, updateOp4);
                arrayList.set(i8, updateOp3);
            }
            i7 = -1;
        }
        int size2 = this.mPendingUpdates.size();
        int i32 = 0;
        while (i32 < size2) {
            UpdateOp updateOpObtainUpdateOp3 = (UpdateOp) this.mPendingUpdates.get(i32);
            int i33 = updateOpObtainUpdateOp3.cmd;
            if (i33 != 1) {
                Callback callback2 = this.mCallback;
                if (i33 == 2) {
                    int i34 = updateOpObtainUpdateOp3.positionStart;
                    int i35 = updateOpObtainUpdateOp3.itemCount + i34;
                    int i36 = i34;
                    int i37 = 0;
                    char c2 = 65535;
                    while (i36 < i35) {
                        RecyclerView recyclerView = RecyclerView.this;
                        RecyclerView.ViewHolder viewHolderFindViewHolderForPosition = recyclerView.findViewHolderForPosition(i36, true);
                        if (viewHolderFindViewHolderForPosition == null) {
                            viewHolderFindViewHolderForPosition = null;
                        } else if (recyclerView.mChildHelper.isHidden(viewHolderFindViewHolderForPosition.itemView)) {
                            int[] iArr = RecyclerView.NESTED_SCROLLING_ATTRS;
                            viewHolderFindViewHolderForPosition = null;
                        }
                        if (viewHolderFindViewHolderForPosition != null || canFindInPreLayout(i36)) {
                            if (c2 == 0) {
                                dispatchAndUpdateViewHolders(obtainUpdateOp(2, i34, i37, null));
                                z = true;
                            } else {
                                z = false;
                            }
                            c = 1;
                        } else {
                            if (c2 == 1) {
                                postponeAndUpdateViewHolders(obtainUpdateOp(2, i34, i37, null));
                                z = true;
                            } else {
                                z = false;
                            }
                            c = 0;
                        }
                        if (z) {
                            i36 -= i37;
                            i35 -= i37;
                            i37 = 1;
                        } else {
                            i37++;
                        }
                        i36++;
                        c2 = c;
                    }
                    if (i37 != updateOpObtainUpdateOp3.itemCount) {
                        recycleUpdateOp(updateOpObtainUpdateOp3);
                        updateOp = null;
                        updateOpObtainUpdateOp3 = obtainUpdateOp(2, i34, i37, null);
                    } else {
                        updateOp = null;
                    }
                    if (c2 == 0) {
                        dispatchAndUpdateViewHolders(updateOpObtainUpdateOp3);
                    } else {
                        postponeAndUpdateViewHolders(updateOpObtainUpdateOp3);
                    }
                } else if (i33 != 4) {
                    if (i33 == i) {
                        postponeAndUpdateViewHolders(updateOpObtainUpdateOp3);
                    }
                    updateOp = updateOpObtainUpdateOp;
                } else {
                    int i38 = updateOpObtainUpdateOp3.positionStart;
                    int i39 = updateOpObtainUpdateOp3.itemCount + i38;
                    int i40 = i38;
                    int i41 = 0;
                    char c3 = 65535;
                    while (i38 < i39) {
                        RecyclerView recyclerView2 = RecyclerView.this;
                        RecyclerView.ViewHolder viewHolderFindViewHolderForPosition2 = recyclerView2.findViewHolderForPosition(i38, true);
                        if (viewHolderFindViewHolderForPosition2 == 0) {
                            updateOp2 = updateOpObtainUpdateOp;
                        } else {
                            boolean zIsHidden = recyclerView2.mChildHelper.isHidden(viewHolderFindViewHolderForPosition2.itemView);
                            updateOp2 = viewHolderFindViewHolderForPosition2;
                            if (zIsHidden) {
                                int[] iArr2 = RecyclerView.NESTED_SCROLLING_ATTRS;
                                updateOp2 = null;
                            }
                        }
                        if (updateOp2 != null || canFindInPreLayout(i38)) {
                            if (c3 == 0) {
                                dispatchAndUpdateViewHolders(obtainUpdateOp(4, i40, i41, updateOpObtainUpdateOp3.payload));
                                i40 = i38;
                                i41 = 0;
                            }
                            c3 = 1;
                        } else {
                            if (c3 == 1) {
                                postponeAndUpdateViewHolders(obtainUpdateOp(4, i40, i41, updateOpObtainUpdateOp3.payload));
                                i40 = i38;
                                i41 = 0;
                            }
                            c3 = 0;
                        }
                        i41++;
                        i38++;
                        updateOpObtainUpdateOp = null;
                    }
                    if (i41 != updateOpObtainUpdateOp3.itemCount) {
                        Object obj = updateOpObtainUpdateOp3.payload;
                        recycleUpdateOp(updateOpObtainUpdateOp3);
                        updateOpObtainUpdateOp3 = obtainUpdateOp(4, i40, i41, obj);
                    }
                    if (c3 == 0) {
                        dispatchAndUpdateViewHolders(updateOpObtainUpdateOp3);
                    } else {
                        postponeAndUpdateViewHolders(updateOpObtainUpdateOp3);
                    }
                    updateOp = null;
                }
            } else {
                updateOp = updateOpObtainUpdateOp;
                postponeAndUpdateViewHolders(updateOpObtainUpdateOp3);
            }
            i32++;
            updateOpObtainUpdateOp = updateOp;
            i = 8;
        }
        this.mPendingUpdates.clear();
    }

    public final void recycleUpdateOp(UpdateOp updateOp) {
        if (this.mDisableRecycler) {
            return;
        }
        updateOp.payload = null;
        this.mUpdateOpPool.release(updateOp);
    }

    public final void recycleUpdateOpsAndClearList(List list) {
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            recycleUpdateOp((UpdateOp) arrayList.get(i));
        }
        arrayList.clear();
    }

    public final int updatePositionWithPostponed(int i, int i2) {
        int i3;
        int i4;
        for (int size = this.mPostponedList.size() - 1; size >= 0; size--) {
            UpdateOp updateOp = (UpdateOp) this.mPostponedList.get(size);
            int i5 = updateOp.cmd;
            if (i5 == 8) {
                int i6 = updateOp.positionStart;
                int i7 = updateOp.itemCount;
                if (i6 < i7) {
                    i4 = i6;
                    i3 = i7;
                } else {
                    i3 = i6;
                    i4 = i7;
                }
                if (i < i4 || i > i3) {
                    if (i < i6) {
                        if (i2 == 1) {
                            updateOp.positionStart = i6 + 1;
                            updateOp.itemCount = i7 + 1;
                        } else if (i2 == 2) {
                            updateOp.positionStart = i6 - 1;
                            updateOp.itemCount = i7 - 1;
                        }
                    }
                } else if (i4 == i6) {
                    if (i2 == 1) {
                        updateOp.itemCount = i7 + 1;
                    } else if (i2 == 2) {
                        updateOp.itemCount = i7 - 1;
                    }
                    i++;
                } else {
                    if (i2 == 1) {
                        updateOp.positionStart = i6 + 1;
                    } else if (i2 == 2) {
                        updateOp.positionStart = i6 - 1;
                    }
                    i--;
                }
            } else {
                int i8 = updateOp.positionStart;
                if (i8 <= i) {
                    if (i5 == 1) {
                        i -= updateOp.itemCount;
                    } else if (i5 == 2) {
                        i += updateOp.itemCount;
                    }
                } else if (i2 == 1) {
                    updateOp.positionStart = i8 + 1;
                } else if (i2 == 2) {
                    updateOp.positionStart = i8 - 1;
                }
            }
        }
        for (int size2 = this.mPostponedList.size() - 1; size2 >= 0; size2--) {
            UpdateOp updateOp2 = (UpdateOp) this.mPostponedList.get(size2);
            if (updateOp2.cmd == 8) {
                int i9 = updateOp2.itemCount;
                if (i9 == updateOp2.positionStart || i9 < 0) {
                    this.mPostponedList.remove(size2);
                    recycleUpdateOp(updateOp2);
                }
            } else if (updateOp2.itemCount <= 0) {
                this.mPostponedList.remove(size2);
                recycleUpdateOp(updateOp2);
            }
        }
        return i;
    }

    public AdapterHelper(Callback callback, boolean z) {
        this.mUpdateOpPool = new Pools$SimplePool(30);
        this.mPendingUpdates = new ArrayList();
        this.mPostponedList = new ArrayList();
        this.mExistingUpdateTypes = 0;
        this.mCallback = callback;
        this.mDisableRecycler = z;
        this.mOpReorderer = new OpReorderer(this);
    }
}

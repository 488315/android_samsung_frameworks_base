package androidx.recyclerview.widget;

import androidx.core.util.Pools$SimplePool;
import androidx.recyclerview.widget.OpReorderer;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AdapterHelper implements OpReorderer.Callback {
    public final Callback mCallback;
    public final boolean mDisableRecycler;
    public int mExistingUpdateTypes;
    public final OpReorderer mOpReorderer;
    public final ArrayList mPendingUpdates;
    public final ArrayList mPostponedList;
    public final Pools$SimplePool mUpdateOpPool;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        ArrayList arrayList = this.mPostponedList;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            UpdateOp updateOp = (UpdateOp) arrayList.get(i2);
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
        ArrayList arrayList = this.mPostponedList;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((RecyclerView.C045611) this.mCallback).dispatchUpdate((UpdateOp) arrayList.get(i));
        }
        recycleUpdateOpsAndClearList(arrayList);
        this.mExistingUpdateTypes = 0;
    }

    public final void consumeUpdatesInOnePass() {
        consumePostponedUpdates();
        ArrayList arrayList = this.mPendingUpdates;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            UpdateOp updateOp = (UpdateOp) arrayList.get(i);
            int i2 = updateOp.cmd;
            Callback callback = this.mCallback;
            if (i2 == 1) {
                RecyclerView.C045611 c045611 = (RecyclerView.C045611) callback;
                c045611.dispatchUpdate(updateOp);
                c045611.offsetPositionsForAdd(updateOp.positionStart, updateOp.itemCount);
            } else if (i2 == 2) {
                RecyclerView.C045611 c0456112 = (RecyclerView.C045611) callback;
                c0456112.dispatchUpdate(updateOp);
                int i3 = updateOp.positionStart;
                int i4 = updateOp.itemCount;
                RecyclerView recyclerView = RecyclerView.this;
                recyclerView.offsetPositionRecordsForRemove(i3, i4, true);
                recyclerView.mItemsAddedOrRemoved = true;
                recyclerView.mState.mDeletedInvisibleItemCountSincePreviousLayout += i4;
            } else if (i2 == 4) {
                RecyclerView.C045611 c0456113 = (RecyclerView.C045611) callback;
                c0456113.dispatchUpdate(updateOp);
                c0456113.markViewHoldersUpdated(updateOp.positionStart, updateOp.itemCount, updateOp.payload);
            } else if (i2 == 8) {
                RecyclerView.C045611 c0456114 = (RecyclerView.C045611) callback;
                c0456114.dispatchUpdate(updateOp);
                c0456114.offsetPositionsForMove(updateOp.positionStart, updateOp.itemCount);
            }
        }
        recycleUpdateOpsAndClearList(arrayList);
        this.mExistingUpdateTypes = 0;
    }

    public final void dispatchAndUpdateViewHolders(UpdateOp updateOp) {
        int i;
        int i2 = updateOp.cmd;
        if (i2 == 1 || i2 == 8) {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int updatePositionWithPostponed = updatePositionWithPostponed(updateOp.positionStart, i2);
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
            int updatePositionWithPostponed2 = updatePositionWithPostponed((i * i6) + updateOp.positionStart, updateOp.cmd);
            int i7 = updateOp.cmd;
            if (i7 == 2 ? updatePositionWithPostponed2 == updatePositionWithPostponed : i7 == 4 && updatePositionWithPostponed2 == updatePositionWithPostponed + 1) {
                i5++;
            } else {
                UpdateOp obtainUpdateOp = obtainUpdateOp(i7, updatePositionWithPostponed, i5, updateOp.payload);
                dispatchFirstPassAndUpdateViewHolders(obtainUpdateOp, i3);
                recycleUpdateOp(obtainUpdateOp);
                if (updateOp.cmd == 4) {
                    i3 += i5;
                }
                i5 = 1;
                updatePositionWithPostponed = updatePositionWithPostponed2;
            }
        }
        Object obj = updateOp.payload;
        recycleUpdateOp(updateOp);
        if (i5 > 0) {
            UpdateOp obtainUpdateOp2 = obtainUpdateOp(updateOp.cmd, updatePositionWithPostponed, i5, obj);
            dispatchFirstPassAndUpdateViewHolders(obtainUpdateOp2, i3);
            recycleUpdateOp(obtainUpdateOp2);
        }
    }

    public final void dispatchFirstPassAndUpdateViewHolders(UpdateOp updateOp, int i) {
        RecyclerView.C045611 c045611 = (RecyclerView.C045611) this.mCallback;
        c045611.dispatchUpdate(updateOp);
        int i2 = updateOp.cmd;
        if (i2 != 2) {
            if (i2 != 4) {
                throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
            }
            c045611.markViewHoldersUpdated(i, updateOp.itemCount, updateOp.payload);
        } else {
            int i3 = updateOp.itemCount;
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.offsetPositionRecordsForRemove(i, i3, true);
            recyclerView.mItemsAddedOrRemoved = true;
            recyclerView.mState.mDeletedInvisibleItemCountSincePreviousLayout += i3;
        }
    }

    public final int findPositionOffset(int i, int i2) {
        ArrayList arrayList = this.mPostponedList;
        int size = arrayList.size();
        while (i2 < size) {
            UpdateOp updateOp = (UpdateOp) arrayList.get(i2);
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
            ((RecyclerView.C045611) callback).offsetPositionsForAdd(updateOp.positionStart, updateOp.itemCount);
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
            ((RecyclerView.C045611) callback).markViewHoldersUpdated(updateOp.positionStart, updateOp.itemCount, updateOp.payload);
        } else if (i == 8) {
            ((RecyclerView.C045611) callback).offsetPositionsForMove(updateOp.positionStart, updateOp.itemCount);
        } else {
            throw new IllegalArgumentException("Unknown update op type for " + updateOp);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:118:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x00a1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0009 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x011f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0112 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x00cf A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void preProcess() {
        int i;
        char c;
        int i2;
        int i3;
        boolean z;
        char c2;
        UpdateOp obtainUpdateOp;
        int i4;
        int i5;
        boolean z2;
        boolean z3;
        int i6;
        int i7;
        int i8;
        ArrayList arrayList = this.mPendingUpdates;
        OpReorderer opReorderer = this.mOpReorderer;
        opReorderer.getClass();
        while (true) {
            int size = arrayList.size() - 1;
            boolean z4 = false;
            while (true) {
                i = 8;
                c = 65535;
                if (size < 0) {
                    size = -1;
                    break;
                }
                if (((UpdateOp) arrayList.get(size)).cmd != 8) {
                    z4 = true;
                } else if (z4) {
                    break;
                }
                size--;
            }
            UpdateOp updateOp = null;
            i2 = 2;
            if (size == -1) {
                break;
            }
            int i9 = size + 1;
            UpdateOp updateOp2 = (UpdateOp) arrayList.get(size);
            UpdateOp updateOp3 = (UpdateOp) arrayList.get(i9);
            int i10 = updateOp3.cmd;
            if (i10 != 1) {
                OpReorderer.Callback callback = opReorderer.mCallback;
                if (i10 == 2) {
                    int i11 = updateOp2.positionStart;
                    int i12 = updateOp2.itemCount;
                    if (i11 < i12) {
                        if (updateOp3.positionStart == i11 && updateOp3.itemCount == i12 - i11) {
                            z3 = true;
                            z2 = false;
                            i6 = updateOp3.positionStart;
                            if (i12 >= i6) {
                            }
                            i7 = updateOp2.positionStart;
                            i8 = updateOp3.positionStart;
                            if (i7 > i8) {
                            }
                            if (z3) {
                            }
                        } else {
                            z3 = false;
                            z2 = z3;
                            i6 = updateOp3.positionStart;
                            if (i12 >= i6) {
                                updateOp3.positionStart = i6 - 1;
                            } else {
                                int i13 = updateOp3.itemCount;
                                if (i12 < i6 + i13) {
                                    updateOp3.itemCount = i13 - 1;
                                    updateOp2.cmd = 2;
                                    updateOp2.itemCount = 1;
                                    if (updateOp3.itemCount == 0) {
                                        arrayList.remove(i9);
                                        ((AdapterHelper) callback).recycleUpdateOp(updateOp3);
                                    }
                                }
                            }
                            i7 = updateOp2.positionStart;
                            i8 = updateOp3.positionStart;
                            if (i7 > i8) {
                                updateOp3.positionStart = i8 + 1;
                            } else {
                                int i14 = i8 + updateOp3.itemCount;
                                if (i7 < i14) {
                                    updateOp = ((AdapterHelper) callback).obtainUpdateOp(2, i7 + 1, i14 - i7, null);
                                    updateOp3.itemCount = updateOp2.positionStart - updateOp3.positionStart;
                                }
                            }
                            if (z3) {
                                if (z2) {
                                    if (updateOp != null) {
                                        int i15 = updateOp2.positionStart;
                                        if (i15 > updateOp.positionStart) {
                                            updateOp2.positionStart = i15 - updateOp.itemCount;
                                        }
                                        int i16 = updateOp2.itemCount;
                                        if (i16 > updateOp.positionStart) {
                                            updateOp2.itemCount = i16 - updateOp.itemCount;
                                        }
                                    }
                                    int i17 = updateOp2.positionStart;
                                    if (i17 > updateOp3.positionStart) {
                                        updateOp2.positionStart = i17 - updateOp3.itemCount;
                                    }
                                    int i18 = updateOp2.itemCount;
                                    if (i18 > updateOp3.positionStart) {
                                        updateOp2.itemCount = i18 - updateOp3.itemCount;
                                    }
                                } else {
                                    if (updateOp != null) {
                                        int i19 = updateOp2.positionStart;
                                        if (i19 >= updateOp.positionStart) {
                                            updateOp2.positionStart = i19 - updateOp.itemCount;
                                        }
                                        int i20 = updateOp2.itemCount;
                                        if (i20 >= updateOp.positionStart) {
                                            updateOp2.itemCount = i20 - updateOp.itemCount;
                                        }
                                    }
                                    int i21 = updateOp2.positionStart;
                                    if (i21 >= updateOp3.positionStart) {
                                        updateOp2.positionStart = i21 - updateOp3.itemCount;
                                    }
                                    int i22 = updateOp2.itemCount;
                                    if (i22 >= updateOp3.positionStart) {
                                        updateOp2.itemCount = i22 - updateOp3.itemCount;
                                    }
                                }
                                arrayList.set(size, updateOp3);
                                if (updateOp2.positionStart != updateOp2.itemCount) {
                                    arrayList.set(i9, updateOp2);
                                } else {
                                    arrayList.remove(i9);
                                }
                                if (updateOp != null) {
                                    arrayList.add(size, updateOp);
                                }
                            } else {
                                arrayList.set(size, updateOp3);
                                arrayList.remove(i9);
                                ((AdapterHelper) callback).recycleUpdateOp(updateOp2);
                            }
                        }
                    } else if (updateOp3.positionStart == i12 + 1 && updateOp3.itemCount == i11 - i12) {
                        z3 = true;
                        z2 = z3;
                        i6 = updateOp3.positionStart;
                        if (i12 >= i6) {
                        }
                        i7 = updateOp2.positionStart;
                        i8 = updateOp3.positionStart;
                        if (i7 > i8) {
                        }
                        if (z3) {
                        }
                    } else {
                        z2 = true;
                        z3 = false;
                        i6 = updateOp3.positionStart;
                        if (i12 >= i6) {
                        }
                        i7 = updateOp2.positionStart;
                        i8 = updateOp3.positionStart;
                        if (i7 > i8) {
                        }
                        if (z3) {
                        }
                    }
                } else if (i10 == 4) {
                    int i23 = updateOp2.itemCount;
                    int i24 = updateOp3.positionStart;
                    if (i23 < i24) {
                        updateOp3.positionStart = i24 - 1;
                    } else {
                        int i25 = updateOp3.itemCount;
                        if (i23 < i24 + i25) {
                            updateOp3.itemCount = i25 - 1;
                            obtainUpdateOp = ((AdapterHelper) callback).obtainUpdateOp(4, updateOp2.positionStart, 1, updateOp3.payload);
                            i4 = updateOp2.positionStart;
                            i5 = updateOp3.positionStart;
                            if (i4 > i5) {
                                updateOp3.positionStart = i5 + 1;
                            } else {
                                int i26 = i5 + updateOp3.itemCount;
                                if (i4 < i26) {
                                    int i27 = i26 - i4;
                                    updateOp = ((AdapterHelper) callback).obtainUpdateOp(4, i4 + 1, i27, updateOp3.payload);
                                    updateOp3.itemCount -= i27;
                                }
                            }
                            arrayList.set(i9, updateOp2);
                            if (updateOp3.itemCount <= 0) {
                                arrayList.set(size, updateOp3);
                            } else {
                                arrayList.remove(size);
                                ((AdapterHelper) callback).recycleUpdateOp(updateOp3);
                            }
                            if (obtainUpdateOp != null) {
                                arrayList.add(size, obtainUpdateOp);
                            }
                            if (updateOp == null) {
                                arrayList.add(size, updateOp);
                            }
                        }
                    }
                    obtainUpdateOp = null;
                    i4 = updateOp2.positionStart;
                    i5 = updateOp3.positionStart;
                    if (i4 > i5) {
                    }
                    arrayList.set(i9, updateOp2);
                    if (updateOp3.itemCount <= 0) {
                    }
                    if (obtainUpdateOp != null) {
                    }
                    if (updateOp == null) {
                    }
                }
            } else {
                int i28 = updateOp2.itemCount;
                int i29 = updateOp3.positionStart;
                int i30 = i28 < i29 ? -1 : 0;
                int i31 = updateOp2.positionStart;
                if (i31 < i29) {
                    i30++;
                }
                if (i29 <= i31) {
                    updateOp2.positionStart = i31 + updateOp3.itemCount;
                }
                int i32 = updateOp3.positionStart;
                if (i32 <= i28) {
                    updateOp2.itemCount = i28 + updateOp3.itemCount;
                }
                updateOp3.positionStart = i32 + i30;
                arrayList.set(size, updateOp3);
                arrayList.set(i9, updateOp2);
            }
        }
        int size2 = arrayList.size();
        int i33 = 0;
        while (i33 < size2) {
            UpdateOp updateOp4 = (UpdateOp) arrayList.get(i33);
            int i34 = updateOp4.cmd;
            if (i34 != 1) {
                Callback callback2 = this.mCallback;
                if (i34 == i2) {
                    int i35 = updateOp4.positionStart;
                    int i36 = updateOp4.itemCount + i35;
                    int i37 = i35;
                    int i38 = 0;
                    char c3 = 65535;
                    while (i37 < i36) {
                        RecyclerView recyclerView = RecyclerView.this;
                        RecyclerView.ViewHolder findViewHolderForPosition = recyclerView.findViewHolderForPosition(i37, true);
                        if (findViewHolderForPosition == null || recyclerView.mChildHelper.isHidden(findViewHolderForPosition.itemView)) {
                            findViewHolderForPosition = null;
                        }
                        if (findViewHolderForPosition != null || canFindInPreLayout(i37)) {
                            if (c3 == 0) {
                                dispatchAndUpdateViewHolders(obtainUpdateOp(2, i35, i38, null));
                                z = true;
                            } else {
                                z = false;
                            }
                            c2 = 1;
                        } else {
                            if (c3 == 1) {
                                postponeAndUpdateViewHolders(obtainUpdateOp(2, i35, i38, null));
                                z = true;
                            } else {
                                z = false;
                            }
                            c2 = 0;
                        }
                        if (z) {
                            i37 -= i38;
                            i36 -= i38;
                            i38 = 1;
                        } else {
                            i38++;
                        }
                        i37++;
                        c3 = c2;
                    }
                    if (i38 != updateOp4.itemCount) {
                        recycleUpdateOp(updateOp4);
                        i3 = 2;
                        updateOp4 = obtainUpdateOp(2, i35, i38, null);
                    } else {
                        i3 = 2;
                    }
                    if (c3 == 0) {
                        dispatchAndUpdateViewHolders(updateOp4);
                    } else {
                        postponeAndUpdateViewHolders(updateOp4);
                    }
                } else if (i34 != 4) {
                    if (i34 == i) {
                        postponeAndUpdateViewHolders(updateOp4);
                    }
                    i3 = i2;
                } else {
                    int i39 = updateOp4.positionStart;
                    int i40 = updateOp4.itemCount + i39;
                    char c4 = c;
                    int i41 = i39;
                    int i42 = 0;
                    while (i39 < i40) {
                        RecyclerView recyclerView2 = RecyclerView.this;
                        RecyclerView.ViewHolder findViewHolderForPosition2 = recyclerView2.findViewHolderForPosition(i39, true);
                        if (findViewHolderForPosition2 == null || recyclerView2.mChildHelper.isHidden(findViewHolderForPosition2.itemView)) {
                            findViewHolderForPosition2 = null;
                        }
                        if (findViewHolderForPosition2 != null || canFindInPreLayout(i39)) {
                            if (c4 == 0) {
                                dispatchAndUpdateViewHolders(obtainUpdateOp(4, i41, i42, updateOp4.payload));
                                i41 = i39;
                                i42 = 0;
                            }
                            c4 = 1;
                        } else {
                            if (c4 == 1) {
                                postponeAndUpdateViewHolders(obtainUpdateOp(4, i41, i42, updateOp4.payload));
                                i41 = i39;
                                i42 = 0;
                            }
                            c4 = 0;
                        }
                        i42++;
                        i39++;
                    }
                    if (i42 != updateOp4.itemCount) {
                        Object obj = updateOp4.payload;
                        recycleUpdateOp(updateOp4);
                        updateOp4 = obtainUpdateOp(4, i41, i42, obj);
                    }
                    if (c4 == 0) {
                        dispatchAndUpdateViewHolders(updateOp4);
                    } else {
                        postponeAndUpdateViewHolders(updateOp4);
                    }
                    i3 = 2;
                }
            } else {
                i3 = i2;
                postponeAndUpdateViewHolders(updateOp4);
            }
            i33++;
            i2 = i3;
            i = 8;
            c = 65535;
        }
        arrayList.clear();
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
        ArrayList arrayList = this.mPostponedList;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            UpdateOp updateOp = (UpdateOp) arrayList.get(size);
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
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            UpdateOp updateOp2 = (UpdateOp) arrayList.get(size2);
            if (updateOp2.cmd == 8) {
                int i9 = updateOp2.itemCount;
                if (i9 == updateOp2.positionStart || i9 < 0) {
                    arrayList.remove(size2);
                    recycleUpdateOp(updateOp2);
                }
            } else if (updateOp2.itemCount <= 0) {
                arrayList.remove(size2);
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

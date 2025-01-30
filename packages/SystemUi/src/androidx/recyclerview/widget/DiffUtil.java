package androidx.recyclerview.widget;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DiffUtil {
    public static final C04391 DIAGONAL_COMPARATOR = new Comparator() { // from class: androidx.recyclerview.widget.DiffUtil.1
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((Diagonal) obj).f175x - ((Diagonal) obj2).f175x;
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Callback {
        public abstract boolean areContentsTheSame(int i, int i2);

        public abstract boolean areItemsTheSame(int i, int i2);

        public Object getChangePayload(int i, int i2) {
            return null;
        }

        public abstract int getNewListSize();

        public abstract int getOldListSize();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CenteredArray {
        public final int[] mData;
        public final int mMid;

        public CenteredArray(int i) {
            int[] iArr = new int[i];
            this.mData = iArr;
            this.mMid = iArr.length / 2;
        }

        public final int get(int i) {
            return this.mData[i + this.mMid];
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Diagonal {
        public final int size;

        /* renamed from: x */
        public final int f175x;

        /* renamed from: y */
        public final int f176y;

        public Diagonal(int i, int i2, int i3) {
            this.f175x = i;
            this.f176y = i2;
            this.size = i3;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DiffResult {
        public final Callback mCallback;
        public final boolean mDetectMoves;
        public final List mDiagonals;
        public final int[] mNewItemStatuses;
        public final int mNewListSize;
        public final int[] mOldItemStatuses;
        public final int mOldListSize;

        public DiffResult(Callback callback, List<Diagonal> list, int[] iArr, int[] iArr2, boolean z) {
            Callback callback2;
            int[] iArr3;
            int[] iArr4;
            int i;
            Diagonal diagonal;
            int i2;
            this.mDiagonals = list;
            this.mOldItemStatuses = iArr;
            this.mNewItemStatuses = iArr2;
            Arrays.fill(iArr, 0);
            Arrays.fill(iArr2, 0);
            this.mCallback = callback;
            int oldListSize = callback.getOldListSize();
            this.mOldListSize = oldListSize;
            int newListSize = callback.getNewListSize();
            this.mNewListSize = newListSize;
            this.mDetectMoves = z;
            Diagonal diagonal2 = list.isEmpty() ? null : list.get(0);
            if (diagonal2 == null || diagonal2.f175x != 0 || diagonal2.f176y != 0) {
                list.add(0, new Diagonal(0, 0, 0));
            }
            list.add(new Diagonal(oldListSize, newListSize, 0));
            Iterator<Diagonal> it = list.iterator();
            while (true) {
                boolean hasNext = it.hasNext();
                callback2 = this.mCallback;
                iArr3 = this.mNewItemStatuses;
                iArr4 = this.mOldItemStatuses;
                if (!hasNext) {
                    break;
                }
                Diagonal next = it.next();
                for (int i3 = 0; i3 < next.size; i3++) {
                    int i4 = next.f175x + i3;
                    int i5 = next.f176y + i3;
                    int i6 = callback2.areContentsTheSame(i4, i5) ? 1 : 2;
                    iArr4[i4] = (i5 << 4) | i6;
                    iArr3[i5] = (i4 << 4) | i6;
                }
            }
            if (this.mDetectMoves) {
                int i7 = 0;
                for (Diagonal diagonal3 : list) {
                    while (true) {
                        i = diagonal3.f175x;
                        if (i7 < i) {
                            if (iArr4[i7] == 0) {
                                int size = list.size();
                                int i8 = 0;
                                int i9 = 0;
                                while (true) {
                                    if (i8 < size) {
                                        diagonal = list.get(i8);
                                        while (true) {
                                            i2 = diagonal.f176y;
                                            if (i9 < i2) {
                                                if (iArr3[i9] == 0 && callback2.areItemsTheSame(i7, i9)) {
                                                    int i10 = callback2.areContentsTheSame(i7, i9) ? 8 : 4;
                                                    iArr4[i7] = (i9 << 4) | i10;
                                                    iArr3[i9] = i10 | (i7 << 4);
                                                } else {
                                                    i9++;
                                                }
                                            }
                                        }
                                    }
                                    i9 = diagonal.size + i2;
                                    i8++;
                                }
                            }
                            i7++;
                        }
                    }
                    i7 = diagonal3.size + i;
                }
            }
        }

        public static PostponedUpdate getPostponedUpdate(Collection collection, int i, boolean z) {
            PostponedUpdate postponedUpdate;
            Iterator it = ((ArrayDeque) collection).iterator();
            while (true) {
                if (!it.hasNext()) {
                    postponedUpdate = null;
                    break;
                }
                postponedUpdate = (PostponedUpdate) it.next();
                if (postponedUpdate.posInOwnerList == i && postponedUpdate.removal == z) {
                    it.remove();
                    break;
                }
            }
            while (it.hasNext()) {
                PostponedUpdate postponedUpdate2 = (PostponedUpdate) it.next();
                if (z) {
                    postponedUpdate2.currentPos--;
                } else {
                    postponedUpdate2.currentPos++;
                }
            }
            return postponedUpdate;
        }

        public final void dispatchUpdatesTo(ListUpdateCallback listUpdateCallback) {
            int i;
            int[] iArr;
            Callback callback;
            int i2;
            List list;
            int i3;
            DiffResult diffResult = this;
            BatchingListUpdateCallback batchingListUpdateCallback = listUpdateCallback instanceof BatchingListUpdateCallback ? (BatchingListUpdateCallback) listUpdateCallback : new BatchingListUpdateCallback(listUpdateCallback);
            ArrayDeque arrayDeque = new ArrayDeque();
            List list2 = diffResult.mDiagonals;
            int size = list2.size() - 1;
            int i4 = diffResult.mOldListSize;
            int i5 = diffResult.mNewListSize;
            int i6 = i4;
            while (size >= 0) {
                Diagonal diagonal = (Diagonal) list2.get(size);
                int i7 = diagonal.f175x;
                int i8 = diagonal.size;
                int i9 = i7 + i8;
                int i10 = diagonal.f176y;
                int i11 = i8 + i10;
                while (true) {
                    i = 0;
                    iArr = diffResult.mOldItemStatuses;
                    callback = diffResult.mCallback;
                    if (i6 <= i9) {
                        break;
                    }
                    i6--;
                    int i12 = iArr[i6];
                    if ((i12 & 12) != 0) {
                        list = list2;
                        int i13 = i12 >> 4;
                        PostponedUpdate postponedUpdate = getPostponedUpdate(arrayDeque, i13, false);
                        if (postponedUpdate != null) {
                            i3 = i5;
                            int i14 = (i4 - postponedUpdate.currentPos) - 1;
                            batchingListUpdateCallback.onMoved(i6, i14);
                            if ((i12 & 4) != 0) {
                                batchingListUpdateCallback.onChanged(i14, 1, callback.getChangePayload(i6, i13));
                            }
                        } else {
                            i3 = i5;
                            arrayDeque.add(new PostponedUpdate(i6, (i4 - i6) - 1, true));
                        }
                    } else {
                        list = list2;
                        i3 = i5;
                        batchingListUpdateCallback.onRemoved(i6, 1);
                        i4--;
                    }
                    list2 = list;
                    i5 = i3;
                }
                List list3 = list2;
                while (i5 > i11) {
                    i5--;
                    int i15 = diffResult.mNewItemStatuses[i5];
                    if ((i15 & 12) != 0) {
                        int i16 = i15 >> 4;
                        PostponedUpdate postponedUpdate2 = getPostponedUpdate(arrayDeque, i16, true);
                        if (postponedUpdate2 == null) {
                            arrayDeque.add(new PostponedUpdate(i5, i4 - i6, false));
                            i2 = 0;
                        } else {
                            i2 = 0;
                            batchingListUpdateCallback.onMoved((i4 - postponedUpdate2.currentPos) - 1, i6);
                            if ((i15 & 4) != 0) {
                                batchingListUpdateCallback.onChanged(i6, 1, callback.getChangePayload(i16, i5));
                            }
                        }
                    } else {
                        i2 = i;
                        batchingListUpdateCallback.onInserted(i6, 1);
                        i4++;
                    }
                    diffResult = this;
                    i = i2;
                }
                i6 = diagonal.f175x;
                int i17 = i6;
                int i18 = i10;
                while (i < i8) {
                    if ((iArr[i17] & 15) == 2) {
                        batchingListUpdateCallback.onChanged(i17, 1, callback.getChangePayload(i17, i18));
                    }
                    i17++;
                    i18++;
                    i++;
                }
                size--;
                diffResult = this;
                i5 = i10;
                list2 = list3;
            }
            batchingListUpdateCallback.dispatchLastEvent();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PostponedUpdate {
        public int currentPos;
        public final int posInOwnerList;
        public final boolean removal;

        public PostponedUpdate(int i, int i2, boolean z) {
            this.posInOwnerList = i;
            this.currentPos = i2;
            this.removal = z;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Range {
        public int newListEnd;
        public int newListStart;
        public int oldListEnd;
        public int oldListStart;

        public Range() {
        }

        public Range(int i, int i2, int i3, int i4) {
            this.oldListStart = i;
            this.oldListEnd = i2;
            this.newListStart = i3;
            this.newListEnd = i4;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Snake {
        public int endX;
        public int endY;
        public boolean reverse;
        public int startX;
        public int startY;

        public final int diagonalSize() {
            return Math.min(this.endX - this.startX, this.endY - this.startY);
        }
    }

    private DiffUtil() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x00a9, code lost:
    
        if (r5.get(r14 + 1) > r5.get(r14 - 1)) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00f4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static DiffResult calculateDiff(Callback callback) {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        CenteredArray centeredArray;
        Snake snake;
        ArrayList arrayList4;
        ArrayList arrayList5;
        ArrayList arrayList6;
        int i;
        Range range;
        Diagonal diagonal;
        int i2;
        CenteredArray centeredArray2;
        int[] iArr;
        boolean z;
        Snake snake2;
        int i3;
        Snake snake3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        boolean z2;
        int oldListSize = callback.getOldListSize();
        int newListSize = callback.getNewListSize();
        ArrayList arrayList7 = new ArrayList();
        ArrayList arrayList8 = new ArrayList();
        int i10 = 0;
        arrayList8.add(new Range(0, oldListSize, 0, newListSize));
        int i11 = oldListSize + newListSize;
        int i12 = 1;
        int i13 = (((i11 + 1) / 2) * 2) + 1;
        CenteredArray centeredArray3 = new CenteredArray(i13);
        CenteredArray centeredArray4 = new CenteredArray(i13);
        ArrayList arrayList9 = new ArrayList();
        while (!arrayList8.isEmpty()) {
            Range range2 = (Range) arrayList8.remove(arrayList8.size() - i12);
            int i14 = range2.oldListEnd;
            int i15 = range2.oldListStart;
            int i16 = i14 - i15;
            if (i16 >= i12 && (i2 = range2.newListEnd - range2.newListStart) >= i12) {
                int i17 = ((i2 + i16) + i12) / 2;
                int i18 = centeredArray3.mMid;
                int[] iArr2 = centeredArray3.mData;
                iArr2[i12 + i18] = i15;
                int i19 = centeredArray4.mMid;
                int[] iArr3 = centeredArray4.mData;
                iArr3[i12 + i19] = i14;
                int i20 = i10;
                while (i20 < i17) {
                    boolean z3 = Math.abs((range2.oldListEnd - range2.oldListStart) - (range2.newListEnd - range2.newListStart)) % 2 == 1;
                    int i21 = i17;
                    int i22 = (range2.oldListEnd - range2.oldListStart) - (range2.newListEnd - range2.newListStart);
                    int i23 = -i20;
                    int i24 = i23;
                    while (true) {
                        if (i24 > i20) {
                            arrayList = arrayList9;
                            arrayList2 = arrayList7;
                            arrayList3 = arrayList8;
                            centeredArray2 = centeredArray3;
                            iArr = iArr3;
                            z = false;
                            snake2 = null;
                            break;
                        }
                        if (i24 != i23) {
                            if (i24 != i20) {
                                arrayList3 = arrayList8;
                                arrayList = arrayList9;
                            } else {
                                arrayList = arrayList9;
                                arrayList3 = arrayList8;
                            }
                            i7 = centeredArray3.get(i24 - 1);
                            i8 = i7 + 1;
                            arrayList2 = arrayList7;
                            centeredArray2 = centeredArray3;
                            i9 = ((i8 - range2.oldListStart) + range2.newListStart) - i24;
                            int i25 = (i20 == 0 && i8 == i7) ? i9 - 1 : i9;
                            iArr = iArr3;
                            while (i8 < range2.oldListEnd && i9 < range2.newListEnd && callback.areItemsTheSame(i8, i9)) {
                                i8++;
                                i9++;
                            }
                            iArr2[i24 + i18] = i8;
                            if (!z3) {
                                int i26 = i22 - i24;
                                z2 = z3;
                                if (i26 >= i23 + 1 && i26 <= i20 - 1 && centeredArray4.get(i26) <= i8) {
                                    snake2 = new Snake();
                                    snake2.startX = i7;
                                    snake2.startY = i25;
                                    snake2.endX = i8;
                                    snake2.endY = i9;
                                    z = false;
                                    snake2.reverse = false;
                                    break;
                                }
                            } else {
                                z2 = z3;
                            }
                            i24 += 2;
                            arrayList8 = arrayList3;
                            arrayList9 = arrayList;
                            arrayList7 = arrayList2;
                            centeredArray3 = centeredArray2;
                            iArr3 = iArr;
                            z3 = z2;
                        } else {
                            arrayList = arrayList9;
                            arrayList3 = arrayList8;
                        }
                        i7 = centeredArray3.get(i24 + 1);
                        i8 = i7;
                        arrayList2 = arrayList7;
                        centeredArray2 = centeredArray3;
                        i9 = ((i8 - range2.oldListStart) + range2.newListStart) - i24;
                        if (i20 == 0) {
                        }
                        iArr = iArr3;
                        while (i8 < range2.oldListEnd) {
                            i8++;
                            i9++;
                        }
                        iArr2[i24 + i18] = i8;
                        if (!z3) {
                        }
                        i24 += 2;
                        arrayList8 = arrayList3;
                        arrayList9 = arrayList;
                        arrayList7 = arrayList2;
                        centeredArray3 = centeredArray2;
                        iArr3 = iArr;
                        z3 = z2;
                    }
                    if (snake2 != null) {
                        snake = snake2;
                        centeredArray = centeredArray2;
                        break;
                    }
                    int i27 = (range2.oldListEnd - range2.oldListStart) - (range2.newListEnd - range2.newListStart);
                    boolean z4 = i27 % 2 == 0 ? true : z;
                    int i28 = i23;
                    while (true) {
                        if (i28 > i20) {
                            i3 = i18;
                            centeredArray = centeredArray2;
                            snake3 = null;
                            break;
                        }
                        if (i28 == i23 || (i28 != i20 && centeredArray4.get(i28 + 1) < centeredArray4.get(i28 - 1))) {
                            i4 = centeredArray4.get(i28 + 1);
                            i5 = i4;
                        } else {
                            i4 = centeredArray4.get(i28 - 1);
                            i5 = i4 - 1;
                        }
                        int i29 = range2.newListEnd - ((range2.oldListEnd - i5) - i28);
                        int i30 = (i20 == 0 || i5 != i4) ? i29 : i29 + 1;
                        while (i5 > range2.oldListStart && i29 > range2.newListStart) {
                            int i31 = i5 - 1;
                            i3 = i18;
                            int i32 = i29 - 1;
                            if (!callback.areItemsTheSame(i31, i32)) {
                                break;
                            }
                            i5 = i31;
                            i29 = i32;
                            i18 = i3;
                        }
                        i3 = i18;
                        iArr[i28 + i19] = i5;
                        if (!z4 || (i6 = i27 - i28) < i23 || i6 > i20) {
                            centeredArray = centeredArray2;
                        } else {
                            centeredArray = centeredArray2;
                            if (centeredArray.get(i6) >= i5) {
                                snake3 = new Snake();
                                snake3.startX = i5;
                                snake3.startY = i29;
                                snake3.endX = i4;
                                snake3.endY = i30;
                                snake3.reverse = true;
                                break;
                            }
                        }
                        i28 += 2;
                        centeredArray2 = centeredArray;
                        i18 = i3;
                    }
                    if (snake3 != null) {
                        snake = snake3;
                        break;
                    }
                    i20++;
                    centeredArray3 = centeredArray;
                    i17 = i21;
                    arrayList8 = arrayList3;
                    arrayList9 = arrayList;
                    arrayList7 = arrayList2;
                    iArr3 = iArr;
                    i18 = i3;
                }
            }
            arrayList = arrayList9;
            arrayList2 = arrayList7;
            arrayList3 = arrayList8;
            centeredArray = centeredArray3;
            snake = null;
            if (snake != null) {
                if (snake.diagonalSize() > 0) {
                    int i33 = snake.endY;
                    int i34 = snake.startY;
                    int i35 = i33 - i34;
                    int i36 = snake.endX;
                    int i37 = snake.startX;
                    int i38 = i36 - i37;
                    if (!(i35 != i38)) {
                        diagonal = new Diagonal(i37, i34, i38);
                    } else if (snake.reverse) {
                        diagonal = new Diagonal(i37, i34, snake.diagonalSize());
                    } else {
                        diagonal = i35 > i38 ? new Diagonal(i37, i34 + 1, snake.diagonalSize()) : new Diagonal(i37 + 1, i34, snake.diagonalSize());
                    }
                    arrayList6 = arrayList2;
                    arrayList6.add(diagonal);
                } else {
                    arrayList6 = arrayList2;
                }
                if (arrayList.isEmpty()) {
                    range = new Range();
                    arrayList5 = arrayList;
                    i = 1;
                } else {
                    i = 1;
                    arrayList5 = arrayList;
                    range = (Range) arrayList5.remove(arrayList.size() - 1);
                }
                range.oldListStart = range2.oldListStart;
                range.newListStart = range2.newListStart;
                range.oldListEnd = snake.startX;
                range.newListEnd = snake.startY;
                arrayList4 = arrayList3;
                arrayList4.add(range);
                range2.oldListEnd = range2.oldListEnd;
                range2.newListEnd = range2.newListEnd;
                range2.oldListStart = snake.endX;
                range2.newListStart = snake.endY;
                arrayList4.add(range2);
            } else {
                arrayList4 = arrayList3;
                arrayList5 = arrayList;
                arrayList6 = arrayList2;
                i = 1;
                arrayList5.add(range2);
            }
            arrayList9 = arrayList5;
            arrayList8 = arrayList4;
            centeredArray3 = centeredArray;
            i10 = 0;
            int i39 = i;
            arrayList7 = arrayList6;
            i12 = i39;
        }
        ArrayList arrayList10 = arrayList7;
        Collections.sort(arrayList10, DIAGONAL_COMPARATOR);
        return new DiffResult(callback, arrayList10, centeredArray3.mData, centeredArray4.mData, true);
    }
}

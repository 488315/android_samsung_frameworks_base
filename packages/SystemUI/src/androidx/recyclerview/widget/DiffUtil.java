package androidx.recyclerview.widget;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class DiffUtil {
    public static final AnonymousClass1 DIAGONAL_COMPARATOR = new Comparator() { // from class: androidx.recyclerview.widget.DiffUtil.1
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((Diagonal) obj).x - ((Diagonal) obj2).x;
        }
    };

    public class CenteredArray {
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

    public class Diagonal {
        public final int size;
        public final int x;
        public final int y;

        public Diagonal(int i, int i2, int i3) {
            this.x = i;
            this.y = i2;
            this.size = i3;
        }
    }

    public class DiffResult {
        public final Callback mCallback;
        public final boolean mDetectMoves;
        public final List mDiagonals;
        public final int[] mNewItemStatuses;
        public final int mNewListSize;
        public final int[] mOldItemStatuses;
        public final int mOldListSize;

        public DiffResult(Callback callback, List<Diagonal> list, int[] iArr, int[] iArr2, boolean z) {
            int[] iArr3;
            int[] iArr4;
            Callback callback2;
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
            if (diagonal2 == null || diagonal2.x != 0 || diagonal2.y != 0) {
                list.add(0, new Diagonal(0, 0, 0));
            }
            list.add(new Diagonal(oldListSize, newListSize, 0));
            Iterator<Diagonal> it = list.iterator();
            while (true) {
                boolean zHasNext = it.hasNext();
                iArr3 = this.mNewItemStatuses;
                iArr4 = this.mOldItemStatuses;
                callback2 = this.mCallback;
                if (!zHasNext) {
                    break;
                }
                Diagonal next = it.next();
                for (int i3 = 0; i3 < next.size; i3++) {
                    int i4 = next.x + i3;
                    int i5 = next.y + i3;
                    int i6 = callback2.areContentsTheSame(i4, i5) ? 1 : 2;
                    iArr4[i4] = (i5 << 4) | i6;
                    iArr3[i5] = (i4 << 4) | i6;
                }
            }
            if (this.mDetectMoves) {
                int i7 = 0;
                for (Diagonal diagonal3 : this.mDiagonals) {
                    while (true) {
                        i = diagonal3.x;
                        if (i7 < i) {
                            if (iArr4[i7] == 0) {
                                int size = this.mDiagonals.size();
                                int i8 = 0;
                                int i9 = 0;
                                while (true) {
                                    if (i8 < size) {
                                        diagonal = (Diagonal) this.mDiagonals.get(i8);
                                        while (true) {
                                            i2 = diagonal.y;
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

        public static PostponedUpdate getPostponedUpdate(boolean z, Collection collection, int i) {
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
            int[] iArr;
            Callback callback;
            int i;
            int i2;
            DiffResult diffResult = this;
            BatchingListUpdateCallback batchingListUpdateCallback = listUpdateCallback instanceof BatchingListUpdateCallback ? (BatchingListUpdateCallback) listUpdateCallback : new BatchingListUpdateCallback(listUpdateCallback);
            ArrayDeque arrayDeque = new ArrayDeque();
            boolean z = true;
            int size = diffResult.mDiagonals.size() - 1;
            int i3 = diffResult.mOldListSize;
            int i4 = diffResult.mNewListSize;
            int i5 = i3;
            while (size >= 0) {
                Diagonal diagonal = (Diagonal) diffResult.mDiagonals.get(size);
                int i6 = diagonal.x;
                int i7 = diagonal.size;
                int i8 = i6 + i7;
                int i9 = diagonal.y;
                int i10 = i9 + i7;
                while (true) {
                    iArr = diffResult.mOldItemStatuses;
                    callback = diffResult.mCallback;
                    boolean z2 = z;
                    i = 0;
                    if (i5 <= i8) {
                        break;
                    }
                    i5--;
                    int i11 = iArr[i5];
                    if ((i11 & 12) != 0) {
                        int i12 = i11 >> 4;
                        PostponedUpdate postponedUpdate = getPostponedUpdate(false, arrayDeque, i12);
                        if (postponedUpdate != null) {
                            int i13 = (i3 - postponedUpdate.currentPos) - 1;
                            batchingListUpdateCallback.onMoved(i5, i13);
                            if ((i11 & 4) != 0) {
                                callback.getChangePayload(i5, i12);
                                batchingListUpdateCallback.onChanged(i13, z2 ? 1 : 0, null);
                            }
                        } else {
                            arrayDeque.add(new PostponedUpdate(i5, (i3 - i5) - (z2 ? 1 : 0), z2));
                        }
                    } else {
                        batchingListUpdateCallback.onRemoved(i5, z2 ? 1 : 0);
                        i3--;
                    }
                    z = true;
                }
                while (i4 > i10) {
                    i4--;
                    int i14 = diffResult.mNewItemStatuses[i4];
                    if ((i14 & 12) != 0) {
                        int i15 = i14 >> 4;
                        PostponedUpdate postponedUpdate2 = getPostponedUpdate(true, arrayDeque, i15);
                        if (postponedUpdate2 == null) {
                            arrayDeque.add(new PostponedUpdate(i4, i3 - i5, false));
                            i2 = 0;
                        } else {
                            i2 = 0;
                            batchingListUpdateCallback.onMoved((i3 - postponedUpdate2.currentPos) - 1, i5);
                            if ((i14 & 4) != 0) {
                                callback.getChangePayload(i15, i4);
                                batchingListUpdateCallback.onChanged(i5, 1, null);
                            }
                        }
                    } else {
                        i2 = i;
                        batchingListUpdateCallback.onInserted(i5, 1);
                        i3++;
                    }
                    diffResult = this;
                    i = i2;
                }
                i5 = diagonal.x;
                int i16 = i5;
                int i17 = i9;
                while (i < i7) {
                    if ((iArr[i16] & 15) == 2) {
                        callback.getChangePayload(i16, i17);
                        batchingListUpdateCallback.onChanged(i16, 1, null);
                    }
                    i16++;
                    i17++;
                    i++;
                }
                size--;
                diffResult = this;
                i4 = i9;
                z = true;
            }
            batchingListUpdateCallback.dispatchLastEvent();
        }
    }

    public class PostponedUpdate {
        public int currentPos;
        public final int posInOwnerList;
        public final boolean removal;

        public PostponedUpdate(int i, int i2, boolean z) {
            this.posInOwnerList = i;
            this.currentPos = i2;
            this.removal = z;
        }
    }

    public class Range {
        public int newListEnd;
        public int newListStart;
        public int oldListEnd;
        public int oldListStart;

        public Range() {
        }

        public final int newSize() {
            return this.newListEnd - this.newListStart;
        }

        public final int oldSize() {
            return this.oldListEnd - this.oldListStart;
        }

        public Range(int i, int i2, int i3, int i4) {
            this.oldListStart = i;
            this.oldListEnd = i2;
            this.newListStart = i3;
            this.newListEnd = i4;
        }
    }

    public class Snake {
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

    /* JADX WARN: Removed duplicated region for block: B:30:0x00d9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x012f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static DiffResult calculateDiff(Callback callback) {
        CenteredArray centeredArray;
        Snake snake;
        int i;
        Range range;
        int[] iArr;
        int[] iArr2;
        int i2;
        int i3;
        boolean z;
        Snake snake2;
        Snake snake3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int oldListSize = callback.getOldListSize();
        int newListSize = callback.getNewListSize();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i18 = 0;
        arrayList2.add(new Range(0, oldListSize, 0, newListSize));
        int i19 = oldListSize + newListSize;
        int i20 = 1;
        int i21 = (((i19 + 1) / 2) * 2) + 1;
        CenteredArray centeredArray2 = new CenteredArray(i21);
        CenteredArray centeredArray3 = new CenteredArray(i21);
        ArrayList arrayList3 = new ArrayList();
        while (true) {
            boolean zIsEmpty = arrayList2.isEmpty();
            int[] iArr3 = centeredArray2.mData;
            int[] iArr4 = centeredArray3.mData;
            if (zIsEmpty) {
                Collections.sort(arrayList, DIAGONAL_COMPARATOR);
                return new DiffResult(callback, arrayList, iArr3, iArr4, true);
            }
            Range range2 = (Range) arrayList2.remove(arrayList2.size() - i20);
            if (range2.oldSize() < i20 || range2.newSize() < i20) {
                centeredArray = centeredArray3;
                snake = null;
            } else {
                int iNewSize = ((range2.newSize() + range2.oldSize()) + i20) / 2;
                int i22 = range2.oldListStart;
                int i23 = centeredArray2.mMid;
                iArr3[i20 + i23] = i22;
                int i24 = range2.oldListEnd;
                int i25 = centeredArray3.mMid;
                iArr4[i20 + i25] = i24;
                int i26 = i18;
                while (i26 < iNewSize) {
                    int i27 = Math.abs(range2.oldSize() - range2.newSize()) % 2 == i20 ? i20 : i18;
                    int iOldSize = range2.oldSize() - range2.newSize();
                    int i28 = -i26;
                    int i29 = i28;
                    while (true) {
                        if (i29 > i26) {
                            iArr = iArr3;
                            iArr2 = iArr4;
                            i2 = iNewSize;
                            i3 = i23;
                            z = false;
                            snake2 = null;
                            break;
                        }
                        if (i29 != i28) {
                            if (i29 != i26) {
                                i9 = i29;
                                iArr = iArr3;
                                if (centeredArray2.get(i9 + 1) > centeredArray2.get(i9 - 1)) {
                                }
                                iArr2 = iArr4;
                                i12 = ((i11 - range2.oldListStart) + range2.newListStart) - i9;
                                if (i26 == 0 || i11 != i10) {
                                    i13 = i11;
                                    i14 = i12;
                                } else {
                                    i13 = i11;
                                    i14 = i12 - 1;
                                }
                                i15 = i27;
                                i16 = i12;
                                i17 = i13;
                                i2 = iNewSize;
                                while (i17 < range2.oldListEnd && i16 < range2.newListEnd && callback.areItemsTheSame(i17, i16)) {
                                    i17++;
                                    i16++;
                                }
                                iArr[i9 + i23] = i17;
                                if (i15 == 0) {
                                    int i30 = iOldSize - i9;
                                    i3 = i23;
                                    if (i30 >= i28 + 1 && i30 <= i26 - 1 && centeredArray3.get(i30) <= i17) {
                                        snake2 = new Snake();
                                        snake2.startX = i10;
                                        snake2.startY = i14;
                                        snake2.endX = i17;
                                        snake2.endY = i16;
                                        z = false;
                                        snake2.reverse = false;
                                        break;
                                    }
                                } else {
                                    i3 = i23;
                                }
                                i29 = i9 + 2;
                                iArr3 = iArr;
                                iArr4 = iArr2;
                                i27 = i15;
                                iNewSize = i2;
                                i23 = i3;
                            } else {
                                i9 = i29;
                                iArr = iArr3;
                            }
                            i10 = centeredArray2.get(i9 - 1);
                            i11 = i10 + 1;
                            iArr2 = iArr4;
                            i12 = ((i11 - range2.oldListStart) + range2.newListStart) - i9;
                            if (i26 == 0) {
                                i13 = i11;
                                i14 = i12;
                                i15 = i27;
                                i16 = i12;
                                i17 = i13;
                                i2 = iNewSize;
                                while (i17 < range2.oldListEnd) {
                                    i17++;
                                    i16++;
                                }
                                iArr[i9 + i23] = i17;
                                if (i15 == 0) {
                                }
                                i29 = i9 + 2;
                                iArr3 = iArr;
                                iArr4 = iArr2;
                                i27 = i15;
                                iNewSize = i2;
                                i23 = i3;
                            }
                        } else {
                            i9 = i29;
                            iArr = iArr3;
                        }
                        i10 = centeredArray2.get(i9 + 1);
                        i11 = i10;
                        iArr2 = iArr4;
                        i12 = ((i11 - range2.oldListStart) + range2.newListStart) - i9;
                        if (i26 == 0) {
                        }
                    }
                    if (snake2 != null) {
                        centeredArray = centeredArray3;
                        snake = snake2;
                        break;
                    }
                    boolean z2 = (range2.oldSize() - range2.newSize()) % 2 == 0 ? true : z;
                    int iOldSize2 = range2.oldSize() - range2.newSize();
                    int i31 = i28;
                    while (true) {
                        if (i31 > i26) {
                            centeredArray = centeredArray3;
                            snake3 = null;
                            break;
                        }
                        if (i31 == i28 || (i31 != i26 && centeredArray3.get(i31 + 1) < centeredArray3.get(i31 - 1))) {
                            i4 = centeredArray3.get(i31 + 1);
                            i5 = i4;
                        } else {
                            i4 = centeredArray3.get(i31 - 1);
                            i5 = i4 - 1;
                        }
                        int i32 = range2.newListEnd - ((range2.oldListEnd - i5) - i31);
                        if (i26 == 0 || i5 != i4) {
                            i6 = i32;
                        } else {
                            i6 = i32;
                            i32++;
                        }
                        int i33 = i6;
                        centeredArray = centeredArray3;
                        int i34 = i5;
                        int i35 = i33;
                        boolean z3 = z2;
                        while (i34 > range2.oldListStart && i35 > range2.newListStart) {
                            i7 = iOldSize2;
                            if (!callback.areItemsTheSame(i34 - 1, i35 - 1)) {
                                break;
                            }
                            i34--;
                            i35--;
                            iOldSize2 = i7;
                        }
                        i7 = iOldSize2;
                        iArr2[i31 + i25] = i34;
                        if (z3 && (i8 = i7 - i31) >= i28 && i8 <= i26 && centeredArray2.get(i8) >= i34) {
                            snake3 = new Snake();
                            snake3.startX = i34;
                            snake3.startY = i35;
                            snake3.endX = i4;
                            snake3.endY = i32;
                            snake3.reverse = true;
                            break;
                        }
                        i31 += 2;
                        centeredArray3 = centeredArray;
                        z2 = z3;
                        iOldSize2 = i7;
                    }
                    if (snake3 != null) {
                        snake = snake3;
                        break;
                    }
                    i26++;
                    centeredArray3 = centeredArray;
                    iArr3 = iArr;
                    iArr4 = iArr2;
                    iNewSize = i2;
                    i23 = i3;
                    i20 = 1;
                    i18 = 0;
                }
                centeredArray = centeredArray3;
                snake = null;
            }
            if (snake != null) {
                if (snake.diagonalSize() > 0) {
                    int i36 = snake.endY;
                    int i37 = snake.startY;
                    int i38 = i36 - i37;
                    int i39 = snake.endX;
                    int i40 = snake.startX;
                    int i41 = i39 - i40;
                    arrayList.add(i38 != i41 ? snake.reverse ? new Diagonal(i40, i37, snake.diagonalSize()) : i38 > i41 ? new Diagonal(i40, i37 + 1, snake.diagonalSize()) : new Diagonal(i40 + 1, i37, snake.diagonalSize()) : new Diagonal(i40, i37, i41));
                }
                if (arrayList3.isEmpty()) {
                    range = new Range();
                    i = 1;
                } else {
                    i = 1;
                    range = (Range) arrayList3.remove(arrayList3.size() - 1);
                }
                range.oldListStart = range2.oldListStart;
                range.newListStart = range2.newListStart;
                range.oldListEnd = snake.startX;
                range.newListEnd = snake.startY;
                arrayList2.add(range);
                range2.oldListEnd = range2.oldListEnd;
                range2.newListEnd = range2.newListEnd;
                range2.oldListStart = snake.endX;
                range2.newListStart = snake.endY;
                arrayList2.add(range2);
            } else {
                i = 1;
                arrayList3.add(range2);
            }
            centeredArray3 = centeredArray;
            i20 = i;
            i18 = 0;
        }
    }

    public abstract class Callback {
        public abstract boolean areContentsTheSame(int i, int i2);

        public abstract boolean areItemsTheSame(int i, int i2);

        public abstract int getNewListSize();

        public abstract int getOldListSize();

        public void getChangePayload(int i, int i2) {
        }
    }
}

package androidx.recyclerview.widget;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class DiffUtil {
    public static final AnonymousClass1 DIAGONAL_COMPARATOR = new Comparator() { // from class: androidx.recyclerview.widget.DiffUtil.1
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((Diagonal) obj).x - ((Diagonal) obj2).x;
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                boolean hasNext = it.hasNext();
                iArr3 = this.mNewItemStatuses;
                iArr4 = this.mOldItemStatuses;
                callback2 = this.mCallback;
                if (!hasNext) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:18:0x00ad, code lost:
    
        if (r5.get(r19 + 1) > r5.get(r19 - 1)) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:120:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x010a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static androidx.recyclerview.widget.DiffUtil.DiffResult calculateDiff(androidx.recyclerview.widget.DiffUtil.Callback r26) {
        /*
            Method dump skipped, instructions count: 705
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.DiffUtil.calculateDiff(androidx.recyclerview.widget.DiffUtil$Callback):androidx.recyclerview.widget.DiffUtil$DiffResult");
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Callback {
        public abstract boolean areContentsTheSame(int i, int i2);

        public abstract boolean areItemsTheSame(int i, int i2);

        public abstract int getNewListSize();

        public abstract int getOldListSize();

        public void getChangePayload(int i, int i2) {
        }
    }
}

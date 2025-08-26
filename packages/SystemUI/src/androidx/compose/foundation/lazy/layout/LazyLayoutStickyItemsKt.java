package androidx.compose.foundation.lazy.layout;

import androidx.collection.IntListKt;
import androidx.collection.MutableIntList;
import androidx.compose.ui.unit.IntOffset;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public abstract class LazyLayoutStickyItemsKt {
    public static final List applyStickyItems(StickyItemsPlacement stickyItemsPlacement, List list, MutableIntList mutableIntList, int i, int i2, int i3, Function1 function1) {
        MutableIntList mutableIntList2;
        char c;
        long j;
        int i4;
        Object obj;
        int i5;
        long j2;
        boolean z;
        int i6;
        boolean z2 = true;
        if (stickyItemsPlacement == null || list.isEmpty() || mutableIntList._size == 0) {
            return EmptyList.INSTANCE;
        }
        int index = ((LazyLayoutMeasuredItem) CollectionsKt___CollectionsKt.first(list)).getIndex();
        int i7 = -1;
        if (((LazyLayoutMeasuredItem) CollectionsKt___CollectionsKt.last(list)).getIndex() - index < 0 || (i6 = mutableIntList._size) == 0) {
            mutableIntList2 = IntListKt.EmptyIntList;
        } else {
            IntRange intRangeUntil = RangesKt___RangesKt.until(0, i6);
            int i8 = intRangeUntil.first;
            int i9 = intRangeUntil.last;
            int i10 = -1;
            if (i8 <= i9) {
                while (mutableIntList.get(i8) <= index) {
                    i10 = mutableIntList.get(i8);
                    if (i8 == i9) {
                        break;
                    }
                    i8++;
                }
            }
            if (i10 == -1) {
                mutableIntList2 = IntListKt.EmptyIntList;
            } else {
                MutableIntList mutableIntList3 = IntListKt.EmptyIntList;
                mutableIntList2 = new MutableIntList(1);
                mutableIntList2.add(i10);
            }
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) list;
        ArrayList arrayList3 = new ArrayList(arrayList2.size());
        int size = list.size();
        int i11 = 0;
        while (i11 < size) {
            Object obj2 = arrayList2.get(i11);
            int index2 = ((LazyLayoutMeasuredItem) obj2).getIndex();
            int[] iArr = mutableIntList.content;
            int i12 = mutableIntList._size;
            int i13 = 0;
            while (true) {
                if (i13 >= i12) {
                    z = z2;
                    break;
                }
                z = z2;
                if (iArr[i13] == index2) {
                    arrayList3.add(obj2);
                    break;
                }
                i13++;
                z2 = z;
            }
            i11++;
            z2 = z;
        }
        int[] iArr2 = mutableIntList2.content;
        int i14 = mutableIntList2._size;
        int i15 = 0;
        while (i15 < i14) {
            int i16 = iArr2[i15];
            int size2 = arrayList2.size();
            int i17 = 0;
            int i18 = 0;
            while (true) {
                if (i18 >= size2) {
                    i17 = i7;
                    break;
                }
                Object obj3 = arrayList2.get(i18);
                i18++;
                if (((LazyLayoutMeasuredItem) obj3).getIndex() == i16) {
                    break;
                }
                i17++;
            }
            LazyLayoutMeasuredItem lazyLayoutMeasuredItem = i17 == i7 ? (LazyLayoutMeasuredItem) function1.mo781invoke(Integer.valueOf(i16)) : (LazyLayoutMeasuredItem) arrayList2.remove(i17);
            int mainAxisSizeWithSpacings = lazyLayoutMeasuredItem.getMainAxisSizeWithSpacings();
            if (i17 == i7) {
                c = ' ';
                i4 = Integer.MIN_VALUE;
            } else {
                long jMo155getOffsetBjo55l4 = lazyLayoutMeasuredItem.mo155getOffsetBjo55l4(0);
                if (lazyLayoutMeasuredItem.isVertical()) {
                    IntOffset.Companion companion = IntOffset.Companion;
                    c = ' ';
                    j = jMo155getOffsetBjo55l4 & 4294967295L;
                } else {
                    c = ' ';
                    IntOffset.Companion companion2 = IntOffset.Companion;
                    j = jMo155getOffsetBjo55l4 >> 32;
                }
                i4 = (int) j;
            }
            int size3 = arrayList3.size();
            int i19 = 0;
            while (true) {
                if (i19 >= size3) {
                    obj = null;
                    break;
                }
                obj = arrayList3.get(i19);
                if (((LazyLayoutMeasuredItem) obj).getIndex() != i16) {
                    break;
                }
                i19++;
            }
            LazyLayoutMeasuredItem lazyLayoutMeasuredItem2 = (LazyLayoutMeasuredItem) obj;
            if (lazyLayoutMeasuredItem2 != null) {
                long jMo155getOffsetBjo55l42 = lazyLayoutMeasuredItem2.mo155getOffsetBjo55l4(0);
                if (lazyLayoutMeasuredItem2.isVertical()) {
                    IntOffset.Companion companion3 = IntOffset.Companion;
                    j2 = jMo155getOffsetBjo55l42 & 4294967295L;
                } else {
                    IntOffset.Companion companion4 = IntOffset.Companion;
                    j2 = jMo155getOffsetBjo55l42 >> c;
                }
                i5 = (int) j2;
            } else {
                i5 = Integer.MIN_VALUE;
            }
            int iMax = i4 == Integer.MIN_VALUE ? -i : Math.max(-i, i4);
            if (i5 != Integer.MIN_VALUE) {
                iMax = Math.min(iMax, i5 - mainAxisSizeWithSpacings);
            }
            lazyLayoutMeasuredItem.setNonScrollableItem();
            lazyLayoutMeasuredItem.position(iMax, 0, i2, i3);
            arrayList.add(lazyLayoutMeasuredItem);
            i15++;
            i7 = -1;
        }
        return arrayList;
    }
}

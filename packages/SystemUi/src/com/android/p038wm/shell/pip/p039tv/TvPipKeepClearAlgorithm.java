package com.android.p038wm.shell.pip.p039tv;

import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.ArraySet;
import android.util.Size;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.pip.p039tv.TvPipKeepClearAlgorithm;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvPipKeepClearAlgorithm {
    public Size screenSize = new Size(0, 0);
    public final Rect movementBounds = new Rect();
    public int pipAreaPadding = 48;
    public int stashOffset = 48;
    public double maxRestrictedDistanceFraction = 0.15d;
    public int pipGravity = 85;
    public Rect transformedScreenBounds = new Rect();
    public Rect transformedMovementBounds = new Rect();
    public Set lastAreasOverlappingUnstashPosition = EmptySet.INSTANCE;
    public Insets pipPermanentDecorInsets = Insets.NONE;

    public static int candidateCost(Rect rect, Rect rect2) {
        int i = rect.left - rect2.left;
        int i2 = rect.top - rect2.top;
        return (i2 * i2) + (i * i);
    }

    public static int getStashType(Rect rect, Rect rect2) {
        if (rect2 == null) {
            return 0;
        }
        if (rect.left < rect2.left) {
            return 1;
        }
        if (rect.right > rect2.right) {
            return 2;
        }
        if (rect.top < rect2.top) {
            return 4;
        }
        return rect.bottom > rect2.bottom ? 3 : 0;
    }

    public static boolean intersectsX(Rect rect, Rect rect2) {
        return rect.right >= rect2.left && rect.left <= rect2.right;
    }

    public static boolean intersectsY(Rect rect, Rect rect2) {
        return rect.bottom >= rect2.top && rect.top <= rect2.bottom;
    }

    public static SweepLineEvent sweepLineFindEarliestGap(List list, int i, int i2, int i3) {
        int i4;
        ArrayList arrayList = (ArrayList) list;
        arrayList.add(new SweepLineEvent(false, i2, true, true));
        if (arrayList.size() > 1) {
            CollectionsKt__MutableCollectionsJVMKt.sortWith(list, new Comparator() { // from class: com.android.wm.shell.pip.tv.TvPipKeepClearAlgorithm$sweepLineFindEarliestGap$$inlined$sortBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(-((TvPipKeepClearAlgorithm.SweepLineEvent) obj).pos), Integer.valueOf(-((TvPipKeepClearAlgorithm.SweepLineEvent) obj2).pos));
                }
            });
        }
        int i5 = 0;
        for (int i6 = 0; i6 < arrayList.size(); i6++) {
            SweepLineEvent sweepLineEvent = (SweepLineEvent) arrayList.get(i6);
            boolean z = sweepLineEvent.start;
            if (!z) {
                i5 = sweepLineEvent.open ? i5 + 1 : i5 - 1;
            }
            if (i5 == 0 && (i4 = sweepLineEvent.pos) <= i2) {
                int i7 = z ? i3 : i;
                SweepLineEvent sweepLineEvent2 = (SweepLineEvent) CollectionsKt___CollectionsKt.getOrNull(i6 + 1, list);
                if (sweepLineEvent2 != null) {
                    if (sweepLineEvent2.pos < i4 - i7) {
                    }
                }
                return sweepLineEvent;
            }
        }
        return (SweepLineEvent) CollectionsKt___CollectionsKt.last(list);
    }

    public final Rect findFreeMovePosition(Rect rect, Set set, Set set2) {
        boolean z;
        Object obj;
        Rect rect2 = this.transformedMovementBounds;
        ArrayList arrayList = new ArrayList();
        double width = rect.right - (this.screenSize.getWidth() * (isPipAnchoredToCorner() ? this.maxRestrictedDistanceFraction : 0.0d));
        int width2 = rect2.width() + this.pipAreaPadding;
        Rect rect3 = new Rect(rect2);
        int i = 0;
        rect3.offset(width2, 0);
        arrayList.add(rect3);
        arrayList.addAll(set2);
        ArrayList arrayList2 = new ArrayList();
        Iterator it = set.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            if (((double) ((Rect) next).left) >= width) {
                arrayList2.add(next);
            }
        }
        arrayList.addAll(arrayList2);
        final int width3 = rect.width() + rect2.left;
        CollectionsKt__MutableCollectionsKt.filterInPlace$CollectionsKt__MutableCollectionsKt((List) arrayList, new Function1() { // from class: com.android.wm.shell.pip.tv.TvPipKeepClearAlgorithm$findFreeMovePosition$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return Boolean.valueOf(((Rect) obj2).left - TvPipKeepClearAlgorithm.this.pipAreaPadding > width3);
            }
        }, false);
        double height = this.screenSize.getHeight() * this.maxRestrictedDistanceFraction;
        if (Double.isNaN(height)) {
            throw new IllegalArgumentException("Cannot round NaN value.");
        }
        int round = height > 2.147483647E9d ? Integer.MAX_VALUE : height < -2.147483648E9d ? VideoPlayer.MEDIA_ERROR_SYSTEM : (int) Math.round(height);
        ArrayList arrayList3 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            Rect rect4 = (Rect) it2.next();
            int width4 = ((rect4.left - this.pipAreaPadding) - rect.width()) - rect.left;
            final Rect rect5 = new Rect(rect);
            rect5.offset(width4, i);
            boolean isPipAnchoredToCorner = isPipAnchoredToCorner() ^ z;
            final ArrayList arrayList4 = new ArrayList();
            Function1 function1 = new Function1() { // from class: com.android.wm.shell.pip.tv.TvPipKeepClearAlgorithm$findMinMoveUp$generateEvents$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    final boolean booleanValue = ((Boolean) obj2).booleanValue();
                    final TvPipKeepClearAlgorithm tvPipKeepClearAlgorithm = TvPipKeepClearAlgorithm.this;
                    final Rect rect6 = rect5;
                    final List<TvPipKeepClearAlgorithm.SweepLineEvent> list = arrayList4;
                    return new Function1() { // from class: com.android.wm.shell.pip.tv.TvPipKeepClearAlgorithm$findMinMoveUp$generateEvents$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            Rect rect7 = (Rect) obj3;
                            TvPipKeepClearAlgorithm tvPipKeepClearAlgorithm2 = TvPipKeepClearAlgorithm.this;
                            Rect rect8 = rect6;
                            tvPipKeepClearAlgorithm2.getClass();
                            if (TvPipKeepClearAlgorithm.intersectsX(rect8, rect7)) {
                                list.add(new TvPipKeepClearAlgorithm.SweepLineEvent(true, rect7.bottom, booleanValue, false, 8, null));
                                list.add(new TvPipKeepClearAlgorithm.SweepLineEvent(false, rect7.top, booleanValue, false, 8, null));
                            }
                            return Unit.INSTANCE;
                        }
                    };
                }
            };
            Function1 function12 = (Function1) function1.invoke(Boolean.FALSE);
            Iterator it3 = set.iterator();
            while (it3.hasNext()) {
                function12.invoke(it3.next());
            }
            Function1 function13 = (Function1) function1.invoke(Boolean.TRUE);
            Iterator it4 = set2.iterator();
            while (it4.hasNext()) {
                function13.invoke(it4.next());
            }
            SweepLineEvent sweepLineFindEarliestGap = sweepLineFindEarliestGap(arrayList4, rect5.height() + this.pipAreaPadding, rect5.bottom, rect5.height());
            int i2 = (sweepLineFindEarliestGap.pos - rect.bottom) - (sweepLineFindEarliestGap.start ? 0 : this.pipAreaPadding);
            int height2 = sweepLineFindEarliestGap.unrestricted ? rect2.height() : round;
            Rect rect6 = new Rect(rect);
            rect6.offset(width4, i2);
            boolean z2 = rect6.top > rect2.top;
            boolean z3 = !intersectsY(rect6, rect4);
            if (z2 && Math.abs(i2) <= height2 && !z3) {
                arrayList3.add(rect6);
            }
            if (isPipAnchoredToCorner) {
                final ArrayList arrayList5 = new ArrayList();
                Function1 function14 = new Function1() { // from class: com.android.wm.shell.pip.tv.TvPipKeepClearAlgorithm$findMinMoveDown$generateEvents$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        final boolean booleanValue = ((Boolean) obj2).booleanValue();
                        final TvPipKeepClearAlgorithm tvPipKeepClearAlgorithm = TvPipKeepClearAlgorithm.this;
                        final Rect rect7 = rect5;
                        final List<TvPipKeepClearAlgorithm.SweepLineEvent> list = arrayList5;
                        return new Function1() { // from class: com.android.wm.shell.pip.tv.TvPipKeepClearAlgorithm$findMinMoveDown$generateEvents$1.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                Rect rect8 = (Rect) obj3;
                                TvPipKeepClearAlgorithm tvPipKeepClearAlgorithm2 = TvPipKeepClearAlgorithm.this;
                                Rect rect9 = rect7;
                                tvPipKeepClearAlgorithm2.getClass();
                                if (TvPipKeepClearAlgorithm.intersectsX(rect9, rect8)) {
                                    list.add(new TvPipKeepClearAlgorithm.SweepLineEvent(true, -rect8.top, booleanValue, false, 8, null));
                                    list.add(new TvPipKeepClearAlgorithm.SweepLineEvent(false, -rect8.bottom, booleanValue, false, 8, null));
                                }
                                return Unit.INSTANCE;
                            }
                        };
                    }
                };
                Function1 function15 = (Function1) function14.invoke(Boolean.FALSE);
                Iterator it5 = set.iterator();
                while (it5.hasNext()) {
                    function15.invoke(it5.next());
                }
                Function1 function16 = (Function1) function14.invoke(Boolean.TRUE);
                Iterator it6 = set2.iterator();
                while (it6.hasNext()) {
                    function16.invoke(it6.next());
                }
                SweepLineEvent sweepLineFindEarliestGap2 = sweepLineFindEarliestGap(arrayList5, rect5.height() + this.pipAreaPadding, -rect5.top, rect5.height());
                SweepLineEvent sweepLineEvent = new SweepLineEvent(sweepLineFindEarliestGap2.open, -sweepLineFindEarliestGap2.pos, sweepLineFindEarliestGap2.unrestricted, sweepLineFindEarliestGap2.start);
                int i3 = (sweepLineEvent.pos - rect.top) + (sweepLineEvent.start ? 0 : this.pipAreaPadding);
                int height3 = sweepLineEvent.unrestricted ? rect2.height() : round;
                Rect rect7 = new Rect(rect);
                rect7.offset(width4, i3);
                boolean z4 = rect7.bottom < rect2.bottom;
                z = true;
                boolean z5 = !intersectsY(rect7, rect4);
                if (z4 && Math.abs(i3) <= height3 && !z5) {
                    arrayList3.add(rect7);
                }
                i = 0;
            } else {
                i = 0;
                z = true;
            }
        }
        Iterator it7 = arrayList3.iterator();
        if (it7.hasNext()) {
            Object next2 = it7.next();
            if (it7.hasNext()) {
                int candidateCost = candidateCost((Rect) next2, rect);
                do {
                    Object next3 = it7.next();
                    int candidateCost2 = candidateCost((Rect) next3, rect);
                    if (candidateCost > candidateCost2) {
                        next2 = next3;
                        candidateCost = candidateCost2;
                    }
                } while (it7.hasNext());
            }
            obj = next2;
        } else {
            obj = null;
        }
        return (Rect) obj;
    }

    public final Rect findRelaxedMovePosition(int i, Rect rect, Set set, Set set2) {
        Object obj;
        if (i == 0) {
            return findFreeMovePosition(rect, set, set2);
        }
        ArrayList arrayList = new ArrayList();
        for (Rect rect2 : CollectionsKt___CollectionsKt.toList(set)) {
            set.remove(rect2);
            Rect findRelaxedMovePosition = findRelaxedMovePosition(i - 1, rect, set, set2);
            set.add(rect2);
            if (findRelaxedMovePosition != null) {
                arrayList.add(findRelaxedMovePosition);
            }
        }
        Iterator it = arrayList.iterator();
        if (it.hasNext()) {
            Object next = it.next();
            if (it.hasNext()) {
                int candidateCost = candidateCost((Rect) next, rect);
                do {
                    Object next2 = it.next();
                    int candidateCost2 = candidateCost((Rect) next2, rect);
                    if (candidateCost > candidateCost2) {
                        next = next2;
                        candidateCost = candidateCost2;
                    }
                } while (it.hasNext());
            }
            obj = next;
        } else {
            obj = null;
        }
        return (Rect) obj;
    }

    public final Rect fromTransformedSpace(Rect rect) {
        boolean shouldTransformRotate = shouldTransformRotate();
        Size size = this.screenSize;
        int height = shouldTransformRotate ? size.getHeight() : size.getWidth();
        Size size2 = this.screenSize;
        int width = shouldTransformRotate ? size2.getWidth() : size2.getHeight();
        Point[] pointArr = {new Point(rect.left, rect.top), new Point(rect.right, rect.top), new Point(rect.right, rect.bottom), new Point(rect.left, rect.bottom)};
        int i = 0;
        while (true) {
            boolean z = true;
            if (i >= 4) {
                break;
            }
            Point point = pointArr[i];
            int i2 = this.pipGravity;
            if (i2 == 3 || i2 == 19 || i2 == 51 || i2 == 83 || i2 == 48 || i2 == 49) {
                point.x = height - point.x;
            }
            if (i2 != 51 && i2 != 53) {
                z = false;
            }
            if (z) {
                point.y = width - point.y;
            }
            i++;
        }
        if (shouldTransformRotate) {
            for (int i3 = 0; i3 < 4; i3++) {
                Point point2 = pointArr[i3];
                int width2 = point2.y - this.screenSize.getWidth();
                int i4 = point2.x;
                point2.x = -width2;
                point2.y = i4;
            }
        }
        Point point3 = pointArr[0];
        int i5 = point3.y;
        IntProgressionIterator it = new IntRange(1, 3).iterator();
        while (it.hasNext) {
            Point point4 = pointArr[it.nextInt()];
            int i6 = point4.y;
            if (i5 > i6) {
                point3 = point4;
                i5 = i6;
            }
        }
        Intrinsics.checkNotNull(point3);
        int i7 = point3.y;
        Point point5 = pointArr[0];
        int i8 = point5.x;
        IntProgressionIterator it2 = new IntRange(1, 3).iterator();
        while (it2.hasNext) {
            Point point6 = pointArr[it2.nextInt()];
            int i9 = point6.x;
            if (i8 < i9) {
                point5 = point6;
                i8 = i9;
            }
        }
        Intrinsics.checkNotNull(point5);
        int i10 = point5.x;
        Point point7 = pointArr[0];
        int i11 = point7.y;
        IntProgressionIterator it3 = new IntRange(1, 3).iterator();
        while (it3.hasNext) {
            Point point8 = pointArr[it3.nextInt()];
            int i12 = point8.y;
            if (i11 < i12) {
                point7 = point8;
                i11 = i12;
            }
        }
        Intrinsics.checkNotNull(point7);
        int i13 = point7.y;
        Point point9 = pointArr[0];
        int i14 = point9.x;
        IntProgressionIterator it4 = new IntRange(1, 3).iterator();
        while (it4.hasNext) {
            Point point10 = pointArr[it4.nextInt()];
            int i15 = point10.x;
            if (i14 > i15) {
                point9 = point10;
                i14 = i15;
            }
        }
        Intrinsics.checkNotNull(point9);
        return new Rect(point9.x, i7, i10, i13);
    }

    public final boolean isPipAnchoredToCorner() {
        int i = this.pipGravity;
        return (((i & 7) == 3) || ((i & 7) == 5)) && (((i & 112) == 48) || ((i & 112) == 80));
    }

    public final boolean shouldTransformRotate() {
        int i = this.pipGravity;
        int i2 = i & 7;
        if (i2 == 3 || i2 == 5) {
            return false;
        }
        int i3 = i & 112;
        return i3 == 48 || i3 == 80;
    }

    public final Rect toTransformedSpace(Rect rect) {
        int width = this.screenSize.getWidth();
        int height = this.screenSize.getHeight();
        Point[] pointArr = {new Point(rect.left, rect.top), new Point(rect.right, rect.top), new Point(rect.right, rect.bottom), new Point(rect.left, rect.bottom)};
        if (shouldTransformRotate()) {
            for (int i = 0; i < 4; i++) {
                Point point = pointArr[i];
                int i2 = point.x;
                point.x = point.y;
                point.y = (-i2) + width;
            }
            width = this.screenSize.getHeight();
            height = this.screenSize.getWidth();
        }
        int i3 = 0;
        while (true) {
            boolean z = true;
            if (i3 >= 4) {
                break;
            }
            Point point2 = pointArr[i3];
            int i4 = this.pipGravity;
            if (i4 == 3 || i4 == 19 || i4 == 51 || i4 == 83 || i4 == 48 || i4 == 49) {
                point2.x = width - point2.x;
            }
            if (i4 != 51 && i4 != 53) {
                z = false;
            }
            if (z) {
                point2.y = height - point2.y;
            }
            i3++;
        }
        Point point3 = pointArr[0];
        int i5 = point3.y;
        IntProgressionIterator it = new IntRange(1, 3).iterator();
        while (it.hasNext) {
            Point point4 = pointArr[it.nextInt()];
            int i6 = point4.y;
            if (i5 > i6) {
                point3 = point4;
                i5 = i6;
            }
        }
        Intrinsics.checkNotNull(point3);
        int i7 = point3.y;
        Point point5 = pointArr[0];
        int i8 = point5.x;
        IntProgressionIterator it2 = new IntRange(1, 3).iterator();
        while (it2.hasNext) {
            Point point6 = pointArr[it2.nextInt()];
            int i9 = point6.x;
            if (i8 < i9) {
                point5 = point6;
                i8 = i9;
            }
        }
        Intrinsics.checkNotNull(point5);
        int i10 = point5.x;
        Point point7 = pointArr[0];
        int i11 = point7.y;
        IntProgressionIterator it3 = new IntRange(1, 3).iterator();
        while (it3.hasNext) {
            Point point8 = pointArr[it3.nextInt()];
            int i12 = point8.y;
            if (i11 < i12) {
                point7 = point8;
                i11 = i12;
            }
        }
        Intrinsics.checkNotNull(point7);
        int i13 = point7.y;
        Point point9 = pointArr[0];
        int i14 = point9.x;
        IntProgressionIterator it4 = new IntRange(1, 3).iterator();
        while (it4.hasNext) {
            Point point10 = pointArr[it4.nextInt()];
            int i15 = point10.x;
            if (i14 > i15) {
                point9 = point10;
                i14 = i15;
            }
        }
        Intrinsics.checkNotNull(point9);
        return new Rect(point9.x, i7, i10, i13);
    }

    public final Set transformAndFilterAreas(Set set) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = ((ArraySet) set).iterator();
        while (it.hasNext()) {
            Rect rect = (Rect) it.next();
            Rect transformedSpace = rect.contains(this.movementBounds) ? null : toTransformedSpace(rect);
            if (transformedSpace != null) {
                linkedHashSet.add(transformedSpace);
            }
        }
        return linkedHashSet;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SweepLineEvent {
        public final boolean open;
        public final int pos;
        public final boolean start;
        public final boolean unrestricted;

        public SweepLineEvent(boolean z, int i, boolean z2, boolean z3) {
            this.open = z;
            this.pos = i;
            this.unrestricted = z2;
            this.start = z3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SweepLineEvent)) {
                return false;
            }
            SweepLineEvent sweepLineEvent = (SweepLineEvent) obj;
            return this.open == sweepLineEvent.open && this.pos == sweepLineEvent.pos && this.unrestricted == sweepLineEvent.unrestricted && this.start == sweepLineEvent.start;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v1, types: [int] */
        /* JADX WARN: Type inference failed for: r1v7 */
        /* JADX WARN: Type inference failed for: r1v8 */
        /* JADX WARN: Type inference failed for: r2v1, types: [boolean] */
        public final int hashCode() {
            boolean z = this.open;
            ?? r1 = z;
            if (z) {
                r1 = 1;
            }
            int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.pos, r1 * 31, 31);
            ?? r2 = this.unrestricted;
            int i = r2;
            if (r2 != 0) {
                i = 1;
            }
            int i2 = (m42m + i) * 31;
            boolean z2 = this.start;
            return i2 + (z2 ? 1 : z2 ? 1 : 0);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SweepLineEvent(open=");
            sb.append(this.open);
            sb.append(", pos=");
            sb.append(this.pos);
            sb.append(", unrestricted=");
            sb.append(this.unrestricted);
            sb.append(", start=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.start, ")");
        }

        public /* synthetic */ SweepLineEvent(boolean z, int i, boolean z2, boolean z3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(z, i, z2, (i2 & 8) != 0 ? false : z3);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Placement {
        public final Rect anchorBounds;
        public final Rect bounds;
        public final int stashType;
        public final boolean triggerStash;
        public final Rect unstashDestinationBounds;

        public Placement(Rect rect, Rect rect2, int i, Rect rect3, boolean z) {
            this.bounds = rect;
            this.anchorBounds = rect2;
            this.stashType = i;
            this.unstashDestinationBounds = rect3;
            this.triggerStash = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Placement)) {
                return false;
            }
            Placement placement = (Placement) obj;
            return Intrinsics.areEqual(this.bounds, placement.bounds) && Intrinsics.areEqual(this.anchorBounds, placement.anchorBounds) && this.stashType == placement.stashType && Intrinsics.areEqual(this.unstashDestinationBounds, placement.unstashDestinationBounds) && this.triggerStash == placement.triggerStash;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.stashType, (this.anchorBounds.hashCode() + (this.bounds.hashCode() * 31)) * 31, 31);
            Rect rect = this.unstashDestinationBounds;
            int hashCode = (m42m + (rect == null ? 0 : rect.hashCode())) * 31;
            boolean z = this.triggerStash;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            return hashCode + i;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Placement(bounds=");
            sb.append(this.bounds);
            sb.append(", anchorBounds=");
            sb.append(this.anchorBounds);
            sb.append(", stashType=");
            sb.append(this.stashType);
            sb.append(", unstashDestinationBounds=");
            sb.append(this.unstashDestinationBounds);
            sb.append(", triggerStash=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.triggerStash, ")");
        }

        public /* synthetic */ Placement(Rect rect, Rect rect2, int i, Rect rect3, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(rect, rect2, (i2 & 4) != 0 ? 0 : i, (i2 & 8) != 0 ? null : rect3, (i2 & 16) != 0 ? false : z);
        }
    }
}

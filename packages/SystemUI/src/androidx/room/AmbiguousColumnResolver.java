package androidx.room;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.room.AmbiguousColumnResolver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.builders.ListBuilder;
import kotlin.collections.builders.SetBuilder;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AmbiguousColumnResolver {
    public static final AmbiguousColumnResolver INSTANCE = new AmbiguousColumnResolver();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Match {
        public final List resultIndices;
        public final IntRange resultRange;

        public Match(IntRange intRange, List<Integer> list) {
            this.resultRange = intRange;
            this.resultIndices = list;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ResultColumn {
        public final int index;
        public final String name;

        public ResultColumn(String str, int i) {
            this.name = str;
            this.index = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ResultColumn)) {
                return false;
            }
            ResultColumn resultColumn = (ResultColumn) obj;
            return Intrinsics.areEqual(this.name, resultColumn.name) && this.index == resultColumn.index;
        }

        public final int hashCode() {
            return Integer.hashCode(this.index) + (this.name.hashCode() * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ResultColumn(name=");
            sb.append(this.name);
            sb.append(", index=");
            return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.index, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Solution implements Comparable {
        public static final Companion Companion = new Companion(null);
        public static final Solution NO_SOLUTION = new Solution(EmptyList.INSTANCE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        public final int coverageOffset;
        public final List matches;
        public final int overlaps;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public Solution(List<Match> list, int i, int i2) {
            this.matches = list;
            this.coverageOffset = i;
            this.overlaps = i2;
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            Solution solution = (Solution) obj;
            int compare = Intrinsics.compare(this.overlaps, solution.overlaps);
            return compare != 0 ? compare : Intrinsics.compare(this.coverageOffset, solution.coverageOffset);
        }
    }

    private AmbiguousColumnResolver() {
    }

    public static void dfs(List list, List list2, int i, Function1 function1) {
        ArrayList arrayList = (ArrayList) list;
        if (i == arrayList.size()) {
            function1.mo779invoke(CollectionsKt___CollectionsKt.toList(list2));
            return;
        }
        Iterator it = ((Iterable) arrayList.get(i)).iterator();
        while (it.hasNext()) {
            ArrayList arrayList2 = (ArrayList) list2;
            arrayList2.add(it.next());
            INSTANCE.getClass();
            dfs(list, list2, i + 1, function1);
            arrayList2.remove(arrayList2.size() - 1);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v6, types: [T, androidx.room.AmbiguousColumnResolver$Solution] */
    public static final int[][] resolve(List list, String[][] strArr) {
        String[][] strArr2 = strArr;
        int i = 0;
        String[] strArr3 = (String[]) list.toArray(new String[0]);
        int length = strArr3.length;
        for (int i2 = 0; i2 < length; i2++) {
            String str = strArr3[i2];
            if (str.charAt(0) == '`' && str.charAt(str.length() - 1) == '`') {
                str = str.substring(1, str.length() - 1);
            }
            strArr3[i2] = str.toLowerCase(Locale.ROOT);
        }
        int length2 = strArr2.length;
        for (int i3 = 0; i3 < length2; i3++) {
            int length3 = strArr2[i3].length;
            for (int i4 = 0; i4 < length3; i4++) {
                String[] strArr4 = strArr2[i3];
                strArr4[i4] = strArr4[i4].toLowerCase(Locale.ROOT);
            }
        }
        SetBuilder setBuilder = new SetBuilder();
        for (String[] strArr5 : strArr2) {
            CollectionsKt__MutableCollectionsKt.addAll(setBuilder, strArr5);
        }
        SetBuilder build = setBuilder.build();
        ListBuilder createListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        int length4 = strArr3.length;
        int i5 = 0;
        int i6 = 0;
        while (i5 < length4) {
            String str2 = strArr3[i5];
            int i7 = i6 + 1;
            if (build.contains(str2)) {
                createListBuilder.add(new ResultColumn(str2, i6));
            }
            i5++;
            i6 = i7;
        }
        ListBuilder build2 = createListBuilder.build();
        int length5 = strArr2.length;
        final ArrayList arrayList = new ArrayList(length5);
        for (int i8 = 0; i8 < length5; i8++) {
            arrayList.add(new ArrayList());
        }
        int length6 = strArr2.length;
        int i9 = 0;
        final int i10 = 0;
        while (true) {
            AmbiguousColumnResolver ambiguousColumnResolver = INSTANCE;
            if (i9 >= length6) {
                if (!arrayList.isEmpty()) {
                    int size = arrayList.size();
                    int i11 = 0;
                    while (i11 < size) {
                        Object obj = arrayList.get(i11);
                        i11++;
                        if (((List) obj).isEmpty()) {
                            throw new IllegalStateException("Failed to find matches for all mappings");
                        }
                    }
                }
                final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                Solution.Companion.getClass();
                ref$ObjectRef.element = Solution.NO_SOLUTION;
                Function1 function1 = new Function1() { // from class: androidx.room.AmbiguousColumnResolver$$ExternalSyntheticLambda2
                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r0v1, types: [T, androidx.room.AmbiguousColumnResolver$Solution] */
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        List list2 = (List) obj2;
                        AmbiguousColumnResolver ambiguousColumnResolver2 = AmbiguousColumnResolver.INSTANCE;
                        AmbiguousColumnResolver.Solution.Companion.getClass();
                        List<AmbiguousColumnResolver.Match> list3 = list2;
                        int i12 = 0;
                        int i13 = 0;
                        for (AmbiguousColumnResolver.Match match : list3) {
                            IntRange intRange = match.resultRange;
                            i13 += ((intRange.last - intRange.first) + 1) - match.resultIndices.size();
                        }
                        Iterator it = list3.iterator();
                        if (!it.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        int i14 = ((AmbiguousColumnResolver.Match) it.next()).resultRange.first;
                        while (it.hasNext()) {
                            int i15 = ((AmbiguousColumnResolver.Match) it.next()).resultRange.first;
                            if (i14 > i15) {
                                i14 = i15;
                            }
                        }
                        Iterator it2 = list3.iterator();
                        if (!it2.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        int i16 = ((AmbiguousColumnResolver.Match) it2.next()).resultRange.last;
                        while (it2.hasNext()) {
                            int i17 = ((AmbiguousColumnResolver.Match) it2.next()).resultRange.last;
                            if (i16 < i17) {
                                i16 = i17;
                            }
                        }
                        Iterable intRange2 = new IntRange(i14, i16);
                        if (!(intRange2 instanceof Collection) || !((Collection) intRange2).isEmpty()) {
                            IntProgressionIterator it3 = intRange2.iterator();
                            int i18 = 0;
                            while (it3.hasNext) {
                                int nextInt = it3.nextInt();
                                Iterator it4 = list3.iterator();
                                int i19 = 0;
                                while (true) {
                                    if (it4.hasNext()) {
                                        IntRange intRange3 = ((AmbiguousColumnResolver.Match) it4.next()).resultRange;
                                        if (intRange3.first <= nextInt && nextInt <= intRange3.last) {
                                            i19++;
                                        }
                                        if (i19 > 1) {
                                            i18++;
                                            if (i18 < 0) {
                                                CollectionsKt__CollectionsKt.throwCountOverflow();
                                                throw null;
                                            }
                                        }
                                    }
                                }
                            }
                            i12 = i18;
                        }
                        ?? solution = new AmbiguousColumnResolver.Solution(list2, i13, i12);
                        Ref$ObjectRef ref$ObjectRef2 = Ref$ObjectRef.this;
                        AmbiguousColumnResolver.Solution solution2 = (AmbiguousColumnResolver.Solution) ref$ObjectRef2.element;
                        int compare = Intrinsics.compare(solution.overlaps, solution2.overlaps);
                        if (compare == 0) {
                            compare = Intrinsics.compare(solution.coverageOffset, solution2.coverageOffset);
                        }
                        if (compare < 0) {
                            ref$ObjectRef2.element = solution;
                        }
                        return Unit.INSTANCE;
                    }
                };
                ArrayList arrayList2 = new ArrayList();
                ambiguousColumnResolver.getClass();
                dfs(arrayList, arrayList2, 0, function1);
                List list2 = ((Solution) ref$ObjectRef.element).matches;
                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    arrayList3.add(CollectionsKt___CollectionsKt.toIntArray(((Match) it.next()).resultIndices));
                }
                return (int[][]) arrayList3.toArray(new int[0][]);
            }
            final String[] strArr6 = strArr2[i9];
            int i12 = i10 + 1;
            Function3 function3 = new Function3() { // from class: androidx.room.AmbiguousColumnResolver$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj2, Object obj3, Object obj4) {
                    Object obj5;
                    List list3 = arrayList;
                    int intValue = ((Integer) obj2).intValue();
                    int intValue2 = ((Integer) obj3).intValue();
                    List list4 = (List) obj4;
                    AmbiguousColumnResolver ambiguousColumnResolver2 = AmbiguousColumnResolver.INSTANCE;
                    String[] strArr7 = strArr6;
                    ArrayList arrayList4 = new ArrayList(strArr7.length);
                    for (String str3 : strArr7) {
                        Iterator it2 = list4.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                obj5 = null;
                                break;
                            }
                            obj5 = it2.next();
                            if (Intrinsics.areEqual(str3, ((AmbiguousColumnResolver.ResultColumn) obj5).name)) {
                                break;
                            }
                        }
                        AmbiguousColumnResolver.ResultColumn resultColumn = (AmbiguousColumnResolver.ResultColumn) obj5;
                        if (resultColumn == null) {
                            return Unit.INSTANCE;
                        }
                        arrayList4.add(Integer.valueOf(resultColumn.index));
                    }
                    ((List) list3.get(i10)).add(new AmbiguousColumnResolver.Match(new IntRange(intValue, intValue2 - 1), arrayList4));
                    return Unit.INSTANCE;
                }
            };
            ambiguousColumnResolver.getClass();
            int length7 = strArr6.length;
            int i13 = i;
            int i14 = i13;
            while (i13 < length7) {
                i14 += strArr6[i13].hashCode();
                i13++;
            }
            int length8 = strArr6.length;
            ListIterator listIterator = ((ListBuilder.BuilderSubList) build2.subList(i, length8)).listIterator(i);
            int i15 = i;
            while (true) {
                ListBuilder.BuilderSubList.Itr itr = (ListBuilder.BuilderSubList.Itr) listIterator;
                if (!itr.hasNext()) {
                    break;
                }
                i15 += ((ResultColumn) itr.next()).name.hashCode();
            }
            int i16 = i;
            while (true) {
                if (i14 == i15) {
                    function3.invoke(Integer.valueOf(i16), Integer.valueOf(length8), build2.subList(i16, length8));
                }
                int i17 = i16 + 1;
                int i18 = length8 + 1;
                if (i18 > build2.getSize()) {
                    break;
                }
                i15 = (i15 - ((ResultColumn) build2.get(i16)).name.hashCode()) + ((ResultColumn) build2.get(length8)).name.hashCode();
                i16 = i17;
                length8 = i18;
            }
            if (((List) arrayList.get(i10)).isEmpty()) {
                ArrayList arrayList4 = new ArrayList(strArr6.length);
                for (String str3 : strArr6) {
                    ListBuilder createListBuilder2 = CollectionsKt__CollectionsJVMKt.createListBuilder();
                    ListIterator listIterator2 = build2.listIterator(0);
                    while (true) {
                        ListBuilder.Itr itr2 = (ListBuilder.Itr) listIterator2;
                        if (!itr2.hasNext()) {
                            break;
                        }
                        ResultColumn resultColumn = (ResultColumn) itr2.next();
                        if (Intrinsics.areEqual(str3, resultColumn.name)) {
                            createListBuilder2.add(Integer.valueOf(resultColumn.index));
                        }
                    }
                    ListBuilder build3 = createListBuilder2.build();
                    if (build3.isEmpty()) {
                        throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Column ", str3, " not found in result").toString());
                    }
                    arrayList4.add(build3);
                }
                Function1 function12 = new Function1() { // from class: androidx.room.AmbiguousColumnResolver$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        List list3 = arrayList;
                        List list4 = (List) obj2;
                        AmbiguousColumnResolver ambiguousColumnResolver2 = AmbiguousColumnResolver.INSTANCE;
                        List list5 = list4;
                        Iterator it2 = list5.iterator();
                        if (!it2.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        int intValue = ((Number) it2.next()).intValue();
                        while (it2.hasNext()) {
                            int intValue2 = ((Number) it2.next()).intValue();
                            if (intValue > intValue2) {
                                intValue = intValue2;
                            }
                        }
                        Iterator it3 = list5.iterator();
                        if (!it3.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        int intValue3 = ((Number) it3.next()).intValue();
                        while (it3.hasNext()) {
                            int intValue4 = ((Number) it3.next()).intValue();
                            if (intValue3 < intValue4) {
                                intValue3 = intValue4;
                            }
                        }
                        ((List) list3.get(i10)).add(new AmbiguousColumnResolver.Match(new IntRange(intValue, intValue3), list4));
                        return Unit.INSTANCE;
                    }
                };
                ArrayList arrayList5 = new ArrayList();
                ambiguousColumnResolver.getClass();
                dfs(arrayList4, arrayList5, 0, function12);
            }
            i9++;
            strArr2 = strArr;
            i10 = i12;
            i = 0;
        }
    }
}
